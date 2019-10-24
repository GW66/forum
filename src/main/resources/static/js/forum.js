function post() {
    var questionId =$("#question_id").val();
    var content =$("#comment_content").val();
    if (!content){
        alert("不能回复空内容！")
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content":content,
            "type":1
        }),
        success:function(response){
            if(response.code==2000){
                window.location.reload();
            }else {
                if (response.code == 2003) {
                    var isAccepted =confirm(response.message);
                    if (isAccepted){
                        window.open("http://192.168.31.174/oauth/authorize?client_id=cc518f33e8f444004b8656c006ad516c5f1ee7d3537a6802be0b7d7c70af15ed&redirect_uri=http://localhost:8887/callback&response_type=code&state=active");
                        var url=window.location.href
                        window.localStorage.setItem("closable",true);
                        window.localStorage.setItem("url",url);
                        window.close();
                    }
                }else {
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    });
}

function collapseComments(e) {
    var id=e.getAttribute("data-id");
    var comments= $("#comment-"+id);
    var collapse=e.getAttribute("data-collapse")
    if (!collapse) {
        comments.addClass("in");
        e.classList.add("comment-comment-color")
        e.setAttribute("data-collapse","in");
    }else {
        comments.removeClass("in");
        e.classList.remove("comment-comment-color")
        e.removeAttribute("data-collapse");
    }
}