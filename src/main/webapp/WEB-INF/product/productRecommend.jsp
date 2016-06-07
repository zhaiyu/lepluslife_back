<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/6/6
  Time: 下午2:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE>
<style>

    .table > thead > tr > td, .table > thead > tr > th {
        line-height: 40px;
    }

    .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
        line-height: 80px;
    }

    .active img {
        /*width: 80px;*/
        height: 80px;
    }

    table tr td img, form img {
        /*width: 80px;*/
        height: 80px;
    }

</style>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>乐+生活 后台模板管理系统</title>
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>

    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>

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
            <c:if test="${state==0}">
                <h3 style="margin:20px;"> APP首页推荐商品</h3>
            </c:if>
            <c:if test="${state==1}">
                <h3 style="margin:20px;"> APP首页推荐商家</h3>
            </c:if>
            <div class="container-fluid">
                <c:if test="${state==0}">
                    <button type="button"
                            class="btn btn-primary"
                            style="margin:10px;"
                            onclick="editProRec(null)">新增推荐商品
                    </button>
                </c:if>
                <c:if test="${state==1}">
                    <button type="button"
                            class="btn btn-primary"
                            style="margin:10px;"
                            onclick="editMerRec(null)">新增推荐商家
                    </button>
                </c:if>
                <hr>
                <ul class="nav nav-tabs" style="margin-bottom: 10px">
                    <c:if test="${state==0}">
                        <li class="active"><a data-toggle="tab">推荐商品</a>
                        </li>
                        <li><a data-toggle="tab" onclick="stateChange(1)">推荐商家</a>
                        </li>
                    </c:if>
                    <c:if test="${state==1}">
                        <li><a data-toggle="tab" onclick="stateChange(0)">推荐商品</a>
                        </li>
                        <li class="active"><a data-toggle="tab">推荐商家</a>
                        </li>
                    </c:if>
                </ul>
                <c:if test="${state==0}">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="text-center">推荐序号</th>
                            <th class="text-center">商品海报</th>
                            <th class="text-center">推荐商品ID</th>
                            <th class="text-center">推荐商品名称</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${recommends}" var="recommend">
                            <tr class="active">
                                <td class="text-center">${recommend.sid}</td>
                                <td class="text-center">
                                    <img src="${recommend.picture}"/>
                                </td>
                                <td class="text-center">
                                        ${recommend.product.id}
                                </td>
                                <td class="text-center">
                                        ${recommend.product.name}
                                </td>

                                <td class="text-center">
                                    <button type="button"
                                            class="btn btn-default"
                                            data-target="#dlgEditModel"
                                            onclick="editProRec('${recommend.id}','${recommend.sid}','${recommend.product.id}','${recommend.picture}')">
                                        编辑
                                    </button>
                                    <button type="button"
                                            class="btn btn-default"
                                            onclick="putOffProRec(${recommend.id})">
                                        删除
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
                                       onclick="proPageChange(${currentPage-1})"><span
                                        aria-hidden="true">«</span></a></li>
                            </c:if>
                            <c:if test="${pages>1}">
                                <c:if test="${pages<=5}">
                                    <c:forEach begin="1" end="${pages}"
                                               varStatus="index">
                                        <c:if test="${index.count==currentPage}">
                                            <li><a href="/manage/productRec"
                                                   class="focusClass">${currentPage}</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${index.count!=currentPage}">
                                            <li>
                                                <a onclick="proPageChange(${index.count})">${index.count}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${pages>5}">
                                    <c:if test="${currentPage<=3}">
                                        <c:forEach begin="1" end="5"
                                                   varStatus="index">
                                            <c:if test="${index.count==currentPage}">
                                                <li>
                                                    <a class="focusClass">${currentPage}</a>
                                                </li>
                                            </c:if>
                                            <c:if test="${index.count!=currentPage}">
                                                <li>
                                                    <a onclick="proPageChange(${index.count})">${index.count}</a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${currentPage>3}">
                                        <c:if test="${pages-currentPage>=2}">
                                            <li>
                                                <a onclick="proPageChange(${currentPage-2})">${currentPage-2}</a>
                                            </li>
                                            <li>
                                                <a onclick="proPageChange(${currentPage-1})">${currentPage-1}</a>
                                            </li>
                                            <li><a
                                                    class="focusClass">${currentPage}</a>
                                            </li>
                                            <li>
                                                <a onclick="proPageChange(${currentPage+1})">${currentPage+1}</a>
                                            </li>
                                            <li>
                                                <a onclick="proPageChange(${currentPage+2})">${currentPage+2}</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${pages-currentPage<2}">
                                            <c:forEach begin="${pages-5}"
                                                       end="${pages}"
                                                       varStatus="index">
                                                <c:if test="${index.current==currentPage}">
                                                    <li><a
                                                            class="focusClass">${currentPage}</a>
                                                    </li>
                                                </c:if>
                                                <c:if test="${index.current!=currentPage}">
                                                    <li>
                                                        <a onclick="proPageChange(${index.current})">${index.current}</a>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:if>
                                </c:if>
                            </c:if>
                            <c:if test="${pages>currentPage}">
                                <li><a onclick="proPageChange(${currentPage+1})"
                                       aria-label="Next"><span
                                        aria-hidden="true">»</span></a></li>
                            </c:if>
                        </ul>
                    </nav>
                </c:if>
                <c:if test="${state==1}">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="text-center">推荐序号</th>
                            <th class="text-center">店铺列表图</th>
                            <th class="text-center">店铺ID</th>
                            <th class="text-center">店铺名称</th>
                            <th class="text-center">店铺地址</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${recommends}" var="recommend">
                            <tr class="active">
                                <td class="text-center">${recommend.sid}</td>
                                <td class="text-center">
                                    <img src="${recommend.merchant.picture}"/>
                                </td>
                                <td class="text-center">
                                        ${recommend.merchant.id}
                                </td>
                                <td class="text-center">
                                        ${recommend.merchant.name}
                                </td>
                                <td class="text-center">
                                        ${recommend.merchant.location}
                                </td>

                                <td class="text-center">
                                    <button type="button"
                                            class="btn btn-default"
                                            onclick="editMerRec('${recommend.id}','${recommend.sid}','${recommend.merchant.id}')">
                                        编辑
                                    </button>
                                    <button type="button"
                                            class="btn btn-default"
                                            onclick="putOffMerRec(${recommend.id})">
                                        删除
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
                                       onclick="merPageChange(${currentPage-1})"><span
                                        aria-hidden="true">«</span></a></li>
                            </c:if>
                            <c:if test="${pages>1}">
                                <c:if test="${pages<=5}">
                                    <c:forEach begin="1" end="${pages}"
                                               varStatus="index">
                                        <c:if test="${index.count==currentPage}">
                                            <li><a href="/manage/merchantRec"
                                                   class="focusClass">${currentPage}</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${index.count!=currentPage}">
                                            <li>
                                                <a onclick="merPageChange(${index.count})">${index.count}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${pages>5}">
                                    <c:if test="${currentPage<=3}">
                                        <c:forEach begin="1" end="5"
                                                   varStatus="index">
                                            <c:if test="${index.count==currentPage}">
                                                <li>
                                                    <a class="focusClass">${currentPage}</a>
                                                </li>
                                            </c:if>
                                            <c:if test="${index.count!=currentPage}">
                                                <li>
                                                    <a onclick="merPageChange(${index.count})">${index.count}</a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${currentPage>3}">
                                        <c:if test="${pages-currentPage>=2}">
                                            <li>
                                                <a onclick="merPageChange(${currentPage-2})">${currentPage-2}</a>
                                            </li>
                                            <li>
                                                <a onclick="merPageChange(${currentPage-1})">${currentPage-1}</a>
                                            </li>
                                            <li><a
                                                    class="focusClass">${currentPage}</a>
                                            </li>
                                            <li>
                                                <a onclick="merPageChange(${currentPage+1})">${currentPage+1}</a>
                                            </li>
                                            <li>
                                                <a onclick="merPageChange(${currentPage+2})">${currentPage+2}</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${pages-currentPage<2}">
                                            <c:forEach begin="${pages-5}"
                                                       end="${pages}"
                                                       varStatus="index">
                                                <c:if test="${index.current==currentPage}">
                                                    <li><a
                                                            class="focusClass">${currentPage}</a>
                                                    </li>
                                                </c:if>
                                                <c:if test="${index.current!=currentPage}">
                                                    <li>
                                                        <a onclick="merPageChange(${index.current})">${index.current}</a>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:if>
                                </c:if>
                            </c:if>
                            <c:if test="${pages>currentPage}">
                                <li><a onclick="merPageChange(${currentPage+1})"
                                       aria-label="Next"><span
                                        aria-hidden="true">»</span></a></li>
                            </c:if>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
