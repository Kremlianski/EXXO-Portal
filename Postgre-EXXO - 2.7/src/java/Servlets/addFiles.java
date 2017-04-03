package Servlets;

import BEAN.AddFileBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
//import javax.servlet.ServletInputStream;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

public class addFiles extends HttpServlet {

    private DiskFileItemFactory factory = null;

    @Override
    public void init(ServletConfig config) throws ServletException {

        factory = new DiskFileItemFactory();

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, FileUploadException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String res = "<div class='check'></div>";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Properties prop = new Properties();
        byte bytes[] = null;
        byte icon[] = null;
        String type = null;
        String fname = null;
        long length = 0;
        int iconlength = 0;
        int files_seq = 0;
        int files_ver_seq = 0;
        String format = "noresult";
        ServletContext sc = request.getServletContext();
        AddFileBean bean = (AddFileBean) session.getAttribute("addFileBean");
        if (bean == null) {
            bean = new AddFileBean();
            session.setAttribute("addFileBean", bean);
        }
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
        Boolean yes = false;
        String own = "";
        String owns = "";
        if (owner != null) {
            own = "&owner=" + owner;
            owns = "?owner=" + owner;
        }
        if (owner == null || (role.indexOf("o") >= 0 && owner.equals("-100")) || (role.indexOf("p") >= 0 && owner.equals("-101"))
                || (role.indexOf("q") >= 0 && owner.equals("-102")) || (role.indexOf("r") >= 0 && owner.equals("-103"))) {
            yes = true;
        }
        if (owner == null) {
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

                int store = BASE.VER.whereStore(sc);
                if (store == -1) {
                    store = 0;
                }
                Connection con = BASE.VER.getServletConnection(sc);
                Connection con1 = BASE.VER.getDocsConnection(sc, store);
                if (con1 == null) {
                    throw new SQLException("не задан параметр соединения с хранилищем документов");
                }
                boolean trig = false;
                con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                if (icon == null) {
                    iconlength = 0;
                } else {
                    iconlength = icon.length;
                }
                if (con1 != null) {
                    String seqs = "SELECT nextval('files_seq'), nextval('files_ver_seq')";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(seqs);
                    if (rs.next()) {
                        files_seq = rs.getInt(1);
                        files_ver_seq = rs.getInt(2);
                    }
                    rs.close();
                    stmt.close();
                    String INSERT_FILE = "insert into files (owner, name, file,superior,type,longname,tags,descr,fname, dopusk_type, id, copy) values (?::int, ?, ?::smallint, ?::int, ?, ?, ?, ?, ?, ?::int,?,? )";
                    String dopusk = "4";
                    if (owner.equals("-100")) {
                        dopusk = "0";
                    }
                    if (prop.getProperty("dopusk") != null) {
                        dopusk = prop.getProperty("dopusk");
                    }

                    PreparedStatement ps = null;

                    con.setAutoCommit(false);
                    String name = "";
                    if (prop.containsKey("name")) {
                        name = prop.getProperty("name");
                    }
                    if (name.equals("")) {
                        name = fname;
                        name = (name.split("\\."))[0];
                    }
                    String discription = bean.getDescription();
                    if (!bean.getName().equals("")) {
                        name = bean.getName() + " " + name;
                    }
                    String tags = bean.getTags();

                    ps = con.prepareStatement(INSERT_FILE);
                    ps.setString(1, owner);
                    ps.setString(2, name);
                    ps.setString(3, "1");
                    ps.setString(4, prop.getProperty("superior"));
                    ps.setString(5, type);
                    ps.setString(6, "");
                    ps.setString(7, tags);
                    ps.setString(8, discription);
                    ps.setString(9, fname);
                    ps.setString(10, dopusk);
                    ps.setInt(11, files_seq);
                    ps.setInt(12, files_seq);
                    ps.executeUpdate();

                    String sql = "INSERT INTO files_vers (id, size, ico, sizeico, ver_id, store, status) VALUES (?, ?, ?, ?::int,?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setInt(2, bytes.length);
                    //   ps.setBytes(1, bytes);
                    ps.setBytes(3, icon);
                    ps.setInt(4, iconlength);
                    ps.setInt(1, files_seq);
                    ps.setInt(5, files_ver_seq);
                    ps.setInt(6, store);
                    if (prop.getProperty("public") != null) {
                        ps.setInt(7, 1);
                    } else {
                        ps.setInt(7, 0);
                    }
                    ps.executeUpdate();
                    con.commit();

                    sql = "INSERT INTO file_store(id,bin) VALUES(?, ?);";
                    PreparedStatement ps1 = con1.prepareStatement(sql);
                    ps1.setBytes(2, bytes);
                    ps1.setInt(1, files_ver_seq);
                    ps1.executeUpdate();
                    ps1.close();
                    con1.close();
                    ps.close();
                } else {
                    trig = true;
                }

                con.close();
                if (trig) {
                    response.sendRedirect("alert.xhtml?p=noconnection");
                }

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
            //   if(prop.getProperty("props")!=null)response.sendRedirect("fileProp.jsp?id="+files_seq);
            //   else if(prop.getProperty("r")!=null) response.sendRedirect("docClassic.jsp?id="+prop.getProperty("superior")+own);
            //   else response.sendRedirect("documentsMod.jsp"+owns);
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
            Logger.getLogger(addFiles.class.getName()).log(Level.SEVERE, null, ex);
            //          response.sendRedirect("alert.xhtml?p=sqlError"); 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addFiles.class.getName()).log(Level.SEVERE, null, ex);
            //          response.sendRedirect("alert.xhtml?p=unknownError"); 
        } catch (FileUploadException ex) {
            //          Logger.getLogger(addFiles.class.getName()).log(Level.SEVERE, null, ex);
            //           response.sendRedirect("alert.xhtml?p=bigSizeF");
            PrintWriter out = response.getWriter();
            out.println("<div class='error'></div>");
            out.close();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
