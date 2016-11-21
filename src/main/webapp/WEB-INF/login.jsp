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

        /*new html*/
        body {
            background: url("${resourceUrl}/images/login_background.png") no-repeat;
            background-size: 100% 100%;
        }
        .title {
            width: 100%;
            text-align: center;
            margin-top: 12vw;
        }
        .login_blank {
            width: 448px;
            margin: 0 auto;
        }
        .login_input {
            width: 448px;
            line-height: 58px;
            background-color: rgba(255,255,255,0.14);
            border:2px solid rgba(0,0,0,0.14);
            -webkit-border-radius:10px 10px;
            -moz-border-radius:10px 10px;
            border-radius:10px 10px;
            -webkit-box-shadow:inset 0 0 10px rgba(0,0,0,0.24);
            -moz-box-shadow:inset 0 0 10px rgba(0,0,0,0.24);
            box-shadow:inset 0 0 10px rgba(0,0,0,0.24);
        }
        .login_input > div > p {
            display: inline;
            padding: 0 30px;
            text-align: center;
            font-size: 24px;
            color: #FFFFFF;
            line-height: 58px;
        }
        .login_input > div > input {
            width: 310px;
            height: 45px;
            border:0;
            font-size: 24px;
            color: #FFF !important;
            background-color:transparent !important;
            outline:none;
        }
        input:-webkit-autofill {
            background: none;
            background-color: #FFF !important;
            background-image: none;
            color: #FFF !important;
        }
        .login_button {
            width: 216px;
            height: 58px;
            border:1px solid #ea5413;
            -webkit-border-radius:10px 10px;
            -moz-border-radius:10px 10px;
            border-radius:10px 10px;
            font-size: 24px;
            font-weight: bold;
            background-color: transparent;
            color: #ea5413;
            margin-right: 10px;
        }
        .login_ {
            background-color: #ea5413;
            color: #FFFFFF;
            margin-right: 0;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
</head>

<body>
<%--<h1 class="text-center" style="margin-top: 10vw">乐+生活 运营管理后台</h1>--%>
<div class="title">
    <img src="${resourceUrl}/images/login_title.png" alt="乐+生活 运营管理后台" />
</div>
<div class="container" style="margin-top: 46px">
    <div class="login_blank">
        <form class="form-horizontal" action="/manage/login" method="post">
            <div class="login_input">
                <div>
                    <p>账户</p>
                    <input type="text" id="inputEmail3" name="name" autocomplete="off" />
                </div>
            </div>
            <div class="login_input" style="margin-top: 24px;">
                <div>
                    <p>密码</p>
                    <input type="password" id="inputPassword3" name="password">
                </div>
            </div>
            <div style="margin-top: 40px;">
                <div>
                    <input type="reset" class="login_button" value="重置">
                    <button type="submit" class="login_button login_">登录</button>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>

