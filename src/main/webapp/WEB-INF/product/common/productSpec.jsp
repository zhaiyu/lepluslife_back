<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/11/03
  Time: 上午11:49
  商品规格管理
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
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <style>
        table tr td img, form img {
            width: 80px;
            height: 80px;
        }

        .table > thead > tr > td, .table > thead > tr > th {
            line-height: 40px;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
            line-height: 60px;
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
    <%@include file="../../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main">
            <div class="container-fluid">
                <button type="button" class="btn btn-primary btn-create" style="margin:10px;"
                        onclick="goProductPage()">
                    返回商品列表
                </button>
                <hr>

                <div id="myTabContent" class="tab-content">

                    <div class="tab-pane fade in active" id="guige">
                        <button type="button" class="btn btn-primary createWarn"
                                style="margin:10px;" onclick="editProductSpec()">新增规格
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th class="text-center">单品ID</th>
                                <th class="text-center">规格品类</th>
                                <th class="text-center">正常价格</th>
                                <th class="text-center">最低价格</th>
                                <th class="text-center">状态</th>
                                <th class="text-center">缩略图</th>
                                <th class="text-center">库存</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${productSpecs}" var="productSpec">
                                <tr class="active">
                                    <td class="text-center">${productSpec.id}</td>
                                    <td class="text-center">${productSpec.specDetail}</td>
                                    <td class="text-center">${productSpec.price/100}</td>
                                    <td class="text-center">${productSpec.minPrice/100}</td>
                                    <td class="text-center">
                                        <c:if test="${productSpec.state==1}">
                                            已上架
                                        </c:if>
                                        <c:if test="${productSpec.state==0}">
                                            <font color="red">已下架</font>
                                        </c:if>
                                    </td>
                                    <td class="text-center"><img src="${productSpec.picture}"></td>
                                    <td class="text-center">${productSpec.repository}</td>
                                    <td class="text-center">
                                        <button type="button" class="btn btn-default createWarn"
                                                onclick="editProductSpecNumber(${productSpec.id},1)">
                                            进库
                                        </button>
                                        <button type="button" class="btn btn-default createWarn"
                                                onclick="editProductSpecNumber(${productSpec.id},0)">
                                            出库
                                        </button>
                                        <button type="button" class="btn btn-default createWarn"
                                                onclick="editProductSpec(${productSpec.id})">编辑
                                        </button>
                                        <c:if test="${productSpec.state==1}">
                                            <button type="button" class="btn btn-default upWarn"
                                                    data-target="#upWarn"
                                                    onclick="putOff(${productSpec.id})">下架
                                            </button>
                                        </c:if>
                                        <c:if test="${productSpec.state==0}">
                                            <button type="button" class="btn btn-default downWarn"
                                                    data-target="#downWarn"
                                                    onclick="putOn(${productSpec.id})">
                                                上架
                                            </button>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../../common/bottom.jsp" %>
</div>

<!--添加或修改规格提示框-->
<div class="modal" id="createSpecWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <input type="hidden" id="specId" value="">

                    <div class="form-group">
                        <label for="specDetail" class="col-sm-3 control-label">规格名称</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="specDetail"
                                   placeholder="请输入规格名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="price" class="col-sm-3 control-label">市场价</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="price"
                                   placeholder="请输入正常价格">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="minPrice" class="col-sm-3 control-label">最低价格</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="minPrice"
                                   placeholder="请输入最低价格">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="minScore" class="col-sm-3 control-label">可用积分</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="minScore"
                                   placeholder="请输入可用积分">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="repository" class="col-sm-3 control-label">初始库存</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="repository"
                                   placeholder="请输入初始库存">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="toMerchant" class="col-sm-3 control-label">商户返佣金额(元)</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="toMerchant"
                                   placeholder="商户返佣金额" value="0">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="toPartner" class="col-sm-3 control-label">合伙人返佣金额(元)</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="toPartner"
                                   placeholder="合伙人返佣金额" value="0">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="specPicture" class="col-sm-3 control-label">商品图片</label>

                        <div class="col-sm-4">
                            <!--<div class="thumbnail">-->
                            <img src="" alt="..." id="picture">
                            <!--</div>-->
                            <input type="file" class="form-control" id="specPicture" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="productSpec-confim" onclick="submit()">确认
                </button>
            </div>
        </div>
    </div>
</div>

<!--添加规格数量提示框-->
<div class="modal" id="addNumberWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">

                    <div class="form-group">
                        <label for="addNumber" class="col-sm-2 control-label">进库数量</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="addNumber"
                                   placeholder="请输入进库数量">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="detail1" class="col-sm-2 control-label">备注信息</label>

                        <div class="col-sm-4">
                            <textarea class="form-control" id="detail1"
                                      placeholder="请输入备注信息"></textarea>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="addNumber-confim">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--减少规格数量提示框-->
<div class="modal" id="deleteNumberWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">

                    <div class="form-group">
                        <label for="deleteNumber" class="col-sm-2 control-label">出库数量</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="deleteNumber"
                                   placeholder="请输入出库数量">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="detail2" class="col-sm-2 control-label">备注信息</label>

                        <div class="col-sm-4">
                            <textarea class="form-control" id="detail2"
                                      placeholder="请输入备注信息"></textarea>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="deleteNumber-confim">确认
                </button>
            </div>
        </div>
    </div>
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
                <h4>确定要下架商品规格吗？</h4>

                <p>下架后，该商品规格将不再展示给消费者，但您随时可以将该商品规格可以再次上架销售。</p>
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
                <h4>确定要上架商品规格吗？</h4>
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

<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    $(function () {
        $('#specPicture').fileupload({
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

    function editProductSpec(id) {//编辑规格
        if (id != null) {
            $.get("/manage/productSpec/" + id, null, function (data) {
                $('#specId').val(data.id);
                $("#picture").attr("src", data.picture);
                $("#specDetail").val(data.specDetail);
                $("#price").val(data.price / 100);
                $("#minPrice").val(data.minPrice / 100);
                $("#minScore").val(data.minScore);
                $("#toMerchant").val(data.toMerchant / 100);
                $("#toPartner").val(data.toPartner / 100);
                $("#repository").val(data.repository);
                $("#createSpecWarn").modal("show");
            });

        } else {
            $('#specId').val("");
            $("#picture").attr("src", "");
            $("#specDetail").val("");
            $("#price").val("");
            $("#minPrice").val("");
            $("#minScore").val("");
            $("#toMerchant").val("");
            $("#toPartner").val("");
            $("#repository").val("");
            $("#createSpecWarn").modal("show");
        }
    }

    function submit() {
        var productSpec = {};
        productSpec.picture = $("#picture").attr("src");
        productSpec.specDetail = $("#specDetail").val();
        productSpec.price = Math.round($("#price").val() * 100);
        productSpec.minPrice = Math.round($("#minPrice").val() * 100);
        productSpec.minScore = $("#minScore").val();
        productSpec.toMerchant = Math.round($("#toMerchant").val() * 100);
        productSpec.toPartner = Math.round($("#toPartner").val() * 100);
        productSpec.repository = $("#repository").val();
        var specId = $('#specId').val();
        if (specId != null || specId != "") {
            productSpec.id = specId;
        }
        var product = {};
        product.id = ${product.id};
        productSpec.product = product;
        $.ajax({
                   type: "post",
                   url: "/manage/productSpec",
                   contentType: "application/json",
                   data: JSON.stringify(productSpec),
                   success: function (data) {
                       setTimeout(function () {
                           location.href =
                           "/manage/product/specManage?id=${product.id}";
                       }, 0);
                   }
               });
    }
    function editProductSpecNumber(id, state) {//修改规格数量
        if (id != null) {
            var productSpec = {};
            productSpec.id = id;
            var productSpecLog = {};
            productSpecLog.productSpec = productSpec;
            if (state == 0) {
                $("#deleteNumber-confim").bind("click", function () {
                    productSpecLog.detail = $("#detail2").val();
                    productSpecLog.state = 0;
                    productSpecLog.number = $("#deleteNumber").val();
                    $.ajax({
                               type: "post",
                               url: "/manage/productSpec/editRepository",
                               contentType: "application/json",
                               data: JSON.stringify(productSpecLog),
                               success: function (data) {
                                   setTimeout(function () {
                                       location.href =
                                       "/manage/product/specManage?id=${product.id}";
                                   }, 0);
                               }
                           });
                });
                $("#deleteNumberWarn").modal("show");
            } else if (state == 1) {
                $("#addNumber-confim").bind("click", function () {
                    productSpecLog.detail = $("#detail1").val();
                    productSpecLog.state = 1;
                    productSpecLog.number = $("#addNumber").val();
                    $.ajax({
                               type: "post",
                               url: "/manage/productSpec/editRepository",
                               contentType: "application/json",
                               data: JSON.stringify(productSpecLog),
                               success: function (data) {
                                   setTimeout(function () {
                                       location.href =
                                       "/manage/product/specManage?id=${product.id}";
                                   }, 0);
                               }
                           });
                });
                $("#addNumberWarn").modal("show");
            }
        }
    }

    function putOn(id) {  //上架商品规格
        $("#putOn-confirm").bind("click", function () {
            $.post("/manage/productSpec/putOn/" + id, null, function (data) {
                setTimeout(function () {
                    location.href =
                    "/manage/product/specManage?id=${product.id}";
                }, 1000);

            });
        });
        $("#upWarn").modal("show");
    }

    function putOff(id) { //下架商品规格
        $("#putOff-confirm").bind("click", function () {
            $.post("/manage/productSpec/putOff/" + id, null, function (data) {
                setTimeout(function () {
                    location.href =
                    "/manage/product/specManage?id=${product.id}";
                }, 1000);
            });
        });
        $("#downWarn").modal("show");
    }

    //强制保留两位小数
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

    function goProductPage() {
        location.href = "/manage/product"
    }
</script>
</body>
</html>

