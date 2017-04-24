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
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>
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
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-6">
                        <label for="date-end">消费时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="customer-ID">消费者ID</label>
                        <input type="text" class="form-control" id="customer-ID"
                               placeholder="请输入消费者ID">
                    </div>
                </div>
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="merchant-name">消费门店</label>
                        <input type="text" id="merchant-name" class="form-control"
                               placeholder="输入店铺名称查询"/>
                    </div>
                    <%--<div class="form-group col-md-3">--%>
                    <%--<label for="remote-style">订单类型</label>--%>
                    <%--<select class="form-control" id="remote-style">--%>
                    <%--<option value="">全部类型</option>--%>
                    <%--<option value="0">即将锁满</option>--%>
                    <%--<option value="1">已锁满</option>--%>
                    <%--<option value="2">未锁定</option>--%>
                    <%--</select>--%>
                    <%--</div>--%>
                    <div class="form-group col-md-3">
                        <label for="order-source">订单状态</label>
                        <select class="form-control" id="order-source">
                            <option value="">全部状态</option>
                            <option value="0">未支付</option>
                            <option value="1">已支付(未对账)</option>
                            <option value="2">已支付(已对账)</option>
                            <option value="3">未支付(已冲正)</option>
                            <option value="4">未支付(已撤销)</option>
                        </select>
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
                                <th>订单号</th>
                                <th>订单类型</th>
                                <th>交易完成时间</th>
                                <th>消费门店</th>
                                <th>消费者</th>
                                <th>付款方式</th>
                                <th>消费金额</th>
                                <th>使用红包</th>
                                <th>使用储值</th>
                                <th>实际支付</th>
                                <th>佣金(手续费)</th>
                                <th>实际入账</th>
                                <th>发红包</th>
                                <th>发金币</th>
                                <th>终端号</th>
                                <th>状态</th>
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
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var unionPosOrderCriteria = {};
    var orderContent = document.getElementById("orderContent");
    var flag = true;
    var init1 = 0;
    $(function () {
        // 时间选择器
        $(document).ready(function () {
//            $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss')
//                                     + ' - ' +
//                                     moment().format('YYYY/MM/DD HH:mm:ss'));
            $('#date-end').daterangepicker({
                maxDate: moment(), //最大时间
//                                               dateLimit: {
//                                                   days: 30
//                                               }, //起止时间的最大间隔
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
        })
        unionPosOrderCriteria.offset = 1;
        getUnionPosOrderByAjax(unionPosOrderCriteria);
    })
    function getUnionPosOrderByAjax(unionPosOrderCriteria) {
        orderContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/unionPosOrder/getUnionPosOrderByAjax",
            async: false,
            data: JSON.stringify(unionPosOrderCriteria),
            contentType: "application/json",
            success: function (data) {
                var page = data.data.page;
                var content = page.content;
                var totalPage = page.totalPages;
                $("#totalElements").html(page.totalElements);
                if (totalPage == 0) {
                    totalPage = 1;
                }
                if (flag) {
                    flag = false;
                    initPage(unionPosOrderCriteria.offset, totalPage);
                }
                if (init1) {
                    initPage(1, totalPage);
                }
                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr><td>' + content[i].orderSid + '</td>';

                    if (content[i].rebateWay == 0) {
                        contentStr +=
                                '<td><span>普通订单(非会员)</span></td>';
                    } else if (content[i].rebateWay == 1) {
                        contentStr +=
                                '<td><span>导流订单</span></td>';
                    } else if (content[i].rebateWay == 2) {
                        contentStr +=
                                '<td><span>普通订单(会员)</span></td>';
                    } else if (content[i].rebateWay == 3) {
                        contentStr +=
                                '<td><span>会员订单</span></td>';
                    } else {
                        contentStr +=
                                '<td><span>--</span></td>';
                    }
                    contentStr +=
                            '<td><span>'
                            + new Date(content[i].completeDate).format('yyyy-MM-dd HH:mm:ss')
                            + '</span></td>';
                    contentStr +=
                            '<td><span>' + content[i].merchant.name + '</span><br><span>(' + content[i].merchant.sid + ')</span></td>';
                    if (content[i].leJiaUser === null) {
                        contentStr +=
                                '<td><span>匿名</span></td>';
                    } else {
                        if (content[i].leJiaUser.phoneNumber == null) {
                            contentStr +=
                                    '<td><span>--</span><br><span>' + '(' + content[i].leJiaUser.userSid + ')' + '</span></td>';
                        } else {
                            contentStr +=
                                    '<td><span>' + content[i].leJiaUser.phoneNumber + '</span><br><span>' + '(' + content[i].leJiaUser.userSid + ')' + '</span></td>';
                        }

                    }

                    if (content[i].paidType == 2) {
                        contentStr += '<td><span>红包</span></td>';
                    }
                    if (content[i].paidType == 1) {
                        if (content[i].channel == 0) {
                            contentStr += '<td><span>刷卡</span></td>';
                        }
                        if (content[i].channel == 1) {
                            contentStr += '<td><span>微信</span></td>';
                        }
                        if (content[i].channel == 2) {
                            contentStr += '<td><span>支付宝</span></td>';
                        }
                    }
                    if (content[i].paidType == 3) {
                        if (content[i].channel == 0) {
                            contentStr += '<td><span>刷卡+红包</span></td>';
                        }
                        if (content[i].channel == 1) {
                            contentStr += '<td><span>微信+红包</span></td>';
                        }
                        if (content[i].channel == 2) {
                            contentStr += '<td><span>支付宝+红包</span></td>';
                        }
                    }

                    contentStr += '<td><span>' + content[i].totalPrice / 100 + '</span></td>';
                    contentStr += '<td><span>' + content[i].trueScore / 100 + '</span></td>';
                    contentStr += '<td><span>--</span></td>';
                    contentStr += '<td><span>' + content[i].truePay / 100 + '</span></td>';
                    if (content[i].ljCommission == 0) {
                        contentStr += '<td><span>--</span></td>';
                    } else {
                        contentStr += '<td><span>' + content[i].ljCommission / 100 + '</span></td>';
                    }
                    if (content[i].transferMoney == 0) {
                        contentStr += '<td><span>--</span></td>';
                    } else {
                        contentStr += '<td><span>' + content[i].transferByBank / 100 + '+' + content[i].transferByScore / 100 + '(红包)' + '</span></td>';
                    }
                    contentStr += '<td><span>' + content[i].rebate / 100 + '</span></td>';
                    contentStr += '<td><span>--</span></td>';
                    contentStr += '<td><span>--</span></td>';
                    if (content[i].orderState == 0) {
                        contentStr += '<td><span>未支付</span></td>';
                    }
                    if (content[i].orderState == 1) {
                        contentStr += '<td><span>已支付(未对账)</span></td>';
                    }
                    if (content[i].orderState == 2) {
                        contentStr += '<td><span>已支付(已对账)</span></td>';
                    }
                    if (content[i].orderState == 3) {
                        contentStr += '<td><span>未支付(已冲正)</span></td>';
                    }
                    if (content[i].orderState == 4) {
                        contentStr += '<td><span>未支付(已撤销)</span></td>';
                    }
                    contentStr += '</tr>';
                    orderContent.innerHTML += contentStr;
                }
            }
        })
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
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                unionPosOrderCriteria.offset = p;
                init1 = 0;
                getUnionPosOrderByAjax(unionPosOrderCriteria);
            }
        });
    }
    function searchOrderByCriteria() {
        unionPosOrderCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            unionPosOrderCriteria.startDate = startDate;
            unionPosOrderCriteria.endDate = endDate;
        }

        if ($("#customer-ID").val() != "" && $("#customer-ID").val() != null) {
            unionPosOrderCriteria.userSid = $("#customer-ID").val();
        } else {
            unionPosOrderCriteria.userSid = null;
        }

        if ($("#order-source").val() != "" && $("#order-source").val() != null) {
            unionPosOrderCriteria.orderState = $("#order-source").val();
        } else {
            unionPosOrderCriteria.orderState = null;
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            unionPosOrderCriteria.merchant = $("#merchant-name").val();
        } else {
            unionPosOrderCriteria.merchant = null;
        }
        getUnionPosOrderByAjax(unionPosOrderCriteria);
    }


    function exportExcel() {
        if (unionPosOrderCriteria.startDate == null || unionPosOrderCriteria.startDate == "") {
            unionPosOrderCriteria.startDate = null;
        }
        if (unionPosOrderCriteria.endDate == null || unionPosOrderCriteria.endDate == "") {
            unionPosOrderCriteria.endDate = null;
        }
        if (unionPosOrderCriteria.orderState == null || unionPosOrderCriteria.orderState == "") {
            unionPosOrderCriteria.orderState = null;
        }
        if (unionPosOrderCriteria.userSid == null || unionPosOrderCriteria.userSid == "") {
            unionPosOrderCriteria.userSid = null;
        }
        if (unionPosOrderCriteria.merchant == null || unionPosOrderCriteria.merchant == "") {
            unionPosOrderCriteria.merchant = null;
        }
        post("/manage/unionPosOrder/exportExcel", unionPosOrderCriteria);
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
</script>
</body>
</html>