package com.example.ankur.booklisting;

/**
 * Created by Ankur on 17-02-2018.
 */

public class BookDataClass {
    private String title;
    private String author;
    private String description;

    public BookDataClass(String t, String a, String d){
        title = t;
        author = a;
        description = d;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getDescription(){
        return description;
    }

}
