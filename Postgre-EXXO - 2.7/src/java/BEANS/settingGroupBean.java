package BEANS;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.bean.RequestScoped;
import javax.faces.context.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.faces.model.SelectItem;

@ManagedBean
@RequestScoped
public class settingGroupBean {

    String project = "";
    String group = "";
    Connection con = null;
    Statement stmt = null;
    String id = null;
    HttpSession session = null;
    HttpServletRequest req = null;

    public void initParams(String id, String pr) throws ClassNotFoundException, SQLException, IOException {
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("n") >= 0) {
            yes = true;
        }
        if (yes) {
            if (id != null && !id.equals("")) {
                startSQL();
                String sql = "SELECT project, name FROM groups WHERE id='" + id + "'";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    project = rs.getString("project");
                    group = rs.getString("name");
                    this.id = id;
                }

                rs.close();
                stmt.close();
                con.close();
            } else {
                project = pr;
            }
        } else {
            res.sendRedirect("notPermited.html");
        }
    }

    public String getProject() {
        return project;
    }

    public void setProject(String l) {
        project = l;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String l) {
        group = l;
    }

    public String getId() {
        return id;
    }

    public void setId(String l) {
        id = l;
    }

    void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = new Properties();
        properties = BASE.VER.getDBProp();

        con = DriverManager.getConnection(url, properties);

        stmt = con.createStatement();
    }

    public void insertData() throws ClassNotFoundException, SQLException, IOException {
        if (id != null && !id.equals("")) {
            startSQL();
            String sql = "UPDATE groups SET name='" + group + "', project='" + project + "' WHERE id='" + id + "'";
            stmt.executeUpdate(sql);
        } else {
            startSQL();
            String sql = "INSERT INTO groups (project, name) VALUES ('" + project + "', '" + group + "')";
            stmt.executeUpdate(sql);
        }

        stmt.close();
        con.close();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        res.sendRedirect("groupList.jsp?pr=" + project);
    }

    public ArrayList getList() throws ClassNotFoundException, SQLException {
        ArrayList options = new ArrayList();
        startSQL();
        String sql = "SELECT id, name FROM projects";

        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            SelectItem si = new SelectItem(rs.getString(1), rs.getString(2), "");
            options.add(si);
        }
        rs.close();
        stmt.close();
        con.close();

        return options;
    }

    public String getNavi() {
        return "groupList.xhtml?pr=" + project;
    }
}
