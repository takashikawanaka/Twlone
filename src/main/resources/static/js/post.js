function baseURL() { return location.protocol + '//' + location.host; }

function checkAuthenticated() {
    if (document.querySelector('meta[name="_username"]')) return true;
    return false;
}

function closeWindow() {
    const scroll = window.scrollY;
    location.hash = '';
    window.scrollTo(0, scroll);
}

function clearMediaImport() {
    const media = document.getElementById('media');
    media.innerHTML = '';
}

function deletePreview(preview) { preview.remove(); }

function clearForm() {
    document.getElementById('tw_form').reset();
    document.getElementById('retw_id').value = '';
    document.getElementById('replytw_id').value = '';

    clearWordCounter();
    clearMediaImport();
    removeReplyTw();
    removeReTw();
}

function moveToTwPage(node) {
    window.location.href = `/user/${node.dataset.userid}/status/${node.dataset.twid}`;
}

function clearWordCounter() {
    const area = document.getElementById('content');
    const progress = document.getElementById('progress');
    const value = document.getElementById('value');
    const color = (percent) => {
        if (percent < 0.5) return 'rgb(14, 165, 233)' //bg-sky-500
        else if (percent < 1) return 'rgb(245, 158, 11)' //bg-amber-500
        else return 'rgb(244, 63, 94)' //bg-rose-500
    };
    progress.style.background = `conic-gradient(${color(0)} 0deg, transparent 0deg)`;
    value.textContent = 280;
    area.style.height = '160px';
}

// Tw Form
function initTwForm() {
    //Add Word counter
    const area = document.getElementById('content');
    const progress = document.getElementById('progress');
    const value = document.getElementById('value');
    const color = (percent) => {
        if (percent < 0.5) return 'rgb(14, 165, 233)' //bg-sky-500
        else if (percent < 1) return 'rgb(245, 158, 11)' //bg-amber-500
        else return 'rgb(244, 63, 94)' //bg-rose-500
    };
    clearWordCounter();

    area.addEventListener('input', () => {
        const per = area.value.length / 280;
        area.style.height = `${area.scrollHeight}px`;
        progress.style.background = `conic-gradient(${color(per)} ${360 * per}deg, transparent 0deg)`;
        value.textContent = 280 - area.value.length;
    });
    // Add Media Input
    const fileInput = document.getElementById('fileInput');
    const media = document.getElementById('preview');
    const parser = new DOMParser();
    fileInput.addEventListener('change', e => {
        const { files } = e.target;
        if (4 < files.length + media.childElementCount) {
            console.log("Too many files selected. Please select 4 or fewer files");
            return;
        }
        for (let i = 0; i < files.length; i++) {
            const reader = new FileReader();
            const dt = new DataTransfer();
            dt.items.add(files[i]);
            //Fix Size Responsive
            const div = parser.parseFromString(`<div class="h-24 relative overflow-hidden">`, 'text/html').body.firstChild;
            const img = parser.parseFromString(`<img class="object-contain h-24">`, 'text/html').body.firstChild;
            const button = parser.parseFromString(`<button type="button" onclick="deletePreview(this.parentElement)" class="absolute top-0 right-0"> <span class="material-icons-round" style="font-size: 36px;">cancel</span></button>`, 'text/html').body.firstChild;
            const dom = parser.parseFromString(`<input type="file" id="file${i}" name="file" style="display: none;">`, 'text/html').body.firstChild;
            dom.files = dt.files;
            div.appendChild(img);
            div.appendChild(button);
            div.appendChild(dom);
            media.appendChild(div);

            reader.addEventListener('load', () => {
                img.setAttribute('src', reader.result);
            });
            reader.readAsDataURL(files[i]);
        }
        fileInput.value = '';
    }, false)
}

//Open Tw Form
function openTw() {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }
    initTwForm();
    location.hash = 'tw';
}

// ReTw
function openReTw(node) {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }
    const twid = node.dataset.id;
    removeReTw();

    document.getElementById('replytw').classList.add('hidden');
    document.getElementById('retw').classList.remove('hidden');
    document.getElementById('retw_id').value = twid;
    document.getElementById('retw_icon').setAttribute('src', document.getElementById('icon_' + twid).getAttribute('src'));
    document.getElementById('retw_name').textContent = document.getElementById('name_' + twid).textContent;
    document.getElementById('retw_userid').textContent = document.getElementById('userid_' + twid).textContent;
    document.getElementById('retw_content').textContent = document.getElementById('content_' + twid).textContent;
    const source_medias = document.getElementById('medias_' + twid);
    if (source_medias != null) {
        const medias = (new DOMParser).parseFromString(`<div id="retw_medias" class="grid gap-0.5 h-auto mt-1 rounded-lg overflow-hidden" style="grid-template-columns: repeat(${source_medias.childElementCount == 1 ? 1 : 2}, ${source_medias.childElementCount == 1 ? '100%' : '50%'});grid-template-rows: repeat(${source_medias.childElementCount < 3 ? 1 : 2}, ${source_medias.childElementCount < 3 ? '100%' : '50%'}); aspect-ratio: 3/2;"></div>`, 'text/html').body.firstChild;
        medias.innerHTML = source_medias.innerHTML;
        document.getElementById('retw').appendChild(medias);
    }
    const retw = document.getElementById('reTw_' + twid);
    if (retw != null) {
        document.getElementById('retw_retw').classList.remove('hidden');
        document.getElementById('retw_retw_icon').setAttribute('src', document.getElementById('reTw_icon_' + twid).getAttribute('src'));
        document.getElementById('retw_retw_name').textContent = document.getElementById('reTw_name_' + twid).textContent;
        document.getElementById('retw_retw_userid').textContent = document.getElementById('reTw_userid_' + twid).textContent;
        document.getElementById('retw_retw_content').textContent = document.getElementById('reTw_content_' + twid).textContent;
        const retw_source_medias = document.getElementById('reTw_medias_' + twid);
        if (retw_source_medias != null) {
            const retw_medias = (new DOMParser).parseFromString(`<div id="retw_retw_medias" class="grid gap-0.5 h-auto mt-1 rounded-lg overflow-hidden" style="grid-template-columns: repeat(${retw_source_medias.childElementCount == 1 ? 1 : 2}, ${retw_source_medias.childElementCount == 1 ? '100%' : '50%'});grid-template-rows: repeat(${retw_source_medias.childElementCount < 3 ? 1 : 2}, ${retw_source_medias.childElementCount < 3 ? '100%' : '50%'}); aspect-ratio: 3/2;"></div>`, 'text/html').body.firstChild;
            retw_medias.innerHTML = retw_source_medias.innerHTML;
            document.getElementById('retw_retw').appendChild(retw_medias);
        }
    }

    initTwForm();
    location.hash = 'tw';
}

function removeReTw() {
    document.getElementById('content').value = '';
    document.getElementById('retw').classList.add('hidden');
    document.getElementById('retw_content').textContent = '';
    const medias = document.getElementById('retw_medias');
    if (medias != null) {
        medias.remove();
    }
    const retw = document.getElementById('retw_retw');
    if (!retw.classList.contains('hidden')) {
        retw.classList.add('hidden');
        document.getElementById('retw_retw_content').textContent = '';
        const retw_medias = document.getElementById('retw_retw_medias');
        if (retw_medias != null) {
            retw_medias.remove();
        }
    }
}

function postTw(form) {
    if (280 < document.getElementById('content').value.length) {
        console.log("ERROR");
        return
    }

    let formData = new FormData(form);
    formData.delete('fileInput');
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
            clearMediaImport();
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

    initTwForm();
    location.hash = 'tw';
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
}
