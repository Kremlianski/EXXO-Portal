package Servlets;

//import java.io.IOException;
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

public class voting extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String vote = request.getParameter("vote");
        String quest_id = request.getParameter("quest_id");
        String res = "OK";
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        try {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            if (vote != null) {
                String sql = "insert into answers values('" + quest_id + "','" + user + "', '" + vote + "')";
                stmt.executeUpdate(sql);
            }

            con.close();
            stmt.close();

        } catch (SQLException e) {
        }
        response.sendRedirect("votes.jsp");
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
