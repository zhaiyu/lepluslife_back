<%--
  Created by IntelliJ IDEA.
  User: WhiteFeather
  Date: 2017/6/21
  Time: 9:39
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


    <script type="text/javascript" src="${resourceUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/Mod/common.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.combo.select.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/Mod/RetainDecimalFor2.js"></script>
    <!--强制保留2位小数js-->
    <script src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script src="${resourceUrl}/js/daterangepicker.js"></script>
    <script src="${resourceUrl}/js/moment.min.js"></script>
    <script src="${resourceUrl}/js/jquery.page.js"></script>
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
        <div class="merchant_management-filter">
            <div class="MODInput_row">
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">所属商户</div>
                    <div class="Mod-6">
                        <input  id="bindMU" class="ModRadius-right" placeholder="请输入所属商户">
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">团购ID</div>
                    <div class="Mod-6">
                        <input  id="grounonSid"  class="ModRadius-right" placeholder="请输入团购ID">
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">团购名称</div>
                    <div class="Mod-6">
                        <input  id="grounonName"  class="ModRadius-right" placeholder="请输入团购名称">
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">状态</div>
                    <div class="Mod-6">
                        <select  id="prdState"  name="filter-city" id="merchant_management-filterCity" class="ModRadius-right">
                            <option value="-1">全部状态</option>
                            <option value="1">上架</option>
                            <option value="0">下架</option>
                        </select>
                    </div>
                </div>
                <div class="Mod-2">
                    <button class="ModButton ModButton_ordinary ModRadius" onclick="searchProductByCriteria()">筛选查询</button>
                </div>
            </div>
        </div>
        <div class="ModLine ModRadius"></div>
        <div class="merchant_management-table">
            <div class="merchant_management-addButton">
                <button class="ModButton ModButton_ordinary ModRadius" onclick="productAdd()">添加</button>
            </div>
            <div class="toggleTable">
                <div id="toggleContent" class="tab-content">
                    <div class="tab-pane fade in active">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>团购ID</th><th>团购名称</th><th>所属商户</th><th>可用门店</th><th>普通团购价</th>
                                <th>乐+会员价</th><th>收取佣金</th><th>发放鼓励金</th><th>发放金币</th><th>普通库存</th>
                                <th>乐+库存</th><th>状态</th><th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="productContent">

                            </tbody>
                        </table>
                    </div>
                    <div class="tcdPageCode" style="display: inline;">
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
    var productCriteria = {};
    productCriteria.offset = 1;
    var productContent = document.getElementById("productContent");
    //   根据条件查询产品
    function searchProductByCriteria() {
        productCriteria.offset = 1;
        // 商户名称
        if($("#bindMU").val()!=null&&$("#bindMU").val()!='') {
            productCriteria.merchantUser = $("#bindMU").val();
        }else {
            productCriteria.merchantUser  = null;
        }
        //  团购 ID
        if($("#grounonSid").val()!=null&&$("#grounonSid").val()!='') {
            productCriteria.sid = $("#grounonSid").val();
        }else {
            productCriteria.sid = null;
        }
        //  团购名称
        if($("#grounonName").val()!=null&&$("#grounonName").val()!='') {
            productCriteria.name = $("#grounonName").val();
        }else {
            productCriteria.name = null;
        }
        //  状态
        if($("#prdState").val()!=null&&$("#prdState").val()!=-1) {
            productCriteria.state = $("#prdState").val();
        }else {
            productCriteria.state = null;
        }
        getProductByAjax(productCriteria);
    }
    function getProductByAjax(productCriteria) {
        productContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/grouponProduct/findByCriteria",
            async: false,
            data: JSON.stringify(productCriteria),
            contentType: "application/json",
            success: function (result) {
                var page = result.data.data;
                var list = page.content;
                var bindMerchants = result.data.bindMerchants;
                var totalPage = page.totalPages;
                var content = "";
                for (i = 0; i < list.length; i++) {
                    content+='<tr><td>'+list[i].id+'</td><td>'+list[i].name+'</td><td>'+list[i].merchantUser.name+'</td>'+
                        '<td>'+bindMerchants[i]+'家</td><td>￥'+(list[i].normalPrice/100.0)+'</td><td>￥'+(list[i].ljPrice/100.0)+'</td><td>￥'+(list[i].ljCommission/100.0)+'</td><td>￥'+(list[i].rebateScorea/100.0)+'</td>'+
                        '<td>'+list[i].rebateScorec/100.0+'</td><td>'+list[i].normalStorage+'</td><td>'+list[i].ljStorage+'</td>';
                    if(list[i].state==0) {
                        content+='<td>已下架</td>';
                        content+='<td><input type="button" class="btn btn-xs btn-primary select-btn createWarn" value="编辑" onclick="productEdit('+list[i].id+')">'+
                            '<input type="button" class="btn btn-xs btn-danger select-btn createWarn" value="上架" onclick="prodUp('+list[i].sid+')"></td></tr> ';
                    }else{
                        content+='<td>已上架</td>';
                        content+='<td><input type="button" class="btn btn-xs btn-primary select-btn createWarn" value="编辑" onclick="productEdit('+list[i].id+')">'+
                            '<input type="button" class="btn btn-xs btn-danger select-btn createWarn" value="下架"  onclick="prodDown('+list[i].sid+')"></td></tr> ';
                    }
                }
                initPage(productCriteria.offset, totalPage);
                productContent.innerHTML +=content;
            }
        });
    }
    getProductByAjax(productCriteria);
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                productCriteria.offset = p;
                getProductByAjax(productCriteria);
            }
        });
    }
    // 跳转至添加页面
    function productAdd() {
        location.href="/manage/grouponProduct/add";
    }
    // 跳转到编辑页面
    function productEdit(id) {
        location.href="/manage/grouponProduct/edit?id="+id;
    }
    //   上架
    function  prodUp(sid) {
        var sure = confirm("请问是否要上架该商品");
        if(sure) {
            $.ajax({
                type: "get",
                url: "/manage/grouponProduct/up?sid="+sid,
                success: function (result) {
                    if(result.status==200) {
                        alert("上架成功 ^_^");
                        location.href="/manage/grouponProduct/list";
                    }else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
    //   下架
    function  prodDown(sid) {
        var sure = confirm("请问是否要下架该商品");
        if(sure) {
            $.ajax({
                type: "get",
                url: "/manage/grouponProduct/down?sid="+sid,
                success: function (result) {
                    if(result.status==200) {
                        alert("下架成功 ^_^");
                        location.href="/manage/grouponProduct/list";
                    }else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
</script>
</html>
