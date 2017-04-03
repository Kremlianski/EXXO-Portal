package BEAN;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ru.EXXO.employee.Employee;
import ru.EXXO.employee.EmployeeDAO;
import ru.EXXO.employee.Office;
import ru.EXXO.employee.OfficeDAO;

@ManagedBean
@ViewScoped
public class EmpListTerBean implements Serializable {

    private ArrayList<Employee> emp;
    private String office;
    private String of;
    private String q;
    private Office o;

    public String init() throws SQLException, UnsupportedEncodingException, ClassNotFoundException {

        if (office == null || office.equals("")) {
            return "territories?faces-redirect=true";
        }
        of = URLDecoder.decode(office, "utf8");

        o = new OfficeDAO().loadOffice(of);
        if (o == null) {
            return "territories?faces-redirect=true";
        }
        emp = o.getEmps();
        return null;
    }

    public ArrayList<Employee> getEmp() {
        return emp;
    }

    public void setEmp(ArrayList<Employee> emp) {
        this.emp = emp;
    }

    public String getOf() {
        return of;
    }

    public void setOf(String of) {
        this.of = of;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Office getO() {
        return o;
    }

    public void setO(Office o) {
        this.o = o;
    }

}
