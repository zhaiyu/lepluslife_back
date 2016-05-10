<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/6
  Time: 下午3:46
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
    <script type="text/javascript" src="${resourceUrl}/js/menu.js"></script>
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
                <button type="button" class="btn btn-primary btn-return" style="margin:10px;"
                        onclick="goMerchantPage()">
                    返回商户列表
                </button>
                <hr>
                <div class="form-horizontal">
                    <div class="form-group">
                        <label for="locationCity" class="col-sm-2 control-label">所属城市</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="locationCity">

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="locationArea" class="col-sm-2 control-label">所属区域</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="locationArea">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="merchantType" class="col-sm-2 control-label">所属类型</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="merchantType" name="merchantType">
                                <option value="0">请选择商户类型</option>
                                <c:forEach items="${merchantTypes}" var="merchantType">
                                    <option value="${merchantType.id}">${merchantType.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="merchantName" class="col-sm-2 control-label">商户名称</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="merchantName"
                                   placeholder="请输入商户名称" value="${merchant.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="cooperate-style" class="col-sm-2 control-label">合作方式</label>

                        <div class="col-sm-3" id="cooperate-style">
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" class="radio-change" name="optionsRadios"
                                           id="optionsRadios1" value="0" checked>
                                    普通商户
                                </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" class="radio-change" name="optionsRadios"
                                           id="optionsRadios2" value="1">
                                    加盟商户
                                </label>
                            </div>

                        </div>
                        <div class="col-sm-4">
                            <div class="inline changeDisplay" style="display: none">
                                <label for="yongjin">佣金点<input type="text" id="yongjin"
                                                               value="${merchant.ljCommission}"
                                                               style="margin-left: 10px">%</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="merchantPic" class="col-sm-2 control-label">商户图片</label>

                        <div class="col-sm-4">
                            <div class="thumbnail">
                                <img src="${merchant.picture}" alt="..." id="merchantPicture">
                            </div>
                            <input type="file" class="form-control" id="merchantPic" name="file"
                                   data-url="/manage/file/saveImage">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="locationDetail" class="col-sm-2 control-label">商户地址</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="locationDetail"
                                   value="${merchant.location}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="merchantPhone" class="col-sm-2 control-label">商户电话</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="merchantPhone"
                                   value="${merchant.phoneNumber}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">经纬度</label>

                        <div class="col-sm-4 form-inline">
                            <input type="text" class="form-control input-inline" placeholder="经度"
                                   id="lng" value="${merchant.lng}">
                            <input type="text" class="form-control input-inline" placeholder="纬度"
                                   id="lat" value="${merchant.lat}">
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                    <%--<label for="rebate" class="col-sm-2 control-label">返利点</label>--%>

                    <%--<div class="col-sm-4 form-inline">--%>
                    <%--<input type="text" class="form-control input-inline" id="rebate"--%>
                    <%--value="${merchant.rebate}">%--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label for="discount" class="col-sm-2 control-label">折扣</label>

                        <div class="col-sm-4 form-inline">
                            <input type="text" class="form-control" id="discount"
                                   value="${merchant.discount}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bindTel" class="col-sm-2 control-label">绑定手机</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="bindTel"
                                   placeholder="请输入手机号" value="${merchant.merchantPhone}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="merchantBankNumber" class="col-sm-2 control-label">银行卡号</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="merchantBankNumber"
                                   placeholder="请输入银行卡号"
                                   value="${merchant.merchantBank.bankNumber}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="merchantBankName" class="col-sm-2 control-label">所在支行</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="merchantBankName"
                                   placeholder="请输入所在支行地址"
                                   value="${merchant.merchantBank.bankName}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="payee" class="col-sm-2 control-label">收款人</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="payee"
                                   placeholder="请输入收款人姓名" value="${merchant.payee}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-4">
                            <input type="button" class="btn btn-primary" value="提交"
                                   onclick="submit()"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="merchantId" value="${merchant.id}"/>
<input type="hidden" id="merchantBankId" value="${merchant.merchantBank.id}"/>

<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    $(function () {

        if (${merchant.partnership==1}) {
            $("#optionsRadios2").prop("checked", true);
            $(".changeDisplay").css("display", 'block');
        }

        $("input[name='optionsRadios']").change(function () {
            var value = $("input[name='optionsRadios']:checked").val();
            // alert($selectedvalue);
            if (value == 1) {
                $(".changeDisplay").css("display", 'block');
            } else {
                $(".changeDisplay").css("display", 'none');
            }
        });

        $('#merchantPic').fileupload({
                                         dataType: 'json',
                                         maxFileSize: 5000000,
                                         acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                         add: function (e, data) {
                                             data.submit();
                                         },
                                         done: function (e, data) {
                                             var resp = data.result;
                                             $('#merchantPicture').attr('src',
                                                                        '${ossImageReadRoot}/'
                                                                        + resp.data);
                                         }
                                     });

//        数组

//        调数据
        $.ajax({
                   type: 'GET',
                   url: '/manage/city/ajax',
                   async: false,
                   dataType: 'json',
                   success: function (data) {
                       console.log(data[0]);
                       var dataStr1 = '',
                               dataStr2 = '';
                       $.each(data, function (i) {
                           dataStr1 +=
                           '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                       });
                       $.each(data[0].areas, function (j) {
                           dataStr2 +=
                           '<option value="' + data[0].areas[j].id + '">' + data[0].areas[j].name
                           + '</option>';
                       });
                       $('#locationCity').empty().append(dataStr1);
                       $('#locationArea').empty().append(dataStr2);
                   },
                   error: function (jqXHR) {
                       alert('发生错误：' + jqXHR.status);
                   }
               });
        $('#locationCity').change(function () {
            var val = $(this).val();
            $.ajax({
                       type: 'GET',
                       url: '/manage/city/ajax',
                       async: false,
                       dataType: 'json',
                       success: function (data) {
                           $.each(data, function (i) {
                               if (data[i].id == val) {
                                   var dataStr = '';
                                   $.each(data[i].areas, function (j) {
                                       dataStr +=
                                       '<option value="' + data[i].areas[j].id + '">'
                                       + data[i].areas[j].name + '</option>';
                                   });
                                   $('#locationArea').empty().append(dataStr);
                               }
                           });
                       },
                       error: function (jqXHR) {
                           alert('发生错误：' + jqXHR.status);
                       }
                   });
        })
        if (${merchant!=null}) {
            $("#merchantType option[value=${merchant.merchantType.id}]").attr("selected", true);
            $("#locationCity option[value=${merchant.city.id}]").attr("selected", true);
            $("#locationArea option[value=${merchant.area.id}]").attr("selected", true);
        }
    })

    function submit() {
        var merchant = {};
        var value = $("input[name='optionsRadios']:checked").val();
        // alert($selectedvalue);
        var merchantBank = {};
        merchantBank.bankNumber = $("#merchantBankNumber").val();
        merchantBank.bankName = $("#merchantBankName").val();
        merchantBank.id = $("#merchantBankId").val();
        merchant.merchantBank = merchantBank;
        if (value == 1) {
            merchant.partnership = 1;
            merchant.ljCommission = $("#yongjin").val();
        } else {
            merchant.partnership = 0;
            merchant.ljCommission = 0;
        }
        merchant.merchantPhone = $("#bindTel").val();
        merchant.id = $("#merchantId").val();
        var city = {};
        city.id = $("#locationCity").val();
        merchant.city = city;
        var area = {};
        area.id = $("#locationArea").val();
        merchant.payee = $("#payee").val();
        merchant.area = area;
        var merchantType = {};
        if ($("#merchantType").val() == null || $("#merchantType").val() == 0) {
            alert("请输入商户类型")
            return;
        }
        merchantType.id = $("#merchantType").val();
        merchant.location = $("#locationDetail").val()
        merchant.merchantType = merchantType;
        merchant.name = $("#merchantName").val();
        merchant.lng = $("#lng").val();
        merchant.lat = $("#lat").val();
        merchant.discount = $("#discount").val();
        merchant.picture = $("#merchantPicture").attr("src");
        merchant.phoneNumber = $("#merchantPhone").val()
        if (merchant.id == null || merchant.id == "") {
            $.ajax({
                       type: "post",
                       url: "/manage/merchant",
                       contentType: "application/json",
                       data: JSON.stringify(merchant),
                       success: function (data) {
                           alert(data.data);
                           setTimeout(function () {
                               location.href = "/manage/merchant";
                           }, 0);
                       }
                   });
        } else {
            $.ajax({
                       type: "put",
                       url: "/manage/merchant",
                       contentType: "application/json",
                       data: JSON.stringify(merchant),
                       success: function (data) {
                           alert(data.data);
                           setTimeout(function () {
                               location.href = "/manage/merchant";
                           }, 0);
                       }
                   });
        }
    }

    //        控制字数
    $("#merchantName").bind("input propertychange", function () {
        if ($(this).val().length >= 12) {
            alert("已经是最大字数了哦！");
            $(this).val($(this).val().slice(0, 13));
        }
    });
    $("#locationDetail").bind("input propertychange", function () {
        if ($(this).val().length >= 35) {
            alert("已经是最大字数了哦！");
            $(this).val($(this).val().slice(0, 35));
        }
    });

    function goMerchantPage() {
        location.href = "/manage/merchant";
    }
</script>
</body>
</html>


