<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/8/29
  Time: 上午10:02
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
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">

    <link type="text/css" rel="stylesheet" href="${resourceUrl}/product/css/edit.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>

    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>

    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
</head>
<style>
    input[type = radio] {
        width: 10%;
    }

    input[type = number] {
        width: 30%;
    }

    .limited, .youfei, .banner, .information {
        width: 70% !important;
    }

    .specifications {
        width: 80% !important;
    }

    .limited > div {
        width: 50%;
        float: left;
    }

    .limited:after {
        content: '\20';
        display: block;
        clear: both;
    }

    .specifications input {
        width: 20%;
    }

    .specifications input[type = number] {
        width: 12%;
    }

    .addImg {
        display: block;
        width: 150px;
        height: 150px;
    }

    .addFile {
        width: 150px;
    }

    .banner > div, .information > div {
        width: 25%;
        float: left;
    }

    .limited .input-group {
        width: 40% !important;
        float: right;
        margin-right: 40%;
    }

    .input-group {
        width: 40% !important;
    }

    .banner:after, .information:after {
        content: '\20';
        display: block;
        clear: both;
    }

    .m-right {
        width: 100%;
    }

    .m-right > div > div:first-child {
        width: 10% !important;
    }

    .del {
        padding: 5px 10px;
        margin: -200px 5px 0 0 !important;
        float: right;
        background-color: rgba(255, 0, 0, 0.7);
        -webkit-border-radius: 50% 50%;
        -moz-border-radius: 50% 50%;
        border-radius: 50% 50%;
        color: #FFFFFF;
        z-index: 100;
        cursor: pointer;
    }

    .w-prace {
        width: 90% !important;
    }

    .w-prace input {
        width: 20% !important;
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
        <p style="padding-top: 20px">
            <button onclick="javascript:history.go(-1);">返回限量秒杀</button>
            创建秒杀商品
        </p>
        <input id="productId" type="hidden" value="${product.id}"/>

        <div>
            <div>商品名称</div>
            <input type="text" id="name" class="check" value="${product.name}"/>
        </div>
        <div>
            <div>商品图片(宽高比:336*210)</div>
            <div class="col-sm-6">
                <div>
                    <!--<div class="thumbnail">-->
                    <img style="width: 100%" src="${product.picture}" alt="..." id="picture">
                    <!--</div>-->
                    <input type="file" class="form-control" id="productPicture" name="file"
                           data-url="/manage/file/saveImage"/>
                </div>
            </div>
        </div>
        <div>
            <div>个人兑换限制</div>
            <div class="limited">
                <c:if test="${product.buyLimit == 0}">
                    <div>
                        <input type="radio" name="limited" value="1"><span>有限制</span>

                        <div>
                            <span>每人可兑换</span><input name="buyLimit"
                                                     value="">
                        </div>
                    </div>
                    <div><input type="radio" name="limited" value="0" checked="checked"
                                class="checked"><span>无限制，可随意领取</span>
                    </div>
                </c:if>
                <c:if test="${product.buyLimit != 0}">
                    <div>
                        <input type="radio" name="limited" value="1"
                               checked="checked" class="checked"><span>有限制</span>

                        <div>
                            <span>每人可兑换</span><input name="buyLimit"
                                                     value="${product.buyLimit}">
                        </div>
                    </div>
                    <div><input type="radio" name="limited" value="0"><span>无限制，可随意领取</span></div>
                </c:if>
            </div>
        </div>
        <div>
            <div>
                规格
            </div>
            <div class="specifications">
                <c:forEach items="${specList}" var="spec" step="1" varStatus="specNumber">
                    <div id="specifications${specNumber.count}">
                        <input type="hidden" value="${spec.id}"/>
                        名称：<input type="text" placeholder="请输入规格名称" value="${spec.specDetail}"/>
                        库存：<input type="number" placeholder="库存" value="${spec.repository}"/>
                        <span>积分：</span>
                        <input type="number" placeholder="所需积分"
                               value="${spec.minScore}"/><span>支付价：</span>
                        <input type="number" placeholder="需支付的金额" onblur="noNumbers(event,2)"
                               value="${spec.minPrice/100}"/>
                        <span>市场价：</span>
                        <input type="number" placeholder="市场价" onblur="noNumbers(event,2)"
                               value="${spec.price/100}"/>
                        <button onclick="delSpec('specifications${specNumber.count}');"
                                style="margin: 0">删除
                        </button>
                    </div>
                </c:forEach>
            </div>
            <div style="margin: 10px 0 0 10%">
                <button onclick="addGG('specifications');">添加规格</button>
            </div>
        </div>
        <div>
            <div>价格(展示用)</div>
            <div class="w-prace">
                <span>所需积分：</span>
                <input type="number" placeholder="所需积分" onblur="noNumbers(event,0)"
                       id="productMinScore"
                       value="${product.minScore}"/><span>  实际价格(支付用)：</span>
                <input type="number" placeholder="需支付的金额" onblur="noNumbers(event,2)"
                       id="productMinPrice"
                       value="${product.minPrice/100}"/>
                <span>  市场价(展示用)：</span>
                <input type="number" placeholder="市场价" onblur="noNumbers(event,2)" id="productPrice"
                       value="${product.price/100}"/>
            </div>
        </div>
        <div class="yf">
            <div>邮费</div>
            <div class="youfei">
                <input placeholder="请输入邮费金额" id="hasPointLimit" name="hasPointLimit"
                       value="${product.postage/100}"/>
            </div>
        </div>
        <div>
            <div>初始销量(展示用)</div>
            <div class="youfei">
                <input placeholder="请输入初始销量" id="customSale" name="noPointLimit"
                       value="${product.customSale}"/>
            </div>
        </div>
        <div>
            <div>轮播图(宽高比:750*600)</div>
            <div class="banner">
                <c:forEach items="${scrollList}" var="scroll" step="1"
                           varStatus="scrollNumber">
                    <div id="banner${scrollNumber.count}">
                        <input type="hidden" value="${scroll.id}"/>

                        <div>
                            <img src="${scroll.picture}" class="addImg">
                        </div>
                        <div>
                            <input type="file" class="form-control" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                        <button onclick="delScroll('banner${scrollNumber.count}');" class="del">x
                        </button>
                    </div>
                </c:forEach>
            </div>
            <div style="margin: 10px 0 0 18%">
                <button onclick="addScroll();">添加轮播图</button>
            </div>
        </div>
        <div>
            <div>详情图</div>
            <div class="information">
                <c:forEach items="${detailList}" var="detail" step="1"
                           varStatus="detailNumber">
                    <div id="information${detailNumber.count}">
                        <input type="hidden" value="${detail.id}"/>

                        <div>
                            <img src="${detail.picture}" class="addImg">
                        </div>
                        <div>
                            <input type="file" class="form-control" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                        <button onclick="delDetail('information${detailNumber.count}');"
                                class="del">x
                        </button>
                    </div>
                </c:forEach>
            </div>
            <div style="margin: 10px 0 0 18%">
                <button onclick="addDetail();">添加详情图</button>
            </div>
        </div>
        <div>
            <button id="save">保存</button>
            <button>取消</button>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script>
    console.log("${specList}");
    var delSpecList = [], delScrollList = [], delDetailList = [];
    $(function () {
        $("input[name=limited]").click(function (e) {
            $("input[name=limited]").removeClass("checked");
            $(this).attr("class", "checked");
        });
        $('input[name=noPointLimit]').TouchSpin({
                                                    min: 1,
                                                    max: 10000,
                                                    step: 1,
                                                    decimals: 0, //精度
                                                    maxboostedstep: 100,
                                                    prefix: ''
                                                });
        $('input[name=buyLimit]').TouchSpin({
                                                    min: 1,
                                                    max: 100,
                                                    step: 1,
                                                    decimals: 0, //精度
                                                    maxboostedstep: 100,
                                                    prefix: ''
                                                });
        $('input[name=hasPointLimit]').TouchSpin({
                                                     min: 0,
                                                     max: 10000,
                                                     step: 0.01,
                                                     decimals: 2, //精度
                                                     maxboostedstep: 100,
                                                     prefix: '￥'
                                                 });
        $('#productPicture').fileupload({
                                            dataType: 'json',
                                            maxFileSize: 5000000,
                                            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                            add: function (e, data) {
                                                data.submit();
                                            },
                                            done: function (e, data) {
                                                var resp = data.result;
                                                $('#picture').attr('src',
                                                                   '${ossImageReadRoot}/'
                                                                   + resp.data);
                                            }
                                        });
    });
    function noNumbers(e, len) {
        var val = e.currentTarget.value;
        if (!val) {
            return;
        }
        var b = parseFloat(val);
        e.currentTarget.value = Number(b).toFixed(len);
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
                        ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468")
                                : "") + week[this.getDay() + ""]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt =
                fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr((""
                                                                                                 + o[k]).length)));
            }
        }
        return fmt;
    };
