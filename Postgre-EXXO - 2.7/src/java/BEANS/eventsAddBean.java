package BEANS;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class eventsAddBean {

    String name = "";
    String descr = "";
    String type = "";
    String date = "";
    String id = "";
    String time = "";
    String lon = "";
    String user = "";
    String kill = "";
    String add = "";
    boolean important = false;
    boolean outside = false;
    boolean nophone = false;
    int hours = 0;
    int minutes = 0;
    String ar = "";
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

    public void initParams(String id, String date) throws ClassNotFoundException, SQLException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        this.id = id;
        this.date = date;
        session = req.getSession(true);
        user = (String) session.getAttribute("id");
        if (id != null && !id.equals("")) {
            startSQL();
            String sql = "SELECT * FROM events WHERE id='" + id + "' AND user_id='" + user + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                name = rs.getString("name");
                descr = rs.getString("descr");
                this.date = rs.getString("day");
                important = rs.getBoolean("important");
                nophone = rs.getBoolean("nophone");
                outside = rs.getBoolean("outside");
                hours = rs.getInt("hours");
                minutes = rs.getInt("minutes");
                lon = rs.getString("long");
                time = ((hours - 8) * 4 + (int) (minutes / 15)) + "";
                kill = "<a href='eventsKill?id=" + id + "&amp;date=" + this.date + "' id='kill'>удалить событие</a>";
                add = "<a href=\"eventsAdd.xhtml?date=" + this.date + "\" id=\"new\">новое событие</a>";
            }
            rs.close();
            stmt.close();
            con.close();
        } else {
            time = req.getParameter("start");
            lon = "15";
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

    public String getLon() {
        return lon;
    }

    public void setLon(String l) {
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

    public void insertData() throws ClassNotFoundException, SQLException, IOException {
        int t = java.lang.Integer.parseInt(time);
        hours = (int) java.lang.Math.floor(t / 4) + 8;
        minutes = (t - (hours - 8) * 4) * 15;
        if (name == null || name.equals("")) {
            name = "Событие не задано";
        }
        startSQL();
        String sql3 = "select hours, minutes, long from events where user_id='" + user + "' AND day='" + date + "' AND id<>'" + id + "' ORDER BY hours, minutes";
        ResultSet rs = stmt.executeQuery(sql3);
        boolean trig = true;
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (rs.next()) {
            int hm = rs.getInt(1) * 4 + rs.getInt(2) / 15 - 32;
            int length = rs.getInt(3) / 15;
            for (int l = 0; l < length; l++) {
                list.add(hm + l);
            }
        }
        int length = java.lang.Integer.parseInt(lon) / 15;
        for (int l = 0; l < length; l++) {
            if (list.contains(t + l)) {
                trig = false;
                break;
            }
        }

        if (trig) {

            if (id != null && !id.equals("")) {

                String sql = "UPDATE events SET day='" + date + "', hours='" + hours + "',minutes='" + minutes + "', name='" + name + "',long='" + lon + "', descr='" + descr + "', important=" + important + " ,"
                        + "nophone=" + nophone + ", outside=" + outside + " WHERE id='" + id + "' AND user_id='" + user + "'";
                stmt.executeUpdate(sql);
            } else {

                String sql = "INSERT INTO events (id, user_id, day, hours, minutes, name, long, descr, important, outside, nophone) VALUES(concat('events_',NEXTVAL('events_seq')),"
                        + "'" + user + "','" + date + "','" + hours + "','" + minutes + "','" + name + "','" + lon + "','" + descr + "'," + important + "," + outside + "," + nophone + ")";
                stmt.executeUpdate(sql);
            }
        }
        rs.close();
        stmt.close();
        con.close();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        res.sendRedirect("allDay.xhtml?date=" + date);
    }

}
