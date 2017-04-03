package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class add_newDoc extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        String guser = (String) session.getAttribute("global_id");
        String list = "";
        int width = java.lang.Integer.parseInt(request.getParameter("width"));
        int count = java.lang.Integer.parseInt(request.getParameter("count"));
        int font = java.lang.Integer.parseInt(request.getParameter("font"));
        int block = 4;

        String json;
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            int start = BASE.VER.getVidsDocs(sc) * count;
            int blocks = (int) java.lang.Math.ceil(BASE.VER.getVidsDocs(sc) / block);
            int limit = block * blocks;
            Statement stmt = con.createStatement();
            String string = "";
            String add = "";
            String href = "";
            if (id.length() == 11) {
                if (id.charAt(8) == '1') {
                    string = " AND files.owner=" + user + " ";
                    add += " мои";
                    href = "empDoc.jsp";
                } else if (id.charAt(8) == '2') {
                    string = " AND files.owner<>" + user + " AND files.owner>0 ";
                    add += " сотрудников";
                    href = "newDoc.jsp";
                } else if (id.charAt(8) == '3') {
                    string = " AND files.owner=-100 ";
                    add += " компании общие";
                    href = "newDocK.jsp?own=-100";
                } else if (id.charAt(8) == '4') {
                    string = " AND files.owner=-101 ";
                    add += " компании входящие";
                    href = "newDocK.jsp?own=-101";
                } else if (id.charAt(8) == '5') {
                    string = " AND files.owner=-102 ";
                    add += " компании исходящие";
                    href = "newDocK.jsp?own=-102";
                } else if (id.charAt(8) == '6') {
                    string = " AND files.owner=-103 ";
                    add += " компании внутренние";
                    href = "newDocK.jsp?own=-103";
                }

                char dt = id.charAt(10);
                if (dt == '2') {
                    string += " AND dopusk_type=0 ";
                } else if (dt == '3') {
                    string += " AND dopusk_type=4 ";
                } else if (dt == '4') {
                    string += " AND dopusk_type=2 ";
                } else if (dt == '5') {
                    string += " AND dopusk_type=1 ";
                } else if (dt == '6') {
                    string += " AND dopusk_type=3 ";
                } else if (dt == '7') {
                    string += " AND dopusk_type<>0 ";
                }
            }

            String sql = "WITH A AS (SELECT DISTINCT ON (files.id) files.id, files.owner, files.name, get_fio(files.owner), files.fname, files.descr, "
                    + " type, properTime(files_vers.created) AS create, files.superior, "
                    + " catalog_name(superior),dopusk_type,  ver_id, files_vers.created AS cr, is_doc_opend(ver_id, " + user + ") AS opend, status FROM files, files_vers WHERE file='1' "
                    + " AND  files.copy=files_vers.id  AND ((isBlogPermitted (dopusk_type, dopusk, " + guser + "::Int) AND (status=1 OR owner<0)) OR owner=" + user + ") " + string + " ORDER BY files.id DESC, files_vers.created DESC ) "
                    + "SELECT * FROM A ORDER BY cr DESC LIMIT " + limit + " "
                    + "OFFSET " + start;

            ResultSet rs = stmt.executeQuery(sql);
            int i = 1;
            while (rs.next()) {
                if (i == 1) {
                    list += "<li><table class='exxo-table'><tbody>";
                }
                String opend = "";
                String fio = rs.getString(4);
                if (!rs.getBoolean("opend")) {
                    opend = "notopend";
                }
                String Fio = EXXOlib.textLib.shortFIO(fio);
                if (rs.getInt(2) == -100) {
                    Fio = "Общие";
                } else if (rs.getInt(2) == -101) {
                    Fio = "Входящие";
                } else if (rs.getInt(2) == -102) {
                    Fio = "Исходящие";
                } else if (rs.getInt(2) == -103) {
                    Fio = "Внутренние";
                }
                String t = rs.getString("type");
                String type = EXXOlib.DOCTYPES.getType(t, rs.getString(5));
                String docOwner = "";
                if (!href.contains("newDocK.jsp")) {
                    docOwner = "<td class='fio'><div style=\"background-image:url(IMG?id=" + rs.getInt(2) + ")\"></div></td>";
                }

                if (width <= 250) {
                    list += "<tr class='docs'><td class='fil'><a href=\"fileLoader.jsp?id=" + rs.getString(1) + "\" class='" + opend + "'>" + rs.getString(3) + "</a>";
                    list += "</td><td class='first " + type + "T'>"
                            + "<div class='typediv'></div><div class='fiodiv' style='background-image: url(" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + ")'></div>"
                            + "</td></tr>";
                } else if (width < 400) {
                    list += "<tr class='docs'>" + docOwner + "<td class='fil'><a href=\"fileLoader.jsp?id=" + rs.getString(1) + "\" class='" + opend + "'><span class='exxo-date'>" + rs.getString("create") + "</span>" + rs.getString(3) + "</a>";
                    list += " </td><td class='first " + type + "T'>"
                            + "<div class='typediv'></div><div class='fiodiv' style='background-image: url(" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + ")'></div>"
                            + "</td></tr>";
                } else {
                    list += "<tr class='docs'>" + docOwner + "<td class='fil'><a href=\"fileLoader.jsp?id=" + rs.getString(1) + "\" class='" + opend + "'><span class='exxo-date'>" + rs.getString("create") + "</span>" + rs.getString(3) + "</a>";
                    list += " </td><td class='first " + type + "T'>"
                            + "<div class='typediv'></div><div class='fiodiv' style='background-image: url(" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + ")'></div>"
                            + "</td></tr>";
                }
                if (i == block) {
                    list += "</tbody></table></li>";
                    i = 0;
                }
                i++;
            }
            if (i != 1) {
                list += "</tbody></table></li>";
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            json = ex.getLocalizedMessage();
        }

        PrintWriter out = response.getWriter();
        try {

            out.println(list);

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(textOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(textOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(textOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(textOut.class.getName()).log(Level.SEVERE, null, ex);
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
