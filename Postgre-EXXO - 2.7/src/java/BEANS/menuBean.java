package BEANS;

import java.io.IOException;
//import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.jdom.JDOMException;
//import org.jaxen.jdom.JDOMXPath;

public class menuBean {

    public String getMenu(HttpServletRequest r) throws ClassNotFoundException, SQLException, UnsupportedEncodingException, JDOMException, IOException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT menu FROM menu";
        ResultSet rs = stmt.executeQuery(sql);
        String res = "Ошибка ввода";
        if (rs.next()) {
            res = rs.getString(1);
        }

        rs.close();
        stmt.close();
        con.close();
        return res;

    }
}
