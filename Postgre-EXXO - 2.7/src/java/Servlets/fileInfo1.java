package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class fileInfo1 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //       response.setContentType("application/octet-stream");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        if (id == null) {
            id = "0";
        }
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String res = "";
        String tags = " теги базы знаний не заданы";
        try {
            String fio = "Компания";
            String sql = "";
            sql = "SELECT tags.name,tags.tag_id FROM global_tags, tags WHERE global_id = (SELECT global_id FROM files WHERE id=" + id + ") AND tags.tag_id=global_tags.tag_id"
                    + " ORDER BY tags.name";
            ResultSet rs2 = stmt.executeQuery(sql);
            while (rs2.next()) {
                if (rs2.isFirst()) {
                    tags = "";
                }
                tags += "<a href='docTag?el=" + rs2.getString(2) + "&s=files'>" + rs2.getString(1) + "</a> ";
            }
            rs2.close();

            sql = "SELECT size, descr, tags, owner, sizeico, dopusk_type, copy, dopusk FROM files, files_vers WHERE files.id='" + id + "' AND files.copy=files_vers.id";

            ResultSet rs = stmt.executeQuery(sql);
            String too = "";
            if (rs.next()) {
                if (!rs.getString(4).equals("-100")) {
                    Statement stmt1 = con.createStatement();
                    String sql1 = "SELECT fio FROM employee WHERE id='" + rs.getString(4) + "' ";
                    ResultSet rs1 = stmt1.executeQuery(sql1);
                    if (rs1.next()) {
                        fio = rs1.getString(1);
                    }
                    rs1.close();
                    stmt1.close();
                }

                ///Допски
                String dopusk_type = rs.getString("dopusk_type");
                java.sql.Array reArray = rs.getArray("dopusk");
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

                res = "<div id='panel'><a href='fileLoader?i=" + id + "&t=" + System.currentTimeMillis() + "'>СОХРАНИТЬ</a><a href='fileLoader?i=" + id + "&s=1&t=" + System.currentTimeMillis() + "'>ОТКРЫТЬ</a></div>";
                res += too;
                res += "<br><b>Размер:</b> " + rs.getString(1) + " байт";
                String D = "";
                if (!rs.getString(2).equals("")) {
                    D = rs.getString(2);
                }
                res += "<div id='descrInfo'>" + D + "</div>";
                /*
    String T="нет";
    if(!rs.getString(3).equals("")) T=rs.getString(3);
    res+="<b>Тэги: </b><span id='tags'> "+T+"</span><br>";
                 */
                res += "<br><br></div>";
                if (rs.getInt(5) > 0) {
                    res += "<div><img src='iconLoader?id=" + id + "'></div>";
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
