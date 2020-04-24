package com.example.weatherappwithnav;

public class User {
    int uid;
    String userName;
    String password;
    String colorPreference;

    public User() {
    }

    public User(int uid, String userName, String password, String colorPreference) {
        this.uid = uid;
        this.userName = userName;
        this.password = password;
        this.colorPreference = colorPreference;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getColorPreference() {
        return colorPreference;
    }

    public void setColorPreference(String colorPreference) {
        this.colorPreference = colorPreference;
    }
}
