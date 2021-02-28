package com.example.depremfectherdemo1;

import java.util.Date;

public class Quake {

    private String date;
    private float latitude ,longtitude,depth,magnitude;
    private String epicenter;

    public Quake(String date, float latitude, float longtitude, float depth, float magnitude, String epicenter) {
        this.date = date;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.depth = depth;
        this.magnitude = magnitude;
        this.epicenter = epicenter;
    }

    @Override
    public String toString() {
        return "Quake{" +
                "date=" + date +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                ", depth=" + depth +
                ", magnitude=" + magnitude +
                ", epicenter='" + epicenter + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public float getDepth() {
        return depth;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public String getEpicenter() {
        return epicenter;
    }
}
