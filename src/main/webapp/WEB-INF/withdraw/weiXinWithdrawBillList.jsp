<%--
  Created by IntelliJ IDEA.
  User: lss
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

        .modal-content > p {
            text-align: center;
            padding: 30px 0;
        }

        .remarks {
            width: 10%;
        }

        .remarksText {
            width: 150px;
            display: block;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        #more {
            position: absolute;
            width: 200px;
            background-color: rgba(0, 0, 0, 0.7);
            z-index: 100;
            color: #FFFFFF;
            padding: 10px;
            font-size: 16px;
            -webkit-border-radius: 0 10px 10px 10px;
            -moz-border-radius: 0 10px 10px 10px;
            border-radius: 0 10px 10px 10px;
            display: none;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
</head>
<%--提现窗口--%>
<div class="modal" id="createWarn">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">提示</h4>
            </div>
            <input type="hidden" id="confirmID">
            <input type="hidden" id="confirmTotalPrice">
            <div class="modal-body" id="withdrawal-title">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="withdraw-confirm" onclick="withdrawConfirm()">确认
                </button>
            </div>
        </div>
    </div>
</div>
<%--驳回窗口--%>
<body>
<div class="modal" id="reject">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">提示</h4>
            </div>

            <input type="hidden" id="rejectID">
            <div id="rejectMessage">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="reject-confirm" onclick="rejectConfirm()">确认
                </button>
            </div>
        </div>
    </div>
</div>
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
                    <div class="form-group col-md-6">
                        <label for="date-end">申请时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="partner-name">合伙人名称</label>
                        <input type="text" id="partner-name" class="form-control"
                               placeholder="请输入合伙人名称"/>
                    </div>

                    <div class="form-group col-md-3">
                        <label for="partner-ID">合伙人ID</label>
                        <input type="text" id="partner-ID" class="form-control"
                               placeholder="请输入合伙人ID"/>
                    </div>


                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchWeiXinWithdrawBillByCriteria()">查询
                        </button>
                    </div>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab"
                           onclick="searchWeiXinWithdrawBillByState()">全部</a>
                    </li>
                    <li class="active"><a href="#tab2" data-toggle="tab"
                                          onclick="searchWeiXinWithdrawBillByState(0)">申请中</a></li>
                    <li><a href="#tab2" data-toggle="tab"
                           onclick="searchWeiXinWithdrawBillByState(1)">未接收</a>
                    </li>
                    <li><a href="#tab3" data-toggle="tab"
                           onclick="searchWeiXinWithdrawBillByState(2)">已驳回</a>
                    </li>
                    <li><a href="#tab4" data-toggle="tab"
                           onclick="searchWeiXinWithdrawBillByState(3)">已退款</a>
                    </li>
                    <li><a href="#tab5" data-toggle="tab"
                           onclick="searchWeiXinWithdrawBillByState(4)">成功接收</a>
                    </li>
                    <li><a href="#tab6" data-toggle="tab"
                           onclick="searchWeiXinWithdrawBillByState(5)">异常</a>
                    </li>
                </ul>

                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>提现单编号</th>
                                <th>申请时间</th>
                                <th>提现合伙人</th>
                                <th>合伙人ID</th>
                                <th>结算账号</th>
                                <th>提现金额</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="weiXinWithdrawBillContent">
                            </tbody>
                            <div id="more">
                            </div>
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
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var weiXinWithdrawBillCriteria = {};
    var flag = true;
    var init1 = 0;
    var weiXinWithdrawBillContent = document.getElementById("weiXinWithdrawBillContent");
    $(function () {
        // tab切换
        $('#myTab li:eq(0) a').tab('show');
        // 提示框
//        $(".deleteWarn").click(function () {
//            $("#deleteWarn").modal("toggle");
//        });
        // 时间选择器
        $(document).ready(function () {
//            $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss')
//                                     + ' - ' +
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
        weiXinWithdrawBillCriteria.offset = 1;
        getWeiXinWithdrawBillByAjax(weiXinWithdrawBillCriteria);
    });
    //
    function getWeiXinWithdrawBillByAjax(weiXinWithdrawBillCriteria) {
        weiXinWithdrawBillContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/weiXinWithdrawBill/getWeiXinWithdrawBillByAjax",
            async: false,
            data: JSON.stringify(weiXinWithdrawBillCriteria),
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
                    initPage(weiXinWithdrawBillCriteria.offset, totalPage);
                }
                if (init1) {
                    initPage(1, totalPage);
                }


                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr><td>' + content[i].withdrawBillSid + '</td>';
                    contentStr +=
                        '<td><span>'
                        + new Date(content[i].createdDate).format('yyyy-MM-dd HH:mm:ss')
                        + '</span></td>';
                    if (content[i].partner != null) {
                        contentStr += '<td>' + content[i].partner.partnerName + '</td>';
                    } else {
                        contentStr += '<td>--</td>';
                    }
                    if (content[i].partner != null) {
                        contentStr += '<td>' + content[i].partner.partnerSid + '</td>';
                    } else {
                        contentStr += '<td>--</td>';
                    }
                    if (content[i].weiXinOtherUser != null) {
                        if (content[i].weiXinOtherUser.weiXinUser != null) {
                            contentStr +=
                                '<td><img height="75" width="75" src="' + content[i].weiXinOtherUser.weiXinUser.headImageUrl + '" alt="...">'
                                + content[i].weiXinOtherUser.weiXinUser.nickname + '</td>';
                        } else {
                            contentStr += '<td>--</td>';
                        }

                    } else {
                        contentStr += '<td>--</td>';
                    }

                    contentStr += '<td>' + content[i].totalPrice / 100.0 + '</td>';

                    if (content[i].state == 0) {
                        contentStr += '<td>申请中</td>';
                    } else if (content[i].state == 1) {
                        contentStr += '<td>未接收</td>';
                    } else if (content[i].state == 2) {
                        contentStr += '<td>已驳回</td>';
                    } else if (content[i].state == 3) {
                        contentStr += '<td>已退款</td>';
                    } else if (content[i].state == 4) {
                        contentStr += '<td>成功接收</td>';
                    } else if (content[i].state == 5) {
                        contentStr += '<td>异常</td>';
                    } else {
                        contentStr += '<td>--</td>';
                    }



                    if(content[i].state == 0){
                        contentStr +=
                            '<td>' + '<input type="hidden" class="id-hidden" value="'
                            + content[i].id + '" >'
                            + '<button class="btn btn-primary shareDetails" style="margin-top: 10px" >分润详情</button><button class="btn btn-primary confirmWithdrawBill" style="margin-top: 10px" >确认提现</button><button class="btn btn-primary reject" style="margin-top: 10px" >驳回</button>'
                    }else {
                        contentStr +=
                            '<td>' + '<input type="hidden" class="id-hidden" value="'
                            + content[i].id + '" >'
                            + '<button class="btn btn-primary shareDetails" style="margin-top: 10px" >分润详情</button>'

                    }

                    contentStr += '</tr>';
                    weiXinWithdrawBillContent.innerHTML += contentStr;

                }

                $(".reject").each(function (i) {
                    $(".reject").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        $("#reject").modal("toggle");
                        $.ajax({
                            type: "get",
                            url: "/manage/weiXinWithdrawBill/getOneWeiXinWithdrawBill/" + id,
                            contentType: "application/json",
                            success: function (data) {
                                var weiXinWithdrawBill = data.data;
                                var partnerName = weiXinWithdrawBill.partner.partnerName;
                                var totalPrice = weiXinWithdrawBill.totalPrice / 100.0;
                                var rejectMessage = document.getElementById("rejectMessage");
                                rejectMessage.innerHTML = "<h4>确认驳回合伙人" + partnerName + "，提现" + totalPrice + "元的申请吗？</h4>";
                                $("#rejectID").val(id);
                            }
                        });
                    });
                });


                $(".confirmWithdrawBill").each(function (i) {
                    $(".confirmWithdrawBill").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        $.ajax({
                            type: "get",
                            url: "/manage/weiXinWithdrawBill/getOneWeiXinWithdrawBill/" + id,
                            contentType: "application/json",
                            success: function (data) {
                                var weiXinWithdrawBill = data.data;
                                var partnerName = weiXinWithdrawBill.partner.partnerName;
                                var totalPrice = weiXinWithdrawBill.totalPrice / 100.0;
                                var withdrawalDiv = document.getElementById("withdrawal-title");
                                withdrawalDiv.innerHTML = "<h4>确认通过合伙人" + partnerName + "，提现" + totalPrice + "元的申请吗？</h4>";
                                $("#confirmID").val(id);
                                $("#confirmTotalPrice").val(totalPrice);

                                $("#createWarn").modal("toggle");
                            }
                        });
                    });
                });

                $(".shareDetails").each(function (i) {
                    $(".shareDetails").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        location.href = "/manage/weiXinWithdrawBill/shareDetailsPage/" + id;
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
                weiXinWithdrawBillCriteria.offset = p;
                init1 = 0;
                getWeiXinWithdrawBillByAjax(weiXinWithdrawBillCriteria);
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
    function searchWeiXinWithdrawBillByCriteria() {
        weiXinWithdrawBillCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            weiXinWithdrawBillCriteria.startDate = startDate;
            weiXinWithdrawBillCriteria.endDate = endDate;
        }
        if ($("#partner-name").val() != null && $("#partner-name").val() != '') {
            weiXinWithdrawBillCriteria.partnerName = $("#partner-name").val();
        } else {
            weiXinWithdrawBillCriteria.partnerName = null;
        }

        if ($("#partner-ID").val() != null && $("#partner-ID").val() != '') {
            weiXinWithdrawBillCriteria.partnerSid = $("#partner-ID").val();
        } else {
            weiXinWithdrawBillCriteria.partnerSid = null;
        }
        getWeiXinWithdrawBillByAjax(weiXinWithdrawBillCriteria);
    }


    function searchWeiXinWithdrawBillByState(state) {
        weiXinWithdrawBillCriteria.offset = 1;
        if (state != null) {
            weiXinWithdrawBillCriteria.state = state;
        } else {
            weiXinWithdrawBillCriteria.state = null;
        }
        flag = true;
        getWeiXinWithdrawBillByAjax(weiXinWithdrawBillCriteria);
    }

    function exportExcel() {
        weiXinWithdrawBillCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            weiXinWithdrawBillCriteria.startDate = startDate;
            weiXinWithdrawBillCriteria.endDate = endDate;
        }
        if ($("#partner-name").val() != null && $("#partner-name").val() != '') {
            weiXinWithdrawBillCriteria.partnerName = $("#partner-name").val();
        } else {
            weiXinWithdrawBillCriteria.partnerName = null;
        }

        if ($("#partner-ID").val() != null && $("#partner-ID").val() != '') {
            weiXinWithdrawBillCriteria.partnerSid = $("#partner-ID").val();
        } else {
            weiXinWithdrawBillCriteria.partnerSid = null;
        }
        post("/manage/weiXinWithdrawBill/exportExcel", weiXinWithdrawBillCriteria);

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
    function rejectConfirm() {
        var rejectID = $("#rejectID").val();

        $.ajax({
            type: "get",
            url: "/manage/weiXinWithdrawBill/rejectConfirm/" + rejectID,
            contentType: "application/json",
            success: function (data) {
                var msg=data.msg;
                if(msg=="OK"){
                    alert("驳回成功");
                }else {
                    alert("驳回失败");
                }
                getWeiXinWithdrawBillByAjax(weiXinWithdrawBillCriteria);
            }
        });

    }
    function withdrawConfirm() {
        var confirmID= $("#confirmID").val();
        $.ajax({
            type: "get",
            url: "/manage/weiXinWithdrawBill/withdrawConfirm/" + confirmID,
            contentType: "application/json",
            success: function (data) {
                var confirmTotalPrice=$("#confirmTotalPrice").val()/100.0;
                var msg=data.msg;
                var map=data.data;
                if(msg=="OK"){
                    var send_listid=map.send_listid;
                    alert("提现成功,微信红包"+confirmTotalPrice+"元已发出，微信红包单号："+send_listid+"");
                }else {
                    if(msg=="serverError"){
                      alert("提现失败,错误描述：serverError")
                    }else {
                        var send_listid=map.send_listid;
                        alert("提现失败,错误描述:"+map.err_code_des+"，错误码："+map.err_code+"")
                    }
                }
                getWeiXinWithdrawBillByAjax(weiXinWithdrawBillCriteria);
            }
        });


    }

</script>
</body>
</html>

