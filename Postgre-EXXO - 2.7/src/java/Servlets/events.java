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
import java.util.ArrayList;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


public class events extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        String date = request.getParameter("date");
        String hours = request.getParameter("hours");
        String minutes = request.getParameter("minutes");
        String name = request.getParameter("name");
        String Long = request.getParameter("Long");
//    String descr=request.getParameter("descr");

        String res = "OK";
        if (id.startsWith("events")) {
            try {

                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);
                Statement stmt = con.createStatement();

                String sql3 = "select hours, minutes, long from events where user_id='" + user + "' AND day='" + date + "' AND id<>'" + id + "' ORDER BY hours, minutes";
                ResultSet rs = stmt.executeQuery(sql3);

                ArrayList<Integer> list = new ArrayList<Integer>();
                while (rs.next()) {
                    int hm = rs.getInt(1) * 4 + rs.getInt(2) / 15 - 32;
                    int length = rs.getInt(3) / 15;
                    for (int l = 0; l < length; l++) {
                        list.add(hm + l);
                    }
                }

                boolean trig = true;
                int hm1 = java.lang.Integer.parseInt(hours) * 4 + java.lang.Integer.parseInt(minutes) / 15 - 32;
                int length = java.lang.Integer.parseInt(Long) / 15;
                for (int l = 0; l < length; l++) {
                    if (list.contains(hm1 + l)) {
                        trig = false;
                        res = "bad";
                        break;
                    }
                }

                if (trig) {

                    String sql1 = "select * from events where id='" + id + "'";
                    rs = stmt.executeQuery(sql1);
                    if (rs.next()) {
                        String sql2 = "update events set user_id='" + user + "' , day='" + date + "' , hours='" + hours + "', minutes='" + minutes + "', name='" + name + "', long='" + Long + "' where  id='" + id + "'";
                        stmt.executeUpdate(sql2);
                    } else {
                        String sql = "insert into events (id, user_id, day, hours, minutes, name, long) values('" + id + "','" + user + "','" + date + "','" + hours + "','" + minutes + "','" + name + "','" + Long + "')";
                        stmt.executeUpdate(sql);
                    }
                }
                rs.close();
                stmt.close();
                con.close();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            }

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
            Logger.getLogger(events.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(events.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
