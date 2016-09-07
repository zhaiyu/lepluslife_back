<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/12
  Time: 下午3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>乐+生活 后台模板管理系统</title>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link rel="stylesheet" href="${resourceUrl}/css/summernote.css">
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .thumbnail {
            width: 160px;
            height: 160px;
        }

        .thumbnail img {
            width: 100%;
            height: 100%;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
    <script src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script src="${resourceUrl}/js/summernote.min.js"></script>
    <script src="${resourceUrl}/js/summernote-zh-CN.min.js"></script>
</head>

<body>
<div id="topIframe">
    <%@include file="../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main">
            <div class="container-fluid">
                <button type="button" class="btn btn-primary btn-return" style="margin:10px;">
                    返回专题列表
                </button>
                <hr>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="topic-sid" class="col-sm-2 control-label">序号</label>

                        <div class="col-sm-4">
                            <input type="sid" class="form-control" id="topic-sid"
                                   value="${topic.sid}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="topic-pic" class="col-sm-2 control-label">海报图</label>

                        <div class="col-sm-4">
                            <div class="thumbnail"><img src="${topic.picture}" id="image" alt="...">
                            </div>
                            <input type="file" class="form-control" id="topic-pic" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="topic-title" class="col-sm-2 control-label">标题</label>

                        <div class="col-sm-4"><input type="text" value="${topic.title}"
                                                     class="form-control"
                                                     id="topic-title"></div>
                    </div>
                    <div class="form-group">
                        <label for="topic-description" class="col-sm-2 control-label">描述</label>

                        <div class="col-sm-4"><input type="text" value="${topic.description}"
                                                     class="form-control"
                                                     id="topic-description"></div>
                    </div>
                    <div class="form-group">
                        <label for="topic-minPrice" class="col-sm-2 control-label">最低价格</label>

                        <div class="col-sm-4"><input type="number" value="${topic.minPrice/100}"
                                                     class="form-control"
                                                     id="topic-minPrice"></div>
                    </div>
                    <div class="form-group">
                        <label for="summernote" class="col-sm-2 control-label">详情</label>

                        <div class="col-md-8">
                            <%--<input type="hidden" class="content" value="${topic.content}">--%>

                            <div class="summernote" name="summernote" id="summernote"
                                 style="height: 800px;">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-4">
                            <input type="button" class="btn btn-primary" value="提交" onclick="h()"/>
                            <input type="hidden" value="${topic.id}" id="topicId"/>
                        </div>
                    </div>
                    <div style="display: none" id="hidden-div">${topic.content.content}</div>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<script>
    $(function () {
        $('#topic-pic').fileupload({
                                       dataType: 'json',
                                       maxFileSize: 5000000,
                                       acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                       add: function (e, data) {
                                           data.submit();
                                       },
                                       done: function (e, data) {
                                           var resp = data.result;
                                           $('#image').attr('src',
                                                            '${ossImageReadRoot}/'
                                                            + resp.data);
                                       }
                                   });

        $('#summernote').summernote({
                                        height: 300,                 // set editor height
                                        minHeight: null,             // set minimum height of editor
                                        maxHeight: null,
                                        dialogsInBody: true,
                                        toolbar: [
                                            ['style', ['fontsize', 'bold', 'italic', 'underline']],
                                            ['color', ['color']],
                                            ['Layout', ['ul', 'ol', 'paragraph']],
                                            ['height', ['height']],
                                            ['Insert', ['picture', 'link', 'video', 'table', 'hr']],
                                            ['Misc', ['undo', 'redo']]
                                        ],
                                        focus: true,                // set focus to editable area after initializing summernote
                                        lang: 'zh-CN', // default: 'en-US'
                                        callbacks: {
                                            onImageUpload: function (files, editor, welEditable) {   //图片上传
                                                sendFile(files[0], editor, welEditable);
                                            }
                                        }
                                    });
        $('#summernote').summernote('code', $("#hidden-div").html());

        //    图片上传函数
        function sendFile(file, editor, welEditable) {
            data = new FormData();
            data.append("file", file);
            $.ajax({
                       data: data,
                       type: "POST",
                       url: "/manage/file/saveImage",
                       cache: false,
                       contentType: false,
                       processData: false,
                       success: function (data) {
                           $('#summernote').summernote('editor.insertImage', '${ossImageReadRoot}/'
                                                                             + data.data);
                           //$editor.summernote('insertImage', );
                           // editor.insertImage(welEditable, );
                       }
                   });
        }

        //        数组
//

        //        控制字数
        $("#locationName").bind("input propertychange", function () {
            if ($(this).val().length >= 12) {
                alert("已经是最大字数了哦！");
                $(this).val($(this).val().slice(0, 13));
            }
        });
        $("#locationDetail").bind("input propertychange", function () {
            if ($(this).val().length >= 35) {
                alert("已经是最大字数了哦！");
                $(this).val($(this).val().slice(0, 35));
            }
        });
    });

    $(".btn-return").bind("click", function () {
        location.href = "/manage/topic";
    })

    //取值
    function h() {
        var topic = {};
        topic.sid = $("#topic-sid").val();
        topic.picture = $("#image").attr("src");
        topic.title = $("#topic-title").val();
        topic.description = $("#topic-description").val();
        var minPrice = $("#topic-minPrice").val();
        if(minPrice.toString().split(".")[1]!=null){
            if (minPrice.toString().split(".")[1].length >= 3) {
                alert("只能有2位小数点");
                return;
            }
        }
        topic.minPrice = minPrice * 100;
        var sHTML = $('#summernote').summernote('code');
        var content = {};
        content.content = sHTML;
        topic.content = content;
        if (${topic.id==null}) {
            $.ajax({
                       type: "post",
                       url: "/manage/topic",
                       contentType: "application/json",
                       data: JSON.stringify(topic),
                       success: function (data) {
                           alert(data.msg);
                           location.href = "/manage/topic";
                       }
                   });

        } else {
            topic.id = $("#topicId").val();
            $.ajax({
                       type: "put",
                       url: "/manage/topic",
                       contentType: "application/json",
                       data: JSON.stringify(topic),
                       success: function (data) {
                           alert(data.msg);
                           location.href = "/manage/topic";
                       }
                   });
        }
    }

</script>
</body>
</html>

