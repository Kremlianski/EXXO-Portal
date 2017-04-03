package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class photosBean {

    public String navi = "";
    public int c = 0;
    public int v = 0;
    public int free = 100;
    public String fio = "";
    public String count = "0";
    public String respcount = "0";
    public String commcount = "0";

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        HttpSession session = r.getSession(true);
        String owner = (String) session.getAttribute("id");
        String own = "";
        if (r.getParameter("owner") != null) {
            owner = r.getParameter("owner");
            own = "&owner=" + owner;
        }

        String offset = "0";
        if (r.getParameter("of") != null) {
            offset = r.getParameter("of");
        }
        boolean next = false;
        int ROWS = BASE.VER.getMaxBloks(sc);
        int rows = ROWS + 1;
        String sql = "SELECT id, owner, superior, name, get_fio(owner) AS fio  FROM gallaries WHERE file='1' AND owner=" + owner + "ORDER BY created DESC LIMIT " + rows + " OFFSET " + offset;
        ResultSet rs = stmt.executeQuery(sql);
        String list = "";

        int cc = 0;

        while (rs.next()) {
            if (rs.isLast() && cc == rows - 1) {
                next = true;
            } else {
                cc++;
                //   list+="<a class='img' href=\"galClassic.jsp?owner="+rs.getString(2)+"&id="+rs.getString(3)+"\" style='background-image:url(\"galLoader?id="+rs.getString("id")+"&icon=1\")'> </a>";
                list += " <div class=\"item fil\"><a  href=\"galLoader?id=" + rs.getInt("id") + "\" title=\"" + rs.getString("name") + "\""
                        + "  style=\"background-image: url('galLoader?id=" + rs.getString("id") + "&icon=1');\" class=\"exxo-album-img exxo-loading\"></a><div>"
                        + "<span onClick=\"location='pic.jsp?id=" + rs.getString("id") + "'\" class='picName'>" + rs.getString("name") + "</span>"
                        + "<span class='picOwner'>" + EXXOlib.textLib.shortFIO(rs.getString("fio")) + "</span></div></div>";
            }
        }

        sql = "SELECT get_fio(" + owner + "), gal_cat(" + owner + "::Int), gal_pic(" + owner + "::Int)";
        rs = stmt.executeQuery(sql);
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

        navi += "<div id='menu-navi'><table><tr><td class='oneNavi'>";
        String andown = own;

        int of = Integer.parseInt(offset);
        if (of > 0) {
            int prev = of - ROWS;
            if (prev < 0) {
                prev = 0;
            }
            navi += "<a href='photos.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + ROWS;
            navi += "<a href='photos.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";
        return list;
    }
}
