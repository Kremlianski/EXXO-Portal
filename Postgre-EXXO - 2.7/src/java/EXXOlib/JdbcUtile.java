package EXXOlib;

import java.sql.*;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
//import java.util.Properties;
import javax.servlet.ServletContext;

public class JdbcUtile {

    public static Result runQuery(String query, int maxrows, ServletContext sc) throws SQLException, ClassNotFoundException {
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        ResultSet rset = null;
        Result res = null;

        try {

            rset = stmt.executeQuery(query);

            /* 
         * Convert the ResultSet to a 
         * Result object that can be used with JSTL/JSF tags 
             */
            if (maxrows == -1) {
                res = ResultSupport.toResult(rset);
            } else {
                res = ResultSupport.toResult(rset, maxrows);
            }
        } finally {
            if (rset != null) {
                rset.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            if (con != null) {
                con.close();
            }
        }

        return res;
    }

}
