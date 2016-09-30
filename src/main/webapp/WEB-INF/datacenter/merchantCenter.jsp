<%--
  Created by IntelliJ IDEA.
  User: xf
  Date: 2016/9/20
  Time: 13:23
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
    <title>乐+生活 运营管理系统</title>
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
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
<body>
<div id="topIframe">
    <%@include file="../common/top.jsp" %>
</div>
<div id="content">
        <div id="leftIframe">
            <%@include file="../common/left.jsp" %>
        </div>
        <div class="m-right">
            <div class="main" >
                <div class="container"><h3 style="position: relative;left:400px;top:20px">商家数据概览</h3></div>
                <div class="container-fluid">
                    <hr>
                    <!--查询条件-->
                    <div class="row" style="margin-top: 30px">
                        <div class="form-group col-md-3">
                            <label for="date-end">交易完成时间</label>
                            <div id="date-end" class="form-control">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                <span id="searchDateRange"></span>
                                <b class="caret"></b>
                            </div>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="merchant-city">所在城市</label>
                            <select class="form-control" id="merchant-city">
                                <option value="-1">全部城市</option>
                                <c:forEach items="${cityList}" var="city">
                                    <option value="${city.id}">${city.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="sale-name">销售名称</label>
                            <select class="form-control" id="sale-name">
                                <option value="-1">全部销售</option>
                                <c:forEach items="${staffList}" var="staff">
                                    <option value="${staff.id}">${staff.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="merchant-id">商户ID</label>
                            <input type="text" id="merchant-id" class="form-control"
                                   placeholder="请输入商户ID"/>
                        </div>
                    </div>

                    <div class="row" style="margin-bottom: 30px">
                        <div class="form-group col-md-3">
                            <label for="merchant-date-end">商户创建时间</label>
                            <div id="merchant-date-end" class="form-control">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                <span id="merchant-range"></span>
                                <b class="caret"></b>
                            </div>
                        </div>

                        <div class="form-group col-md-2">
                            <label for="merchant-type">商户类型</label>
                            <select class="form-control" id="merchant-type">
                                <option value="-1">全部类型</option>
                                <c:forEach items="${merchantTypeList}" var="merchantType">
                                    <option value="${merchantType.id}">${merchantType.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group col-md-2">
                            <label for="needNum">有效订单量大于</label>
                            <input type="number" id="needNum" class="form-control"
                                   placeholder="请输入订单量"/>
                        </div>

                        <div class="form-group col-md-2">
                            <label for="merchant-name">商户名称</label>
                            <input type="text" id="merchant-name" class="form-control"
                                   placeholder="请输入商户名称"/>
                        </div>

                        <div class="form-group col-md-3">
                            <button class="btn btn-primary" style="margin-top: 20px"
                                    onclick="searchMerchantByCriteria()">查询
                            </button>
                        </div>

                    </div>
                    <!--展示列表-->
                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane fade in active" id="tab1">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr class="active">
                                    <th>销售名称</th>
                                    <th>商户名称</th>
                                    <th>商户类型</th>
                                    <th>当前锁定情况</th>
                                    <th>时段内锁定</th>
                                    <th>流水额</th>
                                    <th>有效订单量</th>
                                    <th>导流订单数</th>
                                    <th>导流订单流水</th>
                                    <th>导流佣金</th>
                                </tr>
                                </thead>
                                <tbody id="merchantContent">
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!--分页条和导出按钮-->
                    <div class="tcdPageCode" style="display: inline;">
                    </div>
                    <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                    <button class="btn btn-info pull-right" style="margin-top: 5px"
                            onclick="exportExcelAll()">导出全部
                    </button>
                    <button class="btn btn-success pull-right" style="margin-top: 5px"
                            onclick="exportExcel()">导出excel
                    </button>
                    <button class="btn btn-primary pull-right openWarn" style="margin-top: 5px">有效订单设置
                    </button>
                </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!--开通佣金提示框-->
<div class="modal" id="openWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">设置</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label col-sm-offset-2">订单金额</label>
                        <div class="col-sm-6">
                            <input type="text" id="valid-amount" class="form-control pull-left" style="width: 60%" value="10">
                        </div>
                    </div>
                    <p class="text-center"><font color="grey">注: 大于本次设定金额的订单为有效订单.</font>
                    </p>
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
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var merchantCriteria = {};
    var flag = true;
    var init1 = 0;
    var merchantContent = document.getElementById("merchantContent");
    //   var merchantWarn = document.getElementById("merchantWarn");
    $(function () {
        // tab切换
        //   $('#myTab li:eq(0) a').tab('show');
        //        提示框
        $(".openWarn").click(function () {
            $("#openWarn").modal("toggle");
        });
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

            $('#merchant-date-end').daterangepicker({
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
                $('#merchant-date-end span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                                         + end.format('YYYY/MM/DD HH:mm:ss'));
            });
        })
        merchantCriteria.offset = 1;
        getMerchantByAjax(merchantCriteria);
    });
    function getMerchantByAjax(merchantCriteria) {
        merchantContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/merchant_data/search",
                   async: false,
                   data: JSON.stringify(merchantCriteria),
                   contentType: "application/json",
                   success: function (result) {
                       var page = result.data.page;
                       var binds = result.data.binds;
                       var content = page.content;
                       var timeBinds = result.data.timeBinds;
                       var orderTotal = result.data.orderTotal;
                       var orderNum = result.data.orderNum;
                       var leadOrderNum = result.data.leadOrderNum;
                       var leadTotal = result.data.leadTotal;
                       var leadCommission = result.data.leadOrderNum;
                       var staffs = result.data.staffs;
                       var totalPage = page.totalPages;
                       $("#totalElements").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag) {
                           initPage(merchantCriteria.offset, totalPage);
                           flag = false;
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + staffs[i].name + '</td>';
                           contentStr +=
                           '<td>' + content[i].name + '</td>';
                           if (content[i].partnership == 0) {
                               contentStr +=
                               '<td>普通商户</td>';
                           } else {
                               contentStr +=
                               '<td>联盟商户</td>';
                           }
                           contentStr +=
                           '<td>' + binds[i] + '/' + content[i].userLimit + '</td>';
                           contentStr +=
                           '<td>' + timeBinds[i] + '</td>';
                           contentStr +=
                           '<td>￥ ' + orderTotal[i] + '</td>';
                           contentStr +=
                           '<td>' + orderNum[i] +'</td>';
                            contentStr +=
                           '<td>' + leadOrderNum[i] +'</td>';
                            contentStr +=
                           '<td>￥ ' + leadTotal[i] +'</td>';
                            contentStr +=
                           '<td>￥ ' + leadCommission[i] +'</td>';
                           merchantContent.innerHTML += contentStr;

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
                                             init1 = 0;
                                             merchantCriteria.offset = p;
                                             getMerchantByAjax(merchantCriteria);
                                         }
                                     });
    }

    function flushCriteria() {
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            merchantCriteria.startDate = startDate;
            merchantCriteria.endDate = endDate;
        }else {
            if(merchantCriteria.startDate != null) {
                delete merchantCriteria["startDate"];
            }
            if(merchantCriteria.endDate != null) {
                delete merchantCriteria["endDate"];
            }
        }
        var merchantDateStr = $('#merchant-date-end span').text().split("-");
        if(merchantDateStr!=null && merchantDateStr!='') {
            var merchantStart = merchantDateStr[0].replace(/-/g, "/");
            var merchantEnd = merchantDateStr[1].replace(/-/g, "/");
            merchantCriteria.merchantCreateStart = merchantStart;
            merchantCriteria.merchantCreateEnd = merchantEnd;
        }else {
            if(merchantCriteria.merchantCreateStart != null) {
                delete merchantCriteria["merchantCreateStart"];
            }
            if(merchantCriteria.merchantCreateEnd != null) {
                delete merchantCriteria["merchantCreateEnd"];
            }
        }
        if ($("#merchant-id").val() != "" && $("#merchant-id").val() != null) {
            merchantCriteria.merchant = $("#merchant-id").val();
        }else {
            if(merchantCriteria.merchant != null) {
                delete merchantCriteria["merchant"];
            }
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            merchantCriteria.merchantName = $("#merchant-name").val();
        }else {
            if(merchantCriteria.merchantName != null) {
                delete merchantCriteria["merchantName"];
            }
        }
        if ($("#sale-name").val()!=-1) {
            merchantCriteria.salesStaff = $("#sale-name").val();
        }else {
            if(merchantCriteria.salesStaff != null) {
                delete merchantCriteria["salesStaff"];
            }
        }
        if ($("#merchant-city").val()!=-1) {
            merchantCriteria.city = $("#merchant-city").val();
        }else {
            if(merchantCriteria.city != null) {
                delete merchantCriteria["city"];
            }
        }
        if ($("#merchant-type").val()!=-1) {
            merchantCriteria.merchantType = $("#merchant-type").val();
        }else {
            if(merchantCriteria.merchantType != null) {
                delete merchantCriteria["merchantType"];
            }
        }
        if ($("#valid-amount").val() != "" && $("#valid-amount").val() != null) {
            merchantCriteria.validAmount = $("#valid-amount").val();
        }else {
            if(merchantCriteria.validAmount != null) {
                delete merchantCriteria["validAmount"];
            }
        }
        if ($("#needNum").val() != "" && $("#needNum").val() != null) {
            merchantCriteria.needNum = $("#needNum").val();
        }else {
            if(merchantCriteria.needNum != null) {
                delete merchantCriteria["needNum"];
            }
        }
    }

    function searchMerchantByCriteria() {
        merchantCriteria.offset = 1;
        init1 = 1;
        flushCriteria();
        getMerchantByAjax(merchantCriteria);
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
            temp.appendChild(opt);
        }
        document.body.appendChild(temp);
        temp.submit();
        return temp;
    }
    function exportExcel() {
            flushCriteria();
            post("/manage/merchant_data/merchantDataExport",merchantCriteria);
    }
    function exportExcelAll() {
            var sure = confirm("导出全部会比较慢,确认要全部导出吗?");
            if(sure) {
               flushCriteria();
               post("/manage/merchant_data/merchantDataExportAll",merchantCriteria);
            }
    }
</script>
</body>
</html>


