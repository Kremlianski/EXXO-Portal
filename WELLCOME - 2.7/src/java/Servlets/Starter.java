package Servlets;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;

public class Starter extends HttpServlet {

    private static final boolean debug = true;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        ServletContext sc = request.getServletContext();
        String res = "OK";

        boolean yes = false;
        if (debug) {
            yes = true;
        }
        if (yes) {
            try {

                Connection con = BASE.VER.getServletConnection(sc);
                con.setAutoCommit(false);
                Statement stmt = con.createStatement();

                // String sql = "DROP TABLE IF EXISTS register CASCADE";
                //stmt.executeUpdate(sql);
                String sql = "create table register ("
                        + " key varchar(250) not null,"
                        + "  reg varchar(250) not null,"
                        + "  primary key (key))";

                stmt.executeUpdate(sql);

                sql = "INSERT INTO register VALUES('iLikeEXXO','EXXO')";
                stmt.executeUpdate(sql);

                sql = "CREATE TABLE users ("
                        + "id Int, "
                        + "login varchar(250) NOT NULL, "
                        + "pass varchar(50) NOT NULL, "
                        + "PRIMARY KEY (login))";

                stmt.executeUpdate(sql);

                sql = "insert into users(id, login, pass) values(0,'root', MD5('iLikeEXXO'))";
                stmt.executeUpdate(sql);

                sql = "create table user_roles ( "
                        + "login varchar(250) not null REFERENCES users (login) ON DELETE CASCADE ON UPDATE CASCADE, "
                        + "role varchar(250) not null, "
                        + "primary key (login, role))";
                stmt.executeUpdate(sql);

                sql = "insert into user_roles values ('root', 'portal_admin')";
                stmt.executeUpdate(sql);

                sql = "insert into user_roles values ('root', 'portal_user')";
                stmt.executeUpdate(sql);
                con.commit();
                stmt.close();
                con.close();
            } catch (SQLException e) {
                res = e.getLocalizedMessage();
                PrintWriter out = response.getWriter();
                out.print(res);
                out.close();
            }
            response.sendRedirect("index.xhtml");

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (debug) {
                processRequest(request, response);
            }
        } catch (ClassNotFoundException ex) {

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
