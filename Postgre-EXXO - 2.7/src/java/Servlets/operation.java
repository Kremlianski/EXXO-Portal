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

public class operation extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String res = "ok";
        HttpSession session = request.getSession(true);

        String pid = request.getParameter("pid");
        String text = request.getParameter("text");
        String customer = (String) session.getAttribute("id");
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);

        String sql = "INSERT INTO pjornal (customer,pid,text) values (?::int, ?::int, ?)";
        //   FileInputStream fis = null;
        PreparedStatement ps = null;
        try {

            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, customer);
            ps.setString(3, text.replace("\n<br>", "<br>").replace("\n", "<br>"));
            ps.setString(2, pid);

            ps.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            res = e.getLocalizedMessage();
        } finally {
            ps.close();
            con.close();
        }

        out.println(res);
        response.sendRedirect("serviceCard.jsp?id=" + pid);

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
