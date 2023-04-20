class DomUtils {
    constructor() { this.parser = new DOMParser(); }

    getElementById(id) { return document.getElementById(id); }

    getElementListByIdList(...idList) { return idList.map((id) => this.getElementById(id)); }

    copyTextContent(source, target, str) { source.textContent = this.getElementById(target).textContent + (str == undefined ? '' : str); }

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
        if (height == 160) {
            this.area.style.height = '160px';
        } else {
            this.area.style.height = 'auto';
            this.area.style.height = `${this.area.scrollHeight}px`;
        }
        this.progress.style.background = `conic-gradient(${this.color(per)} ${360 * per}deg, transparent 0deg)`;
        this.value.textContent = 280 - lenght;
    }

    refreshCounter() { this.setCounter((this.area.scrollHeight < 160 ? 160 : this.area.scrollHeight), this.area.value.length / 280, this.area.value.length); }

    clearCounter() { this.setCounter(160, 0, 0); }
}

class MediaPreview {
    constructor() {
        [this.mediaInput, this.preview] = utils.domUtils.getElementListByIdList('mediaInput', 'preview');
        this.parser = new DomUtils();
        this.mediaInput.addEventListener('change', e => {
            const { files } = e.target;
            if (4 < files.length + this.preview.childElementCount) {
                console.error('Error: Too many files selected. Please select 4 or fewer files');
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
                const button = this.parser.parseFromString(`<button type="button" onclick="deleteMedia(this.parentElement)" class="absolute top-0 right-0"> <span class="material-icons-round" style="font-size: 36px;">cancel</span></button>`);
                const dom = this.parser.parseFromString(`<input type="file" id="file${i}" name="media" style="display: none;">`);
                dom.files = dt.files;
                div.appendChild(img);
                div.appendChild(button);
                div.appendChild(dom);
                this.preview.appendChild(div);

                reader.addEventListener('load', () => { img.setAttribute('src', reader.result); });
                reader.readAsDataURL(files[i]);
            }
            this.mediaInput.value = '';
        }, false)
    }

    clearPreview() { while (this.preview.firstChild) this.preview.removeChild(this.preview.firstChild); }

    deleteMedia(preview) { preview.remove(); }
}

class TwFormUtils {
    constructor() {
        [this.content, this.form] = utils.domUtils.getElementListByIdList('content', 'tw_form');
        this.wordCounter = new WordCounter();
        this.mediaPreview = new MediaPreview();
        this.reTwUtil = new ReTwUtil();
        this.replyTwUtil = new ReplyTwUtil();
        [this.isReTw, this.isReplyTw] = [false, false];
    }

    openTw() { location.hash = 'tw'; }

    removePreview() { this.mediaPreview.clearPreview(); }

    deletePreview(preview) { this.mediaPreview.deletePreview(preview); }

    refreshForm() {
        this.form.reset();
        this.mediaPreview.clearPreview();
        this.wordCounter.clearCounter();
    }

    showReTw(id) {
        this.cleanUp();
        this.reTwUtil.showReTw(id);
        this.isReTw = true;
        this.openTw();
    }

    showReplyTw(id) {
        this.cleanUp();
        this.replyTwUtil.showReplyTw(id);
        this.wordCounter.refreshCounter();
        this.isReplyTw = true;
        this.openTw();
    }

    cleanUp() {
        this.refreshForm();
        if (this.isReTw) {
            this.reTwUtil.removeReTw();
            this.isReTw = false;
        }
        if (this.isReplyTw) {
            this.replyTwUtil.removeReplyTw();
            this.isReplyTw = false;
        }
    }
}

