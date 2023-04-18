let clickFlag = false;

window.addEventListener('load', () => {
    const searchInput = document.getElementById('search_input');
    searchInput.addEventListener('focus', function(_) {
        searchInput.nextElementSibling.style.visibility = "visible"
    });
    searchInput.addEventListener('blur', function(_) {
        setTimeout(function() {
            if (!clickFlag) {
                searchInput.nextElementSibling.style.visibility = "hidden"
            } else {
                clickFlag = false;
            }
        }, 100);
    });
})

function clearSearchBar(node) {
    clickFlag = true;
    node.previousElementSibling.value = '';
    node.previousElementSibling.focus();
}

