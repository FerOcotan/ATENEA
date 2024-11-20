package com.example.atenea;

public class Document {
    private String name;
    private String filePath;

    public Document(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }
}
