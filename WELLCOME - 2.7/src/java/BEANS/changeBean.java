package BEANS;

import java.io.IOException;
//import java.security.Principal;
//import java.sql.Statement;
//import java.sql.Connection;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
import javax.faces.context.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class changeBean implements Serializable {

    String user = "";
    String pass = "";
    String pass1 = "";
    String massage = "";
    String action = "change.xhtml";
    boolean logout = false;
    private Connection con = null;
    private Statement stmt = null;

    public void setUser(String u) {
        user = u;
    }

    public void setPass(String p) {
        pass = p;
    }

    public void setAction(String p) {
        action = p;
    }

    public String getAction() {
        return action;
    }

    public String getUser() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        user = "";
        if (req.getUserPrincipal() != null) {
            user = req.getUserPrincipal().getName();
        }
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

    public void change() throws ClassNotFoundException, SQLException, IOException, ServletException, InterruptedException {
        startSQL();
        String login = null;
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            res.sendRedirect("index.xhtml");
        } else {
            login = req.getUserPrincipal().getName();
        }
        if (user.equals("")) {
            massage += "Введите логин! ";
        }
        if (pass.equals("")) {
            massage += "Введите новый пароль! ";
        }
        if (!pass.equals(pass1)) {
            massage += "Вы ввели разные пароли! ";
        }
        String regexp = "^\\w{6,}$";
        Pattern p = Pattern.compile(regexp);
        Pattern p1 = Pattern.compile("^\\w{4,}$");
        if (!p.matcher(pass).find()) {
            massage += "Пароль должен содержать латинские буквы (строчные и заглавные), цифры и _. Должен содержать не менее 6 символов! ";
        }
        if (!p1.matcher(user).find()) {
            massage += "Логин должен содержать латинские буквы (строчные и заглавные), цифры и _. Должен содержать не менее 4 символов! ";
        }
        if (massage.equals("")) {
            if (user.equals(login)) {
                String sql = "UPDATE users SET pass=MD5('" + pass + "') WHERE login = '" + login + "'";
                stmt.executeUpdate(sql);
            } else {
                String sql = "SELECT * FROM users WHERE login = '" + user + "'";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    massage = "Введенное значение логин не уникально!";
                } else {
                    if (login.equals("root")) {
                        user = "root";
                    }
                    sql = "UPDATE users SET pass=MD5('" + pass + "'), login='" + user + "' WHERE login = '" + login + "'";
                    if (stmt.executeUpdate(sql) > 0) {
                        logout = true;
                    }
                }
            }
        }
        stmt.close();
        con.close();
        if (logout) {
            req.logout();
        }
        if (massage.equals("")) {
            action = "yes.xhtml";
        }
        //res.sendRedirect(BASE.VER.getDBRedirect());
    }

    void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = new Properties();
        properties = BASE.VER.getDBProp();

        con = DriverManager.getConnection(url, properties);
        stmt = con.createStatement();
    }

    public String getMassage() throws IOException {
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        if (req.getUserPrincipal() == null) {
            res.sendRedirect("index.xhtml");
        }
        return massage;
    }

    public void setMassage() {
        massage = "";
    }
}
