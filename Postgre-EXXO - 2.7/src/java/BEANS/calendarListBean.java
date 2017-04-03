package BEANS;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class calendarListBean {

    public String back = "";
    public String res = "";
    public boolean ok = true;

    public String getList(HttpServletRequest req) throws ClassNotFoundException, SQLException, IOException {
        String list = "";
        ServletContext sc = req.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String s = req.getParameter("s");
        String id = req.getParameter("id");
        String date = req.getParameter("date");
        HttpSession session = req.getSession(true);
        String owner = (String) session.getAttribute("id");
        String sql = "";

        back = "calendarCompare.xhtml";
        if (date != null) {
            back = "calendarCompare.xhtml?date=" + date;
        }
        sql = "SELECT id, get_fio(id) FROM calCimpare WHERE owner = '" + owner + "'";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            res += "<div class='emps' id='" + rs.getInt(1) + "'>" + EXXOlib.textLib.shortFIO(rs.getString(2)) + "</div>";
        }

        sql = "SELECT employee.id, employee.fio, employee.position, units.unit FROM employee, units "
                + "WHERE fired='0' AND employee.unit=units.unit_id ORDER BY fio";

        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            list += "<tr><td class=\"fio\"><span id=\"" + rs.getInt("id") + "\">" + rs.getString("fio") + " </span></td><td>" + rs.getString("position") + " </td><td>" + rs.getString("unit") + " </td></tr>";
        }
        rs.close();
        stmt.close();
        con.close();

        return list;
    }

}
