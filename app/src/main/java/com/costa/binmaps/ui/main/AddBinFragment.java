package com.costa.binmaps.ui.main;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.costa.binmaps.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBinFragment extends Fragment {

    Spinner dropdown;
    EditText mEdit;
    Button submit, clear;

    LocationManager locationManager;
    LocationListener locationListener;
    Location currentLocation;


    public AddBinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_bin, container, false);

        mEdit = view.findViewById(R.id.comment);
        dropdown = view.findViewById(R.id.type);
        submit = view.findViewById(R.id.submitButton);
        clear = view.findViewById(R.id.clearButton);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendToFirebase(v);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearComment(v);
            }
        });

        //create a list of items for the spinner.
        String[] items = new String[]{"Blue (Paper)",
                "Yellow (Plastic, Metal)",
                "Green (Glass)",
                "Black (Indifferent)",
                "Red (Batteries)",
                "Cooking oil",
                "Organic",
                "Clothes",
                "Appliances"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q &&
                (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
        } else {

            Location lastKnownLocation;

            // Emulator
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 30, locationListener);
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            /*
            // Phone
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 30, locationListener);
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
             */

            if (lastKnownLocation != null) {
                currentLocation = lastKnownLocation ;
            }
        }

        return view;
    }

    public void sendToFirebase(View v) {

        String type = dropdown.getSelectedItem().toString();
        String comment = mEdit.getText().toString();

        DatabaseReference mProfileRef = FirebaseDatabase.getInstance().getReference("Locations");
        FirebaseMarker marker = new FirebaseMarker(comment, type, currentLocation.getLatitude(), currentLocation.getLongitude());
        mProfileRef.push().setValue(marker);
        mEdit.getText().clear();


    }

    public void clearComment(View v){
        mEdit.getText().clear();
    }

}
