<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 16-11-16
  Time: 上午10:24
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
            <div class="container-fluid" style="padding-top: 20px">

                <ul id="myTab" class="nav nav-tabs">
                    <li class="w i"><a href="#ing" data-toggle="tab">启动广告管理</a></li>
                </ul>
                <div style="text-align: left;margin: 10px 0 0 0">
                    <button class="btn btn-primary createWarn">新建启动广告</button>
                </div>
                <div id="myTabContent" class="tab-content" style="margin: 10px 0 0 0">
                    <div class="tab-pane fade in active" id="ing">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>序号</th>
                                <th>图片</th>
                                <th>后置类型</th>
                                <th>对应城市</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="bannerContent">

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
    <%@include file="../common/bottom.jsp" %>
</div>

<!--停用提示框-->

<!--添加或编辑启动广告提示框-->
<div class="modal" id="createWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">编辑启动广告</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">

                    <input name="bannerId12" type="hidden" class="o-input" value=""/>
                    <div class="form-group">
                        <label for="sid12" class="col-sm-3 control-label">启动广告序号</label>
                        <div class="col-sm-6">
                            <input name="bannerType" type="text" class="form-control o-input" id="sid12" value="1">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="" class="col-sm-3 control-label">广告对应城市</label>
                        <div class="col-sm-8">
                            <%--<input type="text" class="form-control" id="toggle-name" placeholder="商户名称">--%>
                            <%--<select class="form-control" style="margin: 0 0 10px 0" name="" id="selCity">--%>
                            <select id="select_province" name="select_province" onchange="changeCity(this)">
                                <option value="" selected="selected">全部省份</option>
                            </select>省
                            <select id="select_city" name="select_city" onchange="changeArea(this)">
                                <option value="" selected="selected">全部地级市</option>
                            </select>市
                            <select id="select_area" name="select_area">
                                <option value="" selected="selected">全部县级市,区</option>
                            </select>区,县级市

                        </div>
                    </div>

                    <div class="form-group">
                        <label for="bannerPicture12" class="col-sm-3 control-label">图片(宽高比:750*1334)</label>
                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="picture12">
                            <!--</div>-->
                            <input type="file" class="form-control" id="bannerPicture12" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="afterType12" class="col-sm-3 control-label">后置类型(点击图片后的跳转情况)</label>
                        <div class="col-sm-6" id="afterType12">
                            <div>
                                <input type="radio" name="type12" class="checked12" checked="true"
                                       value="1"/><span>H5页面（跳转到一个H5页面）</span>
                                <input class="w-input" type="text" placeholder="请输入页面链接地址"/>
                                <input class="w-input" type="text" placeholder="请输入网页标题"/>
                            </div>
                            <div>
                                <input type="radio" name="type12"
                                       value="2"/><span>商品详情（跳转到商品的详情页面）</span>
                                <input class="w-input" type="text" placeholder="请输入商品ID"/>
                            </div>
                            <div>
                                <input type="radio" name="type12"
                                       value="3"/><span>商户详情（跳转到店铺的详情页面）</span>
                                <input class="w-input" type="text" placeholder="请输入商户序号"/>
                            </div>
                            <div>
                                <input type="radio" name="type12" value="4"/><span>无跳转（无跳转后置）</span>
                                <input class="w-input" type="text" style="display:none" value="无跳转" />
                            </div>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="ceate_start_ad()">确认</button>
            </div>
        </div>
    </div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/daterangepicker.js"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script src="${resourceUrl}/js/jquery.page.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script>
    $(".w-input").attr("disabled", "true");
    $(".w-input").css("width", "100%");
    $("input[checked=true]").nextAll().removeAttr("disabled");
    $("input[name=type12]").click(function (e) {
        $("input[type=radio]").removeClass("checked12");
        $(this).attr("class", "checked12");
        $(".w-input").attr("disabled", "true");
        $(this).nextAll().removeAttr("disabled");
    });

    $(function () {
        //tab切换
        $('#myTab li:eq(0) a').tab('show');
        //提示框
        $(".deleteWarn").click(function(){
            $("#deleteWarn").modal("toggle");
        });
        //新建启动广告 编辑页面
        $(".createWarn").click(function(){
            $("#createWarn").modal("toggle");

            //加载省份
            initProvince();
        });

    });
    //初始加载省份
    function initProvince(){
        var select_province = document.getElementById("select_province");
        $.post("/manage/start_ad/ajaxProvince", null, function (res) {
            var content = res.data;
            if(content.length>0){
                for (var i = 0; i < content.length; i++) {
                    var contentStr = '<option value="' + content[i] + '">' + content[i] + '</option>';
                    select_province.innerHTML += contentStr;
                }
            }
        });
    }

    //图片上传
    $('#bannerPicture12').fileupload({
                                         dataType: 'json',
                                         maxFileSize: 5000000,
                                         acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                         add: function (e, data) {
                                             data.submit();
                                         },
                                         done: function (e, data) {
                                             var resp = data.result;
                                             $('#picture12').attr('src',
                                                                  '${ossImageReadRoot}/'
                                                                  + resp.data);
                                         }
                                     });

    //查询启动广告
    var bannerCriteria = {};
    var flag = true;
    var init1 = 0;
    var bannerContent = document.getElementById("bannerContent");
    bannerCriteria.offset = 1;
    bannerCriteria.type = 12;
    function getBannerListByType(criteria) {
        bannerContent.innerHTML = "";
        $.ajax({
                   type: "post",
                   url: "/manage/banner/ajaxList",
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
                           var contentStr = '<tr><td>' + content[i].sid + '</td>';
                           contentStr +=
                           '<td><img style="height: 100px; width: auto;" src="' + content[i].picture
                           + '" alt="..."></td>';
                           if (content[i].afterType == 1) {
                               contentStr += '<td><span>H5页面</span></td>';
                           } else if (content[i].afterType == 2) {
                               contentStr += '<td><span>商品详情</span></td>';
                           } else if (content[i].afterType == 3) {
                               contentStr += '<td><span>好店详情</span></td>';
                           } else if (content[i].afterType == 4) {
                               contentStr += '<td><span>无跳转</span></td>';
                           } else {
                               contentStr += '<td><span>未知</span></td>';
                           }

                           if (content[i].city != null) {
                               contentStr += '<td><span>'+content[i].city.province+'-'+content[i].city.name+'-';
                               if (content[i].area != null) {
                                   contentStr += content[i].area.name+'</span></td>';
                               }else{
                                   contentStr += '全部</span></td>';
                               }
                           }else{
                               contentStr += '<td><span>全部省份</span></td>';
                           }

                           if (content[i].status == 1) {
                               contentStr += '<td><span>上架</span></td>';
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="'
                               + content[i].id
                               + '"><button class="btn btn-primary changeStatus">下架</button>'
                               //                                   + '<br/>'
                               + '<button class="btn btn-primary editBanner">编辑</button></td></tr>';
                           } else {
                               contentStr +=
                               '<td><span style="color: red">下架</span></td>';
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="'
                               + content[i].id
                               + '"><button class="btn btn-primary changeStatus">上架</button>'
                               //                                   + '<br/>'
                               + '<button class="btn btn-primary editBanner">编辑</button>'

                               + '<button type="button" class="btn btn-primary deleteWarn" data-target="#deleteWarn" onclick="deleteBanner('
                               + content[i].id + ')">删除</button></td></tr>';
                           }
                           bannerContent.innerHTML += contentStr;
                       }
                       $(".changeStatus").each(function (i) {
                           $(".changeStatus").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();

                               if (!confirm("确定修改？")) {
                                   return false;
                               }
                               $.ajax({
                                          type: "get",
                                          url: "/manage/banner/status/" + id,
                                          contentType: "application/json",
                                          success: function (data) {
                                              getBannerListByType(bannerCriteria);
                                          }
                                      });

                           });
                       });
                       $(".editBanner").each(function (i) {
                           $(".editBanner").eq(i).bind("click", function () {
                               var id = $(this).parent().find(".id-hidden").val();
                               $.ajax({
                                          type: "get",
                                          url: "/manage/banner/find/" + id,
                                          contentType: "application/json",
                                          success: function (data) {
                                              if (data.status == 200) {
                                                  $(".w-input").val("");
                                                  var banner = data.data;
                                                  var afterType = banner.afterType;
                                                  var bannerType = banner.bannerType.id;
                                                  var bannerSid = "sid" + bannerType;
                                                  var bannerId = "bannerId" + bannerType;
                                                  var picture = "picture" + bannerType;
                                                  var model = "type" + bannerType;
                                                  var myDiv = "afterType" + bannerType;
                                                  var checkType = "checked" + bannerType;
                                                  $('#' + bannerSid + '').val(banner.sid);
                                                  $('input[name=' + bannerId
                                                    + ']').val(banner.id);
                                                  $("#" + picture + "").attr("src",
                                                                             banner.picture);
                                                  var radioChecked = $('#' + myDiv
                                                                       + ' input[type=radio][value='
                                                                       + afterType
                                                                       + ']');

                                                  $('#' + myDiv
                                                    + '  input[type=radio]').removeClass(checkType);
                                                  $('#' + myDiv
                                                    + '  input[type=radio]').removeAttr("checked");
                                                  radioChecked[0].checked = true;
                                                  radioChecked.attr("class", checkType);
                                                  $(".w-input").attr("disabled", "true");
                                                  radioChecked.nextAll().removeAttr("disabled");
                                                  if (afterType == 1) {
                                                      radioChecked.next().next().val(banner.url);
                                                      radioChecked.next().next().next().val(banner.urlTitle)
                                                  } else if (afterType == 2) {
                                                      radioChecked.parent().find(".w-input").val(banner.product.id);
                                                  } else if (afterType == 3) {
                                                      radioChecked.parent().find(".w-input").val(banner.merchant.merchantSid);
                                                  }

                                                  $("#createWarn").modal("show");
//                                                  $("#createWarn").modal("toggle");

                                                  //对应城市
                                                  if (banner.city != null) {
                                                      var select_province = document.getElementById("select_province");
                                                      select_province.innerHTML = "";
                                                      select_province.innerHTML = '<option value="">全部省份</option>';
                                                      $.post("/manage/start_ad/ajaxProvince", null, function (res) {
                                                          var content = res.data;
                                                          if(content.length>0){
                                                              for (var i = 0; i < content.length; i++) {
                                                                  var contentStr = '<option value="' + content[i] + '">' + content[i] + '</option>';
                                                                  if(content[i] == banner.city.province){
                                                                      contentStr = '<option selected="selected" value="' + content[i] + '">' + content[i] + '</option>';
                                                                  }
                                                                  select_province.innerHTML += contentStr;
                                                              }
                                                          }
                                                      });

                                                      var select_city = document.getElementById("select_city");
                                                      select_city.innerHTML = '<option selected="selected" value="' + banner.city.id + '">' + banner.city.name + '</option>';
                                                  }else{
                                                      initProvince();
                                                  }
                                                  if (banner.area != null) {
                                                      var select_area = document.getElementById("select_area");
                                                      select_area.innerHTML = '<option selected="selected" value="' + banner.area.id + '">' + banner.area.name + '</option>';
                                                  }
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
                                             bannerCriteria.offset = p;
                                             init1 = 0;
                                             getBannerListByType(bannerCriteria);
                                         }
                                     });
    }

    getBannerListByType(bannerCriteria);

</script>
<script>

    //根据所选省 加载市...
    function changeCity(obj){
        var select_city = document.getElementById("select_city");
        select_city.innerHTML = '<option value="" selected="selected">全部地级市</option>';//初始 城市选项
        var province = $("#select_province").val();
        $.post("/manage/start_ad/ajaxCity", {province : province}, function (res) {
            var content = res.data;
            if(content.length>0){
                for (var i = 0; i < content.length; i++) {
                    var contentStr = '<option value="' + content[i][0] + '">' + content[i][1] + '</option>';
                    select_city.innerHTML += contentStr;
                }
            }
        });
    }
    //根据所选市 加载区县...
    function changeArea(obj){
        var select_area = document.getElementById("select_area");
        select_area.innerHTML = '<option value="" selected="selected">全部县级市,区</option>';//初始 区县选项
        var cityId = $("#select_city").val();
        $.post("/manage/start_ad/ajaxArea", {cityId : cityId}, function (res) {
            var content = res.data;
            if(content.length>0){
                for (var i = 0; i < content.length; i++) {
                    var contentStr = '<option value="' + content[i][0] + '">' + content[i][1] + '</option>';
                    select_area.innerHTML += contentStr;
                }
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



    //保存启动广告
    function ceate_start_ad() {
        var banner = {}, bannerType = {};
        var picture = $("#picture12").attr("src");
        var input = $(".checked12");
        var afterType = input.val();
        var afterType_val = input.next().next().val();

        var urlTitle = "";
        if (afterType == 1) {
            urlTitle = input.next().next().next().val();
            if (urlTitle == null || urlTitle == "") {
                alert("请输入页面标题");
                return false;
            }
        }
        if (picture == null || picture == "") {
            alert("请选择一张图片");
            return false;
        }
        if (afterType == null) {
            alert("请选择一种后置类型");
            return false;
        }
        if (afterType == 1 || afterType == 2 || afterType == 3) {
            if (afterType_val == null || afterType_val == "") {
                alert("请补全类型信息");
                return false;
            }
        }
        bannerType.id = 12;
        banner.id = $('input[name=bannerId12]').val();
        banner.sid = $('#sid12').val();
        banner.picture = picture;
        banner.bannerType = bannerType;
        banner.afterType = afterType;

        var city = {};
        var area = {};
//        alert($("#select_area").val())
        city.id = $("#select_city").val();
        area.id = $("#select_area").val();
        banner.city = city;
        banner.area = area;

        if (afterType == 1) {
            banner.url = afterType_val;
            banner.urlTitle = urlTitle;
        } else if (afterType == 2) {
            var product = {};
            product.id = afterType_val;
            banner.product = product;
        } else if (afterType == 3) {
            var merchant = {};
            merchant.merchantSid = afterType_val;
            banner.merchant = merchant;
        }
        $.ajax({
                   type: "post",
                   url: "/manage/start_ad/save_start_ad",
                   contentType: "application/json",
                   data: JSON.stringify(banner),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("success");
                           location.href = "/manage/start_ad"
                       } else {
                           if (data.status == 509) {
                               alert("此序号已存在！请调整序号！");
                           }else if (data.status == 506) {
                               alert("商户不存在！");
                           }else if (data.status == 505) {
                               alert("商品不存在！");
                           }else{
                               alert(data.status);
                           }
                       }
                   }
               });

        $("#createWarn").modal("toggle");
//        resetAll();
    }


    function resetAll() {
//        $("#toggle-name").val('');
//        bannerId = null;
    }


    //删除启动广告
    function deleteBanner(id){
        $("#banner-confirm").bind("click", function () {
            $.ajax({
                       type: "post",
                       url: "/manage/banner/delete/"+id,
                       success: function (data) {
                           alert(data.msg);
                           setTimeout(function () {
                               location.reload(true);
                           }, 0);
                       }
                   });
        });
        $("#deleteWarn").modal("show");
    }
</script>

<!--删除提示框-->
<div class="modal" id="deleteWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <h4>确定要删除吗？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="banner-confirm">确认</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
