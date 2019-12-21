package com.costa.binmaps.ui.main;


public class FirebaseMarker {

    public String comment;
    public String type;
    public double latitude;
    public double longitude;


    //required empty constructor
    public FirebaseMarker() {
    }

    //Constructor for custom markers
    public FirebaseMarker(String comment, String type, double latitude, double longitude) {
        this.comment = comment;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //Return comment attribute
    public String getComment() {
        return comment;
    }

    //Change comment attribute
    public void setComment(String name) {
        this.comment = name;
    }

    //Get type of bin attribute
    public String getType() {
        return type;
    }

    //Set type of bin attribute
    public void setType(String type) {
        this.type = type;
    }

    //Get longitude of bin
    public double getLongitude() {
        return longitude;
    }

    //Set longitude of bin
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //Get latitude of bin
    public double getLatitude() {
        return latitude;
    }

    //Set latitude of bin
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
