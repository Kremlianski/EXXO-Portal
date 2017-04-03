<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    boolean editable = false;
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") >= 0 || role.indexOf("j") >= 0) {
        editable = true;
    }
    if (!editable) {
        response.sendRedirect("notPermited.html");
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Редактирование списка опросов</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            #content table{width:100%;background-color: white;border-left: 1px solid #DEDEDE;border-right: 1px solid #DEDEDE;}
            .first {padding-left: 10px;}
            .exxo-date {width: 100px; padding-left: 10px;}
            .second{width:150px;padding-left:0px;}
            .third{width:150px;padding-left:0px;}
            th {padding:5px;}
            #content table td{border-bottom-color: #DEDEDE;border-bottom-style: solid;border-bottom-width: 1px;}
            .hover {background-color: #ffae40;cursor:pointer;}
            #topik {width:100%;text-align: right;margin-bottom: 30px;}
            #topik a{ text-decoration: none;margin-right: 30px;}
            h1{font-size: 16px; font-weight: normal;text-align: left; margin-left: 10px} 
            #content table tr:nth-of-type(even) {background-color: #F5F2F2;}  
            .third a,.second a {text-decoration: none; display: block;   padding: 5px; padding-left: 30px;}
            .edit{background-image: url('edit.png'); background-position: 5px 5px; background-repeat: no-repeat;}
            .drop{background-image: url('drop.png'); background-position: 5px 6px; background-repeat: no-repeat;}
            a#addgroup{padding-left: 22px;background-image: url('add.png'); background-position: center left; background-repeat: no-repeat; text-decoration: none;}
            a#addgroup:hover {color: #972626;text-decoration: underline;}
            .third a:hover,.second a:hover {background-color:#ffc473;color: black;}
            #container {position: relative;margin: 10px;margin-top: 0px;width: 90%;}
            #content {font-size: 12px;margin-top: 0px;}
            #content table tr:hover a {color: #972626;}
            #content table a {text-decoration: none;}
            #content table a:hover {text-decoration: underline;}
            #docStat {margin: 25px;  padding:20px; padding-top: 10px;position: absolute;z-index: 99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white;  display: none;overflow: auto; }
            .close {color:red;width:100%;text-align:right;}
            .close span {cursor: pointer;}
            #f {background-color:white; padding:10px; padding-top: 20px; width: 450px; display: block;}
            #f input {width: 250px;} 
            #f td {padding: 20px 0px;}
            #f table {margin-bottom: 20px; width:100%;}
            #f td.one {text-align: right; padding-right: 10px; width: 150px; font-size: 12px; color: gray;}
            #f td.two {text-align: right; padding-left: 10px;}
            #f input[type=submit] {width: 150px; padding: 4px; margin: 0px auto; display: block;}
            #f select {width: 250px;} 
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("qwestList.jsp", request)%>
        </style>
    </head>
    <body class="yui3-skin-sam" id="qwestList_jsp">
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
                                    <jsp:include page="topMenu" flush="true" />
                                    <a href="votes.jsp" class="footmenu" id="finish">Закончить</a>
                                    <a href="http://exxo.ru/howto/vote-howto.html" class="footmenu howto" target="_blank">?</a>
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
                            <div class="menu" id="menu"></div>
                            <div id="menuList"><a href="settingQwest.jsp" id="addgroup">Добавить опрос</a></div>
                        </td>
                        <td id="column-3" valign="top">
                            <jsp:include page="qwestList.xhtml" flush="true" />
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div><div id="menus" class="yui3-menu">
            <jsp:include page="menu.jsp" flush="true" />
        </div>
        <div id="docStat"><div class="close" id="closes"><span>ЗАКРЫТЬ</span></div><div id="docstatus"></div></div>
        <script type="text/javascript" src="scripts/mainMenu.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script> 
    </body>
</html>