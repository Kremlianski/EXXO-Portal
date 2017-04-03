<%@ page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/><jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/><jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="NP" class="BEANS.NewPhoto" scope="page"/><%
    String list = NP.getList(request);
    String slogan = SLOGAN.getSlogan(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Новые фотографии</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta http-equiv="content-style-type" content="text/css">
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {position: relative;margin-top: 0px; margin-left: 10px; width:auto;height: auto;margin-bottom: 10px;background-color: white;padding: 4px;
                      overflow:visible; width: 95%; border-color:#DEDEDE #DEDEDE #BFBFBF #DEDEDE; border-width: 1px 1px 1px 1px; border-style: solid;text-align: center;}
            table#small div#main div.cat {background-image: none; height: auto; width: 180px;display: inline-block; vertical-align: top; margin: 5px; padding: 10px;}
            table#small div#main div.cat1 {background-image: url("small/photo_album.png");background-position: left;background-repeat: no-repeat;padding-left: 30px;}
            table#small {width: 100%;}
            #tdmain {width:100%;}
            #menu-navi {width: 95%; margin-left:10px; height: auto;}
            #menu-navi table {width:100%;}
            #menu-navi table td.oneNavi,#menu-navi table td.threeNavi  {width: 60px;}
            #menu-navi a {display: block; border: 1px solid #BFBFBF; width: 40px; height: 40px; margin: 10px; }
            #menu-navi a:hover {background-color: white;}
            a#backward {background-image: url('small/backward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px;}
            a#forward {background-image: url('small/forward1.png'); background-position: center center; background-repeat: no-repeat;border-radius: 8px; }
            a#backward:hover {background-image: url('small/backward.png'); }
            a#forward:hover {background-image: url('small/forward.png');}
            #menuList a {background-image: url('small/application_view_list.png');background-position: center left;background-repeat: no-repeat;padding: 2px; padding-left: 22px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("newPhotos.jsp", request)%>
        </style>
    </head>
    <body id="newPhotos_jsp">
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
                                        <li> <a href="http://exxo.ru/howto/gallery-howto.html#find" class="footmenu howto" target="_blank">?</a></li>
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
                                <jsp:include page="menu-pic.xhtml" flush="true" />
                            </div>
                            <div id="menuList"><a href="newPhotosList.jsp">В виде списка</a></div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow exxo-images">
                                <%=list%>
                            </div>
                            <%=NP.navi%>
                        </td>
                    </tr>
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <script type="text/javascript">
            AUI().ready('aui-image-viewer', 'imageloader', function (A) {
                new A.ImgLoadGroup({foldDistance: 25, className: 'exxo-loading'});
                var myHeight = A.DOM.winHeight() - 150;
                if (myHeight < 300)
                    myHeight = 300;
                var imageGallery = new A.ImageViewer({links: '#content a',
                    modal: {opacity: .8, background: '#000072'},
                    captionFromTitle: true,
                    preloadNeighborImages: true,
                    preloadAllImages: false,
                    maxHeight: A.DOM.winHeight() - 60,
                    maxWidth: A.DOM.winWidth() - 100,
                    anim: false}).render();
                var arrowLeftEl = imageGallery.get('arrowLeftEl');
                var arrowRightEl = imageGallery.get('arrowRightEl');
                arrowLeftEl.setStyle('left', '15px');
                arrowRightEl.setStyle('right', '15px');
                A.one(".aui-overlaymask-content").setStyle("height", "100%");
                A.one("#content").setStyle("minHeight", myHeight + "px");
            });
        </script>
        <script type="text/javascript" src="resources/scripts/top-menu-fixed.js"></script>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>