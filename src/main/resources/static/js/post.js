/**
 * Created by djwag on 4/14/2017.
 */
$(document).ready(function () {
    $('#form-post').submit(function (event) {
        event.preventDefault();
        call_ajax_to_post();
    });
    $("#form-post-image-cover").keyup(function() {
    	if ($('#form-post-image-cover').val().length == 0) 
    		$('#form-post-image-cover').val("https://www.registerforshare.org/images/shareLogo.png");
        $("#imagePreview").attr("src",$('#form-post-image-cover').val());
    });
    $('#form-post-tag').keyup(function () {
        call_ajax_to_get_tag();
    });
});

function call_ajax_to_get_tag() {
    var input = $('#form-post-tag').val();
    var tags = input.split(" ");
    tags.forEach(function (item, index) {
        if (item=="" ||item==" ") tags.splice(index,1);
    })

    var json = {
        query: tags[tags.length - 1]
    }
    console.log(json);
    $.ajax({
        url:"/getTagToPost",
        type:'POST',
        contentType : "application/json; charset=utf-8",
        data : JSON.stringify(json),
        beforeSend : function(xhr) {
            xhr.setRequestHeader('X-CSRF-Token', $("input[name=_csrf]").val());
        },
        success : function(data) {
            data = JSON.parse(data);
            if (data.length == 0) $('#result-tag').empty();
            else {
                var result = "";
                for (i=0;i<data.length;i++){
                    var f = "onclick=\"completeTag('"+data[i].name+"')\"";
                    result += "<button class='btn btn-info'type='button' style='margin-right: 2px'"+f+">"+data[i].name+"</button>";
                }
                $('#result-tag').html(result);
            }
        },
        error : function() {}
    });

}
function completeTag(name){

    var value = $('#form-post-tag').val();
    var position = value.lastIndexOf(" ");
    if (position == -1 ) position = 0;
    var  x = value.substring(position,value.length);
    if (position == 0)  buffer = ""; else buffer = " ";
    value = value.replace(x,'') +buffer + name;
    $("#form-post-tag").val(value);

}
function  call_ajax_to_post() {
    var t = $("#form-post-tag").val();
    var post = {
        title : $('#form-post-title').val(),
        imageCover: $('#form-post-image-cover').val(),
        bufferTags: t,
        content : $('#form-post-content').val(),
    };
   
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