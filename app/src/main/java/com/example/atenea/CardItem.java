package com.example.atenea;

public class CardItem {
    private final String title;
    private final String time;
    private final String date;

    public CardItem(String title, String time, String date) {
        this.title = title;
        this.time = time;
        this.date = date;
    }

    public String getTitle() { return title; }
    public String getTime() { return time; }
    public String getDate() { return date; }
}
