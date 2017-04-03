package BASE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.servlet.ServletContext;
//import java.util.Date;

public class CHECK {

    public static Boolean checkDates() {
        /*
         * СНЯТЫ ОГРАНИЧЕНИЯ ПО ДАТАМ.
         Date start = BASE.VER.getStart();
         Date stop = BASE.VER.getStop();
         Date now = new Date();
         if(!stop.after(start)) return false;
         if(start.after(now)) return false;
         if(now.after(stop)) return false;
         //if((stop.getTime()-start.getTime())/100000!=26784) return false;
         * 
         */
        return true;
    }

    private Connection startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + BASE.Ver.getDBHost() + "/" + BASE.Ver.getDBName();
        Properties properties = BASE.Ver.getDBProp();
        return DriverManager.getConnection(url, properties);

    }

    int getCount() throws ClassNotFoundException, SQLException {
//        Connection con = startSQL();
//        Statement stmt = con.createStatement();
//        String sql = "SELECT key FROM register WHERE reg = 'EXXO'";
//        ResultSet rs = stmt.executeQuery(sql);
//        int count = 5;
//        while (rs.next()) {
//            String key = rs.getString(1);
//            if (CASE.isKey(key)) {
//                if (key.startsWith("a")) {
//                    count = 1000000;
//                } else if (key.startsWith("b")) {
//                    count += 25;
//                } else if (key.startsWith("c")) {
//                    count += 100;
//                } else if (key.startsWith("d")) {
//                    count += 10;
//                } else if (key.startsWith("e")) {
//                    count += 45;
//                }
//            }
//        }
//        rs.close();
//        stmt.close();
//        con.close();
//        return count;
        return 1000000;
    }

    public boolean checkEmpINS() throws ClassNotFoundException, SQLException {
//        boolean res = false;
//        Connection con = startSQL();
//        Statement stmt = con.createStatement();
//        String sql = "SELECT count(*)<" + getCount() + " FROM employee";
//        ResultSet rs = stmt.executeQuery(sql);
//        if (rs.next()) {
//            res = rs.getBoolean(1);
//        }
//        rs.close();
//        stmt.close();
//        con.close();
//        return res;
        return true;
    }

}
