package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class EmpListTer {

    public String getList(String of, HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT employee.id, employee.fio, employee.position, units.unit AS unit, room FROM employee, units "
                + "WHERE employee.unit=units.unit_id AND fired='0' AND office='" + of + "' ORDER BY room";
        String list = "";
        ResultSet rs = stmt.executeQuery(sql);
        String res = "Ошибка ввода";
        while (rs.next()) {
            list += "<tr><td class=\"room\">" + rs.getString("room") + "</td><td class=\"fio\">"
                    + "<span id=\"" + rs.getString("id") + "\">" + rs.getString("fio") + "</span></td>"
                    + "<td>" + rs.getString("unit") + "</td><td>" + rs.getString("position") + "</td></tr>";

        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }

}
