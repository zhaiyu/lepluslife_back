<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/9/6
  Time: 下午1:25
  content:商家邀请码
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE>
<style>
    .pagination > li > a.focusClass {
        background-color: #ddd;
    }

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

    .w-tabs {
        width: 100%;
        margin: 20px 0;
        border-bottom: 1px solid #000000;
        border-top: 1px solid #000000;
    }

    .w-tabs > div {
        width: 12%;
        margin: 0 2%;
        float: left;
        text-align: center;
    }

    .w-tabs > div > div:first-child {
        padding: 40px 0 0px 0;
        font-size: 18px;
    }

    .w-tabs > div > div:last-child {
        padding: 20px 0;
        color: #000;
        font-weight: bold;
    }

    .w-tabs:after {
        content: '\20';
        display: block;
        clear: both;
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link rel="stylesheet" type="text/css" href="${resourceUrl}/date/css/jedate.css"/>

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
            <div style="margin:20px;font-size: 30px">商户邀请码</div>
            <div class="w-tabs">
                <div>
                    <div id="inviteU"></div>
                    <div>已邀请粉丝</div>
                </div>
                <div>
                    <div id="unSubU"></div>
                    <div>取消关注粉丝</div>
                </div>
                <div>
                    <div id="inviteM"></div>
                    <div>已邀请会员</div>
                </div>
                <div>
                    <div id="totalA"></div>
                    <div>会员获得红包</div>
                </div>
                <div>
                    <div id="usedA"></div>
                    <div>会员使用红包</div>
                </div>
                <div>
                    <div id="commission"></div>
                    <div>会员产生佣金</div>
                </div>
            </div>

            <div class="row">
                <div class="form-group col-md-3">
                    <label for="merchant-name">商户名称</label>
                    <input type="text" id="merchant-name" class="form-control"
                           placeholder="请输入商户名称"/>
                </div>
                <div class="form-group col-md-3">
                    <label for="partnership">商户分类</label>
                    <select class="form-control" id="partnership">
                        <option value="-1">全部分类</option>
                        <option value="0">普通商户</option>
                        <option value="1">联盟商户</option>
                    </select></div>
                <div class="form-group col-md-3">
                    <button class="btn btn-primary" style="margin-top: 24px"
                            onclick="searchMerchantByCriteria()">筛选
                    </button>
                </div>
                <div class="col-md-12">
                    <div class="portlet">
                        <div class="portlet-body">
                            <div class="table-container">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class="text-center">商户ID</th>
                                        <th class="text-center">商户名称</th>
                                        <th class="text-center">商户类型</th>
                                        <th class="text-center">锁定会员</th>
                                        <th class="text-center">邀请粉丝</th>
                                        <th class="text-center">邀请会员</th>
                                        <th class="text-center">会员获得红包</th>
                                        <th class="text-center">会员使用红包</th>
                                        <th class="text-center">会员产生佣金</th>
                                        <th class="text-center">订单分布</th>
                                        <th class="text-center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="userContent">

                                    </tbody>
                                </table>

                                <div class="tcdPageCode" style="display: inline;">
                                </div>
                                <div style="display: inline;"> 共有 <span id="totalElements"></span> 个
                                </div>

                            </div>
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

<!--二维码提示框-->
<div class="modal" id="qrWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">二维码</h4>
            </div>
            <div style="text-align: center">
                <img id="qrCode" src="" alt=""/>
            </div>
        </div>
    </div>
</div>

<!--如果只是做布局的话不需要下面两个引用-->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script src="${resourceUrl}/date/js/jedate.min.js"></script>
<script src="${resourceUrl}/date/js/jedate.js"></script>
<script>

    var offset = 1, init1 = 0, merchantCriteria = {}, flag = true, userContent = document.getElementById("userContent");
    jQuery(document).ready(function () {
        merchantCriteria.offset = 1;
        getTotalData();
        getUserByAjax(merchantCriteria);
    });

    function getTotalData() {
        $.ajax({
                   type: "get",
                   url: "/manage/merchant/ajaxTotalData",
                   async: false,
                   contentType: "application/json",
                   success: function (data) {
                       var map = data.data;
                       $("#inviteU").html(map.inviteU);
                       $("#unSubU").html(map.unSubU);
                       $("#inviteM").html(map.inviteM);
                       $("#totalA").html("￥" + map.totalA / 100);
                       $("#usedA").html("￥" + map.usedA / 100);
                       $("#commission").html("￥" + map.commission / 100);
                   }
               });
    }

    function getUserByAjax(criteria) {
        userContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/merchant/ajaxCodeList",
                   data: JSON.stringify(criteria),
                   async: false,
                   contentType: "application/json",
                   success: function (data) {
                       var msg = data.msg.split("_");
                       var content = data.data;
                       var totalPage = msg[0];
                       var totalElements = msg[1];
                       $("#totalElements").html(totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag) {
                           initPage(offset, totalPage);
                           flag = false;
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td class="text-center">' + content[i].merchantSid
                                            + '</td>';
                           contentStr += '<td class="text-center">' + content[i].name + '</td>';
                           if (content[i].partnership == 1) {
                               contentStr += '<td class="text-center">联盟商户</td>';
                           } else {
                               contentStr += '<td class="text-center">普通商户</td>';
                           }
                           contentStr +=
                           '<td class="text-center">' + content[i].bindM + '/'
                           + content[i].userLimit + '</td>';
                           contentStr += '<td class="text-center">' + content[i].inviteU + '</td>';
                           contentStr += '<td class="text-center">' + content[i].inviteM + '</td>';
                           contentStr +=
                           '<td class="text-center">￥' + content[i].totalA / 100 + '</td>';
                           contentStr +=
                           '<td class="text-center">￥' + content[i].usedA / 100 + '</td>';
                           contentStr +=
                           '<td class="text-center">￥' + content[i].commission / 100 + '</td>';
                           contentStr +=
                           '<td class="text-center">纯支付牌:' + content[i].order_5 + '<br>会员订单:'
                           + content[i].order_3 + '<br>导流订单:' + content[i].order_1 + '</td>';

                           if (content[i].qrCode == 1) { //已有邀请码
                               contentStr +=
                               '<td class="text-center"><input type="hidden" class="ticket-hidden" value="'
                               + content[i].ticket
                               + '"><button class="btn btn-primary showQR">查看二维码</button></td></tr>';
                           } else {
                               contentStr +=
                               '<td class="text-center"><input type="hidden" class="id-hidden" value="'
                               + content[i].id
                               + '"><button class="btn btn-primary getQR"><span style="color: yellow">获取二维码</span></button></td></tr>';
                           }
                           userContent.innerHTML += contentStr;
                       }

                       //永久二维码
                       $(".showQR").each(function (i) {
                           $(".showQR").eq(i).bind("click", function () {
                               var ticket = $(this).parent().find(".ticket-hidden").val();
                               $('#qrCode').attr('src',
                                                 'https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket='
                                                 + ticket);
                               $('#qrWarn').modal('show');
                           });
                       });

                       //获取永久二维码
                       $(".getQR").each(function (i) {
                           $(".getQR").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               if (!confirm('确定生成二维码吗？(将会产生一个永久二维码)')) {
                                   return false;
                               }
                               $.ajax({
                                          type: "get",
                                          url: "/manage/merchant/createQrCode/" + id,
                                          contentType: "application/json",
                                          success: function (data) {
                                              setTimeout(function () {
                                                  getUserByAjax(merchantCriteria);
                                              }, 0);
                                          }
                                      });
                           });
                       });

                   }
               });
    }

    function searchMerchantByCriteria() {
        merchantCriteria.offset = 1;
        init1 = 1;
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            merchantCriteria.merchantName = $("#merchant-name").val();
        } else {
            merchantCriteria.merchantName = null;
        }
        if ($("#partnership").val() != -1) {
            merchantCriteria.partnership = $("#partnership").val();
        } else {
            merchantCriteria.partnership = null;
        }

        getUserByAjax(merchantCriteria);
    }

    function initPage(currPage, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: currPage,
                                         backFn: function (p) {
                                             merchantCriteria.offset = p;
                                             getUserByAjax(merchantCriteria);
                                         }
                                     });
    }

    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        var week = {
            "0": "\u65e5",
            "1": "\u4e00",
            "2": "\u4e8c",
            "3": "\u4e09",
            "4": "\u56db",
            "5": "\u4e94",
            "6": "\u516d"
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        if (/(E+)/.test(fmt)) {
            fmt =
            fmt.replace(RegExp.$1,
                        ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468")
                                : "") + week[this.getDay() + ""]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt =
                fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr((""
                                                                                                 + o[k]).length)));
            }
        }
        return fmt;
    }

</script>
</body>
</html>

