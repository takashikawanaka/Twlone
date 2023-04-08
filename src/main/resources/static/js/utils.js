class DomUtils {
    constructor() {
        this.parser = new DOMParser();
    }

    getElementById(id) { return document.getElementById(id); }

    getElementListByIdList(...idList) { return idList.map((id) => this.getElementById(id)); }

    copyTextContent(source, target) { source.textContent = this.getElementById(target).textContent; }

    copySrc(source, target) { source.src = this.getElementById(target).src; }

    parseFromString(str) {
        return this.parser.parseFromString(str, 'text/html').body.firstChild
    }
}

class WordCounter {
    constructor() {
        [this.area, this.progress, this.value] = utils.domUtils.getElementListByIdList('content', 'progress', 'value');
        this.color = (percent) => {
            if (percent < 0.5) return 'rgb(14, 165, 233)' //bg-sky-500
            else if (percent < 1) return 'rgb(245, 158, 11)' //bg-amber-500
            else return 'rgb(244, 63, 94)' //bg-rose-500
        };

        this.clearCounter();
        this.area.addEventListener('input', () => this.refreshCounter());
    }

    setCounter(height, per, lenght) {
        this.area.style.height = `${height}px`;
        this.progress.style.background = `conic-gradient(${this.color(per)} ${360 * per}deg, transparent 0deg)`;
        this.value.textContent = 280 - lenght;
    }

    refreshCounter() { this.setCounter(this.area.scrollHeight, this.area.value.length / 280, this.area.value.length); }

    clearCounter() {
        this.setCounter(160, 0, 0);
    }
}

class MediaPreview {
    constructor() {
        [this.mediaInput, this.preview] = utils.domUtils.getElementListByIdList('mediaInput', 'preview');
        this.parser = new DomUtils();
        this.mediaInput.addEventListener('change', e => {
            const { files } = e.target;
            if (4 < files.length + this.preview.childElementCount) {
                console.log("Too many files selected. Please select 4 or fewer files");
                return;
            }
            for (let i = 0; i < files.length; i++) {
                const reader = new FileReader();
                const dt = new DataTransfer();
                dt.items.add(files[i]);
                //Rewrite
                //Fix Size Responsive
                const div = this.parser.parseFromString(`<div class="h-24 relative overflow-hidden">`);
                const img = this.parser.parseFromString(`<img class="object-contain h-24">`);
                const button = this.parser.parseFromString(`<button type="button" onclick="utils.mediaPreview.deleteMedia(this.parentElement)" class="absolute top-0 right-0"> <span class="material-icons-round" style="font-size: 36px;">cancel</span></button>`);
                const dom = this.parser.parseFromString(`<input type="file" id="file${i}" name="media" style="display: none;">`);
                dom.files = dt.files;
                div.appendChild(img);
                div.appendChild(button);
                div.appendChild(dom);
                this.preview.appendChild(div);

                reader.addEventListener('load', () => {
                    img.setAttribute('src', reader.result);
                });
                reader.readAsDataURL(files[i]);
            }
            this.mediaInput.value = '';
        }, false)
    }

    clearPreview() {
        while (this.preview.firstChild)
            this.preview.removeChild(this.preview.firstChild);
    }

    deleteMedia(preview) {
        preview.remove();
    }
}

//Add Code
class TwFormUtils {
    constructor() {

        this.content = utils.domUtils.getElementById('content');
        //this.wordCounter = new WordCounter();
        this.reTwUtil = new reTwUtil();
        this.replyTwUtil = new replyTwUtil();
    }

    showReTw(id) {
        this.content.value = '';
        utils.mediaPreview.clearPreview();
        utils.wordCounter.clearCounter();
        this.replyTwUtil.hideReplyTw();
        this.reTwUtil.showReTw(id);
    }

    showReplyTw(id) {

        this.content.value = '';
        utils.mediaPreview.clearPreview();
        utils.wordCounter.clearCounter();

        this.reTwUtil.hideReplyTw();
        this.replyTwUtil.showReTw();
        this.replyTwUtil.importReplyTw(id);
    }

    cleanUp() {
        this.reTwUtil.removeReTw();
        this.replyTwUtil.removeReTw();
    }

}


