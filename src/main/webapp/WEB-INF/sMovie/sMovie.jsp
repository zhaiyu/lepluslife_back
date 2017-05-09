<%--
  Created by IntelliJ IDEA.
  User: lss
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
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
    <script type="text/javascript" src="${resourceUrl}/js/jquery.combo.select.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
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
                <hr>
                <ul id="myTab" class="nav nav-tabs">
                    <li class="active"><a href="#sMovieOrder" data-toggle="tab">电影订单</a></li>
                    <li><a href="#popularMovie" data-toggle="tab" onclick="getSMovieProductByCriteria()">热门电影</a>
                    </li>
                    <li><a href="#banner" data-toggle="tab" onclick="getBannerByCriteria()">轮播图</a>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content" style="margin-top: 10px">
                    <div class="tab-pane fade in active" id="sMovieOrder">
                        <div class="form-group col-md-6">
                            <label for="date-end">下单时间</label>

                            <div id="date-end" class="form-control">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                <span id="searchDateRange"></span>
                                <b class="caret"></b>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="date-end2">支付完成时间</label>

                            <div id="date-end2" class="form-control">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                <span id="searchDateRange2"></span>
                                <b class="caret"></b>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="date-end3">核销时间</label>

                            <div id="date-end3" class="form-control">
                                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                <span id="searchDateRange3"></span>
                                <b class="caret"></b>
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="userId">会员ID</label>
                            <input type="text" class="form-control" id="userId"
                                   placeholder="请输入会员ID">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="userPhoneNumber">会员手机号</label>
                            <input type="text" class="form-control" id="userPhoneNumber"
                                   placeholder="会员手机号">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="movieName">核销影院</label>
                            <select id="movieName" class="hhr">
                                <option value="nullValue">--暂无--</option>
                                <c:forEach items="${sMovieTerminals}" var="sMovieTerminal">
                                    <option value="${sMovieTerminal.id}">${sMovieTerminal.movieName}</option>
                                </c:forEach>
                            </select>
                        </div>


                        <div class="form-group col-md-3">
                            <label for="sMovieOrderSid">订单编号</label>
                            <input type="text" class="form-control" id="sMovieOrderSid"
                                   placeholder="订单编号">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="sMovieOrderState">订单状态</label>
                            <select class="form-control" id="sMovieOrderState">
                                <option value="nullValue">全部状态</option>
                                <option value="0">待付款</option>
                                <option value="1">已付款待核销</option>
                                <option value="2">已付款已核销</option>
                                <option value="3">已退款</option>
                            </select>
                        </div>
                        <div class="form-group col-md-3">
                            <button class="btn btn-primary" style="margin-top: 24px"
                                    onclick="getSMovieByCriteria()">查询
                            </button>
                        </div>

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th class="text-center">订单编号</th>
                                <th class="text-center">会员ID</th>
                                <th class="text-center">会员手机号</th>
                                <th class="text-center">购买产品</th>
                                <th class="text-center">订单金额</th>
                                <th class="text-center">使用金币</th>
                                <th class="text-center">实际支付</th>
                                <th class="text-center">红包奖励</th>
                                <th class="text-center">下单时间</th>
                                <th class="text-center">支付时间</th>
                                <th class="text-center">核销时间</th>
                                <th class="text-center">核销影院</th>
                                <th class="text-center">状态</th>
                            </tr>
                            </thead>
                            <tbody id="sMovieOrderContent">
                            </tbody>
                        </table>
                        <div class="tcdPageCode" id="tcdPageCode" style="display: inline;">
                        </div>
                        <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                        <button class="btn btn-primary pull-right" style="margin-top: 5px"
                                onclick="exportExcel()">导出excel
                        </button>
                    </div>
                    <div class="tab-pane fade" id="popularMovie">
                        <button class="btn btn-primary pull-right" style="margin-top: 5px" onclick="addMovie()">
                            新增
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th class="text-center">ID</th>
                                <th class="text-center">序号</th>
                                <th class="text-center">电影图片</th>
                                <th class="text-center">状态</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody id="sMovieProductContent">
                            </tbody>
                        </table>
                        <div class="tcdPageCode" id="tcdPageCode1" style="display: inline;">
                        </div>
                        <div style="display: inline;"> 共有 <span id="totalElements1"></span> 个</div>
                    </div>
                    <div class="tab-pane fade" id="banner">
                        <button class="btn btn-primary pull-right" style="margin-top: 5px" onclick="addBanner()">
                            新增首页轮播图
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th class="text-center">序号</th>
                                <th class="text-center">电影图片</th>
                                <th class="text-center">后置类型</th>
                                <th class="text-center">状态</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody id="bannerContent">
                            </tbody>
                        </table>
                        <div class="tcdPageCode" id="tcdPageCode2" style="display: inline;">
                        </div>
                        <div style="display: inline;"> 共有 <span id="totalElements2"></span> 个</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--添加热门电影-->
