package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class topMenu extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.print("<a href=\"my.jsp\" class=\"footmenu\">Личная</a><a href=\"allDay.xhtml\" class=\"footmenu\">Календарь</a>"
                    + "<a href=\"correspondence.jsp\" class=\"footmenu\">Сообщения</a><a href=\"galClassic.jsp\" class=\"footmenu\">Фотоальбомы</a>"
                    + "<a href=\"docClassic.jsp\" class=\"footmenu\">Документы</a><a href=\"findTag.jsp\" class=\"footmenu\">Теги</a>");
        } finally {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
