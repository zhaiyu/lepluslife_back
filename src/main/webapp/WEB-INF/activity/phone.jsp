<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 2016/12/08
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/sms.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <style>thead th, tbody td {
        text-align: center;
    }

    #myTab {
        margin-bottom: 10px;
    }</style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script src="${resourceUrl}/js/echarts.min.js"></script>
    <script src="${resourceUrl}/js/jquery.page.js"></script>
</head>
<style>
    body {
        background-color: #FFFFFF !important;
    }

    .w-top {
        width: 100% !important;
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
    }

    /*弹窗页面CSS*/
    .w-addPOS {
        width: 95%;
        margin: 0px auto;
        padding: 10px 0;
    }

    .w-addPOS > div > div:first-child {
        float: left;
        width: 20%;
        margin-right: 10%;
        margin-top: 10px;
        text-align: right;
        font-size: 14px;
    }

    .w-addPOS > div > div:last-child {
        float: left;
        width: 70%;
    }

    .w-addPOS > div {
        margin: 20px 0;
    }

    .w-addPOS > div > div > div, .w-addPOS > div > div > div > div {
        margin: 5px 0;
    }

    .w-addPOS input {
        display: inline;
    }

    .w-addPOS > div > div:last-child input[type=number] {
        width: 30%;
        margin: 0 1%;
    }

    .w-b {
        margin: 0 !important;
    }

    .w-b > div {
        float: left;
        width: 10%;
        padding: 1%;
        color: #333;
        text-align: center;
        border: 1px solid #ddd;
        cursor: pointer;
        -webkit-border-radius: 5px 0 0 5px;
        -moz-border-radius: 5px 0 0 5px;
        border-radius: 5px 0 0 5px;
    }

    .w-b > div:first-child {
        border-right: 0;
    }

    .w-b > div:last-child {
        border-left: 0;
        -webkit-border-radius: 0 5px 5px 0;
        -moz-border-radius: 0 5px 5px 0;
        border-radius: 0 5px 5px 0;
    }

    .w-c > div {
        float: left;
        width: 10%;
        padding: 1%;
        color: #333;
        text-align: center;
        border: 1px solid #ddd;
        cursor: pointer;
        -webkit-border-radius: 5px 0 0 5px;
        -moz-border-radius: 5px 0 0 5px;
        border-radius: 5px 0 0 5px;
    }

    .w-c > div:first-child {
        border-right: 0;
    }

    .w-c > div:last-child {
        border-left: 0;
        -webkit-border-radius: 0 5px 5px 0;
        -moz-border-radius: 0 5px 5px 0;
        border-radius: 0 5px 5px 0;
    }

    /*.w-bActive {*/
    /*background-color: #337ab7;*/
    /*border: 1px solid #337ab7 !important;*/
    /*color: #FFFFFF !important;*/
    /*}*/

    .w-addPOS > div:after, .w-b:after {
        content: '\20';
        display: block;
        clear: both;
    }

    .w-shai > div {
        float: right;
        border: 1px solid #e1e1e1;
        border-right: 0;
        padding: 1% 2%;
        cursor: pointer;
    }

    .w-shai > div:first-child {
        border-right: 1px solid #e1e1e1;
        margin-right: 5%;
        -webkit-border-radius: 0 5px 5px 0;
        -moz-border-radius: 0 5px 5px 0;
        border-radius: 0 5px 5px 0;
    }

    .w-shai > div:last-child {
        -webkit-border-radius: 5px 0 0 5px;
        -moz-border-radius: 5px 0 0 5px;
        border-radius: 5px 0 0 5px;
    }

    .w-shaiActive {
        background-color: #1798fe !important;
        color: #FFFFFF !important;
        border: 1px solid #1798fe !important;
    }

    .w-shai:after {
        content: '\20';
        display: block;
        clear: both;
    }

    .btn-primary {
        background-color: #1798fe !important;
        border: 1px solid #1798fe !important;
    }

    .promptText {
        width: 80%;
        margin: 50px auto;
        text-align: center;
        font-size: 20px
    }

    #Recharge .set {
        width: 80%;
        margin: 50px 20%;
    }

    #Recharge .set > div {
        margin: 10px 0;
    }

    .ing {
        background-color: rgba(255, 0, 0, 0.1);
    }
