<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/4/6
  Time: 下午2:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="merchantUrl" value="http://www.lepluslife.com/lepay/merchant/"></c:set>
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

        table tr td img {
            width: 80px;
            height: 80px;
        }

        .table > thead > tr > td, .table > thead > tr > th {
            line-height: 40px;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th {
            line-height: 60px;
        }

        .modal-body .main {
            width: 345px;
            height: 480px;
            margin: 0 auto;
            background: url("${resourceUrl}/images/lefuma.png") no-repeat;
            background-size: 100% 100%;
            position: relative;
        }

        .modal-body #myShowImage {
            width: 345px;
            height: 480px;
            position: absolute;
            top: 20px;
            left: 0;
            right: 0;
            margin: auto;
            display: none;
        }

        .modal-body .main .rvCode {
            width: 230px;
            height: 230px;
            position: absolute;
            top: 120px;
            left: 0;
            right: 0;
            margin: auto;
        }

        .modal-body .main .shopName {
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
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script src="${resourceUrl}/js/jquery.page.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/html2canvas.js"></script>
    <script src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script src="${resourceUrl}/js/daterangepicker.js"></script>
    <%--<script src="${resourceUrl}/js/bootstrap-datetimepicker.zh-CN.js"></script>--%>
    <script src="${resourceUrl}/js/moment.min.js"></script>

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
            <div class="container-fluid" style="padding-top: 20px">
                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="partnership">商户分类</label>
                        <select class="form-control" id="partnership">
                            <option value="-1">全部分类</option>
                            <option value="0">普通商户</option>
                            <option value="1">联盟商户</option>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="merchantType">所属类型</label>
                        <select class="form-control" id="merchantType">
                            <option value="0">全部分类</option>
                            <c:forEach items="${merchantTypes}" var="merchantType">
                                <option value="${merchantType.id}">${merchantType.name}</option>
                            </c:forEach>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="merchantType">所在城市</label>
                        <select class="form-control" id="city">
                            <option value="0">全部分类</option>
                            <c:forEach items="${cities}" var="city">
                                <option value="${city.id}">${city.name}</option>
                            </c:forEach>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="merchantType">乐店状态</label>
                        <select class="form-control" id="state">
                            <option value="-1">全部分类</option>
                            <option value="0">未开启</option>
                            <option value="1">已开启</option>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="merchantType">红包权限</label>
                        <select class="form-control" id="receiptAuth">
                            <option value="-1">全部分类</option>
                            <option value="0">未开启</option>
                            <option value="1">已开启</option>
                        </select></div>
                    <div class="form-group col-md-3">
                        <label for="merchant-name">商户名称</label>
                        <input type="text" id="merchant-name" class="form-control"
                               placeholder="请输入商户名称"/>
                        <label for="merchant-name">商户ID</label>
                        <input type="text" id="merchant-id" class="form-control"
                               placeholder="请输入商户ID"/>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="merchant-name">创建时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>

                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchMerchantByCriteria()">筛选
                        </button>
                    </div>
                    <div class="form-group col-md-3"></div>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#lunbotu" data-toggle="tab">商户管理</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="lunbotu">
                        <button type="button" class="btn btn-primary createLocation"
                                style="margin:10px 0;" onclick="createMerchant()">新增商户
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>商户序号</th>
                                <th>商户所在城市</th>
                                <th>分类</th>
                                <th>商户名称</th>
                                <th>所属合伙人</th>
                                <th>锁定会员</th>
                                <th>合约分类</th>
                                <th>佣金比(手续费)</th>
                                <th>POS机具</th>
                                <th>乐店状态</th>
                                <th>收取红包权限</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="merchantContent">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="tcdPageCode" style="display: inline;">
                </div>
                <div style="display: inline;"> 共有 <span id="totalElements"></span> 个</div>
                <button class="btn btn-primary pull-right" style="margin-top: 5px"
                        onclick="exportExcel(merchantCriteria)">导出excel
                </button>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

<!--停用提示框-->
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">停用</h4>
            </div>
            <div class="modal-body">
                <h4 id="merchantWarn"></h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="disable-confirm">确认
                </button>
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
            <div class="modal-body">
                <h5 id="merchantUrl"></h5>

                <div class="main">
                    <img class="rvCode" id="qrCode" src="" alt=""/>

                    <p class="shopName"></p>
                </div>
                <img id="myShowImage" style="margin: 0 auto" src=""/>
                <h4 class="text-center text-danger">点击“生成图片”按钮，再右键将图片存到本地</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary savePic">生成图片</button>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    var merchantCriteria = {};
    var flag = true;
    var init1 = 0;
    var merchantContent = document.getElementById("merchantContent");
    var merchantWarn = document.getElementById("merchantWarn");
    $(function () {

        $(document).ready(function () {
//            $('#date-end span').html(moment().subtract('hours', 1).format('YYYY/MM/DD HH:mm:ss')
//                                     + ' - ' +
//                                     moment().format('YYYY/MM/DD HH:mm:ss'));
            $('#date-end').daterangepicker({
                                               maxDate: moment(), //最大时间
//                                               dateLimit: {
//                                                   days: 30
//                                               }, //起止时间的最大间隔
                                               showDropdowns: true,
                                               showWeekNumbers: false, //是否显示第几周
                                               timePicker: true, //是否显示小时和分钟
                                               timePickerIncrement: 60, //时间的增量，单位为分钟
                                               timePicker12Hour: false, //是否使用12小时制来显示时间
                                               ranges: {
                                                   '最近1小时': [moment().subtract('hours', 1),
                                                             moment()],
                                                   '今日': [moment().startOf('day'), moment()],
                                                   '昨日': [moment().subtract('days',
                                                                            1).startOf('day'),
                                                          moment().subtract('days',
                                                                            1).endOf('day')],
                                                   '最近7日': [moment().subtract('days', 6), moment()],
                                                   '最近30日': [moment().subtract('days', 29),
                                                             moment()]
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
                                                                '七月', '八月', '九月', '十月', '十一月',
                                                                '十二月'],
                                                   firstDay: 1
                                               }
                                           }, function (start, end, label) {//格式化日期显示框
                $('#date-end span').html(start.format('YYYY/MM/DD HH:mm:ss') + ' - '
                                         + end.format('YYYY/MM/DD HH:mm:ss'));
            });
        })
//        tab切换
        $('#myTab li:eq(0) a').tab('show');
//        提示框
        $(".deleteWarn").click(function () {
            $("#deleteWarn").modal("toggle");
        });
        $(".rvCodeWorn").click(function () {
            $("#rvCodeWorn").modal("toggle");
        });
        merchantCriteria.offset = 1;

        getMerchantByAjax(merchantCriteria);
    });
    //    $(".savePic").click(function () {
    //        html2canvas($(".modal-body .main"), {
    //            allowTaint: true,
    //            taintTest: false,
    //            onrendered: function (canvas) {
    //                canvas.id = "mycanvas";
    //                var image = canvas.toDataURL("image/png").replace("image/png",
    //                                                                  "image/octet-stream");
    //                var save_link = document.createElement('a');
    //                save_link.href = image;
    //                save_link.download = "1.png";
    //
    //                var event = document.createEvent('MouseEvents');
    //                event.initMouseEvent('click', true, false, null, 0, 0, 0, 0, 0, false, false, false,
    //                                     false, 0, null);
    //                save_link.dispatchEvent(event);
    //
    //            }
    //        });
    //    })
    function getMerchantByAjax(merchantCriteria) {
        merchantContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/merchant/search",
                   async: false,
                   data: JSON.stringify(merchantCriteria),
                   contentType: "application/json",
                   success: function (data) {
                       var list = data.data;
                       var page = list[0];
                       var binds = list[1];
                       var posCount = list[2];
                       var content = page.content;
                       var totalPage = page.totalPages;
                       $("#totalElements").html(page.totalElements);
                       if (totalPage == 0) {
                           totalPage = 1;
                       }
                       if (flag) {
                           initPage(merchantCriteria.offset, totalPage);
                           flag = false;
                       }
                       if (init1) {
                           initPage(1, totalPage);
                       }
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].merchantSid + '</td>';
                           contentStr += '<td>' + content[i].city.name + '</td>'
                           contentStr +=
                           '<td>' + content[i].merchantType.name + '</td>'
                           contentStr +=
                           '<td>' + content[i].name + '</td>';
                           contentStr +=
                           '<td>' + content[i].partner.name + '</td>';
                           contentStr +=
                           '<td>' + binds[i] + '/' + content[i].userLimit + '</td>';
                           if (content[i].partnership == 0) {
                               contentStr +=
                               '<td>普通商户</td>'
                           } else {
                               contentStr +=
                               '<td>联盟商户</td>'
                           }
                           contentStr +=
                           '<td>' + content[i].ljCommission + '%</td>'
                           if(posCount[i]==0) {
                               contentStr +=
                                       '<td>未进件</td>'
                           }else {
                               contentStr +=
                                       '<td>'+posCount[i]+'个</td>'
                           }
                           if (content[i].state == 0) {
                               contentStr +=
                               '<td>未开启</td>'
                           } else {
                               contentStr +=
                               '<td>已开启</td>'
                           }
                           if (content[i].receiptAuth == 0) {
                               contentStr +=
                               '<td>未开通</td>'
                           } else {
                               contentStr +=
                               '<td>已开通</td>'
                           }
                           contentStr +=
                           '<td><span>'
                           + new Date(content[i].createDate).format('yyyy-MM-dd HH:mm:ss')
                           + '</span></td>';
//                           contentStr +=
//                           '<td><span>'
//                           + content[i].merchantSid
//                           + '</span></td>';
                           contentStr +=
                           '<td><input type="hidden" class="name-hidden" value="' + content[i].name
                           + '"><input type="hidden" class="id-hidden" value="' + content[i].id
                           + '"><button type="button" class="btn btn-default createUser">账户</button>';
                           contentStr +=
                           '<button type="button" class="btn btn-default showQRCode">二维码</button>';
                           contentStr +=
                           '<button type="button" class="btn btn-default posManage">pos管理</button>';
                           contentStr +=
                           '<shiro:hasPermission name="merchant:edit"><button type="button" class="btn btn-default editMerchant">编辑</button></shiro:hasPermission>';
                           contentStr +=
                           '<button type="button" class="btn btn-default editMerchantContent">内容管理</button>';
                           if (content[i].partnership == 1) {
                               contentStr +=
                               '<button type="button" class="btn btn-default showPureQRCode">纯支付码</button>';
                           }
                           if (content[i].state == 0) {
                               contentStr +=
                               '<button type="button" class="btn btn-default disableMerchant">开启乐店</button></td></tr>';
                           } else {
                               contentStr +=
                               '<button type="button" class="btn btn-default disableMerchant">乐店编辑</button></td></tr>';
                           }

                           merchantContent.innerHTML += contentStr;

                       }
                       $(".createUser").each(function (i) {
                           $(".createUser").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               location.href = "/manage/merchant/user/" + id;
                           });
                       });
                       $(".posManage").each(function (i) {
                           $(".posManage").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               location.href = "/manage/merchant/pos_manage/" + id;
                           });
                       });
                       $(".showQRCode").each(function (i) {
                           $(".showQRCode").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $.ajax({
                                          type: "get",
                                          data: {id: id},
                                          url: "/manage/merchant/qrCode",
                                          success: function (data) {
                                              var merchant = data.data;
                                              var url = "${productUrl}" + id;
                                              $(".shopName").html(merchant.name);
                                              $("#qrCode").attr("src", merchant.qrCodePicture);
                                              $("#qrWarn").modal("show");
                                              var url = "${merchantUrl}" + merchant.merchantSid;
                                              $("#merchantUrl").html(url);
                                              document.querySelector(".savePic").addEventListener("click",
                                                                                                  function () {
                                                                                                      html2canvas($(".modal-body .main"),
                                                                                                                  {
                                                                                                                      allowTaint: true,
                                                                                                                      taintTest: false,
                                                                                                                      onrendered: function (canvas) {
                                                                                                                          canvas.id =
                                                                                                                          "mycanvas";
                                                                                                                          var dataUrl = canvas.toDataURL();
                                                                                                                          $("#myShowImage").attr("src",
                                                                                                                                                 dataUrl).css({'display': 'block'});
                                                                                                                          $(".main").css({'display': 'none'});
//
                                                                                                                      }
                                                                                                                  });
                                                                                                  },
                                                                                                  false);
                                          }
                                      });
                           });
                       });
                       $(".showPureQRCode").each(function (i) {
                           $(".showPureQRCode").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $.ajax({
                                          type: "get",
                                          data: {id: id},
                                          url: "/manage/merchant/pureQrCode",
                                          success: function (data) {
                                              var merchant = data.data;
                                              var url = "${productUrl}" + id;
                                              $(".shopName").html(merchant.name);
                                              $("#qrCode").attr("src", merchant.pureQrCode);
                                              $("#qrWarn").modal("show");
                                          }
                                      });
                           });
                       });
                       $(".editMerchant").each(function (i) {
                           $(".editMerchant").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               location.href = "/manage/merchant/edit/" + id;
                           });
                       });
                       $(".editMerchantContent").each(function (i) {
                           $(".editMerchantContent").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               location.href = "/manage/merchant/editContent?id=" + id + "&type=1";
                           });
                       });
                       $(".disableMerchant").each(function (i) {
                           $(".disableMerchant").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               location.href = "/manage/merchant/openStore/" + id;
                           });
                       });

                   }
               });
    }

    function createMerchant() {
        location.href = "/manage/merchant/edit";
    }
    function initPage(page, totalPage) {
        $('.tcdPageCode').unbind();
        $(".tcdPageCode").createPage({
                                         pageCount: totalPage,
                                         current: page,
                                         backFn: function (p) {
                                             init1 = 0;
                                             merchantCriteria.offset = p;
                                             getMerchantByAjax(merchantCriteria);
                                         }
                                     });
    }
    function searchMerchantByCriteria() {
        merchantCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            merchantCriteria.startDate = startDate;
            merchantCriteria.endDate = endDate;
        }
        if ($("#merchant-name").val() != "" && $("#merchant-name").val() != null) {
            merchantCriteria.merchantName = $("#merchant-name").val();
        } else {
            merchantCriteria.merchantName = null;
        }
        if ($("#merchant-id").val() != "" && $("#merchant-id").val() != null) {

            merchantCriteria.merchantSid = $("#merchant-id").val();
        } else {
            merchantCriteria.merchantSid = null;
        }
        if ($("#partnership").val() != -1) {
            merchantCriteria.partnership = $("#partnership").val();
        } else {
            merchantCriteria.partnership = null;
        }

        if ($("#city").val() != 0) {
            merchantCriteria.city = $("#city").val();
        } else {
            merchantCriteria.city = null;
        }

        if ($("#receiptAuth").val() != -1) {
            merchantCriteria.receiptAuth = $("#receiptAuth").val();
        } else {
            merchantCriteria.receiptAuth = null;
        }

        if ($("#state").val() != -1) {
            merchantCriteria.storeState = $("#state").val();
        } else {
            merchantCriteria.storeState = null;
        }

        if ($("#merchantType").val() != 0) {
            merchantCriteria.merchantType = $("#merchantType").val();
        } else {
            merchantCriteria.merchantType = null;
        }
        getMerchantByAjax(merchantCriteria);
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
    function exportExcel(merchantCriteria) {
        var city = $("#city").val();
        location.href =
        "/manage/merchant/merchantExport?merchantCriteria=" + merchantCriteria + "&&city=" + city;
    }
</script>
</body>
</html>


