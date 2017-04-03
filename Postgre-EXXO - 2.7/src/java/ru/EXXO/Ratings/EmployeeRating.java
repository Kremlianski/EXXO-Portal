package ru.EXXO.Ratings;

import java.sql.SQLException;
import java.util.ArrayList;
import ru.EXXO.Status;

public class EmployeeRating extends Rating {

    public EmployeeRating(int id, String name, String description, Status status) {
        super(id, name, description, status, ratingTypes.employee);
    }

    public double findResult(int target_id) throws SQLException {
        return new EmployeeRatingDAO().findResults(this, target_id);
    }

    public ArrayList<FIOratingResult> findList() throws SQLException {
        return new EmployeeRatingDAO().findList(this);
    }

}
