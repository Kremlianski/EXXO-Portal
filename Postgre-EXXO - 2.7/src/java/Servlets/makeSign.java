package Servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


public class makeSign extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String vid = request.getParameter("v");
        String id = request.getParameter("id");

        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String gid = (String) session.getAttribute("global_id");
        String user = (String) session.getAttribute("id");

        Boolean yes = true;
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        yes = false;

        String copy = "0";
        String sql = "SELECT isBlogPermitted(dopusk_type, dopusk," + gid + "), copy FROM files WHERE id=" + id;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            yes = rs.getBoolean(1);
            copy = rs.getString(2);
        }
        rs.close();
        if (yes && id != null && vid != null) {

            sql = "SELECT add_sign(" + copy + "," + vid + "," + user + ",1)";

            stmt.execute(sql);

            sql = "SELECT make_step_6(" + id + "," + vid + ", " + copy + ")";

            stmt.execute(sql);

            stmt.close();
            con.close();

        } else {
            stmt.close();
            con.close();
            response.sendRedirect("notPermited.html");
        }
        if (response != null && !response.isCommitted()) {
            response.sendRedirect("fileLoader.jsp?v=" + vid + "&id=" + id);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(docPublish.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(docPublish.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("notPermited.html");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
