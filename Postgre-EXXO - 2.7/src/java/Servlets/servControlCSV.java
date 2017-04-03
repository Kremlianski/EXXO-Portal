package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class servControlCSV extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/csv");
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String id = (String) session.getAttribute("id");
        response.setHeader("Content-Disposition", "attachment; filename=\"servControl_" + id + ".txt\"");
        response.addHeader("Cache-Control", "must-revalidate");
        response.setCharacterEncoding("Cp1251");
        PrintWriter out = response.getWriter();
        boolean yes = true;
        Boolean BOSS = false;
        ServletContext sc = request.getServletContext();
        if (role.indexOf("a") >= 0 || role.indexOf("i") >= 0 || role.indexOf("Z") >= 0) {
            BOSS = true;
        }
        String list = "";
        if (yes) {

            Connection con = BASE.VER.getServletConnection(sc);

            CallableStatement cstmt = con.prepareCall("{? = call servControlCSV(?,?)}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.setString(2, id);
            cstmt.setBoolean(3, BOSS);
            cstmt.executeUpdate();
            list = "";
            list += cstmt.getString(1);
            cstmt.close();
            con.close();

            try {

                out.print(list);
                out.flush();
            } finally {

                out.close();
            }
        } else {
            response.sendRedirect("notPermited.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(qwestCSV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(qwestCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(qwestCSV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(qwestCSV.class.getName()).log(Level.SEVERE, null, ex);
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
