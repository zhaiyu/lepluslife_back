<%--
  Created by IntelliJ IDEA.
  User: WhiteFeather
  Date: 2017/6/21
  Time: 9:51
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
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/commonCss.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/jquery.page.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/combo.select.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModInput.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/Mod/ModCommon.css"/>
    <link type="text/css" rel="stylesheet" href="${resourceUrl}/css/create-edit-store.css"/>
    <script type="text/javascript" src="${resourceUrl}/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/respond.min.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${resourceUrl}/js/jquery.fileupload.js"></script
    <script src="${resourceUrl}/js/bootstrap.min.js"></script>
    <script src="${resourceUrl}/js/daterangepicker.js"></script>
    <script src="${resourceUrl}/js/moment.min.js"></script>
    <script src="${resourceUrl}/js/jquery.page.js"></script>

</head>
<style>
    .fixClear:after {
        content: '\20';
        display: block;
        clear: both;
    }
    .listImg > div {
        float: left;
        width: 100px;
        margin-right: 20px;
        margin-bottom: 20px;
    }
    .listImg > div > div:first-child {
        width: 100%;
        height: 100px;
        border:1px solid #ccCCCC;
    }
    .listImg > div > div:nth-child(2) {
        width: 100%;
        font-size: 13px;
        color: #fff;
        background-color: #f0ad4e;
        border:1px solid #f0ad4e;
        text-align: center;
        padding: 5px 0;
        cursor: pointer;
    }
    .listImg > div > div:nth-child(3) {
        width: 100%;
        display: none;
    }
    .listImg > div > div:last-child {
        width: 100%;
        font-size: 13px;
        color: #fff;
        background-color: #d9534f;
        border:1px solid #d9534f;
        text-align: center;
        padding: 5px 0;
        cursor: pointer;
    }
    .addButton > div {
        background-color: transparent !important;
        color: #ccc !important;
        font-size: 20px !important;
        border:1px solid #ccc !important;
        padding: 0 !important;
        line-height: 100px;
    }
    .yy {
        display: none;
    }
    .xdrq {

    }
    .jdrq {
        display: none;
    }
    .checkArea > div {
        float: left;
        width: 22%;
        margin-right: 3%;
        margin-bottom: 10px;
    }
    .checkArea input {
        width: auto;
        margin-right: 4%;
    }
</style>
<body>
<div id="topIframe">
    <%@include file="../common/top.jsp" %>
</div>
<div id="content">
    <div id="leftIframe">
        <%@include file="../common/left.jsp" %>
    </div>
    <div class="m-right">
        <div class="create_edit-title">
            <div class="ModRadius"> < 返回</div>
            <p>团购商品</p>
        </div>
        <div class="create_edit-body">
            <div class="MODInput_row">
                <div class="Mod-2">团购名称</div>
                <div class="Mod-5">
                    <input type="text" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">简介</div>
                <div class="Mod-5">
                    <input type="text" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">列表图片</div>
                <div class="Mod-5">
                    <div class="listImg fixClear">
                        <div>
                            <div><img src="" alt=""></div>
                            <div class="update">点击上传</div>
                            <div><input type="file"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">轮播图</div>
                <div class="Mod-5">
                    <div class="listImg fixClear">
                        <div>
                            <div><img src="" alt=""></div>
                            <div class="update">点击上传</div>
                            <div><input type="file"></div>
                        </div>
                        <div class="addButton">
                            <div>+</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">详情图片</div>
                <div class="Mod-5">
                    <div class="listImg fixClear">
                        <div>
                            <div><img src="" alt=""></div>
                            <div class="update">点击上传</div>
                            <div><input type="file"></div>
                        </div>
                        <div class="addButton">
                            <div>+</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">可用门店</div>
                <div class="Mod-5">
                    <select class="hhr">
                        <option value="0">请选择可用门店</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2"></div>
                <div class="Mod-5 checkArea fixClear">

                </div>
            </div>
            <div class="MODInput_row create_edit-contractType">
                <div class="Mod-2">是否需预约</div>
                <div class="Mod-5 ModRadio2 create_edit-typeChose xyy">
                    <div class="ModRadius-left ModRadius2_active">免预约</div>
                    <div class="ModRadius-right">需要预约</div>
                </div>
            </div>
            <div class="MODInput_row yy">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <span>提前</span><input style="width: 20%" type="number"><span>天预约</span>
                </div>
            </div>
            <div class="MODInput_row create_edit-contractType">
                <div class="Mod-2">退款设定</div>
                <div class="Mod-5 ModRadio2 create_edit-typeChose">
                    <div class="ModRadius-left ModRadius2_active">随时可退</div>
                    <div class="ModRadius-right">不能退款</div>
                </div>
            </div>
            <div class="MODInput_row create_edit-contractType">
                <div class="Mod-2">有效期</div>
                <div class="Mod-5 ModRadio2 create_edit-typeChose setrq">
                    <div class="ModRadius-left ModRadius2_active">相对日期</div>
                    <div class="ModRadius-right">绝对日期</div>
                </div>
            </div>
            <div class="MODInput_row xdrq">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <span>购买后</span><input style="width: 20%" type="number"><span>天有效（过了有效期未使用将自动退款）</span>
                </div>
            </div>
            <div class="MODInput_row jdrq">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <input style="width: 20%" type="number"><span>前使用（填写方式如：2017-1-1）</span>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">使用说明</div>
                <div class="Mod-5">
                    <div class="listImg fixClear">
                        <div>
                            <div><img src="" alt=""></div>
                            <div class="update">点击上传</div>
                            <div><input type="file"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">详情明细</div>
                <div class="Mod-5">
                    <div class="listImg fixClear">
                        <div>
                            <div><img src="" alt=""></div>
                            <div class="update">点击上传</div>
                            <div><input type="file"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">团购原价</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">普通团购价</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">普通库存</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">乐+团购价</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">乐+佣金</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">普通手续费</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">送鼓励金</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">送金币</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">会员所在门店分润</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">会员锁定天使合伙人分润</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">会员锁定城市合伙人分润</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">核销门店天使合伙人分润</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">核销门店城市合伙人分润</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row">
                <div class="Mod-2">乐+库存</div>
                <div class="Mod-5">
                    <input type="number" class="create_edit-input" />
                </div>
            </div>
            <div class="MODInput_row ModButtonMarginDown">
                <div class="Mod-2"></div>
                <div class="Mod-5">
                    <button class="ModButton ModButton_ordinary ModRadius">保存信息</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="bottomIframe">
    <%@include file="../common/bottom.jsp" %>