</style>
<body>
<%@include file="../common/top.jsp" %>
<div id="content">
    <%@include file="../common/left.jsp" %>
    <div class="m-right">
        <div class="main">
            <div class="container-fluid">
                <div class="w-top">
                    <div class="title">
                        <div>
                            <div class="blank"></div>
                            积分充话费
                        </div>
                        <div>
                            <div class="blank"></div>
                            话费账户余额：<span id="balance"></span>元
                        </div>
                        <button class="headButton type1 Recharge">设置充值参数</button>
                        <%--<div style="float: right;font-size: 13px;">今日充值：<span--%>
                        <%--id="todayUsedWorth"></span>/<span id="worth"></span>，还剩<span--%>
                        <%--id="last"></span>可充--%>
                        <%--</div>--%>
                    </div>
                    <div class="topDataShow">
                        <div>
                            <div><img src="${resourceUrl}/phone/img/cdyh.png" alt=""></div>
                            <div>
                                <p>累计充值话费</p>

                                <p id="totalWorth"></p>

                                <p id="totalWorthDetail"></p>
                            </div>
                        </div>
                        <div>
                            <div><img src="${resourceUrl}/phone/img/cgff.png" alt=""></div>
                            <div>
                                <p>累计充值人/次</p>

                                <p id="totalUser-totalNumber"></p>
                                <p id="totalUser-totalNumberDetail"></p>
                            </div>
                        </div>
                        <div>
                            <div><img src="${resourceUrl}/phone/img/ljtd.png" alt=""></div>
                            <div>
                                <p>累计发放红包</p>

                                <p id="totalBack"></p>
                            </div>
                        </div>
                        <div>
                            <div><img src="${resourceUrl}/phone/img/tdl.png" alt=""></div>
                            <div>
                                <p>乐加成本</p>

                                <p id="totalCost"></p>
                            </div>
                        </div>
                        <%--<div>--%>
                        <%--<div><img src="${resourceUrl}/phone/img/tdl.png" alt=""></div>--%>
                        <%--<div>--%>
                        <%--<p>活动带来粉丝/会员</p>--%>
                        <%--<p id="inviteU-M"></p>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                    </div>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li id="li-tab1"><a href="#tab1" data-toggle="tab">话费订单</a></li>
                    <li class="active" id="li-tab2"><a href="#tab2" data-toggle="tab">话费产品管理</a>
                    </li>
                    <li id="li-tab3"><a href="#tab3" data-toggle="tab">活动数据</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <div class="row" style="margin-top: 30px">
                            <div class="form-group col-md-2">
                                <label>订单状态</label>
                                <select class="form-control" id="state">
                                    <option value="-1">全部状态</option>
                                    <option value="1">待充值</option>
                                    <option value="2">已充值</option>
                                    <option value="0">待支付</option>
                                    <option value="3">充值失败</option>
                                </select>
                            </div>
                            <div class="form-group col-md-2">
                                <label>订单类型</label>
                                <select class="form-control" id="orderType">
                                    <option value="-1">全部类型</option>
                                    <option value="1">积分类</option>
                                    <option value="2">金币类</option>
                                </select>
                            </div>
                            <div class="form-group col-md-3">
                                <label>下单时间</label>

                                <div class="form-control date-end">
                                    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                    <span class="searchDateRange"></span>
                                    <b class="caret"></b>
                                </div>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="recharge-phone">充值手机号</label>
                                <input type="text" id="recharge-phone" class="form-control"
                                       placeholder="请输入手机号"/>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="recharge-person">充值会员</label>
                                <input type="text" id="recharge-person" class="form-control"
                                       placeholder="请输入会员ID"/>
                            </div>
                            <div class="form-group col-md-2">
                                <label>话费金额</label>
                                <select class="form-control" id="worth">
                                    <option value="-1">全部金额</option>
                                    <option value="300">300</option>
                                    <option value="100">100</option>
                                    <option value="50">50</option>
                                    <option value="20">20</option>
                                    <option value="10">10</option>
                                    <option value="5">5</option>
                                    <option value="2">2</option>
                                    <option value="1">1</option>
                                </select>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="calls-ID">话费产品ID</label>
                                <input type="text" id="calls-ID" class="form-control"
                                       placeholder="请输入话费产品ID"/>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="orderId">第三方订单号</label>
                                <input type="text" id="orderId" class="form-control"
                                       placeholder=""/>
                            </div>
                            <div class="form-group col-md-3">
                                <button class="btn btn-primary" style="margin-top: 24px"
                                        onclick="searchOrderByCriteria()">查询
                                </button>
                            </div>
                        </div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>订单编号</th>
                                <th>类型</th>
                                <th>话费金额</th>
                                <th>付费方式</th>
                                <th>充值手机号</th>
                                <th>充值会员</th>
                                <th>下单时间</th>
                                <th>充值时间</th>
                                <th>红包奖励</th>
                                <th class="remarks">状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="tab1-table"></tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade in active" id="tab2">
                        <div class="row" style="margin-top: 30px">
                            <div class="form-group col-md-2">
                                <label>产品状态</label>
                                <select class="form-control" id="ruleState">
                                    <option value="-1">全部状态</option>
                                    <option value="1">已上架</option>
                                    <option value="0">已下架</option>
                                </select>
                            </div>
                            <div class="form-group col-md-3">
                                <button class="btn btn-primary" style="margin-top: 24px"
                                        onclick="searchRuleByCriteria()">查询
                                </button>
                            </div>
                            <div style="float: right;margin: 10px 5% 0 0">
                                <button class="headButton type1 createWarn">新建话费产品</button>
                            </div>
                        </div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>话费产品ID</th>
                                <th>话费金额</th>
                                <th>付费方式</th>
                                <th>购买限制</th>
                                <th>累计购买人数</th>
                                <th>累计购买次数</th>
                                <th>实际支付总额</th>
                                <th>使用积分</th>
                                <th>红包奖励</th>
                                <th>库存</th>
                                <th class="remarks">状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="tab2-table"></tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade in active" id="tab3">
                        <div class="tab-content" style="margin-top: 10px">
                            <div class="row" style="margin: 30px 0 30px 0">
                                <div class="form-group col-md-3">
                                    <div class="form-control date-end3">
                                        <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                        <span class="searchDateRange"></span>
                                        <b class="caret"></b>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <select class="form-control" id="tab3-worth">
                                        <option value="-1">全部话费金额</option>
                                        <option value="300">300</option>
                                        <option value="100">100</option>
                                        <option value="50">50</option>
                                        <option value="20">20</option>
                                        <option value="10">10</option>
                                        <option value="5">5</option>
                                        <option value="2">2</option>
                                        <option value="1">1</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-2">
                                    <label for="recharge-phone">话费产品ID</label>
                                    <input type="text" id="tab3-ruleId" class="form-control"
                                           placeholder="请输入话费产品ID"/>
                                </div>
                                <div class="form-group col-md-3">
                                    <button class="btn btn-primary"
                                            onclick="orderDayListByCriteria()">查询
                                    </button>
                                </div>
                            </div>
                            <div class="eCharts">
                                <div class="w-shai">
                                    <div>发放红包</div>
                                    <%--<div>流量转化</div>--%>
                                    <div>购买情况</div>
                                    <div class="w-shaiActive">充值情况</div>
                                </div>
                                <script>
                                    var picType = 1;
                                    $(".w-shai > div").click(function () {
                                        $(".w-shai > div").removeClass("w-shaiActive");
                                        $(this).addClass("w-shaiActive");
                                        if ($(this).html() == '充值情况') {
                                            picType = 1;
                                            initPic(1);
                                        } else if ($(this).html() == '购买情况') {
                                            picType = 2;
                                            initPic(2);
                                        } else if ($(this).html() == '发放红包') {
                                            picType = 3;
                                            initPic(3);
                                        }
                                    });
                                </script>
                                <div class="container" id="echart-main" style="height:400px;"></div>
                            </div>
                            <div class="container">
                                <div class="tab-pane fade in active">
                                    <table class="table table-bordered table-hover">
                                        <thead>
                                        <tr class="active">
                                            <th>日期</th>
                                            <th>充值话费</th>
                                            <th>使用积分</th>
                                            <th>实际支付</th>
                                            <th>购买次数</th>
                                            <th>购买人数</th>
                                            <th>发放红包</th>
                                            <th>带来粉丝</th>
                                            <th>带来会员</th>
                                        </tr>
                                        </thead>
                                        <tbody id="tab3-table">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
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

