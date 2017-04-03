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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.util.Properties;
import java.sql.SQLException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


public class cover extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //       response.setContentType("application/octet-stream");

        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String ico = request.getParameter("ico");
        //     String filename=request.getParameter("filename");

        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        HttpSession session = request.getSession(true);
        String owner = (String) session.getAttribute("id");
        boolean no = false;
        try {
            String sql = "";
            if (ico != null) {
                sql = "SELECT ico, owner, type, fname FROM gallaries WHERE superior='" + id + "' ORDER BY cover DESC LIMIT 1";
            } else {
                sql = "SELECT binsm, owner, type, fname FROM gallaries WHERE superior='" + id + "' ORDER BY cover DESC LIMIT 1";
            }
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                response.setContentType(resultSet.getString("type"));
                response.setHeader("Content-Disposition", "attachment; filename=\"" + resultSet.getString("fname") + "\"");
                InputStream is = resultSet.getBinaryStream(1);
                int i;
                while ((i = is.read()) != -1) {
                    out.write(i);
                }
                is.close();

            } else {
                no = true;
            }
            resultSet.close();
            stmt.close();
        } finally {
            con.close();
            if (no) {
                if (ico == null) {
                    response.sendRedirect("small/pictures64.png");
                } else {
                    response.sendRedirect("small/pictures32.png");
                }
            }
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
