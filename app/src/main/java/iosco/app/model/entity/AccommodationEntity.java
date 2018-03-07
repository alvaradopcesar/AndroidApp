package iosco.app.model.entity;

import java.io.Serializable;

import iosco.app.utils.Helpers;

/**
 * Created by usuario on 01/03/2016.
 */
public class AccommodationEntity implements Serializable {
    private String Name;
    private String Address;
    private String WebSiteUrl;
    private String PhoneNumber;
    private String ContactName;
    private String EmailAddressContact;
    private double Latitude;
    private double Longitude;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getWebSiteUrl() {
        return WebSiteUrl;
    }

    public void setWebSiteUrl(String webSiteUrl) {
        WebSiteUrl = webSiteUrl;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getEmailAddressContact() {
        return EmailAddressContact;
    }

    public void setEmailAddressContact(String emailAddressContact) {
        EmailAddressContact = emailAddressContact;
    }

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
}
