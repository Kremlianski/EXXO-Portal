package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ProcessUpd {

    public String name = null;
    public String description = null;
    public String owner = null;
    public String supervisor = null;
    public String template = null;
    public int minToDo = 0;
    public int minToDeside = 0;
    public String type = null;
    public int minToOpen = 0;
    public int stoped = 0;
    public String option = "<option value='0'>--------</option>";
    public String option1 = "<option value='0'>--------</option>";
    public Boolean R = false;

    public void setParams(String id, HttpServletRequest r) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        String sql = "SELECT id, fio FROM employee ORDER BY fio";
        String sql1 = "SELECT name, description, owner, supervisor, template, minToDo, minToDeside, type, minToOpen, stoped FROM process WHERE id='" + id + "'";

        ResultSet rs1 = stmt.executeQuery(sql1);
        Statement stmt1 = con.createStatement();
        ResultSet rs = stmt1.executeQuery(sql);

        if (rs1.next()) {
            name = rs1.getString(1);
            description = rs1.getString(2);
            owner = rs1.getString(3);
            supervisor = rs1.getString(4);
            template = rs1.getString(5);
            minToDo = rs1.getInt(6);;
            minToDeside = rs1.getInt(7);

            type = rs1.getString(8);
            minToOpen = rs1.getInt(9);
            stoped = rs1.getInt(10);

        } else {
            R = true;
        }

        while (rs.next()) {
            String ID = rs.getString(1);
            String FIO = rs.getString(2);
            if (ID.equals(owner)) {
                option += "<option value='" + ID + "' SELECTED>" + FIO + "</option>";
            } else {
                option += "<option value='" + ID + "'>" + FIO + "</option>";
            }
            if (ID.equals(supervisor)) {
                option1 += "<option value='" + ID + "' SELECTED>" + FIO + "</option>";
            }
            option1 += "<option value='" + ID + "'>" + FIO + "</option>";
        }

        rs.close();
        rs1.close();
        stmt.close();
        stmt1.close();
        con.close();
    }
}
