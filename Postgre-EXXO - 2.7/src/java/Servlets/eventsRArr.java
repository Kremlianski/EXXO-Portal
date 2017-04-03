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

public class eventsRArr extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/plain;charset=UTF-8");
        String date = request.getParameter("date");
        String id = request.getParameter("id");
        String r = request.getParameter("r");
        if (id == null || id.equals("")) {
            id = "tratata";
        }
        String arr = "";
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        if (r != null && !r.equals("")) {
            try {
                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);
                Statement stmt = con.createStatement();

                String sql1 = "select hours, minutes, long from eventsres where id_res='" + r + "' and day='" + date + "' AND id<>'" + id + "' ORDER BY hours, minutes";
                if (!id.equals("tratata")) {
                    sql1 = "select hours, minutes, long FROM eventsres WHERE id_res=(SELECT id_res FROM eventsres WHERE id='" + id + "')  and day='" + date + "' AND id<>'" + id + "' ORDER BY hours, minutes";
                }
                ResultSet rs = stmt.executeQuery(sql1);

                while (rs.next()) {
                    int hm = rs.getInt(1) * 4 + rs.getInt(2) / 15 - 32;
                    for (int l = 0; l < rs.getInt(3) / 15; l++) {
                        arr += hm + l + " ";
                    }
                }

                rs.close();
                stmt.close();
                con.close();

            } catch (SQLException ex) {
                arr = ex.getLocalizedMessage();
            }
        }
        PrintWriter out = response.getWriter();
        try {

            out.print(arr);

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
            Logger.getLogger(eventsArr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(eventsArr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(eventsArr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(eventsArr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
