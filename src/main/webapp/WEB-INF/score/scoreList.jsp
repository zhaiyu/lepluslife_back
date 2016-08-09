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
    <script src="${resourceUrl}/js/jquery.page.js"></script>
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
            <button type="button" class="btn btn-primary btn-return" style="margin:20px;"
                    onclick="goUserPage()">
                返回会员列表
            </button>
            <h3 style="margin:20px;display: inline">账户明细-->${user.weiXinUser.nickname}</h3><br>

            <h4 style="margin:20px;display: inline">累计获得红包: ￥<span
                    style="color: red;font-size: 30px">${scoreA/100}</span></h4>

            <h4 style="margin:20px;display: inline">累计获得积分: <span
                    style="color: blue;font-size: 30px">${scoreB}</span></h4>

            <div class="container-fluid">
                <ul id="myTab" class="nav nav-tabs" style="margin-top: 10px">
                    <li><a href="#tab1" data-toggle="tab"
                           onclick="searchScoreByType(0)">红包明细</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab"
                                          onclick="searchScoreByType(1)">积分明细</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <%--<div class="row" style="margin-top: 30px">--%>
                        <%--<div class="form-group col-md-5">--%>
                        <%--<label for="date-end" id="date-content"></label>--%>

                        <%--<div id="date-end" class="form-control">--%>
                        <%--<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>--%>
                        <%--<span id="searchDateRange"></span>--%>
                        <%--<b class="caret"></b>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group col-md-3">--%>
                        <%--<label for="merchant-info">商户信息</label>--%>
                        <%--<input id="merchant-name" type="text" id="merchant-info"--%>
                        <%--class="form-control"--%>
                        <%--placeholder="请输入商户名称或ID"/>--%>
                        <%--</div>--%>
                        <%--<div class="form-group col-md-1">--%>
                        <%--<button class="btn btn-primary" style="margin-top: 24px"--%>
                        <%--onclick="searchScoreByCriteria()">查询--%>
                        <%--</button>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active" id="head-content">
                                <th class="text-center">变更内容</th>
                                <th class="text-center">变更项目</th>
                                <th class="text-center">变更来源</th>
                                <th class="text-center">变更时间</th>
                                <th class="text-center">订单号</th>
                            </tr>
                            </thead>
                            <tbody id="scoreContent">
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
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script>
    var scoreCriteria = {};
    var flag = true;
    var status = 0;  //0=红包 1=积分
    var scoreContent = document.getElementById("scoreContent");
    //  var headContent = document.getElementById("head-content");
    //  var dateContent = document.getElementById("date-content");
    $(function () {
//        tab切换
        $('#myTab li:eq(0) a').tab('show');
//        提示框

    });
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
        scoreCriteria.offset = 1;
        scoreCriteria.id = '${scoreAID}';

        getScoreByTypeAjax(scoreCriteria);
    })

    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             scoreCriteria.offset = p;
                                             getScoreByTypeAjax(scoreCriteria);
                                         }
                                     });
    }

    function getScoreByTypeAjax(scoreCriteria) {
        scoreContent.innerHTML = "";
        //   headContent.innerHTML = "";
        //  dateContent.innerHTML = "";
        if (status == 0) {
            scoreCriteria.id = '${scoreAID}';
            $.ajax({
                       type: "post",
                       url: "/manage/score/listA",
                       async: false,
                       data: JSON.stringify(scoreCriteria),
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
                               initPage(scoreCriteria.offset, totalPage);
                           }
//                       headContent.innerHTML =
//                       '<th>结算单号</th><th>结算日期</th><th>商户信息</th><th>结算方式</th><th>结算账户信息</th><th>结算周期</th><th>收款人</th>';

                           for (i = 0; i < content.length; i++) {
                               var contentStr = '<tr><td>' + content[i].number / 100 + '</td>';
                               contentStr += '<td>' + content[i].operate + '</td>';
                               if (content[i].origin == 3) {
                                   contentStr += '<td>线下消费</td>';
                               } else if (content[i].origin == 4) {
                                   contentStr += '<td>线下返还</td>';
                               } else if (content[i].origin == 1) {
                                   contentStr += '<td>线上返还</td>';
                               } else if (content[i].origin == 2) {
                                   contentStr += '<td>线上消费</td>';
                               } else {
                                   contentStr += '<td>未知</td>';
                               }
                               contentStr +=
                               '<td><span>'
                               + new Date(content[i].dateCreated).format('yyyy-MM-dd HH:mm')
                               + '</span></td>';
                               contentStr += '<td>' + content[i].orderSid + '</td>';
                               scoreContent.innerHTML += contentStr;
                           }
                       }
                   });
        } else {
            scoreCriteria.id = '${scoreBID}';
            $.ajax({
                       type: "post",
                       url: "/manage/score/listB",
                       async: false,
                       data: JSON.stringify(scoreCriteria),
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
                               initPage(scoreCriteria.offset, totalPage);
                           }

                           for (i = 0; i < content.length; i++) {
                               var contentStr = '<tr><td>' + content[i].number + '</td>';
                               contentStr += '<td>' + content[i].operate + '</td>';
                               if (content[i].origin == 3) {
                                   contentStr += '<td>线下消费</td>';
                               } else if (content[i].origin == 4) {
                                   contentStr += '<td>线下返还</td>';
                               } else if (content[i].origin == 1) {
                                   contentStr += '<td>线上返还</td>';
                               } else if (content[i].origin == 2) {
                                   contentStr += '<td>线上消费</td>';
                               } else {
                                   contentStr += '<td>未知</td>';
                               }
                               contentStr +=
                               '<td><span>'
                               + new Date(content[i].dateCreated).format('yyyy-MM-dd HH:mm')
                               + '</span></td>';
                               contentStr += '<td>' + content[i].orderSid + '</td>';
                               scoreContent.innerHTML += contentStr;
                           }
                       }
                   });
        }

    }
    function searchScoreByType(state) {
        scoreCriteria.offset = 1;
        status = state;
        flag = true;
        getScoreByTypeAjax(scoreCriteria);
    }

    function searchScoreByCriteria() {
        scoreCriteria.offset = 1;
        flag = true;
        getScoreByTypeAjax(scoreCriteria);
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
    };

    function goUserPage() {
        location.href = "/manage/user";
    }
</script>
</body>
</html>

