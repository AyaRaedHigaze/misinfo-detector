# TruthLens: Misinformation Detection API

TruthLens is a backend Spring Boot service designed to analyze textual claims for misinformation, logical fallacies, and cognitive biases. It uses the Gemini AI (Google) API for analysis, and optionally generates fact-checking search links for further validation.

---

## 🚑 Built With Love By

* Aya Higaze (Backend developer)
* Khadijah Abd el Ghane (Frontend developer)


---

## ✨ Overview

TruthLens receives a text input via a RESTful API, sends it to the Gemini model for deep analysis, and returns:

* **flags**: Detected misinformation, fallacies, or biases
* **explanation**: A scientific or logical summary of the issue
* **sources**: Relevant source links (from Gemini or search generators)
* **neutral**: A boolean indicating whether the claim was neutral (i.e., no flags)

This API is intended to be used as the backend of a misinformation detection platform.

---

## ⚡ Technologies Used

* Java 17
* Spring Boot
* REST API (Spring Web)
* Google Gemini API
* Jackson (for JSON processing)
* Lombok (for cleaner models)

---

## 📊 Example Request

### Endpoint:

`POST /api/analyze`

### Request Body:

```json
{
  "text": "Drinking bleach can cure COVID-19."
}
```

### Example Response:

```json
{
  "flags": [
    "Misinformation",
    "Appeal to False Authority",
    "Oversimplification",
    "Appeal to Desperation"
  ],
  "explanation": "The claim that drinking bleach can cure COVID-19 is false and extremely dangerous...",
  "sources": [
    "https://www.who.int/...",
    "https://www.fda.gov/...",
    "https://www.poison.org/..."
  ],
  "neutral": false
}
```

---

## 📰 Prompt Used for Gemini

````text
Claim: "..."

Analyze the claim for:
1. Logical fallacies (if any)
2. Cognitive biases (if any)
3. A scientific explanation to address or correct the claim, if needed.

If the claim is purely subjective (such as an opinion or personal statement), and does not contain misinformation,
logical fallacies, or cognitive biases, respond with an empty flags list and a simple explanation saying it's 
a personal opinion or neutral statement.

Return the `sources` field as a list of plain URLs in string format only.
Respond STRICTLY with a valid JSON object. Do NOT use Markdown formatting (no ```), and do NOT include any 
explanation or surrounding text.
````

---

## 📄 Folder Structure

```
com.truthlens.misinfodetector
├── controller
│   └── AnalysisController.java
├── model
│   ├── AnalysisRequest.java
│   └── AnalysisResult.java
├── service
│   ├── AIService.java
│   ├── AnalysisService.java
│   └── SimpleFactCheckService.java
└── resources
    └── application.properties
```

---

## 🔄 License

This project is for educational purposes.
