<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 2017/7/13
  Time: 14:02
  编辑易宝商户资质管理
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/bootstrap.min.css"/>

    <!--[if lt IE 9]>
    <script src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script src="${resourceUrl}/js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
    <script src="${resourceUrl}/js/bootstrap.min.js"></script>
    <style>
        input {
            width: auto;
        }

        input[type=file] {
            display: none;
        }

        img {
            width: 100px !important;
        }
    </style>
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
        <div class="ModLine ModRadius"></div>
        <div class="merchant_management-table">
            <div class="toggleTable">
                <div id="toggleContent" class="tab-content">
                    <div class="tab-pane fade in active">
                        <h1 style="text-align: center">资质管理--${q.merchantUserLedger.signedName}&nbsp;&nbsp;&nbsp;
                            注册类型：${q.merchantUserLedger.customerType == 1?'个人':'企业'}</h1>
                        <div class="alert alert-info" role="alert">注意： 1、
                            个人类型账户必上传资质：身份证正、反面，手持身份证照片，银行卡正、反面；<br>
                            企业类型账户必上传资质：企业五证（营业执照，法人身份证正、反面，银行开户许可证，组织机构代码证，
                            税务登记证），仅当营业执照为统一社会信用代码时，不需上传组织机构代码证和税务登记证；<br>
                            2、资质上传后,如易宝审查资质不合格(图片不清晰或资质与信息不符等),将会冻结此分账方，为了保证
                            分账方的正常使用，请根据资质要求上传资质。<br>
                            注释：上传图片类型支持 jpg、jpeg、png，文件大小限制 512KB;
                        </div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>资质类型</th>
                                <th>图片</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>身份证正面</td>
                                <td><img class="showImg" id="picType0" src="#"
                                         width="100"
                                         height="100" alt=""></td>
                                <td id="msg0">未上传图片</td>
                                <td>
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           onclick="updataImg(this,0)" value="编辑图片">
                                    <input type="file" class="fileUpload" name="file"
                                           data-url="/manage/file/localAndOss?ledgerNo=${q.merchantUserLedger.ledgerNo}">
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           value="上送易宝" onclick="uploadQualification(0)">
                                </td>
                            </tr>
                            <tr>
                                <td>身份证背面</td>
                                <td><img src="#" id="picType1" class="showImg" width="100"
                                         height="100" alt="">
                                </td>
                                <td id="msg1">未上传图片</td>
                                <td>
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           onclick="updataImg(this,1)" value="编辑图片">
                                    <input type="file" class="fileUpload" name="file"
                                           data-url="/manage/file/localAndOss?ledgerNo=${q.merchantUserLedger.ledgerNo}">
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           value="上送易宝" onclick="uploadQualification(1)">
                                </td>
                            </tr>
                            <tr>
                                <td>银行卡正面</td>
                                <td><img src="#" id="picType2" class="showImg" width="100"
                                         height="100" alt="">
                                </td>
                                <td id="msg2">未上传图片</td>
                                <td>
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           onclick="updataImg(this,2)" value="编辑图片">
                                    <input type="file" class="fileUpload" name="file"
                                           data-url="/manage/file/localAndOss?ledgerNo=${q.merchantUserLedger.ledgerNo}">
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           value="上送易宝" onclick="uploadQualification(2)">
                                </td>
                            </tr>
                            <tr>
                                <td>银行卡背面</td>
                                <td><img src="#" id="picType3" class="showImg" width="100"
                                         height="100" alt="">
                                </td>
                                <td id="msg3">未上传图片</td>
                                <td>
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           onclick="updataImg(this,3)" value="编辑图片">
                                    <input type="file" class="fileUpload" name="file"
                                           data-url="/manage/file/localAndOss?ledgerNo=${q.merchantUserLedger.ledgerNo}">
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           value="上送易宝" onclick="uploadQualification(3)">
                                </td>
                            </tr>
                            <tr>
                                <td>手持身份证照片</td>
                                <td><img src="#" id="picType4" class="showImg" width="100"
                                         height="100" alt="">
                                </td>
                                <td id="msg4">未上传图片</td>
                                <td>
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           onclick="updataImg(this,4)" value="编辑图片">
                                    <input type="file" class="fileUpload" name="file"
                                           data-url="/manage/file/localAndOss?ledgerNo=${q.merchantUserLedger.ledgerNo}">
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           value="上送易宝" onclick="uploadQualification(4)">
                                </td>
                            </tr>
                            <tr>
                                <td>营业执照</td>
                                <td><img src="#" id="picType5" class="showImg" width="100"
                                         height="100" alt="">
                                </td>
                                <td id="msg5">未上传图片</td>
                                <td>
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           onclick="updataImg(this,5)" value="编辑图片">
                                    <input type="file" class="fileUpload" name="file"
                                           data-url="/manage/file/localAndOss?ledgerNo=${q.merchantUserLedger.ledgerNo}">
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           value="上送易宝" onclick="uploadQualification(5)">
                                </td>
                            </tr>
                            <tr>
                                <td>工商证</td>
                                <td><img src="#" id="picType6" class="showImg" width="100"
                                         height="100" alt="">
                                </td>
                                <td id="msg6">未上传图片</td>
                                <td>
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           onclick="updataImg(this,6)" value="编辑图片">
                                    <input type="file" class="fileUpload" name="file"
                                           data-url="/manage/file/localAndOss?ledgerNo=${q.merchantUserLedger.ledgerNo}">
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           value="上送易宝" onclick="uploadQualification(6)">
                                </td>
                            </tr>
                            <tr>
                                <td>组织机构代码证</td>
                                <td><img src="#" id="picType7" class="showImg" width="100"
                                         height="100" alt="">
                                </td>
                                <td id="msg7">未上传图片</td>
                                <td>
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           onclick="updataImg(this,7)" value="编辑图片">
                                    <input type="file" class="fileUpload" name="file"
                                           data-url="/manage/file/localAndOss?ledgerNo=${q.merchantUserLedger.ledgerNo}">
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           value="上送易宝" onclick="uploadQualification(7)">
                                </td>
                            </tr>
                            <tr>
                                <td>税务登记证</td>
                                <td><img src="#" id="picType8" class="showImg" width="100"
                                         height="100" alt="">
                                </td>
                                <td id="msg8">未上传图片</td>
                                <td>
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           onclick="updataImg(this,8)" value="编辑图片">
                                    <input type="file" class="fileUpload" name="file"
                                           data-url="/manage/file/localAndOss?ledgerNo=${q.merchantUserLedger.ledgerNo}">
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           value="上送易宝" onclick="uploadQualification(8)">
                                </td>
                            </tr>
                            <tr>
                                <td>银行开户许可证</td>
                                <td><img src="#" id="picType9" class="showImg" width="100"
                                         height="100" alt="">
                                </td>
                                <td id="msg9">未上传图片</td>
                                <td>
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           onclick="updataImg(this,9)" value="编辑图片">
                                    <input type="file" class="fileUpload" name="file"
                                           data-url="/manage/file/localAndOss?ledgerNo=${q.merchantUserLedger.ledgerNo}">
                                    <input type="button"
                                           class="btn btn-xs btn-primary select-btn createWarn"
                                           value="上送易宝" onclick="uploadQualification(9)">
                                </td>
                            </tr>
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

