<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/1
  Time: 上午11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/commen.jsp" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>乐+生活 后台模板管理系统</title>
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <style>
        .main .thumbnail{
            padding: 50px 0;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
</head>

<body>
<div id="topIframe">
    <%@include file="common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main" >
            <div class="container">
                <div class="row" style="margin-top: 100px">
                    <div class="col-xs-6 col-sm-3"></div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">导流订单数量</h4>
                                <p class="text-center h3"> ${data['shareOrderCount']}</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">导流总金额</h4>
                                <p class="text-center h3">￥ ${data['shareOrderTotal_price']/100}</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">分润总金额</h4>
                                <p class="text-center h3">￥ ${data['share']/100}</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">会员数量</h4>
                                <p class="text-center h3"> ${data['membersCount']}</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">联盟商户数量</h4>
                                <p class="text-center h3"> ${data['unionMerchantCount']}</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">总销售额</h4>
                                <p class="text-center h3">￥ ${data['turnover']/100}</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">订单量</h4>
                                <p class="text-center h3">${data['orderCount']}</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">发放积分数</h4>
                                <p class="text-center h3">${data['scoreb']}</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">发放红包数</h4>
                                <p class="text-center h3">￥${data['rebate']/100}</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">用户持有红包数</h4>
                                <p class="text-center h3">￥${data['lejiaUserRebate']/100}</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="text-center">用户持有积分数</h4>
                                <p class="text-center h3">${data['lejiaUserScoreb']}</p>
                            </div>
                        </div>
                    </div>


                    <div class="col-xs-6 col-sm-3"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="common/bottom.jsp" %>
</div>

</body>
</html>

