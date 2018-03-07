package iosco.app.model.entity;

/**
 * Created by Victor Casas on 08/02/2016.
 */
public class File {
    private boolean isEvent;
    private int idEvent;
    private int idDay;
    private String name;
    private String duration;
    private int idMonth;
    private boolean isShow;

    public boolean isEvent() {
        return isEvent;
    }

    public void setIsEvent(boolean event) {
        this.isEvent = event;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdDay() {
        return idDay;
    }

    public void setIdDay(int idDay) {
        this.idDay = idDay;
    }

    public String getName() {
        if(!isEvent())
            return (idDay < 10 ? "0"+idDay : idDay)+" "+valMonth()+", 2016";
        else
            return name;
    }

    private String valMonth(){
        switch (this.idMonth){
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "not defined";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getIdMonth() {
        return idMonth;
    }

    public void setIdMonth(int idMonth) {
        this.idMonth = idMonth;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }
}
