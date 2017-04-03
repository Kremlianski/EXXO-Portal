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


public class visaDoc extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("id");
        String vid = request.getParameter("v");
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = request.getParameter("owner");
        Boolean yes = true;
        String own = "";
        int nid = -10;
        if (owner != null) {
            own = "?owner=" + owner;
        }
        yes = false;
        if (owner == null || (role.indexOf("o") >= 0 && owner.equals("-100")) || (role.indexOf("p") >= 0 && owner.equals("-101"))
                || (role.indexOf("q") >= 0 && owner.equals("-102")) || (role.indexOf("r") >= 0 && owner.equals("-103"))) {
            yes = true;
        }
        if (owner == null) {
            owner = (String) session.getAttribute("id");
        }
        if (yes && id != null) {

            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "UPDATE files SET step=3 WHERE id=" + id;
            stmt.executeUpdate(sql);

            sql = "DELETE FROM blogesTMP WHERE owner=" + owner;
            stmt.executeUpdate(sql);
            sql = "SELECT name, dopusk_type, dopusk FROM files WHERE id=" + id + " AND owner=" + owner;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                sql = "INSERT INTO blogestmp (owner, name, dopusk_type, dopusk, bus, refdoc, refVer, status) VALUES (?::int,?,?,?,1,?::int, ?::int, 2)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, owner);
                ps.setString(2, "НА СОГЛАСОВАНИЕ: " + rs.getString("name"));
                ps.setInt(3, rs.getInt("dopusk_type"));
                ps.setArray(4, rs.getArray("dopusk"));
                ps.setString(5, id);
                ps.setString(6, request.getParameter("v"));
                ps.executeUpdate();
                ps.close();
            }

            rs.close();
            //   rs.close();

            stmt.close();
            con.close();

        } else {
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
