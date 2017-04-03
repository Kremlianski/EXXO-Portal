package Servlets;

//import java.io.IOException;
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

/**
 *
 * @author alqumisto
 */
public class Calendar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String date = request.getParameter("date");
        String xml = request.getParameter("xml");
        String user = request.getParameter("user");
        String res = "OK";
        try {

            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql1 = "select xml from calendar where user_id='" + user + "' and day='" + date + "'";
            ResultSet rs = stmt.executeQuery(sql1);
            if (rs.next()) {
                String sql2 = "update calendar set xml='" + xml + "' where  user_id='" + user + "' and day='" + date + "'";
                stmt.executeUpdate(sql2);
            } else {
                String sql = "insert into calendar values('" + user + "','" + date + "','" + xml + "')";
                stmt.executeUpdate(sql);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            res = e.getLocalizedMessage();
        }
        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/plain");
            out.println(res);

        } finally {
            out.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
