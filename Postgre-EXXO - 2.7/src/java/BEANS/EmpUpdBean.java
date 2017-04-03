package BEANS;

import java.sql.*;
//import java.util.Properties;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class EmpUpdBean {

    public String fio = null;
    public String unit = null;
    public String position = null;
    public String birthday = null;
    public String head = null;
    public String education = null;
    public String tellocal = null;
    public String tellmob = null;
    public String email = null;
    public String hobby = null;
    public String comment = null;
    public String responsibility = null;
    public String supervizor = null;
    public String birthdayString = null;
    public String workString = null;
    public String tel = null;
    public String room = null;
    public String office = null;
    public String workSince = null;
    public String role = null;
    public String op = "";
    public int fired = 0;
    public String photoMarker = null;
    public String timer = null;
    public boolean swich_on = false;
    public String user = null;
    public boolean send_on = false;

    public void setParams(String tempImged, String re, String message, String id, HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        ResultSet rs = null;
        String sql = "SELECT employee.fio, employee.unit AS unit,employee.position, employee.birthday, employee.head, employee.education,"
                + " employee.tellocal, employee.tellmob, employee.email, employee.hobby,employee.comment, employee.responsibility,"
                + "units.unit AS unitName,employee.photo,employee.tel,employee.room,employee.office, workSince, role, fired"
                + " FROM employee, units WHERE id='" + id + "' and unit_id=employee.unit";

        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            fio = rs.getString("fio");
            unit = rs.getInt("unit") + "";
            position = rs.getString("position");
            birthday = rs.getString("birthday");
            head = rs.getString("head");
            education = rs.getString("education");
            tellocal = rs.getString("tellocal");
            tellmob = rs.getString("tellmob");
            email = rs.getString("email");
            hobby = rs.getString("hobby");
            comment = rs.getString("comment");
            responsibility = rs.getString("responsibility");
            supervizor = rs.getString("unitName");
            tel = rs.getString("tel");
            room = rs.getString("room");
            office = rs.getString("office");
            workSince = rs.getString("workSince");
            role = rs.getString("role");
            fired = rs.getInt("fired");
            timer = System.currentTimeMillis() + "";
            InputStream is = rs.getBinaryStream("photo");
            if (is != null) {
                photoMarker = "ok";
            }

        }

        boolean sendlogin = false;
        String sl = r.getServletContext().getInitParameter("send_login");
        if (sl != null && sl.equalsIgnoreCase("true")) {
            sendlogin = true;
        }
        String host = r.getServletContext().getInitParameter("smtp_server");
        String from = r.getServletContext().getInitParameter("admin_mail");
        if (sendlogin && host != null && from != null) {
            send_on = true;
        }

        if (birthday != null) {
            birthdayString = "new Date(" + birthday.replace('-', ',') + ")";
        }
        if (workSince != null) {
            workString = "new Date(" + workSince.replace('-', ',') + ")";
        }
        String sql1 = "SELECT short FROM offices";
        ResultSet rs1 = stmt.executeQuery(sql1);
        String selected = "";
        while (rs1.next()) {
            selected = "";

            if (!rs1.getString("short").equals("Переезд") || office.equals("Переезд")) {
                if (rs1.getString("short").equals(office)) {
                    selected = "SELECTED";
                }
                op += "<option value='" + rs1.getString("short") + "' " + selected + ">" + rs1.getString("short") + "</option>";
            }
        }

        rs = stmt.executeQuery("SELECT login FROM users WHERE id='" + id + "'");
        if (rs.next()) {
            user = rs.getString(1);
        }
        if (user != null) {
            rs = stmt.executeQuery("SELECT * FROM user_roles WHERE login='" + user + "' AND role='portal_user'");
            if (rs.next()) {
                swich_on = true;
            }
        }
        rs1.close();
        rs.close();
        stmt.close();
        con.close();
    }

}
