<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 2017/7/13
  Time: 14:02
  创建或修改易宝商户
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../commen.jsp" %>
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
    <link type="text/css" rel="stylesheet"
          href="${resourceUrl}/merchantUser/css/create-edit-store.css"/>

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
    <style>
        .fontColor {
            color: red;
        }
    </style>
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
        <div class="create_edit-title">
            <div class="ModRadius" onclick="back()"> < 返回易宝商户管理</div>
            <p>易宝商户创建/编辑</p>
        </div>
        <div class="MODInput_row merchant_information-tab6">
            <input type="hidden" id="merchantUserId" value="${m.id}"/>
            <input type="hidden" id="ledgerId" value="${ledgerId}"/>

            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">积分客商户<span class="fontColor">*</span></div>
                <div class="Mod-6">
                    <input id="merchantName" class="ModRadius-right"
                           value="${m.merchantName}" disabled>
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">绑定手机号<span class="fontColor">*</span></div>
                <div class="Mod-6">
                    <input id="bindMobile" class="ModRadius-right" placeholder="请输入手机号"
                           value="${l.bindMobile}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">联系人姓名<span class="fontColor">*</span></div>
                <div class="Mod-6">
                    <input id="linkman" class="ModRadius-right" placeholder="联系人姓名"
                           value="${l.linkman}">
                </div>
            </div>
            <div class="Mod-10 merchant_information-filling">
                <div class="ModLabel ModRadius-left">注册结算类型<span class="fontColor">*</span></div>
                <div class="ModRadio2 customerType">
                    <div id="customerType_2" class="ModRadius-left ModRadius2_active">企业对公</div>
                    <div id="customerType_1" class="ModRadius-right">个人对私</div>
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">签约名<span class="fontColor">*</span></div>
                <div class="Mod-6">
                    <input id="signedName" class="ModRadius-right"
                           placeholder="企业注册时填写企业名称，个人注册时填写个人姓名"
                           value="${l.signedName}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">身份证号<span class="fontColor">*</span></div>
                <div class="Mod-6">
                    <input id="idCard" class="ModRadius-right"
                           placeholder="企业注册时填写法人身份证，个人注册时填写个人身份证"
                           value="${l.idCard}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">营业执照</div>
                <div class="Mod-6">
                    <input id="businessLicence" class="ModRadius-right"
                           placeholder="企业注册时填写"
                           value="${l.businessLicence}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">法人姓名</div>
                <div class="Mod-6">
                    <input id="legalPerson" class="ModRadius-right"
                           placeholder="企业注册时填写"
                           value="${l.legalPerson}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">银行账户号<span class="fontColor">*</span></div>
                <div class="Mod-6">
                    <input id="bankAccountNumber" class="ModRadius-right"
                           placeholder="对公账户号，对私银行卡号（必须为储蓄卡）"
                           value="${l.bankAccountNumber}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">开户行<span class="fontColor">*</span></div>
                <div class="Mod-6">
                    <input id="bankName" class="ModRadius-right"
                           placeholder="请根据数据字典「中国所有银行支行省市库表.xlsx」，填写中文" value="${l.bankName}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">开户名<span class="fontColor">*</span></div>
                <div class="Mod-6">
                    <input id="accountName" class="ModRadius-right" value="${l.accountName}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">开户省<span class="fontColor">*</span></div>
                <div class="Mod-6">
                    <input id="bankProvince" class="ModRadius-right"
                           placeholder="请根据数据字典「易宝省市编号表.xls」，填写中文" value="${l.bankProvince}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">开户市<span class="fontColor">*</span></div>
                <div class="Mod-6">
                    <input id="bankCity" class="ModRadius-right"
                           placeholder="请根据数据字典「易宝省市编号表.xls」，填写中文" value="${l.bankCity}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">起结金额（元）</div>
                <div class="Mod-5">
                    <input id="minSettleAmount" class="ModRadius-right"
                           placeholder="最小为0.01元"
                           value="${l.minSettleAmount / 100}">
                </div>
            </div>
            <div class="Mod-10 merchant_information-filling">
                <div class="ModLabel ModRadius-left">结算费用承担方</div>
                <div class="ModRadio2 costSide">
                    <div id="costSide_0" class="ModRadius-left ModRadius2_active">主商户</div>
                    <div id="costSide_1" class="ModRadius-right">子商户</div>
                </div>
                <span class="fontColor">&nbsp;&nbsp;注意：修改时,如果仅修改结算费用承担方，无需点击保存信息，点击切换承担方时即时生效！</span>
            </div>

            <div class="Mod-2">
                <button class="ModButton ModButton_ordinary ModRadius merchant_information-save"
                        onclick="save()">
                    保存信息
                </button>
            </div>
        </div>
    </div>
</div>

