package com.neory.marketsimplifieddemoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(context instanceof BottomNavigationActivity){
            ((BottomNavigationActivity)context).ShowSneak(intent);
        }

    }
}
