package com.example.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class NewProfile extends AppCompatActivity {
    private HashMap<String, String> profileData = new HashMap<String, String>();
    private final String TAG = "NewProfile";

    EditText et_name = null;
    EditText et_surname = null;
    EditText et_job = null;
    EditText et_birthplace = null;
    DatePicker birthday = null;
    AppCompatButton bttStart = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        et_name = findViewById(R.id.name);
        et_surname = findViewById(R.id.surname);
        et_job = findViewById(R.id.job);
        et_birthplace =  findViewById(R.id.birthplace);
        birthday = findViewById(R.id.birthday);
        bttStart = findViewById(R.id.btt_start);

        bttStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillProfile();
                Log.i(TAG, TAG + ": OnClick");
                startActivity(new Intent(NewProfile.this, PgQt.class));
            }
        });
    }

    private void fillProfile(){
        profileData.put("Name", et_name.getText().toString());
        profileData.put("Surname", et_surname.getText().toString());
        profileData.put("Job", et_job.getText().toString() );
        profileData.put("Birthplace", et_birthplace.getText().toString() );
        profileData.put("Birthday_day", Integer.toString(birthday.getDayOfMonth()));
        profileData.put("Birthday_month", Integer.toString(birthday.getMonth()));
        profileData.put("Birthday_year", Integer.toString(birthday.getYear()));
        System.out.println(profileData);
    }

    public HashMap<String, String> getProfileData(){ return profileData;}
}