package com.example.bateriabroadcastreceptorpendingintent;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ReceptorPendingIntent";
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private TextView textViewBatteryStatus;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver batteryStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        textViewBatteryStatus = findViewById(R.id.textViewBatteryStatus);

        // Registrar el BroadcastReceiver para ACTION_BATTERY_CHANGED
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(null, filter);

        // Configurar LocalBroadcastManager para recibir el estado de la batería
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        batteryStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String batteryStatus = intent.getStringExtra("BATTERY_STATUS");
                runOnUiThread(() -> textViewBatteryStatus.setText(batteryStatus));
            }
        };

        // Iniciar el servicio que monitoreará la batería
        Intent serviceIntent = new Intent(this, BatteryMonitorService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        localBroadcastManager.registerReceiver(batteryStatusReceiver, new IntentFilter("BATTERY_STATUS_UPDATE"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(batteryStatusReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Log.d(TAG, "AlarmManager cancelado en onDestroy");
        }
    }
}
