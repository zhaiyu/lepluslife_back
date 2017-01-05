<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 16-11-16
  Time: 上午10:24
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
    <style>
        thead th,tbody td{text-align: center;}
        table tr td img{width: 80px;height:80px;}
        .table>thead>tr>td, .table>thead>tr>th{line-height: 40px;}
        .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th{line-height: 60px;}


        .modal-body .main{
            width: 345px;
            height: 480px;
            margin: 0 auto;
            background: url("${resourceUrl}/images/lefuma.png") no-repeat;
            background-size: 100% 100%;
            position: relative;
        }
        .modal-body #myShowImage{
            width: 345px;
            height: 480px;
            position: absolute;
            top: 20px;
            left: 0;
            right: 0;
            margin: auto;
            display: none;
        }
        .modal-body .main .rvCode{
            width: 230px;
            height: 230px;
            position: absolute;
            top: 120px;
            left: 0;
            right: 0;
            margin:auto;
        }
        .modal-body .main .shopName{
            text-align: center;
            font-size: 24px;
            color: #fff;
            position: absolute;
            top: 365px;
            left: 0;
            right: 0;
            margin: auto;
            font-family: Arial;
        }
    </style>
    <!--<script src="js/html2canvas.js" type="text/javascript" charset="utf-8"></script>-->
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
        <div class="main" >
            <div class="container-fluid" style="padding-top: 20px">

                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-2">
                        <label for="exchangeCode">充值兑换码</label>
                        <input type="text" id="exchangeCode" class="form-control" placeholder="" />
                    </div>
                    <div class="form-group col-md-3">
                        <label for="date-end">兑换时间</label>
                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="ljUserSid">会员id</label>
                        <input type="text" id="ljUserSid" class="form-control" placeholder="" />
                    </div>
                    <div class="form-group col-md-2">
                        <label for="userPhone">会员手机号</label>
                        <input type="text" id="userPhone" class="form-control" placeholder="" />
                    </div>
                 </div>

                 <div class="row" style="margin-top: 10px">
                     <div class="form-group col-md-2">
                         <label for="rechargeStatus">状态</label>
                         <select class="form-control" id="rechargeStatus">
                             <option value="">全部状态</option>
                             <option value="1">充值中</option>
                             <option value="2">已充值</option>
                             <option value="3">充值失败</option>
                         </select>
                     </div>
                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px" onclick="searchByCriteria()">筛选</button>
                    </div>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li class="w i"><a href="#ing" data-toggle="tab">充值卡记录</a></li>
                </ul>

                <div id="myTabContent" class="tab-content" style="margin: 10px 0 0 0">
                    <div class="tab-pane fade in active" id="ing">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>充值兑换码</th>
                                <th>兑换时间</th>
                                <th>处理时间</th>
                                <th>会员信息</th></th>
                                <th>微信信息</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="tablePage">

                            </tbody>
                        </table>
                    </div>
                </div>

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

<!--设置充值成功提示框-->
<div class="modal" id="successWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">设为已充值</h4>
            </div>
            <div class="modal-body">
                <h4>确认要将该条记录设为,已充值吗？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="editSuccess-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--设置充值失败提示框-->
<div class="modal" id="failWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">设为失败</h4>
            </div>
            <div class="modal-body">
                <h4>确认要将该条记录设为,充值失败吗？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="editFail-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    $(function () {
//        tab切换
        $('#myTab li:eq(0) a').tab('show');
    });
