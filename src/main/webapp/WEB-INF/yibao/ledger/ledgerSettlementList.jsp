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
                <div class="row">
                    <div class="form-group col-md-3">
                        <label for="date-end" id="date-content">清算日期</label>
                        <div id="date-end" class="form-control" style="margin-top: 20px">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="ledgerNo">易宝商户编号</label>
                        <input id="ledgerNo" type="text"
                               class="form-control"
                               placeholder="请输入易宝商户编号"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="merchantUserId">乐加商户ID</label>
                        <input id="merchantUserId" type="text"
                               class="form-control"
                               placeholder="请输入商户ID"/>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-2">
                        <label>结算状态</label>
                        <select class="form-control" id="state">
                            <option value="">全部</option>
                            <option value="0">待查询</option>
                            <option value="1">打款成功</option>
                            <option value="4">打款中</option>
                            <option value="2">已退回</option>
                            <option value="-1">打款失败</option>
                        </select>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="orderSid">通道结算单号</label>
                        <input id="orderSid" type="text"
                               class="form-control"
                               placeholder="请输入通道结算单号"/>
                    </div>
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
        dateContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/settlement/ledger/findByCriteria",
                   async: false,
                   data: JSON.stringify(settlementCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var page = data.data.page;
                       var content = page.content;
                       var merchants = data.data.merchantUsers;
                       var totalPage = page.totalPages;
                       $("#totalElements").html(page.totalElements);
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
                       headContent.innerHTML =
                           '<th>通道结算单号</th><th>易宝商户号</th><th>乐加商户ID</th><th>清算日期</th><th>日转账交易金额</th><th>实际转账金额</th>';
                       headContent.innerHTML +=
                           '<th>实际结算金额</th><th>结算状态</th><th>操作</th>';
                       for (var i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].orderSid + '</td>';
                           contentStr +=
                               '<td><span>' + content[i].ledgerNo + '</span></td>';
                           if (merchants[i] != null && merchants[i].name != "") {
                               contentStr +=
                                   '<td><span>' + merchants[i].name + '(' + merchants[i].id
                                   + ')</span></td>';
                           } else {
                               contentStr +=
                                   '<td><span>' + content[i].merchantUserId + '</span></td>';

                           }
                           contentStr +=
                               '<td><span>'
                               + new Date(content[i].tradeDate).format('yyyy-MM-dd')
                               + '</span></td>';
                           contentStr +=
                               '<td><span>' + content[i].totalTransfer / 100.0 + '</span></td>';
                           contentStr +=
                               '<td><span>' + content[i].actualTransfer / 100.0 + '</span></td>';
                           contentStr +=
                               '<td><span>' + content[i].actualTransfer / 100.0 + '</span></td>';
                           if (content[i].state == 0) {
                               contentStr += '<td>待查询</td>'
                           } else if (content[i].state == 1) {
                               contentStr += '<td>转账成功</td>';
                           } else if (content[i].state == 2) {
                               contentStr += '<td>已退回</td>';
                           } else if (content[i].state == 3) {
                               contentStr += '<td>已扣款未打款</td>';
                           } else if (content[i].state == 4) {
                               contentStr += '<td>打款中</td>';
                           } else if (content[i].state == -1) {
                               contentStr += '<td>打款失败</td>';
                           } else if (content[i].state == -2) {
                               contentStr += '<td>银行返回打款失败</td>';
                           } else {
                               contentStr += '<td>未知</td>';
                           }
                           contentStr +=
                               '<td><button class="btn btn-primary" onclick="searchState(\''
                               + content[i].id
                               + '\')">查询状态</button></td></tr>';
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
                            ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f"
                                : "\u5468")
                                : "") + week[this.getDay() + ""]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt =
                    fmt.replace(RegExp.$1,
                                (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr((""
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
        if (dateStr.length > 0 && dateStr[0] != null && dateStr[0] != '') {
            startDate = dateStr[0].replace(/-/g, "/");
            endDate = dateStr[1].replace(/-/g, "/");
            settlementCriteria.startDate = startDate;
            settlementCriteria.endDate = endDate;
        }
        if ($("#ledgerNo").val() != "" && $("#ledgerNo").val() != null) {
            settlementCriteria.ledgerNo = $("#ledgerNo").val();
        } else {
            settlementCriteria.ledgerNo = null;
        }
        if ($("#orderSid").val() != "" && $("#orderSid").val() != null) {
            settlementCriteria.orderSid = $("#orderSid").val();
        } else {
            settlementCriteria.orderSid = null;
        }
        if ($("#merchantUserId").val() != "" && $("#merchantUserId").val() != null) {
            settlementCriteria.merchantUserId = $("#merchantUserId").val();
        } else {
            settlementCriteria.merchantUserId = null;
        }
        if ($("#state").val() != "" && $("#state").val() != null) {
            settlementCriteria.state = $("#state").val();
        } else {
            settlementCriteria.state = null;
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
        post("/manage/settlement/ledger/export", settlementCriteria);
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
    function searchState(id) {
        $.get('/manage/settlement/querySettlement?settlementId=' + id, function (map) {
            if (eval(map.code) === 1) {
                var msg = '';
                //0=待查询，1=打款成功，2=已退回，3=已扣款未打款，4=打款中，-1=打款失败，-2=银行返回打款失败
                switch (eval(map.status)) {
                    case 1:
                        msg = "打款成功";
                        break;
                    case 2:
                        msg = "已退回";
                        break;
                    case 3:
                        msg = "已扣款未打款";
                        break;
                    case 4:
                        msg = "打款中";
                        break;
                    case -1:
                        msg = "打款失败";
                        break;
                    case -2:
                        msg = "银行返回打款失败";
                        break;
                    default:
                        msg = "未知状态";
                }
                alert(msg + '(' + map.info + ')');
            } else {
                alert('请求错误，错误码：' + map.code + '(' + map.msg + ')');
            }
        });
    }
</script>
</body>
</html>