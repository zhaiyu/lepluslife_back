<%@include file="../commen.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    html, body, div, p, form, label, ul, li, dl, dt, dd, ol, img, button, h1, h2, h3, h4, h5, h6, b, em, strong, small {
        margin: 0;
        padding: 0;
        border: 0;
        list-style: none;
        font-style: normal;
        font-weight: normal;
    }

    dl, dt, dd {
        display: block;
        margin: 0;
        padding: 0
    }

    a {
        text-decoration: none;
    }

    body {
        font-family: Microsoft YaHei, Verdana, Arial, SimSun;
        color: #666;
        font-size: 14px;
        background: #f6f6f6;
        overflow: hidden
    }

    .block, #block {
        display: block;
    }

    .none, #none {
        display: none;
    }

    .left_menu {
        float: left;
        width: 200px;
        background: #32323a;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
        overflow: auto;
        padding-bottom: 150px;
    }

    .left_menu ul li {
        display: inline;
    }

    .left_menu ul li .list-item a {
        width: 230px;
        padding-left: 65px;
        text-decoration: none;
        font-size: 14px;
        color: #f5f5f5;
        line-height: 30px;
        display: block;
    }

    .left_menu ul li a.noline {
        border-bottom: none;
    }

    .left_menu ul li a:hover {
        color: #fff;
    }

    .left_menu ul li a.selected:hover {
        color: #fff;
    }

    .left_menu ul li h4 {
        cursor: pointer;
        background: url(${resourceUrl}/images/bg1.png) no-repeat 90% 18px;
        padding-left: 10px;
        text-decoration: none;
        font-size: 14px;
        color: #f5f5f5;
        display: block;
        line-height: 48px;
        font-weight: normal;
    }

    .left_menu ul li.noline {
        border-bottom: none;
    }

    .left_menu ul li.selected h4 {
        background-position: 270px -45px;
    }

    .left_menu li .list-item {
        padding: 5px 0;
        position: relative;
        zoom: 1;
        background: #387FC1;
        overflow: hidden;
    }

    .left_menu h4 span {
        display: block;
        float: left;
        width: 35px;
        height: 26px;
        margin-right: 10px;
        padding-right: 10px;
        background-repeat: no-repeat;
        margin-top: 12px;
    }

    .M1 span {
        background: url(${resourceUrl}/images/ioc.png) 0 -6px;
    }

    .M2 span {
        background: url(${resourceUrl}/images/ioc.png) -36px -6px;
    }

    .M3 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -40px;
    }

    .M4 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -70px;
    }

    .M5 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -70px;
    }

    .M6 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -70px;
    }

    .M7 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -70px;
    }

    .M8 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -70px;
    }

    .M9 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -70px;
    }

    .M10 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -70px;
    }

    .M11 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -70px;
    }

    .M12 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -70px;
    }

    .M13 span {
        background: url(${resourceUrl}/images/ioc.png) -72px -70px;
    }
