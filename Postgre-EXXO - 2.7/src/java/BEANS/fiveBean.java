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

public class fiveBean {

    Connection con = null;
    Statement stmt = null;
    HttpSession session = null;
    List<Days> days;
    String week;
    String prevweek;
    String nextweek;
    String prevweeklink;
    String nextweeklink;

    void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = BASE.VER.getDBProp();
        con = DriverManager.getConnection(url, properties);
        stmt = con.createStatement();
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
        //String w=req.getParameter("w");
        Calendar calendar = GregorianCalendar.getInstance();
        if (dateString != null && !dateString.equals("")) {
            String[] ar = dateString.split("-");
            calendar.set(java.lang.Integer.parseInt(ar[0]), java.lang.Integer.parseInt(ar[1]) - 1, java.lang.Integer.parseInt(ar[2]));
        }
        Calendar prev = (Calendar) calendar.clone();
        Calendar next = (Calendar) calendar.clone();
        prev.add(Calendar.DAY_OF_YEAR, -7);
        next.add(Calendar.DAY_OF_YEAR, 7);
        week = calendar.get(Calendar.WEEK_OF_YEAR) + " неделя " + calendar.get(Calendar.YEAR) + " года";
        prevweek = prev.get(Calendar.WEEK_OF_YEAR) + " неделя";
        nextweek = next.get(Calendar.WEEK_OF_YEAR) + " неделя";
        prevweeklink = prev.get(Calendar.YEAR) + "-" + (prev.get(Calendar.MONTH) + 1) + "-" + prev.get(Calendar.DAY_OF_MONTH);
        nextweeklink = next.get(Calendar.YEAR) + "-" + (next.get(Calendar.MONTH) + 1) + "-" + next.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        days = new ArrayList<Days>();

        String time = "";

        startSQL();

        for (int i = 0; i < 5; i++) {
            String info = "Событий не назначено";
            Days day = new Days();
            time = Week[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " " + Monthes[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR) + " года";
            String today = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            String sql1 = "select hours, minutes, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS Day from events where user_id='" + user + "' and day='" + today + "' ORDER BY hours, minutes";
            ResultSet rs = stmt.executeQuery(sql1);
            while (rs.next()) {

                if (rs.isFirst()) {
                    info = "";
                }
                String pointed = "";
                GregorianCalendar start = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("Day"), rs.getInt("hours"), rs.getInt("minutes"));
                GregorianCalendar fin = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("Day"), rs.getInt("hours"), rs.getInt("minutes") + rs.getInt("long"));
                info += "<div><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <a href='event.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a></div>";
            }
            day.i = "color_" + i;
            day.date = time;
            day.info = info;
            day.today = today;
            days.add(day);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public List<Days> getDays() {
        return days;
    }

    public String getWeek() {
        return week;
    }

    public String getPrevweek() {
        return prevweek;
    }

    public String getNextweek() {
        return nextweek;
    }

    public String getPrevweeklink() {
        return prevweeklink;
    }

    public String getNextweeklink() {
        return nextweeklink;
    }

    public class Days {

        String date = "";
        String today = "";
        String info = "";
        String i;

        public String getDate() {
            return date;
        }

        public String getInfo() {
            return info;
        }

        public String getToday() {
            return today;
        }

        public String getI() {
            return i;
        }
    }
}
