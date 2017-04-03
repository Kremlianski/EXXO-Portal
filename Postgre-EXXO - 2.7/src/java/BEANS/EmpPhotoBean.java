package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class EmpPhotoBean {

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        String sql = "SELECT id, fio, units.unit AS unit FROM employee, units WHERE units.unit_id=employee.unit AND fired='0' ORDER BY fio";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<div id='container'>";

        while (rs.next()) {
            list += "<a href='empCard.jsp?id=" + rs.getString("id") + "'><img class='photo' src='IMG?id=" + rs.getString("id") + "' title='" + rs.getString("fio") + ", " + rs.getString("unit") + "'></a>";

        }
        list += "</div>";
        rs.close();
        stmt.close();
        con.close();
        return list;

    }
}
