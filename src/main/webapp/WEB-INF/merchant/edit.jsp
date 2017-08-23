<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 2017/8/19
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
    <title>${merchant.name}--门店编辑</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <c:set var="resourceUrl" value="http://www.lepluslife.com/resource/backRes"></c:set>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
    <link type="text/css" rel="stylesheet"
          href="${resourceUrl}/merchantUser/css/create-edit-store.css"/>
    <style>
        #yibaoLedgerList {
            display: none;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.min.js"></script>
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
            <div class="ModRadius" onclick="history.back()"> < 返回</div>
            <c:if test="${createOrEdit == 0}"><p>门店创建</p></c:if>
            <c:if test="${createOrEdit == 1}"><p>门店编辑</p></c:if>
        </div>
        <div class="create_edit-body">
            <div class="MODInput_row">
                <div class="Mod-2">所属商户</div>
                <div class="Mod-5 Mod-5_lineCenter">${merchantUser.name}</div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">所属合伙人</div>
                <div class="Mod-5 Mod-5_lineCenter">${merchantUser.partner.name}</div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">所属销售</div>
                <div class="Mod-5">
                    <select id="sales" class="hhr">
                        <option value="0">--暂无--</option>
                        <c:forEach items="${sales}" var="sale">
                            <option value="${sale.id}">${sale.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">门店名称</div>
                <div class="Mod-5">
                    <input type="text" class="create_edit-input" id="name"
                           value="${merchant.name}" placeholder="请输入店铺名称，最长不超过16位"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">所在城市</div>
                <div class="Mod-5">
                    <select id="locationCity">
                        <option value="-1">请选择城市</option>
                    </select>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">所在区域</div>
                <div class="Mod-5">
                    <select id="locationArea">
                        <option value="-1">请先选择所在城市</option>
                    </select>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">详细地址</div>
                <div class="Mod-5">
                    <input type="text" class="create_edit-input" id="location"
                           value="${merchant.location}"
                           placeholder="请输入详细地址，最长不超过16位"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">门店分类</div>
                <div class="Mod-5">
                    <select class="hhr" id="merchantType">
                        <c:forEach items="${merchantTypes}" var="merchantType">
                            <option value="${merchantType.id}">${merchantType.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">门店联系人</div>
                <div class="Mod-5">
                    <input type="text" class="create_edit-input" id="contact"
                           value="${merchant.contact}"
                           placeholder="请输入门店联系人"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">门店联系电话</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" id="merchantPhone"
                           value="${merchant.merchantPhone}"
                           placeholder="请输入门店联系人"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">门店锁定上限</div>
                <div class="Mod-5">
                    <input type="text" class="create_edit-input" id="userLimit"
                           value="${merchant.userLimit}"
                           placeholder="请输入锁定会员的名额上限，不能低于当前已锁定会员"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">分润权限</div>
                <div class="Mod-5">
                    <input type="checkbox" style="width: auto;margin: 0" id="openOnLineShare"
                           value=""/>
                    <span>开启线上分润</span>
                    <input type="checkbox" style="width: auto;margin: 0;" id="openOffLineShare"
                           value=""/>
                    <span>开启线下分润</span>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">红包收取权限</div>
                <div class="Mod-5">
                    <input type="radio" name="receiptAuth" id="receiptAuth-1" value="1">
                    <span>已开通</span>
                    <input type="radio" name="receiptAuth" id="receiptAuth-0" value="0"  checked>
                    <span>未开通</span>
                </div>
            </div>

            <div class="MODInput_row create_edit-contractType">
                <div class="Mod-2">签约类型</div>
                <div class="Mod-5 ModRadio2 create_edit-typeChose">
                    <div class="ModRadius-left ModRadius2_active">普通协议</div>
                    <div class="ModRadius-right create_edit-typeOfLM">联盟协议</div>
                </div>
            </div>
            <div class="MODInput_row create_edit-SettlementMethod">
                <div class="Mod-2">扫码结算方式</div>
                <div class="Mod-5 ModRadio2 create_edit-payChose">
                    <div class="ModRadius-left ModRadius2_active">乐加结算</div>
                    <c:if test="${display_show_yb}">
                        <div class="ModRadius-right">易宝结算</div>
                    </c:if>
                </div>
            </div>
            <c:if test="${display_show_yb}">
                <div class="MODInput_row" id="yibaoLedgerList">
                    <div class="Mod-2">易宝子商编</div>
                    <div class="Mod-5">
                        <select class="hhr" name="ledgerList">
                            <option value="0">----请选择易宝子商户号---</option>
                            <c:forEach items="${ledgerList}" var="ledger">
                                <option value="${ledger.id}">${ledger.ledgerNo}+${ledger.linkman}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </c:if>
            <div class="MODInput_row">
                <div class="Mod-2">订单费率</div>
                <div class="Mod-7">
                    <div>
                        <span>乐加订单费率</span>
                        <input name="ljCommission" class="Mod-1 ModRadius create_edit-for2"
                               type="number" step="0.01" value="${merchant.ljCommission}">
                        <span>%</span>
                        <span>普通订单费率</span>
                        <input name="ljBrokerage" class="Mod-1 ModRadius create_edit-for2"
                               type="number" step="0.01" value="${merchant.ljBrokerage}">
                        <span>%</span>
                    </div>
                </div>
            </div>
            <div class="MODInput_row create_edit-filling">
                <div class="Mod-2">账户类型</div>
                <div class="Mod-5 ModRadio2 create_edit-typeChose_">
                    <div class="ModRadius-left ModRadius2_active">法人私账</div>
                    <div class="ModRadius-right">对公账户</div>
                </div>
            </div>
            <div class="MODInput_row ModFilling">
                <div class="Mod-2">结算卡号</div>
                <div class="Mod-5"><input id="bankNumber" class="create_edit-input"
                                          placeholder="请输入结算卡号"
                                          value="${merchant.merchantBank.bankNumber}"></div>
            </div>
            <div class="MODInput_row ModFilling">
                <div class="Mod-2">开户支行</div>
                <div class="Mod-5"><input id="bankName" class="create_edit-input"
                                          placeholder="请输入开户支行"
                                          value="${merchant.merchantBank.bankName}"></div>
            </div>
            <div class="MODInput_row ModFilling">
                <div class="Mod-2">收款人/主体</div>
                <div class="Mod-5"><input id="payee" class="create_edit-input"
                                          placeholder="请输入收款人/账户主体"
                                          value="${merchant.payee}">
                </div>
            </div>
            <div class="MODInput_row ModButtonMarginDown">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <c:if test="${createOrEdit == 0}">
                        <button class="ModButton ModButton_ordinary ModRadius" onclick="submit()">
                            确认创建门店
                        </button>
                    </c:if>
                    <c:if test="${createOrEdit == 1}">
                        <button class="ModButton ModButton_ordinary ModRadius" onclick="submit()">
                            修改提交
                        </button>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<script type="text/javascript" src="${resourceUrl}/js/Mod/common.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/jquery.combo.select.js"></script>
<!--file预览js-->
<script type="text/javascript" src="${resourceUrl}/js/Mod/ModFilePreview.js"></script>
<!--强制保留2位小数js-->
<script type="text/javascript" src="${resourceUrl}/js/Mod/RetainDecimalFor2.js"></script>
<script>
    var partnership = 0; //协议类型          新建时默认选择普通协议      0=普通协议|1=联盟协议
    var scanPayWay = 1;  //扫码支付方式       新建时默认选择暂不开通     1=乐加结算|3=易宝结算
    var merchantBankType = 0;   //账户类型   新建时默认法人私账         0=法人私账|2=对公账号
    var createOrEdit = eval('${createOrEdit}');//0=创建|1=修改
    /*****************************修改时加载数据****************************/
    function init() {
        //所属销售
        if (${salesStaff != null}) {
            $("#sales option[value=${salesStaff.id}]").attr("selected", true);
        }
        //所在城市、区域
        autoAreas(0, '${merchant.city.id}');
        $("#locationCity option[value=${merchant.city.id}]").attr("selected", true);
        $("#locationArea option[value=${merchant.area.id}]").attr("selected", true);
        //门店分类
        $("#merchantType option[value=${merchant.merchantType.id}]").attr("selected", true);
        //线上线下分润是否开启
        if (${scanPayWay.openOnLineShare==1}) {
            $("#openOnLineShare").attr("checked", true);
        }
        if (${scanPayWay.openOffLineShare==1}) {
            $("#openOffLineShare").attr("checked", true);
        }
        //红包收取权限
        if (${merchant.receiptAuth == 1}) {
            $("#receiptAuth-1").attr("checked", true);
        } else {
            $("#receiptAuth-0").attr("checked", true);
        }
        //签约类型，默认普通
        if (${merchant.partnership==1}) {  //联盟协议
            partnership = 1;
            $(".create_edit-typeChose > div").removeClass("ModRadius2_active");
            $(".create_edit-typeChose .ModRadius-right").addClass("ModRadius2_active");
        }
        //扫码结算方式，默认乐加
        if (${scanPayWay.type == 3}) { //易宝结算
            scanPayWay = 3;
            $(".create_edit-payChose > div").removeClass("ModRadius2_active");
            $(".create_edit-payChose .ModRadius-right").addClass("ModRadius2_active");
            $("select[name='ledgerList'] option[value=${merchantLedger.merchantUserLedger.id}]").attr(
                "selected", true);
            $('#yibaoLedgerList').css('display', 'block');
        }
        //账户类型
        if (${merchant.merchantBank!=null}) {//账户类型  0=法人私账|2=对公账号
            if (${merchant.merchantBank.type==2}) { //对公账户
                merchantBankType = 2;
                $(".create_edit-typeChose_ > div").removeClass("ModRadius2_active");
                $(".create_edit-typeChose_ .ModRadius-right").addClass("ModRadius2_active");
            }
        }
    }
    if (createOrEdit === 1) {
        init();
    } else {
        $('#contact').val('${merchantUser.linkMan}');
        $('#merchantPhone').val('${merchantUser.phoneNum}');
        autoAreas(0, -1);
        $('#locationArea').empty();
        $("input[name=ljCommission]").attr("disabled", true).val('');
    }
    /******************************创建或修改提交************************************/
    function submit() {
        var merchantDto = {};
        var merchantUser = {};
        var merchant = {};
        var merchantBank = {};
        var partner = {};
        var salesStaff = {};
        var merchantScanPayWay = {};
        var merchantLedger = {};
        if (createOrEdit === 1) { //修改
            merchant.id = '${merchant.id}';
            merchantScanPayWay.id = '${scanPayWay.id}';
            merchantBank.id = '${merchant.merchantBank.id}';
        }
        partner.id = '${merchantUser.partner.id}';
        merchant.partner = partner;
        merchantUser.id = ${merchantUser.id};
        merchant.merchantUser = merchantUser;
        //所属销售
        salesStaff.id = $("#sales").val();
        if (salesStaff.id == 0) {
            merchant.salesStaff = null;
        } else {
            merchant.salesStaff = salesStaff;
        }
        //门店名称
        var merchantName = $("#name").val();
        if (merchantName == null || merchantName == '') {
            alert('请输入门店名称！');
            return;
        }
        merchant.name = merchantName;
        //所在城市
        var city = {};
        if ($("#locationCity").val() == -1) {
            alert('请选择城市');
            return;
        }
        city.id = $("#locationCity").val();
        merchant.city = city;
        //所在区域
        var area = {};
        if ($("#locationArea").val() == -1) {
            alert('请选择区域');
            return;
        }
        area.id = $("#locationArea").val();
        merchant.area = area;
        //详细地址
        var location = $("#location").val();
        if (location == null || location == '') {
            alert('请输入详细地址');
            return;
        }
        merchant.location = location;
        //门店分类
        var merchantType = {};
        merchantType.id = $("#merchantType").val();
        merchant.merchantType = merchantType;
        //门店联系人
        var contact = $("#contact").val();
        if (contact == null || contact == '') {
            alert('请输入门店联系人');
            return;
        }
        merchant.contact = contact;
        //门店联系电话
        var merchantPhone = $("#merchantPhone").val();
        if (merchantPhone == null || merchantPhone == '') {
            alert('请输入门店联系电话');
            return;
        }
        merchant.merchantPhone = $("#merchantPhone").val();
        //门店锁定上限
        var userLimit = $("#userLimit").val();
        if (userLimit == null || userLimit == '') {
            alert('请输入门店锁定上限');
            return;
        }
        merchant.userLimit = userLimit;
        //分润权限
        if ($('#openOnLineShare').is(':checked')) {
            merchantScanPayWay.openOnLineShare = 1;
        } else {
            merchantScanPayWay.openOnLineShare = 0;
        }
        if ($('#openOffLineShare').is(':checked')) {
            merchantScanPayWay.openOffLineShare = 1;
        } else {
            merchantScanPayWay.openOffLineShare = 0;
        }
        //收取红包权限
        merchant.receiptAuth = $("input[name='receiptAuth']:checked").val();
        //签约类型
        merchant.partnership = partnership;
        //结算方式
        merchantScanPayWay.type = scanPayWay;
        if (scanPayWay === 3) { //易宝结算
            //易宝商编
            var ledgerId = $("select[name='ledgerList']").val();
            if (ledgerId == 0) {
                alert("请选择易宝子商编");
                return;
            }
            merchantLedger.merchant = merchant;
            var merchantUserLedger = {};
            merchantUserLedger.id = ledgerId;
            merchantLedger.merchantUserLedger = merchantUserLedger;
        }
        //普通订单费率
        var ljBrokerage = $("input[name=ljBrokerage]").val();
        if (ljBrokerage == null || ljBrokerage == "" || ljBrokerage > 100 || ljBrokerage
                                                                             < 0) {
            alert("请输入正确的手续费");
            return;
        }
        merchant.ljBrokerage = ljBrokerage;
        //联盟协议 乐加订单费率
        if (partnership === 1) {
            var ljCommission = $("input[name=ljCommission]").val();
            if (ljCommission == null || ljCommission == "" || ljCommission > 100 || ljCommission
                                                                                    < 0) {
                alert("请输入正确的手续费");
                return;
            }
            merchant.ljCommission = ljCommission;
            merchant.memberCommission = ljCommission;
            merchantScanPayWay.commission = ljCommission;
        } else {
            merchant.ljCommission = ljBrokerage;
            merchant.memberCommission = ljBrokerage;
            merchantScanPayWay.commission = ljBrokerage;
        }
        //结算类型和卡号
        var payee = $("#payee").val();
        merchantBank.payee = payee;
        merchant.payee = payee;
        merchantBank.bankNumber = $("#bankNumber").val();
        merchantBank.bankName = $("#bankName").val();
        if (merchantBank.bankName == "" || merchantBank.bankNumber == null) {
            alert("请输入开户支行");
            return;
        }
        if (merchantBank.bankNumber == "" || merchantBank.bankNumber == null) {
            alert("请输入结算卡号");
            return;
        }
        if (payee == null || payee == "") {
            alert("请输入收款人/账户主体");
            return;
        }
        merchant.merchantBank = merchantBank;
        /*********************************************  ********/
        merchantDto.merchant = merchant;
        merchantDto.merchantScanPayWay = merchantScanPayWay;
        merchantDto.merchantLedger = merchantLedger;
        $('.ModButton_ordinary').attr('onclick', '');
        if (createOrEdit === 0) {//创建
            $.ajax({
                       type: "post",
                       url: "/manage/merchant",
                       contentType: "application/json",
                       data: JSON.stringify(merchantDto),
                       success: function (data) {
                           alert(data.msg);
                           if (data.status == 200) {
                               setTimeout(function () {
                                   window.location.href =
                                       "/manage/merchantUser/info/${merchantUser.id}?li=2";
                               }, 0);
                           } else {
                               $('.ModButton_ordinary').attr('onclick', 'submit()');
                           }
                       }
                   });
        } else {//修改
            $.ajax({
                       type: "put",
                       url: "/manage/merchant",
                       contentType: "application/json",
                       data: JSON.stringify(merchantDto),
                       success: function (data) {
                           alert(data.msg);
                           if (data.status == 200) {
                               setTimeout(function () {
                                   window.location.href =
                                       "/manage/merchantUser/info/${merchantUser.id}?li=2";
                               }, 0);
                           } else {
                               $('.ModButton_ordinary').attr('onclick', 'submit()');
                           }
                       }
                   });

        }
    }
    /******************************选择城市后自动填充区域************************************/
    //onChange=1表示选择选择城市时加载，onChange=0表示一开始有城市时加载
    //cityId 表示选择的城市ID，cityId=-1时表示新建门店
    function autoAreas(onChange, cityId) {
        $.ajax({
                   type: 'GET',
                   url: '/manage/city/ajax',
                   async: false,
                   dataType: 'json',
                   success: function (data) {
                       if (cityId == -1) {
                           var tt1 = '', tt2 = '';
                           $.each(data, function (i) {
                               tt1 +=
                                   '<option value="' + data[i].id + '">' + data[i].name
                                   + '</option>';
                           });
                           $.each(data[0].areas, function (j) {
                               tt2 +=
                                   '<option value="' + data[0].areas[j].id + '">'
                                   + data[0].areas[j].name
                                   + '</option>';
                           });
                           $('#locationCity').append(tt1);
                           $('#locationArea').empty().append(tt2);
                       } else {
                           if (onChange == 1) {
                               $.each(data, function (i) {
                                   if (data[i].id == cityId) {
                                       var dataStr = '';
                                       $.each(data[i].areas, function (j) {
                                           dataStr +=
                                               '<option value="' + data[i].areas[j].id + '">'
                                               + data[i].areas[j].name + '</option>';
                                       });
                                       $('#locationArea').empty().append(dataStr);

                                   }
                               });
                           } else {
                               var dataStr1 = '', dataStr2 = '';
                               $.each(data, function (i) {
                                   dataStr1 +=
                                       '<option value="' + data[i].id + '">' + data[i].name
                                       + '</option>';
                                   if (data[i].id == cityId) {
                                       $.each(data[i].areas, function (j) {
                                           dataStr2 +=
                                               '<option value="' + data[i].areas[j].id + '">'
                                               + data[i].areas[j].name
                                               + '</option>';
                                       });
                                   }
                               });
                               $('#locationCity').empty().append(dataStr1);
                               $('#locationArea').empty().append(dataStr2);
                           }
                       }
                   },
                   error: function (jqXHR) {
                       alert('发生错误：' + jqXHR.status);
                   }
               });
    }
    $('#locationCity').change(function () {
        var val = $(this).val();
        autoAreas(1, val);
    });
    /***********************保留2位小数*************************************/
    function toDecimal(x) {
        var f = parseFloat(x);
        if (isNaN(f)) {
            return false;
        }
        f = Math.round(x * 100) / 100;
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
    var $payType = $(".create_edit-SettlementMethod .ModRadio2 > div");//扫码结算方式
    var $partnership = $(".create_edit-contractType .ModRadio2 > div");//协议类型
    var $bankType = $(".create_edit-filling .ModRadio2 > div");//账户类型

    //下拉模糊查询
    $(function () {
        $('.hhr').comboSelect();
    });
    //保留2位小数
    $(".create_edit-for2").blur(function () {
        var newVal = toDecimal($(this).val());
        if (newVal > 100) {
            newVal = 100;
        } else if (newVal < 0) {
            newVal = 0;
        }
        $(this).val(newVal);
    });

    //扫码结算方式
    $payType.click(function () {
        var val = $(this).html();
        switch (val) {
            case "乐加结算":
                scanPayWay = 1;
                $("#yibaoLedgerList").hide();
                break;
            case "易宝结算":
                scanPayWay = 3;
                $("#yibaoLedgerList").show();
                break;
        }
    });
    //协议类型
    $partnership.click(function () {
        var val = $(this).html();
        switch (val) {
            case "普通协议":
                partnership = 0;
                $("input[name=ljCommission]").attr("disabled", true).val('');
                break;
            case "联盟协议":
                partnership = 1;
                $("input[name=ljCommission]").attr("disabled", false);
                break;
        }
    });
    //账户类型
    $bankType.click(function () {
        var val = $(this).html();
        switch (val) {
            case "法人私账":
                merchantBankType = 0;
                break;
            case "对公账户":
                merchantBankType = 2;
                break;
        }
    });
</script>
</body>
</html>
