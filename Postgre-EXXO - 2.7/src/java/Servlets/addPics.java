package Servlets;

import BEAN.AddPicBean;
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
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
//import javax.servlet.ServletInputStream;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import EXXOlib.imgException;
import EXXOlib.imgFormatException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import ru.EXXO.Util.Imges;

public class addPics extends HttpServlet {

    DiskFileItemFactory factory = null;

    @Override
    public void init(ServletConfig config) throws ServletException {

        factory = new DiskFileItemFactory();

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, FileUploadException, imgException, imgFormatException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String res = "<div class='check'></div>";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        long length = 0;
        String format = "png";
        byte icon[] = null;
        byte bytes[] = null;
        byte ico[] = null;
        String type = null;
        String fname = null;
        String discription = "";
        String tags = "";
        String longname = "";
        String superior = "";
        String name = "";
        String owner = null;
        String tempImged = null;
        String notshow = "0";
        String Re = "";
        int width = 0;
        int height = 0;
        AddPicBean bean = (AddPicBean) session.getAttribute("addPicBean");
        if (bean == null) {
            bean = new AddPicBean();
            session.setAttribute("addPicBean", bean);
        }
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
                if (item.isFormField()) {
                    // если обычное поле, то мы можем получить его значение в выбранной кодировке 
                    // с помощью вызова getString(нужная кодировка)
                    String I = item.getString("utf-8");
                    String N = item.getFieldName();
                    if (N.equals("discription")) {
                        discription = I;
                    } else if (N.equals("tags")) {
                        tags = I;
                    } else if (N.equals("longname")) {
                        longname = I;
                    } else if (N.equals("superior")) {
                        superior = I;
                    } else if (N.equals("name")) {
                        name = I;
                    } else if (N.equals("owner")) {
                        owner = I;
                    } else if (N.equals("notshow")) {
                        notshow = I;
                    } else if (N.equals("Re")) {
                        Re = I;
                    }

                } else {
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

        Boolean yes = true;
        String own = "";
        if (owner != null) {
            own = "owner=" + owner;
        } else {
            owner = (String) session.getAttribute("id");
        }
        if (owner.equals("0")) {
            owner = "-100";
        }
        if (role.indexOf("a") < 0 && role.indexOf("d") < 0 && owner != null && owner.equals("-100")) {
            yes = false;
        }

        if (yes) {
            //450Kb
            if (name.equals("")) {
                name = fname;
                name = (name.split("\\."))[0];
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
                ServletContext sc = request.getServletContext();
                Connection con = BASE.VER.getServletConnection(sc);

                String INSERT_FILE = "insert into gallaries (owner, name, file, superior, type,longname,tags,descr,bin,size, binsm, sizesm, fname, ico, sizeico, notshow, width, height)"
                        + " values (?::int, ?, ?::smallint, ?::int, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::smallint,?,?)";

                fname = fname + ".png";
                type = "image/png";
                PreparedStatement ps = null;
                discription = bean.getDescription();
                if (!bean.getName().equals("")) {
                    name = bean.getName() + " " + name;
                }
                tags = bean.getTags();
                if (bean.isNotshow()) {
                    notshow = "1";
                }
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
                    ps.setString(4, superior);
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
            } else {
                tempImged = null;
            }
            //  prop.list(out);
            out.println(res);
            //     String uri="";
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
            //    if(Re.equals("1"))response.sendRedirect("galClassic.jsp?id="+superior+"&"+own);
            //    else  response.sendRedirect("gallariesMod.jsp?"+own);
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
            Logger.getLogger(addPics.class.getName()).log(Level.SEVERE, null, ex);
            //         response.sendRedirect("alert.xhtml?p=sqlError"); 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addPics.class.getName()).log(Level.SEVERE, null, ex);
            //          response.sendRedirect("alert.xhtml?p=unknownError"); 
        } catch (FileUploadException ex) {
            //           Logger.getLogger(addPic.class.getName()).log(Level.SEVERE, null, ex);
            //          response.sendRedirect("alert.xhtml?p=bigSize");
            PrintWriter out = response.getWriter();
            out.println("<div class='error'></div>");
            out.close();
        } catch (imgException ex) {
            //          response.sendRedirect("alert.xhtml?p=notImage");
            PrintWriter out = response.getWriter();
            out.println("<div class='error'></div>");
            out.close();
        } catch (imgFormatException ex) {
            //          response.sendRedirect("alert.xhtml?p=notImageFormat");
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
