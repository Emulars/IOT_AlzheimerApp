package com.example.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import Utility.Question;

public class Report extends AppCompatActivity {

    private ArrayList<Question> questionsData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent i = getIntent();
        questionsData = (ArrayList<Question>)i.getSerializableExtra("report_data");
    }
}