package Servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class fileLoader extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //       response.setContentType("application/octet-stream");

        //   PrintWriter out = response.getWriter();
        OutputStream os = response.getOutputStream();
        String id = request.getParameter("id");
        String s = request.getParameter("s");
        String idd = request.getParameter("i");
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String global_user = (String) session.getAttribute("global_id");
        String user = (String) session.getAttribute("id");
        String fname = "";
        int store = 0;

        //     String filename=request.getParameter("filename");
        boolean yes = false;
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);

        String type = "APPLICATION/OCTET-STREAM";
        String str = "ver_id=-1 AND files.id=-1";
        if (id != null && idd != null) {
            str = "ver_id=" + id + " AND files.id=" + idd;
        } else if (idd != null) {
            str = "files.id=" + idd;
        }
        String ver = "-1";

        String sql = "SELECT owner, type, dopusk_type, dopusk, fname, ver_id, store, status FROM files, files_vers WHERE " + str + " AND  files.copy=files_vers.id  ORDER "
                + "BY ver_id DESC LIMIT 1";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        int dopusk_type = 0;
        int status = 0;
        ResultSet dopusk = null;
        if (resultSet.next()) {
            ver = resultSet.getString("ver_id");
            store = resultSet.getInt("store");
            dopusk_type = resultSet.getInt(3);
            String owner = resultSet.getString(1);
            java.sql.Array r = resultSet.getArray(4);
            status = resultSet.getInt("status");
            if (r != null) {
                dopusk = r.getResultSet();
            }
            if (user.equals(owner)) {
                yes = true;
            } else if (role.indexOf("s") >= 0 || role.indexOf("e") >= 0 || (role.indexOf("o") >= 0 && owner.equals("-100")) || (role.indexOf("p") >= 0 && owner.equals("-101"))
                    || (role.indexOf("q") >= 0 && owner.equals("-102")) || (role.indexOf("r") >= 0 && owner.equals("-103"))) {
                yes = true;
            } else if (dopusk_type == 0) {
                yes = true;
            } else if (dopusk_type == 1 && dopusk != null && dopusk.next()) {
                Statement stmt1 = con.createStatement();
                sql = "SELECT groups.project FROM groups, wgroup WHERE wgroup.id='" + global_user + "' AND groups.id = wgroup.group_id";
                ResultSet rs = stmt1.executeQuery(sql);
                while (rs.next()) {
                    if (rs.getInt(1) == dopusk.getInt(2)) {
                        yes = true;
                    }
                }
                rs.close();
                stmt1.close();
            } else if (dopusk_type == 2 && dopusk != null && dopusk.next()) {
                Statement stmt1 = con.createStatement();
                sql = "SELECT group_id FROM wgroup WHERE wgroup.id='" + global_user + "'";
                ResultSet rs = stmt1.executeQuery(sql);
                while (rs.next()) {
                    if (rs.getInt(1) == dopusk.getInt(2)) {
                        yes = true;
                    }
                }
                rs.close();
                stmt1.close();

            } else if (dopusk_type == 3 && dopusk != null && dopusk.next()) {
                Statement stmt1 = con.createStatement();
                sql = "SELECT inStructure((SELECT unit FROM employee WHERE id='" + user + "')," + dopusk.getInt(2) + ")";
                ResultSet rs = stmt1.executeQuery(sql);
                if (rs.next()) {
                    yes = rs.getBoolean(1);
                }
                rs.close();
                stmt1.close();

            } else if (dopusk_type == 4 && dopusk != null) {
                while (dopusk.next()) {
                    if (dopusk.getString(2).equals(global_user)) {
                        yes = true;
                    }
                }
            }

            if (yes && status == 2 && role.indexOf("s") < 0 && role.indexOf("e") < 0) {
                yes = false;
            }

            fname = resultSet.getString("fname");
            type = resultSet.getString("type");
            if (yes && !user.equals("0")) {
                sql = "SELECT file_ver_reg(" + ver + "," + user + ")";
                stmt.execute(sql);
            }
        }
        resultSet.close();
        stmt.close();
        con.close();

        if (yes) {
            int length;
            Connection con1 = BASE.VER.getDocsConnection(sc, store);
            if (con1 != null) {

                String sql1 = "SELECT bin, length(bin) FROM file_store WHERE id=" + ver;
                Statement stmt2 = con1.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql1);
                if (rs2.next()) {
                    length = rs2.getInt(2);
                    response.setContentType(type);
                    response.setHeader("Content-Length", length + "");
                    if (s == null) {
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + EXXOlib.textLib.translitText(fname) + "\"");
                    } else {
                        response.setHeader("Content-Disposition", "inline; filename=\"" + EXXOlib.textLib.translitText(fname) + "\"");
                    }
                    response.addHeader("Cache-Control", "must-revalidate");
                    //InputStream is = new BufferedInputStream(rs2.getBinaryStream(1));
                    InputStream is = rs2.getBinaryStream(1);
                    int i;
                    while ((i = is.read()) != -1) {
                        os.write(i);
                    }
                    is.close();

                }
                rs2.close();
                stmt2.close();
                con1.close();

//    con1.setAutoCommit(false);
//    LargeObjectManager lobj = ((org.postgresql.PGConnection)con1).getLargeObjectAPI();    
//    LargeObject obj = lobj.open((long)181622, LargeObjectManager.READ);   
//    length = obj.size();
//    response.setContentType(type);
//    response.setHeader("Content-Length", length+"");
//    if(s==null) response.setHeader("Content-Disposition", "attachment; filename=\""+EXXOlib.textLib.translitText(fname)+"\"");
//    else response.setHeader("Content-Disposition", "inline; filename=\""+EXXOlib.textLib.translitText(fname)+"\"");
//    response.addHeader("Cache-Control", "must-revalidate");
//    InputStream is = new BufferedInputStream(obj.getInputStream());
//    int i;
//    while((i=is.read())!=-1) os.write(i);
//    is.close();
//    obj.close();
//    con1.commit();
//    con1.close();
            } else {
                response.sendRedirect("alert.xhtml?p=noconnection");
            }

        }

        //           
        if (!yes) {
            response.sendRedirect("notPermited.html");
        }

        os.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(fileLoader.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=noconnection");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(fileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(fileLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(fileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
