<%--
  Created by IntelliJ IDEA.
  User: wcg
  Date: 16/7/21
  Time: 上午10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    .table>tbody>tr>td, .table>thead>tr>th{line-height: 40px;text-align: center}
    .table>tbody>tr>td, .table>thead>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th{line-height: 60px;}
  </style>
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="js/html5shiv.min.js"></script>
  <script src="js/respond.min.js"></script>
  <![endif]-->
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
    <div class="main" >
      <div class="container-fluid">
        <a type="button" class="btn btn-primary btn-create" style="margin:10px;" href="/manage/partner/edit"  />新建合伙人</a>
        <hr>
        <ul id="myTab" class="nav nav-tabs">
          <li><a href="#lunbotu" data-toggle="tab">合伙人</a></li>
          <li class="active"><a href="#xiangqing" data-toggle="tab">合伙人管理员</a></li>
        </ul>
        <div id="myTabContent" class="tab-content" style="margin-top: 10px">
          <div class="tab-pane fade in active" id="lunbotu">
            <table class="table table-bordered table-hover">
              <thead>
              <tr class="active">
                <th>合伙人ID</th><th>合伙人姓名</th><th>联系电话</th><th>绑定店铺</th>
                <th>绑定会员</th><th>佣金金额</th><th>累计佣金收入</th><th>操作</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${partners}" var="partner">
                <tr>
                  <td>${partner[0]}</td><td>${partner[6]}</td>
                  <td>${partner[7]}</td><td>${partner[10]}/${partner[3]}</td>
                  <td>${partner[9]}/${partner[2]}</td><td>￥${partner[4]/100.0}</td>
                  <td>￥${partner[5]/100.0}</td>
                  <td>
                    <button type="button" class="btn btn-default zhWarn" onclick="userEdit('${partner[0]}','${partner[1]}','${partner[8]}')">账号</button>
                    <button type="button" class="btn btn-default" onclick="editPartner('${partner[0]}')">编辑</button>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>
          <div class="tab-pane fade" id="xiangqing">
            <table class="table table-bordered table-hover">
              <thead>
              <tr class="active">
                <th class="text-center">合伙人管理员ID</th><th class="text-center">合伙人管理员名称</th>
                <th class="text-center">账户余额</th>
                <%--<th class="text-center">操作</th>--%>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${partnerManagers}" var="partnerManager">
                <tr>
                  <td class="text-center">${partnerManager.partnerManager.id}</td><td class="text-center">${partnerManager.partnerManager.name}</td>
                  <td class="text-center">￥${partnerManager.availableBalance/100}</td>
                  <%--<td class="text-center">--%>
                    <%--<button type="button" class="btn btn-default editWarn">编辑</button>--%>
                    <%--&lt;%&ndash;<button type="button" class="btn btn-default deleteWarn" data-target="#deleteWarn">删除</button>&ndash;%&gt;--%>
                  <%--</td>--%>
                </tr>
              </c:forEach>
              <%--<tr>--%>
                <%--<td class="text-center">12323</td><td class="text-center">王尼玛</td>--%>
                <%--<td class="text-center">￥40</td>--%>
                <%--<td class="text-center">--%>
                  <%--<button type="button" class="btn btn-default editWarn">编辑</button>--%>
                  <%--<button type="button" class="btn btn-default deleteWarn" data-target="#deleteWarn">删除</button>--%>
                <%--</td>--%>
              <%--</tr>--%>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<div id="bottomIframe">
  <%@include file="../common/bottom.jsp" %>
</div>
<!--删除提示框-->
<div class="modal" id="deleteWarn">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">删除</h4>
      </div>
      <div class="modal-body">
        <h4>确定要删除：唐古拉黑糖玛？</h4>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
      </div>
    </div>
  </div>
</div>
<!--添加提示框-->
<div class="modal" id="editWarn">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">上架</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
          <div class="form-group">
            <label class="col-sm-3 control-label">合伙人管理员名称</label>
            <div class="col-sm-8">
              <input type="text" class="form-control">
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
      </div>
    </div>
  </div>
</div>
<!--添加提示框-->
<div class="modal" id="zhWarn">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">账号</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
          <div class="form-group">
            <label class="col-sm-2 control-label">账号</label>
            <div class="col-sm-8">
              <input type="text" class="form-control" id="user">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">密码</label>
            <div class="col-sm-8">
              <input type="text" class="form-control" id="password">
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="confirm">确认</button>
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
//    $(".deleteWarn").click(function(){
//      $("#deleteWarn").modal("toggle");
//    });
//    $(".editWarn").click(function(){
//      $("#editWarn").modal("toggle");
//    });
//    $(".zhWarn").click(function(){
//      $("#zhWarn").modal("toggle");
//    });
  })

  function userEdit(id,name,password){
    $("#user").val(name);
    $("#password").val(password);
    $("#confirm").bind("click",function(){
      var partner ={};
      partner.id = id;
      partner.name =  $("#user").val();
      partner.password =  $("#password").val();
      $("#confirm").unbind("tap");
      $.ajax({
               type: "post",
               data: JSON.stringify(partner),
               contentType: "application/json",
               url: "/manage/partner/editUser",
               success: function (data) {
                alert(data.msg);
                 location.href = "/manage/partner";
               }
             });
    });
    $("#zhWarn").modal("toggle");

  }
  function editPartner(id){
    location.href = "/manage/partner/edit?id="+id;
  }
</script>
</body>
</html>

