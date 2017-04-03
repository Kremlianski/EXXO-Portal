package Servlets;

import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class calCompare extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("dopusk");
        String p = request.getParameter("p");
        String unit = request.getParameter("uid");
        String date = request.getParameter("date");
        String ldate = "";
        String ldate1 = "";
        if (date != null) {
            ldate = "?date=" + date;
            ldate1 = "&date=" + date;
        }
        String uid = "";
        if (unit != null) {
            uid = "?uid=" + unit;
        }
        if (p == null) {
            p = "list";
        }

        HttpSession session = request.getSession(true);
        String owner = (String) session.getAttribute("id");
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();

            //очень сложно. Проще через js как удаление
            String sql = "INSERT INTO calCimpare VALUES (" + owner + "," + id + ")";
            stmt.executeUpdate(sql);

            stmt.close();
            con.close();

        } catch (SQLException e) {
        }

        if (p.equals("list")) {
            response.sendRedirect("calendarList.jsp" + ldate);
        } else if (p.equals("photo")) {
            response.sendRedirect("calendarPhoto.jsp" + ldate);
        } else if (p.equals("comp")) {
            response.sendRedirect("calendarComp.jsp" + uid + ldate1);
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
