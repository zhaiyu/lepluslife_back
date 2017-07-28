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
    <%@include file="../../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main">
            <div class="container-fluid">
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="ledgerSid">通道结算单号</label>
                        <input type="text" id="ledgerSid" class="form-control"
                               placeholder="请输入通道结算单号"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="orderSid">转账请求号</label>
                        <input type="text" id="orderSid" class="form-control"
                               placeholder="请输入转账请求号"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="ledgerNo">易宝子商户号</label>
                        <input type="text" id="ledgerNo" class="form-control"
                               placeholder="请输入易宝子商户号"/>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="state">状态</label>
                        <select class="form-control" id="state">
                            <option value="">所有状态</option>
                            <option value="0">待转账</option>
                            <option value="1">转账成功</option>
                        </select>
                    </div>
                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchOrderByCriteria()">查询
                        </button>
                    </div>
                </div>
            </div>
            <div id="myTabContent" class="tab-content">
                <div class="tab-pane fade in active" id="tab1">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr class="active">
                            <th>结算单号</th>
                            <th>转账类型</th>
                            <th>清算日期</th>
                            <th>子商户号</th>
                            <th>转账金额</th>
                            <th>转账请求时间</th>
                            <th>转账成功时间</th>
                            <th>转账状态</th>
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
<%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>

    var transferCriteria = {};
    var flag = true;
    var init1 = 0;
    var orderContent = document.getElementById("orderContent");
    $(function () {
        // 时间选择器
        $(document).ready(function () {
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
                                                                                   1).startOf(
                                                              'day'),
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
        transferCriteria.offset = 1;
        getOrderByAjax(transferCriteria);
    });
    function getOrderByAjax(refundCriteria) {
        orderContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/transfer/ledger/findByCriteria",
                   async: false,
                   data: JSON.stringify(refundCriteria),
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
                           initPage(refundCriteria.offset, totalPage);
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       var orderContent = document.getElementById("orderContent");
                       for (var i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].orderSid + '</td>';
                           if (content[i].type == 1) {
                               contentStr += '<td>实时转账</td>';
                           } else if (content[i].type == 2) {
                               contentStr += '<td>合并转账</td>';
                           } else {
                               contentStr += '<td>未知</td>';
                           }
                           contentStr += '<td>' + content[i].tradeDate + '</td>';
                           contentStr += '<td>' + content[i].ledgerNo + '</td>';
                           contentStr +=
                               '<td><span>' + content[i].actualTransfer / 100.0 + '</span></td>';
                           contentStr +=
                               '<td>'
                               + new Date(content[i].dateCreated).format('yyyy-MM-dd HH:mm:ss')
                               + '</td>';
                           contentStr +=
                               '<td>'
                               + new Date(content[i].dateCompleted).format('yyyy-MM-dd HH:mm:ss')
                               + '</td>';
                           var stateVal = '';
                           if (content[i].state == 0) {
                               stateVal = '待转账';
                           } else if (content[i].state == 1) {
                               stateVal = '已成功';
                           } else if (content[i].state == 2) {
                               stateVal = '转账失败';
                           } else if (content[i].state == 3) {
                               stateVal = '转账中(非终态)';
                           } else {
                               stateVal = '待查询';
                           }
                           if (content[i].repair == 1) {
                               stateVal += '(有补单)';
                           }
                           contentStr += '<td>' + stateVal + '</td>';
                           if (content[i].state == 2) {
                               contentStr += '<td><button class="btn btn-primary" onclick="retry(\''
                                             + content[i].id
                                             + '\')">手动补单</button></td>';
                           } else {
                               contentStr += '<td></td>';
                           }
                           orderContent.innerHTML += contentStr;
                       }
                   }
               });
    }
    /******************************手动补单************************************/
    function retry(transferId) {
        $.post('/manage/transfer/retry?transferId=' + transferId, function (map) {
            if (eval(map.code) === 1) {
                alert("转账成功");
            } else {
                alert('请求错误，错误码：' + map.code + '(' + map.msg + ')');
            }
        });
    }
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             transferCriteria.offset = p;
                                             init1 = 0;
                                             getOrderByAjax(transferCriteria);
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
    function searchOrderByCriteria() {
        setCriteria();
        getOrderByAjax(transferCriteria);
    }
    function setCriteria() {
        transferCriteria.offset = 1;
        init1 = 1;
        if ($("#ledgerSid").val() != "" && $("#ledgerSid").val() != null) {
            transferCriteria.ledgerSid = $("#ledgerSid").val();
        } else {
            transferCriteria.ledgerSid = null;
        }
        if ($("#orderSid").val() != "" && $("#orderSid").val() != null) {
            transferCriteria.orderSid = $("#orderSid").val();
        } else {
            transferCriteria.orderSid = null;
        }
        if ($("#ledgerNo").val() != "" && $("#ledgerNo").val() != null) {
            transferCriteria.ledgerNo = $("#ledgerNo").val();
        } else {
            transferCriteria.ledgerNo = null;
        }
        if ($("#state").val() != "" && $("#state").val() != null) {
            transferCriteria.state = $("#state").val();
        } else {
            transferCriteria.state = null;
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
        post("/manage/transfer/ledger/export", transferCriteria);
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