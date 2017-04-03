package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
//import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class newBlog extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        String guser = (String) session.getAttribute("global_id");
        String list = "";
        int block = 4;
        int width = java.lang.Integer.parseInt(request.getParameter("width"));
        int font = java.lang.Integer.parseInt(request.getParameter("font"));
        String small = "exxo-width-normal";
        if (width <= 250) {
            small = "exxo-width-small";
        } else if (width > 400) {
            small = "exxo-width-big";
        }
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            int blocks = (int) java.lang.Math.ceil(BASE.VER.getVidsDocs(sc) / block);
            int limit = block * blocks;
            String string = "";
            String add = "";
            String href = "blogs.jsp";
            if (id.length() == 12) {
                if (id.charAt(8) == 'b') {
                    string += " AND bus='1' ";
                    add += " рабочие";
                }
                if (id.charAt(9) == 'I') {
                    string += " AND bloges.owner<>'" + user + "' ";
                    add += " входящие";
                    if (id.charAt(8) == 'b') {
                        href = "newBlogsB.jsp";
                    } else {
                        href = "newBlogs.jsp";
                    }
                } else if (id.charAt(9) == 'O') {
                    string += " AND bloges.owner='" + user + "' ";
                    add += " исходящие";
                }

                char dt = id.charAt(11);
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
            list = "<div  id='blogH3'><a href='" + href + "'>Сообщения" + add + "</a></div><div class=\"newBlogsContent " + small + "\"><ul>";
            String sql = "SELECT bloges.id, bloges.text, bloges.time, bloges.owner, employee.fio, bus, name, "
                    + "isBlogPermitted (dopusk_type, dopusk, " + guser + "::Int) "
                    + "AS permitted, dopusk_type, is_blog_opend(bloges.global_id, " + user + ") AS opend, properTime(bloges.time) AS create FROM bloges, employee WHERE employee.id=bloges.owner AND NOT bloges.blocked " + string + "  AND(isBlogPermitted (dopusk_type, dopusk, " + guser + "::Int) OR owner=" + user + ")  ORDER BY time DESC LIMIT " + limit;
            ResultSet rs = stmt.executeQuery(sql);
            int i = 1;
            while (rs.next()) {
                if (i == 1) {
                    list += "<li><table class='exxo-table'><tbody>";
                }
                String fio = rs.getString(5);
                String Fio = EXXOlib.textLib.shortFIO(fio);
                String docOwner = "<td class='fio'><div style=\"background-image:url(IMG?id=" + rs.getInt(4) + ")\" title='" + Fio + "'></div></td>";

                if (rs.getString(6).equals("0")) {
                    list += "<tr class='blog'>";
                } else {
                    list += "<tr class='blog BUS'>";
                }
                String opend = "";
                if (!rs.getBoolean("opend")) {
                    opend = "notopend";
                }
                if (width <= 250) {
                    list += "<td class='fil'><a href='blog.jsp?id=" + rs.getString(1) + "' class='" + opend + "'>" + rs.getString("name") + "</a>";
                    list += "</td><td class='first'>"
                            + "<div class='fiodiv' style='background-image: url(" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + ")'></div>"
                            + "</td></tr>";
                } else if (width < 400) {
                    list += docOwner + "<td class='fil'><a href='blog.jsp?id=" + rs.getString(1) + "' class='" + opend + "'><span class='exxo-date'>" + rs.getString("create") + "</span>" + rs.getString("name") + "</a>";
                    list += " </td><td class='first'>"
                            + "<div class='fiodiv' style='background-image: url(" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + ")'></div>"
                            + "</td></tr>";
                } else {
                    list += docOwner + "<td class='fil'><a href='blog.jsp?id=" + rs.getString(1) + "' class='" + opend + "'><span class='exxo-date'>" + rs.getString("create") + "</span>" + rs.getString("name") + "</a>";
                    list += " </td><td class='first'>"
                            + "<div class='fiodiv' style='background-image: url(" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + ")'></div>"
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
            list += "</ul></div>";

//     json="[\""+title+"\",\""+text+"\"]";
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
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
