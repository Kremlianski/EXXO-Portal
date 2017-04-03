package ru.EXXO.LOGIN;

import javax.servlet.ServletContext;
import org.imgscalr.Scalr.Method;

public class Context {

    private static String DBHost = "localhost";
    private static String DBName = "EXXO";
    private static String DBUser = "exxo";
    private static String DBPass = "iLikeEXXO";
    private static int galsHeight;
    private static Method galsSpead;

    public static String getDBHost() {
        return DBHost;
    }

    public static void setContext(ServletContext sc) {
        if (sc.getInitParameter("dbHostPortal") != null) {
            DBHost = sc.getInitParameter("dbHostPortal");
        }
        if (sc.getInitParameter("dbNamePortal") != null) {
            DBName = sc.getInitParameter("dbNamePortal");
        }
        if (sc.getInitParameter("dbUserPortal") != null) {
            DBUser = sc.getInitParameter("dbUserPortal");
        }
        if (sc.getInitParameter("dbPassPortal") != null) {
            DBPass = sc.getInitParameter("dbPassPortal");
        }
        galsHeight = BASE.VER.getGalsHeight(sc);
        galsSpead = BASE.VER.getGalsSpeed(sc);
    }

    public static String getDBName() {
        return DBName;
    }

    public static String getDBUser() {
        return DBUser;
    }

    public static String getDBPass() {
        return DBPass;
    }

    public static int getGalsHeight() {
        return galsHeight;
    }

    public static Method getGalsSpead() {
        return galsSpead;
    }

}
