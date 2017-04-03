package Servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class finDocWork extends HttpServlet {
//Удаляет лишние версии (кроме последней), присваивает статус 2  
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, ClassNotFoundException, SQLException {
     request.setCharacterEncoding("utf-8");
     response.setCharacterEncoding("utf-8");

    String id=request.getParameter("id");
    HttpSession session=request.getSession(true);
        String role=(String)session.getAttribute("role");
        String owner=request.getParameter("owner");
        Boolean yes=true;
        String own="";
        if(owner!=null)  own= "?owner="+owner;
        yes=false;
        if(owner==null||(role.indexOf("o")>=0&&owner.equals("-100"))||(role.indexOf("p")>=0&&owner.equals("-101"))||
     (role.indexOf("q")>=0&&owner.equals("-102"))||(role.indexOf("r")>=0&&owner.equals("-103"))) yes=true;
        if(owner==null)  owner=(String)session.getAttribute("id");
        if(yes&&id!=null){
      

    ServletContext sc = request.getServletContext();
    Connection con = BASE.VER.getServletConnection(sc);
    Statement stmt = con.createStatement();
    con.setAutoCommit(false);
    con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
    
    
      String sql="SELECT * FROM drop_vers(?::Int, ?, ?)";
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setString(1, id);
      ps.setBoolean(2, false);
      ps.setInt(3, 1);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
      sql="DELETE FROM files_vers WHERE ver_id="+rs.getInt("ver_id");    
      stmt.executeUpdate(sql);    
      try {
       sql="DELETE FROM file_store WHERE id="+rs.getInt("ver_id");   
       Connection con2=  BASE.VER.getDocsConnection(sc, rs.getInt("store")); 
       Statement stmt1=con2.createStatement();
       stmt1.executeUpdate(sql);
       stmt1.close();
       con2.close();
      } catch(SQLException e){
       Statement stmt3=con.createStatement();
       Logger.getLogger(fileVer.class.getName()).log(Level.SEVERE, null, e);
       sql="INSERT INTO lost_vers VALUES ("+rs.getInt("ver_id")+", "+rs.getInt("store")+")";
       stmt3.executeUpdate(sql);
       stmt3.close();
      }
      }
      rs.close();
      sql="UPDATE files SET step=2 WHERE copy="+id;
           
     stmt.executeUpdate(sql);
     con.commit();
     con.close();
     stmt.close();


        

        } else response.sendRedirect("notPermited.html");
        if(response!=null&&!response.isCommitted())response.sendRedirect("fileLoader.jsp?id="+id); 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {processRequest(request, response);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(docPublish.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
                Logger.getLogger(docPublish.class.getName()).log(Level.SEVERE, null, ex);
            }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.sendRedirect("notPermited.html");
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
