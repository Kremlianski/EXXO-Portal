package ru.EXXO.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date implements java.io.Serializable {

    SimpleDateFormat sf = null;
    SimpleDateFormat sf1 = null;
    SimpleDateFormat sf2 = null;
    private java.util.Date date;
    private String dateString;
    private String dateScript = "";
    private String properTime = "";

    public Date(java.util.Date date) {
        sf = new SimpleDateFormat("yyyy-MM-dd");
        sf1 = new SimpleDateFormat("yyyy,M-1,d");
        sf2 = new SimpleDateFormat("d MMM. yyyy, EEEE");
        this.date = date;
        this.dateString = sf.format(date);
        this.dateScript = "startdate=new Date(" + sf1.format(date) + ")";
        this.properTime = sf2.format(date);
    }

    public Date(String dateString) throws ParseException {
        sf = new SimpleDateFormat("yyyy-MM-dd");
        sf1 = new SimpleDateFormat("yyyy,M-1,d");
        sf2 = new SimpleDateFormat("d MMM. yyyy, EEEE");
        this.date = sf.parse(dateString);
        this.dateString = dateString;
        this.dateScript = "startdate=new Date(" + sf1.format(date) + ")";
        this.properTime = sf2.format(date);
    }

    public String getDateString() {
        return dateString;
    }

    public String getDateScript() {
        return dateScript;
    }

    public String getProperTime() {
        return properTime;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDateString(String dateString) throws ParseException {
        if (!dateString.equals("")) {
            this.date = sf.parse(dateString);
            this.dateString = dateString;
            this.dateScript = "startdate=new Date(" + sf1.format(date) + ")";
            this.properTime = sf2.format(date);
        } else {
            this.date = null;
            this.dateString = dateString;
            this.dateScript = "";
        }
    }

    public void setDateScript(String dateScript) {
        this.dateScript = dateScript;
    }

}
