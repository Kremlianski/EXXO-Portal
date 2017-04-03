package BEAN;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import ru.EXXO.employee.unitDAO;

@ManagedBean
@RequestScoped
public class CompanyBean {

    private String company;

    public String init() throws SQLException, ClassNotFoundException {
        company = new unitDAO().getCompany();
        return null;

    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
