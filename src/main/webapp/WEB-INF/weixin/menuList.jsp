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

            <h3 style="margin:20px;">自定义菜单列表   <button type="button" class="btn btn-primary " style="margin:10px;"
                                                       id="btnNewMenu">创建菜单
            </button></h3>

            <div class="row">
                <div class="col-md-12">
                    <div class="portlet">


                        <div class="portlet-body">
                            <div class="table-container">
                                <div class="form-horizontal">
                                    <div class="form-body">
                                        <div class="table-toolbar">
                                            <div class="row">
                                                <div class="col-md-2">


                                                </div>

                                                <div class="col-md-2 col-md-offset-8">

                                                    <button type="button" class="btn btn-primary " style="margin:10px;"
                                                            id="btnGenerateMenu">生成菜单
                                                    </button>
                                                    <button type="button" class="btn btn-primary " style="margin:10px;"
                                                            id="btnDeleteMenu">删除菜单
                                                    </button>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class="text-center">顺序号</th>
                                        <th class="text-center">菜单名称</th>
                                        <th class="text-center">上级菜单名称</th>
                                        <th class="text-center">触发关键词</th>
                                        <th class="text-center">链接地址</th>
                                        <th class="text-center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${menuList}" var="menu">
                                        <tr class="active">
                                            <td class="text-center">${menu.displayOrder}</td>
                                            <td class="text-center">${menu.name}</td>
                                            <td class="text-center">${menu.parentMenu.name}</td>
                                            <td class="text-center">${menu.triggerKeyword}</td>
                                            <td class="text-center">${menu.triggerUrl}</td>

                                            <td class="text-center">


                                                <button type="button" class="btn btn-default"
                                                        data-target="#dlgEditModel"
                                                        onclick="edit(${menu.id})">编辑
                                                </button>
                                                <button type="button" class="btn btn-default"
                                                        onclick="deleteMenu(${menu.id})">删除
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

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
                    <h4 class="modal-title">菜单编辑</h4>
                </div>

                <div class="modal-body">
                    <input name="menuId" type="hidden" value=""/>

                    <div class="form-body">
                        <div class="form-group">
                            <label class="col-md-4 control-label">显示顺序：</label>

                            <div class="col-md-6">
                                <input name="displayOrder" class="form-control"
                                       value="0"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">菜单名称：</label>

                            <div class="col-md-6">
                                <input name="name" class="form-control" value=""/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">上级菜单：</label>

                            <div class="col-md-6">
                                <select class="form-control" id="parentMenu"
                                        name="parentMenu">
                                    <option value="0">请选择</option>
                                    <c:forEach var="parentMenu"
                                               items="${parentMenuList}">
                                        <option value="${parentMenu.id}">${parentMenu.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">触发关键词：</label>

                            <div class="col-md-6">
                                <input name="triggerKeyword" class="form-control"
                                       value=""/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">链接地址：</label>

                            <div class="col-md-6">
                                <input name="triggerUrl" class="form-control" value=""/>
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

<!--如果只是做布局的话不需要下面两个引用-->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script src="${resourceUrl}/js/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"
        type="text/javascript"></script>

<script>

    var ajaxParams = {};
    jQuery(document).ready(function () {
        //        TableAjax.init();
        window.menuList = null;
        $('input[name=displayOrder]').TouchSpin({
                                                    min: 0,
                                                    max: 1000000000,
                                                    step: 1,
                                                    maxboostedstep: 100,
                                                    prefix: '序号'
                                                });

        $('#btnNewMenu').on('click', function () {
            reset();
            $('#dlgEditModel').modal('show');
        });
        $('#btnGenerateMenu').on('click', function () {
        //    var weixinBody = buildWeixinBody($.parseJSON('${menuJson}'));
            var weixinBody = buildWeixinBody(eval('${menuJson}'));
            var errText = checkLimit(weixinBody);
            if (errText) {
                alert(errText);
                return false;
            }
            if (!confirm('确定生成公众号菜单？')) {
                return false;
            }
            var url = '';
            if (weixinBody.button.length > 0) {
                url = '/manage/weixin/menu/createWeixinMenu';
                postWeixinMenu(url, weixinBody);
            } else{
                alert("没有父菜单");
            }


        });
        $('#btnDeleteMenu').on('click', function () {
            if (!confirm('确定删除公众号菜单？')) {
                return false;
            }
            $.ajax({
                       type: "post",
                       url: "/manage/weixin/menu/deleteWeixinMenu",
                       contentType: "application/json",
                       success: function (data) {
                           alert(data.msg);
                       }
                   });

        });

        $('#btnSubmitForm').on('click', function () {
            var menuId = $('input[name=menuId]').val();
            var displayOrder = $('input[name=displayOrder]').val();
            var name = $('input[name=name]').val();
            var triggerKeyword = $('input[name=triggerKeyword]').val();
            var triggerUrl = $('input[name=triggerUrl]').val();
            var parentMenuId = $('select[name=parentMenu]').val();
            var menu = {};
            var parentMenu = {};
            parentMenu.id = parentMenuId;
            menu.name = name;
            menu.displayOrder = displayOrder;
            menu.triggerKeyword = triggerKeyword;
            menu.triggerUrl = triggerUrl;
            menu.id = menuId;
            menu.parentMenu = parentMenu;
            $.ajax({
                       type: "post",
                       url: "/manage/weixin/menu/save",
                       contentType: "application/json",
                       data: JSON.stringify(menu),
                       success: function (data) {
                           alert(data.msg);
                           setTimeout(function () {
                               location.reload(true);
                           }, 500);
                       }
                   });
        });
    });

    function edit(menuId) {
        reset();
//        var d = $.parseJSON('${menuJson}');
        var d = eval('${menuJson}');
        $('input[name=menuId]').val(menuId);
        //                    取出menu数据
        var menu = null;
        for (var i = 0; i < d.length; i++) {
            var id = d[i].id;
            if (id == menuId) {
                menu = d[i];
                break;
            }
        }
        if (!menu) {
            alert('ERROR-菜单未找到！');
            return;
        }
        //                    select中去掉当前menu
        $('select[name=parentMenu] option').each(function () {
            if ($(this).attr('value') == menuId) {
                $(this).prop('disabled', true);
            }
        });
        $('input[name=displayOrder]').val(menu.displayOrder);
        $('input[name=name]').val(menu.name);
        $('input[name=triggerKeyword]').val(menu.triggerKeyword);
        $('input[name=triggerUrl]').val(menu.triggerUrl);
        if (menu.parentMenu != null) {
            $('select[name=parentMenu]').val(menu.parentMenu.id);
        }

        $('#dlgEditModel').modal("show");
    }

    function deleteMenu(menuId) {

       // var d = $.parseJSON('${menuJson}');
        var d = eval('${menuJson}');
//                    判断是否有下级菜单，如果有，则不允许删除
        var hasChildren = false;
        for (var i = 0; i < d.length; i++) {
            if (d[i].parentMenu != null) {
                if (d[i].parentMenu.id == menuId) {
                    hasChildren = true;
                    break;
                }
            }
        }
        if (hasChildren) {
            alert('该菜单下还有子菜单，不允许直接删除！');
            return false;
        }
        if (!confirm('确定删除该菜单？')) {
            return false;
        }
        if (menuId) {
            $.ajax({
                       type: "delete",
                       url: "/manage/weixin/menu/delete/" + menuId,
                       success: function (data) {
                           alert(data.msg);
                           setTimeout(function () {
                               location.reload(true);
                           }, 500);
                       }
                   });
        }
    }

    function reset() {
        $('input[name=menuId]').val('');
        $('select[name=parentMenu]').val('');
        $('input[name=displayOrder]').val(0);
        $('input[name=name]').val('');
        $('input[name=triggerKeyword]').val('');
        $('input[name=triggerUrl]').val('');

        $('select[name=parentMenu] option:disabled').prop('disabled', false);
        $('select[name=parentMenu]').val('');
    }

    function buildWeixinBody(orgObj) {
        var wxBtnList = [];
        var topBtns = [];
        //        先遍历取出一级菜单
        for (var i = 0; i < orgObj.length; i++) {
            var btn = orgObj[i];
            if (btn.parentMenu == null) {
                wxBtnList.push(buildBtn(btn));
                topBtns.push(btn);
            }
        }
        //        再遍历出微信二级菜单
        for (var i = 0; i < orgObj.length; i++) {
            for (var j = 0; j < topBtns.length; j++) {
                var parent = topBtns[j];
                var wxParent = wxBtnList[j];
                var btn = orgObj[i];
                if (btn.parentMenu != null) {
                    if (btn.parentMenu.id == parent.id) {
                        wxParent.sub_button.push(buildBtn(btn));
                    }
                }
            }
        }
        //        修正一级菜单，如果有子菜单，则删除type和key(或url)属性，如果没有子菜单，则删除sub_button属性
        for (var i = 0; i < wxBtnList.length; i++) {
            var btn = wxBtnList[i];
            if (btn.sub_button.length > 0) {
                delete btn.type;
                delete btn.key;
                delete btn.url;
            } else {
                delete btn.sub_button;
            }
        }
        return {
            button: wxBtnList
        }

    }

    function buildBtn(orgBtn) {
        var type = (orgBtn.triggerKeyword == null || orgBtn.triggerKeyword == "" ) ? "view"
                : "click";
        var wxBtn = {
            name: orgBtn.name
        };
        if (orgBtn.triggerKeyword != null && orgBtn.triggerKeyword != "") {
            wxBtn["type"] = "click";
            wxBtn["key"] = orgBtn.triggerKeyword;
        } else if (orgBtn.triggerUrl != null && orgBtn.triggerUrl != "") {
            wxBtn["type"] = "view";
            wxBtn["url"] = orgBtn.triggerUrl;
        }
        if (orgBtn.parentMenu == null) {
            wxBtn["sub_button"] = [];
        }
        return wxBtn;
    }

    function checkLimit(weixinBody) {
        var len = weixinBody.button.length;
        if (len > 3) {
            return '一级菜单数量不能超过三个（当前有' + len + '个），请修改！';
        } else {
            for (var i = 0; i < len; i++) {
                var topBtn = weixinBody.button[i];
                var topNameLength = getByteLength(topBtn.name);
                if (topNameLength > 8) {
                    return '一级菜单的名称不能超过4个汉字或者8个英文字母，菜单：“' + topBtn.name + '”的名称太长，请修改！';
                }
                var subList = topBtn.sub_button;
                if (subList) {
                    if (subList.length > 5) {
                        return '二级菜单数量不能超过五个（菜单：“' + topBtn.name + '”的二级菜单有' + subList.length
                               + '个），请修改！';
                    }
                    for (var j = 0; j < subList.length; j++) {
                        if (getByteLength(subList[j].name) > 20) {
                            return '二级菜单的名称不能超过10个汉字或者20个英文字母，菜单：“' + subList[j].name
                                   + '”的名称太长，请修改！';
                        }
                    }
                }
            }
        }
        return '';
    }

    function postWeixinMenu(url, weixinBody) {

        $.ajax({
                   type: "post",
                   url: url,
                   contentType: "application/json",
                   data: JSON.stringify(weixinBody),
                   success: function (data) {
                       alert(data.msg);
                   }
               });
    }

    function getByteLength(str) {
        var byteLen = 0, len = str.length;
        if (str) {
            for (var i = 0; i < len; i++) {
                if (str.charCodeAt(i) > 255) {
                    byteLen += 2;
                }
                else {
                    byteLen++;
                }
            }
            return byteLen;
        }
        else {
            return 0;
        }
    }


</script>
</body>
</html>

