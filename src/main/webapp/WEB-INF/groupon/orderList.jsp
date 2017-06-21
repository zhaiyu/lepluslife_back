<%--
  Created by IntelliJ IDEA.
  User: WhiteFeather
  Date: 2017/6/21
  Time: 9:43
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
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
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
        <div class="merchant_management-filter">
            <div class="MODInput_row">
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">订单编号</div>
                    <div class="Mod-6">
                        <input  class="ModRadius-right" placeholder="请输入订单编号">
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">团购ID</div>
                    <div class="Mod-6">
                        <input  class="ModRadius-right" placeholder="请输入团购ID">
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">团购名称</div>
                    <div class="Mod-6">
                        <input  class="ModRadius-right" placeholder="请输入团购名称">
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">订单类型</div>
                    <div class="Mod-6">
                        <select name="filter-partner" id="store_management-filterPartner" class="ModRadius-right">
                            <option value="0">输入订单类型</option>
                            <option value="1">订单类型1</option>
                            <option value="1">订单类型2</option>
                        </select>
                    </div>
                </div>
                <div class="Mod-3">
                    <div class="ModLabel ModRadius-left">状态</div>
                    <div class="Mod-6">
                        <select name="filter-partner" class="ModRadius-right">
                            <option value="0">输入状态</option>
                            <option value="1">状态1</option>
                            <option value="1">状态2</option>
                        </select>
                    </div>
                </div>
                <div class="Mod-2">
                    <button class="ModButton ModButton_ordinary ModRadius">筛选查询</button>
                </div>
            </div>
        </div>
        <div class="ModLine ModRadius"></div>
        <div class="merchant_management-table">
            <div class="merchant_management-addButton">
                <button class="ModButton ModButton_ordinary ModRadius">导出表格</button>
            </div>
            <div class="toggleTable">
                <div id="toggleContent" class="tab-content">
                    <div class="tab-pane fade in active">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>订单编号</th><th>团购ID</th><th>团购名称</th><th>订单类型</th><th>数量</th><th>订单金额</th>
                                <th>使用鼓励金</th><th>实付金额</th><th>支付方式</th><th>送鼓励金</th><th>送金币</th><th>状态</th><th>下单时间</th><th>完成时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>0002230F0370520</td><td>333</td><td>一品江南大果盘</td><td>乐加订单</td><td>3</td>
                                <td>120</td><td>3</td><td>191</td>
                                <td>微信公众号</td><td>2</td>
                                <td>1</td><td>未支付</td>
                                <td><span>2016.6.6 </span><br><span>14：21</span></td>
                                <td><span>2016.6.6 </span><br><span>14：21</span></td>
                            </tr>
                            <tr>
                                <td>0002230F0370520</td><td>333</td><td>一品江南大果盘</td><td>乐加订单</td><td>3</td>
                                <td>120</td><td>3</td><td>191</td>
                                <td>微信公众号</td><td>2</td>
                                <td>1</td><td>未支付</td>
                                <td><span>2016.6.6 </span><br><span>14：21</span></td>
                                <td><span>2016.6.6 </span><br><span>14：21</span></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <nav class="pull-right">
                        <ul class="pagination pagination-lg">
                            <li><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>
                            <li><a href="#" class="focusClass">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>
                        </ul>
                    </nav>
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

</script>
</html>