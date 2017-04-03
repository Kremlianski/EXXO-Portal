package BEANS;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class calendarPhoto {

    public String back = "";
    public String res = "";
    public boolean ok = true;
    public String name = "";

    public String getList(HttpServletRequest req) throws ClassNotFoundException, SQLException, IOException {
        String list = "";
        ServletContext sc = req.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String s = req.getParameter("s");
        String id = req.getParameter("id");
        String sql = "";
        String owner = (String) req.getSession(true).getAttribute("id");
        String date = req.getParameter("date");
        String ldate = "";
        if (date != null && !date.equals("")) {
            ldate = "&date=" + date;
        }
        back = "calendarCompare.xhtml";
        if (date != null) {
            back = "calendarCompare.xhtml?date=" + date;
        }
        sql = "SELECT id, get_fio(id) FROM calCimpare WHERE owner = '" + owner + "'";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            res += "<div class='emps' id='" + rs.getInt(1) + "'>" + EXXOlib.textLib.shortFIO(rs.getString(2)) + "</div>";
        }
        sql = "SELECT id, employee.id, fio, units.unit AS unit FROM employee, units WHERE units.unit_id=employee.unit AND fired='0' ORDER BY fio";
        rs = stmt.executeQuery(sql);
        list = "<div id='container'>";

        while (rs.next()) {
            list += "<a href='calCompare?dopusk=" + rs.getString("id") + "&p=photo" + ldate + "' id='" + rs.getString("id") + "'><img class='photo' src='IMG?id=" + rs.getString("id") + "' title='" + rs.getString("fio") + ", " + rs.getString("unit") + "'></a>";

        }
        list += "</div>";
        rs.close();
        stmt.close();
        con.close();

        return list;
    }

}
