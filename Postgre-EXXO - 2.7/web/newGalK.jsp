<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="NGK" class="BEANS.NewGalK" scope="page"/><%
    String list = NGK.getList(request);
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Новые фотоальбомы компании</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {position: relative;margin:0px 10px;height: auto;margin-bottom: 20px;background-color: white;padding: 0; width: 95%;
                      border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px; border-style: solid;overflow: visible;}
            #picTable {width: 100%; }
            #picTable td {padding-right: 10px; padding-bottom: 5px; padding-top: 5px; font-size: 12px;}
            #picTable td a {text-decoration: none; text-align: left;}
            #picTable tr { border-bottom: 1px solid #dedede;}
            #picTable tr:last-of-type {border:0}
            #picTable tr:nth-of-type(odd) td{background-color: #F5F2F2;}
            td.name {width: 250px; padding-left: 20px;  padding-right: 20px; font-size: 14px;}
            #picTable td.created {font-size: 11px;color: #3B3B8F;width: 100px;padding-right: 20px; padding-left: 40px;}
            td.ico {background-position: center center; background-repeat: no-repeat; width: 60px; height: 60px;}
            #picTable th {border:0; text-align: center; color: #3C3D58; height: 50px;;background-color: #E2E4EE; border-bottom: 1px solid #bfbfbf;}
            #picTable tr:hover td{background-color: #E3E2FD;}
            #picTable tr:hover a {color:#972626;}
            #picTable a:hover {text-decoration: underline;}
            #menu-navi {width: 95%; margin-left:10px; height: auto;}
            #menu-navi table {width:100%;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi a {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi a:hover {background-color: white;}
            a#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            a#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            a#backward:hover {background-image: url('small/backward.png'); }
            a#forward:hover {background-image: url('small/forward.png');}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("newGalK.jsp", request)%>
        </style>
    </head>
    <body class="yui-skin-sam" id="newGalK_jsp">
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
                                        <li> <a href="http://exxo.ru/howto/gallery-howto.html#find" class="footmenu howto" target="_blank">?</a></li>
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
                                <jsp:include page="menu-pic.xhtml" flush="true" />
                            </div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow exxo-images">
                                <%=list%>
                            </div>
                            <%= NGK.navi%>
                        </td>
                    </tr>
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <script type="text/javascript">
            AUI().ready('imageloader', function (A) {
                new A.ImgLoadGroup({foldDistance: 25, className: 'exxo-loading'});
                var myHeight = A.DOM.winHeight() - 300;
                if (myHeight < 300)
                    myHeight = 300;
                A.one("#content").setStyle("minHeight", myHeight + "px");
            });
        </script>
        <script type="text/javascript" src="resources/scripts/top-menu-fixed.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>