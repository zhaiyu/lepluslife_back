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
                    <div class="form-group col-md-5">
                        <label for="date-end">账单日期</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="merchant-info">门店名称</label>
                        <input id="merchant-name" type="text" id="merchant-info"
                               class="form-control"
                               placeholder="请输入门店名称"/>
                    </div>
                    <div class="form-group col-md-1">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchFinancialByCriteria()">查询
                        </button>
                    </div>
                </div>
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

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active" id="head-content">
                                <th>结算单编号</th>
                                <th>账单日期</th>
                                <th>门店信息</th>
                                <th>核销笔数</th>
                                <th>交易总额</th>
                                <th>佣金/手续费</th>
                                <th>应结算总额</th>
                                <th>结算账户信息</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="financialContent">
                            </tbody>
                        </table>
                    </div>

                    <div class="tcdPageCode" style="display: inline;">
                    </div>
                    <div style="display: inline;"> 共有 <span id="totalElements"></span> 个&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;跳转至&nbsp;
                        <input id="toPage" type="text" style="width:60px"
                               onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
                               onafterpaste="this.value=this.value.replace(/[^0-9]/g,'')"/>&nbsp;页
                        <button class="btn btn-primary" style="width:50px;"
                                onclick="searchFinancialByPage()">GO
                        </button>
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
<!--确认转账提示框-->
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <h4>确定已转账？</h4>
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
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var criteria = {};
    var flag = true, init1 = 0;
    var financialContent = document.getElementById("financialContent");
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
        criteria.offset = 1;
        criteria.state = 0;
        getFinancialByAjax(criteria);
    })

    function getFinancialByAjax(criteria) {
        financialContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/groupon/ajaxList",
                   async: false,
                   data: JSON.stringify(criteria),
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
                           initPage(criteria.offset, totalPage);
                           flag = false;
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].sid + '</td>';
                           contentStr +=
                               '<td>' + new Date(content[i].balanceDate).format('yyyy-MM-dd')
                               + '</td>';
                           contentStr +=
                               '<td><span>' + content[i].merchant.name + '</span><br><span>('
                               + content[i].merchant.merchantSid + ')</span></td>';
                           contentStr += '<td>' + content[i].checkNum + '</td>';
                           contentStr += '<td>' + content[i].totalMoney / 100 + '</td>';
                           contentStr += '<td>' + content[i].commission / 100 + '</td>';
                           contentStr += '<td>' + content[i].transferMoney / 100 + '</td>';
                           contentStr += '<td>'
                                         + content[i].merchant.merchantBank.bankName
                                         + '  ' + content[i].merchant.merchantBank.bankNumber
                                         + '</td>';
                           contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                               + '">';
                           if (content[i].state == 0) {
                               contentStr +=
                                   '<button class="btn btn-primary btn-sm changeFinancialToTransfer">确认转账</button>'
                                   +
                                   '<button  class="btn btn-primary btn-sm changeToHover">设为挂账</button></td>';
                           } else if (content[i].state == 1) {
                               contentStr += '</td>'
                           } else {
                               contentStr +=
                                   '<button class="btn btn-primary btn-sm changeFinancialToTransfer">确认转账</button></td>';
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
                                              url: "/manage/groupon/transfer/" + id,
                                              success: function (data) {
                                                  alert(data.msg);
                                                  getFinancialByAjax(criteria);
                                              }
                                          });
                               });
                               $("#deleteWarn").modal("show");
                           });
                       });
                       $(".changeToHover").each(function (i) {
                           $(".changeToHover").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $("#hover-confirm").bind("click", function () {
                                   $("#hover-confirm").unbind("click");
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/groupon/hover/" + id,
                                              success: function (data) {
                                                  alert(data.msg);
                                                  getFinancialByAjax(criteria);
                                              }
                                          });
                               });
                               $("#hover").modal("show");
                           });
                       });
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
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        var startDate = null;
        var endDate = null;
        if (dateStr.length > 0 && dateStr[0] != null && dateStr[0] != '') {
            startDate = dateStr[0].replace(/-/g, "/");
            endDate = dateStr[1].replace(/-/g, "/");
        }
        if (criteria.state == 0) {
            criteria.startDate = startDate;
            criteria.endDate = endDate;
        }
        if (criteria.state == 1) {
            criteria.transferStartDate = startDate;
            criteria.transferEndDate = endDate;
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            criteria.merchant = $("#merchant-name").val();
        } else {
            criteria.merchant = null;
        }
        getFinancialByAjax(criteria);
    }
    //  跳转到指定页数
    function searchFinancialByPage() {
        var pageNum = $("#toPage").val();
        if (pageNum == null || pageNum == '') {
            alert("请输入目标页数 ^_^ ！");
            return;
        }
        criteria.offset = pageNum;
        flag = true;
        var dateStr = $('#date-end span').text().split("-");
        var startDate = null;
        var endDate = null;
        if (dateStr.length > 0 && dateStr[0] != null && dateStr[0] != '') {
            startDate = dateStr[0].replace(/-/g, "/");
            endDate = dateStr[1].replace(/-/g, "/");
        }
        if (criteria.state == 0) {
            criteria.startDate = startDate;
            criteria.endDate = endDate;
        }
        if (criteria.state == 1) {
            criteria.transferStartDate = startDate;
            criteria.transferEndDate = endDate;
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            criteria.merchant = $("#merchant-name").val();
        } else {
            criteria.merchant = null;
        }
//        console.log(JSON.stringify(criteria))
        getFinancialByAjax(criteria);
    }
    function searchFinancialByState(state) {
        criteria.offset = 1;
        if (state != null) {
            criteria.state = state;
        } else {
            criteria.state = null;
        }
        getFinancialByAjax(criteria);
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
        if (criteria.startDate == null) {
            criteria.startDate = null;
        }
        if (criteria.endDate == null) {
            criteria.endDate = null;
        }
        if (criteria.transferStartDate == null) {
            criteria.transferStartDate = null;
        }
        if (criteria.transferEndDate == null) {
            criteria.transferEndDate = null;
        }
        if (criteria.merchant == null) {
            criteria.merchant = null;
        }
        post("/manage/groupon/statistic/export", criteria);
    }
    $("#batchTransfer").bind("click", function () {
        $("#batchTransfer").unbind("click");
        $(".changeFinancialToTransfer").each(function (i) {
            $(".changeFinancialToTransfer").eq(i).unbind("click");
        });
        $.ajax({
                   type: "post",
                   url: "/manage/groupon/batchTransfer",
                   contentType: "application/json",
                   success: function (data) {
                       alert(data.msg);
                       getFinancialByAjax(criteria);
                   }
               });
    })
    function initPage(currPage, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: currPage,
                                         backFn: function (p) {
                                             //单击回调方法，p是当前页码
                                             init1 = 0;
                                             criteria.offset = p;
                                             getFinancialByAjax(criteria);
                                         }
                                     });
    }
</script>
</body>
</html>