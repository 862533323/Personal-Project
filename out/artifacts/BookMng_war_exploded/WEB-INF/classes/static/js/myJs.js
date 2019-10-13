function ClearBr(key) {
    key = key.replace(/<\/?.+?>/g,"");
    key = key.replace(/[\r\n]/g, "");
    return key;
}
function loadMore() {
    setTimeout(function(){
        $.ajax({
            type: 'GET',
            url: "/user/searchNext",
            contentType: "application/json;charset=utf-8",
            dataType: "text",
            success: function (data) {
                var loading="<div class='col-md-12'><div class='load-more-button'><a onclick='loadMore();' href='#'>Load More</a></div></div>";
                if (data!=""){
                    $(".col-md-12").remove();
                    var list = data; //取出后端传过来的list值
                    $("#append").append(list);//将信息追加
                    $("#append").append(loading);
                } else{
                    $(".col-md-12").remove();
                    var loading="<div class='col-md-12'><div class='load-more-button'><a>到底了</a></div></div>";
                    $("#append").append(loading);
                }
            }
        });
    },1000);
    $("#loading").remove();
}
function judge(data) {
    if ($("#user").text()=="未登录")
        window.location.href="/Login/to_login";
    else search(data);
}
function search(keyword){
    $.ajax({
        type: 'GET',
        url: "/user/search/"+keyword,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            var loading="<div class='col-md-12'><div class='load-more-button'><a onclick='loadMore();' href='#'>Load More</a></div></div>";
            if (data!=""){
                $("#append").empty();
                var list = data; //取出后端传过来的list值
                $("#append").append(list);//将信息追加
                $("#append").append(loading);
            } else{
                $(".col-md-12").remove();
                var loading="<div class='col-md-12'><div class='load-more-button'><a>到底了</a></div></div>";
                $("#append").append(loading);
            }
        }
    });
}
function getName() {
    $.ajax({
        type:'get',
        url:"/user/getUserName",
        dataType:'text',
        success:function (data) {
            if (data=="")
                ;
            else
                $("#user").text(data);
        }
    })
}
function getSetting() {
    $.ajax({
        type:'get',
        url:"/user/getSetting",
        dataType:'text',
        success:function (data) {
            if (data=="")
                ;
            else
                $(".main-menu").append(data);
        }
    })
}
function inf(id) {
    window.location='/user/book_inf/'+id;
}
function unLogin() {
    $.ajax({
        type: 'GET',
        url: "/user/unLogin",
        dataType:"text",
        success: function () {
            document.location.reload();
        }
    });
}