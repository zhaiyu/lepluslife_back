<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/5/6
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
                <h3 style="text-align:center">${time}日红包明细记录</h3>
            <div class="container-fluid">
                <button class="btn btn-primary" style="margin-top: 24px"
                        onclick="goback()">返回
                </button>
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-3">
                        <label for="changeProject">变更项目</label>
                        <select class="form-control" id="changeProject">
                            <option value=""></option>
                            <option value="0">关注送红包</option>
                            <option value="1">线上返还</option>
                            <option value="2">线上消费</option>
                            <option value="3">线下消费</option>
                            <option value="4">线下返还</option>
                            <option value="5">活动返还</option>
                            <option value="6">运动</option>
                            <option value="7">摇一摇</option>
                            <option value="8">APP分享</option>
                            <option value="9">线下支付完成页注册会员</option>
                        </select></div>
                    <button class="btn btn-primary" style="margin-top: 24px"
                            onclick="searchScoreAAccountDetailByCriteria()">查询
                    </button>
                </div>
            </div>

            <div id="myTabContent" class="tab-content">
                <div class="tab-pane fade in active" id="tab1">
                    <p id="totalData"></p>
                    <table class="table table-bordered table-hover">

                        <thead>
                        <tr class="active">
                            <th>红包变更时间</th>
                            <th>变更项目</th>
                            <th>消费者使用红包</th>
                            <th>积分客发放红包</th>
                            <th>佣金收入</th>
                            <th>分润后积分客收入</th>
                        </tr>
                        </thead>
                        <tbody id="scoreAAccountDetailContent">
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
        var changeDate = t.replace(/-/g, "/");
        scoreDetailCriteria.dateCreated = changeDate;
    }else{
        scoreDetailCriteria.dateCreated = null;
    }
    var flag = true;
    var init1 = 0;
    var scoreAAccountDetailContent = document.getElementById("scoreAAccountDetailContent");
    $(function () {

        scoreDetailCriteria.offset = 1;
        getScoreAAccountDetailByAjax(scoreDetailCriteria);
    });
    function getScoreAAccountDetailByAjax(scoreDetailCriteria) {
        scoreAAccountDetailContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/scoreAAccountDetail",
                   async: false,
                   data: JSON.stringify(scoreDetailCriteria),
                   contentType: "application/json",
                   success: function (data) {

                       var page = data.data;
                       var content = page.content;
                       var totalPage = page.totalPages;
                       var useScoreA=page.useScoreA/100;
                       var issuedScoreA=page.issuedScoreA/100;
                       var commissionIncome=page.commissionIncome/100;
                       var jfkShare=page.jfkShare/100;
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
                       var scoreAAccountDetailContent = document.getElementById("scoreAAccountDetailContent");
                       var totalData=document.getElementById("totalData");
                       var totalDataStr = '';
                       for ( i = 0; i < content.length; i++) {
                           var contentStr = '';
                           contentStr +=
                           '<td><span>'
                           + new Date(content[i].changeDate).format('yyyy-MM-dd HH-mm-ss')
                           + '</span></td>';
                              if(content[i].changeProject!=null){
                                  if(content[i].changeProject==0){
                                      contentStr += '<td>' +'关注送红包'+'('+content[i].serialNumber+')'+ '</td>';
                                  }
                                  if(content[i].changeProject==1){
                                      contentStr += '<td>' +'线上返还'+'('+content[i].serialNumber+')'+ '</td>';
                                  }
                                  if(content[i].changeProject==2){
                                      contentStr += '<td>' +'线上消费'+ '('+content[i].serialNumber+')'+'</td>';
                                  }
                                  if(content[i].changeProject==3){
                                      contentStr += '<td>' +'线下消费'+'('+content[i].serialNumber+')'+ '</td>';
                                  }
                                  if(content[i].changeProject==4){
                                      contentStr += '<td>' +'线下返还'+'('+content[i].serialNumber+')'+ '</td>';
                                  }
                                  if(content[i].changeProject==5){
                                      contentStr += '<td>' +'活动返还'+'('+content[i].serialNumber+')'+ '</td>';
                                  }
                                  if(content[i].changeProject==6){
                                      contentStr += '<td>' +'运动'+'('+content[i].serialNumber+')'+ '</td>';
                                  }
                                  if(content[i].changeProject==7){
                                      contentStr += '<td>' +'摇一摇'+'('+content[i].serialNumber+')'+ '</td>';
                                  }
                                  if(content[i].changeProject==8){
                                      contentStr += '<td>' +'APP分享'+'('+content[i].serialNumber+')'+ '</td>';
                                  }
                                  if(content[i].changeProject==9){
                                      contentStr += '<td>' +'线下支付完成页注册会员'+ '('+content[i].serialNumber+')'+'</td>';
                                  }
                              }else{
                                  contentStr += '<td>' +content[i].operate+'('+content[i].serialNumber+')'+ '</td>';
                              }
                              if(content[i].useScoreA!=null){
                                  contentStr += '<td>' +  '￥'+content[i].useScoreA / 100 + '</td>';
                              }else{
                                  contentStr += '<td>' +  '￥'+'0'+ '</td>';
                              }
                              if(content[i].issuedScoreA!=null){
                                   contentStr += '<td>' +  '￥'+content[i].issuedScoreA / 100 + '</td>';
                              }else{
                                   contentStr += '<td>' +  '￥'+'0'+ '</td>';
                              }
                               if(content[i].commissionIncome!=null){
                               contentStr += '<td>' +  '￥'+content[i].commissionIncome / 100 + '</td>';
                              }else{
                               contentStr += '<td>' +  '￥'+'0'+ '</td>';
                              }
                              if(content[i].jfkShare!=null){
                               contentStr += '<td>' +  '￥'+content[i].jfkShare / 100 + '</td>';
                              }else{
                               contentStr += '<td>' +  '￥'+'0'+ '</td>';
                              }

                           scoreAAccountDetailContent.innerHTML += contentStr;
                       }
                       totalDataStr='筛选条件下使用红包￥'+useScoreA+',累计发放红包￥'+issuedScoreA+',累计佣金收入￥'+commissionIncome+',分润后积分客累计收入￥'+jfkShare;
                       totalData.innerHTML='';
                       totalData.innerHTML+= totalDataStr;
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
                                             getScoreAAccountDetailByAjax(scoreDetailCriteria);
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


    function searchScoreAAccountDetailByCriteria() {
        scoreDetailCriteria.offset = 1;
        init1 = 1;
        if ($("#changeProject").val() != "") {
            scoreDetailCriteria.origin = $("#changeProject").val();
        } else {
            scoreDetailCriteria.origin = null;
        }

        getScoreAAccountDetailByAjax(scoreDetailCriteria);
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

        if (scoreDetailCriteria.dateCreated == null) {
            scoreDetailCriteria.dateCreated = null;
        }
        if (scoreDetailCriteria.origin == null) {
            scoreDetailCriteria.origin = null;
        }
        post("/manage/scoreAAccountDetail/export", scoreDetailCriteria);
    }
    function goback(){
            location.href = "/manage/scoreAAccountPage";
    }
</script>
</body>
</html>