<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/3/9
  Time: 下午1:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="productUrl" value="http://www.lepluslife.com/weixin/product/"></c:set>
<html>
<!DOCTYPE>
<style>
    .pagination > li > a.focusClass {
        background-color: #ddd;
    }
</style>
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
        table tr td img {
            width: 80px;
        }

        .table > thead > tr > td, .table > thead > tr > th {
            line-height: 40px;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
            line-height: 80px;
        }

        .active img {
            width: 80px;
            height: 80px;
        }
    </style>
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
        <div class="main">
            <div class="container-fluid">
                <button type="button" class="btn btn-primary " style="margin:10px;"
                        onclick="goProductCreatePage()">创建商品
                </button>
                <hr>
                <ul class="nav nav-tabs" style="margin-bottom: 10px">
                    <c:if test="${productCriteria.state==1}">
                        <li class="active"><a data-toggle="tab">已上架</a></li>
                        <li><a data-toggle="tab" onclick="stateChange(0)">已下架</a></li>
                    </c:if>
                    <c:if test="${productCriteria.state==0}">
                        <li><a data-toggle="tab" onclick="stateChange(1)">已上架</a></li>
                        <li class="active"><a data-toggle="tab">已下架</a></li>
                    </c:if>

                    <li class="dropdown">
                        <c:if test="${productCriteria.productType!=null}">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                               aria-haspopup="true"
                               aria-expanded="false">${productCriteria.productType.type}<span
                                    class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${productTypes}" var="productType">
                                    <c:if test="${productCriteria.productType.id==productType.id}">
                                    </c:if>
                                    <c:if test="${productCriteria.productType.id!=productType.id}">
                                        <li>
                                            <a href="/manage/product?productType=${productType.id}&state=${productCriteria.state}">${productType.type}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                                <li>
                                    <a href="/manage/product?productType=&state=${productCriteria.state}">所有</a>
                                </li>
                            </ul>
                        </c:if>
                        <c:if test="${productCriteria.productType==null}">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                               aria-haspopup="true" aria-expanded="false">分类<span
                                    class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${productTypes}" var="productType">
                                    <li>
                                        <a href="/manage/product?productType=${productType.id}&state=${productCriteria.state}">${productType.type}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>

                    </li>
                </ul>
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="text-center">商品分类</th>
                        <th class="text-center">商品序号</th>
                        <th class="text-center">商品ID</th>
                        <th class="text-center">商品名称</th>
                        <th class="text-center">商品图片</th>
                        <th class="text-center">正常单价(元）</th>
                        <th class="text-center">最低单价(元）</th>
                        <th class="text-center">销量</th>
                        <th class="text-center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${products}" var="product">
                        <tr class="active">
                            <td class="text-center">${product.productType.type}</td>
                            <td class="text-center">${product.sid}</td>
                            <td class="text-center">${product.id}</td>
                            <td class="text-center">${product.name}</td>
                            <td class="text-center"><img src="${product.thumb}" alt="..."></td>
                            <td class="text-center">${product.price/100}</td>
                            <td class="text-center">${product.minPrice/100}</td>
                            <td class="text-center">${product.saleNumber}</td>
                            <td class="text-center">
                                <c:if test="${product.state==1}">
                                    <button type="button" class="btn btn-default upWarn"
                                            data-target="#upWarn" onclick="putOff(${product.id})">下架
                                    </button>
                                </c:if>
                                <c:if test="${product.state==0}">
                                    <button type="button" class="btn btn-default downWarn"
                                            data-target="#downWarn" onclick="putOn(${product.id})">
                                        上架
                                    </button>
                                </c:if>
                                    <%--<button type="button" class="btn btn-default deleteWarn"--%>
                                    <%--data-target="#deleteWarn"--%>
                                    <%--onclick="deleteProduct(${product.id})">删除--%>
                                    <%--</button>--%>
                                <button type="button" class="btn btn-default"
                                        onclick="editProduct(${product.id})">编辑
                                </button>
                                <button type="button" class="btn btn-default"
                                        onclick="pictureManage(${product.id})">内容管理
                                </button>
                                <button type="button" class="btn btn-default"
                                        onclick="qrCodeManage(${product.id})">二维码
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <nav class="pull-right">
                    <ul class="pagination pagination-lg">
                        <c:if test="${currentPage>1}">
                            <li><a aria-label="Previous"
                                   onclick="pageChange(${currentPage-1})"><span
                                    aria-hidden="true">«</span></a></li>
                        </c:if>
                        <c:if test="${pages>1}">
                            <c:if test="${pages<=5}">
                                <c:forEach begin="1" end="${pages}" varStatus="index">
                                    <c:if test="${index.count==currentPage}">
                                        <li><a href="/manage/product"
                                               class="focusClass">${currentPage}</a></li>
                                    </c:if>
                                    <c:if test="${index.count!=currentPage}">
                                        <li>
                                            <a onclick="pageChange(${index.count})">${index.count}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${pages>5}">
                                <c:if test="${currentPage<=3}">
                                    <c:forEach begin="1" end="5" varStatus="index">
                                        <c:if test="${index.count==currentPage}">
                                            <li><a class="focusClass">${currentPage}</a></li>
                                        </c:if>
                                        <c:if test="${index.count!=currentPage}">
                                            <li>
                                                <a onclick="pageChange(${index.count})">${index.count}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${currentPage>3}">
                                    <c:if test="${pages-currentPage>=2}">
                                        <li>
                                            <a onclick="pageChange(${currentPage-2})">${currentPage-2}</a>
                                        </li>
                                        <li>
                                            <a onclick="pageChange(${currentPage-1})">${currentPage-1}</a>
                                        </li>
                                        <li><a
                                                class="focusClass">${currentPage}</a></li>
                                        <li>
                                            <a onclick="pageChange(${currentPage+1})">${currentPage+1}</a>
                                        </li>
                                        <li>
                                            <a onclick="pageChange(${currentPage+2})">${currentPage+2}</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${pages-currentPage<2}">
                                        <c:forEach begin="${pages-5}" end="${pages}"
                                                   varStatus="index">
                                            <c:if test="${index.current==currentPage}">
                                                <li><a
                                                        class="focusClass">${currentPage}</a></li>
                                            </c:if>
                                            <c:if test="${index.current!=currentPage}">
                                                <li>
                                                    <a onclick="pageChange(${index.current})">${index.current}</a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                            </c:if>
                        </c:if>
                        <c:if test="${pages>currentPage}">
                            <li><a onclick="pageChange(${currentPage+1})" aria-label="Next"><span
                                    aria-hidden="true">»</span></a></li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<!--下架提示框-->
<div class="modal" id="downWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">下架</h4>
            </div>
            <div class="modal-body">
                <h4>确定要下架商品吗？</h4>

                <p>下架后，该商品将不再展示给消费者，但您随时可以将该商品可以再次上架销售。</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        id="putOff-cancle">取消
                </button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="putOff-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--上架提示框-->
<div class="modal" id="upWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">上架</h4>
            </div>
            <div class="modal-body">
                <h4>确定要上架商品吗？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="putOn-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--删除提示框-->
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">上架</h4>
            </div>
            <div class="modal-body">
                <h4>确定要删除商品？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id=delete-confirm>确认
                </button>
            </div>
        </div>
    </div>
</div>

<!--二维码提示框-->
<div class="modal" id="qrWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">二维码</h4>
            </div>
            <div class="modal-body">
                <h5 id="productUrl"></h5>

                <div class="center-block">
                    <img id="qrCode" src="" class="center-block" alt="">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<!--如果只是做布局的话不需要下面两个引用-->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>

    function putOn(id) {
        $("#putOn-confirm").bind("click", function () {
            $.post("/manage/product/putOn/" + id, null, function (data) {
                alert(data.msg);
                setTimeout(function () {
                    location.reload(true);
                }, 1000);

            });
        });
        $("#upWarn").modal("show");
    }

    function putOff(id) {
        $("#putOff-confirm").bind("click", function () {
            $.post("/manage/product/putOff/" + id, null, function (data) {
                alert(data.msg);
                setTimeout(function () {
                    location.reload(true);
                }, 1000);

            });
        });
        $("#downWarn").modal("show");
    }

    function deleteProduct(id) {
        $("#delete-confirm").bind("click", function () {
            $.ajax({
                       type: "delete",
                       url: "/manage/product/" + id,
                       success: function (data) {
                           alert(data.msg);
                           setTimeout(function () {
                               location.reload(true);
                           }, 1000);
                       }
                   });
        });
        $("#deleteWarn").modal("show");
    }
    function editProduct(id) {
        location.href = "/manage/product/edit?id=" + id;
    }
    function pictureManage(id) {
        location.href = "/manage/product/pictureManage?id=" + id;
    }
    function goProductCreatePage() {
        location.href = "/manage/product/create";
    }

    function stateChange(state) {
        location.href =
        "/manage/product?productType=${productCriteria.productType.id}" + "&state=" + state
        + "&page=1";
    }

    function pageChange(page) {
        location.href =
        "/manage/product?productType=${productCriteria.productType.id}"
        + "&state=${productCriteria.state}" + "&page=" + page;
    }

    function qrCodeManage(id) {
        $.ajax({
                   type: "get",
                   data: {id: id},
                   url: "/manage/product/qrCode/",
                   success: function (data) {
                       var url = "${productUrl}" + id;
                       $("#productUrl").text(url);
                       $("#qrCode").attr("src", data.msg);
                       $("#qrWarn").modal("show");
                   }
               });
    }

</script>
</body>
</html>

