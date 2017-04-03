package BASE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class Ver {

    public static final String DefDBHost = "localhost";
    public static final String DefDBName = "EXXO";
    public static final String DefDBUser = "exxo";
    public static final String DefDBPass = "iLikeEXXO";

    public static final int newVer = 14;

    public static String getVer() {
        return "7.1";
    }

    public static String getFullVer() {
        return "2." + getVer();
    }

    public static String getM() {
        return "iLikeEXXO";
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
        String dbHost = BASE.Ver.DefDBHost;
        String dbName = BASE.Ver.DefDBName;
        String dbUser = BASE.Ver.DefDBUser;
        String dbPass = BASE.Ver.DefDBPass;

        if (sc.getInitParameter("dbHostPortal") != null) {
            dbHost = sc.getInitParameter("dbHostPortal");
        }
        if (sc.getInitParameter("dbNamePortal") != null) {
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
}
