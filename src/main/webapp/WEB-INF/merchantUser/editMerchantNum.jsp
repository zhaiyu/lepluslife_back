<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 2017/1/9
  Time: 17:52
  商户号新建和编辑页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>乐+生活 后台模板管理系统</title>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
    <link type="text/css" rel="stylesheet"
          href="${resourceUrl}/merchantUser/css/merchantManagement.css"/>
    <link type="text/css" rel="stylesheet"
          href="${resourceUrl}/merchantUser/css/merchantInformation.css"/>
    <link type="text/css" rel="stylesheet"
          href="${resourceUrl}/merchantUser/css/create-edit-store.css"/>

    <script src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script src="${resourceUrl}/js/respond.min.js"></script>

    <script type="text/javascript" src="${resourceUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/Mod/common.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.combo.select.js"></script>
    <script type="text/javascript"
            src="${resourceUrl}/js/layer/laydate/laydate.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/echarts.min.js"></script>
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
        <div class="create_edit-title">
            <div class="ModRadius" onclick="back()"> < 返回商户管理</div>
            <p>商户号创建/编辑</p>
        </div>
        <div class="MODInput_row merchant_information-tab6">
            <input type="hidden" id="merchantUserId" value="${m_user.id}"/>
            <input type="hidden" id="id" value="${m_number.id}"/>

            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">商户名称</div>
                <div class="Mod-6 ModDisabled">
                    ${m_user.merchantName}
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">商户号类型</div>
                <div class="Mod-5">
                    <select id="type">
                        <option value="0">普通商户号</option>
                        <option value="1">佣金商户号</option>
                    </select>
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">费率</div>
                <div class="Mod-2">
                    <input class="ModRadius-right" id="commission" value="${m_number.commission}">
                </div>
                <div class="Mod-1 ModDisabled">
                    %
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">商户号</div>
                <div class="Mod-5">
                    <input class="ModRadius-right" placeholder="请输入商户号" id="merchantNum"
                           value="${m_number.merchantNum}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">账号类型</div>
                <div class="Mod-6 ModDisabled">
                    法人私账
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">结算卡号</div>
                <div class="Mod-5">
                    <input class="ModRadius-right" placeholder="请输入结算卡号" id="bankNumber"
                           value="${m_number.bankNumber}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">开户支行</div>
                <div class="Mod-5">
                    <input class="ModRadius-right" placeholder="请输入开户支行" id="bankName"
                           value="${m_number.bankName}">
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">联行号</div>
                <div class="Mod-3">
                    <input class="ModRadius-right" placeholder="请输入联行号" id="bankUnion"
                           value="${m_number.bankUnion}">
                </div>
                <div class="Mod-3 ModDisabled">
                    （为了以后方便对接中汇，请真实填写联行号）
                </div>
            </div>
            <div class="Mod-10">
                <div class="ModLabel ModRadius-left">收款人</div>
                <div class="Mod-5">
                    <input class="ModRadius-right" placeholder="请输入收款人" id="payee"
                           value="${m_number.payee}">
                </div>
            </div>
            <div class="Mod-2">
                <button class="ModButton ModButton_ordinary ModRadius" id="submit"
                        onclick="submit()">保存信息
                </button>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
</body>
<script>
    if (${m_number != null}) {
        $('#type').val(${m_number.type});
    }
    function back() {
        window.location.href =
        "/manage/merchantUser/info/" + $('#merchantUserId').val() + '?li=4';
    }
    function trim(str) { //删除左右两端的空格
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    function submit() {
        var merchantSettlement = {};
        merchantSettlement.id = $('#id').val();
        merchantSettlement.merchantUserId = $('#merchantUserId').val();
        merchantSettlement.type = $('#type').val();

        var w1 = $('#commission').val();
        if (w1 != null && w1 != '') {
            var commission = Number(w1);
            if (isNaN(commission) || commission < 0 || commission > 100) {
                alert("费率输入有误！");
                return;
            } else {
                var dot = w1.indexOf(".");
                if (dot != -1) {
                    var dotCnt = w1.substring(dot + 1, w1.length);
                    if (dotCnt.length > 2) {
                        alert("费率小数位已超过2位！");
                        return;
                    }
                }
            }
            merchantSettlement.commission = commission;
            console.log('commission =============' + commission);
        } else {
            alert("请输入费率！");
            return;
        }

        var merchantNum = $('#merchantNum').val();
        if (merchantNum != null && merchantNum != '') {
            merchantSettlement.merchantNum = merchantNum;
        } else {
            alert("请输入商户号！");
            return;
        }

        merchantSettlement.accountType = 0;  //暂时都是法人私账

        var bankNumber = $('#bankNumber').val();
        if (bankNumber != null && bankNumber != '') {
            merchantSettlement.bankNumber = bankNumber;
        } else {
            alert("请输入结算卡号！");
            return;
        }

        var bankName = $('#bankName').val();
        if (bankName == null || bankName == '') {
            alert('请输入开户支行');
            return;
        }
        merchantSettlement.bankName = bankName;

        var bankUnion = $('#bankUnion').val();
        if (bankUnion != null && bankUnion != '') {
            merchantSettlement.bankUnion = bankUnion;
        } else {
            alert("请输入联行号！");
            return;
        }

        var payee = $('#payee').val();
        if (payee == null || payee == '') {
            alert('请输入收款人');
            return;
        }
        merchantSettlement.payee = payee;
        $('#submit').attr('onclick', '');
        $.ajax({
                   url: "/manage/m_settlement/save",
                   type: "post",
                   contentType: "application/json",
                   data: JSON.stringify(merchantSettlement),
                   success: function (result) {
                       if (result.status == 200) {
                           if ($('#id').val() == '') {
                               alert('创建成功！');
                           } else {
                               alert('修改成功！');
                           }
                           back();
                       } else {
                           alert(result.msg);
                           $('#submit').attr('onclick', 'submit()');
                       }
                   }
               });
    }
</script>
</html>
