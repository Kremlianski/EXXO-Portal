package BEANS;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class changePicStatusBean {

    Connection con = null;
    Statement stmt = null;
    String id = null;
    String blockedText = null;
    boolean blocked = false;
    HttpSession session = null;
    HttpServletRequest req = null;
    String user = null;

    public void setId(String i) {
        id = i;
    }

    public String getId() {
        return id;
    }

    public void setBlockedText(String i) {
        blockedText = i;
    }

    public String getBlockedText() {
        return blockedText;
    }

    public void setBlocked(boolean b) {
        blocked = b;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setUser(String i) {
        user = i;
    }

    public String getUser() {
        return user;
    }

    public void initParams(String i) throws IOException, ClassNotFoundException, SQLException {
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        user = (String) session.getAttribute("id");
        boolean yes = false;
        if (i != null && !i.equals("")) {
            id = i;
        }
        if (role.indexOf("s") >= 0 || role.indexOf("d") >= 0) {
            yes = true;
        }
        if (yes) {
            startSQL();
            String sql = "SELECT blocked, blockedText, blockedPerson FROM gallaries WHERE id='" + id + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                blocked = rs.getBoolean("blocked");
                blockedText = rs.getString(2);
            }
            rs.close();
            stmt.close();
            con.close();

        } else {
            res.sendRedirect("notPermited.html");
        }

    }

    public void insertData() throws ClassNotFoundException, SQLException {
        startSQL();
        String sql = "UPDATE gallaries SET blocked='" + blocked + "', blockedText='" + blockedText + "', blockedPerson='" + user + "' WHERE id='" + id + "'";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = new Properties();
        properties = BASE.VER.getDBProp();

        con = DriverManager.getConnection(url, properties);

        stmt = con.createStatement();
    }
}
