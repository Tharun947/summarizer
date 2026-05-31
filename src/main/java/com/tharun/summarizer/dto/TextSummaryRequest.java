package com.tharun.summarizer.dto;

public record TextSummaryRequest(
        String text,
        String summaryType
) {
}