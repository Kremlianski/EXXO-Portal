<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="TM" class="BEANS.TerritoriesMod" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") < 0 && role.indexOf("c") < 0) {
        response.sendRedirect("notPermited.html");
    }
    String list = TM.getList(request);
    String op = TM.getOp();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Список офисов. Редактировать!</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #menuT {position: relative;width: 90%;display: block; background-color: white; margin-top: 20px;}
            #menuT select { margin: 10px; width: 175px;}
            #addNew{position: absolute;text-align: center;padding: 10px;top:100px;left: 30%;width: 30em;background-color: #426487;
                    color: white;z-index: 99999;display: none; border: 1px solid white;}
            label {display:block;}
            #tel,#short,#adress{width:20em;font-size: 12px;}
            #submit{padding: 3px;}
            #menuT form{display: block;}
            #drop{position: absolute;text-align: center;padding: 10px;top:100px;left: 30%;width: 35em;background-color: red;
                  color: white;z-index: 99998;display: none; border: 1px solid white;}
            #drop input {padding: 5px; cursor: pointer;}
            #drop span{padding-bottom:10px;font-size:11pt;display:block;}
            #close{color:red;width:100%;text-align:right;}
            #close span {cursor:pointer;}
            #menuT>div {border: 1px solid #dedede; border-color: #dedede #dedede #bfbfbf #dedede; background-color: #E2E4EE;}
            #container {position: relative; margin-right: 0px; margin-top: 0px; font-size: 11px; width: 90%;}
            #content {font-size:12px;margin-top:0px;}
            #content table{width:100%;background-color: white;margin-top: 0px;}
            .first {width:25%;padding-top:10px;}
            .second{width:50%;padding-top:10px;}
            .third{width:25%;padding-top:10px;}
            #content table td{padding: 4px;border-bottom-color: #DEDEDE;border-bottom-style: solid;border-bottom-width: 1px;}
            #b1, #b2 {margin: 10px; font-size: 12px;}
            #b1 {margin-top: 10px;}
            #b1  button, #b2 button {width: 175px; text-align: left;}
            #edits {width: 250px;}
        </style>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("territoriesMod.jsp", request)%></style>
    </head><body id="territoriesMod_jsp">
        <div id="content-wrapper"><div id="header"><table id="tableH"><tbody><tr><td id="tdlogo"><img src="LOGO" id="logo"></td><td>
                                <div id="head"><table><tbody><tr><td id="slogan" valign="center"><%= slogan%></td><td><jsp:include page="poisk" flush="true" />
                                                </td></tr></tbody></table></div><div id="foot"><ul class="topMenu"><jsp:include page="topMenu.xhtml" flush="true" /><li><a href="territories.xhtml" class="footmenu">Закончить</a></li>
                                        <li> <a href="http://exxo.ru/howto/staff-howto.html#offices" class="footmenu howto" target="_blank">?</a></li></ul></div>
                            </td></tr></tbody></table></div>
            <table id="grid" class="grid"><tbody id="atbody"><tr id="tr"><td id="menu-td" class="menu-td" valign="top"><div class="exxo-menu exxo-shadow" id="menu"><jsp:include page="menu-emp.xhtml" flush="true" /></div></td><td id="edits" valign="top">
                            <div id="menuT" class="exxo-shadow"><div><form action="javascript:;">
                                        <select id="offices" name="offices"><option value="0" selected="selected">--Выберите территорию--</option><%=op%></select>
                                        <select id="action" name="action" disabled="true"><option value="0" selected="selected">--Выберите действие--</option>
                                            <option value="changeOffice">Изменить данные</option>
                                            <option value="dropOffice">Удалить офис</option>
                                        </select></form><div id="b1"></div>
                                    <div id="b2"></div></div></div>

                        </td>
                        <td id="column-3" valign="top"><div id="container" class="exxo-shadow">
                                <div id="content">
                                    <%=list%>
                                </div></div></td></tr></tbody></table><jsp:include page="FOOTER" flush="true" /></div>
        <div id="addNew"><div id="close"><span>ЗАКРЫТЬ</span></div>
            <form action="addOffice" method="POST" id="forma" >
                <label for="short">Короткое название</label><input id="short" type="text" name="short">
                <label for="adress">Адрес</label><input id="adress" type="text" name="adress">
                <label for="tel">Телефон (телефоны)</label><input id="tel" type="text" name="tel"><br><br>
                <input type="submit" value="OK" id="submit">
            </form></div>
        <div id="drop"><span></span>
            <form action="dropOffice" method="POST" id="dropForma">
                <input type="hidden" name="shorts" id="shorts">
                <input type="submit" value="Да">
                <input type="button" value="Нет" id="no">
            </form></div>
        <script type="text/javascript">
            AUI().ready('aui-button-item', 'anim-node-plugin', 'aui-overlay-mask', function (A) {
                var node = A.one('#addNew');
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
                        }}}
                node.plug(A.Plugin.NodeFX, conf);
                new A.ButtonItem({icon: 'plusthick', label: 'Новый офис', handler: {fn: function (e) {
                            A.one("#short").set("value", "");
                            A.one("#adress").set("value", "");
                            A.one("#tel").set("value", "");
                            A.one("#forma").setAttribute("action", "addOffice");
                            node.fx.run();
                        }, type: 'click'}}).render(A.one("#b1"));
                new A.ButtonItem({icon: 'transferthick-r-l', label: 'Переезд', handler: {fn: function (e) {
                            location = "movement.jsp";
                        }}}).render(A.one("#b2"));
                var overlayMask = new A.OverlayMask().render();
                A.one("#drop").setStyle('opacity', '0');
                A.one("#drop").plug(A.Plugin.NodeFX, conf);
                A.one("#no").on("click", function (e) {
                    A.one('#drop').fx.run();
                });
                A.one('#addNew').setStyle('opacity', '0');
                A.one("#close span").on("click", function (e) {
                    node.fx.run();
                });
                A.one("#offices").on("change", function (e) {
                    if (this.get("value") != 0)
                        A.one("#action").removeAttribute("disabled");
                });
                A.one("#action").on("change", function (e) {
                    var of = A.one("#offices").get("value");
                    var ac = this.get("value");
                    if (of != 0 && ac != 0) {
                        if (ac == "changeOffice") {
                            A.one("#short").set("value", of);
                            A.one("#forma").setAttribute("action", ac);
                            A.all("#content tr.datas").each(function () {
                                if (this.one(".first").text() == of) {
                                    A.one("#adress").set("value", this.one(".second").text());
                                    A.one("#tel").set("value", this.one(".third").text());
                                }
                                ;
                            });
                            node.fx.run();
                        } else if (ac == "dropOffice") {
                            A.one("#shorts").set("value", of);
                            var span = "Хотите ли вы удалить Офис ''" + of + "''?";
                            A.one('#drop span').text(span);
                            A.one('#drop').fx.run();
                        }
                    }
                });
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>