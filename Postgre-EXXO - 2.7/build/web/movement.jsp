<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="MOV" class="BEANS.MovementBean" scope="page"/><%
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") < 0 && role.indexOf("c") < 0 && role.indexOf("Z") < 0) {
        response.sendRedirect("notPermited.html");
    }
    String list = MOV.getList(request);
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Переезд</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {font-size:12px;margin:10px; margin-top: 0px; width:90%;}
            #content table {width:100%;}
            #content table td {padding: 4px;}
            .first {width:50%;}
            .second {width:25%;}
            #content th {background-image: none;color: #3C3D58;background-color: #E2E4EE;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style><%=CSS3.getCSS("movement.jsp", request)%></style>
    </head>
    <body id="movement_jsp">
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
                                        <li>
                                            <a href="territoriesMod.jsp" class="footmenu">Закончить </a>
                                        </li>
                                        <li> <a href="http://exxo.ru/howto/staff-howto.html#offices" class="footmenu howto" target="_blank">?</a></li>
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
                            <div class="menu" id="menu">
                                <div class="exxo-menu exxo-shadow" id="menu">
                                    <jsp:include page="menu-emp.xhtml" flush="true" />
                                </div>
                            </div></td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow"  >
                                <table id="list">
                                    <tbody>
                                        <tr>
                                            <th>Фамилия Имя Отчество</th>
                                            <th>Офис</th>
                                            <th>Комната</th>
                                            <th>ОК</th>
                                        </tr>
                                        <%= list%>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
    </div>
    <script type="text/javascript">
        AUI().ready('aui-button-item', 'aui-node', 'aui-io-request', function (A) {
            A.all("tr.data").each(function () {
                var tr = this;
                new A.ButtonItem({icon: 'check', handler: {fn: function (e) {
                            if (tr.one("select").get("value") != "Переезд") {
                                A.io.request("movement", {cache: false,
                                    data: {id: tr.one("[name=id]").get("value"), office: tr.one("select").get("value"), room: tr.one("[name=room]").get("value")},
                                    method: 'POST', on: {success: function () {
                                            tr.remove();
                                        }}});
                            } else {
                                alert("Необходимо выбрать офис!")
                            }
                        }}}).render(this.one(".ultra"));
            });
        });
    </script>
    <script type="text/javascript" src="scripts/poisk.js"></script>
    <script type="text/javascript" src="scripts/topMenu.js"></script>
</body>
</html>