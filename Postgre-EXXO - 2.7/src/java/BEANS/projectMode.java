package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class projectMode {

    String op = "";
    String emp = "";

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT projects.global_id, projects.name, projects.boss, employee.fio, employee.global_id FROM projects, employee WHERE "
                + "employee.global_id=projects.boss ORDER BY projects.name";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table id=\"tableList\"><tbody><tr><th>Проект</th><th>Руководитель</th></tr>";
        while (rs.next()) {
            String fio = rs.getString(4);
            if (fio == null) {
                fio = "Не назначен";
            }
            list += "<tr class='datas'><td class='first' id='" + rs.getString(1) + "'>" + rs.getString(2) + "</td><td class='second' id='" + rs.getString(5) + "'>" + fio + "</td></tr>";
            op += "<option value='" + rs.getString(1) + "'>" + rs.getString(2) + "</option>";
        }
        list += "</tbody></table>";

        sql = "SELECT global_id, fio FROM employee ORDER BY fio";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            emp += "<option id='" + rs.getString(1) + "' value='" + rs.getString(1) + "'>" + rs.getString(2) + "</option>";
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }

    public String getOp() {
        return op;
    }

    public String getEmp() {
        return emp;
    }
}
