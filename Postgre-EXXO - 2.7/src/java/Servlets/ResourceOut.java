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

public class ResourceOut extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/xml;charset=UTF-8");
        String date = request.getParameter("date");
        String xml = "<xml></xml>";
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        String id = request.getParameter("id");

        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql1 = "select xml from resource where id='" + id + "' and day='" + date + "'";
            String sql2 = "INSERT INTO restimer (id, resource, day) values ('" + user + "', '" + id + "', '" + date + "')";
            String sql3 = "DELETE FROM restimer WHERE id='" + user + "'AND resource='" + id + "' AND day='" + date + "'";
            ResultSet rs = stmt.executeQuery(sql1);
            if (rs.next()) {
                xml = rs.getString("xml");
            }
            stmt.executeUpdate(sql3);
            stmt.executeUpdate(sql2);
            con.close();
            stmt.close();
            rs.close();

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
            Logger.getLogger(CalendarOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CalendarOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CalendarOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CalendarOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
