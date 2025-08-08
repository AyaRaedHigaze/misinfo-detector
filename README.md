
# 🌐 TruthLens: AI-Powered Misinformation Detection Tool

**TruthLens** is a full-stack web tool built to help users analyze short-form content (tweets, headlines, or social media posts) for **misinformation**, **logical fallacies**, and **cognitive biases** using AI.

It consists of:
- A responsive **frontend** (HTML/CSS/JavaScript)
- A powerful **backend** built with Spring Boot
- Integration with **Gemini (Google AI)** for natural language understanding

---

## 👥 Developed By

- **Aya Higaze, 211910872** – Backend Developer (Spring Boot + AI Integration)
- **Khadijah Abd el Ghane, 212060073** – Frontend Developer (HTML/CSS/JS + UX + Documentation)

---

## ✨ Features

✅ Analyze any short text or claim  
✅ Detect fallacies, biases, and misinformation  
✅ Generate links for external fact-checking  
✅ Show explanations and source links  
✅ Clear, readable JSON output with raw data toggle  
✅ Frontend-ready for deployment or extension

---

## 🧠 How It Works

1. User enters a short claim via a textarea field in the UI
2. The frontend sends a `POST` request to `/api/analyze` with the claim
3. The backend calls Gemini AI with a prompt to analyze the claim
4. Gemini responds with:
    - `flags`: list of detected problems
    - `explanation`: summary of the claim’s issues
    - `sources`: related links
    - `neutral`: boolean for neutral claims
5. The result is rendered dynamically to the user

---

## ⚙️ Technologies Used

### 🔧 Backend (Spring Boot)
- Java 17
- Spring Boot
- Google Gemini API
- Jackson (JSON parsing)
- Lombok (for models)
- RESTful APIs

### 🎨 Frontend (Vanilla JS)
- HTML5
- CSS3
- JavaScript (DOM, Fetch API)

---

## 🖥️ Project Structure

```
├── backend/
│   └── src/main/java/com/truthlens/misinfodetector/
│       ├── controller/
│       │   └── AnalysisController.java
│       ├── model/
│       │   ├── AnalysisRequest.java
│       │   └── AnalysisResult.java
│       ├── service/
│       │   ├── AIService.java
│       │   ├── AnalysisService.java
│       │   └── SimpleFactCheckService.java
│       └── resources/
│           └── application.properties
│
├── frontend/
│   ├── index.html
│   ├── style.css
│   └── app.js
```

---

## 📊 API Example

### 🔹 Endpoint:
`POST /api/analyze`

### 🔹 Request:
```json
{
  "text": "Drinking bleach can cure COVID-19."
}
```

### 🔹 Response:
```json
{
  "flags": [
    "Misinformation",
    "Appeal to False Authority"
  ],
  "explanation": "The claim that drinking bleach can cure COVID-19 is false and extremely dangerous...",
  "sources": [
    "https://www.who.int/",
    "https://www.fda.gov/"
  ],
  "neutral": false
}
```

---

## 💬 Gemini Prompt Template

```
Claim: "[input]"

Analyze the claim for:
1. Logical fallacies (if any)
2. Cognitive biases (if any)
3. A scientific explanation to address or correct the claim, if needed.

If the claim is purely subjective, respond with an empty flags list and a short explanation stating it's neutral.

Return the result as a valid JSON object:
{
  "flags": ["..."],
  "explanation": "...",
  "sources": ["..."]
}
```

---

## 🚀 Running the Project

### 🔧 Backend:
```bash
cd backend
./mvnw spring-boot:run
```

Make sure to add Gemini API key in `application.properties`:
```
gemini.api.key=AIzaSyAbbyie3SEIiDgJcw1GSsd3vsEMxgkTltA
```

### 🌐 Frontend:
Just open `index.html` in your browser (it sends requests to the backend on the same host/port).

---

## 📎 Sample Claims to Try

- "Aliens landed in Canada in 2024"
- "Vaccines cause autism"
- "Coffee can cure depression"
- "The earth is flat"

---

## 📎 Attachments:
- GitHub Repository: https://github.com/AyaRaedHigaze/misinfo-detector
- Project Demo (YouTube): https://youtu.be/lb9cKWG6OyY


---

## 📜 License

This project is for educational use only.

---

## 🙌 Acknowledgments

- Google Gemini API
- Spring Boot community
- OpenAI / ChatGPT for prompt engineering assistance