package BEANS;

import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class picBean {

    String owner = "";
    String id = null;
    String role = "";
    String gowner;
    public String o;
    String ansCount = "0";
    String comments = "";
    public String count = "0";
    public String respcount = "0";
    public String commcount = "0";
    boolean answer = false;
    boolean diff = true;
    boolean inspector = false;
    boolean notPermitted = false;
    boolean respect = false;
    public String superior = "";
    public String tags = "";
    public String fio = "";
    public String album = "";
    public String name = "";
    public String descr = "";
    public String time = "";
    public boolean editor = true;
    private String list = "";
    private String hrefs = "";
    public int index = 0;

    public void setOwner(String O, String i, String role, String g, boolean resp, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        owner = O;
        gowner = g;
        id = i;
        respect = resp;
        if (role.indexOf("d") >= 0 || role.indexOf("s") >= 0) {
            inspector = true;
        }

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        boolean permitted = false;
        String respfunk = "";
        if (respect) {
            respfunk = ", respects(global_id,'" + owner + "'::Int, 'gallaries') ";
        }
        String sql = "SELECT id, global_id, name, descr, get_fio(owner) AS fio, owner, superior, gal_name(superior) AS album, properTime(created) AS created, created AS t "
                + respfunk + " FROM gallaries WHERE id=" + id + " LIMIT 1";
        ResultSet rs = stmt.executeQuery(sql);

        //   String redoc="";
        String global_id = null;
        if (rs.next()) {
            fio = rs.getString("fio");
            descr = rs.getString("descr");
            name = rs.getString("name");
            time = rs.getString("created");
            superior = rs.getString("superior");
            global_id = rs.getString("global_id");
            o = rs.getString("owner");
            album = rs.getString("album");
            if (owner.equals(o)) {
                diff = false;
            }
        }
        if (o.equals("-100")) {
            if (role.indexOf("a") < 0 && role.indexOf("d") < 0) {
                editor = false;
            }
        } else if (diff) {
            editor = false;
        }

        String t = "";
        sql = "SELECT tags.name,tags.tag_id FROM global_tags, tags WHERE global_id = " + global_id + " AND tags.tag_id=global_tags.tag_id"
                + " ORDER BY tags.name";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            if (rs.isFirst()) {
                tags = "";
            }
            t += "<a href='docTag?el=" + rs.getString(2) + "&s=gallaries'>" + rs.getString(1) + "</a> ";
        }
        if (!t.equals("")) {
            tags = "<div id=\"tags\"><a href='docTags?el=" + id + "&&s=gallaries'>ТЕГИ</a>: " + t + " </div>";
        }

        rs.close();

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

        sql = "SELECT count(*) FROM picsC WHERE pics_id='" + id + "'";
        ResultSet rs4 = stmt.executeQuery(sql);
        if (rs4.next()) {
            commcount = rs4.getString(1);
        }
        rs4.close();

        if (diff && !permitted && !inspector) {
            notPermitted = true;
        }

        sql = "SELECT id, owner, name FROM gallaries WHERE file='1' AND superior=" + superior + " ORDER BY created DESC";
        ResultSet rs5 = stmt.executeQuery(sql);
        int ind = 0;
        while (rs5.next()) {
            hrefs += "<a class='gallary' href='galLoader?id=" + rs5.getString("id") + "' title='" + rs5.getString("name") + "'> </a>";
            list += "<a class='img exxo-loading' href=\"pic.jsp?id=" + rs5.getString("id") + "\" style='background-image:url(\"galLoader?id=" + rs5.getString("id") + "&icon=1\")' title='" + rs5.getString("name") + "'> </a>";
            if (id.equals(rs5.getString("id"))) {
                index = ind;
            }
            ind++;

        }
        stmt.close();

        CallableStatement cstmt2 = con.prepareCall("{? = call PicCommentsList(?)}");
        cstmt2.registerOutParameter(1, Types.VARCHAR);
        cstmt2.setString(2, id);
        cstmt2.executeUpdate();

        comments = cstmt2.getString(1);
        cstmt2.close();

        con.close();
    }

    public String getCommCount() {
        return commcount;
    }

    public String getRespcount() {
        return respcount;
    }

    public String getComments() {
        return comments;
    }

    public String getCount() {
        return count;
    }

    public String getO() {
        return o;
    }

    public boolean getDiff() {
        return diff;
    }

    public String getList() {
        return list;
    }

    public String getHrefs() {
        return hrefs;
    }
}
