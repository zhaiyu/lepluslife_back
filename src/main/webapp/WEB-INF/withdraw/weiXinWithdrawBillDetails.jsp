<%--
  Created by IntelliJ IDEA.
  User: lss
  Date: 16/7/21
  Time: 上午10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <style>
        table tr td img, form img {
            width: 80px;
            height: 80px;
        }

        .table > tbody > tr > td, .table > thead > tr > th {
            line-height: 40px;
            text-align: center
        }

        .table > tbody > tr > td, .table > thead > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
            line-height: 60px;
        }
    </style>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.combo.select.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
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
                <button class="btn btn-primary" style="margin-top: 10px" onclick="goBack()">返回 </button>
                <input type="hidden" id="goBackUrl" value="${goBackUrl}">
                <input type="hidden" id="partnerID" value="${partner.id}">
                <hr>
                <span>佣金余额 ： ￥${(partnerWallet.availableBalance+partnerWalletOnline.availableBalance)/100.0} ，  提现中 ： ￥${onWithdrawal/100.0}，  已提现 ： ￥${(partnerWallet.totalWithdrawals+partnerWalletOnline.totalWithdrawals)/100.0}，  ￥累计佣金收入 ： ￥${(partnerWallet.totalMoney+partnerWalletOnline.totalMoney)/100.0}</span>
                <center><h1>合伙人：${partner.name}</h1></center>
                <hr>
                <ul id="myTab" class="nav nav-tabs">
                    <li class="active"><a href="#walletOnlineLog" data-toggle="tab">线上钱包记录</a></li>
                    <li><a href="#walletOfflineLog" data-toggle="tab" onclick="getPartnerWalletOfflineLogByCriteria()">线下钱包记录</a>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content" style="margin-top: 10px">


                    <div class="tab-pane fade in active" id="walletOnlineLog">
                        <div class="form-group col-md-6">
                            <label for="date-end">变更时间</label>

                            <div id="date-end" class="form-control">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                <span id="searchDateRange"></span>
                                <b class="caret"></b>
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="walletOnlineLogType">变更来源</label>
                            <select class="form-control" id="walletOnlineLogType">
                                <option value="">全部来源</option>
                                <option value="16001">锁定会员线上消费</option>
                                <option value="16002">锁定会员成为合伙人</option>
                                <option value="16003">提现</option>
                                <option value="16004">用户未接受微信红包</option>
                                <option value="16005">驳回提现请求</option>
                            </select>
                        </div>
                        <div class="form-group col-md-3">
                            <button class="btn btn-primary" style="margin-top: 24px"
                                    onclick="getPartnerWalletOnLineLogByCriteria()">查询
                            </button>
                        </div>

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th class="text-center">变更流水号</th>
                                <th class="text-center">变更时间</th>
                                <th class="text-center">变更值</th>
                                <th class="text-center">变更来源</th>
                                <th class="text-center">关联单号</th>
                                <th class="text-center">变更前余额</th>
                                <th class="text-center">变更后余额</th>
                            </tr>
                            </thead>
                            <tbody id="walletOnlineLogContent">
                            </tbody>
                        </table>
                        <div class="tcdPageCode" id="tcdPageCode" style="display: inline;">
                        </div>
                        <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                        <button class="btn btn-primary pull-right" style="margin-top: 5px"
                                onclick="exportOnLineExcel()">导出excel
                        </button>
                    </div>


                    <div class="tab-pane fade" id="walletOfflineLog">

                        <div class="form-group col-md-6">
                            <label for="date-end">变更时间</label>

                            <div id="date-end1" class="form-control">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                <span id="searchDateRange1"></span>
                                <b class="caret"></b>
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="walletOfflineLogType">变更来源</label>
                            <select class="form-control" id="walletOfflineLogType">
                                <option value="">全部来源</option>
                                <option value="15001">锁定会员线下消费</option>
                                <option value="15002">锁定门店产生佣金</option>
                                <option value="15003">提现</option>
                                <option value="15004"> 用户未接受微信红包</option>
                                <option value="15005"> 驳回提现请求</option>
                            </select>
                        </div>

                        <div class="form-group col-md-3">
                            <button class="btn btn-primary" style="margin-top: 24px"
                                    onclick="getPartnerWalletOfflineLogByCriteria()">查询
                            </button>
                        </div>

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th class="text-center">变更流水号</th>
                                <th class="text-center">变更时间</th>
                                <th class="text-center">变更值</th>
                                <th class="text-center">变更来源</th>
                                <th class="text-center">关联单号</th>
                                <th class="text-center">变更前余额</th>
                                <th class="text-center">变更后余额</th>
                            </tr>
                            </thead>
                            <tbody id="walletOfflineLogContent">
                            </tbody>
                        </table>
                        <div class="tcdPageCode" id="tcdPageCode1" style="display: inline;">
                        </div>
                        <div style="display: inline;"> 共有 <span id="totalElements1"></span> 个</div>
                        <button class="btn btn-primary pull-right" style="margin-top: 5px"
                                onclick="exportOffLineExcel()">导出excel
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

