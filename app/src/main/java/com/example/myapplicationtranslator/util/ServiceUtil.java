package com.example.myapplicationtranslator.util;

import android.app.ActivityManager;
import android.content.Context;
import android.database.ContentObservable;
import android.net.ConnectivityManager;

/**
 * Created by liuht on 2017/3/13.
 */

public class ServiceUtil {

    public boolean isMyServiceRunning(Context context, Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}
