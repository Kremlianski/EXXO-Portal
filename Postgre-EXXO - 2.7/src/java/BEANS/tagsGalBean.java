package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class tagsGalBean {

    public String navi = "";

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String offset = "0";
        if (r.getParameter("of") != null) {
            offset = r.getParameter("of");
        }
        boolean next = false;
        int ROWS = BASE.VER.getMaxBloks(sc);
        int rows = ROWS + 1;
        int cc = 0;
        HttpSession session = r.getSession(true);
        String owner = (String) session.getAttribute("id");
        String s = "gallaries";
        String res = "";
        String sql = "SELECT tag_id, status FROM found_tags WHERE owner='" + owner + "' ORDER BY status";
        ResultSet rs = stmt.executeQuery(sql);
        String[] Ar = {"", "AND ", "OR ", "AND NOT"};
        while (rs.next()) {
            if (rs.isFirst()) {
                if (rs.getInt(2) != 3) {
                    res += " " + s + ".global_id IN (SELECT global_id FROM global_tags WHERE tag_id='" + rs.getString(1) + "') ";
                } else {
                    res += " NOT  " + s + ".global_id IN (SELECT global_id FROM global_tags WHERE tag_id='" + rs.getString(1) + "') ";
                }
            } else {
                res += Ar[rs.getInt(2)] + " " + s + ".global_id IN (SELECT global_id FROM global_tags WHERE tag_id='" + rs.getString(1) + "') ";
            }
        }

        sql = "SELECT id, owner, superior, name, get_fio(owner) AS fio  FROM gallaries WHERE file='1' "
                + "AND (" + res + ") ORDER BY created DESC LIMIT " + rows + " OFFSET " + offset;
        rs = stmt.executeQuery(sql);
        String list = "";
        while (rs.next()) {
            if (rs.isLast() && cc == rows - 1) {
                next = true;
            } else {
                cc++;
                list += " <div class=\"item fil\"><a  href=\"galLoader?id=" + rs.getInt("id") + "\" title=\"" + rs.getString("name") + "\""
                        + "  style=\"background-image: url('galLoader?id=" + rs.getString("id") + "&icon=1');\" class=\"exxo-album-img exxo-loading\"></a><div>"
                        + "<span onClick=\"location='pic.jsp?id=" + rs.getString("id") + "'\" class='picName'>" + rs.getString("name") + "</span>"
                        + "<span class='picOwner'>" + EXXOlib.textLib.shortFIO(rs.getString("fio")) + "</span></div></div>";
            }
        }
        rs.close();
        stmt.close();
        con.close();
        navi += "<div id='menu-navi'><table><tr><td class='oneNavi'>";
        String andown = "";
        int of = Integer.parseInt(offset);
        if (of > 0) {
            int prev = of - ROWS;
            if (prev < 0) {
                prev = 0;
            }
            navi += "<a href='openTagsGal.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + ROWS;
            navi += "<a href='openTagsGal.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";
        return list;
    }
}
