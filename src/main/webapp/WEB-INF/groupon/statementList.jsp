<%--
  Created by IntelliJ IDEA.
  User: WhiteFeather
  Date: 2017/6/21
  Time: 9:58
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
        <div class="main" >
            <div class="merchant_management-filter">
                <div class="MODInput_row">
                    <div class="Mod-10" style="margin: 10px 0">
                        <div class="ModLabel ModRadius-left">交易日期</div>
                        <div class="Mod-6" style="margin-top: -2px">
                            <input id="merchant_management-startTime" class="laydate-icon layer-date ModRadius-right" placeholder="起始时间">
                            <input id="merchant_management-endTime" class="laydate-icon layer-date ModRadius" placeholder="截止时间">
                        </div>
                    </div>
                    <div class="Mod-3">
                        <div class="ModLabel ModRadius-left">门店ID</div>
                        <div class="Mod-6">
                            <input  class="ModRadius-right" placeholder="请输入门店ID">
                        </div>
                    </div>
                    <div class="Mod-3">
                        <div class="ModLabel ModRadius-left">门店名称</div>
                        <div class="Mod-6">
                            <input  class="ModRadius-right" placeholder="请输门店名称">
                        </div>
                    </div>
                    <div class="Mod-10">
                        <button class="ModButton ModButton_ordinary ModRadius">筛选查询</button>
                    </div>
                </div>
            </div>
            <div class="container-fluid">
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab">待结算</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab">已结算</a></li>
                    <li><a href="#tab3" data-toggle="tab">已挂起</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active scanOrder-tabBorder" id="tab1">
                        <div class="merchant_management-table">
                            <div class="merchant_management-addButton">
                                <button class="ModButton ModButton_ordinary ModRadius">导出表格</button>
                                <button class="ModButton ModButton_ordinary ModRadius">全部确认转账</button>
                            </div>
                            <div class="toggleTable">
                                <div class="tab-content">
                                    <div class="tab-pane fade in active">
                                        <table class="table table-bordered table-hover">
                                            <thead>
                                            <tr class="active">
                                                <th>结算单编号</th><th>交易日期</th><th>门店ID</th><th>门店名称</th><th>乐加核销笔数</th><th>普通核销笔数</th><th>乐加应入账</th>
                                                <th>乐加佣金</th><th>普通应入账</th><th>普通手续费</th><th>总入账</th><th>状态</th>
                                                <th>操作</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>32423331</td><td><span>2016.6.6 </span><br><span>14：21</span></td>
                                                <td><span>棉花糖KTV</span><br><span>(朝阳路)</span></td>
                                                <td>棉花糖KTV（231313123）</td><td>5</td>
                                                <td>3</td><td>233</td><td>33</td><td>22</td>
                                                <td>2</td><td>¥476</td><td>待结算</td>
                                                <td>
                                                    <input type="button" class="btn btn-xs btn-warning select-btn Mod-7" value="设为转账">
                                                    <input type="button" class="btn btn-xs btn-danger select-btn Mod-7" value="设为挂起">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>32423331</td><td><span>2016.6.6 </span><br><span>14：21</span></td>
                                                <td><span>棉花糖KTV</span><br><span>(朝阳路)</span></td>
                                                <td>棉花糖KTV（231313123）</td><td>5</td>
                                                <td>3</td><td>233</td><td>33</td><td>22</td>
                                                <td>2</td><td>¥476</td><td>待结算</td>
                                                <td>
                                                    <input type="button" class="btn btn-xs btn-warning select-btn Mod-7" value="设为转账">
                                                    <input type="button" class="btn btn-xs btn-danger select-btn Mod-7" value="设为挂起">
                                                </td>
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
                    <div class="tab-pane fade in active scanOrder-tabBorder" id="tab2">
                        <div class="merchant_management-table">
                            <div class="merchant_management-addButton">
                                <button class="ModButton ModButton_ordinary ModRadius">导出表格</button>
                            </div>
                            <div class="toggleTable">
                                <div class="tab-content">
                                    <div class="tab-pane fade in active">
                                        <table class="table table-bordered table-hover">
                                            <thead>
                                            <tr class="active">
                                                <th>结算单编号</th><th>交易日期</th><th>门店ID</th><th>门店名称</th><th>乐加核销笔数</th><th>普通核销笔数</th><th>乐加应入账</th>
                                                <th>乐加佣金</th><th>普通应入账</th><th>普通手续费</th><th>总入账</th><th>状态</th>
                                                <th>操作</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>32423331</td><td><span>2016.6.6 </span><br><span>14：21</span></td>
                                                <td><span>棉花糖KTV</span><br><span>(朝阳路)</span></td>
                                                <td>棉花糖KTV（231313123）</td><td>5</td>
                                                <td>3</td><td>233</td><td>33</td><td>22</td>
                                                <td>2</td><td>¥476</td><td>待结算</td>
                                                <td>
                                                    <input type="button" class="btn btn-xs btn-warning select-btn Mod-7" value="设为转账">
                                                    <input type="button" class="btn btn-xs btn-danger select-btn Mod-7" value="设为挂起">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>32423331</td><td><span>2016.6.6 </span><br><span>14：21</span></td>
                                                <td><span>棉花糖KTV</span><br><span>(朝阳路)</span></td>
                                                <td>棉花糖KTV（231313123）</td><td>5</td>
                                                <td>3</td><td>233</td><td>33</td><td>22</td>
                                                <td>2</td><td>¥476</td><td>待结算</td>
                                                <td>
                                                    <input type="button" class="btn btn-xs btn-warning select-btn Mod-7" value="设为转账">
                                                    <input type="button" class="btn btn-xs btn-danger select-btn Mod-7" value="设为挂起">
                                                </td>
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
                    <div class="tab-pane fade in active scanOrder-tabBorder" id="tab3">
                        <div class="merchant_management-table">
                            <div class="merchant_management-addButton">
                                <button class="ModButton ModButton_ordinary ModRadius">导出表格</button>
                            </div>
                            <div class="toggleTable">
                                <div class="tab-content">
                                    <div class="tab-pane fade in active">
                                        <table class="table table-bordered table-hover">
                                            <thead>
                                            <tr class="active">
                                                <th>结算单编号</th><th>交易日期</th><th>门店ID</th><th>门店名称</th><th>乐加核销笔数</th><th>普通核销笔数</th><th>乐加应入账</th>
                                                <th>乐加佣金</th><th>普通应入账</th><th>普通手续费</th><th>总入账</th><th>状态</th>
                                                <th>操作</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>32423331</td><td><span>2016.6.6 </span><br><span>14：21</span></td>
                                                <td><span>棉花糖KTV</span><br><span>(朝阳路)</span></td>
                                                <td>棉花糖KTV（231313123）</td><td>5</td>
                                                <td>3</td><td>233</td><td>33</td><td>22</td>
                                                <td>2</td><td>¥476</td><td>待结算</td>
                                                <td>
                                                    <input type="button" class="btn btn-xs btn-warning select-btn Mod-7" value="设为转账">
                                                    <input type="button" class="btn btn-xs btn-danger select-btn Mod-7" value="设为挂起">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>32423331</td><td><span>2016.6.6 </span><br><span>14：21</span></td>
                                                <td><span>棉花糖KTV</span><br><span>(朝阳路)</span></td>
                                                <td>棉花糖KTV（231313123）</td><td>5</td>
                                                <td>3</td><td>233</td><td>33</td><td>22</td>
                                                <td>2</td><td>¥476</td><td>待结算</td>
                                                <td>
                                                    <input type="button" class="btn btn-xs btn-warning select-btn Mod-7" value="设为转账">
                                                    <input type="button" class="btn btn-xs btn-danger select-btn Mod-7" value="设为挂起">
                                                </td>
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
            </div>
        </div>
    </div>
</div>



<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<script>

</script>
</body>
</html>
