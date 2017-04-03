package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class Services {

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT process.id, process.name, process.description, employee.fio, process.stoped FROM process, employee WHERE process.owner=employee.id "
                + "ORDER BY process.name";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table id=\"tableList\"><tbody><tr><th>Сервис</th><th>Описание</th><th>Владелец</th></tr>";
        String op = "";
        while (rs.next()) {
            String stoped = "";
            if (rs.getString(5).equals("1")) {
                stoped = " stoped";
            }
            list += "<tr class='datas" + stoped + "'><td class='first' id='" + rs.getString(1) + "'>" + rs.getString(2) + "</td><td class='second'>" + rs.getString(3) + "</td>"
                    + "<td class='third'>" + rs.getString(4) + "</td></tr>";
// op+="<option value='"+rs.getString("short")+"'>"+rs.getString("short")+"</option>";
        }
        list += "</tbody></table>";
        rs.close();
        stmt.close();
        con.close();
        return list;

    }
}
