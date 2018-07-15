package com.example.ankur.inventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {

    private int quantity = 0;
    Uri imageURI = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        setTitle("ADD ITEM");

        Button decQuantity = findViewById(R.id.quantitydecbutton);
        final TextView quantityView = findViewById(R.id.quantity);
        decQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity > 0){
                    quantity -= 1;
                }
                quantityView.setText("" + quantity);
            }
        });

        Button incQuantity = findViewById(R.id.quantityincbutton);
        incQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity += 1;
                quantityView.setText("" + quantity);
            }
        });

        Button image = findViewById(R.id.imagechoose);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Choose Picture"),1);
            }
        });

        Button addItem = findViewById(R.id.additemtodb);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText itemName = findViewById(R.id.itemname);
                EditText price = findViewById(R.id.price);
                EditText supplier = findViewById(R.id.supplier);
                String iName = itemName.getText().toString();
                String pr = price.getText().toString();
                String supp = supplier.getText().toString();
                int qtity = quantity;
                if(!iName.matches("") && !pr.matches("") && qtity != 0 && imageURI !=null && !supp.matches("")){
                    String imguri = imageURI.toString();
                    ItemDbHelper item = new ItemDbHelper(AddItemActivity.this);
                    item.insertItem(iName, qtity, pr, imguri, supp);
                    finish();
                }
                else{
                    Toast.makeText(AddItemActivity.this, "Enter All The Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                imageURI = data.getData();
            }
        }
    }

}
