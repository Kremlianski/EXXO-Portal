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

public class eventsInfo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");

        String date = request.getParameter("date");
        String id = request.getParameter("id");
        String xml = "<div>Событий не назначено</div>";
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        SimpleDateFormat sf = new SimpleDateFormat("H:mm");

        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();

            String sql1 = "select hours, minutes, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS Day from events where user_id='" + user + "' and day='" + date + "' ORDER BY hours, minutes";
            ResultSet rs = stmt.executeQuery(sql1);
            String dates = "";
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

                xml += "<div" + pointed + "><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <a href='event.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a></div>";
            }

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
