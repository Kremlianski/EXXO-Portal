package BEANS;

import java.sql.*;
//import java.util.Properties;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class EmpInsBean {

    public String fio = null;
    public String unit = null;
    public String position = null;
    public String birthday = null;
    public String head = null;
    public String education = null;
    public String tel = null;
    public String tellocal = null;
    public String tellmob = null;
    public String email = null;
    public String hobby = null;
    public String comment = null;
    public String responsibility = null;
    public String supervizor = null;
    public String birthdayString = null;
    public String workString = null;
    public String room = null;
    public String office = null;
    public String workSince = null;
    public String op = "";
    public String photoMarker = null;
    public String timer = null;

    public void setParams(String TempImged, String Re, String Message, String Id, HttpServletRequest r) throws ClassNotFoundException, SQLException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        Statement stmt1 = con.createStatement();
        ResultSet rs = null;

        if (TempImged == null && Re == null && Message == null) {

//     if(request.getParameter("id")!=null) id=request.getParameter("id");
            String sql = "DELETE FROM employeeTMP WHERE id='" + Id + "'";
            stmt.executeUpdate(sql);

        } else {

            String sql = "SELECT * FROM employeeTMP WHERE id='" + Id + "'";

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                fio = rs.getString("fio");
                unit = rs.getString("unit");
                position = rs.getString("position");
                birthday = rs.getString("birthday");
                head = rs.getString("head");
                education = rs.getString("education");
                tellocal = rs.getString("tellocal");
                tellmob = rs.getString("tellmob");
                email = rs.getString("email");
                hobby = rs.getString("hobby");
                comment = rs.getString("comment");
                responsibility = rs.getString("responsibility");
                supervizor = rs.getString("supervizor");
                tel = rs.getString("tel");
                room = rs.getString("room");
                office = rs.getString("office");
                workSince = rs.getString("workSince");
                timer = System.currentTimeMillis() + "";

                InputStream is = rs.getBinaryStream("photo");
                if (is != null && Re == null && Message == null) {
                    photoMarker = "ok";
                    rs.close();
                } else {

                    String sql1 = "DELETE FROM employeeTMP WHERE id='" + Id + "'";
                    stmt1.executeUpdate(sql1);

                }

            }

            if (birthday != null) {
                birthdayString = "new Date(" + birthday.replace('-', ',') + ")";
            }
            if (workSince != null) {
                workString = "new Date(" + workSince.replace('-', ',') + ")";
            }

        }

        String sql1 = "SELECT short FROM offices";
        ResultSet rs1 = stmt.executeQuery(sql1);
        String selected = "";
        while (rs1.next()) {
            selected = "";
            if (!rs1.getString("short").equals("Переезд")) {
                if (rs1.getString("short").equals(office)) {
                    selected = "SELECTED";
                }
                op += "<option value='" + rs1.getString("short") + "' " + selected + ">" + rs1.getString("short") + "</option>";
            }
        }

        rs1.close();
        stmt.close();
        stmt1.close();
        con.close();

    }

}
