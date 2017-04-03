package ru.EXXO.LOGIN;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ru.EXXO.Status;
import ru.EXXO.employee.Employee;
import ru.EXXO.employee.Office;
import ru.EXXO.employee.unit;

public class RoleDAO extends ru.EXXO.postgresDAO {

    //загружаем экземпляр из базы данных
    public role loadRole(String name) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String description;
        boolean inner;
        String sql = "SELECT * FROM roles WHERE name = '" + name + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            description = rs.getString("description");
            inner = rs.getBoolean("inne");
        } else {
            return null;
        }
        rs.close();
        stmt.close();
        con.close();
        if (inner) {
            return new InnerRole(name, description, ru.EXXO.Status.CLEAN);
        } else {
            return new outerRole(name, description, ru.EXXO.Status.CLEAN);
        }
    }

    public void addRole(role r) throws SQLException {
        if (!r.isInne()) {
            Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO roles VALUES('" + r.getName() + "', '" + r.getDescription() + "', 'f')";
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
        }
    }

    public void modifyRole(role r) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "UPDATE roles SET description='" + r.getDescription() + "' WHERE name='" + r.getName() + "'";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    public void removeRole(role r) throws SQLException {
        if (!r.isInne()) {
            Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
            Statement stmt = con.createStatement();
            String sql = "DELETE FROM roles WHERE name='" + r.getName() + "'";
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
        }
    }

    public ArrayList<role> finedAllRoles() throws SQLException {
        ArrayList<role> a = new ArrayList();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM roles WHERE name<>'portal_admin' ORDER BY name";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("name");
            String description = rs.getString("description");
            boolean inner = rs.getBoolean("inne");
            if (inner) {
                a.add(new InnerRole(name, description, ru.EXXO.Status.CLEAN));
            } else {
                a.add(new outerRole(name, description, ru.EXXO.Status.CLEAN));
            }
        }
        rs.close();
        stmt.close();
        con.close();
        return a;
    }

    public ArrayList<role> finedInnerRoles() throws SQLException {
        ArrayList<role> a = new ArrayList();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM roles WHERE inne='t' AND name<>'portal_admin'";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("name");
            String description = rs.getString("description");
            boolean inner = rs.getBoolean("inne");
            if (inner) {
                a.add(new InnerRole(name, description, ru.EXXO.Status.CLEAN));
            } else {
                a.add(new outerRole(name, description, ru.EXXO.Status.CLEAN));
            }
        }
        rs.close();
        stmt.close();
        con.close();
        return a;
    }

    public ArrayList<role> finedOuterRoles() throws SQLException {
        ArrayList<role> a = new ArrayList();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM roles WHERE inner='t'";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("name");
            String description = rs.getString("description");
            boolean inner = rs.getBoolean("inne");
            if (inner) {
                a.add(new InnerRole(name, description, ru.EXXO.Status.CLEAN));
            } else {
                a.add(new outerRole(name, description, ru.EXXO.Status.CLEAN));
            }
        }
        rs.close();
        stmt.close();
        con.close();
        return a;
    }

    public ArrayList<role> finedUserRoles(int id) throws SQLException {
        ArrayList<role> a = new ArrayList();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT roles.name AS name, roles.description AS description, roles.inne AS inne FROM roles, user_roles, users WHERE user_roles.login = users.login AND user_roles.role = roles.name AND users.id = " + id;
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("name");
            String description = rs.getString("description");
            boolean inner = rs.getBoolean("inne");
            if (inner) {
                a.add(new InnerRole(name, description, ru.EXXO.Status.CLEAN));
            } else {
                a.add(new outerRole(name, description, ru.EXXO.Status.CLEAN));
            }
        }
        rs.close();
        stmt.close();
        con.close();
        return a;
    }

    void addTo(role r, int id) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT add_role_to(" + id + ",'" + r.getName() + "')";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }

    void dropFrom(role r, int id) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "DELETE FROM user_roles WHERE login = (SELECT login FROM users WHERE id = " + id + ") AND role = '" + r.getName() + "'";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    ArrayList<Employee> findEmployees(role r) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Employee> a = new ArrayList();
        String sql = "SELECT fio, employee.unit AS unit, position, head, birthday, education,tellocal,tellmob,email, "
                + "employee.global_id as global_id, employee.id AS id, length(photo)>0 AS photo, hobby, comment, responsibility, employee.role AS role, "
                + "employee.tel AS tel, office, room, worksince, fired, firedsince, short, offices.adress AS o_adress, "
                + "offices.tel AS o_t, unit_id, units.unit AS unitName, superior FROM employee, units, offices, users, user_roles WHERE user_roles.role = '"
                + r.getName() + "' AND employee.id = users.id  AND users.login = user_roles.login AND employee.unit = unit_id AND employee.office = offices.short ORDER BY fio";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Office office = new Office(rs.getString("short"), rs.getString("o_adress"), rs.getString("o_t"), ru.EXXO.Status.CLEAN);
            unit unit = new unit(rs.getInt("unit_id"), rs.getString("unitName"), rs.getInt("superior"), ru.EXXO.Status.CLEAN);

            Employee emp = new Employee(rs.getString("fio"), unit, rs.getString("position"), (rs.getInt("head") == 1), rs.getDate("birthday"),
                    rs.getString("education"), rs.getString("tellocal"), rs.getString("tellmob"), rs.getString("email"),
                    rs.getInt("id"), rs.getInt("global_id"), rs.getBoolean("photo"), rs.getString("hobby"), rs.getString("responsibility"),
                    rs.getString("tel"), office, rs.getString("room"), rs.getDate("worksince"), (rs.getInt("fired") != 0),
                    rs.getDate("firedsince"), (rs.getString("role") != null), rs.getString("comment"), Status.CLEAN);
            a.add(emp);
        }
        stmt.close();
        con.close();
        return a;

    }

    public ArrayList<Employee> findEmployees(String name) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Employee> a = new ArrayList();
        String sql = "SELECT fio, employee.unit AS unit, position, head, birthday, education,tellocal,tellmob,email, "
                + "employee.global_id as global_id, employee.id AS id, length(photo)>0 AS photo, hobby, comment, responsibility, employee.role AS role, "
                + "employee.tel AS tel, office, room, worksince, fired, firedsince, short, offices.adress AS o_adress, "
                + "offices.tel AS o_t, unit_id, units.unit AS unitName, superior FROM employee, units, offices, users, user_roles WHERE user_roles.role = '"
                + name + "' AND employee.id = users.id  AND users.login = user_roles.login AND employee.unit = unit_id AND employee.office = offices.short ORDER BY fio";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Office office = new Office(rs.getString("short"), rs.getString("o_adress"), rs.getString("o_t"), ru.EXXO.Status.CLEAN);
            unit unit = new unit(rs.getInt("unit_id"), rs.getString("unitName"), rs.getInt("superior"), ru.EXXO.Status.CLEAN);

            Employee emp = new Employee(rs.getString("fio"), unit, rs.getString("position"), (rs.getInt("head") == 1), rs.getDate("birthday"),
                    rs.getString("education"), rs.getString("tellocal"), rs.getString("tellmob"), rs.getString("email"),
                    rs.getInt("id"), rs.getInt("global_id"), rs.getBoolean("photo"), rs.getString("hobby"), rs.getString("responsibility"),
                    rs.getString("tel"), office, rs.getString("room"), rs.getDate("worksince"), (rs.getInt("fired") != 0),
                    rs.getDate("firedsince"), (rs.getString("role") != null), rs.getString("comment"), Status.CLEAN);
            a.add(emp);
        }
        stmt.close();
        con.close();
        return a;

    }

    int countUsers(role r) throws SQLException {
        int res = 0;
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT count(*) FROM user_roles WHERE role='" + r.getName() + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            res = rs.getInt(1);
        }
        stmt.close();
        con.close();
        return res;

    }

}
