package BEANS;

import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TagResults {

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
        String list = "";
        HttpSession session = r.getSession(true);
        String owner = (String) session.getAttribute("id");
        String guser = (String) session.getAttribute("global_id");
        String s = r.getParameter("s");
        String res = "";
        int cc = 0;
        String sql = "SELECT tag_id, status FROM found_tags WHERE owner='" + owner + "' ORDER BY status";
        ResultSet rs = stmt.executeQuery(sql);
        String[] Ar = {"", "AND ", "OR ", "AND NOT"};
        while (rs.next()) {
            //   if (rs.isFirst()) {if(rs.getInt(2)!=3) res+=" tag_id='"+rs.getString(1)+"' ";
            //   else res+="NOT tag_id='"+rs.getString(1)+"' ";
            //   } else { res+=Ar[rs.getInt(2)]+" tag_id='"+rs.getString(1)+"' ";}}
            //sql="SELECT DISTINCT bloges.id, bloges.text, bloges.time, bloges.owner, employee.fio, bus FROM bloges, employee, global_tags" +
            //         " WHERE employee.id=bloges.owner" +
            //        " AND ("+res+") AND global_tags.global_id=bloges.global_id LIMIT 25";

            if (rs.isFirst()) {
                if (rs.getInt(2) != 3) {
                    res += " " + s + ".global_id IN (SELECT global_id FROM global_tags WHERE tag_id='" + rs.getString(1) + "') ";
                } else {
                    res += " NOT  " + s + ".global_id IN (SELECT global_id FROM global_tags WHERE tag_id='" + rs.getString(1) + "') ";
                }
            } else {
                res += Ar[rs.getInt(2)] + " " + s + ".global_id IN (SELECT global_id FROM global_tags WHERE tag_id='" + rs.getString(1) + "') ";
            }
        }//
        list = "<div class='alert'>Задайте теги для поиска</div>";
        if (s.equals("bloges")) {
            sql = "SELECT * FROM (SELECT DISTINCT bloges.id, bloges.name, properTime(bloges.time), bloges.owner, employee.fio, bus, bloges.dopusk_type,  isBlogPermitted (dopusk_type, dopusk, " + guser + "::Int) AS permitted, bloges.time AS t  FROM bloges, employee"
                    + " WHERE employee.id=bloges.owner"
                    + " AND (" + res + ") AND NOT bloges.blocked AND (isBlogPermitted (dopusk_type, dopusk, " + guser + "::Int) OR bloges.owner=" + owner + ")) AS foo ORDER BY foo.t DESC LIMIT " + rows + " OFFSET " + offset;

            String clas = "";
            if (!res.equals("")) {
                rs = stmt.executeQuery(sql);
                list = "<table id='listTable'>";

                while (rs.next()) {
                    if (rs.isLast() && cc == rows - 1) {
                        next = true;
                    } else {
                        cc++;
                        if (rs.getBoolean("permitted") || rs.getString(4).equals(owner)) {
                            if (rs.getString(6).equals("1")) {
                                clas = "bus";
                            }
                            list += "<tr><td class=\"type_" + rs.getString(7) + "\"></td>";
                            list += "<td class=\"tdName\"><a href=\"blog.jsp?id=" + rs.getString(1) + "\" class=\"" + clas + "\">" + rs.getString(2);
                            list += "</a></td><td class=\"emp\">" + rs.getString(5) + "</td><td class=\"tdTime\"><span class=\"time\">" + rs.getString(3) + "</span></td></tr>";
                        }
                    }
                }
                list += "</table>";
            }
        } else if (s.equals("files")) {
            //здесь нет учета files_vers.status. А что если версия заблокирована. Вообще переделать, а что если -101?
            //        sql="CREATE FUNCTION catalog_name(integer) RETURNS varchar AS $$ SELECT name FROM files WHERE id=$1;$$ LANGUAGE SQL;";
            sql = "SELECT * FROM (SELECT DISTINCT ON(files.id) files.id, files.name, files.descr, get_fio(owner), files.owner, files.superior, "
                    + " catalog_name(superior),  isBlogPermitted (dopusk_type, dopusk, " + guser + "::Int) AS permitted , files.type, "
                    + "properTime(files.created) AS created, files.created AS t, dopusk_type, fname FROM files WHERE file='1' "
                    + "AND (" + res + ")  AND (isBlogPermitted (dopusk_type, dopusk, " + guser + "::Int) OR files.owner=" + owner + ")) AS foo ORDER BY foo.t DESC LIMIT " + rows + " OFFSET " + offset;
            if (!res.equals("")) {
                rs = stmt.executeQuery(sql);
                list = "<table id='FILESTABLE'><tbody>";

                while (rs.next()) {
                    if (rs.isLast() && cc == rows - 1) {
                        next = true;
                    } else {
                        cc++;
                        if (rs.getBoolean("permitted") || rs.getString(5).equals(owner)) {
                            String fio = "компания";
                            String FIO = "";
                            int OWNER = rs.getInt(5);
                            if (OWNER == -100) {
                                FIO = " / Общие";
                            } else if (OWNER == -101) {
                                FIO = " / Входящие";
                            } else if (OWNER == -102) {
                                FIO = " / Исходящие";
                            } else if (OWNER == -103) {
                                FIO = " / Внутренние";
                            }

                            fio = rs.getString(4);
                            String sub[] = fio.split(" ");
                            String Fio = sub[0];
                            if (sub.length > 1) {
                                Fio += " " + sub[1].charAt(0) + ".";
                            }
                            if (sub.length > 2) {
                                Fio += " " + sub[2].charAt(0) + ".";
                            }
                            Fio += FIO;

                            String t = rs.getString("type");
                            String type = EXXOlib.DOCTYPES.getType(t, rs.getString("fname"));

                            list += "<tr><td class='first " + type + "' id='" + rs.getString(1) + "'></td><td class='fil'><a href=\"fileLoader.jsp?id=" + rs.getString(1) + "\">" + rs.getString(2) + "</a></td>"
                                    + "<td class='fio'><a href='docClassic.jsp?owner=" + rs.getString(5) + "&id=" + rs.getString(6) + "' "
                                    + " >" + rs.getString(7) + "</a> (" + Fio + ")</td><td class='descr'>" + rs.getString(3) + "</td><td class='created'>" + rs.getString("created") + "</td>"
                                    + "<td class='dop'><img src='" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + "'></td></tr>";

                        }
                    }
                }

                list += "</tbody></table>";

            }

        } else if (s.equals("gallaries")) {
            //        sql="CREATE FUNCTION catalog_name(integer) RETURNS varchar AS $$ SELECT name FROM files WHERE id=$1;$$ LANGUAGE SQL;";
            sql = "SELECT * FROM (SELECT DISTINCT ON(gallaries.id) gallaries.id, gallaries.name, gallaries.descr, employee.fio, gallaries.owner, gallaries.superior, "
                    + " gal_name(superior), properTime(gallaries.created) AS created, gallaries.created AS t FROM gallaries, employee WHERE file='1' AND gallaries.superior<>1"
                    + "AND (gallaries.owner=employee.id OR gallaries.owner=-100) AND (" + res + ")) AS foo ORDER BY foo.t DESC LIMIT " + rows + " OFFSET " + offset;
            if (!res.equals("")) {
                rs = stmt.executeQuery(sql);
                list = "<table id='picTable'><tbody>";

                while (rs.next()) {
                    if (rs.isLast() && cc == rows - 1) {
                        next = true;
                    } else {
                        cc++;
                        String fio = "компания";
                        if (!rs.getString(5).equals("-100")) {
                            fio = rs.getString(4);
                        }
                        String sub[] = fio.split(" ");
                        String Fio = sub[0];
                        if (sub.length > 1) {
                            Fio += " " + sub[1].charAt(0) + ".";
                        }
                        if (sub.length > 2) {
                            Fio += " " + sub[2].charAt(0) + ".";
                        }
                        list += "<tr><td class='ico' id='" + rs.getString(1) + "' style='background-image:url(\"galLoader?id=" + rs.getString(1) + "&ico=1\")'></td><td class='name'>"
                                + "<a href=\"galLoader?id=" + rs.getString(1) + "\" class='imger'>" + rs.getString(2) + "</a><br><span class='created'>" + rs.getString("created") + "</span></td>"
                                + "<td class='album'><a href='galClassic.jsp?owner=" + rs.getString(5) + "&&id=" + rs.getString(6) + "' "
                                + " >" + rs.getString(7) + "</a><br><span class='author'>(" + Fio + ")</span></td><td class='descr'>" + rs.getString(3) + "</td></tr>";

                    }
                }

                list += "</tbody></table>";

            }

        }

        rs.close();
        stmt.close();
        con.close();
        if (list.equals("<table id='listTable'></table>") || list.equals("<table id='picTable'><tbody></tbody></table>") || list.equals("<table id='FILESTABLE'><tbody></tbody></table>")) {
            list = "<div class='alert'>Ничего ненайдено</div>";
        } else {
            navi += "<div id='menu-navi'><table><tr><td class='oneNavi'>";
            String andown = "";
            if (s != null) {
                andown = "&s=" + s;
            }
            int of = Integer.parseInt(offset);
            if (of > 0) {
                int prev = of - ROWS;
                if (prev < 0) {
                    prev = 0;
                }
                navi += "<a href='openTags.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
            }
            navi += "</td><td></td><td class='threeNavi'>";
            if (next) {
                int sled = of + ROWS;
                navi += "<a href='openTags.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
            }
            navi += "</td></tr></table></div>";
        }

        return list;
    }

}
