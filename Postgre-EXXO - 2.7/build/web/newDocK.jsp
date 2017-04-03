<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/><jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="NDK" class="BEANS.NewDocK" scope="page"/><%
    String o = (String) session.getAttribute("id");
    String g = (String) session.getAttribute("global_id");
    String list = NDK.getList(request, o, g);
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Новые документы компании</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {width:95%;position: relative;margin:0px 10px;}
            #content table tr {border-top: 1px solid #DEDEDE;}
            #content table {width: 100%;background-color: white; border-left: 1px solid #DEDEDE;
                            border-right: 1px solid #DEDEDE; border-bottom: 1px solid #BFBFBF;}
            #content table tr:nth-of-type(odd) td{background-color: #F5F2F2;}
            .fil {padding-left: 20px;
                  font-size: 11px;width: 30%;}
            .fil a{font-size: 12px;margin-right: 10px;text-decoration: none;}
            .fio a{font-size: 11px;text-decoration: none;}
            .descr {padding-left:  20px; font-size:11px;}
            #content table tr:hover td{background-color: #E3E2FD;}
            #content td {padding: 4px 10px; text-align: left;}
            #column-3 #content {border: 0;}
            .created {font-size: 10px; width: 70px; color: #3B3B8F;}
            a.nopend {font-weight: bold;}
            #content a {text-decoration: none;}
            #content tr:hover a {color:#972626;}
            #content a:hover {text-decoration: underline;}
            #content a.blocked {text-decoration: line-through;}
            #lMenu {margin-top: 20px;width: 170px;background-color: #FDF2F2;padding: 0;border-left: 1px solid #DEDEDE;border-right: 1px solid #DEDEDE; border-bottom: 1px solid #BFBFBF; margin-left:10px;}
            #lMenu a{display: block; padding: 6px; text-decoration: none; border-top:1px solid #DEDEDE; font-size: 12px; color:#22225A;}
            #lMenu a:hover {background-color: #E3E2FD;}
            a.own {background-color: #E9D7D6}
            a#addgroup{padding:4px;padding-left: 22px;background-image: url('small/application_view_icons.png'); background-position: center left; background-repeat: no-repeat; text-decoration: none;}
            a#addgroup:hover {color: #972626;text-decoration: underline;}
            #menu-navi {width: 95%; margin-left:10px; height: auto;}
            #menu-navi table {width:100%;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi a {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi a:hover {background-color: white;}
            a#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            a#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            a#backward:hover {background-image: url('small/backward.png'); }
            a#forward:hover {background-image: url('small/forward.png');}
            #content td.dop {width: 16px; padding: 4px}
            .fio{width: 160px;}
            #content td.first {width:16px; padding: 4px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("newDocK.jsp", request)%>
        </style>
    </head>
    <body id="newDocK_jsp">
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
                                                <td id="slogan" valign="center">
                                                    <%= slogan%>
                                                </td>
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
                                            <a href="http://exxo.ru/howto/documents-howto.html#find" class="footmenu howto" target="_blank">?</a>
                                        </li>
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
                        <td id="menu-td" class="menu-td" valign="top"><div class="exxo-menu exxo-shadow" id="menu"><jsp:include page="menu-doc.xhtml" flush="true" /></div><%=NDK.lMenu%></td>
                        <td id="column-3" valign="top"><div id="content" class="exxo-shadow"><%=list%></div><%=NDK.navi%></td></tr></tbody></table><jsp:include page="FOOTER" flush="true" /></div>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body></html>