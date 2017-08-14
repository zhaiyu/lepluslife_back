<%--
  Created by IntelliJ IDEA.
  User: wcg
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
                <hr>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#lunbotu" data-toggle="tab">打印记录</a></li>
                    <li class="active"><a href="#xiangqing" data-toggle="tab"
                                          onclick="getPrinterByCriteria()">打印机列表</a>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content" style="margin-top: 10px">
                    <div class="tab-pane fade in active" id="lunbotu">
                        <div class="form-group col-md-6">
                            <label for="date-end">交易完成时间</label>

                            <div id="date-end" class="form-control">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                <span id="searchDateRange"></span>
                                <b class="caret"></b>
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="machineCode3">打印机终端号</label>
                            <input type="text" class="form-control" id="machineCode3"
                                   placeholder="请输入打印机终端号">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="merchantName">所属门店</label>
                            <input type="text" class="form-control" id="merchantName"
                                   placeholder="门店名称">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="receiptState">状态</label>
                            <select class="form-control" id="receiptState">
                                <option value="nullValue">全部状态</option>
                                <option value="0">失败</option>
                                <option value="1">成功</option>
                                <option value="3">补打成功</option>
                                <option value="4">补打失败</option>
                            </select>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="receiptSid">易连云单号</label>
                            <input type="text" class="form-control" id="receiptSid"
                                   placeholder="请输入易连云单号">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="offlineOrderSid">扫码订单号</label>
                            <input type="text" class="form-control" id="offlineOrderSid"
                                   placeholder="请输入扫码订单号">
                        </div>

                        <div class="form-group col-md-3">
                            <button class="btn btn-primary" style="margin-top: 24px"
                                    onclick="getReceiptByCriteria()">查询
                            </button>
                        </div>

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>易连云单号</th>
                                <th>订单号</th>
                                <th>打印机终端号</th>
                                <th>所属门店</th>
                                <th>打印联数</th>
                                <th>状态</th>
                                <th>打印时间</th>
                                <th>订单类型</th>
                            </tr>
                            </thead>
                            <tbody id="receiptContent">
                            </tbody>
                        </table>
                        <div class="tcdPageCode" id="tcdPageCode2" style="display: inline;">
                        </div>
                        <div style="display: inline;"> 共有 <span id="totalElements2"></span> 个</div>
                    </div>
                    <div class="tab-pane fade" id="xiangqing">
                        <button class="btn btn-primary pull-right" style="margin-top: 5px"
                                onclick="addPrinterM()">
                            添加打印终端
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th class="text-center">打印机终端号</th>
                                <th class="text-center">打印机名称</th>
                                <th class="text-center">所属门店</th>
                                <th class="text-center">所属商户</th>
                                <th class="text-center">状态</th>
                                <th class="text-center">添加时间</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody id="printerContent">
                            </tbody>
                        </table>
                        <div class="tcdPageCode" id="tcdPageCode1" style="display: inline;">
                        </div>
                        <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!--添加设备-->
<div class="modal" id="addM">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon">打印机名称</span>
                    <input type="text" class="form-control" placeholder="printerName"
                           id="printerName">
                </div>
                <br>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon">密钥</span>
                    <input type="text" class="form-control" placeholder="mKey" id="mKey">
                </div>
                <br>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon">打印联数</span>
                    <input type="text" class="form-control" placeholder="printerCount"
                           id="printerCount">
                </div>
                <br>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon">终端号</span>
                    <input type="text" class="form-control" placeholder="machineCode"
                           id="machineCode">
                </div>
                　　
                <div id="addDiv">
                    <span>所在门店</span>
                    　　　　　　　　　　<select id="hhr">
                    <c:forEach items="${merchantList}" var="merchant">
                        　　　　　　　　　　　　<option value = "0"></option>
                        <option value="${merchant.id}">${merchant.name}</option>
                    </c:forEach>
                </select>
                    　　
                </div>
                　　　　
                <br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="transfer-confirm" onclick="addPrinter()">确认
                </button>
            </div>
        </div>
    </div>
</div>

