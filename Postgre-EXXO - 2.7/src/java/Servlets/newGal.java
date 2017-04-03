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

public class newGal extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
        int width = java.lang.Integer.parseInt(request.getParameter("width")) - 20;
        String list = "<div  id='galH3'><a href='newGal.jsp'>Новые фотоальбомы</a></div>";

        String json;
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "SELECT gallaries.id, gallaries.owner, gallaries.name, employee.fio FROM gallaries, employee WHERE file='0'"
                    + "AND employee.id=gallaries.owner ORDER BY created DESC LIMIT 30";
            ResultSet rs = stmt.executeQuery(sql);
            list += "<div class='newGalsContent yui3-scrollview-loading'><ul>";
            int count = 1;
            int c = 0;
            if (width <= 200) {
                while (rs.next()) {
                    String fio = rs.getString(4);
                    String Fio = EXXOlib.textLib.shortFIO(fio);
                    list += "<li class='gal'><a href=\"galClassic.jsp?id=" + rs.getString(1) + "&owner=" + rs.getString(2) + "\" style='background-image:url(cover?id=" + rs.getString(1) + "&t=" + System.currentTimeMillis() + ")' "
                            + "title='" + Fio + " - " + rs.getString(3) + "' class='exxo-loading unload_" + c + "'></a></li>";
                    c++;
                }
            } else {
                int blocks = (int) Math.floor(width / 122);
                while (rs.next()) {
                    String fio = rs.getString(4);
                    String Fio = EXXOlib.textLib.shortFIO(fio);

                    if (count == 1) {
                        list += "<li class='gal'>";
                    }
                    list += "<a href=\"galClassic.jsp?id=" + rs.getString(1) + "&owner=" + rs.getString(2) + "\" style='background-image:url(cover?id=" + rs.getString(1) + "&t=" + System.currentTimeMillis() + ")' "
                            + "title='" + Fio + " - " + rs.getString(3) + "' class='exxo-loading unload_" + c + "'></a>";
                    if (count == blocks) {
                        list += "</li>";
                        count = 0;
                        c++;
                    }
                    count++;
                }
                if (count == blocks) {
                    list += "</li>";
                }
            }
            list += "</ul></div>";
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            json = ex.getLocalizedMessage();
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
