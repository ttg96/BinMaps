package com.costa.binmaps;


import android.location.Location;

public class LocationData {

    public Location currentLocation;

    //To track the current location of the player and have it accessible to all classes
    public LocationData(){

    }

    //Get current location
    public Location getLocation(){
        return currentLocation;
    }

    //Set current location
    public void setLocation(Location loc){
        currentLocation = loc;
    }

}
