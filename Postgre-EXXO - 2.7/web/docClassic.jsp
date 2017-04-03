<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="DC" class="BEANS.DocClassic" scope="request"/><%
    String slogan = SLOGAN.getSlogan(request);
    String own = "";
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
    String data = "";
    if (owner != null) {
        data = ", data: {owner: \"" + owner + "\"}";
        own = "&owner=" + owner;
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
        <title>Документы</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            td.choose {width: 20px; background-image: none;}
            .file-hover td.choose {background-image: url("check.png"); background-position: center center;background-repeat: no-repeat;}
            #panel a {padding-right: 25px;}
            #panel {margin-bottom: 10px;}
            .fil {cursor: pointer;}
            .cat {cursor: pointer;}
            .item-hover {background: red;}
            .item-hover  a {color: white;}
            #main {position: relative;margin: 25px; margin-left: 10px; width:auto;height: auto;margin-bottom: 10px;background-color: white;padding: 4px;overflow-y: auto;overflow-x: hidden;}
            .hov{ color:black; background-color: white;}
            .inno{margin:5px;margin-left:20px;}
            div.cat {background-image: url("folder_closed.png");background-position: left;background-repeat: no-repeat; padding:6px; padding-left: 20px;}
            .file-hover {background-color: #E3E2FD !important;}
            .filepath{color:gray;}
            table#small {width: 100%;}
            #tdmain {width:70%;}
            #tdinfo{width:30%;}
            #info, #info1 {margin:0px 25px;width:auto;height: 100%;margin-left: 0px;padding: 4px;background-color: rgb(252, 252, 252);}
            table#files {width:100%}
            table#files td {text-align: left;}
            td.icon {width:20px; height:24px;}
            td.fname, td.name {padding-left:20px; max-width: 200px;}
            td.created {font-size: 10px; color: #3B3B8F; width:70px; padding-right: 4px;padding-left: 4px;}
            td.dop {width: 20px;}
            tr.cat td.icon {background-image: url("folder_closed.png");background-position: center center;background-repeat: no-repeat;}
            #tdinfo #info img#dop {border: 0;}
            #tags a{margin-right: 6px;}
            table#files tr:nth-of-type(odd) {background-color: #F5F2F2;}
            #counts {width: 150px;font-size:11px;margin: 10px; margin-top: 25px; color: black;border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;padding: 10px;
                     background-color: white}
            #counts td.res{color:#3B3B8F; text-align: right;}
            #counts table {width: 100%;margin-bottom: 10px;}
            #edit1 button, #edit2 button {height: 24px; padding-top: 5px; min-width: 26px;}
            .lable {font-size:12px; padding:5px;}
            div#edit1, div#edit2 {margin-bottom:0px;}
            #info1 {border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid; background-color: #EEEEF1;}
            #info1 td.td2 {padding-left: 6px;}
            .mem .aui-icon {display: inline-block;}
            .mem-label {height: 24px;vertical-align: middle;padding-left: 10px;}
            #form1, #form {margin: 25px;padding:20px;position: absolute;z-index: 99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white; display: none;overflow: auto;}
            #form1 input, #form input {width:260px;}
            #FILEFORM1 input[type=submit], #FILEFORM input[type=submit]  {padding: 5px; width: 110px; margin: 10px auto; display: block;}
            label {display:block;margin-top:10px;}
            .close {color:red;width:100%;text-align:right;}
            .close span {cursor: pointer;}
            .mems {margin-top: 10px;}
            div#info {text-align: left;}
            #form select {width:260px;}
            #form textarea, #hic textarea{width:260px; overflow: auto;}
            #moveMain{margin: 0; padding:10px;position: absolute;z-index: 99999;top:49px;left: 99px;width: 100%;height: 100%;color: black;background-color: white;
                      display: none;overflow-y: auto;overflow-x: hidden; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;}
            #moveMain .fil {display: none;}
            #moveMain .close {margin-bottom: 10px;}
            .hov {color: red;}
            #info a {text-decoration: none;}
            #info:hover a{color:#972626;}
            #info a:hover {color:#972626; text-decoration: underline;}
            #props {margin-top: 10px;}
            a#addgroup{padding-left: 22px;background-image: url('small/application_view_list.png'); background-position: center left; background-repeat: no-repeat; text-decoration: none;}
            a#addgroup:hover {color: #972626;text-decoration: underline;}
            #content td.dop {width: 16px; padding: 4px}
            #too {margin-bottom: 6px;}
            #too span.emps{color: gray;}
            #catName {position: absolute;z-index: 99998;top:20px;left:10%;height: auto; width: 300px; padding: 5px; color: black;background-color: white; display: none;overflow: auto;}
            #catName1 {padding: 20px;}
            #catName input[type=submit] {display: block; margin: 20px auto; margin-bottom: 0px; padding: 4px;}
            #catName input[type=text]{width: 240px;}
            #form input[type=checkbox] {width: auto;}
            #form td {padding: 2px; padding-right:10px;}
            #form label{font-size: 12px; color: gray;}
            label[for=public], label[for=props] {margin-top:0;}
            table#small #main div.up {background-image: none;padding-left:0;}
            table#small div.up span {display: inline-block; margin-left: 5px; margin-right: 10px; margin-top: 0px;}
            table#small #main div.upper {margin-bottom: 14px;}
            table#small #main div.upper, table#small #main .cat {font-size: 14px;}
        </style>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("docClassic.jsp", request)%></style>
    </head>
    <body id="docClassic_jsp">
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
                                        <li><a href="documentsMod.jsp<% if (owner != null) {%>?owner=<%=owner%><%}%>" class="footmenu">Настройка</a></li>
                                            <% if (editable) {%>
                                        <li> <a href="http://exxo.ru/intranet/howto/documents/files/" class="footmenu howto" target="_blank">?</a></li><%} else {%>
                                        <li> <a href="http://exxo.ru/howto/documents-howto.html#find" class="footmenu howto" target="_blank">?</a></li><%}%>
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
                            <%= DC.href%></td>
                        <td id="column-3" valign="top"> 
                            <table id="small">
                                <tbody>
                                    <tr>
                                        <td id="tdmain"  valign="top">
                                            <div id="main" class="exxo-shadow"></div>
                                        </td>
                                        <td id="tdinfo"  valign="top">
                                            <%if (editable) {%>
                                            <div id="info1">
                                                <table>
                                                    <tr><td><div id="edit1" ></div></td><td class="td2"><div id="edit2" ></div></td></tr>
                                                </table>
                                            </div>
                                            <%}%>
                                            <div id="info" class="exxo-shadow">
                                                <%if (editable) {%>
                                                <table>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-folder-open"></span></td><td class="mem-label"> &#151; Добавить каталог</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-document"></span></td><td class="mem-label"> &#151; Добавить файл</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-copy"></span></td><td class="mem-label"> &#151; Добавить файлы</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-plusthick"></span></td><td class="mem-label"> &#151; Добавить версию</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-closethick"></span></td><td class="mem-label"> &#151; Удалить файл или каталог</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-arrow-4"></span></td><td class="mem-label"> &#151; Переместить файл или каталог</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-gear"></span></td><td class="mem-label"> &#151; Редактировать атрибуты файла или каталога</td></tr></table>
                                                                <%}%>
                                                <div class="mems">Чтобы выделить файл, кликните по нему. Чтобы снять выделение &#151; кликните по нему еще раз или кликните по другому элементу.</div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr> 
                </tbody>
            </table>

        </div>
        <%if (editable) {%>
        <div id="moveMain">
            <div class="close"><span>ЗАКРЫТЬ</span></div>
            <div id="moveMain1"></div>
        </div>
        <div id="form">
            <div class="close"><span>ЗАКРЫТЬ</span></div>
            <form id="FILEFORM" action="addFile" enctype="multipart/form-data" method="POST">
                <label for="file">Выберите файл</label>
                <input type="file" name="file" id="file">
                <label for="name">Имя файла</label>
                <input type="text" name="name" id="name">
                <label for="dopusk">Допуск</label>
                <select name="dopusk" id="dopusk" <% if (general) { %>disabled="disabled"<% }%>>
                    <option value="0" <% if (owner != null && owner.equals("-100")) { %>selected<% }%>>общий</option>    
                    <option value="1" >по проекту</option>
                    <option value="2" >по группе</option>
                    <option value="3" >по подразделению</option>
                    <option value="4" <% if (!general) { %>selected<% }%>>персональный</option>
                </select>
                <label for="discription">Описание</label>
                <textarea name="discription" rows="5" cols="22" ></textarea>
                <label for="tags">Теги</label>
                <textarea name="tags" rows="5" cols="22" ></textarea>
                <br><br>
                <table>
                    <tr><td><label for="public">Опубликовать</label></td><td><input type="checkbox" name="public" value="1"></td></tr>
                    <tr><td><label for="props">Перейти в свойства</label></td><td><input type="checkbox" name="props" value="1" checked></td></tr></table>
                <% if (owner != null) {%><input type="hidden" name="owner" value="<%= owner%>"><%}%>
                <input type='hidden' name='r' value='1'>
                <input type="submit" value="Сохранить">
            </form>
        </div>
        <div id="form1">
            <div class="close"><span>ЗАКРЫТЬ</span></div>
            <form id="FILEFORM1" action="fileVer" enctype="multipart/form-data" method="POST">
                <label for="file">Выберите файл</label>
                <input type="file" name="file" id="file">
                <% if (owner != null) {%>
                <input type="hidden" name="owner" value="<%= owner%>">
                <%}%>
                <input type='hidden' name='r' value='1'>
                <input type="submit" value="Сохранить">
            </form>
        </div>
        <div id="catName">
            <div class="close"><span>ЗАКРЫТЬ</span></div>
            <div id="catName1"></div>
        </div>
        <%}%>
        <script type="text/javascript">
            AUI().ready('aui-io', 'aui-toolbar', 'anim', 'aui-overlay-mask', 'aui-loading-mask', 'aui-loading-mask', function(A) {
            var myHeight = A.DOM.winHeight() - 150;
            if (myHeight < 300) myHeight = 300;
            var fileID;
            var isCopy = false;
            var isStep_6 = false;
            var startID = "1";
            var infoHTML = A.one("#info").html();
            var fixed = false;
            <% if (id != null) {%> startID =<%=id%> <% } %>
            var carCat = startID;
            Reload(startID, null);
            <% if (editable) {%>
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
            new A.Toolbar({activeState: false, children: [
            {label: '', icon: 'folder-open', handler: {fn: function(e){itemAdd(carCat)}}},
            {label: '', icon: 'document', handler: {fn: function(e){fileAdd(carCat)}}},
            {label: '', icon: 'copy', handler: {fn: function(e){filesAdd(carCat)}}},
            {label: '', icon: 'plusthick', handler: {fn: function(e){if (!isCopy && !isStep_6)fileVer(fileID); else if (isCopy)alert('Нельзя добавить версию к копии документа!');
            else alert('Данная операция запрещена с утвержденным документом!'); }}}
            ]}).render(A.one('#edit1'));
            new A.Toolbar({activeState: false, children: [
            {label: '', icon: 'closethick', handler: {fn: function(e){
            if (fixed && !isCopy) {
            if (!isStep_6) dropFile(carCat, fileID, "файл");
            else alert('Данная операция запрещена с утвержденным документом!');
            }
            else if (fixed && isCopy)dropFile(carCat, fileID, "копию");
            else if (A.one('table#files tr') != null) alert("Можно удалить только пустой каталог");
            else if (carCat == 1) alert('Нельзя удалить корневой каталог');
            else dropFile(1, carCat, "каталог");
            }}},
            {label: '', icon: 'arrow-4', handler: {fn: function(e){if (fixed)itemMove(fileID);
            else if (carCat == 1) alert("Нельзя переместить корневой каталог"); else itemMove(carCat)}}},
            {label: '', icon: 'gear', handler: {fn: function(e){if (fixed) location = "fileProp.jsp?id=" + fileID; else changeCatName(carCat); }}}
            ]}).render(A.one('#edit2'));
            <%}%>
            function Reload(superior, id){
            A.one("#main").unplug(A.Plugin.IO);
            var data = {superior:superior<% if (owner != null) {%>, owner:<%= owner%> <%} %>};
            if (id != null) data = {id:id<% if (owner != null) {%>, owner:<%= owner%> <%} %>};
            A.one("#main").plug(A.Plugin.IO, { uri: 'docClassic', method: 'POST',
                    data:data,
                    on:{'end':function(event){
                    var superNode = A.one('.upper');
                    if (superNode != null)
                            carCat = superNode.getAttribute('id');
                    else carCat = 1;
                    A.all(".cat").each(function(){this.on("mouseover", function(){
                    if (!fixed) {
                    this.addClass("file-hover");
                    A.one("#info").html(infoHTML);
                    }
                    });
                    this.on("click", function(){fixed = false; })
                    });
                    A.all(".fil").each(function(){this.on("mouseover", function(){
                    if (!fixed) {
                    this.addClass("file-hover");
                    fileID = this.get("id");
                    if (this.hasClass('copy')) isCopy = true;
                    else isCopy = false;
                    if (this.hasClass('s_6')) isStep_6 = true;
                    else isStep_6 = false;
                    A.one("#info").unplug(A.Plugin.IO);
                    A.one("#info").plug(A.Plugin.IO, { uri: 'fileInfo', data:{id:fileID}, method: 'POST'});
                    }
                    })});
                    A.all(".item").each(function(){this.on("mouseout", function(){if (!fixed) this.removeClass("file-hover"); })});
                    A.all(".fil").each(function(){this.on("click", function(){
                    if (this.hasClass("file-hover") && fixed)fixed = false;
                    else if (fixed && !this.hasClass("file-hover")){
                    this.radioClass("file-hover");
                    fileID = this.get("id");
                    if (this.hasClass('copy')) isCopy = true;
                    else isCopy = false;
                    if (this.hasClass('s_6')) isStep_6 = true;
                    else isStep_6 = false;
                    A.one("#info").unplug(A.Plugin.IO);
                    A.one("#info").plug(A.Plugin.IO, { uri: 'fileInfo', data:{id:fileID}, method: 'POST'});
                    }
                    else {
                    fixed = true;
                    }
                    })});
                    A.all(".cat").each(function(){this.on("click", function(){
                    if (!this.hasClass("upper"))
                            Reload(this.get("id"), null);
                    else Reload(null, this.get("id")); })}); }}})}
            <%if (editable) {%>

            function dropFile(cat, file, what){
            if (confirm("Хотите удалить " + what + "?")) {
            if (what == "каталог") location = "killCat?c=" + cat + "&r=1<%= own%>&p1=" + file;
            else if (what == "файл") location = "dropFile?c=" + cat + "&r=1<%= own%>&p1=" + file;
            else if (what == "копию") location = "dropCopy?c=" + cat + "&r=1<%= own%>&id=" + file;
            }
            }
            var overlayMask = new A.OverlayMask().render();
            var newFile = A.one("#newVer");
            if (newFile != null) newFile.on("click", function(){fileVer(); });
            A.one("#moveMain").setStyle('opacity', '0');
            A.one('#form1').setStyle('opacity', '0');
            A.one('#form').setStyle('opacity', '0');
            A.one('#form1').plug(A.LoadingMask, {background: '#000'});
            A.one('#form').plug(A.LoadingMask, {background: '#000'});
            A.one('#form input[type=submit]').after('click', function(){A.one('#form').loadingmask.toggle(); });
            A.one('#form1 input[type=submit]').after('click', function(){A.one('#form1').loadingmask.toggle(); });
            A.one("#moveMain").plug(A.Plugin.NodeFX, conf);
            A.one('#form1').plug(A.Plugin.NodeFX, conf);
            A.one('#form').plug(A.Plugin.NodeFX, conf);
            A.one('#form1 .close span').on('click', function(event){closer(A.one('#form1')); });
            A.one('#form .close span').on('click', function(event){closer(A.one('#form')); });
            A.one('#moveMain .close span').on('click', function(event){closer(A.one('#moveMain')); });
            A.one('#catName').setStyle('opacity', '0');
            A.one('#catName').plug(A.Plugin.NodeFX, conf);
            A.one('#catName .close span').on('click', function(event){closer(A.one('#catName')); });
            function fileVer(id){
            if (id != null && id != "" && fixed) {
            var str = "<input type='hidden' name='id' value='" + id + "'>";
            A.one('#FILEFORM1').append(A.Node.create(str));
            A.one('#form1').fx.run(); }
            else {alert("Необходимо выбрать файл"); }
            }
            A.one(".aui-overlaymask-content").setStyle("height", "100%");
            function closer(node){node.fx.run(); }
            function itemAdd(id){
            var name = window.prompt("введите имя каталога", "");
            if (name) alterText(id, name<% if (owner != null) {%>, "<%= owner%>"<%}%>, "addCat");
            else alert("Нужно ввести название каталога");
            }
            function alterText(p1, p2<% if (owner != null) {%>, owner<%}%>, servlet){
            A.io.request(servlet, {cache: false, data: {p1: p1,
                    p2: p2 <% if (owner != null) {%>, owner:<%= owner%><%}%>
            }, on:{success:function(){Reload(carCat, null)}},
                    method: 'post'});
            }
            function fileAdd(id){
            var str = "<input type='hidden' name='superior' value='" + id + "'>";
            A.one('#FILEFORM').append(A.Node.create(str));
            A.one('#form').fx.run();
            }
            function itemMove(unit_id){
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
            A.one('#catName .close span').on('click', function(event){closer(A.one('#catName')); });
            A.one("#catName").setStyle('opacity', '0');
            function changeCatName(id) {
            if (id == 1) alert('нельзя изменять корневую папку');
            else {
            var div1 = A.one("#catName1");
            var div = A.one("#catName");
            div1.unplug(A.Plugin.IO);
            div1.plug(A.Plugin.IO, { uri: 'docNameForm', method: 'POST', data:{id:id<% if (owner != null) {%>, owner:<%= owner%><%}%>}});
            div.fx.run();
            }}
            A.one('#form select').on('change', function(){if (this.get('value') == '0') {
            A.one('[name=props]').removeAttribute('checked');
            A.one('[name=public]').setAttribute('checked', '');
            } else {
            A.one('[name=public]').removeAttribute('checked');
            A.one('[name=props]').setAttribute('checked', '');
            }});
            function filesAdd(id) {
            location = "addFile.xhtml?cat=" + id + "<%if (owner != null) {%>&owner=<%= owner%><%}%>";
                }
            <% if (general) {%>
                A.one('[name=props]').removeAttribute('checked');
                A.one('[name=public]').setAttribute('checked', '');
            <%}%>
            <%}%>
                A.one("#main").setStyle("height", myHeight + "px");
                });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>