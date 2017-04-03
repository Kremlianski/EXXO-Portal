package BEAN;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ru.EXXO.employee.Employee;
import ru.EXXO.employee.EmployeeDAO;

@ManagedBean
@ViewScoped
public class emploeeBean implements Serializable {
private int id;
private Employee employee;
private List <ru.EXXO.LOGIN.role> roles;
   public String init() throws SQLException {
       employee=new EmployeeDAO().loadEmployee(id);
       if(employee==null) return ru.EXXO.CONST.alert_no_user;
       return null;
   }
    public List <ru.EXXO.LOGIN.role> getRoles() throws SQLException {
        roles = new ru.EXXO.LOGIN.RoleDAO().finedUserRoles(employee.getId());
        return roles;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
