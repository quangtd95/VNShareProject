$(document).ready(function() {
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