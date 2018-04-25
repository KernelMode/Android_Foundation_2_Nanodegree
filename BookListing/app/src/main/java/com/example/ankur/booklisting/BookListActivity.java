package com.example.ankur.booklisting;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class BookListActivity extends AppCompatActivity {

    private final String API_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=10&q=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        GetBookInfoAsync task = new GetBookInfoAsync(query);
        task.execute();
    }

    private class GetBookInfoAsync extends AsyncTask<URL, Void, String> {

        String query = null;

        public GetBookInfoAsync(String temp){
            query = temp;
        }

        @Override
        protected String doInBackground(URL... urltemp) {

            Intent intent = getIntent();
            URL url = null;
            try {
                url = new URL(API_URL + query);
            }catch (MalformedURLException e){
                return null;
            }

            String response = "";
            HttpURLConnection urlCon = null;
            InputStream inputStream = null;

            try {
                urlCon = (HttpURLConnection)url.openConnection();
                urlCon.setRequestMethod("GET");
                urlCon.setReadTimeout(5000);
                urlCon.setConnectTimeout(7000);
                urlCon.connect();
                if(urlCon.getResponseCode() != 200){
                    return response;
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

            }catch (IOException e){

            }finally {
                if (urlCon != null) {
                    urlCon.disconnect();
                }
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("")){
                TextView text = findViewById(R.id.noDataFound);
                text.setText("Error Occured. Something Went Wrong\n Please Try Again...");
                text.setVisibility(View.VISIBLE);
                ListView list = findViewById(R.id.listView);
                list.setVisibility(View.INVISIBLE);
            }
            populateListItems(result);
        }

        void populateListItems(String response){
            ListView listView = findViewById(R.id.listView);
            TextView text = findViewById(R.id.noDataFound);
            int total = 10;
            ArrayList<BookDataClass> bookList = new ArrayList<>();
            try{
                JSONObject json = new JSONObject(response);
                int items = json.getInt("totalItems");
                if(items <= 0){
                    listView.setVisibility(View.INVISIBLE);
                    text.setVisibility(View.VISIBLE);
                    return;
                }
                if(items < 10){
                    total = items;
                }

                JSONArray jsonItems = json.getJSONArray("items");

                for(int i = 0; i < total; i++){
                    JSONObject json1 = jsonItems.getJSONObject(i).getJSONObject("volumeInfo");
                    String title = json1.getString("title");
                    String desc = "";
                    if(json1.has("description")){
                        desc = json1.getString("description");
                    }
                    String author = "";
                    if(json1.has("authors")){
                        author = json1.getJSONArray("authors").getString(0);
                    }
                    bookList.add(new BookDataClass(title,author,desc));
                }
            }catch(JSONException e){
                // handle
            }

            BookDataAdapter adapter = new BookDataAdapter(BookListActivity.this, bookList);
            listView.setAdapter(adapter);
        }
    }
}