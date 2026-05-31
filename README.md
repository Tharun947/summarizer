# \# AI Multi-Input Summarizer

# 

# A Spring Boot based AI summarization system that can summarize text, PDF files, webpage URLs, and browser page content using the Groq API. The project also includes a Chrome extension that reads the current webpage content and sends it to the backend summarization API.

# 

# \---

# 

# \## Project Overview

# 

# This project is designed to help users quickly generate summaries from different input sources. It supports:

# 

# \* Raw text summarization

# \* PDF file summarization

# \* URL / webpage summarization

# \* Chrome extension page summarization

# \* Multiple summary types such as default summary, bullet points, detailed summary, and key insights

# 

# The backend is built using Java and Spring Boot. PDF text is extracted using PDFBox. Webpage content is extracted using jsoup. AI processing is handled using the Groq API.

# 

# \---

# 

# \## Features

# 

# \### 1. Text Summarization

# 

# Users can send raw text to the backend and receive:

# 

# \* Summary

# \* Key points

# \* Short revision notes

# 

# \### 2. PDF Summarization

# 

# Users can upload a PDF file. The backend extracts text from the PDF and sends it to the AI model for summarization.

# 

# \### 3. URL Summarization

# 

# Users can send an article or webpage URL. The backend fetches the webpage, extracts readable content, and summarizes it.

# 

# \### 4. Chrome Extension

# 

# The Chrome extension allows users to open any normal webpage, click the extension, and summarize the current page content.

# 

# \### 5. Summary Types

# 

# Supported summary types:

# 

# ```text

# DEFAULT

# BULLET\_POINTS

# DETAILED

# KEY\_INSIGHTS

# ```

# 

# \---

# 

# \## Tech Stack

# 

# \### Backend

# 

# \* Java

# \* Spring Boot

# \* Maven

# \* PDFBox

# \* jsoup

# \* Groq API

# 

# \### Testing

# 

# \* Postman

# 

# \### Browser Extension

# 

# \* HTML

# \* CSS

# \* JavaScript

# \* Chrome Extension Manifest V3

# 

# \---

# 

# \## Project Structure

# 

# ```text

# summarizer/

# │

# ├── src/

# │   └── main/

# │       ├── java/com/tharun/summarizer/

# │       │   ├── config/

# │       │   │   └── CorsConfig.java

# │       │   ├── controller/

# │       │   │   └── SummaryController.java

# │       │   ├── dto/

# │       │   │   ├── SummaryResponse.java

# │       │   │   ├── SummaryType.java

# │       │   │   ├── TextSummaryRequest.java

# │       │   │   └── UrlSummaryRequest.java

# │       │   ├── exception/

# │       │   │   └── GlobalExceptionHandler.java

# │       │   ├── service/

# │       │   │   ├── ChunkService.java

# │       │   │   ├── OpenAiService.java

# │       │   │   ├── PdfTextService.java

# │       │   │   ├── SummaryService.java

# │       │   │   └── UrlContentService.java

# │       │   └── SummarizerApplication.java

# │       │

# │       └── resources/

# │           └── application.properties

# │

# ├── summarizer-chrome-extension/

# │   ├── manifest.json

# │   ├── popup.html

# │   ├── popup.css

# │   └── popup.js

# │

# ├── pom.xml

# └── README.md

# ```

# 

# \---

# 

# \## Environment Setup

# 

# This project uses the Groq API. The API key must not be hardcoded inside the project.

# 

# Set the API key as an environment variable.

# 

# \### Windows CMD

# 

# ```cmd

# setx GROQ\_API\_KEY "your\_groq\_api\_key\_here"

# ```

# 

# After setting the key, close and reopen CMD or VS Code.

# 

# Check if the key is available:

# 

# ```cmd

# echo %GROQ\_API\_KEY%

# ```

# 

# \---

# 

# \## Application Properties

# 

# The `application.properties` file should look like this:

# 

# ```properties

# spring.application.name=summarizer

# server.port=8080

# 

# groq.api.key=${GROQ\_API\_KEY}

# groq.model=llama-3.3-70b-versatile

# 

# spring.servlet.multipart.max-file-size=10MB

# spring.servlet.multipart.max-request-size=10MB

# ```

# 

# \---

# 

# \## How to Run the Backend

# 

# From the backend project root folder, run:

# 

# ```bash

# mvn clean spring-boot:run

# ```

# 

# The backend will start at:

# 

# ```text

# http://localhost:8080

# ```

