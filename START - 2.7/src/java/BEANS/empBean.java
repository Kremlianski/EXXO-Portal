package BEANS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import ru.exxo.imgException;
import ru.exxo.imgFormatException;
import javax.servlet.http.Part;

@ManagedBean
@SessionScoped
public class empBean implements Serializable {

    private ru.exxo.Employee emp;
    private ru.exxo.Offices office;

    private String timer = "";
    private String birthdayString = "";
    private String birthdayScript = "";
    private String workString = "";
    private String workScript = "";
    private ru.exxo.ExxoDate birthday;
    private ru.exxo.ExxoDate work;
    private Part file = null;
    private String error = "";
    private String supervizor = "Нажмите, чтобы выбрать подразделение";
    private boolean edit = false;

    private String login = "";
    private String pass = "";
    private String pass1 = "";
    private String errorMessage = "";

    public void initParams() throws ParseException, ClassNotFoundException, SQLException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        if (req.getParameter("edit") != null) {
            edit = true;
        } else {
            edit = false;
            removeImg();
        }
        if (emp == null) {
            edit = false;
        }
        if (!edit) {
            emp = new ru.exxo.Employee();
            timer = "";
            birthdayString = "";
            birthdayScript = "";
            workString = "";
            workScript = "";
            supervizor = "Нажмите, чтобы выбрать подразделение";
            file = null;
        }
        errorMessage = "";
        office = new ru.exxo.Offices();
        error = "";

