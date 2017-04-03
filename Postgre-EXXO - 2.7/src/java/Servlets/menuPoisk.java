package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class menuPoisk extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.print("A.Node.all(\"#menus a\").each(function(){var home=this.getAttribute(\"href\");"
                    + "if (home.indexOf(\"?id=\")==0) this.setAttribute(\"href\", \"index.jsp\"+home);});");
            out.print("A.one('#menus').setStyle('display', 'block').prependTo(A.one('#menu'));"
                    + "var menu = A.one(\"#menus\");"
                    + "menu.plug(A.Plugin.NodeMenuNav, { submenuHideDelay: 750 });"
                    + "menu.get(\"ownerDocument\").get(\"documentElement\").removeClass(\"aui-loading\");");

            out.print("new A.Button({icon: 'search',label:'Искать'}).render(A.one(\"#b\"));");
            out.print("A.one('#content-wrapper').setStyle('minHeight',A.DOM.winHeight()+'px');");

        } finally {
            //         out.close();
        }
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
