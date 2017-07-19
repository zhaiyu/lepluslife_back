<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 17/6/15
  Time: 下午6:37
  门店信息修改记录
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../commen.jsp" %>
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
        table tr td img {
            width: 60px;
        }

        textarea {
            resize: none;
            width: 400px;
            height: 300px;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
</head>

<body>
<div id="topIframe">
    <%@include file="../../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main">
            <div class="container-fluid" style="padding-top: 20px">

                <div class="row" style="margin-bottom: 30px">

                    <div class="form-group col-md-2">
                        <label for="ledgerNo">易宝商户编号</label>
                        <input type="text" id="ledgerNo" class="form-control"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="merchantUserId">积分客商户编号</label>
                        <input type="text" id="merchantUserId" class="form-control"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="merchantName">商户名称</label>
                        <input type="text" id="merchantName" class="form-control"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label>修改状态</label>
                        <select class="form-control" id="state">
                            <option value="-1">全部</option>
                            <option value="0">审核中</option>
                            <option value="1">审核成功</option>
                            <option value="2">审核失败</option>
                        </select>
                    </div>

                    <div class="form-group col-md-4">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchByCriteria()">筛选
                        </button>
                    </div>

                    <div class="form-group col-md-3"></div>
                </div>
                <div id="myTabContent" class="tab-content">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="text-center">修改请求号</th>
                            <th class="text-center">易宝商户编号</th>
                            <th class="text-center">积分客商户编号</th>
                            <th class="text-center">商户名称</th>
                            <th class="text-center">绑定手机号</th>
                            <th class="text-center">银行卡号</th>
                            <th class="text-center">开户行、省、市</th>
                            <th class="text-center">起结金额</th>
                            <th class="text-center">修改状态</th>
                            <th class="text-center">修改时间</th>
                            <th class="text-center">完成时间</th>
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
    <%@include file="../../common/bottom.jsp" %>
</div>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var criteria = {};
    var flag = true;
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
        criteria.currPage = 1;
        getUserByAjax(criteria);
    });
    function getUserByAjax(criteria) {
        userContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/modify/ajaxList",
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
                           initPage(criteria.currPage, totalPage);
                           flag = false;
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       var userContent = document.getElementById("userContent");
                       for (var i = 0; i < content.length; i++) {
                           var dataAfter = content[i].dataAfter.split(',');
                           var contentStr = '<tr><td>' + content[i].requestId + '</td>';
                           contentStr += '<td>' + content[i].merchantUserLedger.ledgerNo + '</td>';
                           contentStr +=
                               '<td>' + content[i].merchantUserLedger.merchantUser.id + '</td>';
                           contentStr +=
                               '<td>' + content[i].merchantUserLedger.merchantUser.merchantName
                               + '</td>';
                           contentStr += '<td>' + dataAfter[0] + '</td>';
                           contentStr += '<td>' + dataAfter[1] + '</td>';
                           contentStr +=
                               '<td>' + dataAfter[2] + ',' + dataAfter[3] + ',' + dataAfter[4]
                               + '</td>';
                           contentStr += '<td>' + (dataAfter[5] / 100) + '</td>';
                           if (content[i].state == 0) {
                               contentStr += '<td>审核中</td>';
                           } else if (content[i].state == 1) {
                               contentStr += '<td>审核成功</td>';
                           } else {
                               contentStr += '<td>审核失败</td>';
                           }
                           contentStr +=
                               '<td>' + new Date(content[i].dateCreated).format('yyyy-MM-dd HH:mm')
                               + '</td>';
                           if (content[i].dateCompleted == null || content[i].dateCompleted
                                                                   == 'null') {
                               contentStr += '<td></td>';
                           } else {
                               contentStr +=
                                   '<td>' + new Date(content[i].dateCompleted).format(
                                       'yyyy-MM-dd HH:mm')
                                   + '</td>';
                           }
                           contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="'
                               + content[i].requestId
                               + '">'
                               + '<button class="btn btn-primary queryModify" >查询修改结果</button></td></tr>';

                           userContent.innerHTML += contentStr;
                       }
                       $(".queryModify").each(function (i) {
                           $(".queryModify").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $.ajax({
                                          url: "/manage/modify/queryModify?requestId=" + id,
                                          type: "post",
                                          success: function (result) {
                                              if (eval(result.code) === 1) {
                                                  if (result.status == 'SUCCESS') {
                                                      alert("审核成功");
                                                  } else if (result.status == 'FAILED') {
                                                      alert("审核失败");
                                                  } else if (result.status == 'INIT') {
                                                      alert("审核中");
                                                  } else {
                                                      alert("未知状态");
                                                  }
                                                  if (result.diff == 'YES') {
                                                      getUserByAjax(criteria);
                                                  }
                                              } else {
                                                  alert('保存失败.错误码:' + result.code + '(' + result.msg
                                                        + ')');
                                              }
                                          }
                                      });
                           });
                       });
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
                                             criteria.currPage = p;

                                             getUserByAjax(criteria);
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
                            ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f"
                                : "\u5468")
                                : "") + week[this.getDay() + ""]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt =
                    fmt.replace(RegExp.$1,
                                (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr((""
                                                                                          + o[k]).length)));
            }
        }
        return fmt;
    }
    //  设置条件
    function condition() {
        criteria.currPage = 1;
        init1 = 1;
        var ledgerNo = $("#ledgerNo").val();
        if (ledgerNo != null && ledgerNo != "") {
            criteria.ledgerNo = ledgerNo;
        } else {
            criteria.ledgerNo = null;
        }
        var merchantUserId = $("#merchantUserId").val();
        if (merchantUserId != null && merchantUserId != "") {
            criteria.merchantUserId = merchantUserId;
        } else {
            criteria.merchantUserId = null;
        }
        var merchantName = $("#merchantName").val();
        if (merchantName != null && merchantName != "") {
            criteria.merchantName = merchantName;
        } else {
            criteria.merchantName = null;
        }
        var state = $("#state").val();
        if (state != null && state != -1) {
            criteria.state = state;
        } else {
            criteria.state = null;
        }
    }
    //  条件查询
    function searchByCriteria() {
        condition();
        getUserByAjax(criteria);
    }
</script>
</body>
</html>

