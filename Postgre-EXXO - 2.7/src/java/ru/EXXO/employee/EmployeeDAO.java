//CREATE TABLE employee
//(
//  fio character varying(250) NOT NULL,
//  unit integer,
//  "position" character varying(250) NOT NULL,
//  head smallint DEFAULT (0)::smallint,
//  birthday date NOT NULL,
//  education character varying(250),
//  tellocal character varying(25),
//  tellmob character varying(25),
//  email character varying(50),
//  id serial NOT NULL,
//  global_id integer NOT NULL DEFAULT nextval('global_seq'::regclass),
//  photo bytea,
//  hobby text,
//  comment text,
//  responsibility text,
//  tel character varying(25),
//  office character varying(250) DEFAULT 'Переезд'::character varying,
//  room character varying(250),
//  worksince date,
//  login character varying(250),
//  pass character varying(50) DEFAULT '7a5f7cb2d2ae0a9657876e460b2342a4'::character varying,
//  role character varying(250) NOT NULL DEFAULT ''::character varying,
//  fired smallint NOT NULL DEFAULT (0)::smallint,
//  firedsince date,
//  CONSTRAINT employee_pkey PRIMARY KEY (id),
//  CONSTRAINT employee_fio_key UNIQUE (fio),
//  CONSTRAINT employee_global_id_key UNIQUE (global_id),
//  CONSTRAINT employee_login_key UNIQUE (login)
//)
package ru.EXXO.employee;

import EXXOlib.imgFormatException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;

public class EmployeeDAO extends ru.EXXO.postgresDAO {

    private HttpSession s;

    public EmployeeDAO() {
    }

    public EmployeeDAO(HttpSession s) {
        this.s = s;
    }

