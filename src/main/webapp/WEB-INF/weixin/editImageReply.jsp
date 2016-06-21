<%--
<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/3/10
  Time: 上午9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
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
                <button type="button" class="btn btn-primary btn-return" style="margin:10px;"
                        onclick="goReplyPage()">
                    返回回复列表
                </button>
                <hr>
                <form class="form-horizontal">

                    <div class="form-group">
                        <label for="replyType" class="col-sm-2 control-label">规则类型</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="replyType" name="replyType">
                                <option value="0">请选择</option>
                                <option value="focusReply">关注公众号自动回复</option>
                                <option value="defaultReply">默认自动回复</option>
                                <option value="keywordReply">关键字自动回复</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="keyword" class="col-sm-2 control-label">关键字</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="keyword"
                                   value="${imageReply.keyword}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="textTitle" class="col-sm-2 control-label">标题</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="textTitle"
                                   value="${imageReply.replyImageText0.textTitle}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="textLink" class="col-sm-2 control-label">链接</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="textLink"
                                   value="${imageReply.replyImageText0.textLink}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="textNote" class="col-sm-2 control-label">描述</label>

                        <div class="col-sm-4">
                            <textarea class="form-control"
                                      id="textNote">${imageReply.replyImageText0.textNote}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="image" class="col-sm-2 control-label">图片</label>

                        <div class="col-sm-4">
                            <div class="thumbnail">
                                <img id="imageUrl" src="${imageReply.replyImageText0.imageUrl}" alt="...">
                            </div>
                            <input type="file" class="form-control" id="image" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-4">
                            <input type="hidden" id="textReplyId" value="${imageReply.id}"/>
                            <input type="hidden" id="imageTextId" value="${imageReply.replyImageText0.id}"/>
                            <input type="button" class="btn btn-primary" id="submit" value="提交"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>


<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    $(function () {
        var replyType = '${imageReply.replyType}';
        if (replyType != null) {
            $('#replyType').val(replyType);
        }
    });

    $(function () {
        $('#image').fileupload({
                                        dataType: 'json',
                                        maxFileSize: 5000000,
                                        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                        add: function (e, data) {
                                            data.submit();
                                        },
                                        done: function (e, data) {
                                            var resp = data.result;
                                            $('#imageUrl').attr('src',
                                                                      '${ossImageReadRoot}/'
                                                                      + resp.data);
                                        }
                                    });

    });

    $("#submit").bind("click", function () {
        var replyType = $('select[name=replyType]').val();
        var keyword = $("#keyword").val();
        var textTitle = $("#textTitle").val();
        var textLink = $("#textLink").val();
        var textNote = $("#textNote").val();
        var imageUrl = $("#imageUrl").attr("src");
        var imageTextId = $("#imageTextId").val();
        var textReplyId = $("#textReplyId").val();


        if (replyType == 0) {
            alert("请选择规则类型");
            return;
        }
        if (keyword == null || keyword == '') {
            alert("请输入关键字");
            return;
        }
        if (textTitle == null || textTitle == '') {
            alert("请输入标题");
            return;
        }
        if (textLink == null || textLink == '') {
            alert("请输入链接");
            return;
        }
        if (textNote == null || textNote == '') {
            alert("请输入描述");
            return;
        }
        if (imageUrl == null || imageUrl == '') {
            alert("请上传图片");
            return;
        }
        var autoReplyRule = {};
        var replyImageText0 = {};
        autoReplyRule.id = textReplyId;
        autoReplyRule.replyType = replyType;
        autoReplyRule.keyword = keyword;
        replyImageText0.id = imageTextId;
        replyImageText0.textTitle = textTitle;
        replyImageText0.textLink = textLink;
        replyImageText0.textNote = textNote;
        replyImageText0.imageUrl = imageUrl;
        autoReplyRule.replyImageText0 = replyImageText0;

        $.ajax({
                   type: "put",
                   url: "/manage/weixin/reply/saveImage",
                   contentType: "application/json",
                   data: JSON.stringify(autoReplyRule),
                   success: function (data) {
                       if (data.status == 200) {
                           alert(data.msg);
                           window.location.href = "/manage/weixin/reply/list";
                       } else {
                           alert(data.msg);
                       }
                   }
               });
    });

    function goReplyPage() {
        location.href = "/manage/weixin/reply/list";
    }

</script>
</body>
</html>


