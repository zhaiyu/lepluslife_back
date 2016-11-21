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
                        <input type="text" id="link-man" class="form-control" placeholder="商户联系人" />
                    </div>
                    <div class="form-group col-md-2">
                        <label for="phone-num">绑定手机号</label>
                        <input type="number" id="phone-num" class="form-control" placeholder="绑定手机号" />
                    </div>
                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px" onclick="searchMerchantUserByCriteria()">筛选</button>
                    </div>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li class="w i"><a href="#ing" data-toggle="tab">商户管理</a></li>
                    <%--<li class="w s"><a href="#stop" data-toggle="tab">门店管理</a></li>--%>
                </ul>
                <div style="text-align: right;margin: 10px 0 0 0">
                    <button class="btn btn-primary">导出表格</button>
                    <button class="btn btn-primary createWarn">新建商户</button>
                </div>
                <div id="myTabContent" class="tab-content" style="margin: 10px 0 0 0">
                    <div class="tab-pane fade in active" id="ing">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>商户ID</th><th>所属城市</th><th>商户名称</th><th>商户联系人</th><th>商户绑定手机号</th><th>名下门店</th>
                                <th>创建时间</th><th>锁定会员</th><th>佣金余额</th><th>累计佣金收入</th><th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="tablePage">
                            <%--<tr>
                                <td>25441</td><td>北京</td><td>棉花糖KTV</td><td>曹广言</td>
                                <td>13513988158</td><td>4（佣金3  非佣金1）</td><td><span>2016.6.5  14:25</span></td>
                                <td>3422</td><td>¥368</td><td>¥368</td>
                                <td>
                                    <button type="button" class="btn btn-default createLocation">门店管理</button>
                                    <button type="button" class="btn btn-default createLocation">账号管理</button>
                                    <button type="button" class="btn btn-default deleteWarn" data-target="#deleteWarn">编辑</button>
                                </td>
                            </tr>--%>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade in active" id="stop">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>活动ID</th><th>活动标题</th><th>活动商户</th><th>已立减金额</th><th>交易次数</th><th>创建时间</th><th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>25441</td><td>微信支付满10元立减</td><td>35家</td><td>5890</td>
                                <td>30</td><td>2016.6.5 14:25</td>
                                <td>
                                    <button type="button" class="btn btn-default createLocation">查看立减交易记录</button>
                                    <!--<button type="button" class="btn btn-default createLocation rvCodeWorn" data-target="#rvCodeWorn">二维码</button>-->
                                    <button type="button" class="btn btn-default createLocation">编辑</button>
                                    <button type="button" class="btn btn-default deleteWarn" data-target="#deleteWarn">再次开启</button>
                                </td>
                            </tr>
                            <tr>
                                <td>25441</td><td>微信支付满10元立减</td><td>35家</td><td>5890</td>
                                <td>30</td><td>2016.6.5 14:25</td>
                                <td>
                                    <button type="button" class="btn btn-default createLocation">查看立减交易记录</button>
                                    <!--<button type="button" class="btn btn-default createLocation rvCodeWorn" data-target="#rvCodeWorn">二维码</button>-->
                                    <button type="button" class="btn btn-default createLocation">编辑</button>
                                    <button type="button" class="btn btn-default deleteWarn" data-target="#deleteWarn">再次开启</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <%--<nav class="pull-right">--%>
                    <%--<ul class="pagination pagination-lg">--%>
                        <%--<li><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>--%>
                        <%--<li><a href="#">1</a></li>--%>
                        <%--<li><a href="#">2</a></li>--%>
                        <%--<li><a href="#">3</a></li>--%>
                        <%--<li><a href="#">4</a></li>--%>
                        <%--<li><a href="#">5</a></li>--%>
                        <%--<li><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>--%>
                    <%--</ul>--%>
                <%--</nav>--%>
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
<!--停用提示框-->
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">暂停</h4>
            </div>
            <div class="modal-body">
                <h4>确定要停用活动？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
            </div>
        </div>
    </div>