<!--编辑设备-->
<div class="modal" id="editM">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon">打印机名称</span>
                    <input type="text" class="form-control" placeholder="printerName"
                           id="printerName2">
                    <input type="hidden" id="printerId2">
                </div>
                <br>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon">密钥</span>
                    <input type="text" class="form-control" placeholder="mKey" id="mKey2">
                </div>
                <br>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon">打印联数</span>
                    <input type="text" class="form-control" placeholder="printerCount"
                           id="printerCount2">
                </div>
                <br>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon">终端号</span>
                    <input type="text" class="form-control" placeholder="machineCode"
                           id="machineCode2">
                </div>
                　　
                <div id="editDiv">
                    <span>所在门店</span>
                    　　　　　　　　　　<select id="editSelect" name="editSelect">
                    <c:forEach items="${merchantList}" var="merchant">
                        　　　　　　　　　　　　<option value = "0"></option>
                        <option value="${merchant.id}">${merchant.name}</option>
                    </c:forEach>
                </select>
                    　　
                </div>
                　　　　
                <br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="edit-confirm" onclick="editPrinter()">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--添加提示框-->
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var printerCriteria = {};
    var printerContent = document.getElementById("printerContent");
    var flag = true;
    var init = 0;

    var receiptCriteria = {};
    var receiptContent = document.getElementById("receiptContent");
    var flag2 = true;
    var init2 = 0;

    $(function () {
        $('#myTab li:eq(0) a').tab('show');
        $("#hhr").comboSelect();
        $("#editSelect").comboSelect();
        getReceiptByCriteria();
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
        })

    })

    function getReceiptByCriteria() {
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            receiptCriteria.startDate = startDate;
            receiptCriteria.endDate = endDate;
        }
        if ($("#machineCode3").val() != 0) {
            receiptCriteria.machineCode = $("#machineCode3").val();
        } else {
            receiptCriteria.machineCode = null;
        }
        if ($("#merchantName").val() != 0) {
            receiptCriteria.merchantName = $("#merchantName").val();
        } else {
            receiptCriteria.merchantName = null;
        }
        if ($("#offlineOrderSid").val() != 0) {
            receiptCriteria.offlineOrderSid = $("#offlineOrderSid").val();
        } else {
            receiptCriteria.offlineOrderSid = null;
        }
        if ($("#receiptSid").val() != 0) {
            receiptCriteria.receiptSid = $("#receiptSid").val();
        } else {
            receiptCriteria.receiptSid = null;
        }
        if ($("#receiptState").val() == "nullValue") {
            receiptCriteria.state = null;
        } else {
            receiptCriteria.state = $("#receiptState").val();
        }

        receiptCriteria.offset = 1;
        getReceiptByAjax(receiptCriteria);
    }

    function getReceiptByAjax(receiptCriteria) {
        receiptContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/receiptList",
                   async: false,
                   data: JSON.stringify(receiptCriteria),
                   contentType: "application/json",
                   success: function (data) {

                       var page = data.data;
                       var content = page.content;
                       var totalPage = page.totalPages;
                       $("#totalElements2").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag2) {
                           flag2 = false;
                           initPage2(receiptCriteria.offset, totalPage);
                       }
                       if (init2) {
                           initPage2(1, totalPage);
                       }
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].receiptSid + '</td>';
                           contentStr +=
                               '<td>' + content[i].orderSid + '</td>';

                           contentStr +=
                               '<td>' + content[i].printer.machineCode + '</td>';

                           contentStr +=
                               '<td>' + content[i].printer.merchant.name + '</td>';

                           contentStr +=
                               '<td>' + content[i].printer.printerCount + '</td>';

                           if (content[i].state == 0) {
                               contentStr +=
                                   '<td><span>失败</span></td>';
                           } else if (content[i].state == 1) {
                               contentStr +=
                                   '<td><span>成功</span></td>';
                           } else if (content[i].state == 3) {
                               contentStr +=
                                   '<td><span>补打成功</span></td>';
                           } else if (content[i].state == 4) {
                               contentStr +=
                                   '<td><span>补打失败</span></td>';
                           }

                           contentStr +=
                               '<td><span>'
                               + new Date(content[i].completeDate).format('yyyy-MM-dd HH:mm:ss')
                               + '</span></td>';
                           if (content[i].type == 2) {
                               contentStr += '<td>通道订单</td></tr>';
                           } else {
                               contentStr += '<td>乐加订单</td></tr>';
                           }

                           receiptContent.innerHTML += contentStr;
                       }
                   }
               });
    }
    function initPage2(page, totalPage) {
        $('#tcdPageCode2').unbind();
        $("#tcdPageCode2").createPage({
                                          pageCount: totalPage,
                                          current: page,
                                          backFn: function (p) {
                                              receiptCriteria.offset = p;
                                              init2 = 0;
                                              getReceiptByAjax(receiptCriteria);
                                          }
                                      });
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
    function addPrinterM() {
        $("#addM").modal("toggle");
    }

    function addPrinter() {
        var merchant = {};
        var printer = {};

        printer.name = $("#printerName").val();

        printer.mKey = $("#mKey").val();

        printer.printerCount = $("#printerCount").val();

        printer.machineCode = $("#machineCode").val();

        merchant.name = $("#hhr").val();

        printer.merchant = merchant;

        $.ajax({
                   type: "post",
                   data: JSON.stringify(printer),
                   contentType: "application/json",
                   url: "/manage/addPrinter",
                   success: function (data) {
                       alert(data.msg);
                       getPrinterByCriteria();
                   }
               });
        ;
        $("#printerName").val("");
        $("#mKey").val("");
        $("#printerCount").val("");
        $("#machineCode").val("");
        $("#addDiv .combo-input").val("");
        $("#addM").modal("toggle");
    }
    function getPrinterByCriteria() {
        printerCriteria.offset = 1;
        getPrinterByAjax(printerCriteria);
    }
    function getPrinterByAjax(printerCriteria) {
        printerContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/printerList",
                   async: false,
                   data: JSON.stringify(printerCriteria),
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
                           initPage(printerCriteria.offset, totalPage);
                       }
                       if (init) {
                           initPage(1, totalPage);
                       }
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].machineCode + '</td>';
                           contentStr +=
                               '<td>' + content[i].name + '</td>';
                           contentStr +=
                               '<td>' + content[i].merchant.name + '</td>';
                           contentStr +=
                               '<td>' + content[i].merchantUser.name + '</td>';

                           if (content[i].state == 0) {
                               contentStr +=
                                   '<td><span>已停用</span></td>';
                           } else {
                               contentStr +=
                                   '<td><span>使用中</span></td>';
                           }
                           contentStr +=
                               '<td><span>'
                               + new Date(content[i].createDate).format('yyyy-MM-dd HH:mm:ss')
                               + '</span></td>';
                           if (content[i].state == 0) {
                               contentStr +=
                                   '<td><input type="hidden" class="id-hidden" value="'
                                   + content[i].id
                                   + '"><button class="btn btn-primary editPrinter")">编辑</button><button class="btn btn-primary changePrinter")">启动</button></td></tr>';
                           } else {
                               contentStr +=
                                   '<td><input type="hidden" class="id-hidden" value="'
                                   + content[i].id
                                   + '"><button class="btn btn-primary editPrinter")">编辑</button><button class="btn btn-primary changePrinter")">停用</button></td></tr>';
                           }
                           printerContent.innerHTML += contentStr;
                       }

                       $(".changePrinter").each(function (i) {
                           $(".changePrinter").eq(i).bind("click", function () {
                               var printerId = $(this).parent().find(".id-hidden").val();
                               $.ajax({
                                          type: "get",
                                          data: {printerId: printerId},
                                          url: "/manage/changePrinter",
                                          success: function (data) {
                                              getPrinterByAjax(printerCriteria);
                                          }
                                      });

                           });
                       });

                       $(".editPrinter").each(function (i) {
                           $(".editPrinter").eq(i).bind("click", function () {
                               var printerId = $(this).parent().find(".id-hidden").val();

                               $.ajax({
                                          type: "get",
                                          data: {printerId: printerId},
                                          url: "/manage/editPage",
                                          success: function (data) {
                                              $("#printerName2").val(data.data.name);
                                              $("#mKey2").val(data.data.mKey);
                                              $("#printerCount2").val(data.data.printerCount);
                                              $("#machineCode2").val(data.data.machineCode);
                                              $("#printerId2").val(data.data.id);
                                              var merchantId = data.data.merchant.id;
                                              var merchantName = data.data.merchant.name;

                                              $("#editDiv .combo-input").val(merchantName);
                                              $("#editSelect").find(
                                                  'option[value="' + merchantId + '"]').attr(
                                                  {"selected": true});
                                          }
                                      });
                               $("#editM").modal("toggle");
                           });
                       });

                   }
               });
    }
    function initPage(page, totalPage) {
        $('#tcdPageCode1').unbind();
        $("#tcdPageCode1").createPage({
                                          pageCount: totalPage,
                                          current: page,
                                          backFn: function (p) {
                                              printerCriteria.offset = p;
                                              init1 = 0;
                                              getPrinterByAjax(printerCriteria);
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
    function editPrinter() {
        var merchant = {};
        var printer = {};
        printer.id = $("#printerId2").val();

        printer.name = $("#printerName2").val();

        printer.mKey = $("#mKey2").val();

        printer.printerCount = $("#printerCount2").val();

        printer.machineCode = $("#machineCode2").val();

        merchant.name = $("#editSelect").val();

        printer.merchant = merchant;

        $.ajax({
                   type: "post",
                   data: JSON.stringify(printer),
                   contentType: "application/json",
                   url: "/manage/editPrinter",
                   success: function (data) {
                       alert(data.msg);
                       getPrinterByAjax(printerCriteria);
                   }
               });
        $("#printerId2").val("");
        $("#printerName2").val("");
        $("#mKey2").val("");
        $("#printerCount2").val("");
        $("#machineCode2").val("");
        $("#editDiv .combo-input").val("");
        $("#editM").modal("toggle");

    }
</script>
</body>
</html>

