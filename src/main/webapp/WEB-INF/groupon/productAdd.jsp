<%--
  Created by IntelliJ IDEA.
  User: WhiteFeather
  Date: 2017/6/21
  Time: 9:51
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
    <!--CSS-->
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/create-edit-store.css"/>
    <!--JS-->
    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/Mod/common.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.combo.select.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/Mod/RetainDecimalFor2.js"></script>
    <script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"></script>
</head>
<style>
    .fixClear:after {
        content: '\20';
        display: block;
        clear: both;
    }

    .listImg > div {
        float: left;
        width: 100px;
        margin-right: 20px;
        margin-bottom: 20px;
    }

    .listImg > div > div:first-child {
        width: 100%;
        height: 100px;
        border: 1px solid #ccCCCC;
    }

    .listImg > div > div:nth-child(2) {
        width: 100%;
        font-size: 13px;
        color: #fff;
        background-color: #f0ad4e;
        border: 1px solid #f0ad4e;
        text-align: center;
        padding: 5px 0;
        cursor: pointer;
    }

    .listImg > div > div:nth-child(3) {
        width: 100%;
        display: none;
    }

    .listImg > div > div:last-child {
        width: 100%;
        font-size: 13px;
        color: #fff;
        background-color: #d9534f;
        border: 1px solid #d9534f;
        text-align: center;
        padding: 5px 0;
        cursor: pointer;
    }

    .addScrollBtn > div {
        background-color: transparent !important;
        color: #ccc !important;
        font-size: 20px !important;
        border: 1px solid #ccc !important;
        padding: 0 !important;
        line-height: 100px;
    }

    .addDetailBtn > div {
        background-color: transparent !important;
        color: #ccc !important;
        font-size: 20px !important;
        border: 1px solid #ccc !important;
        padding: 0 !important;
        line-height: 100px;
    }

    .yy {
        display: none;
    }

    .xdrq {

    }

    .jdrq {
        display: none;
    }

    .checkArea > div {
        float: left;
        width: 22%;
        margin-right: 3%;
        margin-bottom: 10px;
    }

    .checkArea input {
        width: auto;
        margin-right: 4%;
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
            <div class="ModRadius" onclick="productList()"> 返回</div>
            <p>团购商品</p>
        </div>
        <div class="create_edit-body">
            <div class="MODInput_row">
                <div class="Mod-2">团购名称</div>
                <div class="Mod-5">
                    <input id="productId" type="hidden" value="${product.id}"/>
                    <input id="productName" type="text" class="create_edit-input" value="${product.name}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">简介</div>
                <div class="Mod-5">
                    <input id="description" type="text" class="create_edit-input" value="${product.description}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">列表图片</div>
                <div class="Mod-5">
                    <div class="listImg fixClear">
                        <div>
                            <div><img src="${product.displayPicture}" alt="" id="displayPicture"></div>
                            <div class="update">点击上传</div>
                            <div><input id="displayPictureUpload" class="form-control" name="file" type="file"
                                        data-url="/manage/file/saveImage"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">轮播图</div>
                <div class="Mod-5">
                    <div class="listImg fixClear">
                        <c:if test="${product!=null}">
                            <c:forEach var="scroll" items="${scrollPictures}" step="1">
                                <div>
                                    <div>
                                        <img id="scrollPicture${scroll.sid}" src="${scroll.picture}" alt="">
                                        <input id="scrollPictureId${scroll.sid}" type="hidden" value="${scroll.id}"/>
                                    </div>
                                    <div id="scrollUpdate${scroll.sid}" class="update">点击上传</div>
                                    <div><input id="scrollPictureUpload${scroll.sid}" class="form-control" name="file"
                                                type="file"
                                                data-url="/manage/file/saveImage"></div>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${product==null}">
                            <div>
                                <div><img id="scrollPicture1" src="" alt=""></div>
                                <div id="scrollUpdate1" class="update">点击上传</div>
                                <div><input id="scrollPictureUpload1" class="form-control" name="file" type="file"
                                            data-url="/manage/file/saveImage"></div>
                            </div>
                        </c:if>
                        <div class="addScrollBtn">
                            <div>+</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">详情图片</div>
                <div class="Mod-5">
                    <div class="listImg fixClear">
                        <c:if test="${product!=null}">
                            <c:forEach var="detail" items="${productDetails}" step="1">
                                <div>
                                    <div>
                                        <img id="detailPicture${detail.sid}" src="${detail.picture}" alt="">
                                        <input id="detailPictureId${detail.sid}" type="hidden" value="${detail.id}"/>
                                    </div>
                                    <div id="detailUpdate${detail.sid}" class="update">点击上传</div>
                                    <div><input id="detailPictureUpload${detail.sid}" class="form-control" name="file"
                                                type="file"
                                                data-url="/manage/file/saveImage"></div>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${product==null}">
                            <div>
                                <div><img id="detailPicture1" src="" alt=""></div>
                                <div id="detailUpdate1" class="update">点击上传</div>
                                <div><input id="detailPictureUpload1" class="form-control" name="file" type="file"
                                            data-url="/manage/file/saveImage"></div>
                            </div>
                        </c:if>
                        <div class="addDetailBtn">
                            <div>+</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">可用门店</div>
                <div class="Mod-5">
                    <select class="hhr" id="selMcu" value="${product.merchantUser.id}">
                        <option value="-1">请选择可用门店</option>
                    </select>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2"></div>
                <div class="Mod-5 checkArea fixClear">
                    <c:if test="${product!=null}">
                        <div>
                            <input type="checkbox" class="allCheck" value="0"/><span>全部选择</span><br/>
                            <c:forEach var="gm" items="${merchants}" step="1">
                                <input type="checkbox" name="merchant" value="${gm.merchant.id}" checked="true"/><span>${gm.merchant.name}</span><br/>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="MODInput_row create_edit-contractType">
                <div class="Mod-2">是否需预约</div>
                <div class="Mod-5 ModRadio2 create_edit-typeChose xyy">
                    <div class="ModRadius-left ModRadius2_active" onclick="changeReservationType(0)">免预约</div>
                    <div class="ModRadius-right" onclick="changeReservationType(1)">需要预约</div>
                </div>
            </div>
            <div class="MODInput_row yy">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <span>提前</span><input id="reservation" style="width: 20%" type="number"
                                          value="${product.reservation}"><span>天预约</span>
                </div>
            </div>
            <div class="MODInput_row create_edit-contractType">
                <div class="Mod-2">退款设定</div>
                <div class="Mod-5 ModRadio2 create_edit-typeChose">
                    <div class="ModRadius-left ModRadius2_active" onclick="changeRefundType(0)">随时可退</div>
                    <div class="ModRadius-right" onclick="changeRefundType(1)">不能退款</div>
                </div>
            </div>
            <div class="MODInput_row create_edit-contractType">
                <div class="Mod-2">有效期</div>
                <div class="Mod-5 ModRadio2 create_edit-typeChose setrq">
                    <div class="ModRadius-left ModRadius2_active" onclick="changeValidityType(0)">相对日期</div>
                    <div class="ModRadius-right" onclick="changeValidityType(1)">绝对日期</div>
                </div>
            </div>
            <div class="MODInput_row xdrq">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <span>购买后</span><input id="validity1" style="width: 20%"
                                           type="number" value="${product.validity}"><span>天有效（过了有效期未使用将自动退款）</span>
                </div>
            </div>
            <div class="MODInput_row jdrq">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <input id="validity2" style="width: 20%" type="text" value="${product.validity}"><span>前使用（填写方式如：2017-1-1）</span>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">购买须知</div>
                <div class="Mod-5">
                    <div class="listImg fixClear">
                        <div>
                            <div><img id="instruction" src="${product.explainPicture}" alt=""></div>
                            <div class="update">点击上传</div>
                            <div><input id="instructionUpload" class="form-control" name="file" type="file"
                                        data-url="/manage/file/saveImage"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">详情明细</div>
                <div class="Mod-5">
                    <div class="listImg fixClear">
                        <div>
                            <div><img id="explainPicture" src="${product.instruction}" alt=""></div>
                            <div class="update">点击上传</div>
                            <div><input id="explainPictureUpload" class="form-control" name="file" type="file"
                                        data-url="/manage/file/saveImage"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">团购原价</div>
                <div class="Mod-5">
                    <input id="originPrice" type="number" class="create_edit-input"
                           value="${product.originPrice/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">普通团购价</div>
                <div class="Mod-5">
                    <input id="normalPrice" type="number" class="create_edit-input"
                           value="${product.normalPrice/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">普通库存</div>
                <div class="Mod-5">
                    <input id="normalStorage" type="number" class="create_edit-input" value="${product.normalStorage}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">乐+团购价</div>
                <div class="Mod-5">
                    <input id="ljPrice" type="number" class="create_edit-input" value="${product.ljPrice/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">乐+佣金</div>
                <div class="Mod-5">
                    <input id="ljCommission" type="number" class="create_edit-input"
                           value="${product.ljCommission/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">普通手续费</div>
                <div class="Mod-5">
                    <input id="charge" type="number" class="create_edit-input" value="${product.charge/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">送鼓励金</div>
                <div class="Mod-5">
                    <input id="rebateScorea" type="number" class="create_edit-input"
                           value="${product.rebateScorea/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">送金币</div>
                <div class="Mod-5">
                    <input id="rebateScorec" type="number" class="create_edit-input"
                           value="${product.rebateScorec/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">会员所在门店分润</div>
                <div class="Mod-5">
                    <input id="shareToLockMerchant" type="number" class="create_edit-input"
                           value="${product.shareToLockMerchant/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">会员锁定天使合伙人分润</div>
                <div class="Mod-5">
                    <input id="shareToLockPartner" type="number" class="create_edit-input"
                           value="${product.shareToLockPartner/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">会员锁定城市合伙人分润</div>
                <div class="Mod-5">
                    <input id="shareToLockPartnerManager" type="number" class="create_edit-input"
                           value="${product.shareToLockPartnerManager/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">核销门店天使合伙人分润</div>
                <div class="Mod-5">
                    <input id="shareToTradePartner" type="number" class="create_edit-input"
                           value="${product.shareToTradePartner/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">核销门店城市合伙人分润</div>
                <div class="Mod-5">
                    <input id="shareToTradePartnerManager" type="number" class="create_edit-input"
                           value="${product.shareToTradePartnerManager/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">乐+库存</div>
                <div class="Mod-5">
                    <input id="ljStorage" type="number" class="create_edit-input" value="${product.ljStorage/100.0}"/>
                </div>
            </div>
            <div class="MODInput_row ModButtonMarginDown">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <button class="ModButton ModButton_ordinary ModRadius" onclick="saveOrUpdate()">保存信息</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

</body>

<script>
    //    set
    var HtmlType = 0;       //  页面类型：创建页面=0 ; 编辑页面=1;
    var refundType = "${product.refundType}";      //  0 随时退 1 不可退
    var validityType = "${product.validityType}";     //  有效期类型 0 相对日期 1 绝对日期
    var reservationType = "${reservation}";         // 0 不需要预约
    var grouponProductDto = {};
    var grouponProduct = {};
    var scrollNum = "${detailSize}";       // 轮播图数量
    var detailNum = "${scrollSize}";       // 详情图数量
    var delScrollList = [], delDetailList = [], merchantList = [];
    //  模糊检索商户
    loadMerchantUser();
    //    图片上传
    bindFileUpload("displayPictureUpload", "displayPicture")
    bindFileUpload("explainPictureUpload", "explainPicture")
    bindFileUpload("instructionUpload", "instruction")
    bindFileUpload("scrollPictureUpload1", "scrollPicture1")
    bindFileUpload("detailPictureUpload1", "detailPicture1")
    // 编辑页面时绑定图片上传
    $(function () {
        editProductPricture();
    });
    function editProductPricture() {
        var productId = $("#productId").val();
        if (productId != null && productId != '') {
            for (var i = 1; i <= scrollNum; i++) {
                bindFileUpload("scrollPictureUpload" + i, "scrollPicture" + i)
            }
            for (var j = 1; j <= detailNum; j++) {
                bindFileUpload("detailPictureUpload" + j, "detailPicture" + j)
            }
        }
    }
    function bindFileUpload(pictureUpload, picture) {
        $('#' + pictureUpload).fileupload({
            dataType: 'json',
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
            add: function (e, data) {
                data.submit();
            },
            done: function (e, data) {
                var resp = data.result;
                $('#' + picture).attr('src',
                    '${ossImageReadRoot}/'
                    + resp.data);
            }
        });
    }
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

    //下拉模糊查询
    $(function () {
        $('.hhr').comboSelect();
    });

    // 添加 - 图片上传项
    $(".update").click(function () {
        $(this).next().children().click();
    });
    $(".addScrollBtn").click(function () {
        scrollNum++;
        var currId = "banner" + scrollNum;
        var picture = "scrollPicture" + scrollNum;
        var pictureUpload = "scrollPictureUpload" + scrollNum;
        var btn = "scrollUpdate" + scrollNum;
        var imgDiv = $("<div></div>")
        //               .append($("<input>").attr("type", "hidden").attr("value", "").attr("id", currId).attr("style", "margin:5px;"))
            .append(
                $("<div></div>").append($("<img>").attr("id", picture))
            ).append(
                $("<div></div>").attr("id", btn).html("点击上传")
            ).append(
                $("<div></div>").append($("<input>").attr("id", pictureUpload).attr("name", "file").attr("type", "file").attr("class", "form-control").attr("data-url", "/manage/file/saveImage"))
            ).append(
                $("<div></div>").html("删除")
            );
        $(this).before(imgDiv);
        bindFileUpload(pictureUpload, picture)
        $("#" + btn).click(function () {
            $(this).next().children().click();
        });
    });
    $(".addDetailBtn").click(function () {
        detailNum++;
        var currId = "banner" + detailNum;
        var picture = "detailPicture" + detailNum;
        var pictureUpload = "detailPictureUpload" + detailNum;
        var btn = "detailUpdate" + detailNum;
        var imgDiv = $("<div></div>")
        //                        .append($("<input>").attr("type", "hidden").attr("value", "").attr("id", currId).attr("style", "margin:5px;"))
            .append(
                $("<div></div>").append($("<img>").attr("id", picture))
            ).append(
                $("<div></div>").attr("id", btn).html("点击上传")
            ).append(
                $("<div></div>").append($("<input>").attr("id", pictureUpload).attr("name", "file").attr("type", "file").attr("class", "form-control").attr("data-url", "/manage/file/saveImage"))
            ).append(
                $("<div></div>").html("删除")
            );
        $(this).before(imgDiv);
        bindFileUpload(pictureUpload, picture);
        $("#" + btn).click(function () {
            $(this).next().children().click();
        });
    });
    //  按钮触发事件
    function changeRefundType(num) {
        refundType = num;
    }
    function changeValidityType(num) {
        validityType = num;
    }
    function changeReservationType(num) {
        reservationType = num;
    }


    //预约
    $(".xyy > div").click(function () {
        var thisName = $(this).html();
        switch (thisName) {
            case '免预约':
                $(".yy").hide();
                break;
            case '需要预约':
                $(".yy").show();
                break;
            default:
                break;
        }
    });

    //    设置日期
    $(".setrq > div").click(function () {
        var thisName = $(this).html();
        switch (thisName) {
            case '相对日期':
                $(".xdrq").show();
                $(".jdrq").hide();
                break;
            case '绝对日期':
                $(".xdrq").hide();
                $(".jdrq").show();
                break;
            default:
                break;
        }
    });


    //锁定门店多选框
    setTimeout(function () {
        $(".option-item").click(function () {
            changeCheck();
            $(".allCheck").click(function () {
                if (this.checked) {
                    $(".checkArea :checkbox").prop("checked", true);
                } else {
                    $(".checkArea :checkbox").prop("checked", false);
                }
            });
        });
    }, 1000);
    function changeCheck() {
        var id = $("#selMcu").val();
        $.ajax({
            type: "get",
            url: "/manage/merchant/findByMU?id=" + id,
            async: false,
            success: function (result) {
                var list = result.data;
                $(".checkArea").empty();
                $(".checkArea").append(
                    $("<div></div>").append(
                        $('<input>').attr("type", "checkbox").attr("class", "allCheck").attr("value", 0)
                    ).append(
                        $("<span></span>").html("全部选择")
                    )
                );
                for (var i = 0; i < list.length; i++) {
                    $(".checkArea").append(
                        $("<div></div>").append(
                            $('<input>').attr("type", "checkbox").attr("value", list[i].id).attr("name", "merchant")
                        ).append(
                            $("<span></span>").html(list[i].name)
                        )
                    );
                }
            }
        });

    }

    //  保存或更新
    function saveOrUpdate() {
        //  设置参数，校验是否完整
        var flag = setParam();
        if (flag == false) {
            return;
        }
        var productId = $("#productId").val();
        if(productId!=''&&productId!=null) {
            //  编辑
            $.ajax({
                type: "post",
                url: "/manage/grouponProduct/saveEdit",
                contentType: "application/json",
                data: JSON.stringify(grouponProductDto),
                success: function (data) {
                    if (data.status == 200) {
                        alert("修改成功！");
                        location.href = "/manage/grouponProduct/list";
                    } else {
                        alert("编辑失败！");
                    }
                }
            });
        }else {
            // 新增
            $.ajax({
                type: "post",
                url: "/manage/grouponProduct/save",
                contentType: "application/json",
                data: JSON.stringify(grouponProductDto),
                success: function (data) {
                    if (data.status == 200) {
                        alert("添加成功！");
                        location.href = "/manage/grouponProduct/list";
                    } else {
                        alert("添加失败！");
                    }
                }
            });
        }

    }
    //  设置参数
    function setParam() {
        //  团购名称
        if ($("#productName").val() != null && $("#productName").val() != '') {
            grouponProduct.name = $("#productName").val();
        } else {
            alert("请输入团购名称~");
            return false;
        }
        //  团购简介
        if ($("#description").val() != null && $("#description").val() != '') {
            grouponProduct.description = $("#description").val();
        } else {
            alert("请输入简介~");
            return false;
        }
        //  是否需要预约
        if (reservationType == 0) {
            // 不需要预约
            grouponProduct.reservation = 0;
        } else {
            //  需要预约
            if ($("#reservation").val() != null && $("#reservation").val() != '') {
                grouponProduct.reservation = $("#reservation").val();
            } else {
                alert("请输入提前预约天数~");
                return false;
            }
        }
        //  是否可退
        grouponProduct.refundType = refundType;
        //  使用期限
        if (validityType == 0) {
            //  相对期限
            if ($("#validity1").val() != null && $("#validity1").val() != '') {
                grouponProduct.validity = $("#validity1").val();
                grouponProduct.validityType = 0;
            } else {
                alert("请输入有效天数~");
                return false;
            }
        } else {
            //  绝对期限
            if ($("#validity2").val() != null && $("#validity2").val() != '') {
                grouponProduct.validity = $("#validity2").val();
                grouponProduct.validityType = 1;
            } else {
                alert("请输入使用期限~" + $("#validity2").val());
                return false;
            }
        }
        //  团购原价
        if ($("#originPrice").val() != null && $("#originPrice").val() != '') {
            grouponProduct.originPrice = $("#originPrice").val() * 100;
        } else {
            alert("请输入团购原价~");
            return false;
        }


        //  普通团购价
        if ($("#normalPrice").val() != null && $("#normalPrice").val() != '') {
            grouponProduct.normalPrice = $("#normalPrice").val() * 100;
        } else {
            alert("请输入普通团购价~");
            return false;
        }
        //  普通库存
        if ($("#normalStorage").val() != null && $("#normalStorage").val() != '') {
            grouponProduct.normalStorage = $("#normalStorage").val();
        } else {
            alert("请输入普通库存~");
            return false;
        }
        //  乐+团购价
        if ($("#ljPrice").val() != null && $("#ljPrice").val() != '') {
            grouponProduct.ljPrice = $("#ljPrice").val() * 100;
        } else {
            alert("请输入乐+团购价~");
            return false;
        }
        //  乐+佣金
        if ($("#ljCommission").val() != null && $("#ljCommission").val() != '') {
            grouponProduct.ljCommission = $("#ljCommission").val() * 100;
        } else {
            alert("请输入乐+佣金~");
            return false;
        }
        //  普通手续费
        if ($("#charge").val() != null && $("#charge").val() != '') {
            grouponProduct.charge = $("#charge").val() * 100;
        } else {
            alert("请输入普通手续费~");
            return false;
        }
        //  送鼓励金
        if ($("#rebateScorea").val() != null && $("#rebateScorea").val() != '') {
            grouponProduct.rebateScorea = $("#rebateScorea").val() * 100;
        } else {
            alert("请输入送鼓励金~");
            return false;
        }
        //  送金币
        if ($("#rebateScorec").val() != null && $("#rebateScorec").val() != '') {
            grouponProduct.rebateScorec = $("#rebateScorec").val() * 100;
        } else {
            alert("请输入送金币~");
            return false;
        }
        //  会员所在门店分润
        if ($("#shareToLockMerchant").val() != null && $("#shareToLockMerchant").val() != '') {
            grouponProduct.shareToLockMerchant = $("#shareToLockMerchant").val() * 100;
        } else {
            alert("请输入门店分润~");
            return false;
        }
        //  会员锁定天使合伙人分润
        if ($("#shareToLockPartner").val() != null && $("#shareToLockPartner").val() != '') {
            grouponProduct.shareToLockPartner = $("#shareToLockPartner").val() * 100;
        } else {
            alert("请输入天使合伙人分润~");
            return false;
        }
        //  会员锁定城市合伙人分润
        if ($("#shareToLockPartnerManager").val() != null && $("#shareToLockPartnerManager").val() != '') {
            grouponProduct.shareToLockPartnerManager = $("#shareToLockPartnerManager").val() * 100;
        } else {
            alert("请输入城市合伙人分润分润~");
            return false;
        }
        //  核销门店天使合伙人分润
        if ($("#shareToTradePartner").val() != null && $("#shareToTradePartner").val() != '') {
            grouponProduct.shareToTradePartner = $("#shareToTradePartner").val() * 100;
        } else {
            alert("请输入核销门店合伙人分润分润~");
            return false;
        }
        //  核销门店城市合伙人分润
        if ($("#shareToTradePartnerManager").val() != null && $("#shareToTradePartnerManager").val() != '') {
            grouponProduct.shareToTradePartnerManager = $("#shareToTradePartnerManager").val() * 100;
        } else {
            alert("请输入核销门店城市合伙人分润分润~");
            return false;
        }
        //  乐+库存
        if ($("#ljStorage").val() != null && $("#ljStorage").val() != '') {
            grouponProduct.ljStorage = $("#ljStorage").val();
        } else {
            alert("请输入乐+库存~");
            return false;
        }
        //  适用门店
        if ($("#selMcu").val() != -1) {
            var checks = document.getElementsByName("merchant");
            var merchantUser = {};
            merchantUser.id = $("#selMcu").val();
            grouponProduct.merchantUser = merchantUser;
            for (k in checks) {
                if (checks[k].checked) {
                    var merchant = {};
                    merchant.id = checks[k].value;
                    merchantList.push(merchant);
                }
            }
        } else {
            alert("请选择可用门店~");
            return false;
        }
        if (merchantList.length <= 0) {
            alert("请点击复选框选择可用门店~");
            return false;
        }
        //  单图
        if ($("#displayPicture").attr("src") != '') {
            grouponProduct.displayPicture = $("#explainPicture").attr("src");
        } else {
            alert("请上传列表展示图片~");
            return false;
        }
        if ($("#explainPicture").attr("src") != '') {
            grouponProduct.explainPicture = $("#explainPicture").attr("src");
        } else {
            alert("请上传使用说明图片~");
            return false;
        }
        if ($("#instruction").attr("src") != '') {
            grouponProduct.instruction = $("#instruction").attr("src");
        } else {
            alert("请上传购买须知图片~");
            return false;
        }
        //  多图
        if ($("#detailPicture1").attr("src") != '') {
            for (var i = 1; i <= detailNum; i++) {
                if ($("#detailPicture" + i).attr("src") != '' && $("#detailPicture" + i).attr("src") != null) {
                    var grouponProductDetail = {};
                    var picture = $("#detailPicture" + i).attr("src");
                    if ($("#detailPictureId" + i) != null) {
                        var id = $("#detailPictureId" + i).val();
                        grouponProductDetail.id = id;
                    }
                    grouponProductDetail.picture = picture;
                    delDetailList.push(grouponProductDetail);
                }
            }
        } else {
            alert("请至少上传一张详情图片~");
            return false;
        }
        if ($("#scrollPicture1").attr("src") != '') {
            for (var i = 1; i <= scrollNum; i++) {
                if ($("#scrollPicture" + i).attr("src") != null) {
                    var grouponScrollPicture = {};
                    var picture = $("#scrollPicture" + i).attr("src");
                    if ($("#scrollPictureId" + i) != null) {
                        var id = $("#scrollPictureId" + i).val();
                        grouponScrollPicture.id = id;
                    }
                    grouponScrollPicture.picture = picture;
                    delScrollList.push(grouponScrollPicture);
                }
            }
        } else {
            alert("请至少上传一张轮播图~");
            return false;
        }
        grouponProductDto.grouponProduct = grouponProduct;
        grouponProductDto.delScrollList = delScrollList;
        grouponProductDto.delDetailList = delDetailList;
        grouponProductDto.merchantList = merchantList;
        return true;
    }
    function loadMerchantUser() {
        $.ajax({
            type: "get",
            url: "/manage/merchantUser/findAll",
            async: false,
            success: function (result) {
                var list = result.data;
                for (var i = 0; i < list.length; i++) {
                    $("#selMcu").append("<option value=" + list[i].id + ">" + list[i].name + "</option>");
                }
            }
        });
    }
    // 跳转到商品列表页面
    function productList() {
        location.href = "/manage/grouponProduct/list";
    }
</script>

</html>
