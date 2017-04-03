<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    boolean editable = false;
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") >= 0 || role.indexOf("j") >= 0) {
        editable = true;
    }
    if (!editable) {
        response.sendRedirect("notPermited.html");
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Результаты опроса</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            #container {width: 80%; border: 1px solid #dedede; border-bottom: 1px solid #bfbfbf; background-color: white; margin-left: 10px; padding: 20px;}
            #container table {margin-top: 20px; margin-bottom: 30px;}
            #container table tr {border-left: 1px solid #dedede;border-right: 1px solid #dedede;}
            #topik {font-weight: bold;}
            #topik span {padding-right: 10px;}
        </style>
        <style><%=CSS.getCSS(request)%></style>
        <style><%=CSS3.getCSS("qwestResults.jsp", request)%></style>
    </head><body class="yui3-skin-sam" id="qwestResults_jsp">
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
                                    <a href="qwestList.jsp" class="footmenu">Закончить</a>
                                    <a href="http://exxo.ru/howto/vote-howto.html" class="footmenu howto" target="_blank">?</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <table id="grid" class="grid">
                <tbody id="atbody">
                    <tr id="tr">
                        <td id="menu-td" class="menu-td" valign="top"><div class="menu" id="menu"></div>
                        </td>
                        <td id="column-3" valign="top">   
                            <jsp:include page="qwestResults.xhtml" flush="true" />      
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div><div id="menus" class="yui3-menu">
            <jsp:include page="menu.jsp" flush="true" />
        </div>
        <script type="text/javascript" src="scripts/mainMenu.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script> 
    </body>
</html>