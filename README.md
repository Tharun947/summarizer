# Summarizer

A Spring Boot based application that summarizes text, PDF files, URLs, and browser page content using the Groq API. The project also includes a Chrome extension that allows users to summarize the current webpage directly.

---

## Overview

This application enables users to generate summaries from multiple input sources:

- Raw text
- PDF documents
- Webpage URLs
- Live browser content (via Chrome extension)

It supports different summary formats such as default summaries, bullet points, detailed explanations, and key insights.

---

## Features

### Text Summarization
Accepts raw text input and generates:
- Summary
- Key points
- Notes

### PDF Summarization
- Upload PDF files
- Extracts content using PDFBox
- Generates summaries using AI

### URL Summarization
- Accepts webpage URLs
- Extracts readable content using jsoup
- Generates summaries

### Chrome Extension
- Summarizes any open webpage
- Sends page content to backend API
- Supports different summary types

### Summary Types
Supported values:
- DEFAULT
- BULLET_POINTS
- DETAILED
- KEY_INSIGHTS

---

## Technology Stack

Backend:
- Java
- Spring Boot
- Maven
- Apache PDFBox
- jsoup
- Groq API

Frontend (Extension):
- HTML
- CSS
- JavaScript
- Chrome Extension (Manifest V3)

Testing:
- Postman

---

## Project Structure

```
summarizer/
│
├── src/main/java/com/tharun/summarizer/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── service/
│   ├── exception/
│   └── SummarizerApplication.java
│
├── summarizer-chrome-extension/
│   ├── manifest.json
│   ├── popup.html
│   ├── popup.css
│   └── popup.js
│
├── pom.xml
└── README.md
```

---

## Setup

### 1. Configure API Key

Set the Groq API key as an environment variable:

```
setx GROQ_API_KEY "your_api_key"
```

Restart the terminal after setting the variable.

---

### 2. Run the Application

```
mvn spring-boot:run
```

Application will start at:
```
http://localhost:8080
```

---

## API Endpoints

### Text Summarization

```
POST /api/summarize/text
```

Request:
```json
{
  "text": "Your content",
  "summaryType": "DEFAULT"
}
```

---

### PDF Summarization

```
POST /api/summarize/file
```

Use Postman:
- Body → form-data
- Key: file (File)
- Optional: summaryType

---

### URL Summarization

```
POST /api/summarize/url
```

Request:
```json
{
  "url": "https://example.com",
  "summaryType": "KEY_INSIGHTS"
}
```

---

## Chrome Extension Setup

1. Open Chrome
2. Go to: chrome://extensions
3. Enable Developer Mode
4. Click "Load unpacked"
5. Select the summarizer-chrome-extension folder
6. Open any webpage
7. Click the extension and trigger summarization

---

