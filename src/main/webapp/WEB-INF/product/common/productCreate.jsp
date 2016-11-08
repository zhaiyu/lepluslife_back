<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/11/01
  Time: 上午9:49
  新建或修改普通商品
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../commen.jsp" %>
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
    <%@include file="../../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../../common/left.jsp" %>
    </div>
    <div class="m-right">
        <p style="padding-top: 20px">
            <button onclick="javascript:history.go(-1);">返回商品列表</button>
            创建商品
        </p>
        <input id="productId" type="hidden" value="${product.id}"/>

        <div>
            <div>商品序号</div>
            <c:if test="${product==null}">
                <input type="number" id="productNum"
                       value="${sid+1}">
            </c:if>
            <c:if test="${product!=null}">
                <input type="number" id="productNum"
                       value="${product.sid}">
            </c:if>
        </div>

        <div>
            <div>商品分类</div>
            <div style="width: 40%">
                <select id="productType" name="productType" style="width: 20%">
                    <option value="0">请选择</option>
                    <c:forEach var="productType" items="${productTypes}">
                        <option value="${productType.id}">${productType.type}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div>
            <div>商品角标</div>
            <div style="width: 40%">
                <select id="mark" name="mark" style="width: 20%">
                    <option value="0">--无--</option>
                    <c:forEach var="mark" items="${markList}">
                        <option value="${mark.id}">${mark.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div>
            <div>商品名称</div>
            <input type="text" id="name" class="check" value="${product.name}"/>
        </div>
        <div>
            <div>商品图片</div>
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
            <div>缩略图片</div>
            <div class="col-sm-6">
                <div>
                    <!--<div class="thumbnail">-->
                    <img style="width: 100%" src="${product.thumb}" alt="..." id="thumb">
                    <!--</div>-->
                    <input type="file" class="form-control" id="thumbPicture" name="file"
                           data-url="/manage/file/saveImage"/>
                </div>
            </div>
        </div>
        <div>
            <div>邮费</div>
            <div class="limited">
                <c:if test="${product.postage != 0}">
                    <div>
                        <input type="radio" name="postage" value="1"
                               checked="checked"><span>不包邮</span>

                        <div>
                            <span>邮费</span> <input name="postagePrice"
                                                   value="${product.postage/100}"><span><br/><br/>满多少包邮</span><input
                                name="freePrice"
                                value="${product.freePrice/100}">
                        </div>
                    </div>
                    <div><input type="radio" name="postage" value="0"
                                class="checked"><span>包邮</span>
                    </div>
                </c:if>
                <c:if test="${product.postage == 0}">
                    <div>
                        <input type="radio" name="postage" value="1"><span>不包邮</span>

                        <div>
                            <span>邮费</span> <input name="postagePrice"
                                                   value=""><span><br/><br/>满多少包邮</span><input
                                name="freePrice" value="">
                        </div>
                    </div>
                    <div><input type="radio" name="postage" value="0" checked="checked"
                                class="checked"><span>包邮</span>
                    </div>
                </c:if>
            </div>
        </div>

        <div>
            <div>价格(展示用)</div>
            <div class="w-prace">
                <span>所需积分：</span>
                <input type="number" placeholder="所需积分" onblur="noNumbers(event,0)"
                       id="productMinScore"
                       value="${product.minScore}"/><span> 最低价格：</span>
                <input type="number" placeholder="需支付的金额" onblur="noNumbers(event,2)"
                       id="productMinPrice"
                       value="${product.minPrice/100}"/>
                <span> 市场价(展示用)：</span>
                <input type="number" placeholder="市场价" onblur="noNumbers(event,2)" id="productPrice"
                       value="${product.price/100}"/>
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
    <%@include file="../../common/bottom.jsp" %>
</div>

<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script>

    var delSpecList = [], delScrollList = [], delDetailList = [];
    $(function () {
        // $("input[name=limited]").click(function (e) {
        // $("input[name=limited]").removeClass("checked");
        // $(this).attr("class", "checked");
        // });
        $("#productType option[value=${product.productType.id}]").attr("selected", true);
        if (${product.mark != null}) {
            $("#mark option[value=${product.mark.id}]").attr("selected", true);
        }

        $('input[name=noPointLimit]').TouchSpin({
                                                    min: 1,
                                                    max: 10000,
                                                    step: 1,
                                                    decimals: 0, //精度
                                                    maxboostedstep: 100,
                                                    prefix: ''
                                                });
        // $('input[name=buyLimit]').TouchSpin({
        // min: 1,
        // max: 100,
        // step: 1,
        // decimals: 0, //精度
        // maxboostedstep: 100,
        // prefix: ''
        // });
        $('input[name=hasPointLimit]').TouchSpin({
                                                     min: 0,
                                                     max: 10000,
                                                     step: 0.01,
                                                     decimals: 2, //精度
                                                     maxboostedstep: 100,
                                                     prefix: '￥'
                                                 });
        $('input[name=freePrice]').TouchSpin({
                                                 min: 0,
                                                 max: 10000,
                                                 step: 0.01,
                                                 decimals: 2, //精度
                                                 maxboostedstep: 100,
                                                 prefix: '￥'
                                             });
        $('input[name=postagePrice]').TouchSpin({
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
        $('#thumbPicture').fileupload({
                                          dataType: 'json',
                                          maxFileSize: 5000000,
                                          acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                          add: function (e, data) {
                                              data.submit();
                                          },
                                          done: function (e, data) {
                                              var resp = data.result;
                                              $('#thumb').attr('src',
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

    $("#save").click(function () {
        var product = {}, productType = {}, mark = {}, scrollPictureList = [], productDetailList = [], goOn = 0;

        product.id = $("#productId").val();
        //商品序号
        if ($("#productNum").val() == "") {
            alert("请输入商品序号");
            $("#productNum").focus();
            return
        }
        product.sid = $("#productNum").val();
        //商品分类
        var typeId = $("#productType").val();
        if (typeId == 0) {
            alert("请选择商品分类");
            $("#productType").focus();
            return
        }
        productType.id = typeId;
        product.productType = productType;
        //商品角标
        var markId = $("#mark").val();
        if (markId != 0) {
            mark.id = markId;
            product.mark = mark;
        }

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
        //商品缩略图片
        if ($("#thumb").attr("src") == "") {
            alert("请上传缩略图片");
            return
        }
        product.thumb = $("#thumb").attr("src");
        //邮费
        var postage = $("input[name='postage']:checked").val();
        if (postage == null) {
            alert("请选择是否包邮");
            return
        } else if (postage == 0) {
            product.postage = postage;
            product.freePrice = 0;
        } else if (postage == 1) {
            var postagePrice = $("input[name=postagePrice]").val();
            var freePrice = $("input[name=freePrice]").val();
            if (postagePrice == "") {
                alert("请输入邮费");
                $("input[name=postagePrice]").focus();
                return
            } else {
                product.postage = postagePrice * 100;
            }
            if (freePrice == "") {
                alert("请输入满包邮");
                $("input[name=freePrice]").focus();
                return
            } else {
                product.freePrice = freePrice * 100;
            }
        }
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
            // scrollPicture.product = product;
            scrollPictureList.push(scrollPicture);
        });
        if (goOn == 1) {
            return
        }
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
        var productDto = {};
        productDto.product = product;
        productDto.scrollPictureList = scrollPictureList;
        productDto.productDetailList = productDetailList;
        productDto.delScrollList = delScrollList;
        productDto.delDetailList = delDetailList;

        $.ajax({
                   type: "post",
                   url: "/manage/product/save",
                   contentType: "application/json",
                   data: JSON.stringify(productDto),
                   success: function (data) {
                       if (data.status == 200) {
                           location.href = "/manage/product"
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
</body>
</html>

