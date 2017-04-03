<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="BLOG" class="BEANS.NewBlogs" scope="page"/><%
    String owner = (String) session.getAttribute("id");
    String guser = (String) session.getAttribute("global_id");
    String role = (String) session.getAttribute("role");
    boolean inspector = false;
    if (role.indexOf("s") >= 0 || role.indexOf("t") >= 0) {
        inspector = true;
    }
    String t = "all";
    if (request.getParameter("t") != null) {
        t = request.getParameter("t");
    }
    String list = BLOG.getList(request, owner, t, guser);
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Новые сообщения</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {width:90%;height:auto;position: relative;margin:0px 10px;overflow-x: hidden;overflow-y: auto; background-color:#f0f0ff;border: 0px;}
            #listTable td {padding: 5px;padding-left: 20px;padding-right: 20px; text-align: left;}
            #listTable  a {font-size:14px; text-decoration: none; color:black;}
            #listTable  a.bus {color:#0F1F99;}
            #listTable {}
            #listTable .tdTime {width:120px;}
            .time { font-size:11px;}
            .type_0, .type_1, .type_2, .type_3, .type_4 {width: 16px; border-right: 0; background-image: url("small/lock_unlock.png"); background-repeat: no-repeat; background-position: center;}
            .type_3 {background-image: url("small/user_business_boss.png")}
            .type_1 {background-image: url("small/flag_blue.png")}
            .type_2 {background-image: url("small/group.png")}
            .type_4 {background-image: url("small/envelope.png")}
            #topBloges {margin: 20px;}
            #topBloges a {text-decoration: none;}
            #topBloges a:hover {color:#972626; text-decoration: underline;}
            #content table tr {border-top: 1px solid #DEDEDE;}
            #content table {width: 100%;background-color: white; border-left: 1px solid #DEDEDE;
                            border-right: 1px solid #DEDEDE; border-bottom: 1px solid #BFBFBF;}
            #content table tr:nth-of-type(odd) td{background-color: #F5F2F2;}
            #content a {text-decoration: none;}
            #content tr:hover a {color:#972626;}
            #content a:hover {text-decoration: underline;}
            #content a.blocked {text-decoration: line-through;}
            #content table tr:hover td{background-color: #E3E2FD;}
            #content td {padding: 4px 10px; text-align: left;}
            #column-3 #content {border: 0;}
            .tdTime {font-size: 10px; width: 70px; color: #3B3B8F;}
            .aui-icon-dgeneral {background-image: url('small/lock_unlock.png');}
            .aui-icon-dproject {background-image: url('small/flag_blue.png');}
            .aui-icon-dgroup {background-image: url('small/group.png');}
            .aui-icon-dunit {background-image: url('small/user_business_boss.png');}
            .aui-icon-dprivat {background-image: url('small/envelope.png');}
            #buttonsDons {margin-left: 10px;}
            #buttonsDons button {height: 24px; padding-top: 5px; min-width: 26px; margin-top: 10px;}
            #buttonsDons .aui-button-label {font-size: 12px;}
            #menu-navi {width: 90%; margin-left:10px; height: auto;}
            #menu-navi table {width:100%;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi a {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi a:hover {background-color: white;}
            a#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            a#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            a#backward:hover {background-image: url('small/backward.png'); }
            a#forward:hover {background-image: url('small/forward.png');}
            .not_opend {font-weight: bold;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("newBlogs.jsp", request)%>
        </style>
    </head>
    <body id="newBlogs_jsp">
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
                                        <li> <a href="http://exxo.ru/howto/mail-howto.html#find" class="footmenu howto" target="_blank">?</a></li>
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
                                <jsp:include page="menu-blog.xhtml" flush="true" />
                                <% if (inspector) { %>
                                <a href="listBlogs.jsp">Модерация</a>
                                <% }%>
                            </div>
                            <div id="buttonsDons"></div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow"> 
                                <%=list%>
                            </div>
                            <%= BLOG.navi%>
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <script type="text/javascript">
            AUI().ready('aui-toolbar', function (A) {
                new A.Toolbar({activeState: true, children: [
                        {label: '', icon: 'check', handler: {fn: function (e) {
                                    location = 'newBlogs.jsp';
                                }}},
                        {label: '', icon: 'dgeneral', handler: {fn: function (e) {
                                    location = 'newBlogs.jsp?t=0';
                                }}},
                        {label: '', icon: 'dproject', handler: {fn: function (e) {
                                    location = 'newBlogs.jsp?t=1';
                                }}}
                    ]}).render(A.one('#buttonsDons'));
                A.one('#buttonsDons').append("<br>");
                new A.Toolbar({activeState: true, children: [
                        {label: '', icon: 'dgroup', handler: {fn: function (e) {
                                    location = 'newBlogs.jsp?t=2';
                                }}},
                        {label: '', icon: 'dunit', handler: {fn: function (e) {
                                    location = 'newBlogs.jsp?t=3';
                                }}},
                        {label: '', icon: 'dprivat', handler: {fn: function (e) {
                                    location = 'newBlogs.jsp?t=4';
                                }}}
                    ]}).render(A.one('#buttonsDons'));
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>