package com.costa.binmaps.ui.main;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costa.binmaps.LocationData;
import com.costa.binmaps.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapTab extends Fragment implements OnMapReadyCallback {

    //Database values
    private DatabaseReference mDatabase;

    //Map values
    ChildEventListener mChildEventListener;
    LocationData currentLocation;
    GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    //Markers
    BitmapDescriptor recyclingMarker, wasteMarker, batteryMarker, organicMarker, oilMarker, clothesMarker, applianceMarker;

    public MapTab(LocationData locData) {
        mDatabase = FirebaseDatabase.getInstance().getReference("Locations");
        currentLocation = locData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        setupMarkerIcon();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        return rootView;
    }


    //Update the map and player location
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.clear();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation.setLocation(location);
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.clear();
                addMarkersToMap(mMap);
                mMap.addMarker(new MarkerOptions()
                        .position(userLocation)
                        .title("Your current location")
                        .snippet("Please work")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(isEmulator()){
                //Emulator
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 30, locationListener);
                currentLocation.setLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
            } else {
                //Phone
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 30, locationListener);
                currentLocation.setLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
            }
            Location tempLoc = currentLocation.getLocation();

            if (tempLoc!= null) {
                LatLng userLocation = new LatLng(tempLoc.getLatitude(), tempLoc.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(userLocation)
                        .title("Your current location")
                        .snippet("User")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
            }
        }
    }

    //Add custom markers to map
    private void addMarkersToMap(final GoogleMap map){

        mChildEventListener = mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseMarker marker = dataSnapshot.getValue(FirebaseMarker.class);
                Double latitude = marker.getLatitude();
                Double longitude = marker.getLongitude();
                String type = marker.getType();
                String comment = marker.getComment();
                LatLng location = new LatLng(latitude,longitude);
                switch (type) {
                    case "Blue (Paper)":
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment)
                                .icon(recyclingMarker));
                        Log.d("Report", "blue");
                        break;
                    case "Yellow (Plastic, Metal)":
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment)
                                .icon(recyclingMarker));
                        Log.d("Report", "yellow");
                        break;
                    case "Green (Glass)":
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment)
                                .icon(recyclingMarker));
                        Log.d("Report", "green");
                        break;
                    case "Triple (Blue, Yellow, Green)":
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment)
                                .icon(recyclingMarker));
                        Log.d("Report", "triple");
                        break;
                    case "Black (Indifferent)":
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment)
                                .icon(wasteMarker));
                        Log.d("Report", "black");
                        break;
                    case "Red (Batteries)":
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment)
                                .icon(batteryMarker));
                        Log.d("Report", "red");
                        break;
                    case "Cooking oil":
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment)
                                .icon(oilMarker));
                        Log.d("Report", "oil");
                        break;
                    case "Organic":
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment)
                                .icon(organicMarker));
                        Log.d("Report", "organic");
                        break;
                    case "Clothes":
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment)
                                .icon(clothesMarker));
                        Log.d("Report", "clothes");
                        break;
                    case "Appliances":
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment)
                                .icon(applianceMarker));
                        Log.d("Report", "appliances");
                        break;
                    default:
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet(comment));
                        break;

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Create bitmap images for markers
    private void setupMarkerIcon(){
        int height = 128;
        int width = 128;
        Bitmap b, smallMarker;

        b = BitmapFactory.decodeResource(getResources(), R.drawable.recycling_bin);
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        recyclingMarker = BitmapDescriptorFactory.fromBitmap(smallMarker);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.waste_bin);
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        wasteMarker = BitmapDescriptorFactory.fromBitmap(smallMarker);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.battery_bin);
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        batteryMarker = BitmapDescriptorFactory.fromBitmap(smallMarker);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.oil_bin);
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        oilMarker = BitmapDescriptorFactory.fromBitmap(smallMarker);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.organic_waste_bin);
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        organicMarker = BitmapDescriptorFactory.fromBitmap(smallMarker);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.clothes_bin);
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        clothesMarker = BitmapDescriptorFactory.fromBitmap(smallMarker);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.appliance_bin);
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        applianceMarker = BitmapDescriptorFactory.fromBitmap(smallMarker);
    }

    //Check if user is on emulator or on a phone
    private boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }
}
