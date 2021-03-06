<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="T" class="BEANS.ProjectBean" scope="page"/><%
    String user = (String) session.getAttribute("id");
    String list = T.getList(user, request);
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Проекты</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #container {position: relative;padding:0px;margin:0px 10px; width: 90%;}
            #content {font-size:12px;margin-top:0px;}
            #content table{width:100%;background-color: white;}
            .first {width:60%;padding-top:10px;}
            .second{width:40%;padding-top:10px;}
            #content table td{padding: 4px;border-bottom-color: #DEDEDE;border-bottom-style: solid;border-bottom-width: 1px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("projects.jsp", request)%>
        </style>
    </head>
    <body id="projects_jsp">
        <div id="content-wrapper">
            <div id="header">
                <table id="tableH">
                    <tbody>
                        <tr>
                            <td id="tdlogo"><img src="LOGO" id="logo"></td><td>
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
                                        <li><a href="projectMode.jsp" class="footmenu">Настройка</a></li>
                                        <li> <a href="http://exxo.ru/howto/staff-howto.html#projects" class="footmenu howto" target="_blank">?</a></li>
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
                                <jsp:include page="menu-emp.xhtml" flush="true" />
                            </div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="container" class="exxo-shadow">
                                <div id="content">
                                    <%=list%>
                                </div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <script type="text/javascript">
            AUI().ready('aui-node', function (A) {
                A.all("#content .datas").each(function () {
                    this.on("mouseenter", function () {
                        this.addClass("hover")
                    });
                    this.on("click", function () {
                        location = "groups.jsp?id=" + this.one(".first").get("id")
                    });
                    this.on("mouseleave", function () {
                        this.removeClass("hover")
                    });
                });
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>