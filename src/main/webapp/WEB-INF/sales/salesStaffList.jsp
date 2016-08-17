s<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/6
  Time: 下午2:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="merchantUrl" value="http://www.lepluslife.com/lepay/merchant/"></c:set>
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

        table tr td img {
            width: 80px;
            height: 80px;
        }

        .table > thead > tr > td, .table > thead > tr > th {
            line-height: 40px;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
            line-height: 60px;
        }

        .modal-body .main {
            width: 345px;
            height: 480px;
            margin: 0 auto;
            background: url("${resourceUrl}/images/lefuma.png") no-repeat;
            background-size: 100% 100%;
            position: relative;
        }

        .modal-body #myShowImage {
            width: 345px;
            height: 480px;
            position: absolute;
            top: 20px;
            left: 0;
            right: 0;
            margin: auto;
            display: none;
        }

        .modal-body .main .rvCode {
            width: 230px;
            height: 230px;
            position: absolute;
            top: 120px;
            left: 0;
            right: 0;
            margin: auto;
        }

        .modal-body .main .shopName {
            text-align: center;
            font-size: 24px;
            color: #fff;
            position: absolute;
            top: 365px;
            left: 0;
            right: 0;
            margin: auto;
            font-family: Arial;
        }

        #Layer1 {
            height: 250px;
            width: 450px;
            border: 5px solid #999;
            margin-right: auto;
            margin-left: auto;
            z-index: 50;
            display: none;
            position: relative;
            background-color: #FFF;
        }

        #Layer1 #win_top {
            height: 30px;
            width: 450px;
            border-bottom-width: 1px;
            border-bottom-style: solid;
            border-bottom-color: #999;
            line-height: 30px;
            color: #666;
            font-family: "微软雅黑", Verdana, sans-serif, "宋体";
            font-weight: bold;
            text-indent: 1em;
        }

        #Layer1 #win_top a {
            float: right;
            margin-right: 5px;
        }

        #shade {
            background-color: #000;
            position: absolute;
            z-index: 49;
            display: none;
            width: 100%;
            height: 100%;
            opacity: 0.6;
            filter: alpha(opacity=60);
            -moz-opacity: 0.6;
            margin: 0px;
            left: 0px;
            top: 0px;
            right: 0px;
            bottom: 0px;
        }

        #Layer1 .content {
            margin-top: 5px;
            margin-right: 30px;
            margin-left: 30px;
        }
    </style>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script src="${resourceUrl}/js/jquery.page.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/html2canvas.js"></script>
    <script src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script src="${resourceUrl}/js/daterangepicker.js"></script>
    <%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
    <script src="${resourceUrl}/js/moment.min.js"></script>

</head>

<body>
<div class="modal fade" id="myModalThree" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabelThree">

                </h4>
            </div>
            <div class="modal-body">
                <input id="newSalesStaffName">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary"
                        data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" onclick="newSalesStaffName()">
                    提交更改
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div id="topIframe">
    <%@include file="../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main">
            <div class="container-fluid" style="padding-top: 20px">
                <div class="row" style="margin-bottom: 30px">

                    <div class="form-group col-md-3"></div>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#lunbotu" data-toggle="tab">销售人员管理</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="lunbotu">
                        <div id="shade"></div>
                        <button type="button" class="btn btn-primary"
                                onClick="shade.style.display='block';Layer1.style.display='block'">
                            新增销售人员
                        </button>
                        <br/>
                    </div>
                </div>


                <div id="Layer1">
                    <div id="win_top">添加销售人员</div>
                    <br/>

                    <div class="content">
                        <table class="table table-bordered table-hover">
                            <table>
                                <tr>
                                    <td><input type="text" class="form-control" id="salesName"
                                               name="name"></td>

                                    <td>
                                        <button type="button" class="btn btn-primary"
                                                onclick="submitSales()">提交
                                        </button>
                                    </td>

                                    <td>
                                        <button type="button" class="btn btn-primary"
                                                onClick="shade.style.display='none';Layer1.style.display='none'">
                                            关闭
                                        </button>
                                    </td>
                                </tr>
                            </table>
                            <tbody id="saleStaffContent">
                            </tbody>
                        </table>
                    </div>
                </div>


                <table class="table table-bordered table-hover">
                    <thead>
                    <tr class="active">
                        <th>销售人员编号</th>
                        <th>姓名</th>
                        <th>负责商户数量</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${salesStaffList}" var="salesStaff">
                        <tr>
                            <td><span>${salesStaff.id}</span>
                            <td>${salesStaff.name}</td>
                            <td>${salesStaff.merchant.size()}</td>
                            <td><a onclick="salesStaffManage(${salesStaff.id})">商户管理</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                    onclick="updateSalesStaff(${salesStaff.id})">编辑</a></td>

                            <input type="hidden" id>

                            </td>
                        </tr>
                    </c:forEach>
                    </thead>

                </table>


                <div class="tcdPageCode" style="display: inline;">
                </div>
            </div>
        </div>
    </div>
</div>

<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    function submitSales() {
        var name = $("#salesName").val();
        location.href = "/manage/addSales?name=" + name;
    }
    function salesStaffManage(id) {
        location.href = "/manage/salesStaffManageMerchant?id=" + id;
    }
    function updateSalesStaff(salesStaffId) {
        $("#myModalLabelThree").empty();
        $("#myModalLabelThree").append("<input type=\"hidden\"  id=\"" + "salesStaffId"
                                       + "\" value=\"" + salesStaffId + "\">将编号为<span>"
                                       + salesStaffId + "</span>的名字改为");
        $('#myModalThree').modal({
                                     show: true,
                                     backdrop: 'static'
                                 });
    }
    function newSalesStaffName() {
        var newSalesStaffName = $("#newSalesStaffName").val();
        var salesStaffId = $("#salesStaffId").val();
        if (newSalesStaffName != null && newSalesStaffName != "" && newSalesStaffName.split(" ")
                                                                    != null
            && !(/^\s+$/gi.test(newSalesStaffName))) {
            location.href =
            "/manage/newSalesStaffName?salesStaffId=" + salesStaffId + "&&newSalesStaffName="
            + newSalesStaffName;
        }

    }

</script>
</body>
</html>


