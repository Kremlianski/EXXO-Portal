package BEANS;

import java.io.UnsupportedEncodingException;
import java.sql.*;
//import java.util.Properties;
//import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
//import java.net.URLEncoder;

public class ProjectBean {

    public String getList(String user, HttpServletRequest r) throws ClassNotFoundException, SQLException, UnsupportedEncodingException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT projects.id, projects.name, projects.boss,employee.fio FROM projects, employee WHERE employee.global_id=projects.boss "
                + "ORDER BY projects.name";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table id=\"tableList\"><tbody><tr><th>Проект</th><th>Руководитель проекта</th></tr>";
        while (rs.next()) {
            list += "<tr class='datas'><td class='first' id='" + rs.getString(1) + "'>" + rs.getString(2) + "</td>"
                    + "<td class='boss'> " + rs.getString(4) + "</td>"
                    + "</tr>";
        }
        list += "</tbody></table>";

        rs.close();
        stmt.close();
        con.close();
        return list;
    }
}
