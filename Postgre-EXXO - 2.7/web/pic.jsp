<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="BLOG" class="BEANS.picBean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String id = request.getParameter("id");
    boolean inspector = false;
    String role = (String) session.getAttribute("role");
    if (role.indexOf("d") >= 0 || role.indexOf("s") >= 0) {
        inspector = true;
    }

    String owner = (String) session.getAttribute("id");
    boolean r = false;
    if (request.getParameter("respect") != null) {
        r = true;
    }
    if (id == null || id.equals("")) {
        response.sendRedirect("notPermited.html");
    }
    BLOG.setOwner(owner, id, role, (String) session.getAttribute("global_id"), r, request);
    if (!BLOG.getDiff()) {
        inspector = false;
    }
    boolean editor = BLOG.editor;
    String data = ", data: {owner: \"" + BLOG.o + "\"}";
    String index = "index = " + BLOG.index;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Информация об изображении</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {width:90%;height:auto;position: relative; margin: 0px 10px; background-color: white ;border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;}    
            #imgContainer img {margin: 20px auto; display: block; max-width: 100%;}
            #imgContainer {overflow-x: auto; display: block;}
            #innerContent {padding: 10px 10%;text-align: left;}
            #name {font-size: 24px; margin-bottom: 30px; text-align: center;}
            #author a {text-decoration: none; font-size: 12px; font-weight: bold;}
            #author {text-align: center; margin-top: 20px;}
            #created {color: #3B3B8F;}
            #info {width: 100%; vertical-align: top; margin-bottom: 20px;}
            #info td {vertical-align: top; padding: 4px; padding-right: 0px; padding-left:0px;}
            #info a {text-decoration: none;}
            #info a:hover {text-decoration: underline; color:#972626;}
            #info tr {}
            #descr, #tags {padding: 4px; padding-left:0px;}
            #tags {margin-top: 20px;}
            #tags a{padding-right: 8px; text-decoration: none;}
            #tags:hover a {color:#972626;}
            #tags a:hover {text-decoration: underline;}
            td#inform {width: 200px; padding-left: 10px; padding-right: 30px}
            #inform div {margin-bottom: 2px; padding: 4px;padding-left:0px;}
            #res span {padding-right: 20px; color: gray;}
            #res {margin-bottom: 20px; text-align: center;}
            #buts a {text-decoration: none; padding-right: 40px;}
            #buts {text-align: center; margin-bottom: 20px;}
            #buts:hover a {color:#972626;}
            #buts a:hover {text-decoration: underline;}
            .time {color:#3B3B8F; font-size:11px;}
            #commentator {margin: 15px;padding:20px;position: absolute; z-index: 99998;top:0px;;height: auto;width: 700px;color: black;
                          background-color: white;display: none;overflow: auto;}
            .close {color:red;width:auto;text-align:right;}
            .close span {cursor: pointer;}
            #submitComment {padding: 10px; cursor: pointer;}
            #closeCom {cursor: pointer; margin-bottom: 10px;}
            input#submit, #commentator input[type=submit] {margin: 20px auto; display: block; width: 110px; padding: 5px;}
            #commentator {width: 650px;}
            #commentator textarea {width: 630px; height: 200px;border: 1px solid #DEDEDE;overflow: auto;}
            #listTable {width: 100%;}
            #listTable td {vertical-align: middle;}
            #listShrim {width: 100%;}
            td.empIMG img {width: 50px; height: 67px;}
            td.empIMG {width: 60px; padding: 5px; padding-right: 20px;}
            #nocomment {background-color: white; color: gray; font-size: 14px; padding: 20px; text-align: center;}
            .fio {color:black; font-size:12px; padding-right: 10px;  padding-left: 10px; text-align: left;}
            .fio>span {padding-left: 10px;}
            .fio a{font-size: 10px; font-weight: bold;}
            #block {font-size: 10px;}
            .tdName::before {content: ' ';height: 0;position: absolute;width: 0;border: 8px solid transparent;border-right-color: #dedede;right: 100%;top: 10px;}
            .tdName::after {content: ' ';height: 0;position: absolute;width: 0;border: 6px solid transparent;border-right-color: #FDF2F2;right: 100%;top: 12px;}
            #listTable td.tdComment {vertical-align: top; position: relative;}
            .tdName {position: relative; padding: 10px; border: 1px solid #dedede; border-radius: 5px; background-color: #FDF2F2;}
            .empIMG img {border-radius: 5px;}
            #counts {width: 150px;font-size:11px;margin: 10px; margin-top: 25px; color: black;border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;padding: 10px;background-color: white; border-radius: 5px; padding-bottom: 20px;}
            #aPhoto {width: 100%; height: 147px; background-repeat: no-repeat; background-position: center;}
            <% if (editor) {%>
            #lMenu {margin-top: 20px;width: 168px;background-color: #FDF2F2;padding: 0;border-left: 1px solid #DEDEDE;border-right: 1px solid #DEDEDE; border-bottom: 1px solid #BFBFBF; margin-left:10px;}
            #lMenu a{display: block; padding: 6px; text-decoration: none; border-top:1px solid #DEDEDE; font-size: 12px; color:#22225A;}
            #lMenu a:hover {background-color: #E3E2FD;}
            #hic {margin: 25px;padding:20px;position:absolute;z-index:99998;top:20px;left:10%;height:auto;width:auto;color:black;background-color:white; display: none;overflow: auto;}
            label {display:block;margin-top:10px;}
            #hic textarea{width:260px; overflow: hidden;}
            #mhic input[type=submit] {padding: 5px; width: 110px; margin: 10px auto; display: block;}
            label.notshow {display:inline;margin-bottom: 10px;margin-left: 10px;}
            .inno{margin:5px;margin-left:40px;}
            .hov {color: red;}
            #moveMain{position: absolute;background-color: transparent;  width: 100%; height: auto; z-index: 99999999;display:none; left:0px; top: 0px;}
            #imgesInner {position:relative;width:auto;height:auto;margin: 4px; background-color:rgb(250,250,250);border: 1px solid #bfbfbf; border-radius: 5px;}
            #closeM {padding: 5px; padding-right: 10px;}
            <%}%>
            a.img {display: inline-block; width: 180px; height: 180px;background-position: center center;background-repeat: no-repeat; padding-left: 4px; }
            #others {background-color: rgb(50,50,50); padding-left: 10px; margin-top: 10px; margin-bottom: 20px; text-align: center; border-radius: 5px; }
            #album a{font-size: 14px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("pic.jsp", request)%>
        </style>
    </head>
    <body id="pic_jsp">
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
                                        <li> <a href="http://exxo.ru/howto/gallery-howto.html#card" class="footmenu howto" target="_blank">?</a></li>
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
                            <%if (editor) {%>
                            <div id='lMenu' class='exxo-shadow'>
                                <a href='javascript:;'   class='own' id='nameChange'>Сменить название</a><a href='javascript:;'   class='own' id='fileChange'>Изменить данные</a><a href='javascript:;' id='cover' >Сделать обложкой</a><a href='javascript:;' id='moveFile' >Переместить</a><a href='javascript:;' id='tagPhoto'>Добавить теги</a><a href='javascript:;' id='dropPhoto'>Удалить</a></div><%}%>
                            <div id="counts" class="exxo-shadow">
                                <%if (!BLOG.o.equals("-100")) {%>
                                <div id="aPhoto" style="background-image: url(IMG?id=<%=BLOG.o%>)"></div>
                                <% }%>
                                <div id="author"><a href="galClassic.jsp?owner=<%=BLOG.o%>"><%=BLOG.fio%></a></div>
                            </div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow">
                                <a id="imgContainer" href="galLoader?id=<%= id%>"><img src="galLoader?id=<%= id%>" class="exxo-shadow1"></a>
                                <div id="innerContent">
                                    <div id="name"  >«<%=BLOG.name%>»</div>  
                                    <div id="res">
                                        <span>Просмотров: <%=BLOG.count%> </span><span>Комментариев: <%=BLOG.commcount%> </span><span>Понравилось: <%=BLOG.respcount%> </span>
                                    </div>
                                    <div id="buts">
                                        <a href="javascript:;" id="addComment">ОСТАВИТЬ КОММЕНТАРИЙ</a><a href="pic.jsp?respect=1&id=<%= id%>">ПОНРАВИЛОСЬ!</a>
                                    </div>
                                    <table id="info">
                                        <tr>
                                            <td id="inform">
                                                <div id="created"><%=BLOG.time%></div>
                                                <div id ="descr"><%=BLOG.descr%></div>
                                                <%=BLOG.tags%>
                                                <%if (inspector) {%> <div id="block"><a href="changePicStatus.jsp?id=<%= id%>">Заблокировать</a></div><% }%>
                                            </td><td id="comments">
                                                <%=BLOG.getComments()%>
                                            </td></tr></table>
                                    <div id="album"><a href="galClassic.jsp?owner=<%=BLOG.o%>&id=<%=BLOG.superior%>"><%=BLOG.album%></a></div>
                                    <div id="others"><%=BLOG.getList()%></div>
                                </div>    
                            </div>
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="commentator"> 
            <div class="close" id="closeCom">
                <span>ЗАКРЫТЬ</span>
            </div>
            <form action="commentPic" method="post" id="forma">
                <textarea name="comment" id="comment" cols="70" rows="10"></textarea>
                <input type="hidden" name="id" value="<%=id%>">
                <input type="submit" id='submitComment' value="Сохранить">
            </form>
        </div>
                <% if (editor) {%>
        <div id="hic"><div class="close" id="closehic"><span>ЗАКРЫТЬ</span></div><div id="mhic"></div></div>
                    <div id="moveMain">
                        <div id="imgesInner">
                            <div class="exxo-close" id="closeM"><span>ЗАКРЫТЬ</span></div>
                            <div id="Move" class="exxo-images"></div>
                    
                        </div>
                            
                    </div>
        <%}%>
        <div id="container" style="display: none;"><%= BLOG.getHrefs()%></div>
        <script type="text/javascript">
            AUI().ready('aui-overlay-mask', 'aui-io', 'anim', 'imageloader', 'aui-image-viewer', 'aui-loading-mask', function (A) {
                var overlayMask = new A.OverlayMask().render();
                var index = 0;
            <%= index%>
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
                A.one("#imgContainer").setStyle("width", A.one("#content").getComputedStyle("width"));
                A.one('#commentator').setStyle('opacity', '0');
                A.one('#commentator').plug(A.Plugin.NodeFX, conf);
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
                A.one("#addComment").on("click", function () {
                    A.one('#commentator').fx.run();
                    //A.one('#commentator').scrollIntoView ( );
                    A.one('#commentator').setY(A.one('win').get('docScrollY') + 20);
                });
                A.one("#closeCom").on("click", function () {
                    closser(A.one("#commentator"));
                });
                function closser(node) {
                    node.fx.run();
                }
                new A.ImgLoadGroup({foldDistance: 25, className: 'exxo-loading'});
                var iv = new A.ImageViewer({links: 'a.gallary',
                    modal: {opacity: .8, background: '#000072'},
                    captionFromTitle: true,
                    preloadNeighborImages: true,
                    preloadAllImages: false,
                    maxHeight: A.DOM.winHeight() - 60,
                    maxWidth: A.DOM.winWidth() - 100,
                    anim: false
                }).render();
                var closeEl = iv.get('closeEl');
                var arrowLeftEl = iv.get('arrowLeftEl');
                var arrowRightEl = iv.get('arrowRightEl');
                arrowLeftEl.setStyle('left', '15px');
                arrowRightEl.setStyle('right', '15px');
                closeEl.on('click', function () {
                    if (index != iv.get('currentIndex')) {
                        location = iv.getCurrentLink().getAttribute('href').replace('galLoader', 'pic.jsp');
                        A.one('body').loadingmask.toggle();
                    }
                });
                A.one('#imgContainer').on('click', function (e) {
                    iv.set('currentIndex', index);
                    iv.show();
                    e.preventDefault();
                });
                A.one('body').plug(A.LoadingMask, {background: '#000'});
            <% if (editor) {%>
                A.one('#hic').setStyle('opacity', 0);
                A.one('#hic').plug(A.Plugin.NodeFX, conf);
                A.one('#moveMain').setStyle('opacity', 0);
                A.one('#moveMain').plug(A.Plugin.NodeFX, conf);
                A.one("#closehic").on("click", function () {
                    closser(A.one("#hic"));
                });
                A.one('#fileChange').on('click', function () {
                    var fileID = '<%=id%>';
                    A.one('#mhic').unplug(A.Plugin.IO);
                    A.one('#mhic').plug(A.Plugin.IO, {uri: 'picForm', data: {id: fileID, r: '1', owner:<%=BLOG.o%>}, method: 'POST'});
                    A.one('#hic').fx.run();
                    //A.one('#hic').scrollIntoView();
                    A.one('#hic').setY(A.one('win').get('docScrollY') + 20);
                });
                A.one('#nameChange').on('click', function () {
                    var html = '<%=BLOG.name%>';
                    var name = window.prompt("введите новое имя изображения", html);
                    if (name) {
                        if (name.length <= 50) {
                            alterText('<%=id%>', name, "<%=BLOG.o%>", "changeGal");
                        } else
                            alert("Сократите имя до 50 символов!");
                    }
                });
                function alterText(p1, p2, owner, servlet) {
                    A.io.request(servlet, {cache: false, data: {p1: p1, p2: p2, owner: owner},
                        on: {success: function () {
                                location = 'pic.jsp?id=<%=id%>&sys=<%=java.lang.System.currentTimeMillis()%>';
                            }}, method: 'post'});
                }
                A.one('#cover').on('click', function () {
                    location = "changeCover?id=<%=id%>&owner=<%= BLOG.o%>&r=1";
                });
                A.one('#moveFile').on('click', function () {
                    var div = A.one("#Move");
                    var Div = A.one("#moveMain");
                    div.purge(true);
                    div.unplug(A.Plugin.IO);
                    A.one("#imgesInner").setStyle("minHeight", A.DOM.winHeight() - 10 + "px");
                    var unit_id = '<%=id%>';
                    div.plug(A.Plugin.IO, {uri: 'catalogues', method: 'POST', on: {end: function () {
                                A.all('.cat').each(function () {
                                    this.on('click', function () {
                                        var superior = this.get("id");
                                        alterText(unit_id, superior,<%=BLOG.o%>, "moveGal");
                                        div.purge(true);
                                        div.unplug(A.Plugin.IO);
                                        Div.fx.run();
                                    });
                                });
                            }}<%= data%>
                    });
                    //Div.scrollIntoView();
                    Div.fx.run();
                    Div.setY(A.one('win').get('docScrollY'));
                });
                A.one('#tagPhoto').on('click', function () {
                    location = "addTags.jsp?el=<%=id%>&s=gallaries&o=<%=BLOG.o%>&r=1";
                });
                A.one('#dropPhoto').on('click', function () {
                    if (window.confirm("Удалить изображение?"))
                        location = "dropPhoto?id=<%=id%>&o=<%=BLOG.o%>&s=<%=BLOG.superior%>"
                });
                A.one("#closeM span").on("click", function (event) {
                    A.one("#moveMain").fx.run()
                });
            <%}%>
            });
        </script>
        <script type="text/javascript" src="resources/scripts/top-menu-fixed.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>