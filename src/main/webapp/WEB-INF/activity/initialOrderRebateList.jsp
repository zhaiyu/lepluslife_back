<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/9/12
  Time: 下午2:49
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
<style>
    .w-tabs {
        width: 100%;
        margin: 20px 0;
        border-bottom: 1px solid #000000;
        border-top: 1px solid #000000;
    }

    .w-tabs > div {
        width: 12%;
        margin: 0 2%;
        float: left;
        text-align: center;
    }

    .w-tabs > div > div:first-child {
        padding: 40px 0 0px 0;
        font-size: 18px;
    }

    .w-tabs > div > div:last-child {
        padding: 20px 0;
        color: #bbb;
    }

    .w-tabs:after {
        content: '\20';
        display: block;
        clear: both;
    }
</style>
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
                <h1>首单返红包活动</h1>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab">活动商户</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab">首单列表</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <div class="row" style="margin-top: 30px">
                            <div class="form-group col-md-2">
                                <label for="shop-name">商户名称</label>
                                <input type="text" id="shop-name" class="form-control"
                                       placeholder="商户名称"/>
                            </div>
                            <div class="form-group col-md-2">
                                <label>活动状态</label>
                                <select class="form-control" id="activity-state">
                                    <option value="-1">全部状态</option>
                                    <option value="1">已参与</option>
                                    <option value="0">未参与</option>
                                </select>
                            </div>
                            <div class="form-group col-md-2">
                                <label>微信号绑定</label>
                                <select class="form-control" id="bind-wx">
                                    <option value="-1">全部状态</option>
                                    <option value="1">已绑定</option>
                                    <option value="0">未绑定</option>
                                </select>
                            </div>
                            <div class="form-group col-md-3">
                                <button class="btn btn-primary" style="margin-top: 24px"
                                        onclick="searchActivityByCriteria()">查询
                                </button>
                            </div>
                        </div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>登录ID</th>
                                <th>商户名称</th>
                                <th>活动状态</th>
                                <th>商户类型</th>
                                <th>店主微信号</th>
                                <th>累计产生首单</th>
                                <th>累计返还</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="activityContent">
                            </tbody>
                        </table>
                        <div class="tcdPageCode" style="display: inline;">
                        </div>
                        <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                    </div>
                    <div class="tab-pane fade in active" id="tab2">
                        <div class="row" style="margin-top: 30px">
                            <div class="form-group col-md-2">
                                <label for="shop-name">商户名称</label>
                                <input type="text" id="shop-name2" class="form-control"
                                       placeholder="商户名称"/>
                            </div>
                            <div class="form-group col-md-5">
                                <label for="date-end">订单完成时间</label>

                                <div id="date-end" class="form-control">
                                    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                    <span id="searchDateRange"></span>
                                    <b class="caret"></b>
                                </div>
                            </div>
                            <div class="form-group col-md-2">
                                <label>奖励状态</label>
                                <select class="form-control" id="log-state">
                                    <option value="-1">全部类型</option>
                                    <option value="1">已发放</option>
                                    <option value="0">未发放</option>
                                </select>
                            </div>
                            <div class="form-group col-md-3">
                                <button class="btn btn-primary" style="margin-top: 24px" onclick="searchMerchantActivityLogByCriteria()">查询</button>
                            </div>
                        </div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>商户ID</th>
                                <th>商户名称</th>
                                <th>消费者</th>
                                <th>订单类型</th>
                                <th>订单金额</th>
                                <th>完成时间</th>
                                <th>奖励金</th>
                                <th>店主微信</th>
                                <th>奖励状态</th>
                            </tr>
                            </thead>
                            <tbody id="logContent">
                            </tbody>
                        </table>
                        <div class="tcdPageCode" style="display: inline;">
                        </div>
                        <div style="display: inline;"> 共有 <span id="logTotalElements"></span> 个
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<style>
    #addList > div {
        float: left;
    }

    .w-hot {
        width: 50%;
        margin: 10px 0;
    }

    #addList:after {
        content: '\20';
        display: block;
        clear: both;
    }
