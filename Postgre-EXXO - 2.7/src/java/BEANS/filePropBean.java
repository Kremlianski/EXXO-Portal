package BEANS;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class filePropBean {

    String emps = "";
    String name = "";
    String id = "";
    String descr = "";
    String tags = "";
    String global_id = "";
    String list = "";
    String dopusk_type = "0";
    String owner = "";
    String own = "";
    int file_owner = 0;
    int def = 0;
    boolean vers = false;
//String firstDopusk="0";
    ResultSet dopusk;
    Connection con = null;
    Statement stmt = null;
    HttpSession session = null;

    public String getList() {
        return list;
    }

    public String getOwn() {
        return own;
    }

    public void setOwn(String o) {
        own = o;
    }

    public String getName() {
        return name;
    }

    public void setName(String l) {
        name = l;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String l) {
        descr = l;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String l) {
        tags = l;
    }

    public String getGlobal_id() {
        return global_id;
    }

    public void setGlobal_id(String l) {
        global_id = l;
    }

    public String getDopusk_type() {
        return dopusk_type;
    }

    public void setDopusk_type(String l) {
        dopusk_type = l;
    }

    public String getId() {
        return id;
    }

    public void setId(String l) {
        id = l;
    }

    public boolean getVers() {
        return vers;
    }

    public void setVers(boolean l) {
        vers = l;
    }

    public void initParam(String l) throws IOException, SQLException, ClassNotFoundException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        owner = (String) session.getAttribute("id");
        boolean yes = false;
        list = "";

        if (l != null && !l.equals("")) {
            id = l;
        }
        if (id == null || id.equals("")) {
            yes = false;
        }

        startSQL();
        ResultSet rs = null;
        String sql = "SELECT owner=" + owner + ", owner FROM files WHERE id='" + id + "'";
        if (id != null && !id.equals("")) {
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                yes = rs.getBoolean(1);
                own = rs.getString(2);
            }
        }
        if ((role.indexOf("o") >= 0 && own.equals("-100")) || (role.indexOf("p") >= 0 && own.equals("-101"))
                || (role.indexOf("q") >= 0 && own.equals("-102")) || (role.indexOf("r") >= 0 && own.equals("-103"))) {
            yes = true;
        }

        if (yes) {

            sql = "SELECT global_id, name, owner, descr, tags, vers FROM files WHERE id = '" + id + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                name = rs.getString("name");
                descr = rs.getString("descr");
                tags = rs.getString("tags");
                global_id = rs.getString("global_id");
                file_owner = rs.getInt("owner");
                vers = rs.getBoolean("vers");
            }
            sql = "SELECT tags.name FROM tags, global_tags WHERE tags.tag_id = global_tags.tag_id AND global_id = '" + global_id + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                list += "<div class='tag exxo-shadow1'>" + rs.getString(1) + "</div>";
            }
            sql = "SELECT dopusk_type, dopusk FROM files WHERE id = '" + id + "'";
            rs = stmt.executeQuery(sql);
            boolean trig = true;
            if (rs.next()) {
                dopusk_type = rs.getString(1);
                java.sql.Array r = rs.getArray(2);
                if (r != null) {
                    dopusk = r.getResultSet();
                } else {
                    trig = false;
                }
            }
            if (trig) {
                while (dopusk.next()) {
                    if (dopusk.isFirst()) {
                        def = dopusk.getInt(2);
                    }
                    if (dopusk_type.equals("4")) {
                        sql = "SELECT fio FROM employee WHERE global_id='" + dopusk.getInt(2) + "'";
                        ResultSet rr = stmt.executeQuery(sql);
                        while (rr.next()) {
                            emps += "<div class='emps' id='" + dopusk.getInt(2) + "'>" + EXXOlib.textLib.shortFIO(rr.getString(1)) + "</div>";
                        }
                        rr.close();
                    }

                }
            }
            rs.close();
            stmt.close();
            con.close();
        } else {
            id = "-4";
            res.sendRedirect("notPermited.html");
        }
    }

    void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = new Properties();
        properties = BASE.VER.getDBProp();

        con = DriverManager.getConnection(url, properties);

        stmt = con.createStatement();
    }

    public void insertData() throws ClassNotFoundException, SQLException {
        if (!id.equals("-4")) {
            startSQL();
            String sql = "UPDATE files SET name='" + name + "', descr='" + descr + "', tags='" + tags + "', vers=" + vers + " WHERE id='" + id + "'";
            stmt.executeUpdate(sql);

            stmt.close();
            con.close();
        }
    }

    public ArrayList getEmp() throws ClassNotFoundException, SQLException {
        ArrayList options = new ArrayList();
        startSQL();
        String sql = "SELECT global_id, fio FROM employee ORDER BY fio";

        ResultSet rs = stmt.executeQuery(sql);
        options.add(new SelectItem("0", "---нужно выбрать---", ""));

        while (rs.next()) {
            SelectItem si = new SelectItem(rs.getString(1), rs.getString(2), "");
            options.add(si);
        }
        rs.close();
        stmt.close();
        con.close();

        return options;
    }

    public ArrayList getGroups() throws ClassNotFoundException, SQLException {
        ArrayList options = new ArrayList();
        startSQL();
        String sql = "SELECT wgroup.group_id, name FROM wgroup, groups, employee WHERE wgroup.group_id = groups.id AND "
                + "employee.id='" + owner + "' AND employee.global_id= wgroup.id ORDER BY name";

        ResultSet rs = stmt.executeQuery(sql);
        options.add(new SelectItem("0", "---нужно выбрать---", ""));

        while (rs.next()) {
            SelectItem si = new SelectItem(rs.getString(1), rs.getString(2), "");
            options.add(si);
        }
        rs.close();
        stmt.close();
        con.close();

        return options;
    }

    public ArrayList getProjects() throws ClassNotFoundException, SQLException {
        ArrayList options = new ArrayList();
        startSQL();
        String sql = "SELECT DISTINCT projects.id, projects.name FROM wgroup, groups, employee, projects WHERE wgroup.group_id = groups.id AND "
                + "employee.id='" + owner + "' AND employee.global_id= wgroup.id AND projects.id=groups.project ORDER BY projects.name";

        ResultSet rs = stmt.executeQuery(sql);
        options.add(new SelectItem("0", "---нужно выбрать---", ""));

        while (rs.next()) {
            SelectItem si = new SelectItem(rs.getString(1), rs.getString(2), "");
            options.add(si);
        }
        rs.close();
        stmt.close();
        con.close();

        return options;
    }

    public ArrayList getUnits() throws ClassNotFoundException, SQLException {
        ArrayList options = new ArrayList();
        startSQL();
        String sql = "SELECT units.unit_id, units.unit, units.superior, unit_name(units.superior) FROM employee, units WHERE employee.unit = units.unit_id AND "
                + "employee.id='" + owner + "' LIMIT 1";

        ResultSet rs = stmt.executeQuery(sql);
        options.add(new SelectItem("0", "---нужно выбрать---", ""));

        if (rs.next()) {
            if (!rs.getString(1).equals("1")) {
                SelectItem si = new SelectItem(rs.getString(3), rs.getString(4), "");
                options.add(si);
            }
            SelectItem si = new SelectItem(rs.getString(1), rs.getString(2), "");
            options.add(si);
        }
        rs.close();
        stmt.close();
        con.close();

        return options;
    }

    public int getDef(int t) {
        int res = 0;
        if (t == 4) {
            res = 0;
        } else if (t == 1 && dopusk_type.equals("1")) {
            res = def;
        } else if (t == 2 && dopusk_type.equals("2")) {
            res = def;
        } else if (t == 3 && dopusk_type.equals("3")) {
            res = def;
        }
        return res;
    }

    public String getEmps() {
        return emps;
    }

    public boolean isDisabled() {
        boolean res = false;
        if (file_owner == -100) {
            res = true;
        }
        return res;
    }
}
