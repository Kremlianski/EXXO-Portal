package BEANS;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;
//import java.util.Properties;
import java.util.List;
import java.util.Properties;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import javax.faces.context.*;
//import javax.servlet.ServletException;
//import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@ManagedBean
@RequestScoped
public class settingQwestBean {
boolean anonim = false;
String question="";
String answers="";
String start="";
String stop="";
Connection con=null;
Statement stmt=null;
String id=null;
HttpSession session=null;
HttpServletRequest req =null;
public void initParams(String id) throws ClassNotFoundException, SQLException, IOException{
     req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
       HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
       session=req.getSession(true);
       String role=(String)session.getAttribute("role");
       boolean yes=false;
       if(role.indexOf("a")>=0||role.indexOf("j")>=0) yes=true;
       if (yes) {
    if(id!=null&&!id.equals("")){
    startSQL();
    String sql="SELECT anonim, question, answers, start, stop FROM qwests WHERE quest_id='"+id+"'";
    ResultSet rs = stmt.executeQuery(sql);
    if(rs.next()){
    anonim=rs.getBoolean("anonim");
    question=rs.getString("question");
    answers=rs.getString("answers");
    start=rs.getString("start");
    stop=rs.getString("stop");
    this.id=id;
    }
    
    rs.close();
    stmt.close();
    con.close();
    }} else res.sendRedirect("notPermited.html");
}
public boolean getAnonim(){
    return anonim;
}
public void setAnonim(boolean l){
    anonim=l;
}

public String getQuestion(){
    return question;
}
public void setQuestion(String l){
    question=l;
}
public String getAnswers(){
    return answers;
}
public void setAnswers(String l){
    answers=l;
}
public String getStart(){
    return start;
}
public void setStart(String l){
    start=l;
}

public String getStop(){
    return stop;
}
public void setStop(String l){
    stop=l;
}

public String getId(){
    return id;
}
public void setId(String l){
    id=l;
}
void startSQL() throws ClassNotFoundException, SQLException{
 Class.forName("org.postgresql.Driver");

      String url ="jdbc:postgresql://"+BASE.VER.getDBHost()+"/"+BASE.VER.getDBName();
      Properties properties=new Properties();
      properties=BASE.VER.getDBProp();

      con = DriverManager.getConnection(url,properties);


     stmt = con.createStatement();
}
public void insertData() throws ClassNotFoundException, SQLException, IOException{
    if(id!=null&&!id.equals("")){
    startSQL();
       String sql="UPDATE qwests SET  anonim='"+anonim+"', question='"+question+"', answers='"+answers+"'"
        + ", start='"+start+"', stop='"+stop+"' WHERE quest_id='"+id+"'";
    stmt.executeUpdate(sql);
        } else {
         startSQL();
         String sql = "INSERT INTO qwests (anonim,question,answers,start,stop) VALUES('"+anonim+"','"+question+"','"+answers+"','"+start+"','"+stop+"')";
         stmt.executeUpdate(sql);
      }
    
stmt.close();
con.close();
HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
res.sendRedirect("settingQwest.jsp");
  }
public String getList() throws JDOMException, IOException {
    String l="";
    SAXBuilder builder = new SAXBuilder();
    String s=answers;
    if (s.equals("")) s="<vote/>";
    InputStream is = new ByteArrayInputStream(s.getBytes("UTF8"));
    
Document document = (Document) builder.build(is);

Element rootNode = document.getRootElement();
List list = rootNode.getChildren("item");
for (int i = 0; i < list.size(); i++) {
 
		   Element node = (Element) list.get(i);
 
		   l+="<div id='" + node.getChildText("id")+"' class='answerDiv'><span class='itemB'></span><span class='itemText'>"+node.getChildText("text")+"</span></div>";
		   }
    return l;
}
}
