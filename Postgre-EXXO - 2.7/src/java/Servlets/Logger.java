package Servlets;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Level;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import javax.sql.DataSource;
import ru.EXXO.LOGIN.role;
import ru.EXXO.LOGIN.RoleDAO;

public class Logger implements Filter {

    Boolean pas = false;
    private FilterConfig filterConfig = null;
    private static final String aist = "EXXO";
    private boolean redirect = false;
    Connection con = null;
    Statement stmt = null;
    ServletContext sc;

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException, ClassNotFoundException, SQLException {
        pas = false;
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        String user = req.getUserPrincipal().getName();

        String UA = req.getHeader("User-Agent");

        String myLANIP = req.getRemoteAddr();
        redirect = false;
        String note = "";
        if (req.isUserInRole("portal_admin")) {
            redirect = true;
            res.sendRedirect("/START/");
        }
        if (session.getAttribute("fio") == null && !isRegisterd()) {
            redirect = true;
            res.sendRedirect("/WELLCOME/register.xhtml");
        }
        if (session.getAttribute("fio") == null) {
            if (user.equals("root")) {
                startSQL();
                /*
                session.setAttribute("authorized", "yes");
                session.setAttribute("fio", "ROOT");
                session.setAttribute("id", "0");
                session.setAttribute("role", "Z");
                session.setAttribute("global_id", "0");
                //текущий каталог в моих сообщениях:
                session.setAttribute("blog_car_cat", "0");
                 */
                try {
                    stmt.executeUpdate("INSERT INTO ips VALUES (0, now(), '" + myLANIP + "')");
                } finally {
                    stmt.close();
                    con.close();
                }
            } else {

                startSQL();
                String sql = "SELECT employee.id AS id, fio, role, global_id, email FROM employee, users WHERE employee.id=users.id AND users.login='" + user + "'";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    session.setAttribute("authorized", "yes");
                    session.setAttribute("fio", rs.getString("fio"));
                    session.setAttribute("id", rs.getString("id"));

                    session.setAttribute("global_id", rs.getString("global_id"));
                    session.setAttribute("email", rs.getString("email"));
                    session.setAttribute("blog_car_cat", "0");
                    ArrayList<role> roles = new RoleDAO().finedUserRoles(rs.getInt("id"));
                    session.setAttribute("roles", roles);
                    session.setAttribute("role", rolesString(roles));
                    stmt.executeUpdate("INSERT INTO ips VALUES (" + rs.getString("id") + ", now(), '" + myLANIP + "')");
                }
                rs.close();
                stmt.close();
                con.close();
            }
            //      
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            sc = filterConfig.getServletContext();
            ru.EXXO.LOGIN.Context.setContext(sc);

        }
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("Logger()");
        }
        StringBuffer sb = new StringBuffer("Logger(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    private Boolean isRegisterd() throws ClassNotFoundException, SQLException {
        if (BASE.CHECK.checkDates()) {
            return true;
        } else {
            return isActivated();
        }
    }

    public Boolean isActivated() throws ClassNotFoundException, SQLException {
        startSQL();
        String k = "";
        String sql = "SELECT key FROM register WHERE reg = '" + aist + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            k = rs.getString(1);
        }
        rs.close();
        stmt.close();
        con.close();
        return BASE.CASE.isKey(k);
    }

    void startSQL() throws ClassNotFoundException, SQLException {
        DataSource source = ru.EXXO.DBSOURCE.INSTANCE.getSource();
        con = source.getConnection();
        stmt = con.createStatement();
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try {
            doBeforeProcessing(request, response);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!redirect) {
            chain.doFilter(request, response);
        }
        //       doAfterProcessing(request, response);
    }

    private String rolesString(ArrayList<role> r) {
        StringBuilder sb = new StringBuilder();
        ListIterator i = r.listIterator();
        while (i.hasNext()) {
            role role = (role) i.next();
            if (role.getName().equals("boss")) {
                sb.append("a");
            } else if (role.getName().equals("master")) {
                sb.append("b");
            } else if (role.getName().equals("staff")) {
                sb.append("c");
            } else if (role.getName().equals("photo_editor")) {
                sb.append("d");
            } else if (role.getName().equals("resources_editor")) {
                sb.append("f");
            } else if (role.getName().equals("news_editor")) {
                sb.append("g");
            } else if (role.getName().equals("business_process_editor")) {
                sb.append("h");
            } else if (role.getName().equals("business_process_inspector")) {
                sb.append("i");
            } else if (role.getName().equals("tags_editor")) {
                sb.append("k");
            } else if (role.getName().equals("tags_expert")) {
                sb.append("l");
            } else if (role.getName().equals("unit_editor")) {
                sb.append("m");
            } else if (role.getName().equals("projects_editor")) {
                sb.append("n");
            } else if (role.getName().equals("files_security")) {
                sb.append("e");
            } else if (role.getName().equals("general_files_editor")) {
                sb.append("o");
            } else if (role.getName().equals("in_files_editor")) {
                sb.append("p");
            } else if (role.getName().equals("out_files_editor")) {
                sb.append("q");
            } else if (role.getName().equals("inner_files_editor")) {
                sb.append("r");
            } else if (role.getName().equals("security")) {
                sb.append("s");
            } else if (role.getName().equals("blog_security")) {
                sb.append("t");
            } else if (role.getName().equals("vote_editor")) {
                sb.append("j");
            }
        }
        return sb.toString();
    }
}
