package BEANS;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class blogInsertBean {

    String emps = "";
    String name = "";
    String text = "";
    String list = "";
    String dopusk_type = "0";
    String owner = "";
    String edit = null;
    String id = null;
    String status = "0";
    boolean disabled = false;
    int def = 0;
    boolean business = false;
    int Business = 0;
//String firstDopusk="0";
    ResultSet dopusk;
    Connection con = null;
    Statement stmt = null;
    HttpSession session = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String l) {
        status = l;
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

    public String getName() {
        return name;
    }

    public void setName(String l) {
        name = l;
    }

    public String getText() {
        return text;
    }

    public void setText(String l) {
        text = l;
    }

    public String getDopusk_type() {
        return dopusk_type;
    }

    public void setDopusk_type(String l) {
        dopusk_type = l;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String l) {
        owner = l;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String l) {
        edit = "l";
    }

    public String getId() {
        return id;
    }

    public void setId(String l) {
        id = "l";
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean l) {
        disabled = l;
    }

    public void initParam(String e, String i) throws IOException, SQLException, ClassNotFoundException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        owner = (String) session.getAttribute("id");
        //    edit = req.getParameter("edit");
        if (edit == null) {
            edit = e;
        }
        //    if(req.getParameter("status")!=null) status=req.getParameter("status");
        boolean yes = true;
        if (i != null && !i.equals("")) {
            id = i;
        }
        list = "";
        emps = "";
        if (yes) {

            startSQL();
            String sql;
            if (edit != null && !edit.equals("")) {
                sql = "SELECT name, text, dopusk_type, dopusk, bus, ref, status FROM blogesTMP WHERE owner = '" + owner + "'";
                ResultSet rs = stmt.executeQuery(sql);
                boolean trig = false;
                if (rs.next()) {
                    name = rs.getString("name");
                    text = rs.getString("text");
                    Business = rs.getInt("bus");
                    id = rs.getString("ref");
                    status = rs.getString("status");
                    if (id != null) {
                        disabled = true;
                    }
                    trig = true;
                    dopusk_type = rs.getString(3);
                    java.sql.Array r = rs.getArray(4);
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
                                emps += "<div class='emps' id='" + dopusk.getInt(2) + "'>" + rr.getString(1) + "</div>";
                            }
                            rr.close();
                        }

                    }
                }
                rs.close();
            } else {

                sql = "DELETE FROM blogesTMP WHERE owner='" + owner + "'";
                stmt.executeUpdate(sql);
                sql = "DELETE FROM docPoket WHERE id='" + owner + "'";
                stmt.executeUpdate(sql);

                if (id != null) {
                    int reOwner = 0;
                    String fioOwner = "---";
                    disabled = true;
                    sql = "SELECT dopusk_type, dopusk, name, bus, get_global(owner) AS ownId, get_fio(owner) AS fio FROM bloges WHERE id='" + id + "'";
                    ResultSet rs = stmt.executeQuery(sql);
                    boolean trig = false;
                    if (rs.next()) {
                        trig = true;
                        dopusk_type = rs.getString(1);
                        java.sql.Array r = rs.getArray(2);
                        Business = rs.getInt("bus");
                        name = "Re: " + rs.getString("name");
                        reOwner = rs.getInt("ownId");
                        fioOwner = rs.getString("fio");
                        if (r != null) {
                            dopusk = r.getResultSet();
                        } else {
                            trig = false;
                        }
                        if (trig) {
                            while (dopusk.next()) {
                                if (dopusk.isFirst()) {
                                    def = dopusk.getInt(2);
                                }
                                if (dopusk_type.equals("4")) {
                                    boolean sss = true;
                                    sql = "SELECT fio FROM employee WHERE global_id='" + dopusk.getInt(2) + "'";
                                    ResultSet rr = stmt.executeQuery(sql);
                                    while (rr.next()) {
                                        emps += "<div class='emps' id='" + dopusk.getInt(2) + "'>" + rr.getString(1) + "</div>";
                                        if (dopusk.getInt(2) == reOwner) {
                                            sss = false;
                                        }
                                    }
                                    rr.close();
                                    //  if(sss) emps+="<div class='emps' id='"+reOwner+"'>"+fioOwner+"</div>";
                                }
                            }
                        }

                        PreparedStatement pstmt = con.prepareStatement("INSERT INTO blogesTMP (ref, dopusk_type, dopusk, owner, name, bus, status) VALUES (?::Int,?::Int,?,?::Int, ?, ?, ?::int)");
                        pstmt.setString(1, id);
                        pstmt.setString(2, dopusk_type);
                        pstmt.setArray(3, r);
                        pstmt.setString(4, owner);
                        pstmt.setString(5, name);
                        pstmt.setInt(6, Business);
                        pstmt.setString(7, status);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                    rs.close();
                    sql = "SELECT blog_re(" + owner + ", " + reOwner + ")";
                    if (dopusk_type.equals("4")) {
                        stmt.execute(sql);
                    }
                }

                edit = "1";
            }

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

    public void insertData() throws ClassNotFoundException, IOException {
        //HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            startSQL();

            if (text != null) {
                text = BASE.EXXOREGEX.replaceHref(text);
                text = BASE.EXXOREGEX.replaceSrc(text);
            }
            CallableStatement cstmt = con.prepareCall("{call InsertBloges(?,?,?,?)}");
            if (name.equals("")) {
                name = "- Тема не задана -";
            }
            cstmt.setString(1, name);
            cstmt.setString(2, text);
            cstmt.setInt(3, Business);
            cstmt.setString(4, owner);
            cstmt.executeUpdate();

            cstmt.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(blogInsertBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        edit = "1";
        //res.sendRedirect("blogEditor.jsp"); 
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
