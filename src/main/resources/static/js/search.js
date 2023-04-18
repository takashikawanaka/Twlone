window.addEventListener('load', () => {
    utils.search = new Search();
})

class Search {
    constructor() {
        this.clickFlag = false;
        this.initTimeout();
    }

    initTimeout() {
        const searchInput = document.getElementById('search_input');
        searchInput.addEventListener('focus', function(_) {
            searchInput.nextElementSibling.style.visibility = "visible";
        });
        searchInput.addEventListener('blur', function(_) {
            setTimeout(function() {
                if (!utils.search.clickFlag) {
                    searchInput.nextElementSibling.style.visibility = "hidden";
                } else {
                    utils.search.clickFlag = false;
                }
            }, 100);
        });
    }

    clearForm(node) {
        this.clickFlag = true;
        node.previousElementSibling.value = '';
        node.previousElementSibling.focus();
    }
}

function clearSearchBar(node) {
    utils.search.clearForm(node);
}

