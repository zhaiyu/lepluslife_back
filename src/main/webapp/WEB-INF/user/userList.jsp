<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/13
  Time: 下午6:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../commen.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>乐+生活 后台模板管理系统</title>
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <%--<link rel="stylesheet" href="${resourceUrl}/css/jqpagination.css"/>--%>
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <style>
        .pagination > li > a.focusClass {
            background-color: #ddd;
        }

        table tr td img {
            width: 60px;
        }

    </style>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
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

                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-2">
                        <label for="userSid">会员ID</label>
                        <input type="text" id="userSid" class="form-control"
                               placeholder="请输入会员ID"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="nickname">会员昵称</label>
                        <input type="text" id="nickname" class="form-control"
                               placeholder="请输入会员昵称"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="phoneNumber">会员手机号</label>
                        <input type="text" id="phoneNumber" class="form-control"
                               placeholder="会员手机号"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="bindMerchantName">绑定商户名称</label>
                        <input type="text" id="bindMerchantName" class="form-control"
                               placeholder="绑定商户名称"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="bindPartnerName">绑定合伙人名称</label>
                        <input type="text" id="bindPartnerName" class="form-control"
                               placeholder="绑定合伙人名称"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="userType">会员类型</label>
                        <select class="form-control" id="userType">
                            <option value="-1">全部分类</option>
                            <option value="1">乐+会员</option>
                            <%--  1 或 2 --%>
                            <option value="0">普通消费者</option>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="province">所在省份</label>
                        <select class="form-control" id="province">
                            <option value="0">全部分类</option>
                            <option value="2">北京</option>
                            <option value="1">辽宁</option>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="city">所在城市</label>
                        <select class="form-control" id="city">
                            <option value="0">全部分类</option>
                            <c:forEach items="${cities}" var="city">
                                <option value="${city.id}">${city.name}</option>
                            </c:forEach>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="subState">关注状态</label>
                        <select class="form-control" id="subState">
                            <option value="-1">全部分类</option>
                            <option value="1">已关注</option>
                            <option value="0">未关注</option>
                            <%--  0 或 2 --%>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="massRemain">本月群发余额</label>
                        <select class="form-control" id="massRemain">
                            <option value="-1">全部余额</option>
                            <option value="5">余额大于0</option>
                            <option value="4">4</option>
                            <option value="3">3</option>
                            <option value="2">2</option>
                            <option value="1">1</option>
                            <option value="0">0</option>
                        </select></div>

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
                                onclick="searchUserByCriteria(0)">筛选
                        </button>
                        <c:if test="${status == 1}">
                            <button class="btn btn-primary"
                                    style="margin-left:20px;margin-top: 24px"
                                    onclick="searchUserByCriteria(1)">按条件群发消息
                            </button>
                            <button class="btn btn-primary"
                                    style="margin-left:20px;margin-top: 24px"
                                    onclick="sendMassToAll()">所有人群发消息
                            </button>
                        </c:if>
                        <c:if test="${status == 0}">
                            <button class="btn btn-primary"
                                    style="margin-left:20px;margin-top: 24px" disabled>按条件群发消息
                            </button>
                            <button class="btn btn-primary"
                                    style="margin-left:20px;margin-top: 24px" disabled>所有人群发消息
                            </button>
                        </c:if>
                    </div>

                    <div class="form-group col-md-3"></div>
                </div>
                <div id="myTabContent" class="tab-content">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="text-center">会员ID</th>
                            <th class="text-center">会员类型</th>
                            <th class="text-center">关注状态</th>
                            <th class="text-center">微信信息</th>
                            <th class="text-center">注册时间</th>
                            <th class="text-center">手机号</th>
                            <th class="text-center">城市</th>
                            <th class="text-center">绑定商户</th>
                            <th class="text-center">绑定商户时间</th>
                            <th class="text-center">绑定合伙人</th>
                            <th class="text-center">绑合伙人时间</th>
                            <th class="text-center">红包</th>
                            <th class="text-center">积分</th>
                            <th class="text-center">群发余额</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody id="userContent">
                        </tbody>
                    </table>

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

