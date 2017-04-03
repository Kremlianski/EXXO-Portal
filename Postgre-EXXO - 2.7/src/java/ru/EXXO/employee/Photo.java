package ru.EXXO.employee;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;

public class Photo implements ru.EXXO.changeable {

    private byte[] image;
    private String type;
    private ru.EXXO.Status s;

    public Photo(byte[] image, String type, Status s) {
        this.image = image;
        this.type = type;
        this.s = s;
    }

    public Photo() {
        this.s = Status.CLEAN;
    }

    public Status getStatus() {
        return s;
    }

    public void setStatus(Status s) {
        this.s = s;
    }

    public void applyChanges(HttpSession ses) throws SQLException {
        if (s == Status.NEW) {
            new PhotoDAO(ses).addPhoto(this);
            s = Status.CLEAN;
        } else if (s == Status.MODIFIED) {
            new PhotoDAO(ses).modifyPhoto(this);
            s = Status.CLEAN;
        }
    }

    public void remove(HttpSession ses) throws SQLException {
        if (s != Status.NEW) {
            new PhotoDAO(ses).removePhoto();
        }
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
        s = Status.MODIFIED;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        s = Status.MODIFIED;
    }

}
