<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    if (request.getParameter("r") == null) {
        response.sendRedirect("blogEditor.jsp");
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=10" />
        <title>Новое сообщение</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <meta http-equiv="pragma" content="no-cache" />
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="yui/build/assets/skins/sam/skin.css">
        <link rel="stylesheet" type="text/css" href="yui/build/fonts/fonts-min.css" />
        <script type="text/javascript" src="yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
        <script type="text/javascript" src="yui/build/element/element-min.js"></script>
        <script type="text/javascript" src="yui/build/container/container_core-min.js"></script>
        <script type="text/javascript" src="yui/build/menu/menu-min.js"></script>
        <script type="text/javascript" src="yui/build/button/button-min.js"></script>
        <script type="text/javascript" src="yui/build/editor/my-editor-min.js"></script>
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            @import url('editor.css');
            div#topMenu {width: 100%; height: 36px; background-color: gray;background-image: url("img/spr.png");background-position: bottom;background-repeat: repeat-x;}
            #foot {height: 30px;padding-top: 6px;margin: 0;background-color: transparent;width: auto;border: 0;text-align: left;padding-left: 275px;background-image: none;}
            .footmenu {font-size: 15px;text-decoration: none;color: white;background-color: transparent; padding: 5px; display: inline-block;}
            body {background-color:#EEEEF1;}
            #dopuski {background-color:white; padding: 0px; width: auto; margin: 0px; display: block; }
            #bus {padding:0px; padding-bottom: 0px; width: auto; margin: 0px; display: block;}
            #f {background-color:white; padding:0px; padding-bottom: 20px; padding-top: 0px; width: auto; margin: 0px; display: block; }
            label {display:block; margin-top: 0px;}
            #tema input[type=text] {width: 350px; border: 1px solid #bfbfbf; padding:4px; box-sizing: border-box; -moz-box-sizing: border-box;} 
            input[type=submit] {padding: 4px;}
            textarea {width: 100%; overflow: auto;}
            #tagH {font-size: 12px; margin-left: 60px; font-weight: normal;}
            #tags {padding: 10px; margin: 40px; margin-top: 10px;}
            .tag {padding:3px; display: inline-block; background-color: greenyellow; border: 1px solid green;margin:3px;color:black;-moz-border-radius: 4px;
                  -webkit-border-radius: 4px;border-radius: 4px;}
            #tagChange {margin-top: 10px; padding: 10px; text-align: left; clear: both;}
            #tops {text-align: left; font-size: 12px; margin: 20px;}
            #content {width: 700px; margin-left: 20px; background-color: white; height: auto; padding: 20px; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px; border-style: solid; padding-bottom: 100px;}
            .hidden {display: none; }
            .emps:hover {cursor: pointer; color:red;}
            .emps {color: #2F2F70; display: inline-block; margin-right: 10px;}
            #dt, .invis {padding: 3px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgEmp span.yui-toolbar-icon {
                background-image: url("emp.png");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgEmp-selected span.yui-toolbar-icon {
                background-image: url("emp.png");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgMy span.yui-toolbar-icon {
                background-image: url("imgMy.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgMy-selected span.yui-toolbar-icon {
                background-image: url("imgMy.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgMyK span.yui-toolbar-icon {
                background-image: url("imgK.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgMyK-selected span.yui-toolbar-icon {
                background-image: url("imgK.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            #close {color:red;width:auto;text-align:right; padding:5px; padding-right: 10px;}
            #inserter {padding: 20px;}
            #containerImg img.photo {margin: 2px;}
            .hov{color:black;background-color: white;}
            .inno{margin:5px;margin-left:20px;}
            .cat {background-position: left;background-repeat: no-repeat;display: inline-block; width: 180px;vertical-align: top;margin: 10px;font-weight: bold; }
            .fil {margin: 5px;float: left;font-style: italic;font-weight: bolder;}
            .file-hover {color: red;}
            .yui-editor-container .yui-editor-editable-container {width: 610px !important; padding-right: 0px; padding-left:90px;}
            #msgpost {width: 700px;}
            #editorContainer {background-color: white; width:700px;}
            #submitB input {padding: 5px;  cursor: pointer; margin: 0px;}
            #tdMenu {vertical-align: top; padding-top: 25px; padding-left: 15px;}
            #center {vertical-align: top; padding-top: 25px; padding-left: 10px;}
            .mytop {width: 100%; margin-top: 10px;}
            .mytop .one {width: 100px; vertical-align: middle;}
            .mytop select {width: 350px;box-sizing: border-box; -moz-box-sizing: border-box;}
            .mytop .two {width: 350px; text-align: left;}
            .mytop .three {text-align: right;}
            #empMenu button {height: 24px; padding-top: 5px; min-width: 26px;}
            #empMenu {margin-top: 10px; margin-bottom: 20px;}
            #menu-navi {width: 95%; margin-left:10px; height: auto;}
            #menu-navi table {width:100%;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi span {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi span:hover {background-color: white; cursor: pointer;}
            span#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            span#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            span#backward:hover {background-image: url('small/backward.png'); }
            span#forward:hover {background-image: url('small/forward.png');}
            #imges {position: absolute;background-color: transparent;  width: 100%; height: auto; z-index: 99999999;display:none; left:0px; top: 0px;}
            #imgesInner {position:relative;width:auto;height:auto;margin: 4px; background-color:rgb(250,250,250);border: 1px solid #bfbfbf; border-radius: 5px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("blogInsert.jsp", request)%>
        </style>
    </head>
    <body class="yui-skin-sam" id="blogInsert_jsp">
        <div id="topMenu">
            <div id="foot">
                <ul class="topMenu">
                    <jsp:include page="topMenu.xhtml" flush="true" />
                    <li><a href="correspondence.jsp" class="footmenu">Закончить</a></li>
                    <li> <a href="http://exxo.ru/howto/mail-howto.html#send" class="footmenu howto" target="_blank">?</a></li>
                </ul>
            </div>
        </div>
        <jsp:include page="blogInsert.xhtml" flush="true" />
        <input type="hidden" value="<%=BASE.VER.getMaxBloks(request.getServletContext())%>"  id="maxRows" />
        <script type="text/javascript" charset="utf-8" src="scripts/topMenu.js"></script>
    </body>
</html>