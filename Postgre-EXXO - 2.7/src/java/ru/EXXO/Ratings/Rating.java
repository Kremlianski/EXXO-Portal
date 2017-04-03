package ru.EXXO.Ratings;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;
import ru.EXXO.employee.Employee;

public class Rating implements java.io.Serializable, ru.EXXO.changeable {

    private int id;
    private String name;
    private String description;
    private Status s;
    private ratingTypes type;

    public Rating(int id, String name, String description, Status s, ratingTypes type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.s = s;
        this.type = type;
    }

    public Status getStatus() {
        return s;
    }

    public void setStatus(Status s) {
        this.s = s;
    }

    public void applyChanges(HttpSession ses) throws SQLException {
        if (s == Status.NEW) {
            new RatingDAO().addRating(this);
            s = Status.CLEAN;
        } else if (s == Status.MODIFIED) {
            new RatingDAO().modifyRating(this);
            s = Status.CLEAN;
        }
    }

    public void remove(HttpSession ses) throws SQLException {
        if (s != Status.NEW) {
            new RatingDAO().removeRating(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ratingTypes getType() {
        return type;
    }

    public void setType(ratingTypes type) {
        this.type = type;
    }

}
