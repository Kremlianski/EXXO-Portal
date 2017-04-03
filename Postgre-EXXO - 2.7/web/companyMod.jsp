<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") < 0 && role.indexOf("c") < 0 && role.indexOf("Z") < 0) {
        response.sendRedirect("notPermited.html");
    }

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Редактирование структуры компании</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            .inno{margin:5px;margin-left:20px;}
            .title1{width:250px;font-size:15px;}
            .title2{width:250px;font-size:13px;text-decoration:underline;font-weight:bolder;}
            .title3{width:250px;}
            .title4{font-style:italic;width:250px;}
            .title5{width:250px;}
            .title6{font-style:italic;width:250px;}
            .title7{width:250px;}
            .title8{font-style:italic;width:250px;}
            #main {position: relative;margin:0px 10px;height: auto;margin-bottom: 20px;background-color: white;padding: 4px; width: 90%;
                   border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px; border-style: solid;}
            div.item {display: block;width: 160px;padding: 20px 10px;border: 1px solid #BFBFBF; margin: 10px;color: #3C3D58;background-color:#E2E4EE;font-size: 12px;
                      text-align: left;}
            div.inno {padding-left: 70px;}
            .item:hover {background-color:white;color: red;cursor: pointer;}
            .menu1 {position: absolute;width: 150px;background: #FFFFFF;border-color: #DEDEDE #BFBFBF #BFBFBF #DEDEDE;border-style: solid;border-width: 1px; font-size: 11px;}
            .menu1 ul, .menu li {margin: 0;}
            .menu1 li {border-bottom: 1px solid #DEDEDE;display: block;padding: 2px;}
            .menu1 li a {display: block;padding: 2px 5px;text-decoration: none;color: #972626; cursor: pointer;}
            .menu1 li.NoEnter a:hover {background: white; cursor: auto;}
            .menu1 li a:hover {background: #C1C2E0; color: white; cursor: pointer;}
            .item-hover {background: red;}
            .item-hover  a {color: white;}
            #moveMain{position: absolute;background-color: transparent;  width: 100%; height: auto; z-index: 99999999;display:none; left:0px; top: 0px;}
            #imgesInner {position:relative;width:auto;height:auto;margin: 4px; background-color:rgb(250,250,250);border: 1px solid #bfbfbf; border-radius: 5px;}
            .close {color:red;width:auto;text-align:right; padding: 5px; padding-right: 10px;}
            .close span {cursor: pointer;}
        </style>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("companyMod.jsp", request)%></style>
    </head>
    <body id="companyMod_jsp">
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
                                    <ul class="topMenu">
                                        <jsp:include page="topMenu.xhtml" flush="true" />
                                        <li><a href="company.xhtml" class="footmenu">Закончить</a></li>
                                        <li> <a href="http://exxo.ru/howto/staff-howto.html#structure" class="footmenu howto" target="_blank">?</a></li>
                                    </ul>
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
                            <div class="exxo-menu exxo-shadow" id="menu">
                                <jsp:include page="menu-emp.xhtml" flush="true" />
                            </div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="main" class="exxo-shadow"></div>
                        </td>
                    </tr> 
                </tbody>
            </table>

        </div>
        <div class="menu1 aui-overlaycontext-hidden exxo-shadow">
            <ul>
                <li class="item-add">
                    <a >добавить подразделение</a>
                </li>
                <li class="item-move">
                    <a >Переместить</a>
                </li>
                <li class="item-kill"><a >удалить</a></li>
                <li class="item-change" id="item-change"><a >изменить название</a>
                </li>
            </ul>
        </div>
        <div id="moveMain">
            <div id="imgesInner">
                <div class="close"><span>ЗАКРЫТЬ</span></div>
                <div id="moveMain1"></div>
            </div>

        </div>
        <script type="text/javascript">
            AUI().ready('aui-overlay-context', 'aui-io', 'aui-overlay-mask', 'anim', function (A) {
                var overlayMask = new A.OverlayMask().render();
                var div = A.one("#moveMain");
                var conf = {
                    from: {opacity: 0},
                    to: {opacity: 1},
                    on: {'start': function () {
                            if (!this.get('reverse')) {
                                overlayMask.set('target', document).show();
                                this.get('node').setStyle('display', 'block');
                            }
                        }},
                    after: {'end': function () {
                            this.set('reverse', !this.get('reverse'));
                            this.stop();
                            if (this.get('node').getStyle('opacity') == 0)
                                this.get('node').setStyle('display', 'none');
                            if (!this.get('reverse'))
                                overlayMask.set('target', document).hide();
                        }}};
                div.setStyle('opacity', '0');
                div.plug(A.Plugin.NodeFX, conf);
                A.one('#moveMain .close span').on('click', function (event) {
                    div.fx.run();
                });
                Reload();
                function Reload() {
                    A.one('div.menu1').purge(true);
                    A.one("#main").unplug(A.Plugin.IO);
                    A.one("#main").plug(A.Plugin.IO, {uri: 'company', method: 'POST', on: {'end': function (event) {
                                var menuOverlay = new A.OverlayContext({
                                    boundingBox: '.menu1', hideDelay: 500, hideOn: 'mouseleave', showDelay: 0, showOn: 'mouseenter', trigger: '#main .item',
                                    align: {node: null, points: ['bl', 'br']},
                                    on: {
                                        show: function (event) {
                                            menuOverlay.get('currentNode').addClass('item-hover');
                                        },
                                        hide: function (event) {
                                            A.all('#main .item').removeClass('item-hover');
                                        },
                                        xyChange: function (event) {
                                            A.all('#main .item').removeClass('item-hover');
                                        }
                                    }, after: {xyChange: function (event) {
                                            this.get('currentNode').addClass('item-hover');
                                        }}}).render();
                                A.all('.item').removeClass('item-hover');
                                A.all('div.menu1 li').each(function () {
                                    this.on('click', function (event) {
                                        var node = menuOverlay.get('currentNode');
                                        var item = this;
                                        registrat(item, node);
                                    })
                                });
                            }}});
                }
                function registrat(item, node) {
                    if (item.hasClass("item-move"))
                        itemMove(node);
                    else if (item.hasClass("item-kill"))
                        itemKill(node);
                    else if (item.hasClass("item-add"))
                        itemAdd(node);
                    else if (item.hasClass("item-change"))
                        itemChange(node);
                }
                function itemKill(node) {
                    if (!node.next().hasChildNodes())
                        alterText(node.get("id"), "0", "killUnit");
                    else
                        alert("Нельзя удалить подразделение, содержащее другие подразделения. Необходимо удалить или переместить сперва их!")
                }
                function itemAdd(node) {
                    var name = window.prompt("введите название подразделения", "");
                    if (name)
                        alterText(node.get("id"), name, "addUnit");
                }
                function itemChange(node) {
                    var name = window.prompt("введите название подразделения", node.text());
                    if (name)
                        alterText(node.get("id"), name, "changeUnit");
                }
                function itemMove(node) {
                    if (node.get("id") != '1') {
                        var div = A.one("#moveMain");
                        var inner = A.one("#imgesInner");
                        var div1 = A.one("#moveMain1");
                        div1.purge(true);
                        div1.unplug(A.Plugin.IO);
                        A.one('#imgesInner').setStyle("minHeight", A.DOM.winHeight() - 10 + "px");
                        div1.plug(A.Plugin.IO, {uri: 'company', method: 'POST', on: {end: function () {
                                    var nodlist = A.all("#moveMain .title1,#moveMain .title2,#moveMain .title3,#moveMain .title4,#moveMain .title5,#moveMain .title6,#moveMain .title7,#moveMain .title8");
                                    var unit_id = node.get("id");
                                    var n = A.Node.one("#moveMain [id=" + unit_id + "]").setStyle("display", "none");
                                    n.next().setStyle("display", "none");
                                    nodlist.on("mouseenter", function (event) {
                                        event.target.addClass("hov")
                                    });
                                    nodlist.on("mouseleave", function (event) {
                                        event.target.removeClass("hov")
                                    });
                                    nodlist.on("click", function (event) {
                                        var superior = event.target.get("id");
                                        alterText(unit_id, superior, "moveUnit");
                                        div1.purge(true);
                                        div1.unplug(A.Plugin.IO);
                                        div.fx.run();
                                    });
                                }}});
                        div.fx.run();
                    } else
                        alert("Операция невозможна!")
                }
                function alterText(p1, p2, servlet) {
                    A.io.request(servlet, {cache: false, data: {p1: p1, p2: p2}, on: {success: function () {
                                Reload()
                            }}, method: 'post'});
                }
            });
        </script>
        <script type="text/javascript" src="resources/scripts/top-menu-fixed.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>