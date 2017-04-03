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

public class eventsOut extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/plain;charset=UTF-8");
        String date = request.getParameter("date");
        String xml = "";
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");

        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();

            String sql1 = "select hours, minutes, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS Day from events where user_id='" + user + "' and day='" + date + "' ORDER BY hours, minutes";
            ResultSet rs = stmt.executeQuery(sql1);
            xml = "";
            while (rs.next()) {
                xml += "" + rs.getString("hours") + "\t" + rs.getString("minutes") + "\t" + rs.getString("name") + "\t" + rs.getString("long") + "\t" + rs.getString("id") + "\n";
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            xml = ex.getLocalizedMessage();
        }

        PrintWriter out = response.getWriter();
        try {

            out.print(xml);

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