</script>
<script>
    $("#allCheck").click(function (e) {
        if (this.checked) {
            $("#w-tab :checkbox").attr("checked", true);
        } else {
            $("#w-tab :checkbox").attr("checked", false);
        }
    });
    $("#save").click(function () {
        var product = {}, productSpecList = [], scrollPictureList = [], productDetailList = [], goOn = 0;
//        var valArr = new Array;
//        $("#w-tab :checkbox[checked]").each(function (i) {
//            valArr[i] = $(this).attr("id");
//        });
//        var vals = valArr.join(',');//转换为逗号隔开的字符串
//        alert(vals);
        product.id = $("#productId").val();
        //商品名称
        if ($("#name").val() == "") {
            alert("请输入商品名称");
            $("#name").focus();
            return
        }
        product.name = $("#name").val();
        //商品图片
        if ($("#picture").attr("src") == "") {
            alert("请上传商品图片");
            return
        }
        product.picture = $("#picture").attr("src");
        //兑换限制
        var limitCheck = $(".checked").val();
        if (limitCheck == null) {
            alert("请选择兑换限制");
        } else if (limitCheck == 0) {
            product.buyLimit = limitCheck;
        } else if (limitCheck == 1) {
            var buyLimit = $("input[name=buyLimit]").val();
            if (buyLimit == "") {
                alert("请输入限购数量");
                $("input[name=buyLimit]").focus();
                return
            } else {
                product.buyLimit = buyLimit;
            }
        }
        //规格
        var specList = $(".specifications > div");
        if (specList.length < 1) {
            alert("请至少添加一种规格");
            return
        }
        specList.each(function () {
            var thisId = $(this).attr('id');
            var thisSpecList = $("#" + thisId + " > input");
            var productSpec = {};
            productSpec.id = $(thisSpecList[0]).val();
            if ($(thisSpecList[1]).val() == "") {
                alert("请输入规格名称");
                goOn = 1;
                $(thisSpecList[1]).focus();
                return false;
            }
            productSpec.specDetail = $(thisSpecList[1]).val();
            if ($(thisSpecList[2]).val() == "") {
                alert("请输入库存");
                goOn = 1;
                $(thisSpecList[2]).focus();
                return false;
            }
            productSpec.repository = $(thisSpecList[2]).val();
            if ($(thisSpecList[3]).val() == "") {
                alert("请输入该规格所需积分");
                goOn = 1;
                $(thisSpecList[3]).focus();
                return false;
            }
            productSpec.minScore = $(thisSpecList[3]).val();
            if ($(thisSpecList[4]).val() == "") {
                alert("请输入该规格所需支付价");
                goOn = 1;
                $(thisSpecList[4]).focus();
                return false;
            }
            productSpec.minPrice = $(thisSpecList[4]).val() * 100;
            if ($(thisSpecList[5]).val() == "") {
                alert("请输入该规格市场价");
                goOn = 1;
                $(thisSpecList[5]).focus();
                return false;
            }
            productSpec.price = $(thisSpecList[5]).val() * 100;
            //  productSpec.product = product;
            productSpecList.push(productSpec);
        });
        if (goOn == 1) {
            return
        }
        console.log(productSpecList);
        console.log('------------');
        //展示用价格
        if ($("#productMinScore").val() == "") {
            alert("请输入最低积分");
            $("#productMinScore").focus();
            return
        }
        product.minScore = $("#productMinScore").val();
        if ($("#productMinPrice").val() == "") {
            alert("请输入支付价格");
            $("#productMinPrice").focus();
            return
        }
        product.minPrice = $("#productMinPrice").val() * 100;
        if ($("#productPrice").val() == "") {
            alert("请输入市场价");
            $("#productPrice").focus();
            return
        }
        product.price = $("#productPrice").val() * 100;
        //邮费
        if ($("#hasPointLimit").val() == "") {
            alert("请输入邮费");
            $("#hasPointLimit").focus();
            return
        }
        product.postage = $("#hasPointLimit").val() * 100;
        //自定义初始销量
        if ($("#customSale").val() == "") {
            alert("请输入初始销量");
            $("#customSale").focus();
            return
        }
        product.customSale = $("#customSale").val();
        //轮播图
        var scrollList = $(".banner > div");
        if (scrollList.length < 1) {
            alert("请至少上传一张轮播图");
            return
        }
        var scrollSid = 1;
        scrollList.each(function () {
            var scrollPicture = {};
            scrollPicture.id = $(this).find('input').eq(0).val();
            var scrollUrl = $(this).find('div').eq(0).find('img').eq(0).attr('src');
            if (scrollUrl == null || scrollUrl == "") {
                alert("请上传轮播图");
                goOn = 1;
                return false;
            }
            scrollPicture.picture = scrollUrl;
            scrollPicture.sid = scrollSid++;
            //  scrollPicture.product = product;
            scrollPictureList.push(scrollPicture);
        });
        if (goOn == 1) {
            return
        }
        console.log(scrollPictureList);
        console.log('--------------');
        //详情图
        var detailList = $(".information > div");
        if (detailList.length < 1) {
            alert("请至少上传一张详情图");
            return
        }
        var detailSid = 1;
        detailList.each(function () {
            var productDetail = {};
            productDetail.id = $(this).find('input').eq(0).val();
            var detailUrl = $(this).find('div').eq(0).find('img').eq(0).attr('src');
            if (detailUrl == null || detailUrl == "") {
                alert("请上传详情图");
                goOn = 1;
                return false;
            }
            productDetail.picture = detailUrl;
            productDetail.sid = detailSid++;
            // productDetail.product = product;
            productDetailList.push(productDetail);
        });
        if (goOn == 1) {
            return
        }
        console.log(productDetailList);
        console.log('--------------');
        var productDto = {};
        productDto.product = product;
        productDto.productSpecList = productSpecList;
        productDto.scrollPictureList = scrollPictureList;
        productDto.productDetailList = productDetailList;
        productDto.delScrollList = delScrollList;
        productDto.delDetailList = delDetailList;
        productDto.delSpecList = delSpecList;

        $.ajax({
                   type: "post",
                   url: "/manage/limit/save",
                   contentType: "application/json",
                   data: JSON.stringify(productDto),
                   success: function (data) {
                       if (data.status == 200) {
                           location.href = "/manage/limit?type=1"
                       } else {
                           alert("保存异常");
                       }
                   }
               });
    });
