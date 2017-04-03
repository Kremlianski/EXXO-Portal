package ru.EXXO;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;

public interface changeable {

    public Status getStatus();

    public void setStatus(Status s);

    public void applyChanges(HttpSession ses) throws SQLException;

    public void remove(HttpSession ses) throws SQLException;
}
