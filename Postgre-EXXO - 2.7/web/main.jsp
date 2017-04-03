<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="INDEX" class="BEANS.IndexBean" scope="page"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/>
<jsp:useBean id="CSS1" class="BEANS.cssPage" scope="page"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String role = (String) session.getAttribute("role");
    String user_id = (String) session.getAttribute("id");
    if (role.indexOf("a") < 0 && role.indexOf("b") < 0) {
        response.sendRedirect("notPermited.html");
    }
    String id = "2";
    if (request.getParameter("id") != null) {
        id = request.getParameter("id");
    }
    INDEX.setId(id, request);
    String res = INDEX.getRes();
    String title = INDEX.getTitle();
    String css = CSS.getCSS(request);
    String css1 = CSS1.getCSS(id, request);
    boolean trig = BASE.CASE.isDemo(request.getServletContext());
%>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title><%=title%></title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <style type="text/css">
            @import url('css/exxo.css');
            .aui-panel-hd button, #animList button {height: 24px; padding-top: 5px; min-width: 26px;}
            .aui-portal-layout-proxy {position: absolute;width: 100px;height: 20px;background: black;opacity: .7;filter: alpha(opacity=70);}
            #animList {color:#6b6b6b; left: -200px;position: absolute;top: 100px;z-index:100;display:none;background-color: white;}
            .portlet-list {background-color: white;border-width: 0px;width:250px; padding: 10px;}
            .portlet-list .portlet-item {margin: 5px;padding: 2px;display:block; border: 2px solid #dedede;}
            .aui-portal-layout-drag-target-indicator  {margin: 2px 0;}
            #content-wrapper {width: 100%;height:500px;}
            table#grid {position: relative;width:100%;min-height:300px;}
            table#grid td#column-1 {width: 60%;}
            table#grid td#column-4 {width:200px;}
            table#grid td.menu-td{width: 200px;}
            div.portlet {margin-bottom:25px;font-size: 12px;}
            span.blue {color:blue;}
            td#menu-td .portlet{width:150px;}
            #content-wrapper div#header{position: relative;width:  100%;}
            .img {display:block;background-color: white;width: 120px;height: 120px;float: left;margin: 5px;background-position: center;background-repeat: no-repeat;}
            div.aui-panel-hd {background-image: none;background: none;border: 0;}
            div.aui-panel-content {border: 0;}
            div#animList div.aui-panel-hd{background:#ccc;border-bottom:1px solid #999;font-weight:bold;padding:2px 3px 2px 4px; background:#c0c2c5 url(/FILES/alloy/build/aui-skin-classic/images/panel/header_bg.png) repeat-x 0 0;border:0 solid;border-color:#C8C9CA #aeb2b8 #aeb2b8 #C8C9CA;border-bottom-width:1px;padding-left:10px;text-shadow:1px 1px #fff;}
            div#animList .aui-panel-content{border:1px solid #999; font-size:11px;}
            div#animList div.aui-panel-content div.portlet-item {cursor: move; margin-top: 8px;}
            div#animList div.aui-panel-content div.portlet-item span {margin-right:5px;}
            .portlet-list div.portlet-item {display: none; padding: 10px 4px; color: #bfbfbf;}
            .portlet-list select {width: 100%; margin-bottom: 10px;}
            .portlet-list #op1 {}
            .other {display:none;}
            .portlet-list #selects {padding-bottom: 20px;}
            .portlet-list div.portlet-item:hover {border:2px solid #bfbfbf; color: black;}
            .yui3-scrollview-first,  .yui3-scrollview-last{visibility:hidden;}
            .newDocsContent li,.newBlogsContent li {line-height: 0; display: block;}
            div.exxo-scrollview-up {position: absolute; width: 24px; height: 24px; background-color: white; z-index: 999; bottom: 1px; right: 1px; cursor: pointer; background-image: url(/EXXO/small/tobegin.png); background-position: center center; background-repeat: no-repeat; background-size: auto auto; border-radius: 20px;border: 1px solid #bfbfbf;}
            div.exxo-scrollview-buttons {position: absolute; width: 52px; height: 26px; background-color: transparent; z-index: 999; top: 42px; right: 0px;}
            #content-wrapper button.exxo-scrollview-next {position: relative; display: inline-block; width: 20px; height: 20px; background-color: #dedede; cursor: pointer;background-image: url(/EXXO/small/arrow-up.png); background-position: center center; background-repeat: no-repeat; border-radius: 12px; border: 1px solid #bfbfbf; margin: 1px;}
            #content-wrapper button.exxo-scrollview-back {position: relative; display: inline-block; width: 20px; height: 20px; background-color: #dedede; cursor: pointer;background-image: url(/EXXO/small/arrow-down.png); background-position: center center; background-repeat: no-repeat; border-radius: 12px; border: 1px solid #bfbfbf; margin: 1px;}
            button.exxo-scrollview-back:hover {border-color: #dedede;background-color: white;}
            button.exxo-scrollview-next:hover {border-color: #dedede;background-color: white;}
            div.exxo-scrollview-up:hover {border-color: #dedede; background-color: white;}
            .exxo-hidden {display: none !important;}
            .newDocsContent tr.docs td,.newBlogsContent tr.blog td {line-height: normal; height: 44px; padding: 6px;}
            .newDocsContent tr.docs td.first,.newBlogsContent tr.blog td.first {width: 22px;padding: 2px; }
            .newDocsContent tr.docs td.created, .newBlogsContent tr.blog td.created {width: 100px; }
            .newDocsContent tr.docs td.fio,.newBlogsContent tr.blog td.fio {width: 33px;}
            .newDocsContent tr.docs td.fio div,.newBlogsContent tr.blog td.fio div {background-size: contain; background-position: center center; background-repeat: no-repeat; height: 44px; border-radius: 5px;}
            .newDocsContent tr.docs td.first div,.newBlogsContent tr.blog td.first div {width: 100%; height: 20px; background-position: center center; background-repeat: no-repeat;}
            .newDocsContent tr.docs td.fil a,.newBlogsContent tr.blog td.fil a{display: block; width: auto; height: 44px; overflow: hidden; text-decoration: none;}
            span.exxo-date {padding-right: 10px;font-weight: bold;}
            a.notopend {font-weight: normal; color: black;}
            #docsH3 {margin:0; padding: 5px; background-color: rgb(59,59,82);border-radius: 4px 4px 0px 0px;}
            #docsH3 a {color: #dedede;}
            #docsH3 a:hover {color: white;}
            #blogH3 {margin:0; padding: 5px; background-color: rgb(82, 82, 136);border-radius: 4px 4px 0px 0px;}
            #blogH3 a {color: #dedede;}
            #blogH3 a:hover {color: white;}
            div.portlet {border-radius: 4px;}
            .newGalsContent li.gal a {display: inline-block;width: 120px;height: 120px;margin: 2px;background-position: center;background-repeat: no-repeat;}
            .newGalsContent li.gal, .IMG li {background-image: none; display: inline-block; margin:0; padding:0;}
            .newGalsContent, .IMG{white-space:nowrap;}
        </style>
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="vids.css" type="text/css" />
        <link rel="stylesheet" href="index.css" type="text/css" />
        <style><%=css%></style>
        <style><%=CSS3.getCSS("main.jsp", request)%></style>
        <style><%=css1%></style>
    </head>
    <body class="yui3-skin-sam" id="main_jsp">
        <div id="content-wrapper">
            <div id="header">
                <div id="header1"></div>
                <div id="header2"></div>

            </div><%=res%></div>
        <div id="animList" class="exxo-shadow1">
            <div class="portlet-list">
                <div id="selects">
                    <select id="op1" name="op1">
                        <option value="0" SELECTED> выберите виджет </option>
                        <option value="text"> текст </option>
                        <option value="calendar"> календарь </option>
                        <option value="bloges"> сообщения </option>
                        <option value="docs"> документы </option>
                        <option value="birthday"> дни рождения </option>
                        <option value="faces"> новые лица </option>
                        <option value="desk"> объявления </option>
                        <option value="photos"> фотографии </option>
                        <option value="albums"> фотоальбомы </option>
                        <option value="news"> новость </option>
                    </select>
                    <select id="op2" name="op2" class="other">
                        <option value="0" SELECTED>нужно выбрать</option>
                        <option value="1">cотрудников</option>
                        <option value="2">компании</option>
                    </select>
                    <select id="op3" name="op3" class="other">
                        <option value="1" SELECTED>все</option>
                        <option value="2">только по работе</option>
                    </select>
                    <select id="op4" name="op4" class="other">
                        <option value="1" SELECTED>все</option>
                        <option value="2">входящие</option>
                        <option value="3">отправленные</option>
                    </select>
                    <select id="op5" name="op5" class="other">
                        <option value="0" SELECTED>нужно выбрать</option>
                        <option value="1">все</option>
                        <option value="2">общие</option>
                        <option value="3">персональные</option>
                        <option value="4">группа</option>
                        <option value="5">проект</option>
                        <option value="6">подразделение</option>
                        <option value="7">все кроме общих</option>
                    </select>
                    <select id="op6" name="op6" class="other">
                        <option value="1" SELECTED>Мои документы</option>
                        <option value="2">Входящие</option>
                        <option value="3">Общие компании</option>
                        <option value="4">Входящие компании</option>
                        <option value="5">Исходящие компании</option>
                        <option value="6">Внутренние компании</option>
                    </select>
                    <select id="op7" name="op7" class="other">
                        <option value="0" SELECTED>нужно выбрать</option>
                        <option value="1">все</option>
                        <option value="2">общие</option>
                        <option value="3">персональные</option>
                        <option value="4">группа</option>
                        <option value="5">проект</option>
                        <option value="6">подразделение</option>
                        <option value="7">все кроме общих</option>
                    </select>
                </div>   
                <div class="portlet-item" id="text-element"><span class="aui-button-icon aui-icon aui-icon-document"></span>Текстовый элемент</div>
                <div class="portlet-item" id="my-calendar"><span class="aui-button-icon aui-icon aui-icon-calendar"></span>перетащите в нужное место</div>
                <div class="portlet-item" id="my-birthday"><span class="aui-button-icon aui-icon aui-icon-heart"></span>перетащите в нужное место</div>
                <div class="portlet-item" id="my-NC"><span class="aui-button-icon aui-icon aui-icon-person"></span>перетащите в нужное место</div>
                <div class="portlet-item my-D" id="my-D"><span class="aui-button-icon aui-icon aui-icon-copy"></span>перетащите в нужное место</div>
                <div class="portlet-item" id="my-news"><span class="aui-button-icon aui-icon aui-icon-document-b"></span>перетащите в нужное место</div>
                <div class="portlet-item" id="my-gal"><span class="aui-button-icon aui-icon aui-icon-folder-open"></span>перетащите в нужное место</div>
                <div class="portlet-item" id="my-Kgal"><span class="aui-button-icon aui-icon aui-icon-folder-open"></span>перетащите в нужное место</div>
                <div class="portlet-item" id="my-desk"><span class="aui-button-icon aui-icon aui-icon-notice"></span>перетащите в нужное место</div>
                <div class="portlet-item my-B" id="my-B"><span class="aui-button-icon aui-icon aui-icon-note"></span>перетащите в нужное место</div>
                <div class="portlet-item" id="my-P"><span class="aui-button-icon aui-icon aui-icon-image"></span>перетащите в нужное место</div>
            </div>
        </div>
        <div id="menus" class="yui3-menu"><jsp:include page="menu.jsp" flush="true" /></div>
        <script type="text/javascript">
            AUI().ready('aui-portal-layout', 'anim', 'aui-panel', 'aui-io-request', 'aui-toolbar', 'aui-scroller', 'scrollview', 'scrollview-paginator', 'datatype-number', function (A) {
                var user =<%=user_id%>;
                var sv_height = 285;
                var dragging;
                A.one('body').delegate('mousedown', function (e) {
                    dragging = this;
                }, '.portlet');
                A.one('#menus').setStyle('display', 'block').prependTo(A.one('#menu'));
                new A.Panel({collapsible: false, collapsed: false, headerContent: '', bodyContent: A.one('#menus'),
                    icons: [{icon: 'pencil', handler: {fn: function (e) {
                                    location = 'menuEditor.jsp';
                                }, type: 'click'}}]}).render('#menu');
                var pl = A.one("#animList");
                var plHTML = pl.html();
                pl.empty();
                new A.Panel({collapsible: false, collapsed: false, headerContent: '', bodyContent: plHTML,
                    icons: [{icon: 'closethick', handler: {fn: function (e) {
                                    pListHide()
                                }, type: 'click'}}]}).render(pl);
                var DDM = A.DD.DDM;
                var proxyNode = A.Node.create('<div class="aui-portal-layout-proxy"></div>');
                var portalLayout = new A.PortalLayout({dragNodes: '.portlet', dropNodes: '.column', proxyNode: proxyNode, lazyStart: true,
                    after: {'drag:end': function (event) {
                            fixElements();
                            loadOne(dragging);
                        }}});
                loadAll();
                function loadOne(ins) {
                    var id = ins.getAttribute('id');
                    ins.empty();
                    if (id.indexOf("text") == 0) {
                        var instance = new A.Panel({collapsible: false, collapsed: false, headerContent: '', bodyContent: '',
                            icons: [{icon: 'closethick', handler: {fn: function (e) {
                                            if (confirm("Удалить элемент?")) {
                                                alterText(user, id, 'deleteText');
                                                ins.empty().unplug().remove();
                                                fixElements();
                                            }
                                        }, type: 'click'}},
                                {icon: 'pencil', handler: {fn: function (e) {
                                            location = 'editor.jsp?id=' + id + '&b=<%=id%>';
                                        }, type: 'click'}}]}).render(ins);
                        textR(ins, instance, 'textOut');
                    } else if (id.indexOf("calendar") == 0) {
                        widget(ins, 'eventsVid');
                    } else if (id.indexOf("Birthday") == 0) {
                        widget(ins, 'birthOut');
                    } else if (id.indexOf("newComers") == 0) {
                        widget(ins, 'newComers');
                    } else if (id.indexOf("newDocsK") == 0) {
                        widget(ins, 'newDocK');
                    } else if (id.indexOf("newDocs") == 0) {
                        widget(ins, 'newDoc');
                    } else if (id.indexOf("newNews") == 0) {
                        widget(ins, 'newNews');
                    } else if (id.indexOf("newGal") == 0) {
                        widget(ins, 'newGal');
                    } else if (id.indexOf("newKGal") == 0) {
                        widget(ins, 'newKGal');
                    } else if (id.indexOf("newDesk") == 0) {
                        widget(ins, 'newDesk');
                    } else if (id.indexOf("newBlog") == 0) {
                        widget(ins, 'newBlog');
                    } else if (id.indexOf("newBBlog") == 0) {
                        widget(ins, 'newBBlog');
                    } else if (id.indexOf("newPhotos") == 0) {
                        widget(ins, 'NewPhotos');
                    }
                    ;
                }
                function loadAll() {
                    A.all(".portlet").each(function () {
                        var ins = this;
                        var id = ins.getAttribute('id');
                        ins.empty();
                        if (id.indexOf("text") == 0) {
                            var instance = new A.Panel({collapsible: false, collapsed: false, headerContent: '', bodyContent: '',
                                icons: [{icon: 'closethick', handler: {fn: function (e) {
                                                if (confirm("Удалить элемент?")) {
                                                    alterText(user, id, 'deleteText');
                                                    ins.empty().unplug().remove();
                                                    fixElements();
                                                }
                                            }, type: 'click'}},
                                    {icon: 'pencil', handler: {fn: function (e) {
                                                location = 'editor.jsp?id=' + id + '&b=<%=id%>';
                                            }, type: 'click'}}]}).render(ins);
                            textR(ins, instance, 'textOut');
                        } else if (id.indexOf("calendar") == 0) {
                            widget(ins, 'eventsVid');
                        } else if (id.indexOf("Birthday") == 0) {
                            widget(ins, 'birthOut');
                        } else if (id.indexOf("newComers") == 0) {
                            widget(ins, 'newComers');
                        } else if (id.indexOf("newDocsK") == 0) {
                            widget(ins, 'newDocK');
                        } else if (id.indexOf("newDocs") == 0) {
                            widget(ins, 'newDoc');
                        } else if (id.indexOf("newNews") == 0) {
                            widget(ins, 'newNews');
                        } else if (id.indexOf("newGal") == 0) {
                            widget(ins, 'newGal');
                        } else if (id.indexOf("newKGal") == 0) {
                            widget(ins, 'newKGal');
                        } else if (id.indexOf("newDesk") == 0) {
                            widget(ins, 'newDesk');
                        } else if (id.indexOf("newBlog") == 0) {
                            widget(ins, 'newBlog');
                        } else if (id.indexOf("newBBlog") == 0) {
                            widget(ins, 'newBBlog');
                        } else if (id.indexOf("newPhotos") == 0) {
                            widget(ins, 'NewPhotos');
                        }
                    });
                }
                function widget(ins, servlet) {
                    var instance = new A.Panel({collapsible: false, collapsed: false, headerContent: '', bodyContent: '',
                        icons: [{icon: 'closethick', handler: {fn: function (e) {
                                        if (confirm("Удалить элемент?")) {
                                            ins.empty().unplug().remove();
                                            fixElements();
                                        }
                                    }, type: 'click'}}]}).render(ins);
                    textR(ins, instance, servlet);
                }
                function textR(ins, instance, servlet) {
                    var node = ins;
                    var width = Math.round(A.Number.parse(node.getComputedStyle('width').split('px')[0]));
                    var nodeFontSize = A.Number.parse(node.getComputedStyle('fontSize').split('px')[0]);
                    var classNode = "exxo-width-normal";
                    if (width <= 250)
                        classNode = "exxo-width-small";
                    else if (width > 400)
                        classNode = "exxo-width-big";
                    var id = ins.getAttribute('id');
                    var x2 = id;
                    var title = '';
                    var text = '';
                    A.io.request(servlet, {dataType: 'text', cache: false, data: {id: x2, width: width, font: nodeFontSize}, method: 'post',
                        on: {success: function (event, id, xhr) {
                                var Datas = this.get('responseData');
                                instance.set('bodyContent', Datas);
                                if (ins.get('parentNode')) {
                                    if (servlet == 'textOut' || servlet == 'newNews') {
                                        node.all('img').each(function () {
                                            var style = this.getStyle('display');
                                            this.setStyle('display', 'none');
                                            width = Math.round(A.Number.parse(node.one('>div>div>div>div').getComputedStyle('width').split('px')[0]));
                                            this.setStyle('maxWidth', width + 'px');
                                            this.setStyle('display', style);
                                        });
                                    } else if (servlet == 'NewPhotos') {
                                        scrollViewH(node, ".IMG ul", servlet, x2);
                                    } else if (servlet == 'newBlog' || servlet == 'newBBlog') {
                                        scrollView(node, "div.newBlogsContent ul", servlet, x2);
                                    } else if (servlet == 'newDoc' || servlet == 'newDocK') {
                                        scrollView(node, "div.newDocsContent ul", servlet, x2);
                                    } else if (servlet == 'newDesk') {
                                        var component = new A.Scroller({contentBox: node.one('.desk'), height: sv_height, orientation: 'vertical', duration: 0.5}).render();
                                    } else if (servlet == 'newGal' || servlet == 'newKGal') {
                                        scrollViewH(node, "div.newGalsContent", servlet, x2);
                                    }
                                }
                            }}});
                }
                var PortletItem = function () {
                    PortletItem.superclass.constructor.apply(this, arguments);
                };
                PortletItem.NAME = 'PortletItem';
                PortletItem.ATTRS = {
                    dd: {value: {target: false}}, lazyStart: {value: true},
                    proxyNode: {value: proxyNode}, itemContainer: {value: A.one('.portlet-list')}};
                A.extend(PortletItem, A.PortalLayout, {
                    _getAppendNode: function () {
                        var instance = this;
                        instance.appendNode = DDM.activeDrag.get('node').cloneNode(true);
                        return instance.appendNode;
                    }
                });
                var portletListT = new PortletItem({dragNodes: '#text-element'});
                portletListT.on('drag:end', function (event) {
                    var D = new Date();
                    var id = 'text_' + D.getTime() + '-' +<%= user_id%>;
                    alterText(user, id, 'insertText');
                    var newText = A.Node.create('<div class="portlet" id="' + id + '"></div>');
                    if (portletListT.appendNode && portletListT.appendNode.inDoc()) {
                        portletListT.appendNode.replace(newText);
                    }
                    var BodyContent = '<span class="aui-button-icon aui-icon aui-icon-loading"></span>';
                    var instance = new A.Panel({collapsible: false, collapsed: false, headerContent: '', bodyContent: BodyContent, icons: [
                            {icon: 'closethick', handler: {fn: function (e) {
                                        if (confirm("Удалить элемент?")) {
                                            alterText(user, id, 'deleteText');
                                            newText.empty().unplug().remove();
                                            fixElements();
                                        }
                                    }, type: 'click'}},
                            {icon: 'pencil', handler: {fn: function (e) {
                                        location = 'editor.jsp?id=' + id + '&b=<%=id%>';
                                    }, type: 'click'}}],
                        after: {init: function (event) {
                                fixElements();
                            }}}).render(newText);
                });
                makeAPortlet('#my-birthday', 'Birthday', 'birthOut');
                makeAPortlet('#my-NC', 'newComers', 'newComers');
                makeAPortlet('#my-news', 'newNews', 'newNews');
                makeAPortlet('#my-gal', 'newGal', 'newGal');
                makeAPortlet('#my-Kgal', 'newKGal', 'newKGal');
                makeAPortlet('#my-desk', 'newDesk', 'newDesk');
                makeAPortlet('#my-P', 'newPhotos', 'NewPhotos');
                makeAPortlet('#my-calendar', 'calendar', 'eventsVid');
                function makeAPortlet(dragNode, aimNode, servlet) {
                    var portletListB = new PortletItem({dragNodes: dragNode});
                    portletListB.on('drag:end', function (event) {
                        var newBirthday = A.Node.create('<div class="portlet" id="' + aimNode + '"></div>');
                        if (portletListB.appendNode && portletListB.appendNode.inDoc()) {
                            portletListB.appendNode.replace(newBirthday);
                        }
                        var BodyContent = '<span class="aui-button-icon aui-icon aui-icon-loading"></span>';
                        var inst;
                        var instance = new A.Panel({collapsible: false, collapsed: false, headerContent: '', bodyContent: BodyContent, icons: [
                                {icon: 'closethick', handler: {fn: function (e) {
                                            if (confirm("Удалить элемент?")) {
                                                newBirthday.empty().unplug().remove();
                                                fixElements();
                                            }
                                        }, type: 'click'}}],
                            after: {init: function (event) {
                                    inst = this;
                                    textR(newBirthday, inst, servlet);
                                    fixElements();
                                }}}).render(newBirthday);
                    });
                }
            <% if (!trig) {%>
                new A.Toolbar({activeState: true, children: [
                        {label: 'Закончить', icon: 'check', handler: {fn: function (e) {
                                    location = "index.jsp?id=<%=id%>"
                                }}},
                        {label: 'Добавить элемент', icon: 'circle-plus', handler: {fn: function (e) {
                                    pListShow()
                                }}},
                        {label: 'Новая страница', icon: 'document', handler: {fn: function (e) {
                                    location = 'addNew';
                                }}},
                        {label: 'Название страницы', icon: 'pencil', handler: {fn: function (e) {
                                    changeTitle(<%=id%>, "<%=title%>")
                                }}},
                        {label: 'Удалить страницу', icon: 'closethick', handler: {fn: function (e) {
                                    if (confirm("Удалить страницу?"))
                                        closePage(<%=id%>)
                                }}},
                        {label: 'Список страниц', icon: 'script', handler: {fn: function (e) {
                                    location = "listPage.jsp?user=" + user;
                                }}},
                        {label: 'Лого', icon: 'image', handler: {fn: function (e) {
                                    location = "logoIns.jsp";
                                }}}
                        //{label: 'Активировать',icon: 'key',handler: {fn: function(e){location='/WELLCOME/register.xhtml';}}}
                    ]}).render(A.one('#header1'));
            <%} else {%>
                new A.Toolbar({activeState: false, children: [
                        {label: 'Закончить', icon: 'check', handler: {fn: function (e) {
                                    location = "index.jsp?id=<%=id%>"
                                }}},
                        {label: 'Добавить элемент', icon: 'circle-plus', handler: {fn: function (e) {
                                    pListShow()
                                }}},
                        {label: 'Новая страница', icon: 'document', handler: {fn: function (e) {
                                    location = 'addNew';
                                }}},
                        {label: 'Название страницы', icon: 'pencil', handler: {fn: function (e) {
                                    changeTitle(<%=id%>, "<%=title%>")
                                }}},
                        {label: 'Удалить страницу', icon: 'closethick', handler: {fn: function (e) {
                                    if (confirm("Удалить страницу?"))
                                        closePage(<%=id%>)
                                }}},
                        {label: 'Список страниц', icon: 'script', handler: {fn: function (e) {
                                    location = "listPage.jsp?user=" + user;
                                }}},
                        {label: 'Лого', icon: 'image', handler: {fn: function (e) {
                                    location = "logoIns.jsp";
                                }}}
                    ]}).render(A.one('#header1'));
            <%}%>
                new A.Toolbar({activeState: false, children: [
                        {label: 'Стиль общий', icon: 'script', handler: {fn: function (e) {
                                    location = 'css.jsp';
                                }}},
                        {label: 'Стиль страницы', icon: 'script', handler: {fn: function (e) {
                                    location = 'styleP.jsp?id=<%=id%>';
                                }}},
                        {label: 'Стили сервисов', icon: 'script', handler: {fn: function (e) {
                                    location = 'styleList.jsp';
                                }}},
                        {label: 'Справка', icon: 'help', handler: {fn: function (e) {
                                    window.open('http://exxo.ru/intranet/howto/CMS/');
                                }}}
                    ]}).render(A.one('#header2'));
                function alterText(user, id, servlet) {
                    var io = A.io.request(servlet, {cache: false, data: {user: user, id: id}, method: 'post'});
                }
                function fixElements() {
                    var page_id = <%=id%>;
                    var grid = A.one('#grid');
                    grid.all('div, span').each(function () {
                        this.removeAttribute('_yuid');
                    });
                    var node = grid.cloneNode(true);
                    node.all('.column').removeClass('yui3-dd-drop');
                    node.all('.column div').setAttribute('class', 'portlet');
                    node.all('.column div').empty();
                    node.one('div#menu').empty();
                    node.all('div, tbody, td, tr').each(function () {
                        this.removeAttribute('_yuid');
                    });
                    var structure = node.outerHTML();
                    node.remove();
                    node.destroy();
                    alterText(page_id, structure, 'insStructure');
                }
                function pListShow() {
                    var pl = A.one("#animList");
                    pl.setStyle('display', 'block');
                    an.run();
                }
                function pListHide() {
                    an1.run();
                }
                var an = new A.Anim({node: "#animList", duration: 0.85, easing: A.Easing.elasticOut, from: {xy: [-200, 100]}, to: {xy: [200, 100]}});
                var an1 = new A.Anim({node: "#animList", duration: 0.8, easing: A.Easing.elasticOut, from: {xy: [200, 100]}, to: {xy: [-200, 100]}});
                an1.on('end', function () {
                    var pl = A.one("#animList");
                    pl.setStyle('display', 'none');
                });
                function changeTitle(id, title) {
                    var name = window.prompt("Введите новое название страницы", title);
                    if (name) {
                        alterText(name, id, "changeTitle");
                        document.title = name;
                    }
                }
                function closePage() {
                    var pageId =<%=id%>;
                    if (pageId == 2) {
                        alert("Нельзя удалить главную страницу!");
                        return;
                    }
                    A.all(".portlet").each(function () {
                        var idNode = this.getAttribute("id");
                        if (idNode.indexOf("text") >= 0)
                            alterText(user, idNode, 'deleteText');
                    });
                    var str = "=<%=id%>$";
                    var re = new RegExp(str);
                    A.all("#menus a").each(function () {
                        if (this.getAttribute("href").match(re))
                            this.get('parentNode').unplug().remove().destroy();
                    });
                    A.io.request("menu", {cache: false, data: {menu: A.one('#menus').html()}, method: 'post',
                        on: {end: function () {
                                A.io.request("deletePage", {cache: false, data: {menu: "<%=id%>"}, method: 'post',
                                    on: {end: function () {
                                            location = "main.jsp";
                                        }}
                                })
                            }}
                    })
                }
                function alterMenu(menu, servlet) {
                    var io = A.io.request(servlet, {cache: false, data: {menu: menu}, method: 'post'});
                }
                new A.DD.Drag({node: A.one('#animList'), haltDown: false});
                A.one('#content-wrapper').setStyle('minHeight', A.DOM.winHeight() + 'px');


                A.one('#op1').on('change', function () {
                    A.all('.portlet-list div.portlet-item').setStyle('display', 'none');
                    A.all('.other').setStyle('display', 'none');
                    var v = this.get('value');
                    A.all('.portlet-list div.portlet-item').setStyle('display', 'none');
                    if (v == 'calendar')
                        A.one('#my-calendar').setStyle('display', 'block');
                    else if (v == 'text')
                        A.one('#text-element').setStyle('display', 'block');
                    else if (v == 'birthday')
                        A.one('#my-birthday').setStyle('display', 'block');
                    else if (v == 'bloges') {
                        var op3 = A.one("#op3");
                        op3.set('value', '0');
                        op3.setStyle('display', 'block');
                        var op4 = A.one("#op4");
                        op4.set('value', '0');
                        op4.setStyle('display', 'block');
                        var op5 = A.one("#op5");
                        op5.set('value', '0');
                        op5.setStyle('display', 'block');
                    } else if (v == 'docs') {
                        var op6 = A.one("#op6");
                        op6.set('value', '0');
                        op6.setStyle('display', 'block');
                        var op7 = A.one("#op7");
                        op7.set('value', '0');
                        op7.setStyle('display', 'block');
                    } else if (v == 'faces')
                        A.one('#my-NC').setStyle('display', 'block');
                    else if (v == 'desk')
                        A.one('#my-desk').setStyle('display', 'block');
                    else if (v == 'photos')
                        A.one('#my-P').setStyle('display', 'block');
                    else if (v == 'albums') {
                        var op2 = A.one("#op2");
                        op2.set('value', '0');
                        op2.setStyle('display', 'block');
                    } else if (v == 'news')
                        A.one('#my-news').setStyle('display', 'block');
                });
                A.one('#op2').on('change', function () {
                    var v = this.get('value');
                    A.all('.portlet-list div.portlet-item').setStyle('display', 'none');
                    if (v == '1')
                        A.one('#my-gal').setStyle('display', 'block');
                    else if (v == '2')
                        A.one('#my-Kgal').setStyle('display', 'block');
                });
                A.one('#op5').on('change', function () {
                    var v = this.get('value');
                    var s = "my-b";
                    var m = "";
                    var b = A.one('#op3').get('value');
                    var i = A.one('#op4').get('value');
                    if (b == 1)
                        m = 'a';
                    else if (b == 2)
                        m = 'b';
                    if (i == 1)
                        m += 'A';
                    else if (i == 2)
                        m += 'I';
                    else
                        m += 'O';
                    A.all('.portlet-list div.portlet-item').setStyle('display', 'none');
                    m += '_' + v;
                    s += m;
                    A.one('#' + s).setStyle('display', 'block');
                });
                A.one('#op7').on('change', function () {
                    var v = this.get('value');
                    var s = "my-D";
                    var m = "";
                    var i = A.one('#op6').get('value');
                    m += i;
                    A.all('.portlet-list div.portlet-item').setStyle('display', 'none');
                    m += '_' + v;
                    s += m;
                    A.one('#' + s).setStyle('display', 'block');
                });

                A.one('#op3').on("change", function () {
                    if (A.one('#op5').get('value') != 0)
                        A.one('#op5').set('value', '0');
                });
                A.one('#op4').on("change", function () {
                    if (A.one('#op5').get('value') != 0)
                        A.one('#op5').set('value', '0');
                });
                A.one('#op6').on("change", function () {
                    if (A.one('#op7').get('value') != 0)
                        A.one('#op7').set('value', '0');
                });
                var S = ['a', 'b'];
                var B = ['A', 'I', 'O'];

                for (var i = 1; i <= 2; i++) {
                    for (var y = 1; y <= 3; y++) {
                        for (var k = 1; k <= 7; k++) {
                            var m = S[i - 1] + B[y - 1] + "_" + k;
                            var s = '<div class="portlet-item my-b' + m + '" id="my-b' + m + '"><span class="aui-button-icon aui-icon aui-icon-image"></span>перетащите в нужное место</div>';
                            A.one('.portlet-list').append(s);
                            makeAPortlet('#my-b' + m, 'newBlog_' + m, 'newBlog');
                        }
                    }
                }

                for (var y = 1; y <= 6; y++) {
                    for (var k = 1; k <= 7; k++) {
                        var m = y + "_" + k;
                        var s = '<div class="portlet-item my-D' + m + '" id="my-D' + m + '"><span class="aui-button-icon aui-icon aui-icon-image"></span>перетащите в нужное место</div>';
                        A.one('.portlet-list').append(s);
                        makeAPortlet('#my-D' + m, 'newDocs_' + m, 'newDoc');
                    }
                }
                function appender(x2, servlet, sv, count, node) {
                    var width = Math.round(A.Number.parse(node.getComputedStyle('width').split('px')[0]));
                    var nodeFontSize = A.Number.parse(node.getComputedStyle('fontSize').split('px')[0]);
                    var content = sv.get('contentBox');
                    A.io.request("add_" + servlet, {
                        dataType: 'text',
                        cache: false,
                        data: {id: x2, count: count, width: width, font: nodeFontSize},
                        method: 'post',
                        on: {success: function (event, id, xhr) {
                                content.append(this.get('responseData'));
                                sv.set('height', sv_height + 10 + "px");
                                sv.set('height', sv_height + "px");
                                sv.syncUI();
                                contentDelegate(sv);
                            }}});
                }
                ;
                function contentDelegate(sv) {
                    var content = sv.get("contentBox");
                    content.delegate("click", function (e) {
                        if (Math.abs(sv.lastScrolledAmt) > 2) {
                            e.preventDefault();
                        }
                    }, "a");
                    content.delegate("mousedown", function (e) {
                        e.preventDefault();
                    }, "a, li");
                }
                function scrollView(node, string, servlet, x2) {
                    var count = 1;
                    var sv = new A.ScrollView({srcNode: node.one(string), height: sv_height, axis: "y", flick: {
                            minDistance: 10, minVelocity: 0.3, axis: "y"},
                        on: {'scrollEnd': function () {
                                if (sv.pages.get('index') == 0 && up != null)
                                    up.addClass('exxo-hidden');
                                else if (sv.pages.get('index') != 0)
                                    up.removeClass('exxo-hidden');
                                var h = 0;
                                sv.get('contentBox').all('table').each(function () {
                                    h += this.outerHeight();
                                });
                                if (count < 10 && (Math.abs(sv.get('scrollY') - h) < (sv_height + 200 * count))) {
                                    appender(x2, servlet, sv, count, node);
                                    count++;
                                }
                            }}
                    });
                    sv.plug(A.Plugin.ScrollViewPaginator, {selector: 'li'});
                    sv.render();
                    contentDelegate(sv);
                    node.setStyle('position', 'relative');
                    var up = A.Node.create("<div class='exxo-scrollview-up exxo-hidden'></div>");
                    node.append(up);
                    up.on('click', function () {
                        sv.pages.scrollTo(0, 0.6, "ease-in");
                    });
                    var buts = A.Node.create("<div class='exxo-scrollview-buttons'></div>");
                    node.append(buts);
                    var back = A.Node.create("<button class='exxo-scrollview-back'></button>");
                    buts.append(back);
                    var next = A.Node.create("<button class='exxo-scrollview-next'></button>");
                    buts.append(next);
                    next.on('click', function () {
                        sv.pages.next();
                    });
                    back.on('click', function () {
                        sv.pages.prev();
                    });
                }
                function scrollViewH(node, string, servlet, x2) {
                    node.addClass('exxo-hidden');
                    var nodeW = Math.round(A.Number.parse(node.get('parentNode').getComputedStyle('width').split('px')[0]));
                    node.removeClass('exxo-hidden');
                    var count = 1;
                    var sv = new A.ScrollView({srcNode: node.one(string), height: 150, width: nodeW - 16, axis: "x", flick: false, drag: false,
                        on: {'scrollEnd': function () {
                                if (sv.pages.get('index') == 0 && up != null)
                                    up.addClass('exxo-hidden');
                                else if (sv.pages.get('index') != 0)
                                    up.removeClass('exxo-hidden');
                                node.all('.unload_' + (this.pages.get('index') + 1)).removeClass('exxo-loading');
                            },
                            'render': function () {
                                node.all('.unload_' + this.pages.get('index')).removeClass('exxo-loading');
                                A.all('.unload_' + (this.pages.get('index') + 1)).removeClass('exxo-loading');
                            }
                        }
                    });
                    sv.plug(A.Plugin.ScrollViewPaginator, {selector: 'li'});
                    sv.render();
                    node.setStyle('position', 'relative');
                    var up = A.Node.create("<div class='exxo-scrollview-up exxo-hidden'></div>");
                    node.append(up);
                    up.on('click', function () {
                        sv.pages.scrollTo(0, 0.6, "ease-in");
                    });
                    var buts = A.Node.create("<div class='exxo-scrollview-buttons'></div>");
                    node.append(buts);
                    var back = A.Node.create("<button class='exxo-scrollview-back'></button>");
                    buts.append(back);
                    var next = A.Node.create("<button class='exxo-scrollview-next'></button>");
                    buts.append(next);
                    next.on('click', function () {
                        sv.pages.next();
                    });
                    back.on('click', function () {
                        sv.pages.prev();
                    });
                }
            });
        </script>
        <script type="text/javascript">
            YUI().ready('node-menunav', function (A) {
                var menu = A.one("#menus");
                menu.plug(A.Plugin.NodeMenuNav, {submenuHideDelay: 1000, mouseOutHideDelay: 50, submenuShowDelay: 50});
                menu.get("ownerDocument").get("documentElement").removeClass("yui3-loading");
            });
        </script>
    </body>
</html>