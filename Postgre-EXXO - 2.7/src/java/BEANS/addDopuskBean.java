package BEANS;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class addDopuskBean {

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
        if (s == null) {
            s = "files";
        }
        if (id == null) {
            ok = false;
        } else {

            back = "fileProp.jsp?id=" + id;
            sql = "SELECT dopusk_type, dopusk, name FROM " + s + " WHERE id = '" + id + "'";
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
                            res += "<div class='emps' id='" + dopuskrs.getInt(2) + "'>" + EXXOlib.textLib.shortFIO(rr.getString(1)) + "</div>";
                        }
                        rr.close();
                    } else {
                        ok = false;
                    }

                }
            }
            sql = "SELECT employee.global_id, employee.fio, employee.position, units.unit FROM employee, units "
                    + "WHERE fired='0' AND employee.unit=units.unit_id ORDER BY fio";

            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list += "<tr><td class=\"fio\"><span id=\"" + rs.getInt("global_id") + "\">" + rs.getString("fio") + " </span></td><td>" + rs.getString("position") + " </td><td>" + rs.getString("unit") + " </td></tr>";
            }
            rs.close();
            stmt.close();
            con.close();

        }
        return list;
    }

}
