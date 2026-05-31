package com.tharun.summarizer.dto;

public record UrlSummaryRequest(
        String url,
        String summaryType
) {
}