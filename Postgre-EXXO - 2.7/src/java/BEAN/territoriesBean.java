package BEAN;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import ru.EXXO.employee.Office;
import ru.EXXO.employee.OfficeDAO;

@ManagedBean
@RequestScoped
public class territoriesBean {

    private ArrayList<Office> o;

    public String init() throws SQLException {
        o = new OfficeDAO().getList();

        return null;

    }

    public ArrayList<Office> getO() {
        return o;
    }

    public void setO(ArrayList<Office> o) {
        this.o = o;
    }

    public String encode(String s) throws UnsupportedEncodingException {
        return java.net.URLEncoder.encode(s, "utf8");
    }
}
