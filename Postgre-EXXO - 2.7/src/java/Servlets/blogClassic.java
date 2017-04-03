package Servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class blogClassic extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String superior = request.getParameter("superior");
        String id = request.getParameter("id");
        String res = "";
        String t = null;
        String type = "";
        boolean next = false;
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = (String) session.getAttribute("id");
        String offset = "0";
        if (request.getParameter("of") != null) {
            offset = request.getParameter("of");
        }
        if (request.getParameter("owner") != null) {
            owner = request.getParameter("owner");
        }
        Boolean yes = false;
        yes = true;

        if (superior != null || id != null) {
            if (yes) {
                try {
                    ServletContext sc = request.getServletContext();
                    Connection con = BASE.VER.getServletConnection(sc);
                    int ROWS = BASE.VER.getMaxRows1(sc);
                    int rows = ROWS + 1;
                    Statement stmt = con.createStatement();
                    String sql = "";
                    if (superior == null) {
                        sql = "SELECT superior FROM blog_cat WHERE id='" + id + "'";
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            superior = rs.getString("superior");
                        }
                    }
                    session.setAttribute("blog_car_cat", superior);
                    if (!superior.equals("0")) {
                        res += "<div class=\"up cat item\" id=\"0\">/</div>";
                        res += "<div class=\"up upper cat item\" id=\"" + superior + "\">../</div>";
                    }
                    res += "<table id='files'><tbody>";
                    if (offset.equals("0")) {
                        sql = "SELECT id, name  FROM blog_cat WHERE superior='" + superior + "' AND user_id='" + owner + "' ORDER BY name";
                        ResultSet rs1 = stmt.executeQuery(sql);

                        while (rs1.next()) {
                            res += "<tr class=\"item cat\" id=\"" + rs1.getInt("id") + "\"><td class='choose'></td><td class='icon'></td><td class='name'>" + rs1.getString("name") + "</td><td class='dop'></td><td class='created'></td></tr>";
                        }
                        rs1.close();
                    }
                    sql = "SELECT id, name, properTime(time) AS created, dopusk_type, is_blog_marked(id," + owner + ") AS marked FROM bloges WHERE blog_superior(id, owner)=" + superior + " AND owner=" + owner + " "
                            + " AND NOT is_blog_invis(id," + owner + ") ORDER BY time DESC LIMIT " + rows + " OFFSET " + offset;
                    ResultSet rs = stmt.executeQuery(sql);
                    int cc = 0;
                    while (rs.next()) {
                        if (rs.isLast() && cc == rows - 1) {
                            next = true;
                        } else {
                            String star = "";
                            cc++;
                            if (rs.getBoolean("marked")) {
                                star = " star";
                            }
                            res += "<tr class=\"item fil\" id=\"" + rs.getInt("id") + "\"><td class='choose'></td><td class='icon" + star + "'><span></span>"
                                    + "</td><td class='name'>" + rs.getString("name") + "</td><td class='dop'><img src='" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + "'></td>"
                                    + "<td class='created'> " + rs.getString("created") + "</td>"
                                    + "</tr>";
                        }
                    }
                    res += "</tbody></table>";
                    String navi = "<div id='menu-navi'><table><tr><td class='oneNavi'>";
                    int of = Integer.parseInt(offset);
                    if (of > 0) {
                        navi += "<a href='javascript:' class='exxo-shadow' id='backward'></a>";
                    }
                    navi += "</td><td></td><td class='threeNavi'>";
                    if (next) {
                        navi += "<a href='javascript:'  class='exxo-shadow' id='forward'></a>";
                    }
                    navi += "</td></tr></table></div>";
                    res += navi;
                    rs.close();
                    stmt.close();
                    con.close();
                } catch (SQLException e) {
                    res = e.getLocalizedMessage();
                }
                PrintWriter out = response.getWriter();
                try {
                    response.setContentType("text/html");
                    if (res.equals("")) {
                        res = "Данный пользователь еще не добавил ни одного файла или каталога!";
                    }
                    out.print(res);

                } finally {
                    out.close();
                }

            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