</div>

</body>

<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/Mod/common.js"></script>
<script type="text/javascript" src="../js/jquery.combo.select.js"></script>
<!--强制保留2位小数js-->
<script type="text/javascript" src="../js/Mod/RetainDecimalFor2.js"></script>
<script>
    //    set
    var HtmlType = 0;    //页面类型：创建页面=0 ; 编辑页面=1;

    //保留2位小数
    $(".create_edit-for2").blur(function () {
        var newVal = toDecimal($(this).val());
        if(newVal > 100){
            newVal = 100;
        }else if(newVal < 0){
            newVal = 0;
        }
        $(this).val(newVal);
    });

    //下拉模糊查询
    $(function() {
        $('.hhr').comboSelect();
    });

    //添加图片
    $(".update").click(function () {
        $(this).next().children().click();
    });
    $(".addButton").click(function () {
        var imgDiv = $("<div></div>").append(
            $("<div></div>").append(
                $("<img>")
            )
        ).append(
            $("<div></div>").attr("class","update").html("点击上传")
        ).append(
            $("<div></div>").append(
                $("<input>").attr("type","file")
            )
        ).append(
            $("<div></div>").html("删除")
        );

        $(this).before(imgDiv);
        $(".update").click(function () {
            $(this).next().children().click();
        });
    });

    //预约
    $(".xyy > div").click(function () {
        var thisName = $(this).html();
        switch (thisName){
            case '免预约':
                $(".yy").hide();
                break;
            case '需要预约':
                $(".yy").show();
                break;
            default:
                break;
        }
    });

    //    设置日期
    $(".setrq > div").click(function () {
        var thisName = $(this).html();
        switch (thisName){
            case '相对日期':
                $(".xdrq").show();
                $(".jdrq").hide();
                break;
            case '绝对日期':
                $(".xdrq").hide();
                $(".jdrq").show();
                break;
            default:
                break;
        }
    });

    //锁定门店多选框
    setTimeout(function () {
        $(".option-item").click(function () {
            changeCheck();
            $(".allCheck").click(function(){
                if(this.checked){
                    $(".checkArea :checkbox").prop("checked", true);
                }else{
                    $(".checkArea :checkbox").prop("checked", false);
                }
            });
        });
    },1000);

    function changeCheck() {
        $(".checkArea").empty();
        $(".checkArea").append(
            $("<div></div>").append(
                $('<input>').attr("type","checkbox").attr("class","allCheck")
            ).append(
                $("<span></span>").html("全部选择")
            )
        );
        for(var i = 0;i < 10;i++){
            $(".checkArea").append(
                $("<div></div>").append(
                    $('<input>').attr("type","checkbox")
                ).append(
                    $("<span></span>").html("选择" + i)
                )
            );
        }
    }
</script>

</html>
