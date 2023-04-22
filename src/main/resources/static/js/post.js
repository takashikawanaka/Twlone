document.addEventListener('DOMContentLoaded', function() { location.hash = ''; });

window.addEventListener('load', () => {
    utils.domUtils = new DomUtils();
    if (checkAuthenticated()) {
        utils.twFormUtils = new TwFormUtils();
    }
})

function baseURL() { return location.protocol + '//' + location.host; }

function checkAuthenticated() {
    if (document.querySelector('meta[name="_username"]')) return true;
    return false;
}

function clearForm() { utils.twFormUtils.cleanUp(); }

function removePreview() { utils.twFormUtils.clearPreview(); }

function postTw(form) {
    const formData = new FormData(form);
    if (280 < formData.get('content').length) {
        console.error('Error: The content exceeds the 280 character limit.');
        return;
    }
    const regexp = /(?<!#)#(([^\s!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^`{|}~]*[^\s\d!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^`{|}~][^\s!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^`{|}~]*)+)/g
    formData.append('hashTag', [...formData.get('content').matchAll(regexp)].map((item) => item[1]));
    formData.delete('mediaInput');
    fetch(baseURL() + '/user/tw', {
        method: "POST",
        body: formData
    }).then((res) => {
        if (!res.ok) {
            console.error('Error: The post has failed.');
            return;
        }
        closeWindow();
        utils.twFormUtils.cleanUp();
    });
}
