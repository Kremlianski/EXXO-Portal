/*
create table files (
 id int not null auto_increment,
 owner int not null,
 name varchar(250) not null,
 file int(1) default '0',
 superior int default null,
 primary key (id, owner),
 type varchar(250),
 longname varchar(100) null,
 tags text, descr text,
 bin longblob null ) auto_increment=2; <--УЧЕСТЬ!!!
 */
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
import java.util.Vector;
//import java.util.Properties;
import java.util.Stack;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class gallaries extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
//        String role=(String)session.getAttribute("role");
        String owner = (String) session.getAttribute("id");
        if (owner.equals("0")) {
            owner = "-100";
        }
        if (request.getParameter("owner") != null) {
            owner = request.getParameter("owner");
        }
        String fio = "АЛЬБОМЫ";
//             Vector comp = new <Vector> Vector();
        int count;
        Stack<Vector> st = new Stack<Vector>();

        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "SELECT id, name, file, superior, type, fname, notshow, cover FROM gallaries WHERE owner='" + owner + "' ORDER BY name DESC";
            ResultSet rs = stmt.executeQuery(sql);

//     Stack<Vector> st = new Stack<Vector>();
            count = 0;
            String marker = null;
            while (rs.next()) {
                Vector<String> v = new Vector<String>();

                v.add(rs.getString(1));
                v.add(rs.getString(2));
                if (rs.getString(3).equals("1")) {
                    v.add("fil");
                } else {
                    v.add("cat");
                }
                if (rs.getString(4) == null) {
                    v.add("1");
                } else {
                    v.add(rs.getString(4));
                }
                v.add(rs.getString(5));
                if (rs.getString(3).equals("1")) {
                    v.add(rs.getString(6));
                } else {
                    v.add("");
                }
                if (rs.getInt(7) == 1) {
                    v.add("notadv");
                } else {
                    v.add("");
                }
                if (rs.getBoolean("cover")) {
                    v.add(" cover");
                } else {
                    v.add("");
                }
                st.push(v);
            }
            Vector<String> v = new Vector<String>();
            v.add("1");
            v.add("ROOT");
            v.add("0");
            v.add(null);
            v.add("");
            v.add("");
            v.add("");
            v.add("");
            marker = "1";
            count = 1;
            String title = "<div class=\"item cat r\" id=\"1\">";
            String titleEnd = "</div>";
            out.print(title + fio + titleEnd);
////////////
            class recurs {

                private final Stack<Vector> st;

                recurs(Stack<Vector> st) {
                    this.st = st;
                }
                ;
   int count = 1;

                private void printUnit(PrintWriter out, String marker) throws SQLException, ClassNotFoundException {
                    count++;
                    Stack<Vector> st1 = new Stack<Vector>();
                    st1 = (Stack<Vector>) st.clone();

                    out.print("<div class=\"inno\">");

                    while (!st1.empty()) {
                        Vector<String> v = new Vector<String>((Vector<String>) st1.pop());

                        if (v.get(3) != null) {
                            if (v.get(3).equals(marker)) {
                                String center = v.get(1);
                                String style = "";
                                if (v.get(2).equals("fil")) {
                                    center += " <span class=\"filepath\">(" + v.get(5) + ")</span>";
                                    style = "style='background-image:url(\"galLoader?id=" + v.get(0) + "&ico=1\")'";
                                }
                                String title = "<div class=\"" + v.get(2) + " " + v.get(6) + " item" + v.get(7) + "\" id=\"" + v.get(0) + "\" " + style + ">";
                                String titleEnd = "</div>";
                                out.print(title + center + titleEnd);
                                printUnit(out, v.get(0));
                            }
                        }
                    }

                    out.print("</div>");
                    count--;
                }

            }
///////////
            recurs o = new recurs(st);
            o.printUnit(out, marker);
// out.close();
            rs.close();
            stmt.close();
            con.close();
            st.clear();

        } catch (ClassNotFoundException e) {
        }
// out.println(comp.get(0));

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
            Logger.getLogger(company.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(company.class.getName()).log(Level.SEVERE, null, ex);
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
