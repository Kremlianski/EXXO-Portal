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
import java.sql.Statement;
import java.sql.ResultSet;
//import java.util.Properties;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


public class docTags extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //       response.setContentType("application/octet-stream");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String element = request.getParameter("el");
        String serv = request.getParameter("s");
        //    if(id==null) id="0";
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        HttpSession session = request.getSession(true);
        String id = (String) session.getAttribute("id");

        String sql = "SELECT find_as_tags((SELECT global_id FROM " + serv + " WHERE id='" + element + "')::int,'" + id + "')";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        rs.close();

        stmt.close();

        con.close();
        response.sendRedirect("findTag.jsp?s=" + serv);

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
