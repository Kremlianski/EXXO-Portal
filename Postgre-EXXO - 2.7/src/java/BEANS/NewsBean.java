package BEANS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class NewsBean {

    public String getList(Boolean yes, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        String navi = "";
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        String offset = "0";
        if (r.getParameter("of") != null) {
            offset = r.getParameter("of");
        }
        boolean next = false;
        int ROWS = BASE.VER.getMaxNews(sc);
        int rows = ROWS + 1;

        String sql = "SELECT id, text, properTime(time) AS t FROM news ORDER BY time DESC LIMIT " + rows + " OFFSET " + offset;
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<div id=\"blogs\">";
        if (yes) {
            list += "";
        }
        int cc = 0;
        while (rs.next()) {
            if (rs.isLast() && cc == rows - 1) {
                next = true;
            } else {
                cc++;
                list += "<div class='blog exxo-shadow'><div class=\"HBlog\">";
                if (yes) {
                    list += "<a class=\"B edit\" href=\"javascript:\" id=\"" + rs.getString("id") + "\"> Править </a> ";
                    list += "<a class=\"B\" href=\"newsKill?id=" + rs.getString("id") + "\" >    Удалить </a> ";
                }
                list += "</div>";
                list += "<div class='text'>" + rs.getString("text") + "</div><div class='timer exxo-date'>" + rs.getString("t") + "</div></div>";

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
            navi += "<a href='news.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + ROWS;
            navi += "<a href='news.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";
        list += navi;
        return list;
    }
}
