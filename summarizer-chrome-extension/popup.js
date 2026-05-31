const API_BASE_URL = "http://localhost:8080";

document.getElementById("summarizeBtn").addEventListener("click", summarizeCurrentPage);

async function summarizeCurrentPage() {
    const button = document.getElementById("summarizeBtn");
    const status = document.getElementById("status");
    const resultBox = document.getElementById("result");

    try {
        button.disabled = true;
        status.textContent = "Reading page content...";
        resultBox.classList.add("hidden");

        const [tab] = await chrome.tabs.query({
            active: true,
            currentWindow: true
        });

        if (!tab || !tab.id) {
            throw new Error("No active tab found.");
        }

        if (!tab.url.startsWith("http://") && !tab.url.startsWith("https://")) {
            throw new Error("This extension only works on normal webpages.");
        }

        const injectionResult = await chrome.scripting.executeScript({
            target: { tabId: tab.id },
            func: extractPageText
        });

        const pageText = injectionResult[0].result;

        if (!pageText || pageText.trim().length < 100) {
            throw new Error("Not enough readable text found on this page.");
        }

        const summaryType = document.getElementById("summaryType").value;

        status.textContent = "Sending content to AI summarizer...";

        const response = await fetch(`${API_BASE_URL}/api/summarize/text`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                text: pageText,
                summaryType: summaryType
            })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.error || "Failed to summarize page.");
        }

        showResult(data);
        status.textContent = "Done.";

    } catch (error) {
        status.textContent = error.message;
    } finally {
        button.disabled = false;
    }
}

function extractPageText() {
    const clonedBody = document.body.cloneNode(true);

    const unwantedTags = clonedBody.querySelectorAll(
        "script, style, nav, header, footer, aside, form, button, input, textarea, select, noscript"
    );

    unwantedTags.forEach(element => element.remove());

    const article =
        clonedBody.querySelector("article") ||
        clonedBody.querySelector("main") ||
        clonedBody.querySelector("[role='main']") ||
        clonedBody;

    return article.innerText
        .replace(/\s+/g, " ")
        .trim()
        .slice(0, 12000);
}

function showResult(data) {
    document.getElementById("summary").textContent = data.summary || "";

    const keyPointsList = document.getElementById("keyPoints");
    keyPointsList.innerHTML = "";

    if (data.keyPoints && Array.isArray(data.keyPoints)) {
        data.keyPoints.forEach(point => {
            const li = document.createElement("li");
            li.textContent = point;
            keyPointsList.appendChild(li);
        });
    }

    document.getElementById("notes").textContent = data.notes || "";

    document.getElementById("result").classList.remove("hidden");
}