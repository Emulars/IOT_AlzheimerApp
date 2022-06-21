package com.example.profile;

import static java.lang.Math.abs;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import Utility.HttpPostAsyncTask;
import Utility.LanguageType;
import Utility.Question;

// TODO:
// - Disabilitare bottoni prima della registrazione
// - Rimuovere variabile globale currentQuestion
// - Per profilazione potremmo usare Hashtable

public class PgQt extends AppCompatActivity {

    private final String TAG = "PgQt";
    TextView tv_question, tv_counter = null;
    Button btt_stop, btt_play, btt_next= null;
    ImageButton btt_record;
    boolean isRecording=false;
    // Audio
    private static int MICROPHONE_PERMISSION_CODE=200;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String filePath = "";

    // Question
    Question currentQuestion = null;
    private ArrayList<Question> questions = new ArrayList<Question>();
    Iterator questionIterator = null;
    int index = 0;

    Calendar calendar;
    HashMap<String, String> profileData = new HashMap<String, String>();

    private void fillQuestions(ArrayList<Question> q){
        q.add(new Question("Come ti chiami ?", "name", new String[]{ profileData.get("Name") } ));
        q.add(new Question("Quanti anni hai ?", "age", new String[] {inizialateEge()}));
        q.add(new Question("Che ore sono ?", "time", new String[] {String.valueOf(calendar.get(Calendar.HOUR))} ));
        q.add(new Question("In che momento del giorno siamo ? (Mattina, Pomeriggio, ...)", "day_moment", new String[] {"Notte", "Mattino", "Pomeriggio", "Sera",
                                                                                                                                    "Night", "Morning", "Afternoon", "Evening",
                                                                                                                                    "Nuit", "Matin", "Apres_midi", "Soir"}));
        q.add(new Question("Quanti ne abbiamo ?", "day", new String[] {String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))}));
        q.add(new Question("Che giorno è ?", "day_of_the_week", new String[] {"Domenica", "Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato",
                                                                                            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
                                                                                            "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"}));
        q.add(new Question("In che mese siamo ?", "month", new String[] {"Gennaio", "Febbraio","Marzo", "Aprile", "Marzo", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre",
                                                                                        "January", "February", "March", "April", "March", "June", "July", "August", "September", "October", "November", "December",
                                                                                        "Janvier", "Fevrier", "Mars", "Avril", "Mars", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"}));
        q.add( new Question("In che stagione siamo ?", "season", new String[]{"Inverno","Primavera","Estate","Autunno",
                                                                                            "Winter", "Spring", "Summer", "Autumn",
                                                                                            "Hiver", "Printemps", "Ete", "Tomber"}));
        q.add(new Question("In che anno siamo ?", "year", new String[]{String.valueOf(calendar.get(Calendar.YEAR))}));
        q.add(new Question("Che oggetto è ? (1)", "object_1", new String[]{"Palla", "Ball", "Ballon"}));
        q.add(new Question("Che oggetto è ? (2)", "object_2", new String[]{"Matita", "Pencil", "Crayon"}));

        q.add(new Question("Ricordati questo nome: \'Mario\', ti verrà richiesto in seguito", "name_ta", new String[]{"Mario"}));
        //q.add(new Question("Ricordati questo indirizzo: \'Via Bobbio\', ti verrà richiesto in seguito", "street_ta", new String[]{"Via Bobbio"}));

        q.add(new Question("Quando sei natə ?", "birthday", new String[]{"Duemila", "Duemilaventi", "Duemilaventidue", "Duemilaventuno",
                                                                                    "Two_Thousand", "Two_Thousand_Twenty", "Two_Thousand_Twenty_Two", "Two_Thousand_Twenty_One",
                                                                                    "Deux_mille", "Deux_Mille_Vingt", "Deux_Mille_Vingt_Deux", "Deux_Mille_Vingt_Et_Un"} ));
        q.add(new Question("Dove sei natə ?", "birth_loc", new String[]{profileData.get("Birthplace")}));
        q.add(new Question("Che lavoro svogli/hai svolto ?", "job", new String[]{profileData.get("Job")} ));
        q.add(new Question("Data inizio della prima guerra mondiale ?", "wwo_1", new String[]{"Millenovecentoquattordici", "Nineteen_Hundred_Fourteen", "Mil_Neuf_Cent_Quatorze"}));
        q.add(new Question("Data fine della prima guerra mondiale ?", "wwo_2", new String[]{"Millenovecentodiciotto","Nineteen_Hundred_Eighteen","Mille_Neuf_Nent_Dix_Huit"}));
        q.add(new Question("Data inizio della seconda guerra mondiale ?", "wwt_1", new String[]{"Millenovecentotrentanove", "Nineteen_Hundred_Thirty_Nine", "Mille_Neuf_Cent_Trente_Neuf"}));
        q.add(new Question("Data fine della seconda guerra mondiale ?", "wwt_2", new String[]{"Millenovecentoquarantacinque", "Nineteen_Hundred_Forty_Five","Mille_Neuf_Cent_Quarante_Cinq"}));
        q.add(new Question("Chi è l'attuale presidente della Repubblica", "president", new String[]{"Napolitano", "Mattarella"}));
        q.add(new Question("Chi è l'attuale Papa ?", "papa", new String[]{"Francesco", "Giovanni_Paolo_Secondo"}));
        q.add(new Question("Ripetere il nome il fornito in precedenza", "name_ta", new String[]{"Mario"})); // filePath duplicato...
        //q.add(new Question("Ripetere la strada il fornita in precedenza", "street_ta")); // filePath duplicato...
        q.add(new Question("Pronuncia un mese dell'anno", "reverse_month", new String[] {"Gennaio", "Febbraio","Marzo", "Aprile", "Marzo", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre",
                "January", "February", "March", "April", "March", "June", "July", "August", "September", "October", "November", "December",
                "Janvier", "Fevrier", "Mars", "Avril", "Mars", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"}));
        q.add(new Question("Pronuncia un numero da 1 a 20", "numbers", new String[]{"Uno", "Due", "Tre", "Quattro", "Cinque", "Sei", "Sette", "Otto", "Nove", "Dieci", "Undici", "Dodici", "Tredici", "Quattordi", "Quindici", "Sedici", "Diciasette", "Diciotto", "Diciannove", "Venti",
                "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen","Twenty",
                "Un", "Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf", "Dix", "Onze", "Douze", "Treize","Quatorze","Quinze","Seize","Dix_Sept"," Dix_huit","Dix_neuf","Vingt"}));
        //q.add(new Question("Conta da 20 a 1", "reverse_numbers"));

        questionIterator = questions.iterator();
    }

    //Funzioni di inizializzazione
    private String inizialateEge(){
        int currente_YEAR = calendar.get(Calendar.YEAR);
        int year_of_person = Integer.parseInt(profileData.get("Birthday_year"));
        //Log.i(TAG + "inizialateEge: ", String.valueOf(currente_YEAR - year_of_person));
        int result = currente_YEAR - year_of_person;
        if(result < 1) result = 1;
        return String.valueOf(result);
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

        Intent i = getIntent();
        profileData = (HashMap<String, String>)i.getSerializableExtra("profileData");

        calendar = Calendar.getInstance();

        fillQuestions(questions);

        if(isMicrophonePresent()){
            getMicrophonePermission();
        }
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
        btt_record.setBackgroundResource(R.drawable.trasparenza);
        switch ( view.getId()){
            case R.id.btt_mic:
                if(isRecording){
                    mediaRecorder.stop();
                    mediaRecorder.reset();
                    mediaRecorder.release();
                    mediaRecorder=null;
                    Toast.makeText(this, "Recording is stopped", Toast.LENGTH_LONG).show();

                    // Enable play button
                    btt_play.setEnabled(true);
                    btt_play.setVisibility(View.VISIBLE);
                    btt_record.setImageDrawable(getResources().getDrawable(R.drawable.microfono_btsu, null));
                    isRecording=false;
                    // POST Request to Models' server
                    String output = null;
                    try {
                        output = new HttpPostAsyncTask().execute(   getString(R.string.server_url_remote),       // strings[0] -> Service URL
                                getString(R.string.server_charset),         // strings[1] -> Server charset
                                getString(R.string.user_agent),             // strings[2] -> User agent
                                "file",                                     // strings[3] -> Field name
                                getRecordingFilePath()).get();              // strings[4] -> File path
                    }catch (ExecutionException e) {
                        e.printStackTrace();
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    Log.i(TAG, output);
                    HashMap<String, String> results = JSONStringToHashMap(output);
                    String lang = results.get("language");
                    String keyword = results.get("keyword");

                    Log.i(TAG + "language: ", lang);
                    currentQuestion.setLanguage(lang);
                    if(lang.equals("IT")) Toast.makeText(this, "Language: Italiano", Toast.LENGTH_LONG).show();
                    else if(lang.equals("EN")) Toast.makeText(this, "Language: Inglese", Toast.LENGTH_LONG).show();
                    else if(lang.equals("FR")) Toast.makeText(this, "Language: Francese", Toast.LENGTH_LONG).show();


                    Log.i(TAG + "keyword: ", results.get("keyword"));
                    // Check models results
                    //TODO: chiama currentQuestion
                    currentQuestion.checkQuestion(keyword);

                }else
                {
                    //Start recording
                    //se sono stati dati i permessi allora puoi registrare
                    //metodo per avviare registrazione
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
                    btt_record.setImageDrawable(getResources().getDrawable(R.drawable.microfono_btgiu, null));
                    isRecording = true;
                }

                break;
        }
        // Enable Stop button
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

    private HashMap<String, String> JSONStringToHashMap(String s){
        // Remove braces and quotation marks
        s = s.replace("{", "");
        s = s.replace("}", "");
        s = s.replace("\"", "");
        String[] keyValuePairs = s.split(",");      //split the string to create key-value pairs

        // Models results
        // keyword = spotted word
        // language = spotted language
        HashMap<String, String> results = new HashMap<String, String>();
        for(String pair : keyValuePairs)                        //iterate over the pairs
        {
            String[] entry = pair.split(":");                   //split the pairs to get key and value
            results.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
        }

        return results;
    }
}
