package BEANS;

import java.sql.*;
//import java.util.Properties;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class UnitBean {

    private String list = "";

    public String getName(String id, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String name = "";
        String sql = "SELECT text  FROM unitTexts WHERE unit_id='" + id + "' LIMIT 1";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            list = rs.getString(1);
        }
        sql = "SELECT unit FROM units WHERE unit_id='" + id + "'";
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            name = rs.getString(1);
        }
        rs.close();
        stmt.close();
        con.close();

        return name;
    }

    public String getList() {
        return list;
    }
}
