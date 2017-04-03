<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="LIST" class="BEANS.AddDopComp" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String list = LIST.getList(request);
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
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #moveMain{margin:0px 10px;background-color: white;width: 95%;}
            #list {display:none;}
            #moveMain .item {font-size:12px;text-decoration:none;margin-top:0px;margin-bottom:10px;width:100%;
                             padding-bottom: 5px;padding-top: 5px;text-align:center;}
            #moveMain div.item:hover {text-decoration: underline; color:#972626;}
            #moveMain .emps:hover .fio a {color:#972626;}
            .fio a:hover {text-decoration: underline}
            .fio{font-size:12px;margin-left: 20px;width:30%;display:inline-block;}
            .inno {padding-bottom:10px;}
            .employee{width:100%}
            #moveMain div.title1{font-size:14px;}
            #moveMain div.title2{font-size:13px;}
            .position, .room, .tel{display: inline-block;width:20%;margin-bottom: 10px;}
            .item:hover{cursor:pointer;}
            .position, .room, .tel {font-size: 12px;display: inline-block;text-align: left;padding-right: 10px;}
            .fio {display: inline-block;text-align: left;padding-right: 10px;}
            .fio a {text-decoration: none;}
            #moveMain .item {margin-bottom: 0px; color: #3C3D58; background-color: #E2E4EE; background-image: none; margin-top: 20px; border:1px solid #D4D7EB;box-sizing: border-box; -moz-box-sizing: border-box;}
            .inno {padding-bottom: 10px;padding-left: 20px;padding-right: 10px;border-left: 1px solid #D4D7EB;border-bottom: 1px solid #BFBFBF;border-right: 1px solid #D4D7EB;}
            #moveMain > div.item {margin: 0px;}
            .inno div.boss .fio {font-weight: bolder;}
            #moveMain div.emps {width: 270px; display: inline-block; height: 120px; margin-right: 10px; padding-top: 0px; margin-top: 10px; vertical-align: top; background-color: #F3F3F3; border-top: 10px solid white;}
            #moveMain div.emps img {width: 60px; height:80px; margin-top: 10px;}
            #moveMain div.emps span {display: block; width: auto;}
            .position {font-style: italic;}
            span.imgE {float: left; margin:0; margin-right: 10px; padding: 0; padding-left: 6px; width: 60px !important;; height: 120px; display: block;}
            span.texts {padding-right: 10px; height: 110px; display: block; padding-top: 10px;}
            #dopusks {font-size: 12px; margin-left: 10px;width: 168px; border: 1px solid #dedede; border-border: 1px solid #bfbfbf; background-color: rgb(252,252,252);padding-bottom: 20px;}
            #dopusks .emps {padding: 6px; cursor: pointer; padding-right: 30px;padding-left: 8px; border: 1px solid rgb(252,252,252);}
            #dopusks .emps:hover {color: red; background-image:url('drop.png'); background-repeat: no-repeat;background-color: white; border-radius: 5px; border: 1px solid rgb(240,240,240); background-position: 146px center;}
            #empsH {padding: 10px; color: gray; font-weight: bold; padding-left: 8px; padding-top:0;}
            #fname {padding: 8px; font-size: 10px;}
            div#upComp {padding-top: 6px; padding-bottom: 8px; padding-left: 4px; border: 1px solid #dedede; border-bottom:0;}
            div#upComp a {text-decoration: none; padding-left: 20px; background-image: url('img/home.png'); background-repeat: no-repeat; background-position: center left; display: block; height: 16px;}
            #upComp a:hover {text-decoration: underline;color:#972626;}
        </style>
        <style><%=CSS.getCSS(request)%></style>
        <style><%=CSS3.getCSS("addDopComp.jsp", request)%></style>
    </head>
    <body id="addDopComp_jsp">
        <div id="content-wrapper">
            <div id="header">
                <table id="tableH">
                    <tbody>
                        <tr>
                            <td id="tdlogo"><img src="LOGO" id="logo"></td>
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
                                <div id="empsDiv">
                                    <%=d%></div>
                            </div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="moveMain">
                                <div id="upComp">
                                    <a href="addDopCompany.jsp?id=<%=id%>&owner=<%=owner%>">Назад к структуре компании</a>
                                </div>
                                <jsp:include page="company" flush="false" />
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="list"><%=list%></div>
        <script type="text/javascript">
            AUI().ready('aui-node', 'aui-io', function (A) {
                A.Node.all("#list div").each(function () {
                    var str = this.getAttribute("id");
                    var selector = "#moveMain [id=" + str.substr(3, 50) + "]";
                    if (A.one(selector))
                        A.one(selector).next().prepend(this);
                });
                A.Node.all(".boss").each(function () {
                    var par = this.get("parentNode");
                    par.prepend(this);
                });

                function dropRegistrat() {
                    dropAr = "";
                    A.all("#dopusks .emps").each(function () {
                        this.on("click", function () {
                            if (confirm("Удалить?"))
                                empsDrop(this)
                        })
                    });
                    A.one('#dopusks').setStyle('height', A.one('#dopusks').getComputedStyle('height'));
                }
                function empsDrop(node) {
                    node.purge().remove();
                    dropAr = "";
                    A.all("#dopusks .emps").each(function () {
                        if (dropAr === "")
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