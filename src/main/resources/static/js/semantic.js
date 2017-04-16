/**
 * Created by djwag on 4/15/2017.
 */

$(document).ready(function () {
    $('#form-search').submit(function (e) {
        e.preventDefault();
        call_ajax_to_post_search();
    });
});

function  call_ajax_to_post_search() {
    var input = {
        input: $("#input").val()
    };
    $.ajax({
        url : '/semantic',
        type : 'POST',
        contentType : "application/json; charset=utf-8",
        async : true,
        data : JSON.stringify(input),
        beforeSend : function(xhr) {
            xhr.setRequestHeader('X-CSRF-Token', $("input[name=_csrf]").val());
        },
        success : function(data) {
            swal(
                'Kết quả!',
                data,
                'success'
            );
        },
        error : function(data) {
            console.log(data);
            swal('Oops...',
                'code viết chưa xong hoặc bạn không có quyền :))!',
                'error');
        }
    });
}