<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>

    var partnerWalletOnlineLogCriteria = {};
    partnerWalletOnlineLogCriteria.partnerId=$("#partnerID").val();
    var walletOnlineLogContent = document.getElementById("walletOnlineLogContent");
    var flag = true;
    var init = 0;



    var partnerWalletOfflineLogCriteria = {};
    partnerWalletOfflineLogCriteria.partnerId=$("#partnerID").val();
    var walletOfflineLogContent = document.getElementById("walletOfflineLogContent");
    var flag1 = true;
    var init1 = 0;







        function getPartnerWalletOfflineLogByCriteria() {
            var dateStr = $('#date-end1 span').text().split("-");
            if (dateStr != null && dateStr != '') {
                var startDate = dateStr[0].replace(/-/g, "/");
                var endDate = dateStr[1].replace(/-/g, "/");
                partnerWalletOfflineLogCriteria.createdStartDate = startDate;
                partnerWalletOfflineLogCriteria.createdEndDate = endDate;
            }

            if ($("#walletOfflineLogType").val() != "") {
                partnerWalletOfflineLogCriteria.type = $("#walletOfflineLogType").val();
            } else {
                partnerWalletOfflineLogCriteria.type = null;
            }

            partnerWalletOfflineLogCriteria.offset = 1;
            getPartnerWalletOffLineLogByAjax(partnerWalletOfflineLogCriteria);

        }

        function getPartnerWalletOffLineLogByAjax(partnerWalletOfflineLogCriteria) {
            walletOfflineLogContent.innerHTML = "";
                $.ajax({
                    type: "post",
                    url: "/manage/partnerWalletLog/getPartnerWalletOffLineLogByAjax",
                    async: false,
                    data: JSON.stringify(partnerWalletOfflineLogCriteria),
                    contentType: "application/json",
                    success: function (data) {
                        var page = data.data;
                        var content = page.content;
                        var totalPage = page.totalPages;
                        $("#totalElements1").html(page.totalElements);
                        if (totalPage == 0) {
                            totalPage = 1;
                        }
                        if (flag1) {
                            flag = false;
                            initPage1(partnerWalletOfflineLogCriteria.offset, totalPage);
                        }
                        if (init) {
                            initPage1(1, totalPage);
                        }
                        for (i = 0; i < content.length; i++) {
                            var contentStr = '<tr><td>' + content[i].id + '</td>';
                            contentStr +=
                                '<td><span>'
                                + new Date(content[i].createDate).format('yyyy-MM-dd HH:mm:ss')
                                + '</span></td>';
                            contentStr += '<td>' + (content[i].afterChangeMoney-content[i].beforeChangeMoney)/100.0 + '</td>';
                            if(content[i].type==16001){
                                contentStr += '<td>锁定会员线上消费</td>';
                            }else if(content[i].type==16002){
                                contentStr += '<td>锁定会员成为合伙人</td>';
                            }else if(content[i].type==16003){
                                contentStr += '<td>提现</td>';
                            }else if(content[i].type==16004){
                                contentStr += '<td>用户未接受微信红包</td>';
                            }else if(content[i].type==16005){
                                contentStr += '<td>驳回提现请求</td>';
                            }else {
                                contentStr += '<td>--</td>';
                            }
                            contentStr += '<td>' + content[i].orderSid + '</td>';
                            contentStr += '<td>' + content[i].beforeChangeMoney/100.0 + '</td>';
                            contentStr += '<td>' + content[i].afterChangeMoney /100.0+ '</td>';
                            walletOfflineLogContent.innerHTML += contentStr;
                        }
                    }});

        }

    function initPage1(page, totalPage) {
            $('#tcdPageCode1').unbind();
            $("#tcdPageCode1").createPage({
                pageCount: totalPage,
                current: page,
                backFn: function (p) {
                    partnerWalletOfflineLogCriteria.offset = p;
                    init1 = 0;
                    getPartnerWalletOffLineLogByAjax(partnerWalletOfflineLogCriteria);
                }
            });

    }

    function exportOffLineExcel() {
        var dateStr = $('#date-end1 span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            partnerWalletOfflineLogCriteria.createdStartDate = startDate;
            partnerWalletOfflineLogCriteria.createdEndDate = endDate;
        }

        if ($("#walletOfflineLogType").val() != "") {
            partnerWalletOfflineLogCriteria.type = $("#walletOfflineLogType").val();
        } else {
            partnerWalletOfflineLogCriteria.type = null;
        }

        partnerWalletOfflineLogCriteria.offset = 1;

        post("/manage/partnerWalletLog/exportOffLineExcel", partnerWalletOfflineLogCriteria);


    }


    $(function () {
        getPartnerWalletOnLineLogByCriteria();
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
            $('#date-end1').daterangepicker({
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
                $('#date-end1 span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                    + end.format('YYYY/MM/DD HH:mm:ss'));
            });
        });
        
        
    })

