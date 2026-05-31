package com.tharun.summarizer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tharun.summarizer.dto.SummaryResponse;
import com.tharun.summarizer.dto.SummaryType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryService {

    private static final int MAX_INPUT_CHARS = 15000;

    private final OpenAiService openAiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SummaryService(ChunkService chunkService, OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    public SummaryResponse summarize(String text) {
        return summarize(text, SummaryType.DEFAULT);
    }

    public SummaryResponse summarize(String text, SummaryType summaryType) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Input text cannot be empty.");
        }

        String cleanedText = text
                .replaceAll("\\s+", " ")
                .trim();

        if (cleanedText.length() > MAX_INPUT_CHARS) {
            cleanedText = cleanedText.substring(0, MAX_INPUT_CHARS);
        }

        String finalPrompt = buildFinalPrompt(cleanedText, summaryType);

        String aiJson = openAiService.ask(finalPrompt);

        return convertJsonToResponse(aiJson);
    }

    private String buildFinalPrompt(String content, SummaryType summaryType) {
        String instruction = switch (summaryType) {
            case BULLET_POINTS -> """
                    Focus mainly on bullet points.
                    The keyPoints array should contain 8 to 10 clear bullet points.
                    Keep the summary short.
                    """;

            case DETAILED -> """
                    Give a detailed but easy-to-understand summary.
                    The summary should explain the topic clearly.
                    The notes should be useful for revision.
                    """;

            case KEY_INSIGHTS -> """
                    Focus on the most important insights, lessons, conclusions, and takeaways.
                    Avoid unnecessary small details.
                    """;

            default -> """
                    Give a balanced student-friendly summary.
                    Include summary, key points, and short revision notes.
                    """;
        };

        return """
                You are an AI summarization assistant.

                %s

                Return ONLY valid JSON.
                Do not use markdown.
                Do not wrap the answer in triple backticks.

                JSON format:
                {
                  "summary": "short paragraph summary",
                  "keyPoints": ["point 1", "point 2", "point 3"],
                  "notes": "easy revision notes"
                }

                Content:
                %s
                """.formatted(instruction, content);
    }

    private SummaryResponse convertJsonToResponse(String aiJson) {
        try {
            String cleaned = aiJson.trim()
                    .replaceAll("^```json\\s*", "")
                    .replaceAll("^```\\s*", "")
                    .replaceAll("\\s*```$", "");

            return objectMapper.readValue(cleaned, SummaryResponse.class);

        } catch (Exception e) {
            return new SummaryResponse(
                    aiJson,
                    List.of("AI returned text, but it was not valid JSON."),
                    aiJson
            );
        }
    }
}