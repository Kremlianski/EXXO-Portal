package Servlets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
//import java.util.Properties;
import java.io.*;
import java.sql.ResultSet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class sloganIns extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String res = "ok";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("b") >= 0 || role.indexOf("Z") >= 0) {
            yes = true;
        }
        if (yes) {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);

            String test = "SELECT * FROM logo";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(test);
            String INSERT = "INSERT INTO logo (slogan) VALUES (?)";
            String UPDATE = "UPDATE logo SET slogan=?";

            //   FileInputStream fis = null;
            PreparedStatement ps = null;
            try {

                con.setAutoCommit(false);
                if (rs.next()) {
                    ps = con.prepareStatement(UPDATE);
                } else {
                    ps = con.prepareStatement(INSERT);
                }
                ps.setString(1, request.getParameter("slogan"));

                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            } finally {
                rs.close();
                ps.close();
            }
            con.close();

            out.println(res);

            response.sendRedirect("logoIns.jsp");

            out.close();

        } else {
            response.sendRedirect("notPermited.html");
        }

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
