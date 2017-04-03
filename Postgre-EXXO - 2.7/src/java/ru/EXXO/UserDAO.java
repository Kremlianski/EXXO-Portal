/*
CREATE TABLE users
(
  id integer,
  login character varying(250) NOT NULL,
  pass character varying(50) NOT NULL,
  CONSTRAINT users_pkey PRIMARY KEY (login)
)
 */
package ru.EXXO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ru.EXXO.employee.Employee;

public class UserDAO extends ru.EXXO.postgresDAO {

    public User loadUser(String login) throws SQLException {
        User u = null;
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM users WHERE login='" + login + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            u = new User(rs.getInt("id"), rs.getString("login"), "*", ru.EXXO.Users.EMPLOYEE, ru.EXXO.Status.CLEAN);
        }
        stmt.close();
        con.close();
        return u;
    }

    public User loadUser(Employee emp) throws SQLException {
        User u = null;
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT login, id  FROM users WHERE id=" + emp.getId();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            u = new User(rs.getInt("id"), rs.getString("login"), "*", ru.EXXO.Users.EMPLOYEE, ru.EXXO.Status.CLEAN);
        }
        stmt.close();
        con.close();
        return u;
    }

    public void removeUser(User u) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "DELETE FROM users WHERE login='" + u.getLogin() + "'";
        stmt.executeUpdate(sql);

        stmt.close();
        con.close();
    }

    public void addUser(User u) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "INSERT INTO users (id,login,pass) VALUES(" + u.getId() + ",'" + u.getLogin() + "',MD5('" + u.getPass() + "'))";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    public void modifyUser(User u) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "UPDATE users SET  login = '" + u.getLogin() + "' AND pass = MD5('" + u.getPass() + "') WHERE login = '" + u.getLogin() + "'";
        if (u.getPass().equals("*")) {
            sql = "UPDATE users SET  login = '" + u.getLogin() + "' WHERE login = '" + u.getLogin() + "'";
        }
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    public ArrayList<User> getList() throws SQLException {
        ArrayList<User> ar = new ArrayList();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM users ORDER BY login";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ar.add(new User(rs.getInt("id"), rs.getString("login"), "*", ru.EXXO.Users.EMPLOYEE, ru.EXXO.Status.CLEAN));
        }
        stmt.close();
        con.close();
        return ar;
    }

    void dropRole(User u, String roleName) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "DELETE FROM user_roles WHERE login='" + u.getLogin() + "' AND role = '" + roleName + "'";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    void addRole(User u, String roleName) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "INSERT INTO user_roles VALUES ('" + u.getLogin() + "', '" + roleName + "')";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    boolean isPortalUser(User u) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM user_roles WHERE role = 'portal_user' AND login = '"
                + u.getLogin() + "'";
        stmt.executeQuery(sql);
        ResultSet rs = stmt.executeQuery(sql);
        boolean res = rs.next();
        stmt.close();
        con.close();

        return res;
    }

    public boolean checkLogo(String l) throws ClassNotFoundException, SQLException {
        boolean r = true;
        if (l.length() >= 4) {
            Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM users WHERE login='" + l + "'";
            r = stmt.executeQuery(sql).next();
            stmt.close();
            con.close();
            return !r;
        } else {
            return false;
        }
    }

}
