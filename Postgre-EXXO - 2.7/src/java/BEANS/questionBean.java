package BEANS;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
//import java.util.Properties;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class questionBean {

    public String getList(String id, HttpServletRequest r) throws ClassNotFoundException, SQLException, UnsupportedEncodingException, JDOMException, IOException {

        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM qwests WHERE quest_id='" + id + "'";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "";
        if (rs.next()) {
            Date startD = rs.getDate("start");
            Date stopD = rs.getDate("stop");
            Date now = new Date();
            if (startD.before(now) && stopD.after(now)) {

                String question = rs.getString("question");
                String answers = rs.getString("answers");
                String anonim = "";
                if (rs.getBoolean("anonim")) {
                    anonim = "Анонимное голосование";
                }
                SAXBuilder builder = new SAXBuilder();
                list += "<div id='voteForm' class='exxo-shadow'><form action='voting' method='POST'><h2>" + question + "</h2>" + anonim
                        + "<div id='variants'>";
                InputStream is = new ByteArrayInputStream(answers.getBytes("UTF8"));

                Document document = (Document) builder.build(is);

                Element rootNode = document.getRootElement();
                List lists = rootNode.getChildren("item");

                for (int i = 0; i < lists.size(); i++) {

                    Element node = (Element) lists.get(i);

                    list += "<div><input type='radio' name='vote' value='" + node.getChildText("text") + "'><span>" + node.getChildText("text") + "</span></div>";
                }
                list += "</div><input type='hidden' value='" + id + "' name='quest_id'><input type='submit' value='Отправить'></form></div>";
            } else {
                list = "Сейчас не время обсуждать этот вопрос";
            }
        }

        rs.close();
        stmt.close();
        con.close();
        return list;
    }
}
