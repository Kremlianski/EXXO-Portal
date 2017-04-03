<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="BLOG" class="BEANS.BlogEditorBean" scope="page"/><%
    String owner = (String) session.getAttribute("id");
    String list = BLOG.getList(owner, request);
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Черновик записи в блог</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {width:90%;height:auto;position: relative;padding:5px;margin:0px 10px;overflow-x: hidden;overflow-y: auto; background-color:white;
                      border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;}
            span.hoverPlus {color:red;}
            .close, #closer,#closser {color:red;width:100%;text-align:right;}
            .text {font-size:10pt;padding-bottom: 10px;text-align: justify;}
            .text {padding: 20px;padding-top:20px;padding-left: 30px;}
            .HBlog{text-align: center;margin-bottom: 20px;}
            .bus {color:green;}
            .blog {margin-bottom: 20px;background-color: white;padding: 10px;padding-top: 5px;padding-bottom: 5px;clear: both;}
            .B {padding-left: 10px; text-decoration: none;}
            #plus, #plusik {font-size: 14pt;text-align: right;margin-bottom: 20px;}
            #plus span, #plusik span {padding-left: 20px;}
            .BUS {border-color: green;border-style: solid;border-width: 1px;}
            #imges {position:absolute;z-index:99999;width:100%;height:100%;top:0px;left:0px;background-color:white;display:none;}
            #inserter {width:90%;height: 80%;margin: 5px;margin-top: 10px;border-color: gray;border-width: 1px;border-style: solid;overflow-y: auto;
                       overflow-x: hidden;}
            #containerImg img.photo {margin: 2px;}
            .hov{color:black;background-color: white;}
            #closer {width:90%;}
            #imger {position: absolute;display: none;left:20px;top:20px;padding: 10px;background-color: black;z-index: 9999999;}
            #blogs {width: 700px; margin: auto auto; overflow: hidden;}
            #blogs .blog {border:0}
            #addBlog {font-size: 16px; text-align: center;}
            #blogs #name {color: gray; text-align: center; font-size: 18px;}
            #blogs:hover .B {color:#972626;}
            .B:hover {text-decoration: underline;}
            #addBlog {padding:50px;}
            #addBlog a{text-decoration: none}
            #addBlog:hover a{color:#972626;}
            #addBlog a:hover {text-decoration: underline;}
            table.exxo-table tr:nth-of-type(odd) td {background-color: white;}
            table.exxo-table tr:hover td {background-color: #E3E2FD; cursor: pointer;}
            table.exxo-table {margin:100px; margin-top: 10px;}
            table.exxo-table tr:first-of-type td {border-top: 1px solid #bfbfbf;}
            #input {color: gray; margin-left: 100px;}
            div.text img {max-width: 100%;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("blogEditor.jsp", request)%>
        </style>
    </head>
    <body id="blogEditor_jsp">
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
                                            <a href="http://exxo.ru/howto/mail-howto.html#send" class="footmenu howto" target="_blank">?</a>
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
                                <%=list%>
                            </div>
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="imger"><div id="closser"><span>ЗАКРЫТЬ</span></div><div id="imgerViewer"></div></div>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', function (A) {
                var overlayMask = new A.OverlayMask().render();
                var myEditor;
                var myHeight = A.DOM.winHeight() - 150;
                if (myHeight < 300)
                    myHeight = 300;
                var HH = myHeight - 80;
                if (HH < 250)
                    HH = 250;
                A.all("a.imger").each(function () {
                    var href = this.getAttribute('href');
                    this.setAttribute('href', 'javascript:');
                    this.on("click", function () {
                        overlayMask.set('target', document).show();
                        A.one('#imger').setStyle('display', 'block');
                        anim.run();
                        var html = "<img src='" + href + "'>";
                        A.one("#imgerViewer").html(html).scrollIntoView( );
                    });
                });
                var overlayMask = new A.OverlayMask().render();
                A.one('#imger').setStyle('opacity', '0');
                A.one('#imger').setStyle('display', 'none');
                var anim = new A.Anim({node: '#imger', to: {opacity: 1}, on: {'start': function () {
                            A.one("#imger").setStyle('display', 'block');
                            A.one('#closser span').scrollIntoView( )
                        }}});
                var anim1 = new A.Anim({node: '#imger', to: {opacity: 0}, on: {'end': function () {
                            A.one("#imger").setStyle('display', 'none');
                            overlayMask.hide();
                        }}});
                A.one('#closser span').on('click', function (event) {
                    closer(A.one("#imger"));
                });
                function closer(node) {
                    anim1.run();
                }
                //A.one("#content").setStyle("height",myHeight+"px");
                A.all("#content table.exxo-table tr").each(function () {
                    this.on('click', function () {
                        location = "fileLoader.jsp?id=" + this.getAttribute("id");
                    })
                });
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>