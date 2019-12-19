package com.costa.binmaps;


import android.location.Location;

public class LocationData {

    public Location currentLocation;

    public LocationData(){

    }

    public Location getLocation(){
        return currentLocation;
    }
    public void setLocation(Location loc){
        currentLocation = loc;
    }

}
