let hash = '';
let isClick = false;
let isAnimate = false;

function baseURL() { return location.protocol + '//' + location.host; }

function checkOpenTw() {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }
    utils.twFormUtils.openTw();
}

function openReplyTw(node) {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }
    utils.twFormUtils.showReplyTw(node.dataset.id);
}

function openReTw(node) {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }
    utils.twFormUtils.showReTw(node.dataset.id);
}

function closeWindow() {
    const scroll = window.scrollY;
    location.hash = '';
    window.scrollTo(0, scroll);
}

// Check Drag or Click
function mousedown() { isClick = true; }

function mousemove() { isClick = false; }

function moveToTwPage(node) {
    if (isClick) {
        window.location.href = `/user/${node.dataset.userid}/status/${node.dataset.twid}`;
        isClick = false;
    }
}

// Open Close Media
function openMedia(media) {
    hash = location.hash;
    document.getElementById('screen_media').src = media.src;
    location.hash = 'media';
}

function closeMedia() {
    if (hash) {
        location.hash = hash;
        hash = '';
    } else {
        closeWindow();
    }
}

// Post with CSRF
function postCSRF(url, id, callback) {
    const form = new FormData();
    form.append('id', id);

    fetch(url, {
        method: "POST",
        headers: {
            [document.querySelector('meta[name="_csrf_header"]').getAttribute('content')]:
                document.querySelector('meta[name="_csrf"]').getAttribute('content')
        },
        body: form
    }).then((res) => {
        if (res.ok) callback(res);
        else { console.log("ERROR"); }
    });
}

// Follow Button
function postFollow(node) {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }
    if (isAnimate) return;
    const id = node.dataset.id;
    const span = node.firstElementChild;
    if (span.textContent == 'Follow') {
        postCSRF(baseURL() + '/user/follow', id, (_) => {
            isAnimate = true;
            span.style.animationName = 'transX-out';
            setTimeout(() => {
                span.classList.add('material-icons-round');
                span.textContent = 'thumb_up';
                span.style.animationName = 'transX-in';
            }, 150);
            setTimeout(() => {
                span.style.animationName = 'transX-out';
            }, 700);
            setTimeout(() => {
                span.classList.remove('material-icons-round');
                span.textContent = 'Following';
                span.style.animationName = 'transX-in';
                isAnimate = false;
            }, 850);
            const counter = document.getElementById('follower').firstElementChild;
            countUp(counter, parseInt(counter.textContent));
        });
    } else {
        postCSRF(baseURL() + '/user/unfollow', id, (_) => {
            span.textContent = 'Follow';
            span.style.animationName = '';
            const counter = document.getElementById('follower').firstElementChild;
            countDown(counter, parseInt(counter.textContent));
        });
    }
}

// Fav Button
function postFavorite(node, hasCounter) {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }
    const id = node.dataset.id;
    if (node.firstElementChild.textContent == 'favorite') {
        postCSRF(baseURL() + '/user/unfavorite', id, (_) => {
            const span = node.firstElementChild;
            span.style.animationName = 'unfav-scale';
            span.textContent = 'favorite_border';
            span.classList.remove('text-rose-600');
            if (hasCounter) {
                const p = node.nextElementSibling;
                countDown(p, parseInt(p.textContent));
            }
        });
    } else {
        postCSRF(baseURL() + '/user/favorite', id, (_) => {
            const span = node.firstElementChild;
            span.style.animationName = 'fav-scale';
            span.textContent = 'favorite';
            span.classList.add('text-rose-600');
            if (hasCounter) {
                const p = node.nextElementSibling;
                countUp(p, parseInt(p.textContent));
            }
        });
    }
}

function countUp(node, count) {
    node.style.animationName = 'transY-up-out';
    setTimeout(() => {
        node.style.animationName = 'transY-up-in';
        node.textContent = count + 1;
    }, 50);
}

function countDown(node, count) {
    node.style.animationName = 'transY-down-out';
    setTimeout(() => {
        node.style.animationName = 'transY-down-in';
        node.textContent = count - 1;
    }, 50);
}

// Delete Button
function deleteTw(node) {
    const id = node.dataset.id;
    if (window.confirm('Delete Tw??\n' + document.getElementById('content_' + id).textContent)) {
        postCSRF(baseURL() + '/user/deletetw', id, (_) => {
            document.getElementById('tw_' + id).remove();
        });
    };
}