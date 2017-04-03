<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="BLOG" class="BEANS.BlogBean" scope="page"/><%
    String owner = (String) session.getAttribute("id");
    String role = (String) session.getAttribute("role");
    String id = request.getParameter("id");
    String comJS = "";
    String ansJS = "";
    if (request.getParameter("comments") != null) {
        comJS = "A.one('#listTable').scrollIntoView ( );";
    }
    if (request.getParameter("answers") != null) {
        comJS = "A.one('#listTable').scrollIntoView ( );";
    }
    String answers = "";
    boolean a = false;
    boolean r = false;
    boolean c = false;
    if (request.getParameter("answers") != null) {
        a = true;
    }
    if (request.getParameter("respect") != null) {
        r = true;
    }
    if (request.getParameter("comments") != null) {
        c = true;
    }
    if (id == null || id.equals("")) {
        response.sendRedirect("notPermited.html");
    }
    BLOG.setOwner(owner, id, role, (String) session.getAttribute("global_id"), a, r, c);
    String emp = BLOG.getEmp(request);
    String list = BLOG.getList();
    String slogan = SLOGAN.getSlogan(request);
    Boolean diff = BLOG.getDiff();
    String ansCount = BLOG.getAns();
    String count = BLOG.getCount();
    String commCount = BLOG.getCommCount();
    String respcount = BLOG.getRespcount();
    answers = BLOG.getAnswers();
    String comments = BLOG.getComments();
    if (BLOG.getNotPermitted()) {
        response.sendRedirect("notPermited.html");
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Сообщение</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" /><link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {width:90%;height:auto;position: relative; margin:0px 10px;overflow-x: hidden;overflow-y: auto; background-color: white ;border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;}
            span.hoverPlus {color:red;}
            #commentator {margin: 15px;padding:20px;position: absolute;z-index: 99998;top:0px;;height: auto;width: 700px;color: black;
                          background-color: white;display: none;overflow: auto;}
            .close, #closer,#closser {color:red;width:100%;text-align:right;}
            .text {font-size:10pt;padding-bottom: 10px;text-align: justify;}
            .timer {font-size: 11px;color: #3B3B8F;text-align: right;}
            .HBlog{text-align: right;}
            .bus {color:#0F1F99;}
            .blog {margin-bottom: 20px;background-color: white;padding: 10px;padding-top: 5px;padding-bottom: 5px;clear: both;}
            .B {padding-left: 10px;}
            #plus, #plusik {font-size: 14pt;text-align: right;margin-bottom: 20px;}
            #plus span, #plusik span {padding-left: 20px;}
            .BUS {border-color: #0F1F99;border-style: solid;border-width: 1px;}
            #containerImg img.photo {margin: 2px;}
            .hov{color:black;background-color: white;}
            .inno{margin:5px;margin-left:20px;}
            #closer {width:90%;}
            #imger {position: absolute;display: none;left:20px;top:20px;padding: 10px;background-color: black;z-index: 9999999;}
            .text {padding: 20px;padding-top:20px;padding-left: 30px;}
            #blogs {width: 700px; margin: auto auto; overflow: hidden;}
            #blogs .blog {border:0}
            #back {padding-right:40px; text-align: right;}
            .empInfo {width:100%; margin-left: 0px; }
            .empInfo .left {width:100px; padding:20px 0px;}
            .empInfo .right {font-size:14px; vertical-align: top; padding-top: 20px; text-align:left;}
            #bottomBlog {text-align: right;background-color: rgb(248, 248, 248); padding: 10px 5px;border-top: 1px solid #dedede;}
            #bottomBlog a {padding-right: 30px; text-decoration: none;}
            #bottomBlog:hover a, .results:hover a {color: #972626;}
            #bottomBlog a:hover, .results a:hover {text-decoration: underline;}
            .results a {text-decoration: none;}
            #listTable td {padding: 5px; padding-top: 10px; padding-bottom: 10px;}
            #listTable  a {font-size:12px; text-decoration: none; color:black;}
            #listTable tr:hover  a {color: #972626;}
            #listTable  a.bus {color:#0F1F99;}
            #listTable tr:hover  a.bus {color: #972626;}
            #listTable {margin: 0px 0px; width: 100%;}
            #listTable  a:hover {text-decoration: underline;}
            .results {background-color: rgb(248, 248, 248); padding: 10px 5px;}
            .results:last-of-type {border-bottom: 1px solid #dedede;}
            .results a, .results span {padding-left: 20px;}
            #listTable td.type_0, #listTable td.type_1,#listTable td.type_2,#listTable td.type_3,#listTable td.type_4 {padding: 0;}
            .fio {color:black; font-size:12px; padding-right: 10px; text-align: left;}
            .RE {margin-bottom:5px;}
            #submitComment {padding: 10px; cursor: pointer;}
            #closeCom {cursor: pointer; margin-bottom: 10px;}
            input#submit, #commentator input[type=submit] {margin: 20px auto; display: block; width: 110px; padding: 5px;}
            #commentator {width: 650px;}
            #commentator textarea {width: 630px; height: 200px;border: 1px solid #DEDEDE;overflow: auto;}
            #img img {width: 50px; height: 67px; margin-left: 10px;}
            #blogs #name {color: gray; text-align: center; font-size: 18px;}
            #blogs:hover .B {color:#972626;}
            a.B {text-decoration: none;}
            div.RE a, div.DOCRe a{text-decoration: none;}
            #blogs:hover div.RE a,  #blogs:hover div.DOCRe a{color:#972626;}
            #blogs div.RE a:hover, #blogs div.DOCRe a:hover {text-decoration: underline;}
            .B:hover {text-decoration: underline;}
            .time {color:#3B3B8F; font-size:11px;}
            table#nav {width: 100%; border-bottom: 1px solid #dedede;margin-bottom: 20px; background-color:rgb(248, 248, 248)}
            table#nav td.navThree{padding: 5px; width: 35%; padding-right: 20px; text-align: left;}
            table#nav td.navTwo{background-image:url('small/forward-big.png'); background-position: center center; background-repeat: no-repeat; width: 30%;}
            table#nav td.navThree a, table#nav td.navOne a {text-decoration: none;}
            table#nav td.navThree:hover a, table#nav td.navOne:hover a {color: #972626;}
            table#nav td.navThree a:hover,  table#nav td.navOne a:hover{text-decoration: underline;}
            <%if (!diff) {%>table#nav td.navTwo:hover {background-image:url('small/forward-big1.png'); cursor: pointer;}<%}%>
            #listShrim {width: 100%;}
            td.empIMG img {width: 50px; height: 67px;}
            #listTable td.empIMG {width: 60px; padding: 5px; padding-left: 10px; padding-right: 0px;padding-top: 10px; padding-bottom: 10px;}
            #tags {margin-top: 10px;}
            .fio {color:black; font-size:12px; padding-right: 10px;  padding-left: 10px; text-align: left;}
            .fio>span {padding-left: 10px;}
            .fio a{font-size: 10px; font-weight: bold;}
            .tdName::before {content: ' ';height: 0;position: absolute;width: 0;border: 8px solid transparent;border-right-color: #dedede;right: 100%;top: 10px;}
            .tdName::after {content: ' ';height: 0;position: absolute;width: 0;border: 6px solid transparent;border-right-color: #FDF2F2;right: 100%;top: 12px;}
            #listTable td.tdComment {vertical-align: top; position: relative;}
            .tdName {position: relative; padding: 10px; border: 1px solid #dedede; border-radius: 5px; background-color: #FDF2F2;}
            .empIMG img {border-radius: 5px;}
            #listTable .fio a {font-size: 10px;}
            table.exxo-table tr:nth-of-type(odd) td {background-color: white;}
            table.exxo-table tr:hover td {background-color: #E3E2FD; cursor: pointer;}
            table.exxo-table {margin-bottom:20px; margin-top: 10px;}
            table.exxo-table tr:first-of-type td {border-top: 1px solid #dedede;}
            #input {color: gray; margin-left: 20px;}
            div.text img {max-width: 100%;}
        </style>
        <style><%=CSS.getCSS(request)%></style>
        <style><%=CSS3.getCSS("blog.jsp", request)%></style>
    </head>
    <body id="blog_jsp">
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
                                                <td id="slogan" valign="center">
                                                    <%= slogan%>
                                                </td>
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
                                            <a href="http://exxo.ru/howto/mail-howto.html#comunication" class="footmenu howto" target="_blank">?</a>
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
                        <td id="menu-td" class="menu-td" valign="top">
                            <div class="exxo-menu exxo-shadow" id="menu">
                                <jsp:include page="menu-blog.xhtml" flush="true" />
                            </div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow">
                                <table id="nav">
                                    <tr>
                                        <td class="navOne"><%=emp%></td>
                                        <td class="navTwo"></td>
                                        <td class="navThree">
                                            <%= BLOG.too%><%= BLOG.tags%>
                                        </td>
                                    </tr>
                                </table>
                                <table id="listShrim">
                                    <tr>
                                        <td>
                                            <%=list%>
                                        </td>
                                    </tr>
                                </table>
                                <div id="bottomBlog">
                                    <a href="javascript:;" id="addComment">КОММЕНТАРИЙ</a>
                                    <a href="blogInsert.jsp?id=<%= id%>&r=1">ОТВЕТИТЬ</a>
                                    <a href="blog.jsp?id=<%= id%>&amp;respect=1">СОГЛАСИТЬСЯ</a>
                                </div>
                                <div class="results"> 
                                    <span>Просмотров:</span> 
                                    <%=count%>
                                    <span>Согласились:</span> 
                                    <%=respcount%>
                                    <a href="blog.jsp?id=<%= id%>&amp;answers=1">ответов:</a> 
                                    <%= ansCount%>
                                    <a href="blog.jsp?id=<%= id%>&amp;comments=1">комментариев:</a> 
                                    <%= commCount%>
                                </div>
                                <div class="results"> 
                                    <% if (a) {%>
                                    <a href="blog.jsp?id=<%= id%>">свернуть ответы</a>
                                    <%}%><% if (c) {%>
                                    <a href="blog.jsp?id=<%= id%>">свернуть комментарии</a>
                                    <%}%>
                                </div>
                                <%= answers%><%= comments%>
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
            <form action="comment" method="post" id="forma">
                <textarea name="comment" id="comment" cols="70" rows="10"></textarea>
                <input type="hidden" name="id" value="<%=id%>">
                <input type="submit" id='submitComment' value="Сохранить"></form></div>
        <div id="imger">
            <div id="closser">
                <span>ЗАКРЫТЬ</span>
            </div>
            <div id="imgerViewer"></div>
                
        </div>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', 'aui-button-item', 'aui-io', function (A) {
                var overlayMask = new A.OverlayMask().render();
                var myEditor;
                var myHeight = A.DOM.winHeight() - 150;
                if (myHeight < 300)
                    myHeight = 300;
                var HH = myHeight - 80;
                if (HH < 250)
                    HH = 250;
            <%if (!diff) {%>
                A.one("td.navTwo").on("click", function () {
                    location = "addTags.jsp?el=<%=id%>&s=bloges"
                });
            <% } %>
                function closser(node) {
                    node.fx.run();
                }
                A.all("a.imger").each(function () {
                    var href = this.getAttribute('href');
                    this.setAttribute('href', 'javascript:')
                    this.on("click", function () {
                        A.one('#imger').fx.run();
                        var html = "<img src='" + href + "'>";
                        A.one("#imgerViewer").html(html).scrollIntoView( );
                    });
                })
                A.one('#closser span').on('click', function (event) {
                    closser(A.one("#imger"));
                });
            <%if (!diff) { %>
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
            <% }%>
                A.one("#addComment").on("click", function () {
                    A.one('#commentator').fx.run();
                    A.one('#commentator').scrollIntoView( );
                });
                A.one("#closeCom").on("click", function () {
                    closser(A.one("#commentator"));
                });
                A.one("#grid").setStyle("minHeight", myHeight + "px");
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
            <%=comJS%><%=ansJS%>
                A.all("#listShrim table.exxo-table tr").each(function () {
                    this.on('click', function () {
                        location = "fileLoader.jsp?id=" + this.getAttribute("id");
                    })
                });
                var overlayMask = new A.OverlayMask().render();
                A.one('#imger').setStyle('opacity', '0');
                A.one('#commentator').setStyle('opacity', '0');
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
                A.one('#commentator').plug(A.Plugin.NodeFX, conf);
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>