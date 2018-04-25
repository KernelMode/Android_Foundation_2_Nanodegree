package com.example.ankur.newsapp;

/**
 * Created by Ankur on 28-02-2018.
 */

public class News {
    private String headline;
    private String date;
    private String category;
    private String link;
    private String author;

    public News(String h, String d, String c, String l, String a){
        headline = h;
        date = d;
        category = c;
        link = l;
        author = a;
    }

    public News(String h, String c, String l, String a){
        headline = h;
        category = c;
        link = l;
        date = null;
        author = a;
    }

    public News(String h, String c, String l, String d, int a){
        headline = h;
        category = c;
        link = l;
        date = d;
        author = null;
    }


    public News(String h, String c, String l ){
        headline = h;
        category = c;
        link = l;
        date = null;
        author = null;
    }

    public String getLink(){
        return link;
    }

    public String getHeadline(){
        return headline;
    }

    public String getDate(){
        return date;
    }

    public String getCategory(){
        return category;
    }

    public String getAuthor(){return author;}
}