<div class="modal" id="addMovie">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon">序号</span>
                    <input type="text" class="form-control" id="sMovieSid">
                </div>
                　
                <br>

                <div class="input-group input-group-lg">
                    <div>
                        <img style="width: 100%" alt="..." id="picture">
                        <input type="file" class="form-control" id="popularMoviePicture" name="file"
                               data-url="/manage/file/saveImage"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        onclick="addPopularMovie()">确认
                </button>
            </div>
        </div>
    </div>
</div>

<!--编辑热门电影-->
<div class="modal" id="editMovie">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon">序号</span>
                    <input type="text" class="form-control" id="sMovieSid1">
                </div>
                　
                <br>

                <div class="form-group">
                    <label for="popularMoviePicture1"
                           class="col-sm-3 control-label">图片(宽高比:710*230)</label>

                    <div class="col-sm-6">
                        <!--<div class="thumbnail">-->
                        <img style="width: 100%" src="" alt="..." id="picture1">
                        <!--</div>-->
                        <input type="file" class="form-control" id="popularMoviePicture1" name="file"
                               data-url="/manage/file/saveImage"/>
                        <input type="hidden" id="sMovieProductId">
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        onclick="editPopularMovie()">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--添加轮播图-->
<div class="modal" id="type1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <input name="bannerId1" type="hidden" class="o-input" value=""/>

                    <div class="form-group">
                        <label for="banneSid" class="col-sm-3 control-label">序号</label>

                        <div class="col-sm-6">
                            <input name="bannerType" type="text" class="form-control o-input"
                                   id="banneSid">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="bannerPicture"
                               class="col-sm-3 control-label">图片(宽高比:710*230)</label>

                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="picture3">
                            <!--</div>-->
                            <input type="file" class="form-control" id="bannerPicture" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="afterType1"
                               class="col-sm-3 control-label">Banner类型</label>

                        <div class="col-sm-6">
                            <div>
                                <input type="radio" name="radioBannerType" class="checked1" checked="true" id="rdoA1"
                                       value="15"/><span>热门电影列表图</span>
                            </div>
                            <div>
                                <input type="radio" name="radioBannerType" class="checked1" id="rdoA2"
                                       value="16"/><span>电影首页上方轮播图</span>
                            </div>

                        </div>
                    </div>

                    <div class="form-group">
                        <label for="afterType1"
                               class="col-sm-3 control-label">后置类型(点击图片后的跳转情况)</label>

                        <div class="col-sm-6" id="afterType1">
                            <div>
                                <input type="radio" name="type1" class="checked1" checked="true" id="bannerType"
                                       value="1"/><span>H5页面（跳转到一个H5页面）</span>
                                <input class="w-input" id="afterType_val" type="text" placeholder="请输入页面链接地址"/>
                                <input class="w-input" id="urlTitle" type="text" placeholder="请输入网页标题"/>
                            </div>
                            <div>
                                <input type="radio" name="type1" class="checked1"
                                       value="4"/><span>普通图片</span>
                            </div>

                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit1">确认
                </button>
            </div>
        </div>
    </div>
</div>

