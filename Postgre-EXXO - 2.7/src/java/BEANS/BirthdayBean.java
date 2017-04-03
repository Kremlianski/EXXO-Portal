package BEANS;

import java.sql.*;
import java.util.Properties;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class BirthdayBean {

    public String getList(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT employee.id, employee.fio, employee.position, units.unit AS unit, birthday FROM employee, units "
                + "WHERE employee.unit=units.unit_id AND fired='0' ORDER BY EXTRACT(MONTH FROM birthday), EXTRACT(DAY FROM birthday)";

        Calendar calendar = GregorianCalendar.getInstance();
        String[] Monthes = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};

        ResultSet rs = stmt.executeQuery(sql);
        String res = "";
        while (rs.next()) {
            Date birthday = rs.getDate("birthday");
            calendar.setTime(birthday);

            res += "<tr><td class=\"birthday exxo-date\">" + calendar.get(Calendar.DAY_OF_MONTH) + " " + Monthes[calendar.get(Calendar.MONTH)] + "</td>"
                    + "<td class=\"fio\"><span id=\"" + rs.getString("id") + " \">" + rs.getString("fio") + " </span></td>"
                    + "<td> " + rs.getString("unit") + " </td><td>" + rs.getString("position") + "</td></tr>";

        }
        rs.close();
        stmt.close();
        con.close();
        return res;
    }
}
