<%--
  Created by IntelliJ IDEA.
  User: WhiteFeather
  Date: 2017/6/21
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../commen.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>乐+生活 后台模板管理系统</title>
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/create-edit-store.css"/>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script
    <script src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script src="${resourceUrl}/js/daterangepicker.js"></script>
    <script src="${resourceUrl}/js/moment.min.js"></script>
    <script src="${resourceUrl}/js/jquery.page.js"></script>
</head>
<style>
    #details {
        display: none;
    }
    #details .Mod-10 {
        margin: 10px 0;
    }
</style>
<body>
<div id="topIframe">
    <%@include file="../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="merchant_management-filter">
            <div class="MODInput_row">
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">订单编号</div>
                    <div class="Mod-6">
                        <input id="orderSid" class="ModRadius-right" placeholder="请输入订单编号">
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">团购ID</div>
                    <div class="Mod-6">
                        <input  id="productSid" class="ModRadius-right" placeholder="请输入团购ID">
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">团购名称</div>
                    <div class="Mod-6">
                        <input id="productName" class="ModRadius-right" placeholder="请输入团购名称">
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">订单类型</div>
                    <div class="Mod-6">
                        <select id="orderType" name="filter-partner" id="store_management-filterPartner" class="ModRadius-right">
                            <option value="-1">全部</option>
                            <option value="0">普通订单</option>
                            <option value="1">乐加订单</option>
                        </select>
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">状态</div>
                    <div class="Mod-6">
                        <select id="state" name="filter-partner" class="ModRadius-right">
                            <option value="-1">全部</option>
                            <option value="0">退款中</option>
                            <option value="1">退款完成</option>
                            <option value="2">退款驳回</option>
                        </select>
                    </div>
                </div>
                <div class="Mod-2">
                    <button class="ModButton ModButton_ordinary ModRadius" onclick="searchRefundByCriteria()">筛选查询</button>
                </div>
            </div>
        </div>
        <div class="ModLine ModRadius"></div>
        <div class="merchant_management-table">
            <div class="merchant_management-addButton">
                <button class="ModButton ModButton_ordinary ModRadius" onclick="exportExcel()">导出表格</button>
            </div>
            <div class="toggleTable">
                <div id="toggleContent" class="tab-content">
                    <div class="tab-pane fade in active">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>订单编号</th><th>团购ID</th><th>团购名称</th><th>订单类型</th><th>数量</th><th>订单金额</th>
                                <th>使用鼓励金</th><th>实付金额</th><th>支付方式</th><th>送鼓励金</th><th>送金币</th><th>状态</th><th>下单时间</th><th>完成时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="orderContent">
                            <tr>
                                <td>0002230F0370520</td><td>333</td><td>一品江南大果盘</td><td>乐加订单</td><td>3</td>
                                <td>120</td><td>3</td><td>191</td>
                                <td>微信公众号</td><td>2</td>
                                <td>1</td><td>未支付</td>
                                <td><span>2016.6.6 </span><br><span>14：21</span></td>
                                <td><span>2016.6.6 </span><br><span>14：21</span></td>
                                <td>
                                    <input type="button" class="btn btn-xs btn-warning select-btn Mod-7" onclick="layerOpen()" value="设为已退款">
                                </td>
                            </tr>
                            <tr>
                                <td>0002230F0370520</td><td>333</td><td>一品江南大果盘</td><td>乐加订单</td><td>3</td>
                                <td>120</td><td>3</td><td>191</td>
                                <td>微信公众号</td><td>2</td>
                                <td>1</td><td>未支付</td>
                                <td><span>2016.6.6 </span><br><span>14：21</span></td>
                                <td><span>2016.6.6 </span><br><span>14：21</span></td>
                                <td>
                                    <input type="button" class="btn btn-xs btn-warning select-btn Mod-7"  onclick="layerOpen()" value="设为已退款">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <nav class="pull-right">
                        <div class="tcdPageCode" style="display: inline;">
                        </div>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

<!--提示框-->
<div id="details">
    <div class="MODInput_row">
        <div class="Mod-10">
            <div class="ModLabel ModRadius-left">通道退款单号</div>
            <div class="Mod-6">
                <input  class="ModRadius-right" placeholder="请输入通道退款单号">
            </div>
        </div>
        <div class="Mod-10">
            <div class="ModLabel ModRadius-left">实际退款金额</div>
            <div class="Mod-6">
                <input  class="ModRadius-right" placeholder="请输入实际退款金额">
            </div>
        </div>
        <div class="Mod-10">
            <div class="ModLabel ModRadius-left">实际退还鼓励金</div>
            <div class="Mod-6">
                <input  class="ModRadius-right" placeholder="请输入实际退还鼓励金">
            </div>
        </div>
        <div class="Mod-10">
            <div class="ModLabel ModRadius-left">追回鼓励金</div>
            <div class="Mod-6">
                <input  class="ModRadius-right" placeholder="请输入追回鼓励金">
            </div>
        </div>
        <div class="Mod-10">
            <div class="ModLabel ModRadius-left">追回金币</div>
            <div class="Mod-6">
                <input  class="ModRadius-right" placeholder="请输入追回金币">
            </div>
        </div>
        <div class="Mod-10">
            <button class="ModButton ModButton_ordinary ModRadius">确认</button>
            <button class="ModButton ModButton_back ModRadius" onclick="closeLayer()">取消</button>
        </div>
    </div>
</div>

