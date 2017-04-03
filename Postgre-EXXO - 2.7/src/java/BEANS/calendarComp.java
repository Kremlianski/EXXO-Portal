package BEANS;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class calendarComp {

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
        String date = req.getParameter("date");
        String sql = "";
        String ldate = "";
        if (date != null) {
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
                    + "<a href=\"calCompare?dopusk=" + rs.getString("id") + "&p=comp" + uid + ldate + "\" >" + rs.getString("fio") + " </a></span>"
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