</div>

<div class="modal" id="editProRecModel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="form-horizontal">
                <div class="modal-header">
                    <button class="close" data-dismiss="modal"></button>
                    <h4 class="modal-title">推荐商品编辑</h4>
                </div>

                <div class="modal-body">
                    <form class="form-horizontal">
                        <input name="proRecId" type="hidden" value=""/>

                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-4 control-label">推荐序号：</label>

                                <div class="col-md-6">
                                    <input name="proRecSid" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="proRecPic"
                                       class="col-md-4 control-label">推荐海报：</label>

                                <div class="col-md-6">
                                    <img src="" alt="..." id="proPicture">
                                    <input type="file" class="form-control" id="proRecPic"
                                           name="file"
                                           data-url="/manage/file/saveImage"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label">商品ID：</label>

                                <div class="col-md-6">
                                    <input name="productId" class="form-control" value=""/>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" id="proRecCommit" class="btn blue"
                            onclick="proRecCommit()">提交
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal" id="editMerRecModel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="form-horizontal">
                <div class="modal-header">
                    <button class="close" data-dismiss="modal"></button>
                    <h4 class="modal-title">推荐商户编辑</h4>
                </div>

                <div class="modal-body">
                    <form class="form-horizontal">
                        <input name="merRecId" type="hidden" value=""/>

                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-4 control-label">推荐序号：</label>

                                <div class="col-md-6">
                                    <input name="merRecSid" class="form-control"
                                           value="0"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label">商户ID：</label>

                                <div class="col-md-6">
                                    <input name="merchantId" class="form-control" value=""/>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" id="merRecCommit" class="btn blue" onclick="merRecCommit()">提交
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>


