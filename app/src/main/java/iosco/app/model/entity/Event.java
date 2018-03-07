package iosco.app.model.entity;

/**
 * Created by Victor Casas on 12/02/2016.
 */
public class Event extends Row{
    private String hour;
    private String name;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
