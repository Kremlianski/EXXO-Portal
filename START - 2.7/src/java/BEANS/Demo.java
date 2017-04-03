package BEANS;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Demo {

    public String getVer() {
        return BASE.Ver.getFullVer();
    }
}
