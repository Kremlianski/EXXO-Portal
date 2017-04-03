package BEANS;

import java.io.IOException;
//import javax.faces.bean.SessionScoped;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.servlet.jsp.jstl.sql.Result;
import EXXOlib.JdbcUtile;
//import javax.faces.bean.RequestScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.faces.bean.SessionScoped;
import javax.faces.context.*;
//import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class groupEditBean {

    private Result groupResultData;
    private String group = "";
    HttpSession session = null;
    HttpServletRequest req = null;
    Connection con = null;
    Statement stmt = null;

    public Result getGroupResultData() throws SQLException, ClassNotFoundException, IOException {
        String gr = group;
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("n") >= 0) {
            yes = true;
        }
        if (yes) {
            populateEmpResultData(gr);
        } else {
            res.sendRedirect("notPermited.html");
        }
        return groupResultData;
    }

    private void populateEmpResultData(String gr) throws SQLException, ClassNotFoundException {
        groupResultData
                = JdbcUtile.runQuery("SELECT wgroup.id, wgroup.group_id, employee.fio, wgroup.class AS boss FROM wgroup, employee WHERE wgroup.group_id='" + gr + "' AND "
                        + "employee.global_id=wgroup.id ORDER BY fio", -1, req.getServletContext());
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String gr) {
        if (gr != null && !gr.equals("")) {
            group = gr;
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

    public int getProject() throws ClassNotFoundException, SQLException {
        startSQL();
        String sql = "SELECT project FROM groups WHERE id='" + group + "'";
        int project = 0;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            project = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        con.close();
        return project;
    }

}
