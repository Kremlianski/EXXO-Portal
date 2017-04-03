package Servlets;

import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

public class addNew extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String menu = request.getParameter("menu");
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String user_id = (String) session.getAttribute("id");
        String u = "notPermited.html";
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("b") >= 0) {
            yes = true;
        }
        if (yes) {

            PrintWriter out = response.getWriter();
            if (title == null || title.equals("")) {

                try {
                    RequestDispatcher rd = request.getRequestDispatcher("addNew.jsp");
                    rd.include(request, response);
                } finally {
                    out.close();
                }
            } else {
                String res = "ok";
                try {

                    ServletContext sc = request.getServletContext();
                    Connection con = BASE.VER.getServletConnection(sc);
                    Statement stmt = con.createStatement();

                    String str;

                    if (type.equals("1")) {
                        str = "<table id=\"grid\" class=\"grid\"><tbody id=\"atbody\"> <tr id=\"tr\"> <td id=\"menu-td\" class=\"menu-td\" valign=\"top\"><div class=\"menu\" id=\"menu\"></div><div class=\"column\" id=\"undermenu\"></div></td><td id=\"column-3\" class=\"column\" valign=\"top\"></td><td id=\"column-4\" class=\"column\" valign=\"top\"></td></tr></tbody></table>";
                    } else {
                        str = "<table id=\"grid\" class=\"grid\"><tbody id=\"atbody\"> <tr id=\"tr\"> <td id=\"menu-td\" class=\"menu-td\" valign=\"top\"><div class=\"menu\" id=\"menu\"></div><div class=\"column\" id=\"undermenu\"></div></td><td id=\"column-1\" class=\"column\" valign=\"top\"></td><td id=\"column-2\" class=\"column\" valign=\"top\"></td></tr></tbody></table>";
                    }
                    String sql = "Insert into structure (user_id, structure,title) values('" + user_id + "','" + str + "','" + title + "')";

                    stmt.executeUpdate(sql);
                    sql = "select page_id from structure order by page_id desc limit 1";
                    ResultSet rs = stmt.executeQuery(sql);
                    String page_id = "2";
                    if (rs.next()) {
                        page_id = rs.getString("page_id");
                    }
                    rs.close();
                    stmt.close();
                    con.close();
                    String uri = "main.jsp?id=" + page_id;

                    if (menu.equals("1")) {
                        RequestDispatcher rd1 = request.getRequestDispatcher("addInMenu.jsp?id=" + page_id);
                        rd1.include(request, response);

                    } else {
                        response.sendRedirect(uri);
                    }

                } catch (SQLException e) {
                    res = e.getLocalizedMessage();
                }

            }
        } else {
            response.sendRedirect(u);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addNew.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addNew.class.getName()).log(Level.SEVERE, null, ex);
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