<!--添加提示框-->
<div class="modal" id="createWarn">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">话费产品</h4>
            </div>
            <div class="w-addPOS">
                <input type="hidden" id="ruleId" value="">

                <div>
                    <div>话费金额</div>
                    <div class="form-group col-md-2">
                        <select class="form-control" id="rule-worth">
                            <option value="300">300</option>
                            <option value="100">100</option>
                            <option value="50">50</option>
                            <option value="20">20</option>
                            <option value="10">10</option>
                            <option value="5">5</option>
                            <option value="2">2</option>
                            <option value="1">1</option>
                        </select>
                    </div>
                    <%--<div><input type="number" class="form-control"></div>--%>
                </div>
                <div>
                    <div>付费方式</div>
                    <div>
                        <div>
                            <input type="radio" name="payType" checked="checked" value="1"/><span>微信+积分</span>
                            <input type="radio" name="payType" value="2"/><span>纯微信</span>
                            <input type="radio" name="payType" value="3"/><span>纯积分</span>
                        </div>
                        <div>
                            <div>
                                <span>微信支付</span><input class="form-control" id="rule-price"
                                                        type="number"/><span>元 + 积分支付</span><input
                                    class="form-control" id="rule-score" type="number"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <div>特惠活动</div>
                    <div>
                        <div class="w-b">
                            <input type="radio" name="join" checked="checked"
                                   value="0"/><span>不加入</span>
                            <input type="radio" name="join" value="1"/><span>加入</span>
                        </div>
                    </div>
                </div>
                <div class="xg">
                    <div>购买限制</div>
                    <div>
                        <div>
                            <input type="checkbox" name="rule-limit"
                                   value="1"><span>单人累计限购</span><input type="number"
                                                                       id="rule-totalLimit"
                                                                       class="form-control"/><span>次</span>
                        </div>
                        <div>
                            <input type="checkbox" name="rule-limit" value="2">
                            <select id="rule-limitType">
                                <option value="1">每天</option>
                                <option value="2">每周</option>
                                <option value="3">每月</option>
                            </select>
                            <span>限购</span><input type="number" id="rule-buyLimit"
                                                  class="form-control"/><span>次</span>
                        </div>
                    </div>
                </div>
                <div>
                    <div>库存</div>
                    <div>
                        <div class="w-c">
                            <input type="radio" name="repositoryLimit" checked="checked"
                                   value="0"/><span>不做限制</span>
                            <input type="radio" name="repositoryLimit" value="1"/><span>限制库存</span>
                        </div>
                        <div>
                            <div>
                                <span>产品库存</span><input class="form-control" type="number"
                                                        id="rule-repository"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <div>奖励红包</div>
                    <div>
                        <div><input type="radio" name="jlhb"
                                    checked="checked" value="0"/><span>固定金额</span><input
                                type="number" id="rule-rebate"
                                class="form-control"/>
                        </div>
                        <div><input type="radio" name="jlhb" value="1"/><span>随机金额</span><input
                                type="number"
                                class="form-control" id="rule-minRebate"/><span
                                style="margin: 0 1%;">~</span><input type="number"
                                                                     class="form-control"
                                                                     id="rule-maxRebate"/></div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="w-check">确认</button>
            </div>
            <script>
                //  初始化弹窗页面
                //  $("#createWarn input[type=number]").addClass("ing");
                if ($(".w-c > input:first-child").is(':checked')) {
                    $(".w-c").next().hide();
                } else if ($(".w-c > input:nth-child(3)").is(':checked')) {
                    $(".w-c").next().show();
                }

                $(".w-b > input").click(function () {
                    var joinHtml = $(this).next().html();
                    switch (joinHtml) {
                        case "不加入" :
                            $(".xg").show();
                            break;
                        case "加入" :
                            $(".xg").hide();
                    }
                });
                $(".w-c > input").click(function () {
                    var joinHtml = $(this).next().html();
                    switch (joinHtml) {
                        case "不做限制" :
                            $(this).parent().next().hide();
                            break;
                        case "限制库存" :
                            $(this).parent().next().show();
                    }
                });
                //                保留2位小数
                $("input[type=number]").blur(function () {
                    var val = $(this).val();
                    val = Math.round(val * 100) / 100;
                    $(this).val(val);
                    $(this).removeClass("ing");
                });

                //                disabled初始化
                $("input[type=radio]").nextAll().attr("disabled", "disabled");
                if ($("input[type=radio]").is(':checked')) {
                    $("input[checked=checked]").nextAll().removeAttr("disabled");
//                    $("input[checked=checked]").nextAll().removeClass("ing");
                }
                $("input[type=radio]").click(function () {
                    $(this).parent().siblings().children("input[type=number]").attr("disabled",
                                                                                    "disabled");
                    if ($(this).is(':checked')) {
                        $(this).nextAll().removeAttr("disabled");
//                        $(this).removeClass("ing")
                    } else {
                        $(this).nextAll().attr("disabled", "disabled");
//                        $(this).addClass("ing")
                    }
                });
                $("#w-check").click(function () {
                    var rule = {};
                    rule.id = $('#ruleId').val();
                    rule.worth = $('#rule-worth').val();
                    rule.payType = $('input:radio[name="payType"]:checked').val();
                    if (rule.payType == 1) {
                        var price = $('#rule-price').val();
                        if (price == null || price == '') {
                            alert("请输入金额");
                            $("#rule-price").focus();
                            return false;
                        }
                        rule.price = price * 100;
                        var score = $('#rule-score').val();
                        if (score == null || score == '') {
                            alert("请输入积分");
                            $("#rule-score").focus();
                            return false;
                        }
                        rule.score = score;
                    } else if (rule.payType == 2) {
                        var price = $('#rule-price').val();
                        if (price == null || price == '') {
                            alert("请输入金额");
                            $("#rule-price").focus();
                            return false;
                        }
                        rule.price = price * 100;
                    } else if (rule.payType == 3) {
                        var score = $('#rule-score').val();
                        if (score == null || score == '') {
                            alert("请输入积分");
                            $("#rule-score").focus();
                            return false;
                        }
                        rule.score = score;
                    }
                    rule.cheap = $('input:radio[name="join"]:checked').val();
                    if (rule.cheap == 0) {
                        var obj = document.getElementsByName("rule-limit");
                        var check_val = [];
                        for (k in obj) {
                            if (obj[k].checked)
                                check_val.push(obj[k].value);
                        }
                        if (check_val.indexOf('1') != -1) {
                            var totalLimit = $('#rule-totalLimit').val();
                            if (totalLimit == null || totalLimit == '') {
                                alert("请输入累计限购次数");
                                $("#rule-totalLimit").focus();
                                return false;
                            }
                            rule.totalLimit = totalLimit;
                        } else {
                            rule.totalLimit = 0;
                        }
                        if (check_val.indexOf('2') != -1) {
                            rule.limitType = $('#rule-limitType').val();
                            var buyLimit = $('#rule-buyLimit').val();
                            if (buyLimit == null || buyLimit == '') {
                                alert("请输入限购次数");
                                $("#rule-buyLimit").focus();
                                return false;
                            }
                            rule.buyLimit = buyLimit;
                        } else {
                            rule.limitType = 0;
                            rule.buyLimit = 0;
                        }
                    }
                    rule.repositoryLimit = $('input:radio[name="repositoryLimit"]:checked').val();
                    if (rule.repositoryLimit == 1) {
                        var repository = $('#rule-repository').val();
                        if (repository == null || repository == '') {
                            alert("请输入库存");
                            $("#rule-repository").focus();
                            return false;
                        }
                        rule.repository = repository;
                    }
                    rule.rebateType = $('input:radio[name="jlhb"]:checked').val();
                    if (rule.rebateType == 0) {
                        var rebate = $('#rule-rebate').val();
                        if (rebate == null || rebate == '') {
                            alert("请输入奖励金额");
                            $("#rule-rebate").focus();
                            return false;
                        }
                        rule.rebate = rebate * 100;
                    } else {
                        var minRebate = $('#rule-minRebate').val();
                        if (minRebate == null || minRebate == '') {
                            alert("请输入奖励最小金额");
                            $("#rule-minRebate").focus();
                            return false;
                        }
                        var maxRebate = $('#rule-maxRebate').val();
                        if (maxRebate == null || maxRebate == '') {
                            alert("请输入奖励最大金额");
                            $("#rule-maxRebate").focus();
                            return false;
                        }
                        if (maxRebate < minRebate) {
                            alert("最大奖励金额不能低于最小奖励金额");
                            return false;
                        }
                        rule.minRebate = minRebate * 100;
                        rule.rebate = maxRebate * 100;
                    }
                    console.log(rule);
                    $.ajax({
                               type: "post",
                               url: "/manage/phone/edit",
                               async: false,
                               contentType: "application/json",
                               data: JSON.stringify(rule),
                               success: function (data) {
                                   if (data.status == 200) {
                                       alert("操作成功");
                                       $("#createWarn").modal("hide");
                                       resetInput();
                                       showCallsProductList();
                                   } else {
                                       alert(data.msg);
                                   }
                               }
                           });
                });
                function resetInput() {
                    $('#ruleId').val('');
                }
            </script>
        </div>
    </div>
