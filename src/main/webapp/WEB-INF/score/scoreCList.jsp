<%--
  Created by IntelliJ IDEA.
  User: lss
  Date: 2016/12/7
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../commen.jsp" %>
<<!DOCTYPE>
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/merchantManagement.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/merchantInformation.css"/>
    <!--[if lt IE 9]>
    <script src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script src="${resourceUrl}/js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${resourceUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/Mod/common.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/bootstrap.min.js"></script>
    <!--layer-->
    <script type="text/javascript" src="${resourceUrl}/js/layer/laydate/laydate.js"></script>
    <!--echarts-->
    <script type="text/javascript" src="${resourceUrl}/js/echarts.min.js"></script>
    <script src="${resourceUrl}/js/jquery.page.js"></script>
    <script src="${resourceUrl}/js/daterangepicker.js"></script>
    <%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
    <script src="${resourceUrl}/js/moment.min.js"></script>
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
                <h1>金币统计</h1>
                <div class="topDataShow">
                    <div>
                        <div><img src="${resourceUrl}/images/hb.png" alt=""></div>
                        <div>
                            <p>消费者当前持有金币</p>
                            <p>${scoreCSum/100.0}</p>
                        </div>
                    </div>
                    <div>
                        <div><img src="${resourceUrl}/images/hb.png" alt=""></div>
                        <div>
                            <p>累计发放金币</p>
                            <p>${sendScoreCSum/100.0}</p>
                        </div>
                    </div>
                    <div>
                        <div><img src="${resourceUrl}/images/hb.png" alt=""></div>
                        <div>
                            <p>消费已使用金币</p>
                            <p>${-useScoreCSum/100.0}</p>
                        </div>
                    </div>
                </div>


                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <div class="MODInput_row">
                            <div class="Mod-6" style="margin: 10px 0">
                                <div class="ModLabel ModRadius-left">有效期</div>
                                <div class="Mod-6" style="margin-top: -2px">
                                    <input id="HB_trend-startTime" class="laydate-icon layer-date ModRadius-right"
                                           placeholder="起始时间">
                                    <input id="HB_trend-endTime" class="laydate-icon layer-date ModRadius"
                                           placeholder="截止时间">
                                </div>
                            </div>
                            <div class="Mod-2">
                                <button class="ModButton ModButton_ordinary ModRadius"
                                        onclick="searchScoreCByCriteria()">筛选查询
                                </button>
                            </div>
                        </div>

                        <div>
                            <div class="merchant_management-table">
                                <div class="toggleTable">
                                    <div class="tab-content">
                                        <div class="tab-pane fade in active">
                                            <p id="totalData"></p>
                                            <table class="table table-bordered table-hover">
                                                <thead>
                                                <tr class="active">
                                                    <th>日期</th>
                                                    <th>使用金币金额</th>
                                                    <th>发送金币金额</th>
                                                    <th>操作</th>
                                                </tr>
                                                </thead>
                                                <tbody id="scoreCContent">
                                                </tbody>
                                            </table>
                                        </div>
                                        <nav class="pull-right">
                                            <div class="tcdPageCode" style="display: inline;">
                                            </div>
                                            <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                                            <br>
                                            <br>
                                            <%--<button class="btn btn-primary pull-right" style="margin-top: 5px"--%>
                                            <%--onclick="exportExcel()">导出excel--%>
                                            <%--</button>--%>
                                        </nav>
                                    </div>
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

