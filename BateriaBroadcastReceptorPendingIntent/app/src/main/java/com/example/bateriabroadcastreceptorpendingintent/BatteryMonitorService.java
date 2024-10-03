package com.example.bateriabroadcastreceptorpendingintent;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BatteryMonitorService extends Service {
    private BroadcastReceiver batteryReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float batteryPct = level * 100 / (float) scale;
                String batteryStatus = "Battery Level: " + batteryPct + "%";

                Intent localIntent = new Intent("BATTERY_STATUS_UPDATE");
                localIntent.putExtra("BATTERY_STATUS", batteryStatus);
                LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
            }
        };
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