</script>
<script>
    var n = '${scrollSize}';
    function addScroll() {
        n++;
        var currId = "banner" + n;
        var picture = "scrollPicture" + n;
        var pic = "scrollPic" + n;
        $(".banner").append(
                $("<div></div>").attr("id", currId).attr("style", "margin:5px;").append(
                        $("<input>").attr("type", "hidden").attr("value", "")
                ).append(
                        $("<div></div>").append(
                                $("<img>").attr("id", currId).attr("class", "addImg").attr("id",
                                                                                           pic)
                        )
                ).append(
                        $("<div></div>").append(
                                $("<input>").attr("type", "file").attr("id", picture).attr("name",
                                                                                           "file").attr("class",
                                                                                                        "form-control").attr("data-url",
                                                                                                                             "/manage/file/saveImage")
                        )
                ).append(
                        $("<button></button>").attr("onclick",
                                                    "delScroll('" + currId + "');").attr("class",
                                                                                         "del").html("x")
                )
        );

        $('#' + picture).fileupload({
                                        dataType: 'json',
                                        maxFileSize: 5000000,
                                        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                        add: function (e, data) {
                                            data.submit();
                                        },
                                        done: function (e, data) {
                                            var resp = data.result;
                                            $('#' + pic).attr('src',
                                                              '${ossImageReadRoot}/'
                                                              + resp.data);
                                        }
                                    });
    }

    function delScroll(id) {
        var scrollId = $("#" + id).find("input").eq(0).val();
        if (scrollId != "") { //添加需要删除的轮播图(数据库中已存在)
            var scrollPicture = {};
            scrollPicture.id = scrollId;
            delScrollList.push(scrollPicture);
            console.log(delScrollList);
        }
        $("#" + id).remove();
    }
