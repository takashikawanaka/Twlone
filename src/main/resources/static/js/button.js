// Fav Follow Button
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

function postFollow(node, id) {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }

    if (node.textContent == 'Follow') {
        postCSRF(baseURL() + '/user/follow', id, (_) => {
            node.textContent = 'Following';
            const counter = document.getElementById('follower');
            counter.textContent = parseInt(counter.textContent) + 1;
        });
    } else {
        postCSRF(baseURL() + '/user/unfollow', id, (_) => {
            node.textContent = 'Follow';
            const counter = document.getElementById('follower');
            counter.textContent = parseInt(counter.textContent) - 1;
        });
    }
}

function postFavorite(node, id) {
    if (!checkAuthenticated()) {
        window.location.href = '/login';
        return;
    }

    if (node.firstElementChild.textContent == 'favorite') {
        postCSRF(baseURL() + '/user/unfavorite', id, (_) => {
            const span = node.firstElementChild;
            span.textContent = 'favorite_border';
            span.className = 'material-icons-outlined';
            const p = node.nextElementSibling;
            p.textContent = parseInt(p.textContent) - 1;
        });
    } else {
        postCSRF(baseURL() + '/user/favorite', id, (_) => {
            const span = node.firstElementChild;
            span.textContent = 'favorite';
            span.className = 'material-icons-outlined text-rose-600';
            const p = node.nextElementSibling;
            p.textContent = parseInt(p.textContent) + 1;
        })
    }
}