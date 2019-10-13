package com.example.demo.pojo;

import com.google.gson.Gson;

public class Book {
    int id;
    String name;
    String location;
    int value;
    int num;
    String describe;
    String image;

    public Book(int id, String name, String location, int value, int num, String describe, String image) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.value = value;
        this.num = num;
        this.describe = describe;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", value='" + value + '\'' +
                ", describe='" + describe + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
    public  String toJson(){
        final Gson gson=new Gson();
        return gson.toJson(this);
    }

    public Book(String name, int num, String location, int value, String describe, String image) {
        this.name = name;
        this.num = num;
        this.location = location;
        this.value = value;
        this.describe = describe;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
