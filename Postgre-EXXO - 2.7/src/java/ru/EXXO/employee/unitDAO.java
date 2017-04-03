package ru.EXXO.employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class unitDAO extends ru.EXXO.postgresDAO {

    private String company = "";

    public unit loadUnit(int id) throws SQLException {
        unit u = null;
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM units WHERE id=" + id;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            u = new unit(id, rs.getString("unit"), rs.getInt("superior"), ru.EXXO.Status.CLEAN);
        }
        stmt.close();
        con.close();
        return u;
    }

    public int removeUnit(unit u) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        int res;
        String sql1 = "SELECT unit_id FROM units WHERE superior='" + u.getId() + "'";
        ResultSet rs1 = stmt.executeQuery(sql1);
        if (!rs1.next() && u.getId() != 1) {
            String sql2 = "SELECT id FROM employee WHERE unit=" + u.getId();
            ResultSet rs2 = stmt.executeQuery(sql2);
            if (!rs2.next()) {
                String sql = "delete from units where unit_id=" + u.getId();
                int in = stmt.executeUpdate(sql);
                res = in;
            } else {
                res = 2;
            }
            rs2.close();
        } else {
            res = 0;
        }
        rs1.close();
        stmt.close();
        con.close();
        return res;
        /*1-нормально
      *0-верхний элемент или есть еще юниты 
      *2- не пустой, есть сотрудники
         */
    }

    public void addUnit(unit u) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "INSERT INTO units (unit_id,unit,superior) VALUES(" + u.getId() + ",'" + u.getUnit() + "'," + u.getSuperior() + ")";
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    public void modifyUnit(unit u) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "UPDATE units SET unit = '" + u.getUnit() + "' AND superior = " + u.getSuperior() + " WHERE unit_id=" + u.getId();
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
    }

    public ArrayList<unit> getList() throws SQLException {
        ArrayList<unit> ar = new ArrayList();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM units ORDER BY unit";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ar.add(new unit(rs.getInt("unit_id"), rs.getString("unit"), rs.getInt("superior"), ru.EXXO.Status.CLEAN));
        }
        return ar;
    }

    public ArrayList<unit> getList(int superior) throws SQLException {
        ArrayList<unit> ar = new ArrayList();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM units WHERE superior=" + superior + " ORDER BY unit";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ar.add(new unit(rs.getInt("unit_id"), rs.getString("unit"), rs.getInt("superior"), ru.EXXO.Status.CLEAN));
        }
        return ar;
    }

    public String getCompany() throws SQLException, ClassNotFoundException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String id = null;//вроде бы начальная точка
//             Vector comp = new <Vector> Vector();
        int count;
        Stack<Vector> st = new Stack<Vector>();

        String sql = "SELECT unit_id, unit, superior FROM units";
        ResultSet rs = stmt.executeQuery(sql);

//     Stack<Vector> st = new Stack<Vector>();
        count = 0;
        String marker = null;
        while (rs.next()) {
            Vector<String> v = new Vector<String>();

            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));

            st.push(v);

            if ((rs.getString(3) == null && id == null) || (id != null && rs.getString(1).equals(id))) {
                marker = rs.getString(1);
                count = 1;
                String title = "<div class=\"title" + count + " item\" id=\"" + rs.getString(1) + "\">";
                String titleEnd = "</div>";
                company += title + rs.getString(2) + titleEnd;
            }

        }
////////////
        class recurs {

            private final Stack<Vector> st;

            recurs(Stack<Vector> st) {
                this.st = st;
            }
            ;
   int count = 1;

            private void printUnit(String marker) throws SQLException, ClassNotFoundException {
                count++;
                Stack<Vector> st1 = new Stack<Vector>();
                st1 = (Stack<Vector>) st.clone();

                company += "<div class=\"inno\">";

                while (!st1.empty()) {
                    Vector<String> v = new Vector<String>((Vector<String>) st1.pop());

                    if (v.get(2) != null) {
                        if (v.get(2).equals(marker)) {
                            String title = "<div class=\"title" + count + " item\" id=\"" + v.get(0) + "\">";
                            String titleEnd = "</div>";
                            company += title + v.get(1) + titleEnd;
                            printUnit(v.get(0));
                        }
                    }
                }
                company += "</div>";
                count--;
            }
        }
///////////
        recurs o = new recurs(st);
        o.printUnit(marker);
// out.close();
        rs.close();
        st.clear();
        stmt.close();
        con.close();
        return company;
    }

}
