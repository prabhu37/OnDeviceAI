package com.google.mediapipe.examples.llminference

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.common.util.concurrent.ListenableFuture
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.genai.llminference.LlmInferenceSession
import com.google.mediapipe.tasks.genai.llminference.LlmInferenceSession.LlmInferenceSessionOptions
import com.google.mediapipe.tasks.genai.llminference.ProgressListener
import org.koin.core.annotation.Single
import java.io.File
import kotlin.math.max

/** The maximum number of tokens the model can process. */
var MAX_TOKENS = 10024

/**
 * An offset in tokens that we use to ensure that the model always has the ability to respond when
 * we compute the remaining context length.
 */
var DECODE_TOKEN_OFFSET = 256

class ModelLoadFailException :
    Exception("Failed to load model, please try again")

class ModelSessionCreateFailException :
    Exception("Failed to create model session, please try again")


class InferenceModel  private constructor(context: Context) {
    private lateinit var llmInference: LlmInference
    private lateinit var llmInferenceSession: LlmInferenceSession
    private val TAG = InferenceModel::class.qualifiedName

    val uiState: UiState

    init {
        if (!modelExists(context)) {
            throw IllegalArgumentException("Model not found at path: ${model.path}")
        }

        uiState = model.uiState
        createEngine(context)
        createSession()
    }

    fun close() {
        llmInferenceSession.close()
        llmInference.close()
    }

    fun resetSession() {
        llmInferenceSession.close()
        createSession()
    }

    private fun createEngine(context: Context) {
        val inferenceOptions = LlmInference.LlmInferenceOptions.builder()
            .setModelPath(modelPath(context))
            .setMaxTokens(MAX_TOKENS)
            .apply { model.preferredBackend?.let { setPreferredBackend(it) } }
            .build()

        try {
            llmInference = LlmInference.createFromOptions(context, inferenceOptions)
        } catch (e: Exception) {
            Log.e(TAG, "Load model error: ${e.message}", e)
            throw ModelLoadFailException()
        }
    }

    private fun createSession() {
        val sessionOptions =  LlmInferenceSessionOptions.builder()
            .setTemperature(model.temperature)
            .setTopK(model.topK)
            .setTopP(model.topP)
            .build()

        try {
            llmInferenceSession =
                LlmInferenceSession.createFromOptions(llmInference, sessionOptions)
        } catch (e: Exception) {
            Log.e(TAG, "LlmInferenceSession create error: ${e.message}", e)
            throw ModelSessionCreateFailException()
        }
    }

