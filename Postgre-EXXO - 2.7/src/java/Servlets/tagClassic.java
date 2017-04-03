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


public class tagClassic extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String superior = request.getParameter("superior");
        String id = request.getParameter("id");
        String res = "";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = (String) session.getAttribute("id");
        if (request.getParameter("owner") != null) {
            owner = request.getParameter("owner");
        }
        Boolean yes = false;
        yes = true;
        if (superior != null || id != null) {
            //       if(role.indexOf("a")>=0||role.indexOf("c")>=0||role.indexOf("Z")>=0) yes=true;
            if (yes) {
                try {
                    ServletContext sc = request.getServletContext();
                    Connection con = BASE.VER.getServletConnection(sc);
                    Statement stmt = con.createStatement();
                    String sql = "";

                    if (superior == null) {
                        sql = "SELECT superior FROM tags WHERE tag_id='" + id + "'";
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            superior = rs.getString("superior");
                        }
                    }

                    if (!superior.equals("1")) {
                        res += "<div class=\"up cat item\" id=\"1\">/</div>";
                        res += "<div class=\"up upper cat item\" id=\"" + superior + "\">../</div>";
                    }

                    sql = "SELECT tag_id, name, tag FROM tags WHERE superior='" + superior + "' ORDER BY name";

                    ResultSet rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                        if (rs.getString("tag").equals("0")) {
                            res += "<div class=\"item cat\" id=\"" + rs.getInt("tag_id") + "\">" + rs.getString("name") + "</div>";
                        } else {
                            res += "<div class=\"item fil\" id=\"" + rs.getInt("tag_id") + "\">" + rs.getString("name") + "</div>";
                        }
                    }

                    stmt.close();
                    con.close();

                } catch (SQLException e) {
                    res = e.getLocalizedMessage();
                }
                PrintWriter out = response.getWriter();
                try {
                    response.setContentType("text/html");
                    if (res.equals("")) {
                        res = "Тэгов пока нет";
                    }
                    out.print(res);

                } finally {
                    out.close();
                }

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
