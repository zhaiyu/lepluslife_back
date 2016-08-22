<%--
  Created by IntelliJ IDEA.
  User: wcg
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
    <link rel="stylesheet" href="${resourceUrl}/css/jqpagination.css"/>
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
    <script type="text/javascript" src="${resourceUrl}/js/jquery.jqpagination.min.js"></script>
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
                <ul id="myTab" class="nav nav-tabs" style="margin-top: 10px">
                    <li><a href="#tab1" data-toggle="tab"
                           onclick="searchFinancialByState(0)">待转账</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab"
                                          onclick="searchFinancialByState(1)">转账记录</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab"
                                          onclick="searchFinancialByState(2)">挂账记录</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <div class="row" style="margin-top: 30px">
                            <div class="form-group col-md-5">
                                <label for="date-end" id="date-content"></label>

                                <div id="date-end" class="form-control">
                                    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                    <span id="searchDateRange"></span>
                                    <b class="caret"></b>
                                </div>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="merchant-info">商户信息</label>
                                <input id="merchant-name" type="text" id="merchant-info"
                                       class="form-control"
                                       placeholder="请输入商户名称或ID"/>
                            </div>
                            <div class="form-group col-md-1">
                                <button class="btn btn-primary" style="margin-top: 24px"
                                        onclick="searchFinancialByCriteria()">查询
                                </button>
                            </div>
                        </div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active" id="head-content">
                            </tr>
                            </thead>
                            <tbody id="financialContent">
                            </tbody>
                        </table>
                    </div>

                    <div class="pagination">
                        <a href="#" class="first" data-action="first">&laquo;</a>
                        <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                        <input id="page" type="text" readonly="readonly" data-max-page="1"/>
                        <a href="#" class="next" data-action="next">&rsaquo;</a>
                        <a href="#" class="last" data-action="last">&raquo;</a>
                    </div>
                    <button class="btn btn-primary pull-right" style="margin-top: 5px"
                            onclick="exportExcel()">导出表格
                    </button>
                    <shiro:hasPermission name="financial:transfer">
                        <button class="btn btn-primary pull-right" style="margin-top: 5px"
                                id="batchTransfer">全部确认转账
                        </button>
                    </shiro:hasPermission>
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
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script>
    var financialCriteria = {};
    var financialContent = document.getElementById("financialContent");
    var headContent = document.getElementById("head-content");
    var dateContent = document.getElementById("date-content");
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
    })
    //    时间选择器
    $(document).ready(function () {
        $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss') + ' - '
                                 + moment().format('YYYY/MM/DD HH:mm:ss'));
        $('#date-end').daterangepicker({
                                           maxDate: moment(), //最大时间
                                           dateLimit: {
                                               days: 30
                                           }, //起止时间的最大间隔
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
        financialCriteria.offset = 1;
        financialCriteria.state = 0;
        getFinancialByAjax(financialCriteria);
    })
    function initPage(page, totalPage) {
        $('.pagination').jqPagination({
                                          current_page: page, //设置当前页 默认为1
                                          max_page: totalPage, //设置最大页 默认为1
                                          page_string: '当前第' + page + '页,共' + totalPage + '页',
                                          paged: function (page) {
                                              financialCriteria.offset = page;
                                              getFinancialByAjax(financialCriteria);
                                          }
                                      });
    }
    function getFinancialByAjax(financialCriteria) {
        financialContent.innerHTML = "";
        headContent.innerHTML = "";
        dateContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/financial",
                   async: false,
                   data: JSON.stringify(financialCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var page = data.data;
                       var content = page.content;
                       var totalPage = page.totalPages;
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       headContent.innerHTML =
                       '<th>结算单号</th><th>结算日期</th><th>商户信息</th><th>结算方式</th><th>结算账户信息</th><th>结算周期</th><th>收款人</th>';
                       if (financialCriteria.state == 0) {
                           headContent.innerHTML += "<th>待转账金额</th><th>操作</th>"
                           dateContent.innerHTML = "结算日期";
                       }
                       if (financialCriteria.state == 1) {
                           headContent.innerHTML += "<th>转账金额</th><th>转账日期</th><th>操作</th>";
                           dateContent.innerHTML = "转账日期";
                       }
                       if (financialCriteria.state == 2) {
                           headContent.innerHTML += "<th>待转账金额</th><th>操作</th>";
                           dateContent.innerHTML = "结算日期";
                       }
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].statisticId + '</td>';
                           contentStr +=
                           '<td><span>'
                           + new Date(content[i].balanceDate).format('yyyy-MM-dd')
                           + '</span></td>';
                           contentStr +=
                           '<td><span>' + content[i].merchant.name + '</span><br><span>('
                           + content[i].merchant.merchantSid + ')</span></td>'
                           if (content[i].merchant.merchantBank.bankName == "支付宝") {
                               contentStr +=
                               '<td>支付宝</td>' + '<td>' + content[i].merchant.merchantBank.bankNumber
                               + '</td>'
                           } else {
                               contentStr +=
                               '<td>银行卡</td>' + '<td>' + content[i].merchant.merchantBank.bankName
                               + '  ' + content[i].merchant.merchantBank.bankNumber + '</td>'
                           }
                           '<td>' + content[i].merchant.merchantBank.bankName + '</td>'
                           if (content[i].merchant.cycle == 1) {
                               contentStr += '<td>T+1</td>'
                           } else {
                               contentStr += '<td>T+2</td>'
                           }
                           contentStr += '<td>' + content[i].merchant.payee + '</td>';
                           contentStr +=
                           '<td width="150px"><span>' + content[i].transferPrice / 100
                           + '</span><br><span  width="150px">(¥'
                           + content[i].transferFromTruePay / 100 + '微信 ¥'
                           + (content[i].transferPrice - content[i].transferFromTruePay) / 100
                           + '红包)</span></td>';
                           if (content[i].state == 0) {
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                               + '"><shiro:hasPermission name="financial:transfer"><button class="btn btn-primary btn-sm changeFinancialToTransfer">确认转账</button></shiro:hasPermission><button  class="btn btn-primary btn-sm serchDetails">查看详情</button><button  class="btn btn-primary btn-sm changeToHover">设为挂账</button></td>';
                           } else if (content[i].state == 1) {
                               contentStr +=
                               '<td>'
                               + new Date(content[i].transferDate).format('yyyy-MM-dd HH:mm:ss')
                               + '</td>'
                               + '<td>'
                               + '</button><button  class="btn btn-primary btn-sm serchDetails">查看详情</button>'
                               + '</td>'
                           } else {
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                               + '"><button class="btn btn-primary btn-sm changeFinancialToTransfer">确认转账</button><button  class="btn btn-primary btn-sm serchDetails">查看详情</button></td>';
                           }
                           financialContent.innerHTML += contentStr;
                       }
                       $(".changeFinancialToTransfer").each(function (i) {
                           $(".changeFinancialToTransfer").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $("#transfer-confirm").bind("click", function () {
                                   $("#transfer-confirm").unbind("click");
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/financial/" + id,
                                              contentType: "application/json",
                                              success: function (data) {
                                                  alert(data.msg);
                                                  getFinancialByAjax(financialCriteria);
                                              }
                                          });
                               });
                               $("#deleteWarn").modal("show");
                           });
                       });
