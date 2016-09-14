<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/9/5
  Time: 下午4:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div class="container-fluid clearfix">
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-2">
                        <label>商户所在城市</label>
                        <select class="form-control" id="locationCity">
                            <option value="">所在城市（全部）</option>
                        </select>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="date-end">添加时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-2">
                        <label>乐加商户</label>
                        <input type="text" id="merchant-name" class="form-control" placeholder="请输入商户名称或ID查询">
                    </div>
                    <div class="form-group col-md-2">
                        <label>佣金状态</label>
                        <select class="form-control" id="commission-state">
                            <option value="">佣金状态（全部）</option>
                            <option value="1">已开通</option>
                            <option value="0">未开通</option>
                        </select>
                    </div>
                    <div class="form-group col-md-1">
                        <button class="btn btn-primary" style="margin-top: 24px" onclick="searchOrderByCriteria()">查询</button>
                    </div>
                </div>
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr class="active">
                        <th>终端号</th>
                        <th>PSAM卡号</th>
                        <th>乐加商户</th>
                        <th>POS商户号</th>
                        <th>POS注册手机号</th>
                        <th>添加时间</th>
                        <th>佣金状态</th>
                        <th>普通订单流水</th>
                        <th>导流订单流水</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="posContent">
                    </tbody>
                </table>
                <div class="tcdPageCode" style="display: inline;">
                </div>
                <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
            </div>
        </div>
    </div>
</div>

<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!--开通佣金提示框-->
<div class="modal fade" id="openCommissionWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">close</span></button>
                <h4 class="modal-title">开通佣金</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-offset-2 control-label">终端号</label>

                        <div class="col-sm-4">
                            <p id="open-posid"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-offset-2 control-label">佣金比</label>

                        <div class="col-sm-6 form-inline">
                            <input type="text" id="open-commission" class="form-control">%
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="open-confirm"
                        data-dismiss="modal">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--修改佣金提示框-->
<div class="modal fade" id="modifyCommissionWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">close</span></button>
                <h4 class="modal-title">修改佣金</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-offset-2 control-label">终端号</label>

                        <div class="col-sm-4">
                            <p id="change-posid"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-offset-2 control-label">佣金比</label>

                        <div class="col-sm-6 form-inline">
                            <input type="text" id="change-commission" class="form-control">%
                        </div>
                    </div>
                    <div class="form-group">
                        <p class="col-sm-offset-2 col-sm-8"><font color="red">注意：中汇实际结算时的佣金比例以三方协议为准，在此修改佣金比例只是为了保证平台记账和中汇记账一致。</font>
                        </p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="change-confirm"
                        data-dismiss="modal">确认
                </button>
            </div>
        </div>
    </div>
</div>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<!--<script src="js/bootstrap-datetimepicker.min.js"></script>-->
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<!--<script src="js/bootstrap-datetimepicker.zh-CN.js"></script>-->
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var posCriteria = {};
    posCriteria.offset = 1;
    var posContent = document.getElementById("posContent");
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
    function getPosByAjax(posCriteria) {
        posContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/pos",
                   async: false,
                   data: JSON.stringify(posCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var page = data.data;
                       var content = page.content;
                       var totalPage = page.totalPages;
                       $("#totalElements").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       initPage(posCriteria.offset, totalPage);
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].posId + '</td>';
                           contentStr += '<td>' + content[i].psamCard + '</td>';

                           contentStr +=
                           '<td><span>' + content[i].merchant.name + '</span><br><span>('
                           + content[i].merchant.merchantSid + ')</span></td>'
                           contentStr += '<td>' + content[i].posMerchantNo + '</td>';
                           contentStr += '<td>' + content[i].phoneNumber + '</td>';
                           contentStr +=
                           '<td>'
                           + new Date(content[i].createdDate).format('yyyy-MM-dd HH:mm:ss')
                           + '</td>';
                           if (content[i].ljCommission != null) {
                               contentStr += '<td>已开通(' + content[i].ljCommission + '%)</td>';
                           } else {
                               contentStr += '<td>未开通</td>';
                           }
                           contentStr += '<td>' + content[i].normalOrderFlow / 100 + '</td>';
                           contentStr += '<td>' + content[i].importOrderFlow / 100 + '</td>';
                           if (content[i].ljCommission == null) {
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="'
                               + content[i].posId
                               + '"><input type="button" class="btn btn-xs btn-primary openCommission" data-toggle="modal" data-target="#openCommissionWarn" value="开通佣金"></td></tr>';
                           } else {
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="'
                               + content[i].posId
                               + '"><input type="button" class="btn btn-xs btn-primary changeCommission" data-toggle="modal" data-target="#modifyCommissionWarn"value="修改佣金"></td></tr>';
                           }
                           posContent.innerHTML += contentStr;
                       }
                       $(".openCommission").each(function (i) {
                           $(".openCommission").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $("#open-posid").html(id);
                               $("#open-confirm").bind("click", function () {
                                   var commission = $("#open-commission").val();
                                   $("#open-confirm").unbind("click");
                                   $.ajax({
                                              type: "get",
                                              data: {id: id, commission: commission},
                                              url: "/manage/pos/change_commission",
                                              contentType: "application/json",
                                              success: function (data) {
                                                  alert(data.msg);
                                                  getPosByAjax(posCriteria);
                                              }
                                          });
                               });

                           });
                       });
                       $(".changeCommission").each(function (i) {
                           $(".changeCommission").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $("#change-posid").html(id);
                               $("#change-confirm").bind("click", function () {
                                   $("#change-confirm").unbind("click");
                                   var commission = $("#change-commission").val();
                                   $.ajax({
                                              type: "get",
                                              data: {id: id, commission: commission},
                                              url: "/manage/pos/change_commission",
                                              contentType: "application/json",
                                              success: function (data) {
                                                  alert(data.msg);
                                                  getPosByAjax(posCriteria);
                                              }
                                          });
                               });
                           });
                       });
                       ;
                   }
               });
    }
    getPosByAjax(posCriteria)

    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             posCriteria.offset = p;
                                             getPosByAjax(posCriteria);
                                         }
                                     });
    }
    function searchOrderByCriteria() {
        posCriteria.offset = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            posCriteria.startDate = startDate;
            posCriteria.endDate = endDate;
        }
        if ($("#locationCity").val() != "" && $("#locationCity").val() != null) {
            posCriteria.merchantLocation = $("#locationCity").val();
        } else {
            posCriteria.merchantLocation = null;
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            posCriteria.merchant = $("#merchant-name").val();
        } else {
            posCriteria.merchant = null;
        }

        if ($("#commission-state").val() != "" && $("#commission-state").val() != null) {
            posCriteria.state = $("#commission-state").val();
        } else {
            posCriteria.state = null;
        }

        getPosByAjax(posCriteria);
    }
</script>


</body>
</html>

