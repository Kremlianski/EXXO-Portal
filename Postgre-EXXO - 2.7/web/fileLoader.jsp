<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="ND" class="BEANS.FileLoader" scope="page"/><%
    String o = (String) session.getAttribute("id");
    String g = (String) session.getAttribute("global_id");
    String id = request.getParameter("id");
    String list = ND.getList(request, o, g);
    String rightMenu = ND.rightMenu;
    String slogan = SLOGAN.getSlogan(request);
    if (ND.redirect) {
        response.sendRedirect("fileLoader.jsp?id=" + id + "&l=1");
    }
    String own = "";
    if (ND.owner.equals("-100") || ND.owner.equals("-101") || ND.owner.equals("-102") || ND.owner.equals("-103")) {
        own = "&owner=" + ND.owner;
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Документ</title>
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
            #content table {width: 575px;background-color: white; border-left: 1px solid #DEDEDE;
                            border-right: 1px solid #DEDEDE; border-bottom: 1px solid #BFBFBF;}
            .fil {font-size: 12px; background-position: 10px 10px !important; padding-left: 36px !important;}
            .fil a{font-size: 12px;margin-right: 10px;text-decoration: none;}
            .fio a{font-size: 12px;text-decoration: none;}
            .descr {padding-left:  20px; font-size:11px;}
            .fio img{width: 45px; height: 60px; float: left; margin-right: 20px;}
            #content table tr:hover a {color:#972626;}
            #content table tr a:hover {text-decoration: underline;}
            #column-3 #content {border: 0;}
            .first {width:160px; padding: 10px; text-align: right; color: gray; padding-right: 20px;}
            .first a, .second a {text-decoration: none;}
            .ico img {border: 1px solid #DEDEDE;}
            .created {font-size: 10px; width: 70px; color: #972626;}
            .aui-icon-dgeneral {background-image: url('small/lock_unlock.png');}
            .aui-icon-dproject {background-image: url('small/flag_blue.png');}
            .aui-icon-dgroup {background-image: url('small/group.png');}
            .aui-icon-dunit {background-image: url('small/user_business_boss.png');}
            .aui-icon-dprivat {background-image: url('small/envelope.png');}
            #buttonsDons button, #buttonsDons1 button {height: 24px; padding-top: 5px; min-width: 26px; width: 150px;}
            #buttonsDons .aui-button-label, #buttonsDons1 .aui-button-label {font-size: 12px;}
            .tags a, .vers a {margin-right: 6px;}
            .visas a {margin-right: 50px;}
            .buts a {margin-right: 20px;}
            .second {padding: 10px 10px; text-align: left;}
            .emps {padding-right: 10px;}
            td.dop img {margin-right: 8px;}
            #form1 {margin: 25px;padding:20px;position: absolute;z-index: 99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white;
                    display: none;overflow: auto;}
            label {display:block;margin-top:10px;}
            .close {color:red;width:auto;text-align:right;}
            .close span {cursor: pointer;}
            #form1 input {width:260px;}
            #FILEFORM1 input[type=submit] {padding: 5px; width: 110px; margin: 10px auto; display: block;}
            #loaders {margin: 25px;  padding:20px; padding-top: 10px;position: absolute;z-index: 99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white;  display: none;overflow: auto; }
            #loaders table, #content table.visaTable  {margin: 20px 0px;border-left: 1px solid #DEDEDE;border-right: 1px solid #DEDEDE; border-bottom: 1px solid #BFBFBF;}
            #loaders td,#content table.visaTable td {padding: 4px 8px; border-top: 1px solid #DEDEDE;font-size: 12px;}
            #loaders tr:nth-of-type(even),#content table.visaTable tr:nth-of-type(even)  {background-color: #F5F2F2;  }
            #loaders div.notOpen {font-size: 12px; margin: 20px;}
            div.blogs {margin-bottom: 5px;}
            div.blogs span.timeB {font-size: 10px; padding-right: 10px;}
            td#column-3 {width: 600px;}
            #rightMenu {font-size: 12px;}
            #rMenu {margin-top: 20px;width: 180px;background-color: #FDF2F2;padding: 0;border-left: 1px solid #DEDEDE;border-right: 1px solid #DEDEDE; border-bottom: 1px solid #BFBFBF; margin-left:10px;}
            #rMenu a{display: block; padding: 6px; text-decoration: none; border-top:1px solid #DEDEDE;}
            #rMenu a:hover {background-color: #E3E2FD;}
            #content table.visaTable {width: 100%;}
            #docStat {margin: 25px;  padding:20px; padding-top: 10px;position: absolute;z-index: 99998;top:20px;left:10%;height: auto;width: auto;color: black;background-color: white;  display: none;overflow: auto; }
            table.list-sign td, table.list-sign th {padding: 4px 20px; border: 0; font-size: 12px;}
            td.signed {text-align: right; padding-right: 50px;}
            #lMenu {margin-top: 20px;width: 168px;background-color: #FDF2F2;padding: 0;border-left: 1px solid #DEDEDE;border-right: 1px solid #DEDEDE; border-bottom: 1px solid #BFBFBF; margin-left:10px;}
            #lMenu a{display: block; padding: 6px; text-decoration: none; border-top:1px solid #DEDEDE; font-size: 12px; color:#22225A;}
            #lMenu a:hover {background-color: #E3E2FD;}
            #moveMain{position: absolute;background-color: transparent;  width: 100%; height: auto; z-index: 99999999;display:none; left:0px; top: 0px;}
            #imgesInner {position:relative;width:auto;height:auto;margin: 4px; background-color:rgb(250,250,250);border: 1px solid #bfbfbf; border-radius: 5px;}
            #closeM {padding: 5px; padding-right: 10px;}
            <%if (!ND.isOwner) {%>#rMenu {border:0;}<% }%>
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
            <style>
                <%=CSS3.getCSS("fileLoader.jsp", request)%>
            </style>
    </head>
    <body class="yui-skin-sam">
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
                                                            <%if (ND.isDocEditor || ND.isOwner) {%>
                                                            <li><a href="fileProp.jsp?id=<%=id%>" class="footmenu">Настройка</a></li>
                                                            <%}%>
                                        <li> <a href="http://exxo.ru/howto/documents-howto.html#card1" class="footmenu howto" target="_blank">?</a></li>
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
                            <%if ((ND.isDocEditor || ND.isOwner) && ND.isImg) {%>
                            <div id='lMenu' class='exxo-shadow'>
                                <a href='javascript:;' id='moveFile' >Добавить в альбом</a>
                            </div>
                            <% }%>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow"><%=list%></div>
                        </td>
                        <td id="rightMenu" valign="top">
                            <div id="rMenu" class="exxo-shadow"><%=rightMenu%></div>
                        </td>
                    </tr>
                </tbody>
            </table>
                            <jsp:include page="FOOTER" flush="true" />
        </div>
        <div id="form1">
            <div class="close" id="close"><span>ЗАКРЫТЬ</span></div>
            <form id="FILEFORM1" action="fileVer" enctype="multipart/form-data" method="POST">
                <label for="file">Выберите файл</label><input type="file" name="file" id="file">
                <input type='hidden' name='id' value='<%=id%>'>
                <input type='hidden' name='r' value='1'>
                <% if (!own.equals("")) {%>
                <input type='hidden' name='owner' value='<%=ND.owner%>'>
                <%}%>
                <input type="submit" value="Сохранить">
            </form>
        </div>
        <div id="loaders"><div class="close" id="close"><span>ЗАКРЫТЬ</span></div><%=ND.loads_list%></div>
        <div id="docStat"><div class="close" id="closes"><span>ЗАКРЫТЬ</span></div><div id="docstatus"></div></div>
        <div id="moveMain">
            <div id="imgesInner">
                <div class="exxo-close" id="closeM"><span>ЗАКРЫТЬ</span></div>
                <div id="Move" class="exxo-images"><%=ND.albums%></div>
            </div>
        </div>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-toolbar', 'aui-overlay-mask', 'aui-io', function (A) {
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
                A.one('#moveMain').setStyle('opacity', 0);
                A.one('#moveMain').plug(A.Plugin.NodeFX, conf);
                A.one('#form1').setStyle('opacity', '0');
                A.one('#form1').plug(A.Plugin.NodeFX, conf);
                A.one('#loaders').setStyle('opacity', '0');
                A.one('#loaders').plug(A.Plugin.NodeFX, conf);
                A.one('#docStat').setStyle('opacity', '0');
                A.one('#docStat').plug(A.Plugin.NodeFX, conf);
                if (A.one('#buttonsDons') != null)
                    new A.Toolbar({activeState: false, children: [
                            {label: 'Открыть', icon: 'mail-open', handler: {fn: function (e) {
                                        location = 'fileLoader?id=<%=ND.V%>&i=<%=id%>&s=1&t=<%=System.currentTimeMillis()%>';
                                    }}},
                            {label: 'Сохранить', icon: 'disk', handler: {fn: function (e) {
                                        location = 'fileLoader?id=<%=ND.V%>&i=<%=id%>&t=<%=System.currentTimeMillis()%>';
                                    }}}
                        ]}).render(A.one('#buttonsDons'));
                var overlayMask = new A.OverlayMask().render();
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
            <%if (ND.isOwners) {%>
                var dropFile = A.one("#dropFile");
                if (dropFile != null) {
                    dropFile.on('click', function () {
                        if (confirm("Хотите удалить файл?"))
                            location = "dropFile?c=<%=ND.cat%>&r=1&p1=<%=id%><%=own%>";
                    });
                }
                var newFile = A.one("#newVer");
                if (newFile != null)
                    newFile.on("click", function () {
                        fileVer();
                    });
                A.one('#form1 #close span').on('click', function (event) {
                    closer(A.one('#form1'));
                });
                function fileVer() {
                    A.one('#form1').fx.run();
                }
            <%}%>
                function closer(node) {
                    node.fx.run();
                }
                A.one('#loaders .close span').on('click', function (event) {
                    closer(A.one('#loaders'));
                });
                A.one('#loads').on("click", function () {
                    getLoaders();
                });
                function getLoaders() {
                    A.one('#loaders').fx.run();
                    A.one('#loaders .close span').scrollIntoView();
                }
            <%if (ND.step == 6 || ND.step == 7) {%>
                A.one('#docStat').setStyle('opacity', '0');
                A.one('#docStat .close span').on('click', function (event) {
                    closer(A.one('#docStat'));
                });
                A.one('#step_6').on('click', function () {
                    A.one('#docStat').fx.run();
                    A.one('#docStat .close span').scrollIntoView();
                    A.one("#docstatus").unplug(A.Plugin.IO);
                    A.one("#docstatus").plug(A.Plugin.IO, {uri: 'docStatus.xhtml', method: 'POST', data: {vid: '<%=ND.V%>'}});
                });
            <% } %>
            <%if ((ND.isDocEditor || ND.isOwner) && ND.isImg) {%>
                A.one('#moveFile').on('click', function () {
                    var div = A.one("#Move");
                    var Div = A.one("#moveMain");
                    div.purge(true);
                    div.unplug(A.Plugin.IO);
                    A.one("#imgesInner").setStyle("minHeight", A.DOM.winHeight() - 10 + "px");
                    A.all('.cat').each(function () {
                        this.on('click', function () {
                            location = "addToGallary?gal=" + this.getAttribute('id') + "&id=<%=id%>";
                        });
                    });
                    Div.scrollIntoView();
                    Div.fx.run();
                });
                A.one("#closeM span").on("click", function (event) {
                    A.one("#moveMain").fx.run();
                });
            <% }%>
            });
        </script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>