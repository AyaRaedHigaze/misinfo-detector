const API_BASE_URL = ""; // فارغ يعني نفس الباك اند

document.getElementById("analyzeBtn").addEventListener("click", async () => {
    const text = document.getElementById("content").value.trim();
    const errorEl = document.getElementById("error");
    const resultEl = document.getElementById("result");

    errorEl.textContent = "";
    resultEl.textContent = "";

    if (!text) {
        errorEl.textContent = "Please enter some text.";
        return;
    }

    resultEl.textContent = "Analyzing...";

    try {
        const res = await fetch(`${API_BASE_URL}/api/analyze`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ text })
        });

        if (!res.ok) {
            const body = await res.text();
            throw new Error(`${res.status} ${res.statusText}: ${body}`);
        }

        const data = await res.json();

        let html = "";
        if (data.score !== undefined) html += `<p><strong>Risk score:</strong> ${data.score}</p>`;
        if (data.flags?.length) {
            html += `<p><strong>Flags:</strong></p><ul>${data.flags.map(f => `<li>${f}</li>`).join("")}</ul>`;
        }
        if (data.explanation) html += `<p><strong>Explanation:</strong> ${data.explanation}</p>`;
        // عرض الـ flags إذا كانت موجودة
        if (Array.isArray(data.flags) && data.flags.length > 0) {
            html += `<div><strong>Flags:</strong><ul>`;
            data.flags.forEach(flag => {
                html += `<li>${typeof flag === "string" ? flag : JSON.stringify(flag)}</li>`;
            });
            html += `</ul></div>`;
        } else {
            html += `<p>No flags detected.</p>`;
        }

        if (data.sources?.length) {
            html += `<p><strong>Sources:</strong></p><ul>${data.sources.map(s => `<li><a href="${s}" target="_blank">${s}</a></li>`).join("")}</ul>`;
        }

        html += `<details><summary>Raw JSON</summary><pre>${JSON.stringify(data, null, 2)}</pre></details>`;
        resultEl.innerHTML = html;

    } catch (err) {
        errorEl.textContent = err.message || "Request failed";
        resultEl.textContent = "";
    }
});

document.getElementById("clearBtn").addEventListener("click", () => {
    document.getElementById("content").value = "";
    document.getElementById("result").textContent = "No analysis yet. Submit content to see flags, sources, and reasoning.";
    document.getElementById("error").textContent = "";
});