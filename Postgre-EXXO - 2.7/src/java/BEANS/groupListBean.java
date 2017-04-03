package BEANS;

import java.io.IOException;
//import javax.faces.bean.SessionScoped;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.servlet.jsp.jstl.sql.Result;
import EXXOlib.JdbcUtile;
//import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.*;
//import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class groupListBean {

    private Result groupResultData;
    private String project = "";
    HttpSession session = null;
    HttpServletRequest req = null;

    public Result getGroupResultData() throws SQLException, ClassNotFoundException, IOException {
        String pr = project;
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("n") >= 0) {
            yes = true;
        }
        if (yes) {
            populateEmpResultData(pr);
        } else {
            res.sendRedirect("notPermited.html");
        }
        return groupResultData;
    }

    private void populateEmpResultData(String pr) throws SQLException, ClassNotFoundException {
        groupResultData
                = JdbcUtile.runQuery("SELECT * FROM groups WHERE project='" + pr + "' ORDER BY name", -1, req.getServletContext());
    }

    public String getProject() {
        return project;
    }

    public void setProject(String pr) {
        if (pr != null && !pr.equals("")) {
            project = pr;
        }
    }

}