class ReTwUtil {
    constructor() {
        [this.retw, this.retw_id, this.retw_userid, this.retw_name, this.retw_icon, this.retw_content, this.retw_medias]
            = utils.domUtils.getElementListByIdList('retw', 'retw_id', 'retw_userid', 'retw_name', 'retw_icon', 'retw_content', 'retw_medias');
        [this.retw_retw, this.retw_retw_id, this.retw_retw_userid, this.retw_retw_name, this.retw_retw_icon, this.retw_retw_content, this.retw_retw_medias]
            = utils.domUtils.getElementListByIdList('retw_retw', 'retw_retw_id', 'retw_retw_userid', 'retw_retw_name', 'retw_retw_icon', 'retw_retw_content', 'retw_retw_medias');
        [this.hasTwMedias, this.hasReTw, this.hasReTwMedias] = [false, false, false];
    }

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

class ReplyTwUtil {
    constructor() {
        [this.replytw, this.replytw_id, this.replytw_userid, this.replytw_name, this.replytw_icon, this.replytw_content, this.replytw_medias]
            = utils.domUtils.getElementListByIdList('replytw', 'replytw_id', 'replytw_userid', 'replytw_name', 'replytw_icon', 'replytw_content', 'replytw_medias');
        [this.replytw_retw, this.replytw_retw_id, this.replytw_retw_userid, this.replytw_retw_name, this.replytw_retw_icon, this.replytw_retw_content, this.replytw_retw_medias]
            = utils.domUtils.getElementListByIdList('replytw_retw', 'replytw_retw_id', 'replytw_retw_userid', 'replytw_retw_name', 'replytw_retw_icon', 'replytw_retw_content', 'replytw_retw_medias');
        [this.hasTwMedias, this.hasReTw, this.hasReTwMedias] = [false, false, false];
        this.content = utils.domUtils.getElementById('content');
    }

    copyTw(id, is_retw) {
        if (!is_retw) {
            utils.domUtils.copyTextContent(this.replytw_userid, 'userid_' + id);
            utils.domUtils.copyTextContent(this.replytw_name, 'name_' + id);
            utils.domUtils.copySrc(this.replytw_icon, 'icon_' + id);
            utils.domUtils.copyTextContent(this.replytw_content, 'content_' + id);
        } else {
            utils.domUtils.copyTextContent(this.replytw_retw_userid, 'reTw_userid_' + id);
            utils.domUtils.copyTextContent(this.replytw_retw_name, 'reTw_name_' + id);
            utils.domUtils.copySrc(this.replytw_retw_icon, 'reTw_icon_' + id);
            utils.domUtils.copyTextContent(this.replytw_retw_content, 'reTw_content_' + id);
        }
    }

    showReplyTw(id) {
        this.clearReplyTw();
        this.replytw.classList.remove('hidden');
        this.replytw_id.value = id;
        this.copyTw(id, false);
        this.content.value = this.replytw_userid.textContent + ' ';
        const source_medias = utils.domUtils.getElementById('medias_' + id);
        if (source_medias != null) {
            this.hasTwMedias = true;
            this.replytw_medias.appendChild(source_medias.firstElementChild.cloneNode(true));
        }
        const retw = utils.domUtils.getElementById('reTw_' + id);
        if (retw != null) {
            this.hasReTw = true;
            this.replytw_retw.classList.remove('hidden');
            this.copyTw(id, true);
            const retw_source_medias = utils.domUtils.getElementById('reTw_medias_' + id);
            if (retw_source_medias != null) {
                this.hasReTwMedias = true;
                this.replytw_retw_medias.appendChild(retw_source_medias.firstElementChild.cloneNode(true));
            }
        }
    }

    clearReplyTw() {
        this.replytw_content.textContent = '';
        if (this.hasTwMedias) {
            this.hasTwMedias = false;
            this.replytw_medias.removeChild(this.replytw_medias.firstChild);
        }
        if (this.hasReTw) {
            this.hasReTw = false;
            this.replytw_retw.classList.add('hidden')
            this.replytw_retw_content.textContent = '';
            if (this.hasReTwMedias) {
                this.hasReTwMedias = false;
                this.replytw_retw_medias.removeChild(this.replytw_retw_medias.firstChild);
            }
        }
    }

    removeReplyTw() {
        this.replytw.classList.add('hidden');
        this.replytw_id.value = '';
        this.clearReplyTw();
    }
}

class utils {
    static domUtils;
    static twFormUtils;
    static profile;
    static search;
}
