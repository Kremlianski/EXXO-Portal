package BEAN;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ru.EXXO.Ratings.EmployeeRating;
import ru.EXXO.Ratings.EmployeeRatingDAO;
import ru.EXXO.Ratings.EmployeeVoice;
import ru.EXXO.Ratings.EmployeeVoiceDAO;

@ManagedBean
@ViewScoped
public class RatingBlockBean implements java.io.Serializable {

    private int id;
    private ArrayList<EmployeeVoice> voices;
    private ArrayList<EmployeeRating> ratings;

    public void init(int id) throws SQLException {
        ratings = new EmployeeRatingDAO().ratingList();
        voices = new ArrayList();
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ListIterator i = ratings.listIterator();
        while (i.hasNext()) {
            EmployeeRating r = (EmployeeRating) i.next();
            voices.add(new EmployeeVoiceDAO().loadVoice(id, r.getId(), java.lang.Integer.parseInt((String) ses.getAttribute("id"))));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<EmployeeVoice> getVoices() {
        return voices;
    }

    public void setVoices(ArrayList<EmployeeVoice> voices) {
        this.voices = voices;
    }

    public ArrayList<EmployeeRating> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<EmployeeRating> ratings) {
        this.ratings = ratings;
    }

}
