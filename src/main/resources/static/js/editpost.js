/**
 * Created by djwag on 4/15/2017.
 */
$(document).ready(function () {
    if ($('#error_message').val() != null){
        swal('Oops...',
            'Bạn không có quyền chỉnh sửa bài viết!',
            'error');
    }
    $("#form-post-image-cover").keyup(function() {
        $("#imagePreview").attr("src",$('#form-post-image-cover').val());
        console.log($('#form-post-image-cover').val());
    });
    $('#form-post').submit(function (event) {
        event.preventDefault();
        call_ajax_to_update_post();
    });


});

function  call_ajax_to_update_post() {
    var post = {
        id : $('#form-post-id').val(),
        title : $('#form-post-title').val(),
        imageCover : $('#form-post-image-cover').val(),
        content : $('#form-post-content').val()
    };
    console.log(post);
    $.ajax({
        url : '/edit',
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
                'chỉnh sửa bài viết thành công',
                'success'
            ).then(function (val) {
                window.location.replace("/post/"+post.id);
            });
        },
        error : function() {
            swal('Oops...',
                'code viết chưa xong hoặc bạn không có quyền sửa :))!',
                'error');
        }
    });
}