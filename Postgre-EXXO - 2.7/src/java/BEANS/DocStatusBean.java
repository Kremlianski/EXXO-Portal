
package BEANS;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.jsp.jstl.sql.Result;
import EXXOlib.JdbcUtile;
import java.sql.SQLException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@RequestScoped
public class DocStatusBean {

    private Result myResultData;
    HttpServletRequest req = null;

    public void getList(String id) throws SQLException, ClassNotFoundException {
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String sql = "SELECT properTime(data) AS d, get_fio(owner) AS fio,owner FROM sign WHERE ver_id=" + id;
        //  String sql="SELECT 'Круто','COOL'";
        populateMyResultData(sql);

    }

    private void populateMyResultData(String sql) throws SQLException, ClassNotFoundException {
        myResultData
                = JdbcUtile.runQuery(sql, -1, req.getServletContext());
    }

    public Result getMyResultData() {
        return myResultData;
    }
}
