<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        thead th, tbody td {
            text-align: center;
        }

        #myTab {
            margin-bottom: 10px;
        }

        .del {
            background-color: red;
            color: #FFFFFF;
            margin: 0 5px;
            padding: 2px 8px;
        }

        .btn:hover {
            color: #FFFFFF !important;
        }
    </style>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>

    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script>

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
        <div class="main">
            <div class="container-fluid" style="padding-top: 20px">
                <button type="button" class="btn btn-primary " style="margin:10px;"
                        onclick="chooseSecKillDate('${day1}')">今天(${day1})
                </button>
                <button type="button" class="btn btn-primary " style="margin:10px;"
                        onclick="chooseSecKillDate('${day2}')">明天(${day2})
                </button>
                <button type="button" class="btn btn-primary " style="margin:10px;"
                        onclick="chooseSecKillDate('${day3}')">后天(${day3})
                </button>
                <%--<div class="form-group col-md-4">--%>
                    <%--<label for="date-end">选择日期</label>--%>
                    <%--<div id="date-end" class="form-control">--%>
                        <%--<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>--%>
                        <%--<span id="searchDateRange"></span>--%>
                        <%--<b class="caret"></b>--%>
                    <%--</div>--%>
                <%--</div>--%>
                &nbsp;&nbsp;选择日期:&nbsp;<input id="choose_psk_date" class="laydate-icon layer-date ModRadius-right" placeholder="">

                <div class="row" style="margin-top: 10px">
                    <div class="form-group col-md-2">
                        状态
                        <select class="form-control" id="productStatus">
                            <option value="1" selected="selected">上架</option>
                            <option value="0">下架</option>
                        </select>
                    </div>
                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px" onclick="searchByCriteria()">筛选</button>
                    </div>
                </div>

                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr id="tr" class="active">
                                <th>序号</th>
                                <th>时段名称</th>
                                <th>商品ID</th>
                                <th>商品名称</th>
                                <th>兑换价格</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>初始销量</th>
                                <th>实际销量</th>
                                <th>剩余库存</th>
                                <th>有无关联商品</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="productSecKillContent">
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
    <%@include file="../../common/bottom.jsp" %>
</div>

<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script>
    var criteria = {};
    var flag = true;
    var init1 = 0;
    var secKillDate = "";
    var status = "";
    var productSecKillContent = document.getElementById("productSecKillContent");

    //选择日期
    function chooseSecKillDate(date) {
        secKillDate = date;
    }
    function chooseSecKillDate222(datas) {
        secKillDate = $("#choose_psk_date").val();
        alert(secKillDate+"======="+datas)
    }
    var choose_psk_date = {
        elem: '#choose_psk_date',
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



    //初始加载
    $(function () {
        criteria.offset = 1;
        criteria.status = 1;
        getProductSecKillListByCriteria(criteria);
    });
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
    };
</script>
<script>

    function searchByCriteria() {
        flag = true;
        criteria.offset = 1;
        status = $("#productStatus").val();
        secKillDate = $("#choose_psk_date").val();
//        if(secKillDate==""){
//            alert("请选择日期!"+secKillDate)
//            return
//        }
        if(status==""){
            alert("请选择状态!"+secKillDate)
            return
        }
        criteria.secKillDate = secKillDate;
        criteria.status = status;
//        $("#choose_psk_date").val("");
        getProductSecKillListByCriteria(criteria);
    }

    function getProductSecKillListByCriteria(criteria) {
        productSecKillContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/productSecKill/ajaxList",
                   async: false,
                   data: JSON.stringify(criteria),
                   contentType: "application/json",
                   success: function (data) {
                       var result = data.data;
                       var content = result.pskList;
                       var totalPage = result.totalPages;
                       $("#totalElements").html(result.totalElements);
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
                       for (i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].pskId + '</td>';
                           contentStr += '<td>' + content[i].psktName + '</td>';
                           contentStr += '<td>' + content[i].productId + '</td>';
                           contentStr += '<td>' + content[i].productName + '</td>';
                           contentStr += '<td>' + content[i].minScore + '积分+￥'
                                            + content[i].minPrice / 100.0 + '</td>';
                           contentStr += '<td>' + content[i].startTime + '</td>';
                           contentStr += '<td>' + content[i].endTime + '</td>';
                           contentStr += '<td>' + content[i].customSale + '</td>';
                           contentStr += '<td>' + content[i].saleNumber + '</td>';
                           contentStr += '<td>' + content[i].repository + '</td>';
                           if(content[i].isLinkProduct==0) {
                               contentStr += '<td>无</td>';
                           }else if(content[i].isLinkProduct==1) {
                               contentStr += '<td>有</td>';
                           }else {
                               contentStr += '<td></td>';
                           }

                           contentStr +=
                           '<td><input type="hidden" class="id-hidden" value="'
                           + content[i].pskId
                           + '"><input type="hidden" class="id-hidden2" value="'
                           + content[i].productId
                           + '"><button class="btn btn-primary changeStatus">'
                           + (content[i].state == 1 ? '下架' : '上架') + ' </button>'
                           + '<button class="btn btn-primary editLimit">编辑</button></td></tr>';

                           productSecKillContent.innerHTML += contentStr;
                       }

                       $(".changeStatus").each(function (i) {
                           $(".changeStatus").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden2").val();
                               if (!confirm("确定修改？")) {
                                   return false;
                               }
                               $.ajax({
                                          type: "get",
                                          url: "/manage/productSecKill/state/" + id,
                                          contentType: "application/json",
                                          success: function (data) {
                                              getProductSecKillListByCriteria(criteria);
                                          }
                                      });

                           });
                       });
                       $(".editLimit").each(function (i) {
                           $(".editLimit").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               location.href = "/manage/productSecKill/editPage?pskId=" + id;
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
                                             criteria.offset = p;
                                             init1 = 0;
                                             getProductSecKillListByCriteria(criteria);
                                         }
                                     });
    }

</script>
</body>
</html>

