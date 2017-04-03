<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="L" class="BEANS.LogoInsBean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") < 0 && role.indexOf("b") < 0) {
        response.sendRedirect("notPermited.html");
    }

    L.setParams(request);
    String slogan1 = L.slogan;
    String photoMarker = L.photoMarker;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Логотип и слоган</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            #content {position: relative;margin-left: 10px; width: 670px;height: auto;margin-bottom: 10px;background-color: white;padding: 0px; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE; border-width: 1px; border-style: solid;}   
            #content  table {background-color: white;width: 100%;margin:0px;}
            #content label {font-weight: bolder;font-size:12px;}
            #content td.left {text-align:right;padding:10px;padding-right:20px; width: 200px;}
            #content td.right {text-align:left;}
            #content tr {border-bottom: 1px solid #dfdfdf;}
            #submit-button{text-align:center;}
            #submit-button input{width: 200px;}
            #ok{margin:15px;}
            #img{width:153px;height:100px;background-color:gray;margin: 20px 3px;}
            #img img {width:153px;height:100px; cursor: pointer;}
            #img-form {position:absolute;z-index:99999;width:30%;top:20%;left:30%;background-color:white;display:none;padding:10px;}
            #close,#closer{color:red;width:100%;text-align:right; cursor: pointer;}
            #forma{margin:10px;margin-top: 15px;text-align:center;}
            #content h4 {text-align: center; font-size: 12px; margin-top: 40px; margin-bottom: 20px;}
            #imgform input[type=submit] {padding:5px;} 
            #content input[type=text] {margin: 20px 3px; width: 250px;} 

        </style>
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("logoIns.jsp", request)%></style>
    </head>
    <body class="yui3-skin-sam" id="logoIns_jsp">
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
                                    <a href="main.jsp" class="footmenu">Закончить</a> 
                                    <a href="http://exxo.ru/howto/cms-howto.html#logos" class="footmenu howto" target="_blank">?</a>
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
                            <div class="menu" id="menu"></div><
                            /td>
                        <td id="column-3" valign="top">
                            <div id="content"  class="exxo-shadow">
                                <form method="POST" id="form" name="form" action="sloganIns">
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td class="left"><label>Логотип</label></td>
                                                <td class="right">
                                                    <div id="img"><% if (photoMarker != null) { %> <img src="LOGO" > <% } %></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="left"><label>Слоган</label></td>
                                                <td class="right"><input name="slogan" type="text" class="formEl" <%if (slogan1 != null) {%> value="<%=slogan1%>" <% } %>></td></tr></tbody></table>
                                    <div id="submit-button">
                                        <input type="submit" value="Сохранить слоган" id="ok">
                                    </div>
                                </form>
                            </div>
                            </div>
                            <div id="img-form">
                                <div id="close">
                                    <span>ЗАКРЫТЬ</span>
                                </div>
                                <div id="forma"><form action="logoIns" enctype="multipart/form-data" method="POST" id="imgform">
                                        <input type="file" name="file" id="file"><br><br><br>
                                        <input type="submit" value="Загрузить">
                                    </form>
                                </div> 

                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="menus" class="yui3-menu"><jsp:include page="menu.jsp" flush="true" />
        </div>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/mainMenu.js"></script>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', function (A) {
                A.one('#content-wrapper').setStyle('minHeight', A.DOM.winHeight() + 'px');
                var overlayMask = new A.OverlayMask().render();
                function setAnimation(node, triger, closer) {
                    node.setStyle('opacity', '0');
                    var anim = new A.Anim({node: node, to: {opacity: 1}, on: {'start': function () {
                                A.one(node).setStyle('display', 'block');
                            }}});
                    var anim1 = new A.Anim({node: node, to: {opacity: 0}, on: {'end': function () {
                                A.one(node).setStyle('display', 'none');
                                overlayMask.hide();
                            }}});
                    triger.on('click', function () {
                        overlayMask.set('target', document).show();
                        anim.run();
                    });
                    closer.on('click', function () {
                        anim1.run();
                    });
                }
                setAnimation(A.one('#img-form'), A.one('#img'), A.one('#close span'));
            <% if (request.getParameter("message") != null) { %> alert("Размер изображения превышает 10Mb");<%}%>
            });
        </script>
    </body>
</html>