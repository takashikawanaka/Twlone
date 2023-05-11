window.addEventListener('load', () => {
    if (checkAuthenticated()) {
        utils.profile = new Profile();
    }
})

class Profile {
    constructor() {
        [this.name, this.userId, this.description, this.icon, this.back]
            = utils.domUtils.getElementListByIdList('profile_name', 'profile_userId', 'profile_description', 'profile_icon', 'profile_back');
        [this.name_text, this.userId_text, this.description_text] = ['', '', ''];
        [this.isEdit, this.isCheckDuplication] = [false, true];
        this.checkIcon;
        this.timer;

        utils.domUtils.getElementById('userIcon').addEventListener('change', e => {
            const reader = new FileReader();
            reader.addEventListener('load', () => { this.icon.firstElementChild.setAttribute('src', reader.result); });
            reader.readAsDataURL(e.target.files[0]);
        })
        utils.domUtils.getElementById('userBack').addEventListener('change', e => {
            const reader = new FileReader();
            reader.addEventListener('load', () => { this.back.firstElementChild.setAttribute('src', reader.result); });
            reader.readAsDataURL(e.target.files[0]);
        })
    }

    switchEdit(node) {
        if (!this.isEdit) {
            this.convertField();
            this.isEdit = true;
            node.textContent = 'Save';
        } else if (this.isCheckDuplication) {
            this.convertP().then((_) => {
                this.isEdit = false;
                this.checkIcon = null;
                node.textContent = 'Edit';
            });
        }
    }

    convertTag(str) { //Convert HTML Tag
        return str;
    }

    convertField() {
        const [nameChild, userIdChild, descriptionChild] = [this.name.lastElementChild, this.userId.lastElementChild, this.description.children];
        this.name_text = nameChild.textContent;
        this.name.appendChild(utils.domUtils.parseFromString(`<input type="text" class="w-full bg-transparent focus:outline-none" form="postUser" value="${this.name_text}">`));
        nameChild.remove();

        this.userId.classList.remove('text-sm');
        this.userId_text = userIdChild.textContent;
        const userIdInput = utils.domUtils.parseFromString(`<input type="text" class="grow bg-transparent focus:outline-none" form="postUser" value="${this.userId_text}">`);
        userIdInput.addEventListener('input', () => {
            this.isCheckDuplication = false;
            if (this.timer == null) { this.switchIcon(1); }
            clearTimeout(this.timer);
            const userId = userIdInput.value;
            if (userId == '') {
                this.switchIcon(3);
                this.timer = null;
            } else if (this.userId_text == userId) {
                this.switchIcon(2);
                this.isCheckDuplication = true;
                this.timer = null;
            } else {
                this.timer = setTimeout(() => this.checkDuplication(userIdInput.value), 1000);
            }
        });
        this.userId.appendChild(userIdInput);
        this.userId.appendChild(this.switchIcon(2));
        userIdChild.remove();

        const descriptionInput
            = utils.domUtils.parseFromString(`<textarea type="text" rows=1 class="block w-full resize-none bg-transparent focus:outline-none" style="height: ${this.description.clientHeight < 24 ? 24 : this.description.clientHeight}" form="postUser">`);
        this.description_text = Array.from(descriptionChild).map(element => element.textContent).join('\n');
        descriptionInput.value = this.description_text;
        descriptionInput.addEventListener('input', () => {
            descriptionInput.style.height = 'auto';
            descriptionInput.style.height = `${descriptionInput.scrollHeight < 24 ? 24 : descriptionInput.scrollHeight}px`;
        });
        Array.from(descriptionChild).forEach(element => element.remove());
        this.description.appendChild(descriptionInput);

        this.icon.nextElementSibling.classList.remove('hidden');
        this.back.firstElementChild.nextElementSibling.classList.remove('hidden');
    }

    async convertP() {
        const [nameChild, userIdChild, descriptionChild]
            = [this.name.firstElementChild, this.userId.firstElementChild.nextElementSibling, this.description.firstElementChild];
        const [name_text, userId_text, description_text] = [nameChild.value, userIdChild.value, descriptionChild.value];
        await this.postUser(name_text, userId_text, description_text).then(() => {
            this.name.appendChild(utils.domUtils.parseFromString(`<span>${this.convertTag(name_text)}</span>`));
            nameChild.remove();

            this.userId.classList.add('text-sm');
            this.userId.appendChild(utils.domUtils.parseFromString(`<span>${this.convertTag(userId_text)}</span>`));
            userIdChild.nextElementSibling.remove();
            userIdChild.remove();

            description_text.split('\n').forEach(str => this.description.appendChild(utils.domUtils.parseFromString(`<span>${this.convertTag(str)}</span>`)));
            descriptionChild.remove();

            this.icon.nextElementSibling.classList.add('hidden');
            this.back.firstElementChild.nextElementSibling.classList.add('hidden');
        });
    }

    checkDuplication(userId) {
        fetch(location.protocol + '//' + location.host + `/user/?userId=${userId}`, {
            method: "GET",
        }).then((res) => {
            if (!res.ok) {
                console.error('Error: The post has failed.');
                return;
            }
            res.text().then(result => {
                if (result == 'false') {
                    this.switchIcon(2);
                    this.isCheckDuplication = true;
                } else { this.switchIcon(3); }
                this.timer = null;
            })
        });
    }

    switchIcon(num) {
        let icon = null;
        if (num == 1) {
            icon = utils.domUtils.parseFromString(`<span class="material-icons-round animate-spin">refresh</span>`);
        } else if (num == 2) {
            icon = utils.domUtils.parseFromString(`<span class="material-icons-round">check</span>`);
        } else if (num == 3) {
            icon = utils.domUtils.parseFromString(`<span class="material-icons-round">error</span>`);
        }
        if (this.checkIcon != null) { this.userId.replaceChild(icon, this.checkIcon); }
        this.checkIcon = icon;
        return this.checkIcon
    }

    async postUser(name_text, userId_text, description_text) {
        const [name, userId, description, icon, back] = utils.domUtils.getElementListByIdList('name', 'userId', 'description', 'userIcon', 'userBack');
        let changeFlag = false;
        name.value = '';
        if (this.name_text != name_text) {
            name.value = name_text;
            changeFlag = true;
        }
        userId.value = '';
        if (this.userId_text != userId_text) {
            userId.value = userId_text;
            changeFlag = true;
        }
        description.value = '';
        if (this.description_text != description_text) {
            if (140 < description_text.length)
                throw new Error('The description exceeds the 140 character limit.');
            if(description_text == '') description_text = '-1';
            description.value = description_text;
            changeFlag = true;
        }
        //Add Image Size Check
        if (icon.value != '' || back.value != '') { changeFlag = true; }
        if (changeFlag) {
            const userForm = utils.domUtils.getElementById('userForm');
            const formData = new FormData(userForm.firstElementChild);
            await fetch(location.protocol + '//' + location.host + '/user/', {
                method: "POST",
                body: formData
            }).then((res) => {
                if (res.ok) {
                    console.log('update profile');
                    userForm.firstElementChild.reset();
                }
                else { throw new Error('The post has failed.'); }
            });
        }
    }
}

function editProfile(node) {
    utils.profile.switchEdit(node);
}
