package com.pfeisims.riadh.familylocator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import static com.pfeisims.riadh.familylocator.MyServie.isRunning;

/**
 * Created by Riadh on 04/12/2017.
 */

public class StartOs  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")){
            Intent intentService=new Intent(context,MyServie.class);
            context.startService(intentService);
        }
    }
}
