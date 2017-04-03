package BASE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class VER {

    public static final String DefDBHost = "localhost";
    public static final String DefDBName = "EXXO";
    public static final String DefDBUser = "exxo";
    public static final String DefDBPass = "iLikeEXXO";

//private static String user="ООО СофтЛайн Интернет Трейд";
    public static final int newVer = 14;

    public static String getVer() {
        return "2.7.1";
    }

    public static String getStatus() {
        return "d";
    }

    private static String GoD(String a) {
        String b = "";
        char[] s = new char[]{a.charAt(90), a.charAt(32), a.charAt(51), a.charAt(9), a.charAt(77), a.charAt(14),
            a.charAt(2), a.charAt(76), a.charAt(29), a.charAt(18), a.charAt(59), a.charAt(12), a.charAt(80)};
        b = new String(s);
        return b;
    }

    private static String GoDD(String a) {
        String b = "";
        char[] s = new char[]{a.charAt(20), a.charAt(30), a.charAt(10), a.charAt(90), a.charAt(80), a.charAt(70),
            a.charAt(4), a.charAt(58), a.charAt(87), a.charAt(33), a.charAt(77), a.charAt(12), a.charAt(8)};
        b = new String(s);
        return b;
    }

    public static String getM() {
        return "iLikeEXXO";
    }

    private static String[] Go(String ar[]) {
        String[] br = new String[100];
        for (int i = 0; i < 100; i++) {
            String b = "";
            String a = ar[i];
            char[] s = new char[]{a.charAt(4), a.charAt(17), a.charAt(55), a.charAt(2), a.charAt(98), a.charAt(42),
                a.charAt(1), a.charAt(33), a.charAt(77), a.charAt(11), a.charAt(49), a.charAt(8)};
            b = new String(s);
            br[i] = b;
        }
        return br;
    }

    public static String getDBName() {
        String name = DefDBName;
        String n = getParam("dbNamePortal");
        if (n != null) {
            name = n;
        }
        return name;
    }

    public static String getDBHost() {
        String name = DefDBHost;
        String n = getParam("dbHostPortal");
        if (n != null) {
            name = n;
        }
        return name;
    }

    public static Properties getDBProp() {
        Properties properties = new Properties();
        properties.setProperty("user", DefDBUser);
        properties.setProperty("password", DefDBPass);
        Properties n = getProp();
        if (n != null) {
            properties = n;
        }
        return properties;
    }

    public static String getParam(String p) {
        String res = (String) FacesContext.getCurrentInstance().getExternalContext().getInitParameter(p);
        return res;
    }

    public static Properties getProp() {
        Properties properties = new Properties();
        if (getParam("dbUserPortal") != null && getParam("dbPassPortal") != null) {
            properties.setProperty("user", getParam("dbUserPortal"));
            properties.setProperty("password", getParam("dbPassPortal"));
            return properties;
        } else {
            return null;
        }
    }

    public static Connection getServletConnection(ServletContext sc) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String dbHost = BASE.VER.DefDBHost;
        String dbName = BASE.VER.DefDBName;
        String dbUser = BASE.VER.DefDBUser;
        String dbPass = BASE.VER.DefDBPass;

        if (sc.getInitParameter("dbHostPortal") != null) {
            dbHost = sc.getInitParameter("dbHostPortal");
        }
        if (sc.getInitParameter("dbNanePortal") != null) {
            dbName = sc.getInitParameter("dbNamePortal");
        }
        if (sc.getInitParameter("dbUserPortal") != null) {
            dbUser = sc.getInitParameter("dbUserPortal");
        }
        if (sc.getInitParameter("dbPassPortal") != null) {
            dbPass = sc.getInitParameter("dbPassPortal");
        }

        String url = "jdbc:postgresql://" + dbHost + "/" + dbName;
        Properties properties = new Properties();
        properties.setProperty("user", dbUser);
        properties.setProperty("password", dbPass);

        return DriverManager.getConnection(url, properties);

    }

    public static String getDBRedirect() {
        String name = "/EXXO/";
        String n = getParam("dbRedirect");
        if (n != null) {
            name = n;
        }
        return name;
    }

}
