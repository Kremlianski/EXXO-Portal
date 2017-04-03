<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="HTML" class="BEANS.unitHtml" scope="application"/>
<%
    String role = (String) session.getAttribute("role");
    String id = request.getParameter("id");
    String user = (String) session.getAttribute("id");
    if ((role.indexOf("a") < 0 && role.indexOf("b") < 0 && role.indexOf("c") < 0)
            && (role.indexOf("m") < 0 || !BASE.Master.isLegalUnitSec(user, id, request))) {
        response.sendRedirect("notPermited.html");
    }
    String html = HTML.getHtml(id, request);
%>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" >
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <title>Редактор портала</title>
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #submit {padding: 4px; margin: 15px; cursor: pointer;}
            a.html {display:block; margin: 15px; text-decoration: none; font-size: 12px;}
            a.html:hover {text-decoration: underline; color:#972626;}
            #editor-center {padding: 25px;}
            td#menu {width: 200px; vertical-align: top; padding-top: 25px;}
            td#menu input {width: 140px; height: 50px;}
            div#topMenu {width: 100%; height: 36px; background-color: gray;background-image: url("img/spr.png");background-position: bottom;background-repeat: repeat-x;}
            #foot {height: 30px;padding-top: 6px;margin: 0;background-color: transparent;width: auto;border: 0;text-align: left;padding-left: 275px;background-image: none;}
            .footmenu {font-size: 15px;text-decoration: none;color: white;background-color: transparent; padding: 5px; display: inline-block;}
            body {background-color:white;}
            #leftMenu {width: 210px; padding: 10px; margin-left: 10px; background-color: white; margin-top: 25px;}
            #content textarea {width: 100%; overflow: auto; border: 0; height: 300px;background-color: #2B0202;color: white; padding: 7px;}  
            #formcss {margin:25px; text-align: center;}
            #content h3 {text-align: center; font-size: 14px;}
            #contentInner {position: relative;margin-left: 10px; width: 670px;height: auto;margin-bottom: 10px;background-color: white; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE; border-width: 1px; border-style: solid; padding: 0;}
        </style>
    </head>
    <body id="unitHtmlEditor_jsp">
        <div id="topMenu"><div id="foot"><ul class="topMenu">
                    <jsp:include page="topMenu.xhtml" flush="true" /><li><a href="unit.jsp?id=<%=id%>" class="footmenu">Закончить</a></li>  
                    <li> <a href="http://exxo.ru/howto/cms-howto.html#editelement" class="footmenu howto" target="_blank">?</a></li>
                </ul></div></div>
        <form action="alterUnitHTML" method="post">
            <table id="content"><tr><td id="menu">
                        <input type="submit" id='submit' value="Сохранить"/>
                        <a href="unitEditor.jsp?id=<%=id%>" class="html">Визуальный редактор</a>       
                    </td><td id="editor-center">
                        <div id="contentInner" class="exxo-shadow1">
                            <h3>Редактирование HTML</h3>
                            <div id=formcss>       
                                <textarea cols="80" rows="20" name="html"><%=html%></textarea>
                                <input type="hidden" name="id" value="<%=id%>">
                            </div></div> 
                    </td>
                    <td id="info"></td></tr></table></form>
        <script type="text/javascript" charset="utf-8" src="scripts/topMenu.js"></script>
        <script type="text/javascript">
            AUI().ready('node-menunav', function (A) {
                var myHeight = A.DOM.winHeight() - 100;
                if (myHeight < 300)
                    myHeight = 300;
                A.one("#content").setStyle("height", myHeight + "px");
                A.one("#content textarea").setStyle("height", myHeight - 120 + "px");
            });
        </script>
    </body>
</html>