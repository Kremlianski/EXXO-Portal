package BEANS;

import java.sql.*;
//import java.util.Properties;
//import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class OperationCard {

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
    public String customer = null;
    public boolean decide = false;
    public boolean answer = false;
    public int status = 0;

    public void setId(String id, String user, String role, HttpServletRequest r) throws ClassNotFoundException, SQLException {
//String photoMarker=null;
        String type = null;
        Timestamp timeEnter = null;
        Timestamp timeupd = null;
        Timestamp now = null;
        int minToDeside = 0;
        int minToOpen = 0;
        int minToDo = 0;
        String stoped = null;
        String supervisor = null;
        boolean BOSS = false;
        if (role.indexOf("a") >= 0 || role.indexOf("i") >= 0) {
            BOSS = true;
        }

        Calendar calendar = GregorianCalendar.getInstance();
        Calendar NOW = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat();

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT process.name, employee.fio, pjornal.customer, pjornal.text, process.type,properTime(pjornal.timeEnter) AS time, pjornal.timeupd,"
                + "process.minToDeside, process.minToOpen, process.minToDo, employee.position, units.unit, pjornal.status, now(), why,"
                + " owner,pjornal.comment AS comment, process.supervisor  FROM pjornal, process, units, employee "
                + "WHERE pjornal.id='" + id + "' AND employee.id=pjornal.customer AND employee.unit=units.unit_id AND pjornal.pid=process.id";

        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            name = rs.getString(1);
            fio = rs.getString(2);
            customer = rs.getString(3);
            text = rs.getString(4);
            type = rs.getString(5);
            //    timeEnter=rs.getTimestamp(6);
            TimeEnter = rs.getString("time");
            timeupd = rs.getTimestamp(7);
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
            supervisor = rs.getString("supervisor");
            if (status == 0 && owner.equals(user)) {
                int st = 1;

                if (type.equals("simple")) {
                    st = 5;
                }

                String sql2 = "UPDATE pjornal SET status='" + st + "', timeupd=now() "
                        + "where id='" + id + "'";
                status = st;
                stmt.executeUpdate(sql2);
            }
            if (type.equals("answerIsDone")) {
                answer = true;
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
            } else if (status == 1 && !type.equals("answerIsDone")) {
                STATUS = "ОЖИДАЕТ РЕШЕНИЯ";
            } else if (status == 1 && type.equals("answerIsDone")) {
                STATUS = "ОЖИДАЕТ ОТВЕТА";
                decide = false;
            } else if (status == 2 && type.equals("desideIsDone")) {
                STATUS = "ВЫПОЛНЕНО";
            } else if (status == 2 && type.equals("DoneIsDone")) {
                DoneIsDone = true;
                STATUS = "ОЖИДАЕТ ВЫПОЛНЕНИЯ";
            } else if (status == 3) {
                STATUS = "ОТКАЗ";
            } else if (status == 5) {
                STATUS = "ВЫПОЛНЕНО";
            } else if (status == 4) {
                STATUS = "ВОПРОС ЗАКРЫТ";
            } else if (status == 0) {
                STATUS = "НЕ ОТКРЫТО";
            }

            if (!owner.equals(user) && !supervisor.equals(user) && !BOSS) {
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
