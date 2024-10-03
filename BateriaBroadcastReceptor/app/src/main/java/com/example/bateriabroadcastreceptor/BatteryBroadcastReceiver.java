package com.example.bateriabroadcastreceptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.TextView;

public class BatteryBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BatteryBroadcastReceiver";
    private final TextView batteryStatusTextView;

    public BatteryBroadcastReceiver(TextView batteryStatusTextView) {
        this.batteryStatusTextView = batteryStatusTextView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level * 100 / (float) scale;

            String batteryStatus = "Estado de la batería: " + batteryPct + "%";
            Log.d(TAG, batteryStatus);

            // Actualizar el TextView con el nivel de batería
            batteryStatusTextView.setText(batteryStatus);
        }
    }
}