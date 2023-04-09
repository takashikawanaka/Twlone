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
        console.log("ERROR");
        return
    }

    let formData = new FormData(form);
    formData.delete('mediaInput');
    fetch(baseURL() + '/user/tw', {
        method: "POST",
        body: formData
    }).then((res) => {
        if (res.ok) {
            closeWindow();
            utils.twFormUtils.cleanUp();
        }
        else { console.log("ERROR"); }
    });
}