</div>
<div class="modal" id="Recharge">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">话费池</h4>
            </div>
            <div class="set">
                <%--<div>--%>
                <%--<label for="toggle-worth">日话费池</label>--%>
                <%--<input id="toggle-worth" type="number" class="form-control"--%>
                <%--style="width: 30%;display: inline;margin-left: 5%"/>--%>
                <%--</div>--%>
                <div>
                    <label for="toggle-limit">优惠限额</label>
                    <input id="toggle-limit" type="number" class="form-control"
                           style="width: 30%;display: inline;margin-left: 5%"/>
                    <span>（每个会员最多购买一次特惠商品）</span>
                </div>
                <div>
                    <input type="checkbox" id="toggle-update"/>
                    <span>更新特惠活动 (更新特惠活动后，会员的特惠限额将重新计数，请谨慎操作!)</span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="toggle-reChange">确认
                </button>
            </div>
        </div>
    </div>
</div>
<%--重新充值提示窗--%>
<div class="modal" id="prompt">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <input type="hidden" id="order-recharge-sid"/>

            <div class="promptText">
                <p>确认要给手机号<span id="phoneNumber"></span>重新充值<span id="order-worth"></span>元话费吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary changeWord">确认</button>
                <button type="button" class="btn btn-default know" data-dismiss="modal"
                        style="display: none;">知道了
                </button>
            </div>
        </div>
    </div>
</div>
<%--重新充值后状态提示窗--%>
<div class="modal" id="recharge-msg">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div id="msg" class="promptText"></div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" class="btn btn-default know" data-dismiss="modal"
                        style="display: none;">知道了
                </button>
            </div>
        </div>
    </div>
</div>
<%--上架下架提示框--%>
<div class="modal" id="changeState">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <input type="hidden" id="rule-state-id" value="">

            <div class="promptText">
                <p>确定要将该话费产品<span id="stateDetail"></span>吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="changeState()">确认</button>
            </div>
        </div>
    </div>
</div>
<%--设为已充值提示框--%>
<div class="modal" id="setState">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <input type="hidden" id="order-id" value="">

            <div class="promptText">
                <p>确定要将该<span id="order-sid"></span>话费订单设为已充值吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="setStateSubmit()">确认</button>
            </div>
        </div>
    </div>
</div>


<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<!--<script src="js/bootstrap-datetimepicker.min.js"></script>-->
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<!--<script src="js/bootstrap-datetimepicker.zh-CN.js"></script>-->
<script src="${resourceUrl}/js/moment.min.js"></script>

