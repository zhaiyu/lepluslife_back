<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 17/7/19
  Time: 下午6:37
  易宝子商户列表页
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
                        <label>审核状态</label>
                        <select class="form-control" id="state">
                            <option value="-1">全部</option>
                            <option value="0">冻结(待审核)</option>
                            <option value="1">审核成功</option>
                            <option value="2">审核失败</option>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label>结算费承担</label>
                        <select class="form-control" id="costSide">
                            <option value="-1">全部</option>
                            <option value="0">积分客</option>
                            <option value="1">子商户</option>
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
                            <th>易宝商户编号</th>
                            <th>积分客商户编号</th>
                            <th>商户名称</th>
                            <th>签约名称</th>
                            <th>注册结算类型</th>
                            <th>绑定手机号</th>
                            <th>起结金额</th>
                            <th>结算费承担方</th>
                            <th>审核状态</th>
                            <th>操作</th>
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
                   url: "/manage/ledger/ajaxList",
                   async: false,
                   data: JSON.stringify(criteria),
                   contentType: "application/json",
                   success: function (data) {
                       var map = data.data;
                       var list = map.content;
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
                       var currContent = '';
                       for (var i = 0; i < list.length; i++) {
                           var m = list[i];
                           var contentStr = '<tr><td>' + m.ledgerNo + '</td>';
                           contentStr += '<td>' + m.merchantUser.id + '</td>';
                           contentStr += '<td>' + m.merchantUser.merchantName + '</td>';
                           contentStr += '<td>' + m.signedName + '</td>';
                           //注册类型  1=PERSON(个人)|| 2=ENTERPRISE(企业)
                           if (m.customerType == 1) {
                               contentStr += '<td>个人对私</td>';
                           } else {
                               contentStr += '<td>企业对公</td>';
                           }
                           contentStr += '<td>' + m.bindMobile + '</td>';
                           contentStr += '<td>' + m.minSettleAmount / 100 + '</td>';
                           //结算费用承担方  0=积分客（主商户）|1=子商户
                           if (m.costSide == 0) {
                               contentStr += '<td>积分客</td>';
                           } else {
                               contentStr += '<td>子商户</td>';
                           }
                           //状态   -1=冻结|0=激活(审核中)|1=审核成功|其他为审核失败错误码
                           var stateVal = '';
                           switch (m.state) {
                               case -1:
                                   stateVal = '冻结';
                                   break;
                               case 0:
                                   stateVal = '待审核';
                                   break;
                               case 1:
                                   stateVal = '审核成功';
                                   break;
                               case 2:
                                   stateVal = '审核失败';
                                   break;
                               default:
                                   stateVal = m.state + '(未知)';
                           }
                           //修改状态  -1=初始化|0=修改审核中|1=修改审核成功|2=修改审核失败
                           var checkStateVal = '';
                           switch (m.checkState) {
                               case -1:
                                   checkStateVal = '';
                                   break;
                               case 0:
                                   checkStateVal = '（修改审核中）';
                                   break;
                               case 1:
                                   checkStateVal = '';
                                   break;
                               case 2:
                                   checkStateVal = '（修改失败）';
                                   break;
                               default:
                                   checkStateVal = '(修改未知)';
                           }
                           contentStr +=
                               '<td>' + stateVal + '<span style="color: red">' + checkStateVal
                               + '</span></td>';
                           contentStr +=
                               '<td><button class="btn btn-primary" onclick="editQualification(\''
                               + m.id
                               + '\')">资质管理</button><button class="btn btn-primary" onclick="queryBalance(\''
                               + m.ledgerNo
                               + '\')">余额查询</button><button class="btn btn-primary" onclick="queryState(\''
                               + m.id
                               + '\')">状态查询</button></td></tr>';
                           currContent += contentStr;
                       }
                       userContent.innerHTML += currContent;
                   }
               });
    }
    /******************************易宝商户资质管理编辑/新建************************************/
    function editQualification(ledgerId) {
        location.href = "/manage/qualification/edit?ledgerId=" + ledgerId;
    }
    /******************************易宝商户余额查询************************************/
    function queryBalance(ledgerNo) {
        $.get('/manage/ledger/queryBalance?ledgerNo=' + ledgerNo, function (map) {
            if (eval(map.code) === 1) {
                alert(ledgerNo + " 子账户余额为：￥ " + map.ledgerbalance.split(':')[1] + ' 元');
            } else {
                alert('请求错误，错误码：' + map.code + '(' + map.msg + ')');
            }
        });
    }
    /******************************易宝商户状态查询************************************/
    function queryState(ledgerId) {
        $.get('/manage/ledger/queryCheckRecord?ledgerId=' + ledgerId, function (map) {
            if (eval(map.code) === 1) {
                if ("SUCCESS" == map.status) {
                    alert('通过审核');
                } else {
                    alert('审核未通过！，失败原因：' + map.reason);
                }
            } else {
                alert('请求错误，错误码：' + map.code + '(' + map.msg + ')');
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
        var costSide = $("#costSide").val();
        if (costSide != null && costSide != -1) {
            criteria.costSide = costSide;
        } else {
            criteria.costSide = null;
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

