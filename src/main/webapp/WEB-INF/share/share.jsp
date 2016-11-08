<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/7/19
  Time: 上午9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
  <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
  <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
  <style>thead th,tbody td{text-align: center;}  #myTab{  margin-bottom: 10px;}</style>
  <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
  <script src="${resourceUrl}/js/jquery.page.js"></script>
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
      <div class="container-fluid">
        <div class="row" style="margin-top: 30px">
          <div class="form-group col-md-5">
            <label for="date-end">消费完成时间</label>
            <div id="date-end" class="form-control">
              <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
              <span id="searchDateRange"></span>
              <b class="caret"></b>
            </div>
          </div>
          <div class="form-group col-md-2">
            <label for="customer-ID">消费者ID</label>
            <input type="password" class="form-control" id="customer-ID" placeholder="请输入消费者ID">
          </div>
          <div class="form-group col-md-2">
            <label for="customer-tel">消费者手机号</label>
            <input type="password" class="form-control" id="customer-tel" placeholder="请输入消费者手机号">
          </div>
          <div class="form-group col-md-2">
            <label for="merchant-name">商户名称</label>
            <input type="text" id="merchant-name" class="form-control" placeholder="请输入商户名称" />
          </div>
        </div>
        <div class="row" style="margin-bottom: 30px">
          <div class="form-group col-md-4">
            <label>绑定合伙人名称</label>
            <input type="text" id="bindPartner" class="form-control" placeholder="请输入绑定合伙人名称" />
          </div>
          <div class="form-group col-md-2">
            <label>交易商户名称</label>
            <input type="text" id="tradeMerchant" class="form-control" placeholder="请输入交易商户名称" />
          </div>
          <div class="form-group col-md-3">
            <label>交易商户所在合伙人名称</label>
            <input type="text" id="tradePartner" class="form-control" placeholder="请输入交易商户所在合伙人名称" />
          </div>
          <div class="form-group col-md-2">
            <label>订单号</label>
            <input type="text" id="orderSid" class="form-control" placeholder="请输入订单号" />
          </div>
          <div class="form-group col-md-1">
            <button class="btn btn-primary" style="margin-top: 24px" onclick="searchShareByCriteria()">查询</button>
          </div>
        </div>
        <ul id="myTab" class="nav nav-tabs">
          <li class="active"><a href="#tab1" data-toggle="tab">全部订单分润</a></li>
          <li><a href="#tab1" data-toggle="tab">扫码订单分润</a></li>
          <li><a href="#tab1" data-toggle="tab">POS订单分润</a></li>
          <li><a href="#tab1" data-toggle="tab">APP订单分润</a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
          <div class="tab-pane fade in active" id="tab1">
            <table class="table table-bordered table-hover">
              <thead>
              <tr class="active">
                <th>订单号</th><th>交易完成时间</th><th>消费者信息</th><th>分润金额</th><th>交易商户</th>
                <th>交易商户所在合伙人分润</th><th>交易合伙人管理员分润</th><th>会员绑定商户分润</th>
                <th>会员绑定合伙人分润</th><th>绑定合伙人管理员分润</th><th>积分客分润</th>
              </tr>
              </thead>
              <tbody id="shareContent">
              <tr>
                <td>11111</td>
                <td><span>2016.6.5</span><br><span>05：12</span></td>
                <td><span>18766965842</span><br><span>(55245)</span></td>
                <td>35</td><td>嘀咕</td>
                <td><span>白你妹</span><br><span>（￥25）</span></td>
                <td><span>沈阳分公司</span><br><span>（￥25）</span></td>
                <td>无(0)</td><td>无(0)</td><td>无(0)</td><td>556.99</td>
              </tr>
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
</div><!--删除提示框-->
<div class="modal" id="deleteWarn">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">上架</h4>
      </div>
      <div class="modal-body">
        <h4>确定要删除商品：唐古拉黑糖玛？</h4>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
      </div>
    </div>
  </div>
