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


public class changeTag extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("p1");
        String name = request.getParameter("p2");

        String res = "OK";
        HttpSession session = request.getSession(true);
        String o = (String) session.getAttribute("id");
        boolean permitted = BEANS.tagMaster.permitted(id, o, request);
        boolean superpermitted = BEANS.tagMaster.superPermitted(id, o, request);
        String role = (String) session.getAttribute("role");
        String owner = request.getParameter("owner");
        Boolean yes = true;

        yes = true;
        if (role.indexOf("a") < 0 && role.indexOf("k") < 0 && role.indexOf("Z") < 0 && !permitted && !superpermitted) {
            yes = false;
        }
        if (owner == null) {
            owner = (String) session.getAttribute("id");
        }
        if (yes) {
            try {
                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);
                Statement stmt = con.createStatement();

                String sql = "UPDATE tags SET name ='" + name + "' WHERE tag_id='" + id + "'";
                stmt.executeUpdate(sql);

                stmt.close();
                con.close();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            }
        }
        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/plain");
            out.println(res);

        } finally {
            out.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(changeTag.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(changeTag.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
