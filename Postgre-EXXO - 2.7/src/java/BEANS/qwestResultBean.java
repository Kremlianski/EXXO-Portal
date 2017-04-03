package BEANS;

import java.io.IOException;
//import javax.faces.bean.SessionScoped;
//import java.sql.SQLException;  
import javax.faces.bean.ManagedBean;
import javax.servlet.jsp.jstl.sql.Result;
import EXXOlib.JdbcUtile;
import java.sql.*;
//import java.util.Properties;
import javax.faces.bean.RequestScoped;
import javax.faces.context.*;
//import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class qwestResultBean {

    private Result qwestResultData;
    private boolean anonim = false;
    private String question = "";
    HttpSession session;
    String id = null;
    HttpServletRequest req = null;

    public void initParams(String id) throws IOException, ClassNotFoundException, SQLException {
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("j") >= 0) {
            yes = true;
        }
        if (yes) {
            this.id = id;
        } else {
            res.sendRedirect("notPermited.html");
        }
        ServletContext sc = req.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT question, anonim FROM qwests WHERE quest_id='" + id + "'";
        ResultSet rs = stmt.executeQuery(sql);
        String l = "";
        if (rs.next()) {
            question = rs.getString(1);
            anonim = rs.getBoolean(2);
        }
        rs.close();
        stmt.close();
        con.close();

    }

    public void setQuestion(String q) {
        question = q;
    }

    public void setAnonim(boolean q) {
        anonim = q;
    }

    public Result getQwestResultData() throws SQLException, ClassNotFoundException {
        String sql = "select answer, count(*) AS result, count(*)*100/(SELECT count(*) FROM answers where quest_id='" + id + "') AS procent FROM answers where quest_id='" + id + "' group by answer ORDER BY result DESC";
        populateEmpResultData(sql);
        return qwestResultData;
    }

    public Result getQwestList() throws SQLException, ClassNotFoundException {
        String sql = "select user_id, answer, fio from answers, employee where quest_id='" + id + "' AND user_id=employee.id ORDER BY fio";
        populateEmpResultData(sql);
        return qwestResultData;
    }

    private void populateEmpResultData(String sql) throws SQLException, ClassNotFoundException {
        qwestResultData
                = JdbcUtile.runQuery(sql, -1, req.getServletContext());
    }

    public boolean getAnonim() {
        return anonim;
    }

    public String getQuestion() {
        return question;
    }
}
