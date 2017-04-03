package ru.exxo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExxoDate implements Serializable {

    private Date Date;
    private String dateString = "";
    private String dateScript = "";
    private String properTime = "";
    SimpleDateFormat sf = null;
    SimpleDateFormat sf1 = null;

    public ExxoDate() {
        sf = new SimpleDateFormat("yyyy-MM-dd");
        sf1 = new SimpleDateFormat("yyyy,M-1,d");
    }

    public ExxoDate(Date Date) {
        sf = new SimpleDateFormat("yyyy-MM-dd");
        sf1 = new SimpleDateFormat("yyyy,M-1,d");
        this.Date = Date;
        dateString = sf.format(Date);
        dateScript = "startdate=new Date(" + sf1.format(Date) + ")";
    }

    public ExxoDate(String dateString) throws ParseException {
        sf = new SimpleDateFormat("yyyy-MM-dd");
        sf1 = new SimpleDateFormat("yyyy,M-1,d");
        this.dateString = dateString;
        Date = sf.parse(dateString);
        dateScript = "startdate=new Date(" + sf1.format(Date) + ")";
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
        dateString = sf.format(Date);
        dateScript = "new Date(" + sf1.format(Date) + ")";
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) throws ParseException {
        this.dateString = dateString;
        if (!dateString.equals("")) {
            Date = sf.parse(dateString);
            dateScript = "new Date(" + sf1.format(Date) + ")";
        }
    }

    public String getDateScript() {
        return dateScript;
    }

    public String getProperTime() {
        SimpleDateFormat sf2 = new SimpleDateFormat("d MMM. yyyy, EEEE");
        if (Date != null) {
            properTime = sf2.format(Date);
        }
        return properTime;
    }
}
