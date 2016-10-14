<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/5/6
  Time: 上午10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link rel="stylesheet" href="${resourceUrl}/css/jqpagination.css"/>
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <style>
        thead th, tbody td {
            text-align: center;
        }

        #myTab {
            margin-bottom: 10px;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.jqpagination.min.js"></script>
    <%--<script type="text/javascript" src="${resourceUrl}/js/jquery.jqpagination.js"></script>--%>
</head>

<body>
<input type="hidden" id="detailsID" value="${messageDetailsMap.messageDetailsMap}"> </input>
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
                <div class="row" style="margin-top: 30px">
                </div>
            </div>
            <div class="form-group col-md-3">
                <button class="btn btn-primary" style="margin-top: 24px"
                        onclick="goFinancialSettlementPage()">返回财务结算
                </button>
            </div>
        </div>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="tab1">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr class="active">
                        <th>订单编号</th>
                        <th>交易完成时间</th>
                        <th>商户名称</th>
                        <th>消费者信息</th>
                        <th>订单金额</th>
                        <th>红包使用</th>
                        <th>支付方式</th>
                        <th>实际支付</th>
                        <th>佣金(手续费)</th>
                        <th>商户应入账</th>
                        <th>第三方手续费</th>
                        <th>发放红包</th>
                        <th>分润金额</th>
                        <th>发放积分</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody id="orderContent">
                    </tbody>
                </table>
            </div>
            <div class="pagination">
                <a href="#" class="first" data-action="first">&laquo;</a>
                <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                <input id="page" type="text" readonly="readonly" data-max-page="1"/>
                <a href="#" class="next" data-action="next">&rsaquo;</a>
                <a href="#" class="last" data-action="last">&raquo;</a>
            </div>
            <button class="btn btn-primary pull-right" style="margin-top: 5px"
                    onclick="exportExcel(olOrderCriteria)">导出excel
            </button>
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
            </div>
            <div class="modal-body">
                <h4>操作确定</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="paid-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script>
    var olOrderCriteria = {};
    var flag = true;
    var orderContent = document.getElementById("orderContent");
    $(function () {
        // tab切换
        $('#myTab li:eq(0) a').tab('show');
        // 提示框
        $(".deleteWarn").click(function () {
            $("#deleteWarn").modal("toggle");
        });
        $(".createWarn").click(function () {
            $("#createWarn").modal("toggle");
        });
        olOrderCriteria.offset = 1;
        getOffLineOrderByAjax(olOrderCriteria);
    });

    function getOffLineOrderByAjax(olOrderCriteria) {
        orderContent.innerHTML = "";
        var messageDetailsStr=$("#detailsID").val();
        $.ajax({
            type: "post",
            url: "/manage/offLineOrder/messageDetails?messageDetailsStr="+messageDetailsStr,
            async: false,
            data: JSON.stringify(olOrderCriteria),
            contentType: "application/json",
            success: function (data) {
                var page = data.data;
                var content = page.content;
                var totalPage = page.totalPages;
                if (totalPage == 0) {
                    totalPage = 1;
                }
                var orderContent = document.getElementById("orderContent");
                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr><td>' + content[i].orderSid + '</td>';
                    if(content[i].completeDate==null){
                        contentStr +=
                                '<td><span>未完成的订单</span></td>';
                    }else{
                        contentStr +=
                                '<td><span>'
                                + new Date(content[i].completeDate).format('yyyy-MM-dd HH:mm:ss')
                                + '</span></td>';
                    }

                    contentStr +=
                            '<td><span>' + content[i].merchant.name + '</span><br><span>('
                            + content[i].merchant.merchantSid + ')</span></td>'
                    if (content[i].leJiaUser.phoneNumber != null) {
                        contentStr +=
                                '<td><span>' + content[i].leJiaUser.phoneNumber
                                + '</span><br><span>('
                                + content[i].leJiaUser.userSid + ')</span></td>'
                    } else {
                        contentStr +=
                                '<td><span>未绑定手机号</span><br><span>('
                                + content[i].leJiaUser.userSid + ')</span></td>'
                    }
                    contentStr += '<td>' + content[i].totalPrice / 100 + '</td>'
                    contentStr += '<td>' + content[i].trueScore / 100 + '</td>'
                    contentStr += '<td>' + content[i].payWay.payWay + '</td>'
                    contentStr += '<td>' + content[i].truePay / 100 + '</td>'
                    contentStr += '<td>' + content[i].ljCommission / 100 + '</td>'
                    var payToMerchant = content[i].transferMoney/100;
                    contentStr +=
                            '<td>' + payToMerchant + '</td>'
                    contentStr += '<td>' + content[i].wxCommission / 100 + '</td>'
                    contentStr += '<td>' + content[i].rebate / 100 + '</td>';
                    var share = 0;
                    if(content[i].rebateWay!=1){
                        share = 0;
                    }else{
                        share = (content[i].ljCommission-content[i].wxCommission-content[i].rebate)/100;
                    }

                    contentStr +=
                            '<td>' + share + '</td>'
                    contentStr += '<td>' + content[i].scoreB + '</td>'
                    if (content[i].state == 0) {
                        contentStr += '<td>未付款</td>';
                        contentStr +=
                                '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                                + '"><button class="btn btn-primary changeOrderToPaid")">设为已支付</button></td></tr>';
                    }
                    if (content[i].state == 1) {
                        contentStr += '<td>已支付</td></tr>'
                    }
                    orderContent.innerHTML += contentStr;

                }
                $(".changeOrderToPaid").each(function (i) {
                    $(".changeOrderToPaid").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        $("#paid-confirm").bind("click", function () {
                            $.ajax({
                                type: "get",
                                url: "/manage/offLineOrder/" + id,
                                contentType: "application/json",
                                success: function (data) {
                                    alert(data.msg);
                                    getOffLineOrderByAjax(olOrderCriteria);
                                }
                            });
                        });
                        $("#deleteWarn").modal("show");
                    });
                });
                if(flag){
                    initPage(olOrderCriteria.offset, totalPage);
                    flag = false;
                }
            }
        });
    }
    function initPage(page, totalPage) {
        $('.pagination').jqPagination({
            current_page: page, //设置当前页 默认为1
            max_page: totalPage, //设置最大页 默认为1
            page_string: '当前第{current_page}页,共{max_page}页',
            paged: function (page) {
                olOrderCriteria.offset = page;
                getOffLineOrderByAjax(olOrderCriteria);
            }
        });
    }
    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        var week = {
            "0": "\u65e5",
            "1": "\u4e00",
            "2": "\u4e8c",
            "3": "\u4e09",
            "4": "\u56db",
            "5": "\u4e94",
            "6": "\u516d"
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        if (/(E+)/.test(fmt)) {
            fmt =
                    fmt.replace(RegExp.$1,
                            ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468")
                                    : "") + week[this.getDay() + ""]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt =
                        fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr((""
                        + o[k]).length)));
            }
        }
        return fmt;
    }

    function goFinancialSettlementPage() {
        location.href = "/manage/financial";
    }

    function searchOrderByState(state) {
        olOrderCriteria.offset = 1;
        if (state != null) {
            olOrderCriteria.state = state;
        } else {
            olOrderCriteria.state = null;
        }
        flag = true;
        getOffLineOrderByAjax(olOrderCriteria);
    }
    function exportExcel(olOrderCriteria) {
        var d=$("#detailsID").val();

        location.href="/manage/offLineOrderDetails/exportDetails?messageDetails="+d+"&&"+"olOrderCriteria="+olOrderCriteria;
    }
</script>
</body>
</html>

