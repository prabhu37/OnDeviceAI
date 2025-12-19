package com.google.mediapipe.examples.llminference

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.mediapipe.examples.llminference.data.ChunksDB
import com.google.mediapipe.examples.llminference.data.DocumentsDB
import com.google.mediapipe.examples.llminference.data.RetrievedContext
import com.google.mediapipe.examples.llminference.domain.embeddings.SentenceEmbeddingProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.math.max


class ChatViewModel(
    private var inferenceModel: InferenceModel,
    private val documentsDB: DocumentsDB,
    private val chunksDB: ChunksDB,
    private val sentenceEncoder: SentenceEmbeddingProvider,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(inferenceModel.uiState)
    val uiState: StateFlow<UiState> =_uiState.asStateFlow()

    private val _tokensRemaining = MutableStateFlow(-1)
    val tokensRemaining: StateFlow<Int> = _tokensRemaining.asStateFlow()

    private val _textInputEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isTextInputEnabled: StateFlow<Boolean> = _textInputEnabled.asStateFlow()

    fun resetInferenceModel(newModel: InferenceModel) {
        inferenceModel = newModel
        _uiState.value = inferenceModel.uiState
    }

    fun sendMessage(userMessage: String,prompt:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value.addMessage(userMessage, USER_PREFIX)
            _uiState.value.createLoadingMessage()
            setInputEnabled(false)
            try {
                inferenceModel.resetSession()
                var jointContext = ""
                val retrievedContextList = ArrayList<RetrievedContext>()
                val queryEmbedding = sentenceEncoder.encodeText(userMessage)
                chunksDB.getSimilarChunks(queryEmbedding, n = 5).forEach {
                    jointContext += " " + it.second.chunkData
                    retrievedContextList.add(RetrievedContext(it.second.docFileName, it.second.chunkData))
                }

                val inputPrompt = prompt.replace("\$CONTEXT", jointContext).replace("\$QUERY", userMessage+"  this is my query i need answer within minimum 1 line to maximum 5 lines")

                val asyncInference =  inferenceModel.generateResponseAsync(inputPrompt, { partialResult, done ->
                    _uiState.value.appendMessage(partialResult, done)
                    if (done) {
                        setInputEnabled(true)  // Re-enable text input
                    } else {
                        // Reduce current token count (estimate only). sizeInTokens() will be used
                        // when computation is done
                        _tokensRemaining.update { max(0, it - 1) }
                    }
                })
                // Once the inference is done, recompute the remaining size in tokens
                asyncInference.addListener({
                    viewModelScope.launch(Dispatchers.IO) {
                        recomputeSizeInTokens(userMessage)
                    }
                }, Dispatchers.Main.asExecutor())
            } catch (e: Exception) {
                _uiState.value.addMessage(e.localizedMessage ?: "Unknown Error", MODEL_PREFIX)
                setInputEnabled(true)
            }
        }
    }

    private fun setInputEnabled(isEnabled: Boolean) {
        _textInputEnabled.value = isEnabled
    }

    fun recomputeSizeInTokens(message: String) {
        val remainingTokens = inferenceModel.estimateTokensRemaining(message)
        _tokensRemaining.value = remainingTokens
    }

    companion object {
        fun getFactory(context: Context) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val inferenceModel = InferenceModel.getInstance(context)
                val documentsDB = DocumentsDB()
                val chunksDB = ChunksDB()
                val sentenceEncoder = SentenceEmbeddingProvider(context)
                return ChatViewModel(inferenceModel,documentsDB,chunksDB,sentenceEncoder) as T
            }
        }
    }
}