    public Employee loadEmployee(int id) throws SQLException {
        Employee emp = null;
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT fio, employee.unit AS unit, position, head, birthday, education,tellocal,tellmob,email, "
                + "employee.global_id as global_id, id, length(photo)>0 AS photo, hobby, comment, responsibility, role, "
                + "employee.tel AS tel, office, room, worksince, fired, firedsince, short, offices.adress AS o_adress, "
                + "offices.tel AS o_t, unit_id, units.unit AS unitName, superior FROM employee, units, offices WHERE employee.id=" + id
                + " AND employee.unit = unit_id AND employee.office = offices.short";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            Office office = new Office(rs.getString("short"), rs.getString("o_adress"), rs.getString("o_t"), ru.EXXO.Status.CLEAN);
            unit unit = new unit(rs.getInt("unit_id"), rs.getString("unitName"), rs.getInt("superior"), ru.EXXO.Status.CLEAN);

            emp = new Employee(rs.getString("fio"), unit, rs.getString("position"), (rs.getInt("head") == 1), rs.getDate("birthday"),
                    rs.getString("education"), rs.getString("tellocal"), rs.getString("tellmob"), rs.getString("email"),
                    id, rs.getInt("global_id"), rs.getBoolean("photo"), rs.getString("hobby"), rs.getString("responsibility"),
                    rs.getString("tel"), office, rs.getString("room"), rs.getDate("worksince"), (rs.getInt("fired") != 0),
                    rs.getDate("firedsince"), (rs.getString("role") != null), rs.getString("comment"), Status.CLEAN);
        }
        rs.close();
        stmt.close();
        con.close();
        return emp;
    }

    void addEmployee(Employee e) throws SQLException, IOException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        byte[] b = null;
        Photo p = new PhotoDAO(s).loadPhoto();
        if (p != null) {
            b = p.getImage();
            if (b != null) {
                String format = EXXOlib.IMGESLIB.getFormat(b, p.getType());
                b = EXXOlib.IMGESLIB.cropResize(b, 110, 147, format);
            }
        }
        String sql = "INSERT INTO employee (fio, unit, position, birthday, head, education,tellocal,tellmob,"
                + "email, responsibility, tel, room, office, photo, workSince, role, id, global_id) values "
                + "(?, ?, ?, ?::Date, ?::smallint, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::Date, ?, ?, ?)";
        con.setAutoCommit(false);
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, e.getFio());
        ps.setInt(2, e.getUnit().getId());
        ps.setString(3, e.getPosition());
        ps.setDate(4, new java.sql.Date(e.getBirthday().getTime()));
        int head = 0;
        if (e.isHead()) {
            head = 1;
        }
        ps.setInt(5, head);
        ps.setString(6, e.getEducation().replace("\n<br>", "<br>").replace("\n", "<br>"));
        ps.setString(7, e.getTellocal());
        ps.setString(8, e.getTellmob());
        ps.setString(9, e.getEmail());
        ps.setString(10, e.getResponsibility().replace("\n<br>", "<br>").replace("\n", "<br>"));
        ps.setString(11, e.getTel());
        ps.setString(12, e.getRoom());
        ps.setString(13, e.getOffice().getName());
        ps.setBytes(14, b);
        if (e.getWorksince() != null) {
            ps.setDate(15, new java.sql.Date(e.getWorksince().getTime()));
        } else {
            ps.setDate(15, null);
        }
        ps.setString(16, "");
        ps.setInt(17, e.getId());
        ps.setInt(18, e.getGlobal_id());
        ps.executeUpdate();
        con.commit();
        ps.close();
        con.close();
        if (p != null) {
            p.remove(s);
        }

    }

    void modifyEmployee(Employee e) throws SQLException {

        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        String UPDATE = "UPDATE  employee SET fio=?,unit=?::int,position=?,birthday=?::Date,head=?::smallint,education=?,"
                + "tellocal=?,tellmob=?,email=?,hobby=?,comment=?,responsibility=? ,tel=?, room=?, office=?, workSince=?::Date, fired=?, firedSince=?::Date WHERE id=?::int";

        con.setAutoCommit(false);

        PreparedStatement ps = con.prepareStatement(UPDATE);
        ps.setString(1, e.getFio());
        ps.setInt(2, e.getUnit().getId());
        ps.setString(3, e.getPosition());
        ps.setDate(4, new java.sql.Date(e.getBirthday().getTime()));
        int head = 0;
        if (e.isHead()) {
            head = 1;
        }
        ps.setInt(5, head);
        if (e.getEducation() != null) {
            ps.setString(6, e.getEducation().replace("\n<br>", "<br>").replace("\n", "<br>"));
        } else {
            ps.setString(6, "");
        }
        ps.setString(7, e.getTellocal());
        ps.setString(8, e.getTellmob());
        ps.setString(9, e.getEmail());
        if (e.getHobby() != null) {
            ps.setString(10, e.getHobby().replace("\n<br>", "<br>").replace("\n", "<br>"));
        } else {
            ps.setString(10, "");
        }

        if (e.getComment() != null) {
            ps.setString(11, e.getComment().replace("\n<br>", "<br>").replace("\n", "<br>"));
        } else {
            ps.setString(11, "");
        }
        if (e.getResponsibility() != null) {
            ps.setString(12, e.getResponsibility().replace("\n<br>", "<br>").replace("\n", "<br>"));
        } else {
            ps.setString(12, "");
        }
        ps.setString(13, e.getTel());
        ps.setString(14, e.getRoom());
        ps.setString(15, e.getOffice().getName());
        if (e.getWorksince() != null) {
            ps.setDate(16, new java.sql.Date(e.getWorksince().getTime()));
        } else {
            ps.setDate(16, null);
        }
        ps.setInt(19, e.getId());
        if (e.isFired()) {
            ps.setInt(17, 1);
            ps.setString(18, "now()");
        } else {
            ps.setInt(17, 0);
            ps.setDate(18, null);
        }
        ps.executeUpdate();
        con.commit();
        ps.close();
        con.close();
    }

    void removeEmployee(Employee e) throws SQLException {
        int id = e.getId();
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        con.setAutoCommit(false);
        String sql = "DELETE FROM employee WHERE id='" + id + "'";
        stmt.executeUpdate(sql);

        sql = "DELETE FROM bloges WHERE owner='" + id + "'";
        stmt.executeUpdate(sql);

        sql = "DELETE FROM calendar WHERE user_id='" + id + "'";
        stmt.executeUpdate(sql);

        sql = "DELETE FROM desk WHERE owner='" + id + "'";
        stmt.executeUpdate(sql);

        sql = "DELETE FROM files WHERE owner='" + id + "'";
        stmt.executeUpdate(sql);

        sql = "DELETE FROM gallaries WHERE owner='" + id + "'";
        stmt.executeUpdate(sql);

        sql = "DELETE FROM users WHERE id='" + id + "'";
        stmt.executeUpdate(sql);
        con.commit();
        stmt.close();
        con.close();
    }

    public void removeEmployeePhoto(int id) throws SQLException {

        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();

        String UPDATE_PICTURE = "UPDATE employee SET photo=? WHERE id=?::int";

        PreparedStatement ps = con.prepareStatement(UPDATE_PICTURE);
        con.setAutoCommit(false);

        ps.setInt(2, id);
        ps.setBytes(1, null);
        ps.executeUpdate();
        con.commit();
        ps.close();
        con.close();
    }

    public void updateEmployeePhoto(Employee e) throws SQLException, IOException {

        int id = e.getId();
        byte[] b = null;
        Photo p = new PhotoDAO(s).loadPhoto();
        if (p != null) {
            b = p.getImage();
            if (b != null) {
                String format = EXXOlib.IMGESLIB.getFormat(b, p.getType());
                b = EXXOlib.IMGESLIB.cropResize(b, 110, 147, format);
            }
        }
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        String UPDATE_PICTURE = "UPDATE employee SET photo=? WHERE id=?::int";

        PreparedStatement ps = con.prepareStatement(UPDATE_PICTURE);
        con.setAutoCommit(false);

        ps.setInt(2, id);
        ps.setBytes(1, b);
        ps.executeUpdate();
        con.commit();
        ps.close();
        con.close();
    }

    Date getLastTime(Employee emp) throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        Date lasttime = null;
        String sql = "SELECT t FROM ips WHERE id=" + emp.getId() + " ORDER BY t DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            lasttime = rs.getTimestamp(1);
        }

        stmt.close();
        con.close();
        return lasttime;

    }

    public ArrayList<Employee> allEmployees() throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Employee> a = new ArrayList();
        String sql = "SELECT fio, employee.unit AS unit, position, head, birthday, education,tellocal,tellmob,email, "
                + "employee.global_id as global_id, employee.id AS id, length(photo)>0 AS photo, hobby, comment, responsibility, employee.role AS role, "
                + "employee.tel AS tel, office, room, worksince, fired, firedsince, short, offices.adress AS o_adress, "
                + "offices.tel AS o_t, unit_id, units.unit AS unitName, superior FROM employee, units, offices  WHERE "
                + " employee.unit = unit_id AND employee.office = offices.short  AND fired = 0 ORDER BY fio";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Office office = new Office(rs.getString("short"), rs.getString("o_adress"), rs.getString("o_t"), ru.EXXO.Status.CLEAN);
            unit unit = new unit(rs.getInt("unit_id"), rs.getString("unitName"), rs.getInt("superior"), ru.EXXO.Status.CLEAN);

            Employee emp = new Employee(rs.getString("fio"), unit, rs.getString("position"), (rs.getInt("head") == 1), rs.getDate("birthday"),
                    rs.getString("education"), rs.getString("tellocal"), rs.getString("tellmob"), rs.getString("email"),
                    rs.getInt("id"), rs.getInt("global_id"), rs.getBoolean("photo"), rs.getString("hobby"), rs.getString("responsibility"),
                    rs.getString("tel"), office, rs.getString("room"), rs.getDate("worksince"), (rs.getInt("fired") != 0),
                    rs.getDate("firedsince"), (rs.getString("role") != null), rs.getString("comment"), Status.CLEAN);
            a.add(emp);
        }
        stmt.close();
        con.close();
        return a;

    }

    public ArrayList<Employee> findFormer() throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Employee> a = new ArrayList();
        String sql = "SELECT fio, employee.unit AS unit, position, head, birthday, education,tellocal,tellmob,email, "
                + "employee.global_id as global_id, employee.id AS id, length(photo)>0 AS photo, hobby, comment, responsibility, employee.role AS role, "
                + "employee.tel AS tel, office, room, worksince, fired, firedsince, short, offices.adress AS o_adress, "
                + "offices.tel AS o_t, unit_id, units.unit AS unitName, superior FROM employee, units, offices  WHERE "
                + " employee.unit = unit_id AND employee.office = offices.short AND fired <> 0 ORDER BY fio";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Office office = new Office(rs.getString("short"), rs.getString("o_adress"), rs.getString("o_t"), ru.EXXO.Status.CLEAN);
            unit unit = new unit(rs.getInt("unit_id"), rs.getString("unitName"), rs.getInt("superior"), ru.EXXO.Status.CLEAN);

            Employee emp = new Employee(rs.getString("fio"), unit, rs.getString("position"), (rs.getInt("head") == 1), rs.getDate("birthday"),
                    rs.getString("education"), rs.getString("tellocal"), rs.getString("tellmob"), rs.getString("email"),
                    rs.getInt("id"), rs.getInt("global_id"), rs.getBoolean("photo"), rs.getString("hobby"), rs.getString("responsibility"),
                    rs.getString("tel"), office, rs.getString("room"), rs.getDate("worksince"), (rs.getInt("fired") != 0),
                    rs.getDate("firedsince"), (rs.getString("role") != null), rs.getString("comment"), Status.CLEAN);
            a.add(emp);
        }
        stmt.close();
        con.close();
        return a;

    }

    public ArrayList<Employee> findBirthdays() throws SQLException {
        Connection con = ru.EXXO.DBSOURCE.INSTANCE.getSource().getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Employee> a = new ArrayList();
        String sql = "SELECT fio, employee.unit AS unit, position, head, birthday, education,tellocal,tellmob,email, "
                + "employee.global_id as global_id, employee.id AS id, length(photo)>0 AS photo, hobby, comment, responsibility, employee.role AS role, "
                + "employee.tel AS tel, office, room, worksince, fired, firedsince, short, offices.adress AS o_adress, "
                + "offices.tel AS o_t, unit_id, units.unit AS unitName, superior FROM employee, units, offices  WHERE "
                + " employee.unit = unit_id AND employee.office = offices.short AND fired = 0 ORDER BY EXTRACT(MONTH FROM birthday), EXTRACT(DAY FROM birthday)";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Office office = new Office(rs.getString("short"), rs.getString("o_adress"), rs.getString("o_t"), ru.EXXO.Status.CLEAN);
            unit unit = new unit(rs.getInt("unit_id"), rs.getString("unitName"), rs.getInt("superior"), ru.EXXO.Status.CLEAN);

            Employee emp = new Employee(rs.getString("fio"), unit, rs.getString("position"), (rs.getInt("head") == 1), rs.getDate("birthday"),
                    rs.getString("education"), rs.getString("tellocal"), rs.getString("tellmob"), rs.getString("email"),
                    rs.getInt("id"), rs.getInt("global_id"), rs.getBoolean("photo"), rs.getString("hobby"), rs.getString("responsibility"),
                    rs.getString("tel"), office, rs.getString("room"), rs.getDate("worksince"), (rs.getInt("fired") != 0),
                    rs.getDate("firedsince"), (rs.getString("role") != null), rs.getString("comment"), Status.CLEAN);
            a.add(emp);
        }
        stmt.close();
        con.close();
        return a;

    }
}
