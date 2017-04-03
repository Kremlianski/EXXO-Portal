package BEANS;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class eventRBean {

    /**
     * Creates a new instance of eventsAddBean
     */
    String name = "";
    String descr = "";
    String type = "";
    String date = "";
    String id = "";
    String time = "";
    int lon = 0;
    String data = "";
    String user = "";
    String kill = "";
    String add = "";
    String resname = "Ресурс не определен!";
    String info = "Событий не задано";
    Connection con = null;
    Statement stmt = null;
    HttpSession session = null;

    void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = new Properties();
        properties = BASE.VER.getDBProp();

        con = DriverManager.getConnection(url, properties);

        stmt = con.createStatement();
    }

    public void initParams(String id) throws ClassNotFoundException, SQLException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        this.id = id;
        session = req.getSession(true);
        //  SimpleDateFormat sf1=new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        String[] Monthes = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        String[] Week = {"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        SimpleDateFormat sf = new SimpleDateFormat("H:mm");
        user = (String) session.getAttribute("id");
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("f") >= 0) {
            yes = true;
        }
        if (id != null && !id.equals("")) {
            startSQL();
            String sql = "SELECT hours, minutes, day, name, long, eventsres.id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS D, descr, id_res, short, user_id  FROM eventsres, resources WHERE eventsres.id='" + id + "' AND id_res=resources.id";
            ResultSet rs = stmt.executeQuery(sql);
            String id_res = "";
            if (rs.next()) {
                id_res = rs.getString("id_res");
                resname = "<a href='resource.jsp?id=" + rs.getString("id_res") + "'>" + rs.getString("short") + "</a>";
                name = rs.getString("name");
                descr = rs.getString("descr");
                date = rs.getString("day");
                lon = rs.getInt("long");
                if (yes || user.equals(rs.getString("user_id"))) {
                    kill = "<a href='eventsRAdd.xhtml?id=" + id + "' id='kill'>редактировать событие</a>";
                }
                add = "<a href=\"eventsRAdd.xhtml?date=" + date + "&r=" + id_res + "\" id=\"new\">новое событие</a>";

                GregorianCalendar ngc2 = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("D"), rs.getInt("hours"), rs.getInt("minutes"));
                GregorianCalendar ngc1 = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("D"), rs.getInt("hours"), rs.getInt("minutes") + lon);
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(rs.getDate("day"));
                time = Week[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " " + Monthes[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR) + " года";
                data = sf.format(ngc2.getTime()) + " - " + sf.format(ngc1.getTime());

            }

            String sql1 = "select hours, minutes, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day)AS Day , user_id, fio FROM eventsres where id_res='" + id_res + "' and day='" + date + "' ORDER BY hours, minutes";
            rs = stmt.executeQuery(sql1);

            while (rs.next()) {
                if (rs.isFirst()) {
                    info = "";
                }
                String pointed = "";
                if (rs.getString("id").equals(id)) {
                    pointed = " class='pointed'";
                }
                GregorianCalendar start = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("Day"), rs.getInt("hours"), rs.getInt("minutes"));
                GregorianCalendar fin = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("Day"), rs.getInt("hours"), rs.getInt("minutes") + rs.getInt("long"));

                if (yes) {
                    if (user.equals(rs.getString("user_id"))) {
                        info += "<div" + pointed + "><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <a href='eventR.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a></div>";
                    } else {
                        info += "<div" + pointed + "><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <a href='eventR.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("fio") + "</a></div>";
                    }
                } else {
                    if (user.equals(rs.getString("user_id"))) {
                        info += "<div" + pointed + "><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <a href='eventR.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a></div>";
                    } else {
                        info += "<div" + pointed + "><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <span>" + rs.getString("fio") + "</span></div>";
                    }
                }

            }

            rs.close();
            stmt.close();
            con.close();
        }
    }

    public String getId() {
        return id;
    }

    public void setName(String l) {
        name = l;

    }

    public String getName() {
        return name;
    }

    public void setData(String l) {
        data = l;

    }

    public String getData() {
        return data;
    }

    public void setInfo(String l) {
        info = l;

    }

    public String getInfo() {
        return info;
    }

    public void setAdd(String l) {
        add = l;

    }

    public String getAdd() {
        return add;
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

    public String getResname() {
        return resname;
    }

    public void setResname(String l) {
        resname = l;
    }
}
