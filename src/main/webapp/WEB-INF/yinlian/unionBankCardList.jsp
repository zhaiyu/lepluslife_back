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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>

    <style>
        thead th, tbody td {
            text-align: center;
        }

        #myTab {
            margin-bottom: 10px;
        }

        .create_edit-addyjcl > div, .create_edit-addhbcl > div {
            margin-bottom: 10px;
        }

        .create_edit-addyjcl > div *, .create_edit-addhbcl > div * {
            float: left;
        }

        .create_edit-addyjcl input，.create_edit-addhbcl input {
            margin: 0 6px;
            border: 1px solid #ccc;
            -webkit-border-radius: 2px;
            -moz-border-radius: 2px;
            border-radius: 2px;
        }

        .create_edit-addyjcl button, .create_edit-addhbcl button {
            border: 1px solid #ccc;
            -webkit-border-radius: 2px;
            -moz-border-radius: 2px;
            border-radius: 2px;
            padding: 0 1%;
            margin-left: 5px;
        }

        .yjcl_del {
            color: #fff;
            background-color: #c9302c;
            border-color: #ac2925 !important;
        }

        .create_edit-addyjcl > div:after, .create_edit-addhbcl > div:after {
            content: '\20';
            display: block;
            clear: both;
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
        <span style="font-size: large">会员绑卡</span>
        <div class="main">
            <div class="container-fluid">
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-6">
                        <label for="date-end">绑定时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="user_sid">会员ID</label>
                        <input type="text" class="form-control" id="user_sid"
                               placeholder="请输入会员ID">
                    </div>
                </div>
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="bank_number">银行卡号</label>
                        <input type="text" id="bank_number" class="form-control" placeholder="请输入银行卡号"/>
                    </div>

                    <div class="form-group col-md-3">
                        <label for="register-source">注册方式</label>
                        <select class="form-control" id="register-source">
                            <option value="">全部</option>
                            <option value="1">APP注册</option>
                            <option value="2">POS注册</option>
                            <option value="3">手动注册</option>
                            <option value="4">公众号注册</option>
                        </select>
                    </div>

                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchUnionBankCardByCriteria()">筛选
                        </button>
                    </div>
                </div>
                <div style="text-align: right;margin: 10px 0 0 0">
                    <button class="btn btn-primary" onclick="addUnionBankCard()">手动绑卡</button>
                </div>
            </div>

            <span style="font-size: large">门店列表</span>
            <div id="myTabContent" class="tab-content">
                <div class="tab-pane fade in active" id="tab1">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr class="active">
                            <th>银行卡号</th>
                            <th>会员ID</th>
                            <th>手机号</th>
                            <th>开户行</th>
                            <th>卡类型</th>
                            <th>卡名称</th>
                            <th>注册方式</th>
                            <th>绑定时间</th>
                            <th>状态</th>
                        </tr>
                        </thead>
                        <tbody id="unionBankCardContent">
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

<div class="modal fade" id="myModal2">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <table>

                    <tr>
                        <div class="form-group">
                            <label class="form-label">卡号</label>
                            <input type="text" class="form-control" id="number">
                        </div>
                    </tr>
                    <tr>
                        <div class="form-group">
                            <label class="form-label">会员ID</label>
                            <input type="text" class="form-control" id="userSid">
                        </div>
                    </tr>
                    <tr>
                        <div class="MODInput_row">
                            <div class="form-group">
                                <label class="form-label">银商注册手机号</label>
                                <input type="text" class="form-control" id="phoneNumber" placeholder="请输入银商注册手机号">
                            </div>
                    </tr>

                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="deal-confirm2" onclick="confirmAddunionBankCard()">确认
                </button>
            </div>
        </div>
    </div>
</div>


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/Mod/common.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/bootstrap.min.js"></script>
<%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/jquery.combo.select.js"></script>
<script>
    function addUnionBankCard() {
        $('#myModal2').modal({
            show: true,
            backdrop: 'static'
        });
    }


    var unionBankCardCriteria = {};
    var unionBankCardContent = document.getElementById("unionBankCardContent");
    var flag = true;
    var init1 = 0;

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
        unionBankCardCriteria.offset = 1;
        getUnionBankCardByAjax(unionBankCardCriteria);

    })


    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                unionBankCardCriteria.offset = p;
                init1 = 0;
                getUnionBankCardByAjax(unionBankCardCriteria);
            }
        });
    }


    function searchUnionBankCardByCriteria() {
        unionBankCardCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            unionBankCardCriteria.startDate = startDate;
            unionBankCardCriteria.endDate = endDate;
        }

        if ($("#user_sid").val() != "" && $("#user_sid").val() != null) {
            unionBankCardCriteria.userSid = $("#user_sid").val();
        } else {
            unionBankCardCriteria.userSid = null;
        }

        if ($("#bank_number").val() != "" && $("#bank_number").val() != null) {
            unionBankCardCriteria.number = $("#bank_number").val();
        } else {
            unionBankCardCriteria.number = null;
        }

        if ($("#register-source").val() != "" && $("#register-source").val() != null) {
            unionBankCardCriteria.registerWay = $("#register-source").val();
        } else {
            unionBankCardCriteria.registerWay = null;
        }


        getUnionBankCardByAjax(unionBankCardCriteria);
    }

    function getUnionBankCardByAjax() {
        unionBankCardContent.innerHTML = "";

        $.ajax({
            type: "post",
            url: "/manage/unionBankCard/getUnionBankCardByAjax",
            async: false,
            data: JSON.stringify(unionBankCardCriteria),
            contentType: "application/json",
            success: function (data) {
                var page = data.data.page;
                var bankCards = data.data.bankCards;
                var content = page.content;
                var totalPage = page.totalPages;
                $("#totalElements").html(page.totalElements);
                if (totalPage == 0) {
                    totalPage = 1;
                }
                if (flag) {
                    flag = false;
                    initPage(unionBankCardCriteria.offset, totalPage);
                }
                if (init1) {
                    initPage(1, totalPage);
                }
                for (i = 0; i < content.length; i++) {

                    var contentStr = '<tr><td>' + content[i].number + '</td>';
                    contentStr += '<td><span>' + content[i].userSid + '</span></td>';
                    contentStr += '<td><span>' + content[i].phoneNumber + '</span></td>';

                    if (bankCards[i] != null && bankCards[i].bankName != null) {
                        contentStr += '<td><span>' + bankCards[i].bankName + '</span></td>';
                    } else {
                        contentStr += '<td><span>--</span></td>';
                    }
                    if (bankCards[i] != null && bankCards[i].cardType != null) {
                        contentStr += '<td><span>' + bankCards[i].cardType + '</span></td>';
                    } else {
                        contentStr += '<td><span>--</span></td>';
                    }
                    if (bankCards[i] != null && bankCards[i].cardName != null) {
                        contentStr += '<td><span>' + bankCards[i].cardName + '</span></td>';
                    } else {
                        contentStr += '<td><span>--</span></td>';
                    }

                    if (content[i].registerWay == 1) {
                        contentStr += '<td><span>APP注册</span></td>';
                    } else if (content[i].registerWay == 2) {
                        contentStr += '<td><span>POS注册</span></td>';
                    } else if (content[i].registerWay == 3) {
                        contentStr += '<td><span>手动注册</span></td>';
                    } else {
                        contentStr += '<td><span>--</span></td>';
                    }

                    contentStr +=
                            '<td><span>'
                            + new Date(content[i].createDate).format('yyyy-MM-dd HH:mm:ss')
                            + '</span></td>';
                    if (content[i].state == 1) {
                        contentStr += '<td><span>已注册</span></td></tr>';
                    } else if (content[i].state == 0) {
                        contentStr += '<td><span>未注册</span></td></tr>';
                    }else if (content[i].state == 2) {
                        contentStr += '<td><span>取消注册</span></td></tr>';
                    }
                    else {
                        contentStr += '<td><span>--</span></td></tr>';
                    }

                    unionBankCardContent.innerHTML += contentStr;

                }
            }
        })

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
    function confirmAddunionBankCard() {
        var number = $("#number").val();
        var userSid = $("#userSid").val();
        var phoneNumber = $("#phoneNumber").val();

        if ($.trim(number) == "" || $.trim(userSid) == "" || $.trim(phoneNumber) == "" || number == null || userSid == null || phoneNumber == null) {
            alert("请填好参数");
        } else {
            var unionBankCardCriteria2 = {};
            unionBankCardCriteria2.number = number;
            unionBankCardCriteria2.userSid = userSid;
            unionBankCardCriteria2.phoneNumber = phoneNumber;

            if (!(/^1[34578]\d{9}$/.test(phoneNumber))) {
                alert("手机号码有误，请重填");
            } else {
                $.ajax({
                    type: "post",
                    url: "/manage/unionBankCard/registerByHand",
                    async: false,
                    data: JSON.stringify(unionBankCardCriteria2),
                    contentType: "application/json",
                    success: function (data) {
                        alert(data.msg);
                    }
                });
            }

        }
        $("#number").val("");
        $("#userSid").val("");
        $("#phoneNumber").val("");
        getUnionBankCardByAjax(unionBankCardCriteria);
    }
</script>
</body>
</html>