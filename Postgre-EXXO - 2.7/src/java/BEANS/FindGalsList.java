package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FindGalsList {

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

        String frase = "";
        HttpSession session = r.getSession(true);
        if (r.getParameter("find") != null) {
            frase = r.getParameter("find");
            session.setAttribute("frase", frase);
        } else if (session.getAttribute("frase") != null) {
            frase = (String) session.getAttribute("frase");
        }

        String sql = "SELECT gallaries.id, gallaries.name, gallaries.descr, get_fio(owner) AS fio, gallaries.owner, gallaries.superior, "
                + " gal_name(superior), properTime(gallaries.created) AS created, gallaries.created AS t  FROM gallaries WHERE file='1' "
                + "AND to_tsvector('russian',name||' '||descr||' '||tags) @@ plainto_tsquery('" + frase + "')ORDER BY t DESC LIMIT " + rows + " OFFSET " + offset;
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table id='picTable'><tbody><tr><th></th><th>Название</th><th>Альбом</th><th>Описание</th>";
        int cc = 0;
        while (rs.next()) {
            if (rs.isLast() && cc == rows - 1) {
                next = true;
            } else {
                cc++;
                list += "<tr><td class='ico exxo-loading' id='" + rs.getString(1) + "' style='background-image:url(\"galLoader?id=" + rs.getString(1) + "&ico=1\")'><a href=\"galLoader?id=" + rs.getString(1) + "\" class='imger'></a></td><td class='name'>"
                        + "<a href=\"pic.jsp?id=" + rs.getString(1) + "\">" + rs.getString(2) + "</a><br><span class='created'>" + rs.getString("created") + "</span></td>"
                        + "<td class='album'><a href='galClassic.jsp?owner=" + rs.getString(5) + "&&id=" + rs.getString(6) + "' "
                        + ">" + rs.getString(7) + "</a> <br><span class='author'>(" + EXXOlib.textLib.shortFIO(rs.getString("fio")) + ")</span></td><td class='descr'>" + rs.getString(3) + "</td></tr>";
            }
        }
        list += "</tbody></table>";
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
            navi += "<a href='findGalsList.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + ROWS;
            navi += "<a href='findGalsList.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";
        return list;

    }
}
