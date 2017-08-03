<%--
  Created by IntelliJ IDEA.
  User: zhaiyu
  Date: 17/07/15
  Time: 上午10:02
  To change this template use File | Settings | File Templates.
--%>
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
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
            <div class="container-fluid">
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-5">
                        <label for="date-end" id="date-content">清算日期</label>
                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="merchantID">门店ID</label>
                        <input id="merchantID" type="text"
                               class="form-control"
                               placeholder="请输入门店ID"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="merchantID">门店名称</label>
                        <input id="merchantName" type="text"
                               class="form-control"
                               placeholder="请输入门店名称"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="ledgerNo">易宝商户号</label>
                        <input id="ledgerNo" type="text"
                               class="form-control"
                               placeholder="请输入易宝商户号"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="ledgerSid">通道结算单</label>
                        <input id="ledgerSid" type="text"
                               class="form-control"
                               placeholder="请输入通道结算单号"/>
                    </div>
                    <%--<div class="forDifferent" style="display: none;">
                        <div class="form-group col-md-2">
                            <label>结算状态</label>
                            <select class="form-control" id="billingStatus">
                                <option value="">全部</option>
                                <option value="3">已结算</option>
                                <option value="4">未结算</option>
                                <option value="0">挂账</option>
                            </select>
                        </div>
                        <div class="form-group col-md-2">
                            <label>处理状态</label>
                            <select class="form-control" id="dealStatus">
                                <option value="">全部</option>
                                <option value="3">已处理</option>
                                <option value="4">未处理</option>
                            </select>
                        </div>
                    </div>--%>
                    <div class="form-group col-md-1">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchFinancialByCriteria()">查询
                        </button>
                    </div>
                </div>

                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active" id="head-content">
                            </tr>
                            </thead>
                            <tbody id="financialContent">
                            </tbody>
                        </table>
                    </div>

                    <div class="tcdPageCode" style="display: inline;">
                    </div>
                    <%--<div style="display: inline;"> 共有 <span id="totalElements"></span> 个&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;跳转至&nbsp;
                        <input id="toPage" type="text" style="width:60px" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9]/g,'')"/>&nbsp;页
                        <button class="btn btn-primary" style="width:50px;" onclick="searchFinancialByPage()">GO</button>
                    </div>--%>

                    <button class="btn btn-primary pull-right" style="margin-top: 5px"
                            onclick="exportExcel()">导出表格
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../../common/bottom.jsp" %>
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
                        id="transfer-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>
<div class="modal" id="hover">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <h4>设为挂账吗?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="hover-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>
<%--点击确认处理弹窗--%>
<div class="modal" id="deal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <h4>是否要根据差错单内容修改对账单?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="deal-confirm">确认
                </button>
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
    var settlementCriteria = {};
    var flag = true, init1 = 0;
    var financialContent = document.getElementById("financialContent");
    var headContent = document.getElementById("head-content");
    var dateContent = document.getElementById("date-content");
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
        settlementCriteria.offset = 1;
        getFinancialByAjax(settlementCriteria);
    })

    function getFinancialByAjax(settlementCriteria) {
        financialContent.innerHTML = "";
        headContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/settlement/store/findByCriteria",
                   async: false,
                   data: JSON.stringify(settlementCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var page = data.data.page;
                       var merchants = data.data.merchants;
                       var content = page.content;
                       var totalPage = page.totalPages;
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag) {
                           initPage(settlementCriteria.offset, totalPage);
                           flag = false;
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       headContent.innerHTML +=
                               '<th>结算单号</th><th>门店SID</th><th>门店名称</th><th>清算日期</th><th>易宝商户号</th><th>微信应入账</th><th>支付宝应入账</th><th>鼓励金应入账</th>';
                       headContent.innerHTML +=
                               '<th>退款笔数</th><th>退款支出</th><th>实际应转账</th><th>通道结算单</th>';
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].orderSid + '</td>';
                           if(merchants[i]!=null&&merchants[i].id!="") {
                               contentStr +=
                                       '<td><span>' + merchants[i].merchantSid + '</span></td>';
                               contentStr +=
                                       '<td><span>' + merchants[i].name + '</span></td>'
                           }else  {
                               contentStr +=
                                       '<td><span>--</span></td>';
                               contentStr +=
                                       '<td><span>--</span></td>';
                           }
                           contentStr +=
                                   '<td><span>'
                                   + new Date(content[i].tradeDate).format('yyyy-MM-dd')          // 清算日期
                                   + '</span></td>';
                           contentStr +=
                                   '<td><span>' + content[i].ledgerNo + '</span></td>';
                           contentStr +=
                                   '<td><span>' + content[i].wxTruePayTransfer/100.0 + '</span></td>';
                           contentStr +=
                                   '<td><span>' + content[i].aliTruePayTransfer/100.0 + '</span></td>';
                           contentStr +=
                                   '<td><span>' + content[i].scoreTransfer/100.0 + '</span></td>';
                           contentStr +=
                                   '<td><span>' + content[i].refundNumber + '</span></td>';
                           contentStr +=
                                   '<td><span>' + content[i].refundExpend/100.0 + '</span></td>';
                           contentStr +=
                                   '<td><span>' + content[i].actualTransfer/100.0 + '</span></td>';
                           contentStr +=
                                   '<td><span>' + content[i].ledgerSid + '</span></td></tr>';
                           financialContent.innerHTML += contentStr;
                       }
                       initPage(settlementCriteria.offset, totalPage);
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
    // 根据条件查询
    function searchFinancialByCriteria() {
        setCriteria();
        getFinancialByAjax(settlementCriteria);
    }
    // 设置查询条件
    function setCriteria() {
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        var startDate = null;
        var endDate = null;
        if(dateStr.length>0 && dateStr[0]!=null&&dateStr[0]!='') {
            startDate = dateStr[0].replace(/-/g, "/");
            endDate = dateStr[1].replace(/-/g, "/");
            settlementCriteria.startDate = startDate;
            settlementCriteria.endDate = endDate;
        }
        if ($("#merchantID").val()!=null && $("#merchantID").val()!='') {
            settlementCriteria.merchantId == $("#merchantID").val();
        }else {
            settlementCriteria.merchantId == null;
        }
        if ($("#ledgerNo").val() != "" && $("#ledgerNo").val() != null) {
            settlementCriteria.ledgerNo = $("#ledgerNo").val();
        } else {
            settlementCriteria.ledgerNo = null;
        }
        if ($("#merchantName").val() != "" && $("#merchantName").val() != null) {
            settlementCriteria.merchantName = $("#merchantName").val();
        } else {
            settlementCriteria.merchantName = null;
        }
        if ($("#ledgerSid").val()!= ""&& $("#ledgerSid").val()!= null) {
            settlementCriteria.ledgerSid = $("#ledgerSid").val();
        } else {
            settlementCriteria.ledgerSid = null;
        }
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
        setCriteria();
        post("/manage/settlement/store/export", settlementCriteria);
    }
    function initPage(currPage, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: currPage,
                                         backFn: function (p) {
                                             //单击回调方法，p是当前页码
                                             init1 = 0;
                                             settlementCriteria.offset = p;
                                             getFinancialByAjax(settlementCriteria);
                                         }
                                     });
    }
</script>
</body>
</html>