package BEANS;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BlogDopComp {

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
        String owner;
        String unit = req.getParameter("uid");
        String uid = "";
        if (unit != null) {
            uid = "&uid=" + unit;
        }
        HttpSession session = req.getSession(true);
        owner = (String) session.getAttribute("id");
        String sql = "";

        back = "fileProp.jsp?id=" + id;
        sql = "SELECT dopusk_type, dopusk, name FROM blogesTMP WHERE owner= '" + owner + "'";
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
        sql = "SELECT id, global_id, fio, position, unit, head, office, room, tel, tellocal FROM employee WHERE fired='0' ORDER BY fio";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String tellocal = null;
            tellocal = rs.getString("tellocal");
            list += "<div id=\"emp" + rs.getString("unit") + "\"";
            if (rs.getString("head") != null) {
                list += " class=\"boss emps\" ";
            } else {
                list += "class=\"emps\" ";
            }
            list += "><span class=\"imgE\"><img src=\"IMG?id=" + rs.getString("id") + "\"></span><span class=\"texts exxo-shadow1\"><span id=\"employee_" + rs.getString("id") + "\" class=\"fio\">"
                    + "<a href=\"blogDopusk?dopusk=" + rs.getString("global_id") + "&p=comp" + uid + "\" >" + rs.getString("fio") + " </a></span>"
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
