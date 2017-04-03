package BASE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import static org.imgscalr.Scalr.*;

public class VER {

public static final String DefDBHost = "localhost";
public static final String DefDBName = "EXXO";
public static final String DefDBUser = "exxo";
public static final String DefDBPass = "iLikeEXXO";  

private static double DefMaxFilesSize = 50000000;
private static boolean DefFilesVersions = true;
private static int DefMaxFilesVersions = 3;
private static long DefMaxFileSize = 20971520;
private static boolean DefChooseFileVersions = true;
private static int DefMaxRows=25;
private static int DefMaxRows1=50;
private static int DefMaxBloks=50;
private static int DefMaxNews=2;
private static int DefMaxDesk=10;
//private static String defDocStore_0="localhost";

private static int DefVidsDocs=30;
private static int DefVidsBlogs=30;

private static int DefGalsHeight = 640;
private static Method DefGalsSpeed = Method.AUTOMATIC;


private static boolean DefMessages=false;
private static boolean DefMessagesPersonal=false;
public static final String DefReplyAddress = "";  
public static final String DefInnerDocsLogin = ""; 
public static final String DefInnerDocsPOP3 = ""; 
public static final String DefInnerDocsPass = ""; 
public static final String DefInnerDocsIMAP = "";
public static final String DefInnerDocsWord = "";    
public static final String DefInnerDocsFrom = "";
public static final int DefInnerDocsOpen = 4;
    public static final int newVer=14;
    public static String getVer(){
       return "7.1";
          }
    public static String getFullVer(){
    return "2."+getVer();
    }

    public static String getM(){
    return "iLikeEXXO";
    }
    
    
 public static String getDBName(){
    String name=DefDBName;
    String n = getParam("dbNamePortal"); 
    if (n!=null) name = n;
    return name;    
    }
    public static String getDBHost(){
    String name=DefDBHost;
    String n = getParam("dbHostPortal"); 
    if (n!=null) name = n;
    return name;    
    }
    
    public static Properties getDBProp(){
    Properties properties = new Properties();
       properties.setProperty("user", DefDBUser);
       properties.setProperty("password", DefDBPass);
    Properties n = getProp(); 
    if (n!=null) properties = n;
    return properties;    
    }
        public static String getParam(String p) {
        String res=(String)FacesContext.getCurrentInstance().getExternalContext().getInitParameter(p);
        return res;
    }
    public static Properties getProp(){
        Properties properties = new Properties();
    if(getParam("dbUserPortal")!=null&&getParam("dbPassPortal")!=null) {
     properties.setProperty("user", getParam("dbUserPortal"));
     properties.setProperty("password", getParam("dbPassPortal"));
       return properties;
    } else return null;
    }  
  public static Connection getServletConnection(ServletContext sc) throws ClassNotFoundException, SQLException {
      Class.forName("org.postgresql.Driver");
      String dbHost = BASE.VER.DefDBHost;
      String dbName = BASE.VER.DefDBName;
      String dbUser = BASE.VER.DefDBUser;
      String dbPass = BASE.VER.DefDBPass;
      
      if (sc.getInitParameter("dbHostPortal")!=null) dbHost = sc.getInitParameter("dbHostPortal");
      if (sc.getInitParameter("dbNamePortal")!=null) dbName = sc.getInitParameter("dbNamePortal");
      if (sc.getInitParameter("dbUserPortal")!=null) dbUser = sc.getInitParameter("dbUserPortal");
      if (sc.getInitParameter("dbPassPortal")!=null) dbPass = sc.getInitParameter("dbPassPortal");
      
      
      String url ="jdbc:postgresql://"+dbHost +"/"+dbName;
      Properties properties=new Properties();
      properties.setProperty("user", dbUser);
      properties.setProperty("password", dbPass);

     return DriverManager.getConnection(url,properties);
        
    }
  
  //переменная определяет максимальный суммарный размер файлов сотрудника
  public static double getMaxFilesSize(ServletContext sc) {
  double res=DefMaxFilesSize;
  if (sc.getInitParameter("max_files_size")!=null) res= Double.parseDouble(sc.getInitParameter("max_files_size"));    
  return res;
  }
  
  //переменная определяет возможность файлу иметь несколько версий 
  public static boolean getFilesVersions(ServletContext sc) {
  boolean res=DefFilesVersions;
  if (sc.getInitParameter("files_versions")!=null) res= Boolean.parseBoolean(sc.getInitParameter("files_versions"));    
  return res;
  }
  
