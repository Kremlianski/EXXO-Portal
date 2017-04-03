package ru.EXXO.employee;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;
import ru.EXXO.changeable;

public class unit implements java.io.Serializable, changeable {

    private int id;
    private String unit;
    private int superior;
    private Status s;

    public unit(int id, String unit, int superior, Status s) {
        this.s = s;
        this.id = id;
        this.unit = unit;
        this.superior = superior;
    }

    public unit() {
        s = Status.CLEAN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        s = Status.MODIFIED;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        s = Status.MODIFIED;
    }

    public int getSuperior() {
        return superior;
    }

    public void setSuperior(int superior) {
        this.superior = superior;
        s = Status.MODIFIED;
    }

    public Status getStatus() {
        return s;
    }

    public void setStatus(Status s) {
        this.s = s;
    }

    public void applyChanges(HttpSession ses) throws SQLException {
        if (s == Status.NEW) {
            new unitDAO().addUnit(this);
            s = Status.CLEAN;
        } else if (s == Status.MODIFIED) {
            new unitDAO().modifyUnit(this);
            s = Status.CLEAN;
        }
    }

    public void remove(HttpSession ses) throws SQLException {
        if (s != Status.NEW) {
            new unitDAO().removeUnit(this);
        }
    }

    public String getCompany() throws SQLException, ClassNotFoundException {
        return new unitDAO().getCompany();
    }
}
