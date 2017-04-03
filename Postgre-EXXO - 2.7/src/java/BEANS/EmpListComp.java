package BEANS;

import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class EmpListComp {

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT id, fio, position, unit, head, office, room, tel, tellocal FROM employee WHERE fired='0' ORDER BY fio";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "";
        while (rs.next()) {
            String tellocal = null;
            tellocal = rs.getString("tellocal");
            list += "<div id=\"emp" + rs.getString("unit") + "\"";
            if (rs.getString("head") != null) {
                list += " class=\"boss emps\" ";
            } else {
                list += "class=\"emps\" ";
            }
            list += "><span class=\"imgE\" style=\"background-image:url(IMG?id=" + rs.getString("id") + ")\"></span><span class=\"texts exxo-shadow1\"><span id=\"employee_" + rs.getString("id") + "\" class=\"fio\">"
                    + "<a href=\"empCard.jsp?id=" + rs.getString("id") + "\" >" + rs.getString("fio") + " </a></span>"
                    + "<span class=\"position\">" + rs.getString("position") + "</span><span class=\"room\">" + rs.getString("room") + " (" + rs.getString("office") + " )</span>"
                    + "<span class=\"tel\"> " + rs.getString("tel") + " ";
            if (tellocal != null && !tellocal.equals("")) {
                list += "( " + tellocal + " )";
            }
            list += " </span></span></div>";
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }
}
