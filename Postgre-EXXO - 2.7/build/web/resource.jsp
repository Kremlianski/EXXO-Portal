<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String id = request.getParameter("id");
    response.sendRedirect("allDayR.xhtml?r=" + id);
%>