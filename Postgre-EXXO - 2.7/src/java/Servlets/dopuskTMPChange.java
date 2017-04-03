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


public class dopuskTMPChange extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        //  String id=request.getParameter("id");
        String type = request.getParameter("type");
        String dopusk = request.getParameter("dopusk");
        String ser = request.getParameter("ser");
        if (ser == null) {
            ser = "files";
        }
        String res = "";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = (String) session.getAttribute("id");
        //    String l=" AND owner='"+owner+"' ";
        Boolean yes = true;

        String list = "";
        //       if(role.indexOf("a")>=0||role.indexOf("e")>=0||role.indexOf("Z")>=0) yes=true;
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        CallableStatement cstmt = con.prepareCall("{? = call blogtmpchange(?,?,?)}");
        cstmt.registerOutParameter(1, Types.VARCHAR);
        cstmt.setString(2, owner);
        cstmt.setString(3, type);
        cstmt.setString(4, dopusk);
        cstmt.executeUpdate();

        list += cstmt.getString(1);
        cstmt.close();

        con.close();

        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/plain");
            out.println(list);

        } finally {
            out.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(dopuskTMPChange.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(dopuskTMPChange.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
