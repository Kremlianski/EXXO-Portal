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
public class eventsRAddBean {

    String name = "";
    String descr = "";
    String date = "";
    String id = "";
    String time = "";
    String lon = "";
    String user = "";
    String kill = "";
    String add = "";
    String fio = "";
    String r = "";
    String role;
    String resname = "Ресурс не определен!";
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

    public void initParams() throws ClassNotFoundException, SQLException, IOException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        user = (String) session.getAttribute("id");
        role = (String) session.getAttribute("role");
        if (id != null && !id.equals("")) {
            startSQL();
            String sql = "SELECT id_res, name, descr, eventsres.day, hours, minutes, long, short FROM eventsres, resources WHERE eventsres.id='" + id + "' AND resources.id=id_res AND user_id='" + user + "'";
            if (role.indexOf("a") >= 0 || role.indexOf("f") >= 0) {
                sql = "SELECT id_res, name, descr, eventsres.day, hours, minutes, long, short FROM eventsres, resources WHERE eventsres.id='" + id + "' AND resources.id=id_res ";
            }
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                this.r = rs.getString("id_res");
                resname = "<a href='allDayR.xhtml?r=" + this.r + "&amp;date=" + rs.getString("day") + "'>" + rs.getString("short") + "</a>";
                name = rs.getString("name");
                descr = rs.getString("descr");
                this.date = rs.getString("day");
                hours = rs.getInt("hours");
                minutes = rs.getInt("minutes");
                lon = rs.getString("long");
                time = ((hours - 8) * 4 + (int) (minutes / 15)) + "";
                kill = "<a href='eventsRKill?id=" + id + "&amp;date=" + this.date + "&r=" + this.r + "' id='kill'>удалить событие</a>";
                add = "<a href=\"eventsRAdd.xhtml?date=" + this.date + "&r=" + this.r + "\" id=\"new\">новое событие</a>";
            }
            rs.close();
            stmt.close();
            con.close();
        } else {
            if (this.r != null && !this.r.equals("")) {
                time = req.getParameter("start");
                lon = "15";
                startSQL();
                String sql = "SELECT short FROM resources WHERE id=" + this.r;
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    resname = "<a href='allDayR.xhtml?r=" + this.r + "&amp;date=" + this.date + "'>" + rs.getString("short") + "</a>";
                }
                rs.close();
                stmt.close();
                con.close();
            } else {
                res.sendRedirect("resources.jsp");
            }
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

    public String getFio() {
        return fio;
    }

    public void setFio(String l) {
        fio = l;
    }

    public String getR() {
        return r;
    }

    public void setR(String l) {
        r = l;
    }

    public String getResname() {
        return resname;
    }

    public void setResname(String l) {
        resname = l;
    }

    public void insertData() throws ClassNotFoundException, SQLException, IOException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        session = req.getSession(true);
        role = (String) session.getAttribute("role");
        int t = java.lang.Integer.parseInt(time);
        hours = (int) java.lang.Math.floor(t / 4) + 8;
        minutes = (t - (hours - 8) * 4) * 15;
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("f") >= 0) {
            yes = true;
        }
        if (name == null || name.equals("")) {
            name = "Событие не задано";
        }

        startSQL();
        String sql3 = "select hours, minutes, long from eventsres where id_res='" + r + "' AND day='" + date + "' AND id<>'" + id + "' ORDER BY hours, minutes";
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

                String sql = "UPDATE eventsres SET day='" + date + "', hours='" + hours + "',minutes='" + minutes + "', name='" + name + "',long='" + lon + "', descr='" + descr + "' WHERE id='" + id + "' AND user_id='" + user + "'";
                if (yes) {
                    sql = "UPDATE eventsres SET day='" + date + "', hours='" + hours + "',minutes='" + minutes + "', name='" + name + "',long='" + lon + "', descr='" + descr + "' WHERE id='" + id + "'";
                }
                stmt.executeUpdate(sql);
            } else {

                String sql = "INSERT INTO eventsres (id, user_id, day, hours, minutes, name, long, descr,id_res, fio) VALUES(concat('events_',NEXTVAL('events_seq')),'" + user + "','" + date + "','" + hours + "','" + minutes + "','" + name + "','" + lon + "','" + descr + "','" + r + "',get_fio('" + user + "'))";
                stmt.executeUpdate(sql);
            }
        }
        rs.close();
        stmt.close();
        con.close();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        res.sendRedirect("allDayR.xhtml?date=" + date + "&r=" + r);
    }

}
