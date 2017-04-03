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


public class killCat extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("p1");
        //   String name=request.getParameter("p2");
        String res = "OK";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = request.getParameter("owner");
        String r = request.getParameter("r");
        String cat = request.getParameter("c");
        String own = "";
        if (owner != null) {
            own = "&owner=" + owner;
        }
        Boolean yes = false;
        if (owner == null || (role.indexOf("o") >= 0 && owner.equals("-100")) || (role.indexOf("p") >= 0 && owner.equals("-101"))
                || (role.indexOf("q") >= 0 && owner.equals("-102")) || (role.indexOf("r") >= 0 && owner.equals("-103"))) {
            yes = true;
        }
        if (owner == null) {
            owner = (String) session.getAttribute("id");
        }
        if (yes) {
            try {

                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);
                Statement stmt = con.createStatement();
// подумать, что делать, если хранилище недоступно. Скорее всего, нужно пытаться сперва удалять бинарники, затем версии, затем уже запись о файле...
//   Посмотреть, нужны ли внешние ключи

                String sql1 = "SELECT id FROM files WHERE superior='" + id + "'";
                ResultSet rs1 = stmt.executeQuery(sql1);
                if (!rs1.next()) {
                    String sql = "delete from files where id='" + id + "' and owner='" + owner + "'";
                    int in = stmt.executeUpdate(sql);
                    res = "" + in;

                } else {
                    res = "1";
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
                if (r != null) {
                    response.sendRedirect("docClassic.jsp?id=" + cat + own);
                }
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