</style>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!--删除提示框-->
<style>
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
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <!--<h4 class="modal-title">热门关键词</h4>-->
            </div>
            <div class="modal-body" style="text-align: center;font-size: 18px;padding: 20px 0;">
                <p>是否确定退出活动？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="quit-confirm" class="btn btn-primary"
                        data-dismiss="modal">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--添加提示框-->
<div class="modal" id="type1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">

                    <div class="form-group">
                        <label class="col-sm-3 control-label">首单定义</label>

                        <%--<div class="col-sm-7">--%>
                            <%--<span>消费者产生的首笔超过</span>--%>
                            <%--<input name="bannerType"  type="number" style="width: 20%">--%>
                            <%--<span>元订单</span>--%>
                        <%--</div>--%>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">首单奖励金额</label>

                        <div class="col-sm-9">
                            <div style="margin: 5px 0;">
                                <input type="radio" name="money"
                                       value="0"><span>&nbsp;固定金额&nbsp;</span><input
                                    type="number" id="fixValue" placeholder="输入固定金额值"
                                    class="w-input"
                                    style="width: 30%"/>
                            </div>
                            <div style="margin: 5px 0;">
                                <input type="radio" name="money"
                                       value="1"><span>&nbsp;随机金额&nbsp;</span><input
                                    type="number" id="minValue" placeholder="输入随机数下限"
                                    class="w-input"
                                    style="width: 30%"/><span>&nbsp;~&nbsp;</span><input
                                    type="number" id="maxValue" placeholder="输入随机数上限"
                                    class="w-input"
                                    style="width: 30%"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">每日奖励上限</label>

                        <div class="col-sm-9">
                            <div style="margin: 5px 0;">
                                <input type="radio" name="limit"
                                       value="0"><span>&nbsp;无上限&nbsp;</span>
                            </div>
                            <div style="margin: 5px 0;">
                                <input type="radio" name="limit"
                                       value="1"><span>&nbsp;有上限&nbsp;</span><input
                                    type="number" id="rebateLimit" placeholder="每日奖励上限"
                                    class="wh-input"
                                    style="width: 30%"/>
                            </div>
                        </div>
                        <script>
                            $(".w-input,.wh-input").attr("disabled", "true");
                            $("input[checked=true]").nextAll().removeAttr("disabled");
                            $("input[name=money]").click(function (e) {
                                $(".w-input").attr("disabled", "true");
                                $(this).nextAll().removeAttr("disabled");
                            });
                            $("input[name=limit]").click(function (e) {
                                $(".wh-input").attr("disabled", "true");
                                $(this).nextAll().removeAttr("disabled");
                            });
                        </script>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary"
                        id="activity-confirm">确认
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
    var criteria = {};
    var logCriteria = {};
    criteria.offset = 1;
    logCriteria.offset = 1;
    var activityContent = document.getElementById("activityContent");
    var logContent = document.getElementById("logContent");
    function reset(num) {
        var iid = "type" + num;
        $("#" + iid + " input").val("");
        $("#" + iid + " img").attr("src", "");
        $("#" + iid + " textarea").val("");
    }
