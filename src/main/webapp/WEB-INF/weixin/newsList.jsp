<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/5/25
  Time: 下午1:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE>
<style>
    .pagination > li > a.focusClass {
        background-color: #ddd;
    }

    table tr td img {
        width: 80px;
    }

    .table > thead > tr > td, .table > thead > tr > th {
        line-height: 40px;
    }

    .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
        line-height: 80px;
    }

    .active img {
        width: 80px;
        height: 80px;
    }

</style>

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
    <div class="m-right" style="margin-top: 10px">
        <div class="main">


            <h2 style="margin:20px;display: inline"> 发送图文消息</h2>
            <button type="button" class="btn btn-primary btn-return" style="margin:10px;"
                    onclick="goUserPage()">
                返回会员列表
            </button>


            <div class="row">
                <div class="col-md-12">
                    <div class="portlet">
                        <div class="portlet-title">
                            <div class="caption">
                                <p style="margin-left: 50px;font-size: 20px">你正在向 <span
                                        style="color: red;font-size: 30px">${totalElements}</span>
                                    个会员群发消息</p>

                                <p style="margin-left: 50px;font-size: 20px">
                                    如果有会员未关注公众号，或者群发余额为0，将收不到本次群发</p>
                            </div>
                        </div>

                        <div class="portlet-body">
                            <div class="table-container">
                                <div class="form-horizontal">
                                    <div class="form-body">
                                        <div class="table-toolbar">
                                            <div class="row">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class="text-center">选择</th>
                                        <th class="text-center">图文标题</th>
                                        <th class="text-center">图文media_id</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${news.item}" var="item">
                                        <tr class="active">
                                            <td class="text-center">测试</td>
                                            <td class="text-center">
                                                    ${item.content.news_item[0].title}
                                            </td>
                                            <td class="text-center">
                                                    ${item.media_id}
                                            </td>
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
                                                <c:forEach begin="1" end="${pages}"
                                                           varStatus="index">
                                                    <c:if test="${index.count==currentPage}">
                                                        <li><a href="/manage/product"
                                                               class="focusClass">${currentPage}</a>
                                                        </li>
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
                                                            <li>
                                                                <a class="focusClass">${currentPage}</a>
                                                            </li>
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
                                                                class="focusClass">${currentPage}</a>
                                                        </li>
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
                                                                        class="focusClass">${currentPage}</a>
                                                                </li>
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
                                            <li><a onclick="pageChange(${currentPage+1})"
                                                   aria-label="Next"><span
                                                    aria-hidden="true">»</span></a></li>
                                        </c:if>
                                    </ul>
                                </nav>

                            </div>
                        </div>

                        <button class="btn btn-primary" style="margin-left:20px;margin-top: 24px"
                                onclick="sendNews()">
                            确认发送
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>


<!--如果只是做布局的话不需要下面两个引用-->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script>

    var ajaxParams = {};
    jQuery(document).ready(function () {
        window.menuList = null;
        $('input[name=displayOrder]').TouchSpin({
                                                    min: 0,
                                                    max: 1000000000,
                                                    step: 1,
                                                    maxboostedstep: 100,
                                                    prefix: '序号'
                                                });

    });

    //    function editTextRule(id) {
    //        location.href = "/manage/weixin/reply/textEdit/" + id;
    //    }

    function sendNews() {

        if (!confirm('确定发送“' + "测试图文消息" + '”图文消息给 ' + '${totalElements}' + ' 个用户？')) {
            return false;
        }

        $.ajax({
                   type: "post",
                   async: false,
                   url: "/manage/weixin/news/sendNews?mediaId="
                        + "V9tGnEZo9vEqxnbQQltE9NJzRHDuuY1vCe6q_yPlR24 ",
                   contentType: "application/json",
                   data: JSON.stringify(JSON.parse('${leJiaUserCriteria}')),
                   success: function (data) {
                       alert(data.status);
                   }
               });

    }

    function goUserPage() {
        location.href = "/manage/user";
    }

</script>
</body>
</html>

