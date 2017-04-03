package Servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

public class eventsVid extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String[] Week = {"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        java.util.Calendar calendar = GregorianCalendar.getInstance();
        String time = Week[calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(java.util.Calendar.DAY_OF_MONTH);
        Date date = new Date();
        String xml = "<div class=\"calendar\"><a class=\"cal\" href=\"calendar.jsp\">" + time + "</a>";
        String list = "<span class=\"red\">Пока ничего не назначено</span>";

        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        SimpleDateFormat sf = new SimpleDateFormat("H:mm");
        int width = java.lang.Integer.parseInt(request.getParameter("width"));
        String small = "exxo-width-normal";
        if (width <= 250) {
            small = "exxo-width-small";
        } else if (width > 400) {
            small = "exxo-width-big";
        }

        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();

            String sql1 = "SELECT hours, minutes, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS Day FROM events WHERE user_id='" + user + "' AND day=current_date ORDER BY hours, minutes";
            ResultSet rs = stmt.executeQuery(sql1);
            while (rs.next()) {
                if (rs.isFirst()) {
                    list = "";
                }
                GregorianCalendar start = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("Day"), rs.getInt("hours"), rs.getInt("minutes"));
                GregorianCalendar fin = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("Day"), rs.getInt("hours"), rs.getInt("minutes") + rs.getInt("long"));

                list += "<div class='" + small + "'><span class='blue'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <a href='event.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a></div>";
            }
            xml += list + "</div>";

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            xml = ex.getLocalizedMessage();
        }

        PrintWriter out = response.getWriter();
        try {

            out.println(xml);

        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(eventsOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(eventsOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(eventsOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(eventsOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
