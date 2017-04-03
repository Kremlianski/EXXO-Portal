package Servlets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.ResultSet;
import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class decision extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String res = "ok";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        String owner = "";
        String type = "simple";
        String why = "";
        String id = request.getParameter("id");
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String status = request.getParameter("res");
        String text = request.getParameter("text");
        String sql1 = "SELECT owner, type FROM process, pjornal WHERE pjornal.pid=process.id AND "
                + "pjornal.id='" + id + "'";
        try {

            ResultSet rs = stmt.executeQuery(sql1);
            if (rs.next()) {
                owner = rs.getString(1);
                type = rs.getString(2);
            }

        } catch (SQLException e) {
            res = e.getLocalizedMessage();
        }

        if (owner.equals((String) session.getAttribute("id"))) {
            if (type.equals("desideIsDone") && status.equals("2")) {
                status = "5";
            }
            if (text != null) {
                why = ", why='" + text + "'";
            }
            String sql = "UPDATE pjornal SET status='" + status + "'" + why + ", timeupd=now() WHERE "
                    + "id='" + id + "'";

            try {
                stmt.executeUpdate(sql);

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            } finally {
                stmt.close();
                con.close();
            }

            out.println(res);
            response.sendRedirect("myServices.jsp");

        } else {
            response.sendRedirect("notPermited.html");
        }
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