<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var userCriteria = {};
    var flag = true;
    var search = 0;
    var init1 = 0;
    var userContent = document.getElementById("userContent");
    $(function () {
        // 时间选择器
        $(document).ready(function () {
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
        });
        userCriteria.offset = 1;
        getUserByAjax(userCriteria);
    });

    function getUserByAjax(criteria) {
        criteria.userSid = $("#userSid").val();
        if (search == 1) {
            search = 0;
            $.ajax({
                       type: "post",
                       url: "/manage/userList",
                       async: false,
                       data: JSON.stringify(criteria),
                       contentType: "application/json",
                       success: function (data) {
                           var map = data.data;
                           var totalElements = map.totalElements;
                           location.href =
                           "/manage/weixin/news/list?totalElements=" + totalElements
                           + "&leJiaUserCriteria=" + JSON.stringify(criteria);
                       }
                   });

        } else {
            userContent.innerHTML = "";
            $.ajax({
                       type: "post",
                       url: "/manage/userList",
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
                           //   alert(flag + "   " + init1);
                           if (flag) {
                               initPage(userCriteria.offset, totalPage);
                               flag = false;
                           }

                           if (init1) {
                               initPage(1, totalPage);
                           }

                           var userContent = document.getElementById("userContent");
                           for (i = 0; i < content.length; i++) {
                               var contentStr = '<tr><td>' + content[i].userSid + '</td>';
                               if (content[i].state == -1) {
                                   contentStr += '<td><span>未知</span></td>';
                               } else if (content[i].state == 0) {
                                   contentStr += '<td><span>普通消费者</span></td>';
                               } else {
                                   contentStr += '<td><span>乐+会员</span></td>';
                               }
                               if (content[i].subState == 1) {
                                   contentStr += '<td><span>已关注</span></td>';
                               } else if (content[i].subState == 0 || content[i].subState == 2) {
                                   contentStr += '<td><span>未关注</span></td>';
                               } else {
                                   contentStr += '<td><span>未知</span></td>';
                               }
                               contentStr +=
                               '<td><img src="' + content[i].headImageUrl + '" alt="...">'
                               + content[i].nickname + '</td>';
                               contentStr += '<td><span>'
                                             + new Date(content[i].createDate).format('yyyy-MM-dd HH:mm')
                                             + '</span></td>';
                               contentStr +=
                               '<td><span>' + content[i].phoneNumber + '</span></td>';
                               contentStr +=
                               '<td><span>' + content[i].city + '</span></td>';
                               contentStr +=
                               '<td><span>' + content[i].merchantName + '</span></td>';
                               contentStr += '<td><span>'
                                             + new Date(content[i].bindMerchantDate).format('yyyy-MM-dd')
                                             + '</span></td>';
                               contentStr +=
                               '<td><span>' + content[i].partnerName + '</span></td>';
                               contentStr += '<td><span>'
                                             + new Date(content[i].bindPartnerDate).format('yyyy-MM-dd')
                                             + '</span></td>';
                               contentStr +=
                               '<td><span>' + content[i].scoreA / 100 + '</span></td>';
                               contentStr +=
                               '<td><span>' + content[i].scoreB + '</span></td>';
                               contentStr +=
                               '<td><span>' + content[i].massRemain + '</span></td>';
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="'
                               + content[i].id
                               + '"><button class="btn btn-primary changeOrderToPaid">交易记录</button>'
                               +
                               '<button class="btn btn-primary scoreDetail">账户明细</button></td></tr>';

                               userContent.innerHTML += contentStr;
                           }

                           $(".scoreDetail").each(function (i) {
                               $(".scoreDetail").eq(i).bind("click", function () {
                                   var id = $(this).parent().find(".id-hidden").val();
                                   location.href = "/manage/score/list/" + id;
                               });
                           });

                       }
                   });
        }
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

    function searchUserByCriteria(i) {
        userCriteria.offset = 1;
        search = i;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            userCriteria.startDate = startDate;
            userCriteria.endDate = endDate;
        }
        if ($("#userSid").val() != "" && $("#userSid").val() != null) {
            userCriteria.userSid = $("#userSid").val();
        } else {
            userCriteria.userSid = null;
        }
        if ($("#nickname").val() != "" && $("#nickname").val() != null) {
            userCriteria.nickname = $("#nickname").val();
        } else {
            userCriteria.userSid = null;
        }
        if ($("#phoneNumber").val() != "" && $("#phoneNumber").val() != null) {
            userCriteria.phoneNumber = $("#phoneNumber").val();
        } else {
            userCriteria.phoneNumber = null;
        }
        if ($("#bindMerchantName").val() != "" && $("#bindMerchantName").val() != null) {
            userCriteria.merchant = $("#bindMerchantName").val();
        } else {
            userCriteria.merchant = null;
        }
        if ($("#bindPartnerName").val() != "" && $("#bindPartnerName").val() != null) {
            userCriteria.partner = $("#bindPartnerName").val();
        } else {
            userCriteria.partner = null;
        }
        if ($("#userType").val() != -1) {
            userCriteria.userType = $("#userType").val();
        } else {
            userCriteria.userType = null;
        }
        if ($("#subState").val() != -1) {
            userCriteria.subState = $("#subState").val();
        } else {
            userCriteria.subState = null;
        }
        if ($("#massRemain").val() != -1) {
            userCriteria.massRemain = $("#massRemain").val();
        } else {
            userCriteria.massRemain = null;
        }

        if ($("#city").val() != 0) {
            userCriteria.city = $("#city").val();
        } else {
            userCriteria.city = null;
        }

        if ($("#province").val() != 0) {
            userCriteria.province = $("#province").val();
        } else {
            userCriteria.province = null;
        }

        getUserByAjax(userCriteria);
    }

    function sendMassToAll() {
        location.href = "/manage/weixin/news/allList";
    }

</script>
</body>
</html>

