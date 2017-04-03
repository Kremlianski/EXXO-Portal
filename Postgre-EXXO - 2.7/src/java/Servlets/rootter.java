package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;

public class rootter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        if (role.indexOf("Z") < 0) {
            response.sendRedirect("notPermited.html");
        }
        PrintWriter out = response.getWriter();
        String res = "<html><head><title>Сервис выключен</title></head><body>Для смены пароля root воспользуйтесь инструкцией к данной версии продукта.</body>";
        out.print(res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
