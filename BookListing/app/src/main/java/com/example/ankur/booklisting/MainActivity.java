package com.example.ankur.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = findViewById(R.id.searchbutton);
        final EditText searchBox = findViewById(R.id.searchbox);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNetworkStatus()){
                    String query = searchBox.getText().toString();
                    Intent bookIntent = new Intent(MainActivity.this, BookListActivity.class);
                    bookIntent.putExtra("query",query);
                    startActivity(bookIntent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Not Connected To Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkNetworkStatus(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }
}
