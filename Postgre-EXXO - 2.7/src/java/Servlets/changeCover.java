package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class changeCover extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = request.getParameter("owner");
        String id = request.getParameter("id");
        String r = request.getParameter("r");
        Boolean yes = true;
        String own = "";
        if (owner != null) {
            own = "?owner=" + owner;
        }
        yes = true;
        if (owner == null) {
            owner = (String) session.getAttribute("id");
        }
        if (owner.equals("0")) {
            owner = "-100";
        }
        if (role.indexOf("a") < 0 && role.indexOf("d") < 0 && owner != null && owner.equals("-100")) {
            yes = false;
        }
        if (yes) {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "UPDATE gallaries SET cover='f' WHERE superior=(SELECT superior FROM gallaries WHERE id=" + id + " AND owner=" + owner + ") AND owner=" + owner;
            stmt.executeUpdate(sql);
            sql = "UPDATE gallaries SET cover='t' WHERE id=" + id + " AND owner=" + owner;
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
        } else {
            response.sendRedirect("notPermited.html");
        }
        if (r != null) {
            response.sendRedirect("pic.jsp?id=" + id);
        } else {
            response.sendRedirect("gallariesMod.jsp" + own);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(changeCover.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(changeCover.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(changeCover.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(changeCover.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
