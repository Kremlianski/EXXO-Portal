package BASE;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class Master {

    public static boolean isLegalUnitSec(String user, String id, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        ResultSet rs = null;
        boolean res = false;
        String sql = "";

        sql = "SELECT inStructure((SELECT unit from employee WHERE id='" + user + "')," + id + ")";
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            res = rs.getBoolean(1);
        }

        rs.close();
        stmt.close();
        con.close();
        return res;
    }

}
