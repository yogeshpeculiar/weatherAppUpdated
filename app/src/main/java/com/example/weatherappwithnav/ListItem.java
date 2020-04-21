package com.example.weatherappwithnav;

public class ListItem {
    private int mImageResource;
    private String city;
    private String temperature;

    public ListItem() {
    }

    public ListItem(int mImageResource, String city, String temperature) {
        this.mImageResource = mImageResource;
        this.city = city;
        this.temperature = temperature;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
