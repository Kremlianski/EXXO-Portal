package Servlets;

import EXXOlib.imgFormatException;
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
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import ru.EXXO.Util.Imges;

public class addToGallary extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, imgFormatException {

        String idd = request.getParameter("id"); //файл
        String s = request.getParameter("s");
        String id = request.getParameter("i");//версия
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String global_user = (String) session.getAttribute("global_id");
        String user = (String) session.getAttribute("id");
        String gal = request.getParameter("gal");
        String fname = "";
        int store = 0;
        String res = "OK";
        long length = 0;
        String format = "png";
        byte icon[] = null;
        byte bytes[] = null;
        byte ico[] = null;
        String type = null;
        String discription = "";
        String tags = "";
        String longname = "";
        String superior = "";
        String name = "";
        String owner = null;
        String tempImged = null;
        String notshow = "0";
        String Re = "";
        boolean yes = false;
        int width = 0;
        int height = 0;
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        String str = "ver_id=-1 AND files.id=-1";
        if (id != null && idd != null) {
            str = "ver_id=" + id + " AND files.id=" + idd;
        } else if (idd != null) {
            str = "files.id=" + idd;
        }
        String ver = "-1";
        String sql = "SELECT owner, type, dopusk_type, dopusk, fname, ver_id, store, status, name, tags, descr FROM files, files_vers WHERE " + str + " AND  files.copy=files_vers.id  ORDER "
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
            owner = resultSet.getString(1);
            name = resultSet.getString("name");
            discription = resultSet.getString("descr");
            tags = resultSet.getString("tags");
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
            if (owner.contains("-")) {
                owner = "-100";
            }
            if (yes) {
                sql = "SELECT owner FROM gallaries WHERE id= " + gal;
                resultSet = stmt.executeQuery(sql);
                if (resultSet.next()) {
                    yes = owner.equals(resultSet.getString(1));
                } else {
                    yes = false;
                }
            }
        }
        resultSet.close();
        stmt.close();

        if (yes) {
            Connection con1 = BASE.VER.getDocsConnection(sc, store);
            if (con1 != null) {
                String sql1 = "SELECT bin, length(bin) FROM file_store WHERE id=" + ver;
                Statement stmt2 = con1.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql1);
                if (rs2.next()) {
                    length = rs2.getInt(2);
                    bytes = rs2.getBytes(1);
                }
                rs2.close();
                stmt2.close();
                con1.close();
            } else {
                response.sendRedirect("alert.xhtml?p=noconnection");
            }

            if (name.equals("")) {
                name = fname;
            }
            if (bytes != null && (bytes.length > 100 && bytes.length < 10485760 && !name.equals(""))) {
                format = EXXOlib.IMGESLIB.getFormat(bytes, type);
                if (format.equals("noresult")) {
                    throw new imgFormatException("Формат файла не поддерживается");
                }
                Imges.Img img = new Imges().produceImg(bytes);
                bytes = img.getImg();
                icon = img.getSmall();
                ico = img.getIko();
                width = img.getWidth();
                height = img.getHeight();
                type = img.getType();

                String INSERT_FILE = "INSERT INTO gallaries (owner, name, file, superior, type,longname,tags,descr,bin,size, binsm, sizesm, fname, ico, sizeico, notshow, width, height)"
                        + " values (?::int, ?, ?::smallint, ?::int, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::smallint,?,?)";

                type = "image/png";
                fname = fname + ".png";
                PreparedStatement ps = null;
                try {

                    con.setAutoCommit(false);

                    ps = con.prepareStatement(INSERT_FILE);
                    ps.setInt(10, bytes.length);
                    ps.setBytes(9, bytes);
                    ps.setBytes(14, ico);
                    ps.setInt(12, icon.length);
                    ps.setInt(15, ico.length);
                    ps.setBytes(11, icon);
                    ps.setString(1, owner);
                    ps.setString(2, name);
                    ps.setString(3, "1");
                    ps.setString(4, gal);
                    ps.setString(5, type);
                    ps.setString(6, longname);
                    ps.setString(7, tags);
                    ps.setString(8, discription);
                    ps.setString(13, fname);
                    ps.setString(16, notshow);
                    ps.setInt(17, width);
                    ps.setInt(18, height);
                    ps.executeUpdate();
                    con.commit();

                } catch (SQLException e) {
                    res = e.getLocalizedMessage();
                } finally {
                    ps.close();
                    con.close();
                }

            }

            if (!yes) {
                response.sendRedirect("notPermited.html");
            }
        }
        response.sendRedirect("galClassic.jsp?owner=" + owner + "&id=" + gal);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (imgFormatException ex) {
            response.sendRedirect("alert.xhtml?p=notImageFormat");
        } catch (SQLException ex) {
            Logger.getLogger(fileLoader.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=sqlError");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(fileLoader.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=unknownError");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (imgFormatException ex) {
            response.sendRedirect("alert.xhtml?p=notImageFormat");
        } catch (SQLException ex) {
            Logger.getLogger(fileLoader.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=sqlError");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(fileLoader.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=unknownError");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
