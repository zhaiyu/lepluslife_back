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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <style>thead th, tbody td {
        text-align: center;
    }

    #myTab {
        margin-bottom: 10px;
    }
    /*新增*/
    /*弹窗页面CSS*/
    .w-addPOS {
        width: 95%;
        margin: 0px auto;
        padding:10px 0;
    }
    .w-addPOS > div > div:first-child {
        float: left;
        width: 20%;
        margin-right: 10%;
        margin-top: 10px;
        text-align: right;
        font-size: 14px;
    }
    .w-addPOS > div > div:last-child {
        float: left;
        width: 70%;
    }
    .w-addPOS > div{
        margin: 20px 0;
    }
    .w-addPOS > div > div > div,.w-addPOS > div > div > div > div{
        margin: 5px 0;
    }
    .w-addPOS input {
        display: inline;
    }
    .w-addPOS > div > div:last-child input[type=number] {
        width: 30%;
        margin:0 1%;
    }
    .w-b {
        margin: 0 !important;
    }
    .w-b > div {
        float: left;
        width: 10%;
        padding:1%;
        color: #333;
        text-align: center;
        border:1px solid #ddd;
        cursor: pointer;
        -webkit-border-radius: 5px 0 0 5px;
        -moz-border-radius: 5px 0 0 5px;
        border-radius: 5px 0 0 5px;
    }
    .w-b > div:first-child {
        border-right: 0;
    }
    .w-b > div:last-child {
        border-left: 0;
        -webkit-border-radius:0 5px 5px 0;
        -moz-border-radius:0 5px 5px 0;
        border-radius:0 5px 5px 0;
    }
    .w-bActive {
        background-color: #337ab7;
        border:1px solid #337ab7 !important;
        color: #FFFFFF !important;
    }

    .w-addPOS > div:after,.w-b:after {
        content: '\20';
        display: block;
        clear: both;
    }
    </style>
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
                        <select class="form-control" id="locationCity">
                            <option value="">所在城市（全部）</option>
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
                        <input type="text" id="merchant-name" class="form-control" placeholder="请输入商户名称或ID查询">
                    </div>
                    <div class="form-group col-md-2">
                        <label>佣金状态</label>
                        <select class="form-control" id="commission-state">
                            <option value="">佣金状态（全部）</option>
                            <option value="1">已开通</option>
                            <option value="0">未开通</option>
                        </select>
                    </div>
                    <div class="form-group col-md-1">
                        <button class="btn btn-primary" style="margin-top: 24px" onclick="searchPosByCriteria()">查询</button>
                    </div>
                </div>
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr class="active">
                        <th>商户ID</th>
                        <th>商户名称</th>
                        <th>设备号</th>
                        <th>添加时间</th>
                        <th>银行卡</th>
                        <th>微信</th>
                        <th>支付宝</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="posContent">
                    </tbody>
                </table>
                <div class="tcdPageCode" style="display: inline;">
                </div>
                <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
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
                            <p id="open-posid"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-offset-2 control-label">佣金比</label>

                        <div class="col-sm-6 form-inline">
                            <input type="text" id="open-commission" class="form-control">%
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="open-confirm"
                        data-dismiss="modal">确认
                </button>
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
                            <p id="change-posid"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-offset-2 control-label">佣金比</label>

                        <div class="col-sm-6 form-inline">
                            <input type="text" id="change-commission" class="form-control">%
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
                <button type="button" class="btn btn-primary" id="change-confirm"
                        data-dismiss="modal">确认
                </button>
            </div>
        </div>
    </div>
</div>




