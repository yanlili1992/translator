package com.example.myapplicationtranslator.reveiver;

import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Prediction;
import android.preference.PreferenceManager;
import android.widget.ShareActionProvider;

import com.example.myapplicationtranslator.service.ClipboardService;

/**
 * Created by liuht on 2017/3/13.
 */

public class StartClipbordServiceAtReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent){
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if(sharedPreferences.getBoolean("tap_translate",false)){
                Intent serviceIntent = new Intent(context, ClipboardService.class);
                context.startActivity(serviceIntent);
            }
        }
    }
}
