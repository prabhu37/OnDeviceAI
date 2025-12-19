# 📱 RAG-based GenAI Demo App  
### MediaPipe LLM · Hugging Face · ObjectBox

This demo Android application showcases **Retrieval-Augmented Generation (RAG)** using **MediaPipe LLM Inference**, **sentence embedding models**, and a **local vector database powered by ObjectBox**.

The app enables users to **chat with an on-device LLM** that retrieves **relevant context from locally stored documents** before generating responses — all while running **fully offline after the initial model download**.

---

## ✨ Features

- ✅ **On-device LLM inference** using MediaPipe GenAI  
- ✅ **Model download from Hugging Face** (task-compatible models)  
- ✅ **Retrieval-Augmented Generation (RAG)** pipeline  
- ✅ **Sentence embeddings** for semantic similarity search  
- ✅ **Local vector database** powered by ObjectBox  
- ✅ **Fully offline inference** after model download  

---

## 🧠 How It Works

1. Documents are embedded using a sentence embedding model  
2. Embeddings are stored locally in **ObjectBox Vector DB**  
3. User queries are converted into embeddings  
4. Relevant document chunks are retrieved via similarity search  
5. Retrieved context is passed to the **MediaPipe LLM**  
6. The LLM generates context-aware responses  

---

## 📦 Tech Stack

- **Android (Kotlin)**
- **MediaPipe GenAI / LLM Inference**
- **Hugging Face Models**
- **ObjectBox (Vector Database)**
- **On-device AI (Offline-first)**

---

## 🎥 Demo Video

📽️ Watch the demo here:  
https://github.com/prabhu37/OnDeviceAI/blob/main/demo_video_ondevice_ai.mp4


---

## 🚀 Use Cases

- Offline AI chat assistants  
- Private, on-device document Q&A  
- Knowledge-base assistants  
- Edge AI & mobile GenAI demos  

---

## 🔒 Privacy & Offline Support

- No server calls during inference  
- All data and embeddings stored locally  
- Works completely offline after model setup  

---

## 📌 Note

Ensure the downloaded Hugging Face model is **compatible with MediaPipe LLM tasks** for correct inference.
