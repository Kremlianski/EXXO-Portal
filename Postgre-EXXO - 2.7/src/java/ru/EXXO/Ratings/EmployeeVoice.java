package ru.EXXO.Ratings;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;

public class EmployeeVoice extends Voice {

    public EmployeeVoice(int target_id, int rating_id, int employee_id, ratingTypes type, int result, Status s) {
        super(target_id, rating_id, employee_id, ratingTypes.employee, result, s);
    }

    public void applyChanges(HttpSession ses) throws SQLException {
        if (s == Status.NEW) {
            new EmployeeVoiceDAO().addVoice(this);
            s = Status.CLEAN;
        } else if (s == Status.MODIFIED) {
            new EmployeeVoiceDAO().modifyVoice(this);
            s = Status.CLEAN;
        }
    }

    public void remove(HttpSession ses) throws SQLException {
        if (s != Status.NEW) {
            new EmployeeVoiceDAO().removeVoice(this);
        }
    }

    public Rating getRating() throws SQLException {
        return new RatingDAO().loadRating(this.getRating_id());
    }

    public double findTotalResult() throws SQLException {
        return new EmployeeRatingDAO().findResults((EmployeeRating) this.getRating(), this.getTarget_id());
    }
}
