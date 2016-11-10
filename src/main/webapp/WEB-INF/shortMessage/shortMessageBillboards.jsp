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
    <link rel="stylesheet" href="${resourceUrl}/css/sms.css">
    <!--[if lt IE 9]>
    <script src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script src="${resourceUrl}/js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script src="${resourceUrl}/js/echarts.min.js"></script>
</head>
<style>
    .w-shai > div {
        float: right;
        border: 1px solid #e1e1e1;
        border-right: 0;
        padding: 1% 2%;
        cursor: pointer;
    }

    .w-shai > div:first-child {
        border-right: 1px solid #e1e1e1;
        margin-right: 5%;
        -webkit-border-radius: 0 5px 5px 0;
        -moz-border-radius: 0 5px 5px 0;
        border-radius: 0 5px 5px 0;
    }

    .w-shai > div:last-child {
        -webkit-border-radius: 5px 0 0 5px;
        -moz-border-radius: 5px 0 0 5px;
        border-radius: 5px 0 0 5px;
    }

    .w-shaiActive {
        background-color: #1798fe !important;
        color: #FFFFFF !important;
        border: 1px solid #1798fe !important;
    }

    .w-shai:after {
        content: '\20';
        display: block;
        clear: both;
    }
</style>
<body>
<%--<iframe src="commonHtml/top.html" frameborder="0" id="topIframe"></iframe>--%>
<%@include file="../common/top.jsp" %>
<div id="content" style="background-color: #FFF;">
    <%--<iframe src="commonHtml/left.html" frameborder="0" id="leftIframe"></iframe>--%>
    <%@include file="../common/left.jsp" %>
    <div class="m-right">
        <div class="main">
            <div class="container-fluid">
                <div class="w-top">
                    <div class="title">
                        <div>
                            <div class="blank"></div>
                            短信看板
                        </div>
                        <button class="headButton type1">自动发送设置</button>
                    </div>
                    <div class="topDataShow">
                        <div>
                            <div><img src="${resourceUrl}/images/cdyh.png" alt=""></div>
                            <div>
                                <p>累计触达用户（人）</p>

                                <p id="totalcustomer"></p>
                            </div>
                        </div>
                        <div>
                            <div><img src="${resourceUrl}/images/cgff.png" alt=""></div>
                            <div>
                                <p>累计成功发放（人）</p>

                                <p id="totalsendcount"></p>
                            </div>
                        </div>
                        <div>
                            <div><img src="${resourceUrl}/images/ljtd.png" alt=""></div>
                            <div>
                                <p>累计退订数（人）</p>

                                <p id="totalTD"></p>
                            </div>
                        </div>
                        <div>
                            <div><img src="${resourceUrl}/images/tdl.png" alt=""></div>
                            <div>
                                <p>退订率</p>

                                <p id="TDL"></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="w-middle">
                    <div class="title">
                        <div>
                            <div class="blank"></div>
                            短信发放趋势
                        </div>
                        <div>
                            <div id="date-end" class="form-control">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                <span id="searchDateRange"></span>
                                <b class="caret"></b>
                            </div>
                            <div>
                                <button class="btn btn-primary" style="margin-top: 24px"
                                        onclick="searchShortMessageCriteria()">查询
                                </button>
                            </div>
                        </div>

                    </div>
                    <div id="myTabContent" class="tab-content" style="margin-top: 10px">
                        <div class="eCharts">
                            <div class="w-shai">
                                <div>退订用户</div>
                                <div>日均接收</div>
                                <div>触达用户</div>
                                <div class="w-shaiActive">发放条数</div>
                            </div>
                            <div class="container" id="echart-main" style="height:400px;"></div>
                            <p id="totalsource"></p>
                        </div>
                        <div class="container">
                            <div class="tab-pane fade in active" id="tab1">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr class="active">
                                        <th>短信场景</th>
                                        <th>时间段内发放条数</th>
                                        <th>时间段内触达用户</th>
                                    </tr>
                                    </thead>

                                    <tbody id="shortMessageDataContent1">
                                    <tr>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>3</td>
                                    </tr>
                                    <td></td>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--自动发送设置提示框-->
<div class="modal" id="type1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span>自动发送设置</span>
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="setSceneOnOff">

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="productSpec-confim" onclick="automaticSend()">确认
                </button>
            </div>
        </div>
    </div>
