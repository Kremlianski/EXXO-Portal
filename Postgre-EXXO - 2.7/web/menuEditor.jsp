<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String user = (String) session.getAttribute("id");
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") < 0 && role.indexOf("b") < 0 && role.indexOf("Z") < 0) {
        response.sendRedirect("notPermited.html");
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Редактирование меню</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            #pim {font-size:12px;width: 80%;overflow-y: auto;overflow-x: auto;background-color: white;}
            .item {width:220px;}
            .menu1 {position: absolute;width: 150px;background: #FFFFFF;border-color: #DEDEDE #BFBFBF #BFBFBF #DEDEDE;border-style: solid;border-width: 1px; font-size: 11px;}
            .menu1 ul, .menu li {margin: 0;}
            .menu1 li {border-bottom: 1px solid #DEDEDE;display: block;padding: 2px;}
            .menu1 li a {display: block;padding: 2px 5px;text-decoration: none;color: #972626; cursor: pointer;}
            .menu1 li.NoEnter a:hover {background: white; cursor: auto;}
            #pim .item-hover {background-color: #C1C2E0;}
            #pim .item-hover  a {color: white;}   
            #pim .yui3-menu-hidden {visibility: visible; top:0; left:0;z-index:0;}
            #pim .yui3-menu {position: relative; padding: 10px 30px; z-index:0;}
            #pim {padding: 20px; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE; border-style: solid; border-width: 1px;}
            #pim a {white-space: normal; text-decoration: none; padding: 4px;}
            .menu1 li a:hover {background: #C1C2E0; color: white; cursor: pointer;}
            div#pim li.item-menu > a {color: black;}
            #plus {font-size: 12px;text-align: left; margin: 10px; margin-left: 15px;}
            #plus a {padding-left: 5px; text-decoration: none;}
            #plus a:hover {text-decoration: underline; color: #972626; }
            .yui3-skin-sam #pim .yui3-menu-content, .yui3-skin-sam #pim .yui3-menu .yui3-menu .yui3-menu-content {
                border:0 !important;
            }
            .yui3-skin-sam #pim .yui3-menu-label, .yui3-skin-sam #pim .yui3-menu .yui3-menu .yui3-menu-label {
                background-image: none;
                font-weight: bold;
                color: rgb(12,12,12);
            }
            .yui3-skin-sam #pim .yui3-menu ul, .yui3-skin-sam #pim .yui3-menu ul ul {
                border:0;
            }
        </style>
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("menuEditor.jsp", request)%>
        </style>
    </head>
    <body  class="yui3-skin-sam" id="menuEditor_jsp">
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
                                    <a href="main.jsp" class="footmenu">Закончить</a>  <a href="http://exxo.ru/howto/cms-howto.html#menu" class="footmenu howto" target="_blank">?</a>
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
                            <div id="plus">
                                <span class="aui-button-icon aui-icon aui-icon-plusthick"></span><a href="javascript:;" id="top">Добавить подменю на верхний уровень</a>
                            </div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="pim" class="yui3-menu exxo-shadow">
                                <jsp:include page="menu.jsp" flush="true" />
                            </div>
                        </td>
                    </tr> 
                </tbody>
            </table>
        </div>
        <div id="menus" class="yui3-menu">
            <jsp:include page="menu.jsp" flush="true" />
        </div>
        <div class="menu1 aui-overlaycontext-hidden exxo-shadow">
            <ul><li class="item-up"><a >сдвинуть вверх</a></li>
                <li class="item-down"><a >сдвинуть вниз</a></li>
                <li class="item-kill"><a >удалить</a></li>
                <li class="item-add"><a >добавить пункт</a></li>
                <li class="item-submenu"><a >добавить подменю</a></li>
                <li class="item-change" id="item-change"><a >изменить</a></li>
                <li class="item-add-new"><a >в новом окне</a></li></ul>
        </div>
        <script type="text/javascript">
            AUI().ready('aui-overlay-context', 'aui-io-request', function (A) {
                var user =<%= user%>;
                var menuOverlay = new A.OverlayContext({
                    boundingBox: '.menu1', hideDelay: 500, hideOn: 'mouseleave', showDelay: 0, showOn: 'mouseenter', trigger: '#pim .item>a',
                    align: {node: null, points: ['bl', 'br']}, on: {
                        show: function (event) {
                            menuOverlay.get('currentNode').addClass('item-hover');
                        },
                        hide: function (event) {
                            A.all('.item>a').removeClass('item-hover');
                        },
                        xyChange: function (event) {
                            A.all('.item>a').removeClass('item-hover');
                        }},
                    after: {xyChange: function (event) {
                            this.get('currentNode').addClass('item-hover');
                        }}}).render();
                A.all('.item>a').removeClass('item-hover');
                A.all('div.menu1 li').each(function () {
                    this.on('click', function (event) {
                        var node = menuOverlay.get('currentNode').get('parentNode');
                        var item = this;
                        registrat(item, node);
                    })
                });
                function registrat(item, node) {
                    if (item.hasClass("item-up"))
                        itemUp(node);
                    else if (item.hasClass("item-down"))
                        itemDown(node);
                    else if (item.hasClass("item-kill"))
                        itemKill(node);
                    else if (item.hasClass("item-add"))
                        itemAdd(node, null);
                    else if (item.hasClass("item-submenu"))
                        itemSubmenu(node);
                    else if (item.hasClass("item-change"))
                        itemChange(node);
                    else if (item.hasClass("item-add-new"))
                        itemAdd(node, "1");
                    menuOverlay.set('trigger', '#pim .item>a');
                    alterText(A.one('#pim').html(), "menu");
                }
                function itemUp(node) {
                    if (node.previous()) {
                        node.get('parentNode').insert(node, node.previous());
                        menuOverlay.hide();
                    }
                }
                function itemDown(node) {
                    if (node.next()) {
                        node.get('parentNode').insert(node.next(), node);
                        menuOverlay.hide();
                    }
                }
                function itemKill(node) {
                    node.unplug().remove().destroy();
                    menuOverlay.hide();
                }
                function itemAdd(node, blank) {
                    var target = "";
                    if (blank != null)
                        target = " target='_blank'";
                    if (node.hasClass('item-menu'))
                        node.one('ul').append('<li class="yui3-menuitem item"><a class="yui3-menuitem-content" href="#"' + target + '>Новый пункт</a></li>');
                    else
                        node.get('parentNode').append('<li class="yui3-menuitem item"><a class="yui3-menuitem-content" href="#"' + target + '>Новый пункт</a></li>');
                    menuOverlay.hide();
                }
                function itemSubmenu(node) {
                    var D = new Date();
                    var id = 'li_' + D.getTime() + '-' + user;
                    var str = '<li class="yui3-menuitem item-menu item"><a class="yui3-menu-label yui3-menuitem-content" href="#' + id + '">Подменю</a><div id="' + id + '" class="yui3-menu"><div class="yui3-menu-content"><ul><li class="yui3-menuitem item"><a class="yui3-menuitem-content" href="#">Новый пункт</a></li></ul></div></div></li>'
                    if (node.hasClass('item-menu'))
                        node.one('ul').append(str);
                    else
                        node.get('parentNode').append(str);
                    menuOverlay.hide();
                }
                function itemChange(node) {
                    var name = window.prompt("введите новое имя пункта", node.one('a').text());
                    if (name)
                        node.one('a').text(name);
                    if (!node.hasClass('item-menu')) {
                        var host = window.prompt("введите адрес страницы", node.one('a').getAttribute('href'));
                        if (host)
                            node.one('a').setAttribute('href', host);
                    }
                    menuOverlay.hide();
                }
                function alterText(menu, servlet) {
                    var io = A.io.request(servlet, {cache: false, data: {menu: menu}, method: 'post'});
                }
                A.Node.all('#pim li div').each(function () {
                    this.removeAttribute('style')
                });
                A.one("#top").on("click", function () {
                    var D = new Date();
                    var id = 'li_' + D.getTime() + '-' + user;
                    var str = '<li class="yui3-menuitem item-menu item"><a class="yui3-menu-label yui3-menuitem-content" href="#' + id + '">Подменю</a><div id="' + id + '" class="yui3-menu"><div   class="yui3-menu-content"><ul><li class="yui3-menuitem item"><a class="yui3-menuitem-content" href="#">Новый пункт</a></li></ul></div></div></li>'
                    A.one('#pim #topUl').append(str);
                    menuOverlay.set('trigger', '#pim .item>a');
                    alterText(A.one('#pim').html(), "menu");
                })
                var myHeight = A.DOM.winHeight() - 200;
                if (myHeight < 100)
                    myHeight = 100;
                A.one("#pim").setStyle("height", myHeight + "px");
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/mainMenu.js"></script>
    </body>
</html>