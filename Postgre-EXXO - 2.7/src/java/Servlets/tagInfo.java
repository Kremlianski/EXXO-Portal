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


public class tagInfo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //       response.setContentType("application/octet-stream");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String serv = request.getParameter("s");
        String element = request.getParameter("element");
        //    if(id==null) id="0";
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = (String) session.getAttribute("id");
        String res = "";

        //page_id, unit_id
        boolean yes = false;
        if (role.indexOf("k") >= 0 || role.indexOf("l") >= 0) {
            yes = true;
        }

        String sql = "SELECT insert_tag((SELECT global_id FROM " + serv + " WHERE id='" + element + "')::int,'" + id + "')";
        Statement stmt = con.createStatement();
        try {
            if (id != null && (yes || BEANS.tagMaster.permitted(id, owner, request) || BEANS.tagMaster.cleverTest(owner, serv, element, request))) {
                stmt.execute(sql);
            }
        } finally {
            sql = "SELECT tags.name,tags.tag_id FROM global_tags, tags WHERE global_id = (SELECT global_id FROM " + serv + " WHERE id=" + element + ") AND tags.tag_id=global_tags.tag_id"
                    + " ORDER BY tags.name";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                res += "<div class='tagsDiv exxo-shadow1' id='" + rs.getString(2) + "'>" + rs.getString(1) + "</div>";
            }

            rs.close();
        }
        stmt.close();

        con.close();
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
