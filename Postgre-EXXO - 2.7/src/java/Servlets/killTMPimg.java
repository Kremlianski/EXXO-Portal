package Servlets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.Properties;
import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
//import java.util.Iterator;
//import java.util.Set;

public class killTMPimg extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String res = "ok";

        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        HttpSession session = request.getSession(true);
        String id = (String) session.getAttribute("id");

        String INSERT_PICTURE = "UPDATE employeeTMP SET fio=?, photo=?, unit=?::Int, position=?, birthday=?::Date, head=?::smallint, education=?, tellocal=?, "
                + "tellmob=?, email=?, hobby=?, comment=?, responsibility=?, supervizor=?, tel=?, room=?, office=?, workSince=?::Date  WHERE id='" + id + "'";

        //   FileInputStream fis = null;
        PreparedStatement ps = null;
        try {

            con.setAutoCommit(false);
//      File file = new File("/home/alqumisto/NetBeansProjects/EXXO/web/IMG_3012.JPG");
//      fis = new FileInputStream(file);
            ps = con.prepareStatement(INSERT_PICTURE);
            ps.setBytes(2, null);
            ps.setString(1, request.getParameter("fio"));
            ps.setString(3, request.getParameter("unit"));
            ps.setString(4, request.getParameter("position"));
            ps.setString(5, request.getParameter("birthday"));
            ps.setString(6, request.getParameter("head"));
            ps.setString(7, request.getParameter("education"));
            ps.setString(8, request.getParameter("tellocal"));
            ps.setString(9, request.getParameter("tellmob"));
            ps.setString(10, request.getParameter("email"));
            ps.setString(11, request.getParameter("hobby"));
            ps.setString(12, request.getParameter("comment"));
            ps.setString(13, request.getParameter("responsibility"));
            ps.setString(14, request.getParameter("supervizor"));
            ps.setString(15, request.getParameter("tel"));
            ps.setString(16, request.getParameter("room"));
            ps.setString(17, request.getParameter("office"));
            ps.setString(18, request.getParameter("workSince"));
            ps.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            res = e.getLocalizedMessage();
        } finally {

            ps.close();
            con.close();
        }
        String uri = "?re=1";

        out.println(res);

        response.sendRedirect("empIns.jsp" + uri);

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
