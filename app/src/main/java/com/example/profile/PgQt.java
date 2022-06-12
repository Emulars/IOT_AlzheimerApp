package com.example.profile;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import Utility.HttpPostAsyncTask;
import Utility.Question;

// TODO:
// - Disabilitare bottoni prima della registrazione
// - Rimuovere variabile globale currentQuestion
// - Per profilazione potremmo usare Hashtable

public class PgQt extends AppCompatActivity {

    private final String TAG = "PgQt";
    TextView tv_question, tv_counter = null;
    Button btt_record, btt_stop, btt_play, btt_next = null;

    // Audio
    private static int MICROPHONE_PERMISSION_CODE=200;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String filePath = "";

    // Question
    Question currentQuestion = null;
    private ArrayList<Question> questions = new ArrayList<Question>();
    Iterator questionIterator = null;

    private void fillQuestions(ArrayList<Question> q){
        q.add(new Question("Come ti chiami ?", "name"));
        q.add(new Question("Quanti anni hai ?", "age"));
        q.add(new Question("Che ore sono ?", "time"));
        q.add(new Question("In che momento del giorno siamo ? (Mattina, Pomeriggio, ...)", "day_moment"));
        q.add(new Question("Quanti ne abbiamo ?", "day"));
        q.add(new Question("Che giorno è ?", "day_of_the_week"));
        q.add(new Question("In che mese siamo ?", "month"));
        q.add( new Question("In che stagione siamo ?", "season"));
        q.add(new Question("In che anno siamo ?", "year"));
        q.add(new Question("Che oggetto è ? (1)", "object_1"));
        q.add(new Question("Che oggetto è ? (2)", "object_2"));

        q.add(new Question("Ricordati questo nome: \'Mario\', ti verrà richiesto in seguito", "name_ta"));
        q.add(new Question("Ricordati questo indirizzo: \'Via Bobbio\', ti verrà richiesto in seguito", "street_ta"));

        q.add(new Question("Quando sei natə ?", "birthday"));
        q.add(new Question("Dove sei natə ?", "birth_loc"));
        q.add(new Question("Che lavoro svogli/hai svolto ?", "job"));
        q.add(new Question("Data inizio della prima guerra mondiale ?", "wwo_1"));
        q.add(new Question("Data fine della prima guerra mondiale ?", "wwo_2"));
        q.add(new Question("Data inizio della seconda guerra mondiale ?", "wwt_1"));
        q.add(new Question("Data fine della seconda guerra mondiale ?", "wwt_2"));
        q.add(new Question("Chi è l'attuale presidente della Repubblica", "president"));
        q.add(new Question("Chi è l'attuale Papa ?", "papa"));
        q.add(new Question("Ripetere il nome il fornito in precedenza", "name_ta")); // filePath duplicato...
        q.add(new Question("Ripetere la strada il fornita in precedenza", "street_ta")); // filePath duplicato...
        q.add(new Question("Elenca i mesi dell'anno al contrario", "reverse_month"));
        q.add(new Question("Conta da 1 a 20", "numbers"));
        q.add(new Question("Conta da 20 a 1", "reverse_numbers"));

        questionIterator = questions.iterator();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qt);

        btt_record = findViewById(R.id.btt_mic);
        btt_stop =  findViewById(R.id.btt_stop);
        btt_play =  findViewById(R.id.btt_play);
        btt_next =  findViewById(R.id.btt_next);

        tv_counter = findViewById(R.id.tv_counter);

        fillQuestions(questions);

        if(isMicrophonePresent()){
            getMicrophonePermission();
        }

        new HttpPostAsyncTask().execute(getRecordingFilePath());
    }

    public void btnNextPressed(View view){
        tv_question = findViewById(R.id.tv_question);

        if(questionIterator.hasNext())
        {
            currentQuestion = (Question) questionIterator.next();
            tv_question.setText(currentQuestion.getName());
            filePath = currentQuestion.getFilePath();
        }
        // TODO: else arrivato alla fine cambio schermato con resoconto

        // Disable Stop and Play button
        btt_stop.setEnabled(false);
        btt_play.setEnabled(false);

        // enable recording button
        btt_record.setEnabled(true);
        btt_record.setVisibility(View.VISIBLE);
    }

    public void btnRecordPressed(View view){

        // Enable Stop button
        btt_stop.setEnabled(true);
        btt_stop.setVisibility(View.VISIBLE);

        try {
            mediaRecorder= new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getRecordingFilePath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this, "Recording is started", Toast.LENGTH_LONG).show();

            Log.i("File pos: ",getRecordingFilePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void  btnStopPressed(View view){
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
        mediaRecorder=null;
        Toast.makeText(this, "Recording is stopped", Toast.LENGTH_LONG).show();

        // POST Request to Models' server

        // Enable play button
        btt_play.setEnabled(true);
        btt_play.setVisibility(View.VISIBLE);
    }
    public void btnPlayPressed(View view){
        try {
            mediaPlayer= new MediaPlayer();
            mediaPlayer.setDataSource(getRecordingFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Recording is playing", Toast.LENGTH_LONG).show();
    }
    private boolean isMicrophonePresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }else{
            return false;
        }
    }
    private void getMicrophonePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE);
        }
    }

    private String getRecordingFilePath(){
        ContextWrapper contextWrapper= new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, filePath + ".wav");
        return file.getPath();
    }
}
