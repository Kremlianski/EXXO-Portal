<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/><jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String id = request.getParameter("id");
    String carCat = (String) session.getAttribute("blog_car_cat");
    if (carCat == null) {
        carCat = "0";
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Мои сообщения</title>
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
            #tdmain {width:50%;}
            #tdinfo{width:50%;}
            #info, #info1 {margin:0px 25px;width:auto;height: auto; margin-left: 0px;padding: 4px;background-color: #FDF2F2;}
            table#files {width:100%}
            table#files td {text-align: left;}
            td.icon {width:20px; height:24px;}
            td.fname, td.name {padding-left:10px; max-width: 200px;}
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
            #edit1 button, #edit2 button, #edit3 button {height: 24px; padding-top: 5px; min-width: 26px;}
            .lable {font-size:12px; padding:5px;}
            div#edit1, div#edit2 {margin-bottom:0px;}
            #info1 {border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid; background-color: #EEEEF1;}
            #info1 td.td2 , #info1 td.td3 {padding-left: 6px;}
            .mem .aui-icon {display: inline-block;}
            .mem-label {height: 24px;vertical-align: middle;padding-left: 10px;}
            label {display:block;margin-top:10px;}
            .close {color:red;width:100%;text-align:right;}
            .close span {cursor: pointer;}
            .mems {margin-top: 10px;}
            div#info {text-align: left;}
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
            #catName {position: absolute;z-index: 99998;top:20px;left:10%;height: auto; width: 300px; padding: 5px; color: black;background-color: white; display: none;overflow: auto;}
            #catName1 {padding: 20px;}
            #catName input[type=submit] {display: block; margin: 20px auto; margin-bottom: 0px; padding: 4px;}
            #catName input[type=text]{width: 240px;}
            #info {overflow:auto;}
            #cont {font-size: 12px; margin-top: 10px;}
            #infobutt a, #infobutt span{margin-left: 10px; margin-right: 5px; font-weight: bold;}
            #infobutt span:first-of-type {margin-left: 0px;}
            .star span{background-repeat: no-repeat;background: url('/FILES/alloy/build/aui-skin-classic/images/icon_sprite.png') no-repeat 0 0;
                       display: block;height: 16px;overflow: hidden;width: 16px;background-position: -224px -112px;}
            #menu-navi {width: 95%; margin-left:10px; height: auto;}
            #menu-navi table {width:100%;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi a {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi a:hover {background-color: white;}
            a#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            a#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            a#backward:hover {background-image: url('small/backward.png'); }
            a#forward:hover {background-image: url('small/forward.png');}
            #content td.dop {width: 16px; padding: 4px}
            #too {margin-bottom: 6px;}
            #too span.emps{color: gray;}
            #tags {margin: 20px 0px;}
            a#addgroup{padding-left: 22px;background-image: url('small/application_view_list.png'); background-position: center left; background-repeat: no-repeat; text-decoration: none;}
            a#addgroup:hover {color: #972626;text-decoration: underline;}
            #info table.exxo-table tr:nth-of-type(odd) td {background-color: white;}
            #info table.exxo-table tr:hover td {background-color: #E3E2FD; cursor: pointer;}
            #info table.exxo-table {margin-top: 10px;}
            #info table.exxo-table tr:first-of-type td {border-top: 1px solid #bfbfbf;}
            #input {color: gray; margin-top: 20px;}
            #cont img {max-width: 100%;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("correspondence.jsp", request)%>
        </style>
    </head>
    <body id="correspondence_jsp">
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
                                        <li> <a href="http://exxo.ru/howto/mail-howto.html#message" class="footmenu howto" target="_blank">?</a></li>
                                    </ul></div>
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
                                <jsp:include page="menu-blog.xhtml" flush="true" />
                            </div>
                        </td>
                        <td id="column-3" valign="top"> 
                            <table id="small">
                                <tbody>
                                    <tr>
                                        <td id="tdmain"  valign="top">
                                            <div id="main" class="exxo-shadow"></div>
                                        </td>
                                        <td id="tdinfo"  valign="top">
                                            <div id="info1">
                                                <table>
                                                    <tr>
                                                        <td>
                                                            <div id="edit1" ></div>
                                                        </td>
                                                        <td class="td2">
                                                            <div id="edit2" ></div>
                                                        </td>
                                                        <td class="td3">
                                                            <div id="edit3" ></div>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div id="info" class="exxo-shadow">

                                                <table>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-folder-open"></span></td><td class="mem-label"> &#151; Добавить папку</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-pencil"></span></td><td class="mem-label"> &#151; Новое сообщение</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-star"></span></td><td class="mem-label"> &#151; Пометить как «Важное» / снять пометку  «Важное»</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-closethick"></span></td><td class="mem-label"> &#151; «Спрятать» сообщение или удалить папку</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-arrow-4"></span></td><td class="mem-label"> &#151; Переместить сообщение или папку</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-gear"></span></td><td class="mem-label"> &#151; Редактировать атрибуты сообщения или переименовать папку</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-mail-open"></span></td><td class="mem-label"> &#151; Открыть сообщение</td></tr>
                                                    <tr class="mem"><td><span class="aui-icon aui-icon-tag"></span></td><td class="mem-label"> &#151; Найти похожие сообщения в базе знаний</td></tr>
                                                </table>
                                                <div class="mems">Чтобы выделить сообщение, кликните по нему. Чтобы снять выделение &#151; кликните по нему еще раз или кликните по другому элементу.</div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr> 
                </tbody>
            </table>

        </div>

        <div id="moveMain">
            <div class="close"><span>ЗАКРЫТЬ</span></div>
            <div id="moveMain1"></div>
        </div>
        <div id="catName">
            <div class="close"><span>ЗАКРЫТЬ</span></div>
            <div id="catName1"></div>
        </div>
        <script type="text/javascript">
            AUI().ready('aui-io', 'aui-toolbar', 'anim', 'aui-overlay-mask', function (A) {
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

                var myHeight = A.DOM.winHeight() - 150;
                if (myHeight < 300)
                    myHeight = 300;
                var fileID;
                var startID = "<%= carCat%>";
                var infoHTML = A.one("#info").html();
                var fixed = false;
                var of = 0;
                var ROWS =<%=BASE.VER.getMaxRows1(request.getServletContext())%>;
            <% if (id != null) {%> startID =<%=id%> <% }%>
                var carCat = startID;
                Reload(startID, null);

                new A.Toolbar({activeState: false, children: [
                        {label: '', icon: 'folder-open', handler: {fn: function (e) {
                                    itemAdd(carCat);
                                }}},
                        {label: '', icon: 'pencil', handler: {fn: function (e) {
                                    location = 'blogInsert.jsp?r=1';
                                }}},
                        {label: '', icon: 'star', handler: {fn: function (e) {
                                    markBlog(fileID);
                                }}}
                    ]}).render(A.one('#edit1'));

                new A.Toolbar({activeState: false, children: [
                        {label: '', icon: 'closethick', handler: {fn: function (e) {
                                    if (fixed)
                                        dropFile(carCat, fileID, "скрыть сообщение");
                                    else if (A.one('table#files tr') != null)
                                        alert("Можно удалить только пустую папку");
                                    else if (carCat == 0)
                                        alert('Нельзя удалить корневую папку');
                                    else
                                        dropFile(0, carCat, "удалить папку");
                                }}},
                        {label: '', icon: 'arrow-4', handler: {fn: function (e) {
                                    if (fixed)
                                        itemMove(fileID);
                                    else if (carCat == 0)
                                        alert("Нельзя переместить корневую папку");
                                    else
                                        itemMove(carCat)
                                }}},
                        {label: '', icon: 'gear', handler: {fn: function (e) {
                                    if (fixed)
                                        location = "addTags.jsp?el=" + fileID + "&s=bloges";
                                    else
                                        changeCatName(carCat);
                                }}}
                    ]}).render(A.one('#edit2'));

                new A.Toolbar({activeState: false, children: [
                        {label: '', icon: 'mail-open', handler: {fn: function (e) {
                                    if (fixed)
                                        location = "blog.jsp?id=" + fileID;
                                    else
                                        alert("Выберите сообщение");
                                }}},
                        {label: '', icon: 'tag', handler: {fn: function (e) {
                                    if (fixed)
                                        location = "docTags?el=" + fileID + "&&s=bloges";
                                    else
                                        alert("Выберите сообщение");
                                }}}
                    ]}).render(A.one('#edit3'));
                function Reload(superior, id) {
                    A.one("#main").unplug(A.Plugin.IO);
                    var data = {superior: superior, of: of};
                    if (id != null)
                        data = {id: id, of: of};
                    A.one("#main").plug(A.Plugin.IO, {uri: 'blogClassic', method: 'POST',
                        data: data,
                        on: {'end': function (event) {
                                if (A.one("#forward") != null)
                                    A.one("#forward").on("click", function () {
                                        of = of + ROWS;
                                        fixed = false;
                                        Reload(carCat, null);
                                    });
                                if (A.one("#backward") != null)
                                    A.one("#backward").on("click", function () {
                                        of = of - ROWS;
                                        if (of < 0)
                                            of = 0;
                                        fixed = false;
                                        Reload(carCat, null);
                                    });
                                var superNode = A.one('.upper');
                                if (superNode != null)
                                    carCat = superNode.getAttribute('id');
                                else
                                    carCat = 0;
                                A.all(".cat").each(function () {
                                    this.on("mouseover", function () {
                                        if (!fixed) {
                                            this.addClass("file-hover");
                                            A.one("#info").html(infoHTML);
                                        }
                                    });
                                    this.on("click", function () {
                                        fixed = false;
                                    })
                                });
                                A.all(".fil").each(function () {
                                    this.on("mouseover", function () {
                                        if (!fixed) {
                                            this.addClass("file-hover");
                                            fileID = this.get("id");
                                            A.one("#info").unplug(A.Plugin.IO);
                                            A.one("#info").plug(A.Plugin.IO, {uri: 'blogInfo', data: {id: fileID}, method: 'POST'});
                                        }
                                    })
                                });
                                A.all(".item").each(function () {
                                    this.on("mouseout", function () {
                                        if (!fixed)
                                            this.removeClass("file-hover");
                                    })
                                });
                                A.all(".fil").each(function () {
                                    this.on("click", function () {
                                        if (this.hasClass("file-hover") && fixed)
                                            fixed = false;
                                        else if (fixed && !this.hasClass("file-hover")) {
                                            this.radioClass("file-hover");
                                            fileID = this.get("id");
                                            A.one("#info").unplug(A.Plugin.IO);
                                            A.one("#info").plug(A.Plugin.IO, {uri: 'blogInfo', data: {id: fileID}, method: 'POST'});
                                        } else {
                                            fixed = true;
                                        }
                                    })
                                });
                                A.all(".cat").each(function () {
                                    this.on("click", function () {
                                        of = 0;
                                        if (!this.hasClass("upper"))
                                            Reload(this.get("id"), null);
                                        else
                                            Reload(null, this.get("id"));
                                    })
                                });
                            }}})
                }
                function dropFile(cat, file, what) {
                    if (confirm("Хотите " + what + "?")) {
                        if (what == "удалить папку")
                            location = "killDocCat?c=" + cat + "&p1=" + file;
                        else if (what == "скрыть сообщение") {
                            var str = "tr[id=" + file + "] td.icon";
                            var div = A.one(str);
                            if (div.hasClass("star"))
                                alert("Нельзя скрыть сообщение, помеченное как «Важное»!");
                            else
                                A.io.request('hideBlog', {cache: false, data: {id: file, star: '1'}, method: 'post', on: {success: function () {
                                            Reload(cat, null);
                                        }}});
                        }
                    }
                }
                var overlayMask = new A.OverlayMask().render();
                A.one("#moveMain").setStyle('opacity', '0');
                A.one('#catName').setStyle('opacity', '0');
                A.one("#moveMain").plug(A.Plugin.NodeFX, conf);
                A.one('#catName').plug(A.Plugin.NodeFX, conf);
                A.one('#catName .close span').on('click', function (event) {
                    closer(A.one('#catName'));
                });
                A.one('#moveMain .close span').on('click', function (event) {
                    closer(A.one('#moveMain'));
                });
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
                function closer(node) {
                    node.fx.run();
                }
                function itemAdd(id) {
                    var name = window.prompt("введите имя папки", "");
                    if (name)
                        alterText(id, name, "addDocCat");
                    else
                        alert("Нужно ввести название папки");
                }
                function alterText(p1, p2, servlet) {
                    A.io.request(servlet, {cache: false, data: {p1: p1,
                            p2: p2}, on: {success: function () {
                                Reload(carCat, null)
                            }},
                        method: 'post'});
                }
                function itemMove(unit_id) {
                    var div = A.one("#moveMain");
                    var div1 = A.one("#moveMain1");
                    div1.purge(true);
                    div1.unplug(A.Plugin.IO);
                    div.setStyle("width", A.DOM.winWidth() - 198 + "px");
                    div.setStyle("height", A.DOM.winHeight() - 98 + "px");
                    div1.plug(A.Plugin.IO, {uri: 'blogstructure', method: 'POST', on: {end: function () {
                                var nodlist = A.all("#moveMain .cat");
                                if (!fixed) {
                                    var n = A.Node.one("#moveMain [id=" + unit_id + "]").setStyle("display", "none");
                                    n.next().setStyle("display", "none");
                                }
                                nodlist.on("mouseenter", function (event) {
                                    event.target.addClass("hov")
                                });
                                nodlist.on("mouseleave", function (event) {
                                    event.target.removeClass("hov")
                                });
                                nodlist.on("click", function (event) {
                                    var superior = event.target.get("id");
                                    if (!fixed)
                                        alterText(unit_id, superior, "moveDocCat");
                                    else
                                        alterText(unit_id, superior, "moveBlogCat");
                                    div1.purge(true);
                                    div1.unplug(A.Plugin.IO);
                                    div.fx.run();
                                });
                            }}
                    });
                    div.fx.run();
                }
                function changeCatName(id) {
                    if (id == 0)
                        alert('нельзя изменять корневую папку');
                    else {
                        var div1 = A.one("#catName1");
                        var div = A.one("#catName");
                        div1.unplug(A.Plugin.IO);
                        div1.plug(A.Plugin.IO, {uri: 'blogNameForm', method: 'POST', data: {id: id}});
                        div.fx.run();
                    }
                }
                function markBlog(id) {
                    if (fixed) {
                        var star = 1;
                        var str = "tr[id=" + id + "] td.icon";
                        var div = A.one(str);
                        if (div.hasClass("star")) {
                            star = 0;
                        } else {
                            star = 1;
                        }
                        A.io.request('blogStar', {cache: false, data: {star: star, id: id}, method: 'post', on: {success: function () {
                                    if (star == 1)
                                        div.addClass("star");
                                    else
                                        div.removeClass("star");
                                }}});
                    } else
                        alert("Выберите сообщение!");
                }
                var myWidth = A.DOM.winWidth() - 200;
                A.one("#main").setStyle("height", myHeight + "px");
                A.one("#tdmain").setStyle("width", (myWidth * 0.5) + "px");
                A.one("#tdinfo").setStyle("width", (myWidth * 0.5) + "px");
                A.one("#column-3").setStyle("width", (myWidth) + "px");
                A.one("#info").setStyle("width", (myWidth * 0.5 - 58) + "px");
                A.one("#info").setStyle("height", (myHeight - 66) + "px");
                A.one("#info1").setStyle("width", (myWidth * 0.5 - 36) + "px");
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>