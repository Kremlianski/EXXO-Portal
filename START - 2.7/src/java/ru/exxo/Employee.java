package ru.exxo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class Employee implements Serializable {

    private String fio = "";
    private String fioShort = "";
    private int unit = 0;
    private String unitName;
    private String position = "";
    private boolean head = false;
    private Date birthday = null;
    private String education = null;
    private String tellocal = null;
    private String tellmob = null;
    private String email = null;
    private int id;
    private int global_id = 0;
    private boolean photo = false;
    private String hobby = null;
    private String comment = null;
    private String responsibility = null;
    private String tel = null;
    private String office = "Переезд";
    private String room = null;
    private Date worksince = null;
    private boolean fired = false;
    private Date firedsince = null;
    private Connection con = null;

    public Employee() {
        //     worksince=new Date();
        //     birthday=new Date();
    }

    public Employee(int id) throws SQLException {
        this.id = id;
        loadEmp();
    }

    private void loadEmp() throws SQLException {
        //дописать

    }

    public void udate() throws SQLException {
        //дописать

    }

    public void insert() throws SQLException {
        String sql = "INSERT INTO employee (fio,unit,position,birthday,head,education,tellocal,tellmob,"
                + "email,hobby,comment,responsibility, tel, room, office, photo, workSince, role) values "
                + "(?, ?::int, ?, ?::Date, ?::smallint, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT photo FROM employeeTMP WHERE id='" + id + "'), ?::Date)";
        con.setAutoCommit(false);
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, fio);
        ps.setInt(2, unit);
        ps.setString(3, position);
        ps.setDate(4, (java.sql.Date) birthday);
        String headStr = "0";
        if (head) {
            headStr = "1";
        }
        ps.setString(5, headStr);
        ps.setString(6, education.replace("\n<br>", "<br>").replace("\n", "<br>"));
        ps.setString(7, tellocal);
        ps.setString(8, tellmob);
        ps.setString(9, email);
        ps.setString(10, hobby.replace("\n<br>", "<br>").replace("\n", "<br>"));
        ps.setString(11, comment.replace("\n<br>", "<br>").replace("\n", "<br>"));
        ps.setString(12, responsibility.replace("\n<br>", "<br>").replace("\n", "<br>"));
        ps.setString(13, tel);
        ps.setString(14, room);
        ps.setString(15, office);
        ps.setDate(16, (java.sql.Date) worksince);
        ps.setString(17, "a");

        con.commit();
        ps.close();
        con.close();
    }

    //////////////////
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isHead() {
        return head;
    }

    public void setHead(boolean head) {
        this.head = head;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTellocal() {
        return tellocal;
    }

    public void setTellocal(String tellocal) {
        this.tellocal = tellocal;
    }

    public String getTellmob() {
        return tellmob;
    }

    public void setTellmob(String tellmob) {
        this.tellmob = tellmob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGlobal_id() {
        return global_id;
    }

    public void setGlobal_id(int global_id) {
        this.global_id = global_id;
    }

    public boolean isPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getWorksince() {
        return worksince;
    }

    public void setWorksince(Date worksince) {
        this.worksince = worksince;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public Date getFiredsince() {
        return firedsince;
    }

    public void setFiredsince(Date firedsince) {
        this.firedsince = firedsince;
    }

    public Connection getCon() {
        return con;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFioShort() {
        return fioShort;
    }

    private void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + BASE.Ver.getDBHost() + "/" + BASE.Ver.getDBName();
        Properties properties = BASE.Ver.getDBProp();
        con = DriverManager.getConnection(url, properties);
    }
}
