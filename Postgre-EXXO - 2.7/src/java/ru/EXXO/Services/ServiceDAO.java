package ru.EXXO.Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ru.EXXO.Status;
import ru.EXXO.employee.Employee;

public class ServiceDAO extends ru.EXXO.postgresDAO {

    public Service loadService(int id) throws SQLException {
        Service ser = null;
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT name, owner, description, supervisor, template, type, mintodo, mintodeside, mintoopen, stoped <> 0 AS stoped FROM process WHERE id =" + id;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            ser = new Service(id, rs.getString("name"), rs.getInt("owner"), rs.getString("description"), rs.getInt("supervisor"), rs.getString("template"), rs.getString("type"), rs.getInt("mintodo"), rs.getInt("mintodeside"), rs.getInt("mintoopen"), rs.getBoolean("stoped"), Status.CLEAN);
        }
        rs.close();
        stmt.close();
        con.close();
        return ser;
    }

    void addService(Service aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void modifyService(Service aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void removeService(Service aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Service> findServices(Employee emp) throws SQLException {
        ArrayList<Service> al = new ArrayList();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT id, name, description, supervisor, template, type, mintodo, mintodeside, mintoopen, stoped <> 0 AS stoped FROM process WHERE owner =" + emp.getId();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            al.add(new Service(rs.getInt("id"), rs.getString("name"), emp.getId(), rs.getString("description"), rs.getInt("supervisor"), rs.getString("template"), rs.getString("type"), rs.getInt("mintodo"), rs.getInt("mintodeside"), rs.getInt("mintoopen"), rs.getBoolean("stoped"), Status.CLEAN));
        }
        rs.close();
        stmt.close();
        con.close();
        return al;
    }

}
