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
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <style>
        thead th, tbody td {
            text-align: center;
        }

        #myTab {
            margin-bottom: 10px;
        }

        .del {
            background-color: red;
            color: #FFFFFF;
            margin: 0 5px;
            padding: 2px 8px;
        }

        .btn:hover {
            color: #FFFFFF !important;
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

                <button type="button" class="btn btn-primary" style="margin:10px;"
                        onclick="goMerchantPage()">
                    返回商家列表
                </button>
                <hr>

                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab" onclick="searchByType(1)">轮播图</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab" onclick="searchByType(2)">详情图</a>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <button type="button" class="btn btn-primary " style="margin:10px;"
                                id="add">新增轮播图
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th class="text-center">图片序号</th>
                                <th class="text-center">图片</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody id="bannerContent">
                            </tbody>
                        </table>
                    </div>
                    <div class="tcdPageCode" style="display: inline;">
                    </div>
                    <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<!--添加或修改轮播图-->
<div class="modal" id="type1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <input name="id1" type="hidden" class="o-input" value=""/>

                    <div class="form-group">
                        <label for="sid1" class="col-sm-3 control-label">图片序号</label>

                        <div class="col-sm-6">
                            <input name="pictureSid" type="text" class="form-control o-input"
                                   id="sid1"
                                   value="1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="scrollPicture1"
                               class="col-sm-3 control-label">轮播图片(宽高比:750*350)</label>

                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="picture1">
                            <!--</div>-->
                            <input type="file" class="form-control" id="scrollPicture1" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit1">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--添加或修改详情图-->
<div class="modal" id="type2">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <input name="id2" type="hidden" class="o-input" value=""/>

                    <div class="form-group">
                        <label for="sid2" class="col-sm-3 control-label">图片序号</label>

                        <div class="col-sm-6">
                            <input name="pictureSid" type="text" class="form-control o-input"
                                   id="sid2"
                                   value="1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="detailPicture2"
                               class="col-sm-3 control-label">详情图片(宽高比:750*600)</label>

                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="picture2">
                            <!--</div>-->
                            <input type="file" class="form-control" id="detailPicture2" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit2">确认
                </button>
            </div>
        </div>
    </div>
</div>


<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>

<script>
    var flag = true, init1 = 0, picType = 1, currentPage = 1, merchantId = "${merchantId}";
    var bannerContent = document.getElementById("bannerContent");

    $(function () {
        // tab切换
        var tableType = "${type}";
        if (tableType == 1) {
            $('#myTab li:eq(0) a').tab('show');
        } else if (tableType == 2) {
            $('#myTab li:eq(1) a').tab('show');
        }

        $('input[name=pictureSid]').TouchSpin({
                                                  min: 1,
                                                  max: 100,
                                                  step: 1,
                                                  decimals: 0, //精度
                                                  maxboostedstep: 100,
                                                  prefix: ''
                                              });
        $('#scrollPicture1').fileupload({
                                            dataType: 'json',
                                            maxFileSize: 5000000,
                                            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                            add: function (e, data) {
                                                data.submit();
                                            },
                                            done: function (e, data) {
                                                var resp = data.result;
                                                $('#picture1').attr('src',
                                                                    '${ossImageReadRoot}/'
                                                                    + resp.data);
                                            }
                                        });
        $('#detailPicture2').fileupload({
                                            dataType: 'json',
                                            maxFileSize: 5000000,
                                            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                            add: function (e, data) {
                                                data.submit();
                                            },
                                            done: function (e, data) {
                                                var resp = data.result;
                                                $('#picture2').attr('src',
                                                                    '${ossImageReadRoot}/'
                                                                    + resp.data);
                                            }
                                        });
        getListByType(1, tableType);
    });

</script>
<script>

    function resetAndShow(num) {
        var iid = "type" + num;
        var sid = "sid" + num;
        $("#" + iid + " .w-input").val("");
        $("#" + iid + " .o-input").val("");
        $("#" + sid + "").val(1);
        $("#" + iid + " img").attr("src", "");
        $("#" + iid + "").modal("show");
    }

    $('#bannerSubmit1').on('click', function () {
        var merchantScroll = {}, merchant = {};
        merchant.id = merchantId;
        var picture = $("#picture1").attr("src");

        if (picture == null || picture == "") {
            alert("请上传图片");
            return false;
        }

        merchantScroll.id = $('input[name=id1]').val();
        merchantScroll.sid = $('#sid1').val();
        merchantScroll.picture = picture;
        merchantScroll.merchant = merchant;

        $.ajax({
                   type: "post",
                   url: "/manage/merchant/scrollPicture",
                   contentType: "application/json",
                   data: JSON.stringify(merchantScroll),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           location.href = "/manage/merchant/editContent?id=${merchantId}&type=1";
                       } else {
                           alert(data.status);
                           $("#type1").modal("show");
                       }
                   }
               });
    });
    $('#bannerSubmit2').on('click', function () {
        var merchantDetail = {}, merchant = {};
        merchant.id = merchantId;
        var picture = $("#picture2").attr("src");

        if (picture == null || picture == "") {
            alert("请上传图片");
            return false;
        }

        merchantDetail.id = $('input[name=id2]').val();
        merchantDetail.sid = $('#sid2').val();
        merchantDetail.picture = picture;
        merchantDetail.merchant = merchant;

        $.ajax({
                   type: "post",
                   url: "/manage/merchant/detailPicture",
                   contentType: "application/json",
                   data: JSON.stringify(merchantDetail),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           location.href = "/manage/merchant/editContent?id=${merchantId}&type=2";
                       } else {
                           alert(data.status);
                           $("#type2").modal("show");
                       }
                   }
               });
    });

    function searchByType(type) {
        flag = true;
        picType = type;
        getListByType(1, type);
    }

    function editAddButton(type) {
        if (type == 1) {
            $("#add").html("新建轮播图");
            setTimeout(function () {
                $('#add').unbind('click').bind('click', function () {
                    resetAndShow(type);
                });
            }, 1);
        } else if (type == 2) {
            $("#add").html("新建详情图");
            setTimeout(function () {
                $('#add').unbind('click').bind('click', function () {
                    resetAndShow(type);
                });
            }, 1);
        }
    }

    function getListByType(offset, type) {
        bannerContent.innerHTML = "";
        editAddButton(type);
        if (type == 1) {  //商家轮播图
            $.ajax({
                       type: "get",
                       url: "/manage/merchant/scrollList?offset=" + offset + "&merchantId="
                            + merchantId,
                       async: false,
                       contentType: "application/json",
                       success: function (data) {
                           var page = data.data;
                           var content = page.content;
                           var totalPage = page.totalPages;
                           $("#totalElements").html(page.totalElements);
                           if (totalPage == 0) {
                               totalPage = 1;
                           }
                           if (flag) {
                               flag = false;
                               initPage(offset, totalPage);
                           }
                           if (init1) {
                               initPage(1, totalPage);
                           }

                           for (i = 0; i < content.length; i++) {
                               var contentStr = '<tr><td>' + content[i].sid + '</td>';
                               contentStr +=
                               '<td><img style="height: 100px" src="' + content[i].picture
                               + '" alt="..."></td>';
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="'
                               + content[i].id
                               + '"><button class="btn btn-primary delScroll">删除</button>'
                               +
                               '<button class="btn btn-primary editScroll">编辑</button></td></tr>';
                               bannerContent.innerHTML += contentStr;
                           }
                           $(".delScroll").each(function (i) {
                               $(".delScroll").eq(i).bind("click", function () {
                                   var id = $(this).parent().find(".id-hidden").val();

                                   if (!confirm("确定删除该轮播图？")) {
                                       return false;
                                   }
                                   $.ajax({
                                              type: "delete",
                                              url: "/manage/merchant/delScroll/" + id,
                                              contentType: "application/json",
                                              success: function (data) {
                                                  getListByType(1, 1);
                                              }
                                          });
                               });
                           });
                           $(".editScroll").each(function (i) {
                               $(".editScroll").eq(i).bind("click", function () {
                                   var id = $(this).parent().find(".id-hidden").val();
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/merchant/findScroll/" + id,
                                              contentType: "application/json",
                                              success: function (data) {
                                                  if (data.status == 200) {
                                                      var scroll = data.data;
                                                      $('#sid1').val(scroll.sid);
                                                      $('input[name=id1]').val(scroll.id);
                                                      $("#picture1").attr("src", scroll.picture);
                                                      $("#type1").modal("show");
                                                  } else {
                                                      alert("服务器异常");
                                                  }
                                              }
                                          });
                               });
                           });
                       }
                   });
        } else if (type == 2) { //2=详情图
            $.ajax({
                       type: "get",
                       url: "/manage/merchant/detailList?offset=" + offset + "&merchantId="
                            + merchantId,
                       async: false,
                       contentType: "application/json",
                       success: function (data) {
                           var page = data.data;
                           var content = page.content;
                           var totalPage = page.totalPages;
                           $("#totalElements").html(page.totalElements);
                           if (totalPage == 0) {
                               totalPage = 1;
                           }
                           if (flag) {
                               flag = false;
                               initPage(offset, totalPage);
                           }
                           if (init1) {
                               initPage(1, totalPage);
                           }

                           for (i = 0; i < content.length; i++) {
                               var contentStr = '<tr><td>' + content[i].sid + '</td>';
                               contentStr +=
                               '<td><img style="height: 100px" src="' + content[i].picture
                               + '" alt="..."></td>';
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="'
                               + content[i].id
                               + '"><button class="btn btn-primary delDetail">删除</button>'
                               +
                               '<button class="btn btn-primary editDetail">编辑</button></td></tr>';
                               bannerContent.innerHTML += contentStr;
                           }
                           $(".delDetail").each(function (i) {
                               $(".delDetail").eq(i).bind("click", function () {
                                   var id = $(this).parent().find(".id-hidden").val();

                                   if (!confirm("确定删除该详情图？")) {
                                       return false;
                                   }
                                   $.ajax({
                                              type: "delete",
                                              url: "/manage/merchant/delDetail/" + id,
                                              contentType: "application/json",
                                              success: function (data) {
                                                  getListByType(1, 2);
                                              }
                                          });
                               });
                           });
                           $(".editDetail").each(function (i) {
                               $(".editDetail").eq(i).bind("click", function () {
                                   var id = $(this).parent().find(".id-hidden").val();
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/merchant/findDetail/" + id,
                                              contentType: "application/json",
                                              success: function (data) {
                                                  if (data.status == 200) {
                                                      var detail = data.data;
                                                      $('#sid2').val(detail.sid);
                                                      $('input[name=id2]').val(detail.id);
                                                      $("#picture2").attr("src", detail.picture);
                                                      $("#type2").modal("show");
                                                  } else {
                                                      alert("服务器异常");
                                                  }
                                              }
                                          });
                               });
                           });
                       }
                   });

        }
    }

    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             currentPage = p;
                                             init1 = 0;
                                             getListByType(currentPage, picType);
                                         }
                                     });
    }

    function goMerchantPage() {

        location.href = "/manage/merchant";
    }

</script>
</body>
</html>

