package BEAN;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "employeeBlockBean")
@SessionScoped
public class EmployeeBlockBean implements Serializable {

    private boolean table;

    @PostConstruct
    public void init() {
        table = true;
    }

    public void switchOn() {
        table = true;
    }

    public void switchOff() {
        table = false;
    }

    public boolean isTable() {
        return table;
    }

    public void setTable(boolean table) {
        this.table = table;
    }

}
