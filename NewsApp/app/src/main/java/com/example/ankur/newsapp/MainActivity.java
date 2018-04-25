package com.example.ankur.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private ListView listView;
    private NewsAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkNetworkStatus()){
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        }
        else{
            Toast.makeText(MainActivity.this, "Not Connected To Internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        listView = findViewById(R.id.listView);
        listAdapter = new NewsAdapter(this, data);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        listView = findViewById(R.id.listView);
        listAdapter = new NewsAdapter(this, new ArrayList<News>());
        listView.setAdapter(listAdapter);
    }

    public boolean checkNetworkStatus(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

}
