<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/5/11
  Time: 上午11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
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
        table tr td img, form img {
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
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
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
            <div class="container-fluid">
                <button type="button" class="btn btn-primary btn-create" style="margin:10px;">
                    返回商户列表
                </button>
                <h2>${merchant.name}</h2>
                <hr>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#nav1" data-toggle="tab">账号密码</a>
                    </li>
                    <li><a href="#nav2" data-toggle="tab"
                            >绑定微信号</a></li>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active clearfix" id="nav1">
                        <button type="button" class="btn btn-primary createWarn"
                                style="margin:10px;"
                                onclick="createUser()">
                            新建账户
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th class="text-center">账号类型</th>
                                <th class="text-center">用户名</th>
                                <th class="text-center">密码</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${merchantUsers}" var="merchantUser">
                                <tr>
                                    <c:if test="${merchantUser.type==0}">
                                        <td class="text-center">普通账号</td>
                                    </c:if>
                                    <c:if test="${merchantUser.type==1}">
                                        <td class="text-center">店主账号</td>
                                    </c:if>
                                    <c:if test="${merchantUser.name==null}">
                                        <td class="text-center">--</td>
                                        <td class="text-center">--</td>
                                    </c:if>
                                    <c:if test="${merchantUser.name!=null}">
                                        <td class="text-center">${merchantUser.name}</td>
                                        <td class="text-center">${merchantUser.password}</td>
                                    </c:if>
                                    <c:if test="${merchantUser.type==0}">
                                        <td class="text-center">
                                            <button type="button" class="btn btn-default createWarn"
                                                    onclick="editUser(${merchantUser.id})">编辑
                                            </button>
                                            <button type="button" class="btn btn-default deleteWarn"
                                                    onclick="deleteUser(${merchantUser.id})"
                                                    data-target="#deleteWarn">删除
                                            </button>
                                        </td>
                                    </c:if>
                                    <c:if test="${merchantUser.type==1}">
                                        <td class="text-center">
                                            <button type="button" class="btn btn-default createWarn"
                                                    onclick="editUser(${merchantUser.id})">编辑
                                            </button>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade in active clearfix" id="nav2">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>账户类型</th>
                                <th>账户名</th>
                                <th>微信头像</th>
                                <th>昵称</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${merchantWeiXinUsers}" var="merchantWeiXinUser">
                                <tr>
                                    <c:if test="${merchantWeiXinUser.merchantUser.type==0}">
                                        <td class="text-center">普通账号</td>
                                    </c:if>
                                    <c:if test="${merchantWeiXinUser.merchantUser.type==1}">
                                        <td class="text-center">店主账号</td>
                                    </c:if>
                                    <td class="text-center">${merchantWeiXinUser.merchantUser.name}</td>
                                    <td class="text-center"><img
                                            src="${merchantWeiXinUser.headImageUrl}"></td>
                                    <td class="text-center">${merchantWeiXinUser.nickname}</td>
                                    <td>
                                        <button type="button" class="btn btn-default deleteWarn"
                                                onclick="unbindMerchantWeiXinUser(${merchantWeiXinUser.id})"
                                                data-target="#deleteWarn">解除绑定
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
                <h4 class="modal-title">确定操作吗?</h4>
            </div>
            <div class="modal-body">
                <h4 id="merchantUserName"></h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="delete-merchantUser">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--添加提示框-->
<div class="modal" id="createWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">账户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="account-name" class="col-sm-2 control-label">用户名</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="account-name"
                                   placeholder="请输入用户名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="account-password" class="col-sm-2 control-label">密码</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="account-password"
                                   placeholder="请输入密码">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="confirm-merchantUser">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    $(function () {
//        tab切换
        $('#myTab li:eq(0) a').tab('show');
//        提示框
//        $(".deleteWarn").click(function () {
//            $("#deleteWarn").modal("toggle");
//        });
//        $(".createWarn").click(function () {
//            $("#createWarn").modal("toggle");
//        });
    })

    function editUser(id) {
        $.ajax({
                   type: "post",
                   async: false,
                   url: "/manage/merchant/user/" + id,
                   contentType: "application/json",
                   success: function (data) {
                       $("#account-name").val(data.data.name);
                       $("#account-password").val(data.data.password);
                   }
               });
        $("#confirm-merchantUser").bind("click", function () {
            var merchantUser = {};
            merchantUser.id = id;
            merchantUser.name = $("#account-name").val();
            merchantUser.password = $("#account-password").val();
            var merchant = {};
            merchant.id = ${merchant.id};
            merchantUser.merchant = merchant;
            $.ajax({
                       type: "put",
                       url: "/manage/merchant/user",
                       data: JSON.stringify(merchantUser),
                       contentType: "application/json",
                       success: function (data) {
                           $("#confirm-merchantUser").unbind("click");
                           alert(data.data);
                           location.reload(true);
                       }
                   });
        });
        $("#createWarn").modal("show");

    }

    function deleteUser(id) {
        $.ajax({
                   type: "post",
                   async: false,
                   url: "/manage/merchant/user/" + id,
                   contentType: "application/json",
                   success: function (data) {
                       $("#merchantUserName").html("确定删除用户:" + data.data.name + "吗");
                       $("#account-password").val(data.data.password);
                   }
               });
        $("#delete-merchantUser").bind("click", function () {
            $.ajax({
                       type: "delete",
                       url: "/manage/merchant/user/" + id,
                       contentType: "application/json",
                       success: function (data) {
                           alert(data.data);
                           $("#confirm-merchantUser").unbind("click");
                           location.reload(true);
                       }
                   });
        });
        $("#deleteWarn").modal("show");
    }
    function createUser() {
        $("#account-name").val("");
        $("#account-password").val("");
        $("#confirm-merchantUser").bind("click", function () {
            var merchantUser = {};
            merchantUser.name = $("#account-name").val();
            merchantUser.password = $("#account-password").val();
            var merchant = {};
            merchant.id = ${merchant.id};
            merchantUser.merchant = merchant;
            $.ajax({
                       type: "post",
                       url: "/manage/merchant/user",
                       data: JSON.stringify(merchantUser),
                       contentType: "application/json",
                       success: function (data) {
                           $("#confirm-merchantUser").unbind("click");
                           alert(data.data);
                           location.reload(true);
                       }
                   });
        });
        $("#createWarn").modal("show");
    }
    function unbindMerchantWeiXinUser(id) {
        $("#merchantUserName").html("确定解除绑定吗");
        $("#delete-merchantUser").bind("click", function () {
            $.ajax({
                       type: "post",
                       url: "/manage/merchant/weiXinUser/" + id,
                       contentType: "application/json",
                       success: function (data) {
                           alert(data.data);
                           $("#delete-merchantUser").unbind("click");
                           location.reload(true);
                       }
                   });
        });

        $("#deleteWarn").modal("show");
    }
</script>
</body>
</html>

