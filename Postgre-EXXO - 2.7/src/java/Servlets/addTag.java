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

/**
 *
 * @author alqumisto
 */
public class addTag extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("superior");
        String name = request.getParameter("name");
        String tag = request.getParameter("tag");
        String res = "OK";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = request.getParameter("owner");
        String o = (String) session.getAttribute("id");
        boolean permitted = BEANS.tagMaster.permitted(id, o, request);
        Boolean yes = true;
        String own = "";
        if (owner != null) {
            own = "?owner=" + owner;
        }
        yes = true;
        if (role.indexOf("a") < 0 && role.indexOf("k") < 0 && role.indexOf("Z") < 0 && !permitted) {
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

                String sql = "INSERT INTO  tags (owner, name, tag ,superior) VALUES ('" + owner + "','" + name + "', '" + tag + "', '" + id + "')";

                stmt.executeUpdate(sql);

                con.close();
                stmt.close();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            }

        }
        response.sendRedirect("tags.jsp");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(addTag.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(addTag.class.getName()).log(Level.SEVERE, null, ex);
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
