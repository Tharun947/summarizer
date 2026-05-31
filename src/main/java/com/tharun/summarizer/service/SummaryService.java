package com.tharun.summarizer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tharun.summarizer.dto.SummaryResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SummaryService {

    private final ChunkService chunkService;
    private final OpenAiService openAiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SummaryService(ChunkService chunkService, OpenAiService openAiService) {
        this.chunkService = chunkService;
        this.openAiService = openAiService;
    }

    public SummaryResponse summarize(String text) {
        List<String> chunks = chunkService.splitIntoChunks(text);

        String materialForFinalSummary;

        if (chunks.size() == 1) {
            materialForFinalSummary = chunks.get(0);
        } else {
            List<String> chunkSummaries = new ArrayList<>();

            for (int i = 0; i < chunks.size(); i++) {
                String prompt = """
                        Summarize this PDF chunk clearly for a student.
                        Keep important definitions, facts, and examples.
                        Maximum 180 words.

                        Chunk number: %d

                        Text:
                        %s
                        """.formatted(i + 1, chunks.get(i));

                chunkSummaries.add(openAiService.ask(prompt));
            }

            materialForFinalSummary = String.join("\n\n", chunkSummaries);
        }

        String finalPrompt = """
                You are a study notes summarizer for students.

                Read the content below and return ONLY valid JSON.
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
                """.formatted(materialForFinalSummary);

        String aiJson = openAiService.ask(finalPrompt);

        return convertJsonToResponse(aiJson);
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