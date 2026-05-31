package com.tharun.summarizer.controller;

import com.tharun.summarizer.dto.SummaryResponse;
import com.tharun.summarizer.dto.SummaryType;
import com.tharun.summarizer.dto.TextSummaryRequest;
import com.tharun.summarizer.dto.UrlSummaryRequest;
import com.tharun.summarizer.service.PdfTextService;
import com.tharun.summarizer.service.SummaryService;
import com.tharun.summarizer.service.UrlContentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/summarize")
public class SummaryController {

    private final PdfTextService pdfTextService;
    private final SummaryService summaryService;
    private final UrlContentService urlContentService;

    public SummaryController(
            PdfTextService pdfTextService,
            SummaryService summaryService,
            UrlContentService urlContentService
    ) {
        this.pdfTextService = pdfTextService;
        this.summaryService = summaryService;
        this.urlContentService = urlContentService;
    }

    @PostMapping("/text")
    public SummaryResponse summarizeText(@RequestBody TextSummaryRequest request) {
        SummaryType summaryType = SummaryType.from(request.summaryType());
        return summaryService.summarize(request.text(), summaryType);
    }

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SummaryResponse summarizeFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "summaryType", required = false) String summaryTypeValue
    ) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Please upload a PDF file.");
        }

        String filename = file.getOriginalFilename();

        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed.");
        }

        String text = pdfTextService.extractText(file);

        SummaryType summaryType = SummaryType.from(summaryTypeValue);

        return summaryService.summarize(text, summaryType);
    }

    @PostMapping("/url")
    public SummaryResponse summarizeUrl(@RequestBody UrlSummaryRequest request) {
        String text = urlContentService.extractTextFromUrl(request.url());

        SummaryType summaryType = SummaryType.from(request.summaryType());

        return summaryService.summarize(text, summaryType);
    }

    // Optional: keeps your old Postman URL working
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SummaryResponse summarizeOldPdfEndpoint(@RequestParam("file") MultipartFile file) throws IOException {
        return summarizeFile(file, null);
    }
}