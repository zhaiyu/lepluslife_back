<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/8/8
  Time: 下午3:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE>
<style>
    .pagination > li > a.focusClass {
        background-color: #ddd;
    }

    table tr td img {
        width: 80px;
    }

    .table > thead > tr > td, .table > thead > tr > th {
        line-height: 40px;
    }

    .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
        line-height: 80px;
    }

    .active img {
        width: 80px;
        height: 80px;
    }

</style>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>乐+生活 后台模板管理系统</title>
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>

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

            <h3 style="margin:20px;">活动参与人员列表
            </h3>

            <div class="row">
                <div class="col-md-12">
                    <div class="portlet">

                        <div class="row" style="margin-bottom: 30px">

                            <div class="form-group col-md-4">
                                <label for="date-end">创建时间</label>

                                <div id="date-end" class="form-control">
                                    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                    <span id="searchDateRange"></span>
                                    <b class="caret"></b>
                                </div>
                            </div>

                            <div class="form-group col-md-4">
                                <button class="btn btn-primary" style="margin-top: 24px"
                                        onclick="searchUserByCriteria()">筛选
                                </button>
                            </div>

                            <div class="form-group col-md-3"></div>
                        </div>

                        <div class="portlet-body">
                            <div class="table-container">

                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                     <%--   <th class="text-center">会员ID</th>--%>
                                        <th class="text-center">领取红包金额</th>
                                        <th class="text-center">领取时间</th>
                                        <th class="text-center">微信信息</th>
                                        <%--    <th class="text-center">关注方式</th>--%>
                                        <th class="text-center">关注时间</th>
                                    </tr>
                                    </thead>
                                    <tbody id="userContent">

                                    </tbody>
                                </table>

                                <div class="tcdPageCode" style="display: inline;">
                                </div>
                                <div style="display: inline;"> 共有 <span id="totalElements"></span> 个
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<!--如果只是做布局的话不需要下面两个引用-->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script>

    var userCriteria = {}, flag = true, search = 0, init1 = 0, id = '${id}';
    var userContent = document.getElementById("userContent");
    $(function () {
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
                                                                '七月', '八月', '九月', '十月', '十一月',
                                                                '十二月'],
                                                   firstDay: 1
                                               }
                                           }, function (start, end, label) {//格式化日期显示框
                $('#date-end span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                                         + end.format('YYYY/MM/DD HH:mm:ss'));
            });
        });
        userCriteria.offset = 1;
        getUserByAjax(userCriteria);
    });

    function getUserByAjax(criteria) {

        userContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/codeJoin/ajaxList/" + id,
                   async: false,
                   data: JSON.stringify(criteria),
                   contentType: "application/json",
                   success: function (data) {
                       var map = data.data;
                       var content = map.content;
                       var totalPage = map.totalPages;
                       var totalElements = map.totalElements;
                       $("#totalElements").html(totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag) {
                           initPage(userCriteria.offset, totalPage);
                           flag = false;
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }

                       var userContent = document.getElementById("userContent");
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td class="text-center">' + content[i].user.openId
                                            + '</td>';
                           contentStr += '<td class="text-center">' + content[i].detail + '</td>';
                           contentStr += '<td class="text-center"><span>'
                                         + new Date(content[i].createDate).format('yyyy-MM-dd')
                                         + '</span></td>';
                           contentStr +=
                           '<td class="text-center"><img src="' + content[i].user.headImageUrl
                           + '" alt="...">'
                           + content[i].user.nickname + '</td>';
                           contentStr += '<td class="text-center"><span>'
                                         + new Date(content[i].user.subDate).format('yyyy-MM-dd')
                                         + '</span></td></tr>';

                           userContent.innerHTML += contentStr;
                       }
                   }
               });
    }
    function initPage(currPage, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: currPage,
                                         backFn: function (p) {
                                             //单击回调方法，p是当前页码
                                             init1 = 0;
                                             userCriteria.offset = p;
                                             getUserByAjax(userCriteria);
                                         }
                                     });
    }

    function searchUserByCriteria() {
        userCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            userCriteria.startDate = startDate;
            userCriteria.endDate = endDate;
        }
        getUserByAjax(userCriteria);
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

</script>
</body>
</html>