<!--编辑提示框-->
<div class="modal" id="createWarn">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">添加POS机具</h4>
            </div>
            <div class="w-addPOS">
                <div>
                    <div style="margin-top: 0;">乐加商户</div>
                    <div>${merchantName}</div>
                </div>
                <div>
                    <div>设备号</div>
                    <div><input name="id" type="hidden"/><input id="sbh" type="text" class="form-control" name="posId"><input name="merchantId" type="hidden"/></div>
                </div>
                <div>
                    <div>银行卡费率</div>
                    <div>
                        <div><span>借记卡</span><input type="number" class="form-control" name="debitCardCommission"/><span style="margin-right: 5%;">%</span><input type="number" class="form-control" name="ceil"/><span>元封顶</span></div>
                        <div><span>信用卡</span><input type="number" class="form-control" name="creditCardCommission"/><span style="margin-right: 5%;">%</span></div>
                        <div><input type="checkbox"><span>开通佣金</span><input type="number" class="form-control" name="ljCommission"/><span>%</span></div>
                    </div>
                </div>
                <div>
                    <div>微信收款</div>
                    <div>
                        <div class="w-b">
                            <div class="w-bActive">未开通</div>
                            <div>已开通</div>
                        </div>
                        <div>
                            <div>
                                <span>普通手续费</span><input type="number" class="form-control" name="wxUserCommission"/><span>%</span>
                            </div>
                            <div>
                                <input type="checkbox"><span>开通佣金</span><input type="number" class="form-control"  name="wxCommission"/><span>%</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <div>支付宝收款</div>
                    <div>
                        <div class="w-b">
                            <div class="w-bActive">未开通</div>
                            <div>已开通</div>
                        </div>
                        <div>
                            <div>
                                <span>普通手续费</span><input type="number" class="form-control"  name="aliUserCommission" /><span>%</span>
                            </div>
                            <div>
                                <input type="checkbox" checked="checked" ><span>开通佣金</span><input type="number" class="form-control" name="aliCommission"/><span>%</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="w-check" >确认</button>
            </div>
            <script>
                $(".w-b").next().hide();
                $("#createWarn input").addClass("ing");
                $("#sbh").removeClass("ing");
                $("#createWarn input").focus(function () {
                    $(this).css("background-color","#FFF");
                });
                $(".w-b > div").click(function () {
                    $(this).parent().children().removeClass("w-bActive");
                    $(this).addClass("w-bActive");
                    var tabText = $(this).html();
                    switch (tabText){
                        case "未开通":
                            $(this).parent(".w-b").next().hide();
                            break;
                        case "已开通":
                            $(this).parent(".w-b").next().show();
                            break;
                    }
                });
                $("input[type=number]").blur(function () {
                    var val = $(this).val();
                    val = Math.round(val*100)/100;
                    $(this).val(val);
                    $(this).removeClass("ing");
                });
                $("input[type=checkbox]").next().next().attr("disabled","disabled");
                if($("input[type=checkbox]").is(':checked')) {
                    $("input[checked=checked]").next().next().removeAttr("disabled");
                    $("input[checked=checked]").next().next().removeClass("ing");
                }
                $("input[type=checkbox]").click(function () {
                    if($(this).is(':checked')){
                        $(this).next().next().removeAttr("disabled");
                        $(this).removeClass("ing")
                    }else{
                        $(this).next().next().attr("disabled","disabled");
                        $(this).addClass("ing")
                    }
                });
                $("#w-check").click(function () {
                    $("#createWarn .ing").css("background-color","rgba(255,0,0,0.3)");
                    if($("#sbh").val() == ""){
                        $("#sbh").css("background-color","rgba(255,0,0,0.3)")
                        return;
                    }
                    saveMerchantPos();              //  保存
                });
            </script>
        </div>
    </div>
