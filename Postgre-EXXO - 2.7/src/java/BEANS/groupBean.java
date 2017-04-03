package BEANS;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class groupBean {

    public String getList(String project, HttpServletRequest r) throws ClassNotFoundException, SQLException, UnsupportedEncodingException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        String sql = "SELECT DISTINCT ON(groups.name) groups.id, groups.name, group_boss(groups.id) "
                + " FROM groups WHERE groups.project='" + project + "' ORDER BY groups.name";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table id=\"tableList\"><tbody><tr><th>Рабочая группа</th><th>Руководитель рабочей группы</th></tr>";
        while (rs.next()) {
            list += "<tr class='datas'><td class='first' id='" + rs.getString(1) + "'>" + rs.getString(2) + "</td>"
                    + "<td class='boss'> " + rs.getString(3) + "</td>"
                    + "</tr>";
        }
        list += "</tbody></table>";

        rs.close();
        stmt.close();
        con.close();
        return list;
    }
}
