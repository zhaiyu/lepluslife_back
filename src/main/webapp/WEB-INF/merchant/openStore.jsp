<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/10/11
  Time: 下午5:32
  乐店编辑
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">

    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/menu.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/bootstrap.min.js"></script>
    <style>
        .thumbnail {
            width: 160px;
            height: 160px;
        }

        .thumbnail img {
            width: 100%;
            height: 100%;
        }

        .addList input[type='text'] {
            width: 50px;
            margin-left: 5px;
        }

        .addList input[type='button'] {
            margin-left: 5px;
        }

        .addList span {
            cursor: pointer;
        }

        #service, #features {
            padding-top: 5px;
        }

        #service input[type=checkbox], #features input[type=checkbox] {
            margin-left: 15px;
        !important;
        }

        .informationList {
            margin-bottom: 10px
        }

        .informationList * {
            float: left
        }

        .informationList > input {
            width: 80%;
        }

        .informationList > button {
            margin-left: 10px;
        }

        .informationList:after {
            content: '\20';
            display: block;
            clear: both
        }

        .spanColor {
            color: red;
        }
    </style>
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
                        onclick="javascript:history.go(-1);">返回我的店铺
                </button>
                <hr>
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">店铺名称<span
                                class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" value="${merchant.name}"
                                   disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">店铺所在城市<span class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" value="${merchant.city.name}"
                                   disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">所在区域<span
                                class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" value="${merchant.area.name}"
                                   disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">详细地址<span
                                class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" value="${merchant.location}"
                                   disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">店铺类型<span
                                class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control"
                                   value="${merchant.merchantType.name}" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phone" class="col-sm-2 control-label">服务电话<span
                                class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="phone"
                                   value="${merchant.phoneNumber}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lng" class="col-sm-2 control-label">经度<span
                                class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" value="${merchant.lng}"
                                   id="lng">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lat" class="col-sm-2 control-label">纬度<span
                                class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" value="${merchant.lat}"
                                   id="lat">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sid" class="col-sm-2 control-label">展示序号<span class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="sid"
                                   value="${merchant.sid}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="merchantPic" class="col-sm-2 control-label">列表页小图<span
                                class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <div class="thumbnail">
                                <img src="${merchant.picture}" alt="..." id="merchantPicture">
                            </div>
                            <input type="file" class="form-control" id="merchantPic" name="file"
                                   data-url="/manage/file/saveImage">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="merchantPic" class="col-sm-2 control-label">列表页大图(710*360)<span
                                class="spanColor">*</span></label>

                        <div class="col-sm-4">
                            <div class="thumbnail">
                                <img src="${merchant.merchantInfo.doorPicture}" alt="..."
                                     id="doorPicture">
                            </div>
                            <input type="file" class="form-control" id="doorPic" name="file"
                                   data-url="/manage/file/saveImage">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="service" class="col-sm-2 control-label">服务信息</label>

                        <div class="col-sm-4" id="service">
                            <input type="checkbox" id="park" name="checkbox"
                                   <c:if test="${merchant.merchantInfo.park == 1}">checked="true" </c:if>><span>免费停车位</span>
                            <input type="checkbox" id="wifi" name="checkbox"
                                   <c:if test="${merchant.merchantInfo.wifi == 1}">checked="true" </c:if>><span>免费wifi</span>
                            <input type="checkbox" id="card" name="checkbox"
                                   <c:if test="${merchant.merchantInfo.card == 1}">checked="true" </c:if>><span>可刷卡</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="star" class="col-sm-2 control-label">星级</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control"
                                   value="${merchant.merchantInfo.star}" id="star">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="perSale" class="col-sm-2 control-label">客单价</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control"
                                   value="${merchant.merchantInfo.perSale/100}" id="perSale">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="discount" class="col-sm-2 control-label">专享折扣</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control"
                                   style="width: 50%;float: left;"
                                   id="discount" value="${merchant.merchantInfo.discount}">

                            <div style="width: 45%;float: left;margin-top: 7px;margin-left: 5%">折
                            </div>
                            <div style="clear: both;"></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="features" class="col-sm-2 control-label">商铺特色</label>

                        <div class="col-sm-8" id="features">
                            <input type="checkbox" name="ts" id="feature_1"
                                   value="1"><span>乐加联盟</span>
                            <input type="checkbox" name="ts" id="feature_2"
                                   value="2"><span>消费获礼</span>
                            <input type="checkbox" name="ts" id="feature_3"
                                   value="3"><span>优先体验</span>
                            <input type="checkbox" name="ts" id="feature_4"
                                   value="4"><span>双重优惠</span>
                            <input type="checkbox" name="ts" id="feature_5"
                                   value="5"><span>会员积分</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="vipPic" class="col-sm-2 control-label">专享活动图</label>

                        <div class="col-sm-4">
                            <div class="thumbnail">
                                <img src="${merchant.merchantInfo.vipPicture}" alt="..."
                                     id="vipPicture">
                            </div>
                            <input type="file" class="form-control" id="vipPic" name="file"
                                   data-url="/manage/file/saveImage">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="reasons" class="col-sm-2 control-label">推荐理由</label>

                        <div class="col-sm-4" id="reasons">

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="col-sm-2 control-label">服务说明</label>

                        <div class="col-sm-4" id="description">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-4">
                            <button id="saveMerchant" class="btn btn-primary" type="submit">保存
                            </button>
                            <c:if test="${merchant.state==1}">
                                <button id="close" class="btn btn-primary">关闭乐店</button>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
