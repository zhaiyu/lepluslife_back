<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 2017/1/5
  Time: 14:02
  创建或修改商户
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
    <link type="text/css" rel="stylesheet"
          href="${resourceUrl}/merchantUser/css/merchantManagement.css"/>
    <link type="text/css" rel="stylesheet"
          href="${resourceUrl}/merchantUser/css/merchantInformation.css"/>

    <script src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script src="${resourceUrl}/js/respond.min.js"></script>

    <script type="text/javascript" src="${resourceUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/Mod/common.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.combo.select.js"></script>
    <!--layer-->
    <script type="text/javascript"
            src="${resourceUrl}/js/layer/laydate/laydate.js"></script>
    <!--echarts-->
    <script type="text/javascript" src="${resourceUrl}/js/echarts.min.js"></script>
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
            <div class="ModRadius"> < 返回商户列表</div>
            <p>商户创建/编辑</p>
        </div>
        <div class="MODInput_row merchant_information-tab6">
            <input type="hidden" id="id" value="${m.id}"/>

            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">商户名称</div>
                <div class="Mod-6">
                    <input id="merchantName" class="ModRadius-right"
                           placeholder="请输入商户名称"
                           value="${m.merchantName}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">所属合伙人</div>
                <div class="Mod-6">
                    <select class="hhr" id="toggle-partner">
                        <option value="">- 请选择 -</option>
                        <c:forEach items="${partners}" var="partner">
                            <option value="${partner.id}">${partner.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">负责人姓名</div>
                <div class="Mod-6">
                    <input id="linkMan" class="ModRadius-right" placeholder="请输入负责人姓名"
                           value="${m.linkMan}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">负责人手机号</div>
                <div class="Mod-6">
                    <input id="phoneNum" class="ModRadius-right" placeholder="请输入负责人手机号"
                           value="${m.phoneNum}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">主营省市</div>
                <div class="Mod-1" style="margin-left: 2%">
                    <select class="ModRadius" id="selCity">
                        <option value=""> -- 请选择 --</option>
                        <c:forEach items="${citys}" var="city">
                            <option value="${city.id}">${city.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">会员锁定上限</div>
                <div class="Mod-2">
                    <input id="lockLimit" type="number" class="ModRadius-right"
                           placeholder="会员锁定上限不得小于当前锁定值" value="${m.lockLimit}">
                </div>
                <div class="Mod-1">
                    <span style="line-height: 36px" id="hasLock"></span>
                </div>
            </div>
            <p>佣金结算账户</p>

            <div class="Mod-10 merchant_information-filling">
                <div class="ModLabel ModRadius-left">账户类型</div>
                <div class="ModRadio2">
                    <div id="type0" class="ModRadius-left ModRadius2_active">法人私账</div>
                    <div id="type2" class="ModRadius-right">对公账户</div>
                </div>
            </div>
            <p>管理员账号密码</p>

            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">登录账号</div>
                <div class="Mod-6">
                    <input id="name" class="ModRadius-right" placeholder="4-16位汉字、字母或数字"
                           value="${m.name}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">登录密码</div>
                <div class="Mod-6">
                    <input id="password" class="ModRadius-right"
                           placeholder="4-16位字母或数字">
                </div>
            </div>
            <div class="Mod-2">
                <button class="ModButton ModButton_ordinary ModRadius merchant_information-save"
                        onclick="save()">
                    保存基本信息
                </button>
            </div>
        </div>
    </div>
</div>

<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>


<!--添加提示框-->
<div class="modal" id="merchant_information-save">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">话费产品</h4>
            </div>
            <div class="merchant_layer">
                <p>基本信息保存成功！</p>
                <button class="ModButton ModButton_ordinary ModRadius" data-dismiss="modal">知道了
                </button>
            </div>
        </div>
    </div>
</div>

<script>
    var type = 0; //卡类型
    /*****************保存商户****************************/
    function save() {
        var id = $('#id').val();
        var merchantUser = {};
        var merchantName = $("#merchantName").val();
        var linkMan = $("#linkMan").val();
        var phoneNum = $("#phoneNum").val();
        var cardNum = $("#bankNumber").val();
        var bankName = $("#bankName").val();
        var payee = $("#payee").val();
        var lockLimit = $("#lockLimit").val();
        var name = $("#name").val();
        var password = $("#password").val();
        var selCity = $("#selCity").val();
        var city = {};
        city.id = selCity;
        var partner = {};
        var partnerId = $("#toggle-partner").val();
        partner.id = partnerId;
        if (merchantName == null || merchantName == '') {
            alert("请输入商户名称");
            return;
        }
        if (linkMan == null || linkMan == '') {
            alert("请输入商户负责人");
            return;
        }
        if (phoneNum == null || phoneNum == '') {
            alert("请输入绑定手机号");
            return;
        }
        if (name == null || name == '') {
            alert("请输入登录用户名");
            return;
        }
        if (password == null || password == '' || password.length < 4 || password.length > 16) {
            if (id == null || id == '') {
                alert("请输入登录密码（4-16）位");
                return;
            } else if(password != '*********************'){
                alert("请输入登录密码（4-16）位");
                return;
            }
        } else {
            merchantUser.password = password;
        }
        if (cardNum == null || cardNum == '') {
            alert("请输入佣金结算卡号");
            return;
        }
        if (bankName == null || bankName == '') {
            alert("请选择开户行");
            return;
        }
        if (payee == null || payee == '') {
            alert("请输入账户主体");
            return;
        }
        if (lockLimit == null || lockLimit == '' || lockLimit < 0) {
            alert("请输入具体的会员锁定上限");
            return;
        }
        if (selCity == null || selCity == '') {
            alert("请选中所在城市");
            return;
        }
        if (partnerId == null || partnerId == '') {
            alert("请选择合伙人");
            return;
        }
        merchantUser.id = id;
        merchantUser.merchantName = merchantName;
        merchantUser.linkMan = linkMan;
        merchantUser.name = name;
        merchantUser.phoneNum = phoneNum;
        merchantUser.lockLimit = lockLimit;
        var merchantBank = {};
        merchantBank.bankNumber = cardNum;
        merchantBank.bankName = bankName;
        merchantBank.payee = payee;
        merchantBank.type = type;
        merchantUser.merchantBank = merchantBank;
        merchantUser.city = city;
        merchantUser.partner = partner;

        //  修改商户信息
        if (merchantUser.id != null && merchantUser.id != '') {
            $.ajax({
                       url: "/manage/merchantUser/edit",
                       type: "put",
                       contentType: "application/json",
                       data: JSON.stringify(merchantUser),
                       success: function (result) {
                           alert(result.data);
                           window.location.href = "/manage/merchantUser/list";
                       }
                   });
            //  保存商户信息
        } else {
            if (checkNameRepeat()) {             //  校验用户名是否重复
                return;
            }
            $.ajax({
                       url: "/manage/merchantUser/create",
                       type: "post",
                       contentType: "application/json",
                       data: JSON.stringify(merchantUser),
                       success: function (result) {
                           alert(result.data);
                           window.location.href = "/manage/merchantUser/list";
                       }
                   });
        }
    }

    function checkNameRepeat() {
        var username = $("#name").val();
        var flag = false;
        if (username != null && username != '') {
            $.ajax({
                       url: "/manage/merchantUser/check_username",
                       type: "post",
                       contentType: "application/json",
                       data: username,
                       async: false,
                       success: function (result) {
                           if (result.status != 200) {
                               alert(result.msg);
                               $("#name").val('');
                               flag = true;
                           }
                       }
                   });
        }
        return flag;
    }

    /*****************选择不同的佣金结算账户JS****************************/
    function merchant_informationSwitch(val) {
        switch (val) {
            case "法人私账":
                type = 0;
                $(".ModFilling").remove();
                $(".merchant_information-filling").after(filling("收款人", "payee"));
                $(".merchant_information-filling").after(filling("开户支行", "bankName"));
                $(".merchant_information-filling").after(filling("结算卡号", "bankNumber"));
                break;
            case "对公账户":
                type = 2;
                $(".ModFilling").remove();
                $(".merchant_information-filling").after(filling("账户主体", "payee"));
                $(".merchant_information-filling").after(filling("开户支行", "bankName"));
                $(".merchant_information-filling").after(filling("账号", "bankNumber"));
                break;
        }
    }

    $(".ModRadio2 > div").click(function () {
        $(".ModRadio2 > div").removeClass("ModRadius2_active");
        $(this).addClass("ModRadius2_active");
        var thisVal = $(this).html();
        merchant_informationSwitch(thisVal);
    });

    var m = '${m}';
    if (m != null && m != '') {
        $('#password').val('*********************');
        if (${m.partner != null}) {
            $("#toggle-partner").val(${m.partner.id});
        }
        if (${m.city != null}) {
            $("#selCity").val(${m.city.id});
        }
        $('#hasLock').html('当前锁定' + ${hasLock == null ? 0 : hasLock});
        if (${m.merchantBank != null}) {
            if (${m.merchantBank.type == 0}) {
                merchant_informationSwitch("法人私账");
            } else {
                $(".ModRadio2 > div").removeClass("ModRadius2_active");
                $('#type2').addClass("ModRadius2_active");
                merchant_informationSwitch("对公账户");
            }
            $('#payee').val('${m.merchantBank.payee}');
            $('#bankName').val('${m.merchantBank.bankName}');
            $('#bankNumber').val('${m.merchantBank.bankNumber}');
        }
    } else {
        var reVal = $(".ModRadius2_active").html();
        merchant_informationSwitch(reVal);
    }

</script>
</body>
</html>

