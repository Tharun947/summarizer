package com.tharun.summarizer.dto;

public enum SummaryType {
    DEFAULT,
    BULLET_POINTS,
    DETAILED,
    KEY_INSIGHTS;

    public static SummaryType from(String value) {
        if (value == null || value.isBlank()) {
            return DEFAULT;
        }

        return switch (value.trim().toUpperCase()) {
            case "BULLET_POINTS", "BULLET", "BULLETS" -> BULLET_POINTS;
            case "DETAILED", "DETAIL" -> DETAILED;
            case "KEY_INSIGHTS", "INSIGHTS" -> KEY_INSIGHTS;
            default -> DEFAULT;
        };
    }
}