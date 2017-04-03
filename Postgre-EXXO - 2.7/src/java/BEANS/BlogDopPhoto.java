package BEANS;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class BlogDopPhoto {

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
        back = "fileProp.jsp?id=" + id;
        sql = "SELECT dopusk_type, dopusk, name FROM blogesTMP WHERE owner = '" + owner + "'";
        ResultSet rs = stmt.executeQuery(sql);
        boolean trig = true;
        int def = 0;
        ResultSet dopuskrs = null;
        String dopusk_type = "";
        if (rs.next()) {
            name = rs.getString(3);
            dopusk_type = rs.getString(1);
            java.sql.Array r = rs.getArray(2);
            if (r != null) {
                dopuskrs = r.getResultSet();
            } else {
                trig = false;
            }
        }
        if (!dopusk_type.equals("4")) {
            ok = false;
        }
        if (trig) {
            while (dopuskrs.next()) {
                if (dopuskrs.isFirst()) {
                    def = dopuskrs.getInt(2);
                }
                if (dopusk_type.equals("4")) {
                    sql = "SELECT fio FROM employee WHERE global_id='" + dopuskrs.getInt(2) + "'";
                    ResultSet rr = stmt.executeQuery(sql);
                    while (rr.next()) {
                        res += "<div class='emps' id='" + dopuskrs.getInt(2) + "'>" + rr.getString(1) + "</div>";
                    }
                    rr.close();
                } else {
                    ok = false;
                }

            }
        }
        sql = "SELECT id, employee.global_id, fio, units.unit AS unit FROM employee, units WHERE units.unit_id=employee.unit AND fired='0' ORDER BY fio";
        rs = stmt.executeQuery(sql);
        list = "<div id='container'>";

        while (rs.next()) {
            list += "<a href='blogDopusk?dopusk=" + rs.getString("global_id") + "&p=photo' id='" + rs.getString("global_id") + "'><img class='photo' src='IMG?id=" + rs.getString("id") + "' title='" + rs.getString("fio") + ", " + rs.getString("unit") + "'></a>";

        }
        list += "</div>";
        rs.close();
        stmt.close();
        con.close();

        return list;
    }

}
