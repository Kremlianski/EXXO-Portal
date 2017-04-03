package Servlets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
//import java.util.Properties;
import java.io.*;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.*;

public class changePass extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, MessagingException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        boolean sendlogin = false;
        String sl = request.getServletContext().getInitParameter("send_login");
        if (sl != null && sl.equalsIgnoreCase("true")) {
            sendlogin = true;
        }
        String host = request.getServletContext().getInitParameter("smtp_server");
        String from = request.getServletContext().getInitParameter("admin_mail");
        String port = request.getServletContext().getInitParameter("smtp_port");
        String smtp_user = request.getServletContext().getInitParameter("smtp_user");
        String smtp_pass = request.getServletContext().getInitParameter("smtp_pass");
        String smtp_auth = request.getServletContext().getInitParameter("smtp_auth");
        if (smtp_auth == null || !smtp_auth.equalsIgnoreCase("true")) {
            smtp_auth = "false";
        } else {
            smtp_auth = "true";
        }
        if (from == null) {
            from = "";
        }
        if (host == null) {
            host = "";
        }
        String to = "";
        String logo = "";
        String pass = "";
        String fio = "";
        String birthday = "";

        String res = "ok";
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("c") >= 0 || role.indexOf("Z") >= 0) {
            yes = true;
        }
        ServletContext sc = request.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        if (yes) {
            String sql = "SELECT email, fio, birthday FROM employee WHERE id='" + id + "'";
            String sql1 = "SELECT login FROM users WHERE id='" + id + "'";
            ResultSet rs = stmt.executeQuery(sql1);
            ResultSet rs1 = null;
            if (rs.next()) {
                logo = rs.getString(1);
                rs1 = stmt.executeQuery(sql);
                if (rs1.next()) {
                    to = rs1.getString(1);
                }
                fio = rs1.getString(2);
                birthday = rs1.getString(3);
            }
            rs.close();
            rs1.close();
            if (to == null || to.equals("")) {
                sendlogin = false;
            }
            if (sendlogin) {
                pass = newPass();
                sql = "UPDATE users SET pass=MD5('" + pass + "') WHERE id='" + id + "'";
                if (stmt.executeUpdate(sql) <= 0) {
                    sendlogin = false;
                }
            }
            stmt.close();
            con.close();

            if (sendlogin) {
                Properties props = System.getProperties();

// Setup mail server
                props.put("mail.smtp.host", host);

                props.put("mail.smtp.auth", smtp_auth);
                if (port != null) {
                    props.put("mail.smtp.port", port);
                }

// Get session
                Session ses = Session.getInstance(props);

// Define message
                MimeMessage message = new MimeMessage(ses);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(to));
                message.setSubject("Смена пароля");
                String text = "Новые данные:\nЛогин: " + logo + "\nПароль: " + pass;
                message.setText(text);
                message.setSentDate(new Date());
// Send message
                Transport t = ses.getTransport("smtp");
                if (smtp_auth.equalsIgnoreCase("true")) {
                    t.connect(smtp_user, smtp_pass);
                } else {
                    t.connect();
                }
                message.saveChanges();
                t.sendMessage(message, message.getAllRecipients());
                t.close();
            }

        }

        out.println(res);

        response.sendRedirect("empUpd.xhtml?id=" + id);

        out.close();

    }

    String myLogo(String fio, String year, HttpServletRequest request) throws ClassNotFoundException, SQLException {
        String r = fio;
        r = r.replaceAll("\\s", "_");
        r = r.replaceAll("\\W", "");
        String sub[] = r.split("_");
        String fname = sub[0];
        String sname = "";
        String tname = "";
        if (sub.length > 1) {
            sname += sub[1];
        }
        if (sub.length > 2) {
            tname += sub[2];
        }
        if (checkLogo(fname.toLowerCase(), request)) {
            r = fname.toLowerCase();
        } else if (checkLogo(fname, request)) {
            r = fname;
        } else if (checkLogo(fname + "_" + sname.charAt(0), request)) {
            r = fname + "_" + sname.charAt(0);
        } else if (checkLogo((fname + "_" + sname.charAt(0)).toLowerCase(), request)) {
            r = (fname + "_" + sname.charAt(0)).toLowerCase();
        } else if (checkLogo(sname, request)) {
            r = sname;
        } else if (checkLogo(sname.toLowerCase(), request)) {
            r = sname.toLowerCase();
        } else if (checkLogo(sname + year, request)) {
            r = sname + year;
        } else if (checkLogo(sname.toLowerCase() + year, request)) {
            r = sname.toLowerCase() + year;
        } else if (checkLogo(sname + "_" + year, request)) {
            r = sname + "_" + year;
        } else if (checkLogo(sname.toLowerCase() + "_" + year, request)) {
            r = sname.toLowerCase() + "_" + year;
        } else if (checkLogo(fname + year, request)) {
            r = fname + year;
        } else if (checkLogo(fname.toLowerCase() + year, request)) {
            r = fname.toLowerCase() + year;
        } else if (checkLogo(fname + "_" + year, request)) {
            r = fname + "_" + year;
        } else if (checkLogo(fname.toLowerCase() + "_" + year, request)) {
            r = fname.toLowerCase() + "_" + year;
        } else if (checkLogo(sname + "_" + fname.charAt(0), request)) {
            r = sname + "_" + fname.charAt(0);
        } else if (checkLogo((sname + "_" + fname.charAt(0)).toLowerCase(), request)) {
            r = (sname + "_" + fname.charAt(0)).toLowerCase();
        } else if (checkLogo(fname + "_" + sname, request)) {
            r = fname + "_" + sname;
        } else if (checkLogo((fname + "_" + sname).toLowerCase(), request)) {
            r = (fname + "_" + sname).toLowerCase();
        } else if (checkLogo(sname + "_" + fname, request)) {
            r = sname + "_" + fname;
        } else if (checkLogo((sname + "_" + fname).toLowerCase(), request)) {
            r = (sname + "_" + fname).toLowerCase();
        } else if (checkLogo(r, request)) {
            r = r;
        } else if (checkLogo(r.toLowerCase(), request)) {
            r = r.toLowerCase();
        } else if (checkLogo("superchempion", request)) {
            r = "superchempion";
        } else {
            r = "";
        }
        return r;
    }

    String newPass() {
        String pass = "";
        String[] arr = {"qe", "qo", "qa", "qy", "qu", "qi", "qoo", "qee", "qea", "Qe", "Qo", "Qa", "Qy", "Qu", "Qi", "Qoo", "Qee", "Qea",
            "we", "We", "wo", "Wo", "wa", "Wa", "wy", "Wy", "wu", "Wu", "wi", "Wi", "woo", "Woo", "wee", "Wee", "wea", "Wea",
            "re", "Re", "ro", "Ro", "ra", "Ra", "ry", "Ry", "ru", "Ru", "ri", "Ri", "roo", "Roo", "ree", "Ree", "rea", "Rea",
            "te", "Te", "to", "To", "ta", "Ta", "ty", "Ty", "tu", "Tu", "ti", "Ti", "too", "Too", "tee", "Tee", "tea", "Tea",
            "pe", "Pe", "po", "Po", "pa", "Pa", "py", "Py", "pu", "Pu", "pi", "Pi", "poo", "Poo", "pee", "Pee", "pea", "Pea",
            "se", "Se", "so", "So", "sa", "Sa", "sy", "Sy", "su", "Su", "si", "Si", "soo", "Soo", "see", "See", "sea", "Sea",
            "de", "De", "do", "Do", "da", "Da", "dy", "Dy", "du", "Du", "di", "Di", "doo", "Doo", "dee", "Dee", "dea", "Dea",
            "fe", "Fe", "fo", "Fo", "fa", "Fa", "fy", "Fy", "fu", "Fu", "fi", "Fi", "foo", "Foo", "fee", "Fee", "fea", "Fea",
            "ge", "Ge", "go", "Go", "ga", "Ga", "gy", "Gy", "gu", "Gu", "gi", "Gi", "goo", "Goo", "gee", "Gee", "gea", "Gea",
            "he", "He", "ho", "Ho", "ha", "Ha", "hy", "Hy", "hu", "Hu", "hi", "Hi", "hoo", "Hoo", "hee", "Hee", "hea", "Hea",
            "je", "Je", "jo", "Jo", "ja", "Ja", "jy", "Jy", "ju", "Ju", "ji", "Ji", "joo", "Joo", "jee", "Jee", "jea", "Jea",
            "ke", "Ke", "ko", "Ko", "ka", "Ka", "ky", "Ky", "ku", "Ku", "ki", "Ki", "koo", "Koo", "kee", "Kee", "kea", "Kea",
            "Le", "La", "Lo", "Ly", "Lu", "Li", "Loo", "Lee", "Lea",
            "ze", "Ze", "zo", "Zo", "za", "Za", "zy", "Zy", "zu", "Zu", "zi", "Zi", "zoo", "Zoo", "zee", "Zee", "zea", "Zea",
            "xe", "Xe", "xo", "Xo", "xa", "Xa", "xy", "Xy", "xu", "Xu", "xi", "Xi", "xoo", "Xoo", "xee", "Xee", "xea", "Xea",
            "ce", "Ce", "co", "Co", "ca", "Ca", "cy", "Cy", "cu", "Cu", "ci", "Ci", "coo", "Coo", "cee", "Cee", "cea", "Cea",
            "ve", "Ve", "vo", "Vo", "va", "Va", "vy", "Vy", "vu", "Vu", "vi", "Vi", "voo", "Voo", "vee", "Vee", "vea", "Vea",
            "be", "Be", "bo", "Bo", "ba", "Ba", "by", "By", "bu", "Bu", "bi", "Bi", "boo", "Boo", "bee", "Bee", "bea", "Bea",
            "ne", "Ne", "no", "No", "na", "Na", "ny", "Ny", "nu", "Nu", "ni", "Ni", "noo", "Noo", "nee", "Nee", "nea", "Nea",
            "me", "Me", "mo", "Mo", "ma", "Ma", "my", "My", "mu", "Mu", "mi", "Mi", "moo", "Moo", "mee", "Mee", "mea", "Mea",
            "2", "3", "4", "5", "6", "7", "8", "9"};
        Random r = new Random();
        while (pass.length() < 9) {
            pass += arr[r.nextInt(arr.length)];
        }
        return pass;
    }

    boolean checkLogo(String l, HttpServletRequest request) throws ClassNotFoundException, SQLException {
        boolean r = true;
        if (l.length() >= 4) {
            ServletContext sc = request.getServletContext();
            Connection con = BASE.VER.getServletConnection(sc);
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM users WHERE login='" + l + "'";
            r = stmt.executeQuery(sql).next();
            stmt.close();
            con.close();
            return !r;
        } else {
            return false;
        }
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
        } catch (MessagingException ex) {
            Logger.getLogger(empInserter.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=mailError");
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
        } catch (MessagingException ex) {
            Logger.getLogger(empInserter.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("alert.xhtml?p=mailError");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
