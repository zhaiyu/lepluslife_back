<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 16-11-16
  Time: 上午10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../commen.jsp" %>
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
        thead th,tbody td{text-align: center;}
        table tr td img{width: 80px;height:80px;}
        .table>thead>tr>td, .table>thead>tr>th{line-height: 40px;}
        .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th{line-height: 60px;}


        .modal-body .main{
            width: 345px;
            height: 480px;
            margin: 0 auto;
            background: url("${resourceUrl}/images/lefuma.png") no-repeat;
            background-size: 100% 100%;
            position: relative;
        }
        .modal-body #myShowImage{
            width: 345px;
            height: 480px;
            position: absolute;
            top: 20px;
            left: 0;
            right: 0;
            margin: auto;
            display: none;
        }
        .modal-body .main .rvCode{
            width: 230px;
            height: 230px;
            position: absolute;
            top: 120px;
            left: 0;
            right: 0;
            margin:auto;
        }
        .modal-body .main .shopName{
            text-align: center;
            font-size: 24px;
            color: #fff;
            position: absolute;
            top: 365px;
            left: 0;
            right: 0;
            margin: auto;
            font-family: Arial;
        }
    </style>
    <!--<script src="js/html2canvas.js" type="text/javascript" charset="utf-8"></script>-->
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>

    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script src="${resourceUrl}/js/daterangepicker.js"></script>
    <script src="${resourceUrl}/js/moment.min.js"></script>
    <script src="${resourceUrl}/js/jquery.page.js"></script>
    <script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script>

    <!--layer-->
    <script type="text/javascript" src="${resourceUrl}/js/layer/laydate/laydate.js"></script>
</head>

<body>
<div id="topIframe">
    <%@include file="../../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="main" >
            <div class="container-fluid" style="padding-top: 20px">

                <ul id="myTab" class="nav nav-tabs">
                    <li class="w i"><a href="#ing" data-toggle="tab">秒杀时段</a></li>
                </ul>
                <div style="text-align: left;margin: 10px 0 0 0">
                    <button class="btn btn-primary createWarn">新建秒杀时段</button>
                </div>
                <div id="myTabContent" class="tab-content" style="margin: 10px 0 0 0">
                    <div class="tab-pane fade in active" id="ing">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>序号</th>
                                <th>时段名称</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>时段限购</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="productSecKillContent">

                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="tcdPageCode" style="display: inline;"></div>
                <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../../common/bottom.jsp" %>
</div>

<!--停用提示框-->

<!--添加或编辑 秒杀时段提示框-->
<div class="modal" id="createWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">编辑秒杀时段</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">

                    <input id="pskt_id" name="pskt_id" type="hidden" class="o-input" value=""/>

                    <div class="form-group">
                        <label for="secKillDate" class="col-sm-3 control-label">秒杀日期</label>
                        <div class="col-sm-6">
                            <%--<input name="secKillDate" type="text" class="form-control o-input" id="secKillDate" placeholder="">--%>
                            <input id="secKillDate" class="laydate-icon layer-date ModRadius-right" placeholder="">

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="startEndTime" class="col-sm-3 control-label">选择时间</label>
                        <div class="col-sm-6">
                            <input name="startEndTime" type="text" class="form-control o-input"
                                   id="startEndTime" placeholder="">
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label for="endTime" class="col-sm-3 control-label">结束时间</label>--%>
                        <%--<div class="col-sm-6">--%>
                            <%--<input name="endTime" type="text" class="form-control o-input"--%>
                                   <%--id="endTime" placeholder="">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label for="timeLimitNumber" class="col-sm-3 control-label">时段限购</label>
                        <div class="col-sm-6">
                            <input name="timeLimitNumber" type="text" class="form-control o-input"
                                   id="timeLimitNumber" placeholder="单个用户本时段最多可购买商品数量">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="note" class="col-sm-3 control-label">备注信息</label>
                        <div class="col-sm-6">
                            <textarea name="note" class="form-control"
                                      id="note" placeholder="请输入备注信息..."></textarea>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="ceate_seckill_time()">确认</button>
            </div>
        </div>
    </div>
</div>

