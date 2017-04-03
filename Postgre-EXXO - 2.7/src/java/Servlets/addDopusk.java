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


public class addDopusk extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String dopusk = request.getParameter("dopusk");
        String ser = request.getParameter("s");
        String p = request.getParameter("p");
        String unit = request.getParameter("uid");
        String uid = "";
        if (unit != null) {
            uid = "&uid=" + unit;
        }
        if (p == null) {
            p = "list";
        }
        if (ser == null) {
            ser = "files";
        }
        String res = "";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = request.getParameter("owner");
        if (ser.equals("bloges")) {
            owner = (String) session.getAttribute("id");
        }
        String l = " AND owner='" + owner + "' ";
        Boolean yes = false;
        if (ser.equals("files") && owner != null && ((role.indexOf("o") >= 0 && owner.equals("-100")) || (role.indexOf("p") >= 0 && owner.equals("-101"))
                || (role.indexOf("q") >= 0 && owner.equals("-102")) || (role.indexOf("r") >= 0 && owner.equals("-103")))) {
            yes = true;
        }
        //      if(owner==null) owner=(String)session.getAttribute("id");
        if (yes) {
            l = "";
        }
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "SELECT dopusk_type FROM files WHERE id=" + id;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                type = rs.getString(1);
            }
            if (!type.equals("4")) {
                response.sendRedirect("notPermited.html");
            } else //очень сложно. Проще через js как удаление
            {
                sql = "UPDATE " + ser + " SET dopusk=array_append((SELECT dopusk FROM " + ser + " WHERE id='" + id + "')," + dopusk + ") WHERE id='" + id + "' " + l
                        + " AND ((NOT (SELECT dopusk FROM " + ser + " WHERE id='" + id + "')@>'{" + dopusk + "}' AND (SELECT dopusk FROM " + ser + " WHERE id='" + id + "') IS NOT NULL) OR "
                        + " (SELECT dopusk FROM " + ser + " WHERE id='" + id + "') IS NULL)";
            }
            stmt.executeUpdate(sql);

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            res = e.getLocalizedMessage();
        }

        if (ser.equals("files")) {
            if (p.equals("list")) {
                response.sendRedirect("addDopusk.jsp?id=" + id + "&owner=" + owner);
            } else if (p.equals("photo")) {
                response.sendRedirect("addDopPhoto.jsp?id=" + id + "&owner=" + owner);
            } else if (p.equals("comp")) {
                response.sendRedirect("addDopComp.jsp?id=" + id + uid + "&owner=" + owner);
            }
        } else {
            response.sendRedirect("index.jsp");
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
