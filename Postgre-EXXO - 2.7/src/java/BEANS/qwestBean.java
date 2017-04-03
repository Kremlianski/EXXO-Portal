package BEANS;

import java.io.UnsupportedEncodingException;
import java.sql.*;
//import java.util.Properties;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
//import java.net.URLEncoder;

public class qwestBean {

    public String getList(String user, HttpServletRequest r) throws ClassNotFoundException, SQLException, UnsupportedEncodingException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT quest_id, question, start, stop FROM qwests "
                + "WHERE (SELECT count(*)=0 FROM answers "
                + "WHERE qwests.quest_id=answers.quest_id AND user_id='" + user + "')order by stop";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<table id=\"tableList\"><tbody><tr><th>Вопрос</th><th>Начало опроса</th><th>Завершение опроса</th></tr>";
        //   String op="";
        while (rs.next()) {
            Date startD = rs.getDate("start");
            Date stopD = rs.getDate("stop");
            Date now = new Date();
            if (startD.before(now) && stopD.after(now)) {
                list += "<tr class='datas'><td class='first' id='" + rs.getString("quest_id") + "'>" + rs.getString("question") + "</td><td class='second'>" + rs.getString("start") + "</td><td class='third'>" + rs.getString("stop") + "</td></tr>";
//     op+="<option value='"+rs.getString("short")+"'>"+rs.getString("short")+"</option>";
            }
        }
        list += "</tbody></table>";

        rs.close();
        stmt.close();
        con.close();
        return list;
    }
}
