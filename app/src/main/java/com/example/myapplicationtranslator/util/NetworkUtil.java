package com.example.myapplicationtranslator.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.myapplicationtranslator.constant.Constants;

/**
 * Created by liuht on 2017/3/13.
 */

public class NetworkUtil {

    public  static boolean isNetworkConnected(Context context){

        if(context != null){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info != null){
                return true;
            }
        }

        return false;
    }
}
