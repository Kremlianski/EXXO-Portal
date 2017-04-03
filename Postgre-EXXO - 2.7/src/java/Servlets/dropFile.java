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


public class dropFile extends HttpServlet {

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
        int store = 0;
        int ver_id = 0;
        boolean isStep_6 = false;
        boolean yes = true;
        yes = false;
        String own = "";
        if (owner != null) {
            own = "&owner=" + owner;
        }
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
                String sql3 = "";
                String sql2 = "SELECT store, ver_id, step>=6 AS isStep_6 FROM files_vers, files WHERE files.id=" + id + " AND owner=" + owner + " AND files.id=files_vers.id";
                ResultSet rs2 = stmt.executeQuery(sql2);
                while (rs2.next()) {
                    store = rs2.getInt(1);
                    ver_id = rs2.getInt(2);
                    isStep_6 = rs2.getBoolean("isStep_6");
                    if (!isStep_6) {
                        try {
                            sql3 = "DELETE FROM file_store WHERE id=" + ver_id;
                            Connection con1 = BASE.VER.getDocsConnection(sc, store);
                            Statement stmt1 = con1.createStatement();
                            int co = stmt1.executeUpdate(sql3);// было отключено зачем-то
                            stmt1.close();
                            con1.close();
                        } catch (SQLException e) {
                            Statement stmt3 = con.createStatement();
                            Logger.getLogger(dropFile.class.getName()).log(Level.SEVERE, null, e);
                            sql3 = "INSERT INTO lost_vers VALUES (" + ver_id + "," + store + ")";
                            stmt3.executeUpdate(sql3);
                            stmt3.close();
                        }
                    }
                }

                rs2.close();

                if (!isStep_6) {
                    String sql = "DELETE FROM files WHERE (id=" + id + " OR copy=" + id + ") AND owner='" + owner + "'";
                    stmt.executeUpdate(sql);
                }
// подумать, что делать, если хранилище недоступно. Скорее всего, нужно пытаться сперва удалять бинарники, затем версии, затем уже запись о файле...
//   Посмотреть, нужны ли внешние ключи

                stmt.close();
                con.close();
            } catch (SQLException e) {
                res = e.getLocalizedMessage();
                Logger.getLogger(dropFile.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(dropFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dropFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
