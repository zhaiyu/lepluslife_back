<%--
  Created by IntelliJ IDEA.
  User: lss
  Date: 16/11/20
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
        #myModal .col-sm-4 {
            margin-top: 7px;
        }
        #myModal label{
            color: #4f4f4f;
            font-size: 16px;
            font-weight: normal;
        }
        .btn-div{
            width: 160px;
            margin: 0 auto;
        }

    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
</head>

<body>

<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-warning">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">立即充值</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="rechargeScorea" class="col-sm-3 col-sm-offset-1 control-label">充值红包:</label>
                        <div class="col-sm-4">
                            <span id="rechargeScorea"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="rechargeScoreb" class="col-sm-3 col-sm-offset-1 control-label">充值积分:</label>
                        <div class="col-sm-4">
                            <span id="rechargeScoreb"></span>
                            <input type="hidden" id="fillId">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="rechargeRemark" class="col-sm-3 col-sm-offset-1 control-label">备注:</label>
                        <div class="col-sm-4">
                            <textarea id="rechargeRemark" rows="4" cols="40" ></textarea>
                        </div>

                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <div class="clearfix btn-div">
                    <button type="button" class="btn btn-warning pull-left" ng-class="{disabled: srgl.thirNum<=200}" onclick="rechargeSave()">确认</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal2">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-warning">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">驳回处理</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <h4 class="text-center">将该充值申请驳回</h4>

                    <div class="form-group">
                        <label for="rejectRemark" class="col-sm-3 col-sm-offset-1 control-label">备注:</label>
                        <div class="col-sm-4">
                            <textarea id="rejectRemark" rows="4" cols="40" ></textarea>
                        </div>

                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <div class="clearfix btn-div">
                    <button type="button" class="btn btn-warning pull-left" ng-class="{disabled: srgl.thirNum<=200}" onclick="rejectSave()">确认</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="topIframe">
    <%@include file="../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main">
            <h3 style="font-weight:bold">
                充值申请
            </h3>
            <div class="container-fluid">
                <div class="row" style="margin-top: 30px">
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>申请合伙人</th>
                                <th>充值红包</th>
                                <th>充值积分</th>
                                <th>联系电话</th>
                                <th>申请提交时间</th>
                                <th>状态</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="fillingBillContent">
                            </tbody>
                            <tbody id="temporary">
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
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var partnerRechargeCriteria = {};
    var flag = true;
    var init1 = 0;
    var fillingBillContent = document.getElementById("fillingBillContent");
    var temporaryContent = document.getElementById("temporary");
    var temporary = document.getElementById("temporary");
    $(function () {
        partnerRechargeCriteria.offset = 1;
        getPartnerRechargeByAjax(partnerRechargeCriteria);
    });
    function getPartnerRechargeByAjax(partnerRechargeCriteria) {
        fillingBillContent.innerHTML = "";
        $.ajax({
            type: "post",
            url: "/manage/partner/recharge/findByPage",
            async: false,
            data: JSON.stringify(partnerRechargeCriteria),
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
                    initPage(partnerRechargeCriteria.offset, totalPage);
                }
                if (init1) {
                    initPage(1, totalPage);
                }
                for (i = 0; i < content.length; i++) {
                    var contentStr = '<tr><td>' + content[i].partner.name + '</td>';
                    contentStr+='<td>' + content[i].scorea/100 + '</td>';
                    contentStr+='<td>' + content[i].scoreb + '</td>';
                    contentStr+='<td>' + content[i].partnerPhoneNumber + '</td>';
                    contentStr+='<td>' +new Date(content[i].createTime).format('yyyy-MM-dd HH:mm:ss') + '</td>';
                    if(content[i].rechargeState=="0"){
                        contentStr+='<td><span>未处理</span></td>';
                    }else if(content[i].rechargeState=="1"){
                        contentStr+='<td><span>已审核</span></td>';
                    }else if(content[i].rechargeState=="2"){
                        contentStr+='<td><span>驳回</span></td>';
                    }
                    if(content[i].remark==null){
                        contentStr+='<td><span></span></td>';
                    }else{
                        contentStr+='<td>' + content[i].remark + '</td>';
                    }
                    if(content[i].rechargeState=="0"){
                        contentStr +=
                                '<td><input type="hidden" class="id-hidden" value="' + content[i].id+'">'+
                                '<button class="btn btn-primary goToFilling">立即充值</button>' +
                                '<button class="btn btn-primary rejectFilling">驳回</button>'+'</td></tr>';
                    }
                    fillingBillContent.innerHTML += contentStr;
                }
                $(".goToFilling").each(function (i) {
                    $(".goToFilling").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        $.ajax({
                            type: "post",
                            url: "/manage/partner/recharge/findById",
                            async: false,
                            data: '{"fillId":'+id+'}',
                            contentType: "application/json",
                            success: function (data) {
                                $('#rechargeScorea').text("￥"+data.data.scorea/100);
                                $('#rechargeScoreb').text(data.data.scoreb);
                                $('#fillId').val(id);
                            }});

                        $('#myModal').modal('show')
                    });
                });
                $(".rejectFilling").each(function (i) {
                    $(".rejectFilling").eq(i).bind("click", function () {
                        var id = $(this).parent().find(".id-hidden").val();
                        temporaryContent.innerHTML="<input id='rjId' value='"+id+"'>";
                        $('#myModal2').modal("toggle");




                    });
                });
            }
        });
    }
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
            pageCount: totalPage,
            current: page,
            backFn: function (p) {
                partnerRechargeCriteria.offset = p;
                init1 = 0;
                getPartnerRechargeByAjax(partnerRechargeCriteria);
            }
        });
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
            temp.appendChild(opt);
        }
        document.body.appendChild(temp);
        temp.submit();
        return temp;
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
    function rechargeSave (){
      var  fillId=$("#fillId").val();
      var rechargeRemark=$("#rechargeRemark").val();
        //请求到后台
        $.ajax({
            type: "post",
            url: "/manage/partner/recharge/save",
            async: false,
            data: '{"fillId":'+fillId+',"rechargeRemark":'+rechargeRemark+'}',
            contentType: "application/json",
            success: function (data) {
            }});
        $('#myModal').modal("toggle");
        $("#rechargeRemark").val("");
        getPartnerRechargeByAjax(partnerRechargeCriteria);
    }
    function rejectSave() {
        var rjId=$("#rjId").val();
        var rejectRemark=$("#rejectRemark").val();
        $.ajax({
            type: "post",
            url: "/manage/partner/reject/save",
            async: false,
            data: '{"rjId":'+rjId+',"rejectRemark":'+rejectRemark+'}',
            contentType: "application/json",
            success: function (data) {
            }});
        $('#myModal2').modal("toggle");
        temporaryContent.innerHTML="";
        $("#rejectRemark").val("");
        getPartnerRechargeByAjax(partnerRechargeCriteria);
    }
</script>
</body>
</html>