<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="LIST" class="BEANS.addDopCompany" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    LIST.getList(request);
    String s = request.getParameter("s");
    String back = LIST.back;
    String d = LIST.res;
    String id = request.getParameter("id");
    String owner = request.getParameter("owner");
    String name = LIST.name;
    if (owner == null) {
        owner = (String) session.getAttribute("id");
    }
    if (!LIST.ok) {
        response.sendRedirect("notPermited.html");
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Добавить к допущенным</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" /><link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            .inno{margin:5px;margin-left:20px;}
            .title1{width:250px;font-size:15px;}
            .title2{width:250px;font-size:13px;text-decoration:underline;font-weight:bolder;}
            .title3{width:250px;}
            .title4{font-style:italic;width:250px;}
            .title5{width:250px;}
            .title6{font-style:italic;width:250px;}
            .title7{width:250px;}
            .title8{font-style:italic;width:250px;}
            #main {position: relative;margin:0px 10px;height: auto;margin-bottom: 20px;background-color: white;padding: 4px; width: 95%;
                   border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px; border-style: solid;}
            div.item {display: block;width: 160px;padding: 20px 10px;border: 1px solid #BFBFBF; margin: 10px;color: #3C3D58;background-color:#E2E4EE;font-size: 12px;
                      text-align: left;}
            div.inno {padding-left: 70px;}
            .item:hover {background-color:white;color: red;cursor: pointer;}
            #dopusks {font-size: 12px; margin-left: 10px;width: 168px; border: 1px solid #dedede; border-border: 1px solid #bfbfbf; background-color: rgb(252,252,252);padding-bottom: 20px;}
            #dopusks .emps {padding: 6px; cursor: pointer; padding-right: 30px;padding-left: 8px; border: 1px solid rgb(252,252,252);}
            #dopusks .emps:hover {color: red; background-image:url('drop.png'); background-repeat: no-repeat;background-color: white; border-radius: 5px; border: 1px solid rgb(240,240,240); background-position: 146px center;}
            #empsH {padding: 10px; color: gray; font-weight: bold; padding-left: 8px; padding-top:0;}
            #fname {padding: 8px; font-size: 10px;}
        </style>
        <style><%=CSS.getCSS(request)%></style>
        <style><%=CSS3.getCSS("addDopCompany.jsp", request)%></style>
    </head>
    <body id="addDopCompany_jsp">
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
                                                <td><jsp:include page="poisk" flush="true" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div id="foot">
                                    <ul class="topMenu">
                                        <jsp:include page="topMenu.xhtml" flush="true" />
                                        <li><a href="<%=back%>" class="footmenu">Закончить</a></li>
                                        <li> <a href="http://exxo.ru/howto/documents-howto.html#admit" class="footmenu howto" target="_blank">?</a></li>
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
                                <jsp:include page="menu-doc.xhtml" flush="true" />
                            </div>
                            <div id="dopusks" class="exxo-shadow">
                                <div id="fname"><%=name%></div>
                                <div id="empsH">Допущены:</div>
                                <div id="empsDiv"><%=d%></div>
                            </div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="main" class="exxo-shadow"></div>
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <script type="text/javascript">
            AUI().ready('aui-io', function (A) {
                A.one("#main").plug(A.Plugin.IO, {uri: 'company', method: 'POST', on: {"end": function () {
                            A.all("#main .item").each(function () {
                                this.addClass("exxo-shadow1");
                                this.on("click", function () {
                                    location = "addDopComp.jsp?uid=" + this.getAttribute("id") + "&id=<%=id%>&owner=<%=owner%>";
                                })
                            })
                        }}});
            });
        </script>
        <script type="text/javascript">
            AUI().ready('aui-node', 'aui-io', function (A) {
                function dropRegistrat() {
                    dropAr = "";
                    A.all("#dopusks .emps").each(function () {
                        this.on("click", function () {
                            if (confirm("Удалить?"))
                                empsDrop(this)
                        })
                    });
                    A.one('#dopusks').setStyle('height', this.getComputedStyle('height'));
                }
                function empsDrop(node) {
                    node.purge().remove();
                    dropAr = "";
                    A.all("#dopusks .emps").each(function () {
                        if (dropAr == "")
                            dropAr += this.getAttribute("id");
                        else
                            dropAr += "," + this.getAttribute("id");
                    });

                    A.one("#empsDiv").unplug();
                    A.one("#empsDiv").plug(A.Plugin.IO, {uri: 'dopuskDrop', data: {id: "<%=id%>", dopusk: dropAr, owner: "<%=owner%>"},
                        method: 'post', after: {'success': function () {
                                dropRegistrat()
                            }}});
                }
                dropRegistrat();
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>