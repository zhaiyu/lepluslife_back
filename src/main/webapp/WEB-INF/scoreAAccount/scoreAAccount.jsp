<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/5/6
  Time: 上午10:02
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
                    <div class="col-xs-5 col-sm-2 col-sm-offset-1">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">￥${presentHoldScorea/100}</h4>
                                <p class="text-center h4">消费者当前持有红包</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-5 col-sm-2">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">￥${issueScorea/100}</h4>
                                <p class="text-center h4">累计发放红包</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-5 col-sm-2">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">￥${-useScorea/100}</h4>
                                <p class="text-center h4"> 消费者已使用红包</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-5 col-sm-2">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">￥${ljCommission/100}</h4>
                                <p class="text-center h4"> 累计佣金收入</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-5 col-sm-2">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">￥${shareMoney/100}</h4>
                                <p class="text-center h4"> 分润后积分客收入</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row" style="margin-top: 10px">
                    <div class="form-group col-md-6">
                        <label for="date-end">结算时间</label>
                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchScoreAAccountDetailByCriteria()">查询
                        </button>
                    </div>
                </div>

                <div id="myTabContent" class="tab-content container-fluid">
                    <div class="tab-pane fade in active" id="tab1">
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
                    <div class="tcdPageCode" style="display: inline;">
                    </div>
                    <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                    <button class="btn btn-primary pull-right" style="margin-top: 5px"
                            onclick="exportExcel()">导出excel
                    </button>

                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!--删除提示框-->
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <h4>操作确定</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="paid-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var scoreAAccountCriteria = {};
    var flag = true;
    var init1 = 0;
    var scoreAAccountContent = document.getElementById("scoreAAccountContent");
    $(function () {
        // 时间选择器
        $(document).ready(function () {
//            $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss')
//                                    + ' - ' +
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
        scoreAAccountCriteria.offset = 1;
        getScoreAAccountByAjax(scoreAAccountCriteria);
    });
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
                       var useScoreA=page.useScoreA/100;
                       var issuedScoreA=page.issuedScoreA/100;
                       var commissionIncome=page.commissionIncome/100;
                       var jfkShare=page.jfkShare/100;
                       $("#totalElements").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag) {
                           flag = false;
                           initPage(scoreAAccountCriteria.offset, totalPage);
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       var scoreAAccountContent = document.getElementById("scoreAAccountContent");
                       var totalData=document.getElementById("totalData");
                       var totalDataStr = '';
                       for ( i = 0; i < content.length; i++) {
                           var contentStr = '';
                           contentStr +=
                           '<td><span>'
                           + new Date(content[i].changeDate).format('yyyy-MM-dd')
                           + '</span></td>';


                           contentStr += '<td>' + '￥'+content[i].useScoreA / 100 +'('+ content[i].settlementAmount / 100+')'+ '</td>';
                           contentStr += '<td>' +  '￥'+content[i].issuedScoreA / 100 + '</td>';
                           contentStr += '<td>' +  '￥'+content[i].commissionIncome / 100 + '</td>';
                           contentStr += '<td>' +  '￥'+content[i].jfkShare / 100 + '</td>';
                           contentStr +=
                           '<td><input type="hidden" class="id-hidden" value="' + new Date(content[i].changeDate).format('yyyy-MM-dd')
                           + '"><button class="btn btn-primary serchScoreAAccountDetail")">查看详情</button>';
                           scoreAAccountContent.innerHTML += contentStr;
                       }
                       totalDataStr='所选时段内使用红包￥'+useScoreA+',累计发放红包￥'+issuedScoreA+',累计佣金收入￥'+commissionIncome+',分润后积分客累计收入￥'+jfkShare;
                       totalData.innerHTML='';
                       totalData.innerHTML+= totalDataStr;
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
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             scoreAAccountCriteria.offset = p;
                                             init1 = 0;
                                             getScoreAAccountByAjax(scoreAAccountCriteria);
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



    function searchScoreAAccountDetailByCriteria() {
        scoreAAccountCriteria.offset = 1;
        init1 = 1;

        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            scoreAAccountCriteria.startDate = startDate;
            scoreAAccountCriteria.endDate = endDate;
        }
        getScoreAAccountByAjax(scoreAAccountCriteria);
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
            // alert(opt.name)
            temp.appendChild(opt);
        }
        document.body.appendChild(temp);
        temp.submit();
        return temp;
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

</script>
</body>
</html>