async function postTw(form) {
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
    fetch(form.getAttribute("action"), {
        method: 'POST',
        body: new FormData(form),
    }).then((res) => {
        if (res.ok) {
            console.log("OK");
        } else { console.log("ERROR"); }
    });
}