</div>
<%--<iframe src="commonHtml/bottom.html" frameborder="0" id="bottomIframe"></iframe>--%>
<%@include file="../common/bottom.jsp" %>

<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script>
    $(".type1").click(function () {
        $("#setSceneOnOff").html("");
        $("#type1").modal("toggle");
        $.ajax({
                   type: "post",
                   url: "/manage/shortMessage/shortMessageOnOffPage",
                   async: false,
                   contentType: "application/json",
                   success: function (data) {

                       var content = data.data;
                       for (var i = 0; i < content.length; i++) {
                           if (content[i].name == "线下订单后发送") {
                               if (content[i].recloser == true) {
                                   $("#setSceneOnOff").append('  <div class="form-group">' +
                                                              '<label class="col-sm-3 control-label">'
                                                              + content[i].name + '</label>' +

                                                              '<div class="col-sm-7">' +
                                                              '<input name="' + content[i].sceneSid
                                                              + '" type="radio" value="1" checked="checked" '
                                                              + "   " + ' ' +
                                                              'style="width: 20%"><span>开启</span>' +
                                                              '<input name="' + content[i].sceneSid
                                                              + '" value="0" type="radio"' +
                                                              'style="width: 20%"><span>关闭</span>' +
                                                              '<div class="col-sm-9">' +
                                                              '<div class="row">' +
                                                              '<div class="col-sm-3 toggleText">使用超过</div>'
                                                              +
                                                              '<input id="offLineAddedcondition1" class="form-control toggleInput" type="text" value="'
                                                              + content[i].addedcondition1 / 100
                                                              + '"><span' +
                                                              ' class="toggleText">元</span>' +
                                                              '</div>' +
                                                              '<div>或者</div>' +
                                                              '<div class="row">' +
                                                              '<div class="col-sm-3 toggleText">得红包超过</div>'
                                                              +
                                                              '<input id="offLineAddedcondition2" class="form-control toggleInput" type="text" value="'
                                                              + content[i].addedcondition2 / 100
                                                              + '"><span' +
                                                              'class="toggleText">元发送</span>' +
                                                              '           </div>' +
                                                              '          </div>' +
                                                              '       </div>');
                               } else {
                                   $("#setSceneOnOff").append('  <div class="form-group">' +
                                                              '<label class="col-sm-3 control-label">'
                                                              + content[i].name + '</label>' +

                                                              '<div class="col-sm-7">' +
                                                              '<input name="' + content[i].sceneSid
                                                              + '" value="1" type="radio" ' +
                                                              'style="width: 20%"><span>开启</span>' +
                                                              '<input name="' + content[i].name
                                                              + '" type="radio" value="0" checked="checked" '
                                                              + "   " + '' +
                                                              'style="width: 20%"><span>sceneSid</span>'
                                                              +
                                                              '<div class="col-sm-9">' +
                                                              '<div class="row">' +
                                                              '<div class="col-sm-3 toggleText">使用超过</div>'
                                                              +
                                                              '<input id="offLineAddedcondition1" class="form-control toggleInput"  type="text" value="'
                                                              + content[i].addedcondition1 / 100
                                                              + '"><span' +
                                                              ' class="toggleText">元</span>' +
                                                              '</div>' +
                                                              '<div>或者</div>' +
                                                              '<div class="row">' +
                                                              '<div class="col-sm-3 toggleText">得红包超过</div>'
                                                              +
                                                              '<input id="offLineAddedcondition2" class="form-control toggleInput" type="text" value="'
                                                              + content[i].addedcondition2 / 100
                                                              + '"><span' +
                                                              'class="toggleText" >元发送</span>' +
                                                              '           </div>' +
                                                              '          </div>' +
                                                              '       </div>');
                               }

                           } else if (content[i].name == "手动发送") {
                               $("#setSceneOnOff").append("");
                           } else {
                               if (content[i].recloser == true) {
                                   $("#setSceneOnOff").append('  <div class="form-group"><label class="col-sm-3 control-label">'
                                                              + content[i].name + '</label>' +
                                                              ' <div class="col-sm-7">' +
                                                              '  <input name="'
                                                              + content[i].sceneSid
                                                              + '" value="1" type="radio" checked="checked" '
                                                              + "   " + ' ' +
                                                              'style="width: 20%"><span>开启</span>' +
                                                              '<input name="' + content[i].sceneSid
                                                              + '" value="0" type="radio"' +
                                                              'style="width: 20%"><span>关闭</span>' +
                                                              '</div></div>'
                                   );
                               } else {
                                   $("#setSceneOnOff").append('  <div class="form-group"><label class="col-sm-3 control-label">'
                                                              + content[i].name + '</label>' +
                                                              ' <div class="col-sm-7">' +
                                                              '  <input name="'
                                                              + content[i].sceneSid
                                                              + '" value="1" type="radio" ' +
                                                              'style="width: 20%"><span>开启</span>' +
                                                              '<input name="' + content[i].sceneSid
                                                              + '"  value="0" type="radio" checked="checked" '
                                                              + "   " + ' ' +
                                                              'style="width: 20%"><span>关闭</span>' +
                                                              '</div></div>'
                                   );
                               }
                           }
                       }
                   }
               })
    });
