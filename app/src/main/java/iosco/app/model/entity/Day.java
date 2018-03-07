package iosco.app.model.entity;

import java.util.ArrayList;

/**
 * Created by Victor Casas on 08/02/2016.
 */
public class Day {
    private int idMonth;
    private int idDay;
    private String name;
    private ArrayList<CalendarEntity> list;

    public int getIdDay() {
        return idDay;
    }

    public void setIdDay(int idDay) {
        this.idDay = idDay;
    }

    public String getName() {

        return (idDay < 10 ? "0"+idDay : idDay)+" "+valMonth()+", 2016";
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

    public ArrayList<CalendarEntity> getList() {
        return list;
    }

    public void setList(ArrayList<CalendarEntity> list) {
        this.list = list;
    }

    public int getIdMonth() {
        return idMonth;
    }

    public void setIdMonth(int idMonth) {
        this.idMonth = idMonth;
    }
}
