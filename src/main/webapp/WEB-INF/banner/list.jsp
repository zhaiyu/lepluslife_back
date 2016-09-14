<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/8/29
  Time: 上午10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    <div class="form-group col-md-4">
                        <label for="date-end">创建时间</label>

                        <div id="date-end" class="form-control">
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                            <span id="searchDateRange"></span>
                            <b class="caret"></b>
                        </div>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="status">上架/下架</label>
                        <select class="form-control" id="status">
                            <option value="-1">全部分类</option>
                            <option value="1">上架</option>
                            <option value="0">下架</option>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="alive">当期/往期</label>
                        <select class="form-control" id="alive">
                            <option value="-1">全部分类</option>
                            <option value="1">当期</option>
                            <option value="0">往期</option>
                        </select></div>
                    <div class="form-group col-md-2">
                        <label for="city">所在城市</label>
                        <select class="form-control" id="city">
                            <option value="0">全部分类</option>
                            <c:forEach items="${cities}" var="city">
                                <option value="${city.id}">${city.name}</option>
                            </c:forEach>
                        </select></div>
                    <div class="form-group col-md-3">
                        <button class="btn btn-primary" style="margin-top: 24px"
                                onclick="searchByCriteria()">查询
                        </button>
                    </div>
                </div>

                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#tab1" data-toggle="tab" onclick="searchByType(1)">首页推荐</a></li>
                    <li class="active"><a href="#tab2" data-toggle="tab" onclick="searchByType(2)">臻品轮播图</a>
                    </li>
                    <li><a href="#tab3" data-toggle="tab" onclick="searchByType(3)">新品首发</a></li>
                    <li><a href="#tab4" data-toggle="tab" onclick="searchByType(4)">好货推荐</a></li>
                    <li><a href="#tab5" data-toggle="tab" onclick="searchByType(5)">好店推荐</a></li>
                    <li><a href="#tab6" data-toggle="tab" onclick="searchByType(6)">公告</a></li>
                    <li><a href="#tab7" data-toggle="tab" onclick="searchByType(7)">热门关键词</a></li>
                    <li><a href="#tab8" data-toggle="tab" onclick="searchByType(8)">商品分类</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <button type="button" class="btn btn-primary " style="margin:10px;"
                                id="add">新增首页推荐
                        </button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr id="tr" class="active">
                                <th>订单编号</th>
                                <th>订单类型</th>
                                <th>交易完成时间</th>
                                <th>商户名称</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="bannerContent">
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
    <%@include file="../common/bottom.jsp" %>
</div>

<!--添加或修改首页推荐项-->
<div class="modal" id="type1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <input name="bannerId1" type="hidden" class="o-input" value=""/>

                    <div class="form-group">
                        <label for="sid1" class="col-sm-3 control-label">序号</label>

                        <div class="col-sm-6">
                            <input name="bannerType" type="text" class="form-control o-input"
                                   id="sid1"
                                   value="1">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="bannerPicture1"
                               class="col-sm-3 control-label">图片(宽高比:710*230)</label>

                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="picture1">
                            <!--</div>-->
                            <input type="file" class="form-control" id="bannerPicture1" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="afterType1"
                               class="col-sm-3 control-label">后置类型(点击图片后的跳转情况)</label>

                        <div class="col-sm-6" id="afterType1">
                            <div>
                                <input type="radio" name="type1" class="checked1" checked="true"
                                       value="1"/><span>H5页面（跳转到一个H5页面）</span>
                                <input class="w-input" type="text" placeholder="请输入页面链接地址"/>
                                <input class="w-input" type="text" placeholder="请输入网页标题"/>
                            </div>
                            <div>
                                <input type="radio" name="type1"
                                       value="2"/><span>商品详情（跳转到臻品的详情页面）</span>
                                <input class="w-input" type="text" placeholder="请输入商品ID"/>
                            </div>
                            <div>
                                <input type="radio" name="type1"
                                       value="3"/><span>店铺详情（跳转到店铺的详情页面）</span>
                                <input class="w-input" type="text" placeholder="请输入商户序号"/>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit1">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--添加或修改臻品轮播图-->
<div class="modal" id="type2">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <input name="bannerId2" type="hidden" class="o-input" value=""/>

                    <div class="form-group">
                        <label for="sid2" class="col-sm-3 control-label">臻品序号</label>

                        <div class="col-sm-6">
                            <input name="bannerType" type="text" class="form-control o-input"
                                   id="sid2"
                                   value="1">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="bannerPicture2"
                               class="col-sm-3 control-label">图片(宽高比:750*400)</label>

                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="picture2">
                            <!--</div>-->
                            <input type="file" class="form-control" id="bannerPicture2" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="afterType2"
                               class="col-sm-3 control-label">后置类型(点击图片后的跳转情况)</label>

                        <div class="col-sm-6" id="afterType2">
                            <div>
                                <input type="radio" name="type2" class="checked2" checked="true"
                                       value="1"/><span>H5页面（跳转到一个H5页面）</span>
                                <input class="w-input" type="text" placeholder="请输入页面链接地址"/>
                                <input class="w-input" type="text" placeholder="请输入网页标题"/>
                            </div>
                            <div>
                                <input type="radio" name="type2"
                                       value="2"/><span>商品详情（跳转到臻品的详情页面）</span>
                                <input class="w-input" type="text" placeholder="请输入商品ID"/>
                            </div>
                            <div>
                                <input type="radio" name="type2"
                                       value="3"/><span>店铺详情（跳转到店铺的详情页面）</span>
                                <input class="w-input" type="text" placeholder="请输入商户序号"/>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit2">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--添加或修改新品首发-->
