package com.example.profile;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.io.File;
import java.io.IOException;

public class PgQt extends AppCompatActivity {

    private final String TAG = "PgQt";
    Button btt_start = null;
    Button btt_Stop = null;

    // Audio
    private String outputFile;
    private MediaRecorder myAudioRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qt);
        btt_start = findViewById(R.id.btt_mic);
        btt_Stop = findViewById(R.id.btt_stop);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        btt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                }catch (IllegalStateException e){
                    Log.i(TAG, "IllegalStateException");
                    e.printStackTrace();
                } catch (IOException e){
                    Log.i(TAG, "IOException");
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        btt_Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    myAudioRecorder.stop();
                }catch (IllegalStateException e){
                    Log.i(TAG, "IllegalStateException");
                    e.printStackTrace();
                }

                myAudioRecorder.reset();
                myAudioRecorder.release();
                myAudioRecorder = null;
                Toast.makeText(getApplicationContext(), "Audio Recorder successfully", Toast.LENGTH_LONG).show();
            }
        });

    }
}
