<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 17/01/03
  Time: 上午10:02
  富友结算单列表
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

        @media (min-width: 992px) {
            .modal-lg {
                width: 750px;
            }
        }

        .modal-header {
            border: 0 !important;
        }

        .FYOrder {
            width: 90%;
            margin: 0 auto;
        }

        .FYOrder > div {
            margin-top: 10px;
        }

        .FYOrder > div > div {
            float: left;
            font-size: 14px;
        }

        .FYOrder > div > div:first-child {
            width: 25%;
            font-size: 16px;
            text-align: left;
        }

        .recover {
            width: 70%;
        }

        .recover > div > div {
            float: left;
            width: 40%;
        }

        .recover > div > div:first-child {
            margin-right: 5%;
        }

        .recover > div > div:last-child {
            width: 50%;
        }

        .FYOrder > * {
            text-align: center;
        }

        .FYOrder > p:first-child {
            font-size: 26px;
            margin: 10px 0;
        }

        .FYOrder > p:first-child {
            font-size: 20px;
        }

        .FYOrder input[type=radio] {
            width: auto !important;
            margin: 0 !important;
        }

        .FYOrder input[type=number] {
            width: 30%;
            border: 1px solid #dddddd;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            padding: 5px 10px;
        }

        .recover_button {
            text-align: center;
        }

        button {
            text-align: center;
            margin: 20px 0;
        }

        .FYOrder > div:after, .recover > div:after {
            content: '\20';
            display: block;
            clear: both;
        }

        .recover-r {
            color: red;
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
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-3">
                        <label for="date-end">账单日期</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="merchant-name">门店名称</label>
                        <input type="text" id="merchant-name" class="form-control"
                               placeholder="请输入商户名称"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="merchantUser-name">商户名称</label>
                        <input type="text" id="merchantUser-name" class="form-control"
                               placeholder="请输入商户ID"/>
                    </div>
                    <button class="btn btn-primary" style="margin-top: 24px"
                            onclick="searchOrderByCriteria()">查询
                    </button>
                </div>

                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab" onclick="searchOrderByState(0)">待转账</a>
                    </li>
                    <li class="active"><a href="#tab2" data-toggle="tab"
                                          onclick="searchOrderByState(1)">转账记录</a></li>
                    <li><a href="#tab3" data-toggle="tab" onclick="searchOrderByState(2)">挂账记录</a>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>结算单号</th>
                                <th>移动商户号</th>
                                <th>账单日期</th>
                                <th>门店名称</th>
                                <th>商户名称</th>
                                <th>结算账户</th>
                                <th>扫码微信入账<br>(富友结算)</th>
                                <th>补充结算红包</th>
                                <th>操作</th>
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
                    <button class="btn btn-primary pull-right" style="margin-top: 5px"
                            id="batchTransfer">全部确认转账
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<div class="modal" id="Refund_end">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="FYOrder">
                <p id="refund-status"></p>

                <p id="refund-msg"></p>

                <div>
                    <button class="ModButton ModButton_ordinary ModRadius" data-dismiss="modal">
                        知道了
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>

    var scanCodeSettleOrderCriteria = {};
    var flag = true;
    var init1 = 0;
    var orderContent = document.getElementById("orderContent");
    $(function () {
        // tab切换
        $('#myTab li:eq(0) a').tab('show');

        // 时间选择器
        $(document).ready(function () {
            $('#date-end').daterangepicker({
                                               maxDate: moment(), //最大时间
                                               showDropdowns: true,
                                               showWeekNumbers: false, //是否显示第几周
                                               timePicker: false, //是否显示小时和分钟
                                               timePickerIncrement: 1440, //时间的增量，单位为天
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
                $('#date-end span').html(start.format('YYYYMMDD') + ' - '
                                         + end.format('YYYYMMDD'));
            });
        });
        scanCodeSettleOrderCriteria.offset = 1;
        scanCodeSettleOrderCriteria.state = 0;
        getOrderByAjax(scanCodeSettleOrderCriteria);
    });
    function getOrderByAjax(scanCodeSettleOrderCriteria) {
        orderContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/statement/orderList",
                   async: false,
                   data: JSON.stringify(scanCodeSettleOrderCriteria),
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
                           initPage(scanCodeSettleOrderCriteria.offset, totalPage);
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       var orderContent = document.getElementById("orderContent");
                       for (var i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].orderSid + '</td>';
                           if (content[i].type == 0) {
                               contentStr +=
                               '<td>' + content[i].merchantNum + '<br>（普通商户号）</td>';
                           } else {
                               contentStr +=
                               '<td>' + content[i].merchantNum + '<br>（佣金商户号）</td>';
                           }
                           contentStr += '<td>' + content[i].tradeDate + '</td>';

                           var shops = content[i].shopNames.split('_');

                           if (shops != null && shops.length > 1) {
                               contentStr += '<td>' + shops[0] + '等' + shops.length + '家门店</td>';
                           } else {
                               contentStr += '<td>' + content[i].shopNames + '</td>';
                           }

                           contentStr +=
                           '<td>' + content[i].merchantName + '（' + content[i].merchantUserId
                           + '）</td>';
                           contentStr +=
                           '<td>' + content[i].bankNumber + '-' + content[i].payee
                           + '-' + content[i].bankName + '</td>';
                           contentStr +=
                           '<td>￥' + toDecimal(content[i].transferMoneyFromTruePay / 100) + '</td>';
                           contentStr +=
                           '<td>￥' + toDecimal(content[i].transferMoneyFromScore / 100) + '</td>';

                           var state = eval(content[i].state);

                           if (state == 0) {
                               contentStr +=
                               '<td><button class="btn btn-primary" onclick="setTrade(\''
                               + content[i].id
                               + '\')">确认转账</button><button class="btn btn-primary" onclick="setNoTrade(\''
                               + content[i].id + '\')">设为挂账</button></td></tr>';
                           } else if (state == 2) {
                               contentStr +=
                               '<td><button class="btn btn-primary" onclick="setTrade(\''
                               + content[i].id
                               + '\')">确认转账</button></td></tr>';
                           } else {
                               contentStr +=
                               '<td></td></tr>';
                           }
                           orderContent.innerHTML += contentStr;
                       }
                   }
               });
    }

    function setTrade(currId) {
        $.ajax({
                   type: "post",
                   url: "/manage/statement/setTrade/" + currId,
                   async: false,
                   success: function (data) {
                       if (data.status == 200) {
                           getOrderByAjax(scanCodeSettleOrderCriteria);
                       } else {
                           alert(data.msg);
                       }
                   }
               });
    }

    $("#batchTransfer").bind("click", function () {
        $("#batchTransfer").unbind("click");
        $('#refund-msg').html('确认转账中，请稍等片刻。。。');
        $('#Refund_end').modal("toggle");
        $.ajax({
                   type: "post",
                   url: "/manage/statement/batchTransfer",
                   success: function (data) {
                       if (data.status == 200) {
                           $('#refund-msg').html('批量转账成功');
                       } else {
                           $('#refund-msg').html('转账出现问题');
                       }
                       getOrderByAjax(scanCodeSettleOrderCriteria);
                   }
               });
    })
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             scanCodeSettleOrderCriteria.offset = p;
                                             init1 = 0;
                                             getOrderByAjax(scanCodeSettleOrderCriteria);
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
        scanCodeSettleOrderCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split(" - ");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0];
            var endDate = dateStr[1];
            scanCodeSettleOrderCriteria.startDate = startDate;
            scanCodeSettleOrderCriteria.endDate = endDate;
        }

        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            scanCodeSettleOrderCriteria.merchantName = $("#merchant-name").val();
        } else {
            scanCodeSettleOrderCriteria.merchantName = null;
        }
        if ($("#merchantUser-name").val() != "" && $("#merchantUser-name").val() != null) {
            scanCodeSettleOrderCriteria.merchantUserName = $("#merchantUser-name").val();
        } else {
            scanCodeSettleOrderCriteria.merchantUserName = null;
        }

        getOrderByAjax(scanCodeSettleOrderCriteria);
    }
    function searchOrderByState(state) {
        scanCodeSettleOrderCriteria.offset = 1;
        if (state != null) {
            scanCodeSettleOrderCriteria.state = state;
        } else {
            scanCodeSettleOrderCriteria.state = null;
        }
        flag = true;
        getOrderByAjax(scanCodeSettleOrderCriteria);
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

        post("/manage/statement/export", scanCodeSettleOrderCriteria);
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