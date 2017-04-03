package ru.EXXO.Ratings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;

public class RatingDAO extends ru.EXXO.postgresDAO {

    private HttpSession s;

    public RatingDAO() {
    }

    public RatingDAO(HttpSession s) {
        this.s = s;
    }

    public Rating loadRating(int id) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM rating WHERE id=" + id;
        ResultSet rs = stmt.executeQuery(sql);
        String name = "";
        String description = "";
        String type = "";
        if (rs.next()) {
            name = rs.getString("name");
            description = rs.getString("description");
            type = rs.getString("type");
        }
        stmt.close();
        con.close();
        if (type.equals("employee")) {
            return new EmployeeRating(id, name, description, Status.CLEAN);
        } else {
            return null;
        }
    }

    void addRating(Rating r) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "INSERT INTO rating VALUES (" + r.getId() + ",'" + r.getName() + "','" + r.getDescription() + "','" + r.getType().toString() + "')";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    void modifyRating(Rating r) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "UPDATE rating SET name ='" + r.getName() + "' , description = '" + r.getDescription() + "' WHERE id = " + r.getId();
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    void removeRating(Rating r) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "DELETE FROM rating WHERE id = " + r.getId();
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

}
