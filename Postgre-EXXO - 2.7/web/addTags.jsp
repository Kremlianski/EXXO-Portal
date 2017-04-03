<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String element = request.getParameter("el");
    String s = request.getParameter("s");
    String o = request.getParameter("o");
    String r = request.getParameter("r");
    String src = "";
    String go = "";
    if (o != null) {
        go = "?owner=" + o;
    }
    if (s.equals("files")) {
        src = "fileProp.jsp?id=" + element;
    } else if (s.equals("gallaries") && r == null) {
        src = "gallariesMod.jsp" + go;
    } else if (s.equals("gallaries") && r != null) {
        src = "pic.jsp?id=" + element;
    } else if (s.equals("bloges")) {
        src = "blog.jsp?id=" + element;
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Добавить теги</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');    
            .item-hover {background: red;}
            .item-hover  a {color: white;}
            #main {position: relative;margin: 10px;width:auto;height: auto;margin-bottom: 10px;background-color: white;padding: 15px;overflow-y: auto;overflow-x: hidden;}
            .hov{ color:black; background-color: white; }
            .inno{margin:5px;margin-left:20px;}
            .cat {background-image: url("folder_closed.png");background-position: left;background-repeat: no-repeat;padding-left: 20px;}
            .file-hover {color: red;}
            .filepath{color:gray;}
            table#small {width: 100%;}
            #tdmain {width:40%;}
            #tdinfo{width:60%;}
            #info{width:auto;height: 100%;padding: 4px;background-color: white; overflow-y: auto;overflow-x: hidden;}
            .tagsDiv {padding:3px; display: inline-block; background-color: white; border: 1px solid #bfbfbf;margin:3px;color:black;-moz-border-radius: 4px;
                      -webkit-border-radius: 4px;border-radius: 4px; padding-left: 20px; background-image: url('img/tag.png');background-position: 2px center;background-repeat: no-repeat;}
            #info {margin:0px;margin-right:10px; margin-left: 10px;}
            .tagsDiv:hover {border: 1px solid black;margin:3px;color:white;background-color:gray;cursor:pointer;}
            .fil {background-image: url("img/tag.png");background-position: left;background-repeat: no-repeat;padding-left: 20px;}
        </style>
        <style><%=CSS.getCSS(request)%></style>
        <style><%=CSS3.getCSS("addTags.jsp", request)%></style>
    </head>
    <body id="addTags_jsp">
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
                                        <li><a href="<%= src%>" class="footmenu">Закончить</a></li>
                                        <li> <a href="http://exxo.ru/howto/knowledge-management-howto.html#tags" class="footmenu howto" target="_blank">?</a></li>
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
                                            <div id="info" class="exxo-shadow"></div> 
                                        </td> 
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr> 
                </tbody>
            </table>
                
        </div>
        <script type="text/javascript">
            AUI().ready('aui-io', function (A) {
                var myHeight = A.DOM.winHeight() - 175;
                if (myHeight < 300)
                    myHeight = 300;
                Reload("1", null);
                function Reload(superior, id) {
                    A.one("#main").unplug(A.Plugin.IO);
                    var data = {superior: superior};
                    if (id != null)
                        data = {id: id};
                    var DATA = {s: '<%= s%>', element: '<%=element%>'};
                    reInfo(DATA);
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
                                        var DATA = {id: fileID, s: '<%= s%>', element: '<%=element%>'};
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
                A.one("#info").setStyle("height", myHeight + "px");
                function reInfo(data) {
                    A.one("#info").unplug(A.Plugin.IO);
                    A.one("#info").plug(A.Plugin.IO, {uri: 'tagInfo', data: data, method: 'POST',
                        on: {'end': function () {
                                A.all(".tagsDiv").each(function () {
                                    this.on("click", function () {
                                        if (window.confirm('Удалить тег?')) {
                                            var id = this.getAttribute('id');
                                            A.io.request("dropTag", {method: 'post', data: {id: id, s: '<%= s%>', element: '<%=element%>'}}
                                            );
                                            this.remove();
                                        }
                                    })
                                });
                            }}});
                }
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>