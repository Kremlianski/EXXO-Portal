package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DocClassic {

    public int c = 0;
    public int v = 0;
    public int free = 100;
    public String href = "";

    public void getParams(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        HttpSession session = r.getSession(true);
        String owner = (String) session.getAttribute("id");
        if (r.getParameter("owner") != null) {
            owner = r.getParameter("owner");
        }
        String sql = "SELECT count(*), SUM(size) FROM files, files_vers WHERE file=1 AND files.id=files_vers.id AND owner=" + owner;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            c = rs.getInt(1);
            v = rs.getInt(2);
        }
        free = (int) ((BASE.VER.getMaxFilesSize(sc) - v) * 100 / BASE.VER.getMaxFilesSize(sc));
        rs.close();
        stmt.close();
        con.close();

        if (owner.startsWith("-")) {
            href = "<div id=\"menuList\"><a href=\"newDocK.jsp?own=" + owner + "\" id=\"addgroup\">Список документов</a></div>";
        } else if (r.getParameter("owner") != null) {
            href = "<div id=\"menuList\"><a href=\"empDoc.jsp?own=" + owner + "\" id=\"addgroup\">Список документов</a></div>";
        } else {
            href = "<div id=\"menuList\"><a href=\"empDoc.jsp\" id=\"addgroup\">Список документов</a></div>";
        }
    }

}
