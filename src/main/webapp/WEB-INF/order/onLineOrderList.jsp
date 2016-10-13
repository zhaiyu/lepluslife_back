<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/9/26
  Time: 上午10:02
  content:线上订单列表
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
    <%@include file="../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main">
            <div class="container-fluid" style="padding-top: 20px">

                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="date-end">创建时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="date-pay">支付时间</label>

                        <div id="date-pay" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRang"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="order-ID">订单编号</label>
                        <input type="text" id="order-ID" class="form-control"
                               placeholder="请输入订单编号"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="userName">买家姓名</label>
                        <input type="text" id="userName" class="form-control"
                               placeholder="请输入买家姓名"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="payOrigin">订单来源</label>
                        <select class="form-control" id="payOrigin">
                            <option value="-1">全部分类</option>
                            <option value="1">APP</option>
                            <option value="2">公众号</option>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="phoneNumber">买家手机号</label>
                        <input type="text" id="phoneNumber" class="form-control"
                               placeholder="请输入买家手机号"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="minTruePrice">实付最小金额</label>
                        <input type="number" id="minTruePrice" class="form-control"
                               placeholder="请输入最低金额"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="maxTruePrice">实付最大金额</label>
                        <input type="number" id="maxTruePrice" class="form-control"
                               placeholder="请输入最高金额"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="transmitWay">取货方式</label>
                        <select class="form-control" id="transmitWay">
                            <option value="-1">全部分类</option>
                            <option value="1">线下自提</option>
                            <option value="2">物流发货</option>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="payWay">支付方式</label>
                        <select class="form-control" id="payWay">
                            <option value="-1">全部</option>
                            <option value="1">纯微信</option>
                            <option value="3">微信+积分</option>
                            <option value="4">纯积分</option>
                        </select>
                    </div>


                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchByCriteria()">查询
                        </button>
                    </div>
                </div>

                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab" onclick="searchByType(5)">全部</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab" onclick="searchByType(0)">待付款</a>
                    </li>
                    <li><a href="#tab1" data-toggle="tab" onclick="searchByType(1)">待发货</a></li>
                    <li><a href="#tab1" data-toggle="tab" onclick="searchByType(2)">已发货</a></li>
                    <li><a href="#tab1" data-toggle="tab" onclick="searchByType(3)">已完成</a></li>
                    <li><a href="#tab1" data-toggle="tab" onclick="searchByType(4)">已取消</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr id="tr" class="active">
                                <th class="text-center">订单编号</th>
                                <th class="text-center">商品信息</th>
                                <th class="text-center">买家信息</th>
                                <th class="text-center">总价</th>
                                <th class="text-center">实际支付</th>
                                <th class="text-center">来源</th>
                                <th class="text-center">红包返还</th>
                                <th class="text-center">下单时间</th>
                                <th class="text-center">状态</th>
                                <th class="text-center">操作</th>
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
                            onclick="exportExcel(onLineOrderCriteria)">导出表格
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!--确认发货提示框-->
<div class="modal" id="confirmWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">确认发货</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">

                    <div class="form-group">
                        <label for="expressCompany" class="col-sm-2 control-label">物流公司名称</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="expressCompany"
                                   placeholder="请输入公司名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="expressNumber" class="col-sm-2 control-label">订单物流单号</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="expressNumber"
                                   placeholder="请输入物流单号">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="delivery-confirm" onclick="deliverySubmit()">确认
                </button>
            </div>
        </div>
    </div>
</div>

