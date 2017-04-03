package BEANS;

import java.sql.*;
//import java.util.Properties;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class EmpCardBean {

    public String fio = null;
    public String unit = null;
    public String position = null;
    public String birthday = null;
    public String head = null;
    public String education = null;
    public String tel = null;
    public String tellocal = null;
    public String tellmob = null;
    public String email = null;
    public String hobby = null;
    public String comment = null;
    public String responsibility = null;
    public String supervizor = null;
    public String birthdayString = null;
    public String workString = null;
    public String adress = null;
    public String room = null;
    public String workSince = null;
    public int fired = 0;
    public Date firedSince = null;
    public String photoMarker = null;
    public String services = "";
    public String lasttime = "Сотрудник не зарегистрировался";

    public void setId(String ID, HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String[] Week = {"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        String[] Months = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        String sql = "SELECT employee.fio, employee.unit AS unit,employee.position, employee.birthday, employee.head,"
                + " employee.education, employee.tellocal, employee.tellmob, employee.email, employee.hobby,employee.comment, "
                + "employee.responsibility,units.unit AS unitName,employee.photo,employee.tel AS tel, offices.adress AS adress, employee.room, "
                + "workSince, fired, firedSince, EXTRACT(DAY FROM birthday) AS dayb, EXTRACT(DAY FROM workSince) AS dayw,"
                + "EXTRACT(DOW FROM birthday) AS weekb, EXTRACT(DOW FROM workSince) AS weekw, EXTRACT(YEAR FROM birthday) AS yearb, EXTRACT(YEAR FROM workSince) AS yearw,"
                + "EXTRACT(MONTH FROM birthday) AS monthb, EXTRACT(MONTH FROM workSince) AS monthw  FROM employee, units, offices WHERE employee.office<>'Переезд' AND id='" + ID + "' and unit_id=employee.unit and "
                + "employee.office=offices.short UNION ALL "
                + "SELECT employee.fio, employee.unit AS unit,employee.position, employee.birthday, employee.head,"
                + " employee.education, employee.tellocal, employee.tellmob, employee.email, employee.hobby,employee.comment, "
                + "employee.responsibility,units.unit AS unitName,employee.photo,employee.tel AS tel, 'Офис не опредедлен' AS adress, employee.room, "
                + "workSince, fired, firedSince, EXTRACT(DAY FROM birthday) AS dayb, EXTRACT(DAY FROM workSince) AS dayw,"
                + "EXTRACT(DOW FROM birthday) AS weekb, EXTRACT(DOW FROM workSince) AS weekw, EXTRACT(YEAR FROM birthday) AS yearb, EXTRACT(YEAR FROM workSince) AS yearw,"
                + "EXTRACT(MONTH FROM birthday) AS monthb, EXTRACT(MONTH FROM workSince) AS monthw  FROM employee, units, offices WHERE id='" + ID + "' and unit_id=employee.unit and "
                + "employee.office='Переезд'";

        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            fio = rs.getString("fio");
            unit = rs.getInt("unit") + "";
            position = rs.getString("position");
            birthday = rs.getInt("dayb") + " " + Months[rs.getInt("monthb") - 1] + " " + rs.getInt("yearb") + ", " + Week[rs.getInt("weekb")];
            head = rs.getInt("head") + "";
            education = rs.getString("education");
            tellocal = rs.getString("tellocal");
            tel = rs.getString("tel");
            tellmob = rs.getString("tellmob");
            email = rs.getString("email");
            hobby = rs.getString("hobby");
            comment = rs.getString("comment");
            responsibility = rs.getString("responsibility");
            supervizor = rs.getString("unitName");
            adress = rs.getString("adress");
            room = rs.getString("room");
            if (rs.getString("workSince") != null) {
                workSince = rs.getInt("dayw") + " " + Months[rs.getInt("monthw") - 1] + " " + rs.getInt("yearw");
            }
            InputStream is = rs.getBinaryStream("photo");
            fired = rs.getInt("fired");
            firedSince = rs.getDate("firedSince");

            if (is != null) {
                photoMarker = "ok";
            }

        }
        if (birthday != null) {
            birthdayString = "new Date(" + birthday.replace('-', ',') + ")";
        }
        if (workSince != null) {
            workString = "new Date(" + workSince.replace('-', ',') + ")";
        }

        String sql2 = "SELECT id,name FROM process WHERE owner='" + ID + "'";
        ResultSet rs2 = stmt.executeQuery(sql2);
        while (rs2.next()) {
            services += "<a href='serviceCard.jsp?id=" + rs2.getString(1) + "'>" + rs2.getString(2) + "</a><br>";
        }

        SimpleDateFormat sf = new SimpleDateFormat("d.MM.yyyy");
        sql = "SELECT t FROM ips WHERE id=" + ID + " ORDER BY t DESC LIMIT 1";
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            lasttime = "<div>последний визит:</div>" + sf.format(rs.getTimestamp(1));
        }
        rs.close();
        rs2.close();
        stmt.close();
        con.close();

    }

}
