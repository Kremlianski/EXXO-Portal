package BEANS;

import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class NewBlogsB {

    public String navi = "";

    public String getList(HttpServletRequest r, String owner, String t, String guser) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        String list = "";
        int rows = BASE.VER.getMaxRows(sc);
        String ROWS = BASE.VER.getMaxRows(sc) + "";
        String offset = "0";
        if (r.getParameter("of") != null) {
            offset = r.getParameter("of");
        }
        CallableStatement cstmt = con.prepareCall("{? = call NewBlogsB(?,?,?,?,?)}");
        cstmt.registerOutParameter(1, Types.ARRAY);
        cstmt.setString(2, owner);
        cstmt.setString(3, t);
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
        if (t != null) {
            andown = "&t=" + t;
        }
        int of = Integer.parseInt(offset);
        if (of > 0) {
            int prev = of - rows;
            if (prev < 0) {
                prev = 0;
            }
            navi += "<a href='newBlogsB.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + rows;
            navi += "<a href='newBlogsB.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";
        rr.close();
        cstmt.close();

        con.close();

        return list;
    }

}
