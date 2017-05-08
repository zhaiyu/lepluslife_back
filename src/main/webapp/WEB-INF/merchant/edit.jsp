<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 2017/1/10
  Time: 13:08
  门店创建/编辑页面
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
          href="${resourceUrl}/merchantUser/css/create-edit-store.css"/>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
</head>
<style>
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
            <div class="ModRadius" onclick="history.back()"> < 返回门店管理</div>
            <p>门店创建/编辑</p>
        </div>
        <div class="create_edit-body">
            <div class="MODInput_row">
                <div class="Mod-2">所属商户</div>
                <div class="Mod-5 Mod-5_lineCenter">
                    <input id="merchantManager" class="create_edit-input"  type="text" value="${merchantUser.name}" onblur="toCheckExist()"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">所属合伙人</div>
                <div class="Mod-5 Mod-5_lineCenter">${merchantUser.partner.name}</div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">所属销售</div>
                <div class="Mod-5">
                    <select id="sales" class="hhr">
                        <option value="nullValue">--暂无--</option>
                        <c:forEach items="${sales}" var="sale">
                            <option value="${sale.id}">${sale.name}</option>
                        </c:forEach>

                    </select>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">门店名称</div>
                <div class="Mod-5">
                    <input type="text" class="create_edit-input" id="name" value="${merchant.name}"
                           placeholder="请输入店铺名称，最长不超过16位"/>
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
                    <input type="text" class="create_edit-input" id="merchantPhone"
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
                <div class="Mod-2">红包收取权限</div>
                <div class="Mod-5">
                    <input type="radio" name="receiptAuth" id="receiptAuth-1" value="1"/>
                    <span>已开通</span>
                    <input type="radio" name="receiptAuth" id="receiptAuth-0" value="0" checked/>
                    <span>未开通</span>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">佣金协议</div>
                <div class="Mod-5 create_edit-upDataImg">
                    <c:if test="${merchant.merchantProtocols!=null}">
                        <c:forEach items="${merchant.merchantProtocols}" var="merchantProtocol">
                            <div id="create_edit-add_${merchantProtocol.id}" class="Mod-3">
                                <img id="preview${merchantProtocol.id}" class="fileimg" width="150"
                                     height="150" src="${merchantProtocol.picture}" alt="">

                                <div class="create_edit-upDataImgDelete">
                                    <div onclick="upData(this);" class="ModRadius upData">上传</div>
                                    <input id="doc${merchantProtocol.id}" type="file"
                                           data-url="/manage/file/saveImage" style="display: none"
                                           onchange="setImagePreview('doc${merchantProtocol.id}','preview${merchantProtocol.id}')"/>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <div id="create_edit-add" class="Mod-3">
                        <div class="create_edit-addUpDataImg"><img class="Mod-10"
                                                                   src="${resourceUrl}/merchantUser/img/addImg.png"
                                                                   alt="">
                        </div>
                    </div>
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
                    <div class="ModRadius-left ModRadius2_active">暂不开通</div>
                    <div class="ModRadius-center">乐加结算</div>
                    <div class="ModRadius-right">富友结算</div>
                </div>
            </div>
            <div class="create_edit-SettlementAppend">

            </div>
            <div class="MODInput_row ModButtonMarginDown">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <button id="enter" class="ModButton ModButton_ordinary ModRadius">保存信息</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div style="display: none" id="commonSettlementList">
    <c:if test="${settlementList != null}">
        <c:forEach items="${settlementList}" var="settle">
            <c:if test="${settle.type == 0}">
                <div>
                    <input type="radio" name="commonSettlement-radio"
                           id="commonSettlement-${settle.id}"
                           value="${settle.id}"/>
                    <input type="hidden" value="${settle.commission}"/>
                    <span> ${settle.merchantNum} </span>
                    <span>  ${settle.commission}%  </span>
                    <span> ${settle.payee} </span>
                    <span> ${settle.bankNumber} </span>
                </div>
            </c:if>
        </c:forEach>
    </c:if>
</div>
<div style="display: none" id="allianceSettlementList">

    <c:if test="${settlementList != null}">
        <c:forEach items="${settlementList}" var="settle">
            <c:if test="${settle.type == 1}">
                <div>
                    <input type="radio" name="allianceSettlement-radio"
                           id="allianceSettlement-${settle.id}"
                           value="${settle.id}"/>
                    <input type="hidden" value="${settle.commission}"/>
                    <span> ${settle.merchantNum} </span>
                    <span>  ${settle.commission}% </span>
                    <span> ${settle.payee} </span>
                    <span> ${settle.bankNumber} </span>
                </div>
            </c:if>
        </c:forEach>
    </c:if>

</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<%--******************************不可修改信息************************************--%>
<input type="hidden" id="merchantId" value="${merchant.id}"/>
<input type="hidden" id="merchantBankId" value="${merchant.merchantBank.id}"/>
<input type="hidden" id="merchantRebatePolicyId" value="${merchantRebatePolicy.id}"/>
<input type="hidden" id="merchantUserId" value="${merchantUser.id}"/>
<input type="hidden" id="partnerId" value="${merchantUser.partner.id}"/>
<input type="hidden" id="scanPayWayId" value="${scanPayWay.id}"/>
<input type="hidden" id="storeId" value="${store.id}"/>

</body>
<script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>

<script src="${resourceUrl}/js/html5shiv.min.js"></script>
<script src="${resourceUrl}/js/respond.min.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/Mod/common.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/jquery.combo.select.js"></script>
<script type="text/javascript"
        src="${resourceUrl}/js/layer/laydate/laydate.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/echarts.min.js"></script>

<!--file预览js-->
<script type="text/javascript" src="${resourceUrl}/js/Mod/ModFilePreview.js"></script>
<!--强制保留2位小数js-->
<script type="text/javascript" src="${resourceUrl}/js/Mod/RetainDecimalFor2.js"></script>

