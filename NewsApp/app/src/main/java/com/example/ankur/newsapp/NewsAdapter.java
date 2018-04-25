package com.example.ankur.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankur on 28-02-2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private List <News> news = new ArrayList<>();
    private Context context;
    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final News item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }
        context = convertView.getContext();
        TextView headline = convertView.findViewById(R.id.headline);
        TextView date = convertView.findViewById(R.id.date);
        TextView category = convertView.findViewById(R.id.category);
        TextView author = convertView.findViewById(R.id.author);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                if(browserIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(browserIntent);
                }
            }
        });

        headline.setText(item.getHeadline());
        date.setText(item.getDate());
        category.setText(item.getCategory());
        author.setText(item.getAuthor());

        return convertView;
    }
}
