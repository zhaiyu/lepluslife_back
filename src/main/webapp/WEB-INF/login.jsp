<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/3/31
  Time: 下午5:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/commen.jsp" %>
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
        .main .thumbnail {
            padding: 50px 0;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
</head>

<body>
<h1 class="text-center" style="margin-top: 10vw">乐+生活 运营管理后台</h1>
<div class="container" style="margin-top: 6vw">
    <div class="row">
        <div class="col-xs-4 col-xs-push-4">
            <form class="form-horizontal"
                  style="border: solid 1px #a8a8a8;border-radius: 8px;padding: 40px 20px" action="/manage/login" method="post">
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">账户</label>

                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputEmail3" name="name">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>

                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="inputPassword3" name="password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="reset" class="btn" value="重置">
                        <button type="submit" class="btn btn-primary">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>
<div id="bottomIframe">
    <%@include file="common/bottom.jsp" %>
</div>

</body>
</html>


