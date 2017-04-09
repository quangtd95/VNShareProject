$(document).ready(function() {

	$('body').prepend('<a href="#" class="back-to-top">Back to Top</a>');

	var amountScrolled = 300;

	$(window).scroll(function() {
		if ( $(window).scrollTop() > amountScrolled ) {
			$('a.back-to-top').fadeIn('slow');
		} else {
			$('a.back-to-top').fadeOut('slow');
		}
	});

	$('a.back-to-top, a.simple-back-to-top').click(function() {
		$('html, body').animate({
			scrollTop: 0
		}, 700);
		return false;
	});

	$("#form_contact").submit(function(event) {
		event.preventDefault();
		call_ajax_to_submit();
	});

	


});

function call_ajax_to_submit() {

	var contact = {
		name : $('#modal-contact-name').val(),
		email : $('#modal-contact-email').val(),
		phone : $('#modal-contact-phone').val(),
		content : $('#modal-contact-content').val()
	};
	console.log(contact);
	$.ajax({
		url : '/contact',
		type : 'POST',
		contentType : "application/json; charset=utf-8",
		async : true,
		data : JSON.stringify(contact),
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-CSRF-Token', $("input[name=_csrf]").val());
		},
		success : function(data) {
            swal(
                'Thành công!',
                'Chúng tôi sẽ liên hệ lại bạn trong thời gian sớm nhất!',
                'success'
            );
		},
		error : function() {
			swal('Oops...',
                'Có lỗi xảy ra hoặc code viết chưa xong :))!',
                'error');
		}
	});

}