  //переменная определяет максимальное число хранимых версий у файла
  public static int getMaxFilesVersions(ServletContext sc) {
  int res=DefMaxFilesVersions;
  if (sc.getInitParameter("max_files_versions")!=null) res= Integer.parseInt(sc.getInitParameter("max_files_versions"));    
  return res;
  }
  
  //определяет максимальный допустимый размер сохраняемого файла
  public static long getMaxFileSize(ServletContext sc) {
  long res=DefMaxFileSize;
  if (sc.getInitParameter("max_file_size")!=null) res= Long.parseLong(sc.getInitParameter("max_file_size")); 
  return res;
  }
  
  //определяет, может ли пользователь сам задавать количество версий у файла !ПОКА не используется!
  public static boolean getChooseFileVersions(ServletContext sc) {
  boolean res=DefChooseFileVersions;
  if (sc.getInitParameter("choose_file_versions")!=null) res= Boolean.parseBoolean(sc.getInitParameter("choose_file_versions"));    
  return res;
  }
  
  public static Connection getDocsConnection(ServletContext sc, int store) throws ClassNotFoundException, SQLException {
      Class.forName("org.postgresql.Driver");
      
      String dbName = BASE.VER.DefDBName;
      String dbUser = BASE.VER.DefDBUser;
      String dbPass = BASE.VER.DefDBPass;
      
      String dbHost=null;
      if(store==0) {
      dbHost = "localhost"; 
      if (sc.getInitParameter("dbHostStore_0")!=null) dbHost = sc.getInitParameter("dbHostStore_0");} 
      else if (store == 1) {if (sc.getInitParameter("dbHostStore_1")!=null) dbHost = sc.getInitParameter("dbHostStore_1");}
      else if (store == 2) {if (sc.getInitParameter("dbHostStore_2")!=null) dbHost = sc.getInitParameter("dbHostStore_2");}
      else if (store == 3) {if (sc.getInitParameter("dbHostStore_3")!=null) dbHost = sc.getInitParameter("dbHostStore_3");}
      else if (store == 4) {if (sc.getInitParameter("dbHostStore_4")!=null) dbHost = sc.getInitParameter("dbHostStore_4");}
      else if (store == 5) {if (sc.getInitParameter("dbHostStore_5")!=null) dbHost = sc.getInitParameter("dbHostStore_5");}
      else if (store == 6) {if (sc.getInitParameter("dbHostStore_6")!=null) dbHost = sc.getInitParameter("dbHostStore_6");}
      else if (store == 7) {if (sc.getInitParameter("dbHostStore_7")!=null) dbHost = sc.getInitParameter("dbHostStore_7");}
      else if (store == 8) {if (sc.getInitParameter("dbHostStore_8")!=null) dbHost = sc.getInitParameter("dbHostStore_8");}
      else if (store == 9) {if (sc.getInitParameter("dbHostStore_9")!=null) dbHost = sc.getInitParameter("dbHostStore_9");}
      else if (store == 10) {if (sc.getInitParameter("dbHostStore_10")!=null) dbHost = sc.getInitParameter("dbHostStore_10");}
      
      
      if (sc.getInitParameter("dbNamePortal")!=null) dbName = sc.getInitParameter("dbNamePortal");
      if (sc.getInitParameter("dbUserPortal")!=null) dbUser = sc.getInitParameter("dbUserPortal");
      if (sc.getInitParameter("dbPassPortal")!=null) dbPass = sc.getInitParameter("dbPassPortal");
      
      
      String url ="jdbc:postgresql://"+dbHost +"/"+dbName;
      Properties properties=new Properties();
      properties.setProperty("user", dbUser);
      properties.setProperty("password", dbPass);
     if(dbHost==null) return null;
     else return DriverManager.getConnection(url,properties);
        
    }
  public static boolean getStore_0(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_0")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_0"));    
  return res;
  }
  public static boolean getStore_1(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_1")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_1"));    
  return res;
  }
  public static boolean getStore_2(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_2")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_2"));    
  return res;
  }
  public static boolean getStore_3(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_3")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_3"));    
  return res;
  }
  public static boolean getStore_4(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_4")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_4"));    
  return res;
  }
  public static boolean getStore_5(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_5")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_5"));    
  return res;
  }
  public static boolean getStore_6(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_6")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_6"));    
  return res;
  }
  public static boolean getStore_7(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_7")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_7"));    
  return res;
  }
  public static boolean getStore_8(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_8")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_8"));    
  return res;
  }
  public static boolean getStore_9(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_9")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_9"));    
  return res;
  }  
  public static boolean getStore_10(ServletContext sc) {
  boolean res=true;
  if (sc.getInitParameter("store_10")!=null) res= Boolean.parseBoolean(sc.getInitParameter("store_10"));    
  return res;
  } 
  
  public static boolean isStore_0(ServletContext sc) {
  boolean res=false;
  if (getStore_0(sc)&&sc.getInitParameter("dbHostStore_0")!=null) res= true;    
  return res;
  } 
  public static boolean isStore_1(ServletContext sc) {
  boolean res=false;
  if (getStore_1(sc)&&sc.getInitParameter("dbHostStore_1")!=null) res= true;    
  return res;
  } 
   public static boolean isStore_2(ServletContext sc) {
  boolean res=false;
  if (getStore_2(sc)&&sc.getInitParameter("dbHostStore_2")!=null) res= true;    
  return res;
  } 
   public static boolean isStore_3(ServletContext sc) {
  boolean res=false;
  if (getStore_3(sc)&&sc.getInitParameter("dbHostStore_3")!=null) res= true;    
  return res;
  } 
   public static boolean isStore_4(ServletContext sc) {
  boolean res=false;
  if (getStore_4(sc)&&sc.getInitParameter("dbHostStore_4")!=null) res= true;    
  return res;
  } 
   public static boolean isStore_5(ServletContext sc) {
  boolean res=false;
  if (getStore_5(sc)&&sc.getInitParameter("dbHostStore_5")!=null) res= true;    
  return res;
  }  
   public static boolean isStore_6(ServletContext sc) {
  boolean res=false;
  if (getStore_6(sc)&&sc.getInitParameter("dbHostStore_6")!=null) res= true;    
  return res;
  }
   public static boolean isStore_7(ServletContext sc) {
  boolean res=false;
  if (getStore_7(sc)&&sc.getInitParameter("dbHostStore_7")!=null) res= true;    
  return res;
  } 
   public static boolean isStore_8(ServletContext sc) {
  boolean res=false;
  if (getStore_8(sc)&&sc.getInitParameter("dbHostStore_8")!=null) res= true;    
  return res;
  } 
   public static boolean isStore_9(ServletContext sc) {
  boolean res=false;
  if (getStore_9(sc)&&sc.getInitParameter("dbHostStore_9")!=null) res= true;    
  return res;
  }
  public static boolean isStore_10(ServletContext sc) {
  boolean res=false;
  if (getStore_10(sc)&&sc.getInitParameter("dbHostStore_10")!=null) res= true;    
  return res;
  }  
  private static ArrayList <Integer> whatStore(ServletContext sc) {
  ArrayList <Integer> L = new ArrayList <Integer>();
  if(isStore_0(sc)) L.add(0);
  if(isStore_1(sc)) L.add(1);
  if(isStore_2(sc)) L.add(2);
  if(isStore_3(sc)) L.add(3);
  if(isStore_4(sc)) L.add(4);
  if(isStore_5(sc)) L.add(5);
  if(isStore_6(sc)) L.add(6);
  if(isStore_7(sc)) L.add(7);
  if(isStore_8(sc)) L.add(8);
  if(isStore_9(sc)) L.add(9);
  if(isStore_10(sc)) L.add(10);
  
  return L;
  }
  public static int whereStore(ServletContext sc) {
   int r=-1;
   int max=whatStore(sc).size();
   if(max>0){
   java.util.Random ra=new java.util.Random();
   r=ra.nextInt(max);
   } else return -1;
     return whatStore(sc).get(r);
  }
  // максимальное количество рядов в выборке
  public static int getMaxRows(ServletContext sc) {
  int res=DefMaxRows;
  if (sc.getInitParameter("max_rows")!=null) res= Integer.parseInt(sc.getInitParameter("max_rows")); 
  return res;
  }
  public static int getMaxRows1(ServletContext sc) {
  int res=DefMaxRows1;
  if (sc.getInitParameter("max_rows1")!=null) res= Integer.parseInt(sc.getInitParameter("max_rows1")); 
  return res;
  }
  public static int getMaxBloks(ServletContext sc) {
  int res=DefMaxBloks;
  if (sc.getInitParameter("max_bloks")!=null) res= Integer.parseInt(sc.getInitParameter("max_bloks")); 
  return res;
  }
  public static int getMaxNews(ServletContext sc) {
  int res=DefMaxNews;
  if (sc.getInitParameter("max_news")!=null) res= Integer.parseInt(sc.getInitParameter("max_news")); 
  return res;
  }
  public static int getMaxDesk(ServletContext sc) {
  int res=DefMaxDesk;
  if (sc.getInitParameter("max_desk")!=null) res= Integer.parseInt(sc.getInitParameter("max_desk")); 
  return res;
  }
  public static int getVidsDocs(ServletContext sc) {
  int res=DefVidsDocs;
  if (sc.getInitParameter("vids_docs")!=null) res= Integer.parseInt(sc.getInitParameter("vids_docs")); 
  return res;
  }
    public static int getVidsBlogs(ServletContext sc) {
  int res=DefVidsBlogs;
  if (sc.getInitParameter("vids_blogs")!=null) res= Integer.parseInt(sc.getInitParameter("vids_blogs")); 
  return res;
  }
   public static int getGalsHeight(ServletContext sc) {
  int res=DefGalsHeight;
  String h = sc.getInitParameter("gals_height");
  if (h!=null) { 
      if(h.equals("small")) res= 480; 
      else if(h.equals("medium")) res= 640;
      else if(h.equals("big")) res = 820;
  }
       return res;
  }
  
   public static Method getGalsSpeed(ServletContext sc) {
       Method res = DefGalsSpeed;
       String s = sc.getInitParameter("gals_speed");
       if(s!=null&&s.equals("ultra")) res = Method.ULTRA_QUALITY;
       return res;
   }

  public static boolean isMessagesNoBlog(ServletContext sc) {
  boolean res=DefMessages;
  if (sc.getInitParameter("messages")!=null) res= Boolean.parseBoolean(sc.getInitParameter("messages"));    
  return res;
  } 
  public static boolean isMessagesPersonal(ServletContext sc) {
  boolean res=DefMessagesPersonal;
  if (sc.getInitParameter("messages_personal")!=null) res= Boolean.parseBoolean(sc.getInitParameter("messages_personal"));    
  return res;
  } 
    public static String getReplyAddress(ServletContext sc) {
    String res=DefReplyAddress;
   if(sc.getInitParameter("reply_address")!=null) res= sc.getInitParameter("reply_address"); 
    return res;
    }
    public static String getInnerDocsLogin(ServletContext sc) {
    String res=DefInnerDocsLogin;
   if(sc.getInitParameter("inner_docs_login")!=null) res= sc.getInitParameter("inner_docs_login"); 
    return res;
    }
   public static String getInnerDocsPass(ServletContext sc) {
   String res=DefInnerDocsPass;
   if(sc.getInitParameter("inner_docs_pass")!=null) res= sc.getInitParameter("inner_docs_pass"); 
    return res;
    }
    public static String getInnerDocsPOP3(ServletContext sc) {
    String res=DefInnerDocsPOP3;
   if(sc.getInitParameter("inner_docs_pop3")!=null) res= sc.getInitParameter("inner_docs_pop3"); 
    return res;
    }
    public static String getInnerDocsIMAP(ServletContext sc) {
    String res=DefInnerDocsIMAP;
   if(sc.getInitParameter("inner_docs_imap")!=null) res= sc.getInitParameter("inner_docs_imap"); 
    return res;
    }
   public static String getInnerDocsWord(ServletContext sc) {
    String res=DefInnerDocsWord;
   if(sc.getInitParameter("inner_docs_word")!=null) res= sc.getInitParameter("inner_docs_word"); 
    return res;
    } 
    public static String getInnerDocsFrom(ServletContext sc) {
    String res=DefInnerDocsFrom;
   if(sc.getInitParameter("inner_docs_from")!=null) res= sc.getInitParameter("inner_docs_from"); 
    return res;
    }
        public static int getInnerDocsOpen(ServletContext sc) {
    int res=DefInnerDocsOpen;
   if(sc.getInitParameter("inner_docs_open")!=null) res= 0; 
    return res;
    }
}
