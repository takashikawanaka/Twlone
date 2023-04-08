document.addEventListener('DOMContentLoaded', function() {
    location.hash = '';
});

window.onload = function() {
    utils.domUtils = new DomUtils();
    if (checkAuthenticated()) {
        utils.wordCounter = new WordCounter();
        utils.mediaPreview = new MediaPreview();
        utils.twFormUtils = new TwFormUtils();
    }
};

function baseURL() { return location.protocol + '//' + location.host; }

function checkAuthenticated() {
    if (document.querySelector('meta[name="_username"]')) return true;
    return false;
}


//Fix
function clearForm() {
    document.getElementById('tw_form').reset();
    document.getElementById('retw_id').value = '';
    document.getElementById('replytw_id').value = '';

    clearWordCounter();
    removePreview();
    removeReplyTw();
    removeReTw();
}

//Add class
function clearWordCounter() {
    utils.wordCounter.clearCounter();
}

function refreshWordCounter() {
    utils.wordCounter.refreshCounter();
}

function removePreview() {
    utils.mediaPreview.clearPreview();
}

// ReTw
function openReTw(node) {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }
    const twid = node.dataset.id;
    removeReTw();
    document.getElementById('retw_id').value = '';
    document.getElementById('replytw_id').value = '';

    //Add Class
    utils.twFormUtils.showReTw(twid);
    openTw();
    location.hash = 'tw';
}

function removeReTw() {
    return;
}

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
            form.reset();
            document.getElementById('retw_id').value = '';
            document.getElementById('replytw_id').value = '';

            clearWordCounter();
            removePreview();
            removeReplyTw();
            removeReTw();
        }
        else { console.log("ERROR"); }
    });
}

// Reply Tw
function openReplyTw(node) {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }
    const twid = node.dataset.id;
    removeReplyTw();
    document.getElementById('retw_id').value = '';
    document.getElementById('replytw_id').value = '';

    document.getElementById('retw').classList.add('hidden');
    document.getElementById('replytw').classList.remove('hidden');
    document.getElementById('content').value = document.getElementById('userid_' + twid).textContent + ' ';
    document.getElementById('replytw_id').value = twid;
    document.getElementById('replytw_icon').setAttribute('src', document.getElementById('icon_' + twid).getAttribute('src'));
    document.getElementById('replytw_name').textContent = document.getElementById('name_' + twid).textContent;
    document.getElementById('replytw_userid').textContent = document.getElementById('userid_' + twid).textContent;
    document.getElementById('replytw_content').textContent = document.getElementById('content_' + twid).textContent;
    const source_medias = document.getElementById('medias_' + twid);
    if (source_medias != null) {
        const medias = (new DOMParser).parseFromString(`<div id="replytw_medias" class="grid gap-0.5 h-auto mt-0.5 rounded-lg overflow-hidden" style="grid-template-columns: repeat(${source_medias.childElementCount == 1 ? 1 : 2},  ${source_medias.childElementCount == 1 ? '100%' : '50%'});grid-template-rows: repeat(${source_medias.childElementCount < 3 ? 1 : 2},  ${source_medias.childElementCount < 3 ? '100%' : '50%'}); aspect-ratio: 3/2;"></div>`, 'text/html').body.firstChild;
        medias.innerHTML = source_medias.innerHTML;
        document.getElementById('replytw_content').after(medias);
    }
    const retw = document.getElementById('reTw_' + twid);
    if (retw != null) {
        document.getElementById('replytw_retw').classList.remove('hidden');
        document.getElementById('replytw_retw_icon').setAttribute('src', document.getElementById('reTw_icon_' + twid).getAttribute('src'));
        document.getElementById('replytw_retw_name').textContent = document.getElementById('reTw_name_' + twid).textContent;
        document.getElementById('replytw_retw_userid').textContent = document.getElementById('reTw_userid_' + twid).textContent;
        document.getElementById('replytw_retw_content').textContent = document.getElementById('reTw_content_' + twid).textContent;
        const retw_source_medias = document.getElementById('reTw_medias_' + twid);
        if (retw_source_medias != null) {
            const retw_medias = (new DOMParser).parseFromString(`<div id="replytw_retw_medias" class="grid gap-0.5 h-auto mt-1 rounded-lg overflow-hidden" style="grid-template-columns: repeat(${retw_source_medias.childElementCount == 1 ? 1 : 2}, ${retw_source_medias.childElementCount == 1 ? '100%' : '50%'});grid-template-rows: repeat(${retw_source_medias.childElementCount < 3 ? 1 : 2}, ${retw_source_medias.childElementCount < 3 ? '100%' : '50%'}); aspect-ratio: 3/2;"></div>`, 'text/html').body.firstChild;
            retw_medias.innerHTML = retw_source_medias.innerHTML;
            document.getElementById('replytw_retw').appendChild(retw_medias);
        }
    }

    location.hash = 'tw';
    clearWordCounter();
    refreshWordCounter();
}

function removeReplyTw() {
    document.getElementById('content').value = '';
    document.getElementById('replytw').classList.add('hidden');
    document.getElementById('replytw_content').textContent = '';
    const medias = document.getElementById('replytw_medias');
    if (medias != null) {
        medias.remove();
    }
    const replytw = document.getElementById('replytw_retw');
    if (!replytw.classList.contains('hidden')) {
        replytw.classList.add('hidden');
        document.getElementById('replytw_retw_content').textContent = '';
        const replytw_medias = document.getElementById('replytw_retw_medias');
        if (replytw_medias != null) {
            replytw_medias.remove();
        }
    }
    clearWordCounter();
}
