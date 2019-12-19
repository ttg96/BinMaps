package com.costa.binmaps.ui.main;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
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

import com.costa.binmaps.LocationData;
import com.costa.binmaps.R;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBinFragment extends Fragment {

    Spinner dropdown;
    EditText mEdit;
    Button submit, clear;

    LocationData currentLocation;


    public AddBinFragment(LocationData locData) {
        currentLocation = locData;
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

        return view;
    }

    public void sendToFirebase(View v) {

        String type = dropdown.getSelectedItem().toString();
        String comment = mEdit.getText().toString();

        Location tempLoc = currentLocation.getLocation();

        DatabaseReference mProfileRef = FirebaseDatabase.getInstance().getReference("Locations");
        FirebaseMarker marker = new FirebaseMarker(comment, type, tempLoc.getLatitude(), tempLoc.getLongitude());
        mProfileRef.push().setValue(marker);
        mEdit.getText().clear();


    }

    public void clearComment(View v){
        mEdit.getText().clear();
    }

}
