package ru.EXXO.employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import ru.EXXO.Status;

public class OfficeDAO extends ru.EXXO.postgresDAO {

    public Office loadOffice(String name) throws SQLException {
        Office o = null;
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM offices WHERE short='" + name + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            o = new Office(name, rs.getString("adress"), rs.getString("tel"), ru.EXXO.Status.CLEAN);
        }
        stmt.close();
        con.close();
        return o;
    }

    public void removeOffice(Office o) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();

        String sql = "DELETE FROM offices WHERE short='" + o.getName() + "'";
        stmt.executeUpdate(sql);

        String sql1 = "UPDATE  employee SET office='Переезд', room='' WHERE office='" + o.getName() + "'";
        stmt.executeUpdate(sql1);
        stmt.close();
        con.close();

    }

    public void addOffice(Office o) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "INSERT INTO offices (short,adress,tel) VALUES('" + o.getName() + "','" + o.getAddress() + "','" + o.getTelephon() + "')";
        stmt.executeUpdate(sql);

        stmt.close();
        con.close();
    }

    public void modifyOffice(Office o) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "UPDATE offices SET adress='" + o.getAddress() + "', tel='" + o.getTelephon() + "' WHERE short='" + o.getName() + "'";
        stmt.executeUpdate(sql);

        stmt.close();
        con.close();
    }

    public ArrayList<Office> getList() throws SQLException {
        ArrayList<Office> ar = new ArrayList();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM offices ORDER BY short";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ar.add(new Office(rs.getString("short"), rs.getString("adress"), rs.getString("tel"), ru.EXXO.Status.CLEAN));
        }
        return ar;
    }

    public ArrayList getOptions() throws SQLException, ClassNotFoundException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        String sql = "SELECT short FROM offices";
        ArrayList options = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            options.add(new SelectItem(rs.getString(1), rs.getString(1), ""));
        }
        stmt.close();
        con.close();
        return options;
    }

    public ArrayList<Employee> empList(Office o) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Employee> a = new ArrayList();
        String sql = "SELECT fio, employee.unit AS unit, position, head, birthday, education,tellocal,tellmob,email, "
                + "employee.global_id as global_id, employee.id AS id, length(photo)>0 AS photo, hobby, comment, responsibility, employee.role AS role, "
                + "employee.tel AS tel, office, room, worksince, fired, firedsince, short, offices.adress AS o_adress, "
                + "offices.tel AS o_t, unit_id, units.unit AS unitName, superior FROM employee, units, offices  WHERE offices.short='"
                + o.getName() + "' AND employee.unit = unit_id AND employee.office = offices.short AND fired = 0 ORDER BY fio";
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
}
