<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="UNIT" class="BEANS.UnitBean" scope="page"/><%
    String id = request.getParameter("id");
    String name = UNIT.getName(id, request);
    String list = UNIT.getList();
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Информация о подразделении</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {width: 500px;padding: 20px;padding-left: 15%;padding-right: 15%;line-height: 2;font-size: 14px;}
            div.text img {max-width: 100%;}
        </style>
        <style><%=CSS.getCSS(request)%></style>
        <style><%=CSS3.getCSS("unit.jsp", request)%></style>
    </head>
    <body id="unit_jsp">
        <div id="content-wrapper">
            <div id="header">
                <table id="tableH">
                    <tbody>
                        <tr>
                            <td id="tdlogo"><img src="LOGO" id="logo"></td><td>
                                <div id="head"><table><tbody><tr><td id="slogan" valign="center"><%= slogan%></td><td><jsp:include page="poisk" flush="true" />
                                                </td></tr></tbody></table></div><div id="foot"><ul class="topMenu"><jsp:include page="topMenu.xhtml" flush="true" /><li><a href="unitEditor.jsp?id=<%= id%>" class="footmenu">Настройка</a></li>
                                        <li> <a href="http://exxo.ru/howto/staff-howto.html#structure" class="footmenu howto" target="_blank">?</a></li></ul></div>
                            </td></tr></tbody></table></div><table id="grid" class="grid"><tbody id="atbody"><tr id="tr">
                        <td id="menu-td" class="menu-td" valign="top"><div class="exxo-menu exxo-shadow" id="menu"><jsp:include page="menu-emp.xhtml" flush="true" /></div>
                            <div   id="menuList"><a href="empListComp.jsp?uid=<%=id%>"><%=name%></a></div></td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow exxo-content"><%=list%></div>   
                        </td>
                    </tr> </tbody></table><jsp:include page="FOOTER" flush="true" /></div>
        <div id="imger" class="exxo-imger"><div id="close"><span>ЗАКРЫТЬ</span></div><div id="imgerViewer"></div></div>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', function (A) {
                var overlayMask = new A.OverlayMask().render();
                A.one('#imger').setStyle('opacity', '0');
                A.one('#imger').setStyle('display', 'none');
                var anim = new A.Anim({node: '#imger', to: {opacity: 1}, on: {'start': function () {
                            A.one("#imger").setStyle('display', 'block');
                        }}});
                var anim1 = new A.Anim({node: '#imger', to: {opacity: 0}, on: {'end': function () {
                            A.one("#imger").setStyle('display', 'none');
                            overlayMask.hide();
                        }}});
                A.one('#close span').on('click', function (event) {
                    closer(A.one("#imger"));
                });
                function closer(node) {
                    anim1.run();
                }
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

            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>