</div>
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<!--<script src="js/bootstrap-datetimepicker.min.js"></script>-->
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<!--<script src="js/bootstrap-datetimepicker.zh-CN.js"></script>-->
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script>
    var posCriteria = {};
    posCriteria.offset = 1;
    var posContent = document.getElementById("posContent");
    $.ajax({
               type: 'GET',
               url: '/manage/city/ajax',
               async: false,
               dataType: 'json',
               success: function (data) {
                   console.log(data[0]);
                   var dataStr1 = '',
                           dataStr2 = '';
                   $.each(data, function (i) {
                       dataStr1 +=
                       '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                   });
                   $('#locationCity').append(dataStr1);
               },
               error: function (jqXHR) {
                   alert('发生错误：' + jqXHR.status);
               }
           });
    //    时间选择器
    $(document).ready(function () {
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
    function getPosByAjax(posCriteria) {
        posContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/pos",
                   async: false,
                   data: JSON.stringify(posCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var page = data.data;
                       var content = page.content;
                       console.log(JSON.stringify(content));
                       var totalPage = page.totalPages;
                       $("#totalElements").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       initPage(posCriteria.offset, totalPage);
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].merchant.id + '</td>';
                           contentStr += '<td>' + content[i].merchant.name + '</td>';
                           contentStr += '<td>' + content[i].posId + '</td>';
                           contentStr += '<td><h5>' + new Date(content[i].createdDate).format("yyyy-MM-dd") +'</h5><h5'
                                       + new Date(content[i].createdDate).format("HH:mm:ss") +'</h5></td>';
                           // 银行卡
                           contentStr +='<td><h5>（非会员）</h5><h5>&nbsp;&nbsp;借记卡'+ content[i].debitCardCommission +'&nbsp; 封顶 '+content[i].ceil+'</h5>'
                                        +'<h5>&nbsp;&nbsp;贷记卡 '+content[i].creditCardCommission+'% &nbsp;';
                           if(content[i].ljCommission!=null) {
                               contentStr +='佣金'+content[i].ljCommission;
                           }else {
                               contentStr += '佣金 未开通';
                           }
                           contentStr +='</h5></td>';
                           //  微信
                           if(content[i].wxCommission==null&&content[i].wxUserCommission==null) {
                               contentStr+='<td>未开通</td>';
                           }else if(content[i].wxCommission==null&&content[i].wxUserCommission!=null) {
                               alert(content[i].wxCommission);
                               contentStr+='<td><h5>手续费：'+content[i].wxUserCommission +'%  </h5><h5>佣金： 未开通 </h5></td>';
                           }else {
                                contentStr+='<td><h5>手续费：'+content[i].wxUserCommission+'% </h5><h5>佣金： '+content[i].wxCommission+' %  </h5></td>';
                           }
                           //  支付宝
                           if(content[i].aliCommission==null&&content[i].aliUserCommission==null) {
                               contentStr+='<td>未开通</td>';
                           }else if(content[i].aliCommission==null&&content[i].aliUserCommission!=null) {
                               contentStr+='<td><h5>手续费：'+content[i].aliUserCommission +'%  </h5><h5>佣金： 未开通 </h5></td>';
                           }else {
                               contentStr+='<td><h5>手续费：'+content[i].aliUserCommission+'% </h5><h5>佣金： '+content[i].aliCommission+' %  </h5></td>';
                           }
                           //  操作
                           contentStr+='<td><button type="button" class="btn btn-default createWarn" onclick="editPos('+content[i].id+')">编辑</button></td>';
                           posContent.innerHTML += contentStr;
                       }
                       $(".openCommission").each(function (i) {
                           $(".openCommission").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $("#open-posid").html(id);
                               $("#open-confirm").bind("click", function () {
                                   var commission = $("#open-commission").val();
                                   $("#open-confirm").unbind("click");
                                   $.ajax({
                                              type: "get",
                                              data: {id: id, commission: commission},
                                              url: "/manage/pos/change_commission",
                                              contentType: "application/json",
                                              success: function (data) {
                                                  alert(data.msg);
                                                  getPosByAjax(posCriteria);
                                              }
                                          });
                               });

                           });
                       });
                       $(".changeCommission").each(function (i) {
                           $(".changeCommission").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $("#change-posid").html(id);
                               $("#change-confirm").bind("click", function () {
                                   $("#change-confirm").unbind("click");
                                   var commission = $("#change-commission").val();
                                   $.ajax({
                                              type: "get",
                                              data: {id: id, commission: commission},
                                              url: "/manage/pos/change_commission",
                                              contentType: "application/json",
                                              success: function (data) {
                                                  alert(data.msg);
                                                  getPosByAjax(posCriteria);
                                              }
                                          });
                               });
                           });
                       });
                       ;
                   }
               });
    }
    getPosByAjax(posCriteria)

    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             posCriteria.offset = p;
                                             getPosByAjax(posCriteria);
                                         }
                                     });
    }
    function searchPosByCriteria() {
        posCriteria.offset = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            posCriteria.startDate = startDate;
            posCriteria.endDate = endDate;
        }
        if ($("#locationCity").val() != "" && $("#locationCity").val() != null) {
            posCriteria.merchantLocation = $("#locationCity").val();
        } else {
            posCriteria.merchantLocation = null;
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            posCriteria.merchant = $("#merchant-name").val();
        } else {
            posCriteria.merchant = null;
        }

        if ($("#commission-state").val() != "" && $("#commission-state").val() != null) {
            posCriteria.state = $("#commission-state").val();
        } else {
            posCriteria.state = null;
        }

        getPosByAjax(posCriteria);
    }
    function resetAll() {
        $("input[name=id]").val('');
        $("input[name=merchantId]").val('');
        $("input[name=posId]").val('');
        $("input[name=creditCardCommission]").val('');
        $("input[name=debitCardCommission]").val('');
        $("input[name=ljCommission]").val('');
        $("input[name=ceil]").val('');
        $("input[name=wxCommission]").val('');
        $("input[name=aliCommission]").val('');
        $("input[name=wxCommission]").val('');
        $("input[name=wxUserCommission]").val('');
        $("input[name=aliUserCommission]").val('');
    }
    //  编辑机具信息
    function editPos(id) {
        resetAll();                                                         //  清空上一次信息
        $.get("/manage/pos/getById/"+id,function(pos){                      //  查询并进行数据回显
            $("input[name=id]").val(pos.id);
            $("input[name=merchantId]").val(pos.merchant.id);
            $("input[name=posId]").val(pos.posId);
            $("input[name=creditCardCommission]").val(pos.creditCardCommission);
            $("input[name=debitCardCommission]").val(pos.debitCardCommission);
            $("input[name=ljCommission]").val(pos.ljCommission);
            $("input[name=ceil]").val(pos.ceil);
            $("input[name=wxCommission]").val(pos.wxCommission);
            $("input[name=aliCommission]").val(pos.aliCommission);
            $("input[name=wxCommission]").val(pos.wxCommission);
            $("input[name=wxUserCommission]").val(pos.wxUserCommission);
            $("input[name=aliUserCommission]").val(pos.aliUserCommission);
            $("#createWarn").modal();
        },"json");
    }
    //  保存机具信息
    function saveMerchantPos() {
        var merchantPos = {};
        //  校验
        if($("input[name=posId]").val()!=null&&$("input[name=posId]").val()!='') {
            merchantPos.posId = $("input[name=posId]").val();
        }else {
            return;
        }
        if($("input[name=debitCardCommission]").val()!=null&&$("input[name=debitCardCommission]").val()!='') {
            merchantPos.debitCardCommission = $("input[name=debitCardCommission]").val();
        }else {
            return;
        }
        if($("input[name=creditCardCommission]").val()!=null&&$("input[name=creditCardCommission]").val()!='') {
            merchantPos.creditCardCommission = $("input[name=creditCardCommission]").val();
        }else {
            return;
        }
        if($("input[name=ceil]").val()!=null&&$("input[name=ceil]").val()!='') {
            merchantPos.ceil = $("input[name=ceil]").val();
        }else {
            return;
        }
        merchantPos.ljCommission = $("input[name=ljCommission]").val();
        merchantPos.id = $("input[name=id]").val();
        //  表单提交
        merchantPos.wxCommission = $("input[name=wxCommission]").val();
        merchantPos.aliCommission = $("input[name=aliCommission]").val();
        merchantPos.wxUserCommission = $("input[name=wxUserCommission]").val();
        merchantPos.aliUserCommission = $("input[name=aliUserCommission]").val()
        var merchantId = $("input[name=merchantId]").val();
        merchantPos.merchant={id:merchantId};
        $.ajax({
            type:"post",
            url:"/manage/pos/save_pos",
            contentType:"application/json",
            data:JSON.stringify(merchantPos),
            success:function(data){
                alert(data.msg);
                location.href="/manage/pos";
            }
        });
    }
</script>


</body>
</html>

