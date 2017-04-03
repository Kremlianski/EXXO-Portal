package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;

public class newComers extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        int width = java.lang.Integer.parseInt(request.getParameter("width"));
        String small = "";
        if (width < 200) {
            small = "exxo-vids-small";
        }
        String list = "";
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            list = "<div id='containerNC' class='" + small + "'><h3>Новые лица</h3>";
            String op = "";
            String sql = "SELECT id, fio, units.unit AS unit FROM employee, units WHERE (now()-workSince)<'2 months' AND"
                    + " units.unit_id=employee.unit AND fired='0' ORDER BY fio";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list += "<a href='empCard.jsp?id=" + rs.getString("id") + "' class='exxo-shadow1'><img class='photo' src='IMG?id=" + rs.getString("id") + "' title='" + rs.getString("fio") + ", " + rs.getString("unit") + "'></a>";

            }
            list += "</div>";
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            list = ex.getLocalizedMessage();
        }

        PrintWriter out = response.getWriter();
        try {

            out.println(list);

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(textOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(textOut.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(textOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(textOut.class.getName()).log(Level.SEVERE, null, ex);
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
