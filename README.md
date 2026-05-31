# Summarizer Agent

## Project Overview

Summarizer Agent is a Spring Boot backend application that accepts a PDF file, extracts the text, processes it using an AI model, and returns a clear summary, key points, and short revision notes.

This project is useful for students who want to quickly understand lecture notes, assignment PDFs, study materials, or any text-heavy PDF document.

\---

## Main Features

* Upload PDF file using REST API
* Accepts `.pdf` files only
* Extracts text from multi-page PDFs using Apache PDFBox
* Splits large text into smaller chunks
* Sends extracted text to Gemini AI API
* Returns structured JSON response with:

  * Summary
  * Key points
  * Short notes
* Handles invalid files and empty PDFs
* Simple API testing using Postman

\---

## Tech Stack

|Tool / Technology|Purpose|
|-|-|
|Java|Backend programming language|
|Spring Boot|REST API development|
|Apache PDFBox|PDF text extraction|
|Gemini API|AI summarization|
|Maven|Dependency management|
|Postman|API testing|

\---

## Project Structure


src/main/java/com/tharun/summarizer
│
├── SummarizerApplication.java
├── controller
│   └── SummaryController.java
├── dto
│   └── SummaryResponse.java
├── service
│   ├── PdfTextService.java
│   ├── ChunkService.java
│   ├── OpenAiService.java
│   └── SummaryService.java
└── exception
    └── GlobalExceptionHandler.java
```

## Environment Variable Setup

### Windows CMD

```cmd
setx GEMINI\_API\_KEY "your\_gemini\_api\_key\_here"
```

After running the command, close and reopen your terminal or VS Code.

To check whether the key is saved:

```cmd
echo %GEMINI\_API\_KEY%
```



## How to Run the Project

Open terminal inside the project folder and run:

```bash
mvn spring-boot:run
```

If the application starts successfully, it will run locally on:

```text
http://localhost:8080
```

\---

\---

## Postman Testing Steps

1. Open Postman
2. Create a new request
3. Select method: `POST`
4. Enter URL:

```text
http://localhost:8080/api/summarize
```

5. Go to `Body`
6. Select `form-data`
7. Add this key:

|Key|Type|Value|
|-|-|-|
|file|File|Select your PDF|

8. Click `Send`
9. The response should return `summary`, `keyPoints`, and `notes`

\---

\---

## 

