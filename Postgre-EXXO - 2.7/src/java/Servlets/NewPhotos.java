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
import java.util.Properties;
import javax.servlet.ServletContext;

public class NewPhotos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
        int width = java.lang.Integer.parseInt(request.getParameter("width")) - 20;
        String list = "<div  id='blogH3'><a href='newPhotos.jsp'>Новые фотографии</a></div>";

        String json;
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "SELECT id, owner, superior, name, get_fio(owner) AS fio FROM gallaries WHERE file='1' AND notshow='0' ORDER BY created DESC LIMIT 100";
            ResultSet rs = stmt.executeQuery(sql);
            list += "<div class='IMG yui3-scrollview-loading'><ul>";
            int count = 1;
            int c = 0;

            if (width <= 200) {

                while (rs.next()) {
                    String fio = rs.getString(5);
                    String Fio = EXXOlib.textLib.shortFIO(fio);
                    list += "<li><a class='img exxo-loading unload_" + c + "' href=\"pic.jsp?id=" + rs.getString("id") + "\" "
                            + "style='background-image:url(\"galLoader?id=" + rs.getString("id") + "&icon=1\")' title='" + Fio + " - " + rs.getString(4) + "'> </a></li>";
                    c++;
                }
            } else {
                int blocks = (int) Math.floor(width / 122);
                while (rs.next()) {
                    String fio = rs.getString(5);
                    String Fio = EXXOlib.textLib.shortFIO(fio);
                    if (count == 1) {
                        list += "<li>";
                    }
                    list += "<a class='img exxo-loading unload_" + c + "' href=\"pic.jsp?id=" + rs.getString("id") + "\""
                            + " style='background-image:url(\"galLoader?id=" + rs.getString("id") + "&icon=1\")' title='" + Fio + " - " + rs.getString(4) + "'> </a>";
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
            list += "</div></ul>";
            list = "<div class='portlet-photos'>" + list + "</div>";
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
