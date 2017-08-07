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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
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
            width: 60%;
            margin: 0 auto;
            display: none;
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
    <%@include file="../../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main">
            <h1>退款申请</h1>
            <div class="container-fluid" style="padding-top: 50px">

                <div class="row" style="padding-left: 340px">

                    <div class="form-group col-md-2">
                        <label for="orderSid">订单编号</label>
                        <input type="text" name="orderSid" class="form-control"/>
                    </div>

                    <div class="form-group col-md-4">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="query()">查询
                        </button>
                    </div>

                    <div class="form-group col-md-3"></div>
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
                        <div>消费后发放：</div>
                        <div><span id="rebate"></span>元鼓励金+<span id="scoreC"></span>金币</div>
                    </div>
                    <hr style="height:1px;border:none;border-top:1px dashed #cCC;"/>
                    <div class="Mod-10">
                        <div>实付退款金额：</div>
                        <div><span id="truePay"></span>元</div>
                    </div>
                    <div class="Mod-10">
                        <div>鼓励金退款金额：</div>
                        <div><span id="trueScore"></span>元</div>
                    </div>
                    <div class="Mod-10">
                        <div>实际可追回鼓励金：</div>
                        <div>+<span id="realRebate"></span>元</div>
                    </div>
                    <div class="Mod-10">
                        <div>实际可追回金币：</div>
                        <div><span id="realScoreC"></span></div>
                    </div>
                    <div class="Mod-10">
                        <div>退款后鼓励金变更：</div>
                        <div><span id="scoreAChange"></span>元 <span>消费者鼓励金余额<span
                                id="userScoreA"></span></span>
                        </div>
                    </div>
                    <div class="Mod-10">
                        <div>退款后金币变更：</div>
                        <div><span id="scoreCChange"></span> <span>消费者金币余额<span
                                id="userScoreC"></span></span>
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
                                <div><span id="L-M-N"></span>【余额：<span
                                        id="L-M-M"></span>元】<span
                                        id="R-1" class="recover-r"></span></div>
                            </div>
                            <div>
                                <div>会员锁定天使合伙人 —<span id="L-P-P"></span>元</div>
                                <div><span id="L-P-N"></span>【余额：<span
                                        id="L-P-M"></span>元】<span
                                        id="R-2"></span></div>
                            </div>
                            <div>
                                <div>会员锁定城市合伙人 —<span id="L-C-P"></span>元</div>
                                <div><span id="L-C-N"></span>【余额：<span
                                        id="L-C-M"></span>元】<span
                                        id="R-3"></span></div>
                            </div>
                            <div>
                                <div>交易门店锁定天使人 —<span id="T-P-P"></span>元</div>
                                <div><span id="T-P-N"></span>【余额：<span
                                        id="T-P-M"></span>元】<span
                                        id="R-4"></span></div>
                            </div>
                            <div>
                                <div>交易门店锁定城市人 —<span id="T-C-P"></span>元</div>
                                <div><span id="T-C-N"></span>【余额：<span
                                        id="T-C-M"></span>元】<span
                                        id="R-5"></span></div>
                            </div>
                        </div>
                    </div>
                    <div class="Mod-10">
                        <div>追回策略：</div>
                        <div>
                            <div><input type="radio" name="RecoveryStrategy"
                                        checked="checked"
                                        value="1"><span>若账户余额不足以抵扣，则无法退款。</span>
                            </div>
                            <div><input type="radio"
                                        name="RecoveryStrategy"
                                        value="0"><span>若账户余额不足以抵扣，则会扣减到0。</span></div>
                        </div>
                    </div>
                    <div class="Mod-10">
                        <div>校验密码：</div>
                        <div>
                            <%--<span>15501066812</span><span--%>
                            <%--style="margin: 0 2% 0 5%">验证码</span>--%>
                            <input name="validateCode">
                        </div>
                        <%--<button class="ModButton ModButton_ordinary ModRadius"--%>
                        <%--onclick="getVerify()"--%>
                        <%--id="sendCode">发送验证码--%>
                        <%--</button>--%>
                    </div>
                    <div class="recover_button">
                        <button class="ModButton ModButton_ordinary ModRadius Refund_end"
                                onclick="refundSubmit()">确认退款
                        </button>
                        <button class="ModButton ModButton_back ModRadius"
                                data-dismiss="modal">取消
                        </button>
                    </div>
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
<script>
    var orderSid = '';
    function query() {
        orderSid = $('input[name="orderSid"]').val();
        $.ajax({
                   type: "get",
                   url: "/manage/refund/refund/" + orderSid,
                   async: false,
                   contentType: "application/json",
                   success: function (map) {
                       if (map.status != 200) {
                           alert(map.msg);
                       } else {
                           var order = map.order;
                           var user = map.user;
                           var userScoreA = map.userScoreA;
                           var userScoreC = map.userScoreC;
                           var merchant = map.merchant;
                           var share = map.share;
                           $('#orderSid').html(order.orderSid);
                           $('#merchantName').html(merchant.name);
                           if (user.phoneNumber != null && user.phoneNumber != '') {
                               $('#userInfo').html(user.phoneNumber + '(' + user.userSid + ')');
                           } else {
                               $('#userInfo').html(user.userSid);
                           }
                           $('#priceInfo').html(toDecimal(order.totalPrice / 100) + '元（实付'
                                                + toDecimal(order.truePay / 100) + '元+'
                                                + toDecimal(order.trueScore / 100) + '元鼓励金）');
                           $('#rebate').html(toDecimal(order.rebate / 100));
                           $('#scoreC').html(toDecimal(order.scoreC / 100));
                           $('#truePay').html(toDecimal(order.truePay / 100));
                           $('#trueScore').html(toDecimal(order.trueScore / 100));
                           var scoreAChange = order.trueScore - order.rebate;
                           if (scoreAChange >= 0) {
                               $('#scoreAChange').html('+' + toDecimal(scoreAChange / 100));
                           } else {
                               $('#scoreAChange').html('-' + toDecimal(scoreAChange / 100));
                           }
                           $('#scoreCChange').html('-' + toDecimal(order.scoreC / 100));
                           $('#userScoreA').html(toDecimal(userScoreA / 100) + '元');
                           $('#userScoreC').html(toDecimal(userScoreC / 100));

                           if ((userScoreA + order.trueScore - order.rebate) < 0) {
                               $('#userScoreA').html($('#userScoreA').html() + '(鼓励金余额不足)');
                               $('#userScoreA').css('color', 'red');
                               $('#realRebate').html(toDecimal(userScoreA / 100));
                               $('#realRebate').css('color', 'red');
                           } else {
                               $('#realRebate').html(toDecimal(order.rebate / 100));
                           }
                           if ((userScoreC - order.scoreC) < 0) {
                               $('#userScoreC').html($('#userScoreC').html() + '(金币余额不足)');
                               $('#userScoreC').css('color', 'red');
                               $('#realScoreC').html(toDecimal(userScoreC / 100));
                               $('#realScoreC').css('color', 'red');
                           } else {
                               $('#realScoreC').html(toDecimal(order.scoreC / 100));
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
                               if (eval(toTradePartnerManager[0]) > eval(
                                       toTradePartnerManager[2])) {
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

                           $('.FYOrder').css('display', 'block');
                       }

                   }
               });

    }

    function refundSubmit() {
        var code = $("input[name='validateCode']").val();
        if (code == null || code == '') {
            alert("请输入密码");
            return false
        }
        var shareRecoverType = $('input[type="radio"][name="RecoveryStrategy"]:checked').val();
        $.post("/manage/refund/refundSubmit",
               {orderSid: orderSid, shareRecoverType: shareRecoverType, pwd: code},
               function (res) {
                   if (res.status == 200) {
                       alert("退款成功！");
                       window.location.href = '/manage/refund/ledger/list';
                   } else {
                       alert(res.msg);
                   }
               })
    }
</script>
</body>
</html>

