<%@include file="../commen.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <style>
        html,body,div,p,form,label,ul,li,dl,dt,dd,ol,img,button,h1,h2,h3,h4,h5,h6,b,em,strong,small{margin:0;padding:0;border:0;list-style:none;font-style:normal;font-weight:normal;}
        dl,dt,dd{display:block;margin:0;padding: 0}
        a{text-decoration:none;}
        body{font-family:Microsoft YaHei,Verdana,Arial,SimSun;color:#666;font-size:14px;background:#f6f6f6; overflow:hidden}
        .block, #block{display:block;}
        .none, #none{display:none;}
        .left_menu{float:left;width:200px;background:#32323a;height:100%;position:absolute;top:0;left:0;overflow:auto;}
        .left_menu ul li {width:200px; display:inline; }
        .left_menu ul li .list-item a{width:230px;padding-left:110px;text-decoration:none;font-size:14px;color:#f5f5f5;line-height:30px;display:block;}
        .left_menu ul li a.noline { border-bottom:none; }
        .left_menu ul li a:hover{ color:#fff; }
        .left_menu ul li a.selected:hover { color:#fff; }
        .left_menu ul li h4 { cursor:pointer; background:url(${resourceUrl}/images/bg1.png) no-repeat 90% 18px; padding-left:10px; text-decoration:none; font-size:14px; color:#f5f5f5; display:block;  line-height:48px; font-weight:normal; }
        .left_menu ul li.noline { border-bottom:none; }
        .left_menu ul li.selected h4 { background-position:270px -45px;background-color:#00a5a5;}
        .left_menu li .list-item { padding:5px 0; position:relative; zoom:1 ;background:#11b6b6;overflow:hidden;}
        .left_menu h4 span{display: block;float: left;width: 35px;height: 26px;margin-right: 10px;padding-right: 10px;background-repeat: no-repeat;margin-top: 12px;}
        .M1 span{background:url(${resourceUrl}/images/ioc.png) 0 -6px;}
        .M2 span{background:url(${resourceUrl}/images/ioc.png) -36px -6px;}
        .M3 span{background:url(${resourceUrl}/images/ioc.png) -72px -40px;}
        .M4 span{background:url(${resourceUrl}/images/ioc.png) -72px -70px;}
        .M5 span{background:url(${resourceUrl}/images/ioc.png) -72px -70px;}
        .M6 span{background:url(${resourceUrl}/images/ioc.png) -72px -70px;}
        .M7 span{background:url(${resourceUrl}/images/ioc.png) -72px -70px;}
        .M8 span{background:url(${resourceUrl}/images/ioc.png) -72px -70px;}
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/menu.js"></script>
<div class="left_menu">
    <ul id="nav_dot">
        <li>
            <h4 class="M1"><span></span>首页</h4>
        </li>
        <li><h4 class="M2"><span></span>商品管理</h4></li>
        <li><h4 class="M3"><span></span>周边商户</h4></li>
        <li><h4 class="M4"><span></span>订单管理</h4></li>
        <li><h4 class="M5"><span></span>专题模块</h4></li>
        <li><h4 class="M6"><span></span>会员管理</h4></li>
        <li><h4 class="M7"><span></span>线下订单</h4></li>
        <li><h4 class="M8"><span></span>财务结算</h4></li>
    </ul>
</div>
<script>navList(12);</script>
<script>
    $(function () {
//        数组
        var htmlArr=['/manage/index','/manage/product','/manage/product','picManger.html','/manage/merchant','/manage/order','/manage/topic','/manage/user','/manage/offLineOrder','/manage/financial'];
        $('.M1').click(function () {
            window.location.href=htmlArr[0];
        });
        $('.M2').click(function () {
            window.location.href=htmlArr[1];
        });
        $('.M3').click(function () {
            window.location.href=htmlArr[4];
        });
        $('.M4').click(function () {
            window.location.href=htmlArr[5];
        });
        $('.M5').click(function () {
            window.location.href=htmlArr[6];
        });
        $('.M6').click(function () {
            window.location.href=htmlArr[7];
        });
        $('.M7').click(function () {
            window.location.href=htmlArr[8];
        });
        $('.M8').click(function () {
            window.location.href=htmlArr[9];
        });
        $('.btn-create').click(function () {
            window.location.href=htmlArr[2];
        });
        $('.picManger').click(function () {
            window.location.href=htmlArr[3];
        });
    })
</script>
