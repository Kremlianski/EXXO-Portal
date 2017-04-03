package BEAN;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ru.EXXO.employee.Employee;
import ru.EXXO.employee.EmployeeDAO;


@ManagedBean
@ViewScoped
public class EmpPhotoBean implements Serializable {

    private ArrayList <Employee> employees;
    private Employee employee;
    public String init() throws SQLException {
        employees=new EmployeeDAO().allEmployees();
        return null;
    }

    public ArrayList <Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList <Employee> employees) {
        this.employees = employees;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

   
}
