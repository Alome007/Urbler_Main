package com.urbler;

public class regPojo {
String city,country,dateOfBirth, firstName,surName,homeaddress,avatarUrl,state;

public regPojo(){
        //for firebase...
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public regPojo(String city, String country, String dateOfBirth, String firstName, String surName, String homeaddress, String avatarUrl, String state) {
        this.city = city;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.surName = surName;
        this.homeaddress = homeaddress;
        this.avatarUrl = avatarUrl;
        this.state = state;
    }
}