</body>
<script>
    var addNewLine = '<button type="button" class="btn btn-primary addNewLine">添加新行</button>';
    var reason = $('#reasons'), description = $('#description');
    var r = '${merchant.merchantInfo.reason}', d = '${merchant.merchantInfo.description}', features = '${merchant.merchantInfo.feature}'.split('_');
    if (r != null && r != '') {
        var reasons = r.split('+=');
        for (var i = 0; i < reasons.length; i++) {
            reason.html(reason.html()
                        + " <div class='informationList'><input type='text' name='reasonsList' class='form-control' value='"
                        + reasons[i]
                        + "'><button type='button' class='btn btn-danger'onclick='del(this)'>删除</button></div>");
        }
    }
    if (d != null && d != '') {
        descriptions = d.split('+=');
        for (var j = 0; j < descriptions.length; j++) {
            description.html(description.html()
                             + " <div class='informationList'><input type='text' name='descriptionList' class='form-control' value='"
                             + descriptions[j]
                             + "'><button type='button' class='btn btn-danger'onclick='del(this)'>删除</button></div>");
        }
    }
    reason.html(reason.html() + addNewLine);
    description.html(description.html() + addNewLine);
    for (var x = 0; x < features.length; x++) {
        var id = 'feature_' + features[x];
        $("#" + id).attr('checked', 'true');
    }
    $(".addNewLine").click(function (e) {
        var addClass = $(this).parent().attr('id') + 'List';
        $(this).before(" <div class='informationList'><input type='text' class='form-control' name='"
                       + addClass
                       + "'><button type='button' class='btn btn-danger'onclick='del(this)'>删除</button></div>");
    });
    $("#features > input[type=checkbox]").click(function (e) {
        if ($("input[name='ts']:checked").length > 3) {
            $(this).removeAttr("checked");
            alert("最多选3个!")
        }
    });
    function del(t) {
        $(t).closest(".informationList").remove();
    }

