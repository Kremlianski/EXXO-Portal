package ru.EXXO.Ratings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;

public class EmployeeVoiceDAO extends ru.EXXO.postgresDAO {

    HttpSession ses;

    public EmployeeVoiceDAO() {
    }

    public EmployeeVoiceDAO(HttpSession ses) {
        this.ses = ses;
    }

    public EmployeeVoice loadVoice(int target_id, int rating_id, int employee_id) throws SQLException {

        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        EmployeeVoice v;
        String sql = "SELECT * FROM employeeVoice WHERE target_id=" + target_id + " AND rating_id = " + rating_id + " AND employee_id=" + employee_id;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            v = new EmployeeVoice(target_id, rating_id, employee_id, ratingTypes.employee, rs.getInt("result"), Status.MODIFIED);
        } else {
            v = new EmployeeVoice(target_id, rating_id, employee_id, ratingTypes.employee, 0, Status.NEW);
        }
        stmt.close();
        con.close();
        return v;
    }

    void addVoice(EmployeeVoice v) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();

        String sql = "INSERT INTO employeeVoice VALUES (" + v.getTarget_id() + "," + v.getRating_id() + "," + v.getEmployee_id() + "," + v.getResult() + ")";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    void modifyVoice(EmployeeVoice v) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "UPDATE employeeVoice SET result=" + v.getResult() + " WHERE target_id=" + v.getTarget_id() + " AND rating_id = " + v.getRating_id() + " AND employee_id=" + v.getEmployee_id();
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    void removeVoice(EmployeeVoice v) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "DELETE FROM employeeVoice WHERE target_id=" + v.getTarget_id() + " AND rating_id = " + v.getRating_id() + " AND employee_id=" + v.getEmployee_id();
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

}