function getPartnerWalletOnLineLogByCriteria() {
    var dateStr = $('#date-end span').text().split("-");
    if (dateStr != null && dateStr != '') {
        var startDate = dateStr[0].replace(/-/g, "/");
        var endDate = dateStr[1].replace(/-/g, "/");
        partnerWalletOnlineLogCriteria.createdStartDate = startDate;
        partnerWalletOnlineLogCriteria.createdEndDate = endDate;
    }

    if ($("#walletOnlineLogType").val() != "") {
        partnerWalletOnlineLogCriteria.type = $("#walletOnlineLogType").val();
    } else {
        partnerWalletOnlineLogCriteria.type = null;
    }

    partnerWalletOnlineLogCriteria.offset = 1;
    getPartnerWalletOnLineLogByAjax(partnerWalletOnlineLogCriteria);
    
}


    function getPartnerWalletOnLineLogByAjax(partnerWalletOnlineLogCriteria) {
        walletOnlineLogContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/partnerWalletLog/getPartnerWalletOnLineLogByAjax",
            async: false,
            data: JSON.stringify(partnerWalletOnlineLogCriteria),
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
                    initPage(partnerWalletOnlineLogCriteria.offset, totalPage);
                }
                if (init) {
                    initPage(1, totalPage);
                }
                for (i = 0; i < content.length; i++) {

                    var contentStr = '<tr><td>' + content[i].id + '</td>';

                    contentStr +=
                        '<td><span>'
                        + new Date(content[i].createDate).format('yyyy-MM-dd HH:mm:ss')
                        + '</span></td>';
                    contentStr += '<td>' + content[i].changeMoney/100.0 + '</td>';
                    if(content[i].type==16001){
                        contentStr += '<td>锁定会员线上消费</td>';
                    }else if(content[i].type==16002){
                        contentStr += '<td>锁定会员成为合伙人</td>';
                    }else if(content[i].type==16003){
                        contentStr += '<td>提现</td>';
                    }else if(content[i].type==16004){
                        contentStr += '<td>用户未接受微信红包</td>';
                    }else if(content[i].type==16005){
                        contentStr += '<td>驳回提现请求</td>';
                    }else {
                        contentStr += '<td>--</td>';
                    }
                    contentStr += '<td>' + content[i].orderSid + '</td>';
                    contentStr += '<td>' + content[i].beforeChangeMoney/100.0 + '</td>';
                    contentStr += '<td>' + content[i].afterChangeMoney /100.0+ '</td>';
                    walletOnlineLogContent.innerHTML += contentStr;
                }
            }});}

    function initPage(page, totalPage) {
        $('#tcdPageCode').unbind();
        $("#tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                partnerWalletOnlineLogCriteria.offset = p;
                init = 0;
                getPartnerWalletOnLineLogByAjax(partnerWalletOnlineLogCriteria);
            }
        });
    }

    function exportOnLineExcel() {
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            partnerWalletOnlineLogCriteria.createdStartDate = startDate;
            partnerWalletOnlineLogCriteria.createdEndDate = endDate;
        }

        if ($("#walletOnlineLogType").val() != "") {
            partnerWalletOnlineLogCriteria.type = $("#walletOnlineLogType").val();
        } else {
            partnerWalletOnlineLogCriteria.type = null;
        }

        partnerWalletOnlineLogCriteria.offset = 1;
        post("/manage/partnerWalletLog/exportOnLineExcel", partnerWalletOnlineLogCriteria);
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
function goBack() {
   var goBackUrl=$("#goBackUrl").val();
   location.href=goBackUrl;
}
</script>
</body>
</html>