</script>
<script>
    $(function () {
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
        $('#doorPic').fileupload({
                                     dataType: 'json',
                                     maxFileSize: 5000000,
                                     acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                     add: function (e, data) {
                                         data.submit();
                                     },
                                     done: function (e, data) {
                                         var resp = data.result;
                                         $('#doorPicture').attr('src',
                                                                '${ossImageReadRoot}/'
                                                                + resp.data);
                                     }
                                 });
        $('#vipPic').fileupload({
                                    dataType: 'json',
                                    maxFileSize: 5000000,
                                    acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                    add: function (e, data) {
                                        data.submit();
                                    },
                                    done: function (e, data) {
                                        var resp = data.result;
                                        $('#vipPicture').attr('src',
                                                              '${ossImageReadRoot}/'
                                                              + resp.data);
                                    }
                                });
    });

    if (${merchant.officeHour!=null}) {
        var time = '${merchant.officeHour}';
        //var time = '09:40-05:30';
        $("#startHour").val(time.split(":")[0]);
        $("#startMinute").val(time.split(":")[1].split("-")[0]);
        $("#endHour").val(time.split(":")[1].split("-")[1]);
        $("#endMinute").val(time.split(":")[2]);
    }
    $("#saveMerchant").bind("click", function () {

        var merchant = {};
        var merchantInfo = {};
        var discount = $("#discount").val();
        if (parseInt(discount, 10) == discount && 1 <= discount && discount <= 100
            && discount != 0) {
            merchantInfo.discount = discount;
        } else {
            alert("请输入1到100的正整数");
            $("#discount").focus();
            return false;
        }
        var feature = '', reasonList = '', descriptionList = '', shutOff = 0;
        $('input[name="ts"]:checked').each(function () {
            feature += $(this).val() + '_';
        });
        merchantInfo.feature = feature.substr(0, feature.length - 1);
        merchantInfo.vipPicture = $("#vipPicture").attr("src");
        merchantInfo.doorPicture = $("#doorPicture").attr("src");
        $('input[name="reasonsList"]').each(function () {
            if ($(this).val() == null || $(this).val() == '') {
                alert("推荐理由不能为空");
                $(this).focus();
                shutOff = 1;
                return false;
            }
            reasonList += $(this).val() + '+=';
        });
        if (shutOff == 1) {
            return
        }
        $('input[name="descriptionList"]').each(function () {
            if ($(this).val() == null || $(this).val() == '') {
                alert("服务说明不能为空");
                $(this).focus();
                shutOff = 1;
                return false;
            }
            descriptionList += $(this).val() + '+=';
        });
        if (shutOff == 1) {
            return
        }
        merchantInfo.reason = reasonList.substr(0, reasonList.length - 2);
        merchantInfo.description = descriptionList.substr(0, descriptionList.length - 2);

        merchantInfo.park = 0;
        merchantInfo.wifi = 0;
        merchantInfo.card = 0;
        $('input[name="checkbox"]:checked').each(function () {
            var id = $(this).attr('id');
            merchantInfo[id] = 1;
        });

        merchantInfo.star = $("#star").val();
        merchantInfo.perSale = $("#perSale").val() * 100;
        merchant.merchantInfo = merchantInfo;
        merchant.lng = $("#lng").val();
        merchant.lat = $("#lat").val();
        merchant.officeHour =
        $("#startHour").val() + ":" + $("#startMinute").val() + "-" + $("#endHour").val() + ":"
        + $("#endMinute").val();
        merchant.id =${merchant.id};
        merchant.sid = $("#sid").val();
        merchant.picture = $("#merchantPicture").attr("src");
        merchant.phoneNumber = $("#phone").val();
        $.ajax({
                   type: "post",
                   url: "/manage/merchant/openStore",
                   contentType: "application/json",
                   data: JSON.stringify(merchant),
                   success: function (data) {
                       alert(data.msg);
                       location.href = "/manage/merchant";
                   }
               });
    });

    $("#close").bind("click", function () {
        $.ajax({
                   type: "get",
                   url: "/manage/merchant/closeStore/${merchant.id}",
                   contentType: "application/json",
                   success: function (data) {
                       alert(data.msg);
                       location.href = "/manage/merchant";
                   }
               });
    });
</script>
</html>
