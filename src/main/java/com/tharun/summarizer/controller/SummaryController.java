package com.tharun.summarizer.controller;

import com.tharun.summarizer.dto.SummaryResponse;
import com.tharun.summarizer.service.PdfTextService;
import com.tharun.summarizer.service.SummaryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api")
public class SummaryController {

    private final PdfTextService pdfTextService;
    private final SummaryService summaryService;

    public SummaryController(PdfTextService pdfTextService, SummaryService summaryService) {
        this.pdfTextService = pdfTextService;
        this.summaryService = summaryService;
    }

    @PostMapping(value = "/summarize", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SummaryResponse summarizePdf(@RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Please upload a PDF file.");
        }

        String filename = file.getOriginalFilename();

        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            throw new ResponseStatusException(BAD_REQUEST, "Only PDF files are allowed.");
        }

        String text = pdfTextService.extractText(file);

        if (text.isBlank()) {
            throw new ResponseStatusException(BAD_REQUEST, "No readable text found in the PDF.");
        }

        return summaryService.summarize(text);
    }
}