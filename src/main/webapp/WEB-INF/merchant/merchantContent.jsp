<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/5
  Time: 上午11:49
  To change this template use File | Settings | File Templates.
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
  <%@include file="../common/top.jsp" %>
</div>
<div id="content">
  <div id="leftIframe">
    <%@include file="../common/left.jsp" %>
  </div>
  <div class="m-right">
    <div class="main">
      <div class="container-fluid">
        <button type="button" class="btn btn-primary btn-create" style="margin:10px;"
                onclick="goMerchantPage()">
          返回商家列表
        </button>
        <hr>
        <ul id="myTab" class="nav nav-tabs">
          <%--<li class="active"><a href="#guige" data-toggle="tab">规格管理</a></li>--%>
          <li ><a class="active" href="#lunbotu" data-toggle="tab" >轮播图</a></li>
          <li class="active"><a href="#tab2" data-toggle="tab" onclick="searchByType(2)">臻品轮播图</a>
              </li>
          <li><a href="#xiangqing" data-toggle="tab">详情图片</a></li>

              <li><a href="#tab3" data-toggle="tab" onclick="searchByType(3)">新品首发</a></li>
        </ul>
        <div id="myTabContent" class="tab-content">

          <div class="tab-pane fade" id="lunbotu">
            <button type="button" class="btn btn-primary createWarn"
                    style="margin:10px;" onclick="editScrollPicture()">添加轮播图
            </button>
            <table class="table table-bordered table-hover">
              <thead>
              <tr>
                <th class="text-center">图片序号</th>
                <th class="text-center">图片</th>
                <th class="text-center">操作</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${scrollPictures}" var="scrollPicture">
                <tr class="active">
                  <td class="text-center">${scrollPicture.sid}</td>
                  <td class="text-center"><img src="${scrollPicture.picture}">
                  </td>
                  <td class="text-center">
                    <button type="button" class="btn btn-default createWarn"
                            onclick="editScrollPicture(${scrollPicture.id})">编辑
                    </button>
                    <button type="button" class="btn btn-default deleteWarn"
                            data-target="#deleteWarn"
                            onclick="deleteScrollPicture(${scrollPicture.id})">
                      删除
                    </button>
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
  <%@include file="../common/bottom.jsp" %>
</div>

<!--删除提示框-->
<div class="modal" id="deleteWarn">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span
                aria-hidden="true">×</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body">
        <h4>确定要删除？</h4>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal"
                id="delete-confirm">确认
        </button>
      </div>
    </div>
  </div>
</div>
<!--添加提示框-->
<div class="modal" id="createWarn">
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
            <label for="productNum" class="col-sm-2 control-label">图片序号</label>

            <div class="col-sm-4">
              <input type="number" class="form-control" id="productNum"
                     placeholder="Email">
            </div>
          </div>
          <%--<div class="form-group">--%>
            <%--<label for="productName" class="col-sm-2 control-label">图片名称</label>--%>

            <%--<div class="col-sm-4">--%>
              <%--<input type="text" class="form-control" id="productName"--%>
                     <%--placeholder="请输入图片名称">--%>
            <%--</div>--%>
          <%--</div>--%>
          <div class="form-group">
            <label for="productPic" class="col-sm-2 control-label">商户图片</label>

            <div class="col-sm-4">
              <!--<div class="thumbnail">-->
              <img src="" alt="..." id="image">
              <!--</div>-->
              <input type="file" class="form-control" id="productPic" name="file"
                     data-url="/manage/file/saveImage"/>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal"
                id="scroll-confim">确认
        </button>
      </div>
    </div>
  </div>
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
                                    $('#image').attr('src',
                                                     '${ossImageReadRoot}/'
                                                     + resp.data);
                                  }
                                });

    $('#myTab li:eq(0) a').tab('show');

  });



  function editScrollPicture(id) {
    if (id != null) {
      $.get("/manage/merchant/scrollPicture/" + id, null, function (data) {
        $("#productNum").val(data.sid);
        $("#image").attr("src", data.picture);
        $("#productName").val(data.description);
        $("#scroll-confim").bind("click", function () {
          var scrollPicture = {};
          scrollPicture.sid = $("#productNum").val();
          scrollPicture.picture = $("#image").attr("src");
          scrollPicture.description = $("#productName").val();
          scrollPicture.id = id;
          var merchant = {};
          merchant.id = ${merchant.id};
          scrollPicture.merchant = merchant;
          $.ajax({
                   type: "put",
                   url: "/manage/merchant/scrollPicture",
                   contentType: "application/json",
                   data: JSON.stringify(scrollPicture),
                   success: function (data) {
                     alert(data.msg);
                     setTimeout(function () {
                       location.href =
                       "/manage/merchant/editContent/${merchant.id}";
                     }, 0);
                   }
                 });
        });
      });
      $("#createWarn").modal("show");
    } else {
      $("#scroll-confim").bind("click", function () {
        $("#scroll-confim").unbind("click");
        var scrollPicture = {};
        scrollPicture.sid = $("#productNum").val();
        scrollPicture.picture = $("#image").attr("src");
        scrollPicture.description = $("#productName").val();
        var merchant = {};
        merchant.id = ${merchant.id};
        scrollPicture.merchant = merchant;
        $.ajax({
                 type: "post",
                 url: "/manage/merchant/scrollPicture",
                 contentType: "application/json",
                 data: JSON.stringify(scrollPicture),
                 success: function (data) {
                   alert(data.msg);
                   setTimeout(function () {
                     location.href =
                     "/manage/merchant/editContent/${merchant.id}";
                   }, 0);
                 }
               });
      });
      $("#createWarn").modal("show");
    }
  }

  function deleteScrollPicture(id) {
    $("#delete-confirm").bind("click", function () {
      $.ajax({
               type: "delete",
               url: "/manage/merchant/scrollPicture/" + id,
               contentType: "application/json",
               success: function (data) {
                 alert(data.msg);
                 setTimeout(function () {
                   location.href =
                   "/manage/merchant/editContent/${merchant.id}";
                 }, 0);
               }
             });
    });
    $("#deleteWarn").modal("show");
  }

  function goMerchantPage() {
    location.href = "/manage/merchant"
  }
</script>
</body>
</html>

