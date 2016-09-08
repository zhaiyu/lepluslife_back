<%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2016/9/7
  Time: 11:37
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
    <title>乐+生活 运营管理系统</title>
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
    <script src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script src="${resourceUrl}/js/respond.min.js"></script>
    <![endif]-->
    <script src="${resourceUrl}/js/echarts.min.js"></script>
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
                <h3>数据中心</h3>
                <hr>
                <ul id="myTab" class="nav nav-tabs">
                    <li class="active"><a href="#tab1" data-toggle="tab">会员消费次数</a></li>
                </ul>
                <div id="myTabContent" class="tab-content" style="margin-top: 10px">
                    <div class="container" id="echart-main" style="height:400px;"></div>
                    <div class="container">
                        <div class="tab-pane fade in active" id="tab1">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr class="active">
                                    <th>导流消费次数</th><th>消费会员</th><th>占比</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr><td>0</td><td class="data1"></td><td class="data2"></td></tr>
                                <tr><td>1</td><td class="data1"></td><td class="data2"></td></tr>
                                <tr><td>2</td><td class="data1"></td><td class="data2"></td></tr>
                                <tr><td>3</td><td class="data1"></td><td class="data2"></td></tr>
                                <tr><td>超过3次</td><td class="data1"></td><td class="data2"></td></tr>
                                </tbody>
                            </table>
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
<script src="${resourceUrl}/js/bootstrap.min.js"></script>
<script>
    $(function () {
        //        tab切换
        $('#myTab li:eq(0) a').tab('show');
    });

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart-main'));

    // 显示标题，图例和空的坐标轴
    myChart.setOption({
                          tooltip: {},
                          legend: {
                              data:['会员消费次数']
                          },
                          xAxis: {
                              data: ["0次导流消费","1次导流消费","2次导流消费","3次导流消费","超过3次"]
                          },
                          yAxis: {},
                          series: [{
                                       name: '消费次数',
                                       type: 'bar',
                                       data: [],
                                   }],
                          visualMap: {
                              inRange: {
                              },

                          }
                      });

    // 异步加载数据
    $.get('/manage/member_data/count').done(function (data) {
        // 填入数据
        myChart.setOption({
                              series: [{
                                           // 根据名字对应到相应的系列
                                           name: '销量',
                                           data: data
                                       }]
                          });
        var dataSum=0
        for(var i=0;i<data.length;i++){
            dataSum+=data[i];
        }
        for(var j=0;j<data.length;j++){
            $('.data1').eq(j).text(data[j]);
            $('.data2').eq(j).text(toDecimal(data[j]/dataSum*100)+'%');
        }
        function toDecimal(x) {
            var f = parseFloat(x);
            if (isNaN(f)) {
                return false;
            }
            var f = Math.round(x*100)/100;
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
    });
</script>
</body>
</html>