package ru.EXXO.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;

public class PhotoDAO extends ru.EXXO.postgresDAO {

    private HttpSession ses;

    public PhotoDAO(HttpSession ses) {
        this.ses = ses;
    }

    PhotoDAO() {
    }

    public Photo loadPhoto() throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        Photo p = null;
        int id = java.lang.Integer.parseInt((String) ses.getAttribute("id"));
        ResultSet rs = stmt.executeQuery("SELECT length(photo)>0, photo, photo_type FROM employeeTMP WHERE id=" + id);
        if (rs.next()) {
            if (rs.getBoolean(1)) {
                p = new Photo(rs.getBytes(2), rs.getString(3), Status.CLEAN);
            }
        }
        stmt.close();
        con.close();
        return p;
    }

    public void addPhoto(Photo p) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();

        String INSERT_PICTURE = "INSERT INTO employeeTMP (photo,id, photo_type) VALUES (?, ?, ?)";

        con.setAutoCommit(false);

        PreparedStatement ps = con.prepareStatement(INSERT_PICTURE);
        ps.setInt(2, java.lang.Integer.parseInt((String) ses.getAttribute("id")));
        ps.setBytes(1, p.getImage());
        ps.setString(3, p.getType());
        ps.executeUpdate();
        con.commit();

        ps.close();
        con.close();
    }

    public void modifyPhoto(Photo p) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        String sql = "UPDATE employeeTMP SET photo=? WHERE id=" + java.lang.Integer.parseInt((String) ses.getAttribute("id"));
        con.setAutoCommit(false);
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setBytes(1, p.getImage());

        ps.executeUpdate();
        con.commit();
        ps.close();
        con.close();
    }

    public void removePhoto() throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "DELETE FROM employeeTMP WHERE id=" + java.lang.Integer.parseInt((String) ses.getAttribute("id"));
        stmt.executeUpdate(sql);
        con.close();
    }

    public boolean isPhoto() throws ClassNotFoundException, SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        boolean photo = false;
        ResultSet rs = stmt.executeQuery("SELECT length(photo)>0, photo, photo_type FROM employeeTMP WHERE id=" + java.lang.Integer.parseInt((String) ses.getAttribute("id")));
        if (rs.next()) {
            photo = rs.getBoolean(1);
        }
        stmt.close();
        con.close();
        return photo;
    }
}
