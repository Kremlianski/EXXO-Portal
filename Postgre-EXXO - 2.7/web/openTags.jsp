<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="ND" class="BEANS.TagResults" scope="page"/><%
    String s = request.getParameter("s");
    String slogan = SLOGAN.getSlogan(request);
    String list = ND.getList(request);
    String navi = ND.navi;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Поиск по тегам</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');    
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
            #content {width: 90%; margin-left: 10px; border: 1px solid #dedede; border-bottom-color: #bfbfbf;}
            #content table tr {border-top: 1px solid #DEDEDE;}
            #content table tr:first-of-type {border-top: 0;}
            #content table {width: 100%;background-color: white; border: 0;}
            #content table tr:nth-of-type(odd) {background-color: #F5F2F2;}
            #content a {text-decoration: none;}
            #content tr:hover a {color:#972626;}
            #content a:hover {text-decoration: underline;}
            #content a.blocked {text-decoration: line-through;}
            #content table tr:hover {background-color: #E3E2FD;}
            #content td {padding: 4px 10px; text-align: left;}
            .tdTime {font-size: 10px; width: 70px; color: #3B3B8F;}
            .aui-icon-dgeneral {background-image: url('small/lock_unlock.png');}
            .aui-icon-dproject {background-image: url('small/flag_blue.png');}
            .aui-icon-dgroup {background-image: url('small/group.png');}
            .aui-icon-dunit {background-image: url('small/user_business_boss.png');}
            .aui-icon-dprivat {background-image: url('small/envelope.png');}
            #content #picTable {width: 100%; }
            #content #picTable td {padding-right: 10px; padding-bottom: 5px; padding-top: 5px; font-size: 12px; text-align: left;}
            #content #picTable tr { border-bottom: 1px solid #dedede;}
            #content #picTable tr:last-of-type {border:0}
            #content #picTable tr:nth-of-type(odd) {background-color: #F5F2F2;}
            #content #picTable td.name {width: 250px; padding-left: 20px;  padding-right: 20px; font-size: 14px;}
            #content #picTable span.created {font-size: 10px;color: #3B3B8F;}
            #content td.ico {background-position: center center; background-repeat: no-repeat; width: 60px; height: 60px;}
            #content #picTable th {border:0; text-align: center; color: #3C3D58; height: 50px;;background-color: #E2E4EE; border-bottom: 1px solid #bfbfbf;}
            #content #picTable tr:hover {background-color: #E3E2FD;}
            #content #picTable tr:hover a {color:#972626;}
            #picTable a:hover {text-decoration: underline;}
            #content span.author {font-size: 10px;}
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
            #imger {position: absolute;display: none;left:20px;top:20px;padding: 10px;background-color: black;z-index: 9999999; border-radius: 5px;}
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
            #imger {position: absolute; display: none;left:20px;top:20px;padding: 10px;background-color: black;z-index: 9999999;}
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
            #menu-navi {width: 90%; height: auto;}
            #menu-navi table {width:100%; background-color: transparent;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi a {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi a:hover {background-color: white;}
            a#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            a#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            a#backward:hover {background-image: url('small/backward.png'); }
            a#forward:hover {background-image: url('small/forward.png');}
            #menu-navi table tr:hover {background-color: transparent;}
            #menu-navi table:hover {background-color: transparent;}
            #menuList a {background-image: url('small/application_view_icons.png');background-position: center left;background-repeat: no-repeat;padding: 2px; padding-left: 22px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("openTags.jsp", request)%>
        </style>
    </head>
    <body class="yui3-skin-sam" id="openTags_jsp">
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
                                        <li><a href="findTag.jsp?s=<%=s%>" class="footmenu">Назад</a></li>
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
                            <% if (s.equals("gallaries")) { %> 
                            <div id="menuList"><a href="openTagsGal.jsp">В виде галереи</a></div>
                            <% }%>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow">
                                <%= list%>
                            </div>
                            <%= navi%>
                        </td>
                    </tr>
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="imger"><div id="closser"><span>ЗАКРЫТЬ</span></div><div id="imgerViewer"></div></div>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', function (A) {
                var myHeight = A.DOM.winHeight() - 300;
                if (myHeight < 300)
                    myHeight = 300;
                function closser(node) {
                    node.fx.run();
                }
                var overlayMask = new A.OverlayMask().render();
                A.all("a.imger").each(function () {
                    var href = this.getAttribute('href');
                    this.setAttribute('href', 'javascript:')
                    this.on("click", function () {
                        A.one('#imger').fx.run();
                        var html = "<img src='" + href + "'>";
                        A.one("#imgerViewer").html(html);
                        A.one("#imger").setY(A.one('win').get('docScrollY') + 20);
                    });
                });
                A.one('#imger').setStyle('opacity', '0');
                A.one('#closser span').on('click', function (event) {
                    closser(A.one("#imger"));
                });
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
                A.one("#content").setStyle("minHeight", myHeight + "px");
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
            });
        </script>
        <script type="text/javascript" src="resources/scripts/top-menu-fixed.js"></script> 
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>