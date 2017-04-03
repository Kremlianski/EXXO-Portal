<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="DESK" class="BEANS.DeskBean" scope="page"/><%
    String fio = (String) session.getAttribute("fio");
    String list = DESK.getList(request);
    String slogan = SLOGAN.getSlogan(request);
    String of = request.getParameter("of");
    if (of == null) {
        of = "0";
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Доска объявлений</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            #content {width:700px;height:auto;position: relative;padding:0px;margin:0px 10px;overflow-x: hidden;overflow-y: auto;}
            .hoverPlus {color  :red;}
            #editor {margin: 25px;padding:20px;position: absolute;z-index: 99998;top:20px;left:10%;width: 650px;color: black;background-color: white;
                     display: none;overflow: auto;}
            .close {color:red;width:100%;text-align:right; font-size: 12px; margin-bottom: 10px;}
            .text {font-size:12px;padding-bottom: 10px;text-align: left; padding-top: 10px;}
            .timer {font-size: 12px;text-align: left; padding-left: 20px; margin-bottom: 10px;}
            .HBlog{text-align: right;}
            .desc {margin-bottom: 20px;background-color: white;padding: 10px;padding-top: 5px;padding-bottom: 5px;clear: both;background-color: white;}
            .B {padding-left: 10px; text-decoration: none;}
            .desc:hover .B, .desc:hover .timer a {color: #972626;}
            .B:hover, .timer a:hover {text-decoration: underline;}
            #plus, #plusik {font-size: 12px;text-align: left; margin: 10px; margin-left: 15px;}
            #pl {padding-left: 5px; text-decoration: none; cursor: pointer;}
            #pl:hover {color: #972626; text-decoration: underline;}
            .timer a {margin-right: 20px;text-decoration: none;}
            .text {padding-left: 20px;min-height: 60px; border: 1px solid #dedede; border-radius: 5px; position: absolute; background-color: #FDF2F2; width: 560px; padding-right: 10px;}
            .desc {font-size: 12px; border: 1px solid #dedede; border-bottom: 1px solid #bfbfbf;}
            input#submit{margin: 20px auto; display: block; width: 110px; padding: 5px;}
            #editor textarea {width: 600px;}
            td.imgTd {padding: 4px; width: 75px; }
            td.imgTd img {width: 75px;height: 100px;}
            .desc table {width:100%;}
            .desc table td {vertical-align: top;}
            .text::before {content: ' ';height: 0;position: absolute;width: 0;border: 8px solid transparent;border-right-color: #dedede;right: 100%;top: 20px;}
            .text::after {content: ' ';height: 0;position: absolute;width: 0;border: 6px solid transparent;border-right-color: #FDF2F2;right: 100%;top: 22px;}
            #count {height: 20px; font-size: 16px; color: gray;}
            #menu-navi {width: 90%; margin-left:10px; height: auto;}
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
            <%=CSS3.getCSS("desk.jsp", request)%>
        </style>
    </head>
    <body class="yui3-skin-sam" id="desk_jsp">
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
                                    <jsp:include page="topMenu" flush="true" /> 
                                    <a href="http://exxo.ru/howto/board-howto.html" class="footmenu howto" target="_blank">?</a>
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
                            <div id="plus">
                                <span class="aui-button-icon aui-icon aui-icon-plusthick"></span><a id="pl">Добавить запись</a>
                            </div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content">
                                <%=list%>
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
        <div id="editor"> 
            <div class="close" id="close"><span>ЗАКРЫТЬ</span></div>
            <div id="count"></div>
            <form action="desk" method="post" id="forma" class="exxo-form">
                <input type="hidden" name="of" value="<%=of%>">
                <textarea name="msgpost" id="msgpost" cols="50" rows="10"></textarea>
                <input type="hidden" name="fio" value="<%= fio%>">
                <br>
                <input type="submit" id='submit' value="Сохранить">
            </form>
        </div>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', 'aui-io-request', function (A) {
                var MAXLENGTH = 255;
                var overlayMask = new A.OverlayMask().render();
                var myHeight = A.DOM.winHeight() - 150;
                if (myHeight < 300)
                    myHeight = 300;
                var div = A.one('#editor');
                var conf = {
                    from: {opacity: 0},
                    to: {opacity: 1},
                    on: {'start': function () {
                            if (!this.get('reverse')) {
                                overlayMask.set('target', document).show();
                                this.get('node').setStyle('display', 'block');
                            }
                        }},
                    after: {'end': function () {
                            this.set('reverse', !this.get('reverse'));
                            this.stop();
                            if (this.get('node').getStyle('opacity') == 0)
                                this.get('node').setStyle('display', 'none');
                            if (!this.get('reverse'))
                                overlayMask.set('target', document).hide();
                        }}};
                div.setStyle('opacity', '0');
                div.plug(A.Plugin.NodeFX, conf);
                A.one('#close span').on('click', function (event) {
                    div.fx.run();
                });
                A.one("#plus a").on("click", function () {
                    if (A.one('#forma [name=id]') != null)
                        A.one('#forma [name=id]').remove();
                    A.one('#msgpost').text('');
                    A.one('#count').text(MAXLENGTH);
                    A.one('#msgpost').set('value', '');
                    this.removeClass("hoverPlus");
                    overlayMask.set('target', document).show();
                    A.one('#editor').scrollIntoView( );
                    A.one('#editor').fx.run();
                });
                A.all(".edit").each(function () {
                    var node = this;
                    var id = this.get("id");
                    this.on("click", function () {
                        A.one('#editor').fx.run();
                        A.one('#editor').scrollIntoView( );
                        A.one("#forma").insert("<input type='hidden' name='id' value='" + id + "'>");
                        A.one('#msgpost').text(node.get("parentNode").next().one('.text').text());
                        A.one('#msgpost').set('value', node.get("parentNode").next().one('.text').text());
                        newLength(newLength(this));
                    })
                });
                A.one("#content").setStyle("minHeight", myHeight + "px");
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
                //A.one("#count").html(MAXLENGTH);
                A.one("#msgpost").on("keyup", function () {
                    newLength(this)
                });
                function newLength(node) {
                    //var t = node.text();
                    var t = node.get('value');
                    var l = t.length;
                    if (l > MAXLENGTH)
                        node.set('value', t.substring(0, MAXLENGTH));
                    A.one("#count").text(MAXLENGTH - node.get('value').length);
                }
            });
        </script>
        <script type="text/javascript" src="scripts/mainMenu.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script> 
    </body>
</html>