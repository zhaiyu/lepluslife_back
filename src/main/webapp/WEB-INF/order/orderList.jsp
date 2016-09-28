<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/6
  Time: 下午4:42
  待删除
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../commen.jsp" %>
<!DOCTYPE>
<style>
    .pagination > li > a.focusClass {
        background-color: #ddd;
    }
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
                <ul class="nav nav-tabs" style="margin-bottom: 10px">
                    <li class="show"><a data-toggle="tab" onclick="stateChange()">所有</a></li>
                    <li class="show"><a data-toggle="tab" onclick="stateChange(0)">未付款</a></li>
                    <li class="show"><a data-toggle="tab" onclick="stateChange(1)">未发货</a></li>
                    <li class="show"><a data-toggle="tab" onclick="stateChange(2)">已发货</a></li>
                    <li class="show"><a data-toggle="tab" onclick="stateChange(3)">已完成</a></li>
                    <li class="show"><a data-toggle="tab" onclick="stateChange(4)">已取消</a></li>
                </ul>
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="text-center">订单编号</th>
                        <th class="text-center">商品信息</th>
                        <th class="text-center">买家信息</th>
                        <th class="text-center">总价</th>
                        <th class="text-center">实际支付</th>
                        <th class="text-center">红包返还</th>
                        <th class="text-center">下单时间</th>
                        <th class="text-center">状态</th>
                        <th class="text-center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orders}" var="order">
                        <tr class="active">
                            <td class="text-center">${order.orderSid}</td>
                            <td class="text-center">
                                <c:forEach items="${order.orderDetails}" var="orderDetail">
                                    ${orderDetail.product.name}(${orderDetail.productSpec.specDetail})X${orderDetail.productNumber}<br>
                                </c:forEach>
                            </td>
                            <td class="text-center">${order.address.name}${order.address.phoneNumber}${order.address.province}${order.address.city}${order.address.county}${order.address.location}</td>
                            <td class="text-center">${order.totalPrice/100}+${order.totalScore}积分</td>
                            <td class="text-center">${order.truePrice/100}+${order.trueScore}积分</td>
                            <td class="text-center"><fmt:formatNumber type="number"
                                                                      value="${order.truePrice*12/10000}"
                                                                      maxFractionDigits="2"/></td>
                            <td class="text-center"><fmt:formatDate value="${order.createDate}"
                                                                    type="both"/></td>
                            <td class="text-center">
                                <c:if test="${order.state==0}">待付款</c:if>
                                <c:if test="${order.state==1 && order.transmitWay == 1}">已付款(线下自提)</c:if>
                                <c:if test="${order.state==1 && order.transmitWay != 1}">已付款未发货</c:if>
                                <c:if test="${order.state==2}">已发货</c:if>
                                <c:if test="${order.state==3}">订单完成</c:if>
                                <c:if test="${order.state==4}">订单已取消</c:if>
                            </td>
                            <td class="text-center">
                                <c:if test="${order.state==0}">
                                    <button type="button" class="btn btn-default deleteWarn"
                                            data-target="#deleteWarn"
                                            onclick="cancleOrder(${order.id})">取消订单
                                    </button>
                                </c:if>
                                <c:if test="${order.state==1}">
                                    <c:if test="${order.transmitWay == 1}">
                                        <button type="button" class="btn confirmWarn"
                                                onclick="finishOrder('${order.id}')">
                                            确认完成
                                        </button>
                                    </c:if>
                                    <c:if test="${order.transmitWay != 1}">
                                        <button type="button" class="btn confirmWarn"
                                                onclick="delivery('${order.id}','${order.expressCompany}','${order.expressNumber}',0)">
                                            确认发货
                                        </button>
                                    </c:if>
                                    <button type="button" class="btn btn-default deleteWarn"
                                            data-target="#deleteWarn"
                                            onclick="cancleOrder(${order.id})">取消订单
                                    </button>
                                </c:if>
                                <c:if test="${order.state==2}">
                                    <button type="button" class="btn confirmWarn"
                                            onclick="showExpress(${order.id})">查看物流信息
                                    </button>
                                    <button type="button" class="btn btn-default deleteWarn"
                                            data-target="#deleteWarn"
                                            onclick="delivery('${order.id}','${order.expressCompany}','${order.expressNumber}',1)">
                                        修改物流信息
                                    </button>
                                </c:if>
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
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<!--确认发货提示框-->
<div class="modal" id="confirmWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">确认发货</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">

                    <div class="form-group">
                        <label for="expressCompany" class="col-sm-2 control-label">物流公司名称</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="expressCompany"
                                   placeholder="请输入公司名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="expressNumber" class="col-sm-2 control-label">订单物流单号</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="expressNumber"
                                   placeholder="请输入物流单号">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="delivery-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>

<!--取消订单提示框-->
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">取消订单</h4>
            </div>
            <div class="modal-body">
                <p>确认取消订单吗?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="cancle-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>

<!--取消订单提示框-->
<div class="modal" id="finishWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">确认完成订单</h4>
            </div>
            <div class="modal-body">
                <p>将该订单设为已完成?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="finish-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>

<!--如果只是做布局的话不需要下面两个引用-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>

    $(function () {
        if (${orderCriteria.state!=null}) {
            $(".show").eq(${orderCriteria.state+1}).addClass("active");
        } else {
            $(".show").eq(0).addClass("active");
        }
    });

    function cancleOrder(id) {
        $("#cancle-confirm").bind("click", function () {
            $.ajax({
                       type: "get",
                       url: "/manage/orderCancle/" + id,
                       contentType: "application/json",
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

    function finishOrder(id) {
        $("#finish-confirm").bind("click", function () {
            $.ajax({
                       type: "get",
                       url: "/manage/finishOrder/" + id,
                       contentType: "application/json",
                       success: function (data) {
                           alert(data.msg);
                           setTimeout(function () {
                               location.reload(true);
                           }, 0);
                       }
                   });
        });
        $("#finishWarn").modal("show");
    }

    function delivery(id, expressCompany, expressNumber, state) { //state=1 修改物流信息  =0确认发货
        $("#expressCompany").val(expressCompany);
        $("#expressNumber").val(expressNumber);
        $("#delivery-confirm").bind("click", function () {
            var onLineOrder = {};
            onLineOrder.id = id;
            onLineOrder.expressCompany = $("#expressCompany").val();
            onLineOrder.expressNumber = $("#expressNumber").val();
            onLineOrder.state = state;
            $.ajax({
                       type: "post",
                       url: "/manage/order/delivery",
                       contentType: "application/json",
                       data: JSON.stringify(onLineOrder),
                       success: function (data) {
                           alert(data.msg);
                           setTimeout(function () {
                               location.reload(true);
                           }, 0);
                       }
                   });
        });
        $("#confirmWarn").modal("show");
    }

    function stateChange(state) {
        if (state != null) {
            location.href = "/manage/order?state=" + state;
        } else {
            location.href = "/manage/order";
        }
    }

    function showExpress(id) {//查看物流信息
        if (id != null) {
            location.href = "/manage/showExpress/" + id;
        }
    }

    function pageChange(page) {
        location.href =
        "/manage/order?state=${orderCriteria.state}" + "&page=" + page;
    }
</script>
</body>
</html>