<script>
    var scoreCriteria = {};


    var scoreCriteria = {};
    var scoreContent = document.getElementById("scoreCContent");
    var flag = true;
    var init = 0;


    $(document).ready(function () {
        var startDate = new Date();
        var endDate = new Date(new Date().getTime() - 864000000);
        var startYear = startDate.getFullYear();
        var startMonth = startDate.getMonth() + 1;
        if (startMonth < 10) {
            startMonth = "0" + startMonth;
        }
        var startDay = startDate.getDate();
        if (startDay < 10) {
            startDay = "0" + startDay;
        }
        var startDateStr = startYear + "/" + startMonth + "/" + startDay;
        var endYear = endDate.getFullYear();
        var endMonth = endDate.getMonth() + 1;
        if (endMonth < 10) {
            endMonth = "0" + endMonth;
        }
        var endDay = endDate.getDate();
        if (endDay < 10) {
            endDay = "0" + endDay;
        }
        var endDateStr = endYear + "/" + endMonth + "/" + endDay;
        $("#HB_trend-startTime").val(endDateStr);
        $("#HB_trend-endTime").val(startDateStr);
        getScoreByTypeAjax(scoreCriteria);
    })

    function getScoreByTypeAjax(scoreCriteria) {

        scoreContent.innerHTML = "";


        $.ajax({
            type: "post",
            url: "/manage/scoreC/statistics",
            async: false,
            data: JSON.stringify(scoreCriteria),
            contentType: "application/json",
            success: function (data) {
                var page = data.data;
                var content = page.scoreCStatistics;
                var totalPage = page.totalPages;
                var sendScoreC = page.sendScoreC / 100.0;
                var useScoreC = -page.useScoreC / 100.0;
                var handScoreC = page.handScoreC / 100.0;


                $("#totalElements").html(page.totalElements);
                if (totalPage == 0) {
                    totalPage = 1;
                }
                if (flag) {
                    flag = false;
                    initPage(scoreCriteria.offset, totalPage);
                }
                if (init) {
                    initPage(1, totalPage);
                }
                var totalData = document.getElementById("totalData");
                var totalDataStr = '';
                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr>';
                    contentStr += '<td>' + content[i].scoreCStatisticsDate + '</td>';
                    if (content[i].scoreCStatisticsUse != "--") {
                        contentStr += '<td>' + (-content[i].scoreCStatisticsUse) / 100.0 + '</td>';
                    } else {
                        contentStr += '<td>0</td>';
                    }

                    if (content[i].scoreCStatisticsSend != "--") {
                        contentStr += '<td>' + content[i].scoreCStatisticsSend / 100.0 + '</td>';
                    } else {
                        contentStr += '<td>0</td>';
                    }
                    contentStr +=
                        '<td><input type="hidden" class="id-hidden" value="' + content[i].scoreCStatisticsDate + '"><button class="btn btn-primary serchScoreCDetail")">查看详情</button></td>';
                    contentStr += '</tr>';

                    scoreContent.innerHTML += contentStr;
                }
                totalDataStr = '所选时段内用户持有金币￥' + handScoreC + ',累计发放金币￥' + sendScoreC + ',累计使用金币￥' + useScoreC;
                totalData.innerHTML = '';
                totalData.innerHTML += totalDataStr;

                $(".serchScoreCDetail").each(function (i) {
                    $(".serchScoreCDetail").eq(i).bind("click", function () {
                        var time = $(this).parent().find(".id-hidden").val();
                        location.href =
                            "/manage/scoreC/serchScoreCDetail?time=" + time;
                    });
                });
            }
        })
    }


    function searchScoreCByCriteria() {
        scoreCriteria.offset = 1;
        init = 1;

        scoreCriteria.startDate = $("#HB_trend-startTime").val().replace("/", "").replace("/", "");
        scoreCriteria.endDate = $("#HB_trend-endTime").val().replace("/", "").replace("/", "");
        getScoreByTypeAjax(scoreCriteria);
    }

    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                scoreCriteria.offset = p;
                init = 0;
                getScoreByTypeAjax(scoreCriteria);
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
    var start = {
        elem: '#HB_trend-startTime',
        format: 'YYYY/MM/DD',
        max: '2099-06-16', //最大日期
        istime: false,
        istoday: false,
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };
    var end = {
        elem: '#HB_trend-endTime',
        format: 'YYYY/MM/DD',
        max: '2099-06-16',
        istime: false,
        istoday: false,
        choose: function (datas) {
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(start);
    laydate(end);


</script>
</body>
</html>

