function post() {
    var questionId =$("#question_id").val();
    var content =$("#comment_content").val();
    comment1target(questionId,1,content);
}
function comment(e) {
    var commentId=e.getAttribute("data-id");
    var content=$("#input-"+commentId).val();
    comment1target(commentId,2,content);
}
function comment1target(targetid,type,content) {
    if (!content){
        alert("不能回复空内容！")
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId": targetid,
            "content":content,
            "type":type
        }),
        success:function(response){
            if(response.code==2000){
                window.location.reload();
            }else {
                if (response.code == 2003) {
                    var isAccepted =confirm(response.message);
                    if (isAccepted){
                        window.open("http://192.168.31.174/oauth/authorize?client_id=cc518f33e8f444004b8656c006ad516c5f1ee7d3537a6802be0b7d7c70af15ed&redirect_uri=http://localhost:8887/callback&response_type=code&state=active");
                        var url=window.location.href;
                        window.localStorage.setItem("closable",true);
                        window.localStorage.setItem("url1",url);
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
        if (comments.children().length==1) {
            $.getJSON("/comment/"+id,function(data){
                $.each(data.data.reverse(),function (index,comment) {
                    var today = new Date(comment.gmtCreate).format("yyyy-MM-dd");
                    var html=$("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 panel-body comment-all"
                    }).append($("<div/>",{
                        "class":"media"
                    }));
                    var html1=$("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));
                    html.append(html1);
                    var html2=$("<div/>",{
                        "class":"media-body"
                    });
                    var html21=$("<h5/>",{
                        "class":"media-heading"
                    }).append($("<span/>",{
                        "text":comment.user.name
                    }));
                    var html22=$("<pre/>",{
                        "class":"comment-content",
                        "html":comment.content
                    });
                    var html23=$("<div/>",{
                        "class":"btn-group"
                    });
                    var html231=$("<span/>",{
                        "class":"glyphicon glyphicon-thumbs-up btn comment-like",
                        "aria-hidden":"true",
                    }).append($("<span/>",{
                        "class":"comment-likes",
                        "text":comment.likeCount
                    }));
                    var html232=$("<span/>",{
                        "class":"glyphicon glyphicon-comment btn comment-like",
                        "aria-hidden":"true",
                        "data-id":comment.id,
                        "onclick":"collapseComments(this)"
                    }).append($("<span/>",{
                        "class":"comment-likes",
                        "text":comment.commentCount
                    }));
                    var html24=$("<span/>",{
                        "class":"pull-right comment-time",
                        "text":today
                    });
                    html23.append(html231);
                    html23.append(html232);
                    html2.append(html21);
                    html2.append(html22);
                    html2.append(html23);
                    html2.append(html24);
                    html.append(html2);
                    comments.prepend(html);
                });
                console.log(data);
            });
        }
        var content="input-"+id;
        document.getElementById(content).value="";
        comments.addClass("in");
        e.classList.add("comment-comment-color")
        e.setAttribute("data-collapse","in");
    }else {
        comments.removeClass("in");
        e.classList.remove("comment-comment-color")
        e.removeAttribute("data-collapse");
    }
}
Date.prototype.format = function(fmt){
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };

    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(
                RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }

    return fmt;
}
function showTag() {
    $("#select-tag").show();
}
function writeTag(e){
    var value=e.getAttribute("data-tag");
    var write=$("#tag").val();
    if (write.indexOf(value)==-1) {
        if (write){
            $("#tag").val(write+","+value);
        }else {
            $("#tag").val(value);
        }
        // var html=$("<span/>",{
        //     "class":"label label-info question-tag",
        // });
        // var html1=("<span/>",{
        //     "class":"glyphicon glyphicon-tags glyphicon",
        //     "aria-hidden":"true"
        // });
        // var html2=("<span/>",{
        //     "onclick":"label label-info",
        //     "text":write
        // });
        // html.append(html1);
        // html.append(html2);
        // writeAll.prepend(html);
    }
}
//控制箭头事件
function bigImg(x)
{
    x.classList.remove('logo-small')
}

function normalImg(x)
{
    x.classList.add('logo-small')
}