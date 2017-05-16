<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/7/21
  Time: 上午10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <style>
        table tr td img, form img {
            width: 80px;
            height: 80px;
        }

        .table > tbody > tr > td, .table > thead > tr > th {
            line-height: 40px;
            text-align: center
        }

        .table > tbody > tr > td, .table > thead > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
            line-height: 60px;
        }
    </style>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script src="${resourceUrl}/js/daterangepicker.js"></script>
    <script src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script src="${resourceUrl}/js/moment.min.js"></script>
    <script src="${resourceUrl}/js/jquery.page.js"></script>


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
                <a type="button" class="btn btn-primary btn-create" style="margin:10px;" href="/manage/partner/edit"/>新建天使合伙人</a>
                <a type="button" class="btn btn-primary btn-create" style="margin:10px;"
                   href="/manage/cityPartner/edit"/>新建城市合伙人</a>
                <hr>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#lunbotu" data-toggle="tab">天使合伙人</a></li>
                    <li class="active"><a href="#xiangqing" data-toggle="tab">城市合伙人</a></li>
                </ul>
                <div id="myTabContent" class="tab-content" style="margin-top: 10px">
                    <div class="tab-pane fade in active" id="lunbotu">

                        <div class="form-group col-md-6">
                            <label for="date-end">注册时间</label>

                            <div id="date-end" class="form-control">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                <span id="searchDateRange"></span>
                                <b class="caret"></b>
                            </div>
                        </div>

                        <div class="form-group col-md-3">
                            <label for="partnerRegister">注册来源</label>
                            <select id="partnerRegister" class="form-control">
                                <option value="">所有来源</option>
                                <option value="1">公众号注册</option>
                                <option value="0">后台添加</option>
                            </select>
                        </div>


                        <div class="form-group col-md-3">
                            <label for="partnerName">姓名</label>
                            <input type="text" class="form-control" id="partnerName"
                                   placeholder="合伙人姓名">
                        </div>

                        <div class="form-group col-md-3">
                            <label for="partnerPhoneNumber">电话</label>
                            <input type="text" class="form-control" id="partnerPhoneNumber"
                                   placeholder="合伙人电话">
                        </div>

                        <div class="form-group col-md-3">
                            <button class="btn btn-primary" style="margin-top: 24px"
                                    onclick="getPartnerByCriteria()">查询
                            </button>
                        </div>

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>合伙人ID</th>
                                <th>合伙人姓名</th>
                                <th>联系电话</th>
                                <th>绑定店铺</th>
                                <th>绑定会员</th>
                                <th>佣金金额</th>
                                <th>累计佣金收入</th>
                                <th>线上佣金收入</th>
                                <th>线下佣金收入</th>
                                <th>注册来源</th>
                                <th>注册时间</th>
                                <th>对应会员ID</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="partnerContent">
                            <%--<c:forEach items="${partners}" var="partner">--%>
                            <%--<tr>--%>
                            <%--<td>${partner[0]}</td>--%>
                            <%--<td>${partner[6]}</td>--%>
                            <%--<td>${partner[7]}</td>--%>
                            <%--<td>${partner[10]}/${partner[3]}</td>--%>
                            <%--<td>${partner[9]}/${partner[2]}</td>--%>
                            <%--<td>￥${partner[4]/100.0}</td>--%>
                            <%--<td>￥${partner[5]/100.0}</td>--%>
                            <%--<td>--%>
                            <%--<button type="button" class="btn btn-default zhWarn"--%>
                            <%--onclick="userEdit('${partner[0]}','${partner[1]}','${partner[8]}')">账号--%>
                            <%--</button>--%>
                            <%--<button type="button" class="btn btn-default"--%>
                            <%--onclick="editPartner('${partner[0]}')">编辑--%>
                            <%--</button>--%>
                            <%--</td>--%>
                            <%--</tr>--%>
                            <%--</c:forEach>--%>
                            </tbody>
                        </table>
                        <div class="tcdPageCode" id="tcdPageCode" style="display: inline;">
                        </div>
                        <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                        <button class="btn btn-primary pull-right" style="margin-top: 5px"
                                onclick="exportPartnerExcel()">导出excel
                        </button>
                    </div>
                    <div class="tab-pane fade" id="xiangqing">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th class="text-center">城市合伙人ID</th>
                                <th class="text-center">城市合伙人名称</th>
                                <th class="text-center">账户余额</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${partnerManagers}" var="partnerManager">
                                <tr>
                                    <td class="text-center">${partnerManager.partnerManager.id}</td>
                                    <td class="text-center">${partnerManager.partnerManager.name}</td>
                                    <td class="text-center">￥${partnerManager.availableBalance/100}</td>
                                        <%--<td class="text-center">--%>
                                        <%--<button type="button" class="btn btn-default editWarn">编辑</button>--%>
                                        <%--&lt;%&ndash;<button type="button" class="btn btn-default deleteWarn" data-target="#deleteWarn">删除</button>&ndash;%&gt;--%>
                                        <%--</td>--%>
                                    <td>
                                        <button type="button" class="btn btn-default"
                                                onclick="cityUserEdit(${partnerManager.partnerManager.partnerId})">账号
                                        </button>
                                        <button type="button" class="btn btn-default"
                                                onclick="editCityPartner(${partnerManager.partnerManager.id})">编辑
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
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
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">删除</h4>
            </div>
            <div class="modal-body">
                <h4>确定要删除：唐古拉黑糖玛？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
            </div>
        </div>
    </div>
