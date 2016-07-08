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
    <link rel="stylesheet" href="${resourceUrl}/css/jqpagination.css"/>
</head>

<body>


<div id="topIframe">
    <%@include file="../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../common/left.jsp" %>
    </div>
    <div class="m-right" style="margin-top: 10px">
        <div class="main">


            <h2 style="margin:20px;display: inline"> 发送图文消息</h2>
            <button type="button" class="btn btn-primary btn-return" style="margin:10px;"
                    onclick="goUserPage()">
                返回会员列表
            </button>


            <div class="row">
                <div class="col-md-12">
                    <div class="portlet">
                        <div class="portlet-title">
                            <div class="caption">
                                <p style="margin-left: 50px;font-size: 20px">你正在向 <span
                                        style="color: red;font-size: 30px">${totalElements}</span>
                                    个会员群发消息</p>

                                <p style="margin-left: 50px;font-size: 20px">
                                    如果有会员未关注公众号，或者群发余额为0，将收不到本次群发</p>
                            </div>
                        </div>

                        <div class="portlet-body">
                            <div class="table-container">
                                <div class="form-horizontal">
                                    <div class="form-body">
                                        <div class="table-toolbar">
                                            <div class="row">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class="text-center">选择</th>
                                        <th class="text-center">图文标题</th>
                                        <th class="text-center">图文media_id</th>
                                    </tr>
                                    </thead>
                                    <tbody id="newsContent">
                                    </tbody>
                                </table>

                                <div class="pagination">
                                    <a href="#" class="first" data-action="first">&laquo;</a>
                                    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                                    <input type="text" readonly="readonly" data-max-page="40"/>
                                    <a href="#" class="next" data-action="next">&rsaquo;</a>
                                    <a href="#" class="last" data-action="last">&raquo;</a>
                                    共有 <span id="totalElements"></span> 个
                                </div>

                            </div>
                        </div>

                        <button class="btn btn-primary" style="margin-left:20px;margin-top: 24px"
                                onclick="sendNews()">
                            确认发送
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>


<!--如果只是做布局的话不需要下面两个引用-->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>
<script src="${resourceUrl}/js/moment.min.js"></script>
<script>
    var newsContent = document.getElementById("newsContent");
    var flag = true;
    var total_count = 0;
    jQuery(document).ready(function () {
        window.menuList = null;
        $('input[name=displayOrder]').TouchSpin({
                                                    min: 0,
                                                    max: 1000000000,
                                                    step: 1,
                                                    maxboostedstep: 100,
                                                    prefix: '序号'
                                                });

        pageChange(1);

    });

    function pageChange(offset) {
        if (flag) {
            newsContent.innerHTML = "";
            $.ajax({
                       type: "post",
                       url: "/manage/weixin/news/newsList",
                       async: false,
                       data: {offset: offset},
                       success: function (data) {
                           var content = data.data;
                           var items = content.item;
                           $("#totalElements").html(content.total_count);
                           total_count = Math.ceil(content.total_count / 20);
                           for (i = 0; i < items.length; i++) {
                               var contentStr = '<tr><td><label class="radio-inline"><input type="radio" name="inlineRadioOptions" value="'
                                                + items[i].media_id + '"></label></td>';
                               contentStr += '<td>' + items[i].content.news_item[0].title + '</td>';
                               contentStr += '<td>' + items[i].media_id + '</td></tr>';

                               newsContent.innerHTML += contentStr;
                           }

                           initPage(offset, total_count);

                       }
                   });
        }
    }

    function initPage(currPage, totalPage) {
        $('.pagination').jqPagination({
                                          current_page: currPage, //设置当前页 默认为1
                                          max_page: totalPage, //设置最大页 默认为1
                                          page_string: '当前第{current_page}页,共{max_page}页',
                                          paged: function () {
                                              flag = false;
                                              pageChange(currPage);
                                          }
                                      });
    }

    function sendNews() {

        if (!confirm('确定发送“' + "测试图文消息" + '”图文消息给 ' + '${totalElements}' + ' 个用户？')) {
            return false;
        }

        var mediaId = $("input[name='inlineRadioOptions']:checked").val();
        if (mediaId == null || mediaId == '') {
            alert("请先选择要发送的图文消息");
            return false;
        }

        var type = '${type}';
        if (type == 1) {
            $.ajax({
                       type: "post",
                       async: false,
                       url: "/manage/weixin/news/sendNews?mediaId=" + mediaId,
                       contentType: "application/json",
                       data: JSON.stringify(JSON.parse('${leJiaUserCriteria}')),
                       success: function (data) {
                           if (data.status == 200) {
                               alert("发送成功");
                           } else {
                               alert("发送异常: " + data.status);
                           }
                       }
                   });
        } else if (type == 2) {
            $.ajax({
                       type: "post",
                       async: false,
                       url: "/manage/weixin/news/sendNewsToAll?mediaId=" + mediaId,
                       contentType: "application/json",
                       success: function (data) {
                           if (data.status == 200) {
                               alert("发送成功");
                           } else {
                               alert("发送异常: " + data.status);
                           }
                       }
                   });
        }

    }

    function goUserPage() {
        location.href = "/manage/user";
    }


</script>
</body>
</html>

