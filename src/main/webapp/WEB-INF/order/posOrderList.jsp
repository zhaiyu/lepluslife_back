<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/8/30
  Time: 下午4:32
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
    <!--<link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">-->
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>

    <style>thead th, tbody td {
        text-align: center;
    }

    #myTab {
        margin-bottom: 10px;
    }</style>
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
                        <label for="date-end">交易完成时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-2">
                        <label>商户所在城市</label>
                        <select class="form-control" id="locationCity">
                            <option value="">所在城市（全部）</option>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label>订单类型</label>
                        <select class="form-control" id="orderType">
                            <option value="0">全部订单</option>
                            <option value="3">导流订单</option>
                            <option value="1">非会员普通订单</option>
                            <option value="2">会员普通订单</option>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label>支付方式</label>
                        <select class="form-control" id="tradeFlag">
                            <option value="">全部</option>
                            <option value="3">银行卡</option>
                            <option value="4">微信</option>
                            <option value="0">支付宝</option>
                            <option value="5">红包</option>
                        </select>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-2">
                        <label>消费者ID</label>
                        <input type="text" class="form-control" id="userSid" placeholder="请输入消费者ID">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="merchant-name">店铺名称</label>
                        <input type="text" id="merchant-name" class="form-control"
                               placeholder="请输入商户名称或ID"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label>消费者手机号</label>
                        <input type="text" id="userPhone" class="form-control"
                               placeholder="请输入消费者手机号"/>
                    </div>

                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px" onclick="searchOrderByCriteria()">查询</button>
                    </div>
                    <div class="form-group col-md-3"></div>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab" onclick="searchOrderByState()">全部订单</a>
                    </li>
                    <li class="active"><a href="#tab2" data-toggle="tab"
                                          onclick="searchOrderByState(1)">已支付</a></li>
                    <li><a href="#tab3" data-toggle="tab" onclick="searchOrderByState(0)">未支付</a>
                    </li>
                    <li><a href="#tab3" data-toggle="tab">现金记账</a>
                    </li>
                    <li style="float: right"><button type="button" class="btn btn-primary createLocation" onclick="reconciliationDifferences();">对账差错记录</button></li>
                    <li style="float: right;margin-right: 10px;"><button type="button" class="btn btn-primary createLocation" onclick="billingDownload();">掌富对账单下载</button></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>订单编号</th>
                                <th>订单类型</th>
                                <th>交易完成时间</th>
                                <th>交易所在商户</th>
                                <th>消费者</th>
                                <th>支付方式</th>
                                <th>订单金额</th>
                                <th>佣金（手续费）</th>
                                <th>商户入账</th>
                                <th>发放红包</th>
                                <th>发放积分</th>
                                <th>分润金额</th>
                                <th>状态</th>
                            </tr>
                            </thead>
                            <tbody id="orderContent">
                            <%--<tr>--%>
                            <%--<td>32423331</td>--%>
                            <%--<td><span>2016.6.6</span><br><span>14:14:15</span></td>--%>
                            <%--<td><span>棉花糖KTV</span><br><span>（231313123）</span></td>--%>
                            <%--<td><span>18710089228</span><br><span>（231313123）</span></td>--%>
                            <%--<td>已支付</td>--%>
                            <%--<td>导流订单</td>--%>
                            <%--<td>银行卡</td>--%>
                            <%--<td><span>¥510</span><br><span>（¥480+¥30红包）</span></td>--%>
                            <%--<td>¥48+¥3红包</td>--%>
                            <%--<td>¥432+¥27红包</td>--%>
                            <%--<td>¥25.5</td>--%>
                            <%--<td>30</td>--%>
                            <%--<td>¥21.52</td>--%>
                            <%--<td></td>--%>
                            <%--</tr>--%>
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
    <%@include file="../common/bottom.jsp" %>
