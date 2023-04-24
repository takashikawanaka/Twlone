document.addEventListener('DOMContentLoaded', function() { location.hash = ''; });

function baseURL() { return location.protocol + '//' + location.host; }

function checkAuthenticated() {
    if (document.querySelector('meta[name="_username"]')) return true;
    return false;
}