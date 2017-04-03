package BEAN;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ru.EXXO.LOGIN.role;
import ru.EXXO.User;
import ru.EXXO.employee.Employee;

@ManagedBean
@SessionScoped
public class EmpActionBean implements Serializable {

    @ManagedProperty(value = "#{empUpdBean}")
    private empUpdBean eub;

    public String fired() throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Employee emp = eub.getEmp();
        User u = eub.getUser();
        emp.setFired(true);
        emp.setFiredsince(new Date());
        emp.applyChanges(ses);
        if (u != null) {
            u.remove(ses);
        }
        eub.setUser(null);
        eub.setRegisterd(false);
        return "empUpd.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    public String unfired() throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Employee emp = eub.getEmp();
        emp.setFired(false);
        emp.setFiredsince(null);
        emp.applyChanges(ses);
        return "empUpd.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    public String drop() throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Employee emp = eub.getEmp();
        User u = eub.getUser();
        if (u == null) {
            emp.remove(ses);
        }
        eub.setEmp(null);
        eub.setUser(null);

        return "empUpd.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    public String switchOn() throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        User u = eub.getUser();
        if (u != null) {
            u.addRole("portal_user");
        }
        return "empUpd.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    public String switchOff() throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        User u = eub.getUser();
        if (u != null) {
            u.dropRole("portal_user");
        }
        return "empUpd.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    public String dropUser() throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        User u = eub.getUser();
        if (u != null) {
            u.remove(ses);
        }
        eub.setUser(null);
        eub.setRegisterd(false);
        return "empUpd.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    public void setEub(empUpdBean eub) {
        this.eub = eub;
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
            if (role.getName().equals("staff") || role.getName().equals("boss")) {
                return true;
            }
        }
        return false;
    }
}
