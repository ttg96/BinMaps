package com.costa.binmaps.ui.main;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costa.binmaps.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBinFragment extends Fragment {

    public AddBinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_bin, container, false);
        return view;
    }

    public void sendToFirebase() {
        DatabaseReference mProfileRef = FirebaseDatabase.getInstance().getReference("Locations");
        FirebaseMarker marker = new FirebaseMarker("Lincoln", "Hall", -34.506081, 150.88104, "24/12/1940", "02/07/2016" );
        mProfileRef.push().setValue(marker);
    }

}
