package Servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


public class dropQwest extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("id");
        String res = "OK";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("j") >= 0) {
            yes = true;
        }
        if (yes) {
            try {
                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);
                Statement stmt = con.createStatement();

                String sql = "DELETE FROM qwests WHERE quest_id='" + id + "'";
                stmt.executeUpdate(sql);

                stmt.close();
                con.close();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            }
            PrintWriter out = response.getWriter();
            try {
                response.setContentType("text/plain");
                out.println(res);

            } finally {

                response.sendRedirect("qwestList.jsp");
                out.close();
            }
        } else {
            response.sendRedirect("notPermited.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
