$(document).ready(function () {
    $("#form-resetpassword").submit(function (event) {
        event.preventDefault();
        $.ajax({
            url:"/forgotpassword",
            type:'POST',
            data:{
                email:$("#email").val()
            },
            beforeSend : function(xhr) {
                xhr.setRequestHeader('X-CSRF-Token', $("input[name=_csrf]").val());
            },
            success:function () {
                swal('Thành công','Kiểm tra email để nhận lại mật khẩu','success');
            },
            error:function () {
                swal('Lỗi','email không được tìm thấy','error');
            }
        });
    });
});
