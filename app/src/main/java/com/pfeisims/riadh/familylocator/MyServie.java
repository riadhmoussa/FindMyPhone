package com.pfeisims.riadh.familylocator;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Riadh on 04/12/2017.
 */

public class MyServie  extends Service{
    public static boolean isRunning=false;
    DatabaseReference databaseReference;
    public static Location location;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        isRunning=true;
        databaseReference= FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        GlobalInfo globalInfo=new GlobalInfo(this);
        globalInfo.LoadData();

            TrackLocation trackLocation = new TrackLocation(this);
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, trackLocation);
        databaseReference.child("Users").child(GlobalInfo.PhoneNumber).
                child("Updates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DateFormat df=new SimpleDateFormat("yyyy/mm/dd HH:MM:SS");
                Date date=new Date();
                if(location==null)return;
                databaseReference.child("Users").child(GlobalInfo.PhoneNumber).child("Location")
                        .child("lat").setValue(location.getLatitude());
                databaseReference.child("Users").child(GlobalInfo.PhoneNumber).child("Location")
                        .child("log").setValue(location.getLongitude());
                databaseReference.child("Users").child(GlobalInfo.PhoneNumber).child("Location")
                        .child("LastOnLineDate")
                        .setValue(df.format(date).toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return START_NOT_STICKY;
    }

    public class TrackLocation implements LocationListener {
Context context;
        public  boolean isRunning=false;
        public TrackLocation(Context context){
            this.isRunning=true;
            location=new Location("not defined");
            location.setLatitude(0);
            location.setLongitude(0);
            this.context=context;
        }
        @Override
        public void onLocationChanged(Location location) {

            databaseReference.child("Users").child(GlobalInfo.PhoneNumber).child("Location")
                    .child("lat").setValue(location.getLatitude());
            databaseReference.child("Users").child(GlobalInfo.PhoneNumber).child("Location")
                    .child("log").setValue(location.getLongitude());
            location=location;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

}