</div>
<!--添加提示框-->
<div class="modal" id="editWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">上架</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">合伙人管理员名称</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
            </div>
        </div>
    </div>
</div>
<!--添加提示框-->
<div class="modal" id="zhWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">账号</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">账号</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="user">
                            <input type="hidden" id="partnerId">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="password">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="confirm" onclick="confirmUserEdit()">确认</button>
            </div>
        </div>
    </div>
</div>

<div class="modal" id="cityZhangWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">账号</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">账号</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="cityPartnerName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="cityPartnerPwd">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="cityConfirm">确认</button>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    var partnerCriteria = {};
    var partnerContent = document.getElementById("partnerContent");
    var flag = true;
    var init = 0;


    $(function () {
//        tab切换
        $('#myTab li:eq(0) a').tab('show');
//        提示框
//    $(".deleteWarn").click(function(){
//      $("#deleteWarn").modal("toggle");
//    });
//    $(".editWarn").click(function(){
//      $("#editWarn").modal("toggle");
//    });
//    $(".zhWarn").click(function(){
//      $("#zhWarn").modal("toggle");
//    });
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
        });
        getPartnerByCriteria();

    })

    function getPartnerByCriteria() {
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            partnerCriteria.registerStartDate = startDate;
            partnerCriteria.registerEndDate = endDate;
        }

        if ($("#partnerRegister").val() != "") {
            partnerCriteria.origin = $("#partnerRegister").val();
        } else {
            partnerCriteria.origin = null;
        }

        if ($("#partnerName").val() != "") {
            partnerCriteria.name = $("#partnerName").val();
        } else {
            partnerCriteria.name = null;
        }

        if ($("#partnerPhoneNumber").val() != "") {
            partnerCriteria.phoneNumber = $("#partnerPhoneNumber").val();
        } else {
            partnerCriteria.phoneNumber = null;
        }

        partnerCriteria.offset = 1;

        getPartnerByAjax(partnerCriteria);
    }

    function getPartnerByAjax() {
        partnerContent.innerHTML = "";

        $.ajax({
            type: "post",
            url: "/manage/partner/getPartnerByAjax",
            async: false,
            data: JSON.stringify(partnerCriteria),
            contentType: "application/json",
            success: function (data) {
                var page = data.data.page;
                var partnerBindMerchantCountList=data.data.partnerBindMerchantCountList;
                var partnerBindUserCountList=data.data.partnerBindUserCountList;
                var partnerWalletAvailableBalanceList=data.data.partnerWalletAvailableBalanceList;
                var partnerWalletTotalMoneyList=data.data.partnerWalletTotalMoneyList;
                var partnerWalletOnlineAvailableBalanceList=data.data.partnerWalletOnlineAvailableBalanceList;
                var partnerWalletOnlineTotalMoneyList=data.data.partnerWalletOnlineTotalMoneyList;
                var leJiaUserSidList=data.data.leJiaUserSidList;

                var content = page.content;
                var totalPage = page.totalPages;
                $("#totalElements").html(page.totalElements);
                if (totalPage == 0) {
                    totalPage = 1;
                }
                if (flag) {
                    flag = false;
                    initPage(partnerCriteria.offset, totalPage);
                }
                if (init) {
                    initPage(1, totalPage);
                }
                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr><td>' + content[i].partnerSid + '</td>';
                    contentStr+='<td>' + content[i].name + '</td>';
                    contentStr+='<td>' + content[i].phoneNumber + '</td>';
                    contentStr+='<td>' + partnerBindMerchantCountList[i] +'/'+content[i].merchantLimit+'</td>';
                    contentStr+='<td>' + partnerBindUserCountList[i] +'/'+content[i].userLimit+'</td>';
                    contentStr+='<td>' + (partnerWalletAvailableBalanceList[i]+partnerWalletOnlineAvailableBalanceList[i])/100.0 + '</td>';
                    contentStr+='<td>' + (partnerWalletTotalMoneyList[i]+partnerWalletOnlineTotalMoneyList[i])/100.0 + '</td>';
                    contentStr+='<td>' + partnerWalletOnlineTotalMoneyList[i]/100.0 + '</td>';
                    contentStr+='<td>' + partnerWalletTotalMoneyList[i]/100.0 + '</td>';


                    if(content[i].origin==0){
                        contentStr+='<td>后台添加</td>';
                    }else if(content[i].origin==1){
                        contentStr+='<td>公众号注册</td>';
                    }else {
                        contentStr+='<td>--</td>';
                    }
                    contentStr +=
                        '<td><span>'
                        + new Date(content[i].registerDate).format('yyyy-MM-dd HH:mm:ss')
                        + '</span></td>';


                    if(leJiaUserSidList[i]!=null){
                            contentStr+='<td>' + leJiaUserSidList[i] + '</td>';

                    }else {
                        contentStr+='<td>--</td>';
                    }

                    contentStr +=
                        '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                        + '"><button class="btn btn-primary btn-sm userEdit">账号</button><button  class="btn btn-primary btn-sm editPartner">编辑</button>' +
//                        '<button class="btn btn-primary btn-sm partnerWalletLog">详情</button>' +
                        '</td>';
                    partnerContent.innerHTML += contentStr;
                }

                $(".userEdit").each(function (i) {
                    $(".userEdit").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        $.ajax({
                            type: "get",
                            url: "/manage/partner/editPartnerPage/" + id,
                            contentType: "application/json",
                            success: function (data) {
                                var partner=data.data;
                                var name=partner.name;
                                var password=partner.password;
                                userEdit(id,name,password);
                            }
                        });

                    });
                });



                $(".editPartner").each(function (i) {
                    $(".editPartner").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        editPartner(id);
                    });
                });



            }
        })


    }

    function initPage(page, totalPage) {
        $('#tcdPageCode').unbind();
        $("#tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                partnerCriteria.offset = p;
                init = 0;
                getPartnerByAjax(partnerCriteria);
            }
        });
    }




    function exportPartnerExcel() {
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            partnerCriteria.registerStartDate = startDate;
            partnerCriteria.registerEndDate = endDate;
        }

        if ($("#partnerRegister").val() != "") {
            partnerCriteria.origin = $("#partnerRegister").val();
        } else {
            partnerCriteria.origin = null;
        }

        if ($("#partnerName").val() != "") {
            partnerCriteria.name = $("#partnerName").val();
        } else {
            partnerCriteria.name = null;
        }

        if ($("#partnerPhoneNumber").val() != "") {
            partnerCriteria.phoneNumber = $("#partnerPhoneNumber").val();
        } else {
            partnerCriteria.phoneNumber = null;
        }
        partnerCriteria.offset = 1;
        post("/manage/partner/exportExcel", partnerCriteria);
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
    function  userEdit(id,name, password) {
        $("#partnerId").val(id);
        $("#user").val(name);
        $("#password").val(password);
        $("#zhWarn").modal("toggle");
    }
    function confirmUserEdit(){
        var partner = {};
        partner.id =$("#partnerId").val();
        partner.name = $("#user").val();
        partner.password = $("#password").val();
        $("#confirm").unbind("tap");
        $.ajax({
            type: "post",
            data: JSON.stringify(partner),
            contentType: "application/json",
            url: "/manage/partner/editUser",
            success: function (data) {
                $("#user").val("");
                $("#password").val("");
                $("#partnerId").val("")
                alert(data.msg);
                //return data.msg;
            }
        });
    }
    function editPartner(id) {
        location.href = "/manage/partner/edit?id=" + id;
    }

    function cityUserEdit(id) {
        $.ajax({
            type: 'GET',
            url: '/manage/partner/manager?id=' + id,
            async: false,
            dataType: 'json',
            success: function (account) {
                $("#cityPartnerName").val(account.data.name);
                $("#cityPartnerPwd").val(account.data.password);
                $("#cityZhangWarn").modal("toggle");
            }
        });
    }

    function editCityPartner(id) {
        location.href = "/manage/cityPartner/edit?id=" + id;
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
</script>
</body>
</html>

