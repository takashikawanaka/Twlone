document.addEventListener('DOMContentLoaded', function() { location.hash = ''; });

window.onload = function() {
    utils.domUtils = new DomUtils();
    if (checkAuthenticated()) {
        utils.twFormUtils = new TwFormUtils();
    }
};

function baseURL() { return location.protocol + '//' + location.host; }

function checkAuthenticated() {
    if (document.querySelector('meta[name="_username"]')) return true;
    return false;
}

function clearForm() { utils.twFormUtils.cleanUp(); }

function removePreview() { utils.twFormUtils.clearPreview(); }

function postTw(form) {
    if (280 < document.getElementById('content').value.length) {
        console.error('Error: The textarea exceeds the 280 character limit.');
        return;
    }
    const formData = new FormData(form);
    const regexp = /(?<!#)#(([^\s!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^_`{|}~]*[^\s\d!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^_`{|}~][^\s!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^_`{|}~]*)+)/g
    formData.append('hashTag', [...formData.get('content').matchAll(regexp)].map((item) => item[1]));
    formData.delete('mediaInput');
    fetch(baseURL() + '/user/tw', {
        method: "POST",
        body: formData
    }).then((res) => {
        if (res.ok) {
            closeWindow();
            utils.twFormUtils.cleanUp();
        }
        else { console.error('Error: The post has failed.'); }
    });
}
