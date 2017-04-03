package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ProcessIns {

    public String getOption(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT id, fio FROM employee ORDER BY fio";

        ResultSet rs = stmt.executeQuery(sql);
        String option = "<option value='0'>--------</option>";
        while (rs.next()) {
            option += "<option value='" + rs.getString(1) + "'>" + rs.getString(2) + "</option>";
        }
        rs.close();
        stmt.close();
        con.close();
        return option;
    }
}
