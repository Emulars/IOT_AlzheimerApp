package Utility;

public class Question {
    private String name;        // Question phrase
    private String filePath;    // Where to find the file
    private String language;    // Response's Language

    public Question(String name, String filePath){
        this.name = name;
        this.filePath = filePath;
    }

    public String getName(){ return name; }
    public String getFilePath(){ return filePath; }
    public String getLanguage(){ return language; }

    public void setName(String name) { this.name = name;}
    public void setFilePath(String filePath) {this.filePath = filePath; }
    public void setLanguage(String language) { this.language = language; }

}
