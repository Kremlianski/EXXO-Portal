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
//добавить проверку, запрещающую удалять вглубь или с сотруднииками


public class killUnit extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("p1");
        //   String name=request.getParameter("p2");
        String res = "OK";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("c") >= 0 || role.indexOf("Z") >= 0) {
            yes = true;
        }
        if (yes) {
            try {

                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);
                Statement stmt = con.createStatement();

                String sql1 = "SELECT unit_id FROM units WHERE superior='" + id + "'";
                ResultSet rs1 = stmt.executeQuery(sql1);
                if (!rs1.next() && !id.equals("1")) {
                    res = "3";
                    String sql2 = "SELECT id FROM employee WHERE unit='" + id + "'";
                    ResultSet rs2 = stmt.executeQuery(sql2);
                    if (!rs2.next()) {
                        res = "4";
                        String sql = "delete from units where unit_id='" + id + "'";
                        int in = stmt.executeUpdate(sql);
                        res = "" + in;
                    } else {
                        res = "2";
                    }
                    rs2.close();
                } else {
                    res = "1";
                }
                rs1.close();

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
