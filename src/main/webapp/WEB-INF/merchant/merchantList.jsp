<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/6
  Time: 下午2:45
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
    <link rel="stylesheet" href="${resourceUrl}/css/jqpagination.css"/>
    <style>
        thead th, tbody td {
            text-align: center;
        }

        table tr td img {
            width: 80px;
            height: 80px;
        }

        .table > thead > tr > td, .table > thead > tr > th {
            line-height: 40px;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
            line-height: 60px;
        }

        .modal-body .main{
            width: 345px;
            height: 480px;
            margin: 0 auto;
            background: url("${resourceUrl}/images/lefuma.png") no-repeat;
            background-size: 100% 100%;
            position: relative;
        }
        .modal-body #myShowImage{
            width: 345px;
            height: 480px;
            position: absolute;
            top: 20px;
            left: 0;
            right: 0;
            margin: auto;
            display: none;
        }
        .modal-body .main .rvCode{
            width: 230px;
            height: 230px;
            position: absolute;
            top: 120px;
            left: 0;
            right: 0;
            margin:auto;
        }
        .modal-body .main .shopName{
            text-align: center;
            font-size: 24px;
            color: #fff;
            position: absolute;
            top: 365px;
            left: 0;
            right: 0;
            margin: auto;
            font-family: Arial;
        }
    </style>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.jqpagination.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/html2canvas.js"></script>
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
            <div class="container-fluid" style="padding-top: 20px">
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="partnership">商户分类</label>
                        <select class="form-control" id="partnership">
                            <option value="-1">全部分类</option>
                            <option value="0">普通商户</option>
                            <option value="1">联盟商户</option>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="merchantType">所属类型</label>
                        <select class="form-control" id="merchantType">
                            <option value="0">全部分类</option>
                            <c:forEach items="${merchantTypes}" var="merchantType">
                                <option value="${merchantType.id}">${merchantType.name}</option>
                            </c:forEach>
                        </select></div>
                    <div class="form-group col-md-3">
                        <label for="merchant-name">商户名称</label>
                        <input type="text" id="merchant-name" class="form-control"
                               placeholder="请输入商户名称或ID"/>
                    </div>
                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchMerchantByCriteria()">筛选
                        </button>
                    </div>
                    <div class="form-group col-md-3"></div>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#lunbotu" data-toggle="tab">商户管理</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="lunbotu">
                        <button type="button" class="btn btn-primary createLocation"
                                style="margin:10px 0;" onclick="createMerchant()">新增商户
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>商品序号</th>
                                <th>商品名称</th>
                                <th>商品图片</th>
                                <th>商户地址</th>
                                <th>商户分类</th>
                                <th>佣金点</th>
                                <th>所属类型</th>
                                <th>所属区域</th>
                                <th>待转账金额</th>
                                <th>累计转账金额</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="merchantContent">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="pagination">
                    <a href="#" class="first" data-action="first">&laquo;</a>
                    <a href="#" class="previous" data-action="previous">&laquo;</a>
                    <input id="page" type="text" readonly="readonly" data-max-page="1"/>
                    <a href="#" class="next" data-action="next">&laquo;</a>
                    <a href="#" class="last" data-action="last">&laquo;</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<!--停用提示框-->
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">停用</h4>
            </div>
            <div class="modal-body">
                <h4 id="merchantWarn"></h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="disable-confirm">确认
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
                <div class="main">
                    <img class="rvCode" id="qrCode" src="" alt=""/>
                    <p class="shopName"></p>
                </div>
                <img id="myShowImage" style="margin: 0 auto" src="" />
                <h4 class="text-center text-danger">点击“生成图片”按钮，再右键将图片存到本地</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary savePic">生成图片</button>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    var merchantCriteria = {};
    var merchantContent = document.getElementById("merchantContent");
    var merchantWarn = document.getElementById("merchantWarn");
    $(function () {
//        tab切换
        $('#myTab li:eq(0) a').tab('show');
//        提示框
        $(".deleteWarn").click(function () {
            $("#deleteWarn").modal("toggle");
        });
        $(".rvCodeWorn").click(function () {
            $("#rvCodeWorn").modal("toggle");
        });
        merchantCriteria.offset = 1;

        getMerchantByAjax(merchantCriteria);
    });
    //    $(".savePic").click(function () {
    //        html2canvas($(".modal-body .main"), {
    //            allowTaint: true,
    //            taintTest: false,
    //            onrendered: function (canvas) {
    //                canvas.id = "mycanvas";
    //                var image = canvas.toDataURL("image/png").replace("image/png",
    //                                                                  "image/octet-stream");
    //                var save_link = document.createElement('a');
    //                save_link.href = image;
    //                save_link.download = "1.png";
    //
    //                var event = document.createEvent('MouseEvents');
    //                event.initMouseEvent('click', true, false, null, 0, 0, 0, 0, 0, false, false, false,
    //                                     false, 0, null);
    //                save_link.dispatchEvent(event);
    //
    //            }
    //        });
    //    })
    function getMerchantByAjax(merchantCriteria) {
        merchantContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/merchant/search",
                   async: false,
                   data: JSON.stringify(merchantCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var page = data.data;
                       var content = page.content;
                       var totalPage = page.totalPages;
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].merchantSid + '</td>';
                           contentStr += '<td>' + content[i].name + '</td>'
                           contentStr +=
                           '<td><img src="' + content[i].picture + '"></td>'
                           contentStr +=
                           '<td>' + content[i].location + '</td>';
                           if (content[i].partnership == 0) {
                               contentStr +=
                               '<td>普通商户</td>'
                           } else {
                               contentStr +=
                               '<td>联盟商户</td>'
                           }
                           contentStr +=
                           '<td>' + content[i].ljCommission + '%</td>'
                           contentStr += '<td>' + content[i].merchantType.name + '</td>'
                           contentStr += '<td>' + content[i].area.name + '</td>'
                           contentStr += '<td>￥00</td>'
                           contentStr += '<td>￥00</td>'
                           contentStr +=
                           '<td><input type="hidden" class="name-hidden" value="' + content[i].name
                           + '"><input type="hidden" class="id-hidden" value="' + content[i].id
                           + '"><button type="button" class="btn btn-default createUser">账户</button>';
                           contentStr +=
                           '<button type="button" class="btn btn-default showQRCode">二维码</button>';
                           contentStr +=
                           '<button type="button" class="btn btn-default editMerchant">编辑</button>';
                           contentStr +=
                           '<button type="button" class="btn btn-default disableMerchant">停用</button></td></tr>';
                           merchantContent.innerHTML += contentStr;

                       }
                       $(".createUser").each(function (i) {
                           $(".createUser").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               location.href = "/manage/merchant/user/" + id;
                           });
                       });
                       $(".showQRCode").each(function (i) {
                           $(".showQRCode").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $.ajax({
                                          type: "get",
                                          data: {id: id},
                                          url: "/manage/merchant/qrCode",
                                          success: function (data) {
                                              var merchant = data.data;
                                              var url = "${productUrl}" + id;
                                              $(".shopName").html(merchant.name);
                                              $("#qrCode").attr("src", merchant.qrCodePicture);
                                              $("#qrWarn").modal("show");
                                              document.querySelector(".savePic").addEventListener("click",
                                                                                                  function () {
                                                                                                      html2canvas($(".modal-body .main"), {
                                                                                                          allowTaint: true,
                                                                                                          taintTest: false,
                                                                                                          onrendered: function (canvas) {
                                                                                                              canvas.id = "mycanvas";
                                                                                                              var dataUrl = canvas.toDataURL();
                                                                                                              $("#myShowImage").attr("src", dataUrl).css({'display': 'block'});
                                                                                                              $(".main").css({'display': 'none'});
//                                                                                                              ctx.webkitImageSmoothingEnabled = false;
//                                                                                                              ctx.mozImageSmoothingEnabled = false;
//                                                                                                              ctx.imageSmoothingEnabled = false;
//                                                                                                              ctx.drawImage(canvas, 0, 0, 345, 480);
//                                                                                                              var image = canvas.toDataURL();
////                                                                                                              $("#myShowImage").attr("src", dataUrl).css({'display': 'block'});
////                                                                                                              $(".main").css({'display': 'none'});
//                                                                                                              var save_link = document.createElement('a');
//                                                                                                              save_link.href = image;
//                                                                                                              save_link.download = "1.png";
//
//                                                                                                              var event = document.createEvent('MouseEvents');
//                                                                                                              event.initMouseEvent('click', true, false, null, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
//                                                                                                              save_link.dispatchEvent(event);
                                                                                                          }
                                                                                                      });
                                                                                                  },
                                                                                                  false);
                                          }
                                      });
                           });
                       });
                       $(".editMerchant").each(function (i) {
                           $(".editMerchant").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               location.href = "/manage/merchant/edit/" + id;
                           });
                       });
                       $(".disableMerchant").each(function (i) {
                           $(".disableMerchant").eq(i).bind("click", function () {
                               merchantWarn.innerHTML = ""
                               var id = $(this).parent().find(".id-hidden").val();
                               var name = $(this).parent().find(".name-hidden").val();
                               merchantWarn.innerHTML = "确认停用商户" + name + "吗";
                               $("#disable-confirm").bind("click", function () {
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/merchant/disable/" + id,
                                              contentType: "application/json",
                                              success: function (data) {
                                                  alert(data.msg);
                                                  getMerchantByAjax(merchantCriteria);
                                              }
                                          });
                               });
                               $("#deleteWarn").modal("show");
                           });
                       });
                       initPage(merchantCriteria.offset, totalPage);
                   }
               });
    }

    function createMerchant() {
        location.href = "/manage/merchant/edit";
    }
    function initPage(page, totalPage) {
        $('.pagination').jqPagination({
                                          current_page: page, //设置当前页 默认为1
                                          max_page: totalPage, //设置最大页 默认为1
                                          page_string: '当前第' + page + '页,共' + totalPage + '页',
                                          paged: function (page) {
                                              merchantCriteria.offset = page;
                                              getMerchantByAjax(merchantCriteria);
                                          }
                                      });
    }
    function searchMerchantByCriteria() {
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            merchantCriteria.merchant = $("#merchant-name").val();
        } else {
            merchantCriteria.merchant = null;
        }
        if ($("#partnership").val() != -1) {
            merchantCriteria.partnership = $("#partnership").val();
        } else {
            merchantCriteria.partnership = null;
        }

        if ($("#merchantType").val() != 0) {
            merchantCriteria.merchantType = $("#merchantType").val();
        } else {
            merchantCriteria.merchantType = null;
        }
        getMerchantByAjax(merchantCriteria);
    }

</script>
</body>
</html>


