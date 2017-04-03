
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
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

public class docNameForm extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");
        HttpSession session = request.getSession(true);
        String owner = (String) session.getAttribute("id");
        String owner1 = request.getParameter("owner");
        if (owner1 != null) {
            owner = owner1;
        }
        String sql = "SELECT name FROM files WHERE id=" + id + " AND owner=" + owner;
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        String name = "";
        if (rs.next()) {
            name = rs.getString(1);
        }
        rs.close();
        stmt.close();
        con.close();
        try {

            out.println("<form action='changeCat' method='post'>");
            out.println("<input type='text' name='p2' value='" + name + "'>");
            out.println("<input type='hidden' name='p1' value='" + id + "'>");
            if (owner1 != null) {
                out.println("<input type='hidden' name='owner' value='" + owner1 + "'>");
            }
            out.println("<input type='hidden' name='r' value='1'>");
            out.println("<input type='submit' value='Изменить'>");
            out.println("</form>");

        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(blogNameForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(blogNameForm.class.getName()).log(Level.SEVERE, null, ex);
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
