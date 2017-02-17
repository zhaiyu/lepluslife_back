<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/12/28
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
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
    <style>
        thead th, tbody td {
            text-align: center;
        }

        #myTab {
            margin-bottom: 10px;
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
            <div class="container-fluid">
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="date-end">交易完成时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="refund-date-end">退款完成时间</label>

                        <div id="refund-date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="customer-ID">消费者ID</label>
                        <input type="text" class="form-control" id="customer-ID"
                               placeholder="请输入消费者ID">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="customer-tel">消费者手机号</label>
                        <input type="text" id="customer-tel" class="form-control"
                               placeholder="请输入消费者手机号"/>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="merchant-name">门店名称</label>
                        <input type="text" id="merchant-name" class="form-control"
                               placeholder="请输入商户名称"/>
                    </div>

                    <div class="form-group col-md-3">
                        <label for="merchant-userId">商户ID</label>
                        <input type="text" id="merchant-userId" class="form-control"
                               placeholder="请输入商户ID"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="orderType">订单类型</label>
                        <select class="form-control" id="orderType">
                            <option value="-1">所在类型(全部)</option>
                            <option value="12001">非会员普通订单（普通商户）</option>
                            <option value="12003">非会员普通订单（联盟商户）</option>
                            <option value="12002">会员普通订单（普通商户）</option>
                            <option value="12004">导流订单</option>
                            <option value="12005">会员订单（佣金费率）</option>
                            <option value="12006">会员订单（普通费率）</option>
                        </select>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="order-SID">订单编号</label>
                        <input type="text" id="order-SID" class="form-control"
                               placeholder="请输入订单编号"/>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="merchantNum">交易商户号</label>
                        <input type="text" id="merchantNum" class="form-control"
                               placeholder="请输入商户号"/>
                    </div>

                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchOrderByCriteria()">查询
                        </button>
                    </div>
                </div>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>退款单号</th>
                                <th>订单编号</th>
                                <th>富友商户号</th>
                                <th>退款完成时间</th>
                                <th>交易完成时间</th>
                                <th>订单金额</th>
                                <th>交易门店</th>
                                <th>交易商户</th>
                                <th>消费者信息</th>
                                <th>订单类型</th>
                                <th>追回红包积分</th>
                                <th>追回分润</th>
                            </tr>
                            </thead>
                            <tbody id="orderContent">
                            </tbody>
                        </table>
                    </div>
                    <div class="tcdPageCode" style="display: inline;">
                    </div>
                    <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                    <button class="btn btn-primary pull-right" style="margin-top: 5px"
                            onclick="exportExcel()">导出excel
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>

    var scanCodeRefundOrderCriteria = {};
    var flag = true;
    var init1 = 0;
    var orderContent = document.getElementById("orderContent");
    $(function () {
        // 时间选择器
        $(document).ready(function () {
            $('#date-end').daterangepicker({
                                               maxDate: moment(), //最大时间
                                               showDropdowns: true,
                                               showWeekNumbers: false, //是否显示第几周
                                               timePicker: true, //是否显示小时和分钟
                                               timePickerIncrement: 60, //时间的增量，单位为分钟
                                               timePicker12Hour: false, //是否使用12小时制来显示时间
                                               ranges: {
                                                   '最近1小时': [moment().subtract('hours', 1),
                                                             moment()],
                                                   '今日': [moment().startOf('day'), moment()],
                                                   '昨日': [moment().subtract('days',
                                                                            1).startOf('day'),
                                                          moment().subtract('days',
                                                                            1).endOf('day')],
                                                   '最近7日': [moment().subtract('days', 6), moment()],
                                                   '最近30日': [moment().subtract('days', 29),
                                                             moment()]
                                               },
                                               opens: 'right', //日期选择框的弹出位置
                                               buttonClasses: ['btn btn-default'],
                                               applyClass: 'btn-small btn-primary blue',
                                               cancelClass: 'btn-small',
                                               format: 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
                                               separator: ' to ',
                                               locale: {
                                                   applyLabel: '确定',
                                                   cancelLabel: '取消',
                                                   fromLabel: '起始时间',
                                                   toLabel: '结束时间',
                                                   customRangeLabel: '自定义',
                                                   daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                                                   monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                                                                '七月', '八月', '九月', '十月', '十一月',
                                                                '十二月'],
                                                   firstDay: 1
                                               }
                                           }, function (start, end, label) {//格式化日期显示框
                $('#date-end span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                                         + end.format('YYYY/MM/DD HH:mm:ss'));
            });
            $('#refund-date-end').daterangepicker({
                                                      maxDate: moment(), //最大时间
                                                      showDropdowns: true,
                                                      showWeekNumbers: false, //是否显示第几周
                                                      timePicker: true, //是否显示小时和分钟
                                                      timePickerIncrement: 60, //时间的增量，单位为分钟
                                                      timePicker12Hour: false, //是否使用12小时制来显示时间
                                                      ranges: {
                                                          '最近1小时': [moment().subtract('hours', 1),
                                                                    moment()],
                                                          '今日': [moment().startOf('day'), moment()],
                                                          '昨日': [moment().subtract('days',
                                                                                   1).startOf('day'),
                                                                 moment().subtract('days',
                                                                                   1).endOf('day')],
                                                          '最近7日': [moment().subtract('days', 6),
                                                                   moment()],
                                                          '最近30日': [moment().subtract('days', 29),
                                                                    moment()]
                                                      },
                                                      opens: 'right', //日期选择框的弹出位置
                                                      buttonClasses: ['btn btn-default'],
                                                      applyClass: 'btn-small btn-primary blue',
                                                      cancelClass: 'btn-small',
                                                      format: 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
                                                      separator: ' to ',
                                                      locale: {
                                                          applyLabel: '确定',
                                                          cancelLabel: '取消',
                                                          fromLabel: '起始时间',
                                                          toLabel: '结束时间',
                                                          customRangeLabel: '自定义',
                                                          daysOfWeek: ['日', '一', '二', '三', '四', '五',
                                                                       '六'],
                                                          monthNames: ['一月', '二月', '三月', '四月', '五月',
                                                                       '六月',
                                                                       '七月', '八月', '九月', '十月',
                                                                       '十一月',
                                                                       '十二月'],
                                                          firstDay: 1
                                                      }
                                                  }, function (start, end, label) {//格式化日期显示框
                $('#refund-date-end span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                                                + end.format('YYYY/MM/DD HH:mm:ss'));
            });
        });
        scanCodeRefundOrderCriteria.offset = 1;
        getOrderByAjax(scanCodeRefundOrderCriteria);
    });
    function getOrderByAjax(scanCodeRefundOrderCriteria) {
        orderContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/refund/orderList",
                   async: false,
                   data: JSON.stringify(scanCodeRefundOrderCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var page = data.data;
                       var content = page.content;
                       var totalPage = page.totalPages;
                       $("#totalElements").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag) {
                           flag = false;
                           initPage(scanCodeRefundOrderCriteria.offset, totalPage);
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       var orderContent = document.getElementById("orderContent");
                       for (var i = 0; i < content.length; i++) {
                           var order = content[i].scanCodeOrder;
                           var contentStr = '<tr><td>' + content[i].refundOrderSid + '</td>';
                           contentStr += '<td>' + order.orderSid + '</td>';
                           contentStr += '<td>' + order.merchantNum + '</td>';
                           contentStr +=
                           '<td>'
                           + new Date(content[i].completeDate).format('yyyy-MM-dd HH:mm:ss')
                           + '</td>';
                           contentStr +=
                           '<td>'
                           + new Date(order.completeDate).format('yyyy-MM-dd HH:mm:ss')
                           + '</td>';
                           contentStr +=
                           '<td>￥' + order.totalPrice / 100 + '（￥' + order.truePay / 100
                           + '+￥' + order.trueScore / 100 + '红包）</td>';
                           contentStr +=
                           '<td><span>' + order.merchant.name + '</span><br><span>('
                           + order.merchant.merchantSid + ')</span></td>';
                           contentStr += '<td>' + order.merchantUserId + '</td>';
                           if (order.leJiaUser.phoneNumber != null) {
                               contentStr +=
                               '<td><span>' + order.leJiaUser.phoneNumber
                               + '</span><br><span>('
                               + order.leJiaUser.userSid + ')</span></td>'
                           } else {
                               contentStr +=
                               '<td><span>未绑定手机号</span><br><span>('
                               + order.leJiaUser.userSid + ')</span></td>'
                           }
                           var typeDetail = '';
                           switch (eval(order.orderType.id)) {
                               case 12001:
                                   typeDetail = '非会员普通订单（普通商户）';
                                   break;
                               case 12002:
                                   typeDetail = '会员普通订单（普通商户）';
                                   break;
                               case 12003:
                                   typeDetail = '非会员普通订单（联盟商户）';
                                   break;
                               case 12004:
                                   typeDetail = '导流订单';
                                   break;
                               case 12005:
                                   typeDetail = '会员订单（佣金费率）';
                                   break;
                               case 12006:
                                   typeDetail = '会员订单（普通费率）';
                                   break;
                               default:
                                   typeDetail = '未知类型';
                           }
                           contentStr += '<td>' + typeDetail + '</td>';
                           contentStr +=
                           '<td>红包' + content[i].realRebate / 100 + '元+' + content[i].realScoreB
                           + '积分</td>';
                           contentStr +=
                           '<td>' + (content[i].toTradePartner + content[i].toTradePartnerManager
                                     + content[i].toLockMerchant + content[i].toLockPartner
                                     + content[i].toLockPartnerManager ) / 100 + '</td>';
                           orderContent.innerHTML += contentStr;
                       }
                   }
               });
    }
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             scanCodeRefundOrderCriteria.offset = p;
                                             init1 = 0;
                                             getOrderByAjax(scanCodeRefundOrderCriteria);
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
    function searchOrderByCriteria() {
        scanCodeRefundOrderCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            scanCodeRefundOrderCriteria.startDate = dateStr[0].replace(/-/g, "/");
            scanCodeRefundOrderCriteria.endDate = dateStr[1].replace(/-/g, "/");
        }
        var dateStr2 = $('#refund-date-end span').text().split("-");
        if (dateStr2 != null && dateStr2 != '') {
            scanCodeRefundOrderCriteria.refundStartDate = dateStr2[0].replace(/-/g, "/");
            scanCodeRefundOrderCriteria.refundEndDate = dateStr2[1].replace(/-/g, "/");
        }
        if ($("#customer-ID").val() != "" && $("#customer-ID").val() != null) {
            scanCodeRefundOrderCriteria.userSid = $("#customer-ID").val();
        } else {
            scanCodeRefundOrderCriteria.userSid = null;
        }
        if ($("#customer-tel").val() != "" && $("#customer-tel").val() != null) {
            scanCodeRefundOrderCriteria.phoneNumber = $("#customer-tel").val();
        } else {
            scanCodeRefundOrderCriteria.phoneNumber = null;
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            scanCodeRefundOrderCriteria.merchantName = $("#merchant-name").val();
        } else {
            scanCodeRefundOrderCriteria.merchantName = null;
        }
        if ($("#merchant-userId").val() != "" && $("#merchant-userId").val() != null) {
            scanCodeRefundOrderCriteria.merchantUserId = $("#merchant-userId").val();
        } else {
            scanCodeRefundOrderCriteria.merchantUserId = null;
        }
        if ($("#orderType").val() != -1) {
            scanCodeRefundOrderCriteria.orderType = $("#orderType").val();
        } else {
            scanCodeRefundOrderCriteria.orderType = null;
        }
        if ($("#order-SID").val() != "" && $("#order-SID").val() != null) {
            scanCodeRefundOrderCriteria.orderSid = $("#order-SID").val();
        } else {
            scanCodeRefundOrderCriteria.orderSid = null;
        }
        if ($("#merchantNum").val() != "" && $("#merchantNum").val() != null) {
            scanCodeRefundOrderCriteria.merchantNum = $("#merchantNum").val();
        } else {
            scanCodeRefundOrderCriteria.merchantNum = null;
        }
        getOrderByAjax(scanCodeRefundOrderCriteria);
    }

    function post(URL, PARAMS) {
        var temp = document.createElement("form");
        temp.action = URL;
        temp.method = "post";
        temp.style.display = "none";
        for (var x in PARAMS) {
            var opt = document.createElement("textarea");
            opt.name = x;
            opt.value = PARAMS[x];
            // alert(opt.name)
            temp.appendChild(opt);
        }
        document.body.appendChild(temp);
        temp.submit();
        return temp;
    }
    function exportExcel() {
        if (scanCodeRefundOrderCriteria.startDate == null) {
            scanCodeRefundOrderCriteria.startDate = null;
        }
        if (scanCodeRefundOrderCriteria.refundStartDate == null) {
            scanCodeRefundOrderCriteria.refundStartDate = null;
        }
        if (scanCodeRefundOrderCriteria.refundEndDate == null) {
            scanCodeRefundOrderCriteria.refundEndDate = null;
        }
        if (scanCodeRefundOrderCriteria.endDate == null) {
            scanCodeRefundOrderCriteria.endDate = null;
        }
        if (scanCodeRefundOrderCriteria.userSid == null) {
            scanCodeRefundOrderCriteria.userSid = null;
        }
        if (scanCodeRefundOrderCriteria.phoneNumber == null) {
            scanCodeRefundOrderCriteria.phoneNumber = null;
        }
        post("/manage/refund/export", scanCodeRefundOrderCriteria);
    }

    //***********************保留2位小数*************************************
    function toDecimal(x) {
        var f = parseFloat(x);
        if (isNaN(f)) {
            return false;
        }
        var f = Math.round(x * 100) / 100;
        var s = f.toString();
        var rs = s.indexOf('.');
        if (rs < 0) {
            rs = s.length;
            s += '.';
        }
        while (s.length <= rs + 2) {
            s += '0';
        }
        return s;
    }
</script>
</body>
</html>