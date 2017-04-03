package Servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.ServletContext;

public class insPersonal extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String text = (String) request.getParameter("text").replace("\n", "");
        String id = (String) request.getSession().getAttribute("id");
        String res = "OK";

        Boolean yes = true;
        if (yes) {

            try {
                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);
                Statement stmt = con.createStatement();

                String sql = "SELECT personal('" + id + "','" + text + "')";
                stmt.execute(sql);

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
                out.close();
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(insPersonal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(insPersonal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