<script>
    //重置编辑页面
    function resetAll() {
        $('#secKillDate').val('');
        $('#timeLimitNumber').val('');
        $('#note').val('');
        $('#startEndTime').val('');
    }
    $(function () {
        //tab切换
        $('#myTab li:eq(0) a').tab('show');
        //提示框
        $(".deleteWarn").click(function(){
            $("#deleteWarn").modal("toggle");
        });
        //新建秒杀时段 编辑页面
        $(".createWarn").click(function(){
            $("#createWarn").modal("toggle");
            resetAll();
        });

    });


    //    时间选择器
    $(document).ready(function (){
//        $('#secKillDate').daterangepicker({format : 'YYYY-MM-DD'});
//        $('#startEndTime').daterangepicker({format : 'HH:mm:ss'});//DateTimePicker
        $('#startEndTime').daterangepicker({
                                           maxDate : moment(), //最大时间
                                           dateLimit : {
                                               days : 30
                                           }, //起止时间的最大间隔
                                           showDropdowns : true,
                                           showWeekNumbers : false, //是否显示第几周
                                           timePicker : true, //是否显示小时和分钟
                                           timePickerIncrement : 1, //时间的增量，单位为分钟
                                           timePicker12Hour : false, //是否使用12小时制来显示时间
                                           ranges : {},
                                           opens : 'right', //日期选择框的弹出位置
                                           buttonClasses : [ 'btn btn-default' ],
                                           applyClass : 'btn-small btn-primary blue',
                                           cancelClass : 'btn-small',
                                           format : 'HH:mm:ss', //控件中from和to 显示的日期格式
                                           separator : '-',
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
            $('#startEndTime').html(start.format('HH:mm:ss') + '-' + end.format('HH:mm:ss'));
        });

    })
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

    //日期选择
    var choose_psk_date = {
        elem: '#secKillDate',
        format: 'YYYY-MM-DD',
        max: '2099-06-16', //最大日期
        istime: false,
        istoday: false,
        event: 'click',
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
//            chooseSecKillDate222(datas);
        }
    };
    laydate(choose_psk_date);



    //查询 秒杀时段
    var productSecKillCriteria = {};
    var flag = true;
    var init1 = 0;
    var productSecKillContent = document.getElementById("productSecKillContent");
    productSecKillCriteria.offset = 1;
    function getProductSecKilListByType(criteria) {
        productSecKillContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/productSecKill_time/ajaxList",
                   async: false,
                   data: JSON.stringify(criteria),
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
                           initPage(criteria.offset, totalPage);
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }

                       for (var i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].id + '</td>';
                           contentStr += '<td>' + content[i].secKillDateName + '</td>';
                           if(content[i].startTime!=null) {
                               contentStr += '<td>' + content[i].startTime + '</td>';
                           }else{
                               contentStr += '<td></td>';
                           }
                           if(content[i].endTime!=null) {
                               contentStr += '<td>' + content[i].endTime + '</td>';
                           }else{
                               contentStr += '<td></td>';
                           }
                           contentStr += '<td>' + content[i].timeLimitNumber + '</td>';
                           contentStr += '<td>' + content[i].note + '</td>';

                           contentStr += '<td><input type="hidden" class="id-hidden" value="' + content[i].id + '">'
                                        + '<button class="btn btn-primary edit_pskt">编辑</button>'
                                        + '<button class="btn btn-primary deleteWarn222">删除</button></td></tr>';
                           productSecKillContent.innerHTML += contentStr;
                       }

                       $(".deleteWarn222").each(function (i) {
                           $(".deleteWarn222").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();

                               if (!confirm("确定删除？")) {
                                   return false;
                               }
                               $.ajax({
                                          type: "post",
                                          url: "/manage/productSecKill_time/delete/"+id,
                                          success: function (data) {
                                              alert(data.msg);
                                              getProductSecKilListByType(productSecKillCriteria);
                                          }
                                      });

                           });
                       });

                       $(".edit_pskt").each(function (i) {
                           $(".edit_pskt").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $.ajax({
                                          type: "get",
                                          url: "/manage/productSecKill_time/find/" + id,
                                          contentType: "application/json",
                                          success: function (data) {
                                              if (data.status == 200) {
                                                  var pskt = data.data;
                                                  var secKillDate = pskt.secKillDate;
                                                  var startTime = pskt.startTime;
                                                  var endTime = pskt.endTime;
                                                  var timeLimitNumber = pskt.timeLimitNumber;
                                                  var note = pskt.note;
                                                  $('#secKillDate').val(secKillDate);
                                                  $('#startEndTime').val(startTime+'-'+endTime);
                                                  $('#timeLimitNumber').val(timeLimitNumber);
                                                  $('#note').val(note);

                                                  $('#pskt_id').val(pskt.id);

                                                  $("#createWarn").modal("show");
//                                                  $("#createWarn").modal("toggle");

                                              } else {
                                                  alert("服务器异常");
                                              }
                                          }
                                      });

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
                                             productSecKillCriteria.offset = p;
                                             init1 = 0;
                                             getProductSecKilListByType(productSecKillCriteria);
                                         }
                                     });
    }

    getProductSecKilListByType(productSecKillCriteria);
    resetAll();
</script>
<script>


    //保存 秒杀时段
    function ceate_seckill_time() {
        var productSecKillTime = {};
        var secKillDate = $('#secKillDate').val();
        var timeLimitNumber = $('#timeLimitNumber').val();
        var note = $('#note').val();
        if (secKillDate == null || secKillDate == "" || secKillDate.length<10) {
            alert('"秒杀日期"错误!!');
            return false;
        }
        var secKillDate2 = secKillDate.substring(0,10);
        var secKillDateName2 = secKillDate.substring(8,10)+'日';

        var dateStr = $('#startEndTime').val().split("-");
        if (dateStr != null && dateStr != '') {
            var startTime = dateStr[0];
            var endTime = dateStr[1];
            productSecKillTime.startTime = startTime;
            productSecKillTime.endTime = endTime;
        }else{
            alert('"选择时间"错误!!');
            return
        }

        if (timeLimitNumber == null || timeLimitNumber == "") {
            alert('"时段限购"错误!!');
            return false;
        }
        if (note == null || note == "") {
            alert('"备注信息"有误!!');
            return false;
        }
        productSecKillTime.secKillDateName = secKillDateName2;
        productSecKillTime.secKillDate = secKillDate2;
        productSecKillTime.timeLimitNumber = timeLimitNumber;
        productSecKillTime.note = note;
        productSecKillTime.id = $('#pskt_id').val();
        $.ajax({
                   type: "post",
                   url: "/manage/productSecKill_time/save",
                   contentType: "application/json",
                   data: JSON.stringify(productSecKillTime),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("success");
                           location.href = "/manage/productSecKill_time"
                       } else {
                           alert(data.status);
                       }
                   }
               });

        $("#createWarn").modal("toggle");
//        resetAll();
    }

</script>

</body>
</html>
