package com.pfeisims.riadh.familylocator;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Maps extends FragmentActivity implements OnMapReadyCallback {
    DatabaseReference databaseReference;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        GlobalInfo globalInfo=new GlobalInfo(this);
        Bundle b=getIntent().getExtras();

        LoadLocation(b.getString("PhoneNumber"));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }


    void LoadLocation(String PhoneNumber){

        databaseReference.child("Users").child(PhoneNumber).
                child("Location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String,Object> td=(HashMap<String,Object>)dataSnapshot.getValue();
                double lat=Double.parseDouble(td.get("lat").toString());
                double log=Double.parseDouble(td.get("log").toString());
                sydney=new LatLng(lat,log);
                LastDateInLine=td.get("LastOnLineDate").toString();
                LoadMap();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    void LoadMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    LatLng sydney ;
    String LastDateInLine;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        mMap.addMarker(new MarkerOptions().position(sydney).title("last on Line"+LastDateInLine));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
    }
}