<div class="modal" id="type3">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <input name="bannerId3" type="hidden" class="o-input" value=""/>

                    <div class="form-group">
                        <label for="sid3" class="col-sm-3 control-label">首发序号</label>

                        <div class="col-sm-6">
                            <input name="bannerType" type="text" class="form-control o-input"
                                   id="sid3"
                                   value="1">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="bannerPicture3"
                               class="col-sm-3 control-label">图片(宽高比:325*400)</label>

                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="picture3">
                            <!--</div>-->
                            <input type="file" class="form-control" id="bannerPicture3" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="afterType3"
                               class="col-sm-3 control-label">后置类型(点击图片后的跳转情况)</label>

                        <div class="col-sm-6" id="afterType3">
                            <div>
                                <input type="radio" name="type3" class="checked3" checked="true"
                                       value="1"/><span>H5页面（跳转到一个H5页面）</span>
                                <input class="w-input" type="text" placeholder="请输入页面链接地址"/>
                                <input class="w-input" type="text" placeholder="请输入网页标题"/>
                            </div>
                            <div>
                                <input type="radio" name="type3"
                                       value="2"/><span>商品详情（跳转到臻品的详情页面）</span>
                                <input class="w-input" type="text" placeholder="请输入商品ID"/>
                            </div>
                            <div>
                                <input type="radio" name="type3"
                                       value="3"/><span>店铺详情（跳转到店铺的详情页面）</span>
                                <input class="w-input" type="text" placeholder="请输入商户序号"/>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit3">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--添加或修改好货推荐-->
<div class="modal" id="type4">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <input name="bannerId4" type="hidden" class="o-input" value=""/>

                    <div class="form-group">
                        <label for="sid4" class="col-sm-3 control-label">好货序号</label>

                        <div class="col-sm-6">
                            <input name="bannerType" type="text" class="form-control o-input"
                                   id="sid4"
                                   value="1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bannerPicture4"
                               class="col-sm-3 control-label">当期图片(宽高比:710*300)</label>

                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="picture4">
                            <!--</div>-->
                            <input type="file" class="form-control" id="bannerPicture4" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="oldPicture4"
                               class="col-sm-3 control-label">往期图片(宽高比:750*200)</label>

                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="oldPicture4">
                            <!--</div>-->
                            <input type="file" class="form-control" id="oldBannerPicture4"
                                   name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="afterType4"
                               class="col-sm-3 control-label">后置类型(点击图片后的跳转情况)</label>

                        <div class="col-sm-6" id="afterType4">
                            <div>
                                <input type="radio" name="type4" class="checked4" checked="true"
                                       value="1"/><span>H5页面（跳转到一个H5页面）</span>
                                <input class="w-input" type="text" placeholder="请输入页面链接地址"/>
                                <input class="w-input" type="text" placeholder="请输入网页标题"/>
                            </div>
                            <div>
                                <input type="radio" name="type4"
                                       value="2"/><span>商品详情（跳转到臻品的详情页面）</span>
                                <input class="w-input" type="text" placeholder="请输入商品ID"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productName" class="col-sm-3 control-label">商品名称</label>

                        <div class="col-sm-6">
                            <input name="productName" type="text" class="form-control o-input"
                                   id="productName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="productPrice" class="col-sm-3 control-label">商品价格</label>

                        <div class="col-sm-6">
                            <input name="productPrice" type="text" class="form-control o-input"
                                   id="productPrice"
                                   value="0">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="introduce4" class="col-sm-3 control-label">介绍文案</label>

                        <div class="col-sm-6">
                            <textarea name="introduce4" class="form-control"
                                      id="introduce4" placeholder="介绍一下..."></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit4">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--添加或修改好店推荐-->
<div class="modal" id="type5">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <input name="bannerId5" type="hidden" class="o-input" value=""/>

                    <div class="form-group">
                        <label for="sid5" class="col-sm-3 control-label">好店序号</label>

                        <div class="col-sm-6">
                            <input name="bannerType" type="text" class="form-control o-input"
                                   id="sid5"
                                   value="1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bannerPicture5"
                               class="col-sm-3 control-label">当期图片(宽高比:710*300)</label>

                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="picture5">
                            <!--</div>-->
                            <input type="file" class="form-control" id="bannerPicture5" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="oldPicture5"
                               class="col-sm-3 control-label">往期图片(宽高比:750*200)</label>

                        <div class="col-sm-6">
                            <img style="width: 100%" src="" alt="..." id="oldPicture5">
                            <input type="file" class="form-control" id="oldBannerPicture5"
                                   name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="afterType5"
                               class="col-sm-3 control-label">后置类型(点击图片后的跳转情况)</label>

                        <div class="col-sm-6" id="afterType5">
                            <div>
                                <input type="radio" name="type5" class="checked5" checked="true"
                                       value="1"/><span>H5页面（跳转到一个H5页面）</span>
                                <input class="w-input" type="text" placeholder="请输入页面链接地址"/>
                                <input class="w-input" type="text" placeholder="请输入网页标题"/>
                            </div>
                            <div>
                                <input type="radio" name="type5" value="3"/><span>商家详情</span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="merchantSid5"
                               class="col-sm-3 control-label">商户序号</label>

                        <div class="col-sm-6">
                            <input name="merchantSid5" type="text" class="form-control o-input"
                                   id="merchantSid5" placeholder="请输入商户序号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="title5" class="col-sm-3 control-label">宣传口号</label>

                        <div class="col-sm-6">
                            <textarea name="title5" class="form-control"
                                      id="title5" placeholder="请输入不超过20字的宣传口号"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="introduce5" class="col-sm-3 control-label">介绍文案</label>

                        <div class="col-sm-6">
                            <textarea name="introduce5" class="form-control"
                                      id="introduce5" placeholder="介绍一下..."></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit5">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--修改公告内容-->
