package com.example.profile;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Calendar;
import java.util.HashMap;

public class NewProfile extends AppCompatActivity {
    private HashMap<String, String> profileData = new HashMap<String, String>();
    private final String TAG = "NewProfile";

    EditText et_name = null;
    EditText et_surname = null;
    EditText et_job = null;
    EditText et_birthplace = null;
    AppCompatButton bttStart = null;
    Button dateButton;
    TextView dateTextView;
    Calendar calendar1;
    int yearr; int monthh; int datee;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        et_name = findViewById(R.id.name);
        et_surname = findViewById(R.id.surname);
        et_job = findViewById(R.id.job);
        et_birthplace =  findViewById(R.id.birthplace);
        bttStart = findViewById(R.id.btt_start);
        dateButton = findViewById(R.id.dateButton);
        dateTextView = findViewById(R.id.dateTextView);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });

        bttStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillProfile();
                Log.i(TAG, TAG + ": OnClick");

                if(profileData.get("Name").isEmpty() || profileData.get("Surname").isEmpty() || profileData.get("Job").isEmpty() || profileData.get("Birthplace").isEmpty() || profileData.get("Birthday_day").isEmpty())
                    Toast.makeText(getApplicationContext(), "Insert your personal data!", Toast.LENGTH_LONG).show();
                else
                    startActivity(new Intent(NewProfile.this, PgQt.class).putExtra("profileData", profileData));
            }
        });

    }

    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                yearr=year;
                monthh=month;
                datee=date;
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                dateTextView.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    private void fillProfile(){
        profileData.put("Name", et_name.getText().toString());
        profileData.put("Surname", et_surname.getText().toString());
        profileData.put("Job", et_job.getText().toString() );
        profileData.put("Birthplace", et_birthplace.getText().toString() );
        profileData.put("Birthday_day", String.valueOf(yearr));
        profileData.put("Birthday_month", String.valueOf(monthh));
        profileData.put("Birthday_year", String.valueOf(datee));
        System.out.println(profileData);
    }

    public HashMap<String, String> getProfileData(){ return profileData;}
}