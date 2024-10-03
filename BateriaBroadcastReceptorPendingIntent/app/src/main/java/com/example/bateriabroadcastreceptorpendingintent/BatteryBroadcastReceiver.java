package com.example.bateriabroadcastreceptorpendingintent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BatteryBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BatteryBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level * 100 / (float) scale;

            String batteryStatus = "Battery Level: " + batteryPct + "%";
            Log.d(TAG, batteryStatus);

            // Enviar broadcast local para actualizar la interfaz
            Intent localIntent = new Intent("BATTERY_STATUS_UPDATE");
            localIntent.putExtra("BATTERY_STATUS", batteryStatus);
            LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
        }
    }
}
