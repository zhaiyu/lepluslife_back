<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/7/21
  Time: 下午3:19
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/create-edit-store.css"/>
    <style>
        .thumbnail {
            width: 160px;
            height: 160px;
        }

        .thumbnail img {
            width: 100%;
            height: 100%;
        }
    </style>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
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
        <div class="create_edit-title">
            <div class="ModRadius"><a href="/manage/partner" style="color: white;text-decoration: none"> 返回合伙人管理</a></div>
            <p>城市合伙人</p>
        </div>
        <div class="create_edit-body">
            <div class="MODInput_row">
                <div class="Mod-2">合伙人姓名</div>
                <div class="Mod-5">
                    <input type="hidden"  id="pmId"  value="${partnerManager.id}"/>
                    <input type="text"  id="partnerName" class="create_edit-input" value="${partnerManager.name}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">联系电话</div>
                <div class="Mod-5">
                    <input type="text" id="partnerPhone" class="create_edit-input" value="${partnerManager.phoneNumber}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">所在城市</div>
                <div class="Mod-5">
                    <select class="hhr" id="parterCity">
                        <option value="0">请选择城市</option>
                        <option value="1">鞍山</option>
                        <option value="2">北京</option>
                        <option value="3">沈阳</option>
                    </select>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">锁定会员上限</div>
                <div class="Mod-5">
                    <input type="text" id="bindUserLimit" class="create_edit-input" value="${partnerManager.userLimit}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">锁定门店上限</div>
                <div class="Mod-5">
                    <input type="text" id="bindMerchantLimit" class="create_edit-input" value="${partnerManager.bindMerchantLimit}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">锁定天使合伙人上限</div>
                <div class="Mod-5">
                    <input type="text" id="bindPartnerLimit" class="create_edit-input" value="${partnerManager.bindPartnerLimit}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">佣金结算账号</div>
                <div class="Mod-5">
                    <input type="text" id="bankNumber" class="create_edit-input" value="${partnerManager.bankNumber}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">收款人</div>
                <div class="Mod-5">
                    <input type="text" id="payee" class="create_edit-input" value="${partnerManager.payee}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">开户行</div>
                <div class="Mod-5">
                    <input type="text" id="bankName" class="create_edit-input" value="${partnerManager.bankName}"/>
                </div>
            </div>
            <div class="MODInput_row ModButtonMarginDown">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <button class="ModButton ModButton_ordinary ModRadius" onclick="save()">保存信息</button>
                </div>
            </div>
        </div>
</div>
<div id="bottomIframe">
        <%@include file="../common/bottom.jsp" %>
</div>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    loadCitys();
    function loadCitys() {
        $.ajax({
            type: 'GET',
            url: '/manage/city/ajax',
            async: false,
            dataType: 'json',
            success: function (data) {
                console.log(data[0]);
                var dataStr1 = '',
                        dataStr2 = '';
                $.each(data, function (i) {
                    dataStr1 +=
                            '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                });
                $('#parterCity').empty().append(dataStr1);
            }
        });
    }

    function save() {
        var partnerManager = {};
        if ($("#partnerName").val() == null || $("#partnerName").val() == "") {
            alert("请输入城市合伙人姓名");
            return;
        }
        if ($("#partnerPhone").val() == null || $("#partnerPhone").val() == "") {
            alert("请输入城市合伙人电话");
            return;
        }
        if ($("#bindMerchantLimit").val() == null || $("#bindMerchantLimit").val() == "") {
            alert("请输入锁定门店限制");
            return;
        }
        if ($("#bindUserLimit").val() == null || $("#bindUserLimit").val() == "") {
            alert("请输入锁定会员限制");
            return;
        }
        if ($("#bindPartnerLimit").val() == null || $("#bindPartnerLimit").val() == "") {
            alert("请输入锁定天使合伙人限制");
            return;
        }
        if ($("#payee").val() == null || $("#payee").val() == "") {
            alert("请输入收款人");
            return;
        }
        if ($("#bankName").val() == null || $("#bankName").val() == "") {
            alert("请输入开户行");
            return;
        }
        if ($("#bankNumber").val() == null || $("#bankNumber").val() == "") {
            alert("请输入银行卡号");
            return;
        }
        partnerManager.id = $("#pmId").val();
        partnerManager.name = $("#partnerName").val();
        partnerManager.phoneNumber = $("#partnerPhone").val();
        partnerManager.merchantLimit = $("#merchantLimit").val();
        partnerManager.userLimit = $("#bindUserLimit").val();
        partnerManager.bindPartnerLimit = $("#bindPartnerLimit").val();
        partnerManager.bindMerchantLimit = $("#bindMerchantLimit").val();
        var city = {};
        city.id = $("#parterCity").val();
        partnerManager.city = city;
        partnerManager.payee = $("#payee").val();
        partnerManager.bankNumber = $("#bankNumber").val();
        partnerManager.bankName = $("#bankName").val();

        $.ajax({
            type: "put",
            url: "/manage/cityPartner/save",
            contentType: "application/json",
            data: JSON.stringify(partnerManager),
            success: function (data) {
                alert(data.msg);
                location.href = "/manage/partner";
            }
        });

    }
</script>
</body>
</html>
