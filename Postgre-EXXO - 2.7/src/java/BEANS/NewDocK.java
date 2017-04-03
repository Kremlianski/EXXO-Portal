package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class NewDocK {

    public String lMenu = "";
    public String navi = "";

    public String getList(HttpServletRequest r, String o, String g) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        boolean isSecurity = false;
        String offset = "0";
        if (r.getParameter("of") != null) {
            offset = r.getParameter("of");
        }
        String own = r.getParameter("own");
        String OWN = "";
        boolean next = false;
        int ROWS = BASE.VER.getMaxRows(sc);
        int rows = ROWS + 1;
        if (own != null) {
            OWN = " AND owner=" + own;
        }
        String sec = "f";
        String role = (String) r.getSession().getAttribute("role");
        if (role.indexOf("e") >= 0 || role.indexOf("s") >= 0) {
            isSecurity = true;
        }
        if (own != null && ((role.indexOf("o") >= 0 && own.equals("-100")) || (role.indexOf("p") >= 0 && own.equals("-101"))
                || (role.indexOf("q") >= 0 && own.equals("-102")) || (role.indexOf("r") >= 0 && own.equals("-103")))) {
            isSecurity = true;
        }

        String sql = "SELECT files.id, files.owner, files.name, files.fname, files.descr,  "
                + " type, properTime(files_vers.created) AS create, files.superior, "
                + " catalog_name(superior),dopusk_type,  ver_id, files_vers.created, is_doc_opend(ver_id, " + o + ") AS opend, status  FROM files, files_vers WHERE file='1' "
                + " AND files.owner<0 AND  files.copy=files_vers.id" + OWN + " AND (isBlogPermitted (dopusk_type, dopusk, " + g + "::Int) OR '" + isSecurity + "') ORDER BY files_vers.created DESC LIMIT " + rows + " OFFSET " + offset;

        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table><tbody>";
        int cc = 0;
        while (rs.next()) {
            if (rs.isLast() && cc == rows - 1) {
                next = true;
            } else {
                String t = rs.getString("type");
                String type = EXXOlib.DOCTYPES.getType(t, rs.getString(4));

                String opend = "";
                if (!rs.getBoolean("opend")) {
                    opend = "nopend";
                }
                int owner = rs.getInt(2);
                String Fio = "";
                if (owner == -100) {
                    Fio = "Общие";
                } else if (owner == -101) {
                    Fio = "Входящие";
                } else if (owner == -102) {
                    Fio = "Исходящие";
                } else if (owner == -103) {
                    Fio = "Внутренние";
                }
                String classBlocked = "";
                //if(isSecurity||rs.getBoolean("permitted")) { 
                cc++;
                if (rs.getInt("status") == 2) {
                    classBlocked = " blocked";
                }
                list += "<tr><td class='first " + type + "'></td><td class='fil'><a href=\"fileLoader.jsp?id=" + rs.getString(1) + "\" class='" + opend + classBlocked + "'>" + rs.getString(3) + "</a>(" + rs.getString(4) + ")</td>";

                list += "<td class='fio'><a href=\"docClassic.jsp?owner=" + rs.getString(2) + "&id=" + rs.getString(8) + "\">" + rs.getString(9) + " (" + Fio + ")</a></td><td class='descr'>" + rs.getString(5) + "</td>"
                        + "<td class='created'>" + rs.getString("create") + "</td><td class='dop'><img src='" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + "'></td></tr>";
//}
            }
        }
        list += "</tbody></table>";
//навигация пэйджер--
        navi += "<div id='menu-navi'><table><tr><td class='oneNavi'>";
        String andown = "";
        if (own != null) {
            andown = "&own=" + own;
        }
        int of = Integer.parseInt(offset);
        if (of > 0) {
            int prev = of - ROWS;
            if (prev < 0) {
                prev = 0;
            }
            navi += "<a href='newDocK.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + ROWS;
            navi += "<a href='newDocK.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";
//----
        String class1 = "";
        String class100 = "";
        String class101 = "";
        String class102 = "";
        String class103 = "";
        if (own == null) {
            class1 = " class='own'";
        } else if (own.equals("-100")) {
            class100 = " class='own'";
        } else if (own.equals("-101")) {
            class101 = " class='own'";
        } else if (own.equals("-102")) {
            class102 = " class='own'";
        } else if (own.equals("-103")) {
            class103 = " class='own'";
        }
        lMenu = "<div id='lMenu' class='exxo-shadow'>";
        lMenu += "<a href='newDocK.jsp'  " + class1 + ">Все документы</a>";
        lMenu += "<a href='newDocK.jsp?own=-100' " + class100 + ">Общие документы</a>";
        lMenu += "<a href='newDocK.jsp?own=-101' " + class101 + ">Входящие документы</a>";
        lMenu += "<a href='newDocK.jsp?own=-102' " + class102 + ">Исходящие документы</a>";
        lMenu += "<a href='newDocK.jsp?own=-103' " + class103 + ">Внутренние документы</a>";
        lMenu += "</div>";
        if (own != null) {
            lMenu += "<div id=\"menuList\"><a href=\"docClassic.jsp?owner=" + own + "\" id=\"addgroup\">Структура папок</a></div>";
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }
}
