package iosco.app.model.entity;

import java.io.Serializable;

/**
 * Created by usuario on 16/02/2016.
 */
public class EventLocationEntity implements Serializable{
    private double Latitude;
    private double Longitude;
    private String Description;

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
