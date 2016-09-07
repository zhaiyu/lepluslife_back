<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/5/19
  Time: 上午10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/commen.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <!--强制以webkit内核来渲染-->
  <meta name="viewport"
        content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
  <title></title>
  <script src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
  <script src="http://www.lepluslife.com/resource/tiegan/js/html2canvas.min.js"></script>
  <style>
    /*all tag*/
    * {
      margin: 0;
      padding: 0;
      border: none;
      font-size: 1.5625vw;
      font-family: "Microsoft YaHei";
    }

    html, body {
      height: 100%;
      overflow: hidden;
      background-color: #f8f8f8;
    }

    .main {
      background: url("${merchant.qrCodePicture}") no-repeat;
      background-size: 100% 100%;
      width: 86.67vw;
      height: 120vw;
      margin: 13.33vw auto 0;
      position: relative;
    }

    .rvCode {
      width: 56vw;
      height: 56vw;
      position: absolute;
      top: 29.33vw;
      left: 0;
      right: 0;
      margin: auto;
      background: url("${resourceUrl}/images/rvCode.png") no-repeat center;
      background-size: 53.33vw 53.33vw;
      background-color: #fff;
    }

    .shopName {
      font-size: 5.333vw;
      color: #fff;
      width: 100%;
      text-align: center;
      position: absolute;
      top: 90.67vw;
    }

    .ttl {
      font-size: 2.933vw;
      color: #7d7d7d;
      position: absolute;
      top: 106vw;
      left: 33.33vw;
    }
  </style>
</head>
<body>
<button>Download Image</button>
<div class="main" id="content">
  <div class="rvCode"></div>
  <p class="shopName">${merchant.name}</p>

  <p class="ttl">由乐＋钱包提供技术服务<br/>客服电话：18710089228</p>
</div>
</body>
<script type="text/javascript">
  var canvas = document.createElement("canvas");
  var ctx = canvas.getContext("2d");

  var ctx = canvas.getContext('2d');

  ctx.beginPath();
  ctx.arc(75,75,50,0,Math.PI*2,true); // Outer circle
  ctx.moveTo(110,75);
  ctx.arc(75,75,35,0,Math.PI,false);   // Mouth (clockwise)
  ctx.moveTo(65,65);
  ctx.arc(60,65,5,0,Math.PI*2,true);  // Left eye
  ctx.moveTo(95,65);
  ctx.arc(90,65,5,0,Math.PI*2,true);  // Right eye
  ctx.stroke();

  document.querySelector("button").addEventListener("click", function() {
    html2canvas(document.querySelector("#content"), {canvas: canvas}).then(function(canvas) {
      var image = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");
      var save_link = document.createElement('a');
      save_link.href = image;
      save_link.download = "1.png";

      var event = document.createEvent('MouseEvents');
      event.initMouseEvent('click', true, false, null, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
      save_link.dispatchEvent(event);
    });
  }, false);

</script>
</html>
