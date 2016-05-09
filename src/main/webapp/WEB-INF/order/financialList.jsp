<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/5/9
  Time: 下午3:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <title>乐+生活 后台模板管理系统</title>
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="css/daterangepicker-bs3.css">
  <link type="text/css" rel="stylesheet" href="css/commonCss.css"/>
  <style>
    thead th,tbody td{text-align: center;}
    #myTab{
      margin-bottom: 10px;
    }
  </style>
  <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
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
        <ul id="myTab" class="nav nav-tabs" style="margin-top: 10px">
          <li><a href="#tab1" data-toggle="tab">待转账</a></li>
          <li class="active"><a href="#tab2" data-toggle="tab">转账记录</a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
          <div class="tab-pane fade in active" id="tab1">
            <div class="row" style="margin-top: 30px">
              <div class="form-group col-md-5">
                <label for="date-end">结算账单时间</label>
                <div id="date-end" class="form-control">
                  <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                  <span id="searchDateRange"></span>
                  <b class="caret"></b>
                </div>
              </div>
              <div class="form-group col-md-3">
                <label for="merchant-info">商户信息</label>
                <input type="text" id="merchant-info" class="form-control" placeholder="请输入商户名称或ID" />
              </div>
              <div class="form-group col-md-1">
                <button class="btn btn-primary" style="margin-top: 24px">查询</button>
              </div>
            </div>
            <table class="table table-bordered table-hover">
              <thead>
              <tr class="active">
                <th>结算单号</th><th>结算日期</th><th>商户ID</th><th>商户名称</th><th>绑定银行卡</th>
                <th>开户行</th><th>收款人</th><th>待转账金额</th><th>预计到账日期</th><th>操作</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>4566665</td><td>2016.3.8</td><td>21444</td><td>棉花糖KTV</td><td>41256354489625</td>
                <td>中国工商银行建安支行</td><td>王尼玛</td><td>563.24</td><td>2016.3.10</td>
                <td><button class="btn btn-primary btn-sm">确认转账</button></td>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="tab-pane fade in active" id="tab2">
            <div class="row" style="margin-top: 30px">
              <div class="form-group col-md-3">
                <label for="date-transferAccounts">转账时间</label>
                <div class='input-group date' id='date-transferAccounts'>
                  <input type='text' class="form-control" />
                  <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
              </div>
              <div class="form-group col-md-3">
                <label for="shop-transferAccounts">转账门店</label>
                <input type="text" id="shop-transferAccounts" class="form-control" placeholder="请输入交易门店" />
              </div>
              <div class="form-group col-md-3">
                <button class="btn btn-primary" style="margin-top: 24px">查询</button>
              </div>
              <div class="form-group col-md-3"></div>
            </div>
            <table class="table table-bordered table-hover">
              <thead>
              <tr class="active">
                <th>转账流水号</th><th>转账时间</th><th>转账商家</th><th>结算单号</th><th>结算日期</th><th>转账金额</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>0001</td><td>2016.5.4 16：23</td><td>棉花糖KTV</td><td>0002</td><td>2016.5.2</td><td>￥568</td>
              </tr>
              </tbody>
            </table>
          </div>
          <button class="btn btn-primary btn-sm">导出表格</button>
          <nav class="pull-right">
            <ul class="pagination pagination-lg">
              <li><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>
              <li><a href="#" class="focusClass">1</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">3</a></li>
              <li><a href="#">4</a></li>
              <li><a href="#">5</a></li>
              <li><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>
            </ul>
          </nav>
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
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
<script src="js/daterangepicker.js"></script>
<script src="js/moment.min.js"></script>
<script>
  $(function () {
//        tab切换
    $('#myTab li:eq(0) a').tab('show');
//        提示框
    $(".deleteWarn").click(function () {
      $("#deleteWarn").modal("toggle");
    });
    $(".createWarn").click(function () {
      $("#createWarn").modal("toggle");
    });
  })
  //    时间选择器
  $(document).ready(function (){
    $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss') + ' - ' + moment().format('YYYY/MM/DD HH:mm:ss'));
    alert($('#date-end span').text());
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
</script>
</body>
</html>

