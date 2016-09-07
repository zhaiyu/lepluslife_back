<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/6
  Time: 下午4:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../commen.jsp" %>
<!DOCTYPE>
<style>

</style>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>乐+生活 后台模板管理系统</title>
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
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
                        <th class="text-center">时间</th>
                        <th class="text-center">状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${expressList}" var="express">
                        <tr class="active">
                            <td class="text-center">${express.time}</td>
                            <td class="text-center">${express.status}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <nav class="pull-right">
                    <ul class="pagination pagination-lg">
                        <c:if test="${currentPage>1}">
                            <li><a aria-label="Previous"
                                   onclick="pageChange(${currentPage-1})"><span
                                    aria-hidden="true">«</span></a></li>
                        </c:if>
                        <c:if test="${pages>1}">
                            <c:if test="${pages<=5}">
                                <c:forEach begin="1" end="${pages}" varStatus="index">
                                    <c:if test="${index.count==currentPage}">
                                        <li><a href="/manage/product"
                                               class="focusClass">${currentPage}</a></li>
                                    </c:if>
                                    <c:if test="${index.count!=currentPage}">
                                        <li>
                                            <a onclick="pageChange(${index.count})">${index.count}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${pages>5}">
                                <c:if test="${currentPage<=3}">
                                    <c:forEach begin="1" end="5" varStatus="index">
                                        <c:if test="${index.count==currentPage}">
                                            <li><a class="focusClass">${currentPage}</a></li>
                                        </c:if>
                                        <c:if test="${index.count!=currentPage}">
                                            <li>
                                                <a onclick="pageChange(${index.count})">${index.count}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${currentPage>3}">
                                    <c:if test="${pages-currentPage>=2}">
                                        <li>
                                            <a onclick="pageChange(${currentPage-2})">${currentPage-2}</a>
                                        </li>
                                        <li>
                                            <a onclick="pageChange(${currentPage-1})">${currentPage-1}</a>
                                        </li>
                                        <li><a
                                                class="focusClass">${currentPage}</a></li>
                                        <li>
                                            <a onclick="pageChange(${currentPage+1})">${currentPage+1}</a>
                                        </li>
                                        <li>
                                            <a onclick="pageChange(${currentPage+2})">${currentPage+2}</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${pages-currentPage<2}">
                                        <c:forEach begin="${pages-5}" end="${pages}"
                                                   varStatus="index">
                                            <c:if test="${index.current==currentPage}">
                                                <li><a
                                                        class="focusClass">${currentPage}</a></li>
                                            </c:if>
                                            <c:if test="${index.current!=currentPage}">
                                                <li>
                                                    <a onclick="pageChange(${index.current})">${index.current}</a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                            </c:if>
                        </c:if>
                        <c:if test="${pages>currentPage}">
                            <li><a onclick="pageChange(${currentPage+1})" aria-label="Next"><span
                                    aria-hidden="true">»</span></a></li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
</body>
</html>

