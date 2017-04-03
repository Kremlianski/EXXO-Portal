<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="DC" class="BEANS.DocClassic" scope="request"/><%
    String slogan = SLOGAN.getSlogan(request);
    String owner = request.getParameter("owner");
    String id = request.getParameter("id");
    DC.getParams(request);
    String c = DC.c + "";
    String v = DC.v + "";
    String free = DC.free + "%";
    if (DC.free <= 0) {
        free = "<span style='color:red;'>места нет</span>";
    }
    boolean editable = false;
    String role = (String) session.getAttribute("role");
    if (owner == null || (role.indexOf("o") >= 0 && owner.equals("-100")) || (role.indexOf("p") >= 0 && owner.equals("-101"))
            || (role.indexOf("q") >= 0 && owner.equals("-102")) || (role.indexOf("r") >= 0 && owner.equals("-103"))) {
        editable = true;
    }
    if (!editable) {
        response.sendRedirect("notPermited.html");
    }
    String data = "";
    if (owner != null) {
        data = ", data: {owner: \"" + owner + "\"}";
    }
    boolean general = false;
    if (owner != null && owner.equals("-100")) {
        general = true;
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Документы: редактирование</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" /><link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #main {position: relative;margin: 25px; margin-left: 10px; width:auto;height: auto;margin-bottom: 10px;background-color: white;padding: 4px;overflow-y: auto;overflow-x: hidden;}
            table#small {width: 100%;}
            #counts {width: 150px;font-size:11px;margin: 10px; margin-top: 25px; color: black;border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;padding: 10px;
                     background-color: white}
            #counts td.res{color:#972626; text-align: right;}
            #counts table {width: 100%;margin-bottom: 10px;}
            .inno{margin:5px;margin-left:20px; }
            table#small #main div.item {width:250px; min-height: 20px; padding: 0px; padding-left:26px; padding-top: 8px; padding-right: 5px; text-align: left; padding-bottom: 3px; }
            .cat {background-image: url("folder_opened.png");background-position: left;background-repeat: no-repeat;}
            .fil {background-image: url("file.png");background-position: left;background-repeat: no-repeat; border: 1px solid #e1e1f8; -moz-border-radius: 6px;
                  -webkit-border-radius: 6px; border-radius: 6px; background-color: #F3F0F0;}
            .menu1 {position: absolute;width: 150px;background: #FFFFFF;border-color: #DEDEDE #BFBFBF #BFBFBF #DEDEDE;border-style: solid;border-width: 1px; font-size: 12px;} 
            .menu1 ul, .menu1 li {margin: 0;}
            .menu1 li {border-bottom: 1px solid #DEDEDE;display: block;padding: 2px;}
            .menu1 li a {display: block;padding: 1px 5px;text-decoration: none; cursor: pointer;}
            .menu1 li a:hover {background: #C1C2E0; color:white; cursor: pointer;}
            .menu1 li.NoEnter a:hover {background: white; cursor: auto;}
            .menu1 li.NoEnter {display: none;}
            .item-hover {background-color: #C1C2E0; border-radius: 0;}
            .item-hover  {color: white;}
            #form, #form1 {margin: 25px;padding:20px;position: absolute;z-index: 99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white;
                           display: none;overflow: auto;}
            label {display:block;margin-top:10px;}
            .close {color:red;width:100%;text-align:right;}
            .close span {cursor: pointer;}
            .menu li.NoEnter a{color: gray;}
            .filepath{color:gray;}
            #hic {margin: 25px;padding:20px;position: absolute;z-index: 99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white;
                  display: none;overflow: auto;}
            .image {background-image: url("small/photo.png");background-position:  4px center;background-repeat: no-repeat;}
            .text {background-image: url("small/page_white_text.png");background-position:  4px center;background-repeat: no-repeat;}
            .pdf {background-image: url("small/doc_pdf.png");background-position:  4px center;background-repeat: no-repeat;}
            .doc {background-image: url("small/page_white_word.png");background-position:  4px center;background-repeat: no-repeat;}
            .noresult {background-image: url("small/application2.png");background-position:  4px center;background-repeat: no-repeat;}
            .xls {background-image: url("small/page_excel.png");background-position:  4px center;background-repeat: no-repeat;}
            .zip {background-image: url("small/compress.png");background-position:  4px center;background-repeat: no-repeat;}
            .rar {background-image: url("small/compress.png");background-position:  4px center;background-repeat: no-repeat;}
            .ooo_doc {background-image: url("small/doc_offlice.png");background-position:  4px center;background-repeat: no-repeat;}
            .ooo_ss {background-image: url("small/doc_table.png");background-position:  4px center;background-repeat: no-repeat;}
            .audio {background-image: url("small/doc_music.png");background-position:  4px center;background-repeat: no-repeat;}
            .video {background-image: url("small/films.png");background-position:  4px center;background-repeat: no-repeat;}
            .pp {background-image: url("small/page_white_powerpoint.png"); background-position:  left center;background-repeat: no-repeat;}
            #form input, #form1 input {width:260px;}
            #form select {width:260px;}
            #form textarea, #hic textarea{width:260px;}
            #FILEFORM input[type=submit], #mhic input[type=submit], #FILEFORM1 input[type=submit] {padding: 5px; width: 110px; margin: 10px auto; display: block;}
            #moveMain{margin: 0; padding:10px;position: absolute;z-index: 99999;top:49px;left: 99px;width: 100%;height: 100%;color: black;background-color: white;
                      display: none;overflow-y: auto;overflow-x: hidden; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;}
            #moveMain .fil {display: none;}
            #moveMain .close {margin-bottom: 10px;}
            .hov {color: red;}
            #moveMain .inno{margin:5px;margin-left:20px;}
            #moveMain div.cat {background-image: url("folder_closed.png");background-position: left;background-repeat: no-repeat; padding:6px; padding-left: 20px;}
            #moveMain .cat {cursor: pointer; font-size: 11px;}
            textarea {overflow: auto;}
            a#load {font-size: 12px; text-decoration: none; padding-left: 15px;}
            a#load:hover {color:#972626;}
        </style>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("documentsMode.jsp", request)%></style>
    </head>
    <body id="documentsMod_jsp">
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
                                        <li><a href="docClassic.jsp<% if (owner != null) {%>?owner=<%=owner%><%}%>" class="footmenu">Закончить</a></li>
                                        <li> <a href="http://exxo.ru/howto/documents-howto.html#edit1" class="footmenu howto" target="_blank">?</a></li>
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
                                <jsp:include page="menu-doc.xhtml" flush="true" />
                            </div>
                            <div id="counts" class="exxo-shadow">
                                <table>
                                    <tr>
                                        <td>Файлов:</td>
                                        <td class="res"><%=c%></td>
                                    </tr>
                                    <tr>
                                        <td>Байт:</td>
                                        <td class="res"><%=v%></td>
                                    </tr>
                                    <% if (owner == null || !owner.equals("-100")) {%>
                                    <tr><td>Свободно:</td><td class="res"><%=free%></td></tr>
                                            <% }%>
                                </table>
                            </div>
                            <% if (owner != null && owner.equals("-101")) {%>
                            <a href="LoadInnerDocs" id="load">Загрузить с почты</a>
                            <%}%>
                        </td>
                        <td id="column-3" valign="top"> 
                            <table id="small">
                                <tbody>
                                    <tr>
                                        <td id="tdmain"  valign="top">
                                            <div id="main" class="exxo-shadow"></div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr> 
                </tbody>
            </table>

        </div>
        <div class="menu1 aui-overlaycontext-hidden exxo-shadow">
            <ul><li class="item-add c "><a >Добавить каталог</a></li>
                <li class="item-move np"><a >Переместить</a></li>
                <li class="item-kill np"><a >Удалить</a></li>
                <li class="file-ver f np"><a >Новая версия</a></li>
                <li class="item-change np" id="item-change"><a >Изменить название</a></li>
                <li class="file-change f np" id="file-change"><a >Изменить данные</a></li>
                <li class="file-add c " id="file-add"><a >Сохранить файл</a></li>
                <li class="files-add c " id="files-add"><a >Сохранить файлы</a></li>
                <li class="file-prop f " id="file-prop"><a >Свойства</a></li>
            </ul></div>
        <div id="form"><div class="close" id="close"><span>ЗАКРЫТЬ</span></div>
            <form id="FILEFORM" action="addFile" enctype="multipart/form-data" method="POST">
                <label for="file">Выберите файл</label><input type="file" name="file" id="file">
                <label for="name">Имя файла</label><input type="text" name="name" id="name">
                <label for="dopusk">Допуск</label>
                <select name="dopusk" id="dopusk" <% if (general) { %>disabled="disabled"<% }%>>
                    <option value="0" <% if (owner != null && owner.equals("-100")) { %>selected<% }%>>общий</option>    
                    <option value="1" >по проекту</option>
                    <option value="2" >по группе</option>
                    <option value="3" >по подразделению</option>
                    <option value="4" <% if (!general) { %>selected<% }%>>персональный</option>
                </select>
                <label for="discription">Описание</label><textarea name="discription" rows="5" cols="22" ></textarea>
                <label for="tags">Теги</label><textarea name="tags" rows="5" cols="22" ></textarea><br><br>
                <% if (owner != null) {%><input type="hidden" name="owner" value="<%= owner%>"><%}%>
                <input type="submit" value="Сохранить"></form>
        </div>
        <div id="form1"><div class="close" id="close"><span>ЗАКРЫТЬ</span></div>
            <form id="FILEFORM1" action="fileVer" enctype="multipart/form-data" method="POST">
                <label for="file">Выберите файл</label><input type="file" name="file" id="file">
                <% if (owner != null) {%><input type="hidden" name="owner" value="<%= owner%>"><%}%>
                <input type="submit" value="Сохранить"></form>
        </div>
        <div id="hic"><div class="close" id="closehic"><span>ЗАКРЫТЬ</span></div><div id="mhic"></div></div>
        <div id="moveMain">
            <div class="close"><span>ЗАКРЫТЬ</span></div>
            <div id="moveMain1"></div>
        </div>
        <script type="text/javascript">
            AUI().ready('aui-node', function(A) {
            var myHeight = A.DOM.winHeight() - 150;
            if (myHeight < 300) myHeight = 300;
            A.one("#main").setStyle("height", myHeight + "px");
            });
        </script>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', 'aui-overlay-context', 'aui-io', 'aui-loading-mask', function(A) {
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
            var overlayMask = new A.OverlayMask().render();
            A.one("#moveMain").setStyle('opacity', '0');
            A.one('#form1').setStyle('opacity', '0');
            A.one('#form').setStyle('opacity', '0');
            A.one("#moveMain").plug(A.Plugin.NodeFX, conf);
            A.one('#hic').setStyle('opacity', '0');
            A.one("#hic").plug(A.Plugin.NodeFX, conf);
            A.one('#form1').plug(A.Plugin.NodeFX, conf);
            A.one('#form1').plug(A.LoadingMask, {background: '#000'});
            A.one('#form').plug(A.LoadingMask, {background: '#000'});
            A.one('#form input[type=submit]').after('click', function(){A.one('#form').loadingmask.toggle()});
            A.one('#form1 input[type=submit]').after('click', function(){A.one('#form1').loadingmask.toggle()});
            A.one('#form').plug(A.Plugin.NodeFX, conf);
            A.one('#form1 .close span').on('click', function(event){closer(A.one('#form1')); });
            A.one('#form .close span').on('click', function(event){closer(A.one('#form')); });
            A.one('#moveMain .close span').on('click', function(event){closer(A.one('#moveMain')); });
            A.one('#hic .close span').on('click', function(event){closer(A.one('#hic')); });
            Reload();
            function Reload(){
            A.one('div.menu1').purge(true);
            A.one("#main").unplug(A.Plugin.IO);
            A.one("#main").plug(A.Plugin.IO, { uri: 'documents', method: 'POST', on:{'end':function(event){
            //A.all(".fil").each(function(){this.addClass("exxo-shadow");});  
            var menuOverlay = new A.OverlayContext({
            boundingBox: '.menu1', hideDelay: 200, hideOn: 'mouseleave', showDelay: 10, showOn: 'mouseenter', trigger: '#main .item',
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
            A.all('div.menu1 li').each(function(){this.on('click', function(event){
            var node = menuOverlay.get('currentNode');
            var item = this;
            var Enter = true;
            if (item.hasClass("NoEnter")) Enter = false;
            menuOverlay.hide();
            registrat(item, node, Enter);
            })});
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
            else if (item.hasClass("file-prop") && Enter) fileProp(node);
            else if (item.hasClass("file-ver") && Enter) fileVer(node);
            }
            function itemKill(node){
            if (!node.next().hasChildNodes()) {
            if (confirm("Хотите удалить?")) {
            if (node.hasClass("copy")) alterText(node.get("id"), "0"<% if (owner != null) {%>, "<%= owner%>"<%}%>, "dropCopy");
            else if (node.hasClass('fil')) {
            if (node.hasClass('s_6'))  alert("Данная операция запрещена с утвержденным документом!");
            else alterText(node.get("id"), "0"<% if (owner != null) {%>, "<%= owner%>"<%}%>, "dropFile");
            }
            else alterText(node.get("id"), "0"<% if (owner != null) {%>, "<%= owner%>"<%}%>, "killCat");
            }}
            else alert("Можно удалить только пустой каталог. Необходимо удалить содержимое каталога!")
            }
            function itemAdd(node){
            var name = window.prompt("введите имя каталога", "");
            if (name) alterText(node.get("id"), name<% if (owner != null) {%>, "<%= owner%>"<%}%>, "addCat");
            }
            function itemChange(node){
            var html = node.cloneNode(true);
            if (html.one("span")) html.one("span").remove();
            var name = window.prompt("введите новое имя файла или каталога", html.text());
            if (name) alterText(node.get("id"), name<% if (owner != null) {%>, "<%= owner%>"<%}%>, "changeCat");
            }
            function itemMove(node){
            var unit_id = node.get("id");
            var div = A.one("#moveMain");
            var div1 = A.one("#moveMain1");
            div1.purge(true);
            div1.unplug(A.Plugin.IO);
            div.setStyle("width", A.DOM.winWidth() - 198 + "px");
            div.setStyle("height", A.DOM.winHeight() - 98 + "px");
            div1.plug(A.Plugin.IO, { uri: 'documents', method: 'POST', on:{end:function(){
            var nodlist = A.all("#moveMain .cat");
            var n = A.Node.one("#moveMain [id=" + unit_id + "]").setStyle("display", "none");
            n.next().setStyle("display", "none");
            nodlist.on("mouseenter", function(event){event.target.addClass("hov")});
            nodlist.on("mouseleave", function(event){event.target.removeClass("hov")});
            nodlist.on("click", function(event){
            var superior = event.target.get("id");
            alterText(unit_id, superior<% if (owner != null) {%>, "<%= owner%>"<%}%>, "moveCat");
            div1.purge(true);
            div1.unplug(A.Plugin.IO);
            div.fx.run();
            });
            }}<%= data%>
            });
            div.fx.run();
            }
            function fileAdd(node){
            var id = node.get("id");
            var str = "<input type='hidden' name='superior' value='" + id + "'>";
            A.one('#FILEFORM').append(A.Node.create(str));
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
            A.one('#mhic').plug(A.Plugin.IO, { uri: 'fileForm', data:{id:fileID <% if (owner != null) {%>, owner:<%= owner%><%}%> }, method: 'POST'});
            A.one('#hic').fx.run();
            }
            function fileProp(node){location = "fileProp.jsp?id=" + node.get("id"); }
            function fileVer(node){
            if (node.hasClass("copy")) alert ("Нельзя добавить версию к копии документа!");
            else if (node.hasClass("s_6")) alert ("Данная операция запрещена с утвержденным документом!");
            else {
            var id = node.get("id");
            var str = "<input type='hidden' name='id' value='" + id + "'>";
            A.one('#FILEFORM1').append(A.Node.create(str));
            A.one('#form1').fx.run(); }}
            function filesAdd(node) {
            var id = node.get("id");
            location = "addFile.xhtml?cat=" + id + "<%if (owner != null) {%>&owner=<%= owner%><%}%>";
                }
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
                });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>