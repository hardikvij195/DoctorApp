package com.example.dapp;

public class UserAppClass {


    String Name , Phone , Email , Address , Type , Date ;

    public UserAppClass(String name, String phone, String email, String address, String type, String date) {
        Name = name;
        Phone = phone;
        Email = email;
        Address = address;
        Type = type;
        Date = date;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
