document.getElementById('moodForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const mood = document.getElementById('moodInput').value;
    if (!mood.trim()) return;
    
    showLoading();
    
    fetch(`/generateScript?mood=${encodeURIComponent(mood)}`)
        .then(response => response.text())
        .then(quote => {
            showResult(quote, mood);
        })
        .catch(error => {
            showResult('"The best time to start was yesterday. The next best time is now."', mood);
        });
});

function showLoading() {
    document.getElementById('loading').classList.remove('hidden');
    document.getElementById('result').classList.add('hidden');
}

function showResult(quote, mood) {
    document.getElementById('loading').classList.add('hidden');
    
    document.getElementById('result').innerHTML = `
        <div class="script">${quote}</div>
        <div style="margin-top: 25px;">
            <button class="share-btn" 
                    data-quote="${quote.replace(/"/g, '&quot;')}"
                    data-mood="${mood.replace(/"/g, '&quot;')}">
                📱 Share This Quote
            </button>
        </div>
    `;
    document.getElementById('result').classList.remove('hidden');
    
    // Add click listener to new button
    document.querySelector('.share-btn').addEventListener('click', function() {
        const quote = this.getAttribute('data-quote');
        const mood = this.getAttribute('data-mood');
        shareQuote(quote, mood);
    });
}

function shareQuote(quote, mood) {
    const shareText = `"${quote}" - AI Quote for "${mood}" quotegen.ai`;
    
    if (navigator.share) {
        navigator.share({
            //title: 'AI Quote Generator',
            text: shareText
            //url: window.location.origin
        }).catch(() => copyToClipboard(shareText));
        return;
    }
    
    copyToClipboard(shareText);
}

async function copyToClipboard(text) {
    try {
        await navigator.clipboard.writeText(text);
        alert('✅ Copied!\n\nPaste in WhatsApp/Twitter:\n' + text);
    } catch (err) {
        alert('Copy manually:\n\n' + text);
    }
}