<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var sMovieOrderCriteria = {};
    var sMovieOrderContent = document.getElementById("sMovieOrderContent");
    var flag = true;
    var init = 0;

    var sMovieProductCriteria = {};
    var sMovieProductContent = document.getElementById("sMovieProductContent");
    var flag1 = true;
    var init1 = 0;

    var bannerCriteria = {};
    var bannerContent = document.getElementById("bannerContent");
    var flag2 = true;
    var init2 = 0;


    $(function () {
        $('#myTab li:eq(0) a').tab('show');
        $(".hhr").comboSelect();
        getSMovieByCriteria();

        $('#popularMoviePicture').fileupload({
            dataType: 'json',
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
            add: function (e, data) {
                data.submit();
            },
            done: function (e, data) {
                var resp = data.result;
                $('#picture').attr('src',
                        '${ossImageReadRoot}/'
                        + resp.data);
            }
        });
        $('#popularMoviePicture1').fileupload({
            dataType: 'json',
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
            add: function (e, data) {
                data.submit();
            },
            done: function (e, data) {
                var resp = data.result;
                $('#picture1').attr('src',
                        '${ossImageReadRoot}/'
                        + resp.data);
            }
        });


        $('#bannerPicture').fileupload({
            dataType: 'json',
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
            add: function (e, data) {
                data.submit();
            },
            done: function (e, data) {
                var resp = data.result;
                $('#picture3').attr('src',
                        '${ossImageReadRoot}/'
                        + resp.data);
            }
        });
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
            $('#date-end2').daterangepicker({
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
                $('#date-end2 span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                + end.format('YYYY/MM/DD HH:mm:ss'));
            });

            $('#date-end3').daterangepicker({
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
                $('#date-end3 span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                + end.format('YYYY/MM/DD HH:mm:ss'));
            });

        })


    })

    function getSMovieByCriteria() {
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            sMovieOrderCriteria.createdStartDate = startDate;
            sMovieOrderCriteria.createdEndDate = endDate;
        }
        var dateStr2 = $('#date-end2 span').text().split("-");
        if (dateStr2 != null && dateStr2 != '') {
            var startDate2 = dateStr2[0].replace(/-/g, "/");
            var endDate2 = dateStr2[1].replace(/-/g, "/");
            sMovieOrderCriteria.completedStartDate = startDate2;
            sMovieOrderCriteria.completedEndDate = endDate2;
        }
        var dateStr3 = $('#date-end3 span').text().split("-");
        if (dateStr3 != null && dateStr3 != '') {
            var startDate3 = dateStr3[0].replace(/-/g, "/");
            var endDate3 = dateStr3[1].replace(/-/g, "/");
            sMovieOrderCriteria.dateUsedStartDate = startDate3;
            sMovieOrderCriteria.dateUsedEndDate = endDate3;
        }

        if ($("#userId").val() != "") {
            sMovieOrderCriteria.userSid = $("#userId").val();
        } else {
            sMovieOrderCriteria.userSid = null;
        }
        if ($("#userPhoneNumber").val() != "") {
            sMovieOrderCriteria.phoneNumber = $("#userPhoneNumber").val();
        } else {
            sMovieOrderCriteria.phoneNumber = null;
        }
        if ($("#movieName").val() != "nullValue") {
            sMovieOrderCriteria.sMovieTerminalId = $("#movieName").val();
        } else {
            sMovieOrderCriteria.sMovieTerminalId = null;
        }
        if ($("#sMovieOrderSid").val() != "") {
            sMovieOrderCriteria.orderSid = $("#sMovieOrderSid").val();
        } else {
            sMovieOrderCriteria.orderSid = null;
        }
        if ($("#sMovieOrderState").val() != "nullValue") {
            sMovieOrderCriteria.state = $("#sMovieOrderState").val();
        } else {
            sMovieOrderCriteria.state = null;
        }


        sMovieOrderCriteria.offset = 1;
        getSMovieOrderByAjax(sMovieOrderCriteria);
    }
    function getSMovieProductByCriteria() {
        sMovieProductCriteria.offset = 1;
        sMovieProductCriteria.state = null;
        getSMovieProductByAjax(sMovieProductCriteria);
    }

    function getBannerByCriteria() {
        bannerCriteria.type = 15;
        bannerCriteria.status = null;
        bannerCriteria.offset = 1;
        getSMovieBannerByAjax(bannerCriteria);
    }

    function getSMovieOrderByAjax(sMovieOrderCriteria) {
        sMovieOrderContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/sMovieOrder/getSMovieOrderByAjax",
            async: false,
            data: JSON.stringify(sMovieOrderCriteria),
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
                    initPage(sMovieOrderCriteria.offset, totalPage);
                }
                if (init) {
                    initPage(1, totalPage);
                }
                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr><td>' + content[i].orderSid + '</td>';
                    if (content[i].leJiaUser != null) {
                        contentStr +=
                                '<td>' + content[i].leJiaUser.userSid + '</td>';

                        contentStr +=
                                '<td>' + content[i].leJiaUser.phoneNumber + '</td>';
                    } else {
                        contentStr +=
                                '<td>--</td>';

                        contentStr +=
                                '<td>--</td>';
                    }


                    if (content[i].sMovieProduct != null) {
                        contentStr +=
                                '<td>' + content[i].sMovieProduct.name + '</td>';
                    } else {
                        contentStr +=
                                '<td>--</td>';
                    }
                    contentStr +=
                            '<td>' + content[i].totalPrice / 100 + '</td>';

                    contentStr +=
                            '<td>' + content[i].trueScore / 100 + '</td>';
                    contentStr +=
                            '<td>' + content[i].truePrice / 100 + '</td>';
                    contentStr +=
                            '<td>' + content[i].payBackA / 100 + '</td>';
                    contentStr +=
                            '<td><span>'
                            + new Date(content[i].dateCreated).format('yyyy-MM-dd HH:mm:ss')
                            + '</span></td>';
                    contentStr +=
                            '<td><span>'
                            + new Date(content[i].dateCompleted).format('yyyy-MM-dd HH:mm:ss')
                            + '</span></td>';
                    contentStr +=
                            '<td><span>'
                            + new Date(content[i].dateUsed).format('yyyy-MM-dd HH:mm:ss')
                            + '</span></td>';

                    if (content[i].sMovieTerminal != null) {
                        contentStr +=
                                '<td>' + content[i].sMovieTerminal.movieName + '</td>';
                    }
                    if (content[i].state == 0) {
                        contentStr +=
                                '<td>待付款</td>';
                    } else if (content[i].state == 1) {
                        contentStr +=
                                '<td>已付款</td>';
                    } else if (content[i].state == 1) {
                        contentStr +=
                                '<td>已付款待核销</td>';
                    } else if (content[i].state == 2) {
                        contentStr +=
                                '<td>已付款已核销</td>';
                    } else if (content[i].state == 3) {
                        contentStr +=
                                '<td>已退款</td>';
                    } else {
                        contentStr +=
                                '<td>--</td>';
                    }
                    sMovieOrderContent.innerHTML += contentStr;
                }
            }
        });
    }
    function getSMovieProductByAjax(sMovieProductCriteria) {
        sMovieProductContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/sMovieProduct/getSMovieProductByAjax",
            async: false,
            data: JSON.stringify(sMovieProductCriteria),
            contentType: "application/json",
            success: function (data) {
                var page = data.data;
                var content = page.content;
                var totalPage = page.totalPages;
                $("#totalElements1").html(page.totalElements);
                if (totalPage == 0) {
                    totalPage = 1;
                }
                if (flag1) {
                    flag1 = false;
                    initPage1(sMovieProductCriteria.offset, totalPage);
                }
                if (init1) {
                    initPage1(1, totalPage);
                }
                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr><td>' + content[i].id + '</td>';
                    contentStr += '<td>' + content[i].sid + '</td>';
                    contentStr +=
                            '<td><img style="height: 100px" src="' + content[i].picture
                            + '" alt="..."></td>';
                    if (content[i].state == 1) {
                        contentStr += '<td>已上架</td>';
                        contentStr +=
                                '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                                + '"><button class="btn btn-primary btn-sm editSMovieProduct">编辑</button><button  class="btn btn-primary btn-sm changeSMovieProductState">下架</button></td>';
                    } else {
                        contentStr += '<td>已下架</td>';
                        contentStr +=
                                '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                                + '"><button class="btn btn-primary btn-sm editSMovieProduct">编辑</button><button  class="btn btn-primary btn-sm changeSMovieProductState">上架</button></td>';
                    }


                    sMovieProductContent.innerHTML += contentStr;
                }
                $(".changeSMovieProductState").each(function (i) {
                    $(".changeSMovieProductState").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        $.ajax({
                            type: "get",
                            url: "/manage//sMovieProduct/changeSMovieProductState/" + id,
                            contentType: "application/json",
                            success: function (data) {
                                getSMovieProductByAjax(sMovieProductCriteria);
                            }
                        });
                    });
                });
                $(".editSMovieProduct").each(function (i) {
                    $(".editSMovieProduct").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        $.ajax({
                            type: "get",
                            url: "/manage//sMovieProduct/editSMovieProduct/" + id,
                            contentType: "application/json",
                            success: function (data) {
                                var sMovieProduct = data.data;
                                $("#sMovieSid1").val(sMovieProduct.sid);
                                $("#picture1").attr("src", sMovieProduct.picture);
                                $("#sMovieProductId").val(sMovieProduct.id);
                                $("#editMovie").modal("toggle");
                            }
                        });

                        $("#deleteWarn").modal("show");
                    });
                });
            }
        })
    }

    function getSMovieBannerByAjax() {
        bannerContent.innerHTML = "";

        $.ajax({
            type: "post",
            url: "/manage/banner/getSMovieBannerByAjax",
            async: false,
            data: JSON.stringify(bannerCriteria),
            contentType: "application/json",
            success: function (data) {
                var page = data.data;
                var content = page.content;
                var totalPage = page.totalPages;
                $("#totalElements2").html(page.totalElements);

                if (totalPage == 0) {
                    totalPage = 1;
                }
                if (flag2) {
                    flag2 = false;
                    initPage2(bannerCriteria.offset, totalPage);
                }
                if (init2) {
                    initPage2(1, totalPage);
                }
                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr><td>' + content[i].sid + '</td>';

                    contentStr +=
                            '<td><img style="height: 100px" src="' + content[i].picture
                            + '" alt="..."></td>';

                    if (content[i].afterType == 1) {
                        contentStr += '<td>链接</td>';
                    } else if (content[i].afterType == 4) {
                        contentStr += '<td>普通图片</td>';
                    } else {
                        contentStr += '<td>--</td>';
                    }

                    if (content[i].status == 1) {
                        contentStr += '<td>已上架</td>';
                        contentStr +=
                                '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                                + '"><button class="btn btn-primary btn-sm editSMovieBanner">编辑</button><button  class="btn btn-primary btn-sm changeSMovieBannerState">下架</button></td>';
                    } else {
                        contentStr += '<td>已下架</td>';
                        contentStr +=
                                '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                                + '"><button class="btn btn-primary btn-sm editSMovieBanner">编辑</button><button  class="btn btn-primary btn-sm changeSMovieBannerState">上架</button></td>';
                    }

                    bannerContent.innerHTML += contentStr;
                }
                $(".changeSMovieBannerState").each(function (i) {
                    $(".changeSMovieBannerState").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        $.ajax({
                            type: "get",
                            url: "/manage/banner/changeSMovieBannerState/" + id,
                            contentType: "application/json",
                            success: function (data) {
                                getSMovieBannerByAjax(bannerCriteria);
                            }
                        });
                    });
                });


                $(".editSMovieBanner").each(function (i) {
                    $(".editSMovieBanner").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        $.ajax({
                            type: "get",
                            url: "/manage/banner/find/" + id,
                            contentType: "application/json",
                            success: function (data) {
                                if (data.status == 200) {
                                    var banner = data.data;
                                    var afterType = banner.afterType;
                                    $('#banneSid').val(banner.sid);
                                    $('input[name=bannerId1]').val(banner.id);
                                    $("#picture3").attr("src",
                                            banner.picture);
                                    var afterType = banner.afterType;
                                    var bannerType = banner.bannerType.id;
                                    if (afterType == 1) {
                                        $("input[name=type1][value='1']").prop("checked", true);
                                        $("#afterType_val").val(banner.url);
                                        $("#urlTitle").val(banner.urlTitle);
                                    } else if (afterType == 4) {
                                        $("#afterType_val").val("");
                                        $("#urlTitle").val("");
                                        $("input[name=type1][value='4']").prop("checked", true);
                                    }
                                    if (bannerType == 15) {
                                        $("input[name=radioBannerType][value='15']").prop("checked", true);
                                    } else if (bannerType == 16) {
                                        $("input[name=radioBannerType][value='16']").prop("checked", true);
                                    }

                                    $("#type1").modal("show");
                                } else {
                                    alert("服务器异常");
                                }
                            }
                        });
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
                sMovieOrderCriteria.offset = p;
                init = 0;
                getSMovieOrderByAjax(sMovieOrderCriteria);
            }
        });
    }
    function initPage1(page, totalPage) {
        $('#tcdPageCode1').unbind();
        $("#tcdPageCode1").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                sMovieProductCriteria.offset = p;
                init1 = 0;
                getSMovieProductByAjax(sMovieProductCriteria);
            }
        });
    }

    function initPage2(page, totalPage) {
        $('#tcdPageCode2').unbind();
        $("#tcdPageCode2").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                bannerCriteria.offset = p;
                init2 = 0;
                getSMovieBannerByAjax(bannerCriteria);
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
    function addMovie() {
        $("#addMovie").modal("toggle");
    }


    function addPopularMovie() {
        var sMovieProduct = {};
        var detailUrl = $("#picture").attr('src');
        var sid = $("#sMovieSid").val();
        if (detailUrl == null || detailUrl == "") {
            alert("请上传图片");
        }
        if (sid == null || sid == "") {
            alert("请输入序号");
        }
        if (detailUrl != null && detailUrl != "" && sid != null && sid != "") {
            sMovieProduct.sid = sid;
            sMovieProduct.picture = detailUrl;
            $.ajax({
                type: "post",
                url: "/manage/sMovieProduct/addSMovieProduct",
                async: false,
                data: JSON.stringify(sMovieProduct),
                contentType: "application/json",
                success: function (data) {
                    var status = data.status;
                    if (status == "200") {
                        alert("保存成功");
                    } else {
                        alert("保存失败");
                    }
                }
            })
        }

        $("#sMovieSid").val("");
        $("#picture").attr("src", "");
        flag1 = true;
        getSMovieProductByCriteria(sMovieProductCriteria);
    }
    function editPopularMovie() {
        var sMovieProduct = {};
        var sMovieProductId = $("#sMovieProductId").val();
        var detailUrl = $("#picture1").attr('src');
        var sid = $("#sMovieSid1").val();
        if (detailUrl == null || detailUrl == "") {
            alert("请上传图片");
        }
        if (sid == null || sid == "") {
            alert("请输入序号");
        }
        if (detailUrl != null && detailUrl != "" && sid != null && sid != "") {
            sMovieProduct.id = sMovieProductId;
            sMovieProduct.sid = sid;
            sMovieProduct.picture = detailUrl;
            $.ajax({
                type: "post",
                url: "/manage/sMovieProduct/editSMovieProduct",
                async: false,
                data: JSON.stringify(sMovieProduct),
                contentType: "application/json",
                success: function (data) {
                    var status = data.status;
                    if (status == "200") {
                        alert("修改成功");
                    } else {
                        alert("修改失败");
                    }
                }
            })
        }
        $("#sMovieProductId").val("");
        $("#sMovieSid1").val("");
        $("#picture1").attr("src", "");
        getSMovieProductByAjax(sMovieProductCriteria);
    }
    function addBanner() {
        $("#picture3").attr("src", "");
        $("#banneSid").val("");
        $("input[name='type1']:eq(0)").attr("checked", 'checked');
        $("input[name='radioBannerType']:eq(0)").attr("checked", 'checked');
        $("#afterType_val").val("");
        $("#urlTitle").val("");

        $("#type1").modal("show");
    }
    $('#bannerSubmit1').on('click', function () {
        var banner = {}, bannerType = {};
        var picture = $("#picture3").attr("src");
        var afterType = $('input:radio[name="type1"]:checked').val();
        var radioBannerType = $('input:radio[name="radioBannerType"]:checked').val();
        var afterType_val = "";
        var urlTitle = "";
        var banneSid = $('#banneSid').val();
        if (afterType == 1) {
            afterType_val = $("#afterType_val").val();
            urlTitle = $("#urlTitle").val();
            if (urlTitle == null || urlTitle == "") {
                alert("请输入页面标题");
                return false;
            }
            if (afterType_val == null || afterType_val == "") {
                alert("请补全类型信息");
                return false;
            }
        }

        if (picture == null || picture == "") {
            alert("请选择一张图片");
            return false;
        }
        if (afterType == null) {
            alert("请选择一种后置类型");
            return false;
        }
        if (banneSid == null || banneSid == "") {
            alert("请填写序号");
            return false;
        }

        bannerType.id = radioBannerType;
        banner.id = $('input[name=bannerId1]').val();
        banner.sid = banneSid;
        banner.picture = picture;
        banner.bannerType = bannerType;
        banner.afterType = afterType;
        if (afterType == 1) {
            banner.url = afterType_val;
            banner.urlTitle = urlTitle;
        } else if (afterType == 2) {
            var product = {};
            product.id = afterType_val;
            banner.product = product;
        } else if (afterType == 3) {
            var merchant = {};
            merchant.merchantSid = afterType_val;
            banner.merchant = merchant;
        }

        $.ajax({
            type: "post",
            url: "/manage/banner/saveForMovie",
            contentType: "application/json",
            data: JSON.stringify(banner),
            success: function (data) {
                if (data.status == 200) {
                    flag2 = true;
                    getBannerByCriteria();
                } else {
                    alert(data.status);
                }
            }
        });
        $("#picture3").attr("src", "");
        $("#banneSid").val("");
        $("input[name='type1']:eq(0)").attr("checked", 'checked');
        $("input[name='radioBannerType']:eq(0)").attr("checked", 'checked');
        $("#afterType_val").val("");
        $("#urlTitle").val("");


    });
    function exportExcel() {
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            sMovieOrderCriteria.createdStartDate = startDate;
            sMovieOrderCriteria.createdEndDate = endDate;
        }
        var dateStr2 = $('#date-end2 span').text().split("-");
        if (dateStr2 != null && dateStr2 != '') {
            var startDate2 = dateStr2[0].replace(/-/g, "/");
            var endDate2 = dateStr2[1].replace(/-/g, "/");
            sMovieOrderCriteria.completedStartDate = startDate2;
            sMovieOrderCriteria.completedEndDate = endDate2;
        }
        var dateStr3 = $('#date-end3 span').text().split("-");
        if (dateStr3 != null && dateStr3 != '') {
            var startDate3 = dateStr3[0].replace(/-/g, "/");
            var endDate3 = dateStr3[1].replace(/-/g, "/");
            sMovieOrderCriteria.dateUsedStartDate = startDate3;
            sMovieOrderCriteria.dateUsedEndDate = endDate3;
        }

        if ($("#userId").val() != "") {
            sMovieOrderCriteria.userSid = $("#userId").val();
        } else {
            sMovieOrderCriteria.userSid = null;
        }
        if ($("#userPhoneNumber").val() != "") {
            sMovieOrderCriteria.phoneNumber = $("#userPhoneNumber").val();
        } else {
            sMovieOrderCriteria.phoneNumber = null;
        }
        if ($("#movieName").val() != "nullValue") {
            sMovieOrderCriteria.sMovieTerminalId = $("#movieName").val();
        } else {
            sMovieOrderCriteria.sMovieTerminalId = null;
        }
        if ($("#sMovieOrderSid").val() != "") {
            sMovieOrderCriteria.orderSid = $("#sMovieOrderSid").val();
        } else {
            sMovieOrderCriteria.orderSid = null;
        }
        if ($("#sMovieOrderState").val() != "nullValue") {
            sMovieOrderCriteria.state = $("#sMovieOrderState").val();
        } else {
            sMovieOrderCriteria.state = null;
        }

        sMovieOrderCriteria.offset = 1;
        post("/manage/sMovieOrder/exportExcel", sMovieOrderCriteria);
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
</script>
</body>
</html>