</style>
<script type="text/javascript" src="${resourceUrl}/js/menu.js"></script>
<div class="left_menu">
    <ul id="nav_dot">
        <shiro:hasPermission name="index:query">
            <li><h4 class="M2"><span></span><a style="color: #f5f5f5; text-decoration: none;"  
                                               href="/manage/index">首页</a></h4></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="product:query">
            <li><h4 class="M2"><span></span>臻品商城</h4>

                <div class="list-item none none2">
                    <shiro:hasPermission name="topic:query">
                        <a href="/manage/topic">专题模块</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="product:query">
                        <a href="/manage/product">商品管理</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="product:query">
                        <a href="/manage/limit?type=1">限量秒杀</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="onLineOrder:query"> <a
                            href='/manage/order'>订单管理</a> </shiro:hasPermission>
                </div>
            </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="merchant:query">
        <li><h4 class="M2"><span></span>乐+商户</h4> </shiro:hasPermission>
            <div class="list-item none none3">
                <shiro:hasPermission name="merchant:query">
                    <a href="/manage/merchant">商户管理</a>
                </shiro:hasPermission>

                <a href="/manage/pos">pos管理</a>
            </div>
        </li>
        <shiro:hasPermission name="order:query">
            <li><h4 class="M4"><span></span>交易结算</h4>

                <div class="list-item none none1">
                    <shiro:hasPermission name="offLineOrder:query"> <a
                            href='/manage/offLineOrder'>扫码订单</a> </shiro:hasPermission>
                    <shiro:hasPermission name="financial:query"> <a
                            href='/manage/financial'>财务结算</a> </shiro:hasPermission>
                    <shiro:hasPermission name="share:query"> <a
                            href='/manage/offLineOrder/share'>佣金分润</a> </shiro:hasPermission>
                    <a
                            href='/manage/pos_order'>pos订单</a>
                </div>
            </li>
        </shiro:hasPermission>

        <shiro:hasPermission name="lj_user:query">
            <li><h4 class="M6"><span></span>人员管理</h4>

                <div class="list-item none none4">
                    <shiro:hasPermission name="lj_user:query"> <a
                            href='/manage/user'>会员管理</a> </shiro:hasPermission>
                    <shiro:hasPermission name="partner:query"> <a
                            href='/manage/partner'>合伙人</a> </shiro:hasPermission>
                    <shiro:hasPermission name="SalesStaff:query"> <a
                            href='/manage/sales'>销售人员</a> </shiro:hasPermission>
                </div>
            </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="app_manage:query">
            <li><h4 class="M6"><span></span>APP管理</h4>

                <div class="list-item none none5">
                    <shiro:hasPermission name="app_manage:query"> <a
                            href='/manage/banner?type=1'>APP推荐</a> </shiro:hasPermission>
                </div>
            </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="market_center:query">
            <li><h4 class="M6"><span></span>营销中心</h4>

                <div class="list-item none none6">
                    <shiro:hasPermission name="market_center:query"> <a
                            href='/manage/codeBurse'>送红包活动</a> </shiro:hasPermission>
                    <a href='/manage/activity/initial_order_rebate'>首单返红包</a>
                    <shiro:hasPermission name="app_manage:query">
                        <a href='/manage/merchant/codePage'>商户邀请码</a>
                    </shiro:hasPermission>
                </div>
            </li>
        </shiro:hasPermission>

        <li><h4 class="M6"><span></span>审核流程</h4>

            <div class="list-item none none8">
                <a href='/manage/withdrawBill'>提现审核</a>
            </div>
        </li>
        <shiro:hasPermission name="system_config:query">
            <li><h4 class="M6"><span></span>系统设置</h4>

                <div class="list-item none none7">
                    <shiro:hasPermission name="button_manage:query"> <a
                            href='/manage/weixin/menu/list'>自定义菜单</a> </shiro:hasPermission>
                    <shiro:hasPermission name="wx_reply:query"> <a
                            href='/manage/weixin/reply/list'>微信回复规则</a> </shiro:hasPermission>
                    <shiro:hasPermission name="multiple_message:query"> <a
                            href='/manage/weixin/imageText'>群消息列表</a> </shiro:hasPermission>
                    <shiro:hasPermission name="management:query"> <a
                            href='/manage/managementUserList'>权限管理</a> </shiro:hasPermission>
                </div>
            </li>
        </shiro:hasPermission>
        <li><h4 class="M6"><span></span>数据中心</h4>

            <div class="list-item none none9">
                <a href='/manage/member_data'>会员分析</a>
                <a href='/manage/merchant_data'>商户数据</a>
                <a href='/manage/scoreAAccountPage'>红包账户</a>
                <a href='/manage/transactionAnalysisPage'>交易分析</a>
            </div>
        </li>
    </ul>
</div>
<script>navList(12);</script>
<script>
    $(function () {
        var url = window.location.href;
        if (url.indexOf("withdrawBill") != -1
        ) {
            $(".none8").parent('li').addClass('selected');
            $(".none8").slideDown(300);
        }
        if (url.indexOf("offLineOrder") != -1
            || url.indexOf("financial") != -1 || url.indexOf("/manage/pos_order") != -1) {
            $(".none1").parent('li').addClass('selected');
            $(".none1").slideDown(300);
        }
        if ((url.indexOf("/manage/product") != -1 || url.indexOf("/manage/order") != -1
             || url.indexOf("/manage/topic") != -1) || url.indexOf("productRec") != -1) {
            $(".none2").parent('li').addClass('selected');
            $(".none2").slideDown(300);
        }
        if ((url.indexOf("/manage/merchant") != -1 || url.indexOf("merchantRec") != -1
             || url.indexOf("/manage/pos") != -1) && url.indexOf("/manage/merchant_data") == -1) {
            $(".none3").parent('li').addClass('selected');
            $(".none3").slideDown(300);
        }
        if (url.indexOf("/manage/banner") != -1) {
            $(".none5").parent('li').addClass('selected');
            $(".none5").slideDown(300);
        }
        if (url.indexOf("partner") != -1 || url.indexOf("/manage/user") != -1
            || url.indexOf("sales") != -1) {
            $(".none4").parent('li').addClass('selected');
            $(".none4").slideDown(300);
        }
        if (url.indexOf("codeBurse") != -1 || url.indexOf("initial_order_rebate") != -1
            || url.indexOf("codePage") != -1) {
            $(".none6").parent('li').addClass('selected');
            $(".none6").slideDown(300);
        }
        if (url.indexOf("/weixin/menu/list") != -1 || url.indexOf("/weixin/reply/list") != -1
            || url.indexOf("/weixin/imageText") != -1 || url.indexOf("managementUserList") != -1) {
            $(".none7").parent('li').addClass('selected');
            $(".none7").slideDown(300);
        }
        if (url.indexOf("/manage/member_data") != -1 || url.indexOf("/manage/merchant_data")
                                                        != -1|| url.indexOf("/manage/scoreAAccountPage") != -1||url.indexOf("/manage/transactionAnalysisPage") != -1) {
            $(".none9").parent('li').addClass('selected');
            $(".none9").slideDown(300);
        }
    })
</script>
