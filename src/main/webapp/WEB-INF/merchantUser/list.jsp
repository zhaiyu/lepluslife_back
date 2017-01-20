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
        thead th, tbody td {
            text-align: center;
        }

        table tr td img {
            width: 80px;
            height: 80px;
        }

        .table > thead > tr > td, .table > thead > tr > th {
            line-height: 40px;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
            line-height: 60px;
        }

        .modal-body .main {
            width: 345px;
            height: 480px;
            margin: 0 auto;
            background: url("${resourceUrl}/images/lefuma.png") no-repeat;
            background-size: 100% 100%;
            position: relative;
        }

        .modal-body #myShowImage {
            width: 345px;
            height: 480px;
            position: absolute;
            top: 20px;
            left: 0;
            right: 0;
            margin: auto;
            display: none;
        }

        .modal-body .main .rvCode {
            width: 230px;
            height: 230px;
            position: absolute;
            top: 120px;
            left: 0;
            right: 0;
            margin: auto;
        }

        .modal-body .main .shopName {
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
        <div class="main">
            <div class="container-fluid" style="padding-top: 20px">
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-3">
                        <label for="date-end">创建时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-2">
                        <label>所在城市</label>
                        <select class="form-control" id="stay-city">
                            <option value="">所在城市（全部）</option>
                            <c:forEach items="${citys}" var="city">
                                <option value="${city.id}">${city.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="link-man">商户联系人</label>
                        <input type="text" id="link-man" class="form-control" placeholder="商户联系人"/>
                    </div>
                </div>
                <div class="row" style="margin-top: 10px">
                    <div class="form-group col-md-3">
                        <label for="merchant-name">商户名称</label>
                        <input type="text" id="merchant-name" class="form-control"
                               placeholder="商户名称"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="phone-num">绑定手机号</label>
                        <input type="number" id="phone-num" class="form-control"
                               placeholder="绑定手机号"/>
                    </div>
                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchMerchantUserByCriteria()">筛选
                        </button>
                    </div>
                </div>
                <div style="text-align: right;margin: 10px 0 0 0">
                    <button class="btn btn-primary">导出表格</button>
                    <button class="btn btn-primary" onclick="edit()">新建商户</button>
                </div>
                <div id="myTabContent" class="tab-content" style="margin: 10px 0 0 0">
                    <div class="tab-pane fade in active" id="ing">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>商户ID</th>
                                <th>所属城市</th>
                                <th>商户名称</th>
                                <th>名下门店</th>
                                <th>名下POS</th>
                                <th>移动商户号</th>
                                <th>创建时间</th>
                                <th>锁定会员</th>
                                <th>佣金余额</th>
                                <th>累计佣金收入</th>
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


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var merchantUserCriteria = {};
    var init1 = 0;
    var flag = true;
    merchantUserCriteria.offset = 1;
    var tableContent = document.getElementById("tablePage");
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

    function getMerchantUserByAjax(merchantUserCriteria) {
        tableContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/merchantUser/ajaxList",
                   async: false,
                   data: JSON.stringify(merchantUserCriteria),
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
                           initPage(merchantUserCriteria.offset, totalPage);
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       for (var i = 0; i < content.length; i++) {
                           var o = content[i];
                           var contentStr = '<tr><td>' + o.id + '</td>';
                           contentStr += '<td>' + o.cityName + '</td>';
                           contentStr += '<td>' + o.merchantName + '</td>';
                           var shop = '';
                           shop += o.total_shops;
                           if (o.LM_shops != 0) {
                               shop += '（联盟' + o.LM_shops;
                               if (o.PT_shops != 0) {
                                   shop += '，普通' + o.PT_shops + '）';
                               } else {
                                   shop += '）';
                               }
                           } else if (o.PT_shops != 0) {
                               shop += '（普通' + o.PT_shops + '）';
                           }
                           contentStr += '<td>' + shop + '</td>';
                           contentStr += '<td>' + o.pos + '</td>';
                           var settle = '';
                           settle += o.total_settle;
                           if (o.total_settle == 0) {
                               settle = '0';
                           } else {
                               if (o.LM_settle != 0) {
                                   settle += '（联盟' + o.LM_settle;
                                   if (o.PT_settle != 0) {
                                       settle += '，普通' + o.PT_settle + '）';
                                   } else {
                                       settle += '）';
                                   }
                               } else if (o.PT_settle != 0) {
                                   settle += '（普通' + o.PT_settle + '）';
                               }
                           }
                           contentStr += '<td>' + settle + '</td>';
                           contentStr +=
                           '<td>' + new Date(o.createdDate).format("yyyy-MM-dd HH:mm") + '</td>';
                           contentStr += '<td>' + o.binding_user + '/' + o.total_user + '</td>';
                           contentStr += '<td>￥' + o.availableBalance / 100 + '</td>';
                           contentStr += '<td>￥' + o.totalMoney / 100 + '</td>';
                           //  操作
                           contentStr += '<td>' +
                                         '<button type="button" class="btn btn-default" onclick="showInfo(\''
                                         + o.id + '\')">商户详情</button>'
                                         + '<button type="button" class="btn btn-default" onclick="edit(\''
                                         + o.id + '\')">编辑</button>' +
                                         '</td></tr>';
                           tableContent.innerHTML += contentStr;
                       }
                   }
               });
    }
    function showInfo(currId) {
        window.location.href = '/manage/merchantUser/info/' + currId;
    }
    function edit(currId) {
        if (currId != null) {
            window.location.href = '/manage/merchantUser/edit?id=' + currId;
        } else {
            window.location.href = '/manage/merchantUser/edit';
        }

    }
    getMerchantUserByAjax(merchantUserCriteria);
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             merchantUserCriteria.offset = p;
                                             init1 = 0;
                                             getMerchantUserByAjax(merchantUserCriteria);
                                         }
                                     });
    }
    function searchMerchantUserByCriteria() {
        merchantUserCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            merchantUserCriteria.startDate = startDate;
            merchantUserCriteria.endDate = endDate;
        }
        var city = {};
        var cityId =$("#stay-city").val();
        var $phoneNum = $("#phone-num").val();
        var $linkMan = $("#link-man").val();
        var $merchantName = $("#merchant-name").val();
        if (cityId != "" && cityId != null) {
            city.id = cityId;
        } else {
            city = null;
        }
        merchantUserCriteria.city = city;
        if ($phoneNum != "" && $phoneNum != null) {
            merchantUserCriteria.phoneNum = $phoneNum;
        } else {
            merchantUserCriteria.phoneNum = null;
        }
        if ($linkMan != "" && $linkMan != null) {
            merchantUserCriteria.linkMan = $linkMan;
        } else {
            merchantUserCriteria.linkMan = null;
        }
        if ($merchantName != "" && $merchantName != null) {
            merchantUserCriteria.merchantName = $merchantName;
        } else {
            merchantUserCriteria.merchantName = null;
        }
        getMerchantUserByAjax(merchantUserCriteria);
    }


</script>
</body>
</html>
