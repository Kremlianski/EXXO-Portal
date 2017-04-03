package BEANS;

import java.io.UnsupportedEncodingException;
import java.sql.*;
//import java.util.Properties;
import java.net.URLEncoder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class Territories {

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException, UnsupportedEncodingException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM offices";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table id=\"tableList\"><tbody><tr><th>Терриотория</th><th>Адрес</th><th>Телефоны</th></tr>";
        //   String op="";
        while (rs.next()) {
            if (!rs.getString("short").equals("Переезд")) {
                list += "<tr class='datas'><td class='first' id='" + URLEncoder.encode(rs.getString("short"), "utf8") + "'>" + rs.getString("short") + "</td><td class='second'>" + rs.getString("adress") + "</td><td class='third'>" + rs.getString("tel") + "</td></tr>";
//     op+="<option value='"+rs.getString("short")+"'>"+rs.getString("short")+"</option>";
            }
        }
        list += "</tbody></table>";

        rs.close();
        stmt.close();
        con.close();
        return list;
    }
}
