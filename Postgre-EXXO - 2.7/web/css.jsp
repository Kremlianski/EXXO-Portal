<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") < 0 && role.indexOf("b") < 0) {
        response.sendRedirect("notPermited.html");
    }
    String css = CSS.getCSS(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Добавление стиля, общего для всего портала</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="index.css" type="text/css" /><link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            #content {position: relative;margin-left: 10px; width: 670px;height: auto;margin-bottom: 10px;background-color: white; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE; border-width: 1px; border-style: solid; padding: 0;} 
            #content textarea {width: 100%; overflow: auto; border: 0; height: 300px;background-color: #2B0202;color: white;}  
            #formcss {margin:25px; text-align: center;}
            #content h3 {text-align: center; font-size: 14px; margin-top: 4px;}
            #content input[type=submit] {width: 200px; padding: 2px; margin: auto;}
        </style>
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("css.jsp", request)%>
        </style>
    </head>
    <body class="yui3-skin-sam" id="css_jsp">
        <div id="content-wrapper">
            <div id="header">
                <table id="tableH">
                    <tbody>
                        <tr>
                            <td id="tdlogo">
                                <img src="LOGO" id="logo"></td>
                            <td>
                                <div id="head">
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td id="slogan" valign="center"><%= slogan%></td>
                                                <td>
                                                    <jsp:include page="poisk" flush="true" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div id="foot">
                                    <jsp:include page="topMenu" flush="true" />
                                    <a href="main.jsp" class="footmenu">Закончить</a> 
                                    <a href="http://exxo.ru/howto/cms-howto.html#css" class="footmenu howto" target="_blank">?</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <table id="grid" class="grid">
                <tbody id="atbody">
                    <tr id="tr">
                        <td id="menu-td" class="menu-td" valign="top"><div class="menu" id="menu"></div></td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow">
                                <h3>Вставьте стиль, общий для всех страниц и сервисов</h3>
                                <div id=formcss><form action="addCSS" method="POST">
                                        <textarea name="css"><%=css%></textarea><br><br>
                                        <input type="submit" value="Изменить стиль">
                                    </form></div>
                            </div>

                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="menus" class="yui3-menu"><jsp:include page="menu.jsp" flush="true" /></div>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/mainMenu.js"></script>
        <script type="text/javascript">
            AUI().ready('aui-node', function (A) {
                var myHeight = A.DOM.winHeight() - 150;
                if (myHeight < 300)
                    myHeight = 300;
                A.one("#content").setStyle("height", myHeight + "px");
                A.one("#content textarea").setStyle("height", myHeight - 120 + "px");
            });
        </script>
    </body>
</html>