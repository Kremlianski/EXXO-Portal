<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String owner = request.getParameter("owner");
    String id = request.getParameter("id");
    boolean editable = false;
    String role = (String) session.getAttribute("role");
    if (owner == null || (role.indexOf("o") >= 0 && owner.equals("-100")) || (role.indexOf("p") >= 0 && owner.equals("-101"))
            || (role.indexOf("q") >= 0 && owner.equals("-102")) || (role.indexOf("r") >= 0 && owner.equals("-103"))) {
        editable = true;
    }
    if (!editable) {
        response.sendRedirect("notPermited.html");
    }
    String data = "";
    if (owner != null) {
        data = ", data: {owner: \"" + owner + "\"}";
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Свойства файла</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #c {background-color: white; width: 100%;}
            #c td { vertical-align: top;}
            .grid input[type=text] {width: 250px;} 
            input[type=submit] {padding: 4px;}
            textarea {width: 250px; overflow: auto;}
            #tagH {font-size: 12px; margin-left: 10px;}
            #tags {padding: 10px;}
            .tag {padding:3px; display: inline-block; background-color: white; border: 1px solid #bfbfbf;margin:3px;color:black;-moz-border-radius: 4px;
                  -webkit-border-radius: 4px;border-radius: 4px; padding-left: 20px; background-image: url('img/tag.png');background-position: 2px center;background-repeat: no-repeat;}
            #tagChange {margin-top: 10px; padding: 10px; text-align: left; clear: both;}
            #tops {text-align: left; font-size: 12px; margin: 20px;}
            #content {width: 575px;margin-left: 10px;border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;background-color: white;}
            .hidden {display: none;}
            .emps:hover {cursor: pointer; color:red;}
            .emps {color: #2F2F70; display: inline-block; margin-top: 5px; margin-right: 5px; margin-bottom: 5px; border: 1px solid #dedede; border-radius: 5px; padding: 4px;}
            #dt, .invis {padding: 3px;}
            .table tr {border-bottom: 1px solid #DFDFDF;}
            #c td.first {color: gray;width: 160px;text-align: right;padding: 10px;padding-right: 20px; vertical-align: middle;}
            td.second {padding: 5px;}
            table.table tr:hover a {color:#972626;}
            .table a {color: #5B677D;text-decoration: none;}
            .table a:hover {text-decoration: underline;}
            td.second input[type=text]:hover {}
            #content textarea:hover {}
            td.second input[type=text] {width: 340px;  color: #363672; padding: 2px;}
            td.second {padding: 5px;}
            #content textarea {width: 340px; overflow: auto; height: 100px;color: #363672; padding: 2px;} 
            label {font-weight: normal;font-size:12px;}
            #content select {width: 340px;}
            #empMenu button {height: 24px; padding-top: 5px; min-width: 26px;}
            #empMenu {margin-top: 10px; margin-bottom: 20px;}
            #empsDiv .emps {moz-box-shadow: 0px 4px 15px 0 rgba(0,0,0,0.4);-webkit-box-shadow: 0px 4px 15px 0 rgba(0,0,0,0.4);-o-box-shadow: 0px 4px 15px 0 rgba(0,0,0,0.4);box-shadow: 0px 4px 15px 0 rgba(0,0,0,0.4);}
        </style>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("fileProp.jsp", request)%></style>
    </head>
    <body id="fileProp_jsp">
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
                                        <li><a href="javascript:;" id="finish" class="footmenu">Закончить</a></li>
                                        <li> <a href="http://exxo.ru/howto/documents-howto.html#docprop" class="footmenu howto" target="_blank">?</a></li>
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
                        </td>
                        <td id="column-3" valign="top">
                            <jsp:include page="fileProp.xhtml" flush="true" />
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>