<%@include file="../commen.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <meta charset="UTF-8">
    <title>BOTTOM</title>
    <style>
        html,body,div{  margin: 0;  padding: 0;}
        #footer{height:50px;line-height:50px;background:#5e5e5e;width:100%;text-align:center;color:#fbfbfb;}
    </style>
<div id="footer">Copyright© 乐+生活 2016 版权所有</div>

<script>
    if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
        $('#content').css({height:document.body.clientHeight-130});
    }
</script>
