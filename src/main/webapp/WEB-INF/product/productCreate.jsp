<%--
<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/3/10
  Time: 上午9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../commen.jsp" %>
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
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
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
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
        <div class="main">
            <div class="container-fluid">
                <button type="button" class="btn btn-primary btn-return" style="margin:10px;" onclick="goProductPage()">
                    返回商品列表
                </button>
                <hr>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="productNum" class="col-sm-2 control-label">商品序号</label>

                        <div class="col-sm-4">
                            <c:if test="${product==null}">
                                <input type="number" class="form-control" id="productNum"
                                       value="${sid+1}">
                            </c:if>
                            <c:if test="${product!=null}">
                                <input type="number" class="form-control" id="productNum"
                                       value="${product.sid}">
                            </c:if>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productType" class="col-sm-2 control-label">商品分类</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="productType" name="productType">
                                <option value="0">请选择</option>
                                <c:forEach var="productType" items="${productTypes}">
                                    <option value="${productType.id}">${productType.type}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productName" class="col-sm-2 control-label">商品名称</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="productName"
                                   value="${product.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productDescription" class="col-sm-2 control-label">商品描述</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="productDescription"
                                   value="${product.description}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productPic" class="col-sm-2 control-label">商品图片</label>

                        <div class="col-sm-4">
                            <div class="thumbnail">
                                <img id="productPicture" src="${product.picture}" alt="...">
                            </div>
                            <input type="file" class="form-control" id="productPic" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="smPic" class="col-sm-2 control-label">缩略图片</label>

                        <div class="col-sm-4">
                            <div class="thumbnail">
                                <img id="productThumb" src="${product.thumb}" alt="...">
                            </div>
                            <input type="file" class="form-control" id="smPic" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productPrice" class="col-sm-2 control-label">最高价格</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="productPrice"
                                   value="${product.price/100}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productMinPrice" class="col-sm-2 control-label">最低价格</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="productMinPrice"
                                   value="${product.minPrice/100}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productMinPrice" class="col-sm-2 control-label">初始销量(展示用)</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="customSale"
                                   value="${product.customSale}">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-4">
                            <input type="hidden" id="productId" value="${product.id}"/>
                            <input type="button" class="btn btn-primary" id="submit" value="提交"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>


<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    $(function () {
        $('#productPic').fileupload({
                                        dataType: 'json',
                                        maxFileSize: 5000000,
                                        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                        add: function (e, data) {
                                            data.submit();
                                        },
                                        done: function (e, data) {
                                            var resp = data.result;
                                            $('#productPicture').attr('src',
                                                                      '${ossImageReadRoot}/'
                                                                      + resp.data);
                                        }
                                    });
        $('#smPic').fileupload({
                                   dataType: 'json',
                                   maxFileSize: 5000000,
                                   acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                   add: function (e, data) {
                                       data.submit();
                                   },
                                   done: function (e, data) {
                                       var resp = data.result;
                                       $('#productThumb').attr('src',
                                                               '${ossImageReadRoot}/'
                                                               + resp.data);
                                   }
                               });
//        数组
        $("#productType option[value=${product.productType.id}]").attr("selected", true);

    });

    $("#submit").bind("click", function () {
        if($("#productType").val()==0||$("#productType").val()==null){
            alert("请选择分类");
            return;
        }
        var productDto = {};
        var productType = {};
        productType.id = $("#productType").val();
        productDto.name = $("#productName").val();
        productDto.productType = productType;
        productDto.sid = $("#productNum").val();
        productDto.description = $("#productDescription").val();
        productDto.price = $("#productPrice").val();
        productDto.minPrice = $("#productMinPrice").val();
        productDto.customSale = $("#customSale").val();
        productDto.picture = $("#productPicture").attr("src");
        productDto.thumb = $("#productThumb").attr("src");

        if (${product!=null}) {
            productDto.id = $("#productId").val();
            $.ajax({
                       type: "put",
                       url: "/manage/product",
                       contentType: "application/json",
                       data: JSON.stringify(productDto),
                       success: function (data) {
                           alert(data.msg);
                           setTimeout(function () {
                               location.href = "/manage/product";
                           }, 0);
                       }
                   });
        } else {
            $.ajax({
                       type: "post",
                       url: "/manage/product",
                       contentType: "application/json",
                       data: JSON.stringify(productDto),
                       success: function (data) {
                           alert(data.msg);
                           setTimeout(function () {
                               location.href = "/manage/product";
                           }, 0);
                       }
                   });
        }
    });


    //        控制字数
    $("#productName").bind("input propertychange", function () {
        if($(this).val().length>12){
            alert("已经是最大字数了哦！");
            $(this).val($(this).val().slice(0,13));
        }
    });
    $("#productDescription").bind("input propertychange", function () {
        if($(this).val().length>35){
            alert("已经是最大字数了哦！");
            $(this).val($(this).val().slice(0,36));
        }
    });

    function goProductPage(){
        location.href = "/manage/product"
    }

</script>
</body>
</html>


