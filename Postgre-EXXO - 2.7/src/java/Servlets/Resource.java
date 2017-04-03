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
import javax.servlet.http.HttpSession;

public class Resource extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String date = request.getParameter("date");
        String xml = request.getParameter("xml");
        String id = request.getParameter("id");
        String res = "OK";
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        try {

            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql1 = "select extract(epoch FROM ts) from resource where id='" + id + "' and day='" + date + "'";
            ResultSet rs = stmt.executeQuery(sql1);
            if (rs.next()) {
                int TS = rs.getInt(1);
                String sql3 = "SELECT extract(epoch FROM ts) FROM restimer WHERE id='" + user + "' AND resource='" + id + "' and day='" + date + "'";
                ResultSet rs3 = stmt.executeQuery(sql3);
                if (rs3.next()) {
                    int TS1 = rs3.getInt(1);
                    if (TS1 >= TS) {
                        String sql2 = "update resource set xml='" + xml + "' where  id='" + id + "' and day='" + date + "'";
                        stmt.executeUpdate(sql2);
                    } else {
                        res = "BAD";
                    }
                }
            } else {
                String sql = "insert into resource (id,day,xml) values('" + id + "','" + date + "', '" + xml + "')";
                stmt.executeUpdate(sql);
            }

            con.close();
            stmt.close();
            rs.close();

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
