package BEANS;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class PhotoBean implements Serializable {

    private boolean photo = false;
    private byte[] image = null;
    private int width = 0;
    private int height = 0;
    private int x = 0;
    private int y = 0;
    private String type = "";

    public boolean isPhoto() throws ClassNotFoundException, SQLException {
        Connection con = startSQL();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT length(photo)>0, photo, photo_type FROM employeeTMP WHERE id=0");
        if (rs.next()) {
            photo = rs.getBoolean(1);
            image = rs.getBytes(2);
            type = rs.getString(3);
        }
        stmt.close();
        con.close();
        return photo;
    }

    public void updateImage() throws IOException, ClassNotFoundException, SQLException {
//String format=ru.exxo.IMGES.getFormat(image, type);
        image = ru.exxo.IMGES.photoCrop(image, width, height, x, y, "png");

        Connection con = startSQL();
        String sql = "UPDATE employeeTMP SET photo=? WHERE id=0";
        con.setAutoCommit(false);
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setBytes(1, image);

        ps.executeUpdate();
        con.commit();
        ps.close();
        con.close();
    }

    private Connection startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + BASE.Ver.getDBHost() + "/" + BASE.Ver.getDBName();
        Properties properties = BASE.Ver.getDBProp();
        return DriverManager.getConnection(url, properties);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
