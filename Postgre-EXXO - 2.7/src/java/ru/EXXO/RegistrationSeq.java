package ru.EXXO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationSeq {

    private int getSeq(String s) throws SQLException {
        int r = -1;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT nextval('");
        sb.append(s);
        sb.append("')");
        String sql = sb.toString();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            r = rs.getInt(1);
        }
        stmt.close();
        con.close();
        return r;
    }

    public int blog_cat_id_seq() throws SQLException {
        return getSeq("blog_cat_id_seq");
    }

    public int bloges_seq() throws SQLException {
        return getSeq("bloges_seq");
    }

    public int blogesc_id_seq() throws SQLException {
        return getSeq("blogesc_id_seq");
    }

    public int desc_seq() throws SQLException {
        return getSeq("desc_seq");
    }

    public int employee_id_seq() throws SQLException {
        return getSeq("employee_id_seq");
    }

    public int events_seq() throws SQLException {
        return getSeq("events_seq");
    }

    public int eventsres_seq() throws SQLException {
        return getSeq("eventsres_seq");
    }

    public int files_seq() throws SQLException {
        return getSeq("files_seq");
    }

    public int files_ver_seq() throws SQLException {
        return getSeq("files_ver_seq");
    }

    public int gallaries_seq() throws SQLException {
        return getSeq("gallaries_seq");
    }

    public int global_seq() throws SQLException {
        return getSeq("global_seq");
    }

    public int groups_id_seq() throws SQLException {
        return getSeq("groups_id_seq");
    }

    public int news_id_seq() throws SQLException {
        return getSeq("news_id_seq");
    }

    public int picsc_id_seq() throws SQLException {
        return getSeq("picsc_id_seq");
    }

    public int pjornal_id_seq() throws SQLException {
        return getSeq("pjornal_id_seq");
    }

    public int process_id_seq() throws SQLException {
        return getSeq("process_id_seq");
    }

    public int projects_id_seq() throws SQLException {
        return getSeq("projects_id_seq");
    }

    public int qwests_quest_id_seq() throws SQLException {
        return getSeq("qwests_quest_id_seq");
    }

    public int resource_id_seq() throws SQLException {
        return getSeq("resource_id_seq");
    }

    public int resources_seq() throws SQLException {
        return getSeq("resources_seq");
    }

    public int structure_seq() throws SQLException {
        return getSeq("structure_seq");
    }

    public int tags_seq() throws SQLException {
        return getSeq("tags_seq");
    }

    public int units_unit_id_seq() throws SQLException {
        return getSeq("units_unit_id_seq");
    }

    public int rating_seq() throws SQLException {
        return getSeq("rating_seq");
    }
}
