package com.example.demo.pojo;

public class Sit {
    String uid;

    String location;

    public Sit(String uid, String location) {
        this.uid = uid;
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
