function hideMessage() {
    $('#procesando').remove();
    return false;
}

function showMessage() {
    $('<div id="procesando" class="overlay"></div>').appendTo('body');
    return true;
}