</div>
<!--添加提示框-->
<div class="modal" id="createWarn">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">上架</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
          <div class="form-group">
            <label for="productNum" class="col-sm-2 control-label">商品序号</label>
            <div class="col-sm-4">
              <input type="number" class="form-control" id="productNum" placeholder="Email">
            </div>
          </div>
          <div class="form-group">
            <label for="productName" class="col-sm-2 control-label">商品名称</label>
            <div class="col-sm-4">
              <input type="text" class="form-control" id="productName" placeholder="请输入商品名称">
            </div>
          </div>
          <div class="form-group">
            <label for="productPic" class="col-sm-2 control-label">商品图片</label>
            <div class="col-sm-4">
              <img src="" alt="...">
              <input type="file" class="form-control" id="productPic">
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
      </div>
    </div>
  </div>
</div>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<!--<script src="js/bootstrap-datetimepicker.min.js"></script>-->
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<!--<script src="js/bootstrap-datetimepicker.zh-CN.js"></script>-->
<script src="${resourceUrl}/js/moment.min.js"></script>
<script>
  var shareCriteria = {};
  var flag = true;
  var init1 = 0;
  var shareContent = document.getElementById("shareContent");
  $(function () {
//        tab切换
    $('#myTab li:eq(0) a').tab('show');
//        提示框
    $(".deleteWarn").click(function(){
      $("#deleteWarn").modal("toggle");
    });
    $(".createWarn").click(function(){
      $("#createWarn").modal("toggle");
    });
    $(document).ready(function (){
      $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss') + ' - ' + moment().format('YYYY/MM/DD HH:mm:ss'));
      $('#date-end').daterangepicker({
                                       maxDate : moment(), //最大时间
                                       dateLimit : {
                                         days : 30
                                       }, //起止时间的最大间隔
                                       showDropdowns : true,
                                       showWeekNumbers : false, //是否显示第几周
                                       timePicker : true, //是否显示小时和分钟
                                       timePickerIncrement : 60, //时间的增量，单位为分钟
                                       timePicker12Hour : false, //是否使用12小时制来显示时间
                                       ranges : {
                                         '最近1小时': [moment().subtract('hours',1), moment()],
                                         '今日': [moment().startOf('day'), moment()],
                                         '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                                         '最近7日': [moment().subtract('days', 6), moment()],
                                         '最近30日': [moment().subtract('days', 29), moment()]
                                       },
                                       opens : 'right', //日期选择框的弹出位置
                                       buttonClasses : [ 'btn btn-default' ],
                                       applyClass : 'btn-small btn-primary blue',
                                       cancelClass : 'btn-small',
                                       format : 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
                                       separator : ' to ',
                                       locale : {
                                         applyLabel : '确定',
                                         cancelLabel : '取消',
                                         fromLabel : '起始时间',
                                         toLabel : '结束时间',
                                         customRangeLabel : '自定义',
                                         daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
                                         monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],
                                         firstDay : 1
                                       }
                                     }, function(start, end, label) {//格式化日期显示框
        $('#date-end span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - ' + end.format('YYYY/MM/DD HH:mm:ss'));
      });
    })
    shareCriteria.offset = 1;

    getOffLineOrderShareByAjax(shareCriteria);
  })
  function getOffLineOrderShareByAjax(shareCriteria) {
    shareContent.innerHTML = "";
    $.ajax({
             type: "post",
             url: "/manage/offLineOrder/share",
             async: false,
             data: JSON.stringify(shareCriteria),
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
                 initPage(shareCriteria.offset, totalPage);
               }
               for (i = 0; i < content.length; i++) {
                 var contentStr = '<tr><td>' + content[i].offLineOrder.orderSid + '</td>';
                 contentStr +=
                 '<td><span>'
                 + new Date(content[i].createDate).format('yyyy-MM-dd')
                 + '</span><br><span>'
                 + new Date(content[i].createDate).format('HH:mm:ss')
                 + '</span></td>';
                 if (content[i].offLineOrder.leJiaUser.phoneNumber != null) {
                   contentStr +=
                   '<td><span>' + content[i].offLineOrder.leJiaUser.phoneNumber
                   + '</span><br><span>('
                   + content[i].offLineOrder.leJiaUser.userSid + ')</span></td>'
                 } else {
                   contentStr +=
                   '<td><span>未绑定手机号</span><br><span>('
                   + content[i].offLineOrder.leJiaUser.userSid + ')</span></td>'
                 }
                 //  contentStr += '<td>' + content[i].offLineOrder.totalPrice / 100 + '</td>'
                 contentStr += '<td>' + content[i].shareMoney / 100 + '</td>'
                 contentStr += '<td>' + content[i].offLineOrder.merchant.name+ '</td>'
                 contentStr +=
                 '<td><span>' + content[i].tradePartner.name
                 + '</span><br><span>('
                 + content[i].toTradePartner/100 + ')</span></td>'
                 contentStr +=
                 '<td><span>' + content[i].tradePartnerManager.name
                 + '</span><br><span>('
                 + content[i].toTradePartnerManager/100 + ')</span></td>'
//                 contentStr += '<td>' + content[i].toTradePartner/100 + '</td>'
//                 contentStr += '<td>' + content[i].toTradePartnerManager / 100 + '</td>'
                 if(content[i].lockMerchant!=null){
                   contentStr +=
                   '<td><span>' + content[i].lockMerchant.name
                   + '</span><br><span>('
                   + content[i].toLockMerchant/100 + ')</span></td>'
                 }else{
                   contentStr +=
                   '<td><span>无</span><br><span>(--)</span></td>'
                 }
                 if(content[i].lockPartner!=null){
                   contentStr +=
                   '<td><span>' + content[i].lockPartner.name
                   + '</span><br><span>('
                   + content[i].toLockPartner/100 + ')</span></td>'
                 }else{
                   contentStr +=
                   '<td><span>无</span><br><span>(--)</span></td>'
                 }
                 if(content[i].lockPartnerManager!=null){
                   contentStr +=
                   '<td><span>' + content[i].lockPartnerManager.name
                   + '</span><br><span>('
                   + content[i].toLockPartnerManager/100 + ')</span></td>'
                 }else{
                   contentStr +=
                   '<td><span>无</span><br><span>(--)</span></td>'
                 }
//                 contentStr += '<td>' + content[i].toLockPartner / 100 + '</td>'
//                 contentStr += '<td>' + content[i].toLockPartnerManager / 100 + '</td>'
                 contentStr += '<td>' + content[i].toLePlusLife / 100 + '</td>'
                 shareContent.innerHTML += contentStr;

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
                                     shareCriteria.offset = p;
                                     init1 = 0;
                                     getOffLineOrderShareByAjax(shareCriteria);
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
  function searchShareByCriteria() {
    shareCriteria.offset = 1;
    init1 = 1;
    var dateStr = $('#date-end span').text().split("-");
    if (dateStr != null && dateStr != '') {
      var startDate = dateStr[0].replace(/-/g, "/");
      var endDate = dateStr[1].replace(/-/g, "/");
      shareCriteria.startDate = startDate;
      shareCriteria.endDate = endDate;
    }

    if ($("#customer-ID").val() != "" && $("#customer-ID").val() != null) {
      shareCriteria.userSid = $("#customer-ID").val();
    } else {
      shareCriteria.userSid = null;
    }
    if ($("#customer-tel").val() != "" && $("#customer-tel").val() != null) {
      shareCriteria.userPhone = $("#customer-tel").val();
    } else {
      shareCriteria.userPhone = null;
    }
    if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
      shareCriteria.bindMerchant = $("#merchant-name").val();
    } else {
      shareCriteria.bindMerchant = null;
    }
    if ($("#bindPartner").val() != "" && $("#bindPartner").val() != null) {
      shareCriteria.bindPartner = $("#bindPartner").val();
    } else {
      shareCriteria.bindPartner = null;
    }
    if ($("#tradeMerchant").val() != "" && $("#tradeMerchant").val() != null) {
      shareCriteria.tradeMerchant = $("#tradeMerchant").val();
    } else {
      shareCriteria.tradeMerchant = null;
    }
    if ($("#tradePartner").val() != "" && $("#tradePartner").val() != null) {
      shareCriteria.tradePartner = $("#tradePartner").val();
    } else {
      shareCriteria.tradePartner = null;
    }
    if ($("#orderSid").val() != "" && $("#orderSid").val() != null) {
      shareCriteria.orderSid = $("#orderSid").val();
    } else {
      shareCriteria.orderSid = null;
    }
    getOffLineOrderShareByAjax(shareCriteria);
  }
</script>



</body>
</html>

