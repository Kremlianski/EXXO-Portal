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
import java.util.ArrayList;


public class eventsR extends HttpServlet {

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
        String fio = request.getParameter("fio");
        String r = request.getParameter("r");
//    String descr=request.getParameter("descr");

        String res = "OK";
        if (id.startsWith("events")) {
            try {

                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);
                Statement stmt = con.createStatement();

                String sql3 = "select hours, minutes, long from eventsres where id_res='" + r + "' AND day='" + date + "' AND id<>'" + id + "' ORDER BY hours, minutes";
                ResultSet rs = stmt.executeQuery(sql3);
                /*
String[] timer = {"8:00","8:15","8:30","8:45","9:00","9:15","9:30","9:45","10:00","10:15","10:30","10:45","11:00",
"11:15","11:30","11:45","12:00","12:15","12:30","12:45","13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45",
"15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00","17:15","17:30","17:45","18:00","18:15","18:30","18:45",
"19:00","19:15","19:30","19:45","20:00","20:15","20:30","20:45","21:00","21:15","21:30","21:45",
"22:00","22:15","22:30","22:45","23:00","23:15","23:30","23:45","24:00"};
                 */
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
                    String sql1 = "SELECT * FROM eventsres WHERE id='" + id + "'";
                    rs = stmt.executeQuery(sql1);
                    if (rs.next()) {
                        String sql2 = "UPDATE eventsres SET user_id='" + user + "' , day='" + date + "' , hours='" + hours + "', minutes='" + minutes + "', name='" + name + "', long='" + Long + "', fio='" + fio + "' WHERE  id='" + id + "'";
                        stmt.executeUpdate(sql2);
                    } else {
                        String sql = "INSERT INTO eventsres (id, user_id, day, hours, minutes, name, long, id_res, fio) VALUES('" + id + "','" + user + "','" + date + "','" + hours + "','" + minutes + "','" + name + "','" + Long + "','" + r + "', '" + fio + "')";
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
            out.print(res);

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
