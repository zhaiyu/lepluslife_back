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
                <div class="MODInput_row">
                    <div class="Mod-6" style="margin: 10px 0">
                        <div class="ModLabel ModRadius-left">截止日期</div>
                        <div class="Mod-6" style="margin-top: -2px">
                         <%--   <input id="HB_data-startTime" class="laydate-icon layer-date ModRadius-right"
                                   placeholder="起始时间">--%>
                            <input id="HB_data-endTime" class="laydate-icon layer-date ModRadius"
                                   placeholder="截止时间">
                        </div>
                        <div class="Mod-2">
                           <button class="ModButton ModButton_ordinary ModRadius"
                                    onclick="searchTotalScoreAByDate()">筛选查询
                            </button>
                        </div>
                    </div>
                </div>
                <div class="topDataShow" id="scoreTotal">
                    <div>
                        <div><img src="${resourceUrl}/images/hb.png" alt=""></div>
                        <div>
                            <p>消费者当前持有鼓励金</p>
                            <p>${presentHoldScorea/100}</p>
                        </div>
                    </div>
                    <div>
                        <div><img src="${resourceUrl}/images/hb.png" alt=""></div>
                        <div>
                            <p>累计发放鼓励金</p>
                            <p>${issueScorea/100}</p>
                        </div>
                    </div>
                    <div>
                        <div><img src="${resourceUrl}/images/hb.png" alt=""></div>
                        <div>
                            <p>消费已使用鼓励金</p>
                            <p>${-useScorea/100}</p>
                        </div>
                    </div>
                    <div>
                        <div><img src="${resourceUrl}/images/yj.png" alt=""></div>
                        <div>
                            <p>累计佣金收入</p>
                            <p>${ljCommission/100}</p>
                        </div>
                    </div>
                    <%--  <div>
                          <div><img src="${resourceUrl}/images/jf.png" alt=""></div>
                          <div>
                              <p>分润后积分收入</p>
                              <p>${shareMoney/100}</p>
                          </div>
                      </div>--%>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab" onclick="getScoreaTrend()">红包数据趋势</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab" onclick="getScoreaDistribution()">红包发放分布</a>
                    </li>
                </ul>

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
                                        onclick="searchScoreAAccountByCriteria()">筛选查询
                                </button>
                            </div>
                        </div>
                        <div class="eCharts">
                            <div class="container" id="HB_trendechart-main" style="height:400px;"></div>
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
                                                    <th>使用红包金额(应结算金额)</th>
                                                    <th>发送红包金额</th>
                                                    <th>佣金收入</th>
                                                    <th>分润后积分客收入</th>
                                                    <th>操作</th>
                                                </tr>
                                                </thead>
                                                <tbody id="scoreAAccountContent">
                                                </tbody>
                                            </table>
                                        </div>
                                        <nav class="pull-right">
                                            <div class="tcdPageCode2" style="display: inline;">
                                            </div>
                                            <div style="display: inline;"> 共有 <span id="totalElements2"></span> 个</div>
                                            <br>
                                            <br>
                                            <button class="btn btn-primary pull-right" style="margin-top: 5px"
                                                    onclick="exportExcel()">导出excel
                                            </button>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="tab-pane fade in active" id="tab2">
                        <div class="MODInput_row">
                            <div class="Mod-6" style="margin: 10px 0">
                                <div class="ModLabel ModRadius-left">有效期</div>
                                <div class="Mod-6" style="margin-top: -2px">
                                    <input id="HB-distributed-startTime" class="laydate-icon layer-date ModRadius-right"
                                           placeholder="起始时间">
                                    <input id="HB-distributed-endTime" class="laydate-icon layer-date ModRadius"
                                           placeholder="截止时间">
                                </div>
                            </div>
                            <div class="Mod-2">
                                <button class="ModButton ModButton_ordinary ModRadius"
                                        onclick="getScoreaDistribution()">筛选查询
                                </button>
                            </div>
                        </div>
                        <div class="eCharts">
                            <div class="container" id="HB-distributedEchart-main" style="height:400px;"></div>
                        </div>
                        <div>
                            <div class="merchant_management-table">
                                <div class="toggleTable">
                                    <div class="tab-content">
                                        <div class="tab-pane fade in active">
                                            <table class="table table-bordered table-hover">
                                                <thead>
                                                <tr class="active">
                                                    <th>变更项目</th>
                                                    <th>时段内发放红包</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tbody id="scoreaDistributionContent">
                                                </tbody>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div style="margin-left:120px;">汇总: <span id="totalCount" style="color:red"></span></div>
                                        <nav class="pull-right">
                                            <div class="tcdPageCode" style="display: inline;">
                                            </div>
                                            <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
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

    //红包分布
    var distributionFlag = true;
    var distributionInit1 = 0;
    var scoreAAccountDistributionCriteria = {};

    //红包数据趋势
    var scoreAAccountCriteria = {};
    var flag = true;
    var init1 = 0;
    var scoreAAccountContent = document.getElementById("scoreAAccountContent");

    $(function () {
//tab切换
        $('#myTab li:eq(0) a').tab('show');
        scoreAAccountCriteria.offset = 1;
        getScoreAAccountByAjax(scoreAAccountCriteria);
        drawMyChart();
    });
    //红包数据趋势
    function getScoreAAccountByAjax(scoreAAccountCriteria) {
        scoreAAccountContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/scoreAAccount",
            async: false,
            data: JSON.stringify(scoreAAccountCriteria),
            contentType: "application/json",
            success: function (data) {
                var page = data.data;
                var content = page.content;
                var totalPage = page.totalPages;
                var useScoreA = page.useScoreA / 100;
                var issuedScoreA = page.issuedScoreA / 100;
                var commissionIncome = page.commissionIncome / 100;
                var jfkShare = page.jfkShare / 100;
                $("#totalElements2").html(page.totalElements);
                if (totalPage == 0) {
                    totalPage = 1;
                }
                if (flag) {
                    flag = false;
                    initPage2(scoreAAccountCriteria.offset, totalPage);
                }
                if (init1) {
                    initPage2(1, totalPage);
                }

                var totalData = document.getElementById("totalData");
                var totalDataStr = '';
                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr>';
                    contentStr +=
                        '<td><span>'
                        + new Date(content[i].changeDate).format('yyyy-MM-dd')
                        + '</span></td>';


                    contentStr += '<td>' + '￥' + content[i].useScoreA / 100 + '(' + content[i].settlementAmount / 100 + ')' + '</td>';
                    contentStr += '<td>' + '￥' + content[i].issuedScoreA / 100 + '</td>';
                    contentStr += '<td>' + '￥' + content[i].commissionIncome / 100 + '</td>';
                    contentStr += '<td>' + '￥' + content[i].jfkShare / 100 + '</td>';
                    contentStr +=
                        '<td><input type="hidden" class="id-hidden" value="' + new Date(content[i].changeDate).format('yyyy-MM-dd') + '"><button class="btn btn-primary serchScoreAAccountDetail")">查看详情</button></td>';
                    contentStr += '</tr>';
                    scoreAAccountContent.innerHTML += contentStr;
                }
                totalDataStr = '所选时段内使用红包￥' + useScoreA + ',累计发放红包￥' + issuedScoreA + ',累计佣金收入￥' + commissionIncome + ',分润后积分客累计收入￥' + jfkShare;
                totalData.innerHTML = '';
                totalData.innerHTML += totalDataStr;
                $(".serchScoreAAccountDetail").each(function (i) {
                    $(".serchScoreAAccountDetail").eq(i).bind("click", function () {
                        var time = $(this).parent().find(".id-hidden").val();
                        location.href =
                            "/manage/serchScoreAAccountDetailPage?time=" + time;
                    });
                });
            }
        });
    }
    //红包数据趋势
    function initPage2(page, totalPage) {
        $('.tcdPageCode2').unbind();
        $(".tcdPageCode2").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                scoreAAccountCriteria.offset = p;
                init1 = 0;
                getScoreAAccountByAjax(scoreAAccountCriteria);
            }
        });
    }
    function exportExcel() {


        if (scoreAAccountCriteria.startDate == null) {
            scoreAAccountCriteria.startDate = null;
        }
        if (scoreAAccountCriteria.endDate == null) {
            scoreAAccountCriteria.endDate = null;
        }
        if (scoreAAccountCriteria.useScoreA == null) {
            scoreAAccountCriteria.useScoreA = null;
        }
        if (scoreAAccountCriteria.IssuedScoreA == null) {
            scoreAAccountCriteria.IssuedScoreA = null;
        }
        if (scoreAAccountCriteria.commissionIncome == null) {
            scoreAAccountCriteria.commissionIncome = null;
        }
        if (scoreAAccountCriteria.commissionIncome == null) {
            scoreAAccountCriteria.commissionIncome = null;
        }
        post("/manage/scoreAAccount/export", scoreAAccountCriteria);
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


    var BingChart = echarts.init(document.getElementById('HB-distributedEchart-main'));
    BingChart.setOption({
        title: {
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: []
        },
        series: [
            {
                name: [],
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    });
    function getScoreaDistribution() {
        pieChart();
        getScoreaDistributionByCriteria();
    }
    function getScoreaTrend() {
        drawMyChart();
        getScoreaDistributionByAjax(scoreAAccountDistributionCriteria);
    }
    function getScoreaDistributionByCriteria() {
        distributionInit1 = 1;
        scoreAAccountDistributionCriteria.offset = 1;
        scoreAAccountDistributionCriteria.startDate = $("#HB-distributed-startTime").val();
        scoreAAccountDistributionCriteria.endDate = $("#HB-distributed-endTime").val();
        if (scoreAAccountDistributionCriteria.startDate == "") {
            scoreAAccountDistributionCriteria.startDate = null
        }
        if (scoreAAccountDistributionCriteria.endDate == "") {
            scoreAAccountDistributionCriteria.endDate = null
        }
        getScoreaDistributionByAjax(scoreAAccountDistributionCriteria);
    }
    function getScoreaDistributionByAjax(scoreAAccountDistributionCriteria) {
        var scoreaDistributionContent = document.getElementById("scoreaDistributionContent");
        scoreaDistributionContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/scoreAAccount/distributionList",
            async: false,
            data: JSON.stringify(scoreAAccountDistributionCriteria),
            contentType: "application/json",
            success: function (data) {
                var totalPage = data.data.totalPages;
                $("#totalElements").html(data.data.totalElements);
                if (totalPage == 0) {
                    totalPage = 1;
                }
                if (distributionFlag) {
                    distributionFlag = false;
                    initPage(scoreAAccountDistributionCriteria.offset, totalPage);
                }
                if (distributionInit1) {
                    initPage(1, totalPage);
                }
                var content = data.data.content;
                var countTotalMoney = data.data.totalMoney/100.0;
                var totalCount = document.getElementById("totalCount");    // 汇总
                totalCount.innerHTML = countTotalMoney;
                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr>';
                    if (content[i][0] == 0) {
                        contentStr += '<td>' + '关注送红包' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 1) {
                        contentStr += '<td>' + '线上返还' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 2) {
                        contentStr += '<td>' + '线上消费' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 3) {
                        contentStr += '<td>' + '线下消费' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 4) {
                        contentStr += '<td>' + '线下返还' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 5) {
                        contentStr += '<td>' + '活动返还' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 6) {
                        contentStr += '<td>' + '运动' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 7) {
                        contentStr += '<td>' + '摇一摇' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 8) {
                        contentStr += '<td>' + 'APP分享' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 9) {
                        contentStr += '<td>' + '线下支付完成页注册会员' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 10) {
                        contentStr += '<td>' + '合伙人发福利' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 11) {
                        contentStr += '<td>' + '临时活动' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 13) {
                        contentStr += '<td>' + '充话费发送包' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    if (content[i][0] == 14001) {
                        contentStr += '<td>' + '退款' + '</td>';
                        contentStr += '<td>' + content[i][1] / 100 + '</td>';
                    }
                    contentStr += '</tr>';
                    scoreaDistributionContent.innerHTML += contentStr;
                }
            }
        });
    }

    //红包发放分布
    function initPage(page, totalPage) {
        scoreAAccountDistributionCriteria.startDate = $("#HB-distributed-startTime").val();
        scoreAAccountDistributionCriteria.endDate = $("#HB-distributed-endTime").val();
        if (scoreAAccountDistributionCriteria.startDate == "") {
            scoreAAccountDistributionCriteria.startDate = null
        }
        if (scoreAAccountDistributionCriteria.endDate == "") {
            scoreAAccountDistributionCriteria.endDate = null
        }
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                scoreAAccountDistributionCriteria.offset = p;
                distributionInit1 = 0;
                getScoreaDistributionByAjax(scoreAAccountDistributionCriteria);
            }
        });
    }
    //日期范围限制
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
    var start2 = {
        elem: '#HB-distributed-startTime',
        format: 'YYYY/MM/DD',
        max: '2099-06-16', //最大日期
        istime: false,
        istoday: false,
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };
    var end2 = {
        elem: '#HB-distributed-endTime',
        format: 'YYYY/MM/DD',
        max: '2099-06-16',
        istime: false,
        istoday: false,
        choose: function (datas) {
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
  /*  var start3 = {
        elem: '#HB_data-startTime',
        format: 'YYYY/MM/DD',
        max: '2099-06-16', //最大日期
        istime: false,
        istoday: false,
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };*/
    var end3 = {
        elem: '#HB_data-endTime',
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
    laydate(start2);
    laydate(end2);
//    laydate(start3);
    laydate(end3);
    // 基于准备好的dom，初始化echarts实例，折线图
    var myChart = echarts.init(document.getElementById('HB_trendechart-main'));
    myChart.setOption({
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: []
        },
        xAxis: {
            boundaryGap: false,
            data: []
        },
        yAxis: {
            name: '金额',
            type: 'value'
        },
        series: [],
        visualMap: {
            inRange: {}
        }
    });

    function drawMyChart() {
        scoreAAccountCriteria.startDate = $("#HB_trend-startTime").val();
        scoreAAccountCriteria.endDate = $("#HB_trend-endTime").val();
        if (scoreAAccountCriteria.startDate == "") {
            scoreAAccountCriteria.startDate = null
        }
        if (scoreAAccountCriteria.endDate == "") {
            scoreAAccountCriteria.endDate = null
        }
        $.ajax({
            type: "post",
            url: "/manage/scoreAAccount/geMyChartByAjax",
            async: false,
            data: JSON.stringify(scoreAAccountCriteria),
            contentType: "application/json",
            success: function (data) {
                myChart.setOption({
                    legend: {
                        data: ['使用红包金额', '发送红包金额', '佣金收入', '分润后积分客收入']
                    },
                    xAxis: {
                        boundaryGap: false,
                        data: (function () {
                            var res = [];
                            var len = data.data.dateStrList.length;
                            while (len--) {
                                res.push({
                                    value: data.data.dateStrList[len]
                                });
                            }
                            return res;
                        })()
                    },
                    series: [
                        {
                            type: 'line',
                            name: '使用红包金额',
                            data: (function () {
                                var res = [];
                                var len = data.data.useScoreAList.length;
                                while (len--) {
                                    res.push({
                                        value: data.data.useScoreAList[len]
                                    });
                                }
                                return res;
                            })()

                        },
                        {
                            type: 'line',
                            name: '发送红包金额',
                            data: (function () {
                                var res = [];
                                var len = data.data.issuedScoreAList.length;
                                while (len--) {
                                    res.push({
                                        value: data.data.issuedScoreAList[len]
                                    });
                                }
                                return res;
                            })()

                        },
                        {
                            type: 'line',
                            name: '佣金收入',
                            data: (function () {
                                var res = [];
                                var len = data.data.commissionIncomeList.length;
                                while (len--) {
                                    res.push({
                                        value: data.data.commissionIncomeList[len]
                                    });
                                }
                                return res;
                            })()

                        },
                        {
                            type: 'line',
                            name: '分润后积分客收入',
                            data: (function () {
                                var res = [];
                                var len = data.data.jfkShareList.length;
                                while (len--) {
                                    res.push({
                                        value: data.data.jfkShareList[len]
                                    });
                                }
                                return res;
                            })()

                        }
                    ]
                });
            }
        });
    }


    function pieChart() {
        scoreAAccountDistributionCriteria.startDate = $("#HB-distributed-startTime").val();
        scoreAAccountDistributionCriteria.endDate = $("#HB-distributed-endTime").val();
        if (scoreAAccountDistributionCriteria.startDate == "") {
            scoreAAccountDistributionCriteria.startDate = null
        }
        if (scoreAAccountDistributionCriteria.endDate == "") {
            scoreAAccountDistributionCriteria.endDate = null
        }
        $.ajax({
            type: "post",
            url: "/manage/scoreAAccount/getpieChartByAjax",
            async: false,
            data: JSON.stringify(scoreAAccountDistributionCriteria),
            contentType: "application/json",
            success: function (data) {
                BingChart.setOption({

                    legend: {
                        data: (function () {
                            var res = [];
                            var len = data.data.nameList.length;
                            while (len--) {
                                res.push(
                                    data.data.nameList[len]
                                );
                            }
                            return res;
                        })()
                    },
                    series: [
                        {
                            name: '红包分布:',
                            data: (function () {
                                var res = [];
                                var len = data.data.valueList.length;
                                while (len--) {
                                    res.push({
                                        name: data.data.nameList[len],
                                        value: data.data.valueList[len] / 100
                                    });
                                }
                                return res;
                            })()

                        }
                    ]
                });
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
    function searchScoreAAccountByCriteria() {
        scoreAAccountCriteria.offset = 1;
        init1 = 1;

        scoreAAccountCriteria.startDate = $("#HB_trend-startTime").val();
        scoreAAccountCriteria.endDate = $("#HB_trend-endTime").val();

        getScoreAAccountByAjax(scoreAAccountCriteria);
        drawMyChart();
    }
    //  2017/6/7  根据日期查询红包数据
    function searchTotalScoreAByDate() {
        var thisEndDate = $("#HB_data-endTime").val();
        if(thisEndDate==''||thisEndDate==null) {
            alert("请选择截止日期");
            return;
        }
        var criteria = {};
        criteria.endDate = thisEndDate;
        $.ajax({
            type: "post",
            url: "/manage/scoreAAccount/dailyTotal",
            async: false,
            data: JSON.stringify(criteria),
            contentType: "application/json",
            success: function (response) {
                if(response.status==200) {
                    var data = response.data;
                    var scoreTotal = document.getElementById("scoreTotal");
                    scoreTotal.innerHTML="";
                    scoreTotal.innerHTML+='<div><div><img src="${resourceUrl}/images/hb.png" alt=""></div><div><p>消费者持有鼓励金</p>'+
                        '<p>'+(data.totalScoreA/100.0)+'</p></div></div>'+
                        '<div> <div><img src="${resourceUrl}/images/hb.png" alt=""></div> <div><p>累计发放鼓励金</p>'+
                        '<p>'+(data.provideScoreA/100.0)+'</p> </div></div>'+
                        '<div><div><img src="${resourceUrl}/images/hb.png" alt=""></div><div>'+
                        '<p>消费已使用鼓励金</p><p>'+(-data.usedScoreA/100.0)+'</p>'+
                        '</div></div><div><div><img src="${resourceUrl}/images/yj.png" alt=""></div>'+
                        '<div><p>累计佣金收入</p><p>'+(data.commissionIncome/100.0)+'</p></div></div>';
                }else {
                    alert(response.msg)
                }
            }
        });
    }
</script>
</body>
</html>

