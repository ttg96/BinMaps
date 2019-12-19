package com.costa.binmaps.ui.main;

import android.media.Image;

public class FirebaseMarker {

    public String comment;
    public String type;
    public double latitude;
    public double longitude;


    //required empty constructor
    public FirebaseMarker() {
    }

    public FirebaseMarker(String comment, String type, double latitude, double longitude) {
        this.comment = comment;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String name) {
        this.comment = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
