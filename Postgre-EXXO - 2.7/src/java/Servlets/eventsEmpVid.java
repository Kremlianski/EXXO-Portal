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
//import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

public class eventsEmpVid extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String date = request.getParameter("date");
        String id = request.getParameter("id");
        boolean trig = false;
        java.util.Calendar calendar = GregorianCalendar.getInstance();
        String[] ar;
        if (date != null) {
            ar = date.split("-");
            if (ar.length == 3) {
                calendar.set(java.lang.Integer.parseInt(ar[0]),
                        java.lang.Integer.parseInt(ar[1]) - 1,
                        java.lang.Integer.parseInt(ar[2]));
                trig = true;
            }
        }

        String[] Week = {"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};

        String time = Week[calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(java.util.Calendar.DAY_OF_MONTH);

        String xml = "<div class=\"calendar\"><div id=\"timer\">" + time + "</div>";
        String list = "<span class=\"red\">Пока ничего не назначено</span>";
        String images = "";

        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        if (id != null) {
            user = id;
        }
        SimpleDateFormat sf = new SimpleDateFormat("H:mm");

        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();

            String sql1 = "SELECT hours, minutes, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS Day, important, outside, nophone FROM events WHERE user_id='" + user + "' AND day=current_date ORDER BY hours, minutes";
            if (trig) {
                sql1 = "SELECT hours, minutes, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS Day, important, outside, nophone FROM events WHERE user_id='" + user + "' AND day='" + date + "' ORDER BY hours, minutes";
            }
            ResultSet rs = stmt.executeQuery(sql1);
            while (rs.next()) {
                if (rs.isFirst()) {
                    list = "";
                }
                images = "";
                if (rs.getBoolean("important")) {
                    images += "<img src='small/important.png'>";
                }
                if (rs.getBoolean("outside")) {
                    images += "<img src='small/door_out.png'>";
                }
                if (rs.getBoolean("nophone")) {
                    images += "<img src='small/phone_stop.png'>";
                }
                GregorianCalendar start = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("Day"), rs.getInt("hours"), rs.getInt("minutes"));
                GregorianCalendar fin = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("Day"), rs.getInt("hours"), rs.getInt("minutes") + rs.getInt("long"));

                list += "<div class='event'><span class='blue'>с " + sf.format(start.getTime()) + " до " + sf.format(fin.getTime()) + "</span>" + images + "</div>";
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