</script>
<script>
    var m = '${detailSize}';
    function addDetail() {
        m++;
        var currId = "information" + m;
        var picture = "detailPicture" + m;
        var pic = "detailPic" + m;
        $(".information").append(
                $("<div></div>").attr("id", currId).attr("style", "margin:5px;").append(
                        $("<input>").attr("type", "hidden").attr("value", "")
                ).append(
                        $("<div></div>").append(
                                $("<img>").attr("id", currId).attr("class", "addImg").attr("id",
                                                                                           pic)
                        )
                ).append(
                        $("<div></div>").append(
                                $("<input>").attr("type", "file").attr("name", "file").attr("id",
                                                                                            picture).attr("class",
                                                                                                          "form-control").attr("data-url",
                                                                                                                               "/manage/file/saveImage")
                        )
                ).append(
                        $("<button></button>").attr("onclick",
                                                    "delDetail('" + currId + "');").attr("class",
                                                                                         "del").html("x")
                )
        );
        $('#' + picture).fileupload({
                                        dataType: 'json',
                                        maxFileSize: 5000000,
                                        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                        add: function (e, data) {
                                            data.submit();
                                        },
                                        done: function (e, data) {
                                            var resp = data.result;
                                            $('#' + pic).attr('src',
                                                              '${ossImageReadRoot}/'
                                                              + resp.data);
                                        }
                                    });
    }

    function delDetail(id) {
        var detailId = $("#" + id).find("input").eq(0).val();
        if (detailId != "") { //添加需要删除的详情图(数据库中已存在)
            var productDetail = {};
            productDetail.id = detailId;
            delDetailList.push(productDetail);
            console.log(delDetailList);
        }
        $("#" + id).remove();
    }
