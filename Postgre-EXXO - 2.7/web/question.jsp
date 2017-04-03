<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="T" class="BEANS.questionBean" scope="page"/><%
    String list = T.getList(request.getParameter("id"), request);
    String slogan = SLOGAN.getSlogan(request);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Вопрос для голосования.</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            #voteForm {width:50%; background-color: white; margin-left: 20px; padding: 10px; padding-left: 50px; border: 1px solid #dedede; border-bottom: 1px solid #bfbfbf;}
            #variants {margin-bottom: 40px; padding: 5px; font-size: 14px; margin-top: 20px;}
            #variants div {margin-bottom: 10px;}
            #voteForm input[type=submit] {padding: 5px; width: 110px; margin: 20px auto; display: block;}
            #voteForm h2 {text-align: center; margin-bottom: 20px;}
            #variants span {padding-left: 10px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("question.jsp", request)%>
        </style>
    </head>
    <body class="yui3-skin-sam" id="question_jsp">
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
                                    <a href="qwestList.jsp" class="footmenu">Настройка</a>
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
                        </td>
                        <td id="column-3" valign="top">
                            <div id="container">
                                <div id="content">
                                    <%=list%>
                                </div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="menus" class="yui3-menu">
            <jsp:include page="menu.jsp" flush="true" />
        </div>
        <script type="text/javascript" src="scripts/mainMenu.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script> 
    </body>
</html>