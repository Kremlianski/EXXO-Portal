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
public class blogPropBean {

    String ref = null;
    String emps = "";
    String name = "";
    String id = "";
    String global_id = "";
    String list = "";
    String dopusk_type = "0";
    String owner = "";
    String refresh = "";
    boolean business = false;
    int Business = 0;
    int bloges_owner = 0;
    int def = 0;
//String firstDopusk="0";
    ResultSet dopusk;
    Connection con = null;
    Statement stmt = null;
    HttpSession session = null;
    boolean disabled = false;

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean l) {
        disabled = l;
    }

    public String getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String l) {
        name = l;
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

    public boolean getBusiness() {
        if (Business == 1) {
            business = true;
        } else {
            business = false;
        }
        return business;
    }

    public void setBusiness(boolean l) {
        business = l;
        if (business) {
            Business = 1;
        } else {
            Business = 0;
        }
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String l) {
        refresh = l;
    }

    public void initParam(String l) throws IOException, SQLException, ClassNotFoundException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        owner = (String) session.getAttribute("id");
        boolean yes = false;
        list = "";

        //  if(role.indexOf("a")>=0||role.indexOf("e")>=0||role.indexOf("Z")>=0) yes=true;
        if (l != null && !l.equals("")) {
            id = l;
        }

        if (!yes) {
            startSQL();
            String sql = "SELECT owner=" + owner + " FROM bloges WHERE id='" + id + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                yes = rs.getBoolean(1);
            }
            rs.close();
            stmt.close();
            con.close();
        }
        if (id == null || id.equals("")) {
            yes = false;
        }
        if (yes) {

            startSQL();
            String sql = "SELECT global_id, name, owner, bus, ref FROM bloges WHERE id = '" + id + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                name = rs.getString("name");
                global_id = rs.getString("global_id");
                bloges_owner = rs.getInt("owner");
                Business = rs.getInt("bus");
                ref = rs.getString("ref");
                if (ref != null) {
                    disabled = true;
                    refresh = "<div class='refresh'><a href='refreshBlog?id=" + id + "'>ОБНОВИТЬ</a></div>";
                }

            }
            sql = "SELECT tags.name FROM tags, global_tags WHERE tags.tag_id = global_tags.tag_id AND global_id = '" + global_id + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                list += "<div class='tag exxo-shadow1'>" + rs.getString(1) + "</div>";
            }
            sql = "SELECT dopusk_type, dopusk FROM bloges WHERE id = '" + id + "'";
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

        startSQL();
        String sql = "UPDATE bloges SET name='" + name + "', bus='" + Business + "' WHERE id='" + id + "'";
        stmt.executeUpdate(sql);

        stmt.close();
        con.close();
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
}
