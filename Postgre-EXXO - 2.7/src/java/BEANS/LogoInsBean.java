package BEANS;

import java.sql.*;
//import java.util.Properties;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class LogoInsBean {

    public String slogan = null;
    public String photoMarker = null;

    public void setParams(HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT img, slogan FROM logo";

        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {

            slogan = rs.getString("slogan");

            InputStream is = rs.getBinaryStream("img");
            if (is != null) {
                photoMarker = "ok";
            }
        }
        rs.close();
        stmt.close();
        con.close();
    }
}
