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


public class docBlock extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String vid = request.getParameter("v");
        String id = request.getParameter("id");
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = request.getParameter("owner");
        Boolean yes = false;
        String own = "";
        if (owner != null) {
            own = "?owner=" + owner;
        }
        yes = true;
        if (role.indexOf("e") >= 0 || role.indexOf("s") >= 0) {
            yes = true;
        }
        if (owner == null) {
            owner = (String) session.getAttribute("id");
        }
        if (yes && id != null && vid != null) {

            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();

            String sql = "UPDATE files_vers SET status=2 WHERE ver_id=" + vid;

            stmt.executeUpdate(sql);

            stmt.close();
            con.close();

        } else {
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
