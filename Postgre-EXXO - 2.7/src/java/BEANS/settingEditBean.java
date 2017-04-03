package BEANS;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.faces.model.SelectItem;

@ManagedBean
@RequestScoped
public class settingEditBean {

    String emp = "";
    String group = "";
    Connection con = null;
    Statement stmt = null;
    HttpSession session = null;

    public String getEmp() {
        return emp;
    }

    public void setEmp(String l) {
        emp = l;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String l) throws IOException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("n") >= 0 || role.indexOf("Z") >= 0) {
            yes = true;
        }
        if (yes) {
            group = l;
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
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            startSQL();

            String sql = "INSERT INTO wgroup (id, group_id) VALUES ('" + emp + "', '" + group + "')";
            stmt.executeUpdate(sql);

            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(settingEditBean.class.getName()).log(Level.SEVERE, null, ex);
            res.sendRedirect("groupEdit.jsp?gr=" + group);
        }
        res.sendRedirect("groupEdit.jsp?gr=" + group);
    }

    public ArrayList getList() throws ClassNotFoundException, SQLException {
        ArrayList options = new ArrayList();
        startSQL();
        String sql = "SELECT global_id, fio FROM employee ORDER BY fio";

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
        return "groupEdit.xhtml?gr=" + group;
    }
}
