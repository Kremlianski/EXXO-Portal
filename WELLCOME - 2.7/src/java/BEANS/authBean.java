package BEANS;

import BASE.CASE;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Properties;

@ManagedBean
@SessionScoped
public class authBean implements Serializable {

    String key = "";
    private static final String aist = "EXXO";
    private Connection con = null;
    private Statement stmt = null;

    public String getKey() {
        return key;
    }

    public void setKey(String k) {
        key = k;
    }

    public Boolean isUser(String user) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return req.isUserInRole(user);
    }

    void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = new Properties();
        properties = BASE.VER.getDBProp();

        con = DriverManager.getConnection(url, properties);
        stmt = con.createStatement();
    }

    public void register() throws ClassNotFoundException, SQLException, IOException {
        if (CASE.isKey(key)) {
            startSQL();

            String sql = "INSERT INTO register VALUES ( '" + key + "','" + aist + "')";
            key = "";
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
        }
        /*else {
        HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        res.sendRedirect("index.xhtml");
        }
         * 
         */
    }

    public Boolean isActivated() throws ClassNotFoundException, SQLException {
        startSQL();
        String k = "";
        String sql = "SELECT key FROM register WHERE reg = '" + aist + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            k = rs.getString(1);
        }
        rs.close();
        stmt.close();
        con.close();
        return BASE.CASE.isKey(k);
    }

    public String getCount() throws ClassNotFoundException, SQLException {
        startSQL();
        String sql = "SELECT key FROM register WHERE reg = '" + aist + "'";
        ResultSet rs = stmt.executeQuery(sql);
        String res = "Демонстрация с правом использования для 5 сотрудников";
        boolean start = false;
        boolean nolimited = false;
        int count = 5;

        while (rs.next()) {
            String key = rs.getString(1);
            if (CASE.isKey(key)) {
                if (key.startsWith("a")) {
                    res = "Лицензия на неограниченное число сотрудников. Дополнительные ключи не требуются.";
                    nolimited = true;
                    break;
                } else if (key.startsWith("b")) {
                    count += 25;//начиная с версии 2.2 до этого было 30
                } else if (key.startsWith("c")) {
                    count += 100;
                } else if (key.startsWith("d")) {
                    count += 10;
                } else if (key.startsWith("e")) {
                    count += 45;
                }
                res = "Лицензия на " + count + " сотрудников";
            }

        }
        rs.close();
        stmt.close();
        con.close();
        return res;

    }
}
