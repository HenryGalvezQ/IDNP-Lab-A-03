package com.example.bateriabroadcastreceptor;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Receptor";

    private MoonBroadcastReceiver moonBroadcastReceiver = new MoonBroadcastReceiver();
    private BatteryBroadcastReceiver batteryBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        TextView batteryStatusTextView = findViewById(R.id.textViewBatteryStatus);
        batteryBroadcastReceiver = new BatteryBroadcastReceiver(batteryStatusTextView);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        IntentFilter filter = new IntentFilter(MoonBroadcastReceiver.EXTRA_MOON_PHASE);
        registerReceiver(moonBroadcastReceiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        IntentFilter batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBroadcastReceiver, batteryFilter);
        Log.d(TAG, "BatteryBroadcastReceiver registrado satisfactoriamente");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(batteryBroadcastReceiver);
        Log.d(TAG, "BatteryBroadcastReceiver desregistrado satisfactoriamente");
    }

    @Override
    public void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        unregisterReceiver(moonBroadcastReceiver);
        super.onDestroy();
    }
}
