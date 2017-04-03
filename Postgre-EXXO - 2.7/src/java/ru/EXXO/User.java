package ru.EXXO;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;

public class User implements java.io.Serializable, ru.EXXO.changeable {

    private int id;
    private String login;
    private String pass;
    private Users u;
    private Status s;

    public User(int id, String login, String pass, Users u, Status s) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.u = u;
        this.s = s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        s = Status.MODIFIED;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (!this.login.equals("root")) {
            this.login = login;
            s = Status.MODIFIED;
        }
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
        s = Status.MODIFIED;
    }

    public Users getU() {
        return u;
    }

    public void setU(Users u) {
        this.u = u;
    }

    public void applyChanges(HttpSession ses) throws SQLException {
        if (s == Status.NEW) {
            new UserDAO().addUser(this);
            s = Status.CLEAN;
        } else if (s == Status.MODIFIED) {
            new UserDAO().modifyUser(this);
            s = Status.CLEAN;
        }
    }

    public void remove(HttpSession ses) throws SQLException {
        if (s != Status.NEW) {
            new UserDAO().removeUser(this);
        }
    }

    public Status getStatus() {
        return s;
    }

    public void setStatus(Status s) {
        this.s = s;
    }

    public void dropRole(String roleName) throws SQLException {
        new UserDAO().dropRole(this, roleName);
    }

    public void addRole(String roleName) throws SQLException {
        new UserDAO().addRole(this, roleName);

    }

    public boolean isPortalUser() throws SQLException {
        return new UserDAO().isPortalUser(this);
    }

}