        if (emp.getBirthday() != null) {
            birthday = new ru.exxo.ExxoDate(emp.getBirthday());
        } else {
            birthday = new ru.exxo.ExxoDate();
        }
        if (emp.getWorksince() != null) {
            work = new ru.exxo.ExxoDate(emp.getWorksince());
        } else {
            work = new ru.exxo.ExxoDate();
        }

    }

    public void initUser() throws ClassNotFoundException, SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        login = myLogo(emp.getFio(), sdf.format(emp.getBirthday()));
    }

    public void updateBirthday() throws ParseException {
        if (birthdayString != null && !birthdayString.equals("")) {
            birthday = new ru.exxo.ExxoDate(birthdayString);
            emp.setBirthday(birthday.getDate());
        }
        if (workString != null && !workString.equals("")) {
            work = new ru.exxo.ExxoDate(workString);
            emp.setWorksince(work.getDate());
        }
    }

    private Connection startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + BASE.Ver.getDBHost() + "/" + BASE.Ver.getDBName();
        Properties properties = BASE.Ver.getDBProp();
        return DriverManager.getConnection(url, properties);

    }

    public void loadImg() throws FileUploadException, UnsupportedEncodingException, IOException, ClassNotFoundException, SQLException, ParseException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String res = "";
        String id = "0";
        byte bytes[] = null;
        String format = null;
        String type = "";
        long length = 0;
        String fname = null;
        Properties prop = new Properties();
        updateBirthday();
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

            format = ru.exxo.IMGES.getFormat(bytes, type);
            if (format.equals("noresult")) {
                throw new imgFormatException("Файл не является изображением, либо формат файла не поддерживается!");
            }
            //  bytes=ru.exxo.IMGES.cropResize(bytes, 110, 147, format);
            bytes = ru.exxo.IMGES.preCropResize(bytes, 480, "png", 147, 110);
            if (bytes == null) {
                throw new imgException("Размер изображения не должен быть меньше 110px*147px");
            }

            Connection con = startSQL();

            String INSERT_PICTURE = "INSERT INTO employeeTMP (photo,id, photo_type) VALUES (?, ?::int,?)";

            PreparedStatement ps = null;
            try {

                con.setAutoCommit(false);

                ps = con.prepareStatement(INSERT_PICTURE);
                ps.setString(2, id);
                ps.setBytes(1, bytes);
                ps.setString(3, type);
                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                res = e.getLocalizedMessage();
            } finally {
                ps.close();
                con.close();
            }
        } catch (imgException ex) {
            error = ex.toString();
        } catch (imgFormatException ex) {
            error = ex.toString();
        }

    }

    public String insert() throws ClassNotFoundException, SQLException, IOException {
        Connection con = startSQL();
        if (!pass.equals(pass1)) {
            errorMessage = "Пароли не совпадают";
            return null;
        }
        if (!new BASE.CHECK().checkEmpINS()) {
            errorMessage = "Количество сотрудников достигло максимального значения, позволенного вашей лицензией!";
            return null;
        }
        Statement stmt = con.createStatement();

        String sql = "SELECT * FROM users WHERE login = '" + login + "'";
        ResultSet resultSet = stmt.executeQuery(sql);
        if (resultSet.next()) {
            errorMessage = "Такой логин уже есть";
            return null;
        }
        int id = new ru.exxo.RegistrationSeq().employee_id_seq();
        sql = "SELECT photo, photo_type FROM employeeTMP WHERE id='0' ";

        resultSet = stmt.executeQuery(sql);
        byte[] b = null;
        if (resultSet.next()) {
            b = resultSet.getBytes(1);
            String format = ru.exxo.IMGES.getFormat(b, resultSet.getString(2));
            b = ru.exxo.IMGES.cropResize(b, 110, 147, "png");
        }

        String INSERT_PICTURE = "INSERT INTO employee (fio,unit,position,birthday,head,education,tellocal,tellmob,"
                + "email, responsibility, tel, room, office, photo, workSince, id) values "
                + "(?, ?, ?, ?::Date, ?::smallint, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::Date, ?)";
        con.setAutoCommit(false);

        if (workString.equals("")) {
            workString = null;
        }
        PreparedStatement ps = con.prepareStatement(INSERT_PICTURE);
        ps.setString(1, emp.getFio());
        ps.setInt(2, emp.getUnit());
        ps.setString(3, emp.getPosition());
        ps.setString(4, birthdayString);
        int head = 0;
        if (emp.isHead()) {
            head = 1;
        }
        ps.setInt(5, head);
        ps.setString(6, emp.getEducation().replace("\n<br>", "<br>").replace("\n", "<br>"));
        ps.setString(7, emp.getTellocal());
        ps.setString(8, emp.getTellmob());
        ps.setString(9, emp.getEmail());
        ps.setString(10, emp.getResponsibility().replace("\n<br>", "<br>").replace("\n", "<br>"));
        ps.setString(11, emp.getTel());
        ps.setString(12, emp.getRoom());
        ps.setString(13, emp.getOffice());
        ps.setBytes(14, b);
        ps.setString(15, workString);
        ps.setInt(16, id);
        ps.executeUpdate();

        sql = "INSERT INTO users (id,login,pass) VALUES(" + id + ",'" + login + "',MD5('" + pass + "'))";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO user_roles VALUES ('" + login + "', 'boss'),('" + login + "', 'portal_user')";
        stmt.executeUpdate(sql);
        con.commit();
        ps.close();
        stmt.close();
        con.close();
        removeImg();
        emp = null;
        timer = "";
        birthdayString = "";
        birthdayScript = "";
        workString = "";
        workScript = "";
        supervizor = "Нажмите, чтобы выбрать подразделение";
        file = null;
        errorMessage = "";
        return "finish.xhtml?faces-redirect=true";
    }

    public void removeImg() throws ClassNotFoundException, SQLException {
        Connection con = startSQL();
        String sql = "DELETE FROM employeeTMP WHERE id=0";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();

    }

    private static final String[] charTable = new String[81];

    private static final char START_CHAR = 'Ё';

    static {
        charTable['А' - START_CHAR] = "A";
        charTable['Б' - START_CHAR] = "B";
        charTable['В' - START_CHAR] = "V";
        charTable['Г' - START_CHAR] = "G";
        charTable['Д' - START_CHAR] = "D";
        charTable['Е' - START_CHAR] = "E";
        charTable['Ё' - START_CHAR] = "E";
        charTable['Ж' - START_CHAR] = "ZH";
        charTable['З' - START_CHAR] = "Z";
        charTable['И' - START_CHAR] = "I";
        charTable['Й' - START_CHAR] = "I";
        charTable['К' - START_CHAR] = "K";
        charTable['Л' - START_CHAR] = "L";
        charTable['М' - START_CHAR] = "M";
        charTable['Н' - START_CHAR] = "N";
        charTable['О' - START_CHAR] = "O";
        charTable['П' - START_CHAR] = "P";
        charTable['Р' - START_CHAR] = "R";
        charTable['С' - START_CHAR] = "S";
        charTable['Т' - START_CHAR] = "T";
        charTable['У' - START_CHAR] = "U";
        charTable['Ф' - START_CHAR] = "F";
        charTable['Х' - START_CHAR] = "H";
        charTable['Ц' - START_CHAR] = "C";
        charTable['Ч' - START_CHAR] = "CH";
        charTable['Ш' - START_CHAR] = "SH";
        charTable['Щ' - START_CHAR] = "SH";
        charTable['Ъ' - START_CHAR] = "'";
        charTable['Ы' - START_CHAR] = "Y";
        charTable['Ь' - START_CHAR] = "'";
        charTable['Э' - START_CHAR] = "E";
        charTable['Ю' - START_CHAR] = "U";
        charTable['Я' - START_CHAR] = "YA";

        for (int i = 0; i < charTable.length; i++) {
            char idx = (char) ((char) i + START_CHAR);
            char lower = new String(new char[]{idx}).toLowerCase().charAt(0);
            if (charTable[i] != null) {
                charTable[lower - START_CHAR] = charTable[i].toLowerCase();
            }
        }
    }

    public static String translitText(String text) {
        char charBuffer[] = text.toCharArray();
        StringBuilder sb = new StringBuilder(text.length());
        for (char symbol : charBuffer) {
            int i = symbol - START_CHAR;
            if (i >= 0 && i < charTable.length) {
                String replace = charTable[i];
                sb.append(replace == null ? symbol : replace);
            } else {
                sb.append(symbol);
            }
        }
        return sb.toString();
    }

    private String myLogo(String fio, String year) throws ClassNotFoundException, SQLException {
        String r = translitText(fio);
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
        if (checkLogo(fname.toLowerCase())) {
            r = fname.toLowerCase();
        } else if (checkLogo(fname)) {
            r = fname;
        } else if (checkLogo(fname + "_" + sname.charAt(0))) {
            r = fname + "_" + sname.charAt(0);
        } else if (checkLogo((fname + "_" + sname.charAt(0)).toLowerCase())) {
            r = (fname + "_" + sname.charAt(0)).toLowerCase();
        } else if (checkLogo(sname)) {
            r = sname;
        } else if (checkLogo(sname.toLowerCase())) {
            r = sname.toLowerCase();
        } else if (checkLogo(sname + year)) {
            r = sname + year;
        } else if (checkLogo(sname.toLowerCase() + year)) {
            r = sname.toLowerCase() + year;
        } else if (checkLogo(sname + "_" + year)) {
            r = sname + "_" + year;
        } else if (checkLogo(sname.toLowerCase() + "_" + year)) {
            r = sname.toLowerCase() + "_" + year;
        } else if (checkLogo(fname + year)) {
            r = fname + year;
        } else if (checkLogo(fname.toLowerCase() + year)) {
            r = fname.toLowerCase() + year;
        } else if (checkLogo(fname + "_" + year)) {
            r = fname + "_" + year;
        } else if (checkLogo(fname.toLowerCase() + "_" + year)) {
            r = fname.toLowerCase() + "_" + year;
        } else if (checkLogo(sname + "_" + fname.charAt(0))) {
            r = sname + "_" + fname.charAt(0);
        } else if (checkLogo((sname + "_" + fname.charAt(0)).toLowerCase())) {
            r = (sname + "_" + fname.charAt(0)).toLowerCase();
        } else if (checkLogo(fname + "_" + sname)) {
            r = fname + "_" + sname;
        } else if (checkLogo((fname + "_" + sname).toLowerCase())) {
            r = (fname + "_" + sname).toLowerCase();
        } else if (checkLogo(sname + "_" + fname)) {
            r = sname + "_" + fname;
        } else if (checkLogo((sname + "_" + fname).toLowerCase())) {
            r = (sname + "_" + fname).toLowerCase();
        } else if (checkLogo(r)) {
            r = r;
        } else if (checkLogo(r.toLowerCase())) {
            r = r.toLowerCase();
        } else if (checkLogo("superchempion")) {
            r = "superchempion";
        } else {
            r = "";
        }
        return r;
    }

    public boolean checkLogo(String l) throws ClassNotFoundException, SQLException {
        boolean r = true;
        if (l.length() >= 4) {
            Connection con = startSQL();;
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

    public ru.exxo.Employee getEmp() {
        return emp;

    }

    public String getTimer() {
        timer = System.currentTimeMillis() + "";
        return timer;
    }

    public ru.exxo.Offices getOffice() {
        return office;
    }

    public String getBirthdayString() {
        if (birthday != null) {
            birthdayString = birthday.getDateString();
        }
        return birthdayString;
    }

    public void setBirthdayString(String birthdayString) throws ParseException {
        this.birthdayString = birthdayString;
        birthday.setDateString(birthdayString);
    }

    public String getBirthdayScript() {
        if (birthday != null) {
            birthdayScript = birthday.getDateScript();
        }
        return birthdayScript;
    }

    public void setBirthdayScript(String birthdayScript) {
        this.birthdayScript = birthdayScript;
    }

    public String getWorkString() {
        if (work != null) {
            workString = work.getDateString();
        }
        return workString;
    }

    public void setWorkString(String workString) throws ParseException {
        this.workString = workString;
        work.setDateString(workString);
    }

    public String getWorkScript() {
        if (work != null) {
            workScript = work.getDateScript();
        }
        return workScript;
    }

    public void setWorkScript(String workScript) {
        this.workScript = workScript;
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

    public ru.exxo.ExxoDate getBirthday() {
        return birthday;
    }

    public ru.exxo.ExxoDate getWork() {
        return work;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
