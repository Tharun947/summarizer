# AI Multi-Input Summarizer

A Spring Boot based AI summarization system that can summarize text, PDF files, webpage URLs, and browser page content using the Groq API. The project also includes a Chrome extension that reads the current webpage content and sends it to the backend summarization API.

---

## Project Overview

This project is designed to help users quickly generate summaries from different input sources. It supports:

* Raw text summarization
* PDF file summarization
* URL / webpage summarization
* Chrome extension page summarization
* Multiple summary types such as default summary, bullet points, detailed summary, and key insights

The backend is built using Java and Spring Boot. PDF text is extracted using PDFBox. Webpage content is extracted using jsoup. AI processing is handled using the Groq API.

---

## Features

### 1. Text Summarization

Users can send raw text to the backend and receive:

* Summary
* Key points
* Short revision notes

### 2. PDF Summarization

Users can upload a PDF file. The backend extracts text from the PDF and sends it to the AI model for summarization.

### 3. URL Summarization

Users can send an article or webpage URL. The backend fetches the webpage, extracts readable content, and summarizes it.

### 4. Chrome Extension

The Chrome extension allows users to open any normal webpage, click the extension, and summarize the current page content.

### 5. Summary Types

Supported summary types:

```text
DEFAULT
BULLET_POINTS
DETAILED
KEY_INSIGHTS
```

---

## Tech Stack

### Backend

* Java
* Spring Boot
* Maven
* PDFBox
* jsoup
* Groq API

### Testing

* Postman

### Browser Extension

* HTML
* CSS
* JavaScript
* Chrome Extension Manifest V3

---

## Project Structure

```text
summarizer/
│
├── src/
│   └── main/
│       ├── java/com/tharun/summarizer/
│       │   ├── config/
│       │   │   └── CorsConfig.java
│       │   ├── controller/
│       │   │   └── SummaryController.java
│       │   ├── dto/
│       │   │   ├── SummaryResponse.java
│       │   │   ├── SummaryType.java
│       │   │   ├── TextSummaryRequest.java
│       │   │   └── UrlSummaryRequest.java
│       │   ├── exception/
│       │   │   └── GlobalExceptionHandler.java
│       │   ├── service/
│       │   │   ├── ChunkService.java
│       │   │   ├── OpenAiService.java
│       │   │   ├── PdfTextService.java
│       │   │   ├── SummaryService.java
│       │   │   └── UrlContentService.java
│       │   └── SummarizerApplication.java
│       │
│       └── resources/
│           └── application.properties
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

## Environment Setup

This project uses the Groq API. The API key must not be hardcoded inside the project.

Set the API key as an environment variable.

### Windows CMD

```cmd
setx GROQ_API_KEY "your_groq_api_key_here"
```

After setting the key, close and reopen CMD or VS Code.

Check if the key is available:

```cmd
echo %GROQ_API_KEY%
```

---

## Application Properties

The `application.properties` file should look like this:

```properties
spring.application.name=summarizer
server.port=8080

groq.api.key=${GROQ_API_KEY}
groq.model=llama-3.3-70b-versatile

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

---

## How to Run the Backend

From the backend project root folder, run:

```bash
mvn clean spring-boot:run
```

The backend will start at:

```text
http://localhost:8080
```

---

## API Endpoints

### 1. Text Summarization API

```text
POST http://localhost:8080/api/summarize/text
```

#### Request Body

```json
{
  "text": "Artificial intelligence helps students summarize content, understand concepts, and revise faster for exams.",
  "summaryType": "DEFAULT"
}
```

#### Example Response

```json
{
  "summary": "Artificial intelligence helps students learn faster by summarizing content and explaining concepts clearly.",
  "keyPoints": [
    "AI helps students summarize long content.",
    "AI can support exam revision.",
    "AI makes concepts easier to understand."
  ],
  "notes": "AI can be used as a study assistant for summarizing notes, understanding topics, and preparing for exams."
}
```

---

### 2. PDF File Summarization API

```text
POST http://localhost:8080/api/summarize/file
```

#### Postman Setup

Go to:

```text
Body -> form-data
```

Add:

```text
Key: file
Type: File
Value: Upload your PDF file
```

