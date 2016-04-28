<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/6
  Time: 下午2:45
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
        .pagination > li > a.focusClass {
            background-color: #ddd;
        }
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
                    <li><a href="#lunbotu" data-toggle="tab">商户管理</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="lunbotu">
                        <button type="button" class="btn btn-primary createLocation"
                                style="margin:10px 0;" onclick="goMerchantCreatePage()">新增商户
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th class="text-center">商品序号</th>
                                <th class="text-center">商户名称</th>
                                <th class="text-center">商户图片</th>
                                <th class="text-center">商户地址</th>
                                <th class="text-center">返利百分比</th>
                                <th class="text-center">折扣</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${merchants}" var="merchant">
                                <tr class="active">
                                    <td class="text-center">${merchant.sid}</td>
                                    <td class="text-center">${merchant.name}</td>
                                    <td class="text-center"><img src="${merchant.picture}"></td>
                                    <td class="text-center">${merchant.location}</td>
                                    <td class="text-center">${merchant.rebate}%</td>
                                    <td class="text-center">${merchant.discount}</td>
                                    <td class="text-center">
                                        <button type="button" class="btn btn-default"
                                                onclick="editMerchant(${merchant.id})">编辑
                                        </button>
                                        <button type="button" class="btn btn-default deleteWarn"
                                                data-target="#deleteWarn"
                                                onclick="deleteMerchant(${merchant.id})">删除
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <nav class="pull-right">
                    <ul class="pagination pagination-lg">
                        <li><a href="#" aria-label="Previous"><span aria-hidden="true" onclick="goPage(${currentPage-1})">«</span></a>
                        </li>
                        <c:if test="${pages<=5}">
                            <c:forEach begin="1" end="${pages}" varStatus="index">
                                <c:if test="${index.count==currentPage}">
                                    <li><a href="/manage/merchant"
                                           class="focusClass">${currentPage}</a></li>
                                </c:if>
                                <c:if test="${index.count!=currentPage}">
                                    <li>
                                        <a href="/manage/merchant?page=${index.count}">${index.count}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${pages>5}">
                            <c:if test="${currentPage<=3}">
                                <c:forEach begin="1" end="5" varStatus="index">
                                    <c:if test="${index.count==currentPage}">
                                        <li><a href="/manage/merchant"
                                               class="focusClass">${currentPage}</a></li>
                                    </c:if>
                                    <c:if test="${index.count!=currentPage}">
                                        <li>
                                            <a href="/manage/merchant?page=${index.count}">${index.count}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${currentPage>3}">
                                <c:if test="${pages-currentPage>=2}">
                                    <li>
                                        <a href="/manage/merchant?page=${currentPage-2}">${currentPage-2}</a>
                                    </li>
                                    <li>
                                        <a href="/manage/merchant?page=${currentPage-1}">${currentPage-1}</a>
                                    </li>
                                    <li><a href="/manage/merchant?page=${currentPage}"
                                           class="focusClass">${currentPage}</a></li>
                                    <li>
                                        <a href="/manage/merchant?page=${currentPage+1}">${currentPage+1}</a>
                                    </li>
                                    <li>
                                        <a href="/manage/merchant?page=${currentPage+2}">${currentPage+2}</a>
                                    </li>
                                </c:if>
                                <c:if test="${pages-currentPage<2}">
                                    <c:forEach begin="${pages-5}" end="${pages}" varStatus="index">
                                        <c:if test="${index.current==currentPage}">
                                            <li><a href="/manage/merchant?page=${currentPage}"
                                                   class="focusClass">${currentPage}</a></li>
                                        </c:if>
                                        <c:if test="${index.current!=currentPage}">
                                            <li>
                                                <a href="/manage/merchant?page=${index.current}">${index.current}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </c:if>
                        </c:if>
                        <li><a href="#" aria-label="Next"><span aria-hidden="true" onclick="goPage(${currentPage+1})">»</span></a></li>
                    </ul>
                </nav>
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
                <h4>确定要删除商户吗？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="merchant-confirm">确认</button>
                <input type="hidden" value="${pages}" id="pages" />
            </div>
        </div>
    </div>
</div>


<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    function goPage(page){
        if(page==0){
            location.href="/manage/merchant";
        }
        if(page>=$("#pages").val()){
            location.href="/manage/merchant?page="+$("#pages").val();
        }
        location.href = "/manage/merchant?page="+page;
    }

    function goMerchantCreatePage(){
        location.href = "/manage/merchant/edit";
    }

    function editMerchant(id){
        location.href = "/manage/merchant/edit/"+id;
    }
    function deleteMerchant(id){
        $("#merchant-confirm").bind("click", function () {
            $.ajax({
                       type: "delete",
                       url: "/manage/merchant/"+id,
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

