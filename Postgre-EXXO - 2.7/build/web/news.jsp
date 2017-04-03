<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="NEWS" class="BEANS.NewsBean" scope="page"/><%
    String role = (String) session.getAttribute("role");
    Boolean yes = false;
    if (role.indexOf("a") >= 0 || role.indexOf("g") >= 0) {
        yes = true;
    }
    String of = request.getParameter("of");
    if (of == null) {
        of = "0";
    }
    String list = NEWS.getList(yes, request);
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=10" />
        <title>Новости компании</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="alloy/build/aui-skins/core/css/main.css" type="text/css" >
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/exxo.css');
            @import url('editor.css');  
            #content {width:auto;height:auto;position: relative;padding:0px 10px;margin:0;overflow-x: hidden;overflow-y: auto;}
            .hoverPlus {color:red;}
            #editor {margin: 15px;padding:20px;position: absolute;z-index: 99998;top:0px;height: auto;width: 700px;color: black;
                     background-color: white;display: none;overflow: auto;}
            .close, #closer,#closser {color:red;width:auto;text-align:right;}
            .text {font-size:10pt;padding-bottom: 10px;text-align: justify;}
            .timer {text-align: right;}
            .HBlog{text-align: right;}
            .blog {margin-bottom: 20px;background-color: white;padding: 10px;padding-top: 5px;padding-bottom: 5px;clear: both; width:90%;}
            .B {padding-left: 10px; text-decoration: none;}
            .blog:hover .B {color: #972626;}
            .B:hover {text-decoration: underline;}
            #plus, #plusik {font-size: 12px;text-align: left; margin: 10px; margin-left: 15px;}
            #pl {padding-left: 5px; text-decoration: none;}
            #pl:hover {color: #972626; text-decoration: underline;}
            #close {color:red;width:auto;text-align:right; padding:5px; padding-right: 10px;}
            #inserter {padding: 20px;}
            #containerImg img.photo {margin: 2px;}
            .hov{color:black;background-color: white;}
            .inno{margin:5px;margin-left:20px;}
            .cat {background-image: none;background-position: left;background-repeat: no-repeat;padding-left: 20px;}
            .fil {margin: 5px;float: left;font-style: italic;font-weight: bolder;}
            .file-hover {color: red;}
            .yui-skin-sam #editor .yui-toolbar-container .yui-toolbar-imgEmp span.yui-toolbar-icon {
                background-image: url("emp.png" );background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam #editor .yui-toolbar-container .yui-toolbar-imgEmp-selected span.yui-toolbar-icon {
                background-image: url("emp.png");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam #editor .yui-toolbar-container .yui-toolbar-imgMy span.yui-toolbar-icon {
                background-image: url("imgMy.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam #editor .yui-toolbar-container .yui-toolbar-imgMy-selected span.yui-toolbar-icon {
                background-image: url("imgMy.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam #editor .yui-toolbar-container .yui-toolbar-imgMyK span.yui-toolbar-icon {
                background-image: url("imgK.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            .yui-skin-sam #editor .yui-toolbar-container .yui-toolbar-imgMyK-selected span.yui-toolbar-icon {
                background-image: url("imgK.gif");background-position: 1px 1px;background-repeat: no-repeat;left: 5px;}
            #closer {width:auto; padding: 5px; padding-right: 10px;}
            #imger {position: absolute;display: none;left:20px;top:20px;padding: 10px;background-color: black;z-index: 9999999;}
            #msgpost-panel_f {filter:none !important;}
            .text {padding: 40px;padding-top:20px;padding-left: 100px;}
            .blog {font-size: 12px;background-image: url("img/paper.png");background-position: top;background-repeat:repeat;background-color:#FAFAFA;}
            .yui-editor-container .yui-editor-editable-container {width: 610px !important; padding-right: 0px; padding-left:90px;}
            #msgpost {width: 700px; margin: 0px auto;}
            input#submit{margin: 20px auto; display: block; width: 110px; padding: 5px;}
            #menu-navi {width: 90%; margin-left:10px; height: auto;}
            #menu-navi table {width:100%;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi a {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi a:hover {background-color: white;}
            a#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            a#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            a#backward:hover {background-image: url('small/backward.png'); }
            a#forward:hover {background-image: url('small/forward.png');}
            #menu-navi span {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi span:hover {background-color: white; cursor: pointer;}
            span#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            span#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            span#backward:hover {background-image: url('small/backward.png'); }
            span#forward:hover {background-image: url('small/forward.png');}
            #imges {position: absolute;background-color: transparent;  width: 100%; height: auto; z-index: 99999999;display:none; left:0px; top: 0px;}
            #imgesInner {position:relative;width:auto;height:auto;margin: 4px; background-color:rgb(250,250,250);border: 1px solid #bfbfbf; border-radius: 5px;}
            div.text img {max-width: 100%;}
        </style>
        <link rel="stylesheet" type="text/css" href="yui/build/assets/skins/sam/skin.css">
        <link rel="stylesheet" type="text/css" href="yui/build/fonts/fonts-min.css" />
        <script type="text/javascript" src="yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
        <script type="text/javascript" src="yui/build/element/element-min.js"></script>
        <script type="text/javascript" src="yui/build/container/container_core-min.js"></script>
        <script type="text/javascript" src="yui/build/menu/menu-min.js"></script>
        <script type="text/javascript" src="yui/build/button/button-min.js"></script>
        <script type="text/javascript" src="yui/build/editor/my-editor-min.js"></script>
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("news.jsp", request)%>
        </style>
    </head>
    <body class="yui-skin-sam yui3-skin-sam" id="news_jsp">
        <div id="content-wrapper">
            <div id="header">
                <table id="tableH">
                    <tbody>
                        <tr>
                            <td id="tdlogo"><img src="LOGO" id="logo">
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
                                    <jsp:include page="topMenu" flush="true" />
                                    <a href="http://exxo.ru/howto/news-howto.html" class="footmenu howto" target="_blank">?</a>
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
                            <%if (yes) {%>
                            <div id="plus">
                                <span class="aui-button-icon aui-icon aui-icon-plusthick"></span>
                                <a href="javascript:;" id="pl">Добавить новость</a>
                            </div>
                            <%}%>
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
            <form action="news" method="post" id="forma">
                <input type="hidden" name="of" value="<%=of%>">
                <textarea name="msgpost" id="msgpost" cols="50" rows="10"></textarea><input type="submit" id='submit' value="Сохранить">
            </form>
        </div>
        <div id="imges">
            <div id="imgesInner">
                <div id="closer">
                    <span class="exxo-anim-closer">ЗАКРЫТЬ</span>
                </div>
                <div id="inserter" class="exxo-images"></div>
                    
            </div>
                
        </div>
        <div id="imger">
            <div id="closser"><span>ЗАКРЫТЬ</span></div>
            <div id="imgerViewer"></div>
                
        </div>
        <script type="text/javascript">
            AUI().ready('anim', 'aui-overlay-mask', 'aui-button-item', 'aui-io', 'imageloader', function (A) {
                var overlayMask = new A.OverlayMask().render();
                var editor = A.one('#editor');
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
                editor.setStyle('opacity', '0');
                editor.plug(A.Plugin.NodeFX, conf);
                var myHeight = A.DOM.winHeight() - 150;
                if (myHeight < 300)
                    myHeight = 300;
                var HH = myHeight - 80;
                if (HH < 250)
                    HH = 250;
            <%if (yes) {%>
                A.one("#plus a").on("click", function () {
                    this.removeClass("hoverPlus");
                    editor.fx.run();
                    editorPut();
                });
                var myEditor;
                var of = 0;
                var ROWS =<%=BASE.VER.getMaxBloks(request.getServletContext())%>;
                function editorPut() {
                    var title = "";
                    myEditor = new YAHOO.widget.Editor('msgpost', {height: HH + 'px', width: '700px', dompath: false, animate: true,
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
                }
                A.one('#submit').on('click', function (e) {
                    YAHOO.widget.EditorInfo.saveAll();
                });
                function closer(node) {
                    node.setStyle('display', 'none');
                    node.setStyle('opacity', '0');
                    overlayMask.hide();
                    location = "news.jsp?of=<%=of%>";
                }
                A.one("#close").on("click", function () {
                    closer(A.one("#editor"))
                });
                A.all(".edit").each(function () {
                    var id = this.get("id");
                    this.on("click", function () {
                        editor.fx.run();
                        A.one("#forma").insert("<input type='hidden' name='id' value='" + id + "'>");
                        A.io.request('newsOut', {dataType: 'text', cache: false, data: {id: id}, method: 'post',
                            on: {success: function (event, id, xhr) {
                                    var Datas = this.get('responseData');
                                    A.one('#msgpost').text(Datas);
                                    editorPut();
                                }}});
                    })
                });
                function closers(node) {
                    closeAnim.run();
                    ;
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
                    var io = A.io.request(Ser, {dataType: 'text', cache: false, data: Data, method: 'post', on: {
                            success: function (event, id, xhr) {
                                var Datas = this.get('responseData');
                                A.one('#imges #inserter').html(Datas);
                                A.all('img.photo').each(function () {
                                    this.on("click", function () {
                                        myEditor.execCommand('insertimage', this.getAttribute('src'));
                                        closers(A.one("#imges"));
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
                                    this.setAttribute('href', 'javascript:');
                                    this.on("click", function () {
                                        var html = '<a href="' + href + '" class="imger"><img src="' + href + '&icon=1" title="' + this.getAttribute('title') + '"></a>';
                                        myEditor.execCommand('inserthtml', html);
                                        //myEditor.execCommand('insertimage', href);
                                        closers(A.one("#imges"));
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
                                if (A.one("span#forward") != null)
                                    A.one("span#forward").on("click", function () {
                                        of = of + ROWS;
                                        Reload(superior, id, owner);
                                        A.one("#imges .exxo-anim-closer").scrollIntoView( );
                                    });
                                if (A.one("span#backward") != null)
                                    A.one("span#backward").on("click", function () {
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
                var myHeight1 = A.DOM.winHeight() - 50;
                if (myHeight1 < 250)
                    myHeight1 = 250;
                A.one('#imgesInner').setStyle("minHeight", A.DOM.winHeight() - 10 + "px");
                A.one('#closer span').on('click', function (event) {
                    closers(A.one("#imges"));
                });
            <% } %>
                function closser(node) {
                    node.setStyle('display', 'none');
                    node.setStyle('opacity', '0');
                    overlayMask.hide();
                }
                A.all("a.imger").each(function () {
                    var href = this.getAttribute('href');
                    this.setAttribute('href', 'javascript:')
                    this.on("click", function () {
                        div.fx.run();
                        var html = "<img src='" + href + "'>";
                        A.one("#imgerViewer").html(html).scrollIntoView( );
                    });
                })
                A.one('#imger').setStyle('opacity', '0');
            <%if (yes) { %>
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
            <% }%>
                A.one("#content").setStyle("minHeight", myHeight + "px");
                A.one('#menus').setStyle('display', 'block').prependTo(A.one('#menu'));
                var node = A.one(A.one("#imges"));
                node.setStyle('opacity', '0');
                var openAnim = new A.Anim({node: node, to: {opacity: 1}, on: {'start': function () {
                            A.one(node).setStyle('display', 'block');
                            node.one('.exxo-anim-closer').scrollIntoView( );
                        }}});
                var closeAnim = new A.Anim({node: node, to: {opacity: 0}, on: {'end': function () {
                            A.one(node).setStyle('display', 'none');
                        }}});
                node.one('.exxo-anim-closer').on('click', function () {
                    closeAnim.run();
                });
                var div = A.one('#imger');
                div.setStyle('opacity', '0');
                div.plug(A.Plugin.NodeFX, conf);
                A.one('#closser span').on('click', function (event) {
                    div.fx.run();
                });
            });
        </script>
        <script type="text/javascript" src="scripts/mainMenu.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script> 
    </body>
</html>