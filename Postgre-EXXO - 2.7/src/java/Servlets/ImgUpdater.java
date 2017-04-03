package Servlets;

import EXXOlib.imgException;
import EXXOlib.imgFormatException;
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
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletInputStream;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

public class ImgUpdater extends HttpServlet {

    private DiskFileItemFactory factory = null;

    ;
    @Override
    public void init(ServletConfig config) throws ServletException {

        factory = new DiskFileItemFactory();

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, FileUploadException, imgException, imgFormatException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String res = "ok";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("c") >= 0 || role.indexOf("Z") >= 0) {
            yes = true;
        }

        if (yes) {
            Properties prop = new Properties();
            byte bytes[] = null;
            String type = "";
            long length = 0;
            String fname = null;
            String format = null;

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setFileSizeMax(10485760);
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
                        bytes = item.get();
                        // а теперь нужно что-то сделать с самим содержимым файла ....

                    } else {
                        prop.setProperty(item.getFieldName(), item.getString("utf-8"));
                    }
                    //else если это обычное текстовое поле, а не файл
                } // завершили обработку запроса с помощью mutipart/form-data.
            }

            if (bytes.length > 10485760 || bytes.length <= 10) {
                bytes = null;
                prop.setProperty("message", "bigSize");
            } else {
                if (!type.startsWith("image")) {
                    throw new imgException("Файл не является изображением");
                }
                format = EXXOlib.IMGESLIB.getFormat(bytes, type);
                if (format.equals("noresult")) {
                    throw new imgFormatException("Формат файла не поддерживается");
                }
                bytes = EXXOlib.IMGESLIB.cropResize(bytes, 110, 147, format);
            }
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);

            String UPDATE_PICTURE = "UPDATE employee SET photo=? WHERE id=?::int";

            //   FileInputStream fis = null;
            PreparedStatement ps = null;
            try {

                con.setAutoCommit(false);
//      File file = new File("/home/alqumisto/NetBeansProjects/EXXO/web/IMG_3012.JPG");
//      fis = new FileInputStream(file);
                ps = con.prepareStatement(UPDATE_PICTURE);
                ps.setString(2, id);
                ps.setBytes(1, bytes);
                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            } finally {
                ps.close();
                con.close();
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
            if (prop.getProperty("tempImged") != null) {
                uri = "?tempImged=" + prop.getProperty("tempImged");
                if (prop.getProperty("message") != null) {
                    uri = "?message=bigSize";
                }
//out.println(prop.size());

            }
        }
        response.sendRedirect("empUpd.jsp?id=" + id);
        out.close();

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
            Logger.getLogger(ImgUpdater.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=sqlError");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImgUpdater.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=unknownError");
        } catch (FileUploadException ex) {
            Logger.getLogger(ImgUpdater.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=bigSize");
        } catch (imgException ex) {
            response.sendRedirect("alert.xhtml?p=notImage");
        } catch (imgFormatException ex) {
            response.sendRedirect("alert.xhtml?p=notImageFormat");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
