package ru.EXXO.employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import ru.EXXO.Services.Service;
import ru.EXXO.Services.ServiceDAO;
import ru.EXXO.Status;

public class Employee implements java.io.Serializable, ru.EXXO.changeable {

    private String fio;
    private unit unit;
    private String position;
    private boolean head;
    private Date birthday;
    private String education;
    private String tellocal;
    private String tellmob;
    private String email;
    private int id;
    private int global_id;
    private boolean photo;
    private String hobby;
    private String comment;
    private String responsibility;
    private String tel;
    private Office office;
    private String room;
    private Date worksince;
    private boolean fired;
    private Date firedsince;
    private boolean initialBoss;
    private Status s;

    public Employee(String fio, unit unit, String position, boolean head, Date birthday, String education, String tellocal, String tellmob, String email, int id, int global_id, boolean photo, String hobby, String responsibility, String tel, Office office, String room, Date worksince, boolean fired, Date firedsince, boolean initialBoss, String comment, Status s) {
        this.fio = fio;
        this.unit = unit;
        this.position = position;
        this.head = head;
        this.birthday = birthday;
        this.education = education;
        this.tellocal = tellocal;
        this.tellmob = tellmob;
        this.email = email;
        this.id = id;
        this.global_id = global_id;
        this.photo = photo;
        this.hobby = hobby;
        this.responsibility = responsibility;
        this.tel = tel;
        this.office = office;
        this.room = room;
        this.worksince = worksince;
        this.fired = fired;
        this.firedsince = firedsince;
        this.initialBoss = initialBoss;
        this.comment = comment;
        this.s = s;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
        s = Status.MODIFIED;
    }

    public unit getUnit() {
        return unit;
    }

    public void setUnit(unit unit) {
        this.unit = unit;
        s = Status.MODIFIED;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
        s = Status.MODIFIED;
    }

    public boolean isHead() {
        return head;
    }

    public void setHead(boolean head) {
        this.head = head;
        s = Status.MODIFIED;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        s = Status.MODIFIED;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
        s = Status.MODIFIED;
    }

    public String getTellocal() {
        return tellocal;
    }

    public void setTellocal(String tellocal) {
        this.tellocal = tellocal;
        s = Status.MODIFIED;
    }

    public String getTellmob() {
        return tellmob;
    }

    public void setTellmob(String tellmob) {
        this.tellmob = tellmob;
        s = Status.MODIFIED;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        s = Status.MODIFIED;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        s = Status.MODIFIED;
    }

    public int getGlobal_id() {
        return global_id;
    }

    public void setGlobal_id(int global_id) {
        this.global_id = global_id;
        s = Status.MODIFIED;
    }

    public boolean isPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
        s = Status.MODIFIED;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
        s = Status.MODIFIED;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        s = Status.MODIFIED;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
        s = Status.MODIFIED;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
        s = Status.MODIFIED;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
        s = Status.MODIFIED;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
        s = Status.MODIFIED;
    }

    public Date getWorksince() {
        return worksince;
    }

    public void setWorksince(Date worksince) {
        this.worksince = worksince;
        s = Status.MODIFIED;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
        s = Status.MODIFIED;
    }

    public Date getFiredsince() {
        return firedsince;
    }

    public void setFiredsince(Date firedsince) {
        this.firedsince = firedsince;
        s = Status.MODIFIED;
    }

    public boolean isInitialBoss() {
        return initialBoss;
    }

    public void setInitialBoss(boolean initialBoss) {
        this.initialBoss = initialBoss;
        s = Status.MODIFIED;
    }

    public Status getStatus() {
        return s;
    }

    public void setStatus(Status s) {
        this.s = s;
    }

    public void applyChanges(HttpSession ses) throws SQLException {
        if (s == Status.NEW) {
            try {
                new EmployeeDAO(ses).addEmployee(this);
            } catch (IOException ex) {
                Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            }
            s = Status.CLEAN;
        } else if (s == Status.MODIFIED) {
            new EmployeeDAO().modifyEmployee(this);
            s = Status.CLEAN;
        }
    }

    public void remove(HttpSession ses) throws SQLException {
        if (s != Status.NEW) {
            new EmployeeDAO().removeEmployee(this);
        }
    }

    public void removePhoto() throws SQLException {
        if (photo) {
            new EmployeeDAO().removeEmployeePhoto(this.id);
        }

    }

    public void updatePhoto(HttpSession ses) throws SQLException, IOException {
        new EmployeeDAO(ses).updateEmployeePhoto(this);

    }

    public Date getLastTime() throws SQLException, IOException {
        return new EmployeeDAO().getLastTime(this);
    }

    public ArrayList<Service> findServicesList() throws SQLException {
        return new ServiceDAO().findServices(this);
    }
}
