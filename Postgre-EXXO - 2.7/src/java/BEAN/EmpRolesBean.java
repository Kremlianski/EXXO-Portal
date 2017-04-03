package BEAN;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ru.EXXO.LOGIN.role;

@ManagedBean
@ViewScoped
public class EmpRolesBean implements java.io.Serializable {

    private List<ru.EXXO.LOGIN.role> roles;
    private List<ru.EXXO.LOGIN.role> roleList;
    private int id;
    private ru.EXXO.employee.Employee emp;
    private boolean sec = false;

    public String initParams() throws SQLException {
        emp = new ru.EXXO.employee.EmployeeDAO().loadEmployee(id);
        sec = sec();
        if (!sec) {
            return "alert.xhtml?p=nopermit";
        } else if (emp != null) {
            return null;
        } else {
            return "alert.xhtml?p=noEmp";
        }
    }

    public List<ru.EXXO.LOGIN.role> getRoles() throws SQLException {
        roles = new ru.EXXO.LOGIN.RoleDAO().finedUserRoles(id);
        return roles;
    }

    public List<ru.EXXO.LOGIN.role> getRoleList() throws SQLException {
        roleList = new ru.EXXO.LOGIN.RoleDAO().finedAllRoles();
        return roleList;

    }

    public void addRole(ru.EXXO.LOGIN.role role) throws SQLException {
        if (sec()) {
            role.addTo(id);
        }
    }

    public void dropRole(ru.EXXO.LOGIN.role role) throws SQLException {
        if (sec()) {
            role.dropFrom(id);
        }
    }

    public void list() {
        id = 4;
    }

    public int getId() {
        return id;
    }

    public ru.EXXO.employee.Employee getEmp() {
        return emp;
    }

    public void setEmp(ru.EXXO.employee.Employee emp) {
        this.emp = emp;
    }

    public String navigate() {
        return "empRoles.xhtml?faces-redirect=true&amp;includeViewParams=true";
    }

    public void setId(int id) {
        this.id = id;
    }

    private boolean sec() {
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ArrayList<role> r = (ArrayList<role>) ses.getAttribute("roles");
        if (r == null) {
            return false;
        }
        ListIterator li = r.listIterator();
        while (li.hasNext()) {
            role role = (role) li.next();
            if (role.getName().equals("roles_staff") || role.getName().equals("boss")) {
                return true;
            }
        }
        return false;
    }

    public boolean isSec() {
        return sec;
    }

    public void setSec(boolean sec) {
        this.sec = sec;
    }

}
