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
    <div class="m-right">
        <div class="main">

            <h3 class="page-title">

            </h3>

            <h3 style="margin:20px;"> 微信回复规则</h3>

            <div class="row">
                <div class="col-md-12">
                    <div class="portlet">
                        <div class="portlet-title">
                            <div class="caption">

                            </div>
                        </div>

                        <div class="portlet-body">
                            <div class="table-container">
                                <div class="form-horizontal">
                                    <div class="form-body">
                                        <div class="table-toolbar">
                                            <div class="row">
                                                <div class="col-md-2">

                                                </div>

                                                <div class="col-md-3 col-md-offset-9">
                                                    <button type="button" class="btn btn-primary "
                                                            style="margin:10px;"
                                                            id="addTextRule">新增文字回复规则
                                                    </button>
                                                    <button type="button" class="btn btn-primary "
                                                            style="margin:10px;"
                                                            id="addImageRule">新增图文回复规则
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class="text-center">关键字</th>
                                        <th class="text-center">规则类型</th>
                                        <th class="text-center">回复类型</th>
                                        <th class="text-center">回复内容</th>
                                        <th class="text-center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${replyRuleList}" var="replyRule">
                                        <tr class="active">
                                            <td class="text-center">${replyRule.keyword}</td>
                                            <td class="text-center">
                                                <c:if test="${replyRule.replyType == 'focusReply'}">
                                                    关注公众号自动回复
                                                </c:if>
                                                <c:if test="${replyRule.replyType == 'defaultReply'}">
                                                    默认自动回复
                                                </c:if>
                                                <c:if test="${replyRule.replyType == 'keywordReply'}">
                                                    关键字自动回复
                                                </c:if>
                                            </td>
                                            <td class="text-center">
                                                <c:if test="${replyRule.replyText == null || replyRule.replyText == ''}">
                                                    图文回复
                                                </c:if>
                                                <c:if test="${replyRule.replyText != null && replyRule.replyText != ''}">
                                                    文字回复
                                                </c:if>
                                            </td>
                                            <td class="text-center">
                                                <c:if test="${replyRule.replyText != null && replyRule.replyText != ''}">
                                                    ${replyRule.replyText}
                                                </c:if>
                                                <c:if test="${replyRule.replyText == null || replyRule.replyText == ''}">
                                                    <img src="${replyRule.replyImageText0.imageUrl}"/>${replyRule.replyImageText0.textTitle}
                                                </c:if>
                                            </td>

                                            <td class="text-center">
                                                <c:if test="${replyRule.replyText != null && replyRule.replyText != ''}">
                                                    <button type="button" class="btn btn-default"
                                                            onclick="editTextRule(${replyRule.id})">
                                                        编辑
                                                    </button>
                                                </c:if>
                                                <c:if test="${replyRule.replyText == null || replyRule.replyText == ''}">
                                                    <button type="button" class="btn btn-default"
                                                            onclick="editImageRule(${replyRule.id})">
                                                        编辑
                                                    </button>
                                                </c:if>

                                                <button type="button" class="btn btn-default"
                                                        onclick="deleteAutoRule(${replyRule.id})">
                                                    删除
                                                </button>
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

        $('#addTextRule').on('click', function () {
            location.href = "/manage/weixin/reply/textEdit/0";
        });
        $('#addImageRule').on('click', function () {
            location.href = "/manage/weixin/reply/imageEdit/0";
        });

    });

    function editTextRule(id) {
        location.href = "/manage/weixin/reply/textEdit/" + id;
    }
    function editImageRule(id) {
        location.href = "/manage/weixin/reply/imageEdit/" + id;
    }

    function deleteAutoRule(ruleId) {

        if (!confirm('确定删除该回复规则？')) {
            return false;
        }
        if (ruleId) {
            $.ajax({
                       type: "delete",
                       url: "/manage/weixin/reply/deleteAutoReply/" + ruleId,
                       success: function (data) {
                           if (data.status == 200) {
                               alert(data.msg);
                               setTimeout(function () {
                                   location.reload(true);
                               }, 100);
                           } else {
                               alert(data.msg);
                           }
                       }
                   });
        }
    }

    function pageChange(page) {
        location.href =
        "/manage/weixin/reply/list?page=" + page;
    }


</script>
</body>
</html>

