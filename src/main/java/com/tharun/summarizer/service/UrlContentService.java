package com.tharun.summarizer.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UrlContentService {

    public String extractTextFromUrl(String url) {
        validateUrl(url);

        try {
            Connection.Response response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/124.0 Safari/537.36")
                    .referrer("https://www.google.com")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .timeout(20000)
                    .followRedirects(true)
                    .ignoreHttpErrors(true)
                    .execute();

            if (response.statusCode() >= 400) {
                throw new IllegalArgumentException("Website returned error status: " + response.statusCode());
            }

            Document document = response.parse();

            document.select("script, style, nav, header, footer, aside, form, noscript").remove();

            String text = extractMainText(document);

            if (text.isBlank() || text.length() < 100) {
                throw new IllegalArgumentException("No readable content found from the URL.");
            }

            return text;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read content from the URL.");
        }
    }

    private String extractMainText(Document document) {
        Element mainContent = document.selectFirst(
                "article, main, [role=main], .article-content, .post-content, .entry-content, .content, .td-post-content"
        );

        if (mainContent != null) {
            String text = mainContent.text().trim();
            if (!text.isBlank()) {
                return text;
            }
        }

        Elements paragraphs = document.select("p");

        StringBuilder builder = new StringBuilder();

        for (Element paragraph : paragraphs) {
            String paragraphText = paragraph.text().trim();

            if (paragraphText.length() > 40) {
                builder.append(paragraphText).append("\n");
            }
        }

        String paragraphText = builder.toString().trim();

        if (!paragraphText.isBlank()) {
            return paragraphText;
        }

        String metaDescription = document.select("meta[name=description]").attr("content");

        if (metaDescription != null && !metaDescription.isBlank()) {
            return metaDescription.trim();
        }

        if (document.body() != null) {
            return document.body().text().trim();
        }

        return "";
    }

    private void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be empty.");
        }

        try {
            URI uri = URI.create(url);

            if (uri.getScheme() == null ||
                    (!uri.getScheme().equalsIgnoreCase("http") &&
                            !uri.getScheme().equalsIgnoreCase("https"))) {
                throw new IllegalArgumentException("Only http and https URLs are allowed.");
            }

            if (uri.getHost() == null || uri.getHost().isBlank()) {
                throw new IllegalArgumentException("Invalid URL.");
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL.");
        }
    }
}