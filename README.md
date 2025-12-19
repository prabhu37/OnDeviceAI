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
https://private-user-images.githubusercontent.com/13618996/528582101-3c50ad59-e43d-4521-95da-48d429fb6822.mp4?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjYxNDM1MDcsIm5iZiI6MTc2NjE0MzIwNywicGF0aCI6Ii8xMzYxODk5Ni81Mjg1ODIxMDEtM2M1MGFkNTktZTQzZC00NTIxLTk1ZGEtNDhkNDI5ZmI2ODIyLm1wND9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTEyMTklMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUxMjE5VDExMjAwN1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWNmYjM1OWU1NzdjZjc5NGNjM2YzZmUxZGM2M2RkOGY5MmVmMjFlM2JkY2Y3MTRkNzgzZTAyNjk4MjNlNmI0N2MmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.YY0LDvpU5ZUfqDSyLAiumloElc5ePU3u5w5fFuezsaE


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
