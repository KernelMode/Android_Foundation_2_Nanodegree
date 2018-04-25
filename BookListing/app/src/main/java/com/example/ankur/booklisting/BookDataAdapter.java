package com.example.ankur.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ankur on 17-02-2018.
 */

public class BookDataAdapter extends ArrayAdapter<BookDataClass> {

    public BookDataAdapter(Context context, List<BookDataClass> bookInfo) {
        super(context, 0, bookInfo);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookDataClass item = getItem(position);
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }

        TextView title = view.findViewById(R.id.Title);
        TextView desc = view.findViewById(R.id.desc);
        TextView author = view.findViewById(R.id.AuthorName);

        title.setText(item.getTitle());
        desc.setText(item.getDescription());
        author.setText(item.getAuthor());
        return view;
    }
}