</script>
<script>
    var specSize = '${specSize}';
    function addGG(id) {
        specSize++;
        $("." + id).append(
                $("<div></div>").attr("id", "specifications" + specSize).attr("style",
                                                                              "margin:10px 0;").append(
                        $("<input>").attr("type", "hidden").attr("value", "")
                ).append(
                        $("<span></span>").html("名称："),
                        $("<input>").attr("type", "text").attr("placeholder", "请输入规格名称")
                ).append(
                        $("<span></span>").html("库存："),
                        $("<input>").attr("type", "number").attr("style",
                                                                 "margin:0 1% 0 0;").attr("placeholder",
                                                                                          "库存")
                ).append(
                        $("<span></span>").html("积分：")
                ).append(
                        $("<input>").attr("type", "number").attr("style",
                                                                 "margin:0 1% 0 0;").attr("placeholder",
                                                                                          "所需积分")
                ).append(
                        $("<span></span>").html("支付价：")
                ).append(
                        $("<input>").attr("type", "number").attr("onblur",
                                                                 "noNumbers(event,2)").attr("style",
                                                                                            "margin:0 1% 0 1%;").attr("placeholder",
                                                                                                                      "需支付的金额")
                ).append(
                        $("<span></span>").html("市场价：")
                ).append(
                        $("<input>").attr("type", "number").attr("onblur",
                                                                 "noNumbers(event,2)").attr("placeholder",
                                                                                            "市场价")
                ).append(
                        $("<button></button>").attr("onclick",
                                                    "delSpec('specifications" + specSize
                                                    + "');").html("删除")
                )
        );
    }
    function delSpec(id) {
        var specId = $("#" + id).find("input").eq(0).val();
        if (specId != "") { //添加需要删除的规格(数据库中已存在)
            var productSpec = {};
            productSpec.id = specId;
            delSpecList.push(productSpec);
            console.log(delSpecList);
        }
        $("#" + id).remove();
    }
</script>
</body>
</html>

