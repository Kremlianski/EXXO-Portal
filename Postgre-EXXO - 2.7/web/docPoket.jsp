<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String id = request.getParameter("id");
    String uri = request.getParameter("s");
    if (uri == null) {
        uri = "docBlog";
    } else if (!uri.equals("docBlog") && !uri.equals("docEvent")) {
        uri = "docBlog";
    }
    String end = "blogEditor.jsp";
    String ev = "noevent";
    if (request.getParameter("ev") != null && !request.getParameter("ev").equals("")) {
        ev = request.getParameter("ev");
    }
    if (uri.equals("docEvent")) {
        end = "event.xhtml?id=" + ev;
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Добавить документ</title>
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
            #tdmain {width:70%;}
            #tdinfo{width:30%;}
            #info, #info1 {margin:0px 25px;width:auto;height: 100%;margin-left: 0px;padding: 4px;background-color: rgb(252, 252, 252);}
            table#files {width:100%}
            table#files td {text-align: left;}
            td.icon {width:20px; height:24px;}
            td.fname, td.name {padding-left:20px; max-width: 200px;}
            td.created {font-size: 10px; color: #3B3B8F; width:70px; padding-right: 4px;padding-left: 4px;}
            td.dop {width: 20px;}
            tr.cat td.icon {background-image: url("folder_closed.png");background-position: center center;background-repeat: no-repeat;}
            #tdinfo #info img#dop {border: 0;}
            #tags a{margin-right: 6px;}
            table#files tr:nth-of-type(odd) {background-color: #F5F2F2;}

            #info1 {border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid; background-color: #EEEEF1;}
            #info1 td.td2 {padding-left: 6px;}
            .mem .aui-icon {display: inline-block;}
            .mem-label {height: 24px;vertical-align: middle;padding-left: 10px;}
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
            #content td.dop {width: 16px; padding: 4px}
            #too {margin-bottom: 6px;}
            #too span.emps{color: gray;}
            table#small #main div.up {background-image: none;padding-left:0;}
            table#small div.up span {display: inline-block; margin-left: 5px; margin-right: 10px; margin-top: 0px;}
            table#small #main div.upper {margin-bottom: 14px;}
            table#small #main div.upper, table#small #main .cat {font-size: 14px;}
            #listDocs {font-size: 11px; margin-left: 10px;width: 168px; border: 1px solid #dedede; border-border: 1px solid #bfbfbf; background-color: rgb(252,252,252);padding-bottom: 20px;}
            #listDocs .listDoc {padding: 6px; cursor: pointer; padding-right: 30px;padding-left: 8px; border: 1px solid rgb(252,252,252);}
            #listDocs .listDoc:hover {color: red; background-image:url('drop.png'); background-repeat: no-repeat;background-color: white; border-radius: 5px; border: 1px solid rgb(240,240,240); background-position: 146px center;}
            #listH {padding: 10px; color: gray; font-weight: bold; padding-left: 8px; padding-top:0; font-size: 12px; margin-top: 5px;}
            #empty {color: #dedede; padding: 10px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("docPoket.jsp", request)%>
        </style>
    </head>
    <body id="docPoket_jsp">
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
                                        <li><a href="<%=end%>" class="footmenu">Закончить</a></li>
                                        <li> <a href="http://exxo.ru/howto/documents-howto.html#find" class="footmenu howto" target="_blank">?</a></li>
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
                            <div id="listDocs"  class="exxo-shadow">
                                <div id="listH">Вложения:</div>
                                <div id="innerList"></div>
                        </td>
                        <td id="column-3" valign="top"> 
                            <table id="small">
                                <tbody>
                                    <tr>
                                        <td id="tdmain"  valign="top">
                                            <div id="main" class="exxo-shadow"></div>
                                        </td>
                                        <td id="tdinfo"  valign="top">
                                            <div id="info" class="exxo-shadow">
                                                <div class="mems">Чтобы добавить файл, кликните по нему.</div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table></td>
                    </tr> 
                </tbody>
            </table>

        </div>
        <script type="text/javascript">
            AUI().ready('aui-io', function (A) {
                var myHeight = A.DOM.winHeight() - 150;
                if (myHeight < 300)
                    myHeight = 300;
                var fileID;
                var startID = "1";
                var infoHTML = A.one("#info").html();
            <% if (id != null) {%> startID =<%=id%> <% }%>
                var carCat = startID;
                var uri = '<%=uri%>';
                var ev = '<%=ev%>';

                Reload(startID, null);
                function Reload(superior, id) {
                    A.one("#main").unplug(A.Plugin.IO);
                    var data = {superior: superior};
                    if (id != null)
                        data = {id: id};
                    A.one("#main").plug(A.Plugin.IO, {uri: 'docClassic', method: 'POST',
                        data: data,
                        on: {'end': function (event) {
                                var superNode = A.one('.upper');
                                if (superNode != null)
                                    carCat = superNode.getAttribute('id');
                                else
                                    carCat = 1;
                                A.all(".cat").each(function () {
                                    this.on("mouseover", function () {
                                        this.addClass("file-hover");
                                        A.one("#info").removeClass("exxo-shadow").html(infoHTML);
                                    });
                                });

                                A.all(".fil").each(function () {
                                    this.on("mouseover", function () {
                                        this.addClass("file-hover");
                                        fileID = this.get("id");
                                        A.one("#info").removeClass("exxo-shadow");
                                        A.one("#info").unplug(A.Plugin.IO);
                                        A.one("#info").plug(A.Plugin.IO, {uri: 'fileInfo1', data: {id: fileID}, method: 'POST', after: {'end': function () {
                                                    A.one("#info").addClass("exxo-shadow");
                                                }}});
                                    })
                                });
                                A.all(".item").each(function () {
                                    this.on("mouseout", function () {
                                        this.removeClass("file-hover");
                                    })
                                });
                                A.all(".fil").each(function () {
                                    this.on("click", function () {
                                        listReload(this.get("id"), '1');
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
                function listReload(id, del) {
                    A.one("#listDocs").removeClass("exxo-shadow");
                    A.one("#innerList").unplug(A.Plugin.IO);
                    A.one("#innerList").plug(A.Plugin.IO, {uri: uri, data: {id: id, del: del, ev: ev}, method: 'POST', after: {'end': function () {
                                A.one("#listDocs").addClass("exxo-shadow");
                                A.all('.listDoc').each(function () {
                                    this.on('click', function () {
                                        if (confirm("Удалить?"))
                                            listReload(this.get('id').substring(1, this.get('id').length), '2')
                                    })
                                });
                            }}
                    });
                }
                listReload('-1', '0');
                A.one("#main").setStyle("height", myHeight + "px");
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>