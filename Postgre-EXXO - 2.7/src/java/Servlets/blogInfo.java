package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.util.Properties;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class blogInfo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //       response.setContentType("application/octet-stream");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String owner = (String) session.getAttribute("id");
        String id = request.getParameter("id");
        if (id == null) {
            id = "0";
        }
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String res = "";
        String global_id = "0";
        String tags = "";
        try {
            String fio = "Компания";
            String sql = "";
            sql = "SELECT tags.name,tags.tag_id FROM global_tags, tags WHERE global_id = (SELECT global_id FROM bloges WHERE id=" + id + ") AND tags.tag_id=global_tags.tag_id"
                    + " ORDER BY tags.name";
            ResultSet rs2 = stmt.executeQuery(sql);
            while (rs2.next()) {
                if (rs2.isFirst()) {
                    tags = "";
                }
                tags += "<a href='docTag?el=" + rs2.getString(2) + "&s=bloges'>" + rs2.getString(1) + "</a> ";
            }
            rs2.close();

            sql = "SELECT text, global_id, refBlog(ref) AS re, refDoc(refdoc, refVer) AS redoc, dopusk_type, dopusk FROM bloges WHERE id='" + id + "' AND owner=" + owner;
            String text = "";
            String redoc = "";
            String re = "";
            String too = "";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String dopusk_type = rs.getString("dopusk_type");
                java.sql.Array reArray = rs.getArray("dopusk");

                text = "<div id='cont'>" + rs.getString(1) + "</div>";
                global_id = rs.getString(2);
                redoc = rs.getString("redoc");
                re = rs.getString("re");

                ///Допски
                String emps = "";
                int def = 0;
                Statement stmt1 = con.createStatement();
                ResultSet dopusk;
                if (reArray != null) {
                    dopusk = reArray.getResultSet();
                } else {
                    dopusk = null;
                }
                if (dopusk != null) {
                    while (dopusk.next()) {
                        if (dopusk.isFirst()) {
                            def = dopusk.getInt(2);
                        }
                        if (dopusk_type.equals("4")) {
                            sql = "SELECT fio, id FROM employee WHERE global_id='" + dopusk.getInt(2) + "'";
                            ResultSet rr = stmt1.executeQuery(sql);
                            if (rr.next()) {
                                if (dopusk.isFirst()) {
                                    emps = "<span class='emps'>Адресовано сотрудникам:</span> ";
                                }
                                emps += " <a class='emps' href='empCard.jsp?id=" + rr.getString(2) + "'>" + EXXOlib.textLib.shortFIO(rr.getString(1)) + "</a>";
                            }
                            rr.close();
                        }
                    }
                    dopusk.close();
                }
                if (dopusk_type.equals("0")) {
                    emps = "<span class='emps'>Адресовано ВСЕМ</span>";
                } else if (dopusk_type.equals("1")) {
                    sql = "SELECT name FROM projects WHERE id=" + def;
                    ResultSet rs3 = stmt1.executeQuery(sql);
                    if (rs3.next()) {
                        emps = "<span class='emps'>Адресовано участникам проекта:</span> <a class='emps' href='groups.jsp?id=" + def + "'>" + rs3.getString(1) + "</a>";
                    }
                    rs3.close();
                } else if (dopusk_type.equals("2")) {
                    sql = "SELECT name FROM groups WHERE id=" + def;
                    ResultSet rs3 = stmt1.executeQuery(sql);
                    if (rs3.next()) {
                        emps = "<span class='emps'>Адресовано участникам рабочей группы:</span> <a class='emps' href='group.jsp?id=" + def + "'>" + rs3.getString(1) + "</a>";
                    }
                    rs3.close();
                } else if (dopusk_type.equals("3")) {
                    sql = "SELECT unit FROM units WHERE unit_id=" + def;
                    ResultSet rs3 = stmt1.executeQuery(sql);
                    if (rs3.next()) {
                        emps = "<span class='emps'>Адресовано работникам подразделения:</span><a class='emps' href='empListComp.jsp?uid=" + def + "'>" + rs3.getString(1) + "</a>";
                    }
                    rs3.close();
                }
                stmt1.close();
                too = emps;
                ///     

            }
            String ansCount = "";
            String count = "";
            String respcount = "";
            String commcount = "";
            sql = "SELECT count(*) FROM bloges WHERE ref='" + id + "' AND NOT blocked";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ansCount = rs.getString(1);
            }

            sql = "SELECT count(*) FROM registor WHERE global_id='" + global_id + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                count = rs.getString(1);
            }

            sql = "SELECT count(*) FROM respects WHERE global_id='" + global_id + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                respcount = rs.getString(1);
            }

            sql = "SELECT count(*) FROM blogesC WHERE bloges_id='" + id + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                commcount = rs.getString(1);
            }

            res += "<div id='too'>" + too + "</div>";
            res += "<div id='infobutt'>";
            res += "<span>Просмотров:</span>" + count + "<span>Одобрений:</span>" + respcount + "<a href='blog.jsp?id=" + id + "&answers=1'>Ответов:</a>" + ansCount + "<a href='blog.jsp?id=" + id + "&comments=1'>Комментариев:</a>" + commcount;

            res += redoc;
            res += re;
            res += "</div>";
            res += text;
            if (!tags.equals("")) {
                res += "<div id=\"tags\"><a href='docTags?el=" + id + "&&s=bloges'>ТЕГИ</a>: " + tags + " </div>";
            }
            sql = "SELECT name, type, fname, files.id AS id FROM files, docsBlog WHERE files.id=docsBlog.doc AND docsBlog.id=" + id;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.isFirst()) {
                    res += "<div id='input'>Вложения:</div>";
                    res += "<table class='exxo-table'>";
                }
                res += "<tr><td class='" + EXXOlib.DOCTYPES.getType(rs.getString("type"), rs.getString("fname")) + "'></td><td><a href='fileLoader.jsp?id=" + rs.getString("id") + "'>" + rs.getString("name") + "</a></td><td>" + rs.getString("fname") + "</td></tr>";
                if (rs.isLast()) {
                    res += "</table>";
                }
            }
            rs.close();
            stmt.close();

        } finally {
            con.close();
            out.println(res);
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ImgLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImgLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ImgLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImgLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
