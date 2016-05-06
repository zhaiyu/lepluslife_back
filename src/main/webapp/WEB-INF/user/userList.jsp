<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/13
  Time: 下午6:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        .pagination > li > a.focusClass {
            background-color: #ddd;
        }
        table tr td img {
            width: 60px;
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
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="text-center">会员ID</th>
                        <th class="text-center">头像</th>
                        <th class="text-center">昵称</th>
                        <th class="text-center">注册来源</th>
                        <th class="text-center">绑定商户</th>
                        <th class="text-center">手机号</th>
                        <th class="text-center">注册时间</th>
                        <th class="text-center">红包余额</th>
                        <th class="text-center">累计红包</th>
                        <th class="text-center">积分余额</th>
                        <th class="text-center">累计积分</th>
                        <th class="text-center">线上消费次数</th>
                        <th class="text-center">线下消费次数</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr class="active">
                            <td class="text-center">${user.userSid}</td>
                            <td class="text-center"><img src="${user.headImageUrl}" alt="..."></td>
                            <td class="text-center">${user.nickname}</td>
                            <td class="text-center"><c:if
                                    test="${user.registerOrigin.originType==0}">微信</c:if>
                                <c:if test="${user.registerOrigin.originType==1}">app</c:if>
                                <c:if test="${user.registerOrigin.originType==2}">${user.registerOrigin.merchant.name}</c:if>
                            </td>
                            <td class="text-center"></td>
                            <td class="text-center">${user.phoneNumber}</td>
                            <td class="text-center"><fmt:formatDate
                                    value="${user.createDate}" type="both"/></td>
                            <td class="text-center">${user.scoreA}</td>
                            <td class="text-center">${user.totalScoreA}</td>
                            <td class="text-center">${user.scoreB}</td>
                            <td class="text-center">${user.totalScoreB}</td>
                            <td class="text-center">${user.onLineCount}</td>
                            <td class="text-center">0</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <nav class="pull-right">
                    <ul class="pagination pagination-lg">
                        <li><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a>
                        </li>
                        <c:if test="${pages<=5}">
                            <c:forEach begin="1" end="${pages}" varStatus="index">
                                <c:if test="${index.count==currentPage}">
                                    <li><a href="/manage/user"
                                           class="focusClass">${currentPage}</a></li>
                                </c:if>
                                <c:if test="${index.count!=currentPage}">
                                    <li>
                                        <a href="/manage/user?page=${index.count}">${index.count}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${pages>5}">
                            <c:if test="${currentPage<=3}">
                                <c:forEach begin="1" end="5" varStatus="index">
                                    <c:if test="${index.count==currentPage}">
                                        <li><a href="/manage/user"
                                               class="focusClass">${currentPage}</a></li>
                                    </c:if>
                                    <c:if test="${index.count!=currentPage}">
                                        <li>
                                            <a href="/manage/user?page=${index.count}">${index.count}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${currentPage>3}">
                                <c:if test="${pages-currentPage>=2}">
                                    <li>
                                        <a href="/manage/user?page=${currentPage-2}">${currentPage-2}</a>
                                    </li>
                                    <li>
                                        <a href="/manage/user?page=${currentPage-1}">${currentPage-1}</a>
                                    </li>
                                    <li><a href="/manage/user?page=${currentPage}"
                                           class="focusClass">${currentPage}</a></li>
                                    <li>
                                        <a href="/manage/user?page=${currentPage+1}">${currentPage+1}</a>
                                    </li>
                                    <li>
                                        <a href="/manage/user?page=${currentPage+2}">${currentPage+2}</a>
                                    </li>
                                </c:if>
                                <c:if test="${pages-currentPage<2}">
                                    <c:forEach begin="${pages-5}" end="${pages}" varStatus="index">
                                        <c:if test="${index.current==currentPage}">
                                            <li><a href="/manage/user?page=${currentPage}"
                                                   class="focusClass">${currentPage}</a></li>
                                        </c:if>
                                        <c:if test="${index.current!=currentPage}">
                                            <li>
                                                <a href="/manage/user?page=${index.current}">${index.current}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </c:if>
                        </c:if>
                        <li><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
</body>
</html>

