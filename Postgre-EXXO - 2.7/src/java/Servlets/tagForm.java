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
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.util.Properties;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;


public class tagForm extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //       response.setContentType("application/octet-stream");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String owner = request.getParameter("owner");

        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        String name = "";
        String own = "";
        String res = "";

        String sql = "SELECT name, owner FROM tags WHERE tag_id='" + id + "' ";
        Statement stmt = con.createStatement();
        Statement stmt1 = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            name = rs.getString(1);
            own = rs.getString(2);
        }
        String list = "";

        String sql1 = "SELECT id, fio FROM employee ORDER BY fio";
        ResultSet rs1 = stmt1.executeQuery(sql1);
        String selected = "";

        while (rs1.next()) {
            selected = "";

            if (rs1.getString("id").equals(own)) {
                selected = "SELECTED";
            }
            list += "<option value='" + rs1.getString("id") + "' " + selected + ">" + rs1.getString("fio") + "</option>";
        }

        res += "<form id=\"FILECHANGE\" action=\"tagChange\" method=\"POST\">"
                + name
                + "<label for=\"owner\">Владелец</label>"
                + "<select name=\"owner\" >" + list + "</select><br><br>"
                + "<input type=\"hidden\" name=\"id\" value=\"" + id + "\" >";
        res += "<input type=\"submit\" value=\"Сохранить\"></form>";

        rs.close();
//    rs1.close();
        stmt.close();
        stmt1.close();

        out.println(res);
        out.close();
        con.close();

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
