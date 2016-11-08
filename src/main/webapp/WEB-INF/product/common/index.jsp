<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/11/01
  Time: 上午10:02
  content:普通商品列表
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../commen.jsp" %>
<c:set var="productUrl" value="http://www.lepluslife.com/weixin/product/"></c:set>
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

        #qrCode {
            width: 300px;
            height: 300px;
        }
    </style>
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

                <div class="row" style="margin-bottom: 30px">
                    <div class="form-group col-md-3">
                        <label for="date-end">创建时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="productType">商品分类</label>
                        <select class="form-control" id="productType">
                            <option value="-1">全部分类</option>
                            <c:forEach items="${productTypes}" var="productType">
                                <option value="${productType.id}">${productType.type}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="mark">角标分类</label>
                        <select class="form-control" id="mark">
                            <option value="-1">全部分类</option>
                            <c:forEach items="${markList}" var="mark">
                                <option value="${mark.id}">${mark.value}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-md-2">
                        <label for="minTruePrice">最低金额</label>
                        <input type="number" id="minTruePrice" class="form-control"
                               placeholder="请输入最低金额"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="maxTruePrice">最高金额</label>
                        <input type="number" id="maxTruePrice" class="form-control"
                               placeholder="请输入最高金额"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="maxTruePrice">商品名称</label>
                        <input type="text" id="name" class="form-control"
                               placeholder="请输入商品名称"/>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="postage">是否包邮</label>
                        <select class="form-control" id="postage">
                            <option value="-1">全部分类</option>
                            <option value="0">包邮</option>
                            <option value="1">不包邮</option>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="orderBy">排序条件</label>
                        <select class="form-control" id="orderBy">
                            <option value="0">默认排序</option>
                            <option value="1">创建时间</option>
                            <option value="2">商品序号</option>
                            <option value="3">实际价格</option>
                            <option value="4">可用积分</option>
                            <option value="5">实际销量</option>
                            <option value="6">最后修改时间</option>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="Desc">排序方式</label>
                        <select class="form-control" id="Desc">
                            <option value="1">降序</option>
                            <option value="2">升序</option>
                        </select>
                    </div>

                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchByCriteria()">查询
                        </button>
                        <button type="button" class="btn btn-primary "
                                style="margin-top: 24px;margin-left: 24px"
                                onclick="goProductCreatePage()">创建商品
                        </button>
                    </div>
                </div>

                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab" onclick="searchByType(1)">已上架</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab" onclick="searchByType(0)">已下架</a>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr id="tr" class="active">
                                <th class="text-center">商品分类</th>
                                <th class="text-center">商品序号</th>
                                <th class="text-center">商品ID</th>
                                <th class="text-center">商品名称</th>
                                <th class="text-center">商品图片</th>
                                <th class="text-center">正常单价</th>
                                <th class="text-center">市场价(元）</th>
                                <th class="text-center">实际销量</th>
                                <th class="text-center">库存</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody id="orderContent">
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


<!--下架提示框-->
<div class="modal" id="downWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">下架</h4>
            </div>
            <div class="modal-body">
                <h4>确定要下架商品吗？</h4>

                <p>下架后，该商品将不再展示给消费者，但您随时可以将该商品可以再次上架销售。</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        id="putOff-cancle">取消
                </button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="putOff-confirm">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--上架提示框-->
<div class="modal" id="upWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">上架</h4>
            </div>
            <div class="modal-body">
                <h4>确定要上架商品吗？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="putOn-confirm">确认
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
                <h5 id="productUrl"></h5>

                <div class="center-block">
                    <img id="qrCode" src="" class="center-block" alt="">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
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
    var criteria = {}, flag = true, init1 = 0, orderId = 0, orderState = 0;
    var orderContent = document.getElementById("orderContent");

    $(function () {
        // tab切换
        var tableType = 0;
        if (tableType == 0) {
            $('#myTab li:eq(0) a').tab('show');
        } else if (tableType == 1) {
            $('#myTab li:eq(1) a').tab('show');
        }

        // 时间选择器
        $(document).ready(function () {
            $('#date-end').daterangepicker({
                                               maxDate: moment(), //最大时间
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

        });
        criteria.state = 1;
        criteria.offset = 1;
        criteria.type = 1;
        getListByCriteria(criteria);
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
    //强制保留两位小数
    function toDecimal(x) {
        var f = parseFloat(x);
        if (isNaN(f)) {
            return false;
        }
        var f = Math.round(x * 100) / 100;
        var s = f.toString();
        var rs = s.indexOf('.');
        if (rs < 0) {
            rs = s.length;
            s += '.';
        }
        while (s.length <= rs + 2) {
            s += '0';
        }
        return s;
    }

    function searchByType(type) {
        criteria.offset = 1;
        if (type != null) {
            criteria.state = type;
        } else {
            criteria.state = 1;
        }
        flag = true;
        init1 = 1;
        getListByCriteria(criteria);
    }

    function getListByCriteria(criteria) {
        orderContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/product/ajaxList",
                   async: false,
                   data: JSON.stringify(criteria),
                   contentType: "application/json",
                   success: function (data) {
                       var result = data.data;
                       var content = result.productList;
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

                       for (var i = 0; i < content.length; i++) {
                           var contentStr = '<tr><td>' + content[i].typeName + '</td>';
                           contentStr += '<td>' + content[i].sid + '</td>';
                           contentStr += '<td>' + content[i].id + '</td>';
                           contentStr += '<td>' + content[i].name + '</td>';
                           contentStr +=
                           '<td><img src="' + content[i].thumb + '" alt="...">' + '</td>';
                           contentStr +=
                           '<td>￥' + toDecimal(content[i].minPrice / 100) + '+'
                           + content[i].minScore
                           + '积分</td>';
                           contentStr += '<td>￥' + toDecimal(content[i].price / 100) + '</td>';
                           contentStr += '<td>' + content[i].saleNumber + '</td>';
                           contentStr += '<td>' + content[i].repository + '</td>';

                           if (content[i].state == 1) {
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                               + '"><button class="btn btn-primary" onclick="putOff('
                               + content[i].id
                               + ')">下架</button>';
                           } else {
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="' + content[i].id
                               + '"><button class="btn btn-primary" onclick="putOn('
                               + content[i].id
                               + ')">上架</button>';
                           }
                           contentStr +=
                           '<button class="btn btn-primary" onclick="editProduct('
                           + content[i].id
                           + ')">编辑</button><button class="btn btn-primary" onclick="specManage('
                           + content[i].id
                           + ')">规格管理</button><button class="btn btn-primary" onclick="qrCodeManage('
                           + content[i].id
                           + ')">二维码</button></td></tr>';

                           orderContent.innerHTML += contentStr;
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
                                             criteria.offset = p;
                                             init1 = 0;
                                             getListByCriteria(criteria);
                                         }
                                     });
    }

    function searchByCriteria() {
        condition();
        getListByCriteria(criteria);
    }
    function condition() {
        criteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            criteria.startDate = startDate;
            criteria.endDate = endDate;
        }
        var typeId = $("#productType").val();
        if (typeId != -1) {
            var productType = {};
            productType.id = typeId;
            criteria.productType = productType;
        } else {
            criteria.productType = null;
        }
        var minTruePrice = $("#minTruePrice").val();
        if (minTruePrice != "" && minTruePrice != null) {
            criteria.minTruePrice = minTruePrice * 100;
        } else {
            criteria.minTruePrice = null;
        }
        var maxTruePrice = $("#maxTruePrice").val();
        if (maxTruePrice != "" && maxTruePrice != null) {
            criteria.maxTruePrice = maxTruePrice * 100;
        } else {
            criteria.maxTruePrice = null;
        }
        var name = $("#name").val();
        if (name != null && name != "") {
            criteria.name = name;
        } else {
            criteria.name = null;
        }
        var postage = $("#postage").val();
        if (postage != -1) {
            criteria.postage = postage;
        } else {
            criteria.postage = null;
        }
        var mark = $("#mark").val();
        if (mark != -1) {
            criteria.mark = mark;
        } else {
            criteria.mark = null;
        }
        criteria.orderBy = $("#orderBy").val();
        criteria.desc = $("#Desc").val();
    }

    function putOn(id) {
        $("#putOn-confirm").bind("click", function () {
            $.post("/manage/product/putOn/" + id, null, function (data) {
                alert(data.msg);
                setTimeout(function () {
                    location.reload(true);
                }, 1000);

            });
        });
        $("#upWarn").modal("show");
    }

    function putOff(id) {
        $("#putOff-confirm").bind("click", function () {
            $.post("/manage/product/putOff/" + id, null, function (data) {
                alert(data.msg);
                setTimeout(function () {
                    location.reload(true);
                }, 1000);

            });
        });
        $("#downWarn").modal("show");
    }
    function qrCodeManage(id) {
        $.ajax({
                   type: "get",
                   data: {id: id},
                   url: "/manage/product/qrCode/",
                   success: function (data) {
                       var url = "${productUrl}" + id;
                       $("#productUrl").text(url);
                       $("#qrCode").attr("src", data.msg);
                       $("#qrWarn").modal("show");
                   }
               });
    }
    function specManage(id) {
        location.href = "/manage/product/specManage?id=" + id;
    }
    function editProduct(id) {
        location.href = "/manage/product/edit?id=" + id;
    }
    function goProductCreatePage() {
        location.href = "/manage/product/edit";
    }

</script>
</body>
</html>

