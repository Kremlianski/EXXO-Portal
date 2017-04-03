package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class CSSTotal {

    String css = "";

    public String getCSS(HttpServletRequest r) throws SQLException, ClassNotFoundException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT css FROM css";
        ResultSet rs1 = stmt.executeQuery(sql);
        if (rs1.next()) {
            css = rs1.getString(1);
        }
        rs1.close();
        stmt.close();
        con.close();
        return this.css;
    }

}
