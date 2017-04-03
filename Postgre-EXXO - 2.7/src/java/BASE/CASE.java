package BASE;

//import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;

//import java.util.Properties;
public class CASE {

    public static boolean isDemo(ServletContext sc) throws ClassNotFoundException {
        //включается либо если register содержит iLikeEXXO или если таблицы не существует
        boolean res = false;
        Connection con;
        try {
            con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "SELECT key='iLikeEXXO' FROM register WHERE reg='EXXO' LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                res = rs.getBoolean(1);
            }
            rs.close();
            stmt.close();
            con.close();
            return res;
        } catch (SQLException ex) {
            return true;
        }
    }

    public static boolean isDemo() throws ClassNotFoundException {
        //включается либо если register содержит iLikeEXXO или если таблицы не существует
        boolean res = false;
        Connection con;
        try {
            con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT key='iLikeEXXO' FROM register WHERE reg='EXXO' LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                res = rs.getBoolean(1);
            }
            rs.close();
            stmt.close();
            con.close();
            return res;
        } catch (SQLException ex) {
            return true;
        }
    }

    public static boolean isKey(String S) {
        Boolean res = false;
        String[] KEYS;
        String A[] = more.getA();
        String B[] = more.getB();
        String C[] = more.getC();
        String D[] = more.getD();
        String E[] = more.getE();
        String F = S.substring(2);

        if (S.startsWith("a")) {
            KEYS = A;
        } else if (S.startsWith("b")) {
            KEYS = B;
        } else if (S.startsWith("c")) {
            KEYS = C;
        } else if (S.startsWith("d")) {
            KEYS = D;
        } else {
            KEYS = E;
        }

        for (int i = 0; i < KEYS.length; i++) {
            if (KEYS[i].equals(F)) {
                res = true;
            }
        }
        return res;
    }

}
