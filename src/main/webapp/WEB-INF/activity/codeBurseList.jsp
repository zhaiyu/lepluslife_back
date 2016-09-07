<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../commen.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/5/25
  Time: 下午1:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE>
<style>
    .pagination > li > a.focusClass {
        background-color: #ddd;
    }

    table tr td img {
        width: 80px;
    }

    .table > thead > tr > td, .table > thead > tr > th {
        line-height: 40px;
    }

    .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
        line-height: 80px;
    }

    .active img {
        width: 80px;
        height: 80px;
    }

</style>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>乐+生活 后台模板管理系统</title>
    <link href="${resourceUrl}/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link rel="stylesheet" type="text/css" href="${resourceUrl}/date/css/jedate.css"/>

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

            <h3 style="margin:20px;">送红包活动列表
                <button type="button" class="btn btn-primary " style="margin:10px;"
                        id="btnNewActivity">创建活动
                </button>
            </h3>

            <div class="row">
                <div class="col-md-12">
                    <div class="portlet">


                        <div class="portlet-body">
                            <div class="table-container">

                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class="text-center">活动标题</th>
                                        <th class="text-center">送红包金额</th>
                                        <th class="text-center">扫码领红包人数</th>
                                        <th class="text-center">发放红包金额</th>
                                        <th class="text-center">活动预算</th>
                                        <th class="text-center">活动时间</th>
                                        <th class="text-center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="userContent">

                                    </tbody>
                                </table>

                                <div class="tcdPageCode" style="display: inline;">
                                </div>
                                <div style="display: inline;"> 共有 <span id="totalElements"></span> 个
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>


<div class="modal" id="dlgEditModel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="form-horizontal">
                <div class="modal-header">
                    <button class="close" data-dismiss="modal"></button>
                    <h4 class="modal-title">活动编辑</h4>
                </div>

                <div class="modal-body">
                    <input name="activityId" type="hidden" value=""/>

                    <div class="form-body">
                        <div class="form-group">
                            <label class="col-md-4 control-label">活动标题：</label>

                            <div class="col-md-6">
                                <input name="title" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">开始时间：</label>

                            <div class="col-md-6">
                                <input id="beginDate" name="beginDate" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">结束时间：</label>

                            <div class="col-md-6">
                                <input id="endDate" name="endDate" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">单人金额：</label>

                            <div class="col-md-6">
                                <input name="singleMoney" class="form-control" value="0"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-md-4 control-label">活动预算：</label>

                            <div class="col-md-6">
                                <input name="budget" class="form-control"
                                       value="0"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label"></label>

                            <div class="col-md-6">
                                <font color="black">（超过预算将无法再领取红包）</font>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" id="btnSubmitForm" class="btn blue">提交
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!--二维码提示框-->
<div class="modal" id="qrWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">二维码</h4>
            </div>
            <div style="text-align: center">
                <img id="qrCode" src="" alt=""/>
            </div>
        </div>
    </div>
</div>

<!--如果只是做布局的话不需要下面两个引用-->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script src="${resourceUrl}/date/js/jedate.min.js"></script>
<script src="${resourceUrl}/date/js/jedate.js"></script>
<script type="text/javascript">
    jeDate({
               dateCell: "#beginDate",
               format: "YYYY-MM-DD hh:mm:ss",
               isinitVal: true,
               isTime: true, //isClear:false,
               zIndex: 1055,
               minDate: "2000-01-01 00:00:00"
           });
    jeDate({
               dateCell: "#endDate",
               format: "YYYY-MM-DD hh:mm:ss",
               isinitVal: true,
               zIndex: 1055,
               isTime: true, //isClear:false,
               minDate: "2000-01-01 00:00:00"
           });
