package BEANS;

import java.io.IOException;
import javax.faces.bean.SessionScoped;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.servlet.jsp.jstl.sql.Result;
import EXXOlib.JdbcUtile;
import java.io.Serializable;
import javax.faces.context.*;
//import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class qwestListBean implements Serializable {

    private Result qwestResultData;
    HttpSession session = null;
    HttpServletRequest req = null;

    public Result getQwestResultData() throws SQLException, ClassNotFoundException, IOException {
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        session = req.getSession(true);
        String role = (String) session.getAttribute("role");
        boolean yes = false;
        if (role.indexOf("a") >= 0 || role.indexOf("j") >= 0) {
            yes = true;
        }
        if (yes) {
            populateEmpResultData();
        } else {
            res.sendRedirect("notPermited.html");
        }
        return qwestResultData;
    }

    private void populateEmpResultData() throws SQLException, ClassNotFoundException {
        qwestResultData
                = JdbcUtile.runQuery("SELECT * FROM qwests ORDER BY stop DESC", -1, req.getServletContext());
    }

}
