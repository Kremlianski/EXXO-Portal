<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/>
<%
    String role = (String) session.getAttribute("role");
    String id = request.getParameter("id");
    String user = (String) session.getAttribute("id");
    if ((role.indexOf("a") < 0 && role.indexOf("b") < 0 && role.indexOf("c") < 0)
            && (role.indexOf("m") < 0 || !BASE.Master.isLegalUnitSec(user, id, request))) {
        response.sendRedirect("notPermited.html");
    }
%>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=10" >
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <link rel="stylesheet" type="text/css" href="yui/build/assets/skins/sam/skin.css"><link rel="stylesheet" type="text/css" href="yui/build/fonts/fonts-min.css" />
        <script type="text/javascript" src="yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
        <script type="text/javascript" src="yui/build/element/element-min.js"></script><script type="text/javascript" src="yui/build/container/container_core-min.js"></script>
        <script type="text/javascript" src="yui/build/menu/menu-min.js"></script><script type="text/javascript" src="yui/build/button/button-min.js"></script>
        <script type="text/javascript" src="yui/build/editor/my-editor-min.js"></script>
        <title>Редактор портала</title>
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            @import url('editor.css');    
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgEmp span.yui-toolbar-icon {
                background-image: url("emp.png");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgEmp-selected span.yui-toolbar-icon {
                background-image: url("emp.png");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgMy span.yui-toolbar-icon {
                background-image: url("imgMy.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgMy-selected span.yui-toolbar-icon {
                background-image: url("imgMy.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgMyK span.yui-toolbar-icon {
                background-image: url("imgK.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam .yui-toolbar-container .yui-toolbar-imgMyK-selected span.yui-toolbar-icon {
                background-image: url("imgK.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            #close {color:red;width:auto;text-align:right; padding:5px; padding-right: 10px;}
            #inserter {padding: 20px;}
            #containerImg img.photo {margin: 2px;}
            .hov{color:black;background-color: white;}
            .inno{margin:5px;margin-left:20px;}
            .cat {background-position: left;background-repeat: no-repeat;display: inline-block; width: 180px;vertical-align: top;margin: 10px;font-weight: bold; }
            .fil {margin: 5px;float: left;font-style: italic;font-weight: bolder;}
            .file-hover {color: red;}
            .yui-editor-container .yui-editor-editable-container {width: 610px !important; padding-right: 0px; padding-left:90px;}
            #msgpost {width: 700px;}
            #editorContainer {background-color: white; width:700px;}
            #submit {padding: 4px; margin: 15px; cursor: pointer;}
            a.html {display:block; margin: 15px; text-decoration: none; }
            a.html:hover {text-decoration: underline; color:#972626;}
            #editor-center {padding: 25px;}
            td#menu {width: 200px; vertical-align: top; padding-top: 25px;}
            td#menu input {width: 140px; height: 50px;}
            div#topMenu {width: 100%; height: 36px; background-color: gray;background-image: url("img/spr.png");background-position: bottom;background-repeat: repeat-x;}
            #foot {height: 30px;padding-top: 6px;margin: 0;background-color: transparent;width: auto;border: 0;text-align: left;padding-left: 275px;background-image: none;}
            .footmenu {font-size: 15px;text-decoration: none;color: white;background-color: transparent; padding: 5px; display: inline-block;}
            body {background-color:white;}
            #leftMenu {width: 210px; padding: 10px; margin-left: 10px; background-color: white; margin-top: 25px;}
            #menu-navi {width: 95%; margin-left:10px; height: auto;}
            #menu-navi table {width:100%;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi span {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi span:hover {background-color: white; cursor: pointer;}
            span#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            span#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            span#backward:hover {background-image: url('small/backward.png'); }
            span#forward:hover {background-image: url('small/forward.png');}
            #imges {position: absolute;background-color: transparent;  width: 100%; height: auto; z-index: 99999999;display:none; left:0px; top: 0px;}
            #imgesInner {position:relative;width:auto;height:auto;margin: 4px; background-color:rgb(250,250,250);border: 1px solid #bfbfbf; border-radius: 5px;}
        </style>
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <style><%=CSS.getCSS(request)%></style><style><%=CSS3.getCSS("unitEditor.jsp", request)%></style>
    </head>
    <body class="yui-skin-sam" id="unitEditor_jsp">
        <div id="topMenu"><div id="foot"><ul class="topMenu">
                    <jsp:include page="topMenu.xhtml" flush="true" /><li><a href="unit.jsp?id=<%=id%>" class="footmenu">Закончить</a></li>  
                    <li> <a href="http://exxo.ru/howto/cms-howto.html#editelement" class="footmenu howto" target="_blank">?</a></li>
                </ul></div></div>
        <form action="unitText" method="post">
            <table id="content"><tr><td id="menu">
                        <input type="submit" id='submit' value="Сохранить"/>
                        <a href="unitHtmlEditor.jsp?id=<%=id%>" class="html">Редактировать HTML</a>         
                    </td><td id="editor-center">
                        <input type="hidden" name="id" id="id" value="<%out.print(id);%>">
                        <div id="editorContainer" class="exxo-shadow1">
                            <textarea name="msgpost" id="msgpost" cols="50" rows="10"></textarea>
                        </div>   
                    </td>
                    <td id="info"></td></tr></table></form>
        <div id="imges"><div id="imgesInner">
                <div id="close"><span class="exxo-anim-closer">ЗАКРЫТЬ</span></div><div id="inserter" class="exxo-images"></div></div></div>
        <script type="text/javascript" charset="utf-8" src="scripts/topMenu.js"></script>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-io', 'aui-resize', 'imageloader', 'aui-overlay-mask', function (A) {
                var data;
                var myEditor;
                var of = 0;
                var title = '';
                var text = '';
                var overlayMask = new A.OverlayMask().render();
                var id = '<%out.print(id);%>';
                var ROWS =<%=BASE.VER.getMaxBloks(request.getServletContext())%>;
                var io = A.io.request('unitTextOut', {
                    dataType: 'text', cache: false, data: {id: id}, method: 'post', on: {
                        success: function (event, id, xhr) {
                            var Datas = this.get('responseData');
                            A.one('#msgpost').text(Datas);
                            editorPut();
                        }}});
                A.one('#imges').setStyle('opacity', '0');
                function editorPut() {
                    myEditor = new YAHOO.widget.Editor('msgpost', {
                        height: '300px', width: '700px', dompath: false, animate: true,
                        css: 'html { height: 95%; } body { padding: 7px; box-sizing: border-box; -moz-box-sizing: border-box; background-color: #fff; font: 14px arial,helvetica,clean,sans-serif;*font-size:small;*font:x-small;line-height: 2; } a, a:visited, a:hover { color: blue !important; text-decoration: underline !important; cursor: text !important; } .warning-localfile { border-bottom: 1px dashed red !important; } .yui-busy { cursor: wait !important; } img.selected { border: 2px dotted #808080; } img { cursor: pointer !important; border: none; } body.ptags.webkit div.yui-wk-p { margin: 11px 0; } body.ptags.webkit div.yui-wk-div { margin: 0; } img {max-width: 100%}'});
                    myEditor.on('toolbarLoaded', function () {
                        this.toolbar.set('titlebar', title);
                        var flickrConfig = {type: 'push', label: 'Вставить фото сотрудника', value: 'imgEmp'}
                        myEditor.toolbar.addButtonToGroup(flickrConfig, 'insertitem');
                        this.toolbar.on('imgEmpClick', function () {
                            var _sel = this._getSelectedElement();
                            if (_sel && _sel.tagName && (_sel.tagName.toLowerCase() == 'img')) {
                            } else {
                                openAnim.run();
                                inserter("emp", myEditor);
                                return false;
                            }
                        }, this, true);
                        var imgMyConfig = {type: 'push', label: 'Вставить фото из моей галереи', value: 'imgMy'}
                        myEditor.toolbar.addButtonToGroup(imgMyConfig, 'insertitem');
                        this.toolbar.on('imgMyClick', function () {
                            var _sel = this._getSelectedElement();
                            if (_sel && _sel.tagName && (_sel.tagName.toLowerCase() == 'img')) {
                            } else {
                                of = 0;
                                openAnim.run();
                                Reload("1", null, null);
                                return false;
                            }
                        }, this, true);
                        var imgMyKConfig = {type: 'push', label: 'Вставить фото из общей галереи', value: 'imgMyK'}
                        myEditor.toolbar.addButtonToGroup(imgMyKConfig, 'insertitem');
                        this.toolbar.on('imgMyKClick', function () {
                            var _sel = this._getSelectedElement();
                            if (_sel && _sel.tagName && (_sel.tagName.toLowerCase() == 'img')) {
                            } else {
                                of = 0;
                                openAnim.run();
                                Reload("1", null, "-100");
                                return false;
                            }
                        }, this, true);
                    });
                    myEditor.render();
                    myEditor.on('windowRender', function () {
                        new A.Resize({node: '.yui-editor-editable-container', handles: 'b'}).render();
                    });
                }
                A.one('#submit').on('click', function (e) {
                    YAHOO.widget.EditorInfo.saveAll();
                });
                function closer(node) {
                    closeAnim.run();
                }
                function inserter(type, myEditor) {
                    var Ser = "";
                    var Data;
                    if (type == "emp") {
                        Ser = 'imgesOut';
                        Data = {}
                    } else if (type == "my") {
                        Ser = 'galClassic';
                        Data = {owner: '10', superior: '1'}
                    }
                    var io = A.io.request(Ser, {dataType: 'text', cache: false, data: Data, method: 'post',
                        on: {
                            success: function (event, id, xhr) {
                                var Datas = this.get('responseData');
                                A.one('#imges #inserter').html(Datas);
                                A.all('img.photo').each(function () {
                                    this.on("click", function () {
                                        myEditor.execCommand('insertimage', this.getAttribute('src'));
                                        closer(A.one("#imges"));
                                    })
                                });
                            }}});
                }
                function Reload(superior, id, owner) {
                    A.one("#inserter").unplug(A.Plugin.IO);
                    var data = {superior: superior, of: of};
                    if (owner != null)
                        data = {superior: superior, owner: owner, of: of};
                    if (id != null && owner != null)
                        data = {id: id, owner: owner, of: of};
                    else if (id != null && owner == null)
                        data = {id: id, of: of};
                    A.one("#inserter").plug(A.Plugin.IO, {uri: 'galClassic', method: 'POST', data: data, on: {'end': function (event) {
                                A.all(".cat").each(function () {
                                    this.on("mouseover", function () {
                                        this.addClass("file-hover");
                                    })
                                });
                                A.all(".fil").each(function () {
                                    this.on("mouseover", function () {
                                        this.addClass("file-hover");
                                    })
                                });
                                A.all(".item").each(function () {
                                    this.on("mouseout", function () {
                                        this.removeClass("file-hover");
                                    })
                                });
                                new A.ImgLoadGroup({foldDistance: 25, className: 'exxo-loading'});
                                A.all(".fil a").each(function () {
                                    var href = this.getAttribute('href');
                                    this.setAttribute('href', 'javascript:')
                                    this.on("click", function () {
                                        var html = '<a href="' + href + '" class="imger"><img src="' + href + '&icon=1" title="' + this.getAttribute('title') + '"></a>';
                                        myEditor.execCommand('inserthtml', html);
                                        //myEditor.execCommand('insertimage', href);
                                        closer(A.one("#imges"));
                                        return false;
                                    })
                                });
                                A.all(".cat").each(function () {
                                    this.on("click", function () {
                                        of = 0;
                                        if (!this.hasClass("up"))
                                            Reload(this.get("id"), null, owner);
                                        else
                                            Reload("1", null, owner);
                                    })
                                });
                                A.all(".cat1").each(function () {
                                    this.on("click", function () {
                                        of = 0;
                                        if (!this.hasClass("up"))
                                            Reload(this.get("id"), null, owner);
                                        else
                                            Reload("1", null, owner);
                                    })
                                });
                                if (A.one("#forward") != null)
                                    A.one("#forward").on("click", function () {
                                        of = of + ROWS;
                                        Reload(superior, id, owner);
                                        A.one("#imges .exxo-anim-closer").scrollIntoView( );
                                    });
                                if (A.one("#backward") != null)
                                    A.one("#backward").on("click", function () {
                                        of = of - ROWS;
                                        if (of < 0)
                                            of = 0;
                                        Reload(superior, id, owner);
                                        A.one("#imges .exxo-anim-closer").scrollIntoView( );
                                    });
                            }}})
                }
                A.one('#close span').on('click', function (event) {
                    closer(A.one("#imges"));
                });
                var myHeight = A.DOM.winHeight() - 50;
                if (myHeight < 250)
                    myHeight = 250;
                A.one('#imgesInner').setStyle("minHeight", A.DOM.winHeight() - 10 + "px");
                var node = A.one(A.one("#imges"));
                node.setStyle('opacity', '0');
                var openAnim = new A.Anim({node: node, to: {opacity: 1}, on: {'start': function () {
                            A.one(node).setStyle('display', 'block');
                            node.one('.exxo-anim-closer').scrollIntoView( );
                            overlayMask.set('target', document).show();
                        }}});
                var closeAnim = new A.Anim({node: node, to: {opacity: 0}, on: {'end': function () {
                            A.one(node).setStyle('display', 'none');
                            overlayMask.set('target', document).hide();
                        }}});
                node.one('.exxo-anim-closer').on('click', function () {
                    closeAnim.run();
                });
            });
        </script>
    </body>
</html>