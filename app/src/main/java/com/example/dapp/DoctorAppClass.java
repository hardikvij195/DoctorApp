package com.example.dapp;

public class DoctorAppClass {


    String Name , Num , Date ;

    public DoctorAppClass(String name, String num, String date) {

        Name = name;
        Num = num;
        Date = date;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
