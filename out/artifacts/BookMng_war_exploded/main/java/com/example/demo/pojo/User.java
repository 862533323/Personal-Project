package com.example.demo.pojo;

public class User {
    String Num;
    String Password;
    int Power;
    String Name;

    @Override
    public String toString() {
        return "UserController{" +
                "Num='" + Num + '\'' +
                ", Password='" + Password + '\'' +
                ", Power=" + Power +
                ", Name='" + Name + '\'' +
                '}';
    }
    public String toJson(){
        return "{" +
                "Num:" + Num +
                        ", Password:" + Password +
                        ", Power:" + Power +
                        ", Name:" + Name +
                        '}';
    }

    public User(String num, String password, int power, String name) {
        Num = num;
        Password = password;
        Power = power;
        Name = name;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getPower() {
        return Power;
    }

    public void setPower(int power) {
        Power = power;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
