<%--
  Created by IntelliJ IDEA.
  User: whl
  Date: 16/10/25
  Time: 下午5:08
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
    <!--<link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">-->
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>

    <style>thead th, tbody td {
        text-align: center;
    }

    #myTab {
        margin-bottom: 10px;
    }</style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
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
        <div class="btn btn-primary createLocation" style="margin: 20px 0 0px 1%;">返回POS订单列表</div>
        <hr />
        <div class="main">
            <div class="container-fluid">
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>账单名称</th>
                                <th>对账开始时间</th>
                                <th>差错明细</th>
                            </tr>
                            </thead>
                            <tbody id="orderContent">
                            <%--<tr>--%>
                            <%--<td>32423331</td>--%>
                            <%--<td><span>2016.6.6</span><br><span>14:14:15</span></td>--%>
                            <%--<td><span>棉花糖KTV</span><br><span>（231313123）</span></td>--%>
                            <%--<td><span>18710089228</span><br><span>（231313123）</span></td>--%>
                            <%--<td>已支付</td>--%>
                            <%--<td>导流订单</td>--%>
                            <%--<td>银行卡</td>--%>
                            <%--<td><span>¥510</span><br><span>（¥480+¥30红包）</span></td>--%>
                            <%--<td>¥48+¥3红包</td>--%>
                            <%--<td>¥432+¥27红包</td>--%>
                            <%--<td>¥25.5</td>--%>
                            <%--<td>30</td>--%>
                            <%--<td>¥21.52</td>--%>
                            <%--<td></td>--%>
                            <%--</tr>--%>
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
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<!--<script src="js/bootstrap-datetimepicker.min.js"></script>-->
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<!--<script src="js/bootstrap-datetimepicker.zh-CN.js"></script>-->
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>

</body>
</html>

