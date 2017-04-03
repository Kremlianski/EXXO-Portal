package Servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Date;


public class blogPublish extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, MessagingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String res = "";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        String owner = (String) session.getAttribute("id");
        //    String l=" AND owner='"+owner+"' ";
        Boolean yes = true;
        boolean sendlogin = false;
        String sl = request.getServletContext().getInitParameter("messages_personal");
        if (sl != null && sl.equalsIgnoreCase("true")) {
            sendlogin = true;
        }

        String list = "OK";
        //       if(role.indexOf("a")>=0||role.indexOf("e")>=0||role.indexOf("Z")>=0) yes=true;
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        String curCat = (String) session.getAttribute("blog_car_cat");
        if (curCat == null) {
            curCat = "0";
        }
        Statement stmt = con.createStatement();
        String[] ar = {};
        ResultSet rs = stmt.executeQuery("SELECT addtoblog(" + owner + "," + curCat + ")");
        if (rs.next()) {
            Array a = rs.getArray(1);
            ar = (String[]) a.getArray();
        }
        stmt.close();
        con.close();
        if (sendlogin) {
            sendMail(request, ar[0], ar[1], ar[2]);
        }
        response.sendRedirect("correspondence.jsp");
    }

    private void sendMail(HttpServletRequest request, String adress, String name, String text) throws MessagingException {
        String host = request.getServletContext().getInitParameter("smtp_server");
        String port = request.getServletContext().getInitParameter("smtp_port");
        String smtp_user = request.getServletContext().getInitParameter("smtp_user");
        String smtp_pass = request.getServletContext().getInitParameter("smtp_pass");
        String smtp_auth = request.getServletContext().getInitParameter("smtp_auth");
        if (smtp_auth == null || !smtp_auth.equalsIgnoreCase("true")) {
            smtp_auth = "false";
        } else {
            smtp_auth = "true";
        }

        String reply = BASE.VER.getReplyAddress(request.getServletContext());
        String from = (String) request.getSession().getAttribute("email");
        if (!reply.equals("")) {
            from = reply;
        }
        if (from != null && !from.equals("") && host != null && !host.equals("")) {
            Properties props = System.getProperties();

// Setup mail server
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", smtp_auth);
            if (port != null) {
                props.put("mail.smtp.port", port);
            }
// Get session
            //        Session ses = Session.getDefaultInstance(props, null);
            Session ses = Session.getInstance(props);
            adress = adress.substring(0, adress.length() - 1);
// Define message
            MimeMessage message = new MimeMessage(ses);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.TO,
                    adress);
            message.setSubject(name);
            message.setSentDate(new Date());
            String content = "<h3>Вам сообщенеи с интранет-портала</h3>";
            content += "<p>Отправитель: <b>" + (String) request.getSession().getAttribute("fio") + "</b></p>";
            message.setText(content + text, "utf-8", "html");

// Send message
            Transport t = ses.getTransport("smtp");

            try {
                if (smtp_auth.equalsIgnoreCase("true")) {
                    t.connect(smtp_user, smtp_pass);
                } else {
                    t.connect();
                }
                message.saveChanges();
                t.sendMessage(message, message.getAllRecipients());
            } finally {
                t.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(blogPublish.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(blogPublish.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(blogPublish.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(blogPublish.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(blogPublish.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(blogPublish.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
