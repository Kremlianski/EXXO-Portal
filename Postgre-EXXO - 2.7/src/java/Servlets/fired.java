package Servlets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
//import java.util.Properties;
import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;

public class fired extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String fired = request.getParameter("fired");
        String res = "ok";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("c") >= 0) {
            yes = true;
        }
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        if (yes) {
            String UPDATE = "";
            String DELETE = "";
            if (fired != null) {
                UPDATE = "UPDATE  employee SET fired='0' , firedSince=null WHERE id='" + id + "'";
                //  DELETE = "INSERT INTO user_roles VALUES((SELECT login FROM users WHERE id='"+id+"'), 'portal_user')";

            } else {
                UPDATE = "UPDATE  employee SET fired='1' , firedSince=now() WHERE id='" + id + "'";
                // DELETE = "DELETE FROM user_roles WHERE login=(SELECT login FROM users WHERE id='"+id+"') AND role='portal_user'";
                DELETE = "DELETE FROM users WHERE  id=" + id;
            }

            try {
                con.setAutoCommit(false);
                stmt.executeUpdate(UPDATE);
                if (!DELETE.equals("")) {
                    stmt.executeUpdate(DELETE);
                }
                con.commit();
            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            }

            stmt.close();

        }
        con.close();

        out.println(res);

        response.sendRedirect("empUpd.jsp?id=" + id);

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
