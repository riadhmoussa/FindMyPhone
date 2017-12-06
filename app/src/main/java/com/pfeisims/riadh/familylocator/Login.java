package com.pfeisims.riadh.familylocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Login extends AppCompatActivity {
EditText EDTNumber;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EDTNumber=(EditText)findViewById(R.id.EDTNumber);
        MobileAds.initialize(this,
                "ca-app-pub-8589437265385157/8599178046");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void BuNext(View view) {
        GlobalInfo.PhoneNumber=GlobalInfo.FormatPhoneNumber(EDTNumber.getText().toString());
        GlobalInfo.UpdatesInfo(GlobalInfo.PhoneNumber);
        finish();
        Intent intent=new Intent(this,MyTrackers.class);
        startActivity(intent);
    }

}
