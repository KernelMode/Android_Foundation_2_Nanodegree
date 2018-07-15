package com.example.ankur.inventory;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ItemAdapter adapter;
    ItemDbHelper itemDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addItem = findViewById(R.id.addbutton);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItemIntent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(addItemIntent);
            }
        });

        itemDb = new ItemDbHelper(MainActivity.this);
        if(itemDb.rows() == 0){
            itemDb.insertDefault();
        }
        Cursor data = itemDb.readAllItems();
        adapter = new ItemAdapter(MainActivity.this, data);
        ListView list = findViewById(R.id.listview);
        list.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(itemDb.readAllItems());
        TextView text = findViewById(R.id.textbox);
        if(itemDb.rows() == 0){
            text.setVisibility(View.VISIBLE);
        }
        else{
            text.setVisibility(View.INVISIBLE);
        }
    }
}