</script>
<script>

    var offset = 1, flag = true, userContent = document.getElementById("userContent");
    jQuery(document).ready(function () {

        //        TableAjax.init();
        window.menuList = null;
        $('input[name=singleMoney]').TouchSpin({
                                                   min: 0,
                                                   max: 100,
                                                   step: 0.01,
                                                   decimals: 2, //精度
                                                   maxboostedstep: 100,
                                                   prefix: '￥'
                                               });
        $('input[name=budget]').TouchSpin({
                                              min: 0,
                                              max: 1000000,
                                              step: 1,
                                              decimals: 0, //精度
                                              maxboostedstep: 100,
                                              prefix: '￥'
                                          });

        getUserByAjax(offset);
    });

    function getUserByAjax(offset) {
        userContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/codeBurse/ajaxList?offset=" + offset,
                   async: false,
                   contentType: "application/json",
                   success: function (data) {
                       var map = data.data;
                       var content = map.content;
                       var totalPage = map.totalPages;
                       var totalElements = map.totalElements;
                       $("#totalElements").html(totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }

                       if (flag) {
                           initPage(offset, totalPage);
                           flag = false;
                       }
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td class="text-center">' + content[i].title
                                            + '</td>';

                           contentStr +=
                           '<td class="text-center"><span>￥</span>' + content[i].singleMoney / 100
                           + '</td>';
                           contentStr +=
                           '<td class="text-center">' + content[i].totalNumber + '</td>';
                           contentStr +=
                           '<td class="text-center"><span>￥</span>' + content[i].totalMoney / 100
                           + '</td>';
                           contentStr +=
                           '<td class="text-center"><span>￥</span>' + content[i].budget / 100
                           + '</td>';

                           contentStr += '<td class="text-center"><span>'
                                         + new Date(content[i].beginDate).format('yyyy-MM-dd HH:mm')
                                         + ' - '
                                         + new Date(content[i].endDate).format('yyyy-MM-dd HH:mm')
                                         + '</span></td>';

                           contentStr +=
                           '<td class="text-center"><input type="hidden" class="id-hidden" value="'
                           + content[i].id
                           + '"><input type="hidden" class="ticket-hidden" value="'
                           + content[i].ticket
                           + '"><button class="btn btn-primary showQR">二维码</button>'
                           + '<button class="btn btn-primary editCode">编辑</button>'
                           + '<button class="btn btn-primary showInfo">查看记录</button>';

                           if (content[i].state == 1) {
                               contentStr +=
                               '<button class="btn btn-primary changeState">进行中</button></td></tr>';
                           } else {
                               contentStr +=
                               '<button class="btn btn-primary changeState" style="background-color: crimson">已暂停</button></td></tr>';
                           }

                           userContent.innerHTML += contentStr;
                       }

                       //永久二维码
                       $(".showQR").each(function (i) {
                           $(".showQR").eq(i).bind("click", function () {
                               var ticket = $(this).parent().find(".ticket-hidden").val();
                               $('#qrCode').attr('src',
                                                 'https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket='
                                                 + ticket);
                               $('#qrWarn').modal('show');
                           });
                       });

                       //查看记录
                       $(".showInfo").each(function (i) {
                           $(".showInfo").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               location.href = "/manage/codeJoin/" + id;
                           });
                       });

                       //修改活动
                       $(".editCode").each(function (i) {
                           $(".editCode").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $.ajax({
                                          type: "get",
                                          url: "/manage/codeBurse/find/" + id,
                                          contentType: "application/json",
                                          success: function (data) {
                                              var bean = data.data;
                                              $('input[name=activityId]').val(bean.id);
                                              $('input[name=beginDate]').val(new Date(bean.beginDate).format('yyyy-MM-dd HH:mm:ss'));
                                              $('input[name=endDate]').val(new Date(bean.endDate).format('yyyy-MM-dd HH:mm:ss'));
                                              $('input[name=title]').val(bean.title);
                                              $('input[name=singleMoney]').val(bean.singleMoney
                                                                               / 100);
                                              $('input[name=budget]').val(bean.budget / 100);
                                              $('#dlgEditModel').modal('show');
                                          }
                                      });
                           });
                       });

                       //修改活动状态
                       $(".changeState").each(function (i) {
                           $(".changeState").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               var state = $(this).html();
                               if (state == '进行中') {
                                   if (!confirm('确定暂停该活动吗？')) {
                                       return false;
                                   }
                               } else {
                                   if (!confirm('确定启动该活动吗？')) {
                                       return false;
                                   }
                               }
                               $.ajax({
                                          type: "get",
                                          url: "/manage/codeBurse/changeState/" + id,
                                          contentType: "application/json",
                                          success: function (data) {
                                              setTimeout(function () {
                                                  getUserByAjax(1);
                                              }, 0);
                                          }
                                      });
                           });
                       });

                   }
               });

    }

    function initPage(currPage, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: currPage,
                                         backFn: function (p) {
                                             offset = p;
                                             getUserByAjax(offset);
                                         }
                                     });
    }

    $('#btnNewActivity').on('click', function () {
        reset();
        $('#dlgEditModel').modal('show');
    });

    $('#btnSubmitForm').on('click', function () {
        var activityId = $('input[name=activityId]').val();
        var title = $('input[name=title]').val();
        var beginDate = new Date($('input[name=beginDate]').val().replace(/-/g, "/"));
        var endDate = new Date($('input[name=endDate]').val().replace(/-/g, "/"));
        var singleMoney = $('input[name=singleMoney]').val() * 100;
        var budget = $('input[name=budget]').val() * 100;

        var activityCodeBurse = {};
        activityCodeBurse.id = activityId;
        activityCodeBurse.title = title;
        activityCodeBurse.beginDate = beginDate;
        activityCodeBurse.endDate = endDate;
        activityCodeBurse.singleMoney = singleMoney;
        activityCodeBurse.budget = budget;
        $.ajax({
                   type: "post",
                   url: "/manage/codeBurse/save",
                   contentType: "application/json",
                   data: JSON.stringify(activityCodeBurse),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           reset();
                           setTimeout(function () {
                               location.reload(true);
                           }, 300);
                       } else {
                           alert(data.status);
                       }
                   }
               });
    });

    function reset() {
        $('input[name=activityId]').val('');
        $('input[name=beginDate]').val('');
        $('input[name=endDate]').val('');
        $('input[name=title]').val('');
        $('input[name=singleMoney]').val(0);
        $('input[name=budget]').val(0);
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

