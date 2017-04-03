package BEANS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class DeskBean {

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        String navi = "";
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        String offset = "0";
        if (r.getParameter("of") != null) {
            offset = r.getParameter("of");
        }
        boolean next = false;
        int ROWS = BASE.VER.getMaxDesk(sc);
        int rows = ROWS + 1;

        String sql = "SELECT id, text, properTime(time) AS t, owner, fio FROM desk ORDER BY time DESC LIMIT " + rows + " OFFSET " + offset;
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<div id=\"blogs\">";

        list += "";
        int cc = 0;
        while (rs.next()) {
            if (rs.isLast() && cc == rows - 1) {
                next = true;
            } else {
                cc++;
                list += "<div class='desc exxo-shadow'><div class=\"HBlog\">";
                list += "<a class=\"B edit\" href=\"javascript:\" id=\"" + rs.getString("id") + "\"> Править </a> ";
                list += "<a class=\"B\" href=\"deskKill?id=" + rs.getString("id") + "\" >    Удалить </a> </div>";
                list += "<table><tr><td class='imgTd'><img src='IMG?id=" + rs.getString("owner") + "' class='exxo-shadow1'></td>";
                list += "<td><div class='timer'><a href='empCard.jsp?id=" + rs.getString("owner") + "'>" + rs.getString("fio") + "</a>"
                        + "<span class='exxo-date'>" + rs.getString("t") + "</span></div>";
                list += "<div class='text'>" + rs.getString("text") + "</div></td></tr></table></div>";

            }
        }
        list += "</div>";
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
            navi += "<a href='desk.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + ROWS;
            navi += "<a href='desk.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";
        list += navi;
        return list;
    }
}