<!--如果只是做布局的话不需要下面两个引用-->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
<script>

    $(function () {
        $('#proRecPic').fileupload({
                                       dataType: 'json',
                                       maxFileSize: 5000000,
                                       acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                       add: function (e, data) {
                                           data.submit();
                                       },
                                       done: function (e, data) {
                                           var resp = data.result;
                                           $('#proPicture').attr('src',
                                                                 '${ossImageReadRoot}/'
                                                                 + resp.data);
                                       }
                                   });

    });

    function proPageChange(page) {
        location.href = "/manage/productRec?state=0&page=" + page;
    }
    function merPageChange(page) {
        location.href = "/manage/merchantRec?state=1&page=" + page;
    }

    function stateChange(state) {
        if (state == 1) {
            location.href = "/manage/merchantRec?state=" + state + "&page=1";
        } else {
            location.href = "/manage/productRec?state=" + state + "&page=1";
        }
    }

    function editProRec(id, sid, productId, picture) {
        resetPro();
        if (id != null && id != '') {
            $('input[name=proRecId]').val(id);
            $('input[name=proRecSid]').val(sid);
            $('input[name=proRecPic]').val(picture);
            $('#proPicture').attr("src", picture);
            $('input[name=productId]').val(productId);
        }
        $('#editProRecModel').modal("show");
    }

    function proRecCommit() {
        var productRecommend = {};
        productRecommend.id = $("input[name=proRecId]").val();
        productRecommend.picture = $("#proPicture").attr("src");
        productRecommend.sid = $("input[name=proRecSid]").val();
        var product = {};
        product.id = $("input[name=productId]").val();
        productRecommend.product = product;

        if (productRecommend.sid == null || productRecommend.sid == '') {
            alert("请输入序号");
            return false;
        }
        if (productRecommend.sid <= 0) {
            alert("序号必须大于0");
            return false;
        }
        if (productRecommend.picture == null || productRecommend.picture == '') {
            alert("请选择图片");
            return false;
        }
        if (product.id == null || product.id == '') {
            alert("请输入商品ID");
            return false;
        }
        $.ajax({
                   type: "post",
                   url: "/manage/productRec",
                   contentType: "application/json",
                   data: JSON.stringify(productRecommend),
                   success: function (data) {
                       if (data.status != 200) {
                           alert(data.msg);
                       } else {
                           alert(data.msg);
                           setTimeout(function () {
                               location.href = "/manage/productRec?state=0";
                           }, 0);
                       }

                   }
               });
    }

    function putOffProRec(id) { //下架推荐商品
        if (!confirm("确定下架该推荐商品？")) {
            return false;
        }
        $.post("/manage/productRec/putOff/" + id, null, function (data) {
            if (data.status != 200) {
                alert(data.msg);
            } else {
                alert(data.msg);
                setTimeout(function () {
                    location.href = "/manage/productRec?state=0";
                }, 0);
            }
        });
    }

    function resetPro() {
        $('input[name=proRecId]').val('');
        $('input[name=proRecSid]').val('');
        $('input[name=proRecPic]').val('');
        $('#proPicture').attr("src", '');
        $('input[name=productId]').val('');
    }

    function editMerRec(id, sid, merchantId) {
        resetMer();
        if (id != null && id != '') {
            $('input[name=merRecId]').val(id);
            $('input[name=merRecSid]').val(sid);
            $('input[name=merchantId]').val(merchantId);
        }
        $('#editMerRecModel').modal("show");
    }

    function merRecCommit() {
        var merchantRecommend = {};
        merchantRecommend.id = $("input[name=merRecId]").val();
        merchantRecommend.sid = $("input[name=merRecSid]").val();
        var merchant = {};
        merchant.id = $("input[name=merchantId]").val();
        merchantRecommend.merchant = merchant;

        if (merchantRecommend.sid == null || merchantRecommend.sid == '') {
            alert("请输入序号");
            return false;
        }
        if (merchantRecommend.sid <= 0) {
            alert("序号必须大于0");
            return false;
        }
        if (merchant.id == null || merchant.id == '') {
            alert("请输入商家ID");
            return false;
        }
        $.ajax({
                   type: "post",
                   url: "/manage/merchantRec",
                   contentType: "application/json",
                   data: JSON.stringify(merchantRecommend),
                   success: function (data) {
                       if (data.status != 200) {
                           alert(data.msg);
                       } else {
                           alert(data.msg);
                           setTimeout(function () {
                               location.href = "/manage/merchantRec?state=1";
                           }, 0);
                       }

                   }
               });
    }

    function putOffMerRec(id) { //下架推荐商家
        if (!confirm("确定下架该推荐商家？")) {
            return false;
        }
        $.post("/manage/merchantRec/putOff/" + id, null, function (data) {
            if (data.status != 200) {
                alert(data.msg);
            } else {
                alert(data.msg);
                setTimeout(function () {
                    location.href = "/manage/merchantRec?state=1";
                }, 0);
            }
        });
    }

    function resetMer() {
        $('input[name=merRecId]').val('');
        $('input[name=merRecSid]').val('');
        $('input[name=merchantId]').val('');
    }

</script>
</body>
</html>

