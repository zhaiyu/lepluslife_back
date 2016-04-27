<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/12
  Time: 下午2:53
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
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <style>
        table tr td img {
            width: 80px;
            height: 80px;
        }

        .table > thead > tr > td, .table > thead > tr > th {
            line-height: 40px;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
            line-height: 60px;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
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
            <div class="container-fluid" style="padding-top: 20px">
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#lunbotu" data-toggle="tab">专题管理</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="lunbotu">
                        <button type="button" class="btn btn-primary createTopic"
                                style="margin:10px 0;" onclick="goCreateTopicPage()">新增专题
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th class="text-center">专题序号</th>
                                <th class="text-center">海报</th>
                                <th class="text-center">标题</th>
                                <th class="text-center">描述语</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${topics}" var="topic">
                                <tr class="active">
                                    <td class="text-center">${topic.sid}</td>
                                    <td class="text-center"><img src="${topic.picture}"></td>
                                    <td class="text-center">${topic.title}</td>
                                    <td class="text-center">${topic.description}</td>
                                    <td class="text-center">
                                        <button type="button"
                                                class="btn btn-default createLocation" onclick="editTopic(${topic.id})">编辑
                                        </button>
                                        <button type="button" class="btn btn-default deleteWarn"
                                                data-target="#deleteWarn" onclick="deleteTopic(${topic.id})">删除
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>


<!--删除提示框-->
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <h4>确定要删除吗？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="topic-confirm">确认</button>
            </div>
        </div>
    </div>
</div>

<!--如果只是做布局的话不需要下面两个引用-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>

    $(".createTopic").click(function () {
        window.location.href ='/manage/topic/create';
    });

    function editTopic(id){
        location.href = "/manage/topic/edit/"+id;
    }

    function deleteTopic(id){
        $("#topic-confirm").bind("click", function () {
            $.ajax({
                       type: "delete",
                       url: "/manage/topic/"+id,
                       success: function (data) {
                           alert(data.msg);
                           setTimeout(function () {
                               location.reload(true);
                           }, 0);
                       }
                   });
        });
        $("#deleteWarn").modal("show");
    }
</script>
</body>
</html>

