package Utility;

import java.util.ArrayList;

public class Question {
    private String name;        // Question phrase
    private String filePath;    // Where to find the file
    private String language;    // Response's Language
    private boolean answer;
    private String[] answers ;

    public Question(String name, String filePath, String[] list){
        this.name = name;
        this.filePath = filePath;
        this.answers = list;
    }

    public String getName(){ return name; }
    public String getFilePath(){ return filePath; }
    public String getLanguage(){ return language; }
    public boolean getAnswer(){ return answer; }

    public void setName(String name) { this.name = name;}
    public void setFilePath(String filePath) {this.filePath = filePath; }
    public void setLanguage(String language) { this.language = language; }
    public boolean setAnswer(boolean answer){ return this.answer = answer; }

}
