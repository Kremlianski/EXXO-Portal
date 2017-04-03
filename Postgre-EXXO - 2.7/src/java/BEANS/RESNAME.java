package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class RESNAME {

    String name = "";

    public String getName(String id, HttpServletRequest r) throws SQLException, ClassNotFoundException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT short FROM resources WHERE id='" + id + "'";
        ResultSet rs1 = stmt.executeQuery(sql);
        if (rs1.next()) {
            name = rs1.getString(1);
        }
        rs1.close();
        stmt.close();
        con.close();

        return this.name;
    }

}
