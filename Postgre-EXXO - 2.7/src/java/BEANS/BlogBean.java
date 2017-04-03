package BEANS;

import java.sql.*;
//import java.util.Properties;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class BlogBean {

    String owner = "";
    String id = null;
    String role = "";
    String gowner;
    String o;
    String ansCount = "0";
    String answers = "";
    String comments = "";
    String count = "0";
    String respcount = "0";
    String commcount = "0";
    boolean answer = false;
    boolean diff = true;
    boolean inspector = false;
    boolean notPermitted = false;
    boolean respect = false;
    boolean comment = false;
    String list = "";
    public String too = "";
    public String tags = "";

    public void setOwner(String O, String i, String r, String g, boolean a, boolean resp, boolean c) {
        owner = O;
        answer = a;
        gowner = g;
        id = i;
        role = r;
        respect = resp;
        comment = c;
        if (role.indexOf("s") >= 0 || role.indexOf("t") >= 0) {
            inspector = true;
        }
    }

    public String getEmp(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        Calendar calendar = GregorianCalendar.getInstance();
        String[] Monthes = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        String[] W = {"Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"};

        boolean permitted = false;
        String respfunk = "";
        if (respect) {
            respfunk = ", respects(global_id,'" + owner + "'::Int, 'bloges') ";
        }
        String sql = "SELECT text, properTime(time) AS time, bus, name, owner, dopusk_type, blocked, isBlogPermitted(dopusk_type, dopusk,'" + gowner + "') AS permitted, refBlog(ref) AS re,"
                + " registor(global_id,'" + owner + "'::Int, 'bloges'), refDoc(refdoc, refVer) AS redoc, global_id" + respfunk + ", dopusk FROM bloges WHERE id='" + id + "'";
        ResultSet rs = stmt.executeQuery(sql);

        list = "<div id=\"blogs\">";
        String text = "";
        String name = "";
        //   String redoc="";
        String global_id = null;
        if (rs.next()) {

            ///Допуски
            String emps = "";
            int def = 0;
            Statement stmt1 = con.createStatement();
            String dopusk_type = rs.getString("dopusk_type");
            java.sql.Array re = rs.getArray("dopusk");
            ResultSet dopusk;
            if (re != null) {
                dopusk = re.getResultSet();
            } else {
                dopusk = null;
            }
            if (dopusk != null) {
                while (dopusk.next()) {
                    if (dopusk.isFirst()) {
                        def = dopusk.getInt(2);
                    }
                    if (dopusk_type.equals("4")) {
                        sql = "SELECT fio, id FROM employee WHERE global_id='" + dopusk.getInt(2) + "'";
                        ResultSet rr = stmt1.executeQuery(sql);
                        if (rr.next()) {
                            if (dopusk.isFirst()) {
                                emps = "<span class='emps'>Адресовано сотрудникам:</span> ";
                            }
                            emps += " <a class='emps' href='empCard.jsp?id=" + rr.getString(2) + "'>" + EXXOlib.textLib.shortFIO(rr.getString(1)) + "</a>";
                        }
                        rr.close();
                    }
                }
                dopusk.close();
            }
            if (dopusk_type.equals("0")) {
                emps = "<span class='emps'>Адресовано ВСЕМ</span>";
            } else if (dopusk_type.equals("1")) {
                sql = "SELECT name FROM projects WHERE id=" + def;
                ResultSet rs3 = stmt1.executeQuery(sql);
                if (rs3.next()) {
                    emps = "<span class='emps'>Адресовано участникам проекта:</span> <a class='emps' href='groups.jsp?id=" + def + "'>" + rs3.getString(1) + "</a>";
                }
                rs3.close();
            } else if (dopusk_type.equals("2")) {
                sql = "SELECT name FROM groups WHERE id=" + def;
                ResultSet rs3 = stmt1.executeQuery(sql);
                if (rs3.next()) {
                    emps = "<span class='emps'>Адресовано участникам рабочей группы:</span> <a class='emps' href='group.jsp?id=" + def + "'>" + rs3.getString(1) + "</a>";
                }
                rs3.close();
            } else if (dopusk_type.equals("3")) {
                sql = "SELECT unit FROM units WHERE unit_id=" + def;
                ResultSet rs3 = stmt1.executeQuery(sql);
                if (rs3.next()) {
                    emps = "<span class='emps'>Адресовано работникам подразделения:</span> <a class='emps' href='empListComp.jsp?uid=" + def + "'>" + rs3.getString(1) + "</a>";
                }
                rs3.close();
            }

            too = emps;
            ///     
            String t = "";
            sql = "SELECT tags.name,tags.tag_id FROM global_tags, tags WHERE global_id = (SELECT global_id FROM bloges WHERE id=" + id + ") AND tags.tag_id=global_tags.tag_id"
                    + " ORDER BY tags.name";
            ResultSet rs2 = stmt1.executeQuery(sql);
            while (rs2.next()) {
                if (rs2.isFirst()) {
                    tags = "";
                }
                t += "<a href='docTag?el=" + rs2.getString(2) + "&s=bloges'>" + rs2.getString(1) + "</a> ";
            }
            if (!t.equals("")) {
                tags = "<div id=\"tags\"><a href='docTags?el=" + id + "&&s=bloges'>ТЕГИ</a>: " + t + " </div>";
            }
            rs2.close();
            stmt1.close();

            permitted = rs.getBoolean("permitted");
            text = rs.getString("text");
            name = rs.getString("name");
            global_id = rs.getString("global_id");
            o = rs.getString("owner");
            if (rs.getBoolean("blocked")) {
                text = "Текст сообщения заблокирован модератором";
                name = "Название блога заблокировано";
            }
            if (owner.equals(o)) {
                diff = false;
            }
            list += "<h1 id='name'>" + name + " </h1>";
            list += rs.getString("redoc");
            list += rs.getString("re");
            if (rs.getString(3).equals("0")) {
                list += "<div class='blog'><div class=\"HBlog\">";
            } else {
                list += "<div class='blog BUS'><div class=\"HBlog\">";
            }
            if (diff) {
                if (inspector) {
                    list += "<a class='B' href='changeBlogStatus.jsp?id=" + id + "'>Заблокировать</a>";
                }

            }
            list += "</div>";

            list += "<div class='text'>" + text + "</div><div class='timer'>" + rs.getString("time") + "</div></div>";

        }
        list += "</div>";
        sql = "SELECT name, type, fname, files.id AS id FROM files, docsBlog WHERE files.id=docsBlog.doc AND docsBlog.id=" + id;
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            if (rs.isFirst()) {
                list += "<div id='input'>Вложения:</div>";
                list += "<table class='exxo-table'>";
            }
            list += "<tr id='" + rs.getString("id") + "'><td class='" + EXXOlib.DOCTYPES.getType(rs.getString("type"), rs.getString("fname")) + "'></td><td>" + rs.getString("name") + "</td><td>" + rs.getString("fname") + "</td></tr>";
            if (rs.isLast()) {
                list += "</table>";
            }
        }
        rs.close();

        String emp = "";

        CallableStatement cstmt1 = con.prepareCall("{? = call BlogsBeanEmp(?)}");
        cstmt1.registerOutParameter(1, Types.VARCHAR);
        cstmt1.setString(2, o);
        cstmt1.executeUpdate();

        emp += cstmt1.getString(1);

        cstmt1.close();

        sql = "SELECT count(*) FROM bloges WHERE ref='" + id + "' AND NOT blocked";
        ResultSet rs1 = stmt.executeQuery(sql);
        if (rs1.next()) {
            ansCount = rs1.getString(1);
        }
        rs1.close();

        sql = "SELECT count(*) FROM registor WHERE global_id='" + global_id + "'";
        ResultSet rs2 = stmt.executeQuery(sql);
        if (rs2.next()) {
            count = rs2.getString(1);
        }
        rs2.close();

        sql = "SELECT count(*) FROM respects WHERE global_id='" + global_id + "'";
        ResultSet rs3 = stmt.executeQuery(sql);
        if (rs3.next()) {
            respcount = rs3.getString(1);
        }
        rs3.close();

        sql = "SELECT count(*) FROM blogesC WHERE bloges_id='" + id + "'";
        ResultSet rs4 = stmt.executeQuery(sql);
        if (rs4.next()) {
            commcount = rs4.getString(1);
        }
        rs4.close();

        if (diff && !permitted && !inspector) {
            notPermitted = true;
        }

        rs1.close();
        stmt.close();

        if (answer) {
            CallableStatement cstmt = con.prepareCall("{? = call BlogsAnswersList(?, ?, ?)}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.setString(2, id);
            cstmt.setString(3, owner);
            cstmt.setString(4, gowner);
            cstmt.executeUpdate();

            answers = cstmt.getString(1);
            cstmt.close();

        }
        if (comment) {
            CallableStatement cstmt2 = con.prepareCall("{? = call BlogCommentsList(?)}");
            cstmt2.registerOutParameter(1, Types.VARCHAR);
            cstmt2.setString(2, id);
            cstmt2.executeUpdate();

            comments = cstmt2.getString(1);
            cstmt2.close();

        }
        con.close();
        return emp;
    }

    public String getCommCount() {
        return commcount;
    }

    public String getRespcount() {
        return respcount;
    }

    public String getAnswers() {

        return answers;
    }

    public String getComments() {
        return comments;
    }

    public String getCount() {
        return count;
    }

    public String getAns() {
        return ansCount;
    }

    public String getO() {
        return o;
    }

    public String getList() {
        return list;
    }

    public boolean getDiff() {
        return diff;
    }

    public boolean getNotPermitted() {
        return notPermitted;
    }
}
