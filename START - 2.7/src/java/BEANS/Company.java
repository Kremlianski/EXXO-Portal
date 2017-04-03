
package BEANS;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped
public class Company {

    private ru.exxo.Company Company;

    public ru.exxo.Company getCompany() {
        ru.exxo.Company Company = new ru.exxo.Company();
        return Company;
    }

}
