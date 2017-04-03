package BEANS;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ApplicationScoped
public class MainBean implements Serializable {

    private String slogan = "";

    public String getSlogan() throws SQLException, ClassNotFoundException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        ServletContext sc = req.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT slogan FROM logo";
        ResultSet rs1 = stmt.executeQuery(sql);
        if (rs1.next()) {
            slogan = rs1.getString(1);
        }
        rs1.close();
        stmt.close();
        con.close();
        return slogan;
    }
}
