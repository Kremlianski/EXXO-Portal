<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="GC" class="BEANS.galClassic" scope="request"/><%
    String UA = request.getHeader("User-Agent");
    String owner = request.getParameter("owner");
    String ID = request.getParameter("id");
    String slogan = SLOGAN.getSlogan(request);
    GC.getParams(request);
    String own = "";
    if (owner != null) {
        own = "?owner=" + owner;
    }
    boolean editor = GC.editor;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Фотографии</title>
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
            #main {position: relative;margin: 25px; margin-left: 10px; width:auto;height: auto;margin-bottom: 10px;background-color: white;padding: 4px;overflow-x: hidden; text-align: center;}
            .hov{color:black;background-color: white; width: 95%;}
            .inno{margin:5px;margin-left:20px;}
            table#small div#main div.cat {background-image: none; height: auto; width: 180px;
                                          display: inline-block; vertical-align: top; margin: 5px; padding: 10px;}
            table#small div#main div.cat1 {background-image: url("small/photo_album.png");background-position: left;background-repeat: no-repeat;padding-left: 30px; text-align: left;}
            table#small {width: 100%;}
            #tdmain {width:100%;}
            #menu-navi {width: 95%; margin-left:10px; height: auto;}
            #menu-navi table {width:100%;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi span {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi span:hover {background-color: white; cursor: pointer;}
            span#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            span#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            span#backward:hover {background-image: url('small/backward.png'); }
            span#forward:hover {background-image: url('small/forward.png');}
            #counts {width: 150px;font-size:11px;margin: 10px; margin-top: 25px; color: black;border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;padding: 10px;
                     background-color: white}
            #counts td.res{color:#3B3B8F; text-align: right;}
            #counts table {width: 100%;margin-bottom: 10px;}
            #counts table td {padding-bottom: 2px;}
            #counts a{text-decoration: none;}
            #counts:hover a {color: #972626;}
            #counts a:hover {text-decoration: underline;}
            #author {margin-bottom: 4px;font-size: 12px;}
            #menuList a {background-image: url('small/application_view_icons.png');background-position: center left;background-repeat: no-repeat;padding: 2px; padding-left: 22px;}
            #lMenu {margin-top: 20px;width: 168px;background-color: #FDF2F2;padding: 0;border-left: 1px solid #DEDEDE;border-right: 1px solid #DEDEDE; border-bottom: 1px solid #BFBFBF; margin-left:10px;}
            #lMenu a{display: block; padding: 6px; text-decoration: none; border-top:1px solid #DEDEDE; font-size: 12px; color:#22225A;}
            #lMenu a:hover {background-color: #E3E2FD;}
            .loading {background:none !important;}
            <%if (editor) {%>
            #lMenu a[disabled=true] {color: #bfbfbf;}
            #form {font-size: 12px; border-radius: 5px;}
            #form input[type=text] {width:260px;}
            #form select {width:260px;}
            #form textarea, #hic textarea{width:260px; overflow: auto;}
            #FILEFORM input[type=submit], #mhic input[type=submit] {padding: 5px; width: 110px; margin: 10px auto; display: block;}
            #form {margin: 25px;padding:20px;position: absolute;z-index:99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white;display: none;overflow: auto;}
            .close {color:red;width:100%;text-align:right;}
            .close span {cursor: pointer;}
            label {display:block;margin-top:10px;}
            label.notshow {display:inline;margin-bottom: 10px;margin-left: 10px;}
            <%}%>
        </style>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("galClassic.jsp", request)%></style>
    </head>
    <body id="galClassic_jsp">
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
                                        <li><a href="gallariesMod.jsp<% if (owner != null) {%>?owner=<%=owner%><%}%>" class="footmenu">Настройка</a></li>
                                        <li> <a href="http://exxo.ru/howto/gallery-howto.html#find" class="footmenu howto" target="_blank">?</a></li>
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
                            <%if (editor) {%><div id='lMenu' class='exxo-shadow'><a href='javascript:;'   class='own' id='newAlbom'>Добавить альбом</a><a href='javascript:;' id='dropAlbom' class='fotoedit' >Удалить альбом</a><a href='javascript:;' id='albomName' class='fotoedit' >Название альбома</a>
                                <a href='javascript:;' id='newPhoto' class='fotoedit'>Добавить фото</a><a href='javascript:;' id='manyPhotos' class='fotoedit'>Несколько фотографий</a></div><%}%>
                            <div id="counts" class="exxo-shadow">
                                <%if (owner != null) {%> <div id="author"><%=GC.fio%></div><% }%>
                                <table>
                                    <tr>
                                        <td>Альбомов:</td>
                                        <td class="res"><%=GC.c%></td>
                                    </tr>
                                    <tr>
                                        <td>Фотографий</td>
                                        <td class="res"><%= GC.v%></td>
                                    </tr>
                                    <tr>
                                        <td>Просмотров:</td>
                                        <td class="res"><%= GC.count%></td>
                                    </tr>
                                    <tr>
                                        <td>Понравилось:</td>
                                        <td class="res"><%= GC.respcount%></td>
                                    </tr>
                                    <tr>
                                        <td>Комментариев</td>
                                        <td class="res"><%= GC.commcount%></td>
                                    </tr>
                                </table>
                            </div>
                            <div id="menuList"><a href="photos.jsp<%=own%>">Фотографии</a></div>
                        </td>
                        <td id="column-3" valign="top"> 
                            <table id="small">
                                <tbody>
                                    <tr>
                                        <td id="tdmain"  valign="top">
                                            <div id="main" class="exxo-shadow exxo-images"></div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>

        </div>
        <%if (editor) {%>
        <div id="form"><div class="close" id="close"><span>ЗАКРЫТЬ</span></div>
            <form id="FILEFORM" action="addPic" enctype="multipart/form-data" method="POST">
                <label for="file">Выберите изображение</label><input type="file" name="file" id="file">
                <label for="name">Название фотографии</label><input type="text" name="name" id="name" maxlength="50">
                <label for="discription">Описание</label><textarea name="discription" rows="5" cols="22" ></textarea>
                <label for="tags">Теги</label><textarea name="tags" rows="5" cols="22" ></textarea><br><br>
                <input type="checkbox" value="1" name="notshow"><label class="notshow">Не афишировать</label>
                <br><br>
                <% if (owner != null) {%>
                <input type="hidden" name="owner" value="<%= owner%>">
                <%}%>
                <input type="hidden" name="Re" value="1">
                <input type="submit" value="Сохранить"></form>
        </div>
        <%}%>
        <script type="text/javascript">
            AUI().ready('aui-io', 'aui-image-viewer', 'aui-overlay-mask', 'anim', 'imageloader', 'aui-loading-mask', function(A) {
            var myHeight = A.DOM.winHeight() - 150;
            if (myHeight < 300) myHeight = 300;
            A.one("#main").setStyle("minHeight", myHeight + "px");
            var of = 0;
            var cargal = '1';
            var ROWS =<%=BASE.VER.getMaxBloks(request.getServletContext())%>;
            <%if (ID != null) {%>
            cargal = '<%=ID%>';
            Reload('<%=ID%>', null);
            <%} else { %>
            Reload("1", null);
            <%}%>
            function Reload(superior, id){
            cargal = superior;
            A.one("#main").unplug(A.Plugin.IO);
            var data = {superior:superior<% if (owner != null) {%>, owner:<%= owner%> <%} %>, of:of};
            if (id != null) data = {id:id<% if (owner != null) {%>, owner:<%= owner%> <%} %>, of:of};
            A.one("#main").plug(A.Plugin.IO, { uri: 'galClassic', method: 'POST',
                    data:data,
                    on:{'end':function(event){
                    A.one('#header').scrollIntoView();
                    A.all(".cat").each(function(){this.on("mouseover", function(){
                    this.addClass("file-hover"); })});
                    A.all(".fil").each(function(){this.on("mouseover", function(){
                    this.addClass("file-hover"); })});
                    A.all(".item").each(function(){this.on("mouseout", function(){
                    this.removeClass("file-hover"); })});
                    new A.ImgLoadGroup({foldDistance: 25, className: 'exxo-loading'});
                    A.all(".cat").each(function(){this.on("click", function(){
                    of = 0;
                    if (!this.hasClass("up")) Reload(this.get("id"), null);
                    else Reload('1', null); })});
                    A.all(".cat1").each(function(){this.on("click", function(){
                    of = 0;
                    if (!this.hasClass("up")) Reload(this.get("id"), null);
                    else Reload('1', null); })});
                    var imageGallery = new A.ImageViewer({links: '#main a',
                            modal:{ opacity: .8, background: '#000072' },
                            captionFromTitle: true,
                            preloadNeighborImages: true,
                            preloadAllImages: false,
                            maxHeight: A.DOM.winHeight() - 60,
                            maxWidth: A.DOM.winWidth() - 100,
                            anim: false}).render();
                    var arrowLeftEl = imageGallery.get('arrowLeftEl');
                    var arrowRightEl = imageGallery.get('arrowRightEl');
                    arrowLeftEl.setStyle('left', '15px');
                    arrowRightEl.setStyle('right', '15px');
                    A.one(".aui-overlaymask-content").setStyle("height", "100%");
                    if (A.one("#forward") != null) A.one("#forward").on("click", function(){
                    of = of + ROWS;
                    Reload(superior, id);
                    });
                    if (A.one("#backward") != null) A.one("#backward").on("click", function(){
                    of = of - ROWS;
                    if (of < 0) of = 0;
                    Reload(superior, id);
                    });
            <%if (editor) {%>
                    if (A.one('.up') != null){A.all('.fotoedit').each(function(){this.removeAttribute('disabled')});
                    } else {A.all('.fotoedit').each(function(){this.setAttribute('disabled', 'true')}); }
            <%}%>
                    }}})}
            <%if (editor) {%>
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
            A.one('#close span').on('click', function(event){closer(A.one('#form')); });
            var overlayMask = new A.OverlayMask().render();
            A.one("#newAlbom").on("click", function(){
            var name = window.prompt("введите имя альбома", "");
            if (name) alterText('1', name<% if (owner != null) {%>, "<%= owner%>"<%}%>, "addGal");
            });
            function alterText(p1, p2<% if (owner != null) {%>, owner<%}%>, servlet){
            A.io.request(servlet, {cache: false, data: {p1: p1, p2: p2 <% if (owner != null) {%>, owner:<%= owner%><%}%>
            }, on:{success:function(){Reload('1', null)}}, method: 'post'});
            }
            A.one("#newPhoto").on("click", function(){
            var id = cargal;
            if (cargal != null && cargal != "" && cargal != "1"){
            var str = "<input type='hidden' name='superior' value='" + id + "'>";
            A.one('#FILEFORM').append(A.Node.create(str));
            A.one('#form').fx.run();
            A.one('#form').setY(A.one('win').get('docScrollY') + 20);
            } else alert("Чтобы добавить фотографию, выберите альбом")
            });
            function closer(node){node.fx.run(); }
            A.one("#dropAlbom").on("click", function(){
            var div = A.one(".up");
            if (div != null){
            if (A.one('.fil') == null) {if (window.confirm("Удалить?")) alterText(cargal, "0"<% if (owner != null) {%>, "<%= owner%>"<%}%>, "killGal"); }
            else alert("Можно удалить только пустой альбом. Необходимо удалить содержимое альбома!")}});
            A.one('#albomName').on('click', function(){
            if (A.one('.up') != null){
            var name = window.prompt("введите новое имя изображения или альбома", A.one('.up').get('id'));
            if (name) {if (name.length <= 50) {alterText(cargal, name<% if (owner != null) {%>, "<%= owner%>"<%}%>, "changeGal"); }
            else alert("Сократите имя до 50 символов!"); }
            } else alert ('Выберите альбом'); });
            A.one('#manyPhotos').on('click', function(){
            if (A.one('.up') != null){
            location = "addPic.xhtml?gal=" + cargal + "<% if (owner != null) {%>&owner=<%= owner%><%}%>";
                } else alert ('Выберите альбом');
                });
                A.one('#form').plug(A.LoadingMask, {background: '#000'});
                A.one('#form input[type=submit]').after('click', function(){A.one('#form').loadingmask.toggle()});
                A.all(".aui-overlaymask-content").setStyle("height", "100%");
            <%}%>
                });
        </script>
        <script type="text/javascript" src="resources/scripts/top-menu-fixed.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>