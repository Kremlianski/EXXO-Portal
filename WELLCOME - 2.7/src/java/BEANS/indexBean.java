package BEANS;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@SessionScoped
public class indexBean implements Serializable {

    String user = "";
    String pass = "";
    Boolean change = false;
    String redirect = "";
    String massage = "";
    int count = 0;

    public String getMassage() {
        org.apache.catalina.realm.LockOutRealm realm = new org.apache.catalina.realm.LockOutRealm();
        int minut = realm.getLockOutTime();
        int trial = realm.getFailureCount();
        if (trial >= 4) {
            if (count > trial - 3) {
                massage = count + " неудачная попытка";
            }
            if (count == trial - 2) {
                massage = count + " неудачная попытка. Проверьте, включен ли английский язык. Проверьте, не включен ли Caps Lock. Важно: пароль и логин могут содержать как строчные, так и прописные буквы. ";
            }
            if (count == trial - 1) {
                massage = "У вас последняя попытка!";
            }
            if (count == trial) {
                massage = "Пользователь " + user + " заблокирован на " + minut + " секунд.";
            }
            if (count > trial) {
                massage = "";
            }
        }
        return massage;
    }

    public void setMassage() {
        massage = "";
    }

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

    public boolean getChange() {
        return change;
    }

    public void setChange(boolean c) {
        redirect = BASE.VER.getDBRedirect();
        change = c;
        if (change) {
            redirect = "change.xhtml";
        }
    }

    public void log() throws IOException, ClassNotFoundException, SQLException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        count++;
        try {
            req.login(user, pass);
        } catch (ServletException ex) {
            Logger.getLogger(indexBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Principal p = req.getUserPrincipal();
            res.sendRedirect(redirect);
        }
    }

    Connection startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = new Properties();
        properties = BASE.VER.getDBProp();

        return DriverManager.getConnection(url, properties);

    }

    public Boolean isGuest() throws ClassNotFoundException, SQLException {
        Connection con = startSQL();
        Statement stmt = con.createStatement();
        Boolean res = false;
        String sql = "SELECT id FROM users WHERE login='guest'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            res = true;
        }
        rs.close();
        stmt.close();
        con.close();
        return res;
    }

    public void logGuest() throws ServletException, IOException, ClassNotFoundException, SQLException {
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Connection con = startSQL();
        Statement stmt = con.createStatement();
        String sql = "SELECT id, pass FROM users WHERE login='guest'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            pass = rs.getString(3);

            req.login("guest", pass);
            Principal p = req.getUserPrincipal();
        }
        rs.close();
        stmt.close();
        con.close();
        res.sendRedirect(BASE.VER.getDBRedirect());
    }

    public boolean startRedirect() {
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
