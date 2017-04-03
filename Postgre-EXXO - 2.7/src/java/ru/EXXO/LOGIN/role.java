package ru.EXXO.LOGIN;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;
import ru.EXXO.employee.Employee;

public abstract class role implements java.io.Serializable, ru.EXXO.changeable {

    public role(String name, String description, Status s) {
        this.name = name;
        this.description = description;
        this.s = s;
    }

    private String name;
    private String description;
    private boolean inne;
    private Status s;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        s = Status.MODIFIED;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        s = Status.MODIFIED;
    }

    public boolean isInne() {
        return inne;
    }

    public Status getStatus() {
        return s;
    }

    public void setStatus(Status s) {
        this.s = s;
    }

    public void applyChanges(HttpSession ses) throws SQLException {
        if (s == Status.NEW) {
            new RoleDAO().addRole(this);
            s = Status.CLEAN;
        } else if (s == Status.MODIFIED) {
            new RoleDAO().modifyRole(this);
            s = Status.CLEAN;
        }
    }

    public void remove(HttpSession ses) throws SQLException {
        if (s != Status.NEW) {
            new RoleDAO().removeRole(this);
        }
    }

    public void addTo(int id) throws SQLException {
        if (s == Status.CLEAN) {
            new RoleDAO().addTo(this, id);
        }
    }

    public void dropFrom(int id) throws SQLException {
        new RoleDAO().dropFrom(this, id);
    }

    public ArrayList<Employee> findEmployee() throws SQLException {
        return new RoleDAO().findEmployees(this);
    }

    public int countUsers() throws SQLException {
        return new RoleDAO().countUsers(this);
    }
}
