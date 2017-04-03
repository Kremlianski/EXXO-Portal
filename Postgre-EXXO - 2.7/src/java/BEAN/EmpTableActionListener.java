package BEAN;

import javax.faces.bean.ManagedProperty;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class EmpTableActionListener implements ActionListener {

    @ManagedProperty(value = "#{employeeBlockBean}")
    private EmployeeBlockBean eb;

    @Override
    public void processAction(ActionEvent ae) throws AbortProcessingException {
        eb.switchOn();
    }

    public EmployeeBlockBean getEb() {
        return eb;
    }

    public void setEb(EmployeeBlockBean eb) {
        this.eb = eb;
    }

}