</script>
<script>
    var ShortMessageDataCriteria = {};
    var shortMessageDataContent1 = document.getElementById("shortMessageDataContent1");
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart-main'));
    //时间选择器
    $(document).ready(function () {
        $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss') + ' - '
                                 + moment().format('YYYY/MM/DD HH:mm:ss'));
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
        ShortMessageDataCriteria.startDate = "nullValue";
        ShortMessageDataCriteria.endDate = "nullValue";
        //折线图
        getShortMessageDataByAjax(ShortMessageDataCriteria);
        //表格
        getShortMessagedataFormByAjax(ShortMessageDataCriteria)

    })
    //画折线图的ajax
    function getShortMessageDataByAjax(ShortMessageDataCriteria) {
        $.ajax({
                   type: "post",
                   url: "/manage/getShortMessagedataByAjax",
                   async: false,
                   data: JSON.stringify(ShortMessageDataCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       //日期字符串
                       var x = data.data.dateStrList;
                       var xStr = "";
                       for (i = 0; i < x.length; i++) {
                           xStr += x[x.length - i - 1] + ',';
                       }
                       xStr = xStr.substr(0, xStr.length - 1);
                       var dateArry = new Array()
                       dateArry = xStr.split(",");

                       //发送条数
                       var sendCountAndDateList = data.data.sendCountAndDateList;
                       var sendCountAndDateListStr = "";
                       for (i = 0; i < sendCountAndDateList.length; i++) {
                           sendCountAndDateListStr +=
                           sendCountAndDateList[sendCountAndDateList.length - i - 1] + ',';
                       }
                       sendCountAndDateListStr =
                       sendCountAndDateListStr.substr(0, sendCountAndDateListStr.length - 1);
                       var SendSuccessedCountArry = new Array()
                       SendSuccessedCountArry = sendCountAndDateListStr.split(",");

                       //触达客户
                       var SendSuccessedCountList = data.data.SendSuccessedCountList;
                       var SendSuccessedCountListStr = "";
                       for (i = 0; i < SendSuccessedCountList.length; i++) {
                           SendSuccessedCountListStr +=
                           SendSuccessedCountList[SendSuccessedCountList.length - i - 1] + ',';
                       }
                       SendSuccessedCountListStr =
                       SendSuccessedCountListStr.substr(0, SendSuccessedCountListStr.length - 1);
                       var sendCountAndDateArry = new Array()
                       sendCountAndDateArry = SendSuccessedCountListStr.split(",");

                       //日均接收
                       var DayReceiveCountList = data.data.DayReceiveCountList;

                       var DayReceiveCountListStr = "";
                       for (i = 0; i < DayReceiveCountList.length; i++) {
                           DayReceiveCountListStr +=
                           DayReceiveCountList[DayReceiveCountList.length - i - 1] + ',';
                       }
                       DayReceiveCountListStr =
                       DayReceiveCountListStr.substr(0, DayReceiveCountListStr.length - 1);
                       var DayReceiveCountListArry = new Array()
                       DayReceiveCountListArry = DayReceiveCountListStr.split(",");
                       //退订数
                       var findUserTDList = data.data.findUserTDList;
                       var findUserTDListStr = "";
                       for (i = 0; i < findUserTDList.length; i++) {
                           findUserTDListStr +=
                           findUserTDList[findUserTDList.length - i - 1] + ',';
                       }
                       findUserTDListStr =
                       findUserTDListStr.substr(0, findUserTDListStr.length - 1);
                       var findUserTDListArry = new Array()
                       findUserTDListArry = findUserTDListStr.split(",");
                       // 显示标题，图例和空的坐标轴
                       myChart.setOption({
                                             tooltip: {
                                                 trigger: 'axis'
                                             },
                                             legend: {
                                                 data: ['发送条数', '触达用户', '日均接收', '退订用户']
                                             },
                                             xAxis: {
                                                 boundaryGap: false,
                                                 data: dateArry
                                             },
                                             yAxis: {
                                                 name: '数量',
                                                 type: 'value'
                                             },
                                             series: [
                                                 {
                                                     name: '发送条数',
                                                     type: 'line',
                                                     data: SendSuccessedCountArry
                                                 },
                                                 {
                                                     name: '触达用户',
                                                     type: 'line',
                                                     data: sendCountAndDateArry
                                                 },
                                                 {
                                                     name: '日均接收',
                                                     type: 'line',
                                                     data: DayReceiveCountListArry
                                                 },
                                                 {
                                                     name: '退订用户',
                                                     type: 'line',
                                                     data: findUserTDListArry
                                                 },
                                             ],
                                             visualMap: {
                                                 inRange: {}
                                             }
                                         });
                   }
               })
    }

    //画表格的ajax
    function getShortMessagedataFormByAjax(ShortMessageDataCriteria) {
        $.ajax({
                   type: "post",
                   url: "/manage/getShortMessagedataFormByAjax",
                   async: false,
                   data: JSON.stringify(ShortMessageDataCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       //表格里的数据
                       shortMessageDataContent1.innerHTML = "";
                       var dataslist = data.data;
                       var str = ""
                       for (i = 0; i < dataslist.length; i++) {
                           str +=
                           "<tr><td>" + dataslist[i].sceneName + "</td><td>" + dataslist[i].sint
                           + "条数成功" + dataslist[i].dint + "条数失败" + "</td><td>" + "触达"
                           + dataslist[i].tint + "个用户" + "</td></tr>";
                       }
                       shortMessageDataContent1.innerHTML += str;
                       //alert(str);
                   }
               });
    }
    //取出日期变量
    function searchShortMessageCriteria() {
        var dateStr = $('#date-end span').text().split("-");
        var startDate = dateStr[0];
        var endDate = dateStr[1];
        ShortMessageDataCriteria.startDate = startDate;
        ShortMessageDataCriteria.endDate = endDate;
        //调用getShortMessageDataByAjax 折线图
        getShortMessageDataByAjax(ShortMessageDataCriteria);
        getShortMessagedataFormByAjax(ShortMessageDataCriteria);
    }

    function automaticSend() {

        $.ajax({
                   type: "post",
                   url: "/manage/shortMessage/shortMessageOnOffPage",
                   async: false,
                   contentType: "application/json",
                   success: function (data) {
                       var content = data.data;
                       for (var i = 0; i < content.length; i++) {

                           var recloser = $("input[name=" + content[i].sceneSid
                                            + "]:checked").val();
                           if (recloser != null) {
                               var jsonStr = {
                                   "sceneSid": content[i].sceneSid,
                                   "recloser": recloser
                               };
                               setAutomaticSendShortMesssage(jsonStr);
                           }
                       }
                       var offLineAddedcondition1 = $("#offLineAddedcondition1").val();
                       var offLineAddedcondition2 = $("#offLineAddedcondition2").val();
                       var jsonStr = {
                           "offLineAddedcondition1": offLineAddedcondition1,
                           "offLineAddedcondition2": offLineAddedcondition2
                       };
                       setAutomaticSendShortMesssage(jsonStr);
                   }
               });

    }
    function setAutomaticSendShortMesssage(jsonStr) {
        $.ajax({
                   type: "post",
                   url: "/manage/shortMessage/setAutomaticSendShortMesssage",
                   async: false,
                   contentType: "application/json",
                   data: JSON.stringify(jsonStr),
                   success: function () {
                   }
               });
    }
</script>
</body>
</html>
