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

public class monthBean {

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
        String[] Monthes = {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
        session = req.getSession(true);
        String user = (String) session.getAttribute("id");
        Calendar calendar = GregorianCalendar.getInstance();
        if (dateString != null && !dateString.equals("")) {
            String[] ar = dateString.split("-");
            calendar.set(java.lang.Integer.parseInt(ar[0]), java.lang.Integer.parseInt(ar[1]) - 1, java.lang.Integer.parseInt(ar[2]));
        }
        Calendar prev = (Calendar) calendar.clone();
        Calendar next = (Calendar) calendar.clone();
        prev.set(Calendar.DATE, 15);
        next.set(Calendar.DATE, 15);
        prev.add(Calendar.MONTH, -1);
        next.add(Calendar.MONTH, 1);

        week = Monthes[calendar.get(Calendar.MONTH)] + ", " + calendar.get(Calendar.YEAR);
        prevweek = Monthes[prev.get(Calendar.MONTH)];
        nextweek = Monthes[next.get(Calendar.MONTH)];
        prevweeklink = prev.get(Calendar.YEAR) + "-" + (prev.get(Calendar.MONTH) + 1) + "-" + prev.get(Calendar.DAY_OF_MONTH);
        nextweeklink = next.get(Calendar.YEAR) + "-" + (next.get(Calendar.MONTH) + 1) + "-" + next.get(Calendar.DAY_OF_MONTH);

        Calendar cal = (Calendar) calendar.clone();
        Calendar td = GregorianCalendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        days = new ArrayList<Days>();

        String time = "";

        startSQL();

        for (int y = 0; y < 6; y++) {
            for (int i = 0; i < 7; i++) {
                if (cal.get(Calendar.MONTH) != calendar.get(Calendar.MONTH) && y != 0 && cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                    break;
                }
                Days day = new Days();
                time = cal.get(Calendar.DAY_OF_MONTH) + "";
                String today = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                String tds = td.get(Calendar.YEAR) + "-" + (td.get(Calendar.MONTH) + 1) + "-" + td.get(Calendar.DAY_OF_MONTH);
                String sql1 = "select outside, important, hours, minutes, long from events where user_id='" + user + "' and day='" + today + "' ORDER BY hours, minutes";
                ArrayList<Integer> list = new ArrayList<Integer>();
                ResultSet rs = stmt.executeQuery(sql1);
                while (rs.next()) {
                    day.c++;
                    if (rs.getBoolean("outside")) {
                        day.out++;
                    }
                    if (rs.getBoolean("important")) {
                        day.imp++;
                    }
                    day.lon += rs.getInt("long");
                    int hm = rs.getInt("hours") * 4 + rs.getInt("minutes") / 15 - 32;
                    int length = rs.getInt("long") / 15;
                    for (int l = 0; l < length; l++) {
                        list.add(hm + l);
                    }
                }
                if (cal.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) {
                    day.i = " nomonth";
                }
                if (today.equals(tds)) {
                    day.tds = " today";
                }
                day.date = time;
                day.today = today;
                days.add(day);
                cal.add(Calendar.DAY_OF_MONTH, 1);
                StringBuilder sb = new StringBuilder("");
                for (int z = 0; z <= 63; z++) {
                    sb.append("<span class='");
                    if (list.contains(z)) {
                        sb.append("busy ");
                    } else {
                        sb.append("free ");
                    }
                    if (z == 0 || z % 4 == 0) {
                        sb.append("hour");
                    }
                    sb.append("'");
                    sb.append(" id='i").append(z).append("'");
                    sb.append(">");
                    sb.append("</span>");
                }

                day.timer = sb.toString();
            }
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
        String timer = "";
        int c = 0;
        int imp = 0;
        int out = 0;
        int lon = 0;
        String tds = "";
        boolean crender = false;
        String i;

        public String getDate() {
            return date;
        }

        public String getToday() {
            return today;
        }

        public String getI() {
            return i;
        }

        public String getTds() {
            return tds;
        }

        public String getTimer() {
            return timer;
        }

        public int getC() {
            return c;
        }

        public int getImp() {
            return imp;
        }

        public int getOut() {
            return out;
        }

        public int getLon() {
            return lon;
        }

        public boolean getCrender() {
            if (c == 0) {
                return false;
            } else {
                return true;
            }
        }

        public boolean getOutrender() {
            if (out == 0) {
                return false;
            } else {
                return true;
            }
        }

        public boolean getImprender() {
            if (imp == 0) {
                return false;
            } else {
                return true;
            }
        }
    }
}
