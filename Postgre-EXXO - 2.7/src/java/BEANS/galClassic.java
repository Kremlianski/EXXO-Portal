package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class galClassic {

    public int c = 0;
    public int v = 0;
    public int free = 100;
    public String fio = "";
    public String count = "0";
    public String respcount = "0";
    public String commcount = "0";
    public boolean editor = true;

    public void getParams(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        HttpSession session = r.getSession(true);
        String role = (String) session.getAttribute("role");

        String owner = (String) session.getAttribute("id");
        String user = r.getParameter("owner");

//проверка на право редактирования
        if (user != null && !owner.equals(user)) {
            if (role.indexOf("a") >= 0 || role.indexOf("d") >= 0) {
                if (!user.equals("-100")) {
                    editor = false;
                }
            } else {
                editor = false;
            }
        }
//
        if (user != null) {
            owner = user;
        }
        String sql = "SELECT get_fio(" + owner + "), gal_cat(" + owner + "::Int), gal_pic(" + owner + "::Int)";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            fio = EXXOlib.textLib.shortFIO(rs.getString(1));
            c = rs.getInt(2);
            v = rs.getInt(3);

        }
        sql = "SELECT count(*) FROM registor WHERE global_id IN (SELECT global_id FROM gallaries WHERE owner=" + owner + ")";
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            count = rs.getString(1);
        }

        sql = "SELECT count(*) FROM respects WHERE global_id IN (SELECT global_id FROM gallaries WHERE owner=" + owner + ")";
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            respcount = rs.getString(1);
        }

        sql = "SELECT count(*) FROM picsC WHERE pics_id IN (SELECT id FROM gallaries WHERE owner=" + owner + ")";
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            commcount = rs.getString(1);
        }

        fio = "<a href='empCard.jsp?id=" + owner + "'>" + fio + "</a>";
        rs.close();
        stmt.close();
        con.close();

    }

}
