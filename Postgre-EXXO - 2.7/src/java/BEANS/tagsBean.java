package BEANS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class tagsBean {

    public boolean permitted = false;

    public String getList(String id, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql1 = "SELECT id, fio FROM employee ORDER BY fio";
        ResultSet rs = stmt.executeQuery(sql1);
        String selected = "";
        String list = "";
        while (rs.next()) {
            selected = "";

            if (rs.getString("id").equals(id)) {
                selected = "SELECTED";
            }
            list += "<option value='" + rs.getString("id") + "' " + selected + ">" + rs.getString("fio") + "</option>";
        }

        String sql = "SELECT * FROM tags WHERE owner = '" + id + "'";
        ResultSet rs1 = stmt.executeQuery(sql);
        if (rs1.next()) {
            permitted = true;
        }

        rs1.close();
        rs.close();
        stmt.close();
        con.close();

        return list;
    }

}
