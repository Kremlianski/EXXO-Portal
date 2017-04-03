<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String role = (String) session.getAttribute("role");
    String owner = request.getParameter("owner");
    String id = (String) session.getAttribute("id");
    if (owner != null && !owner.equals(id)) {
        if (role.indexOf("a") >= 0 || role.indexOf("d") >= 0) {
            if (!owner.equals("-100")) {
                response.sendRedirect("notPermited.html");
            }
        } else {
            response.sendRedirect("notPermited.html");
        }
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
        <title>Редактирование фотоальбомов</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            .inno{margin:5px;margin-left:40px;}
            .item {width:250px; font-size: 12px; padding-top: 10px;}
            .cat {background-image: url("small/pictures.png") !important; background-position: left !important;background-repeat: no-repeat !important;padding-left: 60px !important; height: 48px !important;}
            .fil {background-position: center left;background-repeat: no-repeat;padding-left: 60px;height: 50px; border: 1px solid #bfbfd2; -moz-border-radius: 4px;
                  -webkit-border-radius: 4px; border-radius: 4px; background-color: white; text-align: left;}
            .menu1 {position: absolute;width: 150px;background: #FFFFFF;border-color: #DEDEDE #BFBFBF #BFBFBF #DEDEDE;border-style: solid;border-width: 1px; font-size: 12px;} 
            .menu1 ul, .menu1 li {margin: 0;}
            .menu1 li {border-bottom: 1px solid #DEDEDE;display: block;padding: 2px; background-color:#FDF2F2;}
            .menu1 li a {display: block;padding: 1px 5px;text-decoration: none; cursor: pointer;}
            .menu1 li a:hover {background: #C1C2E0; color:white; cursor: pointer;}
            .menu1 li.NoEnter a:hover {background: white; cursor: auto;}
            .menu1 li.NoEnter {display: none;}
            div.item-hover {background-color: #C1C2E0; border-radius: 0;}
            .item-hover  {color: white;}
            #main {position: relative;margin: 25px;width:auto;height: auto;margin-bottom: 10px; margin-top: 0px; margin-left: 10px; overflow-y: auto;overflow-x: hidden;border-color: gray;border-width: 1px;border-style: solid; background-color: white; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE; border-width: 1px;border-style: solid;padding:4px;}
            #moveMain .fil {display: none;}
            .hov{color:black;background-color: white;}
            #form {margin: 25px;padding:20px;position: absolute;z-index:99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white;
                   display: none;overflow: auto;}
            label {display:block;margin-top:10px;}
            .close {color:red;width:100%;text-align:right;}
            .close span {cursor: pointer;}
            .menu1 li.NoEnter a{color: gray; display: none;}
            .filepath{color:gray; font-size: 10px;}
            #hic {margin: 25px;padding:20px;position:absolute;z-index:99998;top:20px;left:10%;height:auto;width:auto;color:black;background-color:white;
                  display: none;overflow: auto;}
            label.notshow {display:inline;margin-bottom: 10px;margin-left: 10px;}
            .notadv {color:gray;}
            .notadv:hover {color:white;}
            #form input[type=text] {width:260px;}
            #form select {width:260px;}
            #form textarea, #hic textarea{width:260px;}
            #FILEFORM input[type=submit], #mhic input[type=submit] {padding: 5px; width: 110px; margin: 10px auto; display: block;}
            #moveMain .fil {display: none;}
            #moveMain .close {margin-bottom: 10px;}
            .hov {color: red;}
            #moveMain .inno{margin:5px;margin-left:20px;}
            #moveMain div.cat {background-image: url("small/pictures.png");background-position: left;background-repeat: no-repeat;height: 48px; padding-left: 60px; margin: 20px;font-size: 14px;width: 150px; color: gray;}
            #moveMain .cat {cursor: pointer;}
            #form, #hic {font-size: 12px;}
            .cover {background-color: #E2E4EE;}
            textarea {overflow: auto;}
            .loading {background:none !important;}
            #moveMain{position: absolute;background-color: transparent;  width: 100%; height: auto; z-index: 99999999;display:none; left:0px; top: 0px;}
            #imgesInner {position:relative;width:auto;height:auto;margin: 4px; background-color:rgb(250,250,250);border: 1px solid #bfbfbf; border-radius: 5px;}
            #closeM {padding: 5px; padding-right: 10px;}
            #moveMain div.exxo-images .cat {height: auto !important; width: 180px; display: inline-block; margin: 5px; padding: 10px; vertical-align: top; cursor: pointer; background-image: none !important;padding-left: 10px !important}
        </style>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("gallariesMod.jsp", request)%></style>
    </head>
    <body id="gallariesMod_jsp">
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
                                        <li><a href="galClassic.jsp<% if (owner != null) {%>?owner=<%=owner%><%}%>" class="footmenu">Закончить</a></li>
                                        <li> <a href="http://exxo.ru/howto/gallery-howto.html#edit" class="footmenu howto" target="_blank">?</a></li>
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
                                <jsp:include page="menu-pic.xhtml" flush="true" />
                            </div>

                        </td>
                        <td id="column-3" valign="top">
                            <div id="main" class="exxo-shadow"></div>
                        </td>
                    </tr> 
                </tbody>
            </table>

        </div>
        <div class="menu1 aui-overlaycontext-hidden exxo-shadow"><ul>
                <li class="item-add c  "><a >Добавить альбом</a></li>
                <li class="item-move f np"><a >Переместить</a></li>
                <li class="item-kill np"><a >Удалить</a></li>
                <li class="item-change np" id="item-change"><a >Изменить название</a></li>
                <li class="file-change np" id="file-change"><a >Изменить данные</a></li>
                <li class="file-add c np" id="file-add"><a >Сохранить картинку</a></li>
                <li class="files-add c np" id="files-add"><a >Сохранить картинки</a></li>
                <li class="tags-add f np"><a >Добавить теги</a></li>
                <li class="cover f np"><a >Сделать обложкой</a></li>
            </ul></div>
        <div id="form"><div class="close" id="close"><span>ЗАКРЫТЬ</span></div>
            <form id="FILEFORM" action="addPic" enctype="multipart/form-data" method="POST">
                <label for="file">Выберите изображение</label><input type="file" name="file" id="file">
                <label for="name">Имя файла</label><input type="text" name="name" id="name" maxlength="50">
                <label for="discription">Описание</label><textarea name="discription" rows="5" cols="22" ></textarea>
                <label for="tags">Теги</label><textarea name="tags" rows="5" cols="22" ></textarea><br><br>
                <input type="checkbox" value="1" name="notshow"><label class="notshow">Не афишировать</label>
                <br><br>
                <% if (owner != null) {%><input type="hidden" name="owner" value="<%= owner%>"><%}%>
                <input type="submit" value="Сохранить">
            </form>
        </div>
        <div id="hic">
            <div class="close" id="closehic"><span>ЗАКРЫТЬ</span></div>
            <div id="mhic"></div>

        </div>
        <div id="moveMain">
            <div id="imgesInner">
                <div class="exxo-close" id="closeM"><span>ЗАКРЫТЬ</span></div>
                <div id="Move" class="exxo-images"></div>

            </div>

        </div>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', 'aui-overlay-context', 'aui-io', 'imageloader', 'aui-loading-mask', function(A) {
            var overlayMask = new A.OverlayMask().render();
            //var myHeight=A.DOM.winHeight()-200;
            //if(myHeight<300) myHeight=300;
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
            A.one('#form').setStyle('opacity', 0);
            A.one('#form').plug(A.Plugin.NodeFX, conf);
            A.one('#form').plug(A.LoadingMask, {background: '#000'});
            A.one('#form input[type=submit]').after('click', function(){A.one('#form').loadingmask.toggle()});
            A.one('#hic').setStyle('opacity', 0);
            A.one('#hic').plug(A.Plugin.NodeFX, conf);
            A.one('#close span').on('click', function(event){closer(A.one('#form')); });
            A.one('#closehic span').on('click', function(event){closer(A.one('#hic')); });
            A.one('#moveMain').setStyle('opacity', 0);
            A.one('#moveMain').plug(A.Plugin.NodeFX, conf);
            Reload();
            function Reload(){
            A.one('div.menu1').purge(true);
            A.one("#main").unplug(A.Plugin.IO);
            A.one("#main").plug(A.Plugin.IO, { uri: 'gallaries', method: 'POST',
                    on:{'end':function(event){
                    A.all('.fil').each(function(){
                    this.addClass('loading');
                    });
                    var foldGroup = new A.ImgLoadGroup({ name: 'fold group', foldDistance: 25, className: 'loading'});
                    var menuOverlay = new A.OverlayContext({boundingBox: '.menu1', hideDelay: 200, hideOn: 'mouseleave', showDelay: 10, showOn: 'mouseenter', trigger: '#main .item',
                            align: { node: null, points: [ 'bl', 'br' ] }, on: {show: function(event) {
                    menuOverlay.get('currentNode').addClass('item-hover');
                    if (menuOverlay.get('currentNode').hasClass("fil")) A.all(".c").addClass("NoEnter");
                    else if (menuOverlay.get('currentNode').hasClass("cat")) A.all(".f").addClass("NoEnter");
                    if (menuOverlay.get('currentNode').hasClass("r")) A.all(".np").addClass("NoEnter");
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
                            if (this.get('currentNode').hasClass("fil")) A.all(".c").addClass("NoEnter");
                            else if (this.get('currentNode').hasClass("cat")) A.all(".f").addClass("NoEnter");
                            if (this.get('currentNode').hasClass("r")) A.all(".np").addClass("NoEnter"); }}}).render();
                    A.all('.item').removeClass('item-hover');
                    A.all('div.menu1 li').each(function(){this.on('click', function(event){
                    var node = menuOverlay.get('currentNode');
                    var item = this;
                    var Enter = true;
                    if (item.hasClass("NoEnter")) Enter = false;
                    menuOverlay.hide();
                    registrat(item, node, Enter);
                    })});
                    //A.one("#main").setStyle("height",myHeight+"px");
                    }}<%= data%>
            });
            }
            function registrat(item, node, Enter){
            if (item.hasClass("item-move") && Enter) itemMove(node);
            else if (item.hasClass("item-kill") && Enter) itemKill(node);
            else if (item.hasClass("item-add") && Enter) itemAdd(node);
            else if (item.hasClass("item-change") && Enter) itemChange(node);
            else if (item.hasClass("file-add") && Enter) fileAdd(node);
            else if (item.hasClass("files-add") && Enter) filesAdd(node);
            else if (item.hasClass("file-change") && Enter) fileChange(node);
            else if (item.hasClass("tags-add") && Enter) addTags(node);
            else if (item.hasClass("cover") && Enter) cover(node);
            }
            function itemKill(node){
            if (!node.next().hasChildNodes()) if (window.confirm("Удалить?")) alterText(node.get("id"), "0"<% if (owner != null) {%>, "<%= owner%>"<%}%>, "killGal");
            else alert("Можно удалить только пустой альбом. Необходимо удалить содержимое альбома!")}
            function itemAdd(node){
            var name = window.prompt("введите имя альбома", "");
            if (name) alterText(node.get("id"), name<% if (owner != null) {%>, "<%= owner%>"<%}%>, "addGal"); }
            function itemChange(node){
            var html = node.cloneNode(true);
            if (html.one("span")) html.one("span").remove();
            var name = window.prompt("введите новое имя изображения или альбома", html.text());
            if (name) {if (name.length <= 50) {alterText(node.get("id"), name<% if (owner != null) {%>, "<%= owner%>"<%}%>, "changeGal"); }
            else alert("Сократите имя до 50 символов!"); }
            }
            function itemMove(node){
            var div = A.one("#Move");
            var Div = A.one("#moveMain");
            div.purge(true);
            div.unplug(A.Plugin.IO);
            A.one("#imgesInner").setStyle("minHeight", A.DOM.winHeight() - 10 + "px");
            div.plug(A.Plugin.IO, { uri: 'catalogues', method: 'POST', on:{end:function(){
            var unit_id = node.get("id");
            Div.all('.cat').each(function(){this.purge(); this.on('click', function(){
            var superior = this.get("id");
            alterText(unit_id, superior<% if (owner != null) {%>, "<%= owner%>"<%}%>, "moveGal");
            Div.fx.run();
            }); });
            }}<%= data%>
            });
            Div.setStyle('opacity', '0');
            Div.fx.run();
            Div.setY(A.one('win').get('docScrollY'));
            //Div.scrollIntoView();
            }
            A.one("#closeM span").on("click", function(event){A.one("#moveMain").fx.run(); });
            function fileAdd(node){
            var id = node.get("id");
            var str = "<input type='hidden' name='superior' value='" + id + "'>";
            A.one('#FILEFORM').append(A.Node.create(str));
            A.one('#form').fx.run();
            A.one('#form').setY(A.one('win').get('docScrollY') + 20);
            }
            function alterText(p1, p2<% if (owner != null) {%>, owner<%}%>, servlet){
            A.io.request(servlet, {cache: false, data: {p1: p1, p2: p2 <% if (owner != null) {%>, owner:<%= owner%><%}%>
            }, on:{success:function(){Reload(); }}, method: 'post'});
            }
            function closer(node){node.fx.run(); }
            function fileChange(node){
            var fileID = node.get("id");
            A.one('#mhic').unplug(A.Plugin.IO);
            A.one('#mhic').plug(A.Plugin.IO, { uri: 'picForm', data:{id:fileID <% if (owner != null) {%>, owner:<%= owner%><%}%> }, method: 'POST'});
            A.one('#hic').fx.run();
            A.one('#hic').setY(A.one('win').get('docScrollY') + 20);
            }
            function addTags(node) {
            var fileID = node.get("id");
            location = "addTags.jsp?el=" + fileID + "&s=gallaries<%if (owner != null) {%>&o=<%= owner%><%}%>";
            }
            function cover(node) {
            var fileID = node.get("id");
            location = "changeCover?id=" + fileID + "<%if (owner != null) {%>&owner=<%= owner%><%}%>";
                }
                function filesAdd(node) {
                var id = node.get("id");
                location = "addPic.xhtml?gal=" + id + "<%if (owner != null) {%>&owner=<%= owner%><%}%>";
                    }
                    A.one(".aui-overlaymask-content").setStyle("height", "100%");
                    });
        </script>
        <script type="text/javascript" src="resources/scripts/top-menu-fixed.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>