    fun generateResponseAsync(prompt: String, progressListener: ProgressListener<String>) : ListenableFuture<String> {
      /*  val prompt1 = "Here is the retrieved context --------------------------------------------------  \n" +
                "                                                                                                    water \n" +
                "                                                                                                    electricity \n" +
                "                                                                                                    facilities. \n" +
                "                                                                                                    IN \n" +
                "                                                                                                    WITNESSES \n" +
                "                                                                                                    WHEREOF BOTH \n" +
                "                                                                                                    THE \n" +
                "                                                                                                    PARTIES AGREES AND SIGN \n" +
                "                                                                                                    THIS \n" +
                "                                                                                                    AGREEMENT ON THIS DAY. \n" +
                "                                                                                                    WITNESSES: \n" +
                "                                                                                                    1. \n" +
                "                                                                                                    OWNER/LESSOR \n" +
                "                                                                                                    1SHSVA \n" +
                "                                                                                                    2.Saaa \n" +
                "                                                                                                    TENANT/LESSEE \n" +
                "                                                                                                    k Se khan  ar month. \n" +
                "                                                                                                    2. The rental agreement is for a period of 11(Eleven) months, commencing \n" +
                "                                                                                                    from 01.04.2024  \n" +
                "                                                                                                    INDIA NON JUDICIAL \n" +
                "                                                                                                    Government of Karnataka \n" +
                "                                                                                                    -Stamp \n" +
                "                                                                                                    Certificate No. IN-KA9461840698830OW \n" +
                "                                                                                                    14-Dec-2024 1055 AM \n" +
                "                                                                                                    Certificate Issued Date \n" +
                "                                                                                                    NONACC (FI)) kagcsi08 IBBALURU/ KA-GN \n" +
                "                                                                                                    Account Reterence \n" +
                "                                                                                                    SUBIN-KAKAGCSLO828340078988141W \n" +
                "                                                                                                    Unique Doc Reference \n" +
                "                                                                                                    PRABHAKARAN PANJALINGAM \n" +
                "                                                                                                    Purchased by \n" +
                "                                                                                                    year \n" +
                "                                                                                                    Property - Not exceeding 1 \n" +
                "                                                                                                    of Immovable \n" +
                "                                                                                                    30(1)0 Lease \n" +
                "                                                                                                    Article \n" +
                "                                                                                                    Description of Document \n" +
                "                                                                                                    in case of Residential property \n" +
                "                                                                                                    RENTAL AGREEMENT \n" +
                "                                                                                                    Property Description \n" +
                "                                                                                                    22,000 \n" +
                "                                                                                                    Consideration Price/Others (Rs.) e OTHER PART: \n" +
                "                                                                                                    WITNESSES AS FOLLOWS: \n" +
                "                                                                                                    the \n" +
                "                                                                                                    Whereas owner is \n" +
                "                                                                                                    the sole \n" +
                "                                                                                                    and \n" +
                "                                                                                                    absolute owner of the \n" +
                "                                                                                                    Sc \n" +
                "                                                                                                    (Twenty Two Thousand only) \n" +
                "                                                                                                    SREENIVASULU A \n" +
                "                                                                                                    First Party \n" +
                "                                                                                                    :PRABHAKARAN PANJALINGAM \n" +
                "                                                                                                    Second Party \n" +
                "                                                                                                    PRABHAKARAN PANJALINGAM \n" +
                "                                                                                                    Stamp Duty Paid By \n" +
                "                                                                                                    ) BR \n" +
                "                                                                                                    1 10 \n" +
                "                                                                                                    Stamp Duty Amount(As.) \n" +
                "                                                                                                    (One Hundred And Ten only) \n" +
                "                                                                                                    ato \n" +
                "                                                                                                    Please wte or type below this lne \n" +
                "                                                                                                    RENTAL AGREEMENT \n" +
                "                                                                                                    This RENTAL AGREEMENT is made and executed at Bangalore on 14h \n" +
                "                                                                                                    day of December 2024 (14. 11.2024) Effective from 01.04.2024 by and \n" +
                "                                                                                                    between: \n" +
                "                                                                                                    Mr. SREENIVASULU A \n" +
                "                                                                                                    S/o Sreeramulu Adimulam \n" +
                "                                                                                                    #34, 2nd Cross Sun City Main Road, \n" +
                "                                                                                                  Iblur -------------------------------------------------- Here is the user's query: which agreement it is?"*/
        val formattedPrompt = model.uiState.formatPrompt(prompt)
        llmInferenceSession.addQueryChunk(formattedPrompt)
        return llmInferenceSession.generateResponseAsync(progressListener)
    }

    fun estimateTokensRemaining(prompt: String): Int {
        val context = uiState.messages.joinToString { it.rawMessage } + prompt
        if (context.isEmpty()) return -1 // Specia marker if no content has been added

        val sizeOfAllMessages = llmInferenceSession.sizeInTokens(context)
        val approximateControlTokens = uiState.messages.size * 3
        val remainingTokens = MAX_TOKENS - sizeOfAllMessages - approximateControlTokens -  DECODE_TOKEN_OFFSET
        // Token size is approximate so, let's not return anything below 0
        return max(0, remainingTokens)
    }

    companion object {
        var model: Model = Model.GEMMA3_CPU
        private var instance: InferenceModel? = null

        fun getInstance(context: Context): InferenceModel {
            return if (instance != null) {
                instance!!
            } else {
                InferenceModel(context).also { instance = it }
            }
        }

        fun resetInstance(context: Context): InferenceModel {
            return InferenceModel(context).also { instance = it }
        }

        fun modelPathFromUrl(context: Context): String {
            if (model.url.isNotEmpty()) {
                val urlFileName = Uri.parse(model.url).lastPathSegment
                if (!urlFileName.isNullOrEmpty()) {
                    return File(context.filesDir, urlFileName).absolutePath
                }
            }

            return ""
        }

        fun modelPath(context: Context): String {
            val modelFile = File(model.path)
            if (modelFile.exists()) {
                return model.path
            }

            return modelPathFromUrl(context)
        }

        fun modelExists(context: Context): Boolean {
            return File(modelPath(context)).exists()
        }
    }
}
