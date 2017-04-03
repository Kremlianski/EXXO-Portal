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

public class blogKill extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");

        String res = "OK";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        yes = false;
        String owner = (String) session.getAttribute("id");
        String ow = request.getParameter("owner");
        if (role.indexOf("a") >= 0 || role.indexOf("d") >= 0 || role.indexOf("Z") >= 0) {
            yes = true;
        }

        try {

            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql1 = "SELECT global_id FROM BLOGES WHERE id='" + id + "'";
            ResultSet rs = stmt.executeQuery(sql1);
            String global_id = null;
            if (rs.next()) {
                global_id = rs.getString(1);
            }
            String sql = null;

            if (yes) {
                sql = "delete from bloges where  id='" + id + "'";
            } else if (id != null) {
                sql = "delete from bloges where  id='" + id + "' AND owner='" + owner + "'";
            }
            if (sql != null) {
                if (stmt.executeUpdate(sql) != 0) {
                    stmt.executeUpdate("delete from global_tags where global_id='" + global_id + "'");
                }
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            res = e.getLocalizedMessage();
        }
        /*
            PrintWriter out=response.getWriter();
              try{response.setContentType("text/html");
                out.println(res);

               }finally{out.close();}
         */
        String extra = "";
        if (ow != null) {
            extra = "?owner=" + ow;
        }
        response.sendRedirect("blogs.jsp" + extra);

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
