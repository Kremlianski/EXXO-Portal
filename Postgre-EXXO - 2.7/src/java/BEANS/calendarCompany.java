package BEANS;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class calendarCompany {

    public String back = "";
    public String res = "";
    public boolean ok = true;
    public String name = "";

    public void getList(HttpServletRequest req) throws ClassNotFoundException, SQLException, IOException {
        ServletContext sc = req.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String s = req.getParameter("s");
        String id = req.getParameter("id");
        String owner;
        String date = req.getParameter("date");
        HttpSession session = req.getSession(true);
        owner = (String) session.getAttribute("id");
        String sql = "";

        back = "calendarCompare.xhtml";
        if (date != null) {
            back = "calendarCompare.xhtml?date=" + date;
        }
        sql = "SELECT id, get_fio(id) FROM calCimpare WHERE owner = '" + owner + "'";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            res += "<div class='emps' id='" + rs.getInt(1) + "'>" + EXXOlib.textLib.shortFIO(rs.getString(2)) + "</div>";
        }

        rs.close();
        stmt.close();
        con.close();
    }

}