</script>
<script>
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
//        $(".type1").click(function () {
//            $("#type1").modal("toggle");
//        });
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
    function getMerchantActivityByAjax(cirteria) {
        activityContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/activity/initial_order_rebate",
                   async: false,
                   data: JSON.stringify(cirteria),
                   contentType: "application/json",
                   success: function (data) {
                       var page = data.data;
                       var content = page.content;
                       var totalPage = page.pages;
                       $("#totalElements").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       initPage(cirteria.offset, totalPage);
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i][0] + '</td>';
                           contentStr +=
                           '<td>' + content[i][1] + '</td>'
                           if (content[i][2] == 1) {
                               contentStr +=
                               '<td>已开启</td>'
                           } else {
                               contentStr +=
                               '<td>未开启</td>'
                           }
                           if (content[i][3] == 1) {
                               contentStr +=
                               '<td>联盟商户</td>'
                           } else {
                               contentStr +=
                               '<td>普通商户</td>'
                           }
                           if (content[i][4] != null) {
                               var str = content[i][4].split("#=$");
                               contentStr +=
                               '<td><span><img src="' + str[0]
                               + '" width="40px" height="40px" alt=""></span><br><span>' + str[1]
                               + '</span></td>'
                           } else {
                               contentStr +=
                               '<td>--</td>'
                           }
                           if (content[i][5] != null) {
                               contentStr +=
                               '<td>' + content[i][5] + '</td>'
                           } else {
                               contentStr +=
                               '<td>0</td>'
                           }
                           if (content[i][6] != null) {
                               contentStr +=
                               '<td>¥' + content[i][6] / 100 + '</td>'
                           } else {
                               contentStr +=
                               '<td>¥0</td>'
                           }
                           if (content[i][2] == 1) {
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i][0]
                               + '"><input type="button" class="btn btn-xs btn-primary editActivity"value="编辑"><input type="button" class="btn btn-xs btn-primary quitActivity"value="退出活动"></td></tr>';
                           } else {
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i][0]
                               + '"> <input type="button" class="btn btn-xs btn-primary joinActivity" value="参与活动"></tr>';
                           }
                           activityContent.innerHTML += contentStr;
                       }
                       $(".joinActivity").each(function (i) {
                           $(".joinActivity").eq(i).unbind("click");
                           $(".joinActivity").eq(i).bind("click", function () {
                               clearTable();
                               var id = $(this).parent().find(".id-hidden").val();
                               $("#activity-confirm").bind("click", function () {
                                   if (!checkTable()) {
                                       return;
                                   }
                                   $("#activity-confirm").unbind("click")
                                   $.ajax({
                                              type: "post",
                                              data: JSON.stringify(getPostParams(id)),
                                              url: "/manage/activity/initial_order_rebate/join",
                                              contentType: "application/json",
                                              success: function (data) {
                                                  alert(data.msg);
                                                  $("#type1").modal("hide");
                                                  getMerchantActivityByAjax(cirteria);
                                              }
                                          });
                               });
                               $("#type1").modal("toggle");
                           });

                       });
                       $(".editActivity").each(function (i) {
                           $(".editActivity").eq(i).unbind("click");
                           $(".editActivity").eq(i).bind("click", function () {
                               clearTable();
                               var id = $(this).parent().find(".id-hidden").val();
                               $.get("/manage/activity/initial_order_rebate/" + id,
                                     function (result) {
                                         var data = result.data;
                                         if (data.rebateType == 0) {
                                             $("input[name=money]")[0].checked = true;
                                             $(".w-input").attr("disabled", "true");
                                             $($("input[name=money]")[0]).nextAll().removeAttr("disabled");
                                             $("#fixValue").val(data.maxRebate / 100);
                                         } else {
                                             $("input[name=money]")[1].checked = true;
                                             $($("input[name=money]")[1]).nextAll().removeAttr("disabled");
                                             $("#maxValue").val(data.maxRebate / 100);
                                             $("#minValue").val(data.minRebate / 100);
                                         }
                                         if (data.dailyRebateType == 0) {
                                             $("input[name=limit]")[0].checked = true;
                                         } else {
                                             $("input[name=limit]")[1].checked = true;
                                             $($("input[name=limit]")[1]).nextAll().removeAttr("disabled");
                                             $("#rebateLimit").val(data.dailyRebateLimit / 100);
                                         }

//                                         $("#limit").val(data.limit / 100);

                                         $("#activity-confirm").bind("click", function () {
                                             if (!checkTable()) {
                                                 return;
                                             }
                                             $("#activity-confirm").unbind("click")
                                             $.ajax({
                                                        type: "post",
                                                        data: JSON.stringify(getPostParams(id)),
                                                        url: "/manage/activity/initial_order_rebate/join",
                                                        contentType: "application/json",
                                                        success: function (data) {
                                                            alert(data.msg);
                                                            $("#type1").modal("hide");
                                                            getMerchantActivityByAjax(cirteria);
                                                        }
                                                    });
                                         });
                                         $("#type1").modal("toggle");
                                     });
                           });
                       });
                       $(".quitActivity").each(function (i) {
                           $(".quitActivity").eq(i).unbind("click");
                           $(".quitActivity").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $("#quit-confirm").bind("click", function () {
                                   $("#quit-confirm").unbind("click")
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/activity/initial_order_rebate/quit/"
                                                   + id,
                                              contentType: "application/json",
                                              success: function (data) {
                                                  alert(data.msg);
                                                  getMerchantActivityByAjax(cirteria);
                                              }
                                          });
                               });
                               $("#deleteWarn").modal("toggle");
                           });

                       });
                   }
               });
    }
    function checkTable() {
//        if ($("#limit").val() == null || $("#limit").val() == "" || $("#limit").val() <= 0) {
//            alert("请输入首单成立金额");
//
//            return false;
//        }
        if ($("input[name=money]:checked").val() == null) {
            alert("请输入首单奖励金额");
            return false;
        }
        if ($("input[name=money]:checked").val() == 0) {
            if ($("#fixValue").val() <= 0) {
                alert("固定金额不得小于0");
                return false;
            }
        }
        if ($("input[name=money]:checked").val() == 1) {
            if ($("#minValue").val() < 0 || $("#maxValue").val() < 0) {
                alert("金额不得小于0");
                return false;
            }
            if ($("#maxValue").val() < $("#minValue").val()) {
                alert("最大金额不得小于最小金额");
                return false;
            }
        }
        if ($("input[name=limit]:checked").val() == null) {
            alert("请输入每日奖励上线");
            return false;
        }
        if ($("input[name=limit]:checked").val() == 1) {
            if ($("#rebateLimit").val() < 0) {
                alert("金额不得小于0");
                return false;
            }
        }
        return true;
    }
    function clearTable() {
        $("input[name=money]:checked").prop("checked", false);
        $("input[name=limit]:checked").prop("checked", false);
//        $("#limit").val("");
        $("#fixValue").val("");
        $("#minValue").val("");
        $("#maxValue").val("");
        $("#rebateLimit").val("");
    }
    function getPostParams(id) {
        var map = {};
        map.id = id;
//        map.limit = $("#limit").val();
        if ($("input[name=money]:checked").val() == 1) {
            map.maxRebate = $("#maxValue").val();
            map.minRebate = $("#minValue").val();
            map.rebateType = 1;
        } else {
            map.maxRebate = $("#fixValue").val();
            map.rebateType = 0;
        }
        if ($("input[name=limit]:checked").val() == 1) {
            map.dailyRebateType = 1;
            map.dailyRebateLimit = $("#rebateLimit").val();
        } else {
            map.dailyRebateType = 0;
        }
        return map;
    }
    getMerchantActivityByAjax(criteria);
    function initPage(page, totalPage) {
        $($('.tcdPageCode')[0]).unbind();
        $($('.tcdPageCode')[0]).createPage({
                                               pageCount: totalPage,
                                               current: page,
                                               backFn: function (p) {
                                                   criteria.offset = p;
                                                   getMerchantActivityByAjax(criteria);
                                               }
                                           });
    }
    function searchActivityByCriteria() {
        criteria.offset = 1;
        if ($("#shop-name").val() != "" && $("#shop-name").val() != null) {
            criteria.merchant = $("#shop-name").val();
        } else {
            criteria.merchant = null;
        }
        if ($("#activity-state").val() != -1) {
            criteria.state = $("#activity-state").val();
        } else {
            criteria.state = null;
        }
        if ($("#bind-wx").val() != -1) {
            criteria.bindWxState = $("#bind-wx").val();
        } else {
            criteria.bindWxState = null;
        }

        getMerchantActivityByAjax(criteria);
    }
