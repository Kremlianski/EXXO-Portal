package Servlets;

import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class innerDoc extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        if (owner == null || !owner.equals("-102")) {
            owner = "-103";
        }
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("id");
        String g_user = (String) session.getAttribute("global_id");
        Boolean yes = true;
        int nid = -10;
        yes = true;
        if (id != null) {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            int step = 0;
            String seqs = "SELECT nextval('files_seq')";

            ResultSet rs = stmt.executeQuery(seqs);
            if (rs.next()) {
                nid = rs.getInt(1);
            }

            String sql1 = "SELECT owner, name, file,superior,type,longname,tags,descr,fname,dopusk_type, step, isHead(owner) AS isHead FROM files WHERE id=" + id + " AND owner=" + user;
            rs = stmt.executeQuery(sql1);

            if (rs.next()) {
                step = rs.getInt("step");
                if (step == 6 && owner.equals("-103")) {
                    step = 7;
                } else if (step == 6 && owner.equals("-102")) {
                    step = 8;
                } else if (step >= 2 && step < 5 && owner.equals("-102")) {
                    step = 9;
                } else if (step >= 2 && step < 5 && owner.equals("-103") && rs.getBoolean("isHead")) {
                    step = 10;
                }

                String sql = "INSERT INTO files (owner, name, file,superior,type,longname,tags,descr,fname, dopusk_type, step, copy, id, dopusk, author)"
                        + " VALUES ('" + owner + "', '" + rs.getString("name") + "', '" + rs.getString("file") + "', '1', '" + rs.getString("type") + "', "
                        + " '" + rs.getString("longname") + "', '" + rs.getString("tags") + "', '"
                        + rs.getString("descr") + "', '" + rs.getString("fname") + "', 4,'" + step + "', " + id + ", " + nid + ", '{" + g_user + "}' , '" + user + "')";
                if (nid > 0 && step != 0) {
                    stmt.executeUpdate(sql);
                }

                sql = "UPDATE files SET step=" + step + " WHERE copy=" + id;
                if (step != 0) {
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
