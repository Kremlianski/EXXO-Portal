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


public class makeNoSign extends HttpServlet {

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
        String name = "";
        String owner = "0";
        Boolean yes = true;
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        yes = false;
        String sql = "SELECT isBlogPermitted(dopusk_type, dopusk," + gid + "), name, owner FROM files WHERE id=" + id;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            yes = rs.getBoolean(1);
            name = rs.getString(2);
            owner = rs.getString(3);

        }
        rs.close();
        if (yes && id != null && vid != null) {

            sql = "SELECT add_sign(" + id + "," + vid + "," + user + ", 2)";

            stmt.execute(sql);

            sql = "DELETE FROM blogesTMP WHERE owner=" + user;
            stmt.executeUpdate(sql);
            sql = "INSERT INTO blogestmp (owner, name, dopusk_type, dopusk, bus, refdoc, refVer, status) VALUES (" + user + ",'ЗАМЕЧАНИЯ:  " + name + "',4, ARRAY[]::Int[]||(SELECT global_id FROM employee WHERE id=" + owner + "), 1," + id + ", " + vid + ", 3)";
            stmt.executeUpdate(sql);

            stmt.close();
            con.close();

        } else {
            stmt.close();
            con.close();
            response.sendRedirect("notPermited.html");
        }
        if (response != null && !response.isCommitted()) {
            response.sendRedirect("blogInsert.jsp?edit=1&r=1");
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
