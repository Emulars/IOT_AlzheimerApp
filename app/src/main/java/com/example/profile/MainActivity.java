package com.example.profile;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    private final String TAG = "MainActivity";
    Button bttNewProfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttNewProfile = findViewById(R.id.btt_start);
        bttNewProfile.setOnClickListener(view -> {
            // Check for Internet Connection
            if (isConnected()) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show());
            }
            if(CheckPermissions()) {
                startActivity(new Intent(MainActivity.this, NewProfile.class));
            }
            else { RequestPermissions(); }
        });
    }

    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        } catch (Exception e) {
            Log.e(TAG, "Connectivity Exception".concat(e.getMessage()));
        }
        return connected;
    }

    //Richiesta permessi on the fly
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_AUDIO_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (permissionToRecord && permissionToStore) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show());
                }
            }
        }
    }


}