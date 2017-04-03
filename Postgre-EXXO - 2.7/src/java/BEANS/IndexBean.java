package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class IndexBean {

    String res = "<tbody><tr><td>Ошибка ввода</td></tr></tbody>";
    String title = "";
    Connection con = null;
    Statement stmt = null;
    String aist = "EXXO";

    public void setId(String page_id, HttpServletRequest r) throws ClassNotFoundException, SQLException {

        startSQL(r);

        String id = "2";
        if (page_id != null) {
            id = page_id;
        }
        String sql = "SELECT structure,title FROM structure WHERE page_id='" + id + "'";
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            res = rs.getString(1);
            title = rs.getString(2);
        }

        rs.close();
        stmt.close();
        con.close();
    }

    public String getRes() {
        return res;
    }

    public String getTitle() {
        return title;
    }

    /*
public boolean isActivated(HttpServletRequest r) throws ClassNotFoundException, SQLException{
            startSQL(r);
            String k="";
            String sql = "SELECT key FROM register WHERE reg = '"+aist+"'";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) k=rs.getString(1);
            rs.close();
            stmt.close();
            con.close();
            return  BASE.CASE.isKey(k); 
    }
 * 
     */
    void startSQL(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        con = BASE.VER.getServletConnection(sc);
        stmt = con.createStatement();
    }

}
