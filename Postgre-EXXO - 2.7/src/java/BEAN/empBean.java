package BEAN;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import EXXOlib.imgException;
import EXXOlib.imgFormatException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import ru.EXXO.LOGIN.role;
import ru.EXXO.Status;
import ru.EXXO.User;
import ru.EXXO.UserDAO;
import ru.EXXO.employee.Office;
import ru.EXXO.employee.unit;

@ManagedBean
@SessionScoped
public class empBean implements Serializable {

    private employeeBuilder emp;
    private ru.EXXO.Util.Date birthday;
    private ru.EXXO.Util.Date work;
    private boolean sendlogin = false;

    private String timer = "";
//private String birthdayString="";
//private String birthdayScript="";
//private String workString="";
//private String workScript="";
    private Part file = null;
    private String error = "";
    private String supervizor = "Нажмите, чтобы выбрать подразделение";
    private boolean edit = false;
    private int id;
    private String rest;

    public String initParams() throws ParseException, ClassNotFoundException, SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        if (!edit) {
            removeImg();
            emp = new employeeBuilder();
            timer = "";
            supervizor = "Нажмите, чтобы выбрать подразделение";
            file = null;
        }
        error = "";

        if (!edit) {
            birthday = new ru.EXXO.Util.Date(new java.util.Date());
            work = new ru.EXXO.Util.Date(new java.util.Date());
            birthday.setDateScript("");
            work.setDateScript("");
            birthday.setDateString("");
            work.setDateString("");
        }
        return null;
    }

    public String initNav() {
        if (emp == null || emp.getFio().equals("")) {
            return ru.EXXO.CONST.alert_no_user;
        }
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        return null;
    }

    public void loadImg() throws FileUploadException, UnsupportedEncodingException, IOException, ClassNotFoundException, SQLException, ParseException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String res = "";
        javax.servlet.http.HttpSession ses = request.getSession();
        byte bytes[] = null;
        String format = null;
        String type = "";
        long length = 0;
        String fname = null;
        Properties prop = new Properties();
        //   updateBirthday();!!!!!!
        try {
            if (file == null) {
                throw new imgException("Вы не выбрали фотографию сотрудника!");
            }
            type = file.getContentType();
            long size = file.getSize();
            InputStream is = file.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int next = is.read();
            while (next > -1) {
                bos.write(next);
                next = is.read();
            }
            bos.flush();
            bytes = bos.toByteArray();

            if (bytes == null || size == 0) {
                throw new imgException("Вы не выбрали фотографию сотрудника!");
            }
            if (bytes.length > 10485760 || bytes.length <= 100) {
                bytes = null;
                throw new imgException("Размер файла не должен превышать 10 Мбайт и не должен быть меньше 100 байт!");
            }

            format = EXXOlib.IMGESLIB.getFormat(bytes, type);
            if (format.equals("noresult")) {
                throw new imgFormatException("Файл не является изображением, либо формат файла не поддерживается!");
            }
            //  bytes=ru.exxo.IMGES.cropResize(bytes, 110, 147, format);
            bytes = EXXOlib.IMGESLIB.preCropResize(bytes, 480, format, 147, 110);
            if (bytes == null) {
                throw new imgException("Размер изображения не должен быть меньше 110px*147px");
            }

            ru.EXXO.employee.Photo img = new ru.EXXO.employee.Photo(bytes, type, ru.EXXO.Status.NEW);
            img.applyChanges(ses);

        } catch (imgException ex) {
            error = ex.toString();
        } catch (imgFormatException ex) {
            error = ex.toString();
        }

    }

    public String insert() throws ClassNotFoundException, SQLException, IOException, MessagingException {
        if (BASE.CASE.isDemo()) {
            return ru.EXXO.CONST.alert_demo + "&amp;faces-redirect=true";
        }
        if (!BASE.CHECK.checkEmpINS()) {
            return ru.EXXO.CONST.alert_countup + "&amp;faces-redirect=true";
        }
        ru.EXXO.employee.Employee e = new ru.EXXO.employee.Employee(emp.getFio(), emp.getUnit(), emp.getPosition(), emp.isHead(), birthday.getDate(), emp.getEducation(), emp.getTellocal(), emp.getTellmob(), emp.getEmail(), new ru.EXXO.RegistrationSeq().employee_id_seq(), new ru.EXXO.RegistrationSeq().global_seq(), emp.isPhoto(), emp.getHobby(), emp.getResponsibility(), emp.getTel(), emp.getOffice(), emp.getRoom(), work.getDate(), false, null, false, emp.getComment(), Status.NEW);
        if (sec()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            e.applyChanges((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false));

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
            String to = emp.getEmail();
            String logo = "";
            String pass = "";
            if (to == null || to.equals("")) {
                sendlogin = false;
            }

            User u = new User(e.getId(), myLogo(e.getFio(), sdf.format(e.getBirthday())), newPass(), ru.EXXO.Users.EMPLOYEE, ru.EXXO.Status.NEW);

            if (sendlogin) {
                Properties props = System.getProperties();
                logo = u.getLogin();
                pass = u.getPass();
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
                message.setSubject("Вы зарегистрированы в корпоративном портале");
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
                u.applyChanges(request.getSession());
                u.addRole("portal_user");
            }
        }
        supervizor = "Нажмите, чтобы выбрать подразделение";
        file = null;
        return empTarget(false);
    }

    public void removeImg() throws ClassNotFoundException, SQLException {
        new ru.EXXO.employee.PhotoDAO((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).removePhoto();
    }

    public String getTimer() {
        timer = System.currentTimeMillis() + "";
        return timer;
    }

    public void updateBirthday() throws ParseException {
        emp.setBirthday(birthday.getDate());
        emp.setWorksince(work.getDate());
    }

    private boolean sec() {
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ArrayList<role> r = (ArrayList<role>) ses.getAttribute("roles");
        if (r == null) {
            return false;
        }
        ListIterator li = r.listIterator();
        while (li.hasNext()) {
            role role = (role) li.next();
            if (role.getName().equals("staff") || role.getName().equals("boss")) {
                return true;
            }
        }
        return false;
    }

    public String empTarget(boolean edit) {
        this.edit = edit;
        return "emp.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    public String photoTarget() {
        return "photo.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    public String empfinTarget() {
        return "empfin.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    private String myLogo(String fio, String year) throws ClassNotFoundException, SQLException {
        String r = EXXOlib.textLib.translitText(fio);
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
        UserDAO ud = new UserDAO();
        if (ud.checkLogo(fname.toLowerCase())) {
            r = fname.toLowerCase();
        } else if (ud.checkLogo(fname)) {
            r = fname;
        } else if (ud.checkLogo(fname + "_" + sname.charAt(0))) {
            r = fname + "_" + sname.charAt(0);
        } else if (ud.checkLogo((fname + "_" + sname.charAt(0)).toLowerCase())) {
            r = (fname + "_" + sname.charAt(0)).toLowerCase();
        } else if (ud.checkLogo(sname)) {
            r = sname;
        } else if (ud.checkLogo(sname.toLowerCase())) {
            r = sname.toLowerCase();
        } else if (ud.checkLogo(sname + year)) {
            r = sname + year;
        } else if (ud.checkLogo(sname.toLowerCase() + year)) {
            r = sname.toLowerCase() + year;
        } else if (ud.checkLogo(sname + "_" + year)) {
            r = sname + "_" + year;
        } else if (ud.checkLogo(sname.toLowerCase() + "_" + year)) {
            r = sname.toLowerCase() + "_" + year;
        } else if (ud.checkLogo(fname + year)) {
            r = fname + year;
        } else if (ud.checkLogo(fname.toLowerCase() + year)) {
            r = fname.toLowerCase() + year;
        } else if (ud.checkLogo(fname + "_" + year)) {
            r = fname + "_" + year;
        } else if (ud.checkLogo(fname.toLowerCase() + "_" + year)) {
            r = fname.toLowerCase() + "_" + year;
        } else if (ud.checkLogo(sname + "_" + fname.charAt(0))) {
            r = sname + "_" + fname.charAt(0);
        } else if (ud.checkLogo((sname + "_" + fname.charAt(0)).toLowerCase())) {
            r = (sname + "_" + fname.charAt(0)).toLowerCase();
        } else if (ud.checkLogo(fname + "_" + sname)) {
            r = fname + "_" + sname;
        } else if (ud.checkLogo((fname + "_" + sname).toLowerCase())) {
            r = (fname + "_" + sname).toLowerCase();
        } else if (ud.checkLogo(sname + "_" + fname)) {
            r = sname + "_" + fname;
        } else if (ud.checkLogo((sname + "_" + fname).toLowerCase())) {
            r = (sname + "_" + fname).toLowerCase();
        } else if (ud.checkLogo(r)) {
            r = r;
        } else if (ud.checkLogo(r.toLowerCase())) {
            r = r.toLowerCase();
        } else if (ud.checkLogo("superchempion")) {
            r = "superchempion";
        } else {
            r = "";
        }
        return r;
    }

    private String newPass() {
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getError() {
        return error;
    }

    public String getSupervizor() {
        return supervizor;
    }

    public void setSupervizor(String supervizor) {
        this.supervizor = supervizor;
    }

    public boolean isEdit() {
        return edit;
    }

    public employeeBuilder getEmp() {
        return emp;
    }

    public void setEmp(employeeBuilder emp) {
        this.emp = emp;
    }

    public ru.EXXO.Util.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(ru.EXXO.Util.Date birthday) {
        this.birthday = birthday;
    }

    public ru.EXXO.Util.Date getWork() {
        return work;
    }

    public void setWork(ru.EXXO.Util.Date work) {
        this.work = work;
    }

    public String getRest() throws ClassNotFoundException, SQLException {
        rest = BASE.CHECK.getRest();
        return rest;
    }

    public class employeeBuilder implements Serializable {

        private String fio = "";
        private unit unit;
        private String position = "";
        private boolean head = false;
        private java.util.Date birthday;
        private String education = "";
        private String tellocal = "";
        private String tellmob = "";
        private String email = "";
        private boolean photo = false;
        private String hobby = "";
        private String comment = "";
        private String responsibility = "";
        private String tel = "";
        private Office office;
        private String room = "";
        private java.util.Date worksince;
        private boolean fired = false;
        private java.util.Date firedsince;
        private boolean initialBoss = false;

        public employeeBuilder() {
            this.office = new ru.EXXO.employee.Office();
            this.unit = new ru.EXXO.employee.unit();
            this.birthday = new java.util.Date();
            this.worksince = new java.util.Date();
        }

        public String getFio() {
            return fio;
        }

        public void setFio(String fio) {
            this.fio = fio;
        }

        public unit getUnit() {
            return unit;
        }

        public void setUnit(unit unit) {
            this.unit = unit;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public boolean isHead() {
            return head;
        }

        public void setHead(boolean head) {
            this.head = head;
        }

        public java.util.Date getBirthday() {
            return birthday;
        }

        public void setBirthday(java.util.Date birthday) {
            this.birthday = birthday;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getTellocal() {
            return tellocal;
        }

        public void setTellocal(String tellocal) {
            this.tellocal = tellocal;
        }

        public String getTellmob() {
            return tellmob;
        }

        public void setTellmob(String tellmob) {
            this.tellmob = tellmob;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isPhoto() {
            return photo;
        }

        public void setPhoto(boolean photo) {
            this.photo = photo;
        }

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getResponsibility() {
            return responsibility;
        }

        public void setResponsibility(String responsibility) {
            this.responsibility = responsibility;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public Office getOffice() {
            return office;
        }

        public void setOffice(Office office) {
            this.office = office;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public java.util.Date getWorksince() {
            return worksince;
        }

        public void setWorksince(java.util.Date worksince) {
            this.worksince = worksince;
        }

        public boolean isFired() {
            return fired;
        }

        public void setFired(boolean fired) {
            this.fired = fired;
        }

        public java.util.Date getFiredsince() {
            return firedsince;
        }

        public void setFiredsince(java.util.Date firedsince) {
            this.firedsince = firedsince;
        }

        public boolean isInitialBoss() {
            return initialBoss;
        }

        public void setInitialBoss(boolean initialBoss) {
            this.initialBoss = initialBoss;
        }

    }

}
