package Servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


public class galClassic extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String superior = request.getParameter("superior");
        String id = request.getParameter("id");
        String res = "";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = (String) session.getAttribute("id");
        String own = "";
        if (request.getParameter("owner") != null) {
            owner = request.getParameter("owner");
            if (owner.equals("0")) {
                owner = "-100";
            }
            own = "&owner=" + owner;
        }
        Boolean yes = false;
        yes = true;
        if (superior != null || id != null) {
            //       if(role.indexOf("a")>=0||role.indexOf("c")>=0||role.indexOf("Z")>=0) yes=true;
            if (yes) {
                try {
                    ServletContext sc = request.getServletContext();
                    Connection con = BASE.VER.getServletConnection(sc);
                    Statement stmt = con.createStatement();
                    String sql = "";
                    String sname = "";

                    if (superior == null) {
                        sql = "SELECT superior FROM gallaries WHERE id='" + id + "'";
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            superior = rs.getString("superior");
                        }
                        rs.close();
                    } else {
                        sql = "SELECT name FROM gallaries WHERE id=" + superior;
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            sname = rs.getString(1);
                        }
                        sname = sname.replace("'", "\"");
                        rs.close();
                    }
                    String navi = "";
                    String offset = "0";
                    if (request.getParameter("of") != null) {
                        offset = request.getParameter("of");
                    }
                    boolean next = false;
                    int ROWS = BASE.VER.getMaxBloks(sc);
                    int rows = ROWS + 1;

                    if (!superior.equals("1")) {
                        res += "<div class=\"up cat1 item\" id='" + sname + "'>НАВЕРХ</div>";
                        sql = "SELECT id, name, file, fname FROM gallaries WHERE superior='" + superior + "' AND owner='" + owner + "' AND file=1 ORDER BY name LIMIT " + rows + " OFFSET " + offset;
                    } else {
                        sql = "SELECT id, name, file, fname FROM gallaries WHERE superior='" + superior + "' AND owner='" + owner + "' AND file=0 ORDER BY name LIMIT " + rows + " OFFSET " + offset;
                    }

                    ResultSet rs = stmt.executeQuery(sql);
                    int cc = 0;
                    while (rs.next()) {
                        if (rs.isLast() && cc == rows - 1) {
                            next = true;
                        } else {
                            if (rs.getString("file").equals("0")) {
                                res += "<div class=\"item cat\" id=\"" + rs.getInt("id") + "\"><div class=\"exxo-album-cover exxo-loading\" style=\"background-image:url('cover?id=" + rs.getInt("id") + "&t=" + System.currentTimeMillis() + "')\"></div><span>" + rs.getString("name") + "</span></div>";
                            } else {
                                res += "<div class=\"item fil\"><a  href=\"galLoader?id=" + rs.getInt("id") + "\" title=\"" + rs.getString("name") + "\""
                                        + " style=\"background-image: url('galLoader?id=" + rs.getString("id") + "&icon=1');\" class=\"exxo-album-img exxo-loading\"></a>"
                                        + "<div>"
                                        + "<span onClick=\"location='pic.jsp?id=" + rs.getString("id") + "'\"  class='picName'>" + rs.getString("name") + "</span></div></div>";
                            }
                            cc++;
                        }
                    }
                    rs.close();
                    stmt.close();
                    con.close();
                    navi += "<div id='menu-navi'><table><tr><td class='oneNavi'>";
                    int of = Integer.parseInt(offset);
                    if (of >= ROWS) {
                        navi += "<span class='exxo-shadow' id='backward'></span>";
                    }
                    navi += "</td><td></td><td class='threeNavi'>";
                    if (next) {
                        navi += "<span  class='exxo-shadow' id='forward'></span>";
                    }
                    navi += "</td></tr></table></div>";
                    res += navi;

                } catch (SQLException e) {
                    res = e.getLocalizedMessage();
                }
                PrintWriter out = response.getWriter();
                try {
                    response.setContentType("text/html");
                    if (res.equals("")) {
                        res = "Данный пользователь еще не добавил ни одного изображения или галереи!";
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
