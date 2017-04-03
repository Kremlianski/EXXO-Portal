<%@page contentType="text/html" pageEncoding="UTF-8"%><%
String q=request.getQueryString();
if(q==null) q="";
response.sendRedirect("empListTer.xhtml?"+q);
%>
