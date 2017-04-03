package BEANS;

import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FindBlogBean {

    public String navi = "";

    public String getFrase(HttpServletRequest r) throws ClassNotFoundException, SQLException {
        HttpSession session = r.getSession(true);
        String o = (String) session.getAttribute("id");
        String g = (String) session.getAttribute("global_id");
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        int rows = BASE.VER.getMaxRows(sc);
        String ROWS = BASE.VER.getMaxRows(sc) + "";
        String offset = "0";
        if (r.getParameter("of") != null) {
            offset = r.getParameter("of");
        }
        String list = "";
        String frase = r.getParameter("find");
        if (frase == null) {
            frase = (String) session.getAttribute("frase");
        } else {
            session.setAttribute("frase", frase);
        }
        CallableStatement cstmt = con.prepareCall("{? = call FindBlogs(?,?,?,?,?)}");
        cstmt.registerOutParameter(1, Types.ARRAY);
        cstmt.setString(2, o);
        cstmt.setString(3, frase);
        cstmt.setString(4, g);
        cstmt.setString(5, ROWS);
        cstmt.setString(6, offset);

        cstmt.executeUpdate();

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
        int of = Integer.parseInt(offset);
        if (of > 0) {
            int prev = of - rows;
            if (prev < 0) {
                prev = 0;
            }
            navi += "<a href='findBlogs.jsp?of=" + prev + andown + "' class='exxo-shadow' id='backward'></a>";
        }
        navi += "</td><td></td><td class='threeNavi'>";
        if (next) {
            int sled = of + rows;
            navi += "<a href='findBlogs.jsp?of=" + sled + andown + "'  class='exxo-shadow' id='forward'></a>";
        }
        navi += "</td></tr></table></div>";
        rr.close();
        cstmt.close();

        con.close();

        return list;

    }
}
