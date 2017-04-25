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
        <span style="font-size: large">银商门店</span>
        <div class="main">
            <div class="container-fluid">
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-6">
                        <label for="date-end">录入时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="merchant-sid">乐加门店号</label>
                        <input type="text" class="form-control" id="merchant-sid"
                               placeholder="请输入乐加门店号">
                    </div>
                </div>
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="shop_number">银联门店号</label>
                        <input type="text" id="shop_number" class="form-control" placeholder="请输入银联门店号"/>
                    </div>
                    <%--<div class="form-group col-md-3">--%>
                    <%--<label for="bankCard">银行卡号</label>--%>
                    <%--<input type="text" class="form-control" id="bankCard"--%>
                    <%--placeholder="请输入消费者ID">--%>
                    <%--</div>--%>
                    <%--<div class="form-group col-md-3">--%>
                    <%--<label for="order-source">绑定方式</label>--%>
                    <%--<select class="form-control" id="order-source">--%>
                    <%--<option value="">全部状态</option>--%>
                    <%--<option value="0">APP注册</option>--%>
                    <%--<option value="1">POS注册</option>--%>
                    <%--</select>--%>
                    <%--</div>--%>
                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchUnionPayStoreByCriteria()">筛选
                        </button>
                    </div>
                </div>
                <div style="text-align: right;margin: 10px 0 0 0">
                    <button class="btn btn-primary" onclick="addUnionPayStore()">新建银商门店</button>
                </div>
            </div>

            <span style="font-size: large">门店列表</span>
            <div id="myTabContent" class="tab-content">
                <div class="tab-pane fade in active" id="tab1">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr class="active">
                            <th>银商门店号</th>
                            <th>银商门店名称</th>
                            <th>银商门店地址</th>
                            <th>营销联盟商户号</th>
                            <th>拥有终端</th>
                            <th>对应乐加门店</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="unionPayStoreContent">
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


