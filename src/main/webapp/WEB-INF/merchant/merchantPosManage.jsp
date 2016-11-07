<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/9/7
  Time: 下午4:04
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <style>
        table tr td img,form img{width: 80px;height:80px;}
        .table>thead>tr>td, .table>thead>tr>th{line-height: 40px;}
        .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th{line-height: 60px;}
        .baseRate .col-sm-4 .form-group,.baseRate .col-sm-7 .form-group{margin-top: 20px}
        .baseRate0{display: block}
        .baseRate1,.baseRate2{display: none}
        .thumbnail {width: 160px;height: 160px;}
        .thumbnail img {width: 100%;height: 100%;}
        .pic-right-btn{position: relative;margin-left: 15px;}
        .pic-right-btn input[type='file']{width: 50px;height: 33px;position: absolute;opacity: 0;}
        #nav2 .form-group>label,#nav2 .form-group>div{margin-top: 40px;}
        #nav3 label{display: block;text-align: left;padding: 30px 20px}

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
    <script src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
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
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-4">
                        <button type="button" class="btn btn-primary btn-create" style="margin:10px;">返回商户管理</button>
                    </div>
                    <div class="col-md-4 text-center">
                        <h2><font color="#a52a2a">${merchantName}</font> - POS机管理<font size="3" color="red">(已认证)</font></h2>
                    </div>
                </div>
                <hr>
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#nav1" data-toggle="tab">进件管理</a></li>
                    <li><a href="#nav2" data-toggle="tab">营业执照</a></li>
                    <li><a href="#nav3" data-toggle="tab">签约协议</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <!--进件管理-->
                    <div class="tab-pane fade in active clearfix" id="nav1">
                        <button type="button" class="btn btn-primary createWarn pull-right" style="margin:10px;" onclick="resetAll()">添加POS机具</button>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th>设备号</th><th>添加时间</th>
                                <th>银行卡</th><th>微信</th>
                                <th>支付宝</th><th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${posList}" var="pos">
                                <tr>
                                    <td>${pos.posId}</td><td>${pos.createdDate}</td>
                                    <!--银行-->
                                    <td>
                                        <h5>（非会员）</h5>
                                        <h5>&nbsp;&nbsp;借记卡 ${pos.debitCardCommission}% &nbsp; 封顶 ${pos.ceil}</h5>
                                        <h5>&nbsp;&nbsp;贷记卡 ${pos.creditCardCommission}% &nbsp;
                                            <c:if test="${pos.ljCommission!=null}">
                                                佣金 ${pos.ljCommission} %
                                            </c:if>
                                            <c:if test="${pos.ljCommission==null}">
                                                佣金 未开通
                                            </c:if>
                                        </h5>
                                    </td>
                                    <!--微信-->
                                    <c:choose>
                                        <c:when test="${pos.wxCommission==null&&pos.wxUserCommission==null}">
                                            <td>未开通</td>
                                        </c:when>
                                        <c:when test="${pos.wxCommission==null&&pos.wxUserCommission!=null}">
                                            <td>
                                                <h5>手续费：${pos.wxUserCommission} %  </h5>
                                                <h5>佣金： 未开通 </h5>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <h5>手续费：${pos.wxUserCommission} % </h5>
                                                <h5>佣金： ${pos.wxCommission} %  </h5>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                    <!--支付宝-->
                                    <c:choose>
                                        <c:when test="${pos.aliCommission==null&&pos.aliUserCommission==null}">
                                            <td>未开通</td>
                                        </c:when>
                                        <c:when test="${pos.aliCommission==null&&pos.aliUserCommission!=null}">
                                            <td>
                                                <h5>手续费：${pos.aliUserCommission} %  </h5>
                                                <h5>佣金： 未开通 </h5>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <h5>手续费：${pos.aliUserCommission} %  </h5>
                                                <h5>佣金： ${pos.aliCommission} % </h5>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                    <!--操作-->
                                    <td>
                                        <button type="button" class="btn btn-default createWarn" onclick="editPos('${pos.id}')">编辑</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!--营业执照-->
                    <div class="tab-pane fade in active clearfix" id="nav2">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">营业执照</label>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="licenseImg-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('licenseImg-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="licenseImg" onclick="fileUpload('licenseImg')" name="file" type="file" class="btn">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <label class="col-sm-2 control-label">法人身份证复印件<br>(正反面)</label>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="idCardImg-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('idCardImg-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="idCardImg" onclick="fileUpload('idCardImg')" name="file" type="file" class="btn">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <label class="col-sm-2 control-label">税务登记证<br>(三证合一请上传营业执照)</label>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="taxRegistrationImg-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('taxRegistrationImg-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="taxRegistrationImg" onclick="fileUpload('taxRegistrationImg')" name="file" type="file" class="btn">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <label class="col-sm-2 control-label">结算银行卡持有身份证复印件</label>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="bankIdCardImg-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('bankIdCardImg-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="bankIdCardImg" onclick="fileUpload('bankIdCardImg')" name="file"  type="file" class="btn">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <label class="col-sm-2 control-label">组织结构照<br>(三证合一请上传营业执照)</label>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="orgConstructionImg-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载"  onclick="fileDownload('orgConstructionImg-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="orgConstructionImg" onclick="fileUpload('orgConstructionImg')" name="file" type="file" class="btn">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <label class="col-sm-2 control-label">结算银行卡正反面</label>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="bankCardImg-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('bankCardImg-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="bankCardImg" onclick="fileUpload('bankCardImg')" name="file" type="file" class="btn">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!--签约协议-->
                    <div class="tab-pane fade in active clearfix" id="nav3">
                        <form class="form-horizontal">
                            <div class="row">
                                <label class="col-sm-2 control-label">积分客微商城’平台受理服务协议书</label>
                            </div>
                            <div class="row">
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="platServerProcImg1-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('platServerProcImg1-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="platServerProcImg1" name="file" type="file" class="btn" onclick="fileUpload('platServerProcImg1')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="platServerProcImg2-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('platServerProcImg2-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="platServerProcImg2" name="file" type="file" class="btn" onclick="fileUpload('platServerProcImg2')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="platServerProcImg3-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('platServerProcImg3-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="platServerProcImg3" name="file" type="file" class="btn"  onclick="fileUpload('platServerProcImg3')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="platServerProcImg4-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('platServerProcImg4-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="platServerProcImg4" name="file" type="file" class="btn" onclick="fileUpload('platServerProcImg4')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="platServerProcImg5-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('platServerProcImg5-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="platServerProcImg5" name="file" type="file" class="btn"  onclick="fileUpload('platServerProcImg5')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label text-left">商户基础资料表</label>
                            </div>
                            <div class="row">
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="mercBaseInfoImg1-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('mercBaseInfoImg1-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="mercBaseInfoImg1" name="file" type="file" class="btn"  onclick="fileUpload('mercBaseInfoImg1')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="mercBaseInfoImg2-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载"  onclick="fileDownload('mercBaseInfoImg2-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="mercBaseInfoImg2" name="file" type="file" class="btn" onclick="fileUpload('mercBaseInfoImg2')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label text-left">中汇支付收单特约商户信息调查表</label>
                            </div>
                            <div class="row">
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="cnepaySpecialMercInfoImg1-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('cnepaySpecialMercInfoImg1-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="cnepaySpecialMercInfoImg1" name="file" type="file" class="btn" onclick="fileUpload('cnepaySpecialMercInfoImg1')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="cnepaySpecialMercInfoImg2-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('cnepaySpecialMercInfoImg2-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="cnepaySpecialMercInfoImg2" name="file"  type="file" class="btn" onclick="fileUpload('cnepaySpecialMercInfoImg2')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="cnepaySpecialMercInfoImg3-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('cnepaySpecialMercInfoImg3-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="cnepaySpecialMercInfoImg3" name="file" type="file" class="btn" onclick="fileUpload('cnepaySpecialMercInfoImg3')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <label class="col-sm-2 control-label text-left" style="text-align: left">结算授权书</label>
                            </div>
                            <div class="row">
                                <div class="col-sm-2">
                                    <div class="thumbnail"><img id="accountAuthorizationImg-img" src="images/me.jpg" alt="..."></div>
                                    <div class="btn clearfix">
                                        <input type="button" class="btn btn-default pull-left" value="下载" onclick="fileDownload('accountAuthorizationImg-img')">
                                        <div class="pull-right pic-right-btn">
                                            <input id="accountAuthorizationImg" name="file" type="file" class="btn" onclick="fileUpload('accountAuthorizationImg')">
                                            <input type="button" class="btn btn-primary" value="上传">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>
<!--开通佣金提示框-->
<div class="modal" id="openWarn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">开通佣金</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label col-sm-offset-2">佣金费率</label>
                        <div class="col-sm-6">
                            <input id="comisNum" type="number" class="form-control pull-left" style="width: 90%">
                            <input id="comisId"  type="hidden" />
                            <span class="pull-right" style="line-height: 30px">%</span>
                        </div>
                    </div>
                    <p class="text-center"><font color="grey">请确保佣金费率和三方协议上签署的费率一致，否则会开通失败</font></p>
                    <p class="text-center"><font color="red">开通失败，请检查佣金费率是否和三方协议一致</font></p>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveLjCommission()">确认</button>
            </div>
        </div>
    </div>
</div>

<!--添加提示框-->
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
                    <div><input name="id" type="hidden"/><input id="sbh" type="text" class="form-control" name="posId"></div>
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
                <%--<div>
                    <div>导流订单参数</div>
                    <div>
                        <div><span>红包比</span><input type="number" class="form-control"  name="scoreARebate" /><span style="margin-right: 5%;">%</span><span>积分比</span><input type="number" class="form-control"  name="scoreBRebate"/><span>%</span></div>
                    </div>
                </div>
                <div>
                    <div>会员订单参数</div>
                    <div>
                        <div><span>红包比</span><input type="number" class="form-control"  name="userScoreARebate"/><span style="margin-right: 5%;">%</span><span>积分比</span><input type="number" class="form-control"  name="userScoreBRebate"/><span>%</span></div>
                    </div>
                </div>--%>
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
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    $(function () {
//        tab切换
        $('#myTab li:eq(0) a').tab('show');
//        提示框
        $(".openWarn").click(function(){
            $("#openWarn").modal("toggle");
        });
        $(".createWarn").click(function(){
            $("#createWarn").modal("toggle");
        });

        $('#select-btn').on('change',function () {
            var index=$(this).val();
            for(var i=0;i<3;i++){
                $('.'+'baseRate'+i).css('display','none');
            }
            $('.'+'baseRate'+index).css('display','block');
        })
//      加载图片
        loadPic();
    })
</script>
<script>
    function place(id,val,min,max) {
        if(val!='' && val < min){
            $("." + id).val(min);
        }else if(val > max){
            $("." + id).val(max);
        }
    }
    $(".w-food1").blur(function (e) {
        var val = $(".w-food1").val();
        place("w-food1",val,1.15,1.25);
    });
    $(".w-food2").blur(function (e) {
        var val = $(".w-food2").val();
        place("w-food2",val,80);
    });
    $(".w-food3").blur(function (e) {
        var val = $(".w-food3").val();
        place("w-food3",val,1.25,100);
    });
    $(".w-common1").blur(function (e) {
        var val = $(".w-common1").val();
        place("w-common1",val,0.65,0.78);
    });
    $(".w-common2").blur(function (e) {
        var val = $(".w-common2").val();
        place("w-common2",val,80);
    });
    $(".w-common3").blur(function (e) {
        var val = $(".w-common3").val();
        place("w-common3",val,0.78,100);
    });
    $(".w-live1").blur(function (e) {
        var val = $(".w-live1").val();
        place("w-live1",val,0.35,0.38);
    });

    $("input[type='radio']").bind('change',function(){
        resetRedio();
    });
    function resetRedio() {
        $(".w-food1").val('');
        $(".w-food2").val('');
        $(".w-food3").val('');
        $(".w-common1").val('');
        $(".w-common2").val('');
        $(".w-common3").val('');
        $(".w-live1").val('');
    }

    function resetAll() {
        $("input[name=id]").val('');
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
     /* $("input[name=scoreARebate]").val('');
        $("input[name=scoreBRebate]").val('');
        $("input[name=userScoreARebate]").val('');
        $("input[name=userScoreBRebate]").val('');*/
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

        /*if($("input[name=scoreARebate]").val()!=null&&$("input[name=scoreARebate]").val()!='') {
            merchantPos.scoreARebate = $("input[name=scoreARebate]").val();
        }else {
            return;
        }
        if($("input[name=scoreBRebate]").val()!=null&&$("input[name=scoreBRebate]").val()!='') {
            merchantPos.scoreBRebate = $("input[name=scoreBRebate]").val();
        }else {
            return;
        }*/
        /*if($("input[name=userScoreARebate]").val()!=null&&$("input[name=userScoreARebate]").val()!='') {
            merchantPos.userScoreARebate = $("input[name=userScoreARebate]").val();
        }else {
            return;
        }
        if($("input[name=userScoreBRebate]").val()!=null&&$("input[name=userScoreBRebate]").val()!='') {
            merchantPos.userScoreBRebate = $("input[name=userScoreBRebate]").val();
        }else {
            return;
        }*/
        merchantPos.ljCommission = $("input[name=ljCommission]").val();
        merchantPos.id = $("input[name=id]").val();
        //  表单提交
        merchantPos.wxCommission = $("input[name=wxCommission]").val();
        merchantPos.aliCommission = $("input[name=aliCommission]").val();
        merchantPos.wxUserCommission = $("input[name=wxUserCommission]").val();
        merchantPos.aliUserCommission = $("input[name=aliUserCommission]").val();
        merchantPos.merchant={id:"${merchantId}"};
        $.ajax({
            type:"post",
            url:"/manage/pos/save_pos",
            contentType:"application/json",
            data:JSON.stringify(merchantPos),
            success:function(data){
                alert(data.msg);
                location.href="/manage/merchant/pos_manage/${merchantId}";
            }
        });
    }
    //  编辑机具信息
    function editPos(id) {
        resetAll();                                                         //  清空上一次信息
        $.get("/manage/pos/getById/"+id,function(pos){                      //  查询并进行数据回显
            $("input[name=id]").val(pos.id);
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
         /* $("input[name=scoreARebate]").val(pos.scoreARebate);
            $("input[name=scoreBRebate]").val(pos.scoreBRebate);
            $("input[name=userScoreARebate]").val(pos.userScoreARebate);
            $("input[name=userScoreBRebate]").val(pos.userScoreBRebate);*/
        },"json");
    }

    // 添加/编辑   会员佣金部分
    function editLjCommission(id,commission) {
        $("#comisNum").val(commission);
        $("#comisId").val(id);
    }
    function saveLjCommission() {
        var ljCommission = $("#comisNum").val();
        var id  = $("#comisId").val();
        var json = JSON.stringify({id:id,ljCommission:ljCommission});
        $.ajax({
            url:"/manage/pos/save_ljCommision",
            type:"post",
            contentType:"application/json",
            data:json,
            success:function(result){
                alert(result.msg);
                location.href="/manage/merchant/pos_manage/${merchantId}";
            }
        });
    }

    //  图片管理部分
    var merchantId = "${merchantId}";
    function fileUpload(id) {                            // id (标签id)
        $("#"+id).fileupload({
            url:"/manage/merchant/saveImage",
            formData:{merchantId:merchantId,fieldName:id},
            done:function(e,result){
                var url = "${ossImageReadRoot}/"+JSON.stringify(result.result.data).replace("\"",'').replace("\"",'');
                $("#"+id+"-img").attr("src",url);
            }
        });
    }

    function fileDownload(id) {
        var url = $("#"+id).attr('src');
        var link = document.createElement('a');
        link.href = url;
        link.download = 'Download.jpg';
        document.body.appendChild(link);
        link.click();
    }


    function loadPic() {
        $.ajax({
            url:"/manage/merchant/loadPosImg",
            type:"get",
            data:{merchantId:merchantId},
            contentType:"application/json",
            success:function(data) {
                var licenseImg = JSON.stringify(data.licenseImg).replace("\"",'').replace("\"",'');
                var idCardImg = JSON.stringify(data.idCardImg).replace("\"",'').replace("\"",'');
                var taxRegistrationImg =  JSON.stringify(data.taxRegistrationImg).replace("\"",'').replace("\"",'');
                var bankIdCardImg =  JSON.stringify(data.bankIdCardImg).replace("\"",'').replace("\"",'');
                var orgConstructionImg =  JSON.stringify(data.orgConstructionImg).replace("\"",'').replace("\"",'');
                var bankCardImg =  JSON.stringify(data.bankCardImg).replace("\"",'').replace("\"",'');
                $("#licenseImg-img").attr("src",licenseImg);
                $("#idCardImg-img").attr("src",idCardImg);
                $("#taxRegistrationImg-img").attr("src",taxRegistrationImg);
                $("#bankIdCardImg-img").attr("src",bankIdCardImg);
                $("#orgConstructionImg-img").attr("src",orgConstructionImg);
                $("#bankCardImg-img").attr("src",bankCardImg);

                var platServerProcImg1 =  JSON.stringify(data.platServerProcImg1).replace("\"",'').replace("\"",'');
                var platServerProcImg2 =  JSON.stringify(data.platServerProcImg2).replace("\"",'').replace("\"",'');
                var platServerProcImg3 =  JSON.stringify(data.platServerProcImg3).replace("\"",'').replace("\"",'');
                var platServerProcImg4 =  JSON.stringify(data.platServerProcImg4).replace("\"",'').replace("\"",'');
                var platServerProcImg5 =  JSON.stringify(data.platServerProcImg5).replace("\"",'').replace("\"",'');
                var mercBaseInfoImg1 =  JSON.stringify(data.mercBaseInfoImg1).replace("\"",'').replace("\"",'');
                var mercBaseInfoImg2 =  JSON.stringify(data.mercBaseInfoImg2).replace("\"",'').replace("\"",'');
                var cnepaySpecialMercInfoImg1 =  JSON.stringify(data.cnepaySpecialMercInfoImg1).replace("\"",'').replace("\"",'');
                var cnepaySpecialMercInfoImg2 =  JSON.stringify(data.cnepaySpecialMercInfoImg2).replace("\"",'').replace("\"",'');
                var cnepaySpecialMercInfoImg3 =  JSON.stringify(data.cnepaySpecialMercInfoImg3).replace("\"",'').replace("\"",'');
                var accountAuthorizationImg =  JSON.stringify(data.accountAuthorizationImg).replace("\"",'').replace("\"",'');

                $("#platServerProcImg1-img").attr("src",platServerProcImg1);
                $("#platServerProcImg2-img").attr("src",platServerProcImg2);
                $("#platServerProcImg3-img").attr("src",platServerProcImg3);
                $("#platServerProcImg4-img").attr("src",platServerProcImg4);
                $("#platServerProcImg5-img").attr("src",platServerProcImg5);
                $("#mercBaseInfoImg1-img").attr("src",mercBaseInfoImg1);
                $("#mercBaseInfoImg2-img").attr("src",mercBaseInfoImg2);
                $("#cnepaySpecialMercInfoImg1-img").attr("src",cnepaySpecialMercInfoImg1);
                $("#cnepaySpecialMercInfoImg2-img").attr("src",cnepaySpecialMercInfoImg2);
                $("#cnepaySpecialMercInfoImg3-img").attr("src",cnepaySpecialMercInfoImg3);
                $("#accountAuthorizationImg-img").attr("src",accountAuthorizationImg);
            }
        });
    }



</script>
</body>
</html>

