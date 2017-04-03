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
//import java.util.Properties;
import java.io.*;
//import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

public class logoIns extends HttpServlet {

    DiskFileItemFactory factory = null;

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
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        byte bytes[] = null;
        String format = null;
        String type = null;
        String fname = null;
        long length = 0;
        String uri = "";

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

                }//else если это обычное текстовое поле, а не файл
            } // завершили обработку запроса с помощью mutipart/form-data.
        }

        if (role.indexOf("a") >= 0 || role.indexOf("b") >= 0) {
            yes = true;
        }
        if (yes) {

            if (bytes != null && (bytes.length > 10485760 || bytes.length <= 10)) {
                bytes = null;
                uri = "?message=bigSize";
            }
            if (bytes != null) {
                format = EXXOlib.IMGESLIB.getFormat(bytes, type);
                if (format.equals("noresult")) {
                    throw new imgFormatException("Формат файла не поддерживается");
                }

                bytes = EXXOlib.IMGESLIB.resizeLogo(bytes, format);
                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);
                Statement stmt = con.createStatement();

                String UPDATE_PICTURE = "UPDATE logo SET img=?";
                String test = "SELECT * FROM logo";

                ResultSet rs = stmt.executeQuery(test);
                String INSERT = "INSERT INTO logo (img) VALUES (?)";
                PreparedStatement ps = null;
                try {

                    con.setAutoCommit(false);
                    if (rs.next()) {
                        ps = con.prepareStatement(UPDATE_PICTURE);
                    } else {
                        ps = con.prepareStatement(INSERT);
                    }
                    ps.setBytes(1, bytes);

                    ps.executeUpdate();
                    con.commit();

                } catch (SQLException e) {
                    res = e.getLocalizedMessage();
                } finally {
                    ps.close();
                }
                con.close();
            }

            out.println(res);
            response.sendRedirect("logoIns.jsp" + uri);
            out.close();

        } else {
            response.sendRedirect("notPermited.html");
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
            Logger.getLogger(logoIns.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=sqlError");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(logoIns.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=unknownError");
        } catch (FileUploadException ex) {
            Logger.getLogger(logoIns.class.getName()).log(Level.SEVERE, null, ex);
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
