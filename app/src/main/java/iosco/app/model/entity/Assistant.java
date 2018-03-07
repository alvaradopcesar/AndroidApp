package iosco.app.model.entity;

import java.io.Serializable;

import iosco.app.utils.Helpers;

/**
 * Created by Victor Casas on 11/02/2016.
 */
public class Assistant implements Serializable {
    private String nombre;
    private String apellido;
    private String position;
    private String photo;

    private int Id;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String Title;
    private String Brief;
    private String JobTitle;
    private CountryEntity Country;
    private OrganizationEntity Organization;
    private String FacebookURL;
    private String TwitterURL;
    private String EmailAddress;
    private String MobileNumber;
    private String PhoneNumber;
private boolean HasProfilePicture;

    public boolean isHasProfilePicture() {
        return HasProfilePicture;
    }

    public void setHasProfilePicture(boolean hasProfilePicture) {
        HasProfilePicture = hasProfilePicture;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName == null ? "" : FirstName; //Helpers.capitalizeString(FirstName.toLowerCase());
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName == null ? "" : LastName;// Helpers.capitalizeString(LastName.toLowerCase());
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBrief() {
        return Brief;
    }

    public void setBrief(String brief) {
        Brief = brief;
    }

    public String getJobTitle() {
        return JobTitle == null ? "" : JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public CountryEntity getCountry() {
        return Country;
    }

    public void setCountry(CountryEntity country) {
        Country = country;
    }

    public OrganizationEntity getOrganization() {
        return Organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        Organization = organization;
    }

    public String getFacebookURL() {
        return FacebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        FacebookURL = facebookURL;
    }

    public String getTwitterURL() {
        return TwitterURL;
    }

    public void setTwitterURL(String twitterURL) {
        TwitterURL = twitterURL;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
