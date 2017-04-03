package BEAN;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class PhotoBean implements Serializable {

    private boolean photo = false;
    private byte[] image = null;
    private int width = 0;
    private int height = 0;
    private int x = 0;
    private int y = 0;
    private String type = "";

    public boolean isPhoto() throws ClassNotFoundException, SQLException {
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return new ru.EXXO.employee.PhotoDAO(ses).isPhoto();
    }

    public void updateImage() throws IOException, ClassNotFoundException, SQLException {
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ru.EXXO.employee.Photo p = new ru.EXXO.employee.PhotoDAO(ses).loadPhoto();
        image = p.getImage();
        type = p.getType();
        String format = EXXOlib.IMGESLIB.getFormat(image, type);
        image = EXXOlib.IMGESLIB.photoCrop(image, width, height, x, y, format);
        p.setImage(image);
        p.applyChanges(ses);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
