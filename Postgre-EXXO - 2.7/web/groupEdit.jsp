<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    boolean editable = false;
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") >= 0 || role.indexOf("n") >= 0) {
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
        <title>Документы: редактирование</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content table{width:100%;background-color: white;border-left: 1px solid #DEDEDE;border-right: 1px solid #DEDEDE;}
            .first {width:60%;padding-left: 10px;}
            .second{width:20%;padding-left:10px;}
            .third{width:20%;padding-left:10px;}
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
            .boss{background-image: url('check.png'); background-position: 5px 5px; background-repeat: no-repeat;}
            .drop{background-image: url('drop.png'); background-position: 5px 6px; background-repeat: no-repeat;}
            #docStat {position: absolute;background-color: transparent;  width: 100%; height: auto; z-index: 99999999;display:none; left:0px; top: 0px; }
            #imgesInner {position:relative; width:350px; height:auto; margin: 20px auto; background-color: white; border: 1px solid #bfbfbf; border-radius: 5px;}
            .close {color:red;width:auto;text-align:right; padding: 5px; padding-right: 10px;}
            .close span {cursor: pointer;}
            #f {background-color:white; padding:10px; padding-top: 20px; display: block;}
            #f input {width: 250px;} 
            #f td {padding: 20px 0px;}
            #f table {margin-bottom: 20px; width:100%;}
            #f td.one {text-align: right; padding-right: 10px; width: 150px; font-size: 12px; color: gray;}
            #f td.two {text-align: center; padding-left: 10px;}
            #f input[type=submit] {width: 150px; padding: 4px; margin: 0px auto; display: block; cursor: pointer;}
            #f select {width: 250px;} 
        </style>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("groupEdit.jsp", request)%></style>
    </head>
    <body id="groupEdit_jsp">
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
                                        <li><a href="javascript:;" class="footmenu" id="finish">Закончить</a></li>
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
                                <jsp:include page="menu-emp.xhtml" flush="true" /></div>
                            <div id="menuList"><a href="javascript:;" id="addgroup">Добавить участника</a></div>
                        </td>
                        <td id="column-3" valign="top">
                            <jsp:include page="groupEdit.xhtml" flush="true" />    
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="docStat"><div id="imgesInner"><div class="close" id="closes"><span>ЗАКРЫТЬ</span></div><div id="docstatus"></div></div></div>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>