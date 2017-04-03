package BEAN;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import ru.EXXO.LOGIN.RoleDAO;
import ru.EXXO.LOGIN.role;
import ru.EXXO.employee.Employee;

@ManagedBean
@SessionScoped
public class RolesBean implements Serializable{
    @ManagedProperty(value="#{employeeBlockBean}")
    private EmployeeBlockBean eb;
   private ArrayList <role> roles;
   private ArrayList <Employee> employees;
   private String str = "";
   private String r;
   
   public String initNav() throws SQLException {
       
      return null;
   }
   
   public String settingCommand() {
       return "roleUpd"+ru.EXXO.CONST.includeViewParams;
   }

    public ArrayList getRoles() throws SQLException {
         roles = new RoleDAO().finedAllRoles();
        return roles;
    }
    
    public void findUsers(role role) throws SQLException {
    str = role.getName();
    employees = role.findEmployee();
    }

    public String getStr() {
        return str;
    }

    public ArrayList <Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList <Employee> employees) {
        this.employees = employees;
    }

    public void setRoles(ArrayList <role> roles) {
        this.roles = roles;
    }

    public EmployeeBlockBean getEb() {
        return eb;
    }

    public void setEb(EmployeeBlockBean eb) {
        this.eb = eb;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

          
}
