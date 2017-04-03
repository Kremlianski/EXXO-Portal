package BEANS;

import java.sql.*;
//import java.util.Properties;
//import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class OpCardBean {

    public String name = null;
    public String owner = null;
    public String fio = null;
    public String position = null;
    public String unit = null;
    public String classLate = "";
    public String STATUS = "";
    public String nextTime = "";
    public String buttons = "";
    public String TimeEnter = null;
    public String text = null;
    public String why = null;
    public String WHY = "Причина отказа: ";
    public String comment = null;
    public Boolean R = false;
    public Boolean DoneIsDone = false;

    public void setId(String id, String user, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        Timestamp timeEnter = null;
        Timestamp timeupd = null;
        Timestamp now = null;
        int minToDeside = 0;
        int minToOpen = 0;
        int minToDo = 0;
        int status = 0;
        Calendar calendar = GregorianCalendar.getInstance();
        Calendar NOW = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat();
        String stoped = null;
        boolean decide = false;
        boolean answer = false;
        String type = null;
        String customer = null;
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        String sql = "SELECT process.name, employee.fio, pjornal.customer, pjornal.text, process.type,properTime(pjornal.timeEnter) AS time, properTime(pjornal.timeupd) AS time1,"
                + "process.minToDeside, process.minToOpen, process.minToDo, employee.position, units.unit, pjornal.status, now(), why, owner,pjornal.comment AS comment, pjornal.timeupd  FROM pjornal, process, units, employee "
                + "WHERE pjornal.id='" + id + "' AND employee.id=process.owner AND employee.unit=units.unit_id AND pjornal.pid=process.id";

        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            name = rs.getString(1);
            fio = rs.getString(2);
            customer = rs.getString(3);
            text = rs.getString(4);
            type = rs.getString(5);
            TimeEnter = rs.getString(6);

            timeupd = rs.getTimestamp("timeupd");
            minToDeside = rs.getInt(8);
            minToOpen = rs.getInt(9);
            minToDo = rs.getInt(10);
            position = rs.getString(11);
            unit = rs.getString(12);
            status = rs.getInt(13);
            now = rs.getTimestamp(14);
            NOW.setTime(now);
            why = rs.getString("why");
            comment = rs.getString("comment");
            owner = rs.getString("owner");

            if (type.equals("answerIsDone")) {
                WHY = "Ответ";
            }
            if (timeupd != null) {
                calendar.setTime(timeupd);
            } else {
                calendar.setTime(now);
            }

            if (status == 1 && minToDeside != 0 && !type.equals("simple")) {
                calendar.add(Calendar.MINUTE, minToDeside);
                if (calendar.before(NOW)) {
                    classLate = " late";
                }
                nextTime = "Думать до: " + sdf.format(calendar.getTime());
                buttons = "<div id='decision'></div>";
                decide = true;
            }

            if (status == 2 && minToDo != 0 && type.equals("DoneIsDone")) {
                calendar.add(Calendar.MINUTE, minToDo);
                if (calendar.before(NOW)) {
                    classLate = " late";
                }
                nextTime = "Сделать до: " + sdf.format(calendar.getTime());
            }
            if (status == 1 && type.equals("simple")) {
                STATUS = "ВЫПОЛНЕНО";
            } else if (status == 0) {
                STATUS = "НЕ ОТКРЫТО";
            } else if (status == 1 && !type.equals("answerIsDone")) {
                STATUS = "ОЖИДАЕТ РЕШЕНИЯ";
            } else if (status == 1 && type.equals("answerIsDone")) {
                STATUS = "ОЖИДАЕТ ОТВЕТА";
            } else if (status == 2 && type.equals("desideIsDone")) {
                STATUS = "ВЫПОЛНЕНО";
            } else if (status == 2 && type.equals("DoneIsDone")) {
                STATUS = "ОЖИДАЕТ ВЫПОЛНЕНИЯ";
            } else if (status == 3) {
                STATUS = "ОТКАЗ";
            } else if (status == 5) {
                STATUS = "ВЫПОЛНЕНО";
            } else if (status == 4) {
                STATUS = "ВОПРОС ЗАКРЫТ";
                DoneIsDone = true;
            }
            if (!customer.equals(user)) {
                R = true;
            }
        } else {
            R = true;
        }
        rs.close();
        stmt.close();
        con.close();
    }
}