</script>
<script>
    var rechargeCardCriteria = {};
    rechargeCardCriteria.offset = 1;
    var tableContent = document.getElementById("tablePage");
    //    时间选择器
    $(document).ready(function (){
//      $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss') + ' - ' + moment().format('YYYY/MM/DD HH:mm:ss'));    设置默认查询时间为当天
        $('#date-end').daterangepicker({
            maxDate : moment(), //最大时间
            dateLimit : {
                days : 30
            }, //起止时间的最大间隔
            showDropdowns : true,
            showWeekNumbers : false, //是否显示第几周
            timePicker : true, //是否显示小时和分钟
            timePickerIncrement : 60, //时间的增量，单位为分钟
            timePicker12Hour : false, //是否使用12小时制来显示时间
            ranges : {
                '最近1小时': [moment().subtract('hours',1), moment()],
                '今日': [moment().startOf('day'), moment()],
                '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                '最近7日': [moment().subtract('days', 6), moment()],
                '最近30日': [moment().subtract('days', 29), moment()]
            },
            opens : 'right', //日期选择框的弹出位置
            buttonClasses : [ 'btn btn-default' ],
            applyClass : 'btn-small btn-primary blue',
            cancelClass : 'btn-small',
            format : 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
            separator : ' to ',
            locale : {
                applyLabel : '确定',
                cancelLabel : '取消',
                fromLabel : '起始时间',
                toLabel : '结束时间',
                customRangeLabel : '自定义',
                daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
                monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],
                firstDay : 1
            }
        }, function(start, end, label) {//格式化日期显示框
            $('#date-end span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - ' + end.format('YYYY/MM/DD HH:mm:ss'));
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
    function getRechargeCardByAjax(rechargeCardCriteria) {
        tableContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/rechargeCard/find_page",
            async: false,
            data: JSON.stringify(rechargeCardCriteria),
            contentType: "application/json",
            success: function (data) {
                    var page = data.data;
                    var content = page.content;
                    var totalPage = page.totalPages;
                    $("#totalElements").html(page.totalElements);
                    if (totalPage == 0) {
                        totalPage = 1;
                    }
                    initPage(rechargeCardCriteria.offset, totalPage);
                    for (var i = 0; i < content.length; i++) {
                        console.log(JSON.stringify(content[i]));
                        var contentStr = '<tr><td>' + content[i].exchangeCode + '</td>';

                        if(content[i].createTime!=null) {
                            contentStr += '<td>' + new Date(content[i].createTime).format('yyyy-MM-dd HH:mm') + '</td>';
                        }else{
                            contentStr += '<td></td>';
                        }

                        if(content[i].completeTime!=null) {
                            contentStr += '<td>' + new Date(content[i].completeTime).format('yyyy-MM-dd HH:mm') + '</td>';
                        }else{
                            contentStr += '<td></td>';
                        }

                        if(content[i].weiXinUser!=null && content[i].weiXinUser.leJiaUser!=null ) {
                            contentStr += '<td>' + content[i].weiXinUser.leJiaUser.phoneNumber
                                            +'('+content[i].weiXinUser.leJiaUser.userSid+')'+ '</td>';
                        }else{
                            contentStr += '<td></td>';
                        }

                        if(content[i].weiXinUser!=null) {
                            contentStr += '<td><img src="' + content[i].weiXinUser.headImageUrl
                                            + '" alt="...">' + content[i].weiXinUser.nickname + '</td>';
                        }else{
                            contentStr += '<td></td>';
                        }

                        if(content[i].rechargeStatus==1) {
                            contentStr += '<td>'+'处理中'+'</td>';
                        }else if(content[i].rechargeStatus==2) {
                            contentStr += '<td>已充值</td>';
                        }else if(content[i].rechargeStatus==3) {
                            contentStr += '<td>充值失败</td>';
                        }else {
                            contentStr += '<td></td>';
                        }

                        //  操作
                        if(content[i].rechargeStatus==1) {
                            contentStr += '<td>'
                                            + '<button type="button" class="btn btn-default" onclick="editSuccess('
                                            + content[i].id + ')">设为已充值</button>'
                                            + '<button type="button" class="btn btn-default" onclick="editFail('
                                            + content[i].id + ')">设为失败</button>' +
                                          '</td>';
                        }else{
                            contentStr += '<td>--</td>';
                        }
                        contentStr+="</tr>";
                        tableContent.innerHTML += contentStr;
                    }
                }
        });
    }
    getRechargeCardByAjax(rechargeCardCriteria);
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                rechargeCardCriteria.offset = p;
                getRechargeCardByAjax(rechargeCardCriteria);
            }
        });
    }
    function searchByCriteria() {
        rechargeCardCriteria.offset = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            rechargeCardCriteria.startDate = startDate;
            rechargeCardCriteria.endDate = endDate;
        }

        var exchangeCode = $("#exchangeCode").val();
        var ljUserSid = $("#ljUserSid").val();
        var userPhone = $("#userPhone").val();
        var rechargeStatus = $("#rechargeStatus").val();
        if (exchangeCode!="" && exchangeCode!= null) {
            rechargeCardCriteria.exchangeCode = exchangeCode;
        } else {
            rechargeCardCriteria.exchangeCode = null;
        }
        rechargeCardCriteria.ljUserSid = ljUserSid;
        rechargeCardCriteria.userPhone = userPhone;
        rechargeCardCriteria.rechargeStatus = rechargeStatus;
        getRechargeCardByAjax(rechargeCardCriteria);
    }


    //充值成功
    function editSuccess(id) {
        $("#editSuccess-confirm").bind("click", function () {
            $.post("/manage/rechargeCard/rechargeStatus/" + id, {rechargeStatus: 2}, function (data) {
                alert(data.msg);
                setTimeout(function () {
                    location.reload(true);
                }, 1000);

            });
        });
        $("#successWarn").modal("show");
    }
    //充值失败
    function editFail(id) {
        $("#editFail-confirm").bind("click", function () {
            $.post("/manage/rechargeCard/rechargeStatus/" + id, {rechargeStatus: 3}, function (data) {
                alert(data.msg);
                setTimeout(function () {
                    location.reload(true);
                }, 1000);

            });
        });
        $("#failWarn").modal("show");
    }

</script>

</body>
</html>
