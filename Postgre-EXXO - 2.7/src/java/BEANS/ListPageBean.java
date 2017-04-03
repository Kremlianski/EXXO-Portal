package BEANS;

import java.io.UnsupportedEncodingException;
import java.sql.*;
//import java.util.Properties;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URLEncoder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ListPageBean {

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException, UnsupportedEncodingException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT title, page_id FROM structure ORDER BY title";
        ResultSet rs = stmt.executeQuery(sql);
        Statement stmt1 = con.createStatement();
        sql = "SELECT menu FROM menu";
        ResultSet rs1 = stmt1.executeQuery(sql);
        String menu = "";
        if (rs1.next()) {
            menu = rs1.getString(1);
        }
        String list = "";
        while (rs.next()) {
            String red = "";
            String re = "\\?id=" + rs.getString(2) + "\"";
            Pattern pattern = Pattern.compile(re);
            Matcher matcher = pattern.matcher(menu);
            if (!matcher.find()) {
                red = "class=\"red\"";
            }
            list += "<tr> <td " + red + "><a href=\"main.jsp?id=" + rs.getString(2) + "\">" + rs.getString(1) + "</a></td><td class=\"red\">";
            if (!red.equals("")) {
                list += "<a href=\"addInMenu.jsp?id=" + rs.getString(2) + "&title=" + URLEncoder.encode(rs.getString(1), "utf8") + "\">>>Добавить в меню>></a>";
            }
            list += "</td></tr>";
        }

        rs1.close();
        rs.close();
        stmt.close();
        con.close();

        return list;
    }
}
