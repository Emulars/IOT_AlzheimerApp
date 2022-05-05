package com.example.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewProfile extends AppCompatActivity {

    private final String TAG = "NewProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);
        Button btnInv = (Button) findViewById(R.id.btt_start);

        btnInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPage1 = new Intent(NewProfile.this, PgQt.class);
                startActivity(openPage1);
            }
        });
    }
}