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


public class dopuskTMPDrop extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("id");
        //   String type=request.getParameter("type");
        String dopusk = request.getParameter("dopusk");
        String res = "";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = (String) session.getAttribute("id");
        Boolean yes = true;
        //      if(role.indexOf("a")>=0||role.indexOf("e")>=0||role.indexOf("Z")>=0) yes=true;
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "";
            sql = "UPDATE blogesTMP SET dopusk='{" + dopusk + "}' WHERE owner='" + owner + "'";
            stmt.executeUpdate(sql);

            sql = "SELECT dopusk_type, dopusk FROM blogesTMP WHERE owner = '" + owner + "'";
            ResultSet rs = stmt.executeQuery(sql);
            boolean trig = true;
            int def = 0;
            ResultSet dopuskrs = null;
            String dopusk_type = "";
            if (rs.next()) {
                dopusk_type = rs.getString(1);
                java.sql.Array r = rs.getArray(2);
                if (r != null) {
                    dopuskrs = r.getResultSet();
                } else {
                    trig = false;
                }
            }
            if (trig) {
                while (dopuskrs.next()) {
                    if (dopuskrs.isFirst()) {
                        def = dopuskrs.getInt(2);
                    }
                    if (dopusk_type.equals("4")) {
                        sql = "SELECT fio FROM employee WHERE global_id='" + dopuskrs.getInt(2) + "'";
                        ResultSet rr = stmt.executeQuery(sql);
                        while (rr.next()) {
                            res += "<div class='emps' id='" + dopuskrs.getInt(2) + "'>" + rr.getString(1) + "</div>";
                        }
                        rr.close();
                    }

                }
            }

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