<div class="modal" id="type6">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="content6" class="col-sm-3 control-label">公告内容</label>

                        <div class="col-sm-6">
                            <textarea name="content6" class="form-control" rows="5"
                                      id="content6"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit6">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--修改热门关键词-->
<div class="modal" id="type7">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span>
                    <span class="sr-only">Close</span></button>
                <h4 class="modal-title"><span id="cityName"></span>--热门关键词</h4>
            </div>
            <div class="modal-body">
                <input name="cityId" id="cityId" type="hidden" class="o-input" value=""/>

                <div id="addList">
                </div>
                <button class="w-add">+添加新关键词</button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit7">确认
                </button>
            </div>
        </div>
    </div>
</div>
<!--添加或修改商品类别-->
<div class="modal" id="type8">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <input name="typeId" type="hidden" class="o-input" value=""/>

                    <div class="form-group">
                        <label for="typeName"
                               class="col-sm-3 control-label">类别名称</label>

                        <div class="col-sm-6">
                            <input name="afterUrl5" type="text" class="form-control o-input"
                                   id="typeName" placeholder="请输入类别名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bannerPicture8"
                               class="col-sm-3 control-label">类别图片(宽高比:750*200)</label>

                        <div class="col-sm-6">
                            <!--<div class="thumbnail">-->
                            <img style="width: 100%" src="" alt="..." id="picture8">
                            <!--</div>-->
                            <input type="file" class="form-control" id="bannerPicture8" name="file"
                                   data-url="/manage/file/saveImage"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="bannerSubmit8">确认
                </button>
            </div>
        </div>
    </div>
</div>

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
    $("input[name=type1]").click(function (e) {
        $("input[type=radio]").removeClass("checked1");
        $(this).attr("class", "checked1");
        $(".w-input").attr("disabled", "true");
        $(this).nextAll().removeAttr("disabled");
    });
    $("input[name=type2]").click(function (e) {
        $("input[type=radio]").removeClass("checked2");
        $(this).attr("class", "checked2");
        $(".w-input").attr("disabled", "true");
        $(this).nextAll().removeAttr("disabled");
    });
    $("input[name=type3]").click(function (e) {
        $("input[type=radio]").removeClass("checked3");
        $(this).attr("class", "checked3");
        $(".w-input").attr("disabled", "true");
        $(this).nextAll().removeAttr("disabled");
    });
    $("input[name=type4]").click(function (e) {
        $("input[type=radio]").removeClass("checked4");
        $(this).attr("class", "checked4");
        $(".w-input").attr("disabled", "true");
        $(this).nextAll().removeAttr("disabled");
    });
    $("input[name=type5]").click(function (e) {
        $("input[type=radio]").removeClass("checked5");
        $(this).attr("class", "checked5");
        $(".w-input").attr("disabled", "true");
        $(this).nextAll().removeAttr("disabled");
    });
