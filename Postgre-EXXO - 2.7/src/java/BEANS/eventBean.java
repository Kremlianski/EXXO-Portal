package BEANS;

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
public class eventBean {

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
    String file = "";
    String list = "";
    String filesNo = "нет";
    String info = "Событий не задано";
    boolean important = false;
    boolean outside = false;
    boolean nophone = false;
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
        if (id != null && !id.equals("")) {
            startSQL();
            String sql = "SELECT hours, minutes, day, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS D, descr, important, outside, nophone  FROM events WHERE id='" + id + "' AND user_id='" + user + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                name = rs.getString("name");
                descr = rs.getString("descr").replaceAll("\n", "<p>");
                descr = "<p>" + descr;;
                this.date = rs.getString("day");
                lon = rs.getInt("long");
                important = rs.getBoolean("important");
                nophone = rs.getBoolean("nophone");
                outside = rs.getBoolean("outside");
                file = "<a href='docPoket.jsp?ev=" + id + "&amp;s=docEvent' id='file'>Вложения:</a>";
                kill = "<a href='eventsAdd.xhtml?id=" + id + "' id='kill'>редактировать событие</a>";
                add = "<a href=\"eventsAdd.xhtml?date=" + this.date + "\" id=\"new\">новое событие</a>";
                list = "";
                GregorianCalendar ngc2 = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("D"), rs.getInt("hours"), rs.getInt("minutes"));
                GregorianCalendar ngc1 = new GregorianCalendar(rs.getInt("Year"), rs.getInt("Month") - 1, rs.getInt("D"), rs.getInt("hours"), rs.getInt("minutes") + lon);
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(rs.getDate("day"));
                time = Week[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " " + Monthes[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR) + " года";
                data = sf.format(ngc2.getTime()) + " - " + sf.format(ngc1.getTime());
            }

            String sql1 = "select hours, minutes, name, long, id, extract(year from day) AS Year, extract(month from day) AS Month,extract(day from day) AS Day from events where user_id='" + user + "' and day='" + date + "' ORDER BY hours, minutes";
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

                info += "<div" + pointed + "><span class='timeInfo'>" + sf.format(start.getTime()) + " - " + sf.format(fin.getTime()) + "</span>  <a href='event.xhtml?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a></div>";
            }

            sql = "SELECT name, type, fname, files.id AS id FROM files, docsEvent WHERE files.id=docsEvent.doc AND docsEvent.id='" + id + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.isFirst()) {
                    filesNo = "";
                    list += "<div id='table-about'><table class='exxo-table'>";
                }
                list += "<tr><td class='" + EXXOlib.DOCTYPES.getType(rs.getString("type"), rs.getString("fname")) + "'></td><td><a href='fileLoader.jsp?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a></td><td>" + rs.getString("fname") + "</td></tr>";
                if (rs.isLast()) {
                    list += "</table></div>";
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
}
