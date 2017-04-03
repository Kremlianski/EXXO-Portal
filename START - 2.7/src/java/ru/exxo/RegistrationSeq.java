package ru.exxo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class RegistrationSeq {

    private int getSeq(String s) throws SQLException, ClassNotFoundException {
        int r = -1;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT nextval('");
        sb.append(s);
        sb.append("')");
        String sql = sb.toString();
        Connection con = startSQL();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            r = rs.getInt(1);
        }
        stmt.close();
        con.close();
        return r;
    }

    public int blog_cat_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("blog_cat_id_seq");
    }

    public int bloges_seq() throws SQLException, ClassNotFoundException {
        return getSeq("bloges_seq");
    }

    public int blogesc_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("blogesc_id_seq");
    }

    public int desc_seq() throws SQLException, ClassNotFoundException {
        return getSeq("desc_seq");
    }

    public int employee_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("employee_id_seq");
    }

    public int events_seq() throws SQLException, ClassNotFoundException {
        return getSeq("events_seq");
    }

    public int eventsres_seq() throws SQLException, ClassNotFoundException {
        return getSeq("eventsres_seq");
    }

    public int files_seq() throws SQLException, ClassNotFoundException {
        return getSeq("files_seq");
    }

    public int files_ver_seq() throws SQLException, ClassNotFoundException {
        return getSeq("files_ver_seq");
    }

    public int gallaries_seq() throws SQLException, ClassNotFoundException {
        return getSeq("gallaries_seq");
    }

    public int global_seq() throws SQLException, ClassNotFoundException {
        return getSeq("global_seq");
    }

    public int groups_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("groups_id_seq");
    }

    public int news_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("news_id_seq");
    }

    public int picsc_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("picsc_id_seq");
    }

    public int pjornal_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("pjornal_id_seq");
    }

    public int process_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("process_id_seq");
    }

    public int projects_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("projects_id_seq");
    }

    public int qwests_quest_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("qwests_quest_id_seq");
    }

    public int resource_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("resource_id_seq");
    }

    public int resources_seq() throws SQLException, ClassNotFoundException {
        return getSeq("resources_seq");
    }

    public int structure_seq() throws SQLException, ClassNotFoundException {
        return getSeq("structure_seq");
    }

    public int tags_seq() throws SQLException, ClassNotFoundException {
        return getSeq("tags_seq");
    }

    public int units_unit_id_seq() throws SQLException, ClassNotFoundException {
        return getSeq("units_unit_id_seq");
    }

    private Connection startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + BASE.Ver.getDBHost() + "/" + BASE.Ver.getDBName();
        Properties properties = BASE.Ver.getDBProp();
        return DriverManager.getConnection(url, properties);

    }
}
