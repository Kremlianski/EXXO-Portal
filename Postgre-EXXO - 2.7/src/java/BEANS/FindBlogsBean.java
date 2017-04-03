package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FindBlogsBean {

    public String navi = "";

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        HttpSession session = r.getSession(true);
        String o = (String) session.getAttribute("id");
        String g = (String) session.getAttribute("global_id");
        String frase = r.getParameter("frase");
        if (frase == null) {
            frase = (String) session.getAttribute("frase");
        } else {
            session.setAttribute("frase", frase);
        }
        String offset = "0";
        if (r.getParameter("of") != null) {
            offset = r.getParameter("of");
        }
        boolean next = false;
        int ROWS = BASE.VER.getMaxRows(sc);
        int rows = ROWS + 1;

        String sql = "SELECT files.id, files.owner, files.name, get_fio(files.owner) AS fio, files.fname, files.descr, "
                + " type, properTime(files_vers.created) AS create, files.superior, "
                + " catalog_name(superior),dopusk_type,  ver_id, files_vers.created, is_doc_opend(ver_id, " + o + ") AS opend, status  FROM files, "
                + " files_vers WHERE file='1' AND  files.copy=files_vers.id  AND "
                + " (isBlogPermitted (dopusk_type, dopusk, " + g + "::Int) OR files.owner=" + o + "::Int) AND status<>2 AND to_tsvector('russian',name||' '||descr||' '||tags) @@ plainto_tsquery('" + frase + "')"
                + " ORDER BY files_vers.created DESC LIMIT " + rows + " OFFSET " + offset;
//sql="SELECT id, name, fname, owner, isBlogPermitted (dopusk_type, dopusk, "+g+"::Int) AS permitted, type, properTime(created) AS created  FROM files WHERE file='1'" +
//"AND to_tsvector('russian',name||' '||descr||' '||tags) @@ plainto_tsquery('"+frase+"') ORDER BY files.created DESC  LIMIT 100 ";
        ResultSet rs = stmt.executeQuery(sql);

        String list = "<table><tbody>";
        int cc = 0;
        while (rs.next()) {
            if (rs.isLast() && cc == rows - 1) {
                next = true;
            } else {
                String t = rs.getString("type");
                String type = EXXOlib.DOCTYPES.getType(t, rs.getString(5));

                String opend = "";
                if (!rs.getBoolean("opend")) {
                    opend = "nopend";
                }

                String fio = rs.getString(4);
                String sub[] = fio.split(" ");
                String Fio = sub[0];
                if (sub.length > 1) {
                    Fio += "&nbsp;" + sub[1].charAt(0) + ".";
                }
                if (sub.length > 2) {
                    Fio += "&nbsp;" + sub[2].charAt(0) + ".";
                }

                String FIO = "";
                int OWNER = rs.getInt(2);
                if (OWNER == -100) {
                    FIO = " / Общие";
                } else if (OWNER == -101) {
                    FIO = " / Входящие";
                } else if (OWNER == -102) {
                    FIO = " / Исходящие";
                } else if (OWNER == -103) {
                    FIO = " / Внутренние";
                }

                Fio += FIO;
                String classBlocked = "";
                cc++;
                if (rs.getInt("status") == 2) {
                    classBlocked = " blocked";
                }
                list += "<tr><td class='first " + type + "'></td><td class='fil'><a href=\"fileLoader.jsp?id=" + rs.getString(1) + "\" class='" + opend + classBlocked + "'>" + rs.getString(3) + "</a>(" + rs.getString(5) + ")</td>";

                list += "<td class='fio'><a href=\"docClassic.jsp?owner=" + rs.getString(2) + "&id=" + rs.getString(9) + "\">" + rs.getString(10) + " (" + Fio + ")</a></td><td class='descr'>" + rs.getString(6) + "</td>"
                        + "<td class='created'>" + rs.getString("create") + "</td><td class='dop'><img src='" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + "'></td></tr>";

            }
        }

        list += "</tbody></table>";
        navi += "<div id='menu-navi'><table><tr><td class='oneNavi'>";
        String andown = "";
        int of = Integer.parseInt(offset);
        if (of > 0) {
            int prev = of - ROWS;
            if (prev < 0) {
                prev = 0;
            }
            navi += "<a href='findDocs.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + ROWS;
            navi += "<a href='findDocs.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";

        rs.close();
        stmt.close();
        con.close();
        return list;

    }

}