<script>
    var partnership = 0; //协议类型，新建时默认选择普通协议
    var scanPayWay = 2;  //扫码支付方式，新建时默认选择暂不开通
    var merchantBankType = 0;   //账户类型  0=法人私账|1=非法人私账|2=对公账号，新建时默认法人私账
    var rebateFlag = 2;   //红包积分发放策略  0-不开启（按比例）  1-开启（全部） 2-不开启，新建时默认不开启
    var SettlementMethodType = $(".create_edit-SettlementMethod .ModRadio2 > div");
    var SettlementAppend = $(".create_edit-SettlementAppend");

    /******************************乐加结算账户类型切换************************************/
    function merchant_informationSwitch(val) {
        switch (val) {
            case "法人私账":
                merchantBankType = 0;
                $(".ModFilling").remove();
                $(".create_edit-filling").after(filling2("收款人", "payee"));
                $(".create_edit-filling").after(filling2("开户支行", "bankName"));
                $(".create_edit-filling").after(filling2("结算卡号", "bankNumber"));
                break;
            case "对公账户":
                merchantBankType = 2;
                $(".ModFilling").remove();
                $(".create_edit-filling").after(filling2("账户主体", "payee"));
                $(".create_edit-filling").after(filling2("开户支行", "bankName"));
                $(".create_edit-filling").after(filling2("账号", "bankNumber"));
                break;
        }
    }
    function yjclInit() {
        if (${merchantRebatePolicy!=null&&merchantRebatePolicy.commissionStages.size()!=0}) {
            var j = "<c:forEach var='commissionStage' items='${merchantRebatePolicy.commissionStages}' varStatus='loop'>";
            j +=
                    "<c:if test="${!loop.last}"><c:if test="${!loop.first}"><div class='w'></c:if><c:if test="${loop.first}"><div></c:if>"
            j +=
                    "<div class='Mod-3'><div class='firstData'>${commissionStage.start/100.0}</div><div>~</div><input type='number' class='Mod-4 lastData' value='${commissionStage.end/100.0}'></div><div class='Mod-3'><span>佣金费率</span><input type='number' value='${commissionStage.commissionScale}' class='Mod-4 collectionData collect_jy'><span>%</span></div>"
            j +=
                    "<div class='Mod-3'><span>金币返点</span><input type='number' value='${commissionStage.scoreCScale}' class='Mod-4 collectionData collect_jb'><span>%</span></div><c:if test="${!loop.first}"><button class='yjcl_del' onclick='yjclDel(this)'>删除</button></c:if></div></c:if>"
            j +=
                    "<c:if test="${loop.last}"><div class='addyjcl_add'><button onclick='addArea(this)'>添加区段</button></div><div><div class='Mod-3'><div>大于</div><div class='finalData'>${commissionStage.start/100.0}</div></div><div class='Mod-3'><span>佣金费率</span><input type='number' value='${commissionStage.commissionScale}' class='Mod-4'><span>%</span></div><div class='Mod-3'><span>金币返点</span><input type='number' class='Mod-4' value='${commissionStage.scoreCScale}'><span>%</span></div></div></c:if>"
            j += "</c:forEach>"
            $(".create_edit-addyjcl").append(j)
        } else {
            yjcl()
        }

    }
    function hbclInit() {
        if (${merchantRebatePolicy!=null&&merchantRebatePolicy.rebateStages.size()!=0}) {
            var j = "<c:forEach var='rebateStage' items='${merchantRebatePolicy.rebateStages}' varStatus='loop'>";
            j +=
                    "<c:if test="${!loop.last}"><c:if test="${!loop.first}"><div class='w'><div class='Mod-3'><div class='firstData'>${rebateStage.start/100.0}</div><div>~</div><input type='number' class='Mod-4 lastData' value='${rebateStage.end/100.0}'></div><div class='Mod-6'><span>送鼓励金</span><input type='number' value='${rebateStage.rebateStart/100.0}' class='Mod-4 collect_start'><span>~</span><input type='number' class='Mod-4 collect_end' value='${rebateStage.rebateEnd/100.0}'></div><button class='yjcl_del' onclick='yjclDel(this)'>删除</button></div></c:if><c:if test="${loop.first}"><div><div class='Mod-3'><div class='firstData'>${rebateStage.start/100.0}</div><div>~</div><input type='number' class='Mod-4 lastData' value='${rebateStage.end/100.0}'></div><div class='Mod-6'><span>送鼓励金</span><input type='number' value='${rebateStage.rebateStart/100.0}' class='Mod-4 collect_start'><span>~</span><input type='number' class='Mod-4 collect_end' value='${rebateStage.rebateEnd/100.0}'></div></div></c:if></c:if>"
            j +=
                    "<c:if test="${loop.last}"><div class='addhbcl_add'><button onclick='addArea1(this)'>添加区段</button></div><div><div class='Mod-3'><div>大于</div><div class='finalData'>${rebateStage.start/100.0}</div></div><div class='Mod-6'><span>送鼓励金</span><input type='number' value='${rebateStage.rebateStart/100.0}' class='Mod-4'><span>~</span><input type='number' value='${rebateStage.rebateEnd/100.0}' class='Mod-4'></div></div></c:if>"
            j += "</c:forEach>"
            $(".create_edit-addhbcl").append(j)
        } else {
            var stages = "${rebateStage}".split("_");
            for (i; i < stages.length - 1; i++) {
                var array = stages[i].replace("(", "").replace(")", "").split(",")
                if (i == 0) {
                    $(".create_edit-addhbcl").append(
                            $("<div></div>").append(
                                    $("<div></div>").attr("class", "Mod-3").append(
                                            $("<div class='firstData'></div>").html(array[0])
                                    ).append(
                                            $("<div></div>").html("~")
                                    ).append(
                                            $("<input>").attr("type", "number").attr("class",
                                                    "Mod-4 lastData").attr("value",
                                                    array[1]).val(array[1])
                                    )
                            ).append(
                                    $("<div></div>").attr("class", "Mod-6").append(
                                            $("<span></span>").html("送鼓励金")
                                    ).append(
                                            $("<input>").attr("type", "number").attr("class",
                                                    "Mod-4").attr("value",
                                                    array[2])
                                    ).append(
                                            $("<span></span>").html("~")
                                    ).append(
                                            $("<input>").attr("type", "number").attr("class",
                                                    "Mod-4").attr("value",
                                                    array[3])
                                    )
                            )
                    )
                } else {
                    $(".create_edit-addhbcl").append(
                            $("<div class='w'></div>").append(
                                    $("<div></div>").attr("class", "Mod-3").append(
                                            $("<div class='firstData'></div>").html(array[0])
                                    ).append(
                                            $("<div></div>").html("~")
                                    ).append(
                                            $("<input>").attr("type", "number").attr("class",
                                                    "Mod-4 lastData").attr("value",
                                                    array[1]).val(array[1])
                                    )
                            ).append(
                                    $("<div></div>").attr("class", "Mod-6").append(
                                            $("<span></span>").html("送鼓励金")
                                    ).append(
                                            $("<input>").attr("type", "number").attr("class",
                                                    "Mod-4 collect_start").attr("value",
                                                    array[2])
                                    ).append(
                                            $("<span></span>").html("~")
                                    ).append(
                                            $("<input>").attr("type", "number").attr("class",
                                                    "Mod-4 collect_end").attr("value",
                                                    array[3])
                                    )
                            )
                    )
                }

            }
            var array = stages[stages.length - 1].replace("(", "").replace(")", "").split(",")
            $(".create_edit-addhbcl").append(
                    $("<div></div>").attr("class", "addhbcl_add").append(
                            $("<button></button>").attr("onclick", "addArea1(this)").html("添加区段")
                    )
            ).append(
                    $("<div></div>").append(
                            $("<div></div>").attr("class", "Mod-3").append(
                                    $("<div></div>").html("大于")
                            ).append(
                                    $("<div></div>").attr("class", "finalData").html(array[0])
                            )
                    ).append(
                            $("<div></div>").attr("class", "Mod-6").append(
                                    $("<span></span>").html("送鼓励金")
                            ).append(
                                    $("<input>").attr("type", "number").attr("class",
                                            "Mod-4").attr("class",
                                            "Mod-4").attr("value",
                                            array[2])
                            ).append(
                                    $("<span></span>").html("~")
                            ).append(
                                    $("<input>").attr("type", "number").attr("class",
                                            "Mod-4").attr("class",
                                            "Mod-4").attr("value",
                                            array[3])
                            )
                    )
            )
        }

    }

    function filling2(title, id) {
        var filling = $("<div></div>").attr("class", "MODInput_row ModFilling").append(
                $("<div></div>").attr("class", "Mod-2").html(title)
        ).append(
                $("<div></div>").attr("class", "Mod-5").append(
                        $("<input>").attr("id", id).attr("class",
                                "create_edit-input").attr("placeholder",
                                "请输入"
                                + title)
                )
        );
        return filling;
    }
    /******************************编辑时初始化数据************************************/
    var commonSettlementList = $('#commonSettlementList').html();
    var allianceSettlementList = $('#allianceSettlementList').html();
    if (${merchant != null}) {
        initProtocol();
        if (${salesStaff != null}) {
            $("#sales option[value=${salesStaff.id}]").attr("selected", true);
        }
        autoAreas(0, '${merchant.city.id}');
        $("#locationCity option[value=${merchant.city.id}]").attr("selected", true);
        $("#locationArea option[value=${merchant.area.id}]").attr("selected", true);
        $("#merchantType option[value=${merchant.merchantType.id}]").attr("selected", true);
        if (${merchant.receiptAuth == 1}) {
            $("#receiptAuth-1").attr("checked", true);
        } else {
            $("#receiptAuth-0").attr("checked", true);
        }
        if (${merchant.partnership==1}) {  //联盟协议
            $(".create_edit-typeChose > div").removeClass("ModRadius2_active");
            $(".create_edit-typeChose .ModRadius-right").addClass("ModRadius2_active");
            partnership = 1;
            if (!$(".create_edit-contractType").next().hasClass("YJ")) { //显示 展示用费率
                addSignedCommission();
                $("input[name=zanShi_commission]").val(${scanPayWay.commission});
            }
            if (${scanPayWay.type == 1}) { //乐加结算
                $(".create_edit-payChose > div").removeClass("ModRadius2_active");
                $(".create_edit-payChose .ModRadius-center").addClass("ModRadius2_active");
                scanPayWay = 1;
                SettlementAppend.empty();
                if (${merchant.memberCommission==merchant.ljCommission}) { //会员订单类型为佣金费率
                    leJia(1);
                    $("#yj-hydd").attr("checked", true);
                    if (${merchantRebatePolicy.rebateFlag==0}) { //红包积分发放策略(按比例)
                        rebateFlag = 0;
                        $("#b-policy").attr("checked", true);
                        $("input[name=lm-userScoreAScale]").val(${merchantRebatePolicy.userScoreAScale});        // 会员发放红包 【比例】
                        $("input[name=lm-userScoreBScale]").val(${merchantRebatePolicy.userScoreBScale});        // 会员发放红包 【比例】
                        $("input[name=lm-userScoreCScale]").val(${merchantRebatePolicy.userScoreCScale});        // 会员发放红包 【比例】
                    }
                    if (${merchantRebatePolicy.rebateFlag==1}) {//红包积分发放策略(全部)
                        rebateFlag = 1;
                        $("#q-policy").attr("checked", true);
                        $("input[name=lm-userScoreBScaleB]").val(${merchantRebatePolicy.userScoreBScaleB});      // 会员发放红包 【全额】
                        $("input[name=lm-userScoreCScaleB]").val(${merchantRebatePolicy.userScoreCScaleB});      // 会员发放红包 【全额】
                    }
                } else {//会员订单类型为普通费率
                    leJia(0);
                    $("#pt-hydd").attr("checked", true);
                }
                if (${merchant.merchantBank!=null}) {//账户类型  0=法人私账|1=非法人私账|2=对公账号
                    if (${merchant.merchantBank.type==2}) {
                        merchantBankType = 2;
                        merchant_informationSwitch("对公账户");
                    } else {
                        merchantBankType = 0;
                        merchant_informationSwitch("法人私账");
                    }
                    $("#bankNumber").val('${merchant.merchantBank.bankNumber}');
                    $("#bankName").val('${merchant.merchantBank.bankName}');
                    $("#payee").val("${merchant.payee}");
                }
            }
            if (${scanPayWay.type == 0}) { //富友结算
                $(".create_edit-payChose > div").removeClass("ModRadius2_active");
                $(".create_edit-payChose .ModRadius-right").addClass("ModRadius2_active");
                scanPayWay = 0;
                SettlementAppend.empty();
                if (${merchant.memberCommission==merchant.ljCommission}) { //会员订单类型为佣金费率
                    fuYou(1);
                    $("#yj-hydd").attr("checked", true);
                    if (${merchantRebatePolicy.rebateFlag==0}) { //红包积分发放策略(按比例)
                        rebateFlag = 0;
                        $("#b-policy").attr("checked", true);
                        $("input[name=lm-userScoreAScale]").val(${merchantRebatePolicy.userScoreAScale});        // 会员发放红包 【比例】
                        $("input[name=lm-userScoreBScale]").val(${merchantRebatePolicy.userScoreBScale});        // 会员发放红包 【比例】
                    }
                    if (${merchantRebatePolicy.rebateFlag==1}) {//红包积分发放策略(全部)
                        rebateFlag = 1;
                        $("#q-policy").attr("checked", true);
                        $("input[name=lm-userScoreBScaleB]").val(${merchantRebatePolicy.userScoreBScaleB});      // 会员发放红包 【全额】
                    }
                } else {//会员订单类型为普通费率
                    fuYou(0);
                    $("#pt-hydd").attr("checked", true);
                }
                if (${store.commonSettlementId != 0}) { //选中默认使用的普通协议
                    var selectComId = 'commonSettlement-${store.commonSettlementId}';
                    $("#" + selectComId).attr("checked", true);
                }
                if (${store.allianceSettlementId != 0}) { //选中默认使用的联盟协议
                    var selectAllId = 'allianceSettlement-${store.allianceSettlementId}';
                    $("#" + selectAllId).attr("checked", true);
                }
            }
            if (${scanPayWay.type == 2}) {  //暂未开通
                //暂时不需要初始化信息
            }
            $("input[name=lm-ljBrokerage]").val(${merchant.ljBrokerage});                       // 普通订单费率
            $("input[name=lm-scoreBRebate]").val(${merchant.scoreBRebate});                     // 普通订单积分返点
            $("input[name=lm-ljCommission]").val(${merchant.ljCommission});                     // 导流订单费率
            $("input[name=lm-scoreARebate]").val(${merchant.scoreARebate});                         // 导流订单红包
            $("input[name=lm-importScoreBScale]").val(${merchantRebatePolicy.importScoreBScale});   // 导流订单积分返点
            $("input[name=lm-importScoreCScale]").val(${merchantRebatePolicy.importScoreCScale});   // 导流订单金币返点
            $("input[name=lm-importShareScale]").val(${merchantRebatePolicy.importShareScale});   // 导流订单金币返点
            $("input[name=lm-memberShareScale]").val(${merchantRebatePolicy.memberShareScale});   // 导流订单金币返点
            if (${merchantRebatePolicy.commissionPolicy == 1}) {
                $(".create_edit-typeChose_").find(".ModRadius-right")[0].className +=
                        " ModRadius2_active";
                $($(".create_edit-typeChose_").find(".ModRadius-left")[0]).removeClass('ModRadius2_active')
                $(".hb_ptcl").fadeOut();
                $(".create_edit-addyjcl").empty();
                $(".hb_ptcl").removeClass("ModRadius2_active");
                $(".hb_glj").addClass("ModRadius2_active");
                $(".create_edit-addhbcl").empty();
                yjclInit();
                hbclInit();

            } else {
                $(".create_edit-typeChose_").find(".ModRadius-left")[0].className +=
                        " ModRadius2_active";
                if (${merchantRebatePolicy.rebatePolicy == 1}) {
                    $(".create_edit-addyjcl").empty();
                    $(".hb_ptcl").removeClass("ModRadius2_active");
                    $(".hb_glj").addClass("ModRadius2_active");
                    $(".create_edit-addhbcl").empty();
                    hbclInit();
                }

            }
        }
        if (${merchant.partnership==0}) { //普通协议
            $(".YJ").remove();
            if (${scanPayWay.type == 1}) { //乐加结算
                $(".create_edit-payChose > div").removeClass("ModRadius2_active");
                $(".create_edit-payChose .ModRadius-center").addClass("ModRadius2_active");
                scanPayWay = 1;
                SettlementAppend.empty();
                leJia_pt();
                if (${merchant.merchantBank!=null}) {//账户类型  0=法人私账|1=非法人私账|2=对公账号
                    if (${merchant.merchantBank.type==2}) {
                        merchantBankType = 2;
                        merchant_informationSwitch("对公账户");
                    } else {
                        merchantBankType = 0;
                        merchant_informationSwitch("法人私账");
                    }
                    $("#bankNumber").val('${merchant.merchantBank.bankNumber}');
                    $("#bankName").val('${merchant.merchantBank.bankName}');
                    $("#payee").val("${merchant.payee}");
                }
            }
            if (${scanPayWay.type == 0}) { //富友结算
                $(".create_edit-payChose > div").removeClass("ModRadius2_active");
                $(".create_edit-payChose .ModRadius-right").addClass("ModRadius2_active");
                scanPayWay = 0;
                SettlementAppend.empty();
                fuYou_pt();
                if (${store.commonSettlementId != 0}) { //选中默认使用的普通协议
                    var selectComId = 'commonSettlement-${store.commonSettlementId}';
                    $("#" + selectComId).attr("checked", true);
                }
                if (${store.allianceSettlementId != 0}) { //选中默认使用的联盟协议
                    var selectAllId = 'allianceSettlement-${store.allianceSettlementId}';
                    $("#" + selectAllId).attr("checked", true);
                }
            }
            if (${scanPayWay.type == 2}) {  //暂未开通
                //暂时不需要初始化信息
            }
            $("input[name=pt-ljCommission]").val(${merchant.ljCommission});
            $("input[name=pt-scoreBRebate]").val(${merchant.scoreBRebate})
        }
    } else {
        $('#contact').val('${merchantUser.linkMan}');
        $('#merchantPhone').val('${merchantUser.phoneNum}');
        autoAreas(0, -1);
        $('#locationArea').empty();
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
                                '<option value="' + data[i].id + '">' + data[i].name + '</option>';
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

    /******************************工具类************************************/
            //    set
    var HtmlType = 0;    //页面类型：创建页面=0 ; 编辑页面=1;

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

    /******************************佣金协议图片上传************************************/
    var i = 0; //当前上传框数量
    function addUpData() {
        var picture = "doc" + i, pic = "preview" + i;
        var inIt = $("<div></div>").attr("class", "Mod-3").append(
                $("<img>").attr('class', 'fileimg').attr("id", pic).attr("width",
                        "150").attr("height",
                        "150").attr("src",
                        "${resourceUrl}/merchantUser/img/noImg.png")
        ).append(
                $("<div></div>").attr("class", "create_edit-upDataImgDelete").append(
                        $("<div></div>").attr("onclick", "upData(this);").attr("class",
                                "ModRadius upData").html("上传")
                ).append(
                        $("<input class='picture'/>").attr('name', 'file').attr("type",
                                "file").attr("data-url",
                                "/manage/file/saveImage").attr("id",
                                picture).attr("style",
                                "display:none;").attr("onchange",
                                "setImagePreview('doc"
                                + i
                                + "','preview"
                                + i
                                + "');")
                )
        );
        $("#create_edit-add").before(inIt);
        i++;
        initProtocol();
        <%--$('#' + picture).fileupload({--%>
        <%--dataType: 'json',--%>
        <%--maxFileSize: 5000000,--%>
        <%--acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,--%>
        <%--add: function (e, data) {--%>
        <%--data.submit();--%>
        <%--},--%>
        <%--done: function (e, data) {--%>
        <%--var resp = data.result;--%>
        <%--$('#' + pic).attr('src',--%>
        <%--'${ossImageReadRoot}/'--%>
        <%--+ resp.data);--%>
        <%--}--%>
        <%--});--%>
    }
    function initProtocol() {
        $('.picture').each(function () {
            $(this).fileupload({
                dataType: 'json',
                maxFileSize: 5000000,
                acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                add: function (e, data) {
                    data.submit();
                },
                done: function (e, data) {
                    var resp = data.result;
                    $(this).parent().parent().find(".fileimg").attr('src',
                            '${ossImageReadRoot}/'
                            + resp.data);
                }
            });
        })
    }
    function upData(onick) {  //上传
        var evt = document.createEvent("MouseEvents");
        evt.initEvent("click", true, true);
        onick.nextSibling.dispatchEvent(evt);
    }
    $("#create_edit-add").click(function (e) {
        var childrenLength = $(".create_edit-upDataImg").children().length;
        if (childrenLength > 5) {
            alert("最多只能上传5张图片");
        } else {
            addUpData();
        }
    });

    /******************************签约类型变更************************************/
    function addSignedCommission() {
        var contractType = $(".create_edit-contractType");
        var typeOfLM = $(".create_edit-typeOfLM");
        var signedCommission = $("<div></div>").attr("class", "MODInput_row YJ").append(
                $("<div></div>").attr("class", "Mod-2").html("签约佣金")
        ).append(
                $("<div></div>").attr("class", "Mod-5").append(
                        $("<input>").attr("name", "zanShi_commission").attr("type",
                                "number").attr("class",
                                "Mod-2 ModRadius create_edit-for2").attr("step",
                                "0.01")
                ).append(
                        $("<span></span>").html("%")
                ).append(
                        $("<span></span>").html(" (该费率纯展示用，不参与实际清算)")
                )
        );
        contractType.after(signedCommission);
    }
    $(".create_edit-typeChose > div").click(function () {
        SettlementAppend.empty();
        if ($(this).html() == "普通协议") {
            partnership = 0;
            $(".YJ").remove();
            if (scanPayWay == 1) {
                leJia_pt();
            } else if (scanPayWay == 0) {
                fuYou_pt();
            }
        } else {
            partnership = 1;
            if (!$(".create_edit-contractType").next().hasClass("YJ")) {
                addSignedCommission();
            }
            if (scanPayWay == 1) {
                leJia(1);
            } else if (scanPayWay == 0) {
                fuYou(1);
            }
        }
    });

    /******************************扫码结算方式变更***********************************/
    SettlementMethodType.click(function () {
        var val = $(this).html();
        SettlementAppend.empty();
        switch (val) {
            case "暂不开通":
                scanPayWay = 2;
                break;
            case "乐加结算":
                scanPayWay = 1;
                if (partnership == 0) {
                    leJia_pt();
                } else {
                    leJia(1);
                }
                break;
            case "富友结算":
                scanPayWay = 0;
                if (partnership == 0) {
                    fuYou_pt();
                } else {
                    fuYou(1);
                }
                break;
        }
    });

    function hyddChange() {//  会员订单费率：佣金费率
        $('#mendiancanshu').append(
                $("<div></div>").attr('id', 'dingdanChange-1').append(
                        $("<input>").attr("type", "radio").attr("name", "policy").attr("id",
                                "b-policy").attr("value",
                                0).attr('checked',
                                true)
                ).append(
                        $("<span></span>").html("按比例发放")
                ).append(
                        $("<span></span>").html("红包比例")
                ).append(
                        $("<input>").attr("name", "lm-userScoreAScale").attr("class",
                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                "number").attr("step",
                                "0.01")
                ).append(
                        $("<span></span>").html("%")
                ).append(
                        $("<span></span>").html("积分返点")
                ).append(
                        $("<input>").attr("name", "lm-userScoreBScale").attr("class",
                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                "number").attr("step",
                                "0.01")
                ).append(
                        $("<span></span>").html("%")
                ).append(
                        $("<span></span>").html("金币返点")
                ).append(
                        $("<input>").attr("name", "lm-userScoreCScale").attr("class",
                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                "number").attr("step",
                                "0.01")
                ).append(
                        $("<span></span>").html("%")
                )
        ).append(
                $("<div></div>").attr('id', 'dingdanChange-2').append(
                        $("<input>").attr("type", "radio").attr("name", "policy").attr("id",
                                "q-policy").attr("value",
                                1)
                ).append(
                        $("<span></span>").html("成本差全数发放(佣金和微信手续费成本的差额，将全部用来发放红包)")
                ).append(
                        $("<span></span>").html("积分返点")
                ).append(
                        $("<input>").attr("name", "lm-userScoreBScaleB").attr("class",
                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                "number").attr("step",
                                "0.01")
                ).append(
                        $("<span></span>").html("%")
                ).append(
                        $("<span></span>").html("金币返点")
                ).append(
                        $("<input>").attr("name", "lm-userScoreCScaleB").attr("class",
                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                "number").attr("step",
                                "0.01")
                ).append(
                        $("<span></span>").html("%")
                )
        );
        $('input[type=radio][name=hydd]').click(function () {
            if ($(this).val() == 0) {
                $('#dingdanChange-1').css('display', 'none');
                $('#dingdanChange-2').css('display', 'none');
            } else {
                $('#dingdanChange-1').css('display', 'block');
                $('#dingdanChange-2').css('display', 'block');
            }
        });
    }
    function leJia(hyddType) { //联盟协议&乐加结算
        SettlementAppend.append(
                $("<div></div>").attr("class", "MODInput_row").append(
                        $("<div></div>").attr("class", "Mod-2").html("门店参数")
                ).append(
                        $("<div></div>").attr("id", "mendiancanshu").attr("class", "Mod-7").append(
                                $("<div></div>").append(
                                        $("<span></span>").html("普通订单费率")
                                ).append(
                                        $("<input>").attr("name", "lm-ljBrokerage")
                                                .attr("class",
                                                        "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                ).append(
                                        $("<span></span>").html("积分返点")
                                ).append(
                                        $("<input>").attr("name", "lm-scoreBRebate")
                                                .attr("class",
                                                        "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                )
                        ).append(
                                $("<div></div>").append(
                                        $("<span></span>").html("导流订单费率")
                                ).append(
                                        $("<input>").attr("name", "lm-ljCommission").attr("class",
                                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                ).append(
                                        $("<span></span>").html("红包比例")
                                ).append(
                                        $("<input>").attr("name", "lm-scoreARebate").attr("class",
                                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                ).append(
                                        $("<span></span>").html("积分返点")
                                ).append(
                                        $("<input>").attr("name",
                                                "lm-importScoreBScale").attr("class",
                                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                ).append(
                                        $("<span></span>").html("金币返点")
                                ).append(
                                        $("<input>").attr("name",
                                                "lm-importScoreCScale").attr("class",
                                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                )
                        ).append(
                                $("<div></div>").append(
                                        $("<span></span>").html("会员订单费率")
                                ).append(
                                        $("<input>").attr("type", "radio").attr("name",
                                                "hydd").attr("id",
                                                "yj-hydd").attr('value',
                                                1).attr('checked',
                                                true)
                                ).append(
                                        $("<span></span>").html("佣金费率")
                                ).append(
                                        $("<input>").attr("type", "radio").attr("name",
                                                "hydd").attr("id",
                                                "pt-hydd").attr('value',
                                                0)
                                ).append(
                                        $("<span></span>").html("普通费率")
                                )
                        )
                )
        ).append(
                $("<div></div>").attr("class", "MODInput_row create_edit-yjcl").append(
                        $("<div></div>").attr("class", "Mod-2").html("佣金策略")
                ).append(
                        $("<div></div>").attr("class",
                                "Mod-5 ModRadio2 create_edit-typeChose_").append(
                                $("<div></div>").attr("class",
                                        "ModRadius-left ModRadius2_active").html("固定策略")
                        ).append(
                                $("<div></div>").attr("class", "ModRadius-right").html("阶梯式收取")
                        )
                )
        ).append(
                $("<div></div>").attr("class", "MODInput_row").append(
                        $("<div></div>").attr("class", "Mod-2").html("")
                ).append(
                        $("<div></div>").attr("class", "Mod-7 create_edit-addyjcl")
                )
        ).append(
                $("<div></div>").attr("class", "MODInput_row create_edit-hbcl").append(
                        $("<div></div>").attr("class", "Mod-2").html("红包策略")
                ).append(
                        $("<div></div>").attr("class",
                                "Mod-5 ModRadio2 create_edit-typeChose_").append(
                                $("<div></div>").attr("class",
                                        "ModRadius-left ModRadius2_active hb_ptcl").html("普通策略")
                        ).append(
                                $("<div></div>").attr("class",
                                        "ModRadius-right hb_glj").html("鼓励金活动")
                        )
                )
        ).append(
                $("<div></div>").attr("class", "MODInput_row").append(
                        $("<div></div>").attr("class", "Mod-2").html("")
                ).append(
                        $("<div></div>").attr("class", "Mod-7 create_edit-addhbcl")
                )
        ).append(
                $("<div></div>").attr("class", "MODInput_row").append(
                        $("<div></div>").attr("class", "Mod-2").html("分润比例")
                ).append(
                        $("<div></div>").attr("class",
                                "Mod-5").append(
                                $("<div></div>").attr("style", "margin:5px 0;").html("佣金类订单参与分润的比例")
                        ).append(
                                $("<div></div>").append(
                                        $("<span></span>").html("导流订单")
                                ).append(
                                        $("<input>").attr("name",
                                                "lm-importShareScale").attr("class",
                                                "Mod-2 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01").attr("style",
                                                "margin-right:0;")
                                ).append(
                                        $("<span></span>").html("%")
                                ).append(
                                        $("<span></span>").html("会员订单（佣金费率）")
                                ).append(
                                        $("<input>").attr("name",
                                                "lm-memberShareScale").attr("class",
                                                "Mod-2 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01").attr("style",
                                                "margin-right:0;")
                                ).append(
                                        $("<span></span>").html("%")
                                )
                        )
                )
        ).append(
                $("<div></div>").attr("class", "MODInput_row create_edit-filling").append(
                        $("<div></div>").attr("class", "Mod-2").html("账户类型")
                ).append(
                        $("<div></div>").attr("class",
                                "Mod-5 ModRadio2 create_edit-typeChose_").append(
                                $("<div></div>").attr("class",
                                        "ModRadius-left ModRadius2_active").html("法人私账")
                        ).append(
                                $("<div></div>").attr("class", "ModRadius-right").html("对公账户")
                        )
                )
        );
        if (hyddType == 1) {//  会员订单费率：佣金费率
            hyddChange();
        }
        $(".create_edit-yjcl .create_edit-typeChose_ > div").click(function () {
            var val = $(this).html();
            $(".create_edit-addyjcl").empty();
            switch (val) {
                case "固定策略":
                    $(".hb_ptcl").fadeIn();
                    $(".create_edit-addyjcl").empty();
                    break;
                case "阶梯式收取":
                    $(".hb_ptcl").fadeOut();
                    $(".create_edit-addyjcl").empty();
                    $(".hb_ptcl").removeClass("ModRadius2_active");
                    $(".hb_glj").addClass("ModRadius2_active");
                    yjclInit();
                    $(".create_edit-addhbcl").empty();
                    hbclInit();
                    break;
            }
        });
        $(".create_edit-hbcl .create_edit-typeChose_ > div").click(function () {
            var val = $(this).html();
            $(".create_edit-addhbcl").empty();
            switch (val) {
                case "普通策略":
                    $(".create_edit-addhbcl").empty();
                    break;
                case "鼓励金活动":
                    $(".create_edit-addhbcl").empty();
                    hbclInit();
                    break;
            }
        });
        var reVal = $(".create_edit-filling .ModRadius2_active").html();
        merchant_informationSwitch(reVal);
        $(".create_edit-filling .ModRadio2 > div").click(function () {
            $(".create_edit-filling .ModRadio2 > div").removeClass("ModRadius2_active");
            $(this).addClass("ModRadius2_active");
            var thisVal = $(this).html();
            merchant_informationSwitch(thisVal);
        });
        $(".create_edit-yjcl .ModRadio2 > div").click(function () {
            $(".create_edit-yjcl .ModRadio2 > div").removeClass("ModRadius2_active");
            $(this).addClass("ModRadius2_active");
        });
        $(".create_edit-hbcl .ModRadio2 > div").click(function () {
            $(".create_edit-hbcl .ModRadio2 > div").removeClass("ModRadius2_active");
            $(this).addClass("ModRadius2_active");
        });
    }
    function leJia_pt() {  //普通协议&乐加结算
        SettlementAppend.append(
                $("<div></div>").attr("class", "MODInput_row").append(
                        $("<div></div>").attr("class", "Mod-2").html("门店参数")
                ).append(
                        $("<div></div>").attr("id", "mendiancanshu").attr("class", "Mod-7").append(
                                $("<div></div>").append(
                                        $("<span></span>").html("普通订单费率")
                                ).append(
                                        $("<input>").attr("name", "pt-ljCommission")
                                                .attr("class",
                                                        "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                ).append(
                                        $("<span></span>").html("积分返点")
                                ).append(
                                        $("<input>").attr("name", "pt-scoreBRebate")
                                                .attr("class",
                                                        "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                )
                        )
                )
        ).append(
                $("<div></div>").attr("class", "MODInput_row create_edit-filling").append(
                        $("<div></div>").attr("class", "Mod-2").html("账户类型")
                ).append(
                        $("<div></div>").attr("class",
                                "Mod-5 ModRadio2 create_edit-typeChose_").append(
                                $("<div></div>").attr("class",
                                        "ModRadius-left ModRadius2_active").html("法人私账")
                        ).append(
                                $("<div></div>").attr("class", "ModRadius-right").html("对公账户")
                        )
                )
        );
        var reVal = $(".create_edit-filling .ModRadius2_active").html();
        merchant_informationSwitch(reVal);
        $(".create_edit-filling .ModRadio2 > div").click(function () {
            $(".create_edit-filling .ModRadio2 > div").removeClass("ModRadius2_active");
            $(this).addClass("ModRadius2_active");
            var thisVal = $(this).html();
            merchant_informationSwitch(thisVal);
        });
    }
    function fuYou(hyddType) { //联盟协议&富友结算
        SettlementAppend.append(
                $("<div></div>").attr("class", "MODInput_row").append(
                        $("<div></div>").attr("class", "Mod-2").html("普通商户号")
                ).append(
                        $("<div></div>").attr("class", "Mod-5 create_edit-OrdinaryBusiness")
                )
        ).append(
                $("<div></div>").attr("class", "MODInput_row").append(
                        $("<div></div>").attr("class", "Mod-2").html("佣金商户号")
                ).append(
                        $("<div></div>").attr("class", "Mod-5 create_edit-CommissionMerchants")
                )
        ).append(
                $("<div></div>").attr("class", "MODInput_row").append(
                        $("<div></div>").attr("class", "Mod-2").html("门店参数")
                ).append(
                        $("<div></div>").attr("id", "mendiancanshu").attr("class", "Mod-7").append(
                                $("<div></div>").append(
                                        $("<span></span>").html("普通订单费率")
                                ).append(
                                        $("<input>").attr("name", "lm-ljBrokerage")
                                                .attr("class",
                                                        "Mod-1 ModRadius create_edit-for2").attr('disabled',
                                                true)
                                ).append(
                                        $("<span></span>").html("%")
                                ).append(
                                        $("<span></span>").html("积分返点")
                                ).append(
                                        $("<input>").attr("name", "lm-scoreBRebate")
                                                .attr("class",
                                                        "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                )
                        ).append(
                                $("<div></div>").append(
                                        $("<span></span>").html("导流订单费率")
                                ).append(
                                        $("<input>").attr("name", "lm-ljCommission").attr("class",
                                                "Mod-1 ModRadius create_edit-for2").attr('disabled',
                                                true)
                                ).append(
                                        $("<span></span>").html("%")
                                ).append(
                                        $("<span></span>").html("红包比例")
                                ).append(
                                        $("<input>").attr("name", "lm-scoreARebate").attr("class",
                                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                ).append(
                                        $("<span></span>").html("积分返点")
                                ).append(
                                        $("<input>").attr("name",
                                                "lm-importScoreBScale").attr("class",
                                                "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                )
                        ).append(
                                $("<div></div>").append(
                                        $("<span></span>").html("会员订单费率")
                                ).append(
                                        $("<input>").attr("type", "radio").attr("name",
                                                "hydd").attr("id",
                                                "yj-hydd").attr('value',
                                                1).attr('checked',
                                                true)
                                ).append(
                                        $("<span></span>").html("佣金费率")
                                ).append(
                                        $("<input>").attr("type", "radio").attr("name",
                                                "hydd").attr("id",
                                                "pt-hydd").attr('value',
                                                0)
                                ).append(
                                        $("<span></span>").html("普通费率")
                                )
                        )
                )
        );
        if (hyddType == 1) {//  会员订单费率：佣金费率
            hyddChange();
        }
        //填充商户号列表信息
        $(".create_edit-OrdinaryBusiness").append(commonSettlementList);
        $(".create_edit-CommissionMerchants").append(allianceSettlementList);

        $('input[type=radio][name=commonSettlement-radio]').click(function () {
            $("input[name=lm-ljBrokerage]").val($(this).next().val());
        });
        $('input[type=radio][name=allianceSettlement-radio]').click(function () {
            $("input[name=lm-ljCommission]").val($(this).next().val());
        });
    }
    function fuYou_pt() { //普通协议&富友结算
        SettlementAppend.append(
                $("<div></div>").attr("class", "MODInput_row").append(
                        $("<div></div>").attr("class", "Mod-2").html("普通商户号")
                ).append(
                        $("<div></div>").attr("class", "Mod-5 create_edit-OrdinaryBusiness")
                )
        ).append(
                $("<div></div>").attr("class", "MODInput_row").append(
                        $("<div></div>").attr("class", "Mod-2").html("门店参数")
                ).append(
                        $("<div></div>").attr("id", "mendiancanshu").attr("class", "Mod-7").append(
                                $("<div></div>").append(
                                        $("<span></span>").html("普通订单费率")
                                ).append(
                                        $("<input>").attr("name", "pt-ljCommission")
                                                .attr("class",
                                                        "Mod-1 ModRadius create_edit-for2").attr('disabled',
                                                true)
                                ).append(
                                        $("<span></span>").html("%")
                                ).append(
                                        $("<span></span>").html("积分返点")
                                ).append(
                                        $("<input>").attr("name", "pt-scoreBRebate")
                                                .attr("class",
                                                        "Mod-1 ModRadius create_edit-for2").attr("type",
                                                "number").attr("step",
                                                "0.01")
                                ).append(
                                        $("<span></span>").html("%")
                                )
                        )
                )
        );
        //填充商户号列表信息
        $(".create_edit-OrdinaryBusiness").append(commonSettlementList);

        $('input[type=radio][name=commonSettlement-radio]').click(function () {
            $("input[name=pt-ljCommission]").val($(this).next().val());
        });
    }
    function yjcl() {
        $(".create_edit-addyjcl").append(
                $("<div></div>").append(
                        $("<div></div>").attr("class", "Mod-3").append(
                                $("<div></div>").html("0.01")
                        ).append(
                                $("<div></div>").html("~")
                        ).append(
                                $("<input>").attr("type", "number").attr("class",
                                        "Mod-4 lastData").attr("value",
                                        "0.02").val("0.02")
                        )
                ).append(
                        $("<div></div>").attr("class", "Mod-3").append(
                                $("<span></span>").html("佣金费率")
                        ).append(
                                $("<input>").attr("type", "number").attr("class", "Mod-4")
                        ).append(
                                $("<span></span>").html("%")
                        )
                ).append(
                        $("<div></div>").attr("class", "Mod-3").append(
                                $("<span></span>").html("金币返点")
                        ).append(
                                $("<input>").attr("type", "number").attr("class", "Mod-4")
                        ).append(
                                $("<span></span>").html("%")
                        )
                )
        ).append(
                $("<div></div>").attr("class", "addyjcl_add").append(
                        $("<button></button>").attr("onclick", "addArea(this)").html("添加区段")
                )
        ).append(
                $("<div></div>").append(
                        $("<div></div>").attr("class", "Mod-3").append(
                                $("<div></div>").html("大于")
                        ).append(
                                $("<div></div>").attr("class", "finalData")
                        )
                ).append(
                        $("<div></div>").attr("class", "Mod-3").append(
                                $("<span></span>").html("佣金费率")
                        ).append(
                                $("<input>").attr("type", "number").attr("class", "Mod-4")
                        ).append(
                                $("<span></span>").html("%")
                        )
                ).append(
                        $("<div></div>").attr("class", "Mod-3").append(
                                $("<span></span>").html("金币返点")
                        ).append(
                                $("<input>").attr("type", "number").attr("class", "Mod-4")
                        ).append(
                                $("<span></span>").html("%")
                        )
                )
        )
        $(".lastData").blur(function () {
            var sData = parseFloat($(this).val);
            var eData = parseFloat($(this).prev().prev().html());
            if (eData < sData && eData == sData) {
                $(this).val(eData + 0.01)
            }
        });
    }
    function hbcl() {
        $(".create_edit-addhbcl").append(
                $("<div></div>").append(
                        $("<div></div>").attr("class", "Mod-3").append(
                                $("<div></div>").html("0.01")
                        ).append(
                                $("<div></div>").html("~")
                        ).append(
                                $("<input>").attr("type", "number").attr("class",
                                        "Mod-4 lastData").attr("value",
                                        "0.02").val("0.02")
                        )
                ).append(
                        $("<div></div>").attr("class", "Mod-6").append(
                                $("<span></span>").html("送鼓励金")
                        ).append(
                                $("<input>").attr("type", "number").attr("class", "Mod-4")
                        ).append(
                                $("<span></span>").html("~")
                        ).append(
                                $("<input>").attr("type", "number").attr("class", "Mod-4")
                        )
                )
        ).append(
                $("<div></div>").attr("class", "addhbcl_add").append(
                        $("<button></button>").attr("onclick", "addArea1(this)").html("添加区段")
                )
        ).append(
                $("<div></div>").append(
                        $("<div></div>").attr("class", "Mod-3").append(
                                $("<div></div>").html("大于")
                        ).append(
                                $("<div></div>").attr("class", "finalData")
                        )
                ).append(
                        $("<div></div>").attr("class", "Mod-6").append(
                                $("<span></span>").html("送鼓励金")
                        ).append(
                                $("<input>").attr("type", "number").attr("class", "Mod-4")
                        ).append(
                                $("<span></span>").html("~")
                        ).append(
                                $("<input>").attr("type", "number").attr("class", "Mod-4")
                        )
                )
        )
    }

    function addArea(e) {
        if ($(e).parent().prev().children("div").children(".lastData").val() == '') {
            alert("请先输入区间！")
        } else {
            var lastData = $(e).parent().prev().children("div").children(".lastData").val();
            lastData = parseFloat(lastData) + 0.01;
            var line = $("<div></div>").attr("class", "w").append(
                    $("<div></div>").attr("class", "Mod-3").append(
                            $("<div></div>").attr("class", "firstData").html(lastData)
                    ).append(
                            $("<div></div>").html("~")
                    ).append(
                            $("<input>").attr("type", "number").attr("class", "Mod-4 lastData")
                    )
            ).append(
                    $("<div></div>").attr("class", "Mod-3").append(
                            $("<span></span>").html("佣金费率")
                    ).append(
                            $("<input>").attr("type", "number").attr("class", "Mod-4 collect_jy")
                    ).append(
                            $("<span></span>").html("%")
                    )
            ).append(
                    $("<div></div>").attr("class", "Mod-3").append(
                            $("<span></span>").html("金币返点")
                    ).append(
                            $("<input>").attr("type", "number").attr("class", "Mod-4 collect_jb")
                    ).append(
                            $("<span></span>").html("%")
                    )
            ).append(
                    $("<button></button>").attr("class", "yjcl_del").attr("onclick",
                            "yjclDel(this)").html("删除")
            );
            $(".addyjcl_add").before(line);
            $(".lastData").blur(function () {
                $(".w").each(function () {
                    var eData = parseFloat($(this).children("div").children(".lastData").val());
                    var sData = parseFloat($(this).children("div").children(".firstData").html());
                    if (sData > eData || eData == sData) {
                        $(this).children("div").children(".lastData").val(toDecimal(sData + 0.01));
                    } else {
                        $(this).children("div").children(".lastData").val(toDecimal(eData));
                    }
                });
            });
        }
    }
    function addArea1(e) {
        if ($(e).parent().prev().children("div").children(".lastData").val() == '') {
            alert("请先输入区间！")
        } else {
            var lastData = $(e).parent().prev().children("div").children(".lastData").val();
            lastData = parseFloat(lastData) + 0.01;
            var line = $("<div></div>").attr("class", "w").append(
                    $("<div></div>").attr("class", "Mod-3").append(
                            $("<div></div>").attr("class", "firstData").html(lastData)
                    ).append(
                            $("<div></div>").html("~")
                    ).append(
                            $("<input>").attr("type", "number").attr("class", "Mod-4 lastData")
                    )
            ).append(
                    $("<div></div>").attr("class", "Mod-6").append(
                            $("<span></span>").html("送鼓励金")
                    ).append(
                            $("<input>").attr("type", "number").attr("class", "Mod-4 collect_start")
                    ).append(
                            $("<span></span>").html("~")
                    ).append(
                            $("<input>").attr("type", "number").attr("class", "Mod-4 collect_end")
                    )
            ).append(
                    $("<button></button>").attr("class", "yjcl_del").attr("onclick",
                            "yjclDel(this)").html("删除")
            );
            $(".addhbcl_add").before(line);
            $(".lastData").blur(function () {
                $(".w").each(function () {
                    var eData = parseFloat($(this).children("div").children(".lastData").val());
                    var sData = parseFloat($(this).children("div").children(".firstData").html());
                    if (sData > eData || eData == sData) {
                        $(this).children("div").children(".lastData").val(toDecimal(sData + 0.01));
                    } else {
                        $(this).children("div").children(".lastData").val(toDecimal(eData));
                    }
                });
            });
        }
    }
    //强制保留2位小数
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
    function yjclDel(e) {
        $(e).parent().remove();
    }
    setInterval(function () {
        var min, max, min_, max_;
        min = $(".create_edit-addyjcl > div:first-child .lastData").val();
        min_ = $(".create_edit-addhbcl > div:first-child .lastData").val();
        $(".create_edit-addyjcl .w").each(function () {
            var firstNum = $(this).prev().children("div").children(".lastData").val();
            var lastNum = $(this).children("div").children(".lastData").val();
            var th_firstNum = $(this).children("div").children(".firstData");
            var new_firstNum = parseFloat(firstNum) + 0.01;
            new_firstNum = toDecimal(new_firstNum);
            th_firstNum.html(new_firstNum);
            max = lastNum;
        });
        $(".create_edit-addhbcl .w").each(function () {
            var firstNum = $(this).prev().children("div").children(".lastData").val();
            var lastNum = $(this).children("div").children(".lastData").val();
            var th_firstNum = $(this).children("div").children(".firstData");
            var new_firstNum = parseFloat(firstNum) + 0.01;
            new_firstNum = toDecimal(new_firstNum);
            th_firstNum.html(new_firstNum);
            max_ = lastNum;
        });
        if (max == '' || max == null) {
            $(".create_edit-addyjcl .finalData").html(min);
        } else {
            $(".create_edit-addyjcl .finalData").html(max);
        }
        if (max_ == '' || max_ == null) {
            $(".create_edit-addhbcl .finalData").html(min_);
        } else {
            $(".create_edit-addhbcl .finalData").html(max_);
        }
    }, 100);
</script>
<script>
    $("#enter").click(function () {
        submitMerchant();
    });
    /******************************确认创建或修改门店信息***********************************/
    function submitMerchant() {
        var merchantUser = {};
        var merchant = {};
        var partner = {};
        var salesStaff = {};
        var merchantRebatePolicy = {};
        var merchantScanPayWay = {};
        var merchantSettlementStore = {};
        merchant.id = $("#merchantId").val();
        merchantSettlementStore.id = $('#storeId').val();
        merchantRebatePolicy.id = $("#merchantRebatePolicyId").val();
        merchantScanPayWay.id = $('#scanPayWayId').val();
        merchantUser.id = $("#merchantUserId").val();
        partner.id = $("#partnerId").val();
        merchant.merchantUser = merchantUser;
        merchant.partner = partner;
        //所属销售
        salesStaff.id = $("#sales").val();
        if (salesStaff.id == "nullValue") {
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
        // 商户名称
        var flag = checkExist();
        if(flag) {
            alert("商户名称有误,换个试试吧 ~");
            return;
        }
        var merchantUser = {};
        merchantUser.name = $("#merchantManager").val();
        merchant.merchantUser = merchantUser;
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
        //收取红包权限
        merchant.receiptAuth = $("input[name='receiptAuth']:checked").val();
        //佣金协议
        var merchantProtocals = [];
        $(".fileimg").each(function () {
            var merchantProtocal = {};
            merchantProtocal.picture = $(this).attr("src");
            merchantProtocals.push(merchantProtocal);
        });
        merchant.merchantProtocols = merchantProtocals;
        //合约类型
        if (partnership == 0) { //普通协议
            merchant.partnership = 0;
            if (scanPayWay != 2) {
                // 置空
                merchant.ljBrokerage = null;
                merchant.scoreBRebate = null;
                merchantRebatePolicy.importScoreBScale = null;
                merchantRebatePolicy.scoreARebate = null;
                merchantRebatePolicy.userScoreBScaleB = null;
                merchantRebatePolicy.userScoreAScale = null;
                merchantRebatePolicy.userScoreBScale = null;
                merchant.memberCommission = null;
                var ljCommission = $("input[name=pt-ljCommission]").val();
                if (ljCommission == null || ljCommission == "" || ljCommission > 100 || ljCommission
                        < 0) {
                    alert("请输入正确的手续费");
                    return;
                }
                merchant.ljCommission = ljCommission;
                merchantScanPayWay.commission = ljCommission;
                var scoreBRebate = $("input[name=pt-scoreBRebate]").val();
                if (scoreBRebate == null || scoreBRebate == "" || scoreBRebate > 100 || scoreBRebate
                        < 0) {
                    alert("请选择积分返利比");
                    return;
                }
                merchant.scoreBRebate = scoreBRebate;
                merchantRebatePolicy.rebateFlag = 2;
            } else {
                merchantScanPayWay.commission = 0;
            }
        }
        if (partnership == 1) { //佣金协议
            merchant.partnership = 1;
            var zanShi_commission = $("input[name=zanShi_commission]").val();
            if (zanShi_commission == null || zanShi_commission === '' || zanShi_commission > 100
                    || zanShi_commission
                    < 0) {
                alert("请输入签约佣金(展示用)");
                return;
            }
            merchantScanPayWay.commission = zanShi_commission;
            if (scanPayWay != 2) {
                //置空
                merchant.scoreBRebate = null;
                merchant.ljCommission = null;
                var ljBrokerage = $("input[name=lm-ljBrokerage]").val();
                if (ljBrokerage == null || ljBrokerage == "" || ljBrokerage > 100 || ljBrokerage
                        < 0) {
                    alert("请输入普通订单费率");
                    return;
                }
                var scoreBRebate1 = $("input[name=lm-scoreBRebate]").val();
                if (scoreBRebate1 == null || scoreBRebate1 == "" || scoreBRebate1 > 100
                        || scoreBRebate1
                        < 0) {
                    alert("请输入普通订单积分返点");
                    return;
                }
                var importScoreBScale = $("input[name=lm-importScoreBScale]").val();
                if (importScoreBScale > 100 || importScoreBScale == null || importScoreBScale
                        == "") {
                    alert("请输入导流订单积分");
                    return;
                }
                var importScoreCScale = $("input[name=lm-importScoreCScale]").val();
                if (importScoreCScale > 100 || importScoreCScale == null || importScoreCScale
                        == "") {
                    alert("请输入导流订单金币");
                    return;
                }
                var scoreARebate = $("input[name=lm-scoreARebate]").val();
                if (scoreARebate > 100 || scoreARebate == null || scoreARebate == "") {
                    alert("请输入导流订单红包");
                    return;
                }

                var importShareScale = $("input[name=lm-importShareScale]").val();
                if (importShareScale > 100 || importShareScale == null || importShareScale == "") {
                    alert("请输入导流分润比例");
                    return;
                }
                var memberShareScale = $("input[name=lm-memberShareScale]").val();
                if (memberShareScale > 100 || memberShareScale == null || memberShareScale == "") {
                    alert("请输入会员分润比例");
                    return;
                }
                var hydd = $("input[name='hydd']:checked").val();
                if (hydd == 1) {                       //  会员订单费率： 佣金费率
                    merchant.memberCommission = $("input[name=lm-ljCommission]").val();                  // 会员订单费率
                }
                if (hydd == 0) {                       //  会员订单费率： 普通费率
                    merchant.memberCommission = ljBrokerage;
                }
                var policy = $("input[name='policy']:checked").val();
                if (policy == 1) {                     //   成本差全额
                    var userScoreBScaleB = $("input[name=lm-userScoreBScaleB]").val();
                    var userScoreCScaleB = $("input[name=lm-userScoreCScaleB]").val();
                    if (userScoreBScaleB == null || userScoreBScaleB == "" || userScoreBScaleB > 100
                            || userScoreBScaleB < 0) {
                        alert("请输入全额发放积分返点");
                        return;
                    }
                    if (userScoreCScaleB == null || userScoreCScaleB == "" || userScoreCScaleB > 100
                            || userScoreCScaleB < 0) {
                        alert("请输入全额发放金币返点");
                        return;
                    }
                    merchantRebatePolicy.rebateFlag = 1;
                    merchantRebatePolicy.userScoreAScale = null;
                    merchantRebatePolicy.userScoreBScale = null;
                    merchantRebatePolicy.userScoreBScaleB = userScoreBScaleB;      // 会员发放红包 【全额】
                    merchantRebatePolicy.userScoreCScaleB = userScoreCScaleB;      // 会员发放红包 【全额】
                }
                if (policy == 0) {                     //   比例发放
                    var userScoreAScale = $("input[name=lm-userScoreAScale]").val();
                    if (userScoreAScale == null || userScoreAScale == "" || userScoreAScale > 100
                            || userScoreAScale < 0) {
                        alert("请输入比例发放红包");
                        return;
                    }
                    var userScoreBScale = $("input[name=lm-userScoreBScale]").val();
                    if (userScoreBScale == null || userScoreBScale == "" || userScoreBScale > 100
                            || userScoreBScale < 0) {
                        alert("请输入比例发放积分");
                        return;
                    }
                    var userScoreCScale = $("input[name=lm-userScoreCScale]").val();
                    if (userScoreCScale == null || userScoreCScale == "" || userScoreCScale > 100
                            || userScoreCScale < 0) {
                        alert("请输入比例发放金币");
                        return;
                    }
                    merchantRebatePolicy.rebateFlag = 0;
                    merchantRebatePolicy.userScoreBScaleB = null;
                    merchantRebatePolicy.userScoreAScale = userScoreAScale;        // 会员发放红包 【比例】
                    merchantRebatePolicy.userScoreBScale = userScoreBScale;        // 会员发放红包 【比例】
                    merchantRebatePolicy.userScoreCScale = userScoreCScale;        // 会员发放红包 【比例】
                }
                merchant.ljBrokerage = ljBrokerage;                                                 // 普通订单费率
                merchant.scoreBRebate = scoreBRebate1;                                               // 普通订单积分返点
                merchant.ljCommission = $("input[name=lm-ljCommission]").val();                     // 导流订单费率
                merchant.scoreARebate = scoreARebate;                                               // 导流订单红包
                merchantRebatePolicy.importScoreBScale = importScoreBScale;                         // 导流订单积分返点
                merchantRebatePolicy.importScoreCScale = importScoreCScale;                         // 导流订单积分返点
                merchantRebatePolicy.importShareScale = importShareScale;                         // 导流订单积分返点
                merchantRebatePolicy.memberShareScale = memberShareScale;                         // 导流订单积分返点
                if ($($('.create_edit-yjcl').children()).children(".ModRadius2_active")[0].innerHTML
                        == '固定策略') {
                    merchantRebatePolicy.commissionPolicy = 0
                } else {
                    merchantRebatePolicy.commissionPolicy = 1
                    merchantRebatePolicy.commissionStages = collectData_yj()
                }
                if ($($('.create_edit-hbcl').children()).children(".ModRadius2_active")[0].innerHTML
                        == '普通策略') {
                    merchantRebatePolicy.rebatePolicy = 0
                } else {
                    merchantRebatePolicy.rebatePolicy = 1
                    merchantRebatePolicy.rebateStages = collectData_hb()
                }
            }
        }
        merchant.cycle = 1;
        //扫码结算方式
        merchantScanPayWay.type = scanPayWay;
        if (scanPayWay == 1) { //乐加结算
            var merchantBank = {};
            merchantBank.id = $("#merchantBankId").val();
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
        }
        if (scanPayWay == 0) { //富友结算
            var commonSettlement = $("input[name='commonSettlement-radio']:checked");
            if (commonSettlement == null) {
                alert('无普通商户号，无法选择富友结算');
                return;
            }
            merchantSettlementStore.commonSettlementId = commonSettlement.val();
            if (partnership == 1) {//佣金协议
                var allianceSettlement = $("input[name='allianceSettlement-radio']:checked");
                if (allianceSettlement == null) {
                    alert('无佣金商户号，无法选择(佣金协议)富友结算');
                    return;
                }
                merchantSettlementStore.allianceSettlementId = allianceSettlement.val();
            }
        }
        /*********************************************     ******************************************************************/
        var merchantDto = {};
        merchantDto.merchant = merchant;
        merchantDto.merchantRebatePolicy = merchantRebatePolicy;
        merchantDto.merchantScanPayWay = merchantScanPayWay;
        merchantDto.merchantSettlementStore = merchantSettlementStore;
        console.log(merchantDto);
        $("#enter").unbind("click");
        if (merchant.id == null || merchant.id == "") {
            $.ajax({
                type: "post",
                url: "/manage/merchant",
                contentType: "application/json",
                data: JSON.stringify(merchantDto),
                success: function (data) {
                    alert(data.data);
                    setTimeout(function () {
                        window.location.href =
                                "/manage/merchantUser/info/" + $('#merchantUserId').val() + '?li=2';
                    }, 0);
                }
            });
        } else {
            $.ajax({
                type: "put",
                url: "/manage/merchant",
                contentType: "application/json",
                data: JSON.stringify(merchantDto),
                success: function (data) {
                    alert(data.msg);
                    setTimeout(function () {
                        window.location.href =
                                "/manage/merchantUser/info/" + data.data + '?li=2';
                    }, 0);
                }
            });
        }
    }

    function toInt(val) {
        return parseInt(val * 100)
    }
    function collectData_yj() {
        var Arry_yj = [];
        var firstLine_start = $(".create_edit-addyjcl > div:first-child > div:first-child > div:first-child").html();
        var firstLine_end = $(".create_edit-addyjcl > div:first-child > div:first-child input[type=number]").val();
        var firstLine_yj = $(".create_edit-addyjcl > div:first-child > div:nth-child(2) input[type=number]").val();
        var firstLine_jb = $(".create_edit-addyjcl > div:first-child > div:last-child input[type=number]").val();
        var firstLine = {
            start: toInt(firstLine_start),
            end: toInt(firstLine_end),
            commissionScale: firstLine_yj,
            scoreCScale: firstLine_jb
        }
        Arry_yj.push(firstLine);
        $(".create_edit-addyjcl .w").each(function () {
            var w_start = $(this).children("div").children(".firstData").html();
            var w_end = $(this).children("div").children(".lastData").val();
            var w_yj = $(this).children("div").children(".collect_jy").val();
            var w_jb = $(this).children("div").children(".collect_jb").val();
            var wLine = {
                start: toInt(w_start),
                end: toInt(w_end),
                commissionScale: w_yj,
                scoreCScale: w_jb
            }
            Arry_yj.push(wLine);
        });
        var finalLine_start = $(".create_edit-addyjcl > div:last-child > div:first-child>div:last-child").html();
        var finalLine_end = Number.MAX_SAFE_INTEGER;
        var finalLine_yj = $(".create_edit-addyjcl > div:last-child > div:nth-child(2) input[type=number]").val();
        var finalLine_jb = $(".create_edit-addyjcl > div:last-child > div:last-child input[type=number]").val();
        var finalLine = {
            start: toInt(finalLine_start),
            end: finalLine_end,
            commissionScale: finalLine_yj,
            scoreCScale: finalLine_jb
        }
        Arry_yj.push(finalLine);
        return Arry_yj;
    }
    function collectData_hb() {
        var Arry_hb = [];
        var firstLine_start = $(".create_edit-addhbcl > div:first-child > div:first-child > div:first-child").html();
        var firstLine_end = $(".create_edit-addhbcl > div:first-child > div:first-child input[type=number]").val();
        var firstLine_left = $(".create_edit-addhbcl > div:first-child > div:last-child > input:nth-child(2)").val();
        var firstLine_right = $(".create_edit-addhbcl > div:first-child > div:last-child > input:last-child").val();
        var firstLine = {
            start: toInt(firstLine_start),
            end: toInt(firstLine_end),
            rebateStart: toInt(firstLine_left),
            rebateEnd: toInt(firstLine_right)
        }
        Arry_hb.push(firstLine);
        $(".create_edit-addhbcl .w").each(function () {
            var w_start = $(this).children("div").children(".firstData").html();
            var w_end = $(this).children("div").children(".lastData").val();
            var w_yj = $(this).children("div").children(".collect_start").val();
            var w_jb = $(this).children("div").children(".collect_end").val();
            var wLine = {
                start: toInt(w_start),
                end: toInt(w_end),
                rebateStart: toInt(w_yj),
                rebateEnd: toInt(w_jb)
            }
            Arry_hb.push(wLine);
        });
        var finalLine_start = $(".create_edit-addhbcl > div:last-child > div:first-child>div:last-child").html();
        var finalLine_end = Number.MAX_SAFE_INTEGER;
        var finalLine_left = $(".create_edit-addhbcl > div:last-child > div:last-child > input:nth-child(2)").val();
        var finalLine_right = $(".create_edit-addhbcl > div:last-child > div:last-child > input:last-child").val();
        var finalLine = {
            start: toInt(finalLine_start),
            end: finalLine_end,
            rebateStart: toInt(finalLine_left),
            rebateEnd: toInt(finalLine_right)
        }
        Arry_hb.push(finalLine);
        return Arry_hb;
    }

    /******************************** 修改门店所属管理员账号 - 校验管理员是否存在****************************/
    function toCheckExist() {
        var flag = checkExist();
        if(flag) {
            alert("商户名称有误,换个试试吧 ~");
            return;
        }
    }

    function checkExist() {
        var merchantManager = $("#merchantManager").val();
        var flag = true;
        $.ajax({
            url:"/manage/merchantUser/check_exist?name="+merchantManager,
            type:"get",
            contentType:"application/json",
            async:false,
            success:function(result){
                console.log(JSON.stringify(result));
                if(result.status==400) {
                    flag = true;
                }else {
                    flag = false;
                }
            }
        });
        return flag;
    }
</script>
<script>


</script>

</html>
