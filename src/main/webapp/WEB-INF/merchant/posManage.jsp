<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/9/5
  Time: 下午4:53
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
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <!--<link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">-->
    <link rel="stylesheet" href="${resourceUrl}/css/daterangepicker-bs3.css">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
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
        <div class="main">
            <div class="container-fluid clearfix">
                <div class="row" style="margin-top: 30px">
                    <div class="form-group col-md-2">
                        <label>商户所在城市</label>
                        <select class="form-control">
                            <option>所在城市（全部）</option>
                            <option>北京</option>
                            <option>鞍山</option>
                            <option>宁波</option>
                        </select>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="date-end">添加时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-2">
                        <label>乐加商户</label>
                        <input type="text" class="form-control" placeholder="请输入商户名称或ID查询">
                    </div>
                    <div class="form-group col-md-2">
                        <label>佣金状态</label>
                        <select class="form-control">
                            <option>佣金状态（全部）</option>
                            <option>已开通</option>
                            <option>未开通</option>
                        </select>
                    </div>
                    <div class="form-group col-md-1">
                        <button class="btn btn-primary" style="margin-top: 24px">查询</button>
                    </div>
                </div>
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr class="active">
                        <th>终端号</th>
                        <th>PSAM卡号</th>
                        <th>乐加商户</th>
                        <th>POS商户号</th>
                        <th>POS注册手机号</th>
                        <th>添加时间</th>
                        <th>佣金状态</th>
                        <th>普通订单流水</th>
                        <th>导流订单流水</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>21323</td>
                        <td>21323</td>
                        <td><span>棉花糖KTV</span><br><span>（231313123）</span></td>
                        <td>2131231321</td>
                        <td>18710089228</td>
                        <td><span>2016.6.6</span><br><span>14:14:15</span></td>
                        <td>已开通（6%）</td>
                        <td>¥520</td>
                        <td>¥5520</td>
                        <td><input type="button" class="btn btn-xs btn-primary"
                                   data-toggle="modal" data-target="#modifyCommissionWarn"
                                   value="修改佣金"></td>
                    </tr>
                    <tr>
                        <td>21323</td>
                        <td>21323</td>
                        <td><span>棉花糖KTV</span><br><span>（231313123）</span></td>
                        <td>2131231321</td>
                        <td>18710089228</td>
                        <td><span>2016.6.6</span><br><span>14:14:15</span></td>
                        <td>已开通（6%）</td>
                        <td>¥520</td>
                        <td>¥5520</td>
                        <td><input type="button" class="btn btn-xs btn-primary"
                                   data-toggle="modal" data-target="#openCommissionWarn"
                                   value="开通佣金"></td>
                    </tr>
                    </tbody>
                </table>
                <nav class="pull-right">
                    <ul class="pagination pagination-lg">
                        <li><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a>
                        </li>
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

<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!--开通佣金提示框-->
<div class="modal fade" id="openCommissionWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">close</span></button>
                <h4 class="modal-title">开通佣金</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-offset-2 control-label">终端号</label>

                        <div class="col-sm-4">
                            <p>131312421</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-offset-2 control-label">佣金比</label>

                        <div class="col-sm-6 form-inline">
                            <input type="text" class="form-control">%
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
<!--修改佣金提示框-->
<div class="modal fade" id="modifyCommissionWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">close</span></button>
                <h4 class="modal-title">修改佣金</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-offset-2 control-label">终端号</label>

                        <div class="col-sm-4">
                            <p>131312421</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-offset-2 control-label">佣金比</label>

                        <div class="col-sm-6 form-inline">
                            <input type="text" class="form-control">%
                        </div>
                    </div>
                    <div class="form-group">
                        <p class="col-sm-offset-2 col-sm-8"><font color="red">注意：中汇实际结算时的佣金比例以三方协议为准，在此修改佣金比例只是为了保证平台记账和中汇记账一致。</font>
                        </p>
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
    $(function () {
//        tab切换
        $('#myTab li:eq(0) a').tab('show');
    });
    //    时间选择器
    $(document).ready(function () {
        $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss') + ' - '
                                 + moment().format('YYYY/MM/DD HH:mm:ss'));
        $('#date-end').daterangepicker({
                                           maxDate: moment(), //最大时间
                                           dateLimit: {
                                               days: 30
                                           }, //起止时间的最大间隔
                                           showDropdowns: true,
                                           showWeekNumbers: false, //是否显示第几周
                                           timePicker: true, //是否显示小时和分钟
                                           timePickerIncrement: 60, //时间的增量，单位为分钟
                                           timePicker12Hour: false, //是否使用12小时制来显示时间
                                           ranges: {
                                               '最近1小时': [moment().subtract('hours', 1), moment()],
                                               '今日': [moment().startOf('day'), moment()],
                                               '昨日': [moment().subtract('days', 1).startOf('day'),
                                                      moment().subtract('days', 1).endOf('day')],
                                               '最近7日': [moment().subtract('days', 6), moment()],
                                               '最近30日': [moment().subtract('days', 29), moment()]
                                           },
                                           opens: 'right', //日期选择框的弹出位置
                                           buttonClasses: ['btn btn-default'],
                                           applyClass: 'btn-small btn-primary blue',
                                           cancelClass: 'btn-small',
                                           format: 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
                                           separator: ' to ',
                                           locale: {
                                               applyLabel: '确定',
                                               cancelLabel: '取消',
                                               fromLabel: '起始时间',
                                               toLabel: '结束时间',
                                               customRangeLabel: '自定义',
                                               daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                                               monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                                                            '七月', '八月', '九月', '十月', '十一月', '十二月'],
                                               firstDay: 1
                                           }
                                       }, function (start, end, label) {//格式化日期显示框
            $('#date-end span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                                     + end.format('YYYY/MM/DD HH:mm:ss'));
        });
    })
</script>


</body>
</html>

