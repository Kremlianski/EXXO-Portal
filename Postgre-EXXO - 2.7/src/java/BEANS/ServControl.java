package BEANS;

import java.sql.*;
//import java.util.Properties;
import java.text.SimpleDateFormat;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServControl {

    public String navi = "";

    public String getList(Boolean BOSS, String id, HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        SimpleDateFormat sdf = new SimpleDateFormat();
        String offset = "0";
        if (r.getParameter("of") != null) {
            offset = r.getParameter("of");
        }
        boolean next = false;
        int ROWS = BASE.VER.getMaxRows(sc);
        int rows = ROWS + 1;
        String sql = "";
        if (BOSS) {
            sql = "SELECT pjornal.timeEnter, pjornal.id, process.name, pjornal.status, employee.fio FROM pjornal, process, employee WHERE pjornal.pid=process.id "
                    + "AND employee.id=pjornal.customer ORDER BY status, timeEnter DESC LIMIT " + rows + " OFFSET " + offset;
        } else {
            sql = "SELECT pjornal.timeEnter, pjornal.id, process.name, pjornal.status, employee.fio FROM pjornal, process, employee WHERE pjornal.pid=process.id "
                    + "AND employee.id=pjornal.customer AND process.supervisor='" + id + "' ORDER BY status, timeEnter DESC LIMIT " + rows + " OFFSET " + offset;
        }
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table id=\"tableList\"><tbody><tr><th>Время</th><th>Номер операции</th><th>Операция</th><th>Отправитель</th></tr>";
        String op = "";
        int cc = 0;
        while (rs.next()) {
            if (rs.isLast() && cc == rows - 1) {
                next = true;
            } else {
                cc++;
                String status = rs.getString(4);
                String stat = "";
                String fio = rs.getString(5);

                if (status.equals("0")) {
                    stat = "notopen";
                } else if (status.equals("1")) {
                    stat = "notdesided";
                } else if (status.equals("2")) {
                    stat = "permitted";
                } else if (status.equals("3")) {
                    stat = "rejected";
                } else if (status.equals("4")) {
                    stat = "closedQ";
                }

                list += "<tr class='datas " + stat + "'><td class='first' >" + sdf.format(rs.getTimestamp("timeEnter")) + "</td><td class='second'>" + rs.getString(2) + "</td>"
                        + "<td class='third'>" + rs.getString(3) + "</td><td class='fio'>" + fio + "</td></tr>";
// op+="<option value='"+rs.getString("short")+"'>"+rs.getString("short")+"</option>";
            }
        }
        list += "</tbody></table>";
        rs.close();
        stmt.close();
        con.close();
        navi += "<div id='menu-navi'><table><tr><td class='oneNavi'>";
        String andown = "";
        int of = Integer.parseInt(offset);
        if (of > 0) {
            int prev = of - ROWS;
            if (prev < 0) {
                prev = 0;
            }
            navi += "<a href='servControl.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + ROWS;
            navi += "<a href='servControl.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";
        return list;
    }
}