<%--页面设置--%>
<script>
    var flag = true, flag2 = true, flag3 = true;
    var init1 = 0, init2 = 0, init3 = 0;
    $(function () {
//        tab切换
        $('#myTab li:eq(0) a').tab('show');
//        提示框
        $(".Recharge").click(function () {
            $("#Recharge").modal("toggle");
        });
        $(".createWarn").click(function () {
            $("#createWarn").modal("toggle");
        });
    });
    //    时间选择器
    $(document).ready(function () {
        $('.date-end').daterangepicker({
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
            $('.date-end span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                                     + end.format('YYYY/MM/DD HH:mm:ss'));
        });
        $('.date-end3').daterangepicker({
                                            maxDate: moment(), //最大时间
                                            showDropdowns: true,
                                            showWeekNumbers: false, //是否显示第几周
                                            timePicker: false, //是否显示小时和分钟
                                            timePickerIncrement: 1440, //时间的增量，单位为分钟
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
            $('.date-end3 span').html(start.format('YYYY-MM-DD') + ' - '
                                      + end.format('YYYY-MM-DD'));
        });
    });

</script>

<!--echart-->
<script>
    var criteria = {}, ruleCriteria = {}, dayCriteria = {};
    function initDay() {
        dayCriteria.ruleId = -1;
        dayCriteria.worth = -1;
        var end = new Date();
        var begin = new Date(end.getTime() - 6 * 24 * 3600 * 1000);
        dayCriteria.begin = [begin.getFullYear(), begin.getMonth() + 1, begin.getDate()].join('-');
        dayCriteria.end = [end.getFullYear(), end.getMonth() + 1, end.getDate()].join('-');
    }
    initDay();
    // 显示标题，图例和空的坐标轴
    var table3 = '';
    function ajaxTable() {
        $("#tab3-table").html('');
        $.ajax({
                   type: "post",
                   url: "/manage/phone/orderByDayList",
                   async: false,
                   data: {
                       "begin": dayCriteria.begin,
                       "end": dayCriteria.end,
                       "ruleId": dayCriteria.ruleId,
                       "worth": dayCriteria.worth
                   },
                   success: function (data) {
                       table3 = data.data;
                       if (flag3) {
                           initPage(1, 1);
                           flag = false;
                       }
                       if (init3) {
                           initPage(1, 1);
                       }
                       //初始化折线图
                       initPic(picType);
                       //初始化表格
                       var len = 0;
                       for (var key in table3) {
                           len++;
                           var map2 = table3[key];
                           $("#tab3-table").append(
                                   $("<tr></tr>").append(
                                           $("<td></td>").html(key)
                                   ).append(
                                           $("<td></td>").html(map2.totalWorth == null ? 0
                                                                       : map2.totalWorth)
                                   ).append(
                                           $("<td></td>").html(map2.totalScore == null ? 0
                                                                       : map2.totalScore)
                                   ).append(
                                           $("<td></td>").html(map2.totalPrice == null ? 0
                                                                       : map2.totalPrice / 100)
                                   ).append(
                                           $("<td></td>").html(map2.totalNumber == null ? 0
                                                                       : map2.totalNumber)
                                   ).append(
                                           $("<td></td>").html(map2.totalUser == null ? 0
                                                                       : map2.totalUser)
                                   ).append(
                                           $("<td></td>").html(map2.totalBack == null ? 0
                                                                       : map2.totalBack / 100)
                                   ).append(
                                           $("<td></td>").html(map2.subCount == null ? 0
                                                                       : map2.subCount)
                                   ).append(
                                           $("<td></td>").html(map2.mCount == null ? 0
                                                                       : map2.mCount)
                                   )
                           )
                       }
                       $("#totalElements").html(len);
                   }
               });
    }

    function initPic(type) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echart-main'));
        //初始化折线图
        var xAxisList = [];
        if (type == 1) {
            var worthList = [], scoreList = [], payList = [];
            for (var key in table3) {
                xAxisList.push(key);
                var map2 = table3[key];
                worthList.push(map2.totalWorth == null ? 0 : map2.totalWorth);
                scoreList.push(map2.totalScore == null ? 0 : map2.totalScore);
                payList.push(map2.totalPrice == null ? 0 : map2.totalPrice / 100);
                myChart.setOption({
                                      tooltip: {
                                          trigger: 'axis'
                                      },
                                      legend: {
                                          data: ['充值话费', '使用积分', '实付金额']
                                      },
                                      xAxis: {
                                          boundaryGap: false,
                                          data: xAxisList
                                      },
                                      yAxis: {
                                          name: '金额',
                                          type: 'value'
                                      },
                                      series: [{
                                                   name: '充值话费',
                                                   type: 'line',
                                                   data: worthList
                                               },
                                               {
                                                   name: '使用积分',
                                                   type: 'line',
                                                   data: scoreList
                                               },
                                               {
                                                   name: '实付金额',
                                                   type: 'line',
                                                   data: payList
                                               }],
                                      visualMap: {
                                          inRange: {}
                                      }
                                  });
            }
        } else if (type == 2) {
            var numberList = [], userList = [];
            for (var key in table3) {
                xAxisList.push(key);
                var map2 = table3[key];
                numberList.push(map2.totalNumber == null ? 0 : map2.totalNumber);
                userList.push(map2.totalUser == null ? 0 : map2.totalUser);
                myChart.setOption({
                                      tooltip: {
                                          trigger: 'axis'
                                      },
                                      legend: {
                                          data: ['购买次数', '购买人数']
                                      },
                                      xAxis: {
                                          boundaryGap: false,
                                          data: xAxisList
                                      },
                                      yAxis: {
                                          name: '数量',
                                          type: 'value'
                                      },
                                      series: [{
                                                   name: '购买次数',
                                                   type: 'line',
                                                   data: numberList
                                               },
                                               {
                                                   name: '购买人数',
                                                   type: 'line',
                                                   data: userList
                                               }],
                                      visualMap: {
                                          inRange: {}
                                      }
                                  });
            }
        } else if (type == 3) {
            var backList = [];
            for (var key in table3) {
                xAxisList.push(key);
                var map2 = table3[key];
                backList.push(map2.totalBack == null ? 0 : map2.totalBack / 100);
                myChart.setOption({
                                      tooltip: {
                                          trigger: 'axis'
                                      },
                                      legend: {
                                          data: ['发放红包']
                                      },
                                      xAxis: {
                                          boundaryGap: false,
                                          data: xAxisList
                                      },
                                      yAxis: {
                                          name: '金额',
                                          type: 'value'
                                      },
                                      series: [{
                                                   name: '发放红包',
                                                   type: 'line',
                                                   data: backList
                                               }],
                                      visualMap: {
                                          inRange: {}
                                      }
                                  });
            }
        }
    }


</script>

<!--tollog-->
<script>
    $("input[name='payType']").click(function () {
        var in_html = $(this).next().html();
        switchPayType(in_html, $(this).parent().next());
    });
    function switchPayType(in_html, attr_div) {
        attr_div.empty();
        switch (in_html) {
            case "微信+积分":
                attr_div.append(
                        $("<span></span>").html("微信支付")
                ).append(
                        $("<input />").attr('id', 'rule-price').attr("type", "number").attr("class",
                                                                                            "form-control")
                ).append(
                        $("<span></span>").html("元 + 积分支付")
                ).append(
                        $("<input />").attr('id', 'rule-score').attr("type", "number").attr("class",
                                                                                            "form-control")
                );
                break;
            case "纯微信":
                attr_div.append(
                        $("<span></span>").html("微信支付")
                ).append(
                        $("<input />").attr('id', 'rule-price').attr("type", "number").attr("class",
                                                                                            "form-control")
                ).append(
                        $("<span></span>").html("元")
                );
                break;
            case "纯积分":
                attr_div.append(
                        $("<span></span>").html("积分支付")
                ).append(
                        $("<input />").attr('id', 'rule-score').attr("type", "number").attr("class",
                                                                                            "form-control")
                ).append(
                        $("<span></span>").html("积分")
                );
                break;
        }
    }
</script>

