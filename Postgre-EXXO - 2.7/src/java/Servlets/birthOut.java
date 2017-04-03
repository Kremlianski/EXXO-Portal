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
import javax.servlet.ServletContext;

public class birthOut extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        int width = java.lang.Integer.parseInt(request.getParameter("width"));
        String small = "exxo-width-normal";
        if (width <= 250) {
            small = "exxo-width-small";
        } else if (width > 400) {
            small = "exxo-width-big";
        }
        String text = "<div class='birthMain " + small + "'><a id='birthH3' href='birthday.xhtml'>Дни рождения</a><div class='" + small + "'><table id='birthdayList'><tbody>";

        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String[] wd = {"Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"};
            String[] wd1 = {"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
            String sql = "SELECT fio, id, extract(day FROM birthday), extract(dow FROM (extract(year from now())||'-'||extract(month from birthday)||'-'||extract(day FROM birthday))::date),  (extract(year from now())||'-'||extract(month from birthday)||'-'||extract(day FROM birthday))::date = now()::date AS now FROM employee where fired='0' AND (ABS(extract(day FROM (extract(year FROM now())||'-'||extract(month FROM birthday)||'-'||extract(day FROM birthday))::date-now()))<=7 OR ABS(extract(day FROM (extract(year FROM now())+1||'-'||extract(month FROM birthday)||'-'||extract(day FROM birthday))::date-now()))<=7) ORDER BY (extract(year FROM now())||'-'||extract(month FROM birthday)||'-'||extract(day FROM birthday))::date-now()";
            ResultSet rs = stmt.executeQuery(sql);
            boolean empty = true;

            while (rs.next()) {
                empty = false;
                String now = "";
                if (rs.getBoolean("now")) {
                    now = "class='today'";
                }
                if (width > 400) {
                    text += "<tr " + now + "><td class='birth'>" + wd1[(rs.getInt(4))] + ", " + rs.getString(3) + "</td><td class='birthFIO'><a href='empCard.jsp?id=" + rs.getString("id") + "'>" + rs.getString("fio") + "</a></td><td class='birthPhoto'><img src=\"IMG?id=" + rs.getString("id") + "\"></td></tr>";
                } else if (width > 200) {
                    text += "<tr " + now + "><td class='birth'>" + wd1[(rs.getInt(4))] + ", " + rs.getString(3) + "</td><td class='birthFIO'><a href='empCard.jsp?id=" + rs.getString("id") + "'>" + EXXOlib.textLib.shortFIO(rs.getString("fio")) + "</a></td><td class='birthPhoto'><img src=\"IMG?id=" + rs.getString("id") + "\"></td></tr>";
                } else {
                    text += "<tr " + now + "><td class='birth'>" + wd[(rs.getInt(4))] + ", " + rs.getString(3) + "</td><td class='birthFIO'><a href='empCard.jsp?id=" + rs.getString("id") + "'>" + EXXOlib.textLib.shortFIO(rs.getString("fio")) + "</a></td></tr>";
                }
            }

            if (empty) {
                text += "<tr><td class='empty'>Список пуст</td></tr>";
            }
            text += "</tbody></table></div></div>";
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            text = ex.getLocalizedMessage();
        }

        PrintWriter out = response.getWriter();
        try {

            out.println(text);

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
