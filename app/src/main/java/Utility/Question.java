package Utility;

import java.io.Serializable;
import java.util.HashMap;

public class Question implements Serializable {
    private String name;        // Question phrase
    private String filePath;    // Where to find the file
    private String language;    // Response's Language
    private boolean isAnswer;
    private String[] answers ;

    public Question(String name, String filePath, String[] list){
        this.name = name;
        this.filePath = filePath;
        this.answers = list;
        this.isAnswer=false;
    }

    public String getName(){ return name; }
    public String getFilePath(){ return filePath; }
    public String getLanguage(){ return language; }
    public boolean getAnswer(){ return isAnswer; }

    public void setName(String name) { this.name = name;}
    public void setFilePath(String filePath) {this.filePath = filePath; }
    public void setLanguage(String language) { this.language = language; }
    public boolean setAnswer(boolean answer){ return this.isAnswer = answer; }


    public boolean checkQuestion(String risp){
        HashMap<Integer, String[]> resultsHMda1a20 = new HashMap<Integer, String[]>();
        HashMap<Integer, String[]> resultsHManni = new HashMap<Integer, String[]>();
        resultsHMda1a20 = numeriDa1a20IntAndString();
        resultsHManni = anniIntAndString();

        //risp 1 - 4 - 8 - 13 - 14 - 15 a 24
        if(name.equals("Come ti chiami ?") || name.equals("In che momento del giorno siamo ? (Mattina, Pomeriggio, ...)") ||
                name.equals("Che giorno è ?") || name.equals("In che stagione siamo ?") || name.equals("Quando sei natə ?") ||
                name.equals("Dove sei natə ?") || name.equals("Che lavoro svogli/hai svolto ?") ||
                name.equals("Data inizio della prima guerra mondiale ?") || name.equals("Data fine della prima guerra mondiale ?")||
                name.equals("Data inizio della seconda guerra mondiale ?") || name.equals("Data fine della seconda guerra mondiale ?") ||
                name.equals("Chi è l'attuale presidente della Repubblica") || name.equals("Chi è l'attuale Papa ?") ||
                name.equals("Ripetere il nome il fornito in precedenza") || name.equals("Pronuncia un mese dell'anno") ||
                name.equals("Pronuncia un numero da 1 a 20") || name.equals("Che oggetto è ? (1)") || name.equals("Che oggetto è ? (2)")){
            for(String s : answers ){
                if(risp.equals(s)){
                    return isAnswer = true;
                }
            }
            return isAnswer = false;
        }

        //risp 2 - 3 - 5
        else if(name.equals("Quanti anni hai ?") || name.equals("Che ore sono ?") || name.equals("Quanti ne abbiamo ?")){
            int number = Integer.parseInt(answers[0]);
            for(String s : resultsHMda1a20.get(number)) {
                if(s.equals(risp)){
                    return isAnswer = true;
                }
            }
            return isAnswer = false;
        }

        //risp 9
        else if(name.equals("In che anno siamo ?")){
            int number = Integer.parseInt(answers[0]);
            for(String s : resultsHManni.get(number)) {
                if(s.equals(risp)){
                    return isAnswer = true;
                }
            }
            return isAnswer = false;
        }
        return isAnswer = false;
    }


    private HashMap<Integer, String[]> numeriDa1a20IntAndString(){
        HashMap<Integer, String[]> results = new HashMap<Integer, String[]>();
        results.put(1, new String[]{"Uno", "One", "un"});
        results.put(2, new String[]{"Due", "Two", "Deux"});
        results.put(3, new String[]{"Tre", "Three", "Trois"});
        results.put(4, new String[]{"Quattro", "Four", "Quatre"});
        results.put(5, new String[]{"Cinque", "Five", "Cinq"});
        results.put(6, new String[]{"Sei", "Six", "Six"});
        results.put(7, new String[]{"Sette", "Seven", "Sept"});
        results.put(8, new String[]{"Otto", "Eight", "Huit"});
        results.put(9, new String[]{"Nove", "Nine", "Neuf"});
        results.put(10, new String[]{"Dieci", "Ten", "Dix"});
        results.put(11, new String[]{"Undici", "Eleven", "Onze"});
        results.put(12, new String[]{"Dodici", "Twelve", "Douze"});
        results.put(13, new String[]{"Tredici", "Thirteen", "Treize"});
        results.put(14, new String[]{"Quattordici", "Fourteen", "Quatorze"});
        results.put(15, new String[]{"Quindici", "Fifteen", "Quinze"});
        results.put(16, new String[]{"Sedici", "Sixteen", "Seize"});
        results.put(17, new String[]{"Diciasette", "Seventeen", "Dix_Sept"});
        results.put(18, new String[]{"Diciotto", "Eighteen", "Dix_Huit"});
        results.put(19, new String[]{"Diciannove", "Nineteen", "Dix_Neuf"});
        results.put(20, new String[]{"Venti", "Twenty", "Vingt",});
        results.put(21, new String[]{"Ventuno", "Twenty_One", "Vingt_Et_Un"});
        results.put(22, new String[]{"Ventidue", "Twenty_Two", "Vingt_Deux"});
        results.put(23, new String[]{"Ventitre", "Twenty_Three", "Vingt_Trois"});
        results.put(24, new String[]{"Ventiquattro", "Twenty_Four", "Vingt_Quatre"});
        results.put(25, new String[]{"Venticinque", "Twenty_Five", "Vingt_Cinq"});
        results.put(26, new String[]{"Ventisei", "Twenty_Six", "Vingt_Six"});
        results.put(27, new String[]{"Ventisette", "Twenty_Seven", "Vingt_Sept"});
        results.put(28, new String[]{"Ventotto", "Twenty_Eight", "Vingt_Huit"});
        results.put(29, new String[]{"Ventinove", "Twenty_Nine", "Vingt_Neuf"});
        results.put(30, new String[]{"Trenta", "Thirty", "Trente"});
        results.put(31, new String[]{"Trentuno", "Thirty_One", "Trente_Et_Un"});

        return results;
    }

    private HashMap<Integer, String[]> anniIntAndString() {
        HashMap<Integer, String[]> results = new HashMap<Integer, String[]>();
        results.put(2022, new String[]{"Duemilaventidue", "Two_Thousand_Twenty_Two", "Deux_Mille_Vingt_Deux"});
        return results;
    }
}
