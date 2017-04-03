<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="SLOGAN" class="BEANS.SLOGAN" scope="application"/>
<jsp:useBean id="CSS" class="BEANS.CSSTotal" scope="application"/>
<jsp:useBean id="CSS3" class="BEANS.css1Bean" scope="page"/><jsp:useBean id="PROCESS" class="BEANS.ProcessIns" scope="page"/><%
    String slogan = SLOGAN.getSlogan(request);
    String role = (String) session.getAttribute("role");
    if (role.indexOf("a") < 0 && role.indexOf("h") < 0) {
        response.sendRedirect("notPermited.html");
    }
    String option = PROCESS.getOption(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Форма добавления нового сервиса</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <link rel="stylesheet" href="/FILES/alloy/build/aui-skin-classic/css/aui-skin-classic-all-min.css" type="text/css" media="screen" />
        <script src="/FILES/alloy/build/aui/aui.js" type="text/javascript"></script>
        <link rel="stylesheet" href="index.css" type="text/css" />
        <link rel="stylesheet" href="blue.css" type="text/css" />
        <style type="text/css">
            @import url('css/topMenu.css');
            @import url('css/exxo.css');
            #content {width:575px; margin-left: 10px; border-color: #DEDEDE #DEDEDE #BFBFBF #DEDEDE;border-width: 1px;border-style: solid;  background-color: white; padding-top:20px;}
            table.table {width: 100%; }
            td.left {text-align:right;padding:10px;padding-right:20px;}
            td.right {text-align:left;}
            .table tr {border-bottom: 1px solid #dfdfdf;}
            #submit-button{width:100%;text-align:center;}
            #ok{margin:15px;padding: 5px; cursor: pointer;}
            table.table {font-size: 12px;}
            td.left {color: gray; width: 200px;}
            td.right input[type=text] {width: 300px; padding: 2px;}
            td.right {padding: 5px;}
            #content textarea {width: 300px; height: 100px;padding: 2px;} 
            label {font-weight: normal;font-size:12px;}
            table.table tr:hover a {color:#972626;}
            .table a {color: #5B677D;text-decoration: none;}
            .table a:hover {text-decoration: underline;}    
            .strict {color:red;}
            #minToDeside,#minToOpen,#minToDo {width: 50px;}
        </style>
        <style>
            <%=CSS.getCSS(request)%>
        </style>
        <style>
            <%=CSS3.getCSS("processIns.jsp", request)%>
        </style>
    </head>
    <body id="processIns_jsp">
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
                                                <td><jsp:include page="poisk" flush="true" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div id="foot">
                                    <ul class="topMenu">
                                        <jsp:include page="topMenu.xhtml" flush="true" />
                                        <li><a href="services.jsp" class="footmenu">Закончить</a></li>
                                        <li> <a href="http://exxo.ru/howto/service-howto.html#edit" class="footmenu howto" target="_blank">?</a></li>
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
                                <jsp:include page="menu-serv.xhtml" flush="true" />
                            </div>
                        </td>
                        <td id="column-3" valign="top">
                            <div id="content" class="exxo-shadow">
                                <form method="post" id="form" name="form" action="processIns">
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td class="left"><label>Название</label></td>
                                                <td class="right"><input name="name" type="text" id="name" class="exxo-input" ></td>
                                            </tr>
                                            <tr>
                                                <td class="left"><label>Описание</label></td>
                                                <td class="right">
                                                    <textarea name="description" rows="5" cols="40" class="exxo-textarea"></textarea>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="left"><label>Владелец</label></td>
                                                <td class="right"><select name="owner"><%= option%></select></td>
                                            </tr>
                                            <tr>
                                                <td class="left"><label>Инспектор</label></td>
                                                <td class="right"><select name="supervisor"><%= option%></select></td>
                                            </tr>
                                            <tr>
                                                <td class="left"><label>Шаблон</label></td>
                                                <td class="right">
                                                    <textarea name="template" rows="5" cols="40" class="exxo-textarea"></textarea>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="left"><label>Время до открытия</label></td>
                                                <td class="right">
                                                    <input name="minToOpen" type="text" id="minToOpen" class="exxo-input">
                                                    <select name="toOpen">
                                                        <option value="1" selected>минут</option>
                                                        <option value="60">часов</option>
                                                        <option value="1440">дней</option>
                                                        <option value="10080">недель</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="left"><label>Время до принятия решения</label></td>
                                                <td class="right">
                                                    <input name="minToDeside" type="text" id="minToDeside" class="exxo-input">
                                                    <select name="toDeside">
                                                        <option value="1" selected>минут</option>
                                                        <option value="60">часов</option>
                                                        <option value="1440">дней</option>
                                                        <option value="10080">недель</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="left"><label>Время до исполнения</label></td>
                                                <td class="right">
                                                    <input name="minToDo" type="text" id="minToDo" class="exxo-input">
                                                    <select name="toDo">
                                                        <option value="1" selected>минут</option>
                                                        <option value="60">часов</option>
                                                        <option value="1440">дней</option>
                                                        <option value="10080">недель</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="left"><label>Тип процесса</label></td>
                                                <td class="right">
                                                    <select name="type">
                                                        <option value="simple" SELECTED>Открыто-сделано</option>
                                                        <option value="desideIsDone">Решено-сделано</option>
                                                        <option value="DoneIsDone">Выполнено-сделано</option>
                                                        <option value="answerIsDone">Отвечено-сделано</option>
                                                    </select>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div id="submit-button">
                                        <input type="submit" value="Сохранить" id="ok">
                                    </div>
                                </form>
                            </div>   
                        </td>
                    </tr> 
                </tbody>
            </table>
            <jsp:include page="FOOTER" flush="true" />
        </div>
        <script type="text/javascript" src="scripts/poisk.js"></script>
        <script type="text/javascript" src="scripts/topMenu.js"></script>
    </body>
</html>