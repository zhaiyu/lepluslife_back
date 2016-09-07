<%--
  Created by IntelliJ IDEA.
  User: zhangwen
  Date: 16/5/27
  Time: 上午11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/commen.jsp" %>
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
        .main .thumbnail {
            padding: 50px 0;
        }

        a:hover {
            text-decoration: none;
        }

        .button {
            min-width: 250px;
            max-width: 350px;
            font-size: 20px;
            display: block;
            margin: 1em;
            border: none;
            background: none;
            color: inherit;
            vertical-align: middle;
            position: relative;
            z-index: 1;
            -webkit-backface-visibility: hidden;
            -moz-osx-font-smoothing: grayscale;
        }

        .button--antiman {
            background: none;
            border: none;
            height: 60px;
        }

        .button--antiman > span {
            padding-left: 0.35em;
        }

        .button--antiman::before,
        .button--antiman::after {
            content: '';
            z-index: -1;
            border-radius: inherit;
            pointer-events: none;
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            -webkit-backface-visibility: hidden;
            -webkit-transition: -webkit-transform 0.3s, opacity 0.3s;
            transition: transform 0.3s, opacity 0.3s;
            -webkit-transition-timing-function: cubic-bezier(0.75, 0, 0.125, 1);
            transition-timing-function: cubic-bezier(0.75, 0, 0.125, 1);
        }

        .button--antiman::before {
            border: 2px solid #37474f;
            opacity: 0;
            -webkit-transform: scale3d(1.2, 1.2, 1);
            transform: scale3d(1.2, 1.2, 1);
        }

        .button--antiman::after {
            background: #fff;
        }

        .button--antiman:hover::before {
            opacity: 1;
            -webkit-transform: scale3d(1, 1, 1);
            transform: scale3d(1, 1, 1);
        }

        .button--antiman:hover::after {
            opacity: 0;
            -webkit-transform: scale3d(0.8, 0.8, 1);
            transform: scale3d(0.8, 0.8, 1);
        }

        .button--round-l {
            border-radius: 40px;
        }

        .button--text-medium {
            font-weight: 500;
        }

        .thumbnail {
            display: block;
            padding: 4px;
            margin-bottom: 20px;
            line-height: 1.42857143;
            background-color: transparent !important;
            border: 0 !important;
            border-radius: 4px;
            -webkit-transition: border .2s ease-in-out;
            -o-transition: border .2s ease-in-out;
            transition: border .2s ease-in-out;
        }
    </style>
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
            <div class="container">
                <div class="row">
                    <div class="col-xs-6 col-sm-3"></div>
                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <a href='/manage/weixin/menu/list'>
                                    <button class="button button--antiman button--round-l button--text-medium">
                                        <span>自定义菜单</span></button>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <a href='/manage/weixin/reply/list'>
                                    <button class="button button--antiman button--round-l button--text-medium">
                                        <span>微信回复规则</span></button>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-3"></div>
                </div>
                <div class="row">
                    <div class="col-xs-6 col-sm-3"></div>
                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <a href='/manage/banner?type=1'>
                                    <button class="button button--antiman button--round-l button--text-medium">
                                        <span>APP推荐</span></button>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <a href='/manage/weixin/imageText'>
                                    <button class="button button--antiman button--round-l button--text-medium">
                                        <span>群发消息列表</span></button>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-3"></div>
                </div>
                <div class="row">
                    <div class="col-xs-6 col-sm-3"></div>
                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail">
                            <div class="caption">
                                <a href='/manage/codeBurse'>
                                    <button class="button button--antiman button--round-l button--text-medium">
                                        <span>送红包活动</span></button>
                                </a>
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

</body>
</html>

