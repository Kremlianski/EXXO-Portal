package Servlets;

import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class blogDopusk extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String dopusk = request.getParameter("dopusk");
        String p = request.getParameter("p");
        String unit = request.getParameter("uid");
        String uid = "";
        if (unit != null) {
            uid = "uid=" + unit;
        }
        if (p == null) {
            p = "list";
        }

        HttpSession session = request.getSession(true);
        String owner = (String) session.getAttribute("id");
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "SELECT dopusk_type FROM blogesTMP WHERE owner=" + owner;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                type = rs.getString(1);
            }
            if (!type.equals("4")) {
                response.sendRedirect("notPermited.html");
            } else //очень сложно. Проще через js как удаление
            {
                sql = "UPDATE blogesTMP SET dopusk=array_append((SELECT dopusk FROM blogesTMP WHERE owner='" + owner + "')," + dopusk + ") WHERE owner='" + owner + "' "
                        + " AND ((NOT (SELECT dopusk FROM blogesTMP WHERE owner='" + owner + "')@>'{" + dopusk + "}' AND (SELECT dopusk FROM blogesTMP WHERE owner='" + owner + "') IS NOT NULL) OR "
                        + " (SELECT dopusk FROM blogesTMP WHERE owner='" + owner + "') IS NULL)";
            }
            stmt.executeUpdate(sql);

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
        }

        if (p.equals("list")) {
            response.sendRedirect("blogDopusk.jsp");
        } else if (p.equals("photo")) {
            response.sendRedirect("blogDopPhoto.jsp");
        } else if (p.equals("comp")) {
            response.sendRedirect("blogDopComp.jsp?" + uid);
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
