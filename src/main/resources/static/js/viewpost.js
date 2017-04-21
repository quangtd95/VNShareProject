function voted_up() {
    $("#icon_btn_bottom").removeAttr('class');
    $("#icon_btn_bottom").attr('class', 'fa fa-caret-down');

    $("#icon_btn_top").removeAttr('class');
    $("#icon_btn_top").attr('class', 'fa fa-close');
}
function voted_down() {
    $("#icon_btn_top").removeAttr('class');
    $("#icon_btn_top").attr('class', 'fa fa-caret-up');

    $("#icon_btn_bottom").removeAttr('class');
    $("#icon_btn_bottom").attr('class', 'fa fa-close');
}
function remove_vote() {
    $("#icon_btn_top").removeAttr('class');
    $("#icon_btn_top").attr('class', 'fa fa-caret-up');
    $("#icon_btn_bottom").removeAttr('class');
    $("#icon_btn_bottom").attr('class', 'fa fa-caret-down');
}
function call_ajax_to_delete_post() {
    var id = $('#post_id').val();
    $.ajax({
        url: '/delete',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        async: true,
        data: JSON.stringify(id),
        beforeSend: function (xhr) {
            xhr.setRequestHeader('X-CSRF-Token', $("input[name=_csrf]").val());
        },
        success: function (data) {
          swal('Thành công',
          data.text,
          'success').then(function (val) {
              window.location.replace("/profile");
          });
        },
        error: function (data) {
            swal('Oops...',
                data.text,
                'error');
        }
    });
}
$(document).ready(function () {
    var status = $('#vote_status').val();
    if (status == 1) {
        voted_up();
    } else if (status == 0) {
        voted_down();
    } else {
        remove_vote();
    }

    $("#btn_top").on('click', function () {
        changeUI(1);
    });
    $("#btn_bottom").on('click', function () {
        changeUI(0);
    });
    $('#btnDeletePost').on('click', function () {
        call_ajax_to_delete_post();
    });
});
function changeUI(b) {
    console.log(b + ", current " + $("#vote_status").val());
    if ($("#vote_status").val() == b) {
        remove_vote();
        call_ajax_to_vote(-1);
    } else {
        if (b == 1) {
            voted_up();
        } else {
            voted_down();
        }
        call_ajax_to_vote(b);
    }

}

function call_ajax_to_vote(b) {
    var vote = {
        userId: $("#user_id").val(),
        postId: $("#post_id").val(),
        status: b
    }
    $.ajax({
        url: '/vote',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        async: true,
        data: JSON.stringify(vote),
        beforeSend: function (xhr) {
            xhr.setRequestHeader('X-CSRF-Token', $("input[name=_csrf]").val());
        },
        success: function (data) {
            var x, reputation;
            if (b == 1) {
                x = parseInt($("#points_vote").text()) + 1;
                reputation = parseInt($("#userReputation").text()) + 1;

            } else if (b == 0) {
                x = parseInt($("#points_vote").text()) - 1;
                reputation = parseInt($("#userReputation").text()) - 1;
            }
            else if (b == -1) {
                if ($("#vote_status").val() == 1) {
                    x = parseInt($("#points_vote").text()) - 1;
                    reputation = parseInt($("#userReputation").text()) - 1;
                }
                if ($("#vote_status").val() == 0) {
                    x = parseInt($("#points_vote").text()) + 1;
                    reputation = parseInt($("#userReputation").text()) + 1;
                }
            }
            $("#points_vote").text(x);
            $("#vote_status").val(b);
            $("#userReputation").text(reputation);
        },
        error: function () {
            swal('Oops...',
                'Có lỗi xảy ra hoặc code viết chưa xong :))!',
                'error');
        }
    });
};