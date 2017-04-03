<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="TAGS" class="BEANS.tagsBean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String role = (String) session.getAttribute("role");
    String owner = request.getParameter("owner");
    String id = (String) session.getAttribute("id");
    String list = TAGS.getList(id, request);
    if (role.indexOf("a") < 0 && role.indexOf("k") < 0 && !TAGS.permitted) {
        response.sendRedirect("notPermited.html");
    }
    String data = "";
    if (owner != null) {
        data = ", data: {owner: \"" + owner + "\"}";
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Редактирование структуры тегов Базы знаний</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            .inno{margin:5px;margin-left:20px; }
            .item {width:150px; font-size: 12px; padding: 4px;}
            .cat {background-image: url("folder_opened.png");background-position: left;background-repeat: no-repeat;padding-left: 20px;}
            .r {background-image: url("img/home.png");}
            .fil {background-image: url("img/tag.png");background-position: left;background-repeat: no-repeat;padding-left: 20px;}
            .menu {position: absolute;width: 150px;background: #FFFFFF;border-color: #DEDEDE #BFBFBF #BFBFBF #DEDEDE;border-style: solid;border-width: 1px; font-size: 12px;}
            .menu ul, .menu li {margin: 0;}
            .menu li {border-bottom: 1px solid #DEDEDE;display: block;padding: 2px;}
            .menu li a {display: block;padding: 2px 5px;text-decoration: none;color: #336699; cursor: pointer;}
            .menu li a:hover {background: #ccc;color:red; cursor: pointer;}
            .menu li.NoEnter a:hover {background: white; cursor: auto;}
            .item-hover {background: red;}
            .item-hover  {color: white;}
            #main {position: relative;margin: 25px; margin-top: 0px; width:auto;height: 50%;overflow-y: auto;overflow-x: hidden;border-color: #dedede #dedede #bfbfbf #dedede;
                   border-width: 1px;border-style: solid; background-color: #d8d8ff;}
            #moveMain{margin: 0; padding:10px;position: absolute;z-index: 99999;top:49px;left: 99px;width: 100%;height: 100%;color: black;background-color: white;
                      display: none;overflow-y: auto;overflow-x: hidden; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;}
            .hov{color:black;background-color: white;}
            #form {margin: 25px;padding:20px;position: absolute;z-index: 99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white;
                   display: none;overflow: auto;}
            label {display:block;margin-top:10px;}
            .close {color:red;width:100%;text-align:right;}
            .close span {cursor: pointer;}
            .menu li.NoEnter {display: none}
            .filepath{color:gray;}
            #hic {margin: 25px;padding:20px;position: absolute;z-index: 99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white;
                  display: none;overflow: auto;}
            #form input {width:100%;}
            #FILEFORM input[type=submit], #mhic input[type=submit] {padding: 5px; width: 110px; margin: 10px auto; display: block;}
            #moveMain .close {margin-bottom: 10px;}
            #moveMain .fil {display: none;}
            #moveMain .cat:hover {cursor: pointer; color: red;}
            #mhic {font-size: 12px;}
            #mhic label {color: gray;}
        </style>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("tags.jsp", request)%></style>
    </head>
    <body id="tags_jsp">
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
                                            <tr><td id="slogan" valign="center"><%= slogan%></td>
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
                                        <li><a href="findTag.jsp" class="footmenu">Закончить</a></li>
                                        <li> <a href="http://exxo.ru/howto/knowledge-management-howto.html#creation" class="footmenu howto" target="_blank">?</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <table id="grid" class="grid">
                <tbody id="atbody"><tr id="tr">
                        <td id="column-3" valign="top">
                            <div id="main" class="exxo-shadow"></div>
                        </td>
                    </tr>
                </tbody>
            </table>

        </div>
        <div class="menu aui-overlaycontext-hidden">
            <ul><li class="item-add c "><a >Добавить категорию</a></li>
                <li class="item-move np"><a >Переместить</a></li>
                <li class="item-kill np"><a >Удалить</a></li>
                <li class="item-change np" id="item-change"><a >Изменить название</a></li>
                <li class="file-change np" id="file-change"><a >Сменить владельца</a></li>
                <li class="file-add c " id="file-add"><a >Добавить тег</a></li></ul></div>
        <div id="moveMain"><div class="close" id="closeM"><span>ЗАКРЫТЬ</span></div><div id="Move"></div></div>
        <div id="form"><div class="close" id="close"><span>ЗАКРЫТЬ</span></div>
            <form id="FILEFORM" action="addTag" method="POST">
                <label for="name">Название</label><input type="text" name="name" id="name">
                <label for="discription">Владелец</label><select name="owner" id="owner"><%= list%></select><br><br>
                <input type="hidden" name="tag" id="tag" value="0">
                <input type='hidden' name='superior' value='0' id="superior">
                <input type="submit" value="Сохранить"></form>
        </div>
        <div id="hic"><div class="close" id="closehic"><span>ЗАКРЫТЬ</span></div><div id="mhic"></div></div>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', 'aui-overlay-context', 'aui-io', function(A) {
            var overlayMask = new A.OverlayMask().render();
            var conf = {
            from:{opacity: 0},
                    to: {opacity: 1},
                    on:{'start': function(){
                    if (!this.get('reverse')){
                    overlayMask.set('target', document).show();
                    this.get('node').setStyle('display', 'block');
                    }}},
                    after: {'end': function(){
                    this.set('reverse', !this.get('reverse'));
                    this.stop();
                    if (this.get('node').getStyle('opacity') == 0) this.get('node').setStyle('display', 'none');
                    if (!this.get('reverse'))overlayMask.set('target', document).hide();
                    }}};
            A.one('#form').setStyle('opacity', '0');
            A.one('#form').plug(A.Plugin.NodeFX, conf);
            A.one('#hic').setStyle('opacity', '0');
            A.one('#hic').plug(A.Plugin.NodeFX, conf);
            A.one('#moveMain').setStyle('opacity', '0');
            A.one('#moveMain').plug(A.Plugin.NodeFX, conf);
            var myHeight = A.DOM.winHeight() - 160;
            if (myHeight < 300) myHeight = 300;
            A.one('#close span').on('click', function(event){closer(A.one('#form')); });
            A.one('#closehic span').on('click', function(event){closer(A.one('#hic')); });
            Reload();
            function Reload(){
            A.one('div.menu').purge(true);
            A.one("#main").unplug(A.Plugin.IO);
            A.one("#main").plug(A.Plugin.IO, { uri: 'tags', method: 'POST', on:{'end':function(event){var menuOverlay = new A.OverlayContext({
            boundingBox: '.menu', hideDelay: 200, hideOn: 'mouseleave', showDelay: 0, showOn: 'mouseenter', trigger: '.item',
                    align: { node: null, points: [ 'bl', 'br' ] }, on: {
            show: function(event) {
            menuOverlay.get('currentNode').addClass('item-hover');
            if (menuOverlay.get('currentNode').hasClass("fil"))
                    A.all(".c").addClass("NoEnter");
            else if (menuOverlay.get('currentNode').hasClass("cat"))
                    A.all(".f").addClass("NoEnter");
            if (menuOverlay.get('currentNode').hasClass("r"))
                    A.all(".np").addClass("NoEnter");
            },
                    hide: function(event) {

                    A.all('.item').removeClass('item-hover');
                    A.all(".c").removeClass("NoEnter");
                    A.all(".f").removeClass("NoEnter");
                    A.all(".np").removeClass("NoEnter");
                    },
                    xyChange: function(event) {
                    A.all('.item').removeClass('item-hover');
                    A.all(".c").removeClass("NoEnter");
                    A.all(".f").removeClass("NoEnter");
                    A.all(".np").removeClass("NoEnter");
                    }},
                    after:{xyChange: function(event) {
                    this.get('currentNode').addClass('item-hover');
                    if (this.get('currentNode').hasClass("fil"))
                            A.all(".c").addClass("NoEnter");
                    else if (this.get('currentNode').hasClass("cat"))
                            A.all(".f").addClass("NoEnter");
                    if (this.get('currentNode').hasClass("r"))
                            A.all(".np").addClass("NoEnter");
                    }}}).render();
            A.all('.item').removeClass('item-hover');
            A.all('div.menu li').each(function(){this.on('click', function(event){
            var node = menuOverlay.get('currentNode');
            var item = this;
            var Enter = true;
            if (item.hasClass("NoEnter")) Enter = false;
            menuOverlay.hide();
            registrat(item, node, Enter);
            })});
            A.one("#main").setStyle("height", myHeight + "px");
            }}<%= data%>
            });
            }
            function registrat(item, node, Enter){
            if (item.hasClass("item-move") && Enter) itemMove(node);
            else if (item.hasClass("item-kill") && Enter) itemKill(node);
            else if (item.hasClass("item-add") && Enter) itemAdd(node);
            else if (item.hasClass("item-change") && Enter) itemChange(node);
            else if (item.hasClass("file-add") && Enter) fileAdd(node);
            else if (item.hasClass("file-change") && Enter) fileChange(node);
            }
            function itemKill(node){
            if (!node.next().hasChildNodes()) alterText(node.get("id"), "0"<% if (owner != null) {%>, "<%= owner%>"<%}%>, "killTag");
            else alert("Можно удалить только пустой раздел. Необходимо удалить содержимое раздела!")
            }
            function itemAdd(node){
            var id = node.get("id");
            A.one('#FILEFORM input#superior').set("value", id);
            A.one('#FILEFORM input#tag').set("value", "0");
            A.one('#form').fx.run();
            }
            function itemChange(node){
            var html = node.cloneNode(true);
            if (html.one("span")) html.one("span").remove();
            var name = window.prompt("введите новое имя раздела или тега", html.text());
            if (name) alterText(node.get("id"), name<% if (owner != null) {%>, "<%= owner%>"<%}%>, "changeTag");
            }
            function itemMove(node){
            var div = A.one("#Move");
            var Div = A.one("#moveMain");
            div.purge(true);
            div.unplug(A.Plugin.IO);
            Div.setStyle("width", A.DOM.winWidth() - 198 + "px");
            Div.setStyle("height", A.DOM.winHeight() - 98 + "px");
            div.plug(A.Plugin.IO, { uri: 'tags', method: 'POST', on:{end:function(){
            //alert("Укажите, куда в хотите переместить подразделение");
            var nodlist = A.all("#moveMain .cat");
            var unit_id = node.get("id");
            var n = A.Node.one("#moveMain [id=" + unit_id + "]").setStyle("display", "none");
            n.next().setStyle("display", "none");
            //A.one("#moveMain .r").setStyle("display","none");
            nodlist.on("mouseenter", function(event){event.target.addClass("hov")});
            nodlist.on("mouseleave", function(event){event.target.removeClass("hov")});
            nodlist.on("click", function(event){
            var superior = event.target.get("id");
            alterText(unit_id, superior<% if (owner != null) {%>, "<%= owner%>"<%}%>, "moveTag");
            div.purge(true);
            div.unplug(A.Plugin.IO);
            Div.fx.run();
            });
            }}<%= data%>
            });
            Div.fx.run();
            }
            A.one("#closeM span").on("click", function(event){A.one("#moveMain").fx.run(); });
            function fileAdd(node){
            var id = node.get("id");
            A.one('#FILEFORM input#superior').set("value", id);
            A.one('#FILEFORM input#tag').set("value", "1");
            A.one('#form').fx.run();
            }
            function alterText(p1, p2<% if (owner != null) {%>, owner<%}%>, servlet){
            A.io.request(servlet, {cache: false, data: {p1: p1,
                    p2: p2 <% if (owner != null) {%>, owner:<%= owner%><%}%>
            }, on:{success:function(){Reload()}},
                    method: 'post'});
            }
            function closer(node){node.fx.run(); }
            function fileChange(node){
            var fileID = node.get("id");
            A.one('#mhic').unplug(A.Plugin.IO);
            A.one('#mhic').plug(A.Plugin.IO, { uri: 'tagForm', data:{id:fileID <% if (owner != null) {%>, owner:<%= owner%><%}%> }, method: 'POST'});
            A.one('#hic').fx.run();
            }
            A.one(".aui-overlaymask-content").setStyle("height", "100%");
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>