</script>
<script>
    var bannerCriteria = {};
    var flag = true;
    var init1 = 0;
    var bannerContent = document.getElementById("bannerContent");
    var tr = document.getElementById("tr");

    $(function () {
        // tab切换
        var tableType = "${type}";
        if (tableType == 1) {
            $('#myTab li:eq(0) a').tab('show');
        } else if (tableType == 2) {
            $('#myTab li:eq(1) a').tab('show');
        } else if (tableType == 3) {
            $('#myTab li:eq(2) a').tab('show');
        } else if (tableType == 4) {
            $('#myTab li:eq(3) a').tab('show');
        } else if (tableType == 5) {
            $('#myTab li:eq(4) a').tab('show');
        } else if (tableType == 6) {
            $('#myTab li:eq(5) a').tab('show');
        } else if (tableType == 7) {
            $('#myTab li:eq(6) a').tab('show');
        } else if (tableType == 8) {
            $('#myTab li:eq(7) a').tab('show');
        }

        $('input[name=bannerType]').TouchSpin({
                                                  min: 1,
                                                  max: 100,
                                                  step: 1,
                                                  decimals: 0, //精度
                                                  maxboostedstep: 100,
                                                  prefix: ''
                                              });
        $('input[name=productPrice]').TouchSpin({
                                                    min: 0,
                                                    max: 10000,
                                                    step: 0.01,
                                                    decimals: 2, //精度
                                                    maxboostedstep: 100,
                                                    prefix: '￥'
                                                });

        $('#bannerPicture1').fileupload({
                                            dataType: 'json',
                                            maxFileSize: 5000000,
                                            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                            add: function (e, data) {
                                                data.submit();
                                            },
                                            done: function (e, data) {
                                                var resp = data.result;
                                                $('#picture1').attr('src',
                                                                    '${ossImageReadRoot}/'
                                                                    + resp.data);
                                            }
                                        });
        $('#bannerPicture2').fileupload({
                                            dataType: 'json',
                                            maxFileSize: 5000000,
                                            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                            add: function (e, data) {
                                                data.submit();
                                            },
                                            done: function (e, data) {
                                                var resp = data.result;
                                                $('#picture2').attr('src',
                                                                    '${ossImageReadRoot}/'
                                                                    + resp.data);
                                            }
                                        });
        $('#bannerPicture3').fileupload({
                                            dataType: 'json',
                                            maxFileSize: 5000000,
                                            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                            add: function (e, data) {
                                                data.submit();
                                            },
                                            done: function (e, data) {
                                                var resp = data.result;
                                                $('#picture3').attr('src',
                                                                    '${ossImageReadRoot}/'
                                                                    + resp.data);
                                            }
                                        });
        $('#bannerPicture4').fileupload({
                                            dataType: 'json',
                                            maxFileSize: 5000000,
                                            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                            add: function (e, data) {
                                                data.submit();
                                            },
                                            done: function (e, data) {
                                                var resp = data.result;
                                                $('#picture4').attr('src',
                                                                    '${ossImageReadRoot}/'
                                                                    + resp.data);
                                            }
                                        });
        $('#oldBannerPicture4').fileupload({
                                               dataType: 'json',
                                               maxFileSize: 5000000,
                                               acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                               add: function (e, data) {
                                                   data.submit();
                                               },
                                               done: function (e, data) {
                                                   var resp = data.result;
                                                   $('#oldPicture4').attr('src',
                                                                          '${ossImageReadRoot}/'
                                                                          + resp.data);
                                               }
                                           });
        $('#bannerPicture5').fileupload({
                                            dataType: 'json',
                                            maxFileSize: 5000000,
                                            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                            add: function (e, data) {
                                                data.submit();
                                            },
                                            done: function (e, data) {
                                                var resp = data.result;
                                                $('#picture5').attr('src',
                                                                    '${ossImageReadRoot}/'
                                                                    + resp.data);
                                            }
                                        });
        $('#oldBannerPicture5').fileupload({
                                               dataType: 'json',
                                               maxFileSize: 5000000,
                                               acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                               add: function (e, data) {
                                                   data.submit();
                                               },
                                               done: function (e, data) {
                                                   var resp = data.result;
                                                   $('#oldPicture5').attr('src',
                                                                          '${ossImageReadRoot}/'
                                                                          + resp.data);
                                               }
                                           });
        $('#bannerPicture8').fileupload({
                                            dataType: 'json',
                                            maxFileSize: 5000000,
                                            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                                            add: function (e, data) {
                                                data.submit();
                                            },
                                            done: function (e, data) {
                                                var resp = data.result;
                                                $('#picture8').attr('src',
                                                                    '${ossImageReadRoot}/'
                                                                    + resp.data);
                                            }
                                        });

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

        bannerCriteria.offset = 1;
        bannerCriteria.type = tableType;
        getBannerListByType(bannerCriteria);
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
    $(".w-add").click(function (e) {
        $("#addList").append(
                $("<div></div>").attr("class", "w-hot").append(
                        $("<input>").attr("type", "text")
                ).append(
                        $("<button></button>").attr("class", "del btn").attr("onclick",
                                                                             "del(this);").html("删除")
                )
        )
    });
    function del(e) {
        var div = e.closest('.w-hot');
        div.remove();
    }
    function resetAndShow(num) {
        var iid = "type" + num;
        var sid = "sid" + num;
        $("#" + iid + " .w-input").val("");
        $("#" + iid + " .o-input").val("");
        $("#" + sid + "").val(1);
        $("#" + iid + " img").attr("src", "");
        $("#" + iid + " textarea").val("");
        $("#" + iid + "").modal("show");
    }
    $('#bannerSubmit1').on('click', function () {
        var banner = {}, bannerType = {};
        var picture = $("#picture1").attr("src");
        var input = $(".checked1");
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
        if (afterType_val == null || afterType_val == "") {
            alert("请补全类型信息");
            return false;
        }
        bannerType.id = 1;
        banner.id = $('input[name=bannerId1]').val();
        banner.sid = $('#sid1').val();
        banner.picture = picture;
        banner.bannerType = bannerType;
        banner.afterType = afterType;
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
                   url: "/manage/banner/save",
                   contentType: "application/json",
                   data: JSON.stringify(banner),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           location.href = "/manage/banner?type=1"
                       } else {
                           alert(data.status);
                       }
                   }
               });
    });
    $('#bannerSubmit2').on('click', function () {
        var banner = {}, bannerType = {};
        var picture = $("#picture2").attr("src");
        var input = $(".checked2");
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
        if (afterType_val == null || afterType_val == "") {
            alert("请补全类型信息");
            return false;
        }
        bannerType.id = 2;
        banner.id = $('input[name=bannerId2]').val();
        banner.sid = $('#sid2').val();
        banner.picture = picture;
        banner.bannerType = bannerType;
        banner.afterType = afterType;
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
                   url: "/manage/banner/save",
                   contentType: "application/json",
                   data: JSON.stringify(banner),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           location.href = "/manage/banner?type=2"
                       } else {
                           alert(data.status);
                       }
                   }
               });
    });
    $('#bannerSubmit3').on('click', function () {
        var banner = {}, bannerType = {};
        var picture = $("#picture3").attr("src");
        var input = $(".checked3");
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
        if (afterType_val == null || afterType_val == "") {
            alert("请补全类型信息");
            return false;
        }
        bannerType.id = 3;
        banner.id = $('input[name=bannerId3]').val();
        banner.sid = $('#sid3').val();
        banner.picture = picture;
        banner.bannerType = bannerType;
        banner.afterType = afterType;
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
                   url: "/manage/banner/save",
                   contentType: "application/json",
                   data: JSON.stringify(banner),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           location.href = "/manage/banner?type=3"
                       } else {
                           alert(data.status);
                       }
                   }
               });
    });
    $('#bannerSubmit4').on('click', function () {
        var banner = {}, bannerType = {};
        var picture = $("#picture4").attr("src");
        var oldPicture = $("#oldPicture4").attr("src");
        var introduce = $("#introduce4").val();
        var title = $('input[name=productName]').val();
        var price = $('input[name=productPrice]').val();
        var input = $(".checked4");
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
            alert("请上传当期图片");
            return false;
        }
        if (oldPicture == null || oldPicture == "") {
            alert("请上传往期图片");
            return false;
        }
        if (afterType == null) {
            alert("请选择一种后置类型");
            return false;
        }
        if (afterType_val == null || afterType_val == "") {
            alert("请补全类型信息");
            return false;
        }
        if (title == null || title == "") {
            alert("请输入商品名称");
            return false;
        }
        if (price == null || price == "") {
            alert("请输入商品价格");
            return false;
        }
        if (introduce == null || introduce == "") {
            alert("请输入介绍文案");
            return false;
        }
        bannerType.id = 4;
        banner.id = $('input[name=bannerId4]').val();
        banner.sid = $('#sid4').val();
        banner.picture = picture;
        banner.oldPicture = oldPicture;
        banner.introduce = introduce;
        banner.title = title;
        banner.price = price * 100;
        banner.bannerType = bannerType;
        banner.afterType = afterType;
        if (afterType == 1) {
            banner.url = afterType_val;
            banner.urlTitle = urlTitle;
        } else if (afterType == 2) {
            var product = {};
            product.id = afterType_val;
            banner.product = product;
        }
        $.ajax({
                   type: "post",
                   url: "/manage/banner/save",
                   contentType: "application/json",
                   data: JSON.stringify(banner),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           location.href = "/manage/banner?type=4"
                       } else {
                           alert(data.status);
                       }
                   }
               });
    });
    $('#bannerSubmit5').on('click', function () {
        var banner = {}, bannerType = {}, merchant = {};
        var picture = $("#picture5").attr("src");
        var oldPicture = $("#oldPicture5").attr("src");
        var introduce = $("#introduce5").val();
        var title = $('#title5').val();
        var input = $(".checked5");
        var afterType = input.val();
        var merchantSid = $('input[name=merchantSid5]').val();
        var urlTitle = "", url = "";
        if (afterType == 1) {
            url = input.next().next().val();
            urlTitle = input.next().next().next().val();
            if (url == null || url == "") {
                alert("请输入页面链接");
                return false;
            }
            if (urlTitle == null || urlTitle == "") {
                alert("请输入页面标题");
                return false;
            }
        }
        if (picture == null || picture == "") {
            alert("请上传当期图片");
            return false;
        }
        if (oldPicture == null || oldPicture == "") {
            alert("请上传往期图片");
            return false;
        }
        if (merchantSid == null || merchantSid == "") {
            alert("请输入商户序号");
            return false;
        }
        if (title == null || title == "") {
            alert("请输入宣传口号");
            return false;
        }
        if (introduce == null || introduce == "") {
            alert("请输入介绍文案");
            return false;
        }
        bannerType.id = 5;
        banner.bannerType = bannerType;
        banner.id = $('input[name=bannerId5]').val();
        banner.sid = $('#sid5').val();
        banner.picture = picture;
        banner.oldPicture = oldPicture;
        banner.afterType = 1;
        merchant.merchantSid = merchantSid;
        banner.merchant = merchant;
        banner.title = title;
        banner.introduce = introduce;
        banner.afterType = afterType;
        if (afterType == 1) {
            banner.url = url;
            banner.urlTitle = urlTitle;
        }
        $.ajax({
                   type: "post",
                   url: "/manage/banner/save",
                   contentType: "application/json",
                   data: JSON.stringify(banner),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           location.href = "/manage/banner?type=5"
                       } else {
                           alert(data.status);
                           $("#type5").modal("show");
                       }
                   }
               });
    });
    $('#bannerSubmit6').on('click', function () {
        var content = $('#content6').val();
        if (content == null || content == "") {
            alert("请输入公告内容");
            return false;
        }
        $.ajax({
                   type: "post",
                   url: "/manage/banner/saveNotice",
                   contentType: "application/json",
                   data: content,
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           location.href = "/manage/banner?type=6"
                       } else {
                           alert(data.msg);
                           $("#type6").modal("show");
                       }
                   }
               });
    });
    $('#bannerSubmit7').on('click', function () {
        var inputs = document.getElementById('addList').getElementsByTagName('input');
        var list = [], city = {};
        city.id = $("#cityId").val();
        for (i = 0; i < inputs.length; i++) {
            console.log(inputs[i].value);
            console.log(inputs[i].id);
            var banner = {};
            banner.city = city;
            banner.title = inputs[i].value;
            if (inputs[i].id != null && inputs[i].id != "") {
                banner.id = inputs[i].id.split("_")[1];
            }
            list[i] = banner;
        }
        $.ajax({
                   type: "post",
                   url: "/manage/banner/saveHotWord",
                   contentType: "application/json",
                   data: JSON.stringify(list),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           location.href = "/manage/banner?type=7"
                       } else {
                           alert(data.status);
                           $("#type7").modal("show");
                       }
                   }
               });
    });
    $('#bannerSubmit8').on('click', function () {
        var productType = {};
        var picture = $("#picture8").attr("src");
        var type = $('#typeName').val();

        if (picture == null || picture == "") {
            alert("请上传图片");
            return false;
        }
        if (type == null || type == "") {
            alert("请输入类型名称");
            return false;
        }

        productType.id = $('input[name=typeId]').val();
        productType.type = type;
        productType.picture = picture;
        $.ajax({
                   type: "post",
                   url: "/manage/banner/saveProductType",
                   contentType: "application/json",
                   data: JSON.stringify(productType),
                   success: function (data) {
                       if (data.status == 200) {
                           alert("ok");
                           location.href = "/manage/banner?type=8"
                       } else {
                           alert(data.status);
                           $("#type8").modal("show");
                       }
                   }
               });
    });
    function searchByType(type) {
        bannerCriteria.offset = 1;
        if (type != null) {
            bannerCriteria.type = type;
        } else {
            bannerCriteria.type = 1;
        }
        flag = true;
        getBannerListByType(bannerCriteria);
    }

    function editAddButton(bannerType) {
        if (bannerType == 1) {
            $("#add").html("新建首页推荐项");
            setTimeout(function () {
                $('#add').unbind('click').bind('click', function () {
                    resetAndShow(bannerType);
                });
            }, 1);
        } else if (bannerType == 2) {
            $("#add").html("新建臻品轮播图");
            setTimeout(function () {
                $('#add').unbind('click').bind('click', function () {
                    resetAndShow(bannerType);
                });
            }, 1);
        } else if (bannerType == 3) {
            $("#add").html("新建新品首发");
            setTimeout(function () {
                $('#add').unbind('click').bind('click', function () {
                    resetAndShow(bannerType);
                });
            }, 1);
        } else if (bannerType == 4) {
            $("#add").html("新建好货推荐");
            setTimeout(function () {
                $('#add').unbind('click').bind('click', function () {
                    resetAndShow(bannerType);
                });
            }, 1);
        } else if (bannerType == 5) {
            $("#add").html("新建好店推荐");
            setTimeout(function () {
                $('#add').unbind('click').bind('click', function () {
                    resetAndShow(bannerType);
                });
            }, 1);
        } else if (bannerType == 6) {
            $("#add").html("修改公告内容");
            setTimeout(function () {
                $('#add').unbind('click').bind('click', function () {
                    $.ajax({
                               type: "get",
                               url: "/manage/banner/notice",
                               contentType: "application/json",
                               success: function (data) {
                                   $("#content6").val(data.data);
                                   $("#type6").modal("show");
                               }
                           });
                });
            }, 1);
        } else if (bannerType == 7) {
            $("#add").html("不可点击");
            $('#add').unbind('click')
        } else if (bannerType == 8) {
            $("#add").html("新建分类");
            setTimeout(function () {
                $('#add').unbind('click').bind('click', function () {
                    resetAndShow(bannerType);
                });
            }, 1);
        }
    }

    function getBannerListByType(criteria) {
        bannerContent.innerHTML = "";
        var type = criteria.type;
        editAddButton(type);
        if (type == 1 || type == 2 || type == 3) {  //首页，臻品，新品
            tr.innerHTML =
            "<th>序号</th><th>图片</th><th>后置类型</th><th>点击次数</th><th>状态</th><th>操作</th>";
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

                           for (i = 0; i < content.length; i++) {
                               var contentStr = '<tr><td>' + content[i].sid + '</td>';
                               contentStr +=
                               '<td><img style="height: 200px" src="' + content[i].picture
                               + '" alt="..."></td>';
                               if (content[i].afterType == 1) {
                                   contentStr += '<td><span>H5页面</span></td>';
                               } else if (content[i].afterType == 2) {
                                   contentStr += '<td><span>商品详情</span></td>';
                               } else if (content[i].afterType == 3) {
                                   contentStr += '<td><span>店铺详情</span></td>';
                               } else {
                                   contentStr += '<td><span>未知</span></td>';
                               }
                               contentStr += '<td><span>待定</span></td>';
                               if (content[i].status == 1) {
                                   contentStr += '<td><span>上架</span></td>';
                                   contentStr +=
                                   '<td><input type="hidden" class="id-hidden" value="'
                                   + content[i].id
                                   + '"><button class="btn btn-primary changeStatus">设为下架</button>'
                                   +
                                   '<button class="btn btn-primary editBanner">编辑</button></td></tr>';
                               } else {
                                   contentStr +=
                                   '<td><span style="color: red">下架</span></td>';
                                   contentStr +=
                                   '<td><input type="hidden" class="id-hidden" value="'
                                   + content[i].id
                                   + '"><button class="btn btn-primary changeStatus">设为上架</button>'
                                   +
                                   '<button class="btn btn-primary editBanner">编辑</button></td></tr>';
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
                                                      $("#" + model + "").modal("show");
                                                  } else {
                                                      alert("服务器异常");
                                                  }
                                              }
                                          });

                               });
                           });
                       }
                   });
        } else if (type == 4) { //4=好货推荐
            tr.innerHTML =
            "<th>序号</th><th>当期图片</th><th>往期图片</th><th>后置类型</th><th>状态</th><th>商品名称</th><th>价格</th><th>点击次数</th><th>状态</th><th>操作</th>";
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

                           for (i = 0; i < content.length; i++) {
                               var contentStr = '<tr><td>' + content[i].sid + '</td>';
                               contentStr +=
                               '<td><img style="height: 100px" src="' + content[i].picture
                               + '" alt="..."></td>';
                               contentStr +=
                               '<td><img style="height: 100px" src="' + content[i].oldPicture
                               + '" alt="..."></td>';
                               if (content[i].afterType == 1) {
                                   contentStr += '<td><span>H5页面</span></td>';
                               } else if (content[i].afterType == 2) {
                                   contentStr += '<td><span>商品详情</span></td>';
                               } else if (content[i].afterType == 3) {
                                   contentStr += '<td><span>店铺详情</span></td>';
                               } else {
                                   contentStr += '<td><span>未知</span></td>';
                               }
                               if (content[i].alive == 1) {
                                   contentStr += '<td><span>本期推荐</span></td>';
                               } else {
                                   contentStr +=
                                   '<td><span style="color: red">往期推荐</span></td>';
                               }
                               contentStr +=
                               '<td><span>' + content[i].title + '</span></td>';
                               contentStr +=
                               '<td><span>￥' + content[i].price / 100 + '</span></td>';
                               contentStr += '<td><span>待定</span></td>';
                               if (content[i].status == 1) {
                                   contentStr += '<td><span>上架</span></td>';
                                   contentStr +=
                                   '<td><input type="hidden" class="id-hidden" value="'
                                   + content[i].id
                                   + '"><button class="btn btn-primary changeStatus">设为下架</button>';
                               } else {
                                   contentStr +=
                                   '<td><span style="color: red">下架</span></td>';
                                   contentStr +=
                                   '<td><input type="hidden" class="id-hidden" value="'
                                   + content[i].id
                                   + '"><button class="btn btn-primary changeStatus">设为上架</button>';
                               }
                               if (content[i].alive == 1) {
                                   contentStr +=
                                   '<button class="btn btn-primary changeAlive">设为往期推荐</button>';
                               } else {
                                   contentStr +=
                                   '<button class="btn btn-primary changeAlive">设为本期推荐</button>';
                               }
                               contentStr +=
                               '<button class="btn btn-primary editBanner">编辑</button></td></tr>';

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
                           $(".changeAlive").each(function (i) {
                               $(".changeAlive").eq(i).bind("click", function () {
                                   var id = $(this).parent().find(".id-hidden").val();

                                   if (!confirm("确定修改？")) {
                                       return false;
                                   }
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/banner/alive/" + id,
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
                                                      $('#sid4').val(banner.sid);
                                                      $('#productName').val(banner.title);
                                                      $('input[name="bannerId4"]').val(banner.id);
                                                      $("#picture4").attr("src",
                                                                          banner.picture);
                                                      $("#oldPicture4").attr("src",
                                                                             banner.oldPicture);

                                                      var radioChecked = $('#afterType4 input[type=radio][value='
                                                                           + afterType
                                                                           + ']');
                                                      $("#afterType4 input[type=radio]").removeClass("checked4");
                                                      $("#afterType4 input[type=radio]").removeAttr("checked");
                                                      radioChecked[0].checked = true;
                                                      radioChecked.attr("class", "checked4");
                                                      $(".w-input").attr("disabled", "true");
                                                      radioChecked.nextAll().removeAttr("disabled");

                                                      if (afterType == 1) {
                                                          radioChecked.next().next().val(banner.url);
                                                          radioChecked.next().next().next().val(banner.urlTitle)
                                                      } else if (afterType == 2) {
                                                          radioChecked.parent().find(".w-input").val(banner.product.id);
                                                      }
                                                      $("#introduce4").val(banner.introduce);
                                                      $("#productPrice").val(banner.price
                                                                             / 100);
                                                      $("#type4").modal("show");
                                                  } else {
                                                      alert("服务器异常");
                                                  }
                                              }
                                          });

                               });
                           });
                       }
                   });
        } else if (type == 5) { //5=好店推荐
            tr.innerHTML =
            "<th>序号</th><th>当期图片</th><th>往期图片</th><th>状态</th><th>店铺名称</th><th>所在城市</th><th>点击次数</th><th>状态</th><th>操作</th>";
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

                           for (i = 0; i < content.length; i++) {
                               var contentStr = '<tr><td>' + content[i].sid + '</td>';
                               contentStr +=
                               '<td><img style="height: 100px" src="' + content[i].picture
                               + '" alt="..."></td>';
                               contentStr +=
                               '<td><img style="height: 100px" src="' + content[i].oldPicture
                               + '" alt="..."></td>';
                               if (content[i].alive == 1) {
                                   contentStr += '<td><span>本期推荐</span></td>';
                               } else {
                                   contentStr +=
                                   '<td><span style="color: red">往期推荐</span></td>';
                               }
                               contentStr +=
                               '<td><span>' + content[i].merchant.name + '</span></td>';
                               contentStr +=
                               '<td><span>' + content[i].merchant.city.name + '</span></td>';
                               contentStr += '<td><span>待定</span></td>';
                               if (content[i].status == 1) {
                                   contentStr += '<td><span>上架</span></td>';
                                   contentStr +=
                                   '<td><input type="hidden" class="id-hidden" value="'
                                   + content[i].id
                                   + '"><button class="btn btn-primary changeStatus">设为下架</button>';
                               } else {
                                   contentStr +=
                                   '<td><span style="color: red">下架</span></td>';
                                   contentStr +=
                                   '<td><input type="hidden" class="id-hidden" value="'
                                   + content[i].id
                                   + '"><button class="btn btn-primary changeStatus">设为上架</button>';
                               }
                               if (content[i].alive == 1) {
                                   contentStr +=
                                   '<button class="btn btn-primary changeAlive">设为往期推荐</button>';
                               } else {
                                   contentStr +=
                                   '<button class="btn btn-primary changeAlive">设为本期推荐</button>';
                               }
                               contentStr +=
                               '<button class="btn btn-primary editBanner">编辑</button></td></tr>';
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
                           $(".changeAlive").each(function (i) {
                               $(".changeAlive").eq(i).bind("click", function () {
                                   var id = $(this).parent().find(".id-hidden").val();
                                   if (!confirm("确定修改？")) {
                                       return false;
                                   }
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/banner/alive/" + id,
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
                                                      var banner = data.data;
                                                      var afterType = banner.afterType;
                                                      $('input[name="bannerId5"]').val(banner.id);
                                                      $('#sid5').val(banner.sid);
                                                      $("#picture5").attr("src",
                                                                          banner.picture);
                                                      $("#oldPicture5").attr("src",
                                                                             banner.oldPicture);

                                                      var radioChecked = $('#afterType5 input[type=radio][value='
                                                                           + afterType
                                                                           + ']');
                                                      $("#afterType5 input[type=radio]").removeClass("checked5");
                                                      $("#afterType5 input[type=radio]").removeAttr("checked");
                                                      radioChecked[0].checked = true;
                                                      radioChecked.attr("class", "checked5");
                                                      $(".w-input").attr("disabled", "true");
                                                      radioChecked.nextAll().removeAttr("disabled");

                                                      if (afterType == 1) {
                                                          radioChecked.next().next().val(banner.url);
                                                          radioChecked.next().next().next().val(banner.urlTitle)
                                                      }
                                                      $('input[name=merchantSid5]').val(banner.merchant.merchantSid);
                                                      $('#title5').val(banner.title);
                                                      $("#introduce5").val(banner.introduce);
                                                      $("#type5").modal("show");
                                                  } else {
                                                      alert("服务器异常");
                                                  }
                                              }
                                          });

                               });
                           });
                       }
                   });
        } else if (type == 6) {  //6=公告
            tr.innerHTML = "<th>公告内容</th>";
            $.ajax({
                       type: "get",
                       url: "/manage/banner/notice",
                       async: false,
                       contentType: "application/json",
                       success: function (data) {
                           var content = data.data;
                           var totalPage = 1;
                           $("#totalElements").html(1);
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
                           bannerContent.innerHTML = '<tr><td>' + content + '</td></tr>';
                       }
                   });
        } else if (type == 7) { //7=热门关键词
            tr.innerHTML =
            "<th width='10%'>城市</th><th>关键词</th><th width='10%'>操作</th>";
            $.ajax({
                       type: "post",
                       url: "/manage/banner/hotWord",
                       async: false,
                       contentType: "application/json",
                       success: function (data) {
                           var content = data.data;
                           var elements = data.msg;
                           var totalPage = 1;
                           $("#totalElements").html(elements);
                           if (flag) {
                               flag = false;
                               initPage(criteria.offset, totalPage);
                           }
                           if (init1) {
                               initPage(1, totalPage);
                           }
                           for (i = 0; i < elements; i++) {
                               var contentStr = '<tr><td>' + content[i].cityName + '</td>';
                               var hotList = content[i].hotList;
                               var hotContent = '';
                               for (j = 0; j < hotList.length; j++) {
                                   hotContent += hotList[j] + "， ";
                               }
                               contentStr +=
                               '<td><span>' + hotContent + '</span></td>';
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="'
                               + content[i].cityId
                               + '"><button class="btn btn-primary editBanner">编辑</button></td></tr>';
                               bannerContent.innerHTML += contentStr;
                           }
                           $(".editBanner").each(function (i) {
                               $(".editBanner").eq(i).bind("click", function () {
                                   var id = $(this).parent().find(".id-hidden").val();
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/banner/findHot/" + id,
                                              contentType: "application/json",
                                              success: function (data) {
                                                  if (data.status == 200) {
                                                      $("#addList").html("");
                                                      var map = data.data;
                                                      $("#cityName").html(map.cityName);
                                                      $("#cityId").val(map.cityId);
                                                      var list = map.hotList;
                                                      for (j = 0; j < list.length; j++) {
                                                          var bannerId = "hotId_"
                                                                         + list[j].bannerId;
                                                          $("#addList").append(
                                                                  $("<div></div>").attr("class",
                                                                                        "w-hot").append(
                                                                          $("<input>").attr("type",
                                                                                            "text").attr("id",
                                                                                                         bannerId).val(list[j].hotName)
                                                                  ).append(
                                                                          $("<button></button>").attr("class",
                                                                                                      "del btn").attr("onclick",
                                                                                                                      "del(this);").html("删除")
                                                                  )
                                                          )
                                                      }
                                                      $("#type7").modal("show");
                                                  } else {
                                                      alert("服务器异常");
                                                  }
                                              }
                                          });
                               });
                           });
                       }
                   });
        } else if (type == 8) {  //8=商品类别编辑
            tr.innerHTML = "<th>类别ID</th><th>类别名称</th><th>类别图片</th><th>操作</th>";
            $.ajax({
                       type: "get",
                       url: "/manage/banner/productType",
                       async: false,
                       contentType: "application/json",
                       success: function (data) {
                           var content = data.data;
                           var totalPage = 1;
                           $("#totalElements").html(content.length);
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
                               var contentStr = '<tr><td>' + content[i].id + '</td>';
                               contentStr += '<td><span>' + content[i].type + '</span></td>';
                               contentStr +=
                               '<td><img style="height: 200px" src="' + content[i].picture
                               + '" alt="..."></td>';
                               contentStr +=
                               '<td><input type="hidden" class="id-hidden" value="'
                               + content[i].id
                               + '"><button class="btn btn-primary editBanner">编辑</button></td></tr>';
                               bannerContent.innerHTML += contentStr;
                           }
                           $(".editBanner").each(function (i) {
                               $(".editBanner").eq(i).bind("click", function () {
                                   var id = $(this).parent().find(".id-hidden").val();
                                   $.ajax({
                                              type: "get",
                                              url: "/manage/banner/findProductType/" + id,
                                              contentType: "application/json",
                                              success: function (data) {
                                                  if (data.status == 200) {
                                                      var productType = data.data;
                                                      $('#typeName').val(productType.type);
                                                      $('input[name="typeId"]').val(productType.id);
                                                      $("#picture8").attr("src",
                                                                          productType.picture);
                                                      $("#type8").modal("show");
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

    function searchByCriteria() {
        bannerCriteria.offset = 1;
        init1 = 1;
        var dateStr = $('#date-end span').text().split("-");
        if (dateStr != null && dateStr != '') {
            var startDate = dateStr[0].replace(/-/g, "/");
            var endDate = dateStr[1].replace(/-/g, "/");
            bannerCriteria.startDate = startDate;
            bannerCriteria.endDate = endDate;
        }
        if ($("#status").val() != -1) {
            bannerCriteria.status = $("#status").val();
        } else {
            bannerCriteria.status = null;
        }
        if ($("#alive").val() != -1) {
            bannerCriteria.alive = $("#alive").val();
        } else {
            bannerCriteria.alive = null;
        }
        if ($("#city").val() != 0) {
            bannerCriteria.city = $("#city").val();
        } else {
            bannerCriteria.city = null;
        }
        getBannerListByType(bannerCriteria);
    }
</script>
</body>
</html>

