package BEAN;

import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import ru.EXXO.Ratings.EmployeeRating;

@ManagedBean
@RequestScoped
public class EmpRatingsBean {

    private List<EmployeeRating> l;

    public String init() throws SQLException {
        l = new ru.EXXO.Ratings.EmployeeRatingDAO().ratingList();
        return null;
    }

    public List<EmployeeRating> getL() {
        return l;
    }

    public void setL(List<EmployeeRating> l) {
        this.l = l;
    }
}
