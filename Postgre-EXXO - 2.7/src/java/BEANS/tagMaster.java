package BEANS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class tagMaster {

    private static boolean permitted = false;

    public static boolean permitted(String id, String owner, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT owner='" + owner + "' FROM tags WHERE tag_id = '" + id + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            permitted = rs.getBoolean(1);
        }
        rs.close();
        stmt.close();
        con.close();
        return permitted;
    }

    public static boolean superPermitted(String id, String owner, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        boolean superpermitted = false;
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT owner='" + owner + "' FROM tags WHERE tag_id = (SELECT superior FROM tags WHERE tag_id = '" + id + "')";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            permitted = rs.getBoolean(1);
        }
        rs.close();
        stmt.close();
        con.close();
        return superpermitted;
    }

    public static boolean cleverTest(String owner, String server, String id, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        ResultSet rs = null;
        boolean res = false;
        String sql = "";
        String own = "";
        HttpSession session = r.getSession();
        String role = (String) session.getAttribute("role");
        if (server.equals("bloges") || server.equals("files") || server.equals("gallaries")) {
            sql = "SELECT owner=" + owner + ", owner FROM " + server + " WHERE id='" + id + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                res = rs.getBoolean(1);
                own = rs.getString(2);
            }
        } else if (server.equals("news")) {
            sql = "SELECT role FROM employee WHERE id='" + owner + "'";
            rs = stmt.executeQuery(sql);
            //проверить, что-то не так
            if (rs.next()) {
                res = rs.getString(1).indexOf("g") >= 0;
            }
        }
        if (server.equals("files") && (((role.indexOf("o") >= 0 && own.equals("-100")) || (role.indexOf("p") >= 0 && own.equals("-101"))
                || (role.indexOf("q") >= 0 && own.equals("-102")) || (role.indexOf("r") >= 0 && own.equals("-103"))))) {
            res = true;
        }

        if (server.equals("gallaries") && ((role.indexOf("a") >= 0 || role.indexOf("a") >= 0) && own.equals("-100"))) {
            res = true;
        }
        rs.close();
        stmt.close();
        con.close();
        return res;
    }

}
