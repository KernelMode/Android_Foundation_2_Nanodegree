package com.example.ankur.inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        TextView itemName = findViewById(R.id.itemnamedetails);
        TextView price = findViewById(R.id.pricedetails);
        final TextView quantity = findViewById(R.id.quantityValDetails);
        TextView supplier = findViewById(R.id.supplieremaildetails);
        ImageView image = findViewById(R.id.itemimagedetails);

        final ItemDbHelper item = new ItemDbHelper(DetailsActivity.this);
        Cursor c = item.read(name);
        c.moveToFirst();
        String quant = c.getString(c.getColumnIndex(ItemContract.ItemEntry.COLUMN_QUANTITY));
        final String email = c.getString(c.getColumnIndex(ItemContract.ItemEntry.COLUMN_SUPPLIER));
        itemName.setText(c.getString(c.getColumnIndex(ItemContract.ItemEntry.COLUMN_INAME)));
        price.setText("$"+ c.getString(c.getColumnIndex(ItemContract.ItemEntry.COLUMN_PRICE)));
        quantity.setText(quant);
        supplier.setText(email);
        String imageUri = c.getString(c.getColumnIndex(ItemContract.ItemEntry.COLUMN_IMAGE));

        try {
            InputStream imageStream = getContentResolver().openInputStream(Uri.parse(imageUri));
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            image.setImageBitmap(selectedImage);
        }
        catch(FileNotFoundException e){
            //
        }

        Button order = findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Order");
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                }

            }
        });

        Button decQuant = findViewById(R.id.quantitydecbuttondetails);
        decQuant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cq = item.read(name);
                cq.moveToFirst();
                int q = cq.getInt(cq.getColumnIndex(ItemContract.ItemEntry.COLUMN_QUANTITY));
                if( q > 0){
                    item.updateItemQuantity(name, q-1);
                    quantity.setText("" + (q-1));
                }
            }
        });

        Button incQuant = findViewById(R.id.quantityincbuttondetails);
        incQuant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cq = item.read(name);
                cq.moveToFirst();
                int q = cq.getInt(cq.getColumnIndex(ItemContract.ItemEntry.COLUMN_QUANTITY));
                if( q >= 0){
                    item.updateItemQuantity(name, q+1);
                    quantity.setText("" + (q+1));
                }
            }
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailsActivity.this)
                        .setMessage("Are you sure you want to delete the item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                item.deleteItem(name);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

    }
}
