package com.example.dapp;

public class DoctorInfoClass {


    String Name , Phone , Email , Address , Type , Id ;


    public DoctorInfoClass(String name, String phone, String email, String address, String type, String id) {
        Name = name;
        Phone = phone;
        Email = email;
        Address = address;
        Type = type;
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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





}
