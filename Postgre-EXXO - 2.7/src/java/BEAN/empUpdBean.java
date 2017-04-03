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
import java.util.ArrayList;
import java.util.ListIterator;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import ru.EXXO.LOGIN.role;
import ru.EXXO.User;

@ManagedBean(name = "empUpdBean")
@SessionScoped
public class empUpdBean implements Serializable {

    private ru.EXXO.employee.Employee emp;
    private ru.EXXO.Util.Date birthday;
    private ru.EXXO.Util.Date work;
    private int id = 0;
    private boolean notMe = true;
    private String timer = "";
    private Part file = null;
    private String error = "";
    private String supervizor = "Нажмите, чтобы выбрать подразделение";
    private User user;
    private boolean registerd = false;
    private boolean sendOn = false;

    public String initParams() throws ParseException, ClassNotFoundException, SQLException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpSession ses = req.getSession();
        user = null;
        emp = null;
        if (id == 0) {
            id = java.lang.Integer.parseInt((String) ses.getAttribute("id"));
        }
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        emp = new ru.EXXO.employee.EmployeeDAO(ses).loadEmployee(id);
        if (emp == null) {
            return ru.EXXO.CONST.alert_no_user;
        }
        user = new ru.EXXO.UserDAO().loadUser(emp);
        if (user != null) {
            registerd = true;
        }
        if (emp.getResponsibility() != null) {
            emp.setResponsibility(emp.getResponsibility().replaceAll("<br>", ""));
        }
        if (emp.getEducation() != null) {
            emp.setEducation(emp.getEducation().replaceAll("<br>", ""));
        }
        if (emp.getComment() != null) {
            emp.setComment(emp.getComment().replaceAll("<br>", ""));
        }
        if (emp.getHobby() != null) {
            emp.setHobby(emp.getHobby().replaceAll("<br>", ""));
        }
        birthday = new ru.EXXO.Util.Date(emp.getBirthday());
        if (emp.getWorksince() != null) {
            work = new ru.EXXO.Util.Date(emp.getWorksince());
        } else {
            work = new ru.EXXO.Util.Date(new java.util.Date());
            work.setDateScript("");
            work.setDateString("");
        }
        removeImg();
        timer = "";
        supervizor = "Нажмите, чтобы выбрать подразделение";
        file = null;
        ////////////
        boolean sendlogin = false;
        String sl = req.getServletContext().getInitParameter("send_login");
        if (sl != null && sl.equalsIgnoreCase("true")) {
            sendlogin = true;
        }
        String host = req.getServletContext().getInitParameter("smtp_server");
        String from = req.getServletContext().getInitParameter("admin_mail");
        if (sendlogin && host != null && from != null) {
            sendOn = true;
        }
        if (emp.getEmail() == null || emp.getEmail().equals("")) {
            sendOn = false;
        }
/////////
        return null;

    }

    public String initNav() {
        if (emp == null) {
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
            removeImg();
            img.applyChanges(ses);

        } catch (imgException ex) {
            error = ex.toString();
        } catch (imgFormatException ex) {
            error = ex.toString();
        }

    }

    public String empUpdTarget() {
        return "empUpd.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    public String photoUpdTarget() {
        return "photoUpd.xhtml" + ru.EXXO.CONST.includeViewParams;
    }

    public void insert() throws ClassNotFoundException, SQLException, IOException {
        if (sec()) {
            emp.setBirthday(birthday.getDate());
            emp.setWorksince(work.getDate());
            emp.applyChanges((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false));
            emp.updatePhoto((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        }
        supervizor = "Нажмите, чтобы выбрать подразделение";
        file = null;
    }

    public void removeImg() throws ClassNotFoundException, SQLException {
        new ru.EXXO.employee.PhotoDAO((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).removePhoto();
    }

    public String getTimer() {
        timer = System.currentTimeMillis() + "";
        return timer;
    }

    public void updateBirthday() throws ParseException, SQLException {
        emp.setBirthday(birthday.getDate());
        if (work != null) {
            emp.setWorksince(work.getDate());
        }
        if (sec()) {
            emp.applyChanges((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false));
        }
    }

    public String dropImg() throws SQLException {
        if (!sec()) {
            return ru.EXXO.CONST.alert_not_permitted;
        }
        new ru.EXXO.employee.EmployeeDAO((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).removeEmployeePhoto(id);
        return "empUpd.xhtml" + ru.EXXO.CONST.includeViewParams;
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
                notMe = true;
                return true;
            }
        }
        if (id == java.lang.Integer.parseInt((String) ses.getAttribute("id"))) {
            notMe = false;
            return true;
        }
        return false;
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

    public ru.EXXO.employee.Employee getEmp() {
        return emp;
    }

    public void setEmp(ru.EXXO.employee.Employee emp) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNotMe() {
        return notMe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRegisterd() {
        return registerd;
    }

    public void setRegisterd(boolean registerd) {
        this.registerd = registerd;
    }

    public boolean isSendOn() {
        return sendOn;
    }

    public void setSendOn(boolean sendOn) {
        this.sendOn = sendOn;
    }
}
