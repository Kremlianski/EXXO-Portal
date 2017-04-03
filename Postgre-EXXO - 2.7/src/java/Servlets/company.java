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



public class company extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("uid");
//             Vector comp = new <Vector> Vector();
        int count;
        Stack<Vector> st = new Stack<Vector>();

        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "SELECT unit_id, unit, superior FROM units";
            ResultSet rs = stmt.executeQuery(sql);

//     Stack<Vector> st = new Stack<Vector>();
            count = 0;
            String marker = null;
            while (rs.next()) {
                Vector<String> v = new Vector<String>();

                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));

                st.push(v);

                if ((rs.getString(3) == null && id == null) || (id != null && rs.getString(1).equals(id))) {
                    marker = rs.getString(1);
                    count = 1;
                    String title = "<div class=\"title" + count + " item\" id=\"" + rs.getString(1) + "\">";
                    String titleEnd = "</div>";
                    out.print(title + rs.getString(2) + titleEnd);
                }

            }
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

                        if (v.get(2) != null) {
                            if (v.get(2).equals(marker)) {
                                String title = "<div class=\"title" + count + " item\" id=\"" + v.get(0) + "\">";
                                String titleEnd = "</div>";
                                out.print(title + v.get(1) + titleEnd);
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
            st.clear();
            stmt.close();
            con.close();
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
