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

## Requirements

Before running the project, make sure these are installed:

- Java 17 or above
- Maven
- Postman
- Gemini API key
- VS Code / IntelliJ IDEA / Eclipse

---

## Environment Variable Setup

Do not hardcode the Gemini API key directly inside the code.

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

## Application Configuration

File location:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
spring.application.name=summarizer
server.port=8081

gemini.api.key=${GEMINI_API_KEY}
gemini.model=gemini-1.5-flash

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

If your project uses port `8080`, then the API URL will use `8080` instead of `8081`.

---

## How to Run the Project

Open terminal inside the project folder and run:

```bash
mvn spring-boot:run
```

If the application starts successfully, it will run locally on:

```text
http://localhost:8081
```

---

## API Endpoint

### Summarize PDF

```text
POST http://localhost:8081/api/summarize
```

If your `server.port` is `8080`, use:

```text
POST http://localhost:8080/api/summarize
```

---

## Request Format

Use `multipart/form-data`.

| Key | Type | Value |
|---|---|---|
| file | File | Upload a PDF file |

---

## Example Response

```json
{
  "summary": "This PDF explains the main ideas in a short paragraph.",
  "keyPoints": [
    "First important point from the PDF.",
    "Second important point from the PDF.",
    "Third important point from the PDF."
  ],
  "notes": "Short revision notes generated from the uploaded PDF."
}
```

---

## Postman Testing Steps

1. Open Postman
2. Create a new request
3. Select method: `POST`
4. Enter URL:

```text
http://localhost:8081/api/summarize
```

5. Go to `Body`
6. Select `form-data`
7. Add this key:

| Key | Type | Value |
|---|---|---|
| file | File | Select your PDF |

8. Click `Send`
9. The response should return `summary`, `keyPoints`, and `notes`

---

## Postman Collection

You can manually create this collection in Postman:

```json
{
  "info": {
    "name": "PDF Notes Summarizer Agent",
    "description": "Postman collection for testing the Spring Boot PDF summarizer API.",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Summarize PDF",
      "request": {
        "method": "POST",
        "header": [],
        "body": {
          "mode": "formdata",
          "formdata": [
            {
              "key": "file",
              "type": "file",
              "src": []
            }
          ]
        },
        "url": {
          "raw": "http://localhost:8081/api/summarize",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "8081",
          "path": [
            "api",
            "summarize"
          ]
        },
        "description": "Upload a PDF file and receive summary, key points, and short notes."
      },
      "response": []
    }
  ]
}
```

---

## Error Handling

The application handles common errors such as:

| Error Case | Expected Behaviour |
|---|---|
| No file uploaded | Returns error message |
| Non-PDF file uploaded | Returns error message |
| Empty PDF | Returns error message |
| Encrypted PDF | Returns error message |
| AI API error | Returns error message |

---

## Future Enhancements

Possible future improvements:

- Add text input summarization
- Ask questions from uploaded PDF
- Save previous summaries in a database
- Add user login
- Add Angular frontend
- Download summary as PDF or Word document
- Support multiple file uploads

---

## Security Notes

- Do not upload API keys to GitHub
- Do not paste API keys in screenshots
- Store API keys using environment variables
- Revoke and recreate the API key if it is accidentally exposed

---

## Success Criteria

The project is successful when:

- User can upload a PDF file
- Backend extracts text correctly
- AI generates meaningful summary
- API returns valid JSON response
- Postman request works without errors

---

## Author

Developed by Tharun as a Java Spring Boot AI Agent project.
