package com.example.ankur.newsapp;

import android.annotation.TargetApi;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankur on 28-02-2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    String API_URL = "https://content.guardianapis.com/search?page-size=20&api-key=d009edd7-0bb9-4baf-b9fd-4abb4bcdce45&show-tags=contributor";

    public NewsLoader(Context context){
        super(context);
    }

    @Override
    public List<News> loadInBackground() {
        List<News> list = new ArrayList<>();
        URL url = null;
        try{
            url = new URL(API_URL);
        }catch(MalformedURLException e){
            // handle exception
        }
        HttpURLConnection urlCon = null;
        InputStream inputStream = null;
        String response = null;

        try {
            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("GET");
            urlCon.setReadTimeout(5000);
            urlCon.setConnectTimeout(7000);
            urlCon.connect();
            if (urlCon.getResponseCode() != 200) {
                // handle HTTP error code
            }
            inputStream = urlCon.getInputStream();
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            response = output.toString();
        }catch(IOException e){
            // handle exception
        }finally {
            if (urlCon != null) {
                urlCon.disconnect();
            }
        }

        try {
            JSONObject json = new JSONObject(response).getJSONObject("response");
            JSONArray newsArray = json.getJSONArray("results");
            for(int i=0;i<20;i++){
                JSONObject news = newsArray.getJSONObject(i);
                if(news.has("webPublicationDate") && !news.has("tags")){
                    String date = news.getString("webPublicationDate");
                    list.add(new News(news.getString("webTitle"), news.getString("sectionName"), news.getString("webUrl"), date.substring(0,10), 1));
                }
                else if(!news.has("webPublicationDate") && news.has("tags")){
                    JSONArray author = news.getJSONArray("tags");
                    if(author.getJSONObject(0).has("firstName")){
                        list.add(new News(news.getString("webTitle"), news.getString("sectionName"), news.getString("webUrl"),
                            author.getJSONObject(0).getString("firstName") + " " + author.getJSONObject(0).getString("lastName")));
                    }
                }
                else if(news.has("webPublicationDate") && news.has("tags")){
                    JSONArray author = news.getJSONArray("tags");
                    String date = news.getString("webPublicationDate");
                    if(author.getJSONObject(0).has("firstName")) {
                        list.add(new News(news.getString("webTitle"), date.substring(0, 10), news.getString("sectionName"), news.getString("webUrl"),
                                author.getJSONObject(0).getString("firstName") + " " + author.getJSONObject(0).getString("lastName")));
                    }
                    else {
                        list.add(new News(news.getString("webTitle"), news.getString("sectionName"), news.getString("webUrl"), date.substring(0,10), 1));
                    }
                }
                else{
                    list.add(new News(news.getString("webTitle"), news.getString("sectionName"), news.getString("webUrl")));
                }
            }
        }catch (JSONException e){
            // handle exception
        }

        return list;
    }
}
