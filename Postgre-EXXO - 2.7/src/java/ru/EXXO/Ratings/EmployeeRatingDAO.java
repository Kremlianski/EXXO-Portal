package ru.EXXO.Ratings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;

public class EmployeeRatingDAO {

    private HttpSession s;

    public EmployeeRatingDAO() {
    }

    public EmployeeRatingDAO(HttpSession s) {
        this.s = s;
    }

    public ArrayList<EmployeeRating> ratingList() throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM rating ORDER BY name";
        ArrayList<EmployeeRating> ar = new ArrayList();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ar.add(new EmployeeRating(rs.getInt("id"), rs.getString("name"), rs.getString("description"), Status.CLEAN));
        }
        return ar;
    }

    public double findResults(EmployeeRating r, int target_id) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT avg(result) FROM employeeVoice WHERE target_id=" + target_id + " AND rating_id = " + r.getId();
        ResultSet rs = stmt.executeQuery(sql);
        double res = 0;
        if (rs.next()) {
            res = rs.getDouble(1);
        }
        stmt.close();
        con.close();
        return res;
    }

    public ratingResult findResult(EmployeeRating r, int target_id) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT avg(result), count(*) FROM employeeVoice WHERE target_id=" + target_id + " AND rating_id = " + r.getId();
        ResultSet rs = stmt.executeQuery(sql);
        ratingResult res = null;
        if (rs.next()) {
            res = new ratingResult(rs.getDouble(1), rs.getInt(2), target_id, r.getId());
        }
        stmt.close();
        con.close();
        return res;
    }

    public ArrayList<FIOratingResult> findList(EmployeeRating r) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        ArrayList<FIOratingResult> a = new ArrayList();
        String sql = "SELECT avg(result), count(*), target_id, (SELECT fio FROM employee WHERE id = target_id) FROM employeeVoice WHERE rating_id = " + r.getId() + " GROUP BY target_id  ORDER BY avg(result) DESC, count(*) DESC";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            a.add(new FIOratingResult(rs.getString(4), rs.getDouble(1), rs.getInt(2), rs.getInt(3), r.getId()));
        }
        return a;
    }
}