</div>
<!--添加提示框-->
<div class="modal" id="createWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">新建商户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="toggle-name" class="col-sm-3 control-label">商户名称</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="toggle-name" placeholder="商户名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="toggle-people" class="col-sm-3 control-label">商户负责人</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="toggle-people" placeholder="商户负责人">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="toggle-usrname" class="col-sm-3 control-label">用户名</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="toggle-usrname" placeholder="请输入登录用户名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="toggle-passwd" class="col-sm-3 control-label">登录密码</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="toggle-passwd" placeholder="请输入用户密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="toggle-phone" class="col-sm-3 control-label">绑定手机号</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="toggle-phone" placeholder="绑定手机号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="toggle-card" class="col-sm-3 control-label">佣金结算卡号</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="toggle-card" placeholder="佣金结算卡号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="toggle-bank" class="col-sm-3 control-label">开户行</label>
                        <div class="col-sm-8">
                            <select class="form-control" style="margin: 0 0 10px 0" name="" id="selBankName">
                                <option value=""> -- 请选择 --</option>
                                <option value="1">中国农业银行</option>
                                <option value="2">中国工商银行</option>
                                <option value="3">中国银行</option>
                                <option value="4">交通银行</option>
                                <option value="5">中信银行</option>
                                <option value="6">中国光大银行</option>
                                <option value="7">华夏银行</option>
                                <option value="8">中国民生银行</option>
                                <option value="9">广发银行股份有限公司</option>
                                <option value="10">中国邮政储蓄银行</option>
                                <option value="11">平安银行</option>
                                <option value="12">兴业银行</option>
                                <option value="13">上海浦东发展银行</option>
                                <option value="14">中国建设银行</option>
                                <option value="15">鞍山市商业银行</option>
                                <option value="16">营口银行</option>
                                <option value="17">农村信用社</option>
                                <option value="18">锦州银行</option>
                                <option value="19">北京农村商业银行</option>
                                <option value="20">吉林银行</option>
                                <option value="21">盛京银行</option>
                                <option value="22">大连银行</option>
                            </select>
                            <input type="text" class="form-control" id="toggle-bank" placeholder="开户行">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="toggle-bank" class="col-sm-3 control-label">所在城市</label>
                        <div class="col-sm-8">
                            <select class="form-control" style="margin: 0 0 10px 0" name="" id="selCity">
                                <option value=""> -- 请选择 --</option>
                                <c:forEach items="${citys}" var="city">
                                    <option value="${city.id}">${city.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="toggle-limit" class="col-sm-3 control-label">会员锁定上限</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="toggle-limit" placeholder="会员锁定上限">
                        </div>
                        <div style="text-align: right;margin-right: 11%">
                            <p>365/500</p>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="ceateMerchantUser()">确认</button>
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
//        提示框
        $(".deleteWarn").click(function(){
            $("#deleteWarn").modal("toggle");
        });
        $(".rvCodeWorn").click(function(){
            $("#rvCodeWorn").modal("toggle");
        });
        $(".createWarn").click(function(){
            $("#createWarn").modal("toggle");
        });

    });

    $(".savePic").click(function() {
        html2canvas($(".modal-body .main"), {
            allowTaint: true,
            taintTest: false,
            onrendered: function (canvas) {
                canvas.id = "mycanvas";
                var dataUrl = canvas.toDataURL();
                console.log(dataUrl);
                $("#myShowImage").attr("src", dataUrl).css({'display': 'block'});
                $(".main").css({'display': 'none'});
            }
        });
    })
