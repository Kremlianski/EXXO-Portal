<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="OP" class="BEANS.OperationCard" scope="page"/><%
    String user = (String) session.getAttribute("id");
    String role = (String) session.getAttribute("role");
    String id = request.getParameter("id");
    OP.setId(id, user, role, request);

    String name = OP.name;
    String owner = OP.owner;
    String fio = OP.fio;
    String position = OP.position;
    String unit = OP.unit;
    String classLate = OP.classLate;
    String STATUS = OP.STATUS;
    String nextTime = OP.nextTime;
    String buttons = OP.buttons;
    String TimeEnter = OP.TimeEnter;
    String text = OP.text;
    String why = OP.why;
    String WHY = OP.WHY;
    String comment = OP.comment;
    Boolean R = OP.R;
    Boolean DoneIsDone = OP.DoneIsDone;
    String customer = OP.customer;
    boolean decide = OP.decide;
    boolean answer = OP.answer;
    int status = OP.status;

    if (R) {
        response.sendRedirect("notPermited.html");
    }
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Операция <%=id%></title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="alloy/build/aui-skins/core/css/main.css" type="text/css" >
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css" >
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {width:575px; margin-left: 20px; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid; }
            table.table {background-color: white;}
            .table a {color: #5B677D;text-decoration: none;}
            .table a:hover {text-decoration: underline;}
            label {font-weight: bolder;font-size:12px;}
            .table td.left {text-align:right;padding:10px;padding-right:20px; width: 110px;}
            .table td.right {text-align:left; padding: 10px 5px;}
            .table tr:first-of-type td.right {vertical-align: top; padding: 5px;}
            .table tr {border-bottom: 1px solid #dfdfdf;}
            #img{width:110px;height:147px;background-color:gray;margin:3px;float: right;}
            #img img {width:110px;height:147px;}
            .maininfo{font-size: 14px; margin-top: 10px;}
            #birthday {font-size: 12px;}
            #submenu {font-size: 13px;}
            #submenu a {margin-right: 10px;}
            table.table {font-size: 12px;}
            td.left {color: gray;}
            div#birthday {color:#5F6186;margin-top: 20px;}
            #position {margin-top: 8px; font-size: 12px; font-style: italic;}
            #company {margin-top: 8px;}
            #inspector {text-align: right;padding-right: 20px; font-size:  10px;}
            #description {font-size: 11px; font-weight: bold;}
            #docs {}
            #blogs {}
            #gals {}
            #servs a {}
            table.table tr:hover a {color:#972626;}
            #close,#closer{color:red;width:100%;text-align:right;}
            .late {color: red;}
            #decision{margin-top: 10px;}
            #ans {background-color: #f6e4b2;}
            #ans div {font-size: 14px;}
            #start {height: 30px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("operationCard.jsp", request)%>
        </style>
    </head>
    <body id="operationCard_jsp">
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
                                    <ul class="topMenu">
                                        <jsp:include page="topMenu.xhtml" flush="true" />
                                        <li> <a href="http://exxo.ru/howto/service-howto.html#control" class="footmenu howto" target="_blank">?</a></li>
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
                                <jsp:include page="menu-serv.xhtml" flush="true" /></div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow">
                                <table class="table">
                                    <tbody>
                                        <tr>
                                            <td class="left">
                                                <div id="img"><img src="IMG?id=<%=customer%>"  class="exxo-shadow1"></div>
                                            </td>
                                            <td class="right">
                                                <div class="maininfo"><strong><%=name%></strong></div>
                                                <div class="maininfo fio"><a href="empCard.jsp?id=<%=customer%>"><%=fio%></a></div>
                                                <div class="maininfo blue" id="position"><%=position%></div>
                                                <div class="maininfo blue" id="company"> <%=unit%> </div>
                                                <div class="maininfo<%=classLate%>" > <%=STATUS%> </div>
                                                <div class="maininfo<%=classLate%>" > <%=nextTime%> </div>
                                                <div class="maininfo" id="start"><%=buttons%></div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div id="jornal" >
                                    <div class="exxo-date">
                                        <%= TimeEnter%>
                                    </div>
                                    <%=text%> 
                                </div>
                                <%if (why != null && !why.equals("")) {
                                        out.print("<div id='ans'><div>" + WHY + "</div>" + why + "</div>");
                                    }%>
                                <%if (comment != null && !comment.equals("")) {
                                        out.print("<div id='ans'><div>Оценка выполнения:</div>" + comment + "</div>");
                                    }%>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="pool">
            <div id="close">
                <span>ЗАКРЫТЬ</span>
            </div>
            <form action="decision" method="POST">
                <input type="hidden" name="id" value="<%= id%>">
                <input type="hidden" name="res" value="<% if (answer) {
                        out.print("5");
                    } else {
                        out.print("3");
                    }%>">
                <label id="why"><%= WHY%></label><br>
                <textarea cols="50" rows="10" name="text" class="exxo-textarea"></textarea>
                <br>
                <input type="submit" value="Отправить">
            </form>
        </div>
        <script  type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', 'aui-button-item', 'aui-toolbar', function (A) {
                var overlayMask = new A.OverlayMask().render();
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
                var div = A.one('#pool');
                div.setStyle('opacity', '0');
                div.plug(A.Plugin.NodeFX, conf);
                function negative() {
                    div.fx.run();
                }
                A.one('#close span').on('click', function (event) {
                    closer(A.one('#pool'));
                });
                function closer(node) {
                    node.fx.run();
                }
            <%if (decide && owner.equals(user)) {%>
                new A.Toolbar({activeState: true, children: [
                        {label: 'Да', icon: 'plus', handler: {fn: function (e) {
                                    location = "decision?res=2&id=<%=id%>"
                                }}},
                        {label: 'Нет', icon: 'minus', handler: {fn: function (e) {
                                    negative()
                                }}}]}).render(A.one('#decision'));
            <%} else if (answer && status == 1 && owner.equals(user)) {%>
                new A.ButtonItem({icon: 'check', label: 'Ответить', handler: {fn: function (e) {
                            A.one('#pool').fx.run();
                        }}}).render(A.one("#start"));
            <%} else if (DoneIsDone && owner.equals(user)) {%>
                new A.ButtonItem({icon: 'check', label: 'Закрыть вопрос', handler: {fn: function (e) {
                            location = "decision?res=4&id=<%=id%>";
                        }}}).render(A.one("#start"));
            <%}%>
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>