<div id="bottomIframe">
    <%@include file="../../common/bottom.jsp" %>
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
    function back() {
        window.location.href =
            "/manage/merchantUser/info/" + $('#merchantUserId').val() + '?li=6';
    }
    var customerType = 2; //注册及结算类型  1=PERSON(个人)|| 2=ENTERPRISE(企业)
    var costSide = 0; //结算费用承担方  0=积分客（主商户）|1=子商户
    var ledgerId = $('#ledgerId').val();
    /*****************注册类型****************************/
    $(".customerType > div").click(function () {
        $(".customerType > div").removeClass("ModRadius2_active");
        $(this).addClass("ModRadius2_active");
        switch ($(this).html()) {
            case "个人对私":
                customerType = 1;
                break;
            case "企业对公":
                customerType = 2;
        }
    });
    /*****************结算费用承担方****************************/
    $(".costSide > div").click(function () {
        $(".costSide > div").removeClass("ModRadius2_active");
        $(this).addClass("ModRadius2_active");
        switch ($(this).html()) {
            case "主商户":
                costSide = 0;
                break;
            case "子商户":
                costSide = 1;
        }
        if (eval(ledgerId) !== 0) {
            $.post('/manage/ledger/editCostSide', {ledgerId: ledgerId, costSide: costSide});
        }
    });
    /*****************保存商户****************************/
    function save() {
        var merchantUserLedger = {};
        merchantUserLedger.id = ledgerId;
        var merchantUser = {};
        merchantUser.id = $('#merchantUserId').val();
        merchantUserLedger.merchantUser = merchantUser;
        //填充数据

        //手机号校验
        var bindMobile = $('#bindMobile').val();
        if ((!(/^1[3|4|5|6|7|8]\d{9}$/.test(bindMobile)))) {
            alert("请输入正确的手机号");
            return false
        }
        merchantUserLedger.bindMobile = bindMobile.trim();
        //联系人
        var linkman = $('#linkman').val();
        if (!linkman || linkman.trim().length === 0) {
            alert("联系人不能为空");
            return false
        }
        merchantUserLedger.linkman = linkman.trim();
        //注册类型
        merchantUserLedger.customerType = customerType;
        //结算账户类型
        merchantUserLedger.bankAccountType = customerType;
        //签约名
        var signedName = $('#signedName').val();
        if (!signedName || signedName.trim().length === 0) {
            alert("签约名不能为空");
            return false
        }
        merchantUserLedger.signedName = signedName.trim();
        //身份证号
        var idCard = $('#idCard').val();
        if (!idCard || idCard.trim().length !== 18) {
            alert("身份证号有误");
            return false
        }
        merchantUserLedger.idCard = idCard;
        if (customerType === 2) {
            //营业执照号
            var businessLicence = $('#businessLicence').val();
            if (!businessLicence || businessLicence.trim().length === 0) {
                alert("营业执照号不能为空");
                return false
            }
            merchantUserLedger.businessLicence = businessLicence.trim();
            //法人姓名
            var legalPerson = $('#legalPerson').val();
            if (!legalPerson || legalPerson.trim().length === 0) {
                alert("法人姓名不能为空");
                return false
            }
            merchantUserLedger.legalPerson = legalPerson.trim();
        }
        //银行账户号
        var bankAccountNumber = $('#bankAccountNumber').val();
        if (!bankAccountNumber || bankAccountNumber.trim().length === 0) {
            alert("银行账户号不能为空");
            return false
        }
        merchantUserLedger.bankAccountNumber = bankAccountNumber.trim();
        //开户行
        var bankName = $('#bankName').val();
        if (!bankName || bankName.trim().length === 0) {
            alert("开户行不能为空");
            return false
        }
        merchantUserLedger.bankName = bankName.trim();
        //开户名
        var accountName = $('#accountName').val();
        if (!accountName || accountName.trim().length === 0) {
            alert("开户名不能为空");
            return false
        }
        merchantUserLedger.accountName = accountName.trim();
        //开户省
        var bankProvince = $('#bankProvince').val();
        if (!bankProvince || bankProvince.trim().length === 0) {
            alert("开户省不能为空");
            return false
        }
        merchantUserLedger.bankProvince = bankProvince.trim();
        //开户市
        var bankCity = $('#bankCity').val();
        if (!bankCity || bankCity.trim().length === 0) {
            alert("开户市不能为空");
            return false
        }
        merchantUserLedger.bankCity = bankCity.trim();
        //起结金额
        var minSettleAmount = $('#minSettleAmount').val();
        if (!minSettleAmount || eval(minSettleAmount) < 0.01) {
            alert("请输入大于0.01元的起结金额");
            return false
        }
        merchantUserLedger.minSettleAmount = minSettleAmount * 100;
        //结算费用承担方
        merchantUserLedger.costSide = costSide;

        console.log(JSON.stringify(merchantUserLedger));

        $.ajax({
                   url: "/manage/ledger/edit",
                   type: "post",
                   contentType: "application/json",
                   data: JSON.stringify(merchantUserLedger),
                   success: function (result) {
                       if (eval(result.code) === 1) {
                           alert("保存成功");
                           window.location.href =
                               "/manage/merchantUser/info/" + $('#merchantUserId').val() + '?li=6';
                       } else {
                           alert('保存失败.错误码:' + result.code + '(' + result.msg + ')');
                       }
                   }
               });
    }

    var ledger = '${l}';
    if (ledger != null && ledger != '') {
        if (${l.customerType == 1}) {
            $(".customerType > div").removeClass("ModRadius2_active");
            $("#customerType_1").addClass("ModRadius2_active");
            customerType = 1;
        }
        if (${l.costSide == 1}) {
            $(".costSide > div").removeClass("ModRadius2_active");
            $("#costSide_1").addClass("ModRadius2_active");
            costSide = 1;
        }
        $("#linkman").attr('disabled', 'disabled');
        $("#signedName").attr('disabled', 'disabled');
        $("#idCard").attr('disabled', 'disabled');
        $("#businessLicence").attr('disabled', 'disabled');
        $("#legalPerson").attr('disabled', 'disabled');
        $("#accountName").attr('disabled', 'disabled');
        $(".customerType > div").unbind("click");
    }

</script>
</body>
</html>

