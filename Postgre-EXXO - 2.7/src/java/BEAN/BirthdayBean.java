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
public class BirthdayBean implements Serializable {

    private ArrayList<Employee> emp;
    private String q = "";

    public String init() throws SQLException {
        emp = new EmployeeDAO().findBirthdays();
        return null;
    }

    public ArrayList<Employee> getEmp() {
        return emp;
    }

    public void setEmp(ArrayList<Employee> emp) {
        this.emp = emp;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

}