//查看详情
                       $(".serchDetails").each(function (i) {
                           $(".serchDetails").eq(i).bind("click", function () {
                               var date = new Date(content[i].balanceDate).format('yyyy-MM-dd');
                               var name = content[i].merchant.name
                               var str = date + "@" + name;
                               location.href =
                               "/manage/offLineOrder/messageDetailsPage?messageDetailsStr=" + str;
                           });
                       });
                       $(".changeToHover").each(function (i) {
                           $(".changeToHover").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $("#hover-confirm").bind("click", function () {
                                   $("#hover-confirm").unbind("click");
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/financial/hover/" + id,
                                              contentType: "application/json",
                                              success: function (data) {
                                                  alert(data.msg);
                                                  getFinancialByAjax(financialCriteria);
                                              }
                                          });
                               });
                               $("#hover").modal("show");
                           });
                       });
                       initPage(financialCriteria.offset, totalPage);
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
    function searchFinancialByCriteria() {
        var dateStr = $('#date-end span').text().split("-");
        var startDate = dateStr[0].replace(/-/g, "/");
        var endDate = dateStr[1].replace(/-/g, "/");
        if (financialCriteria.state == 0) {
            financialCriteria.startDate = startDate;
            financialCriteria.endDate = endDate;
        }
        if (financialCriteria.state == 1) {
            financialCriteria.transferStartDate = startDate;
            financialCriteria.transferEndDate = endDate;
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            financialCriteria.merchant = $("#merchant-name").val();
        } else {
            financialCriteria.merchant = null;
        }
        getFinancialByAjax(financialCriteria);
    }
    function searchFinancialByState(state) {
        financialCriteria.offset = 1;
        if (state != null) {
            financialCriteria.state = state;
        } else {
            financialCriteria.state = null;
        }
        getFinancialByAjax(financialCriteria);
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
        if (financialCriteria.startDate == null) {
            financialCriteria.startDate = null;
        }
        if (financialCriteria.endDate == null) {
            financialCriteria.endDate = null;
        }
        if (financialCriteria.transferStartDate == null) {
            financialCriteria.transferStartDate = null;
        }
        if (financialCriteria.transferEndDate == null) {
            financialCriteria.transferEndDate = null;
        }
        if (financialCriteria.merchant == null) {
            financialCriteria.merchant = null;
        }
        post("/manage/financial/export", financialCriteria);
    }
    $("#batchTransfer").bind("click", function () {
        $("#batchTransfer").unbind("click");
        $(".changeFinancialToTransfer").each(function (i) {
            $(".changeFinancialToTransfer").eq(i).unbind("click");
        });
        var ids = [];
        $(".id-hidden").each(function () {
            ids.push(this.value)
        });
        $.ajax({
                   type: "post",
                   url: "/manage/financial/batchTransfer",
                   data: JSON.stringify(ids),
                   contentType: "application/json",
                   success: function (data) {
                       alert(data.msg);
                       location.href = "/manage/financial";
                   }
               });
    })
    function serchDetails(str) {
        alert(str);
    }
</script>
</body>
</html>