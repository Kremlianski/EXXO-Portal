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

public class docClassic extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String superior = request.getParameter("superior");
        String id = request.getParameter("id");
        String res = "";

        String t = null;
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = (String) session.getAttribute("id");
        String o = (String) session.getAttribute("id");
        String g = (String) session.getAttribute("global_id");
        if (request.getParameter("owner") != null) {
            owner = request.getParameter("owner");
        }
        Boolean yes = false;
        yes = true;
        if (superior != null || id != null) {
            //       if(role.indexOf("a")>=0||role.indexOf("c")>=0||role.indexOf("Z")>=0) yes=true;
            boolean isSecurity = false;
            if (role.indexOf("e") >= 0 || role.indexOf("s") >= 0) {
                isSecurity = true;
            }
            if (owner != null && ((role.indexOf("o") >= 0 && owner.equals("-100")) || (role.indexOf("p") >= 0 && owner.equals("-101"))
                    || (role.indexOf("q") >= 0 && owner.equals("-102")) || (role.indexOf("r") >= 0 && owner.equals("-103")))) {
                isSecurity = true;
            }

            if (yes) {
                try {
                    ServletContext sc = request.getServletContext();
                    Connection con = BASE.VER.getServletConnection(sc);
                    Statement stmt = con.createStatement();
                    String sql = "";

                    if (superior == null) {
                        sql = "SELECT superior FROM files WHERE id='" + id + "'";
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            superior = rs.getString("superior");
                        }
                    }
                    String sname = "";
                    String root = "/";
                    sql = "SELECT name FROM files WHERE id='" + superior + "'";
                    ResultSet rs1 = stmt.executeQuery(sql);
                    if (rs1.next()) {
                        sname = rs1.getString(1);
                    }
                    sql = "SELECT get_fio(" + owner + ")";
                    rs1 = stmt.executeQuery(sql);
                    if (rs1.next()) {
                        root = rs1.getString(1);
                    }
                    if (owner.equals("-100")) {
                        root += "/Общие";
                    } else if (owner.equals("-101")) {
                        root += "/Входящие";
                    } else if (owner.equals("-102")) {
                        root += "/Исходящие";
                    } else if (owner.equals("-103")) {
                        root += "/Внутренние";
                    } else if (owner.equals(o)) {
                        root = "Мои документы";
                    } else {
                        root += " / Документы";
                    }
                    if (!superior.equals("1")) {
                        res += "<div class=\"up cat item\" id=\"1\"><span class='aui-icon aui-icon-home'></span>" + root + "</div>";
                        res += "<div class=\"up upper cat item\" id=\"" + superior + "\"><span class='aui-icon aui-icon-circle-arrow-t'></span>" + sname + "</div>";
                    }

                    sql = "SELECT id, name, file, fname, type, properTime(created) AS created, id = copy AS isCopy, step, dopusk_type, "
                            + "(isBlogPermitted (dopusk_type, dopusk, " + g + "::Int) OR '" + isSecurity + "' OR owner=" + o + ") AS permitted FROM files WHERE superior='" + superior + "' AND owner='" + owner + "' ORDER BY file,name";

                    ResultSet rs = stmt.executeQuery(sql);
                    res += "<table id='files'><tbody>";
                    while (rs.next()) {

                        if (rs.getString("file").equals("0")) {
                            res += "<tr class=\"item cat\" id=\"" + rs.getInt("id") + "\"><td class='choose'></td><td class='icon'></td><td class='name'>" + rs.getString("name") + "</td><td class='fname'></td><td class='dop'></td><td class='created'></td></tr>";
                        } else {
                            String step = "";
                            if (rs.getInt("step") >= 6) {
                                step = " s_6";
                            }
                            t = rs.getString("type");

                            String type = EXXOlib.DOCTYPES.getType(t, rs.getString("fname"));
                            String copy = "";
                            String copyStyle = "";
                            if (!rs.getBoolean("isCopy")) {
                                copy = " (копия)";
                                copyStyle = " copy";
                            }

                            if (rs.getBoolean("permitted")) {
                                res += "<tr class=\"item fil " + type + copyStyle + step + "\" id=\"" + rs.getInt("id") + "\"><td class='choose'></td><td class='icon'></td><td class='name'>" + rs.getString("name") + copy + "</td><td class='fname'> " + rs.getString("fname") + "</td><td class='dop'><img src='" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + "'></td><td class='created'> " + rs.getString("created") + "</td></tr>";
                            }
                        }
                    }
                    res += "</tbody></table>";

                    rs.close();
                    rs1.close();
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
