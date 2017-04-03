package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ResourceMode {

    String op = "";

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM resources";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table id=\"tableList\"><tbody><tr><th>Ресурс</th><th>Описание</th></tr>";
        while (rs.next()) {

            list += "<tr class='datas'><td class='first' id='" + rs.getString("id") + "'>" + rs.getString("short") + "</td><td class='second'>" + rs.getString("comment") + "</td></tr>";
            op += "<option value='" + rs.getString("id") + "'>" + rs.getString("short") + "</option>";
        }
        list += "</tbody></table>";

        rs.close();
        stmt.close();
        con.close();
        return list;
    }

    public String getOp() {
        return op;
    }
}
