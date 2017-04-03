package BEAN;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ru.EXXO.LOGIN.role;
import ru.EXXO.Ratings.EmployeeRating;
import ru.EXXO.Status;

@ManagedBean
@ViewScoped
public class EmpRatingUpdBean implements Serializable {

    private ArrayList<EmployeeRating> er;
    private String name;
    private String description;
    private EmployeeRating rating;

    public String init() throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        er = new ru.EXXO.Ratings.EmployeeRatingDAO().ratingList();
        return null;
    }

    public String input() throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        if (name.equals("")) {
            return null;
        }
        EmployeeRating rat = new EmployeeRating(new ru.EXXO.RegistrationSeq().rating_seq(), name, description, Status.NEW);
        rat.applyChanges(session());
        return "empRatingUpd" + ru.EXXO.CONST.includeViewParams;
    }

    public String drop(EmployeeRating r) throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        r.remove(session());
        return "empRatingUpd" + ru.EXXO.CONST.includeViewParams;
    }

    public String update() throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        rating.setStatus(Status.MODIFIED);
        rating.applyChanges(session());
        return "empRatingUpd" + ru.EXXO.CONST.includeViewParams;
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

    public HttpSession session() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public ArrayList<EmployeeRating> getEr() {
        return er;
    }

    public void setEr(ArrayList<EmployeeRating> er) {
        this.er = er;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EmployeeRating getRating() {
        return rating;
    }

    public void setRating(EmployeeRating rating) {
        this.rating = rating;
    }

}
