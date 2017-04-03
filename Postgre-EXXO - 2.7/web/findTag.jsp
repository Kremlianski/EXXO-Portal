<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    //    String element=request.getParameter("el");
    String s = request.getParameter("s");
    if (s == null) {
        s = "bloges";
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Поиск по тегам</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            #listTable td {padding: 5px;padding-left: 20px;padding-right: 20px; text-align: left;}
            #listTable  a {font-size:14px; text-decoration: none; color:black;}
            #listTable  a.bus {color:#0F1F99;}
            #listTable {font-size: 12px;}
            #listTable .tdTime {width:120px;}
            #listTable .time { font-size:11px;}
            .type_0, .type_1, .type_2, .type_3, .type_4 {width: 16px; border-right: 0; background-image: url("small/lock_unlock.png"); background-repeat: no-repeat; background-position: center;}
            .type_3 {background-image: url("small/user_business_boss.png")}
            .type_1 {background-image: url("small/flag_blue.png")}
            .type_2 {background-image: url("small/group.png")}
            .type_4 {background-image: url("small/envelope.png")}
            #results table tr {border-top: 1px solid #DEDEDE;}
            #results table tr:first-of-type {border-top: 0;}
            #results table {width: 100%;background-color: white; border: 0;}
            #results table tr:nth-of-type(odd) {background-color: #F5F2F2;}
            #results a {text-decoration: none;}
            #results tr:hover a {color:#972626;}
            #results a:hover {text-decoration: underline;}
            #results a.blocked {text-decoration: line-through;}
            #results table tr:hover {background-color: #E3E2FD;}
            #results td {padding: 4px 10px; text-align: left;}
            #column-3 #content {border: 0;}
            .tdTime {font-size: 10px; width: 70px; color: #3B3B8F;}
            .aui-icon-dgeneral {background-image: url('small/lock_unlock.png');}
            .aui-icon-dproject {background-image: url('small/flag_blue.png');}
            .aui-icon-dgroup {background-image: url('small/group.png');}
            .aui-icon-dunit {background-image: url('small/user_business_boss.png');}
            .aui-icon-dprivat {background-image: url('small/envelope.png');}
            #results #picTable {width: 100%; }
            #results #picTable td {padding-right: 10px; padding-bottom: 5px; padding-top: 5px; font-size: 12px; text-align: left;}
            #results #picTable tr { border-bottom: 1px solid #dedede;}
            #results #picTable tr:last-of-type {border:0}
            #results #picTable tr:nth-of-type(odd) {background-color: #F5F2F2;}
            #results #picTable td.name {width: 250px; padding-left: 20px;  padding-right: 20px; font-size: 14px;}
            #results #picTable span.created {font-size: 10px;color: #3B3B8F;}
            #results td.ico {background-position: center center; background-repeat: no-repeat; width: 60px; height: 60px;}
            #results #picTable th {border:0; text-align: center; color: #3C3D58; height: 50px;;background-color: #E2E4EE; border-bottom: 1px solid #bfbfbf;}
            #results #picTable tr:hover {background-color: #E3E2FD;}
            #results #picTable tr:hover a {color:#972626;}
            #picTable a:hover {text-decoration: underline;}
            #results span.author {font-size: 10px;}
            div.alert {margin: 20px;font-size: 14px;}
            .item-hover {background: red;}
            .item-hover  a {color: white;}
            #main {position: relative;margin: 25px;width:auto;height: auto;margin-bottom: 10px;background-color: white;padding: 4px;overflow-y: auto;overflow-x: hidden;}
            .hov{ color:black; background-color: white;}
            .inno{margin:5px;margin-left:20px;}
            .cat {background-image: url("folder_closed.png");background-position: left;background-repeat: no-repeat;padding-left: 20px;}
            .fil {background-image: url("img/tag.png");background-position: left;background-repeat: no-repeat;padding-left: 20px;}
            .file-hover {color: red;}
            .filepath{color:gray;}
            table#small {width: 100%;}
            #tdmain {width:30%;}
            #tdinfo {width:70%;}
            #info{margin-left: 0px;padding: 4px;background-color: white; overflow: auto;}
            .tagsDiv {padding:3px; display: inline-block; background-color: white; border: 1px solid #bfbfbf;margin:3px;color:black;-moz-border-radius: 4px;
                      -webkit-border-radius: 4px;border-radius: 4px; padding-left: 20px; background-image: url('img/tag.png');
                      background-position: 2px center;
                      background-repeat: no-repeat;}
            #tdinfo #info {margin:0px;height: 70px; border:0;}
            #panel {margin:0px;height: 30px; width: 100%; background-color: #CFDDCF; }
            #results {margin:0px;margin-right:10px; margin-top: 16px; background-color: whitesmoke; overflow: auto;border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;
                      border-width: 1px;border-style: solid;}
            .tagsDiv:hover {border: 1px solid black;margin:3px;color:white;background-color:gray;cursor:pointer;}
            div.GREEN {color:green;}
            div.RED {color: red; }
            div.BLUE {color: blue; }
            #panel div {display:block; margin-left: 50px;}
            #imger {position: absolute;display: none;left:20px;top:20px;padding: 10px;background-color: black;z-index: 9999999;}
            #closser {color:red;width:100%;text-align:right;}
            table#FILESTABLE td {font-size: 12px;}
            table#FILESTABLE {width: 100%;}
            table#FILESTABLE tr.imges {height: auto;}
            table#FILESTABLE .first {width:20px;}
            table#FILESTABLE .fil {background-image: none;padding-left: 20px;font-size: 11px;color: gray;width: 30%; text-align: left;}
            table#FILESTABLE .fil a{font-size: 12px;margin-right: 20px;}
            table#FILESTABLE .fio {background-image: none; padding-left: 20px;font-size: 11px;
                                   color: black; width: 30%; text-align: left;}
            table#FILESTABLE .fio a{font-size: 12px; margin-right: 10px;}
            #closer {width:90%;}
            #imger {position: absolute;display: none;left:20px;top:20px;padding: 10px;background-color: black;z-index: 9999999;}
            table#FILESTABLE .image {background-image: url("small/photo.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .text {background-image: url("small/page_white_text.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .pdf {background-image: url("small/doc_pdf.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .doc {background-image: url("small/page_white_word.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .noresult {background-image: url("small/application2.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .xls {background-image: url("small/page_excel.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .zip {background-image: url("small/compress.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .rar {background-image: url("small/compress.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .ooo_doc {background-image: url("small/doc_offlice.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .ooo_ss {background-image: url("small/doc_table.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .audio {background-image: url("small/doc_music.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .video {background-image: url("small/films.png");background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE .pp {background-image: url("small/page_white_powerpoint.png"); background-position:  center center;background-repeat: no-repeat;}
            table#FILESTABLE td.created {font-size: 10px;color: #3B3B8F;}
            #topRight {margin-right: 10px; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE; border-width: 1px;border-style: solid;}
            .aui-icon-circulR {background-image: url('small/circulR.png'); background-position: center;}
            .aui-icon-circulG {background-image: url('small/circulG.png'); background-position: center;}
            .aui-icon-circulB {background-image: url('small/circulB.png'); background-position: center;}
            #panel1 button {width: 70px;}
            #panel1 button, #panel2 button {margin-top: 0px !important;margin-right: 0px; height: 22px;}
            #menu-navi {width: 95%; margin-left:10px; height: auto;}
            #menu-navi table {width:100%;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi a {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi a:hover {background-color: white;}
            a#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            a#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            a#backward:hover {background-image: url('small/backward.png'); }
            a#forward:hover {background-image: url('small/forward.png');}
            #menu-navi table tr:hover {background-color: transparent;}
            #menu-navi table:hover {background-color: transparent;}
            #panel2 span.aui-button-icon {margin-top: 0px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("findTag.jsp", request)%>
        </style>
    </head>
    <body id="findTag_jsp">
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
                                        <li>
                                            <a href="tags.jsp" class="footmenu">Настройка</a>
                                        </li>
                                        <li> 
                                            <a href="http://exxo.ru/howto/knowledge-management-howto.html#tags" class="footmenu howto" target="_blank">?</a>
                                        </li>
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
                        <td id="column-3" valign="top">
                            <table id="small">
                                <tbody>
                                    <tr>
                                        <td id="tdmain"  valign="top">
                                            <div id="main" class="exxo-shadow"></div>
                                        </td>
                                        <td id="tdinfo"  valign="top">
                                            <div id="topRight" class="exxo-shadow">
                                                <div id="info"></div>
                                                <table id="panel">
                                                    <tr>
                                                        <td>
                                                            <div id="panel1"></div>
                                                        </td>
                                                        <td>
                                                            <div id="op">
                                                                <select name="tagAim" id="tagAim">
                                                                    <option value="bloges" <%if (s.equals("bloges")) {%>SELECTED <%}%>>Найти в сообщениях</option>
                                                                    <option value="files" <%if (s.equals("files")) {%>SELECTED <%}%>>Найти в файлах</option>
                                                                    <option value="gallaries" <%if (s.equals("gallaries")) {%>SELECTED <%}%>>Найти в картинках</option>
                                                                </select>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div id="panel2"></div>
                                                        </td>
                                                    </tr>
                                                </table>
                                                    
                                            </div>
                                            <div id="results" class="exxo-shadow"></div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr> 
                </tbody>
            </table>
                
        </div>
        <div id="imger"><div id="closser"><span>ЗАКРЫТЬ</span></div><div id="imgerViewer"></div></div>
        <script type="text/javascript">
            AUI().ready('aui-io', 'aui-toolbar', 'aui-overlay-mask', 'anim', function (A) {
                var server = "<%=s%>";
                var status = 1;
                var myHeight = A.DOM.winHeight() - 150;
                if (myHeight < 300)
                    myHeight = 300;
                var of = 0;
                var ROWS =<%=BASE.VER.getMaxRows1(request.getServletContext())%>;
                var DATA = {of: of};
                reInfo(DATA);
                Reload("1", null);
                function Reload(superior, id) {
                    A.one("#main").unplug(A.Plugin.IO);
                    var data = {superior: superior};
                    if (id != null)
                        data = {id: id};

                    A.one("#main").plug(A.Plugin.IO, {uri: 'tagClassic', method: 'POST',
                        data: data,
                        on: {'end': function (event) {
                                A.all(".cat").each(function () {
                                    this.on("mouseover", function () {
                                        this.addClass("file-hover");
                                    })
                                });
                                A.all(".fil").each(function () {
                                    this.on("click", function () {
                                        this.addClass("file-hover");
                                        var fileID = this.get("id");
                                        var DATA = {id: fileID, status: status, of: of};
                                        reInfo(DATA);
                                    })
                                });
                                A.all(".item").each(function () {
                                    this.on("mouseout", function () {
                                        this.removeClass("file-hover");
                                    })
                                });
                                A.all(".cat").each(function () {
                                    this.on("click", function () {
                                        if (!this.hasClass("upper"))
                                            Reload(this.get("id"), null);
                                        else
                                            Reload(null, this.get("id"));
                                    })
                                });
                            }}})
                }
                A.one("#main").setStyle("height", myHeight + "px");
                A.one("#results").setStyle("height", (myHeight - 140) + "px");
                function reInfo(data) {
                    A.one("#info").unplug(A.Plugin.IO);
                    A.one("#info").plug(A.Plugin.IO, {uri: 'tagFound', data: data, method: 'POST',
                        on: {'end': function () {
                                of = 0;
                                var data = {s: server};
                                reResults(data);
                                if (A.one(".tagsDiv") != null)
                                    A.all(".tagsDiv").each(function () {
                                        this.on("click", function () {
                                            if (window.confirm('Удалить тег?')) {
                                                var id = this.getAttribute('id');
                                                of = 0;
                                                A.io.request("dropFoundTag", {method: 'post', data: {id: id}, on: {'success': function () {
                                                            var data = {s: server};
                                                            reResults(data)
                                                        }}}
                                                );
                                                this.remove();
                                            }
                                        })
                                    });
                            }}});
                }
                function reResults(data) {
                    A.one("#results").unplug(A.Plugin.IO);
                    A.one("#results").plug(A.Plugin.IO, {uri: 'tagResults', method: 'POST', data: data, on: {"end": function () {
                                if (A.one("a.imger"))
                                    A.all("a.imger").each(function () {
                                        var href = this.getAttribute('href');
                                        this.setAttribute('href', 'javascript:')
                                        this.on("click", function () {
                                            A.one('#imger').fx.run();
                                            var html = "<img src='" + href + "'>";
                                            A.one("#imgerViewer").html(html).scrollIntoView( );
                                        });
                                    })
                                if (A.one("#forward") != null)
                                    A.one("#forward").on("click", function () {
                                        of = of + ROWS;
                                        reResults({s: server, of: of});
                                    });
                                if (A.one("#backward") != null)
                                    A.one("#backward").on("click", function () {
                                        of = of - ROWS;
                                        if (of < 0)
                                            of = 0;
                                        reResults({s: server, of: of});
                                    });
                            }}});
                }
                new A.Toolbar({activeState: false, children: [
                        {label: 'И', icon: 'circulG', handler: {fn: function (e) {
                                    A.one("#tdinfo #panel").setStyle('backgroundColor', '#CFDDCF');
                                    status = 1;
                                }}},
                        {label: 'ИЛИ', icon: 'circulB', handler: {fn: function (e) {
                                    A.one("#tdinfo #panel").setStyle('backgroundColor', '#DEDEE4');
                                    status = 2;
                                }}},
                        {label: 'НЕ', icon: 'circulR', handler: {fn: function (e) {
                                    A.one("#tdinfo #panel").setStyle('backgroundColor', '#E4D7D7');
                                    status = 3;
                                }}}
                    ]}).render(A.one("#panel1"));
                new A.Toolbar({activeState: false, children: [
                        {label: 'ОЧИСТИТЬ', icon: 'closethick', handler: {fn: function (e) {
                                    A.io.request('dropAllTags',
                                            {method: 'POST', on: {'success': function () {
                                                        var D = {};
                                                        reInfo(D);
                                                    }}});
                                }}},
                        {label: 'РАЗВЕРНУТЬ', icon: 'arrow-4-diag', handler: {fn: function (e) {
                                    var s = server;
                                    location = "openTags.jsp?s=" + s;
                                }}}
                    ]}).render(A.one("#panel2"));
                A.one("#tagAim").on("change", function (e) {
                    server = this.get("value");
                    of = 0;
                    var data = {s: server, of: of};
                    reResults(data);
                })
                var overlayMask = new A.OverlayMask().render();
                A.one('#imger').setStyle('opacity', '0');
                var div = A.one('#imger');
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
                A.one('#closser span').on('click', function (event) {
                    div.fx.run();
                });
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>