# 

# \---

# 

# \## API Endpoints

# 

# \### 1. Text Summarization API

# 

# ```text

# POST http://localhost:8080/api/summarize/text

# ```

# 

# \#### Request Body

# 

# ```json

# {

# &#x20; "text": "Artificial intelligence helps students summarize content, understand concepts, and revise faster for exams.",

# &#x20; "summaryType": "DEFAULT"

# }

# ```

# 

# \#### Example Response

# 

# ```json

# {

# &#x20; "summary": "Artificial intelligence helps students learn faster by summarizing content and explaining concepts clearly.",

# &#x20; "keyPoints": \[

# &#x20;   "AI helps students summarize long content.",

# &#x20;   "AI can support exam revision.",

# &#x20;   "AI makes concepts easier to understand."

# &#x20; ],

# &#x20; "notes": "AI can be used as a study assistant for summarizing notes, understanding topics, and preparing for exams."

# }

# ```

# 

# \---

# 

# \### 2. PDF File Summarization API

# 

# ```text

# POST http://localhost:8080/api/summarize/file

# ```

# 

# \#### Postman Setup

# 

# Go to:

# 

# ```text

# Body -> form-data

# ```

# 

# Add:

# 

# ```text

# Key: file

# Type: File

# Value: Upload your PDF file

# ```

# 

# Optional summary type:

# 

# ```text

# Key: summaryType

# Type: Text

# Value: DETAILED

# ```

# 

# Supported values:

# 

# ```text

# DEFAULT

# BULLET\_POINTS

# DETAILED

# KEY\_INSIGHTS

# ```

# 

# \---

# 

# 

# 

# \---

# 

# \## Test Using Postman

# 

# \### Test 1: Text API

# 

# 1\. Open Postman

# 2\. Select `POST`

# 3\. Enter:

# 

# ```text

# http://localhost:8080/api/summarize/text

# ```

# 

# 4\. Go to:

# 

# ```text

# Body -> raw -> JSON

# ```

# 

# 5\. Paste:

# 

# ```json

# {

# &#x20; "text": "Artificial intelligence helps students summarize content, understand concepts, and revise faster for exams.",

# &#x20; "summaryType": "DEFAULT"

# }

# ```

# 

# 6\. Click `Send`

# 

# Expected result:

# 

# ```text

# 200 OK

# ```

# 

# \---

# 

# \### Test 2: PDF API

# 

# 1\. Open Postman

# 2\. Select `POST`

# 3\. Enter:

# 

# ```text

# http://localhost:8080/api/summarize/file

# ```

# 

# 4\. Go to:

# 

# ```text

# Body -> form-data

# ```

# 

# 5\. Add:

# 

# ```text

# file -> File -> choose a PDF

# summaryType -> Text -> DETAILED

# ```

# 

# 6\. Click `Send`

# 

# Expected result:

# 

# ```text

# 200 OK

# ```

# 

# \---

# 

# \### Test 3: URL API

# 

# 1\. Open Postman

# 2\. Select `POST`

# 3\. Enter:

# 

# ```text

# http://localhost:8080/api/summarize/url

# ```

# 

# 4\. Go to:

# 

# ```text

# Body -> raw -> JSON

# ```

# 

# 5\. Paste:

# 

# ```json

# {

# &#x20; "url": "https://en.wikipedia.org/wiki/Artificial\_intelligence",

# &#x20; "summaryType": "KEY\_INSIGHTS"

# }

# ```

# 

# 6\. Click `Send`

# 

# Expected result:

# 

# ```text

# 200 OK

# ```

# 

# \---

# 

# \## Chrome Extension Setup

# 

# The Chrome extension is inside:

# 

# ```text

# summarizer-chrome-extension/

# ```

# 

# \### Load Extension in Chrome

# 

# 1\. Open Chrome

# 2\. Go to:

# 

# ```text

# chrome://extensions

# ```

# 

# 3\. Turn on `Developer mode`

# 4\. Click `Load unpacked`

# 5\. Select the `summarizer-chrome-extension` folder

# 6\. Pin the extension

# 7\. Open any normal webpage

# 8\. Click the extension icon

# 9\. Select summary type

# 10\. Click `Summarize This Page`

# 

# \### Extension API Used

# 

# The extension sends webpage text to:

# 

# ```text

# POST http://localhost:8080/api/summarize/text

# ```

# 

# \---

# 

# \---

# 

# 

# 

# \---

# 

# ```

# 

# \---

# 



