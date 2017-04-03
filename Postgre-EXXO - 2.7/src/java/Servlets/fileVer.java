package Servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

public class fileVer extends HttpServlet {

    private DiskFileItemFactory factory = null;

    @Override
    public void init(ServletConfig config) throws ServletException {

        factory = new DiskFileItemFactory();

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, FileUploadException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String res = "ok";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Properties prop = new Properties();
        byte bytes[] = null;
        byte icon[] = null;
        String type = null;
        String fname = null;
        long length = 0;
        int iconlength = 0;
        String format = "noresult";
        boolean notInFormat = false;
        boolean tooMoreFiles = false;
        boolean isStep_6 = false;
        ServletContext sc = request.getServletContext();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(BASE.VER.getMaxFileSize(sc));
            RequestContext rc = new ServletRequestContext(request);
            List items = upload.parseRequest(rc);
            Iterator iter = items.iterator();
            // получили список всех полей из html-формы
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // берем элемент формы и анализируем то, какого он типа
                if (!item.isFormField()) {
                    // если файл
                    // так мы получим значение оригинального имени файла
                    String filepath = item.getName();
                    int pos = filepath.lastIndexOf("\\");
                    if (pos != -1) {
                        fname = filepath.substring(pos + 1);
                    } else {
                        fname = filepath;
                    }
                    length = item.getSize();
                    // так мы определим его размер
                    // а так мы определим его content-type
                    type = item.getContentType();
                    //    if(!type.startsWith("image")) response.sendRedirect("alert.xhtml?p=notImage");

                    bytes = item.get();
                    // а теперь нужно что-то сделать с самим содержимым файла ....

                } else {
                    prop.setProperty(item.getFieldName(), item.getString("utf-8"));
                }
                //else если это обычное текстовое поле, а не файл
            } // завершили обработку запроса с помощью mutipart/form-data.
        }

        String owner = prop.getProperty("owner");
        Boolean yes = true;
        String own = "";
        if (owner != null) {
            own = "?owner=" + owner;
            if (role.indexOf("o") < 0 && owner.equals("-100")) {
                yes = false;
            } else if (role.indexOf("p") < 0 && owner.equals("-101")) {
                yes = false;
            } else if (role.indexOf("q") < 0 && owner.equals("-102")) {
                yes = false;
            } else if (role.indexOf("r") < 0 && owner.equals("-103")) {
                yes = false;
            }
            if (!owner.equals("-100") && !owner.equals("-101") && !owner.equals("-102") && !owner.equals("-103")) {
                yes = false;
            }
        } else {
            owner = (String) session.getAttribute("id");
        }
        if (yes) {
            if (bytes.length > BASE.VER.getMaxFileSize(sc) || bytes.length <= 10) {
                bytes = null;
                prop.setProperty("message", "bigSize");
            }
            if (bytes != null) {

                if (type.startsWith("image")) {
                    format = EXXOlib.IMGESLIB.getFormat(bytes, type);
                }
                if (!format.equals("noresult")) {
                    icon = EXXOlib.IMGESLIB.iconCropResize(bytes, 150, 150, format);
                }
                Connection con = BASE.VER.getServletConnection(sc);
                int store = BASE.VER.whereStore(sc);
                if (store == -1) {
                    store = 0;
                }
                Connection con1 = BASE.VER.getDocsConnection(sc, store);
                if (con1 == null) {
                    throw new SQLException("не задан параметр соединения с хранилищем документов");
                }
                con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                if (icon == null) {
                    iconlength = 0;
                } else {
                    iconlength = icon.length;
                }

                PreparedStatement ps = null;
                int files_ver_seq = 0;
                try {

                    con.setAutoCommit(false);
                    String seqs = "SELECT nextval('files_ver_seq')";
                    Statement stmt4 = con.createStatement();
                    ResultSet rs4 = stmt4.executeQuery(seqs);
                    if (rs4.next()) {

                        files_ver_seq = rs4.getInt(1);
                    }
                    rs4.close();
                    stmt4.close();
                    Statement stmt = con.createStatement();
                    String sql = "INSERT INTO files_vers (id, ver_id, size, ico, sizeico, store) VALUES (?::int, ?, ?, ?, ?::int,?)";
                    String sql1 = "SELECT files.type='" + type + "', vers, step, owner=" + owner + " AS isPermitted FROM files WHERE id=" + prop.getProperty("id");
                    String sql2 = "SELECT (SUM(size)-" + BASE.VER.getMaxFilesSize(sc) + "<=0) FROM files, files_vers WHERE file=1 AND files.id=files_vers.id AND owner=" + owner;
                    String sql3 = "SELECT * FROM drop_vers(?::Int, ?, ?)";
                    ps = con.prepareStatement(sql1);
                    ResultSet rs = ps.executeQuery();
                    boolean typeCheck = false;
                    boolean MaxCheck = true;
                    boolean verMay = false;
                    boolean isPermitted = false;
                    int step = 0;
                    if (rs.next()) {
                        typeCheck = rs.getBoolean(1);
                        typeCheck = true;//пока не решу, как с этим быть или не исправят баг в IE
                        verMay = rs.getBoolean(2);
                        step = rs.getInt(3);
                        isPermitted = rs.getBoolean("isPermitted");
                        if (step >= 2) {
                            verMay = false;
                        } else if (step >= 6) {
                            isStep_6 = true;
                        }
                    }
                    if (typeCheck && !owner.equals("-100") && !owner.equals("-101") && !owner.equals("-102") && !owner.equals("-103")) {
                        ps = con.prepareStatement(sql2);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            MaxCheck = rs.getBoolean(1);
                        }
                    }
                    rs.close();
                    if (typeCheck && MaxCheck && !isStep_6 && isPermitted) {
                        ps = con.prepareStatement(sql);
                        ps.setString(1, prop.getProperty("id"));
                        ps.setInt(3, bytes.length);
                        ps.setInt(2, files_ver_seq);
                        ps.setBytes(4, icon);
                        ps.setInt(5, iconlength);
                        ps.setInt(6, store);
                        ps.executeUpdate();
                        sql = "INSERT INTO file_store(id,bin) VALUES(?, ?);";
                        PreparedStatement ps1 = con1.prepareStatement(sql);
                        ps1.setBytes(2, bytes);
                        ps1.setInt(1, files_ver_seq);
                        ps1.executeUpdate();
                        ps1.close();
                        con1.close();

                        ps = con.prepareStatement(sql3);
                        ps.setString(1, prop.getProperty("id"));
                        ps.setBoolean(2, BASE.VER.getFilesVersions(sc));
                        if (owner.equals("-100") || !verMay) {
                            ps.setInt(3, 1);
                        } else {
                            ps.setInt(3, BASE.VER.getMaxFilesVersions(sc));
                        }
                        ResultSet rs1 = ps.executeQuery();
                        String sql4 = "";
                        String sql5 = "";
                        while (rs1.next()) {
                            sql4 = "DELETE FROM files_vers WHERE ver_id=" + rs1.getInt("ver_id");
                            stmt.executeUpdate(sql4);
                            try {
                                sql5 = "DELETE FROM file_store WHERE id=" + rs1.getInt("ver_id");
                                Connection con2 = BASE.VER.getDocsConnection(sc, rs1.getInt("store"));
                                Statement stmt1 = con2.createStatement();
                                stmt1.executeUpdate(sql5);
                                stmt1.close();
                                con2.close();
                            } catch (SQLException e) {
                                Statement stmt3 = con.createStatement();
                                Logger.getLogger(fileVer.class.getName()).log(Level.SEVERE, null, e);
                                sql3 = "INSERT INTO lost_vers VALUES (" + rs1.getInt("ver_id") + ", " + rs1.getInt("store") + ")";
                                stmt3.executeUpdate(sql3);
                                stmt3.close();
                            }
                        }
                        con.commit();
                    } else {
                        if (!typeCheck) {
                            notInFormat = true;
                        } else {
                            tooMoreFiles = true;
                        }
                    }
                } catch (SQLException e) {
                    res = e.getLocalizedMessage();
                    Logger.getLogger(fileVer.class.getName()).log(Level.SEVERE, null, e);
                } finally {
                    ps.close();
                    con.close();
                }
            } else {
                prop.remove("tempImged");
            }
            //  prop.list(out);
            out.println(res);
            String uri = "";
            /*
      Set keys=prop.keySet();
      Iterator it=keys.iterator();
      while(it.hasNext()){
          String next = (String) it.next();
          uri+=next+"="+prop.getProperty(next);
          if (it.hasNext()) uri+="&";
        }
             */
            //          if (prop.getProperty("tempImged")!=null){uri="?tempImged="+prop.getProperty("tempImged");
            //          if (prop.getProperty("message")!=null) uri="?message=bigSize";
//out.println(prop.size());

            //          }
            String redirect = "documentsMod.jsp" + own;
            if (prop.getProperty("r") != null) {
                redirect = "fileLoader.jsp?id=" + prop.getProperty("id");
            }
            if (notInFormat) {
                response.sendRedirect("alert.xhtml?p=notFileFormat");
            } else if (tooMoreFiles) {
                response.sendRedirect("alert.xhtml?p=tooMoreFiles");
            } else {
                response.sendRedirect(redirect);
            }
            out.close();

        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("notPermited.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(fileVer.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=sqlError");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(fileVer.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=unknownError");
        } catch (FileUploadException ex) {
            Logger.getLogger(fileVer.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=bigSizeF");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
