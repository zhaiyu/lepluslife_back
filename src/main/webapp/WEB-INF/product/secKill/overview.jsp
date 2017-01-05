<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../commen.jsp" %>
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
    <style>
        thead th, tbody td {
            text-align: center;
        }

        #myTab {
            margin-bottom: 10px;
        }

        .del {
            background-color: red;
            color: #FFFFFF;
            margin: 0 5px;
            padding: 2px 8px;
        }

        .btn:hover {
            color: #FFFFFF !important;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>

    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
</head>

<body>
<div id="topIframe">
    <%@include file="../../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main">
            <div class="container-fluid" style="padding-top: 20px">
                <button type="button" class="btn btn-primary " style="margin:10px;"
                        onclick="goLimitEditPage()">今天()
                </button>
                <button type="button" class="btn btn-primary " style="margin:10px;"
                        onclick="goLimitEditPage()">明天()
                </button>
                <button type="button" class="btn btn-primary " style="margin:10px;"
                        onclick="goLimitEditPage()">后天()
                </button>
                <div class="form-group col-md-4">
                    <label for="date-end">选择日期</label>

                    <div id="date-end" class="form-control">
                        <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                        <span id="searchDateRange"></span>
                        <b class="caret"></b>
                    </div>
                </div>

                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab" onclick="searchByType(1)">上架</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab" onclick="searchByType(0)">下架</a>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr id="tr" class="active">
                                <th>商品ID</th>
                                <th>商品名称</th>
                                <th>兑换价格</th>
                                <th>销量</th>
                                <th>剩余库存</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="bannerContent">
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
    <%@include file="../../common/bottom.jsp" %>
</div>

<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script>
    var productCriteria = {};
    var flag = true;
    var init1 = 0;
    var bannerContent = document.getElementById("bannerContent");

    $(function () {
        // tab切换
        var tableType = "${type}";
        if (tableType == 1) {
            $('#myTab li:eq(0) a').tab('show');
        } else if (tableType == 2) {
            $('#myTab li:eq(1) a').tab('show');
        }

        $('#productPic').fileupload({
                                        dataType: 'json',
                                        maxFileSize: 5000000,
                                        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                        add: function (e, data) {
                                            data.submit();
                                        },
                                        done: function (e, data) {
                                            var resp = data.result;
                                            $('#picture').attr('src',
                                                               '${ossImageReadRoot}/'
                                                               + resp.data);
                                        }
                                    });

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
        });
        productCriteria.state = 1;
        productCriteria.offset = 1;
        getBannerListByType(productCriteria);
    });
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
    };
</script>
<script>

</script>
</body>
</html>

