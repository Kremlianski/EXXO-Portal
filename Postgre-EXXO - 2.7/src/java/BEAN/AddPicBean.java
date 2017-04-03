package BEAN;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class AddPicBean implements java.io.Serializable {

    private String description;
    private String tags;
    private String name;
    private boolean notshow = true;

    public String init() {
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ses.setAttribute("addPicBean", this);
        name = "";
        tags = "";
        description = "";
        notshow = true;
        return null;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNotshow() {
        return notshow;
    }

    public void setNotshow(boolean notshow) {
        this.notshow = notshow;
    }

}
