package BEANS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CSS1 {

    private String css = "";

    public String getCss(String page) throws SQLException, ClassNotFoundException {
        Connection con = startSQL();
        Statement stmt = con.createStatement();
        String sql = "SELECT css FROM css1 WHERE page='" + page + "'";
        ResultSet rs1 = stmt.executeQuery(sql);
        if (rs1.next()) {
            css = rs1.getString(1);
        }
        rs1.close();
        stmt.close();
        con.close();
        return css;
    }

    private Connection startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = BASE.VER.getDBProp();
        return DriverManager.getConnection(url, properties);
    }
}