<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
</body>
<script>
    var refundCriteria = {};
    refundCriteria.offset = 1;
    var orderContent = document.getElementById("orderContent");
    //   根据条件查询产品
    function searchRefundByCriteria() {
        refundCriteria.offset = 1;
        //  订单SID
        if ($("#orderSid").val() != null && $("#orderSid").val() != '') {
            refundCriteria.orderSid = $("#orderSid").val();
        } else {
            refundCriteria.orderSid = null;
        }
        //  团购 ID
        if ($("#productSid").val() != null && $("#productSid").val() != '') {
            refundCriteria.productSid = $("#productSid").val();
        } else {
            refundCriteria.productSid = null;
        }
        //  团购名称
        if ($("#productName").val() != null && $("#productName").val() != '') {
            refundCriteria.productName = $("#productName").val();
        } else {
            refundCriteria.productName = null;
        }
        //  订单类型
        if ($("#orderType").val() != null && $("#orderType").val() != -1) {
            refundCriteria.orderType = $("#orderType").val();
        } else {
            refundCriteria.orderType = null;
        }
        //  状态
        if ($("#state").val() != null && $("#state").val() != -1) {
            refundCriteria.state = $("#state").val();
        } else {
            refundCriteria.state = null;
        }
        getRefundByAjax(refundCriteria);
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
    function getRefundByAjax(refundCriteria) {
        orderContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/refundOrder/findByCriteria",
            async: false,
            data: JSON.stringify(refundCriteria),
            contentType: "application/json",
            success: function (result) {
                console.log(JSON.stringify(result));
                var page = result.data;
                var list = page.content;
                var totalPage = page.totalPages;
                var content = "";
                  for (var i = 0; i < list.length; i++) {
                   content += '<tr><td>' + list[i].grouponOrder.orderSid + '</td><td>' + list[i].grouponOrder.grouponProduct.sid + '</td><td>' + list[i].grouponOrder.grouponProduct.name + '</td>';
                    if (list[i].grouponOrder.orderType == 0) {
                        content += '<td>普通订单</td>';
                    } else {
                        content += '<td>乐加订单</td>';
                    }
                    //  数量
                    content += '<td>￥' + (list[i].grouponOrder.buyNum) + '</td>';
                    // 订单金额
                    content += '<td>￥' + (list[i].grouponOrder.totalPrice) + '</td>';
                    //  使用鼓励金
                    content += '<td>￥' + (list[i].grouponOrder.scorea / 100.0) + '</td>';
                    //  实付金额
                    content += '<td>' + list[i].grouponOrder.truePay / 100.0 + '</td>';
                    //  支付方式
                    if (list[i].grouponOrder.orderType == 0) {
                        content += '<td>公众号</td>';
                    } else {
                        content += '<td>App</td>';
                    }
                    //  送鼓励金
                    content += '<td>' + list[i].grouponOrder.rebateScorea / 100.0 + '</td>';
                    //  送金币
                    content += '<td>' + list[i].grouponOrder.rebateScorec / 100.0 + '</td>';
                    //  状态
                    if (list[i].state == 0) {
                        content += '<td>退款中</td>';
                    } else if (list[i].state == 1) {
                        content += '<td>退款完成</td>';
                    } else {
                        content += '<td>退款驳回</td>';
                    }
                    //  下单时间
                    content += '<td>' + new Date(list[i].grouponOrder.createDate).format('yyyy-MM-dd HH:mm:ss') + '</td>';
                    //  完成时间
                    if (list[i].grouponOrder.state==1) {
                        content += '<td>' + new Date(list[i].grouponOrder.completeDate).format('yyyy-MM-dd HH:mm:ss') + '</td>';
                    } else {
                        content += '<td>---</td>';
                    }
                    content += '<td><input type="button" class="btn btn-xs btn-warning select-btn Mod-7"  onclick="layerOpen()" value="设为已退款"></td>';
                    content += '</tr>';
                }
                initPage(refundCriteria.offset, totalPage);
                orderContent.innerHTML += content;
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
    getRefundByAjax(refundCriteria);
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                refundCriteria.offset = p;
                getRefundByAjax(refundCriteria);
            }
        });
    }
    //  导出 Excel
    function post(URL, PARAMS) {
        var temp = document.createElement("form");
        temp.action = URL;
        temp.method = "post";
        temp.style.display = "none";
        for (var x in PARAMS) {
            var opt = document.createElement("textarea");
            opt.name = x;
            opt.value = PARAMS[x];
            // alert(opt.name)
            temp.appendChild(opt);
        }
        document.body.appendChild(temp);
        temp.submit();
        return temp;
    }
    function exportExcel() {
        //  订单SID
        if ($("#orderSid").val() != null && $("#orderSid").val() != '') {
            refundCriteria.orderSid = $("#orderSid").val();
        } else {
            refundCriteria.orderSid = null;
        }
        //  团购 ID
        if ($("#productSid").val() != null && $("#productSid").val() != '') {
            refundCriteria.productSid = $("#productSid").val();
        } else {
            refundCriteria.productSid = null;
        }
        //  团购名称
        if ($("#productName").val() != null && $("#productName").val() != '') {
            refundCriteria.productName = $("#productName").val();
        } else {
            refundCriteria.productName = null;
        }
        //  订单类型
        if ($("#orderType").val() != null && $("#orderType").val() != -1) {
            refundCriteria.orderType = $("#orderType").val();
        } else {
            refundCriteria.orderType = null;
        }
        //  状态
        if ($("#state").val() != null && $("#state").val() != -1) {
            refundCriteria.state = $("#state").val();
        } else {
            refundCriteria.state = null;
        }
        post("/manage/refundOrder/export", refundCriteria);
    }
</script>
</html>