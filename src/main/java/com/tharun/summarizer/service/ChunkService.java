package com.tharun.summarizer.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkService {

    private static final int MAX_CHARS = 7000;

    public List<String> splitIntoChunks(String text) {
        List<String> chunks = new ArrayList<>();

        int start = 0;

        while (start < text.length()) {
            int end = Math.min(start + MAX_CHARS, text.length());

            if (end < text.length()) {
                int lastBreak = text.lastIndexOf("\n", end);
                if (lastBreak > start + 1000) {
                    end = lastBreak;
                }
            }

            String chunk = text.substring(start, end).trim();

            if (!chunk.isBlank()) {
                chunks.add(chunk);
            }

            start = end;
        }

        return chunks;
    }
}