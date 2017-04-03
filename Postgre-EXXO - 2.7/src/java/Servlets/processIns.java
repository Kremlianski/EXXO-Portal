package Servlets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.Properties;
import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class processIns extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String res = "ok";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("h") >= 0) {
            yes = true;
        }
        String id = request.getParameter("id");
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);

        if (yes) {
            int open = 0;
            int deside = 0;
            int done = 0;

            if (request.getParameter("minToDo") != null && !request.getParameter("minToDo").equals("")) {
                done = java.lang.Integer.parseInt(request.getParameter("minToDo")) * java.lang.Integer.parseInt(request.getParameter("toDo"));
            }

            if (request.getParameter("minToDeside") != null && !request.getParameter("minToDeside").equals("")) {
                deside = java.lang.Integer.parseInt(request.getParameter("minToDeside")) * java.lang.Integer.parseInt(request.getParameter("toDeside"));
            }

            if (request.getParameter("minToOpen") != null && !request.getParameter("minToOpen").equals("")) {
                open = java.lang.Integer.parseInt(request.getParameter("minToOpen")) * java.lang.Integer.parseInt(request.getParameter("toOpen"));
            }
            String stoped = "0";
            if (request.getParameter("stoped") != null) {
                stoped = request.getParameter("stoped");
            }
            String sql = "INSERT INTO process (name,description,owner,supervisor,template,type,minToDo,minToDeside,minToOpen) values (?, ?, ?::int, ?::int, ?, ?, ?, ?, ?)";
            if (id != null) {
                sql = "UPDATE process SET name=?, description=?, owner=?::int, supervisor=?::int, template=?, type=?, "
                        + "minToDo=?, minToDeside=?,  minToOpen=?, stoped=?::smallint WHERE id=?::int";
            }
            ////было replace. Может чего и не дожелал?
            PreparedStatement ps = null;
            try {

                con.setAutoCommit(false);
                ps = con.prepareStatement(sql);
                ps.setString(1, request.getParameter("name"));
                ps.setString(2, request.getParameter("description").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(3, request.getParameter("owner"));
                ps.setString(4, request.getParameter("supervisor"));
                ps.setString(5, request.getParameter("template"));
                ps.setString(6, request.getParameter("type"));
                ps.setInt(7, done);
                ps.setInt(8, deside);
                ps.setInt(9, open);

                if (id != null) {
                    ps.setString(10, stoped);
                    ps.setString(11, id);
                }
                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            } finally {
                ps.close();
                con.close();
            }

            out.println(res);
            if (id != null) {
                response.sendRedirect("processUpd.jsp?id=" + id);
            } else {
                response.sendRedirect("close.html");
            }

        } else {
            response.sendRedirect("notPermited.html");
        }
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