<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script>
    var onLineOrderCriteria = {}, flag = true, init1 = 0, backA = eval('${backA}'), orderId = 0, orderState = 0;
    var orderContent = document.getElementById("orderContent");

    $(function () {
        // tab切换
        var tableType = "${state}";
        if (tableType == 5) {
            $('#myTab li:eq(0) a').tab('show');
        } else if (tableType == 0) {
            $('#myTab li:eq(1) a').tab('show');
        } else if (tableType == 1) {
            $('#myTab li:eq(2) a').tab('show');
        } else if (tableType == 2) {
            $('#myTab li:eq(3) a').tab('show');
        } else if (tableType == 3) {
            $('#myTab li:eq(4) a').tab('show');
        } else if (tableType == 4) {
            $('#myTab li:eq(5) a').tab('show');
        }

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
            $('#date-pay').daterangepicker({
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
                $('#date-pay span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                                         + end.format('YYYY/MM/DD HH:mm:ss'));
            });
        });
        onLineOrderCriteria.state = 5;
        onLineOrderCriteria.offset = 1;
        getOrderListByCriteria(onLineOrderCriteria);
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
    //强制保留两位小数
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

    function deliverySubmit() {
        var onLineOrder = {};
        onLineOrder.id = orderId;
        var expressCompany = $("#expressCompany").val();
        if (expressCompany == null || expressCompany == '') {
            alert("请输入快递公司名称");
            $("#expressCompany").focus();
            return;
        }
        var expressNumber = $("#expressNumber").val();
        if (expressNumber == null || expressNumber == '') {
            alert("请输入快递单号");
            $("#expressNumber").focus();
            return;
        }
        onLineOrder.expressCompany = expressCompany;
        onLineOrder.expressNumber = expressNumber;
        onLineOrder.state = orderState;
        $.ajax({
                   type: "post",
                   url: "/manage/order/delivery",
                   contentType: "application/json",
                   data: JSON.stringify(onLineOrder),
                   success: function (data) {
                       alert(data.msg);
                       setTimeout(function () {
                           getOrderListByCriteria(onLineOrderCriteria);
                       }, 0);
                   }
               });
    }
    $('#productSubmit').on('click', function () {
        var picture = $("#picture").attr("src");
        if (picture == null || picture == "" || picture == "null") {
            alert("请选择一张爆款图片");
            return false;
        }
        var productId = $('#productId').val();
        $.ajax({
                   type: "get",
                   url: "/manage/limit/changeHot?type=1&id=" + productId + "&thumb=" + picture,
                   contentType: "application/json",
                   success: function (data) {
                       if (data.status == 200) {
                           location.href = "/manage/limit?type=1";
                       } else {
                           alert(data.status);
                       }
                   }
               });
    });

    function searchByType(type) {
        onLineOrderCriteria.offset = 1;
        if (type != null) {
            onLineOrderCriteria.state = type;
        } else {
            onLineOrderCriteria.state = 5;
        }
        flag = true;
        init1 = 1;
        getOrderListByCriteria(onLineOrderCriteria);
    }

    function getOrderListByCriteria(criteria) {
        orderContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/order/onLineOrderList",
                   async: false,
                   data: JSON.stringify(criteria),
                   contentType: "application/json",
                   success: function (data) {
                       var result = data.data;
                       var content = result.content;
                       var totalPage = result.totalPages;
                       $("#totalElements").html(result.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag) {
                           flag = false;
                           initPage(criteria.offset, totalPage);
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       for (var i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].orderSid + '</td>';
                           var orderDetails = content[i].orderDetails;
                           contentStr += '<td>';
                           for (var o = 0; o < orderDetails.length; o++) {
                               contentStr +=
                               orderDetails[o].product.name + '('
                               + orderDetails[o].productSpec.specDetail + ')X'
                               + orderDetails[o].productNumber + '<br>';
                           }
                           contentStr += '</td>';

                           if (content[i].address != null) {
                               contentStr +=
                               '<td>' + content[i].address.name + '(' + content[i].leJiaUser.userSid
                               + ')' + content[i].address.phoneNumber + '<br>'
                               + content[i].address.province
                               + content[i].address.city + content[i].address.county
                               + content[i].address.location + '</td>';
                           } else {
                               contentStr +=
                               '<td>未输入收货地址(' + content[i].leJiaUser.userSid + ')</td>';
                           }
                           contentStr +=
                           '<td>最低需付:' + toDecimal(content[i].totalPrice / 100) + '元<br>最高可用:'
                           + content[i].totalScore
                           + '积分</td>';
                           contentStr +=
                           '<td>实际支付:' + toDecimal(content[i].truePrice / 100) + '元<br>实际使用:'
                           + content[i].trueScore
                           + '积分</td>';
                           //支付方式 6种
                           var pO = content[i].payOrigin.id, pOT = '';
                           switch (pO) {
                               case 1:
                                   pOT = 'APP';
                                   break;
                               case 2:
                                   pOT = 'APP';
                                   break;
                               case 4:
                                   pOT = 'APP';
                                   break;
                               case 9:
                                   pOT = 'APP';
                                   break;
                               case 5:
                                   pOT = '公众号';
                                   break;
                               case 6:
                                   pOT = '公众号';
                                   break;
                               case 8:
                                   pOT = '公众号';
                                   break;
                               case 10:
                                   pOT = '公众号';
                                   break;
                           }
                           if (pO == 1) {
                               pOT = 'APP';
                           }
                           contentStr += '<td>' + pOT + '</td>';
                           contentStr += '<td>' + toDecimal(content[i].payBackA / 100) + '</td>';
                           contentStr +=
                           '<td>' + new Date(content[i].createDate).format('yyyy-MM-dd HH:mm:ss')
                           + '</td>';
                           if (content[i].state == 0) {
                               contentStr += '<td>待付款</td>';
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                               + '"><button class="btn btn-primary cancleOrder">取消订单</button></td></tr>';
                           } else if (content[i].state == 2) {
                               contentStr += '<td>已发货</td>';
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                               + '"><input type="hidden" class="expressCompany" value="'
                               + content[i].expressCompany
                               + '"><input type="hidden" class="expressNumber" value="'
                               + content[i].expressNumber
                               + '"><button class="btn btn-primary showExpress">查看物流信息</button>' +
                               '<button class="btn btn-primary editExpress">修改物流信息</button></td></tr>';
                           } else if (content[i].state == 3) {
                               if (content[i].transmitWay == 1) {
                                   contentStr += '<td>订单完成(线下自提)</td>';
                               } else {
                                   contentStr += '<td>订单完成</td>';
                               }
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                               + '"></td></tr>';
                           } else if (content[i].state == 4) {
                               contentStr += '<td>订单已取消</td>';
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                               + '"></td></tr>';
                           } else if (content[i].state == 1) {
                               if (content[i].transmitWay == 1) {
                                   contentStr += '<td>已付款(线下自提)</td>';
                                   contentStr +=
                                   '<td><input type="hidden" class="id-hidden" value="'
                                   + content[i].id
                                   + '"><button class="btn btn-primary finishOrder">确认完成</button></td></tr>';
                               } else {
                                   contentStr += '<td>已付款未发货</td>';
                                   contentStr +=
                                   '<td><input type="hidden" class="id-hidden" value="'
                                   + content[i].id
                                   + '"><input type="hidden" class="expressCompany" value="'
                                   + content[i].expressCompany
                                   + '"><input type="hidden" class="expressNumber" value="'
                                   + content[i].expressNumber
                                   + '"><button class="btn btn-primary delivery">确认发货</button>' +
                                   '<button class="btn btn-primary cancleOrder">取消订单</button></td></tr>';
                               }
                           }
                           orderContent.innerHTML += contentStr;
                       }
                       $(".cancleOrder").each(function (i) {  //取消订单
                           $(".cancleOrder").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               if (!confirm("确定取消该订单？")) {
                                   return false;
                               }
                               $.ajax({
                                          type: "get",
                                          url: "/manage/orderCancle/" + id,
                                          contentType: "application/json",
                                          success: function (data) {
                                              alert(data.msg);
                                              getOrderListByCriteria(onLineOrderCriteria);
                                          }
                                      });
                           });
                       });
                       $(".finishOrder").each(function (i) {  //自提的确认完成
                           $(".finishOrder").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               if (!confirm("确定完成该订单？")) {
                                   return false;
                               }
                               $.ajax({
                                          type: "get",
                                          url: "/manage/finishOrder/" + id,
                                          contentType: "application/json",
                                          success: function (data) {
                                              alert(data.msg);
                                              getOrderListByCriteria(onLineOrderCriteria);
                                          }
                                      });
                           });
                       });
                       $(".showExpress").each(function (i) { //查看物流信息
                           $(".showExpress").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               location.href = "/manage/showExpress/" + id;
                           });
                       });
                       $(".editExpress").each(function (i) { //修改物流信息
                           $(".editExpress").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               var expressCompany = $(this).parent().find(".expressCompany").val();
                               var expressNumber = $(this).parent().find(".expressNumber").val();
                               $("#expressCompany").val(expressCompany);
                               $("#expressNumber").val(expressNumber);
                               orderId = id;
                               orderState = 1;
                               $("#confirmWarn").modal("show");
                           });
                       });
                       $(".delivery").each(function (i) { //确认发货
                           $(".delivery").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               var expressCompany = $(this).parent().find(".expressCompany").val();
                               var expressNumber = $(this).parent().find(".expressNumber").val();
                               if (expressCompany != null && expressCompany != 'null') {
                                   $("#expressCompany").val(expressCompany);
                               }
                               if (expressNumber != null && expressNumber != 'null') {
                                   $("#expressNumber").val(expressNumber);
                               }
                               orderId = id;
                               orderState = 0;
                               $("#confirmWarn").modal("show");
                           });
                       });
                   }
               });
    }

    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             onLineOrderCriteria.offset = p;
                                             init1 = 0;
                                             getOrderListByCriteria(onLineOrderCriteria);
                                         }
                                     });
    }

    function searchByCriteria() {
        condition();
        getOrderListByCriteria(onLineOrderCriteria);
    }
    function condition() {
        onLineOrderCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            onLineOrderCriteria.startDate = startDate;
            onLineOrderCriteria.endDate = endDate;
        }
        var datePay = $('#date-pay span').text().split("-");
        if (datePay != null && datePay != '') {
            onLineOrderCriteria.startPayDate = datePay[0].replace(/-/g, "/");
            onLineOrderCriteria.endPayDate = datePay[1].replace(/-/g, "/");
        }
        var orderId = $("#order-ID").val();
        if (orderId != "" && orderId != null) {
            onLineOrderCriteria.orderSid = orderId;
        } else {
            onLineOrderCriteria.orderSid = null;
        }
        var userName = $("#userName").val();
        if (userName != "" && userName != null) {
            onLineOrderCriteria.userName = userName;
        } else {
            onLineOrderCriteria.userName = null;
        }
        var phoneNumber = $("#phoneNumber").val();
        if (phoneNumber != "" && phoneNumber != null) {
            onLineOrderCriteria.phoneNumber = phoneNumber;
        } else {
            onLineOrderCriteria.phoneNumber = null;
        }
        var minTruePrice = $("#minTruePrice").val();
        if (minTruePrice != "" && minTruePrice != null) {
            onLineOrderCriteria.minTruePrice = minTruePrice * 100;
        } else {
            onLineOrderCriteria.minTruePrice = null;
        }
        var maxTruePrice = $("#maxTruePrice").val();
        if (maxTruePrice != "" && maxTruePrice != null) {
            onLineOrderCriteria.maxTruePrice = maxTruePrice * 100;
        } else {
            onLineOrderCriteria.maxTruePrice = null;
        }
        var transmitWay = $("#transmitWay").val();
        if (transmitWay != -1) {
            onLineOrderCriteria.transmitWay = transmitWay;
        } else {
            onLineOrderCriteria.transmitWay = null;
        }
        var payOrigin = $("#payOrigin").val();
        if (payOrigin != -1) {
            onLineOrderCriteria.payOrigin = payOrigin;
        } else {
            onLineOrderCriteria.payOrigin = null;
        }
        var payWay = $("#payWay").val();
        if (payWay != -1) {
            onLineOrderCriteria.payWay = payWay;
        } else {
            onLineOrderCriteria.payWay = null;
        }
    }
    function exportExcel(criteria) {
        condition();
        location.href = '/manage/order/export?condition=' + JSON.stringify(criteria);
    }
</script>
</body>
</html>

