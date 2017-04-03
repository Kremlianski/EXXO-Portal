package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class cssList {

    String list = "";

    public String getList(HttpServletRequest r) throws SQLException, ClassNotFoundException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        String sql = "SELECT page FROM css1 ORDER BY page";
        ResultSet rs1 = stmt.executeQuery(sql);
        while (rs1.next()) {
            list += "<a href=\"css1.jsp?page=" + rs1.getString(1) + "\">" + rs1.getString(1) + "</a><br>";
        }

        rs1.close();
        stmt.close();
        con.close();
        return this.list;
    }

}
