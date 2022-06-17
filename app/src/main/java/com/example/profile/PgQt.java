package com.example.profile;

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

    private String totalRisp[]= {"Agosto", "Aprile", "Autista", "Autunno", "Avvocato", "Benedetto_Sedicesimo", "Celeste",
            "Cento", "Cinquanta", "Cinque", "Davide", "Dicembre", "Diciannove", "Diciassette", "Diciotto", "Dieci",
            "Dodici", "Domenica", "Due", "Duemila", "Duemilaventi", "Duemilaventidue", "Duemilaventuno", "Estate",
            "Febbraio", "Filippo", "Firenze", "Francesco", "Gennaio", "Genova", "Giorgio", "Giovanni_Paolo_Secondo",
            "Giovedi", "Giugno", "Insegnante", "Inverno", "Luglio", "Lunedi", "Maggio", "Mario", "Martedi", "Marzo",
            "Matita", "Mattarella", "Mattina", "Mercoledi", "Millenovecento", "Millenovecentodiciotto", "Millenovecentoquarantacinque",
            "Millenovecentoquattordici", "Millenovecentotrentanove", "Napoli", "Napolitano", "Notte", "Novanta", "Nove", "Novembre",
            "Ottanta", "Otto", "Ottobre", "Palla", "Piatto", "Pomeriggio", "Pompiere", "Primavera", "Quaranta", "Quattordici", "Quattro",
            "Quindici", "Sabato", "Sedici", "Sei", "Sera", "Sergio", "Sessanta", "Settanta", "Sette", "Settembre", "Tre", "Tredici",
            "Trenta", "Trentuno", "Undici", "Uno", "Venerdi", "Venezia", "Venti", "Venticinque", "Ventidue", "Ventinove", "Ventiquattro",
            "Ventisei", "Ventisette", "Ventitre", "Ventotto", "Ventuno",

            "August", "April", "Driver", "Autumn", "Lawyer", "Celestial",
            "Hundred", "Fifty", "Five", "Dave", "December", "Nineteen", "Seventeen", "Eighteen", "Ten",
            "Twelve", "Sunday", "Two", "Two_Thousand", "Two_Thousand_Twenty", "Two_Thousand_Twenty_Two", "Two_Thousand_Twenty_One", "Summer",
            "February", "Philipp", "Florence", "January", "Genoa",
            "Thursday", "June", "Teacher", "Winter", "July", "Monday", "May", "Tuesday", "March",
            "Pencil", "Morning", "Wednesday", "Nineteen_Hundred", "Nineteen_Hundred_Eighteen", "Nineteen_Hundred_Forty_Five",
            "Nineteen_Hundred_Fourteen", "Nineteen_Hundred_Thirty_Nine", "Naples", "Night", "Ninety", "Nine", "November",
            "Eighty", "Eight", "October", "Ball", "Plate", "Afternoon", "Fireman", "Spring", "Forty", "Fourteen", "Four",
            "Fifteen", "Saturday", "Sixteen", "Six", "Evening", "Sixty", "Seventy", "Seven", "September", "Three", "Thirteen",
            "Thirty", "Thirty_One", "Eleven", "One", "Friday", "Venice", "Twenty", "Twenty_Five", "Twenty_Two", "Twenty_Nine", "Twenty_Four",
            "Twenty_Six", "Twenty_Seven", "Twenty_Three", "Twenty_Eight", "Twenty_One",

            "Aout", "Avril", "Chauffeur", "Automne", "Avocat",
            "Cent", "Cinquante", "Cinq", "Decembre", "Dix_Neuf", "Dix_Sept", "Dix_Huit", "Dix",
            "Douze", "Dimanche", "Deux", "Deux_mille", "Deux_Mille_Vingt", "Deux_Mille_Vingt_Deux", "Deux_Mille_Vingt_Et_Un", "Ete",
            "Fevrier", "Florence", "Janvier", "Genes",
            "Jeudi", "Juin", "Professeur", "Hiver", "Juillet", "Lundi", "Mai", "Mardi", "Mars",
            "Crayon", "Matin", "Mercredi", "Mille_Neuf_Cent", "Mille_Neuf_Nent_Dix_Huit", "Mille_Neuf_Cent_Quarante_Cinq",
            "Mil_Neuf_Cent_Quatorze","Mille_Neuf_Cent_Trente_Neuf", "Naples","Nuit", "Quatre_Vingt_Dix", "Neuf", "Novembre",
            "Quatre_Vingt", "Huit", "Octobre", "Ballon", " Apres_Midi", " Pompier", " Printemps", " Quarante", " Quatorze", " Quatre",
            "Quinze", "Samedi", "Seize", "Six", "Soir", "Soixante", "Soixante_Dix", "Sept", "Septembre", "Trois", "Treize",
            "Trente", "Trente_Et_Un", "Onze", "Un", "Vendredi", "Venise", "Vingt", "Vingt_Cinq", "Vingt_Deux", "Vingt_Neuf", "Vingt_Quatre",
            "Vingt_Six", "Vingt_Sept", "Vingt_Trois", "Vingt_Huit", "Vingt_Et_Un"};

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

                    // POST Request to Models' server

                    // Enable play button
                    btt_play.setEnabled(true);
                    btt_play.setVisibility(View.VISIBLE);
                    btt_record.setImageDrawable(getResources().getDrawable(R.drawable.microfono_btsu, null));
                    isRecording=false;
                    //risulatato risposte
                    String output = null;
        /*try { output = new HttpPostAsyncTask().execute(getRecordingFilePath()).get();
        } catch (ExecutionException e) {e.printStackTrace();
        } catch (InterruptedException e) {e.printStackTrace(); }

        //Log.i(TAG, output);

        resultServerAnalyze(output);*/
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
    /*public void  btnStopPressed(View view){
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
        mediaRecorder=null;
        Toast.makeText(this, "Recording is stopped", Toast.LENGTH_LONG).show();

        // POST Request to Models' server

        // Enable play button
        btt_play.setEnabled(true);
        btt_play.setVisibility(View.VISIBLE);

        //risulatato risposte
        String output = null;
        try { output = new HttpPostAsyncTask().execute(getRecordingFilePath()).get();
        } catch (ExecutionException e) {e.printStackTrace();
        } catch (InterruptedException e) {e.printStackTrace(); }

        //Log.i(TAG, output);

        resultServerAnalyze(output);
    }*/

    //funzione che prende la stringa che viene dal server
    public void resultServerAnalyze(String output)
    {
        String[] st = output.split(" ");

        //---------------------------------------------
        //LINGUA
        String lang = st[0];
        Log.i(TAG, lang);
        currentQuestion.setLanguage(lang);
        if(lang.equals("IT")) Toast.makeText(this, "Language: Italiano", Toast.LENGTH_LONG).show();
        else if(lang.equals("EN")) Toast.makeText(this, "Language: Inglese", Toast.LENGTH_LONG).show();
        else if(lang.equals("FR")) Toast.makeText(this, "Language: Francese", Toast.LENGTH_LONG).show();

        //---------------------------------------------
        //RISPOSTA
        String risp = st[1];
        Log.i(TAG, risp);

        //Inizializzo variabili per rispondere alle domande
        Calendar calendar = Calendar.getInstance();
        //acquisisco profileData da NewProfile
        Intent i = getIntent();
        HashMap<String, String> profileData = (HashMap<String, String>)i.getSerializableExtra("profileData");
        //Log.i(TAG, "profileData: "+profileData.get("Birthday_year"));

        if(index == 0) currentQuestion.setAnswer(risp.equals(profileData.get("Name")));

        else if(index == 1) {
            int currente_YEAR = calendar.get(Calendar.YEAR);
            int year_of_person = Integer.parseInt(profileData.get("Birthday_year"));
            currentQuestion.setAnswer(risp.equals(currente_YEAR - year_of_person));
        }

        else if(index == 2) currentQuestion.setAnswer(risp.equals(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))));

        else if(index == 3){
            int this_day_moment = calendar.get(Calendar.HOUR_OF_DAY);
            String day_moment = "";
            if(this_day_moment > 00 || this_day_moment <= 6) day_moment = "Notte";
            else if(this_day_moment > 6  || this_day_moment <= 12) day_moment = "Mattina";
            else if(this_day_moment > 12  || this_day_moment <= 18) day_moment = "Pomeriggio";
            else if(this_day_moment > 18  || this_day_moment <= 24) day_moment = "Sera";
            currentQuestion.setAnswer(answerIsPresent(day_moment));
        }

        else if(index == 4) currentQuestion.setAnswer(risp.equals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))));

        else if(index == 5) {
            int this_DAY_OF_WEEK = calendar.get(Calendar.DAY_OF_WEEK);
            String giorno = "";

            if(this_DAY_OF_WEEK == Calendar.SUNDAY) giorno = "Domenica";
            else if(this_DAY_OF_WEEK == Calendar.MONDAY) giorno = "Lunedi";
            else if(this_DAY_OF_WEEK == Calendar.TUESDAY) giorno = "Martedi";
            else if(this_DAY_OF_WEEK == Calendar.WEDNESDAY) giorno = "Mercoledi";
            else if(this_DAY_OF_WEEK == Calendar.THURSDAY) giorno = "Giovedi";
            else if(this_DAY_OF_WEEK == Calendar.FRIDAY) giorno = "Venerdi";
            else if(this_DAY_OF_WEEK == Calendar.SATURDAY) giorno = "Sabato";

            currentQuestion.setAnswer(risp.equals(giorno));
        }

        else if(index == 6){
            int this_MONTH = calendar.get(Calendar.MONTH);
            String mese = "";

            if(this_MONTH == Calendar.JANUARY) mese = "Gennaio";
            else if(this_MONTH == Calendar.FEBRUARY) mese = "Febbraio";
            else if(this_MONTH == Calendar.MARCH) mese = "Marzo";
            else if(this_MONTH == Calendar.APRIL) mese = "Aprile";
            else if(this_MONTH == Calendar.MAY) mese = "Marzo";
            else if(this_MONTH == Calendar.JUNE) mese = "Giugno";
            else if(this_MONTH == Calendar.JULY) mese = "Luglio";
            else if(this_MONTH == Calendar.AUGUST) mese = "Agosto";
            else if(this_MONTH == Calendar.SEPTEMBER) mese = "Settembre";
            else if(this_MONTH == Calendar.OCTOBER) mese = "Ottobre";
            else if(this_MONTH == Calendar.NOVEMBER) mese = "Novembre";
            else if(this_MONTH == Calendar.DECEMBER) mese = "Dicembre";

            currentQuestion.setAnswer(risp.equals(mese));
        }

        else if(index == 7){
            int this_MONTH = calendar.get(Calendar.MONTH);
            String stagione = "";

            if(this_MONTH >= 0 && this_MONTH <= 2) stagione = "Inverno";
            if(this_MONTH >= 3 && this_MONTH <= 6) stagione = "Primavera";
            if(this_MONTH >= 7 && this_MONTH <= 9) stagione = "Estate";
            if(this_MONTH >= 10 && this_MONTH <= 12) stagione = "Autunno";

            currentQuestion.setAnswer(risp.equals(stagione));
        }

        else if(index == 8){
            int this_YEAR = calendar.get(Calendar.YEAR);
            String year = "";
            if(risp.equals("Duemilaventidue")) year = "Duemilaventidue";
            if(risp.equals("Two_Thousand_Twenty_Two")) year = "Two_Thousand_Twenty_Two";
            currentQuestion.setAnswer(risp.equals(year));
        }

        else if(index == 9){
            //oggetto 1
        }

        else if(index == 10){
            //oggetto 2
        }

        else if(index == 13){
            String Birthday_year = "";
            if(profileData.get("Birthday_year").equals("2000")) Birthday_year = "Duemila";
            currentQuestion.setAnswer(risp.equals(Birthday_year));
        }

        else if(index == 14){
            String myBirthplace = profileData.get("Birthplace");
            currentQuestion.setAnswer(risp.equals(myBirthplace));
        }

        else if(index == 15){
            String myJob = profileData.get("Job");
            currentQuestion.setAnswer(risp.equals(myJob) );
        }

        else if(index == 16){
            currentQuestion.setAnswer(risp.equals("Millenovecentoquattordici"));
        }

        else if(index == 17){
            currentQuestion.setAnswer(risp.equals("Millenovecentodiciotto"));
        }

        else if(index == 18){
            currentQuestion.setAnswer(risp.equals("Millenovecentotrentanove"));
        }

        else if(index == 19){
            currentQuestion.setAnswer(risp.equals("Millenovecentoquarantacinque"));
        }

        else if(index == 20){
            //OK per tutte le lingie
            currentQuestion.setAnswer(risp.equals("Napolitano"));
        }

        else if(index == 21){
            //OK per tutte le lingie
            currentQuestion.setAnswer(risp.equals("Giovanni_Paolo_Secondo"));
        }


        else if(index == 22){
            //OK per tutte le lingie
            currentQuestion.setAnswer(risp.equals("Mario"));
        }

        else if(index == 23){
            //OK per tutte le lingie
            currentQuestion.setAnswer(risp.equals("Via Bobbio"));
        }

        /*Elenca i mesi dell'anno al contrario", "reverse_month
        "Conta da 1 a 20", "numbers"
        "Conta da 20 a 1", "reverse_numbers"*/

        Log.i(TAG, "Answer: "+questions.get(index).getAnswer());
        index++;
    }

    private boolean answerIsPresent(String answer)
    {
        for (int i=0; i< totalRisp.length; i++) {
            if (answer.equals(totalRisp[i])) return true;
        }
        return false;
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
