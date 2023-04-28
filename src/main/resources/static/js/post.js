function checkAuthenticated() {
    if (document.querySelector('meta[name="_username"]')) return true;
    return false;
}