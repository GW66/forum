function post() {
    var questionId =$("#question_id").val();
    var content =$("#comment_content").val();
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
                $("#comment_section").hide();
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
            console.log(response);
        },
        dataType:"json"
    });
}