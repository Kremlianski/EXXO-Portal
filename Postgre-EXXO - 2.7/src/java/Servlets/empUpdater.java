package Servlets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.Properties;
import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;

public class empUpdater extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String res = "ok";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Boolean trig = false;
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("Z") >= 0) {
            trig = true;
        }
        if (role.indexOf("a") >= 0 || role.indexOf("c") >= 0 || role.indexOf("Z") >= 0) {
            yes = true;
        }
        String workSince = request.getParameter("workSince");
        if (workSince.equals("")) {
            workSince = null;
        }
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);

        if (yes) {

            String UPDATE = "UPDATE  employee SET fio=?,unit=?::int,position=?,birthday=?::Date,head=?::smallint,education=?,"
                    + "tellocal=?,tellmob=?,email=?,hobby=?,comment=?,responsibility=? ,tel=?, room=?, office=?, workSince=?::Date WHERE id=?::int";

            //   FileInputStream fis = null;
            PreparedStatement ps = null;
            try {

                con.setAutoCommit(false);

                ps = con.prepareStatement(UPDATE);
                ps.setString(1, request.getParameter("fio"));
                ps.setString(2, request.getParameter("unit"));
                ps.setString(3, request.getParameter("position"));
                ps.setString(4, request.getParameter("birthday"));
                ps.setString(5, request.getParameter("head"));
                ps.setString(6, request.getParameter("education").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(7, request.getParameter("tellocal"));
                ps.setString(8, request.getParameter("tellmob"));
                ps.setString(9, request.getParameter("email"));
                ps.setString(10, request.getParameter("hobby").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(11, request.getParameter("comment").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(12, request.getParameter("responsibility").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(13, request.getParameter("tel"));
                ps.setString(14, request.getParameter("room"));
                ps.setString(15, request.getParameter("office"));
                ps.setString(16, workSince);
                ps.setString(17, id);
                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            } finally {

                ps.close();
            }

        }

        if (trig) {
            String UPDATE = "UPDATE  employee SET role=? WHERE id=?::int";
            PreparedStatement ps = null;
            try {

                con.setAutoCommit(false);
                String ROLE = "";
                if (request.getParameter("boss") != null) {
                    ROLE += "a";
                }
                if (request.getParameter("master") != null) {
                    ROLE += "b";
                }
                if (request.getParameter("kadry") != null) {
                    ROLE += "c";
                }
                if (request.getParameter("photo_editor") != null) {
                    ROLE += "d";
                }
                if (request.getParameter("files_security") != null) {
                    ROLE += "e";
                }
                if (request.getParameter("TR") != null) {
                    ROLE += "f";
                }
                if (request.getParameter("News") != null) {
                    ROLE += "g";
                }
                if (request.getParameter("BP") != null) {
                    ROLE += "h";
                }
                if (request.getParameter("BI") != null) {
                    ROLE += "i";
                }
                if (request.getParameter("vote") != null) {
                    ROLE += "j";
                }
                if (request.getParameter("tag") != null) {
                    ROLE += "k";
                }
                if (request.getParameter("tagEx") != null) {
                    ROLE += "l";
                }
                if (request.getParameter("unitSec") != null) {
                    ROLE += "m";
                }
                if (request.getParameter("projectMaster") != null) {
                    ROLE += "n";
                }
                if (request.getParameter("general_files_editor") != null) {
                    ROLE += "o";
                }
                if (request.getParameter("in_files_editor") != null) {
                    ROLE += "p";
                }
                if (request.getParameter("out_files_editor") != null) {
                    ROLE += "q";
                }
                if (request.getParameter("inner_files_editor") != null) {
                    ROLE += "r";
                }
                if (request.getParameter("security") != null) {
                    ROLE += "s";
                }
                if (request.getParameter("blog_security") != null) {
                    ROLE += "t";
                }
                ps = con.prepareStatement(UPDATE);
                ps.setString(1, ROLE);
                ps.setString(2, id);
                ps.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            } finally {
                ps.close();
            }
        }

        if (session.getAttribute("id").equals(id)) {
            String UPDATE = "UPDATE  employee SET comment=?, hobby=? WHERE id=?::int";
            PreparedStatement ps = null;
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(UPDATE);
                ps.setString(1, request.getParameter("comment").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(2, request.getParameter("hobby").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(3, id);
                ps.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            } finally {
                ps.close();
            }
        }

        out.println(res);

        con.close();
        response.sendRedirect("empUpd.jsp?id=" + id);
        out.close();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