class reTwUtil {
    constructor() {
        [this.retw, this.retw_id, this.retw_userid, this.retw_name, this.retw_icon, this.retw_content, this.retw_medias]
            = utils.domUtils.getElementListByIdList('retw', 'retw_id', 'retw_userid', 'retw_name', 'retw_icon', 'retw_content', 'retw_medias');
        [this.retw_retw, this.retw_retw_id, this.retw_retw_userid, this.retw_retw_name, this.retw_retw_icon, this.retw_retw_content, this.retw_retw_medias]
            = utils.domUtils.getElementListByIdList('retw_retw', 'retw_retw_id', 'retw_retw_userid', 'retw_retw_name', 'retw_retw_icon', 'retw_retw_content', 'retw_retw_medias');
        [this.hasTwMedias, this.hasReTw, this.hasReTwMedias] = [false, false, false];
        this.content = utils.domUtils.getElementById('content');
    }

    hideReTw() { this.retw.classList.add('hidden'); }

    copyTw(id, is_retw) {
        if (!is_retw) {
            utils.domUtils.copyTextContent(this.retw_userid, 'userid_' + id);
            utils.domUtils.copyTextContent(this.retw_name, 'name_' + id);
            utils.domUtils.copySrc(this.retw_icon, 'icon_' + id);
            utils.domUtils.copyTextContent(this.retw_content, 'content_' + id);
        } else {
            utils.domUtils.copyTextContent(this.retw_retw_userid, 'reTw_userid_' + id);
            utils.domUtils.copyTextContent(this.retw_retw_name, 'reTw_name_' + id);
            utils.domUtils.copySrc(this.retw_retw_icon, 'reTw_icon_' + id);
            utils.domUtils.copyTextContent(this.retw_retw_content, 'reTw_content_' + id);
        }
    }

    showReTw(id) {
        this.clearReTw();
        this.retw.classList.remove('hidden');
        this.retw_id.value = id;
        this.copyTw(id, false);
        const source_medias = utils.domUtils.getElementById('medias_' + id);
        if (source_medias != null) {
            this.hasTwMedias = true;
            this.retw_medias.appendChild(source_medias.firstElementChild.cloneNode(true));
        }
        const retw = utils.domUtils.getElementById('reTw_' + id);
        if (retw != null) {
            this.hasReTw = true;
            this.retw_retw.classList.remove('hidden');
            this.copyTw(id, true);
            const retw_source_medias = utils.domUtils.getElementById('reTw_medias_' + id);
            if (retw_source_medias != null) {
                this.hasReTwMedias = true;
                this.retw_retw_medias.appendChild(retw_source_medias.firstElementChild.cloneNode(true));
            }
        }
    }

    clearReTw() {
        this.retw_content.textContent = '';
        if (this.hasTwMedias) {
            this.hasTwMedias = false;
            this.retw_medias.removeChild(this.retw_medias.firstChild);
        }
        if (this.hasReTw) {
            this.hasReTw = false;
            this.retw_retw.classList.add('hidden')
            this.retw_retw_content.textContent = '';
            if (this.hasReTwMedias) {
                this.hasReTwMedias = false;
                this.retw_retw_medias.removeChild(this.retw_retw_medias.firstChild);
            }
        }
    }

    removeReTw() {
        this.retw.classList.add('hidden');
        this.retw_id.value = '';
        this.clearReTw();
    }
}

// Add Code
class replyTwUtil {
    constructor() {
        [this.replytw, this.replytw_id, this.replytw_userid, this.replytw_name, this.replytw_icon, this.replytw_content]
            = utils.domUtils.getElementListByIdList('replytw', 'replytw_id', 'replytw_userid', 'replytw_name', 'replytw_icon', 'replytw_content');
        this.content = utils.domUtils.getElementById('content');
    }

    showReplyTw() { this.replytw.classList.remove('hidden'); }

    hideReplyTw() { this.replytw.classList.add('hidden'); }

    importReplyTw(id) { }

    removeInput() {
        replytw_id.value = '';
    }
}

class utils {
    static domUtils;
    static wordCounter;
    static mediaPreview;
    static twFormUtils;
}
