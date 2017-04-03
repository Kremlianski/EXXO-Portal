package BEANS;

import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class BlogEditorBean {

    public String getList(String owner, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();

        String sql = "SELECT text, bus, name FROM blogesTMP WHERE owner='" + owner + "'";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "<div id=\"blogs\">";
        if (rs.next()) {
            list += "<h1 id='name'>" + rs.getString("name") + " </h1>";
            if (rs.getString(2).equals("0")) {
                list += "<div class='blog'><div class=\"HBlog\">";
            } else {
                list += "<div class='blog BUS'><div class=\"HBlog\">";
            }
            list += "<a class=\"B edit\" href=\"blogInsert.jsp?edit=1&r=1\" id=\"\"> Править </a> ";
            list += "<a class=\"B\" href=\"blogInsert.jsp?r=1\" > Очистить    </a><a class=\"B\" href=\"docPoket.jsp\">Прикрепить файлы</a>"
                    + "<a class=\"B\" href=\"blogPublish\">ОПУБЛИКОВАТЬ!</a></div>";
            list += "<div class='text'>" + rs.getString("text") + "</div>";

        } else {
            list += "<div id='addBlog'>Черновик пуст!<br><a href='blogInsert.jsp?r=1'>Создать новую запись?</a></div>";
        }
        list += "</div>";

        sql = "SELECT name, type, fname, files.id AS id FROM files, docPoket WHERE files.id=docPoket.doc AND owner=" + owner;
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            if (rs.isFirst()) {
                list += "<div id='input'>Вложения:</div>";
                list += "<table class='exxo-table'>";
            }
            list += "<tr id='" + rs.getString("id") + "'><td class='" + EXXOlib.DOCTYPES.getType(rs.getString("type"), rs.getString("fname")) + "'></td><td>" + rs.getString("name") + "</td><td>" + rs.getString("fname") + "</td></tr>";
            if (rs.isLast()) {
                list += "</table>";
            }
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }
}
