# PDF / Notes Summarizer Agent

## Project Overview

PDF / Notes Summarizer Agent is a Spring Boot backend application that accepts a PDF file, extracts the text, processes it using an AI model, and returns a clear summary, key points, and short revision notes.

This project is useful for students who want to quickly understand lecture notes, assignment PDFs, study materials, or any text-heavy PDF document.

---

## Main Features

- Upload PDF file using REST API
- Accepts `.pdf` files only
- Extracts text from multi-page PDFs using Apache PDFBox
- Splits large text into smaller chunks
- Sends extracted text to Gemini AI API
- Returns structured JSON response with:
  - Summary
  - Key points
  - Short notes
- Handles invalid files and empty PDFs
- Simple API testing using Postman

---

## Tech Stack

| Tool / Technology | Purpose |
|---|---|
| Java | Backend programming language |
| Spring Boot | REST API development |
| Apache PDFBox | PDF text extraction |
| Gemini API | AI summarization |
| Maven | Dependency management |
| Postman | API testing |

---

## Project Structure

```text
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

---


## Environment Variable Setup


### Windows CMD

```cmd
setx GEMINI_API_KEY "your_gemini_api_key_here"
```

After running the command, close and reopen your terminal or VS Code.

To check whether the key is saved:

```cmd
echo %GEMINI_API_KEY%
```

### Windows PowerShell

```powershell
setx GEMINI_API_KEY "your_gemini_api_key_here"
```

To check:

```powershell
echo $env:GEMINI_API_KEY
```

---




