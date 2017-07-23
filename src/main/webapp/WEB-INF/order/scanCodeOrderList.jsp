<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/12/20
  Time: 上午10:02
  通道订单（易宝&富有）
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
    <style>
        thead th, tbody td {
            text-align: center;
        }

        #myTab {
            margin-bottom: 10px;
        }

        @media (min-width: 992px) {
            .modal-lg {
                width: 750px;
            }
        }

        .modal-header {
            border: 0 !important;
        }

        .FYOrder {
            width: 90%;
            margin: 0 auto;
        }

        .FYOrder > div {
            margin-top: 10px;
        }

        .FYOrder > div > div {
            float: left;
            font-size: 14px;
        }

        .FYOrder > div > div:first-child {
            width: 25%;
            font-size: 16px;
            text-align: left;
        }

        .recover {
            width: 70%;
        }

        .recover > div > div {
            float: left;
            width: 40%;
        }

        .recover > div > div:first-child {
            margin-right: 5%;
        }

        .recover > div > div:last-child {
            width: 50%;
        }

        .FYOrder > * {
            text-align: center;
        }

        .FYOrder > p:first-child {
            font-size: 26px;
            margin: 10px 0;
        }

        .FYOrder > p:first-child {
            font-size: 20px;
        }

        .FYOrder input[type=radio] {
            width: auto !important;
            margin: 0 !important;
        }

        .FYOrder input[type=number] {
            width: 30%;
            border: 1px solid #dddddd;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            padding: 5px 10px;
        }

        .recover_button {
            text-align: center;
        }

        button {
            text-align: center;
            margin: 20px 0;
        }

        .FYOrder > div:after, .recover > div:after {
            content: '\20';
            display: block;
            clear: both;
        }

        .recover-r {
            color: red;
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
        <div class="main">
            <div class="container-fluid">
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-3">
                        <label for="date-end">交易完成时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="payment">付款方式</label>
                        <select class="form-control" id="payment">
                            <option value="-1">全部</option>
                            <option value="0">纯现金</option>
                            <option value="1">纯红包</option>
                            <option value="2">混合支付</option>
                        </select>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="customer-ID">消费者ID</label>
                        <input type="text" class="form-control" id="customer-ID"
                               placeholder="请输入消费者ID">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="customer-tel">消费者手机号</label>
                        <input type="text" id="customer-tel" class="form-control"
                               placeholder="请输入消费者手机号"/>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="merchant-name">门店名称</label>
                        <input type="text" id="merchant-name" class="form-control"
                               placeholder="请输入商户名称"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="orderType">订单类型</label>
                        <select class="form-control" id="orderType">
                            <option value="-1">所在类型(全部)</option>
                            <option value="12001">非会员普通订单（普通商户）</option>
                            <option value="12003">非会员普通订单（联盟商户）</option>
                            <option value="12002">会员普通订单（普通商户）</option>
                            <option value="12004">导流订单</option>
                            <option value="12005">会员订单（佣金费率）</option>
                            <option value="12006">会员订单（普通费率）</option>
                        </select>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="order-SID">订单编号</label>
                        <input type="text" id="order-SID" class="form-control"
                               placeholder="请输入订单编号"/>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="merchantNum">渠道商户号</label>
                        <input type="text" id="merchantNum" class="form-control"
                               placeholder="请输入商户号"/>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-2">
                        <label for="source">支付渠道</label>
                        <select class="form-control" id="source">
                            <option value="-1">全部</option>
                            <option value="0">WAP支付</option>
                            <option value="1">APP支付</option>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="source">通道类型</label>
                        <select class="form-control" id="gatewayType">
                            <option value="-1">全部</option>
                            <option value="1">易宝</option>
                            <option value="0">富友</option>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="source">支付方式</label>
                        <select class="form-control" id="payType">
                            <option value="-1">全部</option>
                            <option value="0">微信</option>
                            <option value="1">支付宝</option>
                        </select>
                    </div>

                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchOrderByCriteria()">查询
                        </button>
                    </div>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab" onclick="searchOrderByState()">全部订单</a>
                    </li>
                    <li class="active"><a href="#tab2" data-toggle="tab"
                                          onclick="searchOrderByState(0)">未支付</a></li>
                    <li><a href="#tab3" data-toggle="tab" onclick="searchOrderByState(1)">已支付</a>
                    <li><a href="#tab3" data-toggle="tab" onclick="searchOrderByState(2)">已退款</a>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>订单编号</th>
                                <th>交易完成时间</th>
                                <th>交易门店</th>
                                <th>消费者信息</th>
                                <th>订单类型</th>
                                <th>总价</th>
                                <th>实付</th>
                                <th>用鼓励金</th>
                                <th>支付方式</th>
                                <th>佣金</th>
                                <th>商户入账</th>
                                <th>手续费成本</th>
                                <th>返鼓励金</th>
                                <th>返金币</th>
                                <th>分润</th>
                                <th>状态</th>
                            </tr>
                            </thead>
                            <tbody id="orderContent">
                            </tbody>
                        </table>
                    </div>
                    <div class="tcdPageCode" style="display: inline;">
                    </div>
                    <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                    <button class="btn btn-primary pull-right" style="margin-top: 5px"
                            onclick="exportExcel()">导出excel
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!--添加提示框-->
<div class="modal" id="Refund">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="FYOrder">
                <div class="Mod-10">
                    <div>订单编号:</div>
                    <div id="orderSid"></div>
                </div>
                <div class="Mod-10">
                    <div>交易门店:</div>
                    <div id="merchantName"></div>
                </div>
                <div class="Mod-10">
                    <div>消费者:</div>
                    <div id="userInfo"></div>
                </div>
                <div class="Mod-10">
                    <div>消费金额：</div>
                    <div id="priceInfo"></div>
                </div>
                <div class="Mod-10">
                    <div>可退金额：</div>
                    <div><span id="canRefundInfo"></span></div>
                </div>
                <div class="Mod-10">
                    <div>消费后发放：</div>
                    <div><span id="rebate"></span>元红包+<span id="scoreB"></span>积分</div>
                </div>
                <hr style="height:1px;border:none;border-top:1px dashed #cCC;"/>
                <div class="Mod-10">
                    <div>微信渠道退款：</div>
                    <div><span id="truePay"></span>元</div>
                </div>
                <div class="Mod-10">
                    <div>红包渠道退款：</div>
                    <div><span id="trueScore"></span>元</div>
                </div>
                <div class="Mod-10">
                    <div>实际可追回红包：</div>
                    <div>+<span id="realRebate"></span>元</div>
                </div>
                <div class="Mod-10">
                    <div>实际可追回积分：</div>
                    <div><span id="realScoreB"></span></div>
                </div>
                <div class="Mod-10">
                    <div>退款后红包变更：</div>
                    <div><span id="scoreAChange"></span>元 <span>消费者红包余额<span id="userScoreA"></span></span>
                    </div>
                </div>
                <div class="Mod-10">
                    <div>退款后积分变更：</div>
                    <div><span id="scoreBChange"></span> <span>消费者积分余额<span id="userScoreB"></span></span>
                    </div>
                </div>
                <div class="Mod-10">
                    <div>追回分润：</div>
                    <div class="recover2" style="display: none">该订单未产生分润</div>
                    <div class="recover">
                        <div>
                            <div>会员锁定门店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                —<span
                                        id="L-M-P"></span>元
                            </div>
                            <div><span id="L-M-N"></span>【余额：<span id="L-M-M"></span>元】<span
                                    id="R-1" class="recover-r"></span></div>
                        </div>
                        <div>
                            <div>会员锁定天使合伙人 —<span id="L-P-P"></span>元</div>
                            <div><span id="L-P-N"></span>【余额：<span id="L-P-M"></span>元】<span
                                    id="R-2"></span></div>
                        </div>
                        <div>
                            <div>会员锁定城市合伙人 —<span id="L-C-P"></span>元</div>
                            <div><span id="L-C-N"></span>【余额：<span id="L-C-M"></span>元】<span
                                    id="R-3"></span></div>
                        </div>
                        <div>
                            <div>交易门店锁定天使人 —<span id="T-P-P"></span>元</div>
                            <div><span id="T-P-N"></span>【余额：<span id="T-P-M"></span>元】<span
                                    id="R-4"></span></div>
                        </div>
                        <div>
                            <div>交易门店锁定城市人 —<span id="T-C-P"></span>元</div>
                            <div><span id="T-C-N"></span>【余额：<span id="T-C-M"></span>元】<span
                                    id="R-5"></span></div>
                        </div>
                    </div>
                </div>
                <div class="Mod-10">
                    <div>追回策略：</div>
                    <div>
                        <div><input type="radio" name="RecoveryStrategy" checked="checked"
                                    value="0"><span>若账户余额不足以抵扣，则无法退款。</span>
                        </div>
                        <div><input type="radio"
                                    name="RecoveryStrategy"
                                    value="1"><span>若账户余额不足以抵扣，则会扣减到0。</span></div>
                    </div>
                </div>
                <div class="Mod-10">
                    <div>校验手机号：</div>
                    <div>
                        <span>15501066812</span><span style="margin: 0 2% 0 5%">验证码</span><input
                            type="number" name="validateCode">
                    </div>
                    <button class="ModButton ModButton_ordinary ModRadius" onclick="getVerify()"
                            id="sendCode">发送验证码
                    </button>
                </div>
                <div class="recover_button">
                    <button class="ModButton ModButton_ordinary ModRadius Refund_end"
                            onclick="refundSubmit()">确认退款
                    </button>
                    <button class="ModButton ModButton_back ModRadius" data-dismiss="modal">取消
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal" id="details">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="FYOrder">
                <div class="Mod-10">
                    <div>订单编号:</div>
                    <div id="d-orderSid"></div>
                </div>
                <div class="Mod-10">
                    <div>交易门店:</div>
                    <div id="d-merchantName"></div>
                </div>
                <div class="Mod-10">
                    <div>退款单号:</div>
                    <div id="d-refundSid"></div>
                </div>
                <div class="Mod-10">
                    <div>退款时间：</div>
                    <div id="d-refundTime"></div>
                </div>
                <div class="Mod-10">
                    <div>消费者:</div>
                    <div id="d-userInfo"></div>
                </div>
                <div class="Mod-10">
                    <div>消费金额：</div>
                    <div id="d-priceInfo"></div>
                </div>
                <div class="Mod-10">
                    <div>消费后发放：</div>
                    <div><span id="d-rebate"></span>元红包+<span id="d-scoreB"></span>积分</div>
                </div>
                <hr style="height:1px;border:none;border-top:1px dashed #cCC;"/>
                <div class="Mod-10">
                    <div>微信渠道退款：</div>
                    <div><span id="d-truePay"></span>元</div>
                </div>
                <div class="Mod-10">
                    <div>红包渠道退款：</div>
                    <div><span id="d-trueScore"></span>元</div>
                </div>
                <div class="Mod-10">
                    <div>追回红包：</div>
                    <div>+<span id="d-realRebate"></span>元</div>
                </div>
                <div class="Mod-10">
                    <div>追回积分：</div>
                    <div><span id="d-realScoreB"></span></div>
                </div>
                <div class="Mod-10">
                    <div>退款后红包变更：</div>
                    <div><span id="d-scoreAChange"></span>元</div>
                </div>
                <div class="Mod-10">
                    <div>退款后积分变更：</div>
                    <div>-<span id="d-scoreBChange"></span></div>
                </div>
                <div class="Mod-10">
                    <div>追回分润：</div>
                    <div class="recover2" style="display: none">该订单未产生分润</div>
                    <div class="recover">
                        <div>
                            <div>会员锁定门店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                —<span
                                        id="d-L-M-P"></span>元
                            </div>
                        </div>
                        <div>
                            <div>会员锁定天使合伙人 —<span id="d-L-P-P"></span>元</div>
                        </div>
                        <div>
                            <div>会员锁定城市合伙人 —<span id="d-L-C-P"></span>元</div>
                        </div>
                        <div>
                            <div>交易门店锁定天使人 —<span id="d-T-P-P"></span>元</div>
                        </div>
                        <div>
                            <div>交易门店锁定城市人 —<span id="d-T-C-P"></span>元</div>
                        </div>
                    </div>
                </div>
                <div class="recover_button">
                    <button class="ModButton ModButton_ordinary ModRadius" data-dismiss="modal">
                        知道了
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal" id="Refund_end">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="FYOrder">
                <p id="refund-status"></p>

                <p id="refund-msg"></p>

                <div>
                    <button class="ModButton ModButton_ordinary ModRadius" data-dismiss="modal">
                        知道了
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var selectOrderSid = '', phoneNumber = '15501066812';

    function showInfo(orderSid) {
        $("#details").modal("toggle");
        $.ajax({
                   type: "get",
                   url: "/manage/refund/refundInfo/" + orderSid,
                   async: false,
                   success: function (data) {
                       var map = data.data;
                       var order = map.order;
                       var refundOrder = map.refundOrder;
                       var user = map.user;
                       var share = map.share;
                       $('#d-orderSid').html(order.orderSid);
                       $('#d-merchantName').html(order.merchant.name);
                       $('#d-refundSid').html(refundOrder.refundOrderSid);
                       $('#d-refundTime').html(
                           new Date(order.completeDate).format('yyyy-MM-dd HH:mm:ss'));
                       if (user.phoneNumber != null && user.phoneNumber != '') {
                           $('#d-userInfo').html(user.phoneNumber + '(' + user.userSid + ')');
                       } else {
                           $('#d-userInfo').html(user.userSid);
                       }
                       $('#d-priceInfo').html(toDecimal(order.totalPrice / 100) + '元（微信实付'
                                              + toDecimal(order.truePay / 100) + '元+'
                                              + toDecimal(order.trueScore / 100) + '元红包）');
                       $('#d-rebate').html(toDecimal(order.rebate / 100));
                       $('#d-scoreB').html(order.scoreB);
                       $('#d-truePay').html(toDecimal(order.truePay / 100));
                       $('#d-trueScore').html(toDecimal(order.trueScore / 100));
                       $('#d-realRebate').html(toDecimal(refundOrder.realRebate / 100));
                       $('#d-realScoreB').html(refundOrder.realScoreB);
                       var scoreAChange = order.trueScore - refundOrder.realRebate;
                       if (scoreAChange >= 0) {
                           $('#d-scoreAChange').html('+' + toDecimal(scoreAChange / 100));
                       } else {
                           $('#d-scoreAChange').html('-' + toDecimal(scoreAChange / 100));
                       }
                       $('#d-scoreBChange').html(refundOrder.realScoreB);
                       $('#userScoreA').html(toDecimal(userScoreA / 100) + '元');
                       $('#userScoreB').html(userScoreB);
                       if (share != null) {
                           $('#d-L-M-P').html(toDecimal(refundOrder.toLockMerchant / 100));
                           $('#d-L-P-P').html(toDecimal(refundOrder.toLockPartner / 100));
                           $('#d-L-C-P').html(toDecimal(refundOrder.toLockPartnerManager / 100));
                           $('#d-T-P-P').html(toDecimal(refundOrder.toTradePartner / 100));
                           $('#d-T-C-P').html(toDecimal(refundOrder.toTradePartnerManager / 100));
                           $('.recover').css('display', 'block');
                           $('.recover2').css('display', 'none');
                       } else {
                           $('.recover').css('display', 'none');
                           $('.recover2').css('display', 'block');
                       }
                   }
               });
    }

    function refund(orderSid) {
        selectOrderSid = orderSid;
        $("#Refund").modal("toggle");
        $.ajax({
                   type: "get",
                   url: "/manage/scanCodeOrder/refund/" + selectOrderSid,
                   async: false,
                   contentType: "application/json",
                   success: function (data) {
                       var map = data.data;
                       var order = map.order;
                       var user = map.user;
                       var userScoreA = map.userScoreA;
                       var userScoreB = map.userScoreB;
                       var merchant = map.merchant;
                       var canRefund = map.canRefund;
                       var share = map.share;
                       $('#orderSid').html(order.orderSid);
                       $('#merchantName').html(merchant.name);
                       if (user.phoneNumber != null && user.phoneNumber != '') {
                           $('#userInfo').html(user.phoneNumber + '(' + user.userSid + ')');
                       } else {
                           $('#userInfo').html(user.userSid);
                       }
                       $('#priceInfo').html(toDecimal(order.totalPrice / 100) + '元（微信实付'
                                            + toDecimal(order.truePay / 100) + '元+'
                                            + toDecimal(order.trueScore / 100) + '元红包）');
                       $('#canRefundInfo').html(toDecimal(canRefund.totalPrice / 100) + '元（微信实付'
                                                + toDecimal(canRefund.truePay / 100) + '元+'
                                                + toDecimal(canRefund.trueScore / 100) + '元红包）');
                       if (canRefund.truePay < order.truePay || canRefund.trueScore
                                                                < order.trueScore) {
                           $('#canRefundInfo').css('color', 'red');
                       }
                       $('#rebate').html(toDecimal(order.rebate / 100));
                       $('#scoreB').html(order.scoreB);
                       $('#truePay').html(toDecimal(order.truePay / 100));
                       $('#trueScore').html(toDecimal(order.trueScore / 100));
                       var scoreAChange = order.trueScore - order.rebate;
                       if (scoreAChange >= 0) {
                           $('#scoreAChange').html('+' + toDecimal(scoreAChange / 100));
                       } else {
                           $('#scoreAChange').html('-' + toDecimal(scoreAChange / 100));
                       }
                       $('#scoreBChange').html('-' + order.scoreB);
                       $('#userScoreA').html(toDecimal(userScoreA / 100) + '元');
                       $('#userScoreB').html(userScoreB);

                       if ((userScoreA + order.trueScore - order.rebate) < 0) {
                           $('#userScoreA').html($('#userScoreA').html() + '(红包余额不足)');
                           $('#userScoreA').css('color', 'red');
                           $('#realRebate').html(toDecimal(userScoreA / 100));
                           $('#realRebate').css('color', 'red');
                       } else {
                           $('#realRebate').html(toDecimal(order.rebate / 100));
                       }
                       if ((userScoreB - order.scoreB) < 0) {
                           $('#userScoreB').html($('#userScoreB').html() + '(积分余额不足)');
                           $('#userScoreB').css('color', 'red');
                           $('#realScoreB').html(userScoreB);
                           $('#realScoreB').css('color', 'red');
                       } else {
                           $('#realScoreB').html(order.scoreB);
                       }
                       if (share != null) {
                           var s = '(余额不足)';
                           var toLockMerchant = share.toLockMerchant.split('_');
                           $('#L-M-P').html(toDecimal(toLockMerchant[0] / 100));
                           $('#L-M-N').html(toLockMerchant[1]);
                           $('#L-M-M').html(toDecimal(toLockMerchant[2] / 100));
                           if (eval(toLockMerchant[0]) > eval(toLockMerchant[2])) {
                               $('#R-1').html(s);
                           } else {
                               $('#R-1').html('');
                           }
                           var toLockPartner = share.toLockPartner.split('_');
                           $('#L-P-P').html(toDecimal(toLockPartner[0] / 100));
                           $('#L-P-N').html(toLockPartner[1]);
                           $('#L-P-M').html(toDecimal(toLockPartner[2] / 100));
                           if (eval(toLockPartner[0]) > eval(toLockPartner[2])) {
                               $('#R-2').html(s);
                           } else {
                               $('#R-2').html('');
                           }
                           var toLockPartnerManager = share.toLockPartnerManager.split('_');
                           $('#L-C-P').html(toDecimal(toLockPartnerManager[0] / 100));
                           $('#L-C-N').html(toLockPartnerManager[1]);
                           $('#L-C-M').html(toDecimal(toLockPartnerManager[2] / 100));
                           if (eval(toLockPartnerManager[0]) > eval(toLockPartnerManager[2])) {
                               $('#R-3').html(s);
                           } else {
                               $('#R-3').html('');
                           }
                           var toTradePartner = share.toTradePartner.split('_');
                           $('#T-P-P').html(toDecimal(toTradePartner[0] / 100));
                           $('#T-P-N').html(toTradePartner[1]);
                           $('#T-P-M').html(toDecimal(toTradePartner[2] / 100));
                           if (eval(toTradePartner[0]) > eval(toTradePartner[2])) {
                               $('#R-4').html(s);
                           } else {
                               $('#R-4').html('');
                           }
                           var toTradePartnerManager = share.toTradePartnerManager.split('_');
                           $('#T-C-P').html(toDecimal(toTradePartnerManager[0] / 100));
                           $('#T-C-N').html(toTradePartnerManager[1]);
                           $('#T-C-M').html(toDecimal(toTradePartnerManager[2] / 100));
                           if (eval(toTradePartnerManager[0]) > eval(toTradePartnerManager[2])) {
                               $('#R-5').html(s);
                           } else {
                               $('#R-5').html('');
                           }

                           $('.recover').css('display', 'block');
                           $('.recover2').css('display', 'none');
                       } else {
                           $('.recover').css('display', 'none');
                           $('.recover2').css('display', 'block');
                       }

                   }
               });
    }

    function refundSubmit() {
        console.log(selectOrderSid);
        var code = $("input[name='validateCode']").val();
        if (code == null || code == '') {
            alert("请输入验证码");
            return false
        }
        $.post("/user/validate", {phoneNumber: phoneNumber, code: code},
               function (res) {
                   if (res.status == 200) {
                       $("input[name='validateCode']").val('');
                       var force = $('input[type="radio"][name="RecoveryStrategy"]:checked').val();
                       $.post("/manage/scanCodeOrder/refundSubmit",
                              {orderSid: selectOrderSid, force: force},
                              function (res) {
                                  if (res.status == 200) {
                                      $('#refund-status').html("退款成功！");
                                  } else {
                                      $('#refund-status').html("退款失败！");
                                      $('#refund-msg').html(res.msg);
                                  }
                                  $("#Refund").modal("hide");
                                  $("#Refund_end").modal("toggle");
                              })
                   } else {
                       alert(res.msg)
                   }
               })

    }

    var scanCodeOrderCriteria = {};
    var flag = true;
    var init1 = 0;
    var orderContent = document.getElementById("orderContent");
    $(function () {
        // tab切换
        $('#myTab li:eq(0) a').tab('show');
        // 提示框
        $(".deleteWarn").click(function () {
            $("#deleteWarn").modal("toggle");
        });
        $(".createWarn").click(function () {
            $("#createWarn").modal("toggle");
        });
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
        });
        scanCodeOrderCriteria.offset = 1;
        getOrderByAjax(scanCodeOrderCriteria);
    });
    function getOrderByAjax(scanCodeOrderCriteria) {
        orderContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/scanCodeOrder/orderList",
                   async: false,
                   data: JSON.stringify(scanCodeOrderCriteria),
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
                           initPage(scanCodeOrderCriteria.offset, totalPage);
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       var orderContent = document.getElementById("orderContent");
                       var contentStr = '';
                       for (var i = 0; i < content.length; i++) {
                           contentStr += '<tr><td>' + content[i].orderSid + '</td>';
                           if (content[i].completeDate == null) {
                               contentStr +=
                                   '<td><span>未完成的订单</span></td>';
                           } else {
                               contentStr +=
                                   '<td><span>'
                                   + new Date(content[i].completeDate).format('yyyy-MM-dd HH:mm:ss')
                                   + '</span></td>';
                           }
                           contentStr +=
                               '<td><span>' + content[i].merchant.name + '</span><br><span>('
                               + content[i].merchant.merchantSid + ')</span></td>';
                           if (content[i].leJiaUser.phoneNumber != null) {
                               contentStr +=
                                   '<td><span>' + content[i].leJiaUser.phoneNumber
                                   + '</span><br><span>('
                                   + content[i].leJiaUser.userSid + ')</span></td>'
                           } else {
                               contentStr +=
                                   '<td><span>未绑定手机号</span><br><span>('
                                   + content[i].leJiaUser.userSid + ')</span></td>'
                           }
                           var typeDetail = '';
                           switch (eval(content[i].orderType)) {
                               case 12001:
                                   typeDetail = '非会员普通订单（普通商户）';
                                   break;
                               case 12002:
                                   typeDetail = '会员普通订单（普通商户）';
                                   break;
                               case 12003:
                                   typeDetail = '非会员普通订单（联盟商户）';
                                   break;
                               case 12004:
                                   typeDetail = '导流订单';
                                   break;
                               case 12005:
                                   typeDetail = '会员订单（佣金费率）';
                                   break;
                               case 12006:
                                   typeDetail = '会员订单（普通费率）';
                                   break;
                               default:
                                   typeDetail = '未知类型';
                           }
                           contentStr += '<td>' + typeDetail + '</td>';
                           contentStr += '<td>' + content[i].totalPrice / 100 + '</td>';
                           contentStr += '<td>' + content[i].truePay / 100 + '</td>';
                           contentStr += '<td>' + content[i].trueScore / 100 + '</td>';
                           if (content[i].scanCodeOrderExt.payType == 0) {
                               contentStr += '<td>微信</td>';
                           } else if (content[i].scanCodeOrderExt.payType == 1) {
                               contentStr += '<td>支付宝</td>';
                           } else {
                               contentStr += '<td>未知</td>';
                           }
                           contentStr += '<td>￥' + content[i].commission / 100 + '</td>';
                           contentStr += '<td>￥' + content[i].transferMoney / 100 + '</td>';
                           contentStr +=
                               '<td>￥' + content[i].scanCodeOrderExt.thirdCommission / 100
                               + '</td>';
                           contentStr += '<td>' + content[i].rebate / 100 + '</td>';
                           contentStr += '<td>' + content[i].scoreC / 100 + '</td>';

                           contentStr += '<td>' + content[i].share / 100 + '</td>';
                           var state = eval(content[i].state);
                           switch (state) {
                               case 0:
                                   contentStr += '<td>未付款</td>';
                                   break;
                               case 1:
                                   contentStr += '<td>已支付</td>';
                                   break;
                               case 2:
                                   contentStr += '<td>已退款</td>';
                                   break;
                               default:
                                   contentStr += '<td>未知</td>';
                           }
//                           if (state == 1) {
//                               contentStr +=
//                                   '<td><button class="btn btn-primary" onclick="refund(\''
//                                   + content[i].orderSid + '\')">退款</button></td></tr>';
//                           } else if (state == 2) {
//                               contentStr +=
//                                   '<td><button class="btn btn-primary" onclick="showInfo(\''
//                                   + content[i].orderSid + '\')">详情</button></td></tr>';
//                           }
                       }
                       orderContent.innerHTML += contentStr;
                   }
               });
    }
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             scanCodeOrderCriteria.offset = p;
                                             init1 = 0;
                                             getOrderByAjax(scanCodeOrderCriteria);
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
    function searchOrderByCriteria() {
        scanCodeOrderCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            scanCodeOrderCriteria.startDate = startDate;
            scanCodeOrderCriteria.endDate = endDate;
        }
        if ($("#payment").val() != -1) {
            scanCodeOrderCriteria.payment = $("#payment").val();
        } else {
            scanCodeOrderCriteria.payment = null;
        }
        if ($("#customer-ID").val() != "" && $("#customer-ID").val() != null) {
            scanCodeOrderCriteria.userSid = $("#customer-ID").val();
        } else {
            scanCodeOrderCriteria.userSid = null;
        }
        if ($("#customer-tel").val() != "" && $("#customer-tel").val() != null) {
            scanCodeOrderCriteria.phoneNumber = $("#customer-tel").val();
        } else {
            scanCodeOrderCriteria.phoneNumber = null;
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            scanCodeOrderCriteria.merchantName = $("#merchant-name").val();
        } else {
            scanCodeOrderCriteria.merchantName = null;
        }
        if ($("#orderType").val() != -1) {
            scanCodeOrderCriteria.orderType = $("#orderType").val();
        } else {
            scanCodeOrderCriteria.orderType = null;
        }
        if ($("#order-SID").val() != "" && $("#order-SID").val() != null) {
            scanCodeOrderCriteria.orderSid = $("#order-SID").val();
        } else {
            scanCodeOrderCriteria.orderSid = null;
        }
        if ($("#source").val() != -1) {
            scanCodeOrderCriteria.source = $("#source").val();
        } else {
            scanCodeOrderCriteria.source = null;
        }
        if ($("#gatewayType").val() != -1) {
            scanCodeOrderCriteria.gatewayType = $("#gatewayType").val();
        } else {
            scanCodeOrderCriteria.gatewayType = null;
        }
        if ($("#payType").val() != -1) {
            scanCodeOrderCriteria.payType = $("#payType").val();
        } else {
            scanCodeOrderCriteria.payType = null;
        }
        if ($("#merchantNum").val() != "" && $("#merchantNum").val() != null) {
            scanCodeOrderCriteria.merchantNum = $("#merchantNum").val();
        } else {
            scanCodeOrderCriteria.merchantNum = null;
        }

        getOrderByAjax(scanCodeOrderCriteria);
    }
    function searchOrderByState(state) {
        scanCodeOrderCriteria.offset = 1;
        if (state != null) {
            scanCodeOrderCriteria.state = state;
        } else {
            scanCodeOrderCriteria.state = null;
        }
        flag = true;
        getOrderByAjax(scanCodeOrderCriteria);
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
    function exportExcel() {
        if (scanCodeOrderCriteria.state == null) {
            scanCodeOrderCriteria.state = null;
        }
        if (scanCodeOrderCriteria.startDate == null) {
            scanCodeOrderCriteria.startDate = null;
        }
        if (scanCodeOrderCriteria.endDate == null) {
            scanCodeOrderCriteria.endDate = null;
        }
        if (scanCodeOrderCriteria.userSid == null) {
            scanCodeOrderCriteria.userSid = null;
        }
        if (scanCodeOrderCriteria.phoneNumber == null) {
            scanCodeOrderCriteria.phoneNumber = null;
        }
        post("/manage/scanCodeOrder/export", scanCodeOrderCriteria);
    }

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

    function getVerify() {
        $("#sendCode").addClass("disClick");
        f_timeout();
        $.post("/user/sendCode", {
            phoneNumber: phoneNumber,
            type: 3
        }, function (res) {
            if (res.status != 200) {
                alert(res.msg);
            }
        });
    }
    function f_timeout() {
        $('#sendCode').attr('onclick', '');
        $('#sendCode').html('<span id="timeb2">60</span>秒后重发');
        $('#sendCode').attr({disabled: 'true'});
        timer = self.setInterval(addsec, 1000)
    }
    function addsec() {
        var t = $('#timeb2').html();
        if (t > 0) {
            $('#timeb2').html(t - 1)
        } else {
            window.clearInterval(timer);
            $("#sendCode").removeClass("disClick");
            $('#sendCode').html('<span id="timeb2"></span>重获验证码');
            $('#sendCode').attr({disabled: false});
            $('#sendCode').attr('onclick', 'getVerify()');
        }
    }


</script>
</body>
</html>