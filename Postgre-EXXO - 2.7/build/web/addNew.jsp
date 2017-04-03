<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") < 0 && role.indexOf("b") < 0) {
        response.sendRedirect("notPermited.html");
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Создание новой страницы</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            #content {position: relative;margin-left: 10px; width: 670px;height: auto;margin-bottom: 10px;background-color: white;padding: 54px; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE; border-width: 1px; border-style: solid;}
            #content td {width: 200px; height: 25px;}   
            #content table {margin-bottom: 50px;}
            .lable {margin-bottom: 5px;}
        </style>
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <style><%=CSS.getCSS(request)%></style>
        <style><%=CSS3.getCSS("addNew.jsp", request)%></style>
    </head>
    <body class="yui3-skin-sam" id="addNew_jsp">
        <div id="content-wrapper">
            <div id="header">
                <table id="tableH">
                    <tbody>
                        <tr>
                            <td id="tdlogo">
                                <img src="LOGO" id="logo">
                            </td>
                            <td>
                                <div id="head">
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td id="slogan" valign="center"><%= slogan%></td>
                                                <td><jsp:include page="poisk" flush="true" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div id="foot">
                                    <jsp:include page="topMenu" flush="true" />
                                    <a href="main.jsp" class="footmenu">Закончить</a>  
                                    <a href="http://exxo.ru/howto/cms-howto.html#newpage" class="footmenu howto" target="_blank">?</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <table id="grid" class="grid">
                <tbody id="atbody">
                    <tr id="tr">
                        <td id="menu-td" class="menu-td" valign="top">
                            <div class="menu" id="menu"></div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content"  class="exxo-shadow">
                                <form method="post" action="addNew">
                                    <div class="lable">Введите название страницы</div>
                                    <input type="text" name="title" size="50" maxlength="255" value=""><br>
                                    <table> 
                                        <tbody> 
                                            <tr> 
                                                <td>Обычная страница</td>
                                                <td>Портал</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <input type="radio" name="type" value="1" checked>
                                                </td>
                                                <td>
                                                    <input type="radio" name="type" value="2"></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td> </td>
                                            </tr><tr>
                                                <td>Вставить страницу в меню</td>
                                                <td>Сделать это потом</td>
                                            </tr>
                                            <tr>
                                                <td><input type="radio" name="menu" value="1"  checked></td>
                                                <td><input type="radio" name="menu" value="2"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <button type="submit">Создать страницу</button>
                                </form>
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
    </body>
</html>