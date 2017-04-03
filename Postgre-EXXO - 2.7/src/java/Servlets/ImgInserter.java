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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.io.*;
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

public class ImgInserter extends HttpServlet {

    private DiskFileItemFactory factory = null;

    @Override
    public void init(ServletConfig config) throws ServletException {

        factory = new DiskFileItemFactory();

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, FileUploadException, imgException, imgFormatException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String res = "ok";
        HttpSession session = request.getSession(true);
        String id = (String) session.getAttribute("id");
        Properties prop = new Properties();
        byte bytes[] = null;
        String format = null;
        String type = "";
        long length = 0;
        String fname = null;

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
                    if (length > 10 && !type.startsWith("image")) {
                        throw new imgException("Файл не является изображением");
                    }

                    bytes = item.get();
                    // а теперь нужно что-то сделать с самим содержимым файла ....

                } else {
                    prop.setProperty(item.getFieldName(), item.getString("utf-8"));
                }
                //else если это обычное текстовое поле, а не файл
            } // завершили обработку запроса с помощью mutipart/form-data.
        }

        if (prop.size() > 1 || bytes != null) {
            if (bytes.length > 10485760 || bytes.length <= 100) {
                bytes = null;
                prop.setProperty("message", "bigSize");
            } else {
                format = EXXOlib.IMGESLIB.getFormat(bytes, type);
                if (format.equals("noresult")) {
                    throw new imgFormatException("Формат файла не поддерживается");
                }
                bytes = EXXOlib.IMGESLIB.cropResize(bytes, 110, 147, format);
            }
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);

            String INSERT_PICTURE = "insert into employeeTMP (fio, photo,id,unit,position,birthday,head,education,tellocal,tellmob,email,hobby,"
                    + "comment,responsibility,supervizor,tel,room,office, workSince) "
                    + "values (?, ?, ?::int, ?::int, ?, ?::Date, ?::smallint, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::Date)";

            PreparedStatement ps = null;
            try {

                con.setAutoCommit(false);

                ps = con.prepareStatement(INSERT_PICTURE);
                ps.setString(3, id);
                ps.setBytes(2, bytes);
                ps.setString(1, prop.getProperty("fio"));
                ps.setString(4, prop.getProperty("unit"));
                ps.setString(5, prop.getProperty("position"));
                ps.setString(6, prop.getProperty("birthday"));
                ps.setString(7, prop.getProperty("head"));
                ps.setString(8, prop.getProperty("education"));
                ps.setString(9, prop.getProperty("tellocal"));
                ps.setString(10, prop.getProperty("tellmob"));
                ps.setString(11, prop.getProperty("email"));
                ps.setString(12, prop.getProperty("hobby"));
                ps.setString(13, prop.getProperty("comment"));
                ps.setString(14, prop.getProperty("responsibility"));
                ps.setString(15, prop.getProperty("supervizor"));
                ps.setString(16, prop.getProperty("tel"));
                ps.setString(17, prop.getProperty("room"));
                ps.setString(18, prop.getProperty("office"));
                ps.setString(19, prop.getProperty("workSince"));
                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
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
        if (prop.getProperty("tempImged") != null) {
            uri = "?tempImged=" + prop.getProperty("tempImged");
            if (prop.getProperty("message") != null) {
                uri = "?message=bigSize";
            }
//out.println(prop.size());

        }
        response.sendRedirect("empIns.jsp" + uri);
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
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=sqlError");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=unknownError");
        } catch (FileUploadException ex) {
            Logger.getLogger(ImgInserter.class.getName()).log(Level.SEVERE, null, ex);
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
    // This method returns a buffered image with the contents of an image

}
