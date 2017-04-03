package BEAN;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import ru.EXXO.Ratings.EmployeeVoice;

@ManagedBean
@ViewScoped
public class RatingBean implements java.io.Serializable {

    private EmployeeVoice voice;

    public void init(EmployeeVoice voice) {
        setVoice(voice);
    }

    public void update() throws SQLException {
        voice.applyChanges(null);
    }

    public EmployeeVoice getVoice() {
        return voice;
    }

    public void setVoice(EmployeeVoice voice) {
        this.voice = voice;
    }

}
