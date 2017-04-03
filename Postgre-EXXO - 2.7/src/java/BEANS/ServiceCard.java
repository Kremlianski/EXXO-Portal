package BEANS;

import java.sql.*;
//import java.util.Properties;
import java.io.InputStream;
//import java.text.SimpleDateFormat;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServiceCard {

    public String fio = null;
    public String name = null;
    public String owner = null;
    public String description = null;
    public String supervisor = null;
    public String type = null;
    public String minToDo = null;
    public String minToDeside = null;
    public String position = null;
    public String unit = null;
    public String template = null;
    public String photoMarker = null;
    public String stoped = null;
    public String jornal = "<table id='jornal' class='exxo-table'><tbody>";

    public void setId(String id, String sesID, HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        //     SimpleDateFormat sdf = new SimpleDateFormat();

        String sql = "SELECT employee.fio, employee.position, process.name, process.description, process.owner AS owner, process.type AS type, "
                + "process.supervisor AS supervisor, minToDo, minToDeside, units.unit AS unit, employee.photo,template, stoped  FROM employee, units, process WHERE process.id='" + id + "' AND units.unit_id=employee.unit AND employee.id=process.owner";

        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            fio = rs.getString("fio");
            unit = rs.getString("unit");
            position = rs.getString("position");
            owner = rs.getString("owner");
            supervisor = rs.getString("supervisor");
            name = rs.getString("name");
            type = rs.getString("type");
            description = rs.getString("description");
            minToDo = rs.getString("minToDo");
            minToDeside = rs.getString("minToDeside");
            template = rs.getString("template");
            InputStream is = rs.getBinaryStream("photo");
            stoped = rs.getString("stoped");
            if (is != null) {
                photoMarker = "ok";
            }

        }

        String sql2 = "SELECT id, properTime(timeEnter) AS time, text FROM pjornal WHERE pid='" + id + "' AND customer='" + sesID + "' ORDER BY timeEnter DESC LIMIT 30 ";
        ResultSet rs2 = stmt.executeQuery(sql2);

        while (rs2.next()) {
            jornal += "<tr><td class='timeEnter exxo-date'>" + rs2.getString("time") + "</td>"
                    + "<td class='tex'><a href='opCard.jsp?id=" + rs2.getString("id") + "' >" + rs2.getString("text") + "</a></td></tr>";

        }
        jornal += "</tbody></table>";

        rs2.close();
        rs.close();
        stmt.close();
        con.close();

    }

}
