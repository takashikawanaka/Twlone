async function postTw(form) {
    if (!isAuthenticated) {
        console.log("login");
        return;
    }
    fetch(form.getAttribute("action"), {
        method: 'POST',
        body: new FormData(form),
    }).then((res) => {
        if (res.ok) {
            form.reset();
            console.log("OK");
        } else { console.log("ERROR"); }
    });
}

async function postFollow(form) {
    if (!isAuthenticated) {
        console.log("login");
        return;
    }
    fetch(form.getAttribute("action"), {
        method: 'POST',
        body: new FormData(form),
    }).then((res) => {
        if (res.ok) {
            console.log("OK");
        } else { console.log("ERROR"); }
    });
}

async function postTw() { }

async function postFavorite(button) {
    if (!isAuthenticated) {
        console.log("login");
        return;
    }
    button.form.setAttribute("action", button.getAttribute("formaction"))
    fetch(button.getAttribute("formaction"), {
        method: 'POST',
        body: new FormData(button.form),
    }).then((res) => {
        if (res.ok) {
            console.log("OK");
        } else { console.log("ERROR"); }
    });
}