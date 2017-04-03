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
import java.io.InputStream;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class iconLoader extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //       response.setContentType("application/octet-stream");

        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String v = request.getParameter("v");
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String global_user = (String) session.getAttribute("global_id");
        String user = (String) session.getAttribute("id");
        String vers = "";
        if (v != null) {
            vers = " AND ver_id=" + v + " ";
        }
        //     String filename=request.getParameter("filename");
        boolean yes = false;
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        try {
            // String sql = "SELECT ico, owner, type, dopusk_type, dopusk, fname FROM files, files_vers WHERE files.id='"+id+"' AND files.id=files_vers.id "
            //        + "ORDER BY ver_id DESC LIMIT 1";
            boolean permitted = false;
            boolean isOwner = false;
            String owner = "";
            String sql = "SELECT isBlogPermitted (dopusk_type, dopusk, " + global_user + "::Int), owner=" + user + " AS isOwner, owner FROM files WHERE id=" + id;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                permitted = rs.getBoolean(1);
                isOwner = rs.getBoolean(2);
                owner = rs.getString(3);
            }
            if (isOwner || permitted) {
                yes = true;
            } else if (role.indexOf("s") >= 0 || role.indexOf("e") >= 0 || (role.indexOf("o") >= 0 && owner.equals("-100")) || (role.indexOf("p") >= 0 && owner.equals("-101"))
                    || (role.indexOf("q") >= 0 && owner.equals("-102")) || (role.indexOf("r") >= 0 && owner.equals("-103"))) {
                yes = true;
            }
            rs.close();
            if (yes) {
                sql = "SELECT ico, owner, type, fname FROM files, files_vers WHERE files.id='" + id + "' AND files.copy=files_vers.id "
                        + vers + " ORDER BY ver_id DESC LIMIT 1";
                ResultSet resultSet = stmt.executeQuery(sql);
                if (resultSet.next()) {
                    response.setContentType(resultSet.getString("type"));
                    response.setHeader("Content-Disposition", "attachment; filename=\"small_" + resultSet.getString("fname") + "\"");
                    InputStream is = resultSet.getBinaryStream(1);
                    int i;
                    while ((i = is.read()) != -1) {
                        out.write(i);
                    }
                    is.close();
                }
                resultSet.close();
            }

            stmt.close();
        } finally {
            con.close();
            //           
        }
        if (!yes) {
            response.sendRedirect("notPermited.html");
        }
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
