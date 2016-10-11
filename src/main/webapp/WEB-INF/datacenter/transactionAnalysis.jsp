<%--
  Created by IntelliJ IDEA.
  User: lss
  Date: 16/10/8
  Time: 上午9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>乐+生活 后台模板管理系统</title>
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <!--<link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">-->
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <style>thead th, tbody td {
        text-align: center;
    }

    #myTab {
        margin-bottom: 10px;
    }</style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script src="${resourceUrl}/js/jquery.page.js"></script>
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
                    <h4>交易分析</h4> <br>

                    <div class="form-group col-md-5">

                        <label for="date-end">消费完成时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-1">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="getTransactionAnalysisByCriteria()">查询
                        </button>
                    </div>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li class="active"><a href="#tab1" data-toggle="tab">每日账单</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="container" id="echart-main" style="height:400px;"></div>
                    <div class="tab-pane fade in active" id="tab1">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>交易完成日期</th>
                                <th>微信实付金额</th>
                                <th>微信手续费</th>
                                <th>微信平台入账</th>
                                <th>扫码日实付</th>
                                <th>商城日实付</th>
                            </tr>
                            </thead>
                            <tbody id="transactionAnalysisContent">
                            </tbody>
                        </table>
                    </div>
                    <div class="tcdPageCode" style="display: inline;">
                    </div>
                    <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
</div>
</div>
</div>
<!--添加提示框-->
<div class="modal" id="createWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">上架</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="productNum" class="col-sm-2 control-label">商品序号</label>

                        <div class="col-sm-4">
                            <input type="number" class="form-control" id="productNum"
                                   placeholder="Email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productName" class="col-sm-2 control-label">商品名称</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="productName"
                                   placeholder="请输入商品名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productPic" class="col-sm-2 control-label">商品图片</label>

                        <div class="col-sm-4">
                            <img src="" alt="...">
                            <input type="file" class="form-control" id="productPic">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
            </div>
        </div>
    </div>