<%--AJAX--%>
<script>
    var selectContent = 1;
    $("#li-tab1").click(function () {
        init1 = 1;
        criteria.currPage = 1;
        selectContent = 1;
        getOrderList();
    });
    $("#li-tab2").click(function () {
        init2 = 1;
        ruleCriteria.currPage = 1;
        selectContent = 2;
        showCallsProductList();
    });
    $("#li-tab3").click(function () {
        init3 = 1;
        initDay();
        selectContent = 3;
        ajaxTable();
    });
    //**********数据展示***********

    //**********头部信息抓取*******
    var allworth, userworth;  // allwroth:总话费池；userworth:今日已使用的话费
    $.ajax({
               type: "get",
               url: "/manage/phone/count",
               async: false,
               contentType: "application/json",
               success: function (data) {
//                   userworth = data.data.todayUsedWorth;
                   var map = data.data;
                   $("#totalWorth").html(map.totalWorth + '(' + map.totalPrice / 100 + '+'
                                         + map.totalScore + ')');                                                  //累计充值话费
                   $("#totalUser-totalNumber").html(map.totalUser + "人/" + map.totalNumber + "次"); //累计充值人/次
                   $("#totalBack").html(map.totalBack / 100);                                                   //累计发放红包
                   $("#totalCost").html(map.totalCost / 100);                                                   //累计成本
//                   $("#inviteU-M").html(data.data.inviteU + "/" + data.data.inviteM);                           //活动带来粉丝/会员
//                   if (map.todayUsedWorth == null || map.todayUsedWorth == '') {                                                        //今日已用话费判断，为null则显示0；
//                       $("#todayUsedWorth").html(0);
//                   } else {
//                       $("#todayUsedWorth").html(map.todayUsedWorth);
//                   }
               }
           });

    //**********点击充值参数按钮**********
    //*****充值参数查询*****
    $.ajax({
               type: "get",
               url: "/manage/phone/pool",
               async: false,
               contentType: "application/json",
               data: {
                   type: 1,    //1=查询话费池|2=修改话费池
                   worth: -1,  //化肥池
                   limit: -1,  //特惠商品限购数量
                   update: -1  //更新优惠活动
               },
               success: function (data) {
                   // allworth = data.data.worth;
//                   $("#worth").html(data.data.worth);       //页面中的总化肥池显示
//                   $("#last").html(allworth - userworth);   //剩余可用话费 = 话费池总额 - 今日已使用的话费
//                   $("#toggle-worth").val(data.data.worth); //弹窗中的话费池总额显示
                   $("#toggle-limit").val(data.data.limit); //特惠商品限购数量
               }
           });
    //*****充值参数修改*****
    $("#toggle-reChange").click(function () {
//        var re_worth = $("#toggle-worth").val();     //获取更新的话费池值
        var re_worth = 10000;     //获取更新的话费池值
        var re_limit = $("#toggle-limit").val();     //获取更新的限制值
        var re_update;                               //更新的checkbox勾选
        if ($("#toggle-update").is(':checked')) {
            re_update = 1;
        } else {
            re_update = 0;
        }
        $.ajax({
                   type: "get",
                   url: "/manage/phone/pool",
                   async: false,
                   contentType: "application/json",
                   data: {
                       type: 2,
                       worth: re_worth,
                       limit: re_limit,
                       update: re_update
                   },
                   success: function (data) {
                       window.location.reload();
                   }
               });
    });

    //*****话费产品编辑弹窗*****
    function showCreate(iid) {
        $.ajax({
                   type: "get",
                   url: "/manage/phone/findRule/" + iid,
                   async: false,
                   contentType: "application/json",
                   data: {},
                   success: function (data) {
                       var rule = data.data;
                       $('#ruleId').val(rule.id);
                       $('#rule-worth').val(rule.worth);
                       $('input:radio[name="payType"][value=' + rule.payType + ']')[0].checked =
                       true;
                       if (rule.payType == 1) {
                           switchPayType('微信+积分', $('input[name="payType"]').parent().next());
                           $('#rule-price').val(rule.price / 100);
                           $('#rule-score').val(rule.score);
                       } else if (rule.payType == 2) {
                           switchPayType('纯微信', $('input[name="payType"]').parent().next());
                           $('#rule-price').val(rule.price / 100);
                       } else if (rule.payType == 3) {
                           switchPayType('纯积分', $('input[name="payType"]').parent().next());
                           $('#rule-score').val(rule.score);
                       }
                       $('input:radio[name="join"][value=' + rule.cheap + ']')[0].checked = true;
                       if (rule.cheap == 0) {
                           if (rule.totalLimit == 1) {
                               $('input:checkbox[name="rule-limit"][value="1"]')[0].checked = true;
                               $('#rule-totalLimit').val(rule.totalLimit);
                           } else {
                               $('input:checkbox[name="rule-limit"][value="1"]')[0].checked = false;
                           }
                           if (rule.limitType != 0) {
                               $('input:checkbox[name="rule-limit"][value="2"]')[0].checked = true;
                               $('#rule-limitType').val(rule.limitType);
                               $('#rule-buyLimit').val(rule.buyLimit);
                           } else {
                               $('input:checkbox[name="rule-limit"][value="2"]')[0].checked = false;
                           }
                       } else {
                           $(".xg").hide();
                       }
                       $('input:radio[name="repositoryLimit"][value=' + rule.repositoryLimit
                         + ']')[0].checked = true;
                       if (rule.repositoryLimit == 1) {
                           $(".w-c").next().show();
                           $('#rule-repository').val(rule.repository);
                       } else {
                           $(".w-c").next().hide();
                       }
                       $('input:radio[name="jlhb"][value=' + rule.rebateType + ']')[0].checked =
                       true;
                       if (rule.rebateType == 0) {
                           $('#rule-rebate').val(rule.rebate / 100);
                       } else {
                           $('#rule-minRebate').val(rule.minRebate / 100);
                           $('#rule-maxRebate').val(rule.rebate / 100);
                       }
                       $("#createWarn").modal("toggle");
                   }
               });
    }
    //*****上架或下架话费产品*****
    function showChange(id, state) {
        $('#rule-state-id').val(id);
        if (state == 1) {
            $('#stateDetail').html('下架');
        } else {
            $('#stateDetail').html('上架');
        }
        $("#changeState").modal("toggle");
    }
    function changeState() {
        $.ajax({
                   type: "get",
                   url: "/manage/phone/changeState/" + $('#rule-state-id').val(),
                   async: false,
                   contentType: "application/json",
                   data: {},
                   success: function (data) {
                       if (data.status != 200) {
                           alert(data.msg);
                       }
                       $("#changeState").modal("hide");
                       showCallsProductList();
                   }
               });
    }

    //***************将订单设为已充值(其他平台充值的)***************
    function setState(orderSid) {
        $('#order-id').val(orderSid);
        $('#order-sid').html(orderSid);
        $("#setState").modal("toggle");
    }
    function setStateSubmit() {
        var orderSid = $('#order-id').val();
        console.log(orderSid);
        $.ajax({
                   type: "post",
                   url: "/manage/phone/setState",
                   async: false,
                   data: {orderSid: orderSid},
                   success: function (data) {
                       if (data.status != 200) {
                           alert(data.msg);
                       } else {
                           alert("设为已支付成功");
                       }
                       $("#setState").modal("hide");
                       getOrderList();
                   }
               });
    }

    //***************订单重新充值***************
    function recharge(orderSid, phoneNumber, orderWorth) {
        $('#order-recharge-sid').val(orderSid);
        $('#phoneNumber').html(phoneNumber);
        $('#order-worth').html(orderWorth);
        $("#prompt").modal("toggle");
    }
    $(".changeWord").click(function () {
        var orderSid = $('#order-recharge-sid').val();
        $.ajax({
                   type: "post",
                   url: "/manage/phone/recharge",
                   async: false,
                   data: {orderSid: orderSid},
                   success: function (data) {
                       var msg = '';
                       if (data.status == 200) {
                           msg = data.data.msg;
                       } else {
                           msg = data.msg;
                       }
                       $("#prompt").modal("hide");
                       $("#msg").html(msg);
                       $("#recharge-msg").modal("toggle");
                       getOrderList();
                   }
               });
    });

    //**********话费订单列表数据展示**********
    criteria.currPage = 1;
    ruleCriteria.currPage = 1;
    function getOrderList() {
        $("#tab1-table").html('');
        $.ajax({
                   type: "post",
                   url: "/manage/phone/orderList",
                   async: false,
                   contentType: "application/json",
                   data: JSON.stringify(criteria),
                   success: function (data) {
                       var map = data.data;
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
                       for (var i = 0; i < data.data.content.length; i++) {
                           var list = data.data.content[i];
                           var trueP, trueS, userPhone, orderType;
                           if (list.type != null && list.type == 2) {
                               orderType = '金币';
                               trueS = toDecimal(list.trueScoreB / 100);
                           } else {
                               orderType = '积分';
                               trueS = list.trueScoreB;
                           }
                           if (list.truePrice == 0) {
                               trueP = '';
                           } else {
                               trueP = toDecimal(list.truePrice / 100) + '+';
                           }
                           if (list.leJiaUser.phoneNumber == "") {
                               userPhone = ''
                           } else {
                               userPhone = "(" + list.leJiaUser.phoneNumber + ")";
                           }
                           //var payType = list.phoneRule.payType;
                           var dateStart = new Date(list.createDate);
                           var dateEnd = new Date(list.completeDate);
                           var stateType = eval(list.state);
                           var stateDetail = '';
                           switch (stateType) {
                               case 0:
                                   stateDetail = "待支付";
                                   break;
                               case 1:
                                   stateDetail = "充值中";
                                   break;
                               case 2:
                                   stateDetail = "已充值";
                                   break;
                               case 3:
                                   stateDetail = "充值失败(" + list.message + ")";
                                   break;
                           }
                           $("#tab1-table").append(
                                   $("<tr></tr>").append(
                                           $("<td></td>").html(list.orderSid)
                                   ).append(
                                           $("<td></td>").html(orderType)
                                   ).append(
                                           $("<td></td>").html(list.worth)
                                   ).append(
                                           $("<td></td>").html(trueP + trueS + orderType)
                                   ).append(
                                           $("<td></td>").html(list.phone)
                                   ).append(
                                           $("<td></td>").append(
                                                   $("<p></p>").html("ID:" + list.leJiaUser.userSid)
                                           ).append(
                                                   $("<p></p>").html(userPhone)
                                           )
                                   ).append(
                                           $("<td></td>").append(
                                                   $("<p></p>").html(dateStart.toLocaleDateString())
                                           ).append(
                                                   $("<p></p>").html(dateStart.toLocaleTimeString())
                                           )
                                   ).append(
                                           $("<td></td>").append(
                                                   $("<p></p>").html(dateEnd.toLocaleDateString())
                                           ).append(
                                                   $("<p></p>").html(dateEnd.toLocaleTimeString())
                                           )
                                   ).append(
                                           $("<td></td>").html("￥" + toDecimal(list.payBackScore
                                                                               / 100))
                                   ).append(
                                           $("<td></td>").html(stateDetail)
                                   ).append(
                                           $("<td></td>").append(
                                                   stateType == 3
                                                           ? $("<input>").attr("type",
                                                                               "button").attr("class",
                                                                                              "btn btn-xs btn-primary select-btn").attr("onclick",
                                                                                                                                        "recharge('"
                                                                                                                                        + list.orderSid
                                                                                                                                        + "','"
                                                                                                                                        + list.phone
                                                                                                                                        + "','"
                                                                                                                                        + list.worth
                                                                                                                                        + "')").val("点击重试")
                                                           : ""
                                           ).append(
                                                   stateType == 3
                                                           ? $("<input>").attr("type",
                                                                               "button").attr("class",
                                                                                              "btn btn-xs btn-primary select-btn").attr("onclick",
                                                                                                                                        "setState('"
                                                                                                                                        + list.orderSid
                                                                                                                                        + "')").val("设为已充值")
                                                           : ""
                                           )
                                   )
                           )
                       }
                   }
               });
    }
    getOrderList();

    //**********话费产品管理**********
    function showCallsProductList() {
        $("#tab2-table").html('');
        $.ajax({
                   type: "post",
                   url: "/manage/phone/ruleList",
                   async: false,
                   contentType: "application/json",
                   data: JSON.stringify(ruleCriteria),
                   success: function (data) {
                       var map = data.data.ruleList;
                       var total = data.data.countList;
                       var totalPage = map.totalPages;
                       var totalElements = map.totalElements;
                       $("#totalElements").html(totalElements);

                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag2) {
                           initPage(ruleCriteria.currPage, totalPage);
                           flag = false;
                       }
                       if (init2) {
                           initPage(1, totalPage);
                       }

                       for (var i = 0; i < map.content.length; i++) {

                           var showList = map.content[i];
                           var payType = showList.payType;       //购买类型
                           var limitBuy = showList.limitType;    //限购类型
                           var totalLimit = showList.totalLimit; //累计购买限制
                           var state = showList.state;           //状态

                           switch (payType) {
                               case 1:
                                   payType =
                                   toDecimal(showList.price / 100) + "+" + showList.score + "积分";
                                   break;
                               case 2:
                                   payType = toDecimal(showList.price / 100);
                                   break;
                               case 3:
                                   payType = showList.score + "积分";
                                   break;
                           }
                           var limitBuyDetail = '';
                           switch (limitBuy) {
                               case 0:
                                   limitBuyDetail = "无限制";
                                   break;
                               case 1:
                                   limitBuyDetail = "每日限购";
                                   break;
                               case 2:
                                   limitBuyDetail = "每周限购";
                                   break;
                               case 3:
                                   limitBuyDetail = "每月限购";
                                   break;
                           }
                           if (totalLimit == 0) {
                               limitBuy = limitBuyDetail + showList.buyLimit + "次";
                           } else {
                               limitBuy =
                               "累计" + totalLimit + "次，" + limitBuyDetail + showList.buyLimit + "次";
                           }
                           var stateDetail = '';
                           switch (state) {
                               case 1:
                                   stateDetail = "<span>已上架</span>";
                                   break;
                               case 0:
                                   stateDetail = "<span style='color: red;'>已下架</span>";
                                   break;
                           }
                           var t = total[showList.id];
                           $("#tab2-table").append(
                                   $("<tr></tr>").append(
                                           $("<td></td>").html(showList.id)
                                   ).append(
                                           $("<td></td>").html(showList.worth)
                                   ).append(
                                           $("<td></td>").html(payType)
                                   ).append(
                                           $("<td></td>").html(limitBuy)
                                   ).append(
                                           $("<td></td>").html(t == null ? 0 : t.totalUser)
                                   ).append(
                                           $("<td></td>").html(t == null ? 0 : t.totalNumber)
                                   ).append(
                                           $("<td></td>").html(t == null ? 0
                                                                       : toDecimal(t.totalPrice
                                                                                   / 100))
                                   ).append(
                                           $("<td></td>").html(t == null ? 0 : t.totalScore)
                                   ).append(
                                           $("<td></td>").html(t == null ? 0 : toDecimal(t.totalBack
                                                                                         / 100))
                                   ).append(
                                           $("<td></td>").html(showList.repositoryLimit == 1
                                                                       ? showList.repository
                                                                       : '无限制')
                                   ).append(
                                           $("<td></td>").html(stateDetail)
                                   ).append(
                                           $("<td></td>").append(
                                                   $("<input>").attr("type", "button").attr("class",
                                                                                            "btn btn-xs btn-primary select-btn createWarn").attr("onclick",
                                                                                                                                                 "showCreate("
                                                                                                                                                 + showList.id
                                                                                                                                                 + ")").val("编辑")
                                           ).append(
                                                   $("<input>").attr("type", "button").attr("class",
                                                                                            "btn btn-xs btn-primary select-btn").attr("onclick",
                                                                                                                                      "showChange("
                                                                                                                                      + showList.id
                                                                                                                                      + ","
                                                                                                                                      + state
                                                                                                                                      + ")").val(state
                                                                                                                                                 == 1
                                                                                                                                                         ? "下架"
                                                                                                                                                         : "上架")
                                           )
                                   )
                           )
                       }

                   }
               });
    }

    //*****条件查询话费订单*****
    function searchOrderByCriteria() {
        criteria.currPage = 1;
        init1 = 1;
        if ($("#state").val() != -1) {
            criteria.state = $("#state").val();
        } else {
            criteria.state = null;
        }
        if ($("#orderType").val() != -1) {
            criteria.type = $("#orderType").val();
        } else {
            criteria.type = null;
        }
        var dateStr = $('.date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            criteria.startDate = startDate;
            criteria.endDate = endDate;
        }
        if ($("#recharge-phone").val() != "" && $("#recharge-phone").val() != null) {
            criteria.phone = $("#recharge-phone").val();
        } else {
            criteria.phone = null;
        }
        if ($("#recharge-person").val() != "" && $("#recharge-person").val() != null) {
            criteria.userSid = $("#recharge-person").val();
        } else {
            criteria.userSid = null;
        }
        if ($("#worth").val() != -1) {
            criteria.worth = $("#worth").val();
        } else {
            criteria.worth = null;
        }
        if ($("#calls-ID").val() != "" && $("#calls-ID").val() != null) {
            criteria.ruleId = $("#calls-ID").val();
        } else {
            criteria.ruleId = null;
        }
        if ($("#orderId").val() != "" && $("#orderId").val() != null) {
            criteria.orderId = $("#orderId").val();
        } else {
            criteria.orderId = null;
        }
        getOrderList();
    }
    //*****条件查询产品*****
    function searchRuleByCriteria() {
        ruleCriteria.currPage = 1;
        init2 = 1;
        if ($("#ruleState").val() != -1) {
            ruleCriteria.state = $("#ruleState").val();
        } else {
            ruleCriteria.state = null;
        }
        showCallsProductList();
    }
    //*****条件查询活动数据*****
    function orderDayListByCriteria() {
        init3 = 1;
        var dateStr = $('.date-end3 span').text().split(" - ");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "-");
            var endDate = dateStr[1].replace(/-/g, "-");
            dayCriteria.begin = startDate;
            dayCriteria.end = endDate;
        }
        dayCriteria.worth = $("#tab3-worth").val();
        if ($("#tab3-ruleId").val() != "" && $("#tab3-ruleId").val() != null) {
            dayCriteria.ruleId = $("#tab3-ruleId").val();
        } else {
            dayCriteria.ruleId = -1;
        }
        ajaxTable();
    }

    //*****分页插件*****
    function initPage(currPage, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: currPage,
                                         backFn: function (p) {
                                             //单击回调方法，p是当前页码
                                             init1 = 0;
                                             if (selectContent == 1) {
                                                 criteria.currPage = p;
                                                 getOrderList();
                                             } else if (selectContent == 2) {
                                                 ruleCriteria.currPage = p;
                                                 showCallsProductList();
                                             }
                                         }
                                     });
    }

    //**********************获得当前account余额**********************
    $.ajax({
               type: "get",
               url: "/manage/phone/balance",
               async: false,
               success: function (data) {
                   $("#balance").html(data.data.balance);
               }
           });

    //***********************保留2位小数*************************************
    function toDecimal(x) {
        var f = parseFloat(x);
        if (isNaN(f)) {
            return false;
        }
        var f = Math.round(x * 100) / 100;
        var s = f.toString();
        var rs = s.indexOf('.');
        if (rs < 0) {
            rs = s.length;
            s += '.';
        }
        while (s.length <= rs + 2) {
            s += '0';
        }
        return s;
    }


</script>
</body>
</html>
