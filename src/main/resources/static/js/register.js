$('#inputPassword, #inputPasswordConfirm').on('keyup',check );
function check() {
    if ($('#inputPassword').val() == $('#inputPasswordConfirm').val()) {
        $('#message').html('Matching').css('color', 'green');
        return true;
    } else 
        $('#message').html('Not Matching').css('color', 'red');
    return false;
    
}
