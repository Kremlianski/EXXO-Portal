package BEAN;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ru.EXXO.Ratings.EmployeeRating;
import ru.EXXO.Ratings.FIOratingResult;
import ru.EXXO.Ratings.RatingDAO;
import ru.EXXO.employee.Employee;

@ManagedBean
@ViewScoped
public class RatingResultsBean implements java.io.Serializable {

    private int id;
    private EmployeeRating er;
    private Employee e;
    private ArrayList<ru.EXXO.Ratings.FIOratingResult> res;

    public String init() throws SQLException {
        er = (EmployeeRating) new RatingDAO().loadRating(id);
        res = er.findList();
        return null;
    }

    public void loadEmp(FIOratingResult frr) throws SQLException {
        e = new ru.EXXO.employee.EmployeeDAO().loadEmployee(frr.getTarget_id());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeeRating getEr() {
        return er;
    }

    public void setEr(EmployeeRating er) {
        this.er = er;
    }

    public ArrayList getRes() {
        return res;
    }

    public void setRes(ArrayList res) {
        this.res = res;
    }

    public Employee getE() {
        return e;
    }

    public void setE(Employee e) {
        this.e = e;
    }
}
