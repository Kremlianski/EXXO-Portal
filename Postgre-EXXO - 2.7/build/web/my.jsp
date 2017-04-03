<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="INDEX" class="BEANS.MyBean" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String res = INDEX.getRes(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Моя личная страница</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            @import url('css/exxo-my.css');
            @import url('vids.css');
            .yui3-scrollview-first,  .yui3-scrollview-last{visibility:hidden;}
            .newDocsContent li,.newBlogsContent li {line-height: 0; display: block;}
            div.exxo-scrollview-up {position: absolute; width: 24px; height: 24px; background-color: white; z-index: 999; bottom: 1px; right: 1px; cursor: pointer; background-image: url(/EXXO/small/tobegin.png); background-position: center center; background-repeat: no-repeat; background-size: auto auto; border-radius: 20px;border: 1px solid #bfbfbf;}
            div.exxo-scrollview-buttons {position: absolute; width: 52px; height: 26px; background-color: transparent; z-index: 999; top: 0px; right: 0px;}
            button.exxo-scrollview-next {position: relative; display: inline-block; width: 20px; height: 20px; background-color: #dedede; cursor: pointer;background-image: url(/EXXO/small/arrow-up.png); background-position: center center; background-repeat: no-repeat; border-radius: 12px; border: 1px solid #bfbfbf; margin: 1px;}
            button.exxo-scrollview-back {position: relative; display: inline-block; width: 20px; height: 20px; background-color: #dedede; cursor: pointer;background-image: url(/EXXO/small/arrow-down.png); background-position: center center; background-repeat: no-repeat; border-radius: 12px; border: 1px solid #bfbfbf; margin: 1px;}
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
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("my.jsp", request)%>
        </style>
    </head>
    <body id="my_jsp">
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
                                        <li><a href="myEdit.jsp" class="footmenu">Настройка</a></li>
                                        <li> <a href="http://exxo.ru/howto/cms-howto.html#newelement" class="footmenu howto" target="_blank">?</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <table id="grid" class="grid">
                <%=res%>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
            <div id="imger"><div id="close"><span>ЗАКРЫТЬ</span></div><div id="imgerViewer"></div></div>
            <script type="text/javascript">
                AUI().ready('aui-overlay-mask', 'aui-io-request', 'anim', 'aui-scroller', 'scrollview', 'scrollview-paginator', 'datatype-number', function (A) {
                    var sv_height = 285;
                    A.all(".portlet").each(function () {
                        var ins = this;
                        var id = ins.getAttribute('id');
                        ins.empty();
                        if (id.indexOf("calendar") == 0) {
                            opener(id, 'eventsVid', ins);
                        } else if (id.indexOf("Birthday") == 0) {
                            opener(id, 'birthOut', ins);
                        } else if (id.indexOf("newComers") == 0) {
                            opener(id, 'newComers', ins);
                        } else if (id.indexOf("newDocsK") == 0) {
                            opener(id, 'newDocK', ins);
                        } else if (id.indexOf("newDocs") == 0) {
                            opener(id, 'newDoc', ins);
                        } else if (id.indexOf("newNews") == 0) {
                            opener(id, 'newNews', ins);
                        } else if (id.indexOf("newGal") == 0) {
                            opener(id, 'newGal', ins);
                        } else if (id.indexOf("newKGal") == 0) {
                            opener(id, 'newKGal', ins);
                        } else if (id.indexOf("newDesk") == 0) {
                            opener(id, 'newDesk', ins);
                        } else if (id.indexOf("newBlog") == 0) {
                            opener(id, 'newBlog', ins);
                        } else if (id.indexOf("newBBlog") == 0) {
                            opener(id, 'newBBlog', ins);
                        } else if (id.indexOf("newPhotos") == 0) {
                            opener(id, 'NewPhotos', ins);
                        }
                    });
                    function opener(x2, servlet, node) {
                        var width = Math.round(A.Number.parse(node.getComputedStyle('width').split('px')[0]));
                        var nodeFontSize = A.Number.parse(node.getComputedStyle('fontSize').split('px')[0]);
                        var classNode = "exxo-width-normal";
                        if (width <= 250)
                            classNode = "exxo-width-small";
                        else if (width > 400)
                            classNode = "exxo-width-big";
                        A.io.request(servlet, {
                            dataType: 'text',
                            cache: false,
                            data: {id: x2, width: width, font: nodeFontSize},
                            method: 'post',
                            on: {success: function (event, id, xhr) {
                                    node.html(this.get('responseData'));
                                    if (servlet == 'newNews') {
                                        node.all('img').each(function () {
                                            var style = this.getStyle('display');
                                            this.setStyle('display', 'none');
                                            width = Math.round(A.Number.parse(node.one('>div').getComputedStyle('width').split('px')[0]));
                                            this.setStyle('maxWidth', width + 'px');
                                            this.setStyle('display', style);
                                        });
                                        node.all("a.imger").each(function () {
                                            var href = this.getAttribute('href');
                                            this.setAttribute('href', 'javascript:');
                                            this.on("click", function () {
                                                div.fx.run();
                                                A.one('#close span').scrollIntoView( );
                                                var html = "<img src='" + href + "'>";
                                                A.one("#imgerViewer").html(html).scrollIntoView( );
                                            });
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
                                }}});
                    }
                    var overlayMask = new A.OverlayMask().render();
                    A.one('#imger').setStyle('opacity', '0');
                    var div = A.one('#imger');
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
                        var nodeWidth = A.Number.parse(node.getComputedStyle('width').split('px')[0]);
                        ;
                        var nodeHeight = A.Number.parse(node.getComputedStyle('height').split('px')[0]);
                        var nodeFontSize = A.Number.parse(node.getComputedStyle('fontSize').split('px')[0]);
                        if (nodeWidth - Math.round(nodeWidth) != 0) {
                            var width = Math.round(nodeWidth) + "px";
                            var height = Math.round(nodeHeight) + "px";
                            var styles = {width: width, height: height};
                            node.setStyles(styles);
                        }
                    }
                    function scrollViewH(node, string, servlet, x2) {
                        node.addClass('exxo-hidden');
                        var nodeW = Math.round(A.Number.parse(node.get('parentNode').getComputedStyle('width').split('px')[0]));
                        node.removeClass('exxo-hidden');
                        var count = 1;
                        var sv = new A.ScrollView({srcNode: node.one(string), height: 150, width: nodeW - 10, axis: "x", flick: false, drag: false,
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
                        //contentDelegate(sv);
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
                        var nodeWidth = A.Number.parse(node.getComputedStyle('width').split('px')[0]);
                        ;
                        var nodeHeight = A.Number.parse(node.getComputedStyle('height').split('px')[0]);
                        var nodeFontSize = A.Number.parse(node.getComputedStyle('fontSize').split('px')[0]);
                        if (nodeWidth - Math.round(nodeWidth) != 0) {
                            var width = Math.round(nodeWidth) + "px";
                            var height = Math.round(nodeHeight) + "px";
                            var styles = {width: width, height: height};
                            node.setStyles(styles);
                        }
                    }
                });
            </script>
            <script type="text/javascript" src="scripts/poisk.js"></script>
            <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>