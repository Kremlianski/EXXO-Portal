package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class qwestCSV extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/csv");
        String id = request.getParameter("id");
        response.addHeader("Cache-Control", "must-revalidate");
        response.setCharacterEncoding("Cp1251");
        PrintWriter out = response.getWriter();
        boolean yes = false;
        String boss = "нет";
        String f = "";
        final int crop = 29;
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        ServletContext sc = request.getServletContext();
        if (role.indexOf("a") >= 0 || role.indexOf("j") >= 0 || role.indexOf("Z") >= 0) {
            yes = true;
        }
        if (yes) {
            Connection con = BASE.VER.getServletConnection(sc);
            String sql = "SELECT question, user_id, answer, fio, head, office, units.unit AS unit FROM answers, employee, units, qwests WHERE "
                    + " answers.quest_id='" + id + "' AND user_id=employee.id AND qwests.quest_id=answers.quest_id "
                    + " AND units.unit_id=employee.unit AND NOT anonim";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String todo = "Имя" + '\t' + "Ответ" + '\t' + "Подразделение" + '\t' + "Руководитель" + '\t' + "Офис" + '\n';

            while (rs.next()) {
                boss = "нет";
                if (rs.getString("head") != null && rs.getString("head").equals("1")) {
                    boss = "да";
                }
                todo += rs.getString("fio") + '\t' + rs.getString("answer") + '\t' + rs.getString("unit") + '\t' + boss + '\t' + rs.getString("office") + '\n';
                f = rs.getString("question");
            }
            try {

                f = EXXOlib.textLib.properFileName(f, crop);
                response.setHeader("Content-Disposition", "attachment; filename=\"voting_" + id + "_" + f + ".txt\"");
                out.print(todo);
                out.flush();
            } finally {
                rs.close();
                stmt.close();
                out.close();
            }
        } else {
            response.sendRedirect("notPermited.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(qwestCSV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(qwestCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(qwestCSV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(qwestCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
