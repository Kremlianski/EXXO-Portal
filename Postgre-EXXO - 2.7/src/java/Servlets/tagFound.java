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


public class tagFound extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //       response.setContentType("application/octet-stream");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String status = request.getParameter("status");
        String id = request.getParameter("id");
        //    if(id==null) id="0";
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        HttpSession session = request.getSession(true);
        String owner = (String) session.getAttribute("id");
        String res = "";

        //page_id, unit_id
        String sql = "INSERT INTO found_tags VALUES ('" + owner + "','" + id + "', '" + status + "')";
        Statement stmt = con.createStatement();
        try {
            if (id != null) {
                stmt.executeUpdate(sql);
            }
        } finally {
            sql = "SELECT tags.name,tags.tag_id,found_tags.status FROM found_tags, tags WHERE found_tags.owner = '" + owner + "' AND tags.tag_id=found_tags.tag_id"
                    + " ORDER BY tags.name";
            ResultSet rs = stmt.executeQuery(sql);
            String[] Ar = {"", "GREEN", "BLUE", "RED"};
            while (rs.next()) {
                res += "<div class='exxo-shadow1 tagsDiv " + Ar[rs.getInt(3)] + "' id='" + rs.getString(2) + "'>" + rs.getString(1) + "</div>";
            }

            rs.close();
        }
        stmt.close();

        con.close();
        if (res.equals("")) {
            res = "Пока нет ни одного тега для поиска.";
        }
        out.println(res);
        out.close();

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
