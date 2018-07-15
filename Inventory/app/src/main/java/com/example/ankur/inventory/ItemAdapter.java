package com.example.ankur.inventory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Ankur on 24-03-2018.
 */

public class ItemAdapter extends CursorAdapter {

    public ItemAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView itemName = view.findViewById(R.id.itemnameview);
        TextView price = view.findViewById(R.id.priceview);
        final TextView quantity = view.findViewById(R.id.quanitiyview);
        ImageView image = view.findViewById(R.id.itemimageview);
        final View tempView = view;
        final String name = cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_INAME));
        String pr = cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_PRICE));
        final String quant = cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_QUANTITY));
        String imageUri = cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_IMAGE));

        itemName.setText(name);
        price.setText("Price: $" + pr);
        quantity.setText("Quantity: " + quant);
        try {
            InputStream imageStream = view.getContext().getContentResolver().openInputStream(Uri.parse(imageUri));
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            image.setImageBitmap(selectedImage);
        }
        catch(FileNotFoundException e){
            //
        }

        Button sell = view.findViewById(R.id.sellview);
        sell.setOnClickListener(new View.OnClickListener() {
            int q = Integer.parseInt(quant);
            @Override
            public void onClick(View v) {
                if(q > 0){
                    ItemDbHelper item = new ItemDbHelper(tempView.getContext());
                    item.updateItemQuantity(name, q - 1);
                    q -= 1;
                    quantity.setText("Quantity: " + q);
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(tempView.getContext(), DetailsActivity.class);
                detail.putExtra("name", name);
                tempView.getContext().startActivity(detail);
            }
        });
    }

}
