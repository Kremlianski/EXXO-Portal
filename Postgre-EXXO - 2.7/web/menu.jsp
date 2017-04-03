<%@page contentType="text/html" pageEncoding="UTF-8"%><jsp:useBean id="MENU" class="BEANS.menuBean" scope="page"/><%
    String res = MENU.getMenu(request);
%><%=res%>