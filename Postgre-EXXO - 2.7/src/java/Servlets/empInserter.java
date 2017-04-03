package Servlets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.*;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class empInserter extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, MessagingException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String res = "ok";
        String u = "notPermited.html";
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
        String to = request.getParameter("email");
        String logo = "";
        String pass = "";
        if (to == null || to.equals("")) {
            sendlogin = false;
        }

        HttpSession session = request.getSession(true);
        String id = (String) session.getAttribute("id");
        String role = (String) session.getAttribute("role");
        Boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("c") >= 0) {
            yes = true;
        }

        String workSince = request.getParameter("workSince");
        String fio = request.getParameter("fio");
        String unit = request.getParameter("unit");
        String position = request.getParameter("position");

        if (fio.equals("")) {
            fio = null;
        }
        if (unit.equals("")) {
            unit = null;
        }
        if (position.equals("")) {
            position = null;
        }
        if (workSince.equals("")) {
            workSince = null;
        }
        ServletContext sc = request.getServletContext();

        if (BASE.CASE.isDemo(sc)) {
            yes = false;
            u = "itsdemo.html";
        } else if (!BASE.CHECK.checkEmpINS(sc)) {
            yes = false;
            u = "countup.html";
        }
        if (yes) {
            Connection con = BASE.VER.getServletConnection(sc);
            String INSERT_PICTURE = "INSERT INTO employee (fio,unit,position,birthday,head,education,tellocal,tellmob,"
                    + "email,hobby,comment,responsibility, tel, room, office, photo, workSince) values "
                    + "(?, ?::int, ?, ?::Date, ?::smallint, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT photo FROM employeeTMP WHERE id='" + id + "'), ?::Date)";

            //   FileInputStream fis = null;
            PreparedStatement ps = null;
            try {

                con.setAutoCommit(false);

                ps = con.prepareStatement(INSERT_PICTURE);
                ps.setString(1, fio);
                ps.setString(2, unit);
                ps.setString(3, position);
                ps.setString(4, request.getParameter("birthday"));
                ps.setString(5, request.getParameter("head"));
                ps.setString(6, request.getParameter("education").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(7, request.getParameter("tellocal"));
                ps.setString(8, request.getParameter("tellmob"));
                ps.setString(9, request.getParameter("email"));
                ps.setString(10, request.getParameter("hobby").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(11, request.getParameter("comment").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(12, request.getParameter("responsibility").replace("\n<br>", "<br>").replace("\n", "<br>"));
                ps.setString(13, request.getParameter("tel"));
                ps.setString(14, request.getParameter("room"));
                ps.setString(15, request.getParameter("office"));
                ps.setString(16, workSince);
                if (ps.executeUpdate() > 0 && sendlogin) {
                    Statement stmt = con.createStatement();
                    logo = myLogo(EXXOlib.textLib.translitText(fio), request.getParameter("birthday").substring(0, 4), request);
                    pass = newPass();
                    String sql = "INSERT INTO users (id, login, pass) VALUES((SELECT currval('employee_id_seq')), '" + logo + "', MD5('" + pass + "')) ";
                    if (stmt.executeUpdate(sql) > 0) {
                        sql = "INSERT INTO user_roles VALUES('" + logo + "', 'portal_user' )";
                        if (stmt.executeUpdate(sql) <= 0) {
                            sendlogin = false;
                        }

                    }
                }
                con.commit();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            } finally {
                ps.close();
                con.close();
            }
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
                message.setSubject("вы зарегистрированы в корпоративном портале");
                String text = "Регистрационные данные:\nЛогин: " + logo + "\nПароль: " + pass;
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

            out.println(res);

            if (request.getParameter("more") != null) {
                response.sendRedirect("empIns.jsp");
            } else {
                response.sendRedirect("empList.jsp");
            }

        } else {
            response.sendRedirect(u);
        }
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
        response.sendRedirect("notPermited.html");
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
