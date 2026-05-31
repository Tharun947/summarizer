package com.tharun.summarizer.dto;

import java.util.List;

public record SummaryResponse(
        String summary,
        List<String> keyPoints,
        String notes
) {
}