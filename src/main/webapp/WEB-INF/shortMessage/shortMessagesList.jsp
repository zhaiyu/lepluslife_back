<%--
  Created by IntelliJ IDEA.
  User: lss
  Date: 16/5/9
  Time: 下午3:56
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <style>
        thead th, tbody td {
            text-align: center;
        }

        #myTab {
            margin-bottom: 10px;
        }

        .modal-content > p {
            text-align: center;
            padding: 30px 0;
        }

        .remarks {
            width: 10%;
        }

        .remarksText {
            width: 150px;
            display: block;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        #more {
            position: absolute;
            width: 200px;
            background-color: rgba(0, 0, 0, 0.7);
            z-index: 100;
            color: #FFFFFF;
            padding: 10px;
            font-size: 16px;
            -webkit-border-radius: 0 10px 10px 10px;
            -moz-border-radius: 0 10px 10px 10px;
            border-radius: 0 10px 10px 10px;
        }

        .toggleRelease > div {
            width: 90%;
            margin: 20px auto;
        }

        .toggleRelease > div:first-child {
            margin-top: 40px;
        }

        .toggleRelease > div:last-child {
            margin-bottom: 40px;
        }

        .toggleRelease > div > div {
            float: left;
            width: 80%;
        }

        .toggleRelease > div > div:first-child {
            width: 20%;
        }

        .toggleTable {
            width: 90%;
            margin: 0 auto;
        }

        .w-toggle .pagination {
            margin: 0 !important;
        }

        .w-toggle .modal-footer {
            margin-top: 100px !important;
            text-align: center !important;
        }

        .toggleRelease > div:after {
            content: '\20';
            display: block;
            clear: both;
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
                <h3>短信看板</h3>
                <hr>
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-3">
                        <label for="date-end">发送时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-2">
                        <label>发送场景</label>
                        <select class="form-control" id="fscj">
                            <option>${category}</option>
                            <c:forEach items="${categoryList}" var="category">
                                <option>${category}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label>状态</label>
                        <select class="form-control" id="status">
                            <option></option>
                            <option>提交成功</option>
                            <option>提交失败</option>
                        </select>
                    </div>
                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 18px"
                                onclick="searchShortMessageByCriteria()">筛选z
                        </button>
                    </div>
                </div>

                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>批次ID</th>
                                <th>发送时间</th>
                                <th>发送场景</th>
                                <th>状态</th>
                                <th>发送对象</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="w-UserList"></tbody>
                        </table>
                    </div>
                    <nav class="pull-right">
                        <div class="tcdPageCode" style="display: inline;"></div>
                        <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!--删除提示框-->
<div class="modal w-toggle" id="createWarn">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">发送详情</h4>
            </div>
            <div class="toggleRelease">
                <div>
                    <div>发送时间：</div>
                    <div id="shortMessageSendTime"></div>
                </div>
                <div>
                    <div>发送状态：</div>
                    <div id="shortMessageSendState">发送成功</div>
                </div>
                <div>
                    <div>发送内容：</div>
                    <div id="shortMessageSendContent"></div>
                </div>
                <div>
                    <div>发送对象：</div>
                    <div id="shortMessageSendAcount"></div>
                </div>
            </div>
            <div class="toggleTable">
                <div id="toggleContent" class="tab-content">
                    <div class="tab-pane fade in active">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>会员ID</th>
                                <th>微信头像</th>
                                <th>微信昵称</th>
                                <th>手机号</th>
                            </tr>
                            </thead>
                            <tbody id="messageDatilList">
                            <tr>

                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <a id="btn0"></a>
                    <a id="datilTotalElements"></a>
                    <a id="sjzl"></a>
                    <a onclick="firstPage()" id="btn1">首页</a>
                    <a onclick="previousPage()" id="btn2">上一页</a>
                    <a onclick="nextPage()" id="btn3">下一页</a>
                    <a onclick="trailerPage()" id="btn4">尾页</a>
                    <a>转到 </a>
                    <input id="skipPage" type="text" size="1" maxlength="4"/>
                    <a>页 </a>
                    <a onclick="skipPage()">跳转</a>
                    <table id="mytable" align="center">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">知道了</button>
            </div>
        </div>
    </div>
</div>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var detailsReqId = null;
    var detailsOffset = 1;
    var datilTotalElements = null;
    var shortMessageCriteria = {};
    var flag = true;
    var init1 = 0;

    var financialContent = document.getElementById("financialContent");

    function showDlog(reqid) {
        detailsReqId = reqid;
        var offset = 1;
        getShortMessageDetailsByAjax(reqid, offset);
        $("#createWarn").modal("toggle");
    }
    $(function () {
//        提示框
        $(".createWarn").click(function () {
            $("#createWarn").modal("toggle");
        });
    })
    //    时间选择器
    $(document).ready(function () {
        $('#date-end').daterangepicker({
                                           maxDate: moment(), //最大时间
                                           showDropdowns: true,
                                           showWeekNumbers: false, //是否显示第几周
                                           timePicker: true, //是否显示小时和分钟
                                           timePickerIncrement: 60, //时间的增量，单位为分钟
                                           timePicker12Hour: false, //是否使用12小时制来显示时间
                                           ranges: {
                                               '最近1小时': [moment().subtract('hours', 1), moment()],
                                               '今日': [moment().startOf('day'), moment()],
                                               '昨日': [moment().subtract('days', 1).startOf('day'),
                                                      moment().subtract('days', 1).endOf('day')],
                                               '最近7日': [moment().subtract('days', 6), moment()],
                                               '最近30日': [moment().subtract('days', 29), moment()]
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
                                                            '七月', '八月', '九月', '十月', '十一月', '十二月'],
                                               firstDay: 1
                                           }
                                       }, function (start, end, label) {//格式化日期显示框
            $('#date-end span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                                     + end.format('YYYY/MM/DD HH:mm:ss'));
        });
        shortMessageCriteria.offset = 1;
        getShortMessageByAjax(shortMessageCriteria);
    })

    function getShortMessageByAjax(shortMessageCriteria) {

        $.ajax({
                   type: "post",
                   url: "/manage/getShortMessageByAjax",
                   async: false,
                   data: JSON.stringify(shortMessageCriteria),
                   contentType: "application/json",
                   success: function (data) {

                       var page = data.data;
                       var totalPage = page.totalPages;
                       $("#totalElements").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag) {
                           flag = false;
                           initPage(shortMessageCriteria.offset, totalPage);
                       }

                       for (var i = 0; i < JSON.stringify(data.data.content.length); i++) {
                           var time = new Date(data.data.content[i].sendDate);
                           var shortMEssageId = data.data.content[i].id;
                           $("#w-UserList").append(
                                   $("<tr></tr>").append(
                                           $("<td></td>").html(data.data.content[i].reqid)
                                   ).append(
                                           $("<td></td>").html(time.toLocaleString())
                                   ).append(
                                           $("<td></td>").html(data.data.content[i].shortMessageScene.name)
                                   ).append(
                                           $("<td></td>").html(data.data.content[i].reqMsg)
                                   ).append(
                                           $("<td></td>").html(data.data.content[i].userAmount)
                                   ).append(
                                           $("<td></td>").append(
                                                   $("<input />").attr("type",
                                                                       "button").attr("class",
                                                                                      "btn btn-xs btn-primary select-btn createWarn").attr("onclick",
                                                                                                                                           "showDlog("
                                                                                                                                           + JSON.stringify(data.data.content[i].reqid)
                                                                                                                                           + ")").val("查看详情")
                                           )
                                   )
                           );

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
                                             shortMessageCriteria.offset = p;
                                             init1 = 0;
                                             searchShortMessageByCriteria(shortMessageCriteria);
                                         }
                                     });
    }

    function searchShortMessageByCriteria() {
        detailsOffset = 1;
        $("#w-UserList").empty();
        init1 = 1;
        shortMessageCriteria.amount = $('#customer-amount').val() * 100;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            shortMessageCriteria.startDate = startDate;
            shortMessageCriteria.endDate = endDate;
        }
        if ($("#status").val() == "提交成功") {
            shortMessageCriteria.reqCode = "00";
        } else if ($("#status").val() == "提交失败") {
            shortMessageCriteria.reqCode = "";
        } else {
            shortMessageCriteria.reqCode = null;
        }
        if ($("#fscj").val() != "" && $("#fscj").val() != null) {
            shortMessageCriteria.category = $("#fscj").val();
        } else {
            shortMessageCriteria.category = null;
        }
        getShortMessageByAjax(shortMessageCriteria);
    }

    function getShortMessageDetailsByAjax(reqId, offset2) {

        $.ajax({
                   type: "post",
                   url: "/manage/shortMessage/serchDetails",
                   async: false,
                   data: JSON.stringify({"reqId": reqId, "offset": offset2}),
                   contentType: "application/json",
                   success: function (data) {
                       datilTotalElements = data.data.totalElements;
                       $("#datilTotalElements").html("共" + datilTotalElements + "条");
                       $("#messageDatilList").empty();
                       for (var i = 0; i < JSON.stringify(data.data.content.length); i++) {
                           $("#messageDatilList").append(
                                   $("<tr></tr>").append(
                                           $("<td></td>").html(data.data.content[i].userSid)
                                   ).append(
                                           "<td><img width='30px' src="
                                           + data.data.content[i].head_image_url + " alt=...>"
                                   ).append(
                                           $("<td></td>").html(data.data.content[i].nickname)
                                   ).append(
                                           $("<td></td>").html(data.data.content[i].phone_number)
                                   ).append(
                                           "<input id='datilTotalPages' type='hidden' value='"
                                           + data.data.totalPages + "'>"
                                   ).append(
                                           "<input id='sendDate' type='hidden' value='"
                                           + data.data.sendDate + "'>"
                                   ).append(
                                           "<input id='sendState' type='hidden' value='"
                                           + data.data.sendState + "'>"
                                   ).append(
                                           "<input id='shortMessageContent' type='hidden' value='"
                                           + data.data.shortMessageContent + "'>"
                                   ).append(
                                           "<input id='sendAmount' type='hidden' value='"
                                           + data.data.sendAmount + "'>"
                                   )
                           );

                           $("#shortMessageSendTime").html(
                                   "<span>" + data.data.sendDate + "</span>"
                           );
                           $("#shortMessageSendContent").html(
                                   "<span>" + data.data.shortMessageContent + "</span>"
                           );
                           $("#shortMessageSendAcount").html(
                                   "<span>" + data.data.sendAcount + "</span>"
                           );
                           $("#shortMessageSendState").html(
                                   "<span>" + data.data.successAcount + "</span>" + "条成功" + "<span>"
                                   + data.data.defeatedAcount + "</span>" + "条失败"
                           );

                       }
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
    function firstPage() {
        detailsOffset = 1;
        getShortMessageDetailsByAjax(detailsReqId, 1);
    }
    function trailerPage() {
        detailsOffset = $("#datilTotalPages").val();
        getShortMessageDetailsByAjax(detailsReqId, detailsOffset);
    }
    function previousPage() {
        detailsOffset = detailsOffset - 1;
        getShortMessageDetailsByAjax(detailsReqId, detailsOffset);
    }
    function nextPage() {
        detailsOffset = detailsOffset + 1;
        getShortMessageDetailsByAjax(detailsReqId, detailsOffset);
    }
    function skipPage() {
        var skipPage = $("#skipPage").val();
        var reg = new RegExp("^[0-9]*$");
        if (!reg.test(skipPage)) {
            detailsOffset = 1;
        } else {
            detailsOffset = skipPage;
        }
        getShortMessageDetailsByAjax(detailsReqId, detailsOffset);
    }
</script>
</body>
</html>