Optional summary type:

```text
Key: summaryType
Type: Text
Value: DETAILED
```

Supported values:

```text
DEFAULT
BULLET_POINTS
DETAILED
KEY_INSIGHTS
```

---

### 3. URL Summarization API

```text
POST http://localhost:8080/api/summarize/url
```

#### Request Body

```json
{
  "url": "https://en.wikipedia.org/wiki/Artificial_intelligence",
  "summaryType": "KEY_INSIGHTS"
}
```

#### Notes

Some websites may block backend scraping or load content using JavaScript. In those cases, URL summarization may fail. The Chrome extension solves this better because it reads the already-loaded webpage content directly from the browser.

---

## How to Test Using Postman

### Test 1: Text API

1. Open Postman
2. Select `POST`
3. Enter:

```text
http://localhost:8080/api/summarize/text
```

4. Go to:

```text
Body -> raw -> JSON
```

5. Paste:

```json
{
  "text": "Artificial intelligence helps students summarize content, understand concepts, and revise faster for exams.",
  "summaryType": "DEFAULT"
}
```

6. Click `Send`

Expected result:

```text
200 OK
```

---

### Test 2: PDF API

1. Open Postman
2. Select `POST`
3. Enter:

```text
http://localhost:8080/api/summarize/file
```

4. Go to:

```text
Body -> form-data
```

5. Add:

```text
file -> File -> choose a PDF
summaryType -> Text -> DETAILED
```

6. Click `Send`

Expected result:

```text
200 OK
```

---

### Test 3: URL API

1. Open Postman
2. Select `POST`
3. Enter:

```text
http://localhost:8080/api/summarize/url
```

4. Go to:

```text
Body -> raw -> JSON
```

5. Paste:

```json
{
  "url": "https://en.wikipedia.org/wiki/Artificial_intelligence",
  "summaryType": "KEY_INSIGHTS"
}
```

6. Click `Send`

Expected result:

```text
200 OK
```

---

## Chrome Extension Setup

The Chrome extension is inside:

```text
summarizer-chrome-extension/
```

### Load Extension in Chrome

1. Open Chrome
2. Go to:

```text
chrome://extensions
```

3. Turn on `Developer mode`
4. Click `Load unpacked`
5. Select the `summarizer-chrome-extension` folder
6. Pin the extension
7. Open any normal webpage
8. Click the extension icon
9. Select summary type
10. Click `Summarize This Page`

### Extension API Used

The extension sends webpage text to:

```text
POST http://localhost:8080/api/summarize/text
```

---

## Error Handling

The backend handles:

* Empty text input
* Invalid PDF file
* Empty PDF file
* Large PDF upload
* Invalid URL
* Website content extraction failure
* AI API request failure

Example error response:

```json
{
  "error": "Input text cannot be empty."
}
```

---

## Important Notes

* Do not commit API keys.
* Do not hardcode Groq API keys in Java code or `application.properties`.
* Keep the API key in the `GROQ_API_KEY` environment variable.
* Some websites may block URL extraction.
* Chrome extension summarization is usually more reliable than backend URL scraping because it reads the loaded page content from the browser.

---

## Files to Push to GitLab

Push these files:

```text
src/
pom.xml
README.md
summarizer-chrome-extension/
.gitignore
```

Do not push these:

```text
target/
.env
*.log
API keys
.idea/
.vscode/
```

---

## Recommended `.gitignore`

```gitignore
# Maven build output
target/

# Environment files
.env
*.env

# Logs
*.log

# IDE files
.idea/
.vscode/

# OS files
.DS_Store
Thumbs.db

# API keys or secrets
secrets.properties
application-local.properties
```

---

## Git Commands

Check changed files:

```bash
git status
```

Add files:

```bash
git add .
```

Commit:

```bash
git commit -m "Add multi-input AI summarizer with Chrome extension"
```

Push:

```bash
git push
```

---

## Future Enhancements

Possible future improvements:

* Save summary history in a database
* Add user login
* Add Angular frontend
* Add copy summary button in Chrome extension
* Add download summary as PDF
* Add question-answering from uploaded PDF
* Add rate limiting for public API usage
* Add API documentation using Swagger

---

## Author

Tharun
