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
import java.sql.SQLException;
//import java.util.Properties;
import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class movement extends HttpServlet {

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
        if (role.indexOf("a") >= 0 || role.indexOf("c") >= 0 || role.indexOf("Z") >= 0) {
            yes = true;
        }
        if (yes) {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);

            String UPDATE_PICTURE = "UPDATE  employee SET office=?, room=? WHERE id=?::int";

            //   FileInputStream fis = null;
            PreparedStatement ps = null;
            try {

                con.setAutoCommit(false);
//      File file = new File("/home/alqumisto/NetBeansProjects/EXXO/web/IMG_3012.JPG");
//      fis = new FileInputStream(file);
                ps = con.prepareStatement(UPDATE_PICTURE);
                ps.setString(1, request.getParameter("office"));
                ps.setString(2, request.getParameter("room"));
                ps.setString(3, request.getParameter("id"));

                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            } finally {

                ps.close();
            }
            con.close();

            out.println(res);

            //    response.sendRedirect("empUpd.jsp?id="+id);
            out.close();

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
