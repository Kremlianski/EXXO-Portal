package BEANS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class MyBean {

    Connection con = null;
    Statement stmt = null;
    String aist = "EXXO";

    public String getRes(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        String id = (String) r.getSession().getAttribute("id");
        startSQL(r);
        String res = "<tbody><tr><td>Ошибка ввода</td></tr></tbody>";
        String sql = "SELECT structure FROM personal WHERE owner='" + id + "'";
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            res = rs.getString(1);
        } else {
            res = "<tbody><tr><td class=\"exxo-my-one exxo-column\"><div id=\"calendar\" class=\"portlet\"></div></td>"
                    + "<td class=\"exxo-my-two exxo-column\"><div id=\"newBlog_aI_1\" class=\"portlet\"></div></td>"
                    + "<td class=\"exxo-my-three exxo-column\"><div id=\"newDocs_2_1\" class=\"portlet\"></div></td></tr></tbody>";
        }
        rs.close();
        stmt.close();
        con.close();

        return res;
    }

    void startSQL(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        con = BASE.VER.getServletConnection(sc);
        stmt = con.createStatement();
    }

}