<div class="modal fade" id="myModal">
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
                            <label class="form-label">银商门店名称</label>
                            <input type="text" class="form-control" id="shopName">
                            <input type="hidden" class="form-control" id="unionPayStoreId">
                        </div>
                    </tr>
                    <tr>
                        <div class="form-group">
                            <label class="form-label">银商门店地址</label>
                            <input type="text" class="form-control" id="merchantAddress">
                        </div>
                    </tr>

                    <tr>
                        <div class="form-group">
                            <%--<label class="form-label">对应乐加门店编号</label>--%>
                            <input type="hidden" class="form-control" id="merchantSid">
                            <input type="hidden" id="merchantUnionPosId">
                        </div>
                    </tr>

                    <tr>
                        <label class="form-label">银商POS佣金费率</label>
                        <input type="text" style="width:60px ;display:-webkit-inline-box;" class="form-control"
                               id="commission">% &nbsp;&nbsp;
                    </tr>

                    <tr>
                        <label class="form-label">导流订单发红包比</label>
                        <input type="text" style="width:60px;display:-webkit-inline-box;" class="form-control"
                               id="scoreARebate">% &nbsp;&nbsp;
                    </tr>
                    <tr>
                        <label class="form-label">发金币比</label>
                        <input type="text" style="width:60px;display:-webkit-inline-box;" class="form-control"
                               id="scoreBRebate">% &nbsp;&nbsp;
                    </tr>

                    <tr>
                        <td>
                            <label>会员订单是否收佣金？</label>
                        </td>
                        <td>
                            <input type="radio" name="postage" value="1" checked="checked"><span>收佣金</span> &nbsp;&nbsp;
                            &nbsp;&nbsp; &nbsp;&nbsp;
                        </td>
                        <td>
                            <input type="radio" name="postage" value="0" class="checked"><span>不收佣金</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>微信支付宝否收佣金？</label>
                        </td>
                        <td>
                            <input type="radio" name="isNonCardCommission" value="1" checked="checked"><span>收佣金</span> &nbsp;&nbsp;
                            &nbsp;&nbsp; &nbsp;&nbsp;
                        </td>
                        <td>

                            <input type="radio" name="isNonCardCommission" value="0" class="checked"><span>不收佣金</span>
                        </td>
                    </tr>
                    <tr>
                        <label class="form-label">会员佣金订单发红包比</label>
                        <input type="text" style="width:60px ;display:-webkit-inline-box;" class="form-control"
                               id="userScoreARebate">% &nbsp;&nbsp;
                    </tr>

                    <tr>
                        <label class="form-label">发金币比</label>
                        <input type="text" style="width:60px;display:-webkit-inline-box;" class="form-control"
                               id="userScoreBRebate">% &nbsp;&nbsp;
                    </tr>
                    <tr>
                        <label class="form-label">红包标准手续费率</label>
                        <input type="text" style="width:60px;display:-webkit-inline-box;" class="form-control"
                               id="userGeneralACommission">% &nbsp;&nbsp;
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="cancelEditUnionPayStore()">
                    取消
                </button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="deal-confirm" onclick="confirmEditUnionPayStore()">确认
                </button>
            </div>
        </div>
    </div>
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
                            <label class="form-label">银商门店名称</label>
                            <input type="text" class="form-control" id="shopName2">
                        </div>
                    </tr>
                    <tr>
                        <div class="form-group">
                            <label class="form-label">银商门店地址</label>
                            <input type="text" class="form-control" id="merchantAddress2">
                        </div>
                    </tr>
                    <tr>
                        <div class="MODInput_row">
                            <div class="form-group">
                                <label class="form-label">对应乐加门店编号</label>
                                <input type="text" class="form-control" id="merchantSid2" placeholder="请输入门店编号">
                            </div>
                    </tr>
                    <br>
                    <tr>
                        <label class="form-label">银商POS佣金费率</label>
                        <input type="text" style="width:60px ;display:-webkit-inline-box;" class="form-control"
                               id="commission2">% &nbsp;&nbsp;
                    </tr>

                    <tr>
                        <label class="form-label">导流订单发红包比</label>
                        <input type="text" style="width:60px;display:-webkit-inline-box;" class="form-control"
                               id="scoreARebate2">% &nbsp;&nbsp;
                    </tr>
                    <tr>
                        <label class="form-label">发金币比</label>
                        <input type="text" style="width:60px;display:-webkit-inline-box;" class="form-control"
                               id="scoreBRebate2">% &nbsp;&nbsp;
                    </tr>
                    <br>
                    <tr>
                        <td>
                            <label>会员订单是否收佣金？</label>
                        </td>
                        <td>
                            <input type="radio" name="postage2" value="1" checked="checked"><span>收佣金</span> &nbsp;&nbsp;
                            &nbsp;&nbsp; &nbsp;&nbsp;
                        </td>
                        <td>

                            <input type="radio" name="postage2" value="0" class="checked"><span>不收佣金</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>微信支付宝否收佣金？</label>
                        </td>
                        <td>
                            <input type="radio" name="isNonCardCommission2" value="1" checked="checked"><span>收佣金</span> &nbsp;&nbsp;
                            &nbsp;&nbsp; &nbsp;&nbsp;
                        </td>
                        <td>

                            <input type="radio" name="isNonCardCommission2" value="0" class="checked"><span>不收佣金</span>
                        </td>
                    </tr>

                    <br>
                    <tr>
                        <label class="form-label">会员佣金订单发红包比</label>
                        <input type="text" style="width:60px ;display:-webkit-inline-box;" class="form-control"
                               id="userScoreARebate2">% &nbsp;&nbsp;
                    </tr>

                    <tr>
                        <label class="form-label">发金币比</label>
                        <input type="text" style="width:60px;display:-webkit-inline-box;" class="form-control"
                               id="userScoreBRebate2">% &nbsp;&nbsp;
                    </tr>
                    <tr>
                        <label class="form-label">红包标准手续费率</label>
                        <input type="text" style="width:60px;display:-webkit-inline-box;" class="form-control"
                               id="userGeneralACommission2">% &nbsp;&nbsp;
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="deal-confirm2" onclick="confirmAddUnionPayStore()">确认
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
    function addUnionPayStore() {
        $('#myModal2').modal({
            show: true,
            backdrop: 'static'
        });
    }


    function confirmAddUnionPayStore() {
        var unionPayStore = {};
        unionPayStore.shopName = $("#shopName2").val();
        unionPayStore.address = $("#merchantAddress2").val();
        var merchantUnionPosCriteria = {};
        merchantUnionPosCriteria.merchantSid = $("#merchantSid2").val();
        merchantUnionPosCriteria.commission = $("#commission2").val();
        merchantUnionPosCriteria.scoreARebate = $("#scoreARebate2").val();
        merchantUnionPosCriteria.scoreBRebate = $("#scoreBRebate2").val();
        merchantUnionPosCriteria.userScoreARebate = $("#userScoreARebate2").val();
        merchantUnionPosCriteria.userScoreBRebate = $("#userScoreBRebate2").val();
        merchantUnionPosCriteria.userGeneralACommission = $("#userGeneralACommission2").val();
        var useCommission = $("input[name=postage2]:checked").val();
        if (useCommission == "1") {
            merchantUnionPosCriteria.useCommission = true;
        }
        if (useCommission == "0") {
            merchantUnionPosCriteria.useCommission = false;
        }
        merchantUnionPosCriteria.isNonCardCommission=$("input[name=isNonCardCommission2]:checked").val();

        $.ajax({
            type: "get",
            url: "/manage/unionPayStore/getMerchantBySid/" + $("#merchantSid2").val(),
            async: false,
            contentType: "application/json",
            success: function (data) {
                var msg = data.msg;
                if (msg == "OK") {
                    var merchant = {};
                    merchant = data.data.merchant;
                    unionPayStore.merchant = merchant;
                    $.ajax({
                        type: "post",
                        url: "/manage/unionPayStore/edit",
                        async: false,
                        data: JSON.stringify(unionPayStore),
                        contentType: "application/json",
                        success: function (data) {
                            var msg = data.msg;
                            if (msg == "OK") {
                                $.ajax({
                                    type: "post",
                                    url: "/manage/merchantUnionPos/add",
                                    async: false,
                                    data: JSON.stringify(merchantUnionPosCriteria),
                                    contentType: "application/json",
                                    success: function (data) {
                                        alert(JSON.stringify(data.msg));
                                    }
                                })
                            } else {
                                alert(JSON.stringify(data.msg));
                            }
                        }
                    });

                } else {
                    alert(JSON.stringify(data.msg));
                }
            }
        });


        $("#shopName2").val("");
        $("#merchantAddress2").val("");
        $("#merchantSid2").val("");
        $("#commission2").val("");
        $("#scoreARebate2").val("");
        $("#scoreBRebate2").val("");
        $("#userScoreARebate2").val("");
        $("#userScoreBRebate2").val("");
        $("#userGeneralACommission2").val("");
        $("#merchantSid2").val("");
        $("input[name='postage2']").get(0).checked = true;
        $("input[name='isNonCardCommission2']").get(0).checked = true;
        searchUnionPayStoreByCriteria();



    }

    var unionPayStoreCriteria = {};
    var unionPayStoreContent = document.getElementById("unionPayStoreContent");
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
        unionPayStoreCriteria.offset = 1;
        getUnionPayStoreByAjax(unionPayStoreCriteria);

    })

    function getUnionPayStoreByAjax(unionPayStoreCriteria) {
        unionPayStoreContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/unionPayStore/getUnionPayStoreByAjax",
            async: false,
            data: JSON.stringify(unionPayStoreCriteria),
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
                    initPage(unionPayStoreCriteria.offset, totalPage);
                }
                if (init1) {
                    initPage(1, totalPage);
                }

                for (i = 0; i < content.length; i++) {

                    var contentStr = '<tr><td>' + content[i].shopNumber + '</td>';
                    contentStr += '<td>' + content[i].shopName + '</td>';
                    contentStr += '<td>' + content[i].address + '</td>';
                    contentStr += '<td>' + content[i].merchantNum + '</td>';
                    contentStr += '<td>' + content[i].termNos + '</td>';
                    contentStr += '<td>' + content[i].merchant.merchantSid + '</td>';
                    contentStr +=
                            '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                            + '"><button class="btn btn-primary edit")">编辑</button></td></tr>';
                    unionPayStoreContent.innerHTML += contentStr;
                }

                $(".edit").each(function (i) {
                    $(".edit").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        $.ajax({
                            type: "get",
                            url: "/manage/unionPayStore/editPage?id=" + id,
                            contentType: "application/json",
                            success: function (data) {
                                var unionPayStore = data.data.unionPayStore;
                                var merchantUnionPos = data.data.merchantUnionPos;
                                if (merchantUnionPos.useCommission) {
                                    $("input[name='postage']").get(0).checked = true;
                                } else {
                                    $("input[name='postage']").get(1).checked = true;
                                }
                                if (merchantUnionPos.isNonCardCommission==1) {
                                    $("input[name='isNonCardCommission']").get(0).checked = true;
                                } else {
                                    $("input[name='isNonCardCommission']").get(1).checked = true;
                                }
                                $("#shopName").val(unionPayStore.shopName);
                                $("#merchantAddress").val(unionPayStore.address);
                                $("#merchantSid").val(unionPayStore.merchant.merchantSid + "(" + unionPayStore.merchant.name + ")");
                                $("#merchantUnionPosId").val(merchantUnionPos.id);
                                $("#commission").val(merchantUnionPos.commission);
                                $("#scoreARebate").val(merchantUnionPos.scoreARebate);
                                $("#scoreBRebate").val(merchantUnionPos.scoreBRebate);
                                $("#userScoreARebate").val(merchantUnionPos.userScoreARebate);
                                $("#userScoreBRebate").val(merchantUnionPos.userScoreBRebate);
                                $("#userGeneralACommission").val(merchantUnionPos.userGeneralACommission);
                                $("#unionPayStoreId").val(unionPayStore.id);
                            }
                        });
                        setTimeout(showModal(), 300);//延时3秒
                    });
                });


            }
        })
    }

    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                unionPayStoreCriteria.offset = p;
                init1 = 0;
                getUnionPayStoreByAjax(unionPayStoreCriteria);
            }
        });
    }

    function confirmEditUnionPayStore() {
        var unionPayStore = {};
        unionPayStore.shopName = $("#shopName").val();
        unionPayStore.address = $("#merchantAddress").val();
        unionPayStore.id = $("#unionPayStoreId").val();
        var merchantUnionPosCriteria = {};
        merchantUnionPosCriteria.merchantSid = $("#merchantSid").val().substring(0, 7);
        merchantUnionPosCriteria.merchantUnionPosId = $("#merchantUnionPosId").val();
        merchantUnionPosCriteria.commission = $("#commission").val();
        merchantUnionPosCriteria.scoreARebate = $("#scoreARebate").val();
        merchantUnionPosCriteria.scoreBRebate = $("#scoreBRebate").val();
        merchantUnionPosCriteria.userScoreARebate = $("#userScoreARebate").val();
        merchantUnionPosCriteria.userScoreBRebate = $("#userScoreBRebate").val();
        merchantUnionPosCriteria.userGeneralACommission = $("#userGeneralACommission").val();
        merchantUnionPosCriteria.isNonCardCommission=$("input[name=isNonCardCommission]:checked").val();
        var useCommission = $("input[name=postage]:checked").val();
        if (useCommission == "1") {
            merchantUnionPosCriteria.useCommission = true;
        }
        if (useCommission == "0") {
            merchantUnionPosCriteria.useCommission = false;
        }
        $.ajax({
            type: "post",
            url: "/manage/unionPayStore/edit",
            async: false,
            data: JSON.stringify(unionPayStore),
            contentType: "application/json",
            success: function (data) {
                var msg = data.msg;
                if (msg == "OK") {
                    $.ajax({
                        type: "post",
                        url: "/manage/merchantUnionPos/edit",
                        async: false,
                        data: JSON.stringify(merchantUnionPosCriteria),
                        contentType: "application/json",
                        success: function (data) {
                            alert(JSON.stringify(data.msg));
                        }
                    })
                } else {
                    alert(JSON.stringify(data.msg));
                }
            }
        });
        $("#shopName").val("");
        $("#merchantAddress").val("");
        $("#merchantSid").val("");
        $("#merchantUnionPosId").val("");
        $("#commission").val("");
        $("#scoreARebate").val("");
        $("#scoreBRebate").val("");
        $("#userScoreARebate").val("");
        $("#userScoreBRebate").val("");
        $("#userGeneralACommission").val("");
        $("#unionPayStoreId").val("");
        $("input[name='postage']").get(0).checked = true;
        $("input[name='isNonCardCommission']").get(0).checked = true;
        searchUnionPayStoreByCriteria();

    }
    function showModal() {
        $('#myModal').modal({
            show: true,
            backdrop: 'static'
        });
    }
    function cancelEditUnionPayStore() {
        $("#shopName").val("");
        $("#merchantAddress").val("");
        $("#merchantSid").val("");
        $("#merchantUnionPosId").val("");
        $("#commission").val("");
        $("#scoreARebate").val("");
        $("#scoreBRebate").val("");
        $("#userScoreARebate").val("");
        $("#userScoreBRebate").val("");
        $("#userGeneralACommission").val("");
        $("input[name='postage']").get(0).checked = true;
    }


    function searchUnionPayStoreByCriteria() {
        unionPayStoreCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            unionPayStoreCriteria.startDate = startDate;
            unionPayStoreCriteria.endDate = endDate;
        }

        if ($("#merchant-sid").val() != "" && $("#merchant-sid").val() != null) {
            unionPayStoreCriteria.merchantSid = $("#merchant-sid").val();
        } else {
            unionPayStoreCriteria.merchantSid = null;
        }

        if ($("#shop_number").val() != "" && $("#shop_number").val() != null) {
            unionPayStoreCriteria.shopNumber = $("#shop_number").val();
        } else {
            unionPayStoreCriteria.shopNumber = null;
        }

        getUnionPayStoreByAjax(unionPayStoreCriteria);
    }


</script>
</body>
</html>