</script>

<%--首单列表js--%>
<script>
    function getMerchantActivityLogByAjax(cirteria) {
        logContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/activity/initial_order_rebate_log",
                   async: false,
                   data: JSON.stringify(cirteria),
                   contentType: "application/json",
                   success: function (data) {
                       var page = data.data;
                       var content = page.content;
                       var totalPage = page.totalPages;
                       $("#logTotalElements").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       initLogPage(cirteria.offset, totalPage);
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>'
                                            + content[i].offLineOrder.merchant.merchantSid
                                            + '</td>';
                           contentStr +=
                           '<td>' + content[i].offLineOrder.merchant.name + '</td>'
                           if (content[i].offLineOrder.leJiaUser.phoneNumber != null) {
                               contentStr +=
                               '<td><span>' + content[i].offLineOrder.leJiaUser.phoneNumber
                               + '</span><br><span>('
                               + content[i].offLineOrder.leJiaUser.userSid + ')</span></td>'
                           } else {
                               contentStr +=
                               '<td><span>未绑定手机号</span><br><span>('
                               + content[i].offLineOrder.leJiaUser.userSid + ')</span></td>'
                           }
                           if (content[i].offLineOrder.rebateWay == 0) {
                               contentStr +=
                               '<td><span>普通订单(非会员)</span></td>';
                           } else if (content[i].offLineOrder.rebateWay == 2) {
                               contentStr +=
                               '<td><span>普通订单(会员)</span></td>';
                           } else if (content[i].offLineOrder.rebateWay == 1) {
                               contentStr +=
                               '<td><span>导流订单</span></td>';
                           } else if (content[i].offLineOrder.rebateWay == 3) {
                               contentStr +=
                               '<td><span>会员订单</span></td>';
                           } else if (content[i].offLineOrder.rebateWay == 4) {
                               contentStr +=
                               '<td><span>非会员纯支付码</span></td>';
                           } else {
                               contentStr +=
                               '<td><span>会员纯支付码</span></td>';
                           }
                           contentStr +=
                           '<td>' + content[i].offLineOrder.totalPrice / 100 + '</td>';

                           contentStr +=
                           '<td><span>'
                           + new Date(content[i].offLineOrder.completeDate).format('yyyy-MM-dd HH:mm:ss')
                           + '</span></td>';
                           contentStr +=
                           '<td>' + content[i].rebate / 100 + '</td>';

                           contentStr +=
                           '<td><span><img src="' + content[i].headImageUrl
                           + '" width="40px" height="40px" alt=""></span><br><span>'
                           + content[i].nickname
                           + '</span></td>'

                           if (content[i].state == 1) {
                               contentStr += '<td><span>已发放</span><br><span></span></td></tr>';
                           } else {
                               contentStr +=
                               '<td><span>未发放</span><br><span>' + content[i].exceptionLog
                               + '</span></td></tr>'
                           }
                           logContent.innerHTML += contentStr;
                       }
                   }
               });
    }
    getMerchantActivityLogByAjax(logCriteria);

    function initLogPage(page, totalPage) {
        $($(".tcdPageCode")[1]).unbind();
        $($(".tcdPageCode")[1]).createPage({
                                               pageCount: totalPage,
                                               current: page,
                                               backFn: function (p) {
                                                   criteria.offset = p;
                                                   getMerchantActivityLogByAjax(criteria);
                                               }
                                           });
    }
    function searchMerchantActivityLogByCriteria() {
        logCriteria.offset = 1;
        if ($("#shop-name2").val() != "" && $("#shop-name2").val() != null) {
            logCriteria.merchant = $("#shop-name2").val();
        } else {
            logCriteria.merchant = null;
        }
        if ($("#log-state").val() != -1) {
            logCriteria.state = $("#log-state").val();
        } else {
            logCriteria.state = null;
        }
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            logCriteria.startDate = startDate;
            logCriteria.endDate = endDate;
        }

        getMerchantActivityLogByAjax(logCriteria);

    }
</script>
</body>
</html>

