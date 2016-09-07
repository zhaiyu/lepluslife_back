<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../commen.jsp" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
    <style>
        html,body,div,li,a{  margin: 0;  padding: 0;}
        .logo{line-height:80px;font-size:30px;color:#fff; letter-spacing:3px;margin-left:50px;float:left;}
        #header{height:80px;line-height:80px;background:#337AB7;width:100%;text-align:center;}
        .navigation{float:right;margin:5px 50px 0 0;color:#fff;font-size:14px;}
        .navigation ul li{height:30px;line-height:30px;text-align:center;float:left;margin-left:15px;list-style: none}
        .navigation ul li a{color:#fff;text-decoration: none}
        .navigation ul li a:hover{color:#32323a;}
    </style>
<div id="header">
    <div class="logo">乐+生活 运营管理后台</div>
    <div class="navigation">
        <ul>
            <li>欢迎您！</li>
            <li><a ><shiro:principal/></a></li>
            <li><a href="/manage/logout">退出</a></li>
        </ul>
    </div>
</div>
