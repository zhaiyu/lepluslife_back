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
                        <h2>POS机管理<font size="3" color="red">(已认证)</font></h2>
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
                                <th>PSAM卡号</th><th>POS商户号</th>
                                <th>终端号</th><th>POS注册手机号</th>
                                <th>费率类型</th><th>添加时间</th>
                                <th>佣金状态</th><th>操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${posList}" var="pos">
                                <tr>
                                    <td>${pos.psamCard}</td><td>${pos.posMerchantNo}</td>
                                    <td>${pos.posId}</td><td>${pos.phoneNumber}</td>

                                    <c:if test="${pos.type==0}">
                                        <td>非封顶机（${pos.posCommission}%）</td>
                                    </c:if>
                                    <c:if test="${pos.type==1}">
                                        <td>封顶机（${pos.posCommission}%，${pos.ceil}元封顶）</td>
                                    </c:if>

                                    <td><h5>${pos.createdDate}</h5></td>

                                    <c:if test="${pos.ljCommission==null}">
                                    <td>未开通</td>
                                    <td>
                                        <button type="button" class="btn btn-default openWarn" onclick="editLjCommission('${pos.id}','${pos.ljCommission}')">开通佣金</button>
                                        </c:if>

                                        <c:if test="${pos.ljCommission!=null}">
                                    <td>已开通</td>
                                    <td>
                                        <button type="button" class="btn btn-default openWarn" onclick="editLjCommission('${pos.id}','${pos.ljCommission}')">编辑佣金</button>
                                        </c:if>
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
                <h4 class="modal-title">POS机具</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">pos商户号</label>
                        <div class="col-sm-6">
                            <input name="id" type="hidden"/>
                            <input name="posMerchantNo" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">POS注册手机号</label>
                        <div class="col-sm-6">
                            <input name="phoneNumber" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">PSAM卡号</label>
                        <div class="col-sm-6">
                            <input name="psamCard" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">终端号</label>
                        <div class="col-sm-6">
                            <input name="posId" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">行业大类</label>
                        <div class="col-sm-6">
                            <select name="posType" class="form-control" id="select-btn">
                                <option value="0">餐娱类</option>
                                <option value="1">一般类</option>
                                <option value="2">民生类</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">结算周期</label>
                        <div class="col-sm-6" style="margin-top: 7px">
                            T+1
                        </div>
                    </div>

                    <div class="form-group baseRate baseRate0">
                        <label class="col-sm-3 control-label">基本费率</label>
                        <div class="col-sm-9">
                            <label class="radio-inline col-sm-4">
                                <input id="Radio0-0" type="radio" name="Radio0" value="0" checked>非封顶类POS
                                <div class="form-group">
                                    <p>单笔计费 <input type="text" class="w-food1" style="width: 50%" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"> %</p>
                                    <p><font color="gray">餐娱类比例在（1.15%~1.25%）</font></p>
                                </div>
                            </label>
                            <label class="radio-inline col-sm-7">
                                <input id="Radio0-1" type="radio" name="Radio0" value="1">封顶类POS
                                <div class="form-group">
                                    <p><input type="text" class="w-food2" style="width: 30%"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"> 元/笔封顶 <font color="gray">（封顶费不得低于80元）</font></p>
                                    <p><font color="gray">少于封顶金额的部分手续费率</font></p>
                                    <p><input type="text" class="w-food3" style="width: 30%" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"> % <font color="gray">（至少是1.25%）</font></p>
                                </div>
                            </label>
                        </div>
                    </div>
                    <div class="form-group baseRate baseRate1">
                        <label class="col-sm-3 control-label">基本费率</label>
                        <div class="col-sm-9">
                            <label class="radio-inline col-sm-4">
                                <input id="Radio1-0" type="radio" name="Radio1" value="0">非封顶类POS
                                <div class="form-group">
                                    <p>单笔计费 <input type="text" class="w-common1" style="width: 50%" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"> %</p>
                                    <p><font color="gray">一般类比例在（0.65%~0.78%）</font></p>
                                </div>
                            </label>
                            <label class="radio-inline col-sm-7">
                                <input id="Radio1-1" type="radio" name="Radio1" value="1">封顶类POS
                                <div class="form-group">
                                    <p><input type="text" class="w-common2" style="width: 30%" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"> 元/笔封顶 <font color="gray">（封顶费不得低于80元）</font></p>
                                    <p><font color="gray">少于封顶金额的部分手续费率</font></p>
                                    <p><input type="text" class="w-common3" style="width: 30%" onkeyup="value=value.replace(/[^\-?\d.]/g,'')">% <font color="gray">（至少是0.78%）</font></p>
                                </div>
                            </label>
                        </div>
                    </div>
                    <div class="form-group baseRate baseRate2">
                        <label class="col-sm-3 control-label">基本费率</label>
                        <div class="col-sm-9">
                            <label class="radio-inline col-sm-4">
                                <input type="radio" name="Radio2" value="0" checked>非封顶类POS
                                <div class="form-group">
                                    <p> 单笔计费<input type="text" class="w-live1" style="width: 50%" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"> %</p>
                                    <p><font color="gray">民生类比例在（0.35%~0.38%）</font></p>
                                </div>
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">结算周期</label>
                        <div class="col-sm-6" style="margin-top: 7px">
                            <p><font color="red">开通失败，该终端号已存在</font></p>
                            <p>微信支付 <input name="wxCommission" type="text" style="width: 30%"> %</p>
                            <p>&nbsp;&nbsp;&nbsp;支付宝 <input name="aliCommission" type="text" style="width: 30%"> %</p>
                            <p>百度钱包 <input name="bdCommission" type="text" style="width: 30%"> %</p>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveMerchantPos()">确认</button>
            </div>
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
        $(".w-food1").val('');
        $(".w-food2").val('');
        $(".w-food3").val('');
        $(".w-common1").val('');
        $(".w-common2").val('');
        $(".w-common3").val('');
        $(".w-live1").val('');

        $("input[name=posId]").val('');
        $("input[name=posMerchantNo]").val('');
        $("input[name=psamCard]").val('');
        $("input[name=phoneNumber]").val('');

        $("input[name=wxCommission]").val('');
        $("input[name=aliCommission]").val('');
        $("input[name=bdCommission]").val('');
    }

    //  保存机具信息
    function saveMerchantPos() {
        var merchantPos = {};
        merchantPos.id=$("input[name=id]").val();
        merchantPos.posId = $("input[name=posId]").val();
        merchantPos.posMerchantNo = $("input[name=posMerchantNo]").val();
        merchantPos.psamCard = $("input[name=psamCard]").val();
        merchantPos.phoneNumber = $("input[name=phoneNumber]").val();
        merchantPos.merchant={id:"${merchantId}"};
        var posType = $("select[name=posType]").val();
        merchantPos.posType= posType;
        if(posType==0) {
            var type = $("input[name=Radio0]:checked").val();
            merchantPos.type = type;
            if(type==0) {
                merchantPos.posCommission=$(".w-food1").val();
            }else if(type==1) {
                merchantPos.ceil=$(".w-food2").val();
                merchantPos.posCommission=$(".w-food3").val();
            }
        }
        if(posType==1) {
            var type = $("input[name=Radio1]:checked").val();
            merchantPos.type = type;
            if(type==0) {
                merchantPos.posCommission= $(".w-common1").val();
            }else if(type==1) {
                merchantPos.ceil=$(".w-common2").val();
                merchantPos.posCommission=$(".w-common3").val();
            }
        }
        if(posType==2) {
            merchantPos.posCommission=$(".w-live1").val();
        }
        merchantPos.wxCommission = $("input[name=wxCommission]").val();
        merchantPos.aliCommission = $("input[name=aliCommission]").val();
        merchantPos.bdCommission = $("input[name=bdCommission]").val();
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
            $("input[name=posMerchantNo]").val(pos.posMerchantNo);
            $("input[name=psamCard]").val(pos.psamCard);
            $("input[name=phoneNumber]").val(pos.phoneNumber);
            var posType = pos.posType;
            $("select[name=posType]").val(posType);
            if(posType==0) {
                var type = pos.type;
                if(type==0) {
                    $("#Radio0-0").attr("checked",true);
                    $(".w-food1").val(pos.posCommission);
                }else if(type==1){
                    $("#Radio0-1").attr("checked",true);
                    $(".w-food2").val(pos.ceil);
                    $(".w-food3").val(pos.posCommission);
                }
            }
            if(posType==1) {
                var type = pos.type;
                if(type==0) {
                    $("#Radio1-0").attr("checked",true);
                    $(".w-common1").val(pos.posCommission);
                }else {
                    $("#Radio1-1").attr("checked",true);
                    $(".w-common2").val(pos.ceil);
                    $(".w-common3").val(pos.posCommission);
                }
            }
            if(posType==2) {
                $(".w-live1").val(pos.posCommission);
            }
            $("input[name=wxCommission]").val(pos.wxCommission);
            $("input[name=aliCommission]").val(pos.aliCommission);
            $("input[name=bdCommission]").val(pos.bdCommission);
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

