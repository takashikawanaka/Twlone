window.addEventListener('load', () => {
    utils.homeTwFormUtils = new HomeTwFormUtil();
})

class HomeTwFormUtil {
    constructor() {
        this.form = utils.domUtils.getElementById('home_tw_form');
        this.wordCounter = new WordCounter('home_content', 'home_progress', 'home_value', 32);
        this.mediaPreview = new MediaPreview('home_mediaInput', 'home_preview');
    }

    deletePreview(preview) { this.mediaPreview.deletePreview(preview); }

    refreshForm() {
        console.log("refresh")
        this.form.reset();
        this.mediaPreview.clearPreview();
        this.wordCounter.clearCounter();
    }

    postTw(form) {
        const formData = new FormData(form);
        if (280 < formData.get('content').length) {
            console.error('Error: The content exceeds the 280 character limit.');
            return;
        }
        const regexp = /(?<!#)#(([^\s!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^_`{|}~]*[^\s\d!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^`{|}~][^\s!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^_`{|}~]*)+)/g
        const hashtagList = [...formData.get('content').matchAll(regexp)].map((item) => item[1])
        if (0 < hashtagList.length) formData.append('hashtag', hashtagList);
        formData.delete('mediaInput');
        fetch(baseURL() + '/user/tw', {
            method: "POST",
            body: formData
        }).then((res) => {
            if (!res.ok) {
                console.error('Error: The post has failed.');
                return;
            }
            closeWindow(); //ReWrite
            this.refreshForm();
        });
    }
}

function clearHomeTwForm() { utils.homeTwFormUtils.refreshForm(); }

function deleteHomeMedia(preview) { utils.homeTwFormUtils.deletePreview(preview); }

function postHomeTw(form) { utils.homeTwFormUtils.postTw(form); }