package com.urbler;

public class appPojo {
    public appPojo(){
        //for firebase
    }
    String city;
    String phoneNumber;
    String type;
    int qty,position;
    String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public appPojo(String city, String phoneNumber, String type, int qty, int position, String day) {
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.qty = qty;
        this.position = position;
        this.day=day;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
