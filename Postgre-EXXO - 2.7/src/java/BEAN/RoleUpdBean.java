package BEAN;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ru.EXXO.LOGIN.RoleDAO;
import ru.EXXO.LOGIN.role;
import ru.EXXO.employee.Employee;

@ManagedBean
@ViewScoped
public class RoleUpdBean implements Serializable {

    private String r;
    private role role;
    private List<Employee> emps;
    private List<Employee> list;
    private int add_id;
    private int drop_id;
    private boolean sec = false;

    public String init() throws SQLException {
        role = new RoleDAO().loadRole(r);
        sec = sec();
        if (!sec) {
            return "alert.xhtml?p=nopermit";
        }
        if (role == null) {
            return ru.EXXO.CONST.alert_no_role;
        }
        emps = role.findEmployee();
        list = new RoleDAO().findEmployees("portal_user");
        return null;
    }

    public String choose() throws SQLException {
        sec = sec();
        if (!sec) {
            return "alert.xhtml?p=nopermit";
        }
        role.addTo(add_id);
        return "roleUpd" + ru.EXXO.CONST.includeViewParams;
    }

    public String drop() throws SQLException {
        sec = sec();
        if (!sec) {
            return "alert.xhtml?p=nopermit";
        }
        role.dropFrom(drop_id);
        return "roleUpd" + ru.EXXO.CONST.includeViewParams;
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

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public role getRole() {
        return role;
    }

    public void setRole(role role) {
        this.role = role;
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public List<Employee> getList() {
        return list;
    }

    public void setList(List<Employee> list) {
        this.list = list;
    }

    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }

    public int getDrop_id() {
        return drop_id;
    }

    public void setDrop_id(int drop_id) {
        this.drop_id = drop_id;
    }

}
