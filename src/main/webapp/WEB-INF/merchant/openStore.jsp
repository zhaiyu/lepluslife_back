<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/6/7
  Time: 下午5:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<!DOCTYPE>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <title>乐+生活 后台模板管理系统</title>
  <link type="text/css" rel="stylesheet" href="${resourceUrl}/commonCss.css"/>
  <link type="text/css" rel="stylesheet" href="${resourceUrl}/checkshop.css"/>


  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <![endif]-->
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
    <p>
      <button onclick="javascript:history.go(-1);">返回我的店铺</button>
      创建乐店信息
    </p>
    <div>
      <div>店铺所在城市</div>
      <input type="text" id="city" class="check" value="${merchant.name}" style="border: 0;" readonly="readonly" />
    </div>
    <div>
      <div>所在区域</div>
      <input type="text" id="area" class="check" value="${merchant.area.name}" style="border: 0;" readonly="readonly" />
    </div>
    <div>
      <div>详细地址</div>
      <input type="text" id="address" class="check" value="${merchant.location}" style="border: 0;" readonly="readonly" />
    </div>
    <div>
      <div>店铺类型</div>
      <input type="text" id="type" class="check" value="${merchant.merchantType.name}" style="border: 0;" readonly="readonly" />
    </div>
    <div>
      <div>服务电话（选填）</div>
      <input type="text" id="phone" value="${merchant.serviceCall}" class="check" placeholder="请输入服务电话" />
    </div>
    <div>
      <div>营业时间</div>
      <input type="text" id="stimeHour" style="width: 15%;" name="datetime" class="check" placeholder="开始（时）" />
      <div style="width: 3%;text-align: center;line-height: 25px;">:</div>
      <input type="text" id="stimeMinute" style="width: 15%;" name="datetime" class="check" placeholder="开始（分）" />
      <div style="width: 4%;text-align: center;line-height: 25px;">-</div>
      <input type="text" id="etimeHour" style="width: 15%;" name="datetime" class="check" placeholder="结束（时）" />
      <div style="width: 3%;text-align: center;line-height: 25px;">:</div>
      <input type="text" id="etimeMinute" style="width: 15%;" name="datetime" class="check" placeholder="结束（分）" />
    </div>
    <div>
      <div>经度</div>
      <input type="text" value="${merchant.lng}" id="lng" class="check" />
    </div>
    <div>
      <div>纬度</div>
      <input type="text" value="${merchant.lat}" id="lat" class="check" />
    </div>

    <div>
      <button>保存</button>
      <button>取消</button>
    </div>
  </div>
</div>
<div id="bottomIframe">
  <%@include file="../common/bottom.jsp" %>
</div>
</body>
</html>

