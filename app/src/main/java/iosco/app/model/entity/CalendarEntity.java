package iosco.app.model.entity;

import java.io.Serializable;

import iosco.app.utils.Helpers;

/**
 * Created by Victor Casas on 08/02/2016.
 */
public class CalendarEntity extends Row implements Serializable{
    private int idEvent;
    private int idDay;
    private int idMonth;
    private String name;
    private String duration;

    private int Id;
    private String Title;
    private String Description;
    private String StartDate;
    private String EndDate;
    private EventLocationEntity EventLocation;
    private int MapId;
    private boolean IsRegistered;
    private String SketchUrl;
    private String SketchUrlJpg;
    private String SketchDescription;

    private boolean IsPrivate;

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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return getStartHour()+":"+(((getStartMinute()+"").equals("0"))?"00":getStartMinute())+" - "+getEndHour()+":"+(((getEndMinute()+"").equals("0"))?"00":getEndMinute());
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public EventLocationEntity getEventLocation() {
        return EventLocation;
    }

    public void setEventLocation(EventLocationEntity eventLocation) {
        EventLocation = eventLocation;
    }

    public int getMapId() {
        return MapId;
    }

    public void setMapId(int mapId) {
        MapId = mapId;
    }

    public boolean isRegistered() {
        return IsRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        IsRegistered = isRegistered;
    }

    public int getIdMonth() {
        return idMonth;
    }

    public void setIdMonth(int idMonth) {
        this.idMonth = idMonth;
    }

    public int getStartYear(){
        return Integer.parseInt(getStartDate().substring(0,4));
    }

    public int getStartMonth(){
        return Integer.parseInt(getStartDate().substring(5,7));
    }

    public int getStartDay(){
        return Integer.parseInt(getStartDate().substring(8, 10));
    }

    private int getStartHour(){
        return Integer.parseInt(getStartDate().substring(11, 13));
    }

    private int getStartMinute(){
        return Integer.parseInt(getStartDate().substring(14, 16));
    }

    private int getEndHour(){
        return Integer.parseInt(getEndDate().substring(11, 13));
    }

    private int getEndMinute(){
        return Integer.parseInt(getEndDate().substring(14, 16));
    }

    public String getDate(){
        return getStartDay()+" "+ Helpers.getNameMonth(getStartMonth())+" "+getStartYear();
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSketchUrl() {
        return SketchUrl;
    }

    public void setSketchUrl(String sketchUrl) {
        SketchUrl = sketchUrl;
    }

    public String getSketchDescription() {
        return SketchDescription;
    }

    public void setSketchDescription(String sketchDescription) {
        SketchDescription = sketchDescription;
    }

    public String getSketchUrlJpg() {
        return SketchUrlJpg;
    }

    public void setSketchUrlJpg(String sketchUrlJpg) {
        SketchUrlJpg = sketchUrlJpg;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isPrivate() {
        return IsPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        IsPrivate = isPrivate;
    }
}
