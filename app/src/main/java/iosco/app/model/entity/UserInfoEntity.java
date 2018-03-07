package iosco.app.model.entity;

import java.io.Serializable;
import java.util.Date;

import iosco.app.utils.Helpers;

/**
 * Created by usuario on 01/03/2016.
 */
public class UserInfoEntity implements Serializable{
    private boolean PendingPay;
    private AccommodationEntity Accommodation;
    private FlightEntity Flight;
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

    private boolean AccomodationInfoVisible;
    private boolean FlightInfoVisible;
    private boolean PayInfoVisible;

private boolean HasProfilePicture;
    private boolean SurveyAnswered;
    private Date SurveyEnableDate;


    public boolean isHasProfilePicture() {
        return HasProfilePicture;
    }

    public void setHasProfilePicture(boolean hasProfilePicture) {
        HasProfilePicture = hasProfilePicture;
    }
    public boolean isPendingPay() {
        return PendingPay;
    }

    public void setPendingPay(boolean pendingPay) {
        PendingPay = pendingPay;
    }

    public AccommodationEntity getAccommodation() {
        return Accommodation;
    }

    public void setAccommodation(AccommodationEntity accommodation) {
        Accommodation = accommodation;
    }

    public FlightEntity getFlight() {
        return Flight;
    }

    public void setFlight(FlightEntity flight) {
        Flight = flight;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName == null ? "" : FirstName;
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
        return LastName == null ? "" : LastName;
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
        return JobTitle;
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

    public boolean isAccomodationInfoVisible() {
        return AccomodationInfoVisible;
    }

    public void setAccomodationInfoVisible(boolean accomodationInfoVisible) {
        AccomodationInfoVisible = accomodationInfoVisible;
    }

    public boolean isFlightInfoVisible() {
        return FlightInfoVisible;
    }

    public void setFlightInfoVisible(boolean flightInfoVisible) {
        FlightInfoVisible = flightInfoVisible;
    }

    public boolean isPayInfoVisible() {
        return PayInfoVisible;
    }

    public void setPayInfoVisible(boolean payInfoVisible) {
        PayInfoVisible = payInfoVisible;
    }

    public boolean isSurveyAnswered() {
        return SurveyAnswered;
    }

    public void setSurveyAnswered(boolean surveyAnswered) {
        SurveyAnswered = surveyAnswered;
    }

    public Date getSurveyEnableDate() {
        return SurveyEnableDate;
    }

    public void setSurveyEnableDate(Date surveyEnableDate) {
        SurveyEnableDate = surveyEnableDate;
    }
}
