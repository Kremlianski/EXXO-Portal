package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class group {

    public String name = "";

    public String getList(String group, HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        String sql = "SELECT name FROM groups WHERE id='" + group + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            name = rs.getString(1);
        }

        sql = "SELECT employee.id, employee.fio, employee.position, units.unit, wgroup.class FROM employee, units, wgroup "
                + "WHERE fired='0' AND employee.unit=units.unit_id AND wgroup.id=employee.global_id AND wgroup.group_id='" + group + "' ORDER BY fio";

        String list = "<table id=\"tableList\"><tbody><tr><th>Участник &laquo;" + name + "&raquo;</th><th>Должность</th><th>Подразделение</th></tr>";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            list += "<tr class='datas " + rs.getString(5) + "'><td class=\"first\" id=\"" + rs.getInt(1) + "\">" + rs.getString(2) + " </td>"
                    + "<td class='second'>" + rs.getString(3) + " </td><td class='third'>" + rs.getString(4) + " </td></tr>";
        }
        list += "</tbody></table>";

        rs.close();
        stmt.close();
        con.close();

        return list;

    }

}
