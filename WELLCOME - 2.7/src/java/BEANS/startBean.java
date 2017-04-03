package BEANS;

import java.sql.Statement;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@SessionScoped
public class startBean implements Serializable {

    String user = "";
    String pass = "";
    String massage = "";
    String message = "";
    String fio = "";
    private Connection con = null;
    private Statement stmt = null;
    String id = "";
    String year = "";
    String logo = "";
    String pass1 = "";
    String pass2 = "";

    public void setUser(String u) {
        user = u;
    }

    public void setPass(String p) {
        pass = p;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String p) {
        pass1 = p;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String p) {
        pass2 = p;
    }

    public void log() throws ClassNotFoundException, SQLException, IOException {
        startSQL();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String fio_login_pass = BASE.VER.getParam("fio_login_pass");
        if (fio_login_pass == null) {
            fio_login_pass = "iLikeEXXO";
        }
        if (!fio_login_pass.equals(pass)) {
            massage += "Неверный пароль! ";
        }
        String sql = "SELECT id, EXTRACT(year FROM birthday) FROM employee WHERE fio='" + user + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            id = rs.getString("id");
            year = rs.getString(2);
            sql = "SELECT * FROM users WHERE id='" + id + "'";
            ResultSet rs1 = stmt.executeQuery(sql);
            if (rs1.next()) {
                massage += "Пользователь с такими данными уже прошел регистрацию! ";
            }
            rs1.close();
        } else {
            massage += "Пользователь с такими данными отсутствует в базе данных! ";
        }
        fio = EXXOlib.textLib.translitText(user);
        logo = myLogo();
        rs.close();
        stmt.close();
        con.close();
        if (!massage.equals("")) {
            res.sendRedirect("start.xhtml");
        }
    }

    void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = new Properties();
        properties = BASE.VER.getDBProp();

        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(url, properties);
        }
        if (stmt == null || stmt.isClosed()) {
            stmt = con.createStatement();
        }
    }

    void stopSQL() throws SQLException {
        if (stmt != null && !stmt.isClosed()) {
            stmt.close();
        }
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }

    public String getMassage() throws IOException, SQLException {
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getUserPrincipal() != null || !isPermitted()) {
            res.sendRedirect(BASE.VER.getDBRedirect());
        }
        stopSQL();
        return massage;
    }

    public void setMassage() throws SQLException {
        massage = "";
        stopSQL();
    }

    public String getMessage() throws IOException, SQLException {
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getUserPrincipal() != null || !isPermitted()) {
            res.sendRedirect(BASE.VER.getDBRedirect());
        }
        stopSQL();
        return message;
    }

    public void setMessage() throws SQLException {
        message = "";
        stopSQL();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String s) {
        logo = s;
    }

    String myLogo() throws ClassNotFoundException, SQLException {
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

    boolean checkLogo(String l) throws ClassNotFoundException, SQLException {
        boolean r = true;
        if (l.length() >= 4) {
            startSQL();
            String sql = "SELECT * FROM users WHERE login='" + l + "'";
            r = stmt.executeQuery(sql).next();
            stmt.close();
            con.close();
            return !r;
        } else {
            return false;
        }
    }

    public void change() throws ClassNotFoundException, SQLException, IOException, ServletException {
        startSQL();
        String login = null;
        boolean go = false;
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (logo.equals("")) {
            message += "Введите логин! ";
        }
        if (pass1.equals("")) {
            message += "Введите новый пароль! ";
        }
        if (!pass1.equals(pass2)) {
            message += "Вы ввели разные пароли! ";
        }
        String regexp = "^\\w{6,}$";
        Pattern p = Pattern.compile(regexp);
        Pattern p1 = Pattern.compile("^\\w{4,}$");
        if (!p.matcher(pass1).find()) {
            message += "Пароль должен содержать латинские буквы (строчные и заглавные), цифры и _. Должен содержать не менее 6 символов! ";
        }
        if (!p1.matcher(logo).find()) {
            message += "Логин должен содержать латинские буквы (строчные и заглавные), цифры и _. Должен содержать не менее 4 символов! ";
        }
        if (message.equals("")) {
            String sql = "SELECT * FROM users WHERE login = '" + logo + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                message = "Введенное значение логин не уникально. Выберите другое! ";
            } else {
                sql = "INSERT INTO users (id, pass, login) VALUES(" + id + ", MD5('" + pass1 + "'), '" + logo + "')";
                if (stmt.executeUpdate(sql) > 0) {
                    sql = "INSERT INTO user_roles VALUES ( '" + logo + "', 'portal_user')";
                    if (stmt.executeUpdate(sql) > 0) {
                        go = true;
                    }
                }
            }
            rs.close();
        }
        stmt.close();
        con.close();
        if (go) {
            req.login(logo, pass1);
        }
        //   if(message.equals("")) res.sendRedirect(BASE.VER.getDBRedirect());
    }

    boolean isPermitted() {
        String fio_login = BASE.VER.getParam("fio_login");
        if (fio_login == null) {
            return true;
        } else if (fio_login.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }
}
