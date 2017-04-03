package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class EmpListBean {

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        String sql = "SELECT employee.id, employee.fio, employee.position, units.unit FROM employee, units "
                + "WHERE fired='0' AND employee.unit=units.unit_id ORDER BY fio";

        String list = "";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            list += "<tr><td class=\"fio\"><span id=\"" + rs.getInt("id") + "\">" + rs.getString("fio") + " </span></td><td>" + rs.getString("position") + " </td><td>" + rs.getString("unit") + " </td></tr>";
        }
        rs.close();
        stmt.close();
        con.close();

        return list;

    }

}
