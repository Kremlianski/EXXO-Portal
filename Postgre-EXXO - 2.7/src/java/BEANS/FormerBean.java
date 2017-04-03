package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class FormerBean {

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT employee.id, employee.fio, employee.position, units.unit AS unit FROM employee, units "
                + "WHERE fired='1' AND employee.unit=units.unit_id ORDER BY fio";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "";
        while (rs.next()) {
            list += "<tr><td class=\"fio\"><span id=\"" + rs.getString("id") + "\">" + rs.getString("fio") + "</span></td>"
                    + "<td>" + rs.getString("unit") + "</td><td>" + rs.getString("position") + "</td></tr>";

        }
        return list;
    }
}
