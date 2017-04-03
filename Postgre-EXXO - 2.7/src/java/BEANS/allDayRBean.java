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

public class allDayRBean {

    Connection con = null;
    Statement stmt = null;
    HttpSession session = null;
    Statement stmt1 = null;
    List<Event> events;
    String time;
    String timedown;
    String timeup;
    String today;
    String nothing = "<div id='nothing'>Событий не назначено!</div>";
    String add = "";
    String scale = "";
    String schedule = "";
    String timer = "";

    void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = BASE.VER.getDBProp();
        con = DriverManager.getConnection(url, properties);
        stmt = con.createStatement();
        stmt1 = con.createStatement();
    }

    public void initParams(String dateString, String id_res) throws ClassNotFoundException, SQLException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        boolean yes = false;
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        if (role.indexOf("a") >= 0 || role.indexOf("f") >= 0) {
            yes = true;
        }
        //  SimpleDateFormat sf1=new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        String[] Monthes = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        String[] Week = {"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        SimpleDateFormat sf = new SimpleDateFormat("H:mm");
        String user = (String) session.getAttribute("id");
        events = new ArrayList<Event>();
        String sql = "SELECT hours, minutes, day, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS D, descr, hours>=extract(hour from now()) AS actualh,extract(day from day)> extract(day from now()) actualD, extract(day from day)= extract(day from now()) AS equalD , fio, user_id FROM eventsres WHERE day='" + dateString + "' AND id_res='" + id_res + "'  ORDER BY hours, minutes";
        if (dateString == null || dateString.equals("")) {
            sql = "SELECT hours, minutes, day, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS D,descr, hours>=extract(hour from now()) AS actualh,extract(day from day)> extract(day from now()) actualD, extract(day from day)= extract(day from now()) AS equalD, fio, user_id FROM eventsres WHERE day=now()::date AND id_res='" + id_res + "'  ORDER BY hours, minutes";
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

        today = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        add = "<a href=\"eventsAdd.xhtml?date=" + today + "\" id=\"new\">новое событие</a>";
        timedown = down.get(Calendar.YEAR) + "-" + (down.get(Calendar.MONTH) + 1) + "-" + down.get(Calendar.DAY_OF_MONTH);
        timeup = up.get(Calendar.YEAR) + "-" + (up.get(Calendar.MONTH) + 1) + "-" + up.get(Calendar.DAY_OF_MONTH);
        time = Week[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " " + Monthes[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR) + " года";
        while (rs.next()) {
            nothing = "";
            Event event = new Event();
            event.id = rs.getString("id");
            if (user.equals(rs.getString("user_id"))) {
                event.name = "<a href='eventR.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a>";
                event.descr = rs.getString("descr").replaceAll("\n", "<p>");
                event.descr = "<p>" + event.descr;
                event.kill = "<a href='eventsRAdd.xhtml?id=" + event.id + "' class='kill'>редактировать</a>";
            } else if (yes) {
                event.name = "<a href='eventR.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("fio") + "</a>";
                event.kill = "<a href='eventsRAdd.xhtml?id=" + event.id + "' class='kill'>редактировать</a>";
                event.my = " myEv";
            } else {
                event.name = "<span>" + rs.getString("fio") + "</span>";
                event.my = " myEvent";
            }

            event.date = rs.getString("day");
            event.lon = rs.getInt("long");
            add = "<a href=\"eventsRAdd.xhtml?date=" + event.date + "&amp;r=" + id_res + "\" id=\"new\">новое событие</a>";
            event.list = "";
            GregorianCalendar ngc2 = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("D"), rs.getInt("hours"), rs.getInt("minutes"));
            GregorianCalendar ngc1 = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("D"), rs.getInt("hours"), rs.getInt("minutes") + event.lon);
            event.data = sf.format(ngc2.getTime()) + " - " + sf.format(ngc1.getTime());
            if (rs.getBoolean("actualD") || (rs.getBoolean("equalD") && rs.getBoolean("actualH"))) {
                event.actual = true;
            }
            if (!event.actual) {
                event.data = "<span class='notactual'>" + event.data + "</span>";
            }

            events.add(event);
            int hm = rs.getInt(1) * 4 + rs.getInt(2) / 15 - 32;
            int length = rs.getInt(5) / 15;
            for (int l = 0; l < length; l++) {
                list.add(hm + l);
            }
        }

        rs.close();
        stmt.close();
        stmt.close();
        con.close();

        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i <= 63; i++) {
            sb.append("<span class='");
            if (list.contains(i)) {
                sb.append("busy ");
            } else {
                sb.append("free ");
            }
            if (i == 0 || i % 4 == 0) {
                sb.append("hour");
            }
            sb.append("'");
            sb.append(" id='i").append(i).append("'");
            sb.append(">");
            sb.append("</span>");
        }

        timer = sb.toString();

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

    public List<Event> getEvents() {
        return events;
    }

    public String getNothing() {
        return nothing;
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

    public class Event {

        String date = "";
        String name = "";
        String descr = "";
        String type = "";
        String id = "";
        String time = "";
        int lon = 0;
        String data = "";
        String user = "";
        String kill = "";
        String add = "";
        String file = "";
        String list = "";
        String my = "";
        boolean actual = false;
        String filesNo = "нет вложенных файлов";
        boolean important = false;
        boolean outside = false;
        boolean nophone = false;

        public String getId() {
            return id;
        }

        public void setName(String l) {
            name = l;
        }

        public String getName() {
            return name;
        }

        public void setList(String l) {
            list = l;
        }

        public String getList() {
            return list;
        }

        public void setData(String l) {
            data = l;
        }

        public String getData() {
            return data;
        }

        public void setAdd(String l) {
            add = l;
        }

        public String getAdd() {
            return add;
        }

        public void setFile(String l) {
            file = l;
        }

        public String getFile() {
            return file;
        }

        public void setKill(String l) {
            kill = l;
        }

        public String getKill() {
            return kill;
        }

        public void setId(String l) {
            id = l;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String l) {
            descr = l;
        }

        public int getLon() {
            return lon;
        }

        public void setLon(int l) {
            lon = l;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String l) {
            date = l;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String l) {
            time = l;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String l) {
            user = l;
        }

        public boolean getImportant() {
            return important;
        }

        public void setImportant(boolean l) {
            important = l;
        }

        public boolean getOutside() {
            return outside;
        }

        public void setOutside(boolean l) {
            outside = l;
        }

        public boolean getNophone() {
            return nophone;
        }

        public void setNophone(boolean l) {
            nophone = l;
        }

        public void setFilesNo(String l) {
            filesNo = l;
        }

        public String getFilesNo() {
            return filesNo;
        }

        public boolean getActual() {
            return actual;
        }

        public void setActual(boolean l) {
            actual = l;
        }

        public String getMy() {
            return my;
        }

        public void setMy(String l) {
            my = l;
        }
    }
}