</script>
<script>
    var merchantUserCriteria = {};
    merchantUserCriteria.offset = 1;
    var tableContent = document.getElementById("tablePage");
    //    时间选择器
    $(document).ready(function (){
        $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss') + ' - ' + moment().format('YYYY/MM/DD HH:mm:ss'));
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
    function getMerchantUserByAjax(merchantUserCriteria) {
        tableContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/merchantUser/find_page",
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
                    initPage(merchantUserCriteria.offset, totalPage);
                    for (var i = 0; i < content.length; i++) {
                        console.log(JSON.stringify(content[i]));
                        var contentStr = '<tr><td>' + content[i].id + '</td>';
                        if(content[i].city!=null) {
                            contentStr += '<td>' + content[i].city.name + '</td>';
                        }else{
                            contentStr += '<td></td>';
                        }
                        if(content[i].merchantName!=null) {
                            contentStr += '<td>' + content[i].merchantName + '</td>';
                        }else{
                            contentStr += '<td></td>';
                        }
                        if(content[i].linkMan!=null) {
                            contentStr += '<td>' + content[i].linkMan + '</td>';
                        }else {
                            contentStr += '<td></td>';
                        }
                        if(content[i].phoneNum!=null) {
                            contentStr += '<td>' + content[i].phoneNum + '</td>';
                        }else {
                            contentStr += '<td></td>';
                        }
                        contentStr += '<td> - </td>';
                        if(content[i].createdDate!=null) {
                            contentStr += '<td><h5>' + new Date(content[i].createdDate).format("yyyy-MM-dd") +'</h5><h5'
                                    + new Date(content[i].createdDate).format("HH:mm:ss") +'</h5></td>';
                        }else {
                            contentStr += '<td></td>';
                        }

                        contentStr += '<td> - </td>';
                        contentStr += '<td> - </td>';
                        contentStr += '<td> - </td>';
                        //  操作
                        contentStr+='<td>' +
                                     '<button type="button" class="btn btn-default createLocation">门店管理</button>'+
                                     '<button type="button" class="btn btn-default createLocation">账号管理</button>'+
                                     '<button type="button" class="btn btn-default" onclick="editUser('+content[i].id+')">编辑</button>' +
                                    '</td>';
                        contentStr+="</tr>";
                        tableContent.innerHTML += contentStr;
                    }
                }
        });
    }
    getMerchantUserByAjax(merchantUserCriteria);
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                merchantUserCriteria.offset = p;
                getMerchantUserByAjax(merchantUserCriteria);
            }
        });
    }
    function searchMerchantUserByCriteria() {
        merchantUserCriteria.offset = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            merchantUserCriteria.startDate = startDate;
            merchantUserCriteria.endDate = endDate;
        }
        var city = {};
        city.id = $("#stay-city").val();
        var $phoneNum = $("#phone-num").val();
        var $linkMan = $("#link-man").val();
        if (city.id!="" && city.id!= null) {
            merchantUserCriteria.city = city;
        } else {
            city = null;
        }
        if ($phoneNum!="" && $phoneNum!= null) {
            merchantUserCriteria.phoneNum = $phoneNum;
        } else {
            merchantUserCriteria.phoneNum = null;
        }
        if ($linkMan!="" && $linkMan!= null) {
            merchantUserCriteria.linkMan = $linkMan;
        } else {
            merchantUserCriteria.linkMan = null;
        }
        getMerchantUserByAjax(merchantUserCriteria);
    }

    // 选中BankName
    $("#selBankName").bind("change",function(){
            var $selBankName = $("#selBankName");
            if($selBankName.val()!=null && $selBankName.val()!='') {
                $("#toggle-bank").val($selBankName.find("option:selected").text());
            }
    });

    // 校验用户名是否重复
    $("#toggle-usrname").bind("blur",function(){
        var $usrname = $("#toggle-usrname").val();
        if($usrname!=null && $usrname!=''){
            $.ajax({
                url:"/manage/merchantUser/check_username",
                type:"post",
                contentType:"application/json",
                data:$usrname,
                success: function(result) {
                    if(result.status!=200) {
                        alert(result.msg);
                        $("#toggle-usrname").val('');
                    }
                }
            });
        }
    });

    var merchantUserId = null;
    //  编辑时加载信息
    function editUser(id) {
        $.ajax({
            url:"/manage/merchantUser/edit/"+id,
            type:"get",
            contentType:"application/json",
            success: function(result) {
                var merchantUser = result.data;
                resetAll()
                merchantUserId = merchantUser.id;
                if(merchantUser.merchantName!=null)
                $("#toggle-name").val(merchantUser.merchantName);
                if(merchantUser.linkMan!=null)
                $("#toggle-people").val(merchantUser.linkMan);
                if(merchantUser.cardNum!=null)
                $("#toggle-card").val(merchantUser.cardNum);
                if(merchantUser.phoneNum!=null)
                $("#toggle-phone").val(merchantUser.phoneNum);
                if(merchantUser.bankName!=null)
                $("#toggle-bank").val(merchantUser.bankName);
                if(merchantUser.lockLimit!=null)
                $("#toggle-limit").val(merchantUser.lockLimit);
                if(merchantUser.name!=null)
                $("#toggle-usrname").val(merchantUser.name);
                if(merchantUser.password!=null)
                $("#toggle-passwd").val(merchantUser.password);
                if(merchantUser.city!=null)
                $("#selCity").val(merchantUser.city.id);
                $("#createWarn").modal("toggle");
            }
        });
    }


    //  保存商户
    function ceateMerchantUser() {
        var merchantUser = {};
        var merchantName = $("#toggle-name").val();
        var linkMan = $("#toggle-people").val();
        var phoneNum = $("#toggle-phone").val();
        var cardNum = $("#toggle-card").val();
        var bankName = $("#toggle-bank").val();
        var lockLimit = $("#toggle-limit").val();
        var name = $("#toggle-usrname").val();
        var password = $("#toggle-passwd").val();
        var selCity = $("#selCity").val();
        var city = {};
        city.id = selCity;

        if(merchantName==null || merchantName=='') {
            alert("请输入商户名称");
            return;
        }
        if(linkMan==null || linkMan=='') {
            alert("请输入商户负责人");
            return;
        }
        if(phoneNum==null || phoneNum=='') {
            alert("请输入绑定手机号");
            return;
        }
        if(name==null || name=='') {
            alert("请输入登录用户名");
            return;
        }
        if(password==null || password==''||password.length<6||password.length>12) {
            if(merchantUserId==null) {
                alert("请输入登录密码（6-12）位");
                return;
            }
        }
        if(cardNum==null || cardNum=='') {
            alert("请输入佣金结算卡号");
            return;
        }
        if(bankName==null || bankName=='') {
            alert("请选择开户行");
            return;
        }
        if(lockLimit==null || lockLimit==''||lockLimit<0 || lockLimit>1000) {
            alert("请输入具体的会员锁定上限");
            return;
        }
        if(selCity==null || selCity=='') {
            alert("请选中所在城市");
            return;
        }
        merchantUser.id = merchantUserId;
        merchantUser.merchantName = merchantName;
        merchantUser.linkMan = linkMan;
        merchantUser.name = name;
        merchantUser.password = password;
        merchantUser.phoneNum = phoneNum;
        merchantUser.cardNum = cardNum;
        merchantUser.merchantName = merchantName;
        merchantUser.bankName = bankName;
        merchantUser.lockLimit = lockLimit;
        merchantUser.city = city;
        //  修改商户信息
        if(merchantUser.id!=null && merchantUser.id!='') {
            sendPutAjax("/manage/merchantUser/edit",merchantUser);
        //  保存商户信息
        }else {
            sendPostAjax("/manage/merchantUser/create",merchantUser);
        }
        $("#createWarn").modal("toggle");
        resetAll();
        window.location.href = "/manage/merchantUser/list";
    }

    function sendPostAjax(url,data) {
        $.ajax({
            url:url,
            type:"post",
            contentType:"application/json",
            data:JSON.stringify(data),
            success: function(result) {
                alert(result.data);
            }
         });
    }

    function sendPutAjax(url,data) {
        $.ajax({
            url:url,
            type:"put",
            contentType:"application/json",
            data:JSON.stringify(data),
            success: function(result) {
                alert(result.data);
            }
        });
    }

    function resetAll() {
        $("#toggle-name").val('');
        $("#toggle-people").val('');
        $("#toggle-phone").val('');
        $("#toggle-card").val('');
        $("#toggle-bank").val('');
        $("#toggle-limit").val('');
        $("#selBankName").val('');
        merchantUserId = null;
    }


</script>
<script>
    $(".w").click(function (e) {
        if($(this).hasClass("active")){
            $(".modal-title").html("暂停");
            $(".modal-body > h4").html("确定要停用活动？");
        }else {
            $(".modal-title").html("开启");
            $(".modal-body > h4").html("确定要再次开启活动？");
        }
    });

</script>
</body>
</html>
