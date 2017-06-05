<%--
  Created by IntelliJ IDEA.
  User: lss
  Date: 17/5/6
  Time: 上午10:02
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
    <style>
        thead th, tbody td {
            text-align: center;
        }
        #myTab {
            margin-bottom: 10px;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
</head>

<body>
<input type="hidden" id="changeDate" value=${time}>
<div id="topIframe">
    <%@include file="../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main">
            <h3 style="text-align:center">${time}金币明细记录</h3>
            <div class="container-fluid">
                <button class="btn btn-primary" style="margin-top: 24px"
                        onclick="goback()">返回
                </button>
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-3">
                        <label for="changeProject">变更项目</label>
                        <select class="form-control" id="changeProject">
                            <option value=""></option>
                            <option value="0">注册有礼</option>
                            <option value="1">线上返还</option>
                            <option value="2">线上消费</option>
                            <option value="3">线下消费</option>
                            <option value="4">线下返还</option>
                            <option value="8">分享得金币</option>
                            <option value="15003">充话费消耗</option>
                        </select></div>
                    <button class="btn btn-primary" style="margin-top: 24px"
                            onclick="searchScoreCDetailByCriteria()">查询
                    </button>
                </div>
            </div>

            <div id="myTabContent" class="tab-content">
                <div class="tab-pane fade in active" id="tab1">
                    <p id="totalData"></p>
                    <table class="table table-bordered table-hover">

                        <thead>
                        <tr class="active">
                            <th>金币变更时间</th>
                            <th>变更项目</th>
                            <th>变更数目</th>
                            <th>对应订单号</th>
                        </tr>
                        </thead>
                        <tbody id="scoreCDetailContent">
                        </tbody>
                    </table>
                </div>
                <div class="tcdPageCode" style="display: inline;">
                </div>
                <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                <button class="btn btn-primary pull-right" style="margin-top: 5px"
                onclick="exportExcel()">导出excel
                </button>
            </div>
        </div>
    </div>
</div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!--删除提示框-->
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <h4>操作确定</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="paid-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var scoreDetailCriteria = {};
    var t=$("#changeDate").val();
    if (t!= null && t!= '') {
        var changeDate = t.substring(0,4)+"/"+t.substring(4,6)+"/"+t.substring(6,8);
        scoreDetailCriteria.dateCreated = changeDate;
    }else{
        scoreDetailCriteria.dateCreated = null;
    }
    var flag = true;
    var init1 = 0;
    var scoreCDetailContent = document.getElementById("scoreCDetailContent");
    $(function () {

        scoreDetailCriteria.offset = 1;
        getScoreCDetailByAjax(scoreDetailCriteria);
    });
    function getScoreCDetailByAjax(scoreDetailCriteria) {
        scoreCDetailContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/scoreC/getScoreCDetailByAjax",
            async: false,
            data: JSON.stringify(scoreDetailCriteria),
            contentType: "application/json",
            success: function (data) {
                var page = data.data;
                var content = page.content;
                var totalPage = page.totalPages;
                $("#totalElements").html(page.totalElements);
                if (totalPage == 0) {
                    totalPage = 1;
                }
                if (flag) {
                    flag = false;
                    initPage(scoreDetailCriteria.offset, totalPage);
                }
                if (init1) {
                    initPage(1, totalPage);
                }
                for ( i = 0; i < content.length; i++) {
                    var contentStr = '<tr>';
                    contentStr +=
                        '<td><span>'
                        + new Date(content[i].dateCreated).format('yyyy-MM-dd HH-mm-ss')
                        + '</span></td>';

                    if(content[i].origin==1){
                        contentStr +=
                            '<td><span>线上返还</span></td>';
                    }else if(content[i].origin==2){
                        contentStr +=
                            '<td><span>线上消费</span></td>';
                    }else if(content[i].origin==3){
                        contentStr +=
                            '<td><span>线下消费</span></td>';
                    }else if(content[i].origin==4){
                        contentStr +=
                            '<td><span>线下返还</span></td>';
                    }else if(content[i].origin==8){
                        contentStr +=
                            '<td><span>分享得金币</span></td>';
                    }
                    else if(content[i].origin==15003){
                        contentStr +=
                            '<td><span>充话费消耗</span></td>';
                    } else if(content[i].origin==0){
                        contentStr +=
                            '<td><span>注册有礼</span></td>';
                    }else{
                        contentStr +=
                            '<td><span>--</span></td>';
                    }
                    contentStr += '<td><span>'
                        +content[i].number/100.0
                        + '</span></td>';
                    if(content[i].orderSid!=null&&content[i].orderSid!=""){
                        contentStr += '<td><span>'
                            +content[i].orderSid
                            + '</span></td>';
                    }else {
                        contentStr +=
                            '<td><span>--</span></td>';
                    }
                    scoreCDetailContent.innerHTML += contentStr;
                }

            }
        });
    }
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                scoreDetailCriteria.offset = p;
                init1 = 0;
                getScoreCDetailByAjax(scoreDetailCriteria);
            }
        });
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


    function searchScoreCDetailByCriteria() {
        scoreDetailCriteria.offset = 1;
        init1 = 1;
        if ($("#changeProject").val() != "") {
            scoreDetailCriteria.origin = $("#changeProject").val();
        } else {
            scoreDetailCriteria.origin = null;
        }

        getScoreCDetailByAjax(scoreDetailCriteria);
    }




    function post(URL, PARAMS) {
        var temp = document.createElement("form");
        temp.action = URL;
        temp.method = "post";
        temp.style.display = "none";
        for (var x in PARAMS) {
            var opt = document.createElement("textarea");
            opt.name = x;
            opt.value = PARAMS[x];
            // alert(opt.name)
            temp.appendChild(opt);
        }
        document.body.appendChild(temp);
        temp.submit();
        return temp;
    }
    function exportExcel() {

        scoreDetailCriteria.offset = 1;
        init1 = 1;
        if ($("#changeProject").val() != "") {
            scoreDetailCriteria.origin = $("#changeProject").val();
        } else {
            scoreDetailCriteria.origin = null;
        }

        post("/manage/scoreC/scoreCDetailExport", scoreDetailCriteria);
    }
    function goback(){
        location.href = "/manage/scoreC/scoreCPage";
    }
</script>
</body>
</html>