package com.accessibility;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver{
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    private static final String TAG = "BootBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Boot this system , BootBroadcastReceiver onReceive()");

        if (intent.getAction().equals(ACTION_BOOT)) {
            Log.i(TAG, "BootBroadcastReceiver onReceive(), Do thing!");
            startService(context);
        }
      int i=   Integer.MAX_VALUE;//public static final int MAX_VALUE = 2147483647
    }

    /**
     * 开启服务
     * @param context
     */
    public void startService(Context context){
        String enabledServicesSetting = Settings.Secure.getString(
                context.getContentResolver(),               Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        ComponentName selfComponentName = new ComponentName(context.getPackageName(),
                "com.accessibility.AccessibilitySampleService");
        String flattenToString = selfComponentName.flattenToString();
        if (enabledServicesSetting==null||
                !enabledServicesSetting.contains(flattenToString)) {
            enabledServicesSetting += flattenToString;
        }
        Settings.Secure.putString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                enabledServicesSetting);
        Settings.Secure.putInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, 1);
    }
}
