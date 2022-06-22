package com.example.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import java.util.Iterator;

import Utility.Question;
import java.lang.String;
import java.util.concurrent.ExecutionException;

public class Report extends AppCompatActivity {

    Question currentQuestion;
    String filePath = "";
    TextView correct_answers, wrong_answers, it_answers, fr_answers, en_answers, final_message;
    private final String TAG = "Report";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Intent i = getIntent();
        ArrayList<Question> questionsData = (ArrayList<Question>) i.getSerializableExtra("report_data");
        Iterator<Question> questionIterator = questionsData.iterator();
        correct_answers = findViewById(R.id.correct_answers);
        wrong_answers = findViewById(R.id.wrong_answers);
        it_answers = findViewById(R.id.it_answers);
        fr_answers = findViewById(R.id.fr_answers);
        en_answers = findViewById(R.id.en_answers);
        final_message = findViewById(R.id.final_message);
        int it, fr, en, correct, wrong;
        it =0; fr=0; en=0; correct=0; wrong=0;

        while(questionIterator.hasNext()){
            currentQuestion = (Question) questionIterator.next();
            if(currentQuestion.getLanguage()==null) continue;
            if(currentQuestion.getLanguage().equals("FR")) fr++;
            if(currentQuestion.getLanguage().equals("IT")) it++;
            if(currentQuestion.getLanguage().equals("EN")) en++;
            if(currentQuestion.getAnswer()) correct++;
            if(!currentQuestion.getAnswer()) wrong++;
        }
        fr_answers.setText(String.valueOf(fr));
        it_answers.setText(String.valueOf(it));
        en_answers.setText(String.valueOf(en));
        wrong_answers.setText(String.valueOf(wrong));
        correct_answers.setText(String.valueOf(correct));
        if(correct<=12){
            final_message.setText("Forse dovresti rivolgerti ad uno specialista");
        }else if(correct<20){
            final_message.setText("Puoi migliorare");
        }else {
            final_message.setText("Hai una memoria di ferro!");
        }

    }
}