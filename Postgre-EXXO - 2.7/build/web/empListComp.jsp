<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="ELC" class="BEANS.EmpListComp" scope="page"/><%
    String list = ELC.getList(request);
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
        <title>Список по подразделениям</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
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
            .emps:hover .fio a {color:#972626;}
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
            #moveMain .item {margin-bottom: 0px; color: #3C3D58; background-color: #E2E4EE; background-image: none; margin-top: 20px; border:1px solid #D4D7EB;box-sizing: border-box;-moz-box-sizing: border-box;}
            .inno {padding-bottom: 10px;padding-left: 20px;padding-right: 10px;border-left: 1px solid #D4D7EB;border-bottom: 1px solid #BFBFBF;border-right: 1px solid #D4D7EB;}
            #moveMain > div.item {margin: 0px;}
            .inno div.boss .fio {font-weight: bolder;}
            div.emps {width: 270px; display: inline-block; height: 120px; margin-right: 10px; padding-top: 0px; margin-top: 10px; vertical-align: top; background-color: #F3F3F3; border-top: 10px solid white;}
            div.emps img {width: 60px; height:80px; margin-top: 10px;}
            div.emps span {display: block; width: auto;}
            .position {font-style: italic;}
            span.imgE {float: left; margin:0; margin-right: 10px; padding: 0; padding-left: 6px; width: 60px !important;; height: 120px; display: block;}
            span.texts {padding-right: 10px; height: 110px; display: block; padding-top: 10px;}
            .loading {background:none !important; visibility: hidden !important;}
            span.imgE {background-size: 60px 80px; background-repeat: no-repeat; background-position: 5px 10px;}
        </style><style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("empListComp.jsp", request)%></style>
    </head>
    <body id="empListComp_jsp">
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
                                                <td><jsp:include page="poisk" flush="true" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div id="foot">
                                    <ul class="topMenu">
                                        <jsp:include page="topMenu.xhtml" flush="true" />
                                        <li>
                                            <a href="http://localhost/EXXO/companyMod.jsp" class="footmenu">Настройка</a>
                                        </li>
                                        <li> <a href="http://exxo.ru/howto/staff-howto.html#find" class="footmenu howto" target="_blank">?</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <table id="grid" class="grid">
                <tbody id="atbody"><tr id="tr">
                        <td id="menu-td" class="menu-td" valign="top">
                            <div class="exxo-menu exxo-shadow" id="menu">
                                <jsp:include page="menu-emp.xhtml" flush="true" />
                            </div>
                        </td>
                        <td id="column-3" valign="top"> 
                            <div id="moveMain"><jsp:include page="company" flush="false" /></div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="list">
            <%=list%>
        </div>
        <script  type="text/javascript">
            AUI().ready('aui-node', 'imageloader', function (A) {
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
                A.all("#moveMain .item").each(function () {
                    this.on("click", function () {
                        location = "unit.jsp?id=" + this.getAttribute("id");
                    });
                });
                A.all('.imgE').each(function () {
                    var node = this;
                    node.addClass('loading');
                });
                new A.ImgLoadGroup({foldDistance: 25, className: 'loading'});
            });
        </script>
        <script type="text/javascript" src="resources/scripts/top-menu-fixed.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>