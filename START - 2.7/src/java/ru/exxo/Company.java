package ru.exxo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;

public class Company {

    private String company = "";
    private Connection con;
    private Statement stmt;

    public Company() {
    }

    public String getCompany() throws ClassNotFoundException, SQLException {
        getComp();
        return company;
    }

    private void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + BASE.Ver.getDBHost() + "/" + BASE.Ver.getDBName();
        Properties properties = BASE.Ver.getDBProp();
        con = DriverManager.getConnection(url, properties);
        stmt = con.createStatement();
    }

    private void getComp() throws ClassNotFoundException, SQLException {
        startSQL();
        String id = null;//вроде бы начальная точка
//             Vector comp = new <Vector> Vector();
        int count;
        Stack<Vector> st = new Stack<Vector>();

        try {

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
        } catch (ClassNotFoundException e) {
        }
    }

}
