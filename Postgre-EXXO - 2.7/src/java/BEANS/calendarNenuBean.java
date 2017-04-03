package BEANS;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class calendarNenuBean {

    String date;
    String hasDate = "";
    String stylecalendar = "";
    String styleoneday = "";
    String styletwoday = "";
    String stylethreeday = "";
    String stylecompare = "";
    String stylefive = "";
    String stylemonth = "";
    String todaylink = "allDay.xhtml";

    public void initParam(String date, String page) {
        if (date != null && !date.equals("")) {
            this.date = date;
        }
        if (this.date != null && !this.date.equals("")) {
            hasDate = "date";
        }
        if (page == null || page.equals("calendar") || page.equals("")) {
            stylecalendar = "exxo-menubold";
        } else if (page.equals("allday")) {
            styleoneday = "exxo-menubold";
            todaylink = "allDay.xhtml";
        } else if (page.equals("twoday")) {
            styletwoday = "exxo-menubold";
        } else if (page.equals("treeday")) {
            stylethreeday = "exxo-menubold";
        } else if (page.equals("compare")) {
            stylecompare = "exxo-menubold";
            todaylink = "calendarCompare.xhtml";
        } else if (page.equals("five")) {
            stylefive = "exxo-menubold";
            todaylink = "five.xhtml";
        } else if (page.equals("month")) {
            stylemonth = "exxo-menubold";
            todaylink = "month.xhtml";
        }
    }

    public String getHasDate() {
        return hasDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String l) {
        date = l;
    }

    ;
public String getStylecalndar() {
        return stylecalendar;
    }

    public String getStyleoneday() {
        return styleoneday;
    }

    public String getStyletwoday() {
        return styletwoday;
    }

    public String getStylethreeday() {
        return stylethreeday;
    }

    public String getStylecompare() {
        return stylecompare;
    }

    public String getStylefive() {
        return stylefive;
    }

    public String getStylemonth() {
        return stylemonth;
    }

    public String getTodaylink() {
        return todaylink;
    }
}
