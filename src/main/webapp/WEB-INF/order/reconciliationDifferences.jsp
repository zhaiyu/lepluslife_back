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
        <div class="btn btn-primary createLocation" style="margin: 20px 0 0px 1%;" onclick="pageReturn()">返回POS订单列表</div>
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
<script>
    var offset=1;               // 当前页数
    var totalPage=0;            // 总页数
    var totalCount=0;           // 总记录数
    var logContent = document.getElementById("orderContent");

    $(function() {
        initPageCount();        // 初始化分页
        getErrorLogByAjax();    // 加载数据
    });

    function initPageCount() {
        $.ajax({
            type:"get",
            url:"/manage/pos_errorlog/pagecount",
            async:false,        //  同步加载（success后才执行其他的脚本）
            cotentType:"application/json",
            success:function(page){
                totalPage = page.data.totalPage;
                totalCount = page.data.totalCount;
                $("#totalElements").text(totalCount);
            }
        });
    }

    function getErrorLogByAjax() {
        logContent.innerHTML= "";
        $.ajax({
            type:"get",
            url:"/manage/pos_errorlog/"+offset,
            cotentType:"application/json",
            success:function(content){
                var contentStr = "";
                var errBills = content.data.errorBills;
                var errLogs = totalCount = content.data.errorLogs;
                for(var i=0;i<errBills.length;i++) {
                    contentStr+="<tr>";
                    contentStr+="<td>"+errBills[i].filename+"</td>";
                    contentStr+="<td>"+new Date(errBills[i].createDate).format('yyyy.MM.dd HH:mm:ss')+"</td>";
                    contentStr+="<td>";
                    for(var x=0;x<errLogs[i].length;x++) {
                        contentStr+="<h5>订单号："+errLogs[i][x].orderSid+"&nbsp;&nbsp;(乐加 : ";
                        // 乐加
                        if(errLogs[i][x].localState==1) {
                            contentStr+=" 已支付&nbsp;&nbsp;";
                        }else if(errLogs[i][x].localState==0) {
                            contentStr+=" 未支付&nbsp;&nbsp;";
                        }else {
                            contentStr+=" 不存在&nbsp;&nbsp;";
                        }
                        contentStr+="  实付 "+errLogs[i][x].localTruePay;
                        contentStr+="  手续费 "+errLogs[i][x].localCommission;
                        //  掌富
                        contentStr+=" &nbsp;/&nbsp;&nbsp;掌富：";
                        if(errLogs[i][x].remoteState==1) {
                            contentStr+="已支付&nbsp;&nbsp;";
                        }else if(errLogs[i][x].remoteState==0){
                            contentStr+="未支付&nbsp;&nbsp;";
                        }else {
                            contentStr+="不存在&nbsp;&nbsp;";
                        }
                        contentStr+=" 实付 "+errLogs[i][x].remoteTruePay;
                        contentStr+=" 手续费 "+errLogs[i][x].remoteCommission;
                        contentStr+=" )</h5>";
                    }
                    contentStr+="</td>";
                    contentStr+="</tr>";
                 }
                logContent.innerHTML+=contentStr;
            }
        });
        initPage(offset,totalPage);
    }

    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                offset = p;
                init1 = 0;
                getErrorLogByAjax();
            }
        });
    }

    function pageReturn() {
        location.href = "/manage/pos_order";
    }

    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        var week = {
            "0": "\u65e5",
            "1": "\u4e00",
            "2": "\u4e8c",
            "3": "\u4e09",
            "4": "\u56db",
            "5": "\u4e94",
            "6": "\u516d"
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        if (/(E+)/.test(fmt)) {
            fmt =
                    fmt.replace(RegExp.$1,
                            ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468")
                                    : "") + week[this.getDay() + ""]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt =
                        fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr((""
                        + o[k]).length)));
            }
        }
        return fmt;
    }
</script>
</body>
</html>

