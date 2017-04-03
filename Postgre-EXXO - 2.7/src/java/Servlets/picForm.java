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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.util.Properties;
import java.sql.SQLException;
import javax.servlet.ServletContext;


public class picForm extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //       response.setContentType("application/octet-stream");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String r = request.getParameter("r");

        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);

        String res = "";
        try {
            String sql = "SELECT name, descr, tags, notshow FROM gallaries WHERE id='" + id + "' ";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String D = "";
                if (!rs.getString(2).equals("")) {
                    D = rs.getString(2);
                }
                String T = "";
                if (!rs.getString(3).equals("")) {
                    T = rs.getString(3);
                }
                res = "<form id=\"FILECHANGE\" action=\"picChange\" method=\"POST\">"
                        + "Имя файла: " + rs.getString(1)
                        + "<label for=\"discription\">Описание</label>"
                        + "<textarea name=\"discription\" rows=\"5\" cols=\"22\" >" + D + "</textarea>"
                        + "<label for=\"tags\">Теги</label>"
                        + "<textarea name=\"tags\" rows=\"5\" cols=\"22\" >" + T + "</textarea><br><br>"
                        + "<input type=\"hidden\" name=\"id\" value=\"" + id + "\" >";
                if (rs.getInt(4) == 0) {
                    res += "<input type=\"checkbox\" value=\"1\" name=\"notshow\"><label class='notshow'>Не афишировать</label>";
                } else {
                    res += "<input type=\"checkbox\" value=\"1\" name=\"notshow\" checked><label class='notshow'>Не афишировать</label>";
                }
                if (owner != null) {
                    res += "<input type=\"hidden\" name=\"owner\" value=\"" + owner + "\" >";
                }
                if (r != null) {
                    res += "<input type=\"hidden\" name=\"r\" value=\"1\" >";
                }
                res += "<br><br><input type=\"submit\" value=\"Сохранить\"></form>";

            }
            rs.close();
            stmt.close();
        } finally {
            out.println(res);
            con.close();
            out.close();
        }
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