<div class="modal fade" id="dlgEditModel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="form-horizontal">
                <h2 class="text-center">上送中。。。请稍候</h2>
                <div class="modal-footer">
                    <button type="button" class="btn default" data-dismiss="modal">关闭
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var picType = 0;
    var uploadUrl = '/manage/qualification/uploadQualification?qualificationId=${q.id}';
    function updataImg(e, type) {
        picType = type;
        $(e).next().trigger('click');
    }
    /** 分账方资质上传**/
    function uploadQualification(type) {
        picType = type;
        var currPic = 'picType' + type;
        var strs = $('#' + currPic).attr('src').split('/');
        var picPath = strs[strs.length - 1];
        var url = uploadUrl + '&picPath=' + picPath + '&type=' + type;
        console.log('请求地址=' + url);
        $('#dlgEditModel').modal('show');
        $.ajax({
                   url: url,
                   type: "post",
                   success: function (result) {
                       var msg = 'msg' + picType;
                       $('#dlgEditModel').modal('hide');
                       if (eval(result.code) === 1) {
                           $('#' + msg).html('上送成功');
                       } else {
                           $('#' + msg).html('上送失败.错误码:' + result.code + '(' + result.msg + ')');
                       }
                   }
               });
    }
    $(function () {
        //初始化
        if ('${q.idCardFront}' != '0') {
            $('#picType0').attr('src', '${ossImageReadRoot}/${q.idCardFront}');
            $('#msg0').html('上送成功');
        }
        if ('${q.idCardBack}' != '0') {
            $('#picType1').attr('src', '${ossImageReadRoot}/${q.idCardBack}');
            $('#msg1').html('上送成功');
        }
        if ('${q.bankCardFront}' != '0') {
            $('#picType2').attr('src', '${ossImageReadRoot}/${q.bankCardFront}');
            $('#msg2').html('上送成功');
        }
        if ('${q.bankCardBack}' != '0') {
            $('#picType3').attr('src', '${ossImageReadRoot}/${q.bankCardBack}');
            $('#msg3').html('上送成功');
        }
        if ('${q.personPhoto}' != '0') {
            $('#picType4').attr('src', '${ossImageReadRoot}/${q.personPhoto}');
            $('#msg4').html('上送成功');
        }
        if ('${q.bussinessLicense}' != '0') {
            $('#picType5').attr('src', '${ossImageReadRoot}/${q.bussinessLicense}');
            $('#msg5').html('上送成功');
        }
        if ('${q.bussinessCertificates}' != '0') {
            $('#picType6').attr('src', '${ossImageReadRoot}/${q.bussinessCertificates}');
            $('#msg6').html('上送成功');
        }
        if ('${q.organizationCode}' != '0') {
            $('#picType7').attr('src', '${ossImageReadRoot}/${q.organizationCode}');
            $('#msg7').html('上送成功');
        }
        if ('${q.taxRegistration}' != '0') {
            $('#picType8').attr('src', '${ossImageReadRoot}/${q.taxRegistration}');
            $('#msg8').html('上送成功');
        }
        if ('${q.bankAccountLicence}' != '0') {
            $('#picType9').attr('src', '${ossImageReadRoot}/${q.bankAccountLicence}');
            $('#msg9').html('上送成功');
        }

        $('.fileUpload').fileupload({
                                        dataType: 'json',
                                        maxFileSize: 5000000,
                                        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                        add: function (e, data) {
                                            data.submit();
                                        },
                                        done: function (e, data) {
                                            var resp = data.result;
                                            if (eval(resp.status) === 200) {
                                                var currPic = 'picType' + picType;
                                                console.log(resp.data);
                                                $('#' + currPic).attr('src', '${ossImageReadRoot}/'
                                                                             + resp.data);
                                            } else {
                                                alert(resp.msg);
                                            }

                                        }
                                    });
    });
</script>
</body>
</html>

