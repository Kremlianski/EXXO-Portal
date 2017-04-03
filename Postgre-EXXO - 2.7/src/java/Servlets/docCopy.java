package Servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


public class docCopy extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("id");
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        int nid = -10;
        if (id != null) {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();

            String seqs = "SELECT nextval('files_seq')";

            ResultSet rs = stmt.executeQuery(seqs);
            if (rs.next()) {
                nid = rs.getInt(1);
            }

            String sql1 = "SELECT owner, name, file,superior,type,longname,tags,descr,fname,dopusk_type, step FROM files WHERE id=" + id;
            rs = stmt.executeQuery(sql1);

            if (rs.next()) {

                String owner = rs.getString("owner");
                if (owner.equals(user) || (role.indexOf("o") >= 0 && owner.equals("-100")) || (role.indexOf("p") >= 0 && owner.equals("-101"))
                        || (role.indexOf("q") >= 0 && owner.equals("-102")) || (role.indexOf("r") >= 0 && owner.equals("-103"))) {
                    yes = true;
                }
                if (owner == null) {
                    owner = (String) session.getAttribute("id");
                }
                String sql = "INSERT INTO files (owner, name, file,superior,type,longname,tags,descr,fname, dopusk_type, step, copy, id)"
                        + " VALUES ('" + rs.getString("owner") + "', '" + rs.getString("name") + "', '" + rs.getString("file") + "', '"
                        + rs.getString("superior") + "', '" + rs.getString("type") + "', '" + rs.getString("longname") + "', '" + rs.getString("tags") + "', '"
                        + rs.getString("descr") + "', '" + rs.getString("fname") + "', 4,'" + rs.getString("step") + "', "
                        + id + ", " + nid + " )";
                if (nid > 0 && yes) {
                    stmt.executeUpdate(sql);
                }

            }
            rs.close();
            con.close();
            stmt.close();

        } else {
            response.sendRedirect("notPermited.html");
        }
        if (response != null && !response.isCommitted()) {
            response.sendRedirect("fileLoader.jsp?id=" + nid);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(docPublish.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(docPublish.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("notPermited.html");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
