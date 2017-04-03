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


public class calCompareDrop extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("id");
        String r = request.getParameter("r");
        String date = request.getParameter("date");
        String ldate = "";
        if (date != null && !date.equals("")) {
            ldate = "?date=" + date;
        }
        //   String type=request.getParameter("type");
        String dopusk = request.getParameter("dopusk");
        String res = "";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = (String) session.getAttribute("id");
        Boolean yes = true;
        //      if(role.indexOf("a")>=0||role.indexOf("e")>=0||role.indexOf("Z")>=0) yes=true;
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "";
            con.setAutoCommit(false);
            sql = "DELETE FROM calCimpare WHERE owner='" + owner + "' AND id='" + dopusk + "'";
            stmt.executeUpdate(sql);
            con.commit();
            sql = "SELECT id, get_fio(id) FROM calCimpare WHERE owner = '" + owner + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                res += "<div class='emps' id='" + rs.getInt(1) + "'>" + EXXOlib.textLib.shortFIO(rs.getString(2)) + "</div>";
            }
            stmt.close();
            con.close();

        } catch (SQLException e) {
            res = e.getLocalizedMessage();
        }

        if (r != null && r.equals("1")) {
            response.sendRedirect("calendarCompare.xhtml" + ldate);
        } else {
            if (res.equals("")) {
                res = "<div id='emptylist'>Список пуст</div>";
            }
            PrintWriter out = response.getWriter();
            try {
                response.setContentType("text/plain");
                out.println(res);

            } finally {
                out.close();
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
