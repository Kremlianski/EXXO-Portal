package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class MovementBean {

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        String list = "";
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT id, fio, office, room , units.unit AS unit FROM employee,  units WHERE office='Переезд' AND units.unit_id=employee.unit";

        String op = "";
        String sql1 = "SELECT short FROM offices";
        ResultSet rs1 = stmt.executeQuery(sql1);
        String selected = "";
        while (rs1.next()) {
            if (!rs1.getString("short").equals("Переезд")) {
                op += "<option value='" + rs1.getString("short") + "' " + selected + ">" + rs1.getString("short") + "</option>";
            }
        }
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            list += "<tr class=\"data\"><form action=\"javascript:\" method=\"POST\"><td class=\"first\">" + rs.getString("fio") + " (" + rs.getString("unit") + ")"
                    + "</td><td class=\"second\"><select><option value=\"Переезд\" selected>Переезд</option>" + op + "</select></td>"
                    + "<td class=\"third\"><input type=\"text\" name=\"room\"><input type=\"hidden\" name=\"id\" value=\""
                    + rs.getString("id") + "\"></td><td class=\"ultra\"></td></form> </tr>";

        }
        rs1.close();
        rs.close();
        stmt.close();
        con.close();
        return list;
    }
}
