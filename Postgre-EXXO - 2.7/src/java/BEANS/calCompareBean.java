package BEANS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped

public class calCompareBean {

    Connection con = null;
    Statement stmt = null;
    HttpSession session = null;
    Statement stmt1 = null;
    String time;
    String timedown;
    String timeup;
    String today;
    String nothing = "<div id='nothing'>Событий не назначено!</div>";
    String add = "";
    String scale = "";
    String schedule = "";
    String timer = "";
    List<Employees> employees;

    void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = BASE.VER.getDBProp();
        con = DriverManager.getConnection(url, properties);
        stmt = con.createStatement();
        stmt1 = con.createStatement();
    }

    public void initParams(String dateString) throws ClassNotFoundException, SQLException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        session = req.getSession(true);
        //  SimpleDateFormat sf1=new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        String[] Monthes = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        String[] Week = {"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        SimpleDateFormat sf = new SimpleDateFormat("H:mm");
        String user = (String) session.getAttribute("id");
        String sql = "SELECT hours, minutes, day, long FROM events WHERE day='" + dateString + "' AND user_id='" + user + "'  ORDER BY hours, minutes";
        if (dateString == null || dateString.equals("")) {
            sql = "SELECT hours, minutes, day, long FROM events WHERE day=now()::date AND user_id='" + user + "'  ORDER BY hours, minutes";
        }
        startSQL();
        ArrayList<Integer> list = new ArrayList<Integer>();
        ResultSet rs = stmt.executeQuery(sql);
        Calendar calendar = GregorianCalendar.getInstance();
        Calendar down = GregorianCalendar.getInstance();
        Calendar up = GregorianCalendar.getInstance();
        if (dateString != null && !dateString.equals("")) {
            String[] ar = dateString.split("-");
            calendar.set(java.lang.Integer.parseInt(ar[0]), java.lang.Integer.parseInt(ar[1]) - 1, java.lang.Integer.parseInt(ar[2]));
            down.set(java.lang.Integer.parseInt(ar[0]), java.lang.Integer.parseInt(ar[1]) - 1, java.lang.Integer.parseInt(ar[2]));
            up.set(java.lang.Integer.parseInt(ar[0]), java.lang.Integer.parseInt(ar[1]) - 1, java.lang.Integer.parseInt(ar[2]));
        }
        down.add(Calendar.DAY_OF_MONTH, -1);
        up.add(Calendar.DAY_OF_MONTH, 1);
        employees = new ArrayList<Employees>();
        today = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        add = "<a href=\"eventsAdd.xhtml?date=" + today + "\" id=\"new\">новое событие</a>";
        timedown = down.get(Calendar.YEAR) + "-" + (down.get(Calendar.MONTH) + 1) + "-" + down.get(Calendar.DAY_OF_MONTH);
        timeup = up.get(Calendar.YEAR) + "-" + (up.get(Calendar.MONTH) + 1) + "-" + up.get(Calendar.DAY_OF_MONTH);
        time = Week[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " " + Monthes[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR) + " года";
        while (rs.next()) {
            int hm = rs.getInt(1) * 4 + rs.getInt(2) / 15 - 32;
            int length = rs.getInt(4) / 15;
            for (int l = 0; l < length; l++) {
                list.add(hm + l);
            }
        }

        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i <= 63; i++) {
            sb.append("<span class='");
            if (list.contains(i)) {
                sb.append("busy ");
            }
            if (i == 0 || i % 4 == 0) {
                sb.append("hour");
            }
            sb.append("'>");
            sb.append("</span>");
        }

        timer = sb.toString();

        sql = "SELECT get_fio(id) AS name, id FROM calCimpare WHERE owner=" + user;
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Employees employee = new Employees();
            employee.id = rs.getInt("id");
            employee.name = rs.getString("name");
            employees.add(employee);
        }

        for (int y = 0; y < employees.size(); y++) {
            list = new ArrayList<Integer>();
            Employees employee = employees.get(y);
            sql = "SELECT hours, minutes, day, long FROM events WHERE day='" + dateString + "' AND user_id='" + employee.id + "'  ORDER BY hours, minutes";
            if (dateString == null || dateString.equals("")) {
                sql = "SELECT hours, minutes, day, long FROM events WHERE day=now()::date AND user_id='" + employee.id + "'  ORDER BY hours, minutes";
            }
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int hm = rs.getInt(1) * 4 + rs.getInt(2) / 15 - 32;
                int length = rs.getInt(4) / 15;
                for (int l = 0; l < length; l++) {
                    list.add(hm + l);
                }
            }

            sb = new StringBuilder("");
            for (int i = 0; i <= 63; i++) {
                sb.append("<span class='");
                if (list.contains(i)) {
                    sb.append("busy ");
                }
                if (i == 0 || i % 4 == 0) {
                    sb.append("hour");
                }
                sb.append("'>");
                sb.append("</span>");
            }
            employee.schedule = sb.toString();
        }

        rs.close();
        stmt.close();
        stmt.close();
        con.close();

    }

    public String getTimer() {
        return timer;
    }

    public String getToday() {
        return today;
    }

    public String getTime() {
        return time;
    }

    public String getTimedown() {
        return timedown;
    }

    public String getTimeup() {
        return timeup;
    }

    public String getAdd() {
        return add;
    }

    public String getScale() {
        StringBuilder sb = new StringBuilder("");
        for (int i = 9; i <= 24; i++) {
            for (int y = 0; y < 4; y++) {
                if (y == 3) {
                    sb.append("<span>");
                    sb.append(i);
                    sb.append("</span>");
                } else {
                    sb.append("<span></span>");
                }
            }
        }
        return sb.toString();

    }

    public String getSchedule() {
        StringBuilder sb = new StringBuilder("");
        for (int i = 9; i <= 24; i++) {
            for (int y = 0; y < 4; y++) {
                if (y == 3) {
                    sb.append("<span class='graf'></span>");
                } else {
                    sb.append("<span></span>");
                }
            }
        }
        return sb.toString();

    }

    public List getEmployees() {
        return employees;
    }

    public class Employees {

        String name;
        int id;
        String schedule = "";

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public String getSchedule() {
            return schedule;
        }

    }

}
