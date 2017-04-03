
package ru.exxo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.model.SelectItem;

public class Offices {

    private Connection con;
    private ArrayList options = null;

    public Offices() {
    }

    public ArrayList getOptions() throws SQLException, ClassNotFoundException {
        getOp();
        return options;
    }

    private void getOp() throws SQLException, ClassNotFoundException {
        startSQL();
        String sql = "SELECT short FROM offices";
        options = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            options.add(new SelectItem(rs.getString(1), rs.getString(1), ""));
        }
        stmt.close();
        con.close();
    }

    private void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + BASE.Ver.getDBHost() + "/" + BASE.Ver.getDBName();
        Properties properties = BASE.Ver.getDBProp();
        con = DriverManager.getConnection(url, properties);
    }

}
