package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class find extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        String variant = request.getParameter("variant");
        String target = request.getParameter("target");
        if (variant == null) {
            variant = "Сотрудники";
        }
        if (target == null) {
            target = "";
        }
        String find = "empList.jsp";

        if (variant.equals("Сотрудники")) {
            find = "employees.xhtml?q=" + target;
        } else if (variant.equals("Документы")) {
            find = "findDocs.jsp?frase=" + target;
        } else if (variant.equals("Сообщения")) {
            find = "findBlogs.jsp?find=" + target;
        } else if (variant.equals("Фотографии")) {
            find = "findGals.jsp?find=" + target;
        }
        RequestDispatcher rd = request.getRequestDispatcher(find);
        rd.forward(request, response);
        //  response.sendRedirect(find);
        //Здесь срабатывает баг со страницы roles.xhtml если использовать mojara
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
