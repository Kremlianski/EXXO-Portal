package BEANS;

import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class NewGalK {

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
        int ROWS = BASE.VER.getMaxRows(sc);
        int rows = ROWS + 1;
        String sql = "SELECT id, name, descr, properTime(created) AS created FROM gallaries WHERE file='0' AND owner='-100' AND notshow=0 ORDER BY gallaries.created DESC LIMIT " + rows + " OFFSET " + offset;
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table id='picTable'><th></th><th>Название альбома</th><th>Время создания</th><th>Описание</th>";
        int cc = 0;
        while (rs.next()) {
            if (rs.isLast() && cc == rows - 1) {
                next = true;
            } else {
                cc++;
                list += "<tr><td class='ico exxo-loading' style=\"background-image:url('cover?id=" + rs.getInt("id") + "&ico=1')\"></td><td class='name'>"
                        + "<a href=\"galClassic.jsp?owner=-100&id=" + rs.getString(1) + "\">" + rs.getString(2) + "</a></td><td class='created'>" + rs.getString("created") + "</td>"
                        + "<td class='descr'>" + rs.getString("descr") + "</td></tr>";

            }
        }
        list += "</table>";
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
            navi += "<a href='newGalK.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + ROWS;
            navi += "<a href='newGalK.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";

        return list;

    }
}
