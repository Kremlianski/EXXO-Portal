package BEANS;

import java.sql.*;
//import java.util.Properties;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class BlogsBean {

    String list = "";
    String owner = "";
    String user = "";
    String guser = "";
    public String navi = "";

    public void setOwner(String O, String u, String g) {
        owner = O;
        user = u;
        guser = g;
    }

    public String getEmp(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        int rows = BASE.VER.getMaxRows(sc);
        String ROWS = BASE.VER.getMaxRows(sc) + "";
        String offset = "0";
        if (r.getParameter("of") != null) {
            offset = r.getParameter("of");
        }
        Connection con = BASE.VER.getServletConnection(sc);
        CallableStatement cstmt = con.prepareCall("{? = call BlogsBeanList(?, ?, ?, ?, ?)}");
        cstmt.registerOutParameter(1, Types.ARRAY);
        cstmt.setString(2, owner);
        cstmt.setString(3, user);
        cstmt.setString(4, guser);
        cstmt.setString(5, ROWS);
        cstmt.setString(6, offset);
        cstmt.executeUpdate();

        list = "";
        ResultSet rr = cstmt.getArray(1).getResultSet();
        if (rr.next()) {
            list += rr.getString(2);
        }
        boolean next = false;
        if (rr.next()) {
            if (rr.getString(2).equals("1")) {
                next = true;
            }
        }
        navi += "<div id='menu-navi'><table><tr><td class='oneNavi'>";
        String andown = "";
        if (!owner.equals(user)) {
            andown = "&owner=" + owner;
        }
        int of = Integer.parseInt(offset);
        if (of > 0) {
            int prev = of - rows;
            if (prev < 0) {
                prev = 0;
            }
            navi += "<a href='blogs.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + rows;
            navi += "<a href='blogs.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";
        rr.close();
        cstmt.close();

        String emp = "";
        CallableStatement cstmt1 = con.prepareCall("{? = call BlogsBeanEmp(?)}");
        cstmt1.registerOutParameter(1, Types.VARCHAR);
        cstmt1.setString(2, owner);
        cstmt1.executeUpdate();

        emp += cstmt1.getString(1);
        cstmt1.close();
        con.close();

        return emp;
    }

    public String getList() {
        return list;
    }

}
