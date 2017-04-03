<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%@ page import="java.net.URLDecoder"%><%
    String slogan = SLOGAN.getSlogan(request);
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") < 0 && role.indexOf("b") < 0) {
        response.sendRedirect("notPermited.html");
    }
    String title = request.getParameter("title");
    if (request.getQueryString() != null) {
        String qqq = request.getQueryString();
        title = URLDecoder.decode(qqq.substring(qqq.indexOf("title") + 6), "utf8");
    }
    String page_id = request.getParameter("id");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Вставка страницы в главное меню</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="index.css" type="text/css" /><link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            #pim {font-size:12px;width: 250px;}
            #pim li {display:none;}
            #pim .item-menu {display:list-item;}
            #afterpim {visibility:hidden;width: 300px;margin: 20px auto;}
            #content {position: relative;margin-left: 10px; width: 670px;height: auto;margin-bottom: 10px;background-color: white;padding: 4px; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE; border-width: 1px; border-style: solid;}
            div#mes {margin:20px auto; width: 400px;}   
            #pim .yui3-menu-hidden {visibility: visible; top:0; left:0;z-index:0;}
            #pim .yui3-menu {position: relative; padding: 4px 30px; z-index:0;}
            #pim {padding: 20px;}
            #pim a {white-space: normal; text-decoration: none; padding: 4px;}
            .yui3-skin-sam #pim .yui3-menu-content, .yui3-skin-sam #pim .yui3-menu .yui3-menu .yui3-menu-content {
                border:0 !important;
            }
            .yui3-skin-sam #pim .yui3-menu-label, .yui3-skin-sam #pim .yui3-menu .yui3-menu .yui3-menu-label {
                background-image: none;
                font-weight: bold;
                color: rgb(12,12,12);
                cursor: pointer;
            }
            .yui3-skin-sam #pim .yui3-menu ul, .yui3-skin-sam #pim .yui3-menu ul ul {
                border:0;
            }
        </style>
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <style><%=CSS.getCSS(request)%></style>
        <style><%=CSS3.getCSS("addInMenu.jsp", request)%></style>
    </head>
    <body class="yui3-skin-sam" id="addInMenu_jsp">
        <div id="content-wrapper">
            <div id="header">
                <table id="tableH">
                    <tbody>
                        <tr>
                            <td id="tdlogo"><img src="LOGO" id="logo"></td>
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
                                <div id="pim" class="yui3-menu">
                                    <jsp:include page="menu.jsp" flush="true" />
                                </div>
                                <div id="mes">Выберите подменю, куда вы хотите добавить новый элемент</div>
                                <div id="afterpim"></div>

                            </div> 
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="menus" class="yui3-menu">
            <jsp:include page="menu.jsp" flush="true" />
        </div>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/mainMenu.js"></script>
        <script type="text/javascript">
            AUI().ready('aui-node', 'aui-button-item', 'anim', 'aui-io-request', function (A) {
                var title = "<%=title%>";
                var uri = "?id=<%=page_id%>";
                var currentNode;
                var button = new A.ButtonItem(
                        {icon: 'check', label: "", handler: {
                                fn: function () {
                                    currentNode.get('parentNode').one('ul').append('<li class="yui3-menuitem item"><a class="yui3-menuitem-content" href="' + uri + '">' + title + '</a></li>');
                                    A.io.request("menu", {cache: false, data: {menu: A.one('#pim').html()}, method: 'post',
                                        on: {end: function () {
                                                location = "main.jsp" + uri;
                                            }}
                                    });
                                }, type: 'click'}}).render("#afterpim");
                myAnim = new A.Anim({
                    node: '#afterpim', to: {opacity: 1}, duration: 1});
                myAnim1 = new A.Anim({node: '#mes', to: {opacity: 0}, duration: 1});
                A.all('li.item-menu a').each(function () {
                    this.on('click', function (event) {
                        var text = this.text();
                        A.one("#afterpim").setStyle('opacity', 0);
                        button.set('label', 'вставить в ' + text);
                        A.one("#afterpim").setStyle('visibility', 'visible');
                        currentNode = this;
                        myAnim1.run();
                        myAnim.run();
                    })
                });
                function alterText(menu, servlet) {
                    A.io.request(servlet, {cache: false, data: {menu: menu}, method: 'post'});
                }
                A.Node.all('#pim li div').each(function () {
                    this.removeAttribute('style')
                });
            });
        </script>
    </body>
</html>