</div>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<!--<script src="js/bootstrap-datetimepicker.min.js"></script>-->
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<!--<script src="js/bootstrap-datetimepicker.zh-CN.js"></script>-->
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var posOrderCriteria = {};
    posOrderCriteria.offset = 1;
    var orderContent = document.getElementById("orderContent");
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
        $.ajax({
                   type: 'GET',
                   url: '/manage/city/ajax',
                   async: false,
                   dataType: 'json',
                   success: function (data) {
                       console.log(data[0]);
                       var dataStr1 = '',
                               dataStr2 = '';
                       $.each(data, function (i) {
                           dataStr1 +=
                           '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                       });
                       $('#locationCity').append(dataStr1);
                   },
                   error: function (jqXHR) {
                       alert('发生错误：' + jqXHR.status);
                   }
               });
    })
    //    时间选择器
    $(document).ready(function () {
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
    })
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
    function getPosOrderByAjax(posOrderCriteria) {
        orderContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/pos_order",
                   async: false,
                   data: JSON.stringify(posOrderCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var page = data.data;
                       var content = page.content;
                       var totalPage = page.totalPages;
                       $("#totalElements").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       initPage(posOrderCriteria.offset, totalPage);
                       var orderContent = document.getElementById("orderContent");
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].orderSid + '</td>';
                           if (content[i].rebateWay == 1) {
                               contentStr +=
                               '<td><span>非会员普通订单</span></td>';
                           } else if (content[i].rebateWay == 2) {
                               contentStr +=
                               '<td><span>会员普通订单</span></td>';
                           } else {
                               contentStr +=
                               '<td><span>导流订单</span></td>';
                           }
                           if (content[i].completeDate == null) {
                               contentStr +=
                               '<td><span>未完成的订单</span></td>';
                           } else {
                               contentStr +=
                               '<td><span>'
                               + new Date(content[i].completeDate).format('yyyy-MM-dd HH:mm:ss')
                               + '</span></td>';
                           }

                           contentStr +=
                           '<td><span>' + content[i].merchant.name + '</span><br><span>('
                           + content[i].merchant.merchantSid + ')</span></td>'
                           if (content[i].leJiaUser.phoneNumber != null) {
                               contentStr +=
                               '<td><span>' + content[i].leJiaUser.phoneNumber
                               + '</span><br><span>('
                               + content[i].leJiaUser.userSid + ')</span></td>'
                           } else {
                               contentStr +=
                               '<td><span>未绑定手机号</span><br><span>('
                               + content[i].leJiaUser.userSid + ')</span></td>'
                           }
                           if (content[i].state == 1) {
                               if (content[i].tradeFlag == 0) {
                                   contentStr +=
                                   '<td>支付宝</td>'
                               } else if (content[i].tradeFlag == 3) {
                                   contentStr +=
                                   '<td>pos刷卡</td>'
                               } else if (content[i].tradeFlag == 4) {
                                   contentStr +=
                                   '<td>微信</td>'
                               } else if (content[i].tradeFlag == 5) {
                                   contentStr +=
                                   '<td>纯积分</td>'
                               }

                           } else {
                               contentStr +=
                               '<td>--</td>'
                           }
                           if (content[i].state == 1) {
                               contentStr +=
                               '<td>¥' + content[i].totalPrice / 100 + '(¥' + content[i].truePay
                                                                              / 100
                               + '+¥' + content[i].trueScore / 100 + '红包)</td>'
                           } else {
                               contentStr +=
                               '<td>¥' + content[i].totalPrice / 100
                               + '</td>'
                           }
                           if (content[i].state == 1) {
                               var bankCommission = content[i].truePay - content[i].transferByBank;
                               var scoreACommission = content[i].ljCommission - bankCommission;
                               contentStr +=
                               '<td>¥' + bankCommission / 100 + "+¥" + scoreACommission / 100
                               + '红包</td>';
                               contentStr +=
                               '<td>' + content[i].transferByBank / 100 + '+¥'
                               + (content[i].transferMoney - content[i].transferByBank) / 100
                               + '红包)</td>'
                               contentStr += '<td>¥' + content[i].rebate / 100 + '</td>'
                               contentStr += '<td>' + content[i].scoreB + '</td>'
                               contentStr +=
                               '<td>' + (content[i].ljCommission - content[i].rebate
                                         - content[i].wxCommission) / 100 + '</td>'

                               contentStr += '<td>已支付</td></tr>'
                           } else {
                               contentStr +=
                               '<td>--</td>';
                               contentStr +=
                               '<td>--</td>'
                               contentStr +=
                               '<td>--</td>'
                               contentStr +=
                               '<td>--</td>'
                               contentStr +=
                               '<td>--</td>'
                               contentStr += '<td>未付款</td></tr>';
                           }
                           orderContent.innerHTML += contentStr;

                       }
                   }
               });
    }
    getPosOrderByAjax(posOrderCriteria);
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             posOrderCriteria.offset = p;
                                             getPosOrderByAjax(posOrderCriteria);
                                         }
                                     });
    }

    function searchOrderByState(state) {
        posOrderCriteria.offset = 1;
        if (state != null) {
            posOrderCriteria.state = state;
        } else {
            posOrderCriteria.state = null;
        }
        getPosOrderByAjax(posOrderCriteria);
    }

    function searchOrderByCriteria() {
        posOrderCriteria.offset = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            posOrderCriteria.startDate = startDate;
            posOrderCriteria.endDate = endDate;
        }
        if ($("#locationCity").val() != "" && $("#locationCity").val() != null) {
            posOrderCriteria.merchantLocation = $("#locationCity").val();
        } else {
            posOrderCriteria.merchantLocation = null;
        }
        if ($("#orderType").val() != 0) {
            posOrderCriteria.rebateWay = $("#orderType").val();
        } else {
            posOrderCriteria.rebateWay = null;
        }
        if ($("#tradeFlag").val() != "" && $("#tradeFlag").val() != null) {
            posOrderCriteria.tradeFlag = $("#tradeFlag").val();
        } else {
            posOrderCriteria.tradeFlag = null;
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            posOrderCriteria.merchant = $("#merchant-name").val();
        } else {
            posOrderCriteria.merchant = null;
        }
        if ($("#userSid").val() != "" && $("#userSid").val() != null) {
            posOrderCriteria.userSid = $("#userSid").val();
        } else {
            posOrderCriteria.userSid = null;
        }

        if ($("#userPhone").val() != "" && $("#userPhone").val() != null) {
            posOrderCriteria.userPhone = $("#userPhone").val();
        } else {
            posOrderCriteria.userPhone = null;
        }

        getPosOrderByAjax(posOrderCriteria);
    }


    function billingDownload() {
        location.href="/manage/billingDownload"
    }
    function reconciliationDifferences() {
        location.href="/manage/reconciliationDifferences"
    }
</script>


</body>
</html>

