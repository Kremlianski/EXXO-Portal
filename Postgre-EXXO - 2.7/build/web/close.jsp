<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Закрывается!</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body onload="javascript:{
                opener.location = '<%= request.getParameter("location")%>';
                window.close();
            }">
        Если страница не закрылась автоматически, закройте ее сами!
    </body>
</html>
