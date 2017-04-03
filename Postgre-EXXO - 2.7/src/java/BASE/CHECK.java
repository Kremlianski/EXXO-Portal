package BASE;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    static int getCount(ServletContext sc) throws ClassNotFoundException, SQLException {
//        Connection con = BASE.VER.getServletConnection(sc);
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

    static int getCount() throws ClassNotFoundException, SQLException {
//        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
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

    public static boolean checkEmpUPD(ServletContext sc) throws ClassNotFoundException, SQLException {
//        boolean res = false;
//        Connection con = BASE.VER.getServletConnection(sc);
//        Statement stmt = con.createStatement();
//        String sql = "SELECT count(*)<=" + getCount(sc) + " FROM employee";
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

    public static boolean checkEmpINS(ServletContext sc) throws ClassNotFoundException, SQLException {
//        boolean res = false;
//        Connection con = BASE.VER.getServletConnection(sc);
//        Statement stmt = con.createStatement();
//        String sql = "SELECT count(*)<" + getCount(sc) + " FROM employee";
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

    public static boolean checkEmpINS() throws ClassNotFoundException, SQLException {
//        boolean res = false;
//        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
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

    public static String getRest(ServletContext sc) throws ClassNotFoundException, SQLException {
//        Connection con = BASE.VER.getServletConnection(sc);
//        Statement stmt = con.createStatement();
//        String sql = "SELECT key FROM register WHERE reg = 'EXXO'";
//        ResultSet rs = stmt.executeQuery(sql);
//        String res = "";
//        int count1 = 0;
//        int count = 5;
//        int countR = 5;
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
//        sql = "SELECT count(*) FROM employee";
//        rs = stmt.executeQuery(sql);
//        if (rs.next()) {
//            count1 = rs.getInt(1);
//        }
//
//        rs.close();
//        stmt.close();
//        con.close();
//
//        countR = count - count1;
//        res = countR + "";
//        if (count >= 1000000) {
//            res = "";
//        } else if (countR < 0) {
//            res = "0";
//        }
//        return res;
        return "";
    }

    public static String getRest() throws ClassNotFoundException, SQLException {
//        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
//        Statement stmt = con.createStatement();
//        String sql = "SELECT key FROM register WHERE reg = 'EXXO'";
//        ResultSet rs = stmt.executeQuery(sql);
//        String res = "";
//        int count1 = 0;
//        int count = 5;
//        int countR = 5;
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
//        sql = "SELECT count(*) FROM employee";
//        rs = stmt.executeQuery(sql);
//        if (rs.next()) {
//            count1 = rs.getInt(1);
//        }
//
//        rs.close();
//        stmt.close();
//        con.close();
//
//        countR = count - count1;
//        res = countR + "";
//        if (count >= 1000000) {
//            res = "";
//        } else if (countR < 0) {
//            res = "0";
//        }
//        return res;

        return "";
    }
}