</div>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<!--<script src="js/bootstrap-datetimepicker.min.js"></script>-->
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<!--<script src="js/bootstrap-datetimepicker.zh-CN.js"></script>-->
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/echarts.min.js"></script>
<script>
    var transactionAnalysisCriteria = {};
    var flag = true;
    var init1 = 0;
    var transactionAnalysisContent = document.getElementById("transactionAnalysisContent");
    var myChart = echarts.init(document.getElementById('echart-main'));
    $(function () {
//        tab切换
        $('#myTab li:eq(0) a').tab('show');
//        提示框
        $(".deleteWarn").click(function () {
            $("#deleteWarn").modal("toggle");
        });
        $(".createWarn").click(function () {
            $("#createWarn").modal("toggle");
        });
        $(document).ready(function () {
            //$('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss') + ' - ' + moment().format('YYYY/MM/DD HH:mm:ss'));
            $('#date-end').daterangepicker({
                                               maxDate: moment(), //最大时间
//                                               dateLimit : {
//                                                   days : 30
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
        transactionAnalysisCriteria.startDate = "nullValue";
        transactionAnalysisCriteria.endDate = "nullValue";
        getTransactionAnalysisByAjax(transactionAnalysisCriteria);
        getTransactionAnalysisFormByAjax(transactionAnalysisCriteria);
    })
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
    //画折线图的ajax
    function getTransactionAnalysisByAjax(transactionAnalysisCriteria) {
        $.ajax({
                   type: "post",
                   url: "/manage/getTransactionAnalysisByAjax",
                   async: false,
                   data: JSON.stringify(transactionAnalysisCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var x = data.data.dateStrList;
                       var xStr = "";
                       for (i = 0; i < x.length; i++) {
                           xStr += x[x.length - i - 1] + ',';
                       }
                       xStr = xStr.substr(0, xStr.length - 1);
                       var dateArry = new Array()
                       dateArry = xStr.split(",");
                       var wxTruePayList = data.data.wxTruePayList;
                       var wxTruePayListStr = "";
                       for (i = 0; i < wxTruePayList.length; i++) {
                           wxTruePayListStr += wxTruePayList[wxTruePayList.length - i - 1] + ',';
                       }
                       wxTruePayListStr = wxTruePayListStr.substr(0, xStr.length - 1);
                       var wxTruePayArry = new Array()
                       wxTruePayArry = wxTruePayListStr.split(",");

                       var offlineWxTruePaylist = data.data.offlineWxTruePaylist;
                       var offlineWxTruePaylistStr = "";
                       for (i = 0; i < offlineWxTruePaylist.length; i++) {
                           offlineWxTruePaylistStr +=
                           offlineWxTruePaylist[offlineWxTruePaylist.length - i - 1] + ',';
                       }
                       offlineWxTruePaylistStr =
                       offlineWxTruePaylistStr.substr(0, offlineWxTruePaylistStr.length - 1);
                       var offlineWxTruePayArry = new Array()
                       offlineWxTruePayArry = offlineWxTruePaylistStr.split(",");

                       var onlineWxTruePaylist = data.data.onlineWxTruePaylist;
                       var onlineWxTruePaylistStr = "";
                       for (i = 0; i < onlineWxTruePaylist.length; i++) {
                           onlineWxTruePaylistStr +=
                           onlineWxTruePaylist[onlineWxTruePaylist.length - i - 1] + ',';
                       }
                       onlineWxTruePaylistStr =
                       onlineWxTruePaylistStr.substr(0, onlineWxTruePaylistStr.length - 1);
                       var onlineWxTruePayArry = new Array()
                       onlineWxTruePayArry = onlineWxTruePaylistStr.split(",");

                       var wxCommissionlist = data.data.wxCommissionlist;
                       var wxCommissionlistStr = "";
                       for (i = 0; i < wxCommissionlist.length; i++) {
                           wxCommissionlistStr +=
                           wxCommissionlist[wxCommissionlist.length - i - 1] + ',';
                       }
                       wxCommissionlistStr =
                       wxCommissionlistStr.substr(0, wxCommissionlistStr.length - 1);
                       var wxCommissionArry = new Array()
                       wxCommissionArry = wxCommissionlistStr.split(",");

                       myChart.setOption({
                                             tooltip: {},
                                             legend: {
                                                 data: ['微信金额实付款', '扫码日实付', '商城日实付']
                                             },
                                             xAxis: {
                                                 data: dateArry
                                             },
                                             yAxis: {},
                                             series: [
                                                 {
                                                     name: '微信金额实付款',
                                                     type: 'line',
                                                     data: wxTruePayArry
                                                 },
                                                 {
                                                     name: '扫码日实付',
                                                     type: 'line',
                                                     data: offlineWxTruePayArry
                                                 },
                                                 {
                                                     name: '商城日实付',
                                                     type: 'line',
                                                     data: onlineWxTruePayArry
                                                 }
                                             ],

                                             visualMap: {
                                                 inRange: {}

                                             }
                                         });
                   }
               })
    }
    //画表格的ajax
    function getTransactionAnalysisFormByAjax(transactionAnalysisCriteria) {
        $.ajax({
                   type: "post",
                   url: "/manage/getTransactionAnalysisFormByAjax",
                   async: false,
                   data: JSON.stringify(transactionAnalysisCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var pageList = data.data.pageList;

                       $("#totalElements").html(pageList[1]);
                       var totalPage = pageList[0];

                       if (init1) {
                           initPage(1, totalPage);
                       }
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag) {
                           flag = false;
                           initPage(transactionAnalysisCriteria.offset, totalPage);
                       }
                       var transactionAnalysisContent = document.getElementById("transactionAnalysisContent");
                       var x = data.data.dateStrFormList;
                       var xStr = "";
                       for (i = 0; i < x.length; i++) {
                           xStr += x[x.length - i - 1] + ',';
                       }
                       xStr = xStr.substr(0, xStr.length - 1);
                       var dateArry = new Array()
                       dateArry = xStr.split(",");
                       var wxTruePayList = data.data.wxTruePayFormList;
                       var wxTruePayListStr = "";
                       for (i = 0; i < wxTruePayList.length; i++) {
                           wxTruePayListStr += wxTruePayList[wxTruePayList.length - i - 1] + ',';
                       }
                       wxTruePayListStr = wxTruePayListStr.substr(0, xStr.length - 1);
                       var wxTruePayArry = new Array()
                       wxTruePayArry = wxTruePayListStr.split(",");

                       var offlineWxTruePaylist = data.data.offlineWxTruePayFormList;
                       var offlineWxTruePaylistStr = "";
                       for (i = 0; i < offlineWxTruePaylist.length; i++) {
                           offlineWxTruePaylistStr +=
                           offlineWxTruePaylist[offlineWxTruePaylist.length - i - 1] + ',';
                       }
                       offlineWxTruePaylistStr =
                       offlineWxTruePaylistStr.substr(0, offlineWxTruePaylistStr.length - 1);
                       var offlineWxTruePayArry = new Array()
                       offlineWxTruePayArry = offlineWxTruePaylistStr.split(",");

                       var onlineWxTruePaylist = data.data.onlineWxTruePayFormList;
                       var onlineWxTruePaylistStr = "";
                       for (i = 0; i < onlineWxTruePaylist.length; i++) {
                           onlineWxTruePaylistStr +=
                           onlineWxTruePaylist[onlineWxTruePaylist.length - i - 1] + ',';
                       }
                       onlineWxTruePaylistStr =
                       onlineWxTruePaylistStr.substr(0, onlineWxTruePaylistStr.length - 1);
                       var onlineWxTruePayArry = new Array()
                       onlineWxTruePayArry = onlineWxTruePaylistStr.split(",");
                       var wxCommissionlist = data.data.wxCommissionFormList;
                       var wxCommissionlistStr = "";
                       for (i = 0; i < wxCommissionlist.length; i++) {
                           wxCommissionlistStr +=
                           wxCommissionlist[wxCommissionlist.length - i - 1] + ',';
                       }
                       wxCommissionlistStr =
                       wxCommissionlistStr.substr(0, wxCommissionlistStr.length - 1);
                       var wxCommissionArry = new Array()
                       wxCommissionArry = wxCommissionlistStr.split(",");

                       transactionAnalysisContent.innerHTML = '';
                       for (i = 0; i < dateArry.length; i++) {
                           var transactionAnalysisContentStr = '<tr><td>' + dateArry[i] + '</td>';
                           transactionAnalysisContentStr +=
                           '<td>' + wxTruePayArry[i] + '</td>';
                           transactionAnalysisContentStr +=
                           '<td>' + wxCommissionArry[i] + '</td>';
                           transactionAnalysisContentStr +=
                           '<td>' + (wxTruePayArry[i] * 1000000 - wxCommissionArry[i] * 1000000) / 1000000
                           + '</td>';
                           transactionAnalysisContentStr +=
                           '<td>' + offlineWxTruePayArry[i] + '</td>';
                           transactionAnalysisContentStr +=
                           '<td>' + onlineWxTruePayArry[i] + '</td></tr>';
                           transactionAnalysisContent.innerHTML += transactionAnalysisContentStr;
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
                                             transactionAnalysisCriteria.offset = p;
                                             init1 = 0;
                                             getTransactionAnalysisFormByAjax(transactionAnalysisCriteria);
                                         }
                                     });
    }
    function getTransactionAnalysisByCriteria() {
        transactionAnalysisCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        var startDate1 = dateStr[0].replace("/", "-");
        var endDate1 = dateStr[1].replace("/", "-");
        var startDate = startDate1.replace("/", "-");
        var endDate = endDate1.replace("/", "-");
        transactionAnalysisCriteria.endDate = endDate;
        transactionAnalysisCriteria.startDate = startDate;
        getTransactionAnalysisByAjax(transactionAnalysisCriteria);
        getTransactionAnalysisFormByAjax(transactionAnalysisCriteria);
    }
</script>


</body>
</html>

