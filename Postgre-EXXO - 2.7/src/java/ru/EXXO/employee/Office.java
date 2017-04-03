package ru.EXXO.employee;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;

public class Office implements java.io.Serializable, ru.EXXO.changeable {

    private String name;
    private String address;
    private String telephon;
    private Status s;

    public Office(String name, String address, String telephon, Status s) {
        this.name = name;
        this.address = address;
        this.telephon = telephon;
        this.s = s;
    }

    public Office() {
        s = Status.CLEAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        s = Status.MODIFIED;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        s = Status.MODIFIED;
    }

    public String getTelephon() {
        return telephon;
    }

    public void setTelephon(String telephon) {
        this.telephon = telephon;
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
            new OfficeDAO().addOffice(this);
            s = Status.CLEAN;
        } else if (s == Status.MODIFIED) {
            new OfficeDAO().modifyOffice(this);
            s = Status.CLEAN;
        }
    }

    public void remove(HttpSession ses) throws SQLException {
        if (s != Status.NEW) {
            new OfficeDAO().removeOffice(this);
        }
    }

    public ArrayList getOptions() throws SQLException, ClassNotFoundException {
        return new OfficeDAO().getOptions();
    }

    public ArrayList getEmps() throws SQLException, ClassNotFoundException {
        return new OfficeDAO().empList(this);
    }
}
