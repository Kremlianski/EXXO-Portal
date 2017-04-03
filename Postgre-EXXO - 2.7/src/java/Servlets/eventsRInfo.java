package Servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
//import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

public class eventsRInfo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");

        String date = request.getParameter("date");
        String id = request.getParameter("id");
        String r = request.getParameter("r");

        String xml = "<div>Событий не назначено</div>";
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        SimpleDateFormat sf = new SimpleDateFormat("H:mm");
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("f") >= 0) {
            yes = true;
        }
        if (date != null && !date.equals("") && r != null && !r.equals("")) {
            try {
                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);
                Statement stmt = con.createStatement();

                String sql1 = "select hours, minutes, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS Day, fio, user_id FROM eventsres where id_res='" + r + "' and day='" + date + "' ORDER BY hours, minutes";
                if (id != null && !id.equals("")) {
                    sql1 = "select hours, minutes, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS Day, fio, user_id FROM eventsres where id_res=(SELECT id_res FROM eventsres WHERE id='" + id + "') and day='" + date + "' ORDER BY hours, minutes";
                }
                ResultSet rs = stmt.executeQuery(sql1);
                while (rs.next()) {
                    if (rs.isFirst()) {
                        xml = "";
                    }
                    String pointed = "";
                    if (rs.getString("id").equals(id)) {
                        pointed = " class='pointed'";
                    }
                    GregorianCalendar start = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("Day"), rs.getInt("hours"), rs.getInt("minutes"));
                    GregorianCalendar fin = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("Day"), rs.getInt("hours"), rs.getInt("minutes") + rs.getInt("long"));
                    if (yes) {
                        if (user.equals(rs.getString("user_id"))) {
                            xml += "<div" + pointed + "><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <a href='eventR.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a></div>";
                        } else {
                            xml += "<div" + pointed + "><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <a href='eventR.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("fio") + "</a></div>";
                        }
                    } else {
                        if (user.equals(rs.getString("user_id"))) {
                            xml += "<div" + pointed + "><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <a href='eventR.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a></div>";
                        } else {
                            xml += "<div" + pointed + "><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <span>" + rs.getString("fio") + "</span></div>";
                        }
                    }
                }

                rs.close();
                stmt.close();
                con.close();

            } catch (SQLException ex) {
                xml = ex.getLocalizedMessage();
            }
        } else {
            xml = "ОШИБКА! Не заданы важные параметры!";
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
