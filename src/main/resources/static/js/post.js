/**
 * Created by djwag on 4/14/2017.
 */
$(document).ready(function () {
    $('#form-post').submit(function (event) {
        event.preventDefault();
        call_ajax_to_post();
    });
});

function  call_ajax_to_post() {

    var post = {
        title : $('#form-post-title').val(),
        image_cover: $('#form-post-image-cover').val(),
        content : $('#form-post-content').val(),
    };
    console.log(post);
    $.ajax({
        url : '/post',
        type : 'POST',
        contentType : "application/json; charset=utf-8",
        async : true,
        data : JSON.stringify(post),
        beforeSend : function(xhr) {
            xhr.setRequestHeader('X-CSRF-Token', $("input[name=_csrf]").val());
        },
        success : function(data) {
            swal(
                'Thành công!',
                'Đăng bài viết thành công',
                'success'
            ).then(function (val) {
                window.location.replace("/post/"+data);
            });
        },
        error : function() {
            swal('Oops...',
                'Có lỗi xảy ra hoặc code viết chưa xong :))!',
                